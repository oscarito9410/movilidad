package com.gruposalinas.elektra.movilidadgs.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;

import com.gruposalinas.elektra.movilidadgs.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class Utils {
	private static final String TAG = "Utils";
	public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
	    double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) (earthRadius * c);

	    return dist;
	}
	
	public static String getJsonDate(Date date){
		Log.i(TAG, "TEST date antes del JsonParse = " + date);
		return "/Date(" + date.getTime() + "-0600)/";
	}
	
	//Parseo de fecha en formato JSON a normal
	@SuppressLint("SimpleDateFormat")
	public static String parseJsonDate(String jsonDate){
		String timeString = jsonDate.substring(jsonDate.indexOf("(") + 1, jsonDate.indexOf(")"));
		String[] timeSegments = timeString.split("\\-");
		// May have to handle negative timezones
		int timeZoneOffSet = Integer.valueOf(timeSegments[1]) * 36000; // (("0100" / 100) * 3600 * 1000)
		
		long millis = Long.valueOf(timeSegments[0]);
		Date time = new Date(millis + timeZoneOffSet); 
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		dateFormatter.setLenient(false);
		
		String dateString = dateFormatter.format(time);
		
		return dateString;
	}
	
	//Parsea del formato Json WCF a formato normal
	static String wcfHourParser(String hour){
		
		return hour;
	}

	
	public static String generateMovementId(String fecha, String hora){
		return fecha.toLowerCase().trim().replace("/", "")
				+ hora.toLowerCase().trim().replace(":", "");
	}

	public static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int)length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "+file.getName());
		}

		is.close();
		return bytes;
	}

	public static float getBatteryLevel(Context context) {
		Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		// Error checking that probably isn't needed but I added just in case.
		if(level == -1 || scale == -1) {
			return 50.0f;
		}

		return ((float)level / (float)scale) * 100.0f;
	}

	@SuppressLint("GetInstance")
	public static String getAES(String dato)
	{
		String encri="";
		try {
			EncryptionAES _crypt = new EncryptionAES();
			String output= "";
			String plainText =dato;
			String key = _crypt.SHA256("my secret key", 32); //32 bytes = 256 bit
			//String iv = a.generateRandomIV(16); //16 bytes = 128 bit
		//	System.out.println("vector inicialiador"+"_oPR1qDeMPfkuv13");
			output = _crypt.encrypt(plainText, key, "_oPR1qDeMPfkuv13"); //encrypt
		//	System.out.println("encrypted text=" + output);
			encri=output;
			//output = _crypt.decrypt(output, key,"_oPR1qDeMPfkuv13"); //decrypt
			//System.out.println("decrypted text=" + output);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return encri;
	}

	public static HostnameVerifier hostnameVerifier(){
		HostnameVerifier   hostnameVerifier = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				HostnameVerifier hv =
						HttpsURLConnection.getDefaultHostnameVerifier();
				return hv.verify("sociomas.com.mx", session);
			}
		};

		return hostnameVerifier;

	}

}

