//////////////////////////
// This class represents a parking office client for "smoke test" use.
// The client is in the same JVM as the Parking Office
// File: ParkingOfficeClient.java
// Author: M. I. Schwartz
//////////////////////////
package edu.du.ict4315.parking.clients;

import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.RealParkingOffice;

public class ParkingOfficeClient {
	public static void main(String[] args) {
		RealParkingOffice office = new RealParkingOffice();
		office.setParkingOfficeName("DU Parking Office");
		office.setParkingOfficeAddress(
				new Address.Builder().withStreetAddress1("2130 S. High St.")
				                     .withCity("Denver")
				                     .withState("CO")
				                     .withZip("80208")
				                     .build()
				);
		sampleTransactions(office);
		System.out.println(office);
	}
	
  // Ths sampleTransactions method can be augmented to include whatever tests you like.
	public static void sampleTransactions(RealParkingOffice office) {
		// Use case: Add a customer
		Address address = new Address.Builder()
            .withStreetAddress1("123 Elm St.")
            .withCity("Denver")
            .withState("CO")
            .withZip("80201")
            .build();
		Customer customer = new Customer("C123","Joan","Public","303-555-1212",address);
		
		// Use case: Register Customer
		String customerId = office.register(customer);
		System.out.println("Registered Joan Public: "+customerId);
		
		// Use case: Give customer a car
		Car car1 = new Car(CarType.COMPACT,"ABC123",customer);
		Car car2 = new Car();
		car2.setType(CarType.SUV);
		car2.setLicensePlate("XYZ987");
		car2.setOwner(customer);
		
		// Use Case: Register Car
		String permit1 = office.register(car1);
		String permit2 = office.register(car2);
		
		System.out.println("Permit: "+office.getParkingPermit(permit1));
		System.out.println("Permit: "+office.getParkingPermit(permit2));

		// Park in Lot W
		ParkingLot lot = office.getParkingLot("W");
		System.out.println("Lot: "+lot);
	}
}
