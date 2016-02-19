package com.capbpm;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capbpm.dto.ObjectBlobs;
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
	    		b = new byte[p.getData().remaining()];
	    		p.getData().get(b);
	    		ObjectMapper mapper = new ObjectMapper();
	    		try {
					Object o = mapper.readValue(new String(b),Object.class);
					System.out.println("GreetingController.greeting() o="+o);
					if (o instanceof String){
						System.out.println("GreetingController.greeting() o is a String");
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
