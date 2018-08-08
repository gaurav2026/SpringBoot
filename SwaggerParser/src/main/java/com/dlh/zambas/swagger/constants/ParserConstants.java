package com.dlh.zambas.swagger.constants;

public enum ParserConstants {
	
	targetNamespace,
	name,
	type,
	values,
	body,
	query,
	header,
	matrix,
	plain,
	template,
	http("http://"),
	https,
	Attachment("attachment;"),
	file("filename="),
	Content_Disposition("Content-disposition"),
	current_directory("user.dir"),
	dummy_file_name("swagger.txt"),
	tns_prefix("tns:"),
	jaxb_tns("net.java.dev.wadl._2009._02"),
	File_Name("converted.wadl"),
	sequence_prefix("xs:sequence"),
	enumeration_prefix("xs:enumeration"),
	restriction_prefix("xs:restriction"),
	simpleType_prefix("xs:simpleType"),
	complexType_prefix("xs:complexType"),
	annotation_prefix("xs:annotation"),
	documentation_prefix("xs:documentation"),
	minOccurs("1"),
	english("en"),
	date_time_prefix("xs:dateTime"),
	boolean_prefix("xs:boolean"),
	element_prefix("xs:element"),
	integer_prefix("xs:integer"),
	long_prefix("xs:long"),
	string_prefix("xs:string"),
	XML_SCHEMA_TAG("xs:schema"),
	TNS_TAG("xmlns:tns"),
	TNS("http://amadeus.com"),
	XML_SCHEMA("http://www.w3.org/2001/XMLSchema");
	private String value = null;
	
	private ParserConstants(String value) {
		this.value = value;
	}
	
	private ParserConstants(){}
	
	public String value(){
		return value;
	}
	
}
