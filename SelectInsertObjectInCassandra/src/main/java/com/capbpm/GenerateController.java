package com.capbpm;

import java.util.ArrayList;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class GenerateController {
    
    @RequestMapping("/generate")
    public String generate(Model model){
    	
   	   Cluster cluster; 
   	   Session session;
   	 
 	   //cluster = new Cluster.Builder().addContactPoints("localhost").withPort(9042).build(); 
 	   //cluster = new Cluster.Builder().addContactPoints("158.85.209.196").withPort(9042).build();	 
 	   //session = cluster.connect("showcase"); 
 	   
 	   //CassandraOperations cassandraOps = new CassandraTemplate(session); 
    	
    	RestTemplate restTemplate = new RestTemplate();
	    //Person p = restTemplate.getForObject("http://localhost:8080/restfulService/greeting", Person.class);
	    byte[] b = restTemplate.getForObject("http://localhost:8080/restfulServiceGenerateObjects/greeting", byte[].class);
	    //cassandraOps.insert(p);
	    System.out.println("GenerateController.generate() byte[] b="+b);
	    System.out.println("GenerateController.generate() byte[] b as String="+new String(b));
	    AccessCassandra ac = new AccessCassandra();
	    ac.insertObjectBlobs(b);
	    //ac.insertPerson(p);
	    
	    //ac.insert(b);
	    
    	//model.addAttribute("userData",p.toString());
	    model.addAttribute("userData",b);
    	model.addAttribute("name", "");
    	return "generate";
    }
    
    

}
