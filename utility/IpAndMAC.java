package com.aj.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class IpAndMAC {
	public static String getIpAddr(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	} 
	
	
	   public static String getMACAddress(String ip){ 
	        String str = ""; 
	        String macAddress = ""; 
	        try { 
	            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip); 
	            InputStreamReader ir = new InputStreamReader(p.getInputStream()); 
	            LineNumberReader input = new LineNumberReader(ir); 
	            for (int i = 1; i <100; i++) { 
	                str = input.readLine(); 
	                if (str != null) { 
	                    if (str.indexOf("MAC Address") > 1) { 
	                        macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length()); 
	                        break; 
	                    } 
	                } 
	            } 
	        } catch (IOException e) { 
	            e.printStackTrace(System.out); 
	        } 
	        return macAddress; 
	    }
	   public static String getMac(String ip) {
		   Pattern macpt = null;
		    // Find OS and set command according to OS
		    String OS = System.getProperty("os.name").toLowerCase();

		    String[] cmd;
		    if (OS.contains("win")) {
		        // Windows
		        macpt = Pattern
		                .compile("[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+");
		        String[] a = { "arp", "-a", ip };
		        cmd = a;
		    } else {
		        // Mac OS X, Linux
		        macpt = Pattern
		                .compile("[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+");
		        String[] a = { "arp", ip };
		        cmd = a;
		    }

		    try {
		        // Run command
		        Process p = Runtime.getRuntime().exec(cmd);
		        p.waitFor();
		        // read output with BufferedReader
		        BufferedReader reader = new BufferedReader(new InputStreamReader(
		                p.getInputStream()));
		        String line = reader.readLine();

		        // Loop trough lines
		        while (line != null) {
		            Matcher m = macpt.matcher(line);

		            // when Matcher finds a Line then return it as result
		            if (m.find()) {
		                System.out.println("Found");
		                System.out.println("MAC: " + m.group(0));
		                return m.group(0);
		            }

		            line = reader.readLine();
		        }

		    } catch (IOException e1) {
		        e1.printStackTrace();
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }

		    // Return empty string if no MAC is found
		    return "";
		}
	   public static Map<String, String> getRequestHeadersInMap(HttpServletRequest request) {

		    Map<String, String> result = new HashMap<>();

		    Enumeration<String> headerNames = request.getHeaderNames();
		    while (headerNames.hasMoreElements()) {
		        String key = headerNames.nextElement();
		        String value = request.getHeader(key);
		        result.put(key, value);
		    }

		    return result;
		}

}
