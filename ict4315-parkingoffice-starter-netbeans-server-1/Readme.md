% Title: Readme
% Author: M I Schwartz
% Date: 2022-02-16

ParkingOffice-Starter-Server
======================

This file contains the changes for the Parking Office application we are exploring this term as a platform to apply Design Patterns to support a simple server.

These are a small number of files that cover many of the key aspects of the Parking Office application as a server. 

Some of the files provided are not needed yet (e.g., User, Password). They _might_ come in handy later.

Some of the files provided use advanced implementations (e.g., streams and lambdas in TransactionManager.java). 
These are for illustration for your examination. Neither streams nor lambdas are required in
the coding, so if you'd like help with a non-lambda or non-stream implementation, let the 
instructor know and we can work through an alternate implementation.

Files described in the Readme for ParkingOffice-Starter are not described in this Readme.

Summary Tables
======================

## **Package edu.du.ict4315.parking.client**

* Classes used as clients for the application
* Only `ParkingOfficeClient.java` is used before the client-server exercise.

| File | Role |
| ---- | ---- |
| Client.java | Client designed to match the Server |
| Command.java | Client version of the protocol so Client.java can produce commands for the Server |
| ParkingGui.java | Swing UI to allow visually creating commands for the Server. Note: a JavaFX version is available if interested.

## **Package edu.du.ict4315.parking.server**

| File | Role |
| ---- | ---- |
| ParkingOfficeRequest.java | Object representing a request made of the Server |
| ParkingOfficeResponse.java | Object representing the Server response |
| Server.java | A rough sketch of a server capable of supporting a few requests and responses |

## **Changed files**

These files represent changes from the Parking-Starter to support the client / server

| File | Role |
| ---- | ---- |
| pom.xml | Maven project file. Has gson added as a dependency for Assignement-Week 8 |
| Readme.md | Markdown file (this one) describing the contents of this project |


## **Added Files**

These files represent additions. These may or may not be used at the programmers' discretion.

| Package | File | Role |
| ------  | ---- | ---- |
| N/A | data/parking_lots_du.csv | Comma-separated value file which can be loaded by the FileLoader to create the parking lots |
| N/A | data/users.csv | Comma-separated value file which can be loaded by the FileLoader to create users, some of whom are Customers |
| edu.du.ict4315.parking.support | FileLoader.java | Utility that can convert CSV files into arrays of objects |
| edu.du.ict4315.parking.support | User.java | Object representing a user. Can be authenticated and authorized to a UserRole |
| edu.du.ict4315.parking.support | UserRole.java | Enumeration representing the possible user roles. |
| edu.du.ict4315.parking.support | Password.java | Object representing a password via its hash, which can be stored |
| edu.du.ict4315.parking.support.test   | TestFileLoader.java | Utility object testing functionality of the FileLoader object |

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

