/////////////////////////
// This class represents users of the system. 
// A user may have one role, assigned at construction time.
// Users are persisted in a CSV file.
// Passwords are stored ONLY hashed.
// File: User.java
// Author: M I Schwartz
/////////////////////////
package edu.du.ict4315.parking.server.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: When various changes are made, automatically rewrite the user store.
// Great place to implement an Observer!
//   Let the RealParkingOffice receive the change events and rewrite the store.

public class User {
  private static Map<String, User> users = new HashMap<>();

  private final String userId;
  private String customerId;
  private String passwdHash;
  private UserRole role = UserRole.OTHER;

  private User(String id, String passwdHash) {
    userId = id;
    this.passwdHash = passwdHash;
    // Store the user by userId
    users.put(userId, this);
  }

  // TODO: Remove this method or change name to authorize.
  public static User authorizeUser(String userName, String passwd) {
    User user = users.get(userName);
    if (user != null && user.getUserId() != "anonymous") {
      String hash = toHash(passwd);
      if (!hash.equals(user.passwdHash)) {
        user = null;
        System.err.println("User password incorrect. Cannot log in user.");
      } else {
        System.out.println("Logged in.");
      }
    }
    return user;
  }
  
  public static User getUser(String userName) {
    User user = users.get(userName);
    return user;
  }
  
  public static User getCustomer(String customerId) {
    for ( String key: users.keySet() ) {
      User user = users.get(key);
      if ( user != null && user.getCustomerId() != null && 
          users.get(key).getCustomerId().equals(customerId)) {
        return users.get(key);
      }
    }
    return null;
  }

  public static String toHash(String string) {
    return Password.getHash(string);
  }

  public static String[] getUsers() {
    return users.keySet().toArray(new String[0]);
  }

  public String getUserId() {
    return userId;
  }

  public String getCustomerId() {
    return customerId;
  }
  
  public void setCustomerId(String id) {
    customerId = id;
  }

  public String getPasswdHash() {
    return passwdHash;
  }

  public UserRole getRole() {
    return role;
  }

  public static User createUser(String userId, String password, UserRole role) {
    if ( users.containsKey(userId)) {
      System.err.println("Duplicate id "+userId+". User not created.");
      return null;
    } else if ( password == null ) {
      System.err.println("No password. User "+userId+" not created.");
      return null;      
    }
    String passwdHash = toHash(password);
    User user = new User(userId,passwdHash);
    user.role = role;
    return user;
  }
  
  // For the file interface
  public static void setUser(String[] record) {
    if ( users.containsKey(record[0].trim())) {
      System.err.println("Duplicate id "+record[0].trim()+". Not added.");
    }
    User user = new User(record[0].trim(), record[3].trim());
    user.customerId = record[1].trim();
    user.role = UserRole.valueOf(record[2].trim().toUpperCase());
  }
  
  public static String[][] getUserInfo() {
    List<String[]> result = new ArrayList<>();
    for ( String key: users.keySet() ) {
      User u = users.get(key);
      String[] userData = new String[4];
      userData[0] = u.userId;
      userData[1] = u.customerId;
      userData[2] = u.role.toString();
      userData[3] = u.passwdHash;
      result.add(userData);
    }
    return result.toArray(new String[0][]);
  }
}
