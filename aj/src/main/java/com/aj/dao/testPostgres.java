package com.aj.dao;

import java.sql.*;

public class testPostgres {

 public testPostgres() {
   try {

     System.err.println( "Starting ..." );
     Class.forName("org.postgresql.Driver");

     System.err.println( "conn..." );
     
     Connection conn = DriverManager.getConnection("jdbc:postgresql://10.0.0.1/AMS","postgres", "123123");

     String tableName = "acceso";
     Statement st = conn.createStatement();
     ResultSet rs = st.executeQuery("select" + "*" + "from " + tableName );

     boolean band = false;
     int intR = rs.getInt(5);
     if (intR == 1) Runtime.getRuntime().exec(" /home/jetaccess/run.sh" );

   }
   catch (Exception e) {
     System.err.println( "e: " + e.getMessage() );
   }

 }


 public static void main(String[] args)
 {
   testPostgres main1 = new testPostgres();
 }

} 