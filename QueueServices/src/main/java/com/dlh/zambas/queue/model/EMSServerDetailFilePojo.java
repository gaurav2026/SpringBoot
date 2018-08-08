package com.dlh.zambas.queue.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * external file location where server propeties are put
 * @author singhg
 *
 */
@Component
@ConfigurationProperties("ems")
public class EMSServerDetailFilePojo {
	
	@Value("${ems.server}")
	private String emsServerPropertyFileLocation = null;

	public String getEmsServerPropertyFileLocation() {
		return emsServerPropertyFileLocation;
	}

	public void setEmsServerPropertyFileLocation(String emsServerPropertyFileLocation) {
		this.emsServerPropertyFileLocation = emsServerPropertyFileLocation;
	}
	
	
}
