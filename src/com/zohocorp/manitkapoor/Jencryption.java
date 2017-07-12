package com.zohocorp.manitkapoor;

import java.security.MessageDigest;

public class Jencryption {
   public static String Encrypt(String pass) throws Exception
   {
       MessageDigest md = MessageDigest.getInstance("MD5");
       byte[] dataBytes = pass.getBytes();
       byte[] mdbytes = md.digest(dataBytes);

       //convert the byte to hex 
       String hexString = "";
   	   for (int i=0;i<mdbytes.length;i++) {
   		  String hex=Integer.toHexString(0xff & mdbytes[i]);
  	     	if(hex.length()==1) hexString += "0";
  	     	hexString += hex;
   	    }
   	 return hexString;
   }
}
