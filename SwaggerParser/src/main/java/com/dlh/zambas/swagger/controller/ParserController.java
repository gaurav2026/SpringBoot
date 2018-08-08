package com.dlh.zambas.swagger.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dlh.zambas.swagger.constants.ParserConstants;
import com.dlh.zambas.swagger.model.ParserModel;
import com.dlh.zambas.swagger.service.IParserService;

@CrossOrigin
@RestController
public class ParserController {
	private static final Logger logger = LoggerFactory.getLogger(ParserController.class);

	private static boolean isFileCreated = true;
	/**
	 * create the file in current directory
	 */
	private static File file = new File(System.getProperty(ParserConstants.current_directory.value()) + File.separator
			+ ParserConstants.dummy_file_name.value());

	static {
		try {
			if (file.createNewFile()) {
				logger.info("File is created!");
			} else {
				logger.debug("File already exists.");
			}
		} catch (IOException e) {
			isFileCreated = false;
			logger.error("Exception occured while creating file", e);
		}
	}

	@Autowired
	private IParserService service;
	
	@RequestMapping(method = RequestMethod.POST, value = "/swagger")
	public ResponseEntity<byte[]> getFile(@RequestBody(required = true) ParserModel model) throws Exception {
		if (isFileCreated) {
			
			byte[] output = service.convertFile(model.getFilePath());

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(MediaType.APPLICATION_XML);
			responseHeaders.setContentLength(output.length);
			responseHeaders.set(ParserConstants.Content_Disposition.value(), ParserConstants.Attachment.value()
					+ ParserConstants.file.value() + ParserConstants.File_Name.value());
			return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
		} else {
			logger.error("File couldn't be created");
			throw new Exception("Exception in reading file URL");
		}
	}

	/**
	 * fileURL is passed to this method
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/url")
	public ResponseEntity<byte[]> getFileURL(@RequestBody(required = true) ParserModel model) throws Exception {
		if (isFileCreated) {
			logger.info(model.getFileURL() + " **** " + file.getAbsolutePath());
			/**
			 * convert URL to file Required because swagger API accepts only
			 * file
			 */
			FileUtils.copyURLToFile(model.getFileURL(), file);
			byte[] output = service.convertFile(file.getAbsolutePath());

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(MediaType.APPLICATION_XML);
			responseHeaders.setContentLength(output.length);
			responseHeaders.set(ParserConstants.Content_Disposition.value(), ParserConstants.Attachment.value()
					+ ParserConstants.file.value() + ParserConstants.File_Name.value());
			return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
		} else {
			logger.error("File couldn't be created");
			throw new Exception("Exception in reading file URL");
		}
	}
}
