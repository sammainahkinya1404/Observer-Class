//////////////////////////
// This class represents a parking transaction event.
// This class is immutable.
// File: ParkingTransaction.java
// Author: M. I. Schwartz
//////////////////////////
package edu.du.ict4315.parking;

import java.time.Instant;
import java.time.LocalDateTime;

import edu.du.ict4315.currency.Money;

public class ParkingTransaction {
  private Instant transactionDate;
	private LocalDateTime date;
	private ParkingPermit permit;
	private ParkingLot parkingLot;
	private Money chargedAmount;
	
	public ParkingTransaction(LocalDateTime d, ParkingPermit p, ParkingLot l, Money m) {
	  transactionDate = Instant.now();
		date = d;
		permit = p;
		parkingLot = l;
		chargedAmount = m;
	}
	
	public Money getChargedAmount() {
		return chargedAmount;
	}
	
	public ParkingPermit getPermit() {
		return permit;
	}

  public LocalDateTime getDate() {
    return date;
  }

  public ParkingLot getParkingLot() {
    return parkingLot;
  }

  public Instant getTransactionDate() {
    return transactionDate;
  }
  
  // TODO: toString()
}
