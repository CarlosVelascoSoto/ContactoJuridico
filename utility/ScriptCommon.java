package com.aj.utility;


public class ScriptCommon {

	public static String encode(String text){
		return Base64.encodeBytes(text.getBytes());
	}
	
	public static String decode(String text){
		return new String(Base64.decode(text));
	}
	
}
