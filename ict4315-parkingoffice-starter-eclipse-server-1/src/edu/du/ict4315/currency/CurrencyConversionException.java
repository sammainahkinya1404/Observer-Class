//////////////////////////
// This class represents an exception in converting parameters to a valid money
// File: CurrencyConversionException.java
// Author: M. I. Schwartz
//////////////////////////
package edu.du.ict4315.currency;

public class CurrencyConversionException extends RuntimeException {
  private static final long serialVersionUID = -6459223342242685383L;

  public CurrencyConversionException(String message) {
    super(message);
  }
}
