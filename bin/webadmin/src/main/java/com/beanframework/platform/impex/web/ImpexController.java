package com.beanframework.platform.impex.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import com.beanframework.menu.domain.Menu;
import com.beanframework.platform.core.base.BaseController;
import com.beanframework.platform.impex.ImpexConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ImpexConstant.PATH_ROOT)
public class ImpexController extends BaseController {
	
	public static final String IMPEX_SPLITTER = ";";
	public static final String IMPEX_INSERT = "INSERT";
	public static final String IMPEX_UPDATE = "UPDATE";
	public static final String IMPEX_INSERT_UPDATE = "INSERT_UPDATE";
	public static final String IMPEX_REMOVE = "REMOVE";
	
	@Autowired
	public ResourceLoader resourceLoader;
	
	/**
	 * Resource[] resources =
	 * foobar.loadResources("classpath*:../../dir/*.txt");
	 * 
	 * @param pattern
	 * @return
	 * @throws IOException
	 */
	public Resource[] loadResources(String pattern) throws IOException {
		return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
	}
	
	@PostConstruct
	public void init() throws IOException {

//		getResourceExcel("import/user/data/*.xlsx");
//		getResourceCsv("classpath:import/*");
	}
	
	@RequestMapping(ImpexConstant.PATH_IMPORT_DATA)
	public void importData(){
		
	}
	
	public void getResourceCsv(String path) throws IOException {

		Resource[] resource = loadResources(path);

		for (int i = 0; i < resource.length; i++) {
			try {
				InputStream is = resource[i].getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				String line;
				List<Menu> menu = new ArrayList<Menu>();
				while ((line = br.readLine()) != null) {
					
					if(line.toUpperCase().startsWith(IMPEX_INSERT_UPDATE.toUpperCase())){
						String[] column = line.split(IMPEX_SPLITTER);
						if(column[0].split(" ")[1].toLowerCase().equals(Menu.TYPE.toLowerCase())){
							
						}
					}
					
					System.out.println(line);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void getResourceExcel(String path) throws IOException {

		Resource[] resource = loadResources("classpath:" + path);

		for (int i = 0; i < resource.length; i++) {

			try {
				InputStream is = resource[i].getInputStream();

				Workbook workbook = WorkbookFactory.create(is);

				// Get first sheet from the workbook
				Sheet sheet = workbook.getSheetAt(0);

				Iterator<Row> ite = sheet.rowIterator();

				// First row of header
				List<String> headers = new ArrayList<String>();
				if (ite.hasNext()) {
					Row row = ite.next();

					Iterator<Cell> cite = row.cellIterator();

					while (cite.hasNext()) {
						headers.add(cite.next().toString());
						System.out.println(cite.next().toString());
					}
				}
				String[] headersArray = headers.toArray(new String[headers.size()]);

				// Rest of rows
				while (ite.hasNext()) {
					Row row = ite.next();

					Iterator<Cell> cite = row.cellIterator();

					List<String> citeList = new ArrayList<String>();

					while (cite.hasNext()) {
						System.out.println(cite.next().toString());
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



}
