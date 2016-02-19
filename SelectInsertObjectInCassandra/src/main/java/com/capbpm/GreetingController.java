package com.capbpm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capbpm.dto.ObjectBlobs;
import com.datastax.driver.core.utils.Bytes;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
    	
    	AccessCassandra ac = new AccessCassandra();
    	ArrayList<ObjectBlobs> alp = (ArrayList<ObjectBlobs>) ac.getListObjectBlob();
    	byte[] b;
    	String output = "";
    	
    	for (ObjectBlobs p : alp){
    		System.out.println("GreetingController.greeting(); ObjectBlobs p.id="+p.getId()+" p.data="+p.getData());
    		output = output + p.toString() + "<br />";
    		if (p.getId().equals("1")){
    			System.out.println("GreetingController.greeting(); skipping id=1");
    		}
    		else{
	    		//b = new byte[p.getData().remaining()];
	    		b = Bytes.getArray(p.getData());
	    		p.getData().get(b);
	    		
	    		ObjectMapper mapper = new ObjectMapper();
	    		try {
					Object o = mapper.readValue(new String(b),Object.class);
					System.out.println("GreetingController.greeting() o="+o);
					if (o instanceof String){
						System.out.println("GreetingController.greeting() o is a String");
					}
					if (o instanceof byte[]){
						System.out.println("GreetingController.greeting() o is a byte[]");
					}
					if (o instanceof LinkedHashMap){
						System.out.println("GreetingController.greeting() o is a LinkedHashMap");
				        LinkedHashMap l = (LinkedHashMap)o;
				        Set s = l.keySet();
				        Iterator i = s.iterator();
				        while (i.hasNext()){
				        	String key=i.next().toString();
				        	//jsonString = jsonString + l.get(i.next().toString());
				        	System.out.println("key="+key+" value="+l.get(key));
				        }
					}
					else{
						System.out.println("GreetingController.greeting() o is NOT A String");
					}
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					System.out.println("GreetingController.greeting()");
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					System.out.println("GreetingController.greeting()");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("GreetingController.greeting()");
					e.printStackTrace();
				}
    		}
    	}
        //model.addAttribute("name", name);
    	model.addAttribute("userData","");
    	model.addAttribute("name", output);
        return "greeting";
    }
   
}
