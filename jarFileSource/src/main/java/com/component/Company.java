package com.component;
 
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import java.util.Date;
 
@Entity
public class Company{
 
 @Id
 public long id;
 
  public long getId(){
    return this.id;
  }
 
  public void setId( long id ){
    this.id=id;
  }
 
  public String address;
 
  public String getAddress(){
    return this.address;
  }
 
  public void setAddress( String address){
    this.address = address;
  }
 
  public int netWorth;
 
  public int getNetWorth(){
    return this.netWorth;
  }
 
  public void setNetWorth( int netWorth ){
    this.netWorth = netWorth;
  }
  
}
