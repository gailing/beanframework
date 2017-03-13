package com.beanframework.photobooth.web;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FilenameUtils;
import com.beanframework.photobooth.domain.ImageFile;
import com.beanframework.photobooth.utils.CSVUtils;
import com.beanframework.photobooth.utils.FlashairManager;
import com.beanframework.photobooth.utils.ImageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class DashboardController extends CommonController {

	protected final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@PostConstruct
	public void init(){
		FlashairManager.getInstance().startDownload();
	}
	
	public BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			// g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			// RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}

	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

		int original_width = imgSize.width;
		int original_height = imgSize.height;
		int bound_width = boundary.width;
		int bound_height = boundary.height;
		int new_width = original_width;
		int new_height = original_height;

		// first check if we need to scale width
		if (original_width > bound_width) {
			// scale width to fit
			new_width = bound_width;
			// scale height to maintain aspect ratio
			new_height = (new_width * original_height) / original_width;
		}

		// then check if we need to scale even with the new height
		if (new_height > bound_height) {
			// scale height to fit instead
			new_height = bound_height;
			// scale width to maintain aspect ratio
			new_width = (new_height * original_width) / original_height;
		}

		return new Dimension(new_width, new_height);
	}

	public File convertToFacebookImage(File sourceFile) throws IOException {

		BufferedImage img = ImageIO.read(sourceFile);

		Dimension imgSize = new Dimension(img.getWidth(), img.getHeight());
		Dimension boundary = new Dimension(960, 960);
		Dimension scaled = getScaledDimension(imgSize, boundary);

		img = getScaledInstance(img, (int) scaled.getWidth(), (int) scaled.getHeight(), true);
		
		File targetFile = new File(photoFacebookPath+File.separator+sourceFile.getName());
		ImageIO.write(img, FilenameUtils.getExtension(sourceFile.getName()), targetFile);
		
		FileTime creationTime = (FileTime) Files.readAttributes(sourceFile.toPath(), "creationTime").get("creationTime");
		Files.setAttribute(targetFile.toPath(), "creationTime", creationTime);
		
		return targetFile;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String dashboard(Model model, HttpServletRequest request) {
		
		if(!isFacebookConnected()){
			return "redirect:/connect/facebook";
		}

		return THEME_PAGE + "/dashboard";
	}

	@RequestMapping(value = "/photo/facebook/{fileName:.+}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable("fileName") String fileName) throws IOException {
		@SuppressWarnings("resource")
		RandomAccessFile f = new RandomAccessFile(photoFacebookPath + File.separator + fileName, "r");
		byte[] b = new byte[(int) f.length()];
		f.readFully(b);
		final HttpHeaders headers = new HttpHeaders();

		String ext = FilenameUtils.getExtension(fileName);

		headers.setContentType(MediaType.valueOf("image/" + ext));

		return new ResponseEntity<byte[]>(b, headers, HttpStatus.CREATED);

	}
	
	private ImageValidator imageValidator = new ImageValidator();

	@RequestMapping(value = "/photo/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> list(Model model, HttpServletRequest request) {

		List<ImageFile> imageFile = new ArrayList<ImageFile>();

		try {
			File sourceDir = new File(photoSourcePath);
			File[] files = sourceDir.listFiles();

			for (File sourceFile : files) {
				if(imageValidator.validate(sourceFile.getName())){
					if(!isPosted(sourceFile.getName())){
						
						// Convert to facebook image
						File targetFile = convertToFacebookImage(sourceFile);
	
						BasicFileAttributes attr = Files.readAttributes(targetFile.toPath(), BasicFileAttributes.class);
						imageFile.add(new ImageFile(targetFile.getName(), "/photo/facebook/" + targetFile.getName(), new Date(attr.creationTime().toMillis())));
					}
				}
			}

			Collections.sort(imageFile);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<ImageFile>>(imageFile, HttpStatus.OK);
	}
	
	public boolean isPosted(String fileName){
		String csvFile = photoFacebookPath+File.separator+"database.csv";
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(csvFile));
			
			while (scanner.hasNext()) {
	            List<String> line = CSVUtils.parseLine(scanner.nextLine());
	            if(line.get(0).equals(fileName)){
	            	return true;
	            }
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(scanner != null){
				scanner.close();
			}
		}
        
        return false;
	}
	
	public void post(String fileName){
		String csvFile = photoFacebookPath+File.separator+"database.csv";
        FileWriter writer = null;
		try {
			writer = new FileWriter(csvFile, true);
			CSVUtils.writeLine(writer, Arrays.asList(fileName));
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

        
	}
	
	@RequestMapping(value = "/photo/skip", method = RequestMethod.GET)
	public String skip(HttpServletRequest request) {
		
		String fileName = request.getParameter("fileName");
		
		post(fileName);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/photo/post", method = RequestMethod.GET)
	public String uploadImage(HttpServletRequest request) {
		
		String fileName = request.getParameter("fileName");
		
		File imageFile = new File(photoFacebookPath + File.separator + fileName);
		
		Resource resource= new FileSystemResource(imageFile.toString());

		facebook.pageOperations().postPhoto(facebookPageId, facebookAlbumId, resource);
		
		post(fileName);
		
		return "redirect:/";
	}

	@RequestMapping(value = "/photo/edited/post", method = RequestMethod.POST)
	@ResponseBody
	public String uploadImage2(@RequestParam("imgBase64") String imgBase64, @RequestParam("fileName") String fileName, HttpServletRequest request) {

		try {
			File imageFile = new File(photoEditedPath + File.separator + fileName);

			byte[] decodedBytes = DatatypeConverter.parseBase64Binary(imgBase64.replaceAll("data:image/.+;base64,", ""));
			BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(decodedBytes));
			ImageIO.write(bfi, "png", imageFile);
			bfi.flush();
						
			Resource resource= new FileSystemResource(imageFile.toString());

			facebook.pageOperations().postPhoto(facebookPageId, facebookAlbumId, resource);
			
			post(fileName);

			return "Edited photo has been post to Facebook ";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: " + e;
		}
	}
	
	@RequestMapping(value = "/flashair/status", method = RequestMethod.GET)
	@ResponseBody
	public String flashairStatus(){
		return FlashairManager.getInstance().checkConn();
	}
}
