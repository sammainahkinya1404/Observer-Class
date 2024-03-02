/////////////////////////////
// File: ServerClient.java
// Author: Instructor
// This file contains the client's list of displayCommands it is willing to
// format and send to the server.
// The main lists the known displayCommands
// With parameters, the main will run those displayCommands
// The server must receive data matching the ParkingService interface.
/////////////////////////////
package edu.du.ict4315.parking.clients;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class ServerClient {

  // The displayCommands will be sorted by key order in the GUI, so alphebetizing is good.
  // First 2 characters are stripped from description to button label
  public static final String[][] COMMANDS = new String[][]{
    {"a. Register Customer", "CUSTOMER", "Name"},
    {"b. Register Vehicle", "CAR", "License", "Customer"},
    {"c. Parking", "PARK", "Lot", "Permit Id", "Time"},
    {"d. Get Charges", "CHARGES", "Customer", "Car"}
  };

  // This must match the server's selected port and host
  private static final int PORT = 7777;
  private static final String SERVER = "localhost";

  private ServerClient() {
  }

  public static List<String> runCommand(String command, Map<String, String> data)
      throws IOException {

    InetAddress host = InetAddress.getByName(SERVER);
    ArrayList<String> response = new ArrayList<>();
    try ( Socket link = new Socket(host, PORT);
         Scanner scanner = new Scanner(link.getInputStream());) {

      // connect to server
      System.out.println("You are now connected to: " + host.getHostAddress());
      PrintWriter output = new PrintWriter(link.getOutputStream());

      for (Entry<String,String> value : data.entrySet()) {
        command += String.format("%n%s=%s", value.getKey(), value.getValue());
      }
      output.println(command);
      output.println("end");
      output.flush();

      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        if (line.equals("end")) {
          break;
        }
        response.add(line);
      }
    }
    return response;

  }

  public static Map<String, ClientCommand> displayCommands() {
    Map<String, ClientCommand> commands = new TreeMap<>();
    for (String[] description : COMMANDS) {
      commands.put(description[0], new ClientCommand(description[0].substring(2), description[1],
          Arrays.asList(description).subList(2, description.length)));
    }
    return commands;
  }

  public static Map<String, ClientCommand> commands() {
    Map<String, ClientCommand> commands = new TreeMap<>();
    for (String[] description : COMMANDS) {
      commands.put(description[1], new ClientCommand(description[0].substring(2), description[1],
          Arrays.asList(description).subList(2, description.length)));
    }
    return commands;
  }
  
  /**
   * Run this as: $ java ict4315.client.Client COMMAND label1=value1 label2=value2 ... Use LIST to
   * get the list of commands and their labels.
   */
  public static void main(String[] args) throws IOException {

    if (args.length == 0 || args[0].equals("LIST")) {
      System.out.println("Here are the commands we know about.");
      System.out.println(
          "Usage: $ java " + ServerClient.class.getName() + " COMMAND label1=value1 label2=value2 ...");
      System.out.println();
      for (String[] description : COMMANDS) {
        System.out.format("%s: %s ", description[0], description[1]);
        for (int i = 2; i < description.length; ++i) {
          System.out.format("%s=value ", description[i].replaceAll(" ", "").toLowerCase());
        }
        System.out.println();
      }
      return;
    }

    ClientCommand command = commands().get(args[0]);
    if (command == null) {
      System.out.println("Unrecognised command: " + args[0]);
      System.out.print("Known commands: ");
      String comma = "";
      for (String[] description : COMMANDS) {
        System.out.print(comma + description[1]);
        comma = ", ";
      }
      System.out.println();
      return;
    }
    Map<String, String> values = new LinkedHashMap<>();
    for (String label : command.fieldNames()) {
      for (int i = 0; i < args.length; ++i) {
        if (args[i].startsWith(label.replaceAll(" ", "").toLowerCase())) {
          values.put(label, args[i].replaceAll(".*=", ""));
          break;
        }
      }
    }
    for (String output : ServerClient.runCommand(args[0], values)) {
      System.out.println(output);
    }
  }
}
