/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.server;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

/**
 *
 * @author michael
 */
public class ParkingOfficeRequest {
  final private String requestCommandName;
  final private String[] requestParameters;
  
  private static final String END_TOKEN = "end";
  
  public ParkingOfficeRequest(String command, String[] parameters) {
    requestCommandName = command;
    requestParameters = parameters.clone();
  }

  public String getRequestCommandName() {
    return requestCommandName;
  }

  public String[] getRequestParameters() {
    return requestParameters;
  }
  
  // Constructor from an input stream
  public ParkingOfficeRequest(InputStream is) {
    // The scanner and input stream will be closed when we disconnect
    Scanner scanner = new Scanner(is);
    ArrayList<String> data = new ArrayList<>();
    while (scanner.hasNext()) {
      String token = scanner.nextLine();
      if (token.equals(END_TOKEN)) {
        break;
      }
      data.add(token);
    }
    requestCommandName = data.remove(0);
    requestParameters = data.toArray(String[]::new);
  }
}
