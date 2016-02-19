package com.component;
 
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import java.util.Date;

@Entity
public class Customer{
 
 @Id
 public long id;
 
  public long getId(){
    return this.id;
  }
 
  public void setId( long id ){
    this.id=id;
  }
 
  public Date startDate;
 
  public Date getStartDate(){
    return this.startDate;
  }
 
  public void setStartDate( Date startDate){
    this.startDate = startDate;
  }
 
  public int age;
 
  public int getAge(){
    return this.age;
  }
 
  public void setAge( int age){
    this.age = age;
  }
 
  public String lastName;
 
  public String getLastName(){
    return this.lastName;
  }
 
  public void setLastName( String lastName){
    this.lastName = lastName;
  }
 
  public double accountBalance;
 
  public double getAccountBalance(){
    return this.accountBalance;
  }
 
  public void setAccountBalance( double accountBalance){
    this.accountBalance = accountBalance;
  }
 
  public boolean isActive;
 
  public boolean getIsActive(){
    return this.isActive;
  }
 
  public void setIsActive( boolean isActive){
    this.isActive = isActive;
  }
 
  public String firstName;
 
  public String getFirstName(){
    return this.firstName;
  }
 
  public void setFirstName( String firstName){
    this.firstName = firstName;
  }
 
}
