package com.beanframework.photobooth.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ImageFile implements Comparable<ImageFile> {

	private String name;

	private String path;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a z", timezone = "Asia/Kuala_Lumpur")
	private Date creationTime;

	public ImageFile(String name, String path, Date creationTime) {
		super();
		this.name = name;
		this.path = path;
		this.creationTime = creationTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public int compareTo(ImageFile o) {
		return getCreationTime().compareTo(o.getCreationTime());
	}

}
