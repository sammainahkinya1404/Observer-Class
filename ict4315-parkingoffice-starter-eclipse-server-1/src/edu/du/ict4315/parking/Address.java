//////////////////////////////
// File: Address.java
// Author: M. I. Schwartz
//
// This class represents a physical address.
// 
// The class would be a good candidate for a record type (JEP 359, Java 14)
//////////////////////////////
package edu.du.ict4315.parking;

public class Address {
  private String streetAddress1;
  private String streetAddress2;
  private String city;
  private String state;
  private String zip;

  public String getAddressInfo() {
    StringBuilder sb = new StringBuilder();
    if (streetAddress1 != null && !streetAddress1.isEmpty()) {
      sb.append(streetAddress1);
      sb.append("\n");
    }
    if (streetAddress2 != null && !streetAddress2.isEmpty()) {
      sb.append(streetAddress2);
      sb.append("\n");
    }
    if (city != null && !city.isEmpty()) {
      sb.append(city);
      sb.append(", ");
    }
    if (state != null && !state.isEmpty()) {
      sb.append(state);
      sb.append("\n");
    }
    if (zip != null && !zip.isEmpty()) {
      sb.append(zip);
      sb.append("\n");
    }

    return sb.toString();
  }

  public static class Builder {
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zip;

    public Builder() {
    }

    public Builder withStreetAddress1(String street1) {
      this.streetAddress1 = street1;

      return this;
    }

    public Builder withStreetAddress2(String street) {
      this.streetAddress2 = street;

      return this;
    }

    public Builder withCity(String city) {
      this.city = city;

      return this;
    }

    public Builder withState(String state) {
      this.state = state;

      return this;
    }

    public Builder withZip(String zip) {
      this.zip = zip;

      return this;
    }

    public Address build() {
      Address address = new Address();
      address.streetAddress1 = this.streetAddress1;
      address.streetAddress2 = this.streetAddress2;
      address.city = this.city;
      address.state = this.state;
      address.zip = this.zip;

      return address;

    }

  }

  // This constructor is modified in early assignments to provide a default
  // address
  private Address() {
    streetAddress1 = "";
    streetAddress2 = "";
    city = "";
    state = "";
    zip = "";
  }

  public String getStreetAddress1() {
    return streetAddress1;
  }

  public void setStreetAddress1(String streetAddress1) {
    this.streetAddress1 = streetAddress1;
  }

  public String getStreetAddress2() {
    return streetAddress2;
  }

  public void setStreetAddress2(String streetAddress2) {
    this.streetAddress2 = streetAddress2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  @Override
  public String toString() {
    return getAddressInfo();
  }
}
