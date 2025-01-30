package com.aj.utility;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import com.aj.utility.Functions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class dbConnect {

	Properties propiedades;

	public dbConnect(String uname, String uemail, String ucompany, String upassw, String uphone, String ucountry,
			String uindustry, String uactivity, String ulevel) {
		try {
			// Si alguno de los parametros viene vacio
			if (uname.length() > 0 && uemail.length() > 0) {

				System.out.println("*** DBCONNECT");
				System.out.println(uname);
				System.out.println(uemail);
				System.out.println("pass:" + upassw);

				System.err.println("Starting ...");
				Connection conn = getConnection();
				String tableName = "clientweb";

				Statement st = conn.createStatement();
				// ResultSet rs = st.executeQuery("update" + " " + tableName + "
				// " + "set status = 0 where userid != 1 and userid != 10 ");

				ResultSet rs;

				// Consulta tabla para validar que el registro no exista
				rs = st.executeQuery("SELECT * FROM " + tableName + " WHERE email = '" + uemail + "'");
				boolean bExist = false;
				while (rs.next()) {
					String s = rs.getString(1);
					System.out.println("s = " + s);
					bExist = true;
				}

				if (!bExist) {
					System.out.println("La cuenta " + uemail + " no existe. Agregando...");
					// Inserta registro
					String sentence = "INSERT INTO " + tableName + " VALUES (nextval('client_seq'), '" + uname + "', '"
							+ uemail + "', '" + upassw + "', null, null, null, null, null, 0, '" + ucompany + "', '"
							+ uphone + "', '" + ucountry + "', '" + uindustry + "', '" + uactivity + "', '" + ulevel
							+ "')";
					// System.out.println( "** Insert " + sentence );
					int rowsAfect = st.executeUpdate(sentence);
					System.out.println("rows affect " + rowsAfect);

				} else {
					System.out.println("La cuenta " + uemail + " ya existe.");
				}
			}
		} catch (Exception e) {
			System.err.println("e: " + e.getMessage());
			System.err.println("e: " + e.toString());
			e.printStackTrace();
		}
	}

	public static void main(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7,
			String arg8, String arg9) {
		dbConnect main1 = new dbConnect(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
	}

	public boolean dbConfirm(String uemail, boolean bUpdated) {
		boolean bExist = false;
		boolean bAccesCreated = false;
		System.out.println("*** dbConfirm...");
		try {
			// Si alguno de los parametros viene vacio
			if (uemail.length() > 0) {

				System.out.println("*** A punto de actualizar...");

				System.out.println("*** DBCONNECT");
				// System.out.println(uname);
				System.out.println(uemail);

				System.err.println("Starting ...");
				Connection conn = getConnection();
				String tableName = "clientweb";

				Statement st = conn.createStatement();
				ResultSet rs;

				// Consulta tabla para validar que el registro no exista
				String password = "";
				rs = st.executeQuery("SELECT * FROM " + tableName + " WHERE email = '" + uemail + "'");
				bExist = false;
				while (rs.next()) {
					String s = rs.getString(1);
					password = rs.getString("password");
					System.out.println("s = " + s);
					bExist = true;
				}

				// Si existe el registro
				if (bExist) {
					System.out.println("La cuenta " + uemail + " ya existe. Actualizando...");
					// Obtiene la fecha y hora actual
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					String strDate = dateFormat.format(date);
					System.out.println(strDate);

					Calendar cal = new GregorianCalendar();
					cal.setTimeInMillis(date.getTime());
					cal.add(Calendar.DATE, 15);
					Date dueDate = new Date();
					dueDate = new Date(cal.getTimeInMillis());
					System.out.println("Fecha vencimiento " + dueDate);

					// Busca registro que no este actualizado
					String sentence = "SELECT * FROM " + tableName + " WHERE email = '" + uemail + "' AND status = 0";
					System.out.println("** select  " + sentence);
					boolean bFound = false;
					rs = st.executeQuery(sentence);
					while (rs.next()) {
						String s = rs.getString(1);
						System.out.println("s = " + s);
						bFound = true;
						System.out.println("Por actualizar");
					}

					// Si lo encontro, no esta activado
					if (bFound) {

						// Actualiza/Activa registro
						sentence = "UPDATE " + tableName + " SET status = 1, activationdate = '" + date
								+ "', demodate = '" + date + "', duedate = '" + dueDate + "' WHERE email = '" + uemail
								+ "';  ";
						System.out.println("** Update " + sentence);
						int intResult = st.executeUpdate(sentence);
						System.out.println("** Update " + intResult);

						// Busca registro actualizado
						sentence = "SELECT * FROM " + tableName + " WHERE email = '" + uemail + "' AND status = 1";
						System.out.println("** Update " + sentence);
						rs = st.executeQuery(sentence);
						if (rs.next()) {
							String s = rs.getString(1);
							st.executeQuery("SELECT registraUsuario('" + Integer.valueOf(s) + "')");
							System.out.println("s = " + s);
							bUpdated = true;
							System.out.println("Cuenta Activada exitosamente.");
						}

						//
						// Si se activo
						//
						// ----------------------
						if (bUpdated) {
							// Cierra base de datos
							conn.close();

							System.out.println("Conecting...RedBahnT-Sales");
							// Se conectara a la instancia asignada para la
							// demostracion
							Connection connect = getConnectionDemostration();

							tableName = "acceso";
							Statement stRBS = connect.createStatement();
							// ResultSet rs = st.executeQuery("update" + " " +
							// tableName + " " + "set status = 0 where userid !=
							// 1 and userid != 10 ");

							int li_user = 0;
							int li_level = 27;
							// Consulta tabla para validar que el registro
							// exista
							ResultSet rsRBS = stRBS.executeQuery("SELECT count(1) as numOfRows FROM " + tableName
									+ " WHERE username = '" + uemail + "'");
							bFound = false;
							while (rsRBS.next()) {
								if (rsRBS.getInt("numOfRows") > 0)
									bFound = true;
							}

							// Si encontro registros disponibles
							if (!bFound) {
								// Inserta registro de users
								String sqlX = "INSERT INTO users VALUES (null, null, '" + uemail
										+ "', 'Usuario', 'Demostracion', null, nextval('users_seq'), null, 'Espa�ol', 2, 1, 1, 1, 1, 1, 1, 1, 1)";
								PreparedStatement pst = connect.prepareStatement(sqlX, new String[] { "userid" });

								int affectedRows = pst.executeUpdate();
								System.out.println("Rows affected:" + affectedRows);
								if (affectedRows == 0) {
									throw new SQLException("Creating user failed, no rows affected.");
								}
								rsRBS = pst.getGeneratedKeys();
								boolean bCreated = false;
								// Define variables para captar un registro
								// disponible
								li_user = 0;
								if (affectedRows > 0 && rsRBS.next()) {
									bCreated = true;
									li_user = rsRBS.getInt(1);
									System.out.println("---> user id: " + li_user);
								}
								if (bCreated) {
									// Inserta registro de acceso
									sqlX = "INSERT Into acceso VALUES ('" + li_level + "', E'" + password
											+ "', 1, " + li_user + ", '" + uemail + "')";
									System.out.println(sqlX);
									pst = connect.prepareStatement(sqlX, new String[] { "userid" });

									affectedRows = pst.executeUpdate();
									rsRBS = pst.getGeneratedKeys();									
									// Define variables para captar un
									// registro disponible
									li_user = 0;
									while (rsRBS.next()) {
										bAccesCreated = true;
										li_user = rsRBS.getInt(1);
										System.out.println("---> user id: " + li_user);
									}

								}

							}
						}
						// ----------------------
						else {
							System.out.println("No se Activo.");
						}
					} else {
						System.out.println("El registro ya estaba activo.");
						bUpdated = true;
					}
				} else {
					System.out.println("La cuenta " + uemail + " NO existe.");
				}
			}
		} catch (Exception e) {
			System.err.println("e: " + e.getMessage());
			e.printStackTrace();
		}
		return bAccesCreated;
	}
	
	
	//
	// Permite comprobar si una cuenta de email esta activa
	//
	public boolean activateAcct(String uemail) {
		boolean bActived = false;
		System.out.println("*** activateAcct...");
		try {
			// Si la cuenta viene vacia...
			if (uemail.length() > 0) {

				System.err.println("Starting ...");
				Connection conn = getConnection();
				String tableName = "clientweb";

				Statement st = conn.createStatement();

				// Consulta tabla
				ResultSet rs = st
						.executeQuery("SELECT * FROM " + tableName + " WHERE email = '" + uemail + "' AND status_wf1 = 1");
				while (rs.next()) {
					String s = rs.getString(1);
					System.out.println("s = " + s);
					bActived = true;
				}
			}
		} catch (Exception e) {
			System.err.println("e: " + e.getMessage());
			System.err.println("e: " + e.toString());
			e.printStackTrace();
		}
		return bActived;
	}

	private Connection getConnection() {
		try {
			loadResourceProperties();
			Class.forName("org.postgresql.Driver");
			System.err.println("connecting... " + Functions.toStr(propiedades.get("URL.DB.CONNECTION")));
			return DriverManager.getConnection(Functions.toStr(propiedades.get("URL.DB.CONNECTION")),
					Functions.toStr(propiedades.get("URL.DB.USER")),
					Functions.toStr(propiedades.get("URL.DB.PASSWORD")));
		} catch (Exception e) {
			System.err.println("e: " + e.getMessage());
			System.err.println("e: " + e.toString());
		}
		return null;

	}

	private Connection getConnectionDemostration() {
		try {
			loadResourceProperties();
			Class.forName("org.postgresql.Driver");
			System.err.println("connecting... " + Functions.toStr(propiedades.get("URL.DB.CONNECTIONDEMO")));
			return DriverManager.getConnection(Functions.toStr(propiedades.get("URL.DB.CONNECTIONDEMO")),
					Functions.toStr(propiedades.get("URL.DB.USERDEMO")),
					Functions.toStr(propiedades.get("URL.DB.PASSWORDDEMO")));
		} catch (Exception e) {
			System.err.println("e: " + e.getMessage());
			System.err.println("e: " + e.toString());
		}
		return null;

	}

	private synchronized void loadResourceProperties() {
		if (propiedades == null) {
			propiedades = new Properties();
			try {
				propiedades.load(
						Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * segundo proyecto JAVAFORM
	 */

	public dbConnect(String uname, String uemail) {
		try {
			// Si alguno de los parametros viene vacio
			if (uname.length() > 0 && uemail.length() > 0) {

				System.out.println("*** DBCONNECT");
				System.out.println(uname);
				System.out.println(uemail);

				System.err.println("Starting ...");
				Connection conn = getConnection();
				String tableName = "clientweb";

				Statement st = conn.createStatement();
				ResultSet rs;

				// Consulta tabla para validar que el registro no exista
				rs = st.executeQuery("SELECT * FROM " + tableName + " WHERE email = '" + uemail + "'");
				boolean bExist = false;
				while (rs.next()) {
					String s = rs.getString(1);
					System.out.println("s = " + s);
					bExist = true;
				}

				if (!bExist) {

					System.out.println("La cuenta " + uemail + " no existe. Agregando...");

					// Inserta registro
					String sentence = "INSERT INTO " + tableName + " VALUES (nextval('client_seq'), '" + uname + "', '"
							+ uemail + "', null, null, null, null, null, null, 0, null, null, null, null, null, null)";
					// System.out.println( "** Insert " + sentence );

					int res = st.executeUpdate(sentence);
					if (res > 0) {
						System.out.println("Reg insertado...");
					}

				} else {
					System.out.println("La cuenta " + uemail + " ya existe.");
				}
			}
		} catch (Exception e) {
			System.err.println("e: " + e.getMessage());
			System.err.println("e: " + e.toString());
			e.printStackTrace();
		}
	}

	public static void main(String arg1, String arg2) { // , boolean bProcType)
														// {
		dbConnect main1 = new dbConnect(arg1, arg2);
	}

	public boolean dbConfirm2(String uemail, boolean bUpdated) {
		boolean bExist = false;
		System.out.println("*** dbConfirm...");
		try {
			// Si alguno de los parametros viene vacio
			if (uemail.length() > 0) {

				System.out.println("*** A punto de actualizar...");

				System.out.println("*** DBCONNECT");
				// System.out.println(uname);
				System.out.println(uemail);

				System.err.println("Starting ...");
				Connection conn = getConnection();
				String tableName = "clientweb";

				Statement st = conn.createStatement();
				ResultSet rs;

				// Consulta tabla para validar que el registro no exista
				rs = st.executeQuery("SELECT * FROM " + tableName + " WHERE email = '" + uemail + "'");
				bExist = false;
				while (rs.next()) {
					String s = rs.getString(1);
					System.out.println("s = " + s);
					bExist = true;
				}

				// Si existe el registro
				if (bExist) {

					System.out.println("La cuenta " + uemail + " ya existe. Actualizando...");

					// Obtiene la fecha y hora actual
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					String strDate = dateFormat.format(date);
					System.out.println(strDate);

					// Busca registro a actualizar
					String sentence = "SELECT * FROM " + tableName + " WHERE email = '" + uemail + "' AND status_wf1 = 0";
					System.out.println("** select  " + sentence);
					boolean bActivate = false;
					rs = st.executeQuery(sentence);
					while (rs.next()) {
						String s = rs.getString(1);
						System.out.println("s = " + s);
						bActivate = true;
						System.out.println("Por actualizar");
					}

					// Si el registro no esta activado
					if (bActivate) {

						// Actualiza/Activa registro
						sentence = "UPDATE " + tableName + " SET status_wf1 = 1, activationdate = '" + date
								+ "' WHERE email = '" + uemail + "'";
						System.out.println("** Update " + sentence);
						int intResult = st.executeUpdate(sentence);
						System.out.println("** Update " + intResult);

						// Busca registro actualizado
						sentence = "SELECT * FROM " + tableName + " WHERE email = '" + uemail + "' AND status_wf1 = 1";
						System.out.println("** Update " + sentence);
						rs = st.executeQuery(sentence);
						while (rs.next()) {
							String s = rs.getString(1);
							System.out.println("s = " + s);
							bUpdated = true;
							System.out.println("Cuenta Activada exitosamente.");
						}
					} else {
						bUpdated = true;
					}
				} else {
					System.out.println("La cuenta " + uemail + " NO existe.");
					// bUpdated = false;
				}
			}
		} catch (Exception e) {
			System.err.println("e: " + e.getMessage());
			System.err.println("e: " + e.toString());
			e.printStackTrace();
		}
		return bUpdated;
	}

}
