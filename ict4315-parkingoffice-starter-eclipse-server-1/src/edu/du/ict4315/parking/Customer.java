////////////////////
// The Customer class represents a parker in the Parking system
// File: Customer.java
// Author: M. I. Schwartz
////////////////////
package edu.du.ict4315.parking;

import edu.du.ict4315.parking.support.IdMaker;

public class Customer {
	private String id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Address address;
	
	public String getCustomerName() {
		return firstName + " " + lastName;
	}

	public Customer() {
		id = IdMaker.makeId("CUST-1");
		firstName = "";
		lastName = "";
		phoneNumber = "";
		address = new Address.Builder().build();
	}
	
	public Customer(String id, String firstName, String lastName,
			String phoneNumber, Address address) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Customer id: ");
		sb.append(id);
		sb.append("\n");
		sb.append(lastName);
		sb.append(", ");
		sb.append(firstName);
		sb.append("\n");
		sb.append(address);
		sb.append("\n");
		sb.append(phoneNumber);
		return sb.toString();
	}
}
