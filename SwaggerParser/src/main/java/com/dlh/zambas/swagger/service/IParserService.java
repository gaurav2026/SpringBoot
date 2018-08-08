package com.dlh.zambas.swagger.service;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;

/**
 * converts json/yaml to swagger and then to WADL
 * @author singhg
 *
 */
@Service
public interface IParserService {
	public byte[] convertFile(String filePath) throws IOException, JAXBException, ParserConfigurationException, Exception;
}
