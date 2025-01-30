package com.aj.controller;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aj.model.Menu;
import com.aj.model.Users;
import com.aj.service.AccessDbJAService;
import com.aj.service.JuiciosService;
import com.aj.service.UserService;
import com.aj.utility.UserDTO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ToolsController {
	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public UserService userService;

	@SuppressWarnings("rawtypes")
	@Autowired
	public AccessDbJAService dao;

	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static final int ROLE_SYSADMIN= 1, ROLE_CJADMIN = 2, ROLE_FIRMADMIN=3;

	/** Muestra un listado de elementos a importar por archivos ".CSV":
		1er. parámetro		Número consecutio.
		2do. parámetro		Cadena de caracteres con dos valores separados por comas:
				Valor 1:	Texto que mostrará en el lístado.
				Valor 2:	Texto correspondiente al nombre de la tabla por cada Model___.java (case sentitive).	*/
	@RequestMapping(value = "/linkToExternal")
	public @ResponseBody Map<Integer, Object> linkToExternal(HttpServletRequest req, HttpServletResponse res) {
		Map<Integer, Object> ext = new HashMap<Integer, Object>();
		ext.put(0, "Países,Paises");
		ext.put(1, "Estados,Estados");
		ext.put(2, "CustomColumns,CustomColumns");
		ext.put(3, "Circuitos Jurisdiccionales,Circuitos");
		ext.put(4, "Órganos jurisdiccionales,Organos");
		ext.put(5, "Regiones,Regiones");
		ext.put(6, "Firmas,Companies");
		ext.put(7, "Columnas relacionadas,RelatedColumns");
		return ext;
	}

	/** Obtiene los nombres de las tablas */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTList")
	public Object[] getTList(HttpServletRequest req, HttpServletResponse res){
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String t = "SELECT table_name FROM INFORMATION_SCHEMA.TABLE WHERE table_schema='public' AND table_type='BASE TABLE'";
				Object data = dao.sqlHQLEntity(t, null);
				return new Object[] {data};
			}
		return null;
	}

	/** Obtiene como encabezados, los nombres de la columna de la tabla.	*/
	@RequestMapping(value = "/getHeadersExt")
	public @ResponseBody List<String> getHeadersExt(HttpServletRequest req, HttpServletResponse res) {
		String tablename = (req.getParameter("forheader") == null) ? "" : req.getParameter("forheader").trim();
		List<String> list = juiciosService.getTableColumnNames(tablename,"");
		return list;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/importCatalogs")
	public @ResponseBody ModelAndView importFiles(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int role = userDto.getRole();

				// Obtiene los privilegios del módulo
				@SuppressWarnings("rawtypes")
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "importCatalogs");
				Menu menu = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				if(role!=ROLE_SYSADMIN && role!=ROLE_CJADMIN)
					return new ModelAndView("login");

				Map<Integer, Object> cat = new HashMap<Integer, Object>();
				/** "cat.put"	Deberá tener la siguiente sintaxis:
								cat.put(indice, "Texto-a-mostrar, Nombre-del-Model")
				Nota: Cualquier opción añadida deberá ser también incluida en
					importCatalogs.js -> function startImport() 	*/
				cat.put(0, "Países,Paises");
				cat.put(1, "Estados,Estados");
				cat.put(2, "Ciudades,Ciudades");
				cat.put(3, "Juzgados,Juzgados");
				cat.put(4, "Tribunales colegiados,TribunalColegiado");
				cat.put(5, "Tribunales unitarios,TribunalUnitario");
				forModel.put("catalog", cat);
				return new ModelAndView("importCatalogs", forModel);
			}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/getHeaders")
	public @ResponseBody List<String> getHeaders(HttpServletRequest req, HttpServletResponse res) {
		String tablename = (req.getParameter("forheader") == null) ? "" : req.getParameter("forheader").trim();
		List<String> list = juiciosService.getTableColumnNames(tablename,"1");
		return list;
	}

	/** Obtiene los nombres de columnas para ser muestras como encabezados.	*/
	@RequestMapping(value = "/getListInfo")
	public @ResponseBody List<?> getListInfo(HttpServletRequest req, HttpServletResponse res) {
		String tablename = (req.getParameter("catalog") == null) ? "" : req.getParameter("catalog").trim(),
			column1 = (req.getParameter("val") == null) ? "" : req.getParameter("val").trim(),
			column2 = (req.getParameter("info") == null) ? "" : req.getParameter("info").trim();
		
		//NOTE:	El intento al acceso "users", anulará cualquier consulta.
//FIXME		if(tablename.equals("users"))return null;
				
		String query = "SELECT " + column1 + " FROM " + tablename;
		if (!column1.equals(column2))
			query = "SELECT " + column1 + ", " + column2 + " FROM " + tablename + " ORDER BY " + column2;
		List<?> list = juiciosService.getInfo(query);
		return list;
	}

	@RequestMapping(value = "/example")
	public ModelAndView example(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP_1.1.
				res.setHeader("Pragma", "no-cache");// HTTP1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if(userDto.getRole()!=ROLE_SYSADMIN)
					return new ModelAndView("login");
			}
		return new ModelAndView("example");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/testing")
	public @ResponseBody Object[] testing(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String query = req.getParameter("mytext") == null ? "" : req.getParameter("mytext").trim();
		UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
		Map<String, String> info = new HashMap<String, String>();

//TODO ******************** //NOTE: El siguiente bloque limita el acceso a consultas de "Users".
/*		if(userDto.getRole()!=ROLE_SYSADMIN || query.indexOf("Users")>=0){
			Map<String, Object> data = new HashMap<String, Object>();
			info.put("Port 8080","Ok");
			data.put("0",info);
			return new Object[] {data};
		}
*/
// TODO: El siguiente bloque es para emular un usuario en específico.
/*int testUser = 176;
userDto = switchUserDTO(testUser);
*/
		query=query.replaceAll(";$","");
		if(query.indexOf("retrieve logs")>=0){	//Ej: "retrieve logs aaaammdd"
			String[] tmp = query.split(" ");
			String day = tmp[tmp.length - 1];
			String path = System.getProperty("catalina.base") + "/logs/"
				+ "localhost_access_log." + day + ".txt";
//System.out.println(query.indexOf("retrieve logs="+path));
			Object abc = locateLogFile(path);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("0",abc);
//${catalina.home}/logs/myapp.log
/*			String[] tmp = query.split(" ");
			String day = tmp[tmp.length];
			String path = System.getProperty("catalina.base") + "/logs/"
				+ "localhost_access_log." + day + ".txt";
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, String> info = new HashMap<String, String>();
			//	catalina.out
			//   instance.log
			//  access log
			//  https://stackoverflow.com/questions/38517475/tomcat-logging-configuration
			info.put("0",abc);
			data.put("0",info);*/
			return new Object[] {};
		}else if((query.toLowerCase()).indexOf("update ")>=0){
			Map<String, Object> data = new HashMap<String, Object>();
			String x = "Port 8081";
			if((query.toLowerCase().indexOf(" where ")>0)){
				int row = dao.updateSqlNative(query, null);
				x += ":" + row;
			}
			info.put(x,"Ok");
			data.put("0",info);
			return new Object[] {data};
		}else{
			List<?> data = dao.sqlHQL(query, null);
			return new Object[] {data};
		}
	}

	public static File locateLogFile( final String prefixToMatch ) {
	    File result = null;
	    Handler[] handlers = LogManager.getLogManager().getLogger( "" ).getHandlers();
	    try {
	        for( Handler handler : handlers ) {
	            Field directoryField;
	            Field prefixField;
	            try {
	                //These are private fields in the juli FileHandler class
	                directoryField = handler.getClass().getDeclaredField( "directory" );
	                prefixField = handler.getClass().getDeclaredField( "prefix" );
	                directoryField.setAccessible( true );
	                prefixField.setAccessible( true );
	            } catch( NoSuchFieldException e ) {
	                continue;
	            }

	            String directory = (String)directoryField.get( handler );
	            if( prefixToMatch.equals( prefixField.get( handler ) ) ) {
	                File logDirectory = new File(  directory );
	                File[] logFiles = logDirectory.listFiles( new FileFilter() {
	                    public boolean accept( File pathname ) {
	                        return pathname.getName().startsWith( prefixToMatch );
	                    }
	                } );
	                if( logFiles.length == 0 ) continue;
	                Arrays.sort( logFiles );
	                result = logFiles[ logFiles.length - 1 ];
	                break;
	            }
	        }
	    } catch( IllegalAccessException e ) {
	        System.err.println("WARNING: Couldn't get log file " + e );
	    }
	    return result;
	}

	/** Cambia temporalmente la sesión emulando otro usuario.
	@param	userAsId	Id del usuario a emular.	*/
	public UserDTO switchUserDTO(int userAsId) {
		UserDTO usrDto = new UserDTO();
		List<Users> newUserTmp = (List<Users>) userService.getAll("FROM Users WHERE id=" + userAsId);
		usrDto.setAddress(newUserTmp.get(0).getAddress());
		usrDto.setId((long) newUserTmp.get(0).getId());
		usrDto.setCellphone(newUserTmp.get(0).getCellphone());
		usrDto.setCompanyid(newUserTmp.get(0).getCompanyid());
		usrDto.setCurrency(newUserTmp.get(0).getCurrency());
		usrDto.setEmail(newUserTmp.get(0).getEmail());
		usrDto.setFirst_name(newUserTmp.get(0).getFirst_name());
		usrDto.setLanguage(newUserTmp.get(0).getLanguage());
		usrDto.setLast_name(newUserTmp.get(0).getLast_name());
		usrDto.setLinkedclientid(newUserTmp.get(0).getLinkedclientid());
		usrDto.setPhone(newUserTmp.get(0).getPhone());
		usrDto.setPhoto_name(newUserTmp.get(0).getPhoto_name());
		usrDto.setRole(newUserTmp.get(0).getRole());
		usrDto.setStatus(newUserTmp.get(0).getStatus());
		usrDto.setUsername(newUserTmp.get(0).getUsername());
		usrDto.setUsertype(newUserTmp.get(0).getUsertype());
		return usrDto;
	}
}