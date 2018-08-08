package com.dlh.zambas.swagger.model;

/**
 * pojo class to map request
 */
import java.net.URL;

public class ParserModel {

	private String filePath = null;
	private URL fileURL = null;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public URL getFileURL() {
		return fileURL;
	}

	public void setFileURL(URL fileURL) {
		this.fileURL = fileURL;
	}

	public ParserModel(String filePath, URL fileURL) {
		this.filePath = filePath;
		this.fileURL = fileURL;
	}

	public ParserModel() {
	}

}
