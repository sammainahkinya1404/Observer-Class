/*
 * Course ICT4315
 * Author: Instructor
 */
package edu.du.ict4315.currency.test;

import edu.du.ict4315.currency.Money;
import java.util.Currency;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * The Money class comprises an integral amount (currency's "penny" equivalent) and an official
 * currency designator. At the time of writing these tests, Money is not immutable, but maybe it
 * should be...
 *
 * @author michael
 */
public class MoneyTest {

  Currency[] currencies = {
    Currency.getInstance("USD"), 
    Currency.getInstance("USD"), 
    Currency.getInstance("USD")
  };
  double[] doubleAmounts = {12.00, 1824.95, -693.03 };
  int[] intAmounts = {1200, 182495, -69303 };
  String[] stringAmounts = {"$ 12.00", "$ 1824.95", "$ -693.03"};
  Money[] monies = {Money.of(doubleAmounts[0], currencies[0]),
                    Money.of(doubleAmounts[1], currencies[1]),
                    Money.of(doubleAmounts[2], currencies[2])
  };

  @Test
  public void testOf_double() {
    System.out.println("of (double)");
    for (int i = 0; i < doubleAmounts.length; i++) {
      double amt = doubleAmounts[i];
      Money money = Money.of(amt);
      assertEquals(monies[i], money);
    }
  }

  @Test
  public void testOf_double_Currency() {
    System.out.println("of (double, Currency)");
    for (int i = 0; i < doubleAmounts.length; i++) {
      double amt = doubleAmounts[i];
      Money money = Money.of(amt, currencies[i]);
      assertEquals(monies[i], money);
    }
  }
  
  @Test
  public void testOf_String() {
    System.out.println("of (String)");
    String[] amounts = {
      "USD 32.44", "$32.44",
      "EUR 93.443", "€ 93.441",
      "GBP 103.3001", "£ 103.3",
      "JPY 213327.443", "¥ 213327",
      "ILS 513.222", "₪ 513.22",
      "IQD 9722.3326"
    };
    String[] expResults = {
      "$ 32.44", "$ 32.44",
      "€ 93.44", "€ 93.44",
      "£ 103.30", "£ 103.30",
      "¥ 213327", "¥ 213327",
      "₪ 513.22", "₪ 513.22",
      "IQD 9722.332"
    };
    for ( int i=0; i < amounts.length; i++) {
      Money money = Money.of(amounts[i]);
      assertEquals(expResults[i], money.toString());
    }
  }

  @Test
  public void testAdd() {
    System.out.println("add");
    double[] left = {4.44, 3.33, -12.50, 63.00, -99.99};
    double[] right = {2.33, 19.60, -33.44, 90.02, 106.87};
    for (int i = 0; i < left.length; i++) {
      Money l = Money.of(left[i]);
      Money r = Money.of(right[i]);
      Money sum = Money.of(left[i] + right[i]);
      Money result = Money.add(l, r);
      assertEquals(sum, result);
    }
  }

  @Test
  public void testSubtract() {
    double[] left = {4.44, 3.33, -12.50, 63.00, -99.99};
    double[] right = {2.33, 19.60, -33.44, 90.02, 106.87};
    for (int i = 0; i < left.length; i++) {
      Money l = Money.of(left[i]);
      Money r = Money.of(right[i]);
      Money diff = Money.of(left[i] - right[i]);
      Money result = Money.subtract(l, r);
      assertEquals(diff, result);
    }
  }

  @Test
  public void testTimes() {
    System.out.println("times");
    double[] left = {4.44, 3.33, -12.50, 63.00, -99.99};
    for (int i=0; i<left.length; i++) {
      for ( int j=-20; j<20; j += 2 ) {
        Money mult = Money.of(left[i]*j);
        Money times = Money.times(Money.of(left[i]), j);
        assertEquals(mult.doubleValue(),times.doubleValue(),0.01);
      }
    }
  }

  @Test
  public void testDiv() {
    double[] left = {4.44, 3.33, -12.50, 63.00, -99.99};
    for (int i=0; i<left.length; i++) {
      for ( int j=-5; j<5; j += 2 ) {
        Money fract = Money.of(left[i]/j);
        Money div = Money.div(Money.of(left[i]), j);
        assertEquals(fract.doubleValue(),div.doubleValue(),0.015);
      }
    }
  }

  @Test
  public void testGetCurrency() {
    System.out.println("getCurrency");
    Set<Currency> currencies = Currency.getAvailableCurrencies();
    String[] badCurrencyString = { "abfg", "qrmd" };
    for (Currency currency: currencies ) {
      Money testMoney = Money.of(100.00,currency);
      assertEquals(testMoney.getCurrency(), currency);
    }
    for (String s: badCurrencyString) {
      Exception thrown = assertThrows(
      IllegalArgumentException.class, () -> Currency.getInstance(s),
              "IllegalArgumentException should have been thrown but wasn't");
    }
  }

  @Test
  public void testDoubleValue() {
    System.out.println("doubleValue");
    double[] left = {4.44, 3.33, -12.50, 63.00, -99.99};

    for (int i=0; i<left.length; i++) {
      Money m = Money.of(left[i]);
      assertEquals(m.doubleValue(),left[i],0.015);
    }
  }

  @Test
  public void testToString() {
    System.out.println("toString");
    for (int i=0; i<doubleAmounts.length; i++) {
      String s1 = Money.of(doubleAmounts[i]).toString();
      assertEquals(stringAmounts[i], s1);
    }
  }

  @Test
  public void testMore() {
    System.out.println("more");
    double[] left = {4.44, 3.33, -12.50, 63.00, -99.99, 4.77 };
    double[] right = {2.33, 19.60, -33.44, 90.02, 106.87, 4.77 };
    boolean[] results = { true, false, true, false, false, false };
    
    for (int i = 0; i < left.length; i++) {
      Money l = Money.of(left[i]);
      Money r = Money.of(right[i]);
      boolean result = l.more(r);
      assertEquals(results[i], result);
    }

  }

  @Test
  public void testEquals() {
    System.out.println("equals");
    double[] left = {4.44, 3.33, -12.50, 63.00, -99.99, 4.77, 0 };
    double[] right = {2.33, 19.60, -33.44, 90.02, 106.87, 4.77, -0 };
    boolean[] results = { false, false, false, false, false, true, true };
    
    for (int i = 0; i < left.length; i++) {
      Money l = Money.of(left[i]);
      Money r = Money.of(right[i]);
      boolean result = l.equals(r);
      assertEquals(results[i], result);
    }

  }

  @Test
  public void testCompareTo() {
    System.out.println("compareTo");
    double[] left = {4.44, 3.33, -12.50, 63.00, -99.99, 4.77 };
    double[] right = {2.33, 19.60, -33.44, 90.02, 106.87, 4.77 };
    int[] results = { 1, -1, 1, -1, -1, 0 };
    
    for (int i = 0; i < left.length; i++) {
      Money l = Money.of(left[i]);
      Money r = Money.of(right[i]);
      int result = l.compareTo(r);
      assertEquals(results[i], result);
    }

  }

}
