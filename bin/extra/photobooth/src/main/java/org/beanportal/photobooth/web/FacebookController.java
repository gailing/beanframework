//package com.beanframework.photobooth.web;
//
//import java.io.IOException;
//import java.io.RandomAccessFile;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.social.connect.ConnectionRepository;
//import org.springframework.social.facebook.api.Facebook;
//import org.springframework.social.facebook.api.FacebookLink;
//import org.springframework.social.facebook.api.PagePostData;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//@RequestMapping("/facebook")
//public class FacebookController extends CommonController{
//
//	@Autowired
//    private Facebook facebook;
//	
//	@Autowired
//    private ConnectionRepository connectionRepository;
//	
//	@Value("${photo.sourcePath}")
//	private String photoSourcePath;
//	
//	@Value("${facebook.pageId}")
//	private String pageId;
//	
//	@Value("${facebook.albumId}")
//	private String albumId;
//
////    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
////        this.facebook = facebook;
////        this.connectionRepository = connectionRepository;
////    }
//
////    @GetMapping
////    public String helloFacebook(Model model) {
////        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
////            return "redirect:/connect/facebook";
////        }
////
////        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
////        PagedList<Post> feed = facebook.feedOperations().getFeed();
////        model.addAttribute("feed", feed);
////        return "hello";
////    }
//	
//	@RequestMapping(value = "/send-status", method = RequestMethod.GET)
//	public String postStatus() throws IOException {
//		
//		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
//			return "redirect:/connect/facebook";
//		}
//	
////		postMessageOnPage("182792198566038", "I'm trying out Spring Social!");
//		
//		postPictureOnPage("182792198566038", "638607009651219");
//		
//		return "Done";
//
//	}
//	
//	public void postPictureOnPage(String pageId, String albumId) {
//
//		Resource resource= new FileSystemResource("D:\\Development\\beanportal\\var\\data\\sample.png");
//
//		facebook.pageOperations().postPhoto(pageId, albumId, resource, "#springsocial");
//	}
//	
//	public void postMessageOnPage(String pageId, String message) {
//
//
//	    PagePostData post = new PagePostData(pageId);
//		post.message(message);
//		facebook.pageOperations().post(post);
//	}
//	
//	@RequestMapping(value = "/send-link", method = RequestMethod.GET)
//	public String postLink() throws IOException {
//		
//		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
//			return "redirect:/connect/facebook";
//		}
//
//		FacebookLink link = new FacebookLink("http://www.mirai.com.my", "Mirai Marketing", "Posting Mirai Marketing", "Mirai Marketing Website");
//		facebook.feedOperations().postLink("i'm posting", link);
//		
//		return "Done";
//
//	}
//	
//	@RequestMapping(value = "/send-image", method = RequestMethod.GET)
//	public String postImage() throws IOException {
//		
//		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
//			return "redirect:/connect/facebook";
//		}
//
//		Resource resource= new FileSystemResource("D:\\Development\\beanportal\\var\\data\\sample.png");//FileSystemResource(“MyResource”)
//		
//		try {
//			String albumId = "638607009651219";
//			facebook.mediaOperations().postPhoto(albumId, resource);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return e.getMessage();
//		}
//
//		return "Done";
//
//	}
//
//}