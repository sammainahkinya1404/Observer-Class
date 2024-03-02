//////////////////////
// File: TestParkingOffice.java
// Author: M I Schwartz
// JUnit 5 unit tests for the RealParkingOffice class
//////////////////////
package edu.du.ict4315.parking.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.RealParkingOffice;
import java.util.Random;

class TestParkingOffice {

  private RealParkingOffice office;
  private List<String> permitIds = new ArrayList<>();
  private List<String> customerIds = new ArrayList<>();

  // A few utility functions to create Addresses, Cars, and Customers
  private final String[] streets = { "S. High St", "E. Main St.", "Front St.", "Second St.", "W. Main St." };
  private final String[] cities = { "Denver", "Boulder", "Glendale", "Aurora", "Longmont" };
  private final Random random = new Random();
  private final String[] fNames = { "Pat", "Sam", "Robin", "Bobby" };
  private final String[] lNames = { "Gross", "Klein", "Schwartz", "Weiss", "Smith", "Jones", "Doe", "Roe" };
  
  private Address createAddress() {
    Address address;
    String st = String.valueOf(random.nextInt(1000) + 1) + " " 
            + streets[random.nextInt(streets.length)];
    String city = cities[random.nextInt(cities.length)];
    String zip = String.valueOf(80000 + random.nextInt(200));
    address = new Address.Builder().withStreetAddress1(st)
            .withCity(city)
            .withState("CO")
            .withZip(zip)
            .build();
    return address;
  }
  
  private Customer createCustomer(Address address) {
    Customer customer = new Customer();
    customer.setFirstName(fNames[random.nextInt(fNames.length)]);
    customer.setLastName(lNames[random.nextInt(lNames.length)]);
    customer.setPhoneNumber(String.format("303-%3d-%04d", random.nextInt(900)+100,random.nextInt(10000)));
    customer.setAddress(address);
    return customer;
  }
  
  private Car createCar(Customer owner) {
    Car car = new Car();
    car.setOwner(owner);
    car.setType(CarType.values()[random.nextInt(CarType.values().length)]);
    car.setLicensePlate(String.format("XYZ-%03d",random.nextInt(1000)));
    return car;
  }
  
  // End of utility functions
  
  @BeforeEach
  void setUp() throws Exception {
    office = new RealParkingOffice();
    // Set an address and name
    office.setParkingOfficeName("DU Parking Office -- Test");
    Address address;
    address = new Address.Builder().withStreetAddress1("2130 S. High St.")
        .withCity("Denver")
        .withState("CO")
        .withZip("80210")
        .build();
    office.setParkingOfficeAddress(address);
    
    Address add1 = createAddress();
    Customer c1 = createCustomer(add1);
    String id1 = office.register(c1);
    customerIds.add(c1.getId());
    assertEquals(id1,c1.getId());
    
    Address add2 = createAddress();
    Customer c2 = createCustomer(add2);
    String id2 = office.register(c2);
    customerIds.add(c2.getId());
    assertEquals(id2,c2.getId());

    // Set up some Cars. Associate them with customer c1
    Car car1 = createCar(c1);
    Car car2 = createCar(c1);
    String car1Id = office.register(car1);
    String car2Id = office.register(car2);
    assertEquals(car1.getOwner(),c1);
    assertEquals(car2.getOwner(),c1);
    permitIds.add(car1Id); 
    permitIds.add(car2Id);
  }

  @Test
  final void testInit() {
    String result = office.toString();
    String expOfficeText = "Parking Office: DU Parking Office -- Test\n" +
        "2130 S. High St.\n" +
        "Denver, CO\n" +
        "80210\n" +
        "\n" +
        "Customer List\n";
    assertTrue(result.startsWith(expOfficeText));
    // These are loaded by a FileLoader, which should have a separate test.
  }

  @Test
  void testCustomerRegistration() {
    // Build a customer, and make sure we can find hir.
    Customer c1 = createCustomer(createAddress());
    Customer c2 = createCustomer(createAddress());
    
    String cid1 = office.register(c1);
    String cid2 = office.register(c2);

    assertTrue(cid1.equals(c1.getId()));
    assertTrue(cid2.equals(c2.getId()));
    assertTrue(office.getCustomer(cid1) == c1); // Same object
    assertTrue(office.getCustomer(cid2) == c2);

    customerIds.add(cid1);
    customerIds.add(cid2);
  }

  @Test
  void testCarRegistration() {
    // Pick a customer, any customer:
    String customerId = customerIds.get(random.nextInt(customerIds.size()));
    Customer customer = office.getCustomer(customerId);
    assertEquals(customerId,customer.getId());
    
    Car car1 = createCar(customer);
    String permitId = office.register(car1);
    permitIds.add(permitId);
    
    ParkingPermit permit = office.getParkingPermit(permitId);
    assertTrue(permit.getCar() == car1); // Same object
  }

  private void createParkingCharges(String id1, String id2) {
    ParkingLot lot = office.getParkingLot("W");
    // create a 2-hour charge
    LocalDateTime dt = LocalDateTime.now();
    dt = dt.minusHours(-1);
    lot.enterLot(dt, id1);
    // create a 2-day charge
    dt = LocalDateTime.now();
    dt = dt.minusDays(2);
    lot.enterLot(dt, id2);
  }

  private String[] getPermitsWithCustomers(int howMany) {
    String[] result = new String[howMany];
    Collections.shuffle(permitIds);
    int j = 0;
    for (int i = 0; i < permitIds.size(); i++) {
      ParkingPermit tmp = office.getParkingPermit(permitIds.get(i));
      if ( tmp != null && tmp.getCar() != null && tmp.getCar().getOwner() != null ) {
        result[j++] = permitIds.get(i);
        if (j >= howMany ) {
          break;
        }
      }
    }
    return result;
  }

  @Test
  void testGetParkingChargesPermit() {
    String[] permits = getPermitsWithCustomers(2);
    String permitId1 = permits[0];
    String permitId2 = permits[1];

    if ( permitId1 == null || permitId2 == null ) {
      System.err.println("Problem: no useful permits present");
      fail("No valid permits");
    }

    createParkingCharges(permitId1, permitId2);
    ParkingPermit permit = office.getParkingPermit(permitId1);
    System.out.println("Customer for "+permitId1+" is: "+permit.getCar().getOwner());
    System.out.println("Customer for "+permitId2+" is: "+office.getParkingPermit(permitId2).getCar().getOwner());
    Money mp1 = office.getParkingCharges(permit);
    Money mp2 = office.getParkingCharges(office.getParkingPermit(permitId2));
    System.out.println("Permit: " + permit + "; Amount: "+mp1);
    System.out.println("Permit: " + office.getParkingPermit(permitId2) + "; Amount: "+mp2);
  }

  @Test
  void testGetParkingChargesCustomer() {
    String[] permits = getPermitsWithCustomers(2);
    String permitId1 = permits[0];
    String permitId2 = permits[1];    if ( permitId1 == null || permitId2 == null ) {
      System.err.println("Problem: no useful permits present");
      fail("No valid permits");
    }
    createParkingCharges(permitId1,permitId2);
    System.out.println("Customer for "+permitId1+" is: "+office.getParkingPermit(permitId1).getCar().getOwner().getId());
    System.out.println("Customer for "+permitId2+" is: "+office.getParkingPermit(permitId2).getCar().getOwner().getId());
    String customerId = office.getParkingPermit(permitId1).getCar().getOwner().getId();
    Money mp1 = office.getParkingCharges(office.getCustomer(customerId));
    Customer c1 = office.getCustomer(customerId);
    System.out.println("Charges for "+c1+": "+mp1);
    assertTrue(mp1.equals(Money.add(
        office.getParkingCharges(office.getParkingPermit(permitId1)),
        office.getParkingCharges(office.getParkingPermit(permitId2)))));
  }

}
