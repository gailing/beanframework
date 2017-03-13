package com.beanframework.photobooth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

public class CommonController {

	public static final String THEME_PAGE = "/thymeleaf/themes/adminlte";
	
	@Autowired
    protected Facebook facebook;
	
	@Autowired
	protected ConnectionRepository connectionRepository;
	
	@Value("${photo.sourcePath}")
	protected String photoSourcePath;
	
	@Value("${photo.facebookPath}")
	protected String photoFacebookPath;
	
	@Value("${photo.editedPath}")
	protected String photoEditedPath;
	
	@Value("${facebook.pageId}")
	protected String facebookPageId;
	
	@Value("${facebook.albumId}")
	protected String facebookAlbumId;
	
	public boolean isFacebookConnected(){
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return false;
		}
		else{
			return true;
		}
	}
}
