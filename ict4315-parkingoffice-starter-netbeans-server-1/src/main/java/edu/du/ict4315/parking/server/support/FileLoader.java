///////////////////////////
// The FileLoader class loads CSV files using a simplistic parser.
// It has specialty parsers for ParkingLot records and for User records.
// File: FileLoader.java
// Author: M I Schwartz
///////////////////////////
package edu.du.ict4315.parking.server.support;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.RealParkingOffice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
  // TODO: Break this into pieces

  public static String[][] loadCsvFile(String filePath) {
    List<String[]> result = new ArrayList<>();
    try {
      File f = new File(filePath);
      if (f.exists()) {
        if (f.canRead()) {
          BufferedReader reader = new BufferedReader(new FileReader(f));
          String record;
          while ((record = reader.readLine()) != null) {
            if (record.length() == 0) {
              continue;
            } else if (record.charAt(0) == '#') {
              continue; // Comment
            }
            result.add(record.split(","));
          }
          reader.close();
        } else {
          System.err.println("Cannot read file " + filePath);
        }
      } else {
        System.err.println("Cannot find file " + f.getCanonicalPath());
      }
    } catch (IOException e) {
      System.err.println(e);
    }

    return result.toArray(new String[0][0]);
  }

  public static int writeCsvFile(String filePath, String[][] records, String[] header) {
    int count = 0;
    try {
      File f = new File(filePath);
      if (f.canWrite()) {
        PrintWriter writer = new PrintWriter(new FileWriter(f));
        writer.println("# This file was automatically generated. Modify it at your own risk.\n");
        if (header != null) {
          writer.println(String.join(", ", header));
        }
        for (String[] record : records) {
          writer.println(String.join(", ", record));
          count++;
        }
        writer.close();
      }
    } catch (IOException e) {
      System.err.println(e);
    }
    return count;
  }

  public static ParkingLot[] loadCsvFileParkingLot(String filePath) {
    List<ParkingLot> result = new ArrayList<ParkingLot>();
    String[][] records = loadCsvFile(filePath);
    Address address = null;
    Money baseRate = Money.of(1.00);
    for (String[] record : records) {
      try {
        // TODO: A utility should convert the 5 strings to an address
        address = new Address.Builder().withStreetAddress1(record[2])
                .withStreetAddress2(record[3])
                .withCity(record[4])
                .withState(record[5])
                .withZip(record[6])
                .build();
        // record[7] specifies the charging algorithm. If a number, it is a flat rate
        // TODO: A utility should convert the string to a ParkingChargeStrategy value.
        double rate = Double.valueOf(record[7]);
        baseRate = Money.of(rate);
      } catch (NullPointerException npe) {
        System.err.println("Missing data");
      } catch (NumberFormatException nfe) {
        System.err.println("Could not convert to Money: |" + record[7] + "|");
      } catch (ArrayIndexOutOfBoundsException oob) {
        System.err.println("Parking lot record format problem: |" + record + "|");
      }

      result.add(new ParkingLot(record[0], record[1], address, baseRate));
    }
    return result.toArray(new ParkingLot[0]);
  }

  public static void loadCsvFileParkingLotFactory(String filePath, RealParkingOffice office) {
    String[][] records = loadCsvFile(filePath);
    for (String[] record : records) {
      try {
        ParkingLot lot = office.getParkingLot(record[0]);
      } catch (Exception ex) {
        System.err.println("Problem with record [" + String.join(", ", record) + "]. Skipping.");
      }
    }
  }

  public static void loadCsvUserFile(String filePath, RealParkingOffice office) {
    String[][] records = loadCsvFile(filePath);
    for (String[] record : records) {
      try {
        User.setUser(record);
      } catch (Exception ex) {
        System.err.println("Problem with record [" + String.join(", ", record) + "]. Skipping: " + ex);
      }
    }
  }

  // Only ADMIN personnel can save the user file (example)
  public static void saveCsvUserFile(String filePath, RealParkingOffice office, String username, String passwd) {
    String[] header = {"# id", "customerId", "role", "passwdHash"};
    User requester = User.authorizeUser(username, passwd);
    if (requester == null) {
      System.err.println("Error: user " + username + " not authenticated");
      return;
    } else if (requester.getRole() != UserRole.ADMIN) {
      System.err.println("Error: user " + username + " not authorized to overwrite passwords");
      return;
    }
    int count = writeCsvFile(filePath, User.getUserInfo(), header);
    System.out.println("Wrote user file with " + count + " records");
  }
}
