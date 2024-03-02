//////////////////////////
// This class represents the Parking Permit itself.
// File: ParkingPermit.java
// Author: M. I. Schwartz
// This class is a candidate to be a record (JEP 359, Java 14)
//////////////////////////
package edu.du.ict4315.parking;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingPermit {
	private String id;
	private Car car;
	private LocalDateTime expiration;
	
	public Car getCar() {
		return car;
	}
	
	public String getId() {
		return id;
	}
	
	public boolean isExpired() {
		LocalDateTime now = LocalDateTime.now();
		return expiration.isBefore(now);
	}
	
	public ParkingPermit(String id, Car car, LocalDateTime expiration) {
		this.id = id;
		this.car = car;
		this.expiration = expiration;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID: ");
		sb.append(id);
		sb.append("; expiration: ");
		sb.append(expiration.toLocalDate().toString());
		if ( isExpired() ) {
			sb.append(" (expired)");
		}
		sb.append("; Car: ");
		sb.append(car.getType());
		sb.append(" ");
		if ( car.getLicensePlate() != null && ! car.getLicensePlate().isEmpty()) {
		  sb.append(car.getLicensePlate());
		}
		return sb.toString();
	}
	
	// Since ParkingPermits are likely to be in collections, we must add equals and hashCode
	@Override
	public boolean equals(Object o) {
	  if ( this == o ) { return true; }
	  if ( ! (o instanceof ParkingPermit) ) { return false; }
	  ParkingPermit p = (ParkingPermit)o;
	  if ( p.id != null && p.id.equals(id) && p.car != null && p.car.equals(car))
	    return true;
	  return false;
	}
	
	@Override
	public int hashCode() {
	  return Objects.hash(id,car);
	}
}
