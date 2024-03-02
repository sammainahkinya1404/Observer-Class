//////////////////////////
// File: ClientCommand.java
// Author: Instructor
// This file represents the commands the client might use and execute.
// The Server will have a similar construct, based on an interface, in Assignment 1
//////////////////////////
package edu.du.ict4315.parking.clients;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ClientCommand {
  private final String name;
  private final String command;
  private final List<String> fieldNames;
  
  public ClientCommand(String name, String command, List<String> fieldNames) {
    this.name = name;
    this.command = command;
    this.fieldNames = Collections.unmodifiableList(fieldNames);
  }
  
  public String name() {
    return name;
  }
  
  public List<String> fieldNames() {
    return fieldNames;
  }

  public String execute(Map<String, String> data) {
    try {
      return ServerClient.runCommand(command, data).toString();
    } catch (IOException e) {
      e.printStackTrace(); // Stack trace is to help with debug
      return e.getMessage();
    }
  }
}
