package com.gruposalinas.elektra.movilidadgs.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.TimeZone;

public class Encryption {

	public static String md5(String s) {

	    try {
           //  s="14948318"; // dato de prueba
	        // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();
	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        for (int i=0; i<messageDigest.length; i++)
			//hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			hexString.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));// se cambio el metodo de conversion debido a que se comia un cero
	        return hexString.toString().substring(0,5);



	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";

	}
	
	public static String getHour(){
		//int date = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);// cambiado por el problema de zona horaria
	int date=	Calendar.getInstance(TimeZone.getTimeZone("UTC")).get(Calendar.HOUR_OF_DAY);
		if(date < 10)
			return "0" + String.valueOf(date);
		else
			return String.valueOf(date);
	}

	public static  int getHora(){
		int date = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

		return date;
	}

	public static  int getminuto(){
		int date = Calendar.getInstance().get(Calendar.MINUTE);

		return date;
	}
	}