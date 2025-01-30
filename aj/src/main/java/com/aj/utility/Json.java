package com.aj.utility;

import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json{
	
	private static ObjectMapper objectMapper = getDefaultObjectMapper();
	
	private static ObjectMapper getDefaultObjectMapper(){
		ObjectMapper defaultObjectMapper = new ObjectMapper();
		// ...
		return defaultObjectMapper;
	}
	
	public static JsonNode parse(String str){
		try{
			return objectMapper.readTree(str);
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}