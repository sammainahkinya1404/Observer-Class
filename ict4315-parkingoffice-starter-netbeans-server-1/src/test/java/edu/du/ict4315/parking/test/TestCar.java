/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.test;

import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author michael
 */
public class TestCar {

   private Customer customer = new Customer("CUST-1", "Sam", "Doe", "303-222-1111", 
        new Address.Builder().withStreetAddress1("111 Main St.")
            .withCity("Denver").withState("CO").withZip("80201").build());
   
   @Test
   void testConstructors() {
     Car car1 = new Car();
     assertEquals(CarType.COMPACT, car1.getType());
     assertTrue(car1.getOwner().getLastName().isBlank());
     assertTrue(car1.getLicensePlate().isBlank());
     
     Car car2 = new Car(CarType.SUV, "ABC-123", customer);
     assertEquals(CarType.SUV, car2.getType());
     assertEquals(car2.getOwner().getLastName(), "Doe");
     assertEquals(car2.getLicensePlate(), "ABC-123");
     
     
     Car car3 = new Car(CarType.COMPACT, "DEF-456", customer);
     assertEquals(CarType.COMPACT, car3.getType());
     assertEquals(car3.getOwner().getLastName(), "Doe");
     assertEquals(car3.getLicensePlate(), "DEF-456");
   }
     
}
