package com.dlh.zambas.swagger.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlh.zambas.swagger.component.ParseFile;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

/**
 * converts json/yaml to swagger and then to WADL
 */

@Service
public class ParserService implements IParserService {

	@Autowired
	private ParseFile parseFile = null;

	@Override
	public byte[] convertFile(String filePath) throws JAXBException, ParserConfigurationException, Exception {
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		/**
		 * convert json/yaml to swagger
		 */
		Swagger swagger = new SwaggerParser().parse(new String(encoded));
		/**
		 * tranform swagger to WADL
		 */
		File convertedFile = parseFile.transform(swagger);
		return getFileContentAsByteArray(convertedFile.getAbsolutePath());
	}

	private byte[] getFileContentAsByteArray(String filePath) throws IOException {
		byte[] bytesArray = null;

		try (FileInputStream fileInputStream = new FileInputStream(new File(filePath))) {
			bytesArray = new byte[(int) fileInputStream.available()];
			// read file into bytes[]
			fileInputStream.read(bytesArray);

		} catch (IOException e) {
			throw new IOException("File not found ", e);
		}

		return bytesArray;
	}
}
