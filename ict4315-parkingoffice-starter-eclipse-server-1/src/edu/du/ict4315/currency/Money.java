////////////////////
// This class represents Money
// File: Money.java (immutable)
// Author: M. I. Schwartz based on R. Judd class Money
////////////////////
////////////////////
// For a complete implementation, including currency conversion and 
// rounding rules, please refer to JSR 354 and the reference
// Moneta implementation
// This implementation overlaps a subset of the FastMoney in Moneta.
////////////////////
package edu.du.ict4315.currency;

import java.util.Currency;
import java.util.Objects;
import java.util.Set;

// A long is used to represent money as this avoids rounding on the client side.
// The intent is that the amount represents the smallest unit of currency,
// so for dollars, it reprsents cents.
// The Currency class represents ISO4217 currencies, and includes how many of the smallest
// currency unit are in the basic currency unit. For the USDollar, it is 2 (that is 10^2 or 100)
// For Japan's Yen, it is zero (that is, the amount represents whole yen)
// For Iraq's Dinar, it is 3 (that is, 1000 fils (10^3) to one Iraqi dinar)
// This implementation does no currency conversion, so all transactions support one currency only.
public final class Money implements Comparable<Money> {

    private final long amount;
    private final Currency currency;

    public static Money of(double amt) {
        return Money.of(amt, Currency.getInstance("USD"));
    }

    public static Money of(String moneyString) {
        return new Money(moneyString);
    }

    public static Money of(double amt, Currency currency) {
        return new Money(amt, currency);
    }

    // TODO: Include locale variations for symbol
    // Problem: codes are locale-dependent (e.g., $ vs US$), 
    // and the same symbol is used by multiple locales (e.g., $)
    // Thus codes are more reliable
    static private Currency fromSymbol(String moneyString) {
        Currency result = Currency.getInstance("USD");
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        for (Currency currency : currencies) {
            String code = currency.getCurrencyCode();
            String symbol = currency.getSymbol();
            if (moneyString.contains(code) || moneyString.contains(symbol)) {
                result = currency;
                break;
            }
        }
        return result;
    }

    /*
     * Multiply the amount by 10^currency fraction digits
     * to represent the
     */
    private Money(double money, Currency currency) {
        this.currency = currency;
        int fractionDigits = currency.getDefaultFractionDigits();
        if (fractionDigits < 0) {
            fractionDigits = 0;
        }
        amount = Math.round(money * Math.pow(10, fractionDigits));
    }

    private Money(long money, Currency currency) {
        amount = money; // Don't scale the "cents" value
        this.currency = currency;
    }

    public Money(Money money) {
        this.amount = money.amount;
        currency = money.currency;
    }

    private Money(String moneyString) {
        Currency curr = fromSymbol(moneyString);
        String amt = moneyString.replaceAll("[^0-9.]", "");
        String[] dollarsCents = amt.split("[.]");
        long dollars = Long.parseLong(dollarsCents[0]);
        int fractionDigits = curr.getDefaultFractionDigits();
        long cents = 0L;
        int fractLength = 0;
        if (dollarsCents.length > 1) {
            fractLength = dollarsCents[1].length();
        }
        if (fractionDigits > 0 && fractLength > 0) {
            cents = Long.parseLong(dollarsCents[1].substring(0, Math.min(fractionDigits, fractLength)));
            for (; fractLength < fractionDigits; fractLength += 1) {
                cents *= 10;
            }
        }

        this.amount = (long) (dollars * Math.pow(10, fractionDigits) + cents);
        this.currency = curr;
    }

    private long getCents() {
        return amount;
    }

    public static Money add(Money l, Money r) {
        if (!l.currency.equals(r.currency)) {
            throw new CurrencyConversionException("Cannot convert currencies at this time");
        }
        return new Money(l.getCents()+r.getCents(), l.currency);
    }

    public static Money subtract(Money l, Money r) {
        if (!l.currency.equals(r.currency)) {
            throw new CurrencyConversionException("Cannot convert currencies at this time");
        }
        return new Money(l.getCents()-r.getCents(),l.currency);
    }

    public static Money times(Money l, int n) {
        return new Money(l.getCents()*n, l.getCurrency());
    }

    public static Money times(Money l, double d) {
        long amt = (long) (l.getCents() * d);
        return new Money(amt, l.currency);
    }

    public static Money div(Money l, int n) {
        return new Money(l.getCents() / n, l.getCurrency());
    }

    public Currency getCurrency() {
        return currency;
    }

    public double doubleValue() {
        int digits = currency.getDefaultFractionDigits();
        if (digits >= 0) {
            return amount * Math.pow(10, -digits);
        }
        return amount;
    }

    /*
	 * Ensure enough leading zeros to have one before the decimal point.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int fractionDigits = currency.getDefaultFractionDigits();
        String spec = "%03d";
        if (fractionDigits > 0) {
            spec = "%0" + (fractionDigits + 1) + "d";
        }

        String amt = String.format(spec, amount);

        sb.append(currency.getSymbol());
        sb.append(" ");
        if (fractionDigits > 0) {
            sb.append(amt.substring(0, amt.length() - fractionDigits));
            if ((amt.length() - fractionDigits < 1)
                    || (amount < 0 && amt.length() - fractionDigits < 2)) {
                sb.append("0");
            }
            sb.append(".");
            sb.append(amt.substring(amt.length() - fractionDigits));
        } else {
            sb.append(amt);
        }

        return sb.toString();
    }

    public boolean more(Money money) {
        // Should use currency as well. No currency conversion in this version.
        return compareTo(money) > 0;
    }

    @Override
    public boolean equals(Object money) {
        if (!(money instanceof Money)) {
            return false;
        }
        return compareTo((Money) money) == 0;
    }

    // Since we are overriding equality, we need to override hashCode.
    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public int compareTo(Money money) {
        if (!currency.equals(money.currency)) {
            return -1;
        }
        long result = amount - money.amount;
        if (result > 0L) {
            return 1;
        } else if (result < 0) {
            return -1;
        }
        return 0;
    }

}
