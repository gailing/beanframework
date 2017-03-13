package com.beanframework.photobooth.utils;

import ro.szzsa.flashair.application.Launcher;
import ro.szzsa.flashair.configuration.Configuration;
import ro.szzsa.flashair.connector.HttpConnector;

public class FlashairManager {

	private static FlashairManager instance;

	private FlashairManager() {
	}

	public static synchronized FlashairManager getInstance() {
		if (instance == null) {
			instance = new FlashairManager();
		}

		return instance;
	}
	
	public void startDownload(){
		Thread thread = new Thread(new Launcher());
		thread.start();
	}

	public String checkConn() {
		try {
			HttpConnector connector;
			Configuration config;
			config = Configuration.getInstance();
			connector = new HttpConnector();
			connector.setBufferSize(config.getDownloaderBufferSize());
			connector.setConnectionTimeout(config.getDownloaderConnectionTimeout());
			connector.setReadTimeout(config.getDownloaderReadTimeout());
			connector.setConnectionRetryDelay(config.getDownloaderConnectionRetryDelay());
			connector.setMaxConnectionRetryCount(config.getDownloaderMaxConnectionRetryCount());
			String LIST_URL_PATH = "/command.cgi?op=100&DIR=";
			String listUrl = config.getFlashairUrlBase() + LIST_URL_PATH + config.getFlashairPictureDirectory();
			connector.doRequest(listUrl);
		} catch (Exception e) {
			return "Flashair Not Connected.";
		}
		
		return "Flashair Ready.";
	}

}
