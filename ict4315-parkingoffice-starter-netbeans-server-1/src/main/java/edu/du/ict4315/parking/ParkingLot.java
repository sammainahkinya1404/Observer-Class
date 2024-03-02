////////////////////
// This class represents the Parking Lot
// File: ParkingLot.java
// Author: M. I. Schwartz
////////////////////
package edu.du.ict4315.parking;

import edu.du.ict4315.currency.Money;
import java.time.LocalDateTime;

public class ParkingLot {
    private String id;
    private String name;
    private Address address;
    private Money baseRate = Money.of(5.00);
    
    public ParkingLot(String id, String name, Address address) {
    	this.id = id;
    	this.name = name;
    	this.address = address;
    }
    
    public ParkingLot(String id, String name, Address address, Money baseRate) {
      this.id = id;
      this.name = name;
      this.address = address;
      this.baseRate = baseRate;
    }
    
    public Money getParkingCharges(ParkingPermit p, LocalDateTime in) {
      return baseRate;
    }
    
    public Money getBaseRate() {
    	return baseRate;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();

    	sb.append(id);
    	sb.append("\n");
    	sb.append(name);
    	sb.append("\n");
    	sb.append(address);

    	return sb.toString();
    }

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}
	
  // Method for permit-required-on-enter lot
  public void enterLot(LocalDateTime in, String permitId) {
  }

	// Method for permit-required-on-exit lot
  public void exitLot(LocalDateTime in, LocalDateTime out, String permitId) {
  }
  
}
