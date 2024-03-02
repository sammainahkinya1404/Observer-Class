/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.server;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author michael
 */
public class ParkingOfficeResponse {
  private int code;
  private List<String> results;
  
  public ParkingOfficeResponse() {
    code = 200; // Default, OK
    results = new ArrayList<>();
  }
  
  public String[] getResponseStrings() {
    return results.toArray(String[]::new);
  }
  
  public void addResponseString(String result) {
    results.add(result);
  }
  
  public int getCode() { 
    return code;
  }
  
  public void setCode(int code) {
    this.code = code;
  }
}
