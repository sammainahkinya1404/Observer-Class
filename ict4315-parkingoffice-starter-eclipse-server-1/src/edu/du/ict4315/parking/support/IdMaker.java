//////////////////////////
// This class uses AtomicIntegers to create a series of one-up ids with arbitrary prefixes
// File: IdMaker.java
// Author: M I Schwartz
//////////////////////////
package edu.du.ict4315.parking.support;

import java.util.concurrent.atomic.AtomicInteger;

public class IdMaker {
  private static final AtomicInteger counter = new AtomicInteger(1);
  
  public static String makeId(String prefix) {
    return prefix + counter.addAndGet(1);
  }
  
}
