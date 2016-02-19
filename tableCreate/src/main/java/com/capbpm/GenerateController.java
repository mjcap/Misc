package com.capbpm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

//import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class GenerateController {
    
    @RequestMapping("/generateTable")
    public String generate(Model model) throws JAXBException{
    	
   	   Cluster cluster; 
   	   Session session;
   	    	
    	RestTemplate restTemplate = new RestTemplate();
	    String s = restTemplate.getForObject("https://bpm.capbpm.com:9443/webapi/ViewSchema.jsp?type=Customer&version=2064.374d42f7-af28-4f6d-a1c0-b34453c39b64T", String.class);

	    System.out.println("GenerateController generate() rest call returned s="+s);
	    /* s looks like:
	     * 
	     * <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tns="http://POAM" targetNamespace="http://POAM" elementFormDefault="qualified" attributeFormDefault="unqualified">

<xs:complexType name="Customer">
<xs:sequence>
<xs:element name="firstName" nillable="false" type="xs:string" minOccurs="0" maxOccurs="1"/>
<xs:element name="lastName" nillable="false" type="xs:string" minOccurs="0" maxOccurs="1"/>
<xs:element name="startDate" nillable="false" type="xs:dateTime" minOccurs="0" maxOccurs="1"/>
<xs:element name="isActive" nillable="false" type="xs:boolean" minOccurs="0" maxOccurs="1"/>
<xs:element name="age" nillable="false" type="xs:int" minOccurs="0" maxOccurs="1"/>
<xs:element name="accountBalance" nillable="false" type="xs:double" minOccurs="0" maxOccurs="1"/>
<xs:element name="alias" nillable="false" type="tns:Map" minOccurs="0" maxOccurs="1"/>
</xs:sequence>
</xs:complexType>

get line
remove < and >
split on space

	     */
	    
	    String tableName=null;
	    
	    String[] xsdArray = s.split("\\r?\\n");
	    Hashtable<String,String> keyNameValueTypePairs = new Hashtable<String,String>();
	    
	    for (String line : xsdArray){
	    	//System.out.println("GenerateController generate() line="+line);
	    	line = line.trim();
	    	line = line.replace("<", "");
	    	line = line.replace(">", "");
	    	String[] lineStrArr = line.split(" ");
            for (String element : lineStrArr){
            	//System.out.println("GenerateController generate() element="+element);
            	if (element.indexOf("/xs:complexType") != -1){
            		/*
            		 * CREATE TABLE emp (
  empID int,
  deptID int,
  first_name varchar,
  last_name varchar
);
            		 */
            		String createTable = "CREATE TABLE "+tableName + "(";
            		String type = null;
            		
            		//System.out.println("GenerateController generate() Create table "+ tableName);
            		//System.out.println("GenerateController generate() Columns:");
            		Enumeration e = keyNameValueTypePairs.keys();
            		boolean execute = true;
            		boolean hasAtLeastOneColumn = false;
            		String primaryKey = null;
            		
            	    while (e.hasMoreElements()){
            	    	String k = (String)e.nextElement();
            	    	type = null;
            	    	String xsdType = keyNameValueTypePairs.get(k);
            	    	if (xsdType.compareTo("xs:string")==0){
            	    		type = "text";
            	    		hasAtLeastOneColumn = true;
            	    	}
            	    	else if (xsdType.compareTo("xs:dateTime")==0){
            	    		type = "timestamp";
            	    		hasAtLeastOneColumn = true;
            	    	}
            	    	else if (xsdType.compareTo("xs:boolean")==0){
            	    		type = "boolean";
            	    		hasAtLeastOneColumn = true;
            	    	}
            	    	else if (xsdType.compareTo("xs:int")==0){
            	    		type = "int";
            	    		hasAtLeastOneColumn = true;
            	    	}
            	    	else if (xsdType.compareTo("xs:double")==0){
            	    		type = "double";
            	    		hasAtLeastOneColumn = true;
            	    	}
            	    	
            	    	if (type != null){
            	    	  System.out.println("GenerateController generate() colName="+k+" colType="+type);
            	    	  createTable = createTable + k + " " + type;
            	    	  
            	    	  if (primaryKey == null){
            	    		  primaryKey = k;
            	    	  }
              	    	  if (e.hasMoreElements()){
            	    		createTable = createTable +", ";
            	    	  }           	    	  
            	    	  
            	    	}
            	    	


            	    }
            	    createTable = createTable +", PRIMARY KEY (" + primaryKey +"));";
            	    if (execute && hasAtLeastOneColumn){
            	       System.out.println("GenerateController generate() NEW CREATE TABLE TO EXECUTE="+createTable);
            	       AccessCassandra ac = new AccessCassandra();
            	       ac.createTable(createTable);
            	    }
            	    keyNameValueTypePairs = new Hashtable<String,String>();
            	}
            	else if (element.indexOf("xs:complexType") != -1){
            		tableName=findValue(lineStrArr,"name=");
            		//System.out.println("GenerateController generate() tableName="+tableName);
            	}
            	else if (element.indexOf("xs:element") != -1){
            		String keyName = findValue(lineStrArr,"name=");
            		String valueType = findValue(lineStrArr,"type=");
            		//System.out.println("GenerateController generate() tableName="+tableName+" keyName="+keyName+" valueType="+valueType);
            		keyNameValueTypePairs.put(keyName, valueType);
            	}
            }
	    }
	    
	    model.addAttribute("userData",s);

        
    	return "generate";
    }
    
    //xs:complexType name="Customer"
    public String findValue(String[] arr, String key){
    	//System.out.println("findValue arr="+arr+" key="+key);
    	String value=null;
    	for (int idx = 1; idx < arr.length; idx++){
    		if (arr[idx].indexOf(key) != -1){
    			String[] keyValueArr=arr[idx].split("=");
    			value = keyValueArr[1];
    			value = value.replace("\"", "");
    			break;
    		}
    	}
    	return value;
    	
    }

}
