/*
 * Course: ICT4315
 * File: TestParkingService.java
 * Author: Instructor
 */
package edu.du.ict4315.parking.service.test;

import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.service.ParkingService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author michael
 */
public class TestParkingService {

  private final RealParkingOffice office = new RealParkingOffice();
  private final ParkingService service = new ParkingService(office);

  private List<String> customerIds = new ArrayList<>();

  @Test
  void testCustomerSuccess() {
    String[][] params = {
      {"lastName=Smith"},
      {"Jones"},
      {"name=Nguyen"}
    };
    for (int i = 0; i < params.length; i++) {
      String result = service.performCommand("CUSTOMER", params[i]);
      System.out.println("Created customer " + result);
      assertTrue(result.contains("CUST-"));
    }
  }

  @Test
  void testCustomerFailure() {
    String[][] params = {
      {}
    };
    for (int i = 0; i < params.length; i++) {
      String result = service.performCommand("CUSTOMER", params[i]);
      System.out.println("Result: " + result);
      assertFalse(result.contains("CUST-"));
    }
  }

  private void collectCustomerIds(int min, int max) {
    // Just check all CUST-num strings between min and max
    for (int num = min; num <= max; num++) {
      Customer c;
      if ((c = office.getCustomer(String.format("CUST-%d", num))) != null) {
        customerIds.add(c.getId());
      }
    }
  }

  private String getCustomerId() {
    if (!customerIds.isEmpty()) {
      return customerIds.get(0);
    }
    return null;
  }

  @Test
  void testCarSuccess() {
    // Create one customer to avoid any ordering problems in the tests
    String[] customerParam = {"Sample"};
    service.performCommand("CUSTOMER", customerParam);
    collectCustomerIds(10, 200);
    if (customerIds.isEmpty()) {
      System.out.println("No customers registered yet. Cannot test Car.");
      return; // Can't test zero ids
    }
    String[][] params = {
      {"license_plate=ABC-123", "customer=" + getCustomerId()},
      {"license_plate=DEF-234", "customer=" + getCustomerId()},};

    for (int i = 0; i < params.length; i++) {
      String result = service.performCommand("CAR", params[i]);
      System.out.println("Created car " + result);
      // The result is multi-line, so the (?s) allows matching across lines.
      // The .* and the beginning and end is to matach anything other than the Pnnnn permit numbers
      assertTrue(result.matches("(?s).*P\\d+.*")); // or "P[0-9]+"
    }
  }

  @Test
  void testCarFailure() {
    String[][] params = {
      {},
      { "license_plate=GHI-987" },
      { "customer="+getCustomerId() }
    };
    // Create one customer to avoid any ordering problems in the tests
    String[] customerParam = {"Sample"};
    service.performCommand("CUSTOMER", customerParam);
    collectCustomerIds(10, 200);
    if (customerIds.isEmpty()) {
      System.out.println("No customers registered yet. Cannot test Car.");
      return; // Can't test zero ids
    }

    for (int i = 0; i < params.length; i++) {
      String result = service.performCommand("CAR", params[i]);
      System.out.println("Result: " + result);
      assertFalse(result.matches("(?s).*P\\d+.*"));
    }
  }

}
