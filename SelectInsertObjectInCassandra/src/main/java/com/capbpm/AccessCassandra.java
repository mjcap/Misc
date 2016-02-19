package com.capbpm;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cassandra.core.RowMapper;
import org.springframework.data.cassandra.core.CassandraOperations; 
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.web.client.RestTemplate;

import com.capbpm.dto.ObjectBlobs;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.DriverException;
import com.datastax.driver.core.querybuilder.QueryBuilder; 
import com.datastax.driver.core.querybuilder.Select; 

public class AccessCassandra {

	public List<ObjectBlobs> getListObjectBlob(){
		
		Cluster cluster; 
		Session session;
		ArrayList<ObjectBlobs> arrayListObjectBlob = null;
		
		//cluster = new Cluster.Builder().addContactPoints("localhost").withPort(9042).build(); 
		//cluster = new Cluster.Builder().addContactPoints("158.85.209.196").withPort(9042).build();
		/*try {
			cluster = Cluster.builder().addContactPoints(InetAddress.getLocalHost()).build();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			cluster = Cluster.builder().addContactPoints(InetAddress.getByName("odm.capbpm.com")).build();
			cluster.init();
			session = cluster.connect("showcase"); 
			CassandraOperations cassandraOps = new CassandraTemplate(session); 
			
			Select s = QueryBuilder.select().all().from("objectblobs");  
			
			   arrayListObjectBlob = (ArrayList)cassandraOps.query(s, new RowMapper<ObjectBlobs>() {

					public ObjectBlobs mapRow(Row row, int rowNum) throws DriverException {
						ObjectBlobs ob = new ObjectBlobs(row.getString("id"), row.getBytes("data"));
						return ob;
					}

				});	   
			   for (ObjectBlobs p : arrayListObjectBlob){
				   System.out.println("AccessCassandra.getListObjectBlob() id="+p.getId()+" data="+p.getData());
			   }			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("AccessCassandra ERROR");
			e.printStackTrace();
		}

		   
		   return arrayListObjectBlob;
	}
	
	public void insertObjectBlobs(byte[] b){
		Cluster cluster; 
		Session session;
		
		
		try {
			
			System.out.println("AccessCassandra.insertObjectBlobs() byte[] b="+b);
			System.out.println("AccessCassandra.insertObjectBlobs() byte[] b as String="+new String(b));
			cluster = Cluster.builder().addContactPoints(InetAddress.getByName("odm.capbpm.com")).build();
			cluster.init();
			session = cluster.connect("showcase"); 
			/*CassandraOperations cassandraOps = new CassandraTemplate(session); 
			cassandraOps.insert(ByteBuffer.wrap(b));	*/
			
			PreparedStatement statement = session.prepare("INSERT INTO showcase.objectblobs (id,data) VALUES (?, ?)");
	        BoundStatement boundStatement = new BoundStatement(statement);
	        session.execute(boundStatement.bind(new Long(System.currentTimeMillis()).toString(),ByteBuffer.wrap(b)));
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("AccessCassandra ERROR");
			e.printStackTrace();
		}		
		
	}

	public void insert(byte[] b) {
		Cluster cluster; 
		Session session;
		
		try{	
			System.out.println("AccessCassadra.insert() b="+b);
			cluster = Cluster.builder().addContactPoints(InetAddress.getByName("odm.capbpm.com")).build();
			cluster.init();
			session = cluster.connect("showcase"); 
			CassandraOperations cassandraOps = new CassandraTemplate(session);
			cassandraOps.insert(b);			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("AccessCassandra ERROR");
			e.printStackTrace();
		}
		
	}

}
