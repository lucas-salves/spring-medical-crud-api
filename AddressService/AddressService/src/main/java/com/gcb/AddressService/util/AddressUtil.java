package com.gcb.AddressService.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AddressUtil {
    public static String hashMD5(String input) throws Exception{
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            
            byte[] message = messageDigest.digest(input.getBytes());
            
            BigInteger signum = new BigInteger(1, message);
            
            String hashText = signum.toString(16);
            
            while(hashText.length()<32){
                hashText = "0" + hashText;
            }
            return hashText;
            
        } catch (NoSuchAlgorithmException ex) {
            
            throw new Exception(ex.getMessage());
        }
    }
}
