package com.dlh.zambas.swagger.component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dlh.zambas.swagger.constants.ParserConstants;

import io.swagger.models.HttpMethod;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.BooleanProperty;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.LongProperty;
import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;
import net.java.dev.wadl._2009._02.Application;
import net.java.dev.wadl._2009._02.Doc;
import net.java.dev.wadl._2009._02.Grammars;
import net.java.dev.wadl._2009._02.Method;
import net.java.dev.wadl._2009._02.ObjectFactory;
import net.java.dev.wadl._2009._02.Param;
import net.java.dev.wadl._2009._02.ParamStyle;
import net.java.dev.wadl._2009._02.Representation;
import net.java.dev.wadl._2009._02.Request;
import net.java.dev.wadl._2009._02.Resource;
import net.java.dev.wadl._2009._02.Resources;
import net.java.dev.wadl._2009._02.Response;

/**
* class parses generated swagger and transforms it to WADL
*/

@Component
public class ParseFile {

	private static final Logger logger = LoggerFactory.getLogger(ParseFile.class);
	
	private ObjectFactory objectFactory;
	private DocumentBuilderFactory documentBuilderFactory;
	private DocumentBuilder documentBuilder;
	private Document document;
	private Element schema;
	private Application application;

	/**
	 * initialize different parameters
	 * 
	 * @throws Exception
	 */
	public ParseFile() throws Exception {
		objectFactory = new ObjectFactory();
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		document = documentBuilder.newDocument();

		schema = document.createElementNS(ParserConstants.XML_SCHEMA.value(), ParserConstants.XML_SCHEMA_TAG.value());
		schema.setAttribute(ParserConstants.TNS_TAG.value(), ParserConstants.TNS.value());
		schema.setAttribute(ParserConstants.targetNamespace.toString(), ParserConstants.TNS.value());

		application = objectFactory.createApplication();
		Grammars grammars = application.getGrammars();
		if (grammars == null) {
			grammars = objectFactory.createGrammars();
			application.setGrammars(grammars);
		}

		application.getGrammars().getAny().add(schema);
	}

	/**
	 * fetch title from swagger
	 * 
	 * @param title
	 * @param text
	 * @return
	 */
	private Doc addDoc(String title, String text) {
		Doc doc = objectFactory.createDoc();
		doc.setTitle(title);
		doc.setLang(ParserConstants.english.toString());
		doc.getContent().add(text);
		return doc;
	}

	/**
	 * check for minOccurs and maxOccurs check for correct data type
	 * 
	 * @param name
	 * @param swagger_property
	 * @return
	 * @throws ParserConfigurationException
	 */
	private Element addProperty(String name, Property swagger_property) throws ParserConfigurationException {
		Element element = document.createElement(ParserConstants.element_prefix.value());
		element.setAttribute(ParserConstants.name.toString(), name);

		Element simple = element;

		if (swagger_property.getRequired()) {
			element.setAttribute(ParserConstants.minOccurs.toString(), ParserConstants.minOccurs.value());
		}

		if (swagger_property instanceof IntegerProperty) {
			IntegerProperty integerProperty = (IntegerProperty) swagger_property;
			element.setAttribute(ParserConstants.type.toString(), ParserConstants.integer_prefix.value());
			if (integerProperty.getEnum() != null) {
				simple = addEnum(integerProperty.getEnum());
				element.appendChild(simple);
			}
		} else if (swagger_property instanceof LongProperty) {
			element.setAttribute(ParserConstants.type.toString(), ParserConstants.long_prefix.value());
		} else if (swagger_property instanceof StringProperty) {
			StringProperty stringProperty = (StringProperty) swagger_property;
			element.setAttribute(ParserConstants.type.toString(), ParserConstants.string_prefix.value());
			if (stringProperty.getEnum() != null) {
				simple = addEnum(stringProperty.getEnum());
				element.appendChild(simple);
			}
		} else if (swagger_property instanceof DateTimeProperty) {
			DateTimeProperty dateTimeProperty = (DateTimeProperty) swagger_property;
			element.setAttribute(ParserConstants.type.toString(), ParserConstants.date_time_prefix.value());
			if (dateTimeProperty.getEnum() != null) {
				simple = addEnum(dateTimeProperty.getEnum());
				element.appendChild(simple);
			}
		} else if (swagger_property instanceof BooleanProperty) {
			BooleanProperty booleanProperty = (BooleanProperty) swagger_property;
			element.setAttribute(ParserConstants.type.toString(), ParserConstants.boolean_prefix.value());
		} else if (swagger_property instanceof RefProperty) {
			RefProperty refProperty = (RefProperty) swagger_property;
			element.setAttribute(ParserConstants.type.toString(), refProperty.getSimpleRef());
		} else if (swagger_property instanceof ObjectProperty) {
			ObjectProperty objectProperty = (ObjectProperty) swagger_property;
			element.appendChild(js2wadl(name, objectProperty.getProperties()));
		} else if (swagger_property instanceof ArrayProperty) {
			ArrayProperty arrayProperty = (ArrayProperty) swagger_property;
			element.appendChild(addProperty(name, arrayProperty.getItems()));
		} else {
			element.setAttribute(ParserConstants.type.toString(), swagger_property.getFormat());
		}

		if (swagger_property.getDescription() != null) {
			Element ann = document.createElement(ParserConstants.annotation_prefix.value());
			Element desc = document.createElement(ParserConstants.documentation_prefix.value());
			desc.setTextContent(swagger_property.getDescription());
			ann.appendChild(desc);
			simple.appendChild(ann);
		}

		return element;
	}

	/**
	 * check for enum type
	 * 
	 * @param list
	 * @return
	 */
	public <T> Element addEnum(List<T> list) {
		Element simple = document.createElement(ParserConstants.simpleType_prefix.value());
		Element restriction = document.createElement(ParserConstants.restriction_prefix.value());
		for (T option : list) {
			Element en = document.createElement(ParserConstants.enumeration_prefix.value());
			en.setAttribute(ParserConstants.values.toString(), option.toString());
			restriction.appendChild(en);
		}
		simple.appendChild(restriction);
		return simple;
	}

	/**
	 * method converts different swagger properties into WADL
	 * 
	 * @param name
	 * @param properties
	 * @return
	 * @throws ParserConfigurationException
	 */
	private Element js2wadl(String name, final Map<String, Property> properties) throws ParserConfigurationException {

		Element type;
		Element base;

		if (properties.size() > 1) {
			type = document.createElement(ParserConstants.complexType_prefix.value());
			type.setAttribute(ParserConstants.name.toString(), name);
			Element seq = document.createElement(ParserConstants.sequence_prefix.value());
			type.appendChild(seq);
			base = seq;
		} else {
			type = document.createElement(ParserConstants.simpleType_prefix.value());
			type.setAttribute(ParserConstants.name.toString(), name);
			base = type;
		}
		for (Entry<String, Property> prop : properties.entrySet()) {
			base.appendChild(addProperty(prop.getKey(), prop.getValue()));
		}
		return type;
	}

	/**
	 * method used JAXB to convert swagger to WADL
	 * 
	 * @param swagger
	 * @return
	 * @throws Exception 
	 */
	public File transform(final Swagger swagger) throws Exception {
		application.getDoc().add(addDoc(swagger.getInfo().getTitle(), swagger.getInfo().getDescription()));
		Resources resources = objectFactory.createResources();
		application.getResources().add(resources);

		/**
		 * look for end point URL This is required for AI
		 */
		String protocol = "";

		String baseURI = null;
		if (swagger.getHost() != null) {
			baseURI = swagger.getHost();
		}
		if (null != baseURI && (!baseURI.contains(ParserConstants.http.toString()) || !baseURI.contains(ParserConstants.https.toString()))) {
			if (null != swagger.getSchemes() && swagger.getSchemes().size() > 0) {
				protocol = swagger.getSchemes().get(0).toString() + "://";
				protocol = protocol.toLowerCase();
			} else {
				protocol = ParserConstants.http.value();
			}
		}
		String basePath = "";
		if (null != swagger.getBasePath() && !swagger.getBasePath().isEmpty()) {
			basePath = swagger.getBasePath();
		}

		if (null != baseURI) {
			resources.setBase(protocol + baseURI + basePath);
		}
		
		if(null!=resources.getBase()){
			logger.info("end point : " + resources.getBase());
		}else{
			throw new Exception("End point URL not present");
		}
		/**
		 * enumerates different parameters type such as headers,query
		 */
		for (Map.Entry<String, Path> path : swagger.getPaths().entrySet()) {
			Resource resource = objectFactory.createResource();
			resource.setId(path.getKey().replace('/', '_').replace('{', '_').replace('}', '_'));
			resource.setPath(path.getKey());

			if (path.getValue().getParameters() != null) {
				for (Parameter param : path.getValue().getParameters()) {
					Param params = objectFactory.createParam();
					params.setName(param.getName());
					params.getDoc().add(addDoc(param.getName(), param.getDescription()));
					params.setStyle(ParamStyle.fromValue(param.getIn()));
					params.setRequired(param.getRequired());
					params.setType(new QName(""));
					resource.getParam().add(params);
				}
			}

			/**
			 * check for operation name, resource base etc.
			 */
			for (Entry<HttpMethod, Operation> op : path.getValue().getOperationMap().entrySet()) {
				Method method = objectFactory.createMethod();
				method.getDoc().add(addDoc(op.getValue().getOperationId(), op.getValue().getSummary()));
				method.setId(op.getValue().getOperationId());
				method.setName(op.getKey().name().toUpperCase());

				Request req = objectFactory.createRequest();
				/**
				 * check tyoe of request, namespace etc.
				 */
				if (op.getValue().getConsumes() != null) {
					for (String mime : op.getValue().getConsumes()) {
						Representation representation = objectFactory.createRepresentation();
						representation.setElement(new QName(ParserConstants.tns_prefix.value() + path.getKey()));
						representation.setMediaType(mime);
						req.getRepresentation().add(representation);
					}
				}
				if (op.getValue().getParameters() != null) {
					for (Parameter param : op.getValue().getParameters()) {
						Param parameter = null;
						parameter = objectFactory.createParam();
						parameter.setName(param.getName());
						parameter.getDoc().add(addDoc(param.getName(), param.getDescription()));
						/**
						 * check input parameter type like header, query etc.
						 */
						if (null != param.getIn() && (param.getIn().equalsIgnoreCase(ParserConstants.query.toString())
								|| param.getIn().equalsIgnoreCase(ParserConstants.header.toString())
								|| param.getIn().equalsIgnoreCase(ParserConstants.template.toString())
								|| param.getIn().equalsIgnoreCase(ParserConstants.matrix.toString())
								|| param.getIn().equalsIgnoreCase(ParserConstants.plain.toString()))) {
							parameter.setStyle(ParamStyle.fromValue(param.getIn()));
						}
						parameter.setRequired(param.getRequired());
						parameter.setType(new QName(""));
						req.getParam().add(parameter);

					}
				}
				//set the request
				method.setRequest(req);

				//set the response
				Response res = objectFactory.createResponse();
				for (String mime : op.getValue().getProduces()) {
					Representation representation = objectFactory.createRepresentation();
					representation.setElement(new QName(ParserConstants.tns_prefix.value() + path.getKey()));
					representation.setMediaType(mime);
					res.getRepresentation().add(representation);
				}
				for (Entry<String, io.swagger.models.Response> response : op.getValue().getResponses().entrySet()) {
					res.getDoc().add(addDoc(response.getKey(), response.getValue().getDescription()));
				}
				method.getResponse().add(res);
				resource.getMethodOrResource().add(method);
			}
			resources.getResource().add(resource);
		}

		for (Entry<String, Model> def : swagger.getDefinitions().entrySet()) {
			if (null != def.getValue().getProperties())
				schema.appendChild(js2wadl(def.getKey(), def.getValue().getProperties()));
		}

		/**
		 * by default store the converted swagger into file
		 */
		JAXBContext jaxbContext = JAXBContext.newInstance(ParserConstants.jaxb_tns.value());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		File file = new File(ParserConstants.File_Name.value());
		marshaller.marshal(application, file);
		return file;
	}
	

}
