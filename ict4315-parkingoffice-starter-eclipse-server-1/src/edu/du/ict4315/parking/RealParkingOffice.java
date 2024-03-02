////////////////////
// This object is at the heart of the Parking System.
// It is called "RealParkingOffice" in anticipation of creating proxies later
// File: RealParkingOffice.java
// Author: M. I. Schwartz
////////////////////
package edu.du.ict4315.parking;

import edu.du.ict4315.currency.Money;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RealParkingOffice {
  private String parkingOfficeName = "Not set";
  private final List<Customer> listOfCustomers = new ArrayList<>();
  private final List<ParkingLot> listOfParkingLots = new ArrayList<>();
  private Address parkingOfficeAddress = new Address.Builder().build();

  // Note: The transaction manager / permit manager approach used here would also  be appropriate
  //       for the customer list and parking lot list
  private final PermitManager permitManager = new PermitManager();  
  private final TransactionManager transactionManager = new TransactionManager(this);

  public String register(Customer c) {
    listOfCustomers.add(c);
    return c.getId();
  }

  public String register(Car c) {
    return permitManager.register(c).getId();
  }

  public ParkingTransaction park(LocalDateTime d, ParkingPermit p, ParkingLot l) {
    return transactionManager.park(d, p, l);
  }

  public Money getParkingCharges(ParkingPermit p) {
    return transactionManager.getParkingCharges(p);
  }

  public Money getParkingCharges(Customer c) {
    return transactionManager.getParkingCharges(c);
  }

  public String getParkingOfficeName() {
    return parkingOfficeName;
  }

  public void setParkingOfficeName(String parkingOfficeName) {
    this.parkingOfficeName = parkingOfficeName;
  }

  public Address getParkingOfficeAddress() {
    return parkingOfficeAddress;
  }

  public void setParkingOfficeAddress(Address parkingOfficeAddress) {
    this.parkingOfficeAddress = parkingOfficeAddress;
  }

  // Until parking lots are injected, this sets up a few from strings.
  // It is final as no derived class of RealParkingLot can override it.
  public final void setupParkingLots() {
    String[][] parkingLotData = { 
      { "W","Lot W","E Jewell Ave","","Denver","CO","80210","$5.00" },
      { "108","Lot 108","E Buchtel Ave","","Denver","CO","80210","$2.00" },
      { "321","Lot 321","S Gaylord St","", "Denver","CO","80210","$8.00" },
      { "301","Lot 301","E Evans Ave","","Denver","CO","80210","$8.00" },
    };
    
    for ( String[] row: parkingLotData ) {
      Address address = new Address.Builder()
          .withStreetAddress1(row[2])
          .withStreetAddress2(row[3])
          .withCity(row[4])
          .withState(row[5])
          .withZip(row[6]).build();
      ParkingLot lot = new ParkingLot(row[0],row[1],address,Money.of(row[7]));
      addParkingLot(lot);
    }
  }
  
  public void addParkingLot(ParkingLot lot) {
    listOfParkingLots.add(lot);
  }
  // TODO: All of existing Customers, Cars, Permits, Lots should be persisted
  // TODO: These interfaces should be dependency-injected, not constructed
  // If an add lot method is ever added, the transaction manager must observer it.
  public RealParkingOffice() {
    setupParkingLots();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Parking Office: ");
    sb.append(parkingOfficeName);
    sb.append("\n");
    sb.append(parkingOfficeAddress);
    sb.append("\n");
    sb.append("Customer List\n");
    sb.append(listOfCustomers);
    sb.append("\n");
    sb.append("Parking Lots\n");
    sb.append(listOfParkingLots);
    sb.append("\n");

    return sb.toString();
  }

  public Customer getCustomer(String id) {
    Customer result = null;
    for (Customer c : listOfCustomers) {
      if (c.getId().equals(id)) {
        result = c;
        break;
      }
    }
    return result;
  }
  
  // Use delegation
  public ParkingPermit getParkingPermit(String id) {
    return permitManager.findPermit(id);
  }

  public ParkingLot getParkingLot(String id) {
    ParkingLot result = null;
    for (ParkingLot p : listOfParkingLots) {
      if (p.getId().equals(id)) {
        result = p;
        break;
      }
    }

    return result;
  }
}
