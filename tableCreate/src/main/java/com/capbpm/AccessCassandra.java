package com.capbpm;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.cassandra.core.RowMapper;
import org.springframework.data.cassandra.core.CassandraOperations; 
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.web.client.RestTemplate;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.exceptions.DriverException;
import com.datastax.driver.core.querybuilder.QueryBuilder; 
import com.datastax.driver.core.querybuilder.Select; 

public class AccessCassandra {
	
	public boolean createTable(String createTable){
		boolean result = false;
		Cluster cluster; 
		Session session;
		
		try {
			cluster = Cluster.builder().addContactPoints(InetAddress.getByName("odm.capbpm.com")).build();
			cluster.init();
			session = cluster.connect("showcase"); 
			
			ResultSet rs = session.execute(createTable);
			
			if (rs == null){
				System.out.println(createTable+" FAILED");
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("AccessCassandra ERROR");
			e.printStackTrace();
		}		
		
		
		return result;
	}
	public List<String> getListObjectStrings(){
		
		Cluster cluster; 
		Session session;
		ArrayList<String> arrayListObjectStrings = null;
		

		try {
			cluster = Cluster.builder().addContactPoints(InetAddress.getByName("odm.capbpm.com")).build();
			cluster.init();
			session = cluster.connect("showcase"); 
			CassandraOperations cassandraOps = new CassandraTemplate(session); 
			
			Select s = QueryBuilder.select().all().from("objectstrings");  
			
			   arrayListObjectStrings = (ArrayList)cassandraOps.query(s, new RowMapper<String>() {

					public String mapRow(Row row, int rowNum) throws DriverException {
						return row.getString("data");
					}

				});	   
			   for (String p : arrayListObjectStrings){
				   System.out.println("AccessCassandra.getListObjectStrings() String="+p);
			   }			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("AccessCassandra ERROR");
			e.printStackTrace();
		}

		   
		   return arrayListObjectStrings;
	}

	public List<String> getAllObjectStringsForClass(String className){
		
		Cluster cluster; 
		Session session;
		ArrayList<String> arrayListObjectStrings = new ArrayList<String>();

		try {
			System.out.println("AccessCassandra.getAllObjectStringsForClass className="+className);
			
			cluster = Cluster.builder().addContactPoints(InetAddress.getByName("odm.capbpm.com")).build();
			cluster.init();
			session = cluster.connect("showcase"); 
			CassandraOperations cassandraOps = new CassandraTemplate(session); 
						   
			String query = "select data from objectstrings where classname=?";  
			// Cache the prepared statement for later re-use
			PreparedStatement pStatement = session.prepare(query);   

			    BoundStatement bStatement = new BoundStatement(pStatement);
			    bStatement.bind(className);  
			    ResultSet resultSet = session.execute(bStatement);
			
			    System.out.println("AccessCassandra.getAllObjectStringsForClass resultSet="+resultSet);

			    String result;
			    Row row = resultSet.one();
			    
			    while (row != null){ 
			    	result = row.getString("data");
			    	System.out.println("AccessCassandra.getAllObjectStringsForClass result="+result);
			    	arrayListObjectStrings.add(result);
			        row = resultSet.one();
			    }
			    		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("AccessCassandra getAllObjectStringsForClass className="+className+" ERROR");
			e.printStackTrace();
		}

		   
		   return arrayListObjectStrings;
	}

}
