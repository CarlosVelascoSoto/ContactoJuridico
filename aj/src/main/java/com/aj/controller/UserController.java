package com.aj.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aj.model.Ciudades;
import com.aj.model.Clients;
import com.aj.model.Companies;
import com.aj.model.Company;
import com.aj.model.Companyclients;
import com.aj.model.ECalendar;
import com.aj.model.Estados;
import com.aj.model.Menu;
import com.aj.model.Menuprivileges;
import com.aj.model.OpportunityAux;
import com.aj.model.Paises;
import com.aj.model.Privileges;
import com.aj.model.Roles;
import com.aj.model.Smtpmail;
import com.aj.model.Users;
import com.aj.service.AccessDbJAService;
import com.aj.service.CiudadesService;
import com.aj.service.ClientsService;
import com.aj.service.CompaniesService;
import com.aj.service.CompanyclientsService;
import com.aj.service.ECalendarService;
import com.aj.service.EstadosService;
import com.aj.service.MenuService;
import com.aj.service.PaisesService;
import com.aj.service.PrivilegesService;
import com.aj.service.RolesService;
import com.aj.service.UserService;
import com.aj.utility.Functions;
import com.aj.utility.ScriptCommon;
import com.aj.utility.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@ComponentScan("com.aj")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	RolesService rolesService;

	@Autowired
	UserService userService;
	
	@Autowired
	private CommonController commonsCtrll;

	@Autowired
	private DataSource dataSource;

	@Autowired
	public AccessDbJAService daoJaService;
	
	@Autowired
	private NotificationsController notificationsCtrll;

	@Autowired
	private PrivilegesService privilegesService;

	@Autowired
	private MenuService menuService;

	@Autowired
	public CompanyclientsService companyclientsService;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	private ECalendarService eCalendarService;

	@Autowired
	public CompaniesService companiesService;

	@Autowired
	public PaisesService paisesService;

	@Autowired
	public EstadosService estadosService;

	@Autowired
	public CiudadesService ciudadesService;

	@Autowired
	public UserService usersService;

	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static final int ROLE_SYSADMIN= 1, ROLE_CJADMIN = 2, ROLE_FIRMADMIN=3;

	@RequestMapping(value = "/home")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP1.1.
				response.setHeader("Pragma", "no-cache");// HTTP 1.0.
				response.setDateHeader("Expires", 0);
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");

				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if(userDto==null)
					return new ModelAndView("login");
				int role = userDto.getRole(),userid=(int) userDto.getId(), clientid = 0, ccid = 0;
				boolean isLawyer = userDto.getUsertype()==null ? true : userDto.getUsertype()==1 ? false : true;
				Map<String, Object> forModel = new HashMap<String, Object>();
				List<Companies> companies = null;
				String whereClause = "", whereClientClause = "", clienteids = "", ccids = "";
				if ((role==ROLE_SYSADMIN)||(role==ROLE_CJADMIN))
					companies = companiesService.getAll("FROM Companies");
				else
					whereClause = " WHERE companyid=" + userDto.getCompanyid();
				if(isLawyer){
					List<Companyclients> cclient = companyclientsService
						.getAll("SELECT DISTINCT(clientid), companyclientid FROM Companyclients" + whereClause);
					if(cclient.size()>0){
						Object[] tmp = cclient.toArray();
						for (Object cdata:tmp){
							Object[] obj= (Object[]) cdata;
							clienteids += (obj[0]==null)?0:(int) obj[0] + ",";
							ccids += (obj[1]==null)?0:(int) obj[1] + ",";
						}
						clienteids = clienteids.replaceAll(",$","");
						ccids = ccids.replaceAll(",$","");
					}
		  			
/* OK v.0			List<Companyclients> cclient = companyclientsService
						.getAll("SELECT DISTINCT(clientid) FROM Companyclients" + whereClause);
					String ids = "";
					for (int i = 0; i < cclient.size(); i++)
						ids += cclient.get(i) + ",";
					ids = ids.replaceAll(".$", "");
					whereClientClause=(Functions.isEmpty(ids))?"=0":" IN(" + ids + ")";*/

					whereClientClause=(Functions.isEmpty(clienteids))?"=0":" IN(" + clienteids + ")";
				}else{
					ccid=userDto.getLinkedclientid();	// Determina si el usuario es un cliente
					clientid=userDto.getLinkedclientid();
					whereClientClause+="=" + clientid;
				}
				
				List<Clients> data = clientsService.getAll("FROM Clients WHERE clientid"
					+ whereClientClause + " ORDER BY client ASC, city ASC");
				forModel.put("client", data);

				/*List<ECalendar> totalEvent=eCalendarService
					.getAll("SELECT count(*) FROM ECalendar WHERE userid IN("+userid+") AND dateini >= now()");
				forModel.put("schedules", totalEvent.get(0));*/
				String query = "FROM ECalendar WHERE userid IN("+userid+") AND dateini >= now()";
				List<ECalendar> events=eCalendarService.getAll(query);
				forModel.put("schedules", events);
				forModel.put("companies", companies);
				forModel.put("role", role);
				forModel.put("first_name",userDto.getFirst_name());
				forModel.put("last_name", userDto.getLast_name());
				forModel.put("companyname", userDto.getCompany_name());
				String userType=(userDto.getUsertype()==null?"0":Functions.toStr(userDto.getUsertype()));
				forModel.put("usertype", userType);
				forModel.put("ctid", userDto.getLinkedclientid());

				HashMap<String, String> trials = commonsCtrll.getAllTrials(request,ccid,0);
				forModel.put("trials", trials.get("juiciosid"));

//				Map<Integer, Object> notifications;
/*Map<Long, Map<String, Object>> notifications = notificationsCtrll.getHomeNotifications(request);
forModel.put("notifications", notifications);
				return new ModelAndView("home", forModel);*/
				if(isLawyer)
					return new ModelAndView("home", forModel);		//Vista para Abogados
				else
					return new ModelAndView("mainhome", forModel);	//Vista para Clientes
			}
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("isLogin", "no");
		request.getSession().setAttribute("UserDTO", null);
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP_1.1.
		response.setHeader("Pragma", "no-cache");// HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		Cookie cookUser = new Cookie("userName", null);
		cookUser.setPath(StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
		cookUser.setMaxAge(0);
		response.addCookie(cookUser);
		Cookie cookPass = new Cookie("passWord", null);
		cookPass.setPath(StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
		cookPass.setMaxAge(0);
		response.addCookie(cookPass);
		request.getSession().invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/getAllCompanyList", method = { RequestMethod.GET, RequestMethod.POST })
	public void getAllCompanyList(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Company> companies = userService.getAllCompanyList();
			Gson gson = new Gson();
			String data = gson.toJson(companies);
			JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
			response.setContentType("application/json");
			response.getWriter().print(jsonArray);
		} catch (Exception ex) {
			logger.error("Exception in getCompanyNames()::" + ex.getMessage());
		}
	}

	@RequestMapping(value = "/addnewuser", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addnewuser(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				Map<String, Object> forModel=new HashMap<String, Object>();
				int role = userDto.getRole();
				String whereClause = "";	//Sin resticciones para SysAdmin
				if(role != ROLE_SYSADMIN){
					if(role == ROLE_CJADMIN)
						whereClause = "WHERE role <> " + ROLE_SYSADMIN;
					else if(role == ROLE_FIRMADMIN)
						whereClause = "WHERE role NOT IN(" + ROLE_SYSADMIN + "," + ROLE_CJADMIN + ") AND companyid=" + userDto.getCompanyid();
					else
						whereClause = "WHERE role NOT IN(" + ROLE_SYSADMIN + "," + ROLE_CJADMIN + "," + ROLE_FIRMADMIN + "," + role
							+ ") AND companyid=" + userDto.getCompanyid();
				}
				List<Users> listUser=userService.getAll("FROM Users " + whereClause + " ORDER BY first_name ASC, last_name ASC");
				forModel.put("users", listUser);

				whereClause=whereClause.replaceAll(" role "," roleid ");
				List<Roles> roleList=rolesService.getAll("FROM Roles " + whereClause + " ORDER BY roleid ASC");
				forModel.put("roleList", roleList);

				List<Companies> compList = daoJaService.getFromJAHibernate(Companies.class);
				forModel.put("compList", compList);
				forModel.put("userRole", role);
				forModel.put("userCompany", userDto.getCompanyid());
				forModel.put("userRole", role);
				return new ModelAndView("addnewuser", forModel);
			}
		}
		return new ModelAndView("login");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/adduser")
	public @ResponseBody void adduser(HttpServletRequest req, HttpServletResponse res){
		HttpSession sess = req.getSession(false);
		String resp = "False";
		validateuser:{
		if(sess != null){
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int status = req.getParameter("status") == null ? 0 : Functions.toInt(req.getParameter("status").trim()),
					firmid = (req.getParameter("addc1") == null || req.getParameter("addc1").equals("")) ? 0
						: Functions.toInt(req.getParameter("addc1").trim()),
					uprole = (req.getParameter("isaddr") == null || req.getParameter("isaddr").equals("")) ? 0
						: Functions.toInt(req.getParameter("isaddr").trim()),
					zipcode=req.getParameter("zipcode")==null?0:Functions.toInt(req.getParameter("zipcode").trim()),
					usertype=req.getParameter("usertype")==null?0:Functions.toInt(req.getParameter("usertype").trim()),
					usrCC=req.getParameter("usrCC")==null?0:Functions.toInt(req.getParameter("usrCC").trim());
				String fullfilename = "",
					uname = req.getParameter("uname") == null ? "" : req.getParameter("uname").trim(),
					fname = req.getParameter("firstName") == null ? "" : req.getParameter("firstName").trim(),
					lname = req.getParameter("lastName") == null ? "" : req.getParameter("lastName").trim(),
					pass = req.getParameter("pass") == null ? "" : ScriptCommon.encode(req.getParameter("pass").trim()),
					email = req.getParameter("email") == null ? "" : req.getParameter("email").trim(),
					phone=req.getParameter("phone") ==null?"" : req.getParameter("phone").trim(),
					cellphone=req.getParameter("cellphone") ==null?"" : req.getParameter("cellphone").trim(),
					address=req.getParameter("address") ==null?"" : req.getParameter("address").trim(),
					country=req.getParameter("country")==null?"":req.getParameter("country").trim(),
					state=req.getParameter("state")==null?"":req.getParameter("state").trim(),
					city=req.getParameter("city")==null?"":req.getParameter("city").trim(),
					lang=req.getParameter("lang")==null?"es":req.getParameter("lang").trim();
				UserDTO userDto = (UserDTO) req.getSession().getAttribute("UserDTO");
				Timestamp updated=new Timestamp(System.currentTimeMillis());
				Users usr = new Users();
				if(firmid < 1){
					if(userDto.getRole()==ROLE_SYSADMIN || userDto.getRole()==ROLE_CJADMIN){
						resp = "err_firm_not_found";
						break validateuser;
					}else{
						firmid = userDto.getCompanyid();
					}
				}
				// Valida que el email y nombre de usuario no se repitan
				List<Users> tmpusr = (List<Users>) daoJaService
					.getFromJA("select id FROM Users WHERE username='" + uname + "'",Users.class);
				if (tmpusr.size() > 0){
					resp = "err_user_exists";
					break validateuser;
				}
				tmpusr = (List<Users>) daoJaService.getFromJA("select id FROM Users WHERE email='"
					+ email + "'",Users.class);
				if (tmpusr.size() > 0){
					resp = "err_email_exists";
					break validateuser;
				}
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);

				// Guardamos archivos relacionados con el registro
				String[] nameFiles = null;
				Enumeration enumeration = req.getParameterNames();
				while(enumeration.hasMoreElements()){
					String parameterName = (String) enumeration.nextElement();
					if(parameterName.contains("fileuploadx_")&& !Functions.isEmpty(req.getParameter(parameterName))){
						nameFiles=(nameFiles==null)?new String[1]
							:(String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
						nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
						break; // Sólo almacena el primer archivo.
					}
				}
				// Mueve archivos a destino e inserta rows en db
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator()
					+"images"+ FileSystems.getDefault().getSeparator()
					+"users", true);
				destinationPath = Functions.addPath(destinationPath, "" + firmid, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				if(paths != null)
					for(File file : paths){
						fullfilename=destinationPath + "/" + file.getName();
						break; // Sólo almacena el primer archivo.
					}

				// Validación de ciudad, país y estado
				if(Functions.isEmpty(state) && !Functions.isEmpty(city)){
					List<Ciudades> xtmp = ciudadesService.getAll("FROM Ciudades WHERE ciudadid="+city);
					state = Functions.toStr(xtmp.get(0).getEstadoid());
				}
				if(Functions.isEmpty(country) && !Functions.isEmpty(state)){
					List<Estados> xtmp = estadosService.getAll("FROM Estados WHERE estadoid="+state);
					country = Functions.toStr(xtmp.get(0).getPaisid());
				}

				List<Companies> firminfo = (List<Companies>) daoJaService.
					getFromJA("SELECT company FROM Companies WHERE companyid=" + firmid, Companies.class);
				Timestamp created = new Timestamp(System.currentTimeMillis());
				usr.setRole(uprole);
				usr.setUsername(uname);
				usr.setPassword(pass);
				usr.setFirst_name(fname);
				usr.setLast_name(lname);
				usr.setCompany_name(firminfo.get(0).getCompany());
				usr.setPhoto_name(fullfilename);
				usr.setEmail(email);
				usr.setPhone(phone);
				usr.setAddress(address);
				usr.setCity(city);
				usr.setState(state);
				usr.setCountry(country);
				usr.setStatus(status);
				usr.setCreated(created);
				usr.setUpdated(created);
				usr.setLanguage(lang);
				//usr.setCurrency("");
				usr.setCompanyid(firmid);
				usr.setZipcode(zipcode);
				usr.setCellphone(cellphone);
				usr.setUsertype(usertype);
				if(usertype==1)	// 1=Usuario de tipo cliente; 0 ó null=Usuario de tipo abogado
					usr.setLinkedclientid(usrCC);
				Long succ = userService.addNewUser(usr);
				if (succ != null && succ > 0){
					try{
						resp = "True";
						FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
					}catch (IOException ex){
						System.out.println("Exception in updateuser(): " + ex.getMessage());
						resp = "err_record_no_saved";
					}
				}else{
					resp="err_record_no_saved";
				}
			}
		}}
		try{
			res.getWriter().write(resp);
		}catch(IOException e){
			e.printStackTrace();
		}
//TODO Analizar que un usuario no autorizado pueda auto-agregarse permisos no autorizados.
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createnewuser")
	public @ResponseBody void createnewuser(HttpServletRequest request, HttpServletResponse response) {
		String resp = "", uname = request.getParameter("uname") == null ? "" : request.getParameter("uname").trim(),
			fname = request.getParameter("fname") == null ? "" : request.getParameter("fname").trim(),
			lname = request.getParameter("lname") == null ? "" : request.getParameter("lname").trim(),
			pass = request.getParameter("pass") == null ? "" : request.getParameter("pass").trim(),
			email = request.getParameter("email") == null ? "" : request.getParameter("email").trim(),
			firmname = request.getParameter("firmname") == null ? "" : request.getParameter("firmname").trim();
		if (Functions.isEmpty(fname))
			resp = "err_enter_firstname";
		else if (Functions.isEmpty(lname))
			resp = "err_enter_lastname";
		else if (uname.equals(pass))
			resp = "err_not_same_user_pass";
		else if (Functions.isEmpty(pass))
			resp = "err_enter_password";
		else if (Functions.isEmpty(email))
			resp = "err_enter_correct_email";
		else if (Functions.isEmpty(firmname))
			resp = "err_enter_firm";

		List<Users> lusers = (List<Users>) daoJaService
			.getFromJA("SELECT username, email FROM Users WHERE UPPER(username)='"
			+ uname.toUpperCase() + "' OR UPPER(email)='" + email.toUpperCase() + "'", Users.class);
		if (lusers != null && lusers.size() > 0){
			if ((lusers.get(0).getEmail()).toUpperCase().equals(email.toUpperCase()))
				resp = "err_email_exists";
			else if ((lusers.get(0).getUsername().toUpperCase()).equals(uname.toUpperCase()))
				resp = "err_user_exists";
		}else{
			List<Companies> existsCompany = companiesService
				.getAll("FROM Companies WHERE UPPER(company)='" + firmname.toUpperCase() + "'");
			if (existsCompany.size()>0)
				resp = "err_firm_exists";
		}
		if (Functions.isEmpty(resp)){
			long companyid = 0;
			Companies company = new Companies();
			company.setCompany(firmname);
			company.setAddress1("Av. ABC No.123");
			company.setEmail(email);
			company.setRfc("XAXX010101000");
			company.setShortname(firmname.replaceAll("(\\S*)\\s.*","$1"));
			companyid = companiesService.addNewCompany(company);
			Users user = new Users();
			Timestamp created = new Timestamp(System.currentTimeMillis());
			user.setCreated(created);
			user.setFirst_name(fname);
			user.setLast_name(lname);
			user.setUsername(uname);
			user.setPassword(ScriptCommon.encode(pass));
			user.setEmail(email);
			user.setRole(ROLE_FIRMADMIN);	// Nuevos usuarios son Administradores de Firmas
			user.setStatus(1);
			user.setCompanyid(Functions.toInt(companyid+""));
			user.setLanguage("es");
			Long succ = userService.addNewUser(user);
			resp = (succ != null && succ > 0) ? "msg_user_saved_successfully" : "err_user_exists";
		}
		try{
			response.getWriter().write(resp);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteuser")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int userId = (request.getParameter("userId") == null || request.getParameter("userId").equals("")) ? 0
						: Functions.toInt(request.getParameter("userId").trim());
				userService.deleteUser(userId);
			}
		}
	}

	@RequestMapping(value = "/edituser")
	@ResponseBody
	public void editUserPopup(HttpServletRequest request, HttpServletResponse response) {
		Users user = new Users();
		long userId = request.getParameter("userId") == null ? 0
			: Functions.toLong(request.getParameter("userId").trim());
		try{
			if(userId==0){
				response.getWriter().write("false");
			}else{
				user=this.userService.getUserById(userId);
				response.getWriter().write("true" + "$$" + (user.getUsername() == null ? "" : user.getUsername()) + "$$"
					+ (user.getEmail() == null ? "" : user.getEmail()) + "$$"
					+ user.getId() + "$$"
					+ (user.getPassword() == null ? "" : ScriptCommon.decode(user.getPassword())) + "$$"
					+ user.getCreated() + "$$"
					+ user.getRole() + "$$"
					+ (user.getFirst_name() == null ? "" : user.getFirst_name()) + "$$"
					+ (user.getLast_name() == null ? "" : user.getLast_name()) + "$$"
					+ (user.getCompany_name() == null ? "" : user.getCompany_name()) + "$$"
					+ (user.getPhone() == null ? "" : user.getPhone()) + "$$"
					+ (user.getAddress() == null ? "" : user.getAddress()) + "$$"
					+ (user.getCity() == null ? "" : user.getCity()) + "$$"
					+ (user.getState() == null ? "" : user.getState()) + "$$"
					+ (user.getCountry() == null ? "" : user.getCountry()) + "$$"
					+ user.getStatus() + "$$"
					+ user.getCompanyid() + "$$");
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
			}
		} catch (Exception e) {
			logger.error("Exception in UserController [editUserPopup()]::" + e.getMessage());
		}
	}

	@RequestMapping(value = "/editUser2")
	public @ResponseBody Object[] editUser2(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				long userid = req.getParameter("userId") == null ? 0
					: Functions.toLong(req.getParameter("userId").trim());
				Map<String, Object> info = new HashMap<String, Object>();
				UserDTO userDto = (UserDTO) req.getSession().getAttribute("UserDTO");
				int roleid = userDto.getRole();
				String firm = roleid==ROLE_SYSADMIN||roleid==ROLE_CJADMIN?""
					:" AND companyid=" + userDto.getCompanyid();
				List<Users> userinfo = usersService.getAll("FROM Users WHERE id="
					+ userid + firm);
				String query="SELECT c.client FROM Clients AS c"
					+ " LEFT JOIN Companyclients AS cc ON cc.clientid=c.clientid"
					+ " WHERE companyclientid=" + userinfo.get(0).getLinkedclientid() + firm;
				List<Clients> c = clientsService.getAll(query);
				info.put("info", userinfo);
				info.put("psw", ScriptCommon.decode(userinfo.get(0).getPassword()));
				info.put("clientName",(Functions.toStr(c)).replaceAll("^\\[(.*)]$","$1"));
				return new Object[] { info };
			}
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/addNewCompany")
	public @ResponseBody void addNewCompany(HttpServletRequest request, HttpServletResponse response) {
		String error = "";
		Company newcompany = new Company();
		String companyname = request.getParameter("company") == null ? "" : request.getParameter("company").trim();
//		String rfc = (request.getParameter("rfc") == null) ? "" : request.getParameter("rfc").trim();
		newcompany.setName(companyname);
		try {
			Integer succ = daoJaService.save(newcompany);
			if (succ > 0)
				response.getWriter().write("" + succ);
			else
				response.getWriter().write("false");
		} catch (Exception e) {
			if (e.getMessage().contains("unique_rfc"))
				error = "rfc_duplicado";
			else
				error = "false";
			logger.error("Exception in addNewCompany()" + e.getMessage());
		}
	}

	@RequestMapping(value = "/findRfc")
	public void findRfc(HttpServletRequest request, HttpServletResponse response) {
		try {
			String rfc = (request.getParameter("rfc") == null) ? "" : request.getParameter("rfc").trim();
			@SuppressWarnings("unchecked")
			List<OpportunityAux> oportunidad = daoJaService
				.getFromJA("select id, rfc FROM companies where rfc = '" + rfc + "'", OpportunityAux.class);
			if (oportunidad.size() > 0)
				response.getWriter().print("true");
			else
				response.getWriter().print("false");
		} catch (Exception ex) {
			logger.error("Errores: " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/profile")
	public ModelAndView getProfilePage(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP_1.1.
				response.setHeader("Pragma", "no-cache");// HTTP 1.0.
				response.setDateHeader("Expires", 0);
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				UserDTO userDto = (UserDTO) request.getSession().getAttribute("UserDTO");
				int role = userDto.getRole();
				// Obtiene los privilegios del módulo, carga menu para verificar permisos
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "profile");
				Menu menu = (Menu)daoJaService.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);
				forModel.put("profile", userService.getProfile(userDto.getEmail()));
				
				@SuppressWarnings("unchecked")
				List<Users> lusers = (List<Users>) daoJaService
					.getFromJA("select country, state, city FROM Users WHERE id=" + userDto.getId(), Users.class);
				String country = lusers.get(0).getCountry(), state = lusers.get(0).getState(),
					city = lusers.get(0).getCity();
				if(Functions.isNumeric(country)){
					List<Paises> info = paisesService.getAll("FROM Paises WHERE paisid=" + country);
					country = info.get(0).getpais();
				}
				if(Functions.isNumeric(state)){
					List<Estados> info = estadosService.getAll("FROM Estados WHERE estadoid=" + state);
					state = info.get(0).getEstado();
				}
				if(Functions.isNumeric(city)){
					List<Ciudades> info = ciudadesService.getAll("FROM Ciudades WHERE ciudadid=" + city);
					city = info.get(0).getCiudad();
				}
				forModel.put("country", country);
				forModel.put("state", state);
				forModel.put("city", city);
				return new ModelAndView("profile", forModel);
			}
		}
		return new ModelAndView("login");
	}

	@ModelAttribute("roles")
	public List<Roles> initializeProfiles() {
		return rolesService.getAll("FROM Roles");
	}

	@RequestMapping(value = "/editprofile")
	public ModelAndView editprofile(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP1.1.
				response.setHeader("Pragma", "no-cache");// HTTP 1.0.
				response.setDateHeader("Expires", 0);
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				// Obtiene los privilegios del módulo
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "editprofile");
				Menu menu = (Menu)daoJaService.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<Users> lusers = (List<Users>) daoJaService
					.getFromJA("select country, state, city FROM Users WHERE id=" + userDto.getId(), Users.class);
				String country = lusers.get(0).getCountry(), state = lusers.get(0).getState(),
					city = lusers.get(0).getCity();
				if(Functions.isNumeric(country)){
					List<Paises> info = paisesService.getAll("FROM Paises WHERE paisid=" + country);
					country = info.get(0).getpais();
				}
				if(Functions.isNumeric(state)){
					List<Estados> info = estadosService.getAll("FROM Estados WHERE estadoid=" + state);
					state = info.get(0).getEstado();
				}
				if(Functions.isNumeric(city)){
					List<Ciudades> info = ciudadesService.getAll("FROM Ciudades WHERE ciudadid=" + city);
					city = info.get(0).getCiudad();
				}

				List<Roles> listRoles = rolesService.getAll("FROM Roles WHERE roleid=" + userDto.getRole());
				forModel.put("rolename", listRoles.get(0).getRolename());
				forModel.put("profile", this.userService.getProfile(userDto.getEmail()));
				forModel.put("country", country);
				forModel.put("state", state);
				forModel.put("city", city);
				return new ModelAndView("editprofile", forModel);
			}
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/updateuser")
	public void updateUser(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "False";
		validateuser:{
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				long userId = (req.getParameter("userId") == null || req.getParameter("userId").equals("0")) ? 0
					: Functions.toLong(req.getParameter("userId").trim());
				int status = req.getParameter("status") == null ? 0 : Functions.toInt(req.getParameter("status").trim()),
					firmid = (req.getParameter("edc1") == null || req.getParameter("edc1").equals("")) ? 0
						: Functions.toInt(req.getParameter("edc1").trim()),
					uprole = (req.getParameter("iseditr") == null || req.getParameter("iseditr").equals("")) ? 0
						: Functions.toInt(req.getParameter("iseditr").trim()),
					zipcode=req.getParameter("zipcode")==null?0:Functions.toInt(req.getParameter("zipcode").trim()),
					usertype=req.getParameter("usertype")==null?0:Functions.toInt(req.getParameter("usertype").trim()),
					usrCC=req.getParameter("usrCC")==null?0:Functions.toInt(req.getParameter("usrCC").trim());
				String fullfilename = "", lang = "",
					uname = req.getParameter("uname") == null ? "" : req.getParameter("uname").trim(),
					fname = req.getParameter("firstName") == null ? "" : req.getParameter("firstName").trim(),
					lname = req.getParameter("lastName") == null ? "" : req.getParameter("lastName").trim(),
					pass = req.getParameter("pass") == null ? "" : ScriptCommon.encode(req.getParameter("pass").trim()),
					email = req.getParameter("email") == null ? "" : req.getParameter("email").trim(),
					phone=req.getParameter("phone") ==null?"" : req.getParameter("phone").trim(),
					photo=req.getParameter("photo") ==null?"" : req.getParameter("photo").trim(),
					cellphone=req.getParameter("cellphone") ==null?"" : req.getParameter("cellphone").trim(),
					address=req.getParameter("address") ==null?"" : req.getParameter("address").trim(),
					country=req.getParameter("country")==null?"":req.getParameter("country").trim(),
					state=req.getParameter("state")==null?"":req.getParameter("state").trim(),
					city=req.getParameter("city")==null?"":req.getParameter("city").trim();
				List<Users> usrInfo = userService.getAll("FROM Users WHERE id=" + userId);
				UserDTO userDto = (UserDTO) req.getSession().getAttribute("UserDTO");
				if(firmid < 1)
					if(userDto.getRole()==ROLE_SYSADMIN || userDto.getRole()==ROLE_CJADMIN){
						resp = "err_firm_not_found";
						break validateuser;
					}else{
						firmid = userDto.getCompanyid();
					}
				// Valida que el email y nombre de usuario no se repitan
				List<Users> tmpusr = (List<Users>) daoJaService.getFromJA("select id FROM Users WHERE username='"
					+ uname + "' AND id!=" + userId, Users.class);
				if (tmpusr.size() > 0){
					resp = "err_user_exists";
					break validateuser;
				}
				tmpusr = (List<Users>) daoJaService.getFromJA("select id FROM Users WHERE email='"
					+ email + "' AND id!=" + userId, Users.class);
				if (tmpusr.size() > 0){
					resp = "err_email_exists";
					break validateuser;
				}

				Timestamp updated=new Timestamp(System.currentTimeMillis());
				Users usr = new Users();
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				if(Functions.isEmpty(photo)){
					// Guardamos archivos relacionados con el registro
					String[] nameFiles = null;
					Enumeration enumeration = req.getParameterNames();
					while(enumeration.hasMoreElements()){
						String parameterName = (String) enumeration.nextElement();
						if(parameterName.contains("fileuploadx_")&& !Functions.isEmpty(req.getParameter(parameterName))){
							nameFiles=(nameFiles==null)?new String[1]
								:(String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
							nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
							break; // Sólo almacena el primer archivo.
						}
					}
					// Mueve archivos a destino e inserta rows en db
					String destinationPath = Functions.addPath(rootPath,
						"doctos" + FileSystems.getDefault().getSeparator()
						+"images"+ FileSystems.getDefault().getSeparator()
						+"users", true);
					destinationPath = Functions.addPath(destinationPath, "" + firmid, true);
					File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
					if(paths != null)
						for(File file : paths){
							fullfilename=destinationPath + "/" + file.getName();
							break; // Sólo almacena el primer archivo.
						}
				}else{
					fullfilename=usrInfo.get(0).getPhoto_name();
				}

				// Validación de ciudad, país y estado
				if(Functions.isEmpty(state) && !Functions.isEmpty(city)){
					List<Ciudades> xtmp = ciudadesService.getAll("FROM Ciudades WHERE ciudadid="+city);
					state = Functions.toStr(xtmp.get(0).getEstadoid());
				}
				if(Functions.isEmpty(country) && !Functions.isEmpty(state)){
					List<Estados> xtmp = estadosService.getAll("FROM Estados WHERE estadoid="+state);
					country = Functions.toStr(xtmp.get(0).getPaisid());
				}
				List<Companies> firminfo = (List<Companies>) daoJaService.
					getFromJA("SELECT company FROM Companies WHERE companyid=" + firmid, Companies.class);
				lang=(Functions.isEmpty(usrInfo.get(0).getLanguage()))?"es":usrInfo.get(0).getLanguage();//Si lang=null, por default será "es"
				Date created = usrInfo.get(0).getCreated();
				usr.setId(userId);
				usr.setRole(uprole);
				usr.setUsername(uname);
				usr.setPassword(pass);
				usr.setFirst_name(fname);
				usr.setLast_name(lname);
				usr.setCompany_name(firminfo.get(0).getCompany());
				usr.setPhoto_name(fullfilename);
				usr.setEmail(email);
				usr.setPhone(phone);
				usr.setAddress(address);
				usr.setCity(city);
				usr.setState(state);
				usr.setCountry(country);
				usr.setStatus(status);
				usr.setCreated(created);
				usr.setUpdated(updated);
				usr.setLanguage(lang);
				usr.setCurrency(usrInfo.get(0).getCurrency());
				usr.setCompanyid(firmid);
				usr.setZipcode(zipcode);
				usr.setCellphone(cellphone);
				usr.setUsertype(usertype);

				if(usertype==1)	// 1=Usuario de tipo cliente; 0 ó null=Usuario de tipo abogado
					usr.setLinkedclientid(usrCC);
				userService.updateUserDetails(usr);
				try{
					resp = "True";
					FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
				}catch (IOException ex){
					System.out.println("Exception in updateuser(): " + ex.getMessage());
					resp = "err_record_no_saved";
				}
			}
		}}
		try{
			res.getWriter().write(resp);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/updateProfile")
	public void updateProfile(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_on_save";
		validateuser:{
		if(sess != null){
			if(sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				long userId = userDto.getId();
				int companyid = userDto.getCompanyid(),
					zipcode=req.getParameter("zipcode")==null?0:Functions.toInt(req.getParameter("zipcode").trim());
				String fullfilename = "",
					fname = req.getParameter("firstName")==null?"" : req.getParameter("firstName").trim(),
					lname = req.getParameter("lastName") ==null?"" : req.getParameter("lastName").trim(),
					phone=req.getParameter("phone") ==null?"" : req.getParameter("phone").trim(),
					photo=req.getParameter("photo") ==null?"" : req.getParameter("photo").trim(),
					cellphone=req.getParameter("cellphone") ==null?"" : req.getParameter("cellphone").trim(),
					address=req.getParameter("address") ==null?"" : req.getParameter("address").trim(),
					lang = req.getParameter("lang")==null?"" : req.getParameter("lang").trim(),
					country=req.getParameter("country")==null?"":req.getParameter("country").trim(),
					state=req.getParameter("state")==null?"":req.getParameter("state").trim(),
					city=req.getParameter("city")==null?"":req.getParameter("city").trim();
				/*List<Users> usrInfo = (List<Users>) daoJaService
					.getFromJA("SELECT id FROM Users WHERE id='" + userId + "'", Users.class);
				if(usrInfo != null && usrInfo.size() > 0 && usrInfo.get(0).getId() != userId){
					resp = "err_user_exists";
					break validateuser;
				}*/
				if(Functions.isEmpty(fname) || Functions.isEmpty(lname)){
					resp="err_enter_"+(Functions.isEmpty(fname)?"fir":"la")+"stname";
					break validateuser;
				}
/*				List<Users> usrInfo = daoJaService.getFromJA("SELECT id FROM Users WHERE id='"
					+ userDto.getId() + "'", Users.class);*/
				Users usr = new Users();
				List<Users> usrInfo = userService.getAll("FROM Users WHERE id=" + userId);

				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				if(Functions.isEmpty(photo) && photo.indexOf("doctos")>=0){
					// Guardamos archivos relacionados con el registro
					String[] nameFiles = null;
					Enumeration enumeration = req.getParameterNames();
					while(enumeration.hasMoreElements()){
						String parameterName = (String) enumeration.nextElement();
						if(parameterName.contains("fileuploadx_")&& !Functions.isEmpty(req.getParameter(parameterName))){
							nameFiles=(nameFiles==null)?new String[1]
								:(String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
							nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
							break; // Sólo almacena el primer archivo.
						}
					}
					// Mueve archivos a destino e inserta rows en db
					String destinationPath = Functions.addPath(rootPath,
						"doctos" + FileSystems.getDefault().getSeparator()
						+"images"+ FileSystems.getDefault().getSeparator()
						+"users", true);
					destinationPath = Functions.addPath(destinationPath, "" + companyid, true);
					File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
					if(paths != null)
						for(File file : paths){
							/*String[] filename = (file.getName()).split("\\.");
							String extFile = filename[filename.length - 1];
							File f = new File(file.getAbsolutePath());// Renombra el archivo por el id
							f.renameTo(new File(destinationPath + "/" + userId + "." + extFile));
							fullfilename=destinationPath + "/" + userId + "." + extFile;
							f.renameTo(new File(destinationPath + "/" + filename));*/
							fullfilename=destinationPath + "/" + file.getName();
							break; // Sólo almacena el primer archivo.
						}
				}else{
					fullfilename=usrInfo.get(0).getPhoto_name();
				}
				Timestamp updated=new Timestamp(System.currentTimeMillis());
				Date created = usrInfo.get(0).getCreated();

				usr.setId(userId);
				usr.setRole(userDto.getRole());
				usr.setUsername(usrInfo.get(0).getUsername());
				usr.setPassword(usrInfo.get(0).getPassword());
				usr.setFirst_name(fname);
				usr.setLast_name(lname);
				usr.setCompany_name(usrInfo.get(0).getCompany_name());
				usr.setPhoto_name(fullfilename);
				usr.setEmail(usrInfo.get(0).getEmail());
				usr.setPhone(phone);
				usr.setAddress(address);
				usr.setCity(city);
				usr.setState(state);
				usr.setCountry(country);
				usr.setStatus(usrInfo.get(0).getStatus());
				usr.setCreated(created);
				usr.setUpdated(updated);
				usr.setLanguage(lang);
				usr.setCurrency(usrInfo.get(0).getCurrency());
				usr.setCompanyid(companyid);
				usr.setZipcode(zipcode);
				usr.setCellphone(cellphone);
				userService.updateUserDetails(usr);
				sess.setAttribute("picprofile", fullfilename.replaceAll("(?:.*)(doctos/.*)+","$1"));
				sess.setAttribute("firstname", fname);
				sess.setAttribute("lastname", lname);
				sess.setAttribute("language", lang);
				try{
					resp = "True";
					FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
				}catch (IOException ex){
					System.out.println("Exception in updateuser(): " + ex.getMessage());
					resp = "err_record_no_saved";
				}
			}
		}}
		try{
			res.getWriter().write(resp);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getUserInfoProfile")
	public @ResponseBody Object[] getUserInfoProfile(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess == null)return null;
		HashMap<Object, Object> data = new HashMap();
		UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
		List<Users> shortInfo = (List<Users>) daoJaService
			.getFromJA("SELECT username, first_name, last_name, photo_name, country, state, city, address, zipcode, phone, cellphone FROM Users WHERE id='" + userDto.getId() + "'", Users.class);
		data.put("info",shortInfo);
		return new Object[] { data };
	}

	public Smtpmail getSmptEmail(Integer smtpid) {
		Object[] params = { smtpid };
		String sql = "SELECT * FROM smtpmail WHERE smtpid=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return (Smtpmail) jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<Smtpmail>(Smtpmail.class));
	}

	@RequestMapping(value = "/roles", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addRolePage(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP
																							// 1.1.
				response.setHeader("Pragma", "no-cache");// HTTP 1.0.
				response.setDateHeader("Expires", 0);
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int role = userDto.getRole();

				// Obtiene los privilegios del módulo
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "roles");
				Menu menu = (Menu)daoJaService.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				String whereClause = "";
				if (role!=ROLE_SYSADMIN){//Sin restricciones para SysAdmin
					whereClause = "WHERE roleid not IN("+ROLE_SYSADMIN;
		  			if (role==ROLE_CJADMIN){
		  				whereClause += "," + ROLE_CJADMIN;
		  			}else{
		  				whereClause += "," + ROLE_CJADMIN+ "," + ROLE_FIRMADMIN;
		  				if(role!=ROLE_FIRMADMIN)
		  					whereClause += "," + role;
		  				whereClause += ") AND companyid=" + userDto.getCompanyid();
		  			}
				}
				List<Roles> listRoles=rolesService.getAll("FROM Roles " + whereClause + " ORDER BY roleid ASC");
				List<Companies> firmas = daoJaService.getFromJAHibernate(Companies.class);
				forModel.put("tabla", listRoles);
				forModel.put("firmas", firmas);
				forModel.put("role", role);
				forModel.put("userfirm", userDto.getCompanyid());
				return new ModelAndView("roles", forModel);
			}
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/addrole")
	@ResponseBody
	public void addNewRole(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Roles roleN = new Roles();
				String newrolename = request.getParameter("rolename") == null ? ""
						: request.getParameter("rolename").trim();
				int companyid=request.getParameter("firm")==null?0:Functions.toInt(request.getParameter("firm").trim());
//FIXME
/*				ERROR: insert or update on table "role" violates foreign key constraint "role_companyid_fkey"
  Detail: Key (companyid)=(28) is not present in table "role":
	  
	  https://stackoverflow.com/questions/54321530/org-postgresql-util-psqlexception-error-insert-or-update-on-table-violates-for
*/
				UserDTO userDto=(UserDTO) sess.getAttribute("UserDTO");
				int roleid=userDto.getRole();
				if((roleid!=ROLE_SYSADMIN)&&(roleid!=ROLE_CJADMIN))
					companyid=userDto.getCompanyid();
				roleN.setRolename(newrolename);
				roleN.setCompanyid(companyid);
				long succ=rolesService.addNewRole(roleN);
				try {
					if (succ > 0)
						response.getWriter().write("True");
					else
						response.getWriter().write("False");
				} catch (IOException e) {
					logger.error("Exception in addNewRole()" + e.getMessage());
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getRoleDetails")
	public Object[] getRoleDetails(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
				if (id > 0) {
					List<Roles> info = rolesService.getAll("FROM Roles WHERE roleid=" + id);
					return new Object[] { info };
				}
			}
		return null;
	}

	@RequestMapping(value = "/updaterole")
	public void updaterole(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long updateRoleid = (request.getParameter("roleid") == null || request.getParameter("roleid").equals("0"))
						? 0L : Functions.toLong(request.getParameter("roleid").trim());
				String rolename = request.getParameter("rolename") == null ? ""
						: request.getParameter("rolename").trim();
				int companyid=request.getParameter("firm")==null?0:Functions.toInt(request.getParameter("firm").trim());
				
				UserDTO userDto=(UserDTO) sess.getAttribute("UserDTO");
				int roleid = userDto.getRole();
				if(roleid!=ROLE_SYSADMIN && roleid!=ROLE_CJADMIN)
					companyid=userDto.getCompanyid();
				Roles role = new Roles();
				role.setRoleid(updateRoleid);
				role.setRolename(rolename);
				role.setCompanyid(companyid);
				rolesService.updateRoleDetails(role);
			}
		}
	}

	@RequestMapping(value = "/deleterole")
	public void deleteRole(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long roleid = (request.getParameter("roleid") == null || request.getParameter("roleid").equals("")) ? 0L
						: Functions.toLong(request.getParameter("roleid").trim());
				rolesService.deleteRole(roleid);
			}
		}
	}

	// ***************** Privileges ******************
	public Long valueFromChecks(String value) {
		if ((value.contains("true")) || (value.contains("on")) || (value.contains("1"))) {
			Long valueFromChecks = 1L;
			return valueFromChecks;
		}
		return 0L;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/privileges", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView privilegesPage(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) request.getSession().getAttribute("UserDTO");
				int role = userDto.getRole();
				int checkrole = Functions.isEmpty(request.getParameter("srl")) ? role
						: Functions.toInt(request.getParameter("srl"));
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP_1.1.
				response.setHeader("Pragma", "no-cache");// HTTP_1.0.
				response.setDateHeader("Expires", 0);
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				// carga menu para verificar permisos
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "privileges");
				Menu menu = (Menu) daoJaService.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ",
						parameters);

				List<Menu> allMenus = daoJaService.getFromJAHibernate(Menu.class);
				parameters.clear();
				parameters.put("roleid", checkrole);
				List<Menuprivileges> listPermisosByRol = daoJaService
						.sqlHQL("From Menuprivileges where roleid=:roleid", parameters);
				List<Privileges> allPermisos = daoJaService.getFromJAHibernate(Privileges.class);

				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("listuser", listPermisosByRol);
				forModel.put("listmenu", allMenus);
				forModel.put("listpermisos", allPermisos);

				String whereClause = "";
 				if (role!=ROLE_SYSADMIN)
					if (role==ROLE_CJADMIN){
						whereClause += " WHERE roleid<>"+ROLE_SYSADMIN;
		  			}else{
		  				whereClause += " WHERE roleid not IN("+ROLE_SYSADMIN + "," + ROLE_CJADMIN;
		  				if(role!=ROLE_FIRMADMIN)
		  					whereClause +=  "," + ROLE_FIRMADMIN;
		  				whereClause += ") AND companyid=" + userDto.getCompanyid();
	  				}
				List<Roles> roleList = rolesService.getAll("FROM Roles" + whereClause + "  ORDER BY roleid ASC");
				forModel.put("listr", roleList);
				forModel.put("roleuser", checkrole);
				forModel.put("menu", menu);
				return new ModelAndView("privileges", forModel);
			}
		}
		return new ModelAndView("login");
	}
//FIXME
	/*@RequestMapping(value = "/privileges", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView privilegesPage(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) request.getSession().getAttribute("UserDTO");
				int role = userDto.getRole();
				int checkrole = Functions.isEmpty(request.getParameter("srl")) ? role
						: Functions.toInt(request.getParameter("srl"));
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP
																							// 1.1.
				response.setHeader("Pragma", "no-cache");// HTTP 1.0.
				response.setDateHeader("Expires", 0);
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				// carga menu para verificar permisos
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "privileges");
				Menu menu = (Menu) daoJaService.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ",
						parameters);

				List<Menu> allMenus = null;
				//allMenus = daoJaService.getFromJAHibernate(Menu.class);
				if(role==ROLE_SYSADMIN){	
					allMenus = daoJaService.getFromJAHibernate(Menu.class);
				}else{
					//ROLE_FIRMADMIN	ROLE_CJADMIN)
				}
				
				allMenus = menuService.getAll("SELECT DISTINCT (menuid) FROM Menuprivileges WHERE roleid="+role);
				parameters.clear();
				parameters.put("roleid", checkrole);
				List<Menuprivileges> listPermisosByRol = daoJaService
						.sqlHQL("From Menuprivileges where roleid=:roleid", parameters);
				List<Privileges> allPermisos = daoJaService.getFromJAHibernate(Privileges.class);

				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("listuser", listPermisosByRol);
				forModel.put("listmenu", allMenus);
				forModel.put("listpermisos", allPermisos);

				String whereClause = "";
 				if (role!=ROLE_SYSADMIN)
					if (role==ROLE_CJADMIN){
						whereClause += " WHERE roleid<>"+ROLE_SYSADMIN;
		  			}else{
		  				whereClause += " WHERE roleid not IN("+ROLE_SYSADMIN + "," + ROLE_CJADMIN;
		  				if(role!=ROLE_FIRMADMIN)
		  					whereClause +=  "," + ROLE_FIRMADMIN;
		  				whereClause += ")";
	  				}
				List<Roles> roleList = rolesService.getAll("FROM Roles" + whereClause + "  ORDER BY roleid ASC");
				forModel.put("listr", roleList);
				forModel.put("roleuser", checkrole);
				forModel.put("menu", menu);
				return new ModelAndView("privileges", forModel);
			}
		}
		return new ModelAndView("login");
	}*/

	/*
	 * public boolean fillPrivilegeModule(int role){ //Sí el módulo no existe
	 * para el rol, lo agrega Long vis=0L, newadd=0L, edt=0L, del=0L; List<Menu>
	 * allMenus=daoJaService.getFromJAHibernate(Menu.class); List<Privileges>
	 * allPermisos=daoJaService.getFromJAHibernate(Privileges.class);
	 * List<Menuprivileges> listPbyUser=new ArrayList<Menuprivileges>();
	 * HashMap<Object, Object> parameters = new HashMap();
	 * parameters.put("roleid", role); for(Menu m:allMenus){ for(Privileges
	 * p:allPermisos){ parameters.put("menuid", m.getMenuid());
	 * parameters.put("privilegesid", p.getPrivilegesid()); List<Menuprivileges>
	 * listPrivileges = daoJaService.sqlHQL(
	 * "From Menuprivileges where roleid=:roleid and menuid=:menuid and privilegesid=:privilegesid"
	 * , parameters); if(listPrivileges.size() == 0){ Menuprivileges
	 * privileges=new Menuprivileges(); privileges.setRoleid(role);
	 * privileges.setMenuid(menuid); privileges.setPrivilegesid(privilegesid);
	 * privileges.setRoleid(role);
	 * 
	 * long succ=privilegesService.addNewPrivilege(privileges); if(succ<0)
	 * return false; } } } return true; }
	 */

	/*
	 * @RequestMapping(value={"/getPrivilegeList" }, method=RequestMethod.POST)
	 * public @ResponseBody Object[] getPrivilegeList(HttpServletRequest
	 * request,HttpServletResponse response){ int
	 * roleid=(request.getParameter("roleid")==null)?0:Functions.toInt(request.
	 * getParameter("roleid").trim()); try{ String wherex=""; if(roleid!=1)
	 * wherex=" where moduleid <> 3 "; List<Privileges>
	 * privilegesList=privilegesService.getAll("FROM Privileges WHERE roleid="
	 * +roleid + " ORDER BY module"); List<Modules>
	 * moduleList=modulesService.getAll("FROM Modules "+wherex +
	 * " ORDER BY menugroup asc"); return new Object[]{privilegesList,
	 * moduleList }; }catch(Exception ex){ return null; } }
	 */

/*	@RequestMapping(value = "/addprivilege")
	@ResponseBody
	public void addprivilege(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Privileges privileges = new Privileges();
				Long newrole = request.getParameter("roleid") == null ? 0L
						: Functions.toLong(request.getParameter("roleid").trim());
				String name = request.getParameter("modulename") == null ? ""
						: request.getParameter("modulename").trim();
				Long newadd = valueFromChecks(request.getParameter("newadd")) == 0L ? 0L : 1L,
						vis = valueFromChecks(request.getParameter("vis")) == 0L ? 0L : 1L,
						ctr = valueFromChecks(request.getParameter("ctr")) == 0L ? 0L : 1L,
						edt = valueFromChecks(request.getParameter("edt")) == 0L ? 0L : 1L,
						del = valueFromChecks(request.getParameter("del")) == 0L ? 0L : 1L,
						cnf = valueFromChecks(request.getParameter("cnf")) == 0L ? 0L : 1L, pcs = 0L;
				String[] assignedMods = name.split(",");
				// se comento temporal x nueva version menu permisos
				/*
				 * for(int m=0; m < assignedMods.length; m++){
				 * privileges.setRoleid(newrole);
				 * privileges.setModule(assignedMods[m]);
				 * privileges.setVis(vis); privileges.setCtr(ctr);
				 * privileges.setNewadd(newadd); privileges.setEdt(edt);
				 * privileges.setDel(del); privileges.setPcs(pcs);
				 * privileges.setCnf(cnf); long
				 * succ=privilegesService.addNewPrivilege(privileges); try{
				 * if(succ > 0) response.getWriter().write("True"); else
				 * response.getWriter().write("False"); }catch(IOException ex){
				 * logger.error("Exception in addprivilege()"+ex.getMessage());
				 * } }
				 * /
			}
		}
	}*/

	@RequestMapping(value = "/updateprivilege")
	public void updatePrivilege(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String privid = request.getParameter("editdataid") == null ? ""
						: request.getParameter("editdataid").trim();
				String roleid = request.getParameter("roleid") == null ? "" : request.getParameter("roleid").trim();
				Map<String, Object> valores = new HashMap<>();
				valores.put("roleid", Functions.toInt(roleid));
				valores.put("menuid", Functions.toInt(privid));
				String query = "delete from menuprivileges where menuid = :menuid and roleid=:roleid";
				int rows = daoJaService.updateSqlNative(query, valores);
				List<Privileges> allPermisos = daoJaService.getFromJAHibernate(Privileges.class);
				for (Privileges p : allPermisos) {
					if (Functions.toStr(request.getParameter("mp_" + p.getPrivilegesid())).equals("on")) {
						Menuprivileges permisox = new Menuprivileges();
						permisox.setMenuid(Functions.toInt(privid));
						permisox.setPrivilegesid(p.getPrivilegesid());
						permisox.setRoleid(Functions.toInt(roleid));
						daoJaService.save(permisox);
					}
				}
			}
		}
	}

	@RequestMapping(value = "/deleteprivilege")
	public void deletePrivilege(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long privilegeid = (request.getParameter("privilegeid") == null
						|| request.getParameter("privilegeid").equals("")) ? 0L
								: Functions.toLong(request.getParameter("privilegeid").trim());
				privilegesService.deletePrivilege(privilegeid);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editprivilege")
	@ResponseBody
	public void editPrivilegePopup(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int menuid = request.getParameter("id") == null ? 0
						: Functions.toInt(request.getParameter("id").trim());
				int roleid = request.getParameter("role") == null ? 0
						: Functions.toInt(request.getParameter("role").trim());
				try {
					if (menuid == 0) {
						HashMap<Object, Object> parameters = new HashMap();
						parameters.put("roleid", roleid);
						List<Menuprivileges> listAllPrivileges = daoJaService
								.sqlHQL("From Menuprivileges where roleid=:roleid", parameters);
						Gson gson = new Gson();
						String data = gson.toJson(Arrays.asList(listAllPrivileges));
						JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
						response.setContentType("application/json");
						response.getWriter().print(jsonArray);
						//response.getWriter().write("false");
					} else {
						Menu menu = (Menu) daoJaService.findById(Menu.class, menuid);
						List<Privileges> allPermisos = daoJaService.getFromJAHibernate(Privileges.class);
						HashMap<Object, Object> parameters = new HashMap();
						parameters.put("roleid", roleid);
						parameters.put("menuid", menuid);
						List<Menuprivileges> listPrivileges = daoJaService
								.sqlHQL("From Menuprivileges where roleid=:roleid and menuid=:menuid", parameters);
						Gson gson = new Gson();
						String data = gson.toJson(Arrays.asList(listPrivileges, menu, allPermisos));
						JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
						response.setContentType("application/json");
						response.getWriter().print(jsonArray);
					}
				} catch (Exception e) {
					logger.error("Exception in UserController [editPrivilegePopup()]::" + e.getMessage());
				}
			}
		}
	}

	@RequestMapping(value = "/getMenuById")
	public @ResponseBody Object[] getMenuById(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Map<String, Object> data = new HashMap<String, Object>();
				String id = request.getParameter("id");
				Menu menu = (Menu) daoJaService.findById(Menu.class, Functions.toInt(id));
				data.put("data", menu);
				return new Object[] { data };
			}
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/setupmenu", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView setupmenu(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) req.getSession().getAttribute("UserDTO");
				if(userDto.getRole()!=ROLE_SYSADMIN)return new ModelAndView("login");
				res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP_1.1.
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=UTF-8");
				res.setCharacterEncoding("UTF-8");
				Map<String, Object> forModel = new HashMap<String, Object>();

				List<Menu> menuList = null;
				menuList = menuService.getAll("FROM Menu mn WHERE tipomenu<>0 ORDER BY mn.menuparentid, mn.orden");
				forModel.put("menus", menuList);
				return new ModelAndView("setupmenu", forModel);
			}
		}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/addNewMenu")
	public void addNewResource(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int menuparent = (req.getParameter("parent") == null) ? 0
						: Functions.toInt(req.getParameter("parent").trim()),
						tipomenu = (req.getParameter("tipomenu") == null) ? 0
								: Functions.toInt(req.getParameter("tipomenu").trim());
				String menutitle = (req.getParameter("menutitle") == null) ? "" : req.getParameter("menutitle").trim(),
						icon = (req.getParameter("icon") == null) ? "" : req.getParameter("icon").trim(),
						link = (req.getParameter("link") == null) ? "" : req.getParameter("link").trim(),
						orderIds = (req.getParameter("orderIds") == null) ? "" : req.getParameter("orderIds").trim();

				if (Functions.isEmpty(menutitle)) {
					try {
						res.getWriter().write("msg_empty_data");
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}

				// Alta de menú
				Menu mn = new Menu();
				mn.setMenu(menutitle);
				mn.setMenuparentid(menuparent);
				mn.setIcon(icon);
				mn.setLink(link);
				mn.setTipomenu(tipomenu);
				mn.setOrden(100);
				long newMenuId = menuService.addNewMenu(mn);

				try {
					if (newMenuId > 0) {
						res.getWriter().write("msg_data_saved");
						orderIds = orderIds.replace("-1", "" + newMenuId);
						// Reorganización de posiciones de menús
						String[] divs = orderIds.split(",");
						if (divs.length > 0) {
							for (int i = 0; i < divs.length; i++) {
								if (Functions.toInt(divs[i]) > 0) {
									try {
										Menu entidadOrder = (Menu) daoJaService.findById(Menu.class,
												Functions.toInt(divs[i]));
										entidadOrder.setOrden(i + 1);
										daoJaService.saveOrUpdate(entidadOrder);
									} catch (Exception e) {
										logger.error(
												"Exception in UserController [update order menu()]::" + e.getMessage());
									}
								}
							}
						}
					} else {
						res.getWriter().write("err_record_no_saved");
					}
				} catch (IOException ex) {
					System.out.println("Exception in addNewMenu(): " + ex.getMessage());
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getDataByMenuId")
	public Object[] getByMenuId(HttpServletRequest req, HttpServletResponse res) {
		int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
		Map<String, Object> data = new HashMap<String, Object>();
		if (id > 0) {
			List<Menu> info = menuService.getAll("FROM Menu WHERE menuid=" + id);
			data.put("detail", info);
		}else{
				List<Menu> info = menuService.getAll("FROM Menu");
				data.put("detail", info);
		}
		return new Object[] { data };
	}

	@ResponseBody
	@RequestMapping(value = "/updateMenu")
	public void updateMenu(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String msg = "err_record_no_saved";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = (req.getParameter("id") == null) ? 0 : Functions.toInt(req.getParameter("id").trim()),
						menuparent = (req.getParameter("parent") == null) ? 0
								: Functions.toInt(req.getParameter("parent").trim()),
						tipomenu = (req.getParameter("tipomenu") == null) ? 0
								: Functions.toInt(req.getParameter("tipomenu").trim());
				String menutitle = (req.getParameter("menutitle") == null) ? "" : req.getParameter("menutitle").trim(),
						icon = (req.getParameter("icon") == null) ? "" : req.getParameter("icon").trim(),
						link = (req.getParameter("link") == null) ? "" : req.getParameter("link").trim(),
						orderIds = (req.getParameter("orderIds") == null) ? "" : req.getParameter("orderIds").trim();
				if (Functions.isEmpty(menutitle)) {
					try {
						res.getWriter().write("msg_empty_data");
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}

				// Actualización del menú
				Menu mn = new Menu();
				mn.setMenuid(id);
				mn.setMenu(menutitle);
				mn.setMenuparentid(menuparent);
				mn.setIcon(icon);
				mn.setLink(link);
				mn.setTipomenu(tipomenu);
				mn.setOrden(100);
				menuService.updateMenu(mn);
				msg = "msg_data_saved";
				String[] divs = orderIds.split(",");
				if ((Functions.isEmpty(orderIds) == false) && (!orderIds.equals("0")))
					for (int i = 0; i < divs.length; i++)
						if (Functions.toInt(divs[i]) > 0)
							try {
								Menu entidadOrder = (Menu) daoJaService.findById(Menu.class, Functions.toInt(divs[i]));
								entidadOrder.setOrden(i + 1);
								daoJaService.saveOrUpdate(entidadOrder);
							} catch (Exception e) {
								logger.error("Exception in UserController [update order menu()]::" + e.getMessage());
							}
			}
		}
		try {
			res.getWriter().write(msg);
		} catch (IOException ex) {
			System.out.println("Exception in updateMenu(): " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getMenuList")
	public Object[] getMenuList(HttpServletRequest req, HttpServletResponse res) {
		int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
		Map<String, Object> data = new HashMap<String, Object>();
		// Obtiene todos la lista marcada como grupos de menús, incluyendo los
		// de barra de menú header y menu (archivos .JSP)
		List<Menu> info = menuService.getAll("FROM Menu AS mn WHERE mn.menuparentid = 0 AND tipomenu<>0 ORDER BY mn.orden");
		data.put("detail", info);
		return new Object[] { data };
	}

	@ResponseBody
	@RequestMapping(value = "/getOptParentMenu")
	public Object[] getOptParentMenu(HttpServletRequest req, HttpServletResponse res) {
		int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
		int tipo = req.getParameter("tipo") == null ? 0 : Functions.toInt(req.getParameter("tipo").trim());
		Map<String, Object> data = new HashMap<String, Object>();
		List<Menu> infomenu = menuService.getAll("FROM Menu WHERE menuparentid=" + id + " and tipomenu=" + tipo);
		data.put("options", infomenu);
		return new Object[] { data };
	}

	@RequestMapping(value = "/deleteMenu")
	public void deleteMenu(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = req.getParameter("id") == "" ? 0 : Functions.toInt(req.getParameter("id").trim());
				menuService.deleteMenu(id);
			}
		}
	}

	@RequestMapping(value = "/subprivileges", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView subprivileges(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) request.getSession().getAttribute("UserDTO");
				if(userDto.getRole()!=ROLE_SYSADMIN)
					return new ModelAndView("login");
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP1.1.
				response.setHeader("Pragma", "no-cache");// HTTP 1.0.
				response.setDateHeader("Expires", 0);
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				// carga menu para verificar permisos
				Map<String, Object> forModel = new HashMap<String, Object>();
				List<Menu> subp = menuService.getAll("FROM Menu WHERE tipomenu = 0 ORDER BY menu ASC");
				forModel.put("subp", subp);
				
				String whereClause = "=0",subsids = "";
				for (int i = 0; i < subp.size(); i++)
					subsids += subp.get(i).getMenuparentid() + ",";
				subsids = subsids.replaceAll(".$", "");
				if(!Functions.isEmpty(subsids))
					whereClause = " IN(" + subsids + ")";
				List<Menu> menudata = menuService.getAll("FROM Menu WHERE menuid" + whereClause + " AND tipomenu<>0");
				forModel.put("menudata", menudata);
				
				return new ModelAndView("subprivileges", forModel);
			}
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/getModules")
	public @ResponseBody Object[] getModules(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			UserDTO userDto = (UserDTO) req.getSession().getAttribute("UserDTO");
			if(userDto.getRole()!=ROLE_SYSADMIN)
				return null;
			List<Menu> menuList = menuService.getAll("FROM Menu WHERE menuid>1 AND tipomenu<>0 AND menuid NOT IN"
				+"(SELECT menuparentid FROM Menu WHERE menuparentid>0 AND tipomenu<>0) ORDER BY tipomenu ASC, orden ASC");
			return new Object[] { menuList };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/addNewSubPrivilege")
	public void addNewSubPrivilege(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
		String resp = "err_record_no_saved";
		saving:{
			if ((sess != null) || (userDto.getRole()!=ROLE_SYSADMIN) )
				if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
					String descr = (req.getParameter("descr") == "") ? "" : req.getParameter("descr"),
						toUrl = (req.getParameter("toUrl") == "") ? "" : req.getParameter("toUrl");
	
					if (Functions.isEmpty(toUrl) || Functions.isEmpty(descr)){
						resp="msg_empty_data";
						break saving;
					}
					List<Menu> existsPriv = menuService.getAll("FROM Menu WHERE tipomenu=0 AND UPPER(menu)='"
						+ descr.toUpperCase() + "' AND link='" + toUrl + "' ORDER BY menu ASC");
					if(existsPriv.size()>0){
						resp="err_duplicated_data";
						break saving;
					}
					Menu subprivileges = new Menu();
					subprivileges.setMenu(descr);
					subprivileges.setMenuparentid(0);
					subprivileges.setLink(toUrl);
					subprivileges.setTipomenu(0);
					subprivileges.setOrden(0);
					int succ = menuService.addNewMenu(subprivileges);
					resp=(succ > 0)?"msg_data_saved":"err_record_no_saved";
				}
		}
		try {
			res.getWriter().write(resp);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getSubPrivilegeById")
	public Object[] getSubPrivilegeById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if (sess != null) {
			int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
			List<Menu> info = menuService.getAll("FROM Menu WHERE tipomenu=0 AND menuid=" + id + " ORDER BY menu ASC");
			data.put("info", info);
		}
		return new Object[] { data };
	}

	/** Obtiene las opciones donde se le permitirá a un usuario realizar cambios cuando se asigne un documento o juicio compartido.	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getSubPrivileges")
	public @ResponseBody Object[] getSubPrivileges(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if (sess != null) {
			int sdid = (req.getParameter("sdid") == null) ? 0 : Functions.toInt(req.getParameter("sdid"));
			HashMap<Object, Object> parameters = new HashMap();
			parameters.put("sdid", sdid);
			List<Menuprivileges> mp = daoJaService.sqlHQL("FROM Menuprivileges WHERE shareddocketid=:sdid ORDER BY privilegesid ASC ", parameters);
			List<Menu> subpriv = menuService.getAll("FROM Menu WHERE tipomenu=0 ORDER BY menu ASC");
			List<Privileges> priv = privilegesService.getAll("FROM Privileges ORDER BY privilegesid ASC");
			data.put("mp", mp);
			data.put("subpriv", subpriv);
			data.put("priv",priv);
		}
		return new Object[] { data };
	}

	@RequestMapping(value = "/updateSubPrivilege")
	public void updateSubPrivilege(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		saving:{
			if(sess!=null)if(sess.getAttribute("isLogin")!=null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
				String descr = (req.getParameter("descr") == "") ? "" : req.getParameter("descr"),
					toUrl = (req.getParameter("toUrl") == "") ? "" : req.getParameter("toUrl");
				int id = (req.getParameter("id") == null) ? 0 : Functions.toInt(req.getParameter("id"));
				if (Functions.isEmpty(toUrl) || Functions.isEmpty(descr)){
					resp="msg_empty_data";
					break saving;
				}
				List<Menu> existsPriv = menuService.getAll("FROM Menu WHERE tipomenu=0 AND UPPER(menu)='"
					+ descr.toUpperCase() + "' AND link='" + toUrl + "' ORDER BY menu ASC");
				if(existsPriv.size()>0)
					if(existsPriv.get(0).getMenuid()!=id){
						resp="err_duplicated_data";
						break saving;
					}
				Menu subprivileges = new Menu();
				subprivileges.setMenuid(id);
				subprivileges.setMenu(descr);
				subprivileges.setMenuparentid(0);
				subprivileges.setLink(toUrl);
				subprivileges.setTipomenu(0);
				subprivileges.setOrden(0);
				menuService.updateMenu(subprivileges);
				resp = "true";
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteSubPrivilege")
	public void deleteSubPrivilege(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = req.getParameter("id") == "" ? 0 : Functions.toInt(req.getParameter("id").trim());
				menuService.deleteMenu(id);
			}
	}

	@RequestMapping(value = "/usersFirm")
	public Object[] usersFirm(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int idFirm = userDto.getCompanyid();
				List<Users> info = userService.getAll("select id, first_name, last_name FROM Users WHERE companyid="+idFirm);
				data.put("info", info);
			}
		return new Object[] { data };
	}

	/** Obtiene la pantalla de 'DashBoard' en lugar de la pantalla de 'captura de origen'.
	@param modid	- Id de la tabla referenciada, debe coincidir con el ID de la tabla Menu.	*/
	@RequestMapping(value = "/getModuleDataById")
	public @ResponseBody JsonObject getModuleDataById(int modid){
		JsonObject moddata = new JsonObject();
		String target = "";
		if(modid==8)
			target = "trialsdashboard";
		else if(modid==7)
			target = "appealsdashboard";
		else if(modid==4)
			target = "protectiondashboard";
		else if(modid==6)
			target = "indprotectiondashboard";
		else if(modid==5)
			target = "resourcedashboard";

		Menu menutmp = null;
		if(Functions.isEmpty(target))
			menutmp = (Menu) daoJaService.sqlHQLEntity("FROM Menu WHERE menuid=" + modid, null);
		else
			menutmp = (Menu) daoJaService.sqlHQLEntity("FROM Menu WHERE link LIKE '%" + target + "%'", null);
		moddata.addProperty("modulename",menutmp.getMenu());
		moddata.addProperty("link",menutmp.getLink());
		return moddata;
	}

	/** Obtiene los datos del menú por medio del nombre de URL.
	@param module	- (opcional) Nombre del módulo que hace referencia a la URL o JSP. Si no es necesario, indicar con una cadena vacía "".
	@param alias	- (opcional) Nombre alterno a 'module' (por lo general es agregado la terminación ".jet" al 'module'. Si no es necesario, indicar con una cadena vacía "".
	@param exclude	- (opcional) Contenido que no se desea incluir y que posiblemente este en el campo "menu". Si no es necesario, indicar con una cadena vacía "".	*/
	@RequestMapping(value = "/getMenuIdByName")
	public @ResponseBody int getMenuIdByName(String module, String alias, String exclude){
		String query = "";
		if(!Functions.isEmpty(module) || !Functions.isEmpty(alias)){
			if(!Functions.isEmpty(module))
				query = "link LIKE '" + module + "'";
			if(!Functions.isEmpty(alias))
				query += (query.equals("")?"":" OR ") + "link LIKE '" + alias + "'";
			query = "(" + query + ")";
		}
		query = (Functions.isEmpty(exclude)?"":"menu NOT LIKE '%" + exclude + "%' AND ") + query;
		if(query.equals(""))return 0;
		@SuppressWarnings("unchecked")
		Menu mtmp = (Menu) daoJaService.sqlHQLEntity("FROM Menu WHERE " + query.replaceAll(" AND $",""), null);
		return mtmp==null?0:mtmp.getMenuid();
	}
}