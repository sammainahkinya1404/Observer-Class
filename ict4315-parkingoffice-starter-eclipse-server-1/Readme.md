% Title: Readme
% Author: M I Schwartz
% Date: 2022-02-16

ParkingOffice-Starter
======================

This file contains the starter files for the Parking Office application we are exploring this term as a platform to apply Design Patterns.

These are fewer than 2 dozen files that cover many of the key aspects of the Parking Office application.

Some of the files provided are not needed yet (e.g., User, Password). They _might_ come in handy later.

Some of the files provided use advanced implementations (e.g., streams and lambdas in TransactionManager.java). 
These are for illustration for your examination. Neither streams nor lambdas are required in
the coding, so if you'd like help with a non-lambda or non-stream implementation, let the 
instructor know and we can work through an alternate implementation.

Summary Tables
======================

Note: Classes marked with a plus sign (+) can be ignored until Assignment 8.

## **Package edu.du.ict4315.currency**

* Classes related to manipulating money

| File | Role |
| ---- | ---- |
| Money.java | Simple, immutable class to hold and manipulate Money objects. |
| CurrencyConversionException.java | A runtime exception related to problems in constructing Money objects |

## **Package edu.du.ict4315.parking**

* Classes in the Parking domain, directly related to domain objects in the problem.

| File | Role |
| ---- | ---- |
| Address.java | Customer address class, using the Builder pattern |
| Car.java | Customer Car class |
| CarType.java | Enumeration of Car types of interest |
| Customer.java | Simple customer class |
| ParkingLot.java | Class to represent Parking Lots |
| ParkingPermit.java | Class to represent a Parking Permit for a Customer's Car |
| ParkingTransaction.java | Immutable class holding information about a parking event |
| PermitManager.java | Class to manage a collection of parking permits |
| RealParkingOffice.java | Class to represent the Parking Office. So named to allow a ParkingOffice interface later. |
| TransactionManager.java | Class to manage a collection of ParkingTransactions |

## **Package edu.du.ict4315.parking.clients**

* Classes used as clients for the application
* Only `ParkingOfficeClient.java` is used before the client-server exercise.

| File | Role |
| ---- | ---- |
| ParkingOfficeClient.java | "Smoke test" class to walk through a use case scenario |

## **Additional notes**

  * Setting up
    - This project is set up as a standard Maven project using Java 17. 
      - If you have installed Maven and Java, `mvn compile test` on the command line will exercise this small body of code.
      - It is expected that Java 17 can be replaced by a later, current version of Java
      - There will be a few features that will not compile without change if using Java 8
    - You can easily copy this code into a Netbeans or Eclipse project as well.
      - If you have any challenges in this regard, please contact the instructor
  * Command lines in an IDE
    - To provide a command line in the IDE, use your IDE's Run/Run Configuration feature (Eclipse) or Run/Set Configuration/Run features (Netbeans)
  * Command line from a Terminal / Command Prompt
    - Make sure Java is on your PATH by running `java -version`
    - Be sure to set up your CLASSPATH environment variable before running the commands in the instructions

