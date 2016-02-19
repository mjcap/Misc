package com.capbpm;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class PersonController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static int upper = 100;
    private static int lower = 1;
    
    /*@RequestMapping("/greeting")
    public Person greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String generatedName = sb.toString();

        int generatedAge = (int) (Math.random() * (upper - lower)) + lower;
        
        return new Person(new Long(System.currentTimeMillis()).toString(),generatedName,generatedAge);
    }*/
    
    @RequestMapping("/greeting")
    public byte[] greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String generatedName = sb.toString();

        int generatedAge = (int) (Math.random() * (upper - lower)) + lower;
        
        ObjectMapper mapper = new ObjectMapper();
        Person p = new Person(new Long(System.currentTimeMillis()).toString(),generatedName,generatedAge);
        String jsonInString="";
        byte[] b = null;
        
		try {
			jsonInString = mapper.writeValueAsString(p);
			b = jsonInString.getBytes();
			System.out.println("PersonController jsonInString="+jsonInString+" b="+b);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println("PersonController error serializing Person");
			e.printStackTrace();
		}
		System.out.println("PersonController returning b="+b);
        return jsonInString.getBytes();
        
    }    

}


