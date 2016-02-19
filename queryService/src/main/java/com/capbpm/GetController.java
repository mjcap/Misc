package com.capbpm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GetController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static int upper = 100;
    private static int lower = 1;
        
    /*@RequestMapping("/create")
    public String greeting(@RequestParam(value="first") String firstName, @RequestParam(value="last") String lastName, @RequestParam(value="age") int age) {
        
        ObjectMapper mapper = new ObjectMapper();
        Person p = new Person(firstName.trim(), lastName.trim(),age);
        String jsonInString="";
        
		try {
			jsonInString = mapper.writeValueAsString(p);
			System.out.println("PersonController jsonInString="+jsonInString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println("PersonController error serializing Person");
			e.printStackTrace();
		}
		System.out.println("PersonController returning jsonInString="+jsonInString);
        return jsonInString;
        
    }*/
    
    @RequestMapping("/get")
    public String getAllImpl(@RequestParam(value="class") String className, 
    		                            @RequestParam(value="query") String subqueryString) {
    	
    	System.out.println("GetController getAlImpl() className="+className);
    	System.out.println("GetController getAlImpl() subqueryString="+subqueryString);
    	
    	AccessCassandra ac = new AccessCassandra();
    	ArrayList<String> alp = (ArrayList<String>) ac.getAllObjectStringsForClass(className);
    	byte[] b;
    	String output = "";
    	JSONArray ja = new JSONArray();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	for (String p : alp){
    	    /*try {
				JSONObject jo = new JSONObject(p);
				Person p1 = mapper.readValue(p, Person.class);
				System.out.println("GetController p1="+p1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
    	    
    		try {
				JSONObject jo = new JSONObject(p);
				ja.put(jo);
				System.out.println("GetController.getImpl() JSONObject="+jo); 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
    	}

    	return ja.toString();
    }

}


