///////////////////////////
// File: Password.java
// Author: M I Schwartz
// Class to turn plaintext into a hash signature.
//
// Also provides a main to allow command line one-way hash
///////////////////////////
package edu.du.ict4315.parking.server.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Password {

  private static final String ALGORITHM = "MD5";
  
//TODO: Add a mechanism for encryption
 public static String getHash(String string) {
   MessageDigest digester;
   try {
     digester = MessageDigest.getInstance(ALGORITHM);
     digester.update(string.getBytes());
     return Base64.getEncoder().encodeToString(digester.digest());
   } catch (NoSuchAlgorithmException e) {
     System.err.println("Unexpected. Standard algorithm not supported.");
   }
   return null;
 }
  
  public static String getNewPasswordHash(User user, String oldPassword, String newPassword) {
    String result = null;
    if ( getHash (oldPassword).equals(user.getPasswdHash())) {
      result = getHash(newPassword);
    } else {
      System.err.println("Not verified.");
    }
    return result;
  }

  public static void main(String[] args) {
    for (String arg : args) {
      if (arg.length() > 3) {
        System.out.println(getHash(arg));
      } else {
        System.err.println("Too short: " + arg);
      }
    }
    if (args.length == 0) {
      System.out.println("Usage: Password plaintext ...");
      System.out.println("       Prints hash of eachg plaintext");
    }
  }
}
