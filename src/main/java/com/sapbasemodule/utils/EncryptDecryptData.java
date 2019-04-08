package com.sapbasemodule.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.net.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class EncryptDecryptData {

	public static String encryptPassword(String texto) {
        //return new String(Base64.encodeBase64(texto.getBytes()));
		
		//String passwordToHash = "password";
        String encryptedText = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(texto.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            encryptedText = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        
        return encryptedText;
        
    }
	
	public static String encodeData(String texto) {
		return new String(Base64.encodeBase64(texto.getBytes()));
    }
	
    public static String decodeData(String texto) {
        return new String(Base64.decodeBase64(texto.getBytes()));
    }
}