////////////////////
// This class represents a Car to the Parking system.
// File: Car.java
// Author: M. I. Schwartz
////////////////////
package edu.du.ict4315.parking;

import java.util.Objects;

public class Car {
	private CarType type;
	private String licensePlate = "";
	private Customer owner;
	
	public Car() {
		type = CarType.COMPACT;
		owner = new Customer();
	}
	
	public Car(CarType type, String licensePlate, Customer owner) {
		this.type = type;
		this.licensePlate = licensePlate;
		this.owner = owner;
	}

	public CarType getType() {
		return type;
	}

	public void setType(CarType type) {
		this.type = type;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}
	
	@Override
	public String toString() {
	  StringBuilder sb = new StringBuilder();
	  sb.append("Car: ");
	  sb.append("Type: ");
	  sb.append(type);
	  sb.append(" ");
	  sb.append("License plate: ");
	  sb.append(licensePlate);
	  sb.append(" ");
	  sb.append("Owner: ");
	  sb.append(owner.getCustomerName());
	  return sb.toString();
	}
	
	// Since Cars may appear on a list, they require equals and a hashCode.
	@Override
	public boolean equals(Object o) {
	  if ( o == this ) { return true; }
	  if ( ! (o instanceof Car) ) { return false; }
	  Car c = (Car)o;
	  // Car equality: Type, license plate
	  return ( c.getLicensePlate().contentEquals(licensePlate) && 
	           c.getType() == getType());
	  
	}
	
	@Override
	public int hashCode() {
	  return Objects.hash(getLicensePlate(),getType());
	}
}
