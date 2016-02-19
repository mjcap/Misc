package com.capbpm; 

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)


public class Person { 
 

 private String id; 
 
 private String name; 
 private int age; 

 public Person(){
	 
 }
 
 
 public Person(String id, String name, int age) { 
  this.id = id; 
  this.name = name; 
  this.age = age; 
 } 
 
 public String getId() { 
  return id; 
 } 
 
 public String getName() { 
  return name; 
 } 
 
 public int getAge() { 
  return age; 
 } 
 
 @Override 
 public String toString() { 
  return "Person [id=" + id + ", name=" + name + ", age=" + age + "]"; 
 } 
 
}
