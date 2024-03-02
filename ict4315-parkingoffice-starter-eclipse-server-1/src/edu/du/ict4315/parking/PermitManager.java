////////////////////////
// This class registers and holds the permits for a ParkingOffice
// File: PermitManager.java
// Author: M. I. Schwartz
////////////////////////
package edu.du.ict4315.parking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PermitManager {
	private List<ParkingPermit> permits = new ArrayList<ParkingPermit>();
	
	private static int idCounter = 1000;
	private static String getPermitId() {
		idCounter += 1;
		return String.format("P%04d", idCounter);
	}
	public ParkingPermit register(Car car) {
		// TODO: Get an id better
		String id = getPermitId();
		// TODO: How to compute an expiration date
		LocalDateTime d = LocalDateTime.now();
		ParkingPermit permit = new ParkingPermit(id,car,d.plusYears(1L));
		permits.add(permit);
		return permit;
	}
	
	public ParkingPermit findPermit(String id) {
		ParkingPermit result = null;
		for ( ParkingPermit p: permits) {
			if ( p.getId().equals(id)) {
				result = p;
				break;
			}
		}
		
		return result;
	}
}
