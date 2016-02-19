package com.capbpm.dto; 

import java.nio.ByteBuffer;

import org.springframework.data.cassandra.mapping.PrimaryKey; 
import org.springframework.data.cassandra.mapping.Table; 

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

@Table 
public class ObjectBlobs { 
 
 @PrimaryKey 
 private String id; 
 
 private ByteBuffer data; 
 

 public ObjectBlobs(){
	 
 }
 
 
 public ObjectBlobs(String id, ByteBuffer data) { 
  this.id = id; 
  this.data = data; 
 } 
 
 public String getId() { 
  return id; 
 } 
 
 public ByteBuffer getData() { 
  return this.data; 
 } 
 
 
 
 @Override 
 public String toString() { 
  return "Person [id=" + id + ", data=" + data + "]"; 
 } 
 
}
