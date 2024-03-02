//////////////////////////
// File: Server.java
// Author: R Judd, modified by M I Schwartz
// This file implements a String message-oriented server to allow clients to send
// commands to the Parking Office server.
// Note: Assignment 8 examines alternate ways of exchanging messages.
// Note: Assignment 9 examines ways of supporting paralellism.
//////////////////////////
package edu.du.ict4315.parking.server;

import edu.du.ict4315.parking.service.ParkingService;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.RealParkingOffice;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

  static {
    System.setProperty(
        "java.util.logging.SimpleFormatter.format", 
        "%1$tc %4$-7s (%2$s) %5$s %6$s%n");
  }

  private static final Logger logger = Logger.getLogger(Server.class.getName());

  // Pick a TCP/IP Port
  private final int PORT = 7777;

  private final ParkingService service;

  public Server(ParkingService service) {
    this.service = service;
  }

  public void startServer() throws IOException {
    logger.info("Starting server: " + InetAddress.getLocalHost().getHostAddress());
    try ( ServerSocket serverSocket = new ServerSocket(PORT)) {
      serverSocket.setReuseAddress(true);
      while (true) {
        Socket client = serverSocket.accept();
        handleClient(client);
      }
    }
  }

  private void handleClient(Socket client) {
    try ( PrintWriter pw = new PrintWriter(client.getOutputStream())) {
      String output;
      ParkingOfficeResponse response = new ParkingOfficeResponse();
      try {
        ParkingOfficeRequest request = new ParkingOfficeRequest(client.getInputStream());
        response.addResponseString(
            service.performCommand(request.getRequestCommandName(), request.getRequestParameters())
        );
      } catch (RuntimeException ex) {
        ex.printStackTrace();
        response.addResponseString(output = ex.getMessage());
        response.setCode(500);
      }

      pw.println(String.join("\n", response.getResponseStrings()));
      pw.println("end");
      pw.flush();

    } catch (IOException e) {
      logger.log(Level.WARNING, "Failed to read from client.", e);
    } finally {
      try {
        client.close();
      } catch (IOException e) {
        logger.log(Level.WARNING, "Failed to close client socket.", e);
      }
    }
  }

  /**
   * Run this as: $ java ict4315.server.Server
   */
  public static void main(String[] args) throws Exception {
    RealParkingOffice parkingOffice = new RealParkingOffice();
        // Set an address and name
    parkingOffice.setParkingOfficeName("DU Parking Office -- Test");
    Address address;
    address = new Address.Builder().withStreetAddress1("2130 S. High St.")
        .withCity("Denver")
        .withState("CO")
        .withZip("80210")
        .build();
    parkingOffice.setParkingOfficeAddress(address);

    ParkingService service = new ParkingService(parkingOffice);

    new Server(service).startServer();
  }
}