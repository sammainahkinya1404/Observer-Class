///////////////////////////////
// File: ParkingService.java
// Author: Instructor
// This file is the seed for how the ParkingOffice server handles remote commands.
// Note that Assignment 1 restructures how this service is written, to use ServiceCommand
//
// Parking Service Commands'
//     Command            command name      parameters
//     Register customer  CUSTOMER          first name
//     Register car       CAR               license plate, owner id
//     Park (leave lot)   PARK              local date time, lot id, permit id
//     Charges            CHARGES           customer id (unimplemented)
//
// TODO: Consider whether the paramters should be a Properties object (removing positionality)
///////////////////////////////
package edu.du.ict4315.parking.service;

import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.ParkingTransaction;
import edu.du.ict4315.parking.RealParkingOffice;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParkingService {

  private final RealParkingOffice parkingOffice;

  private static final Logger logger = Logger.getLogger(ParkingService.class.getName());

  public ParkingService(RealParkingOffice parkingOffice) {
    this.parkingOffice = parkingOffice;
  }

  /**
   * This method performs the service requested.
   * In order to keep the command parameters from being highly positional, each parameter is expected
   * to be provided in "property" format: parameter-name=parameter-value
   * 
   * Sample commands and their parameters:
   *   CUSTOMER 
   * 
   * performCommand
   * @param command a command name to perform
   * @param args an array of Strings representing the parameters
   * @return String representing the result
   */
  public String performCommand(String command, String[] args) {
    List<String> messages = new ArrayList<>();
    logger.log(Level.INFO, "Performing {0} command", command);
    messages.add(command + ": "+ String.join(", ", args));
    switch (command) {
      case "CUSTOMER": // Creates a customer
        if (checkNumberOfParameters(1, args.length)) {
          Customer customer = new Customer();
          customer.setLastName(checkName(args[0]));
          messages.add(parkingOffice.register(customer));
        } else {
          messages.add("Cannot create customer: wrong number of parameters");
        }
        break;
      case "CAR": // Creates a car permit for an existing customer's car
        if (checkNumberOfParameters(2, args.length)) {
          Car car = new Car();
          car.setLicensePlate(checkLicensePlate(args[0]));
          car.setOwner(checkCustomer(args[1]));
          car.setType(CarType.SUV);
          messages.add(parkingOffice.register(car));
        } else {
          messages.add("Cannot create car permit: wrong number of parameters");
        }
        break;
      case "PARK": // Creates the Parking Transaction for a particular car to enter a specific lot at a specific time
        if (checkNumberOfParameters(3, args.length)) {
          LocalDateTime dateTime = checkDateTime(args[2]);
          ParkingLot pl = checkParkingLot(args[0]);
          ParkingPermit parkedCar = checkParkingPermit(args[1]);
          ParkingTransaction transaction = parkingOffice.park(dateTime, parkedCar, pl);
          if ( transaction != null ) {
            // Updated output to simply post the charge amount.
            messages.add(transaction.getChargedAmount().toString());
          } else {
            messages.add("Parking transaction failed. Parameters invalid");
          }
        } else {
          messages.add("Cannot park: wrong number of parameters");
        }
        break;
      case "CHARGES": 
        // TODO: Charges
        messages.add("Charges not implemented yet");
        break;
      default: // Did not recognize this command
        messages.add("Command unknown");
    }
    return String.join("\n", messages);
  }

  ////////////////////
  // TODO: Complete the checks.
  // The below check routines are just sketches. 
  // What should the acceptability rules be?
  // What should the defaults be?
  // Should commands / parameters be edited (trimmed, case changed, length limited)?
  ////////////////////
  private static boolean checkNumberOfParameters(int expected, int provided) {
    boolean result = true;
    if (provided < expected) {
      logger.log(Level.SEVERE, "Not enough parameters! Expected {0} received {1}",
          new Object[]{expected, provided});
      result = false;
    }
    return result;
  }

  private static String checkName(String name) {
    String result = name;
    String[] parts = name.split("=");
    if ( parts.length == 2 ) {
      result = parts[1];
    }
    return result;
  }

  private static LocalDateTime checkDateTime(String dateTime) {
    LocalDateTime result = LocalDateTime.now();
    String[] parts = dateTime.split("=");
    if ( parts.length == 2 ) {
      dateTime = parts[1];
    }
    try {
      result = LocalDateTime.parse(dateTime);
    } catch (DateTimeParseException ex) {
      logger.log(Level.INFO, "Cannot parse datetime {0}: {1}", new Object[]{dateTime, ex});
    }
    return result;
  }

  private Customer checkCustomer(String customerId) {
    String[] parts = customerId.split("=");
    if ( parts.length == 2 ) {
      customerId = parts[1];
    }
    Customer customer = parkingOffice.getCustomer(customerId);
    if (customer == null) {
      customer = new Customer();
      customer.setFirstName("Unknown");
      parkingOffice.register(customer);
    }
    return customer;
  }

  private ParkingLot checkParkingLot(String lotId) {
    String[] parts = lotId.split("=");
    if ( parts.length == 2) {
      lotId = parts[1];
    }
    ParkingLot result = parkingOffice.getParkingLot(lotId);
    return result;
  }

  private static String checkLicensePlate(String plate) {
    String[] parts = plate.split("=");
    if ( parts.length == 2 ) {
      plate = parts[1];
    }
    String result = plate;
    if (result == null) {
      plate = "Unknown";
    }
    return result;
  }

  private ParkingPermit checkParkingPermit(String permitId) {
    String[] parts = permitId.split("=");
    if ( parts.length == 2 ) {
      permitId = parts[1];
    }
    ParkingPermit result;
    result = parkingOffice.getParkingPermit(permitId);
    return result;
  }

}
