package com.aj.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aj.model.Apelaciones;
import com.aj.model.Clients;
import com.aj.model.Companies;
import com.aj.model.Companyclients;
import com.aj.model.Juicios;
import com.aj.model.Menu;
import com.aj.model.Socialnetworkclient;
import com.aj.model.Socialnetworks;
import com.aj.service.AccessDbJAService;
import com.aj.service.ApelacionesService;
import com.aj.service.ClientsService;
import com.aj.service.CompaniesService;
import com.aj.service.CompanyclientsService;
import com.aj.service.JuiciosService;
import com.aj.service.SocialnetworkclientService;
import com.aj.service.SocialnetworkService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class ClientsController {
	@Autowired
	public ApelacionesService apelacionesService;

	@Autowired
	private CatalogsController catalogsCtrll;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	private CommonController commonsCtrll;

	@Autowired
	public CompaniesService companiesService;

	@Autowired
	public CompanyclientsService companyclientService;

	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public SocialnetworkclientService socialnetworkclientService;
	@Autowired

	public SocialnetworkService socialnetworkService;

	@SuppressWarnings("rawtypes")
	@Autowired
	public AccessDbJAService dao;

	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static final int ROLE_SYSADMIN= 1,ROLE_CJADMIN = 2,ROLE_FIRMADMIN=3;

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/clients")
	public ModelAndView clients(HttpServletRequest req, HttpServletResponse res) {
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
				int clientid = (req.getParameter("clid") == null) ? 0
						: Functions.toInt(req.getParameter("clid").trim());//Variable para indicar que proviene de Home
				/*int userid = (int) userDto.getId();
				Users user = new Users();
				user = userService.getUserById(userid);*/

				// Obtiene los privilegios del módulo
				@SuppressWarnings({ "qrawtypes" })
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "clients");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				String whereClause = "";
				if ((role!=ROLE_SYSADMIN && role!=ROLE_CJADMIN))
					whereClause = " WHERE companyid=" + userDto.getCompanyid();
				List<Companyclients> ccclient = companyclientService
						.getAll("SELECT DISTINCT(clientid) FROM Companyclients" + whereClause);
				String ids = "0,";
				for (int i = 0; i < ccclient.size(); i++)
					if(Functions.isNumeric(ccclient.get(i)+""))
						ids += ccclient.get(i) + ",";
					else
						ids += ccclient.get(i).getClientid() + ",";
				ids = ids.replaceAll(".$", "");

				List<Clients> clientsList = null;
				if(clientid==0){
					clientsList = clientsService.getAll("FROM Clients WHERE clientid IN(" + ids + ") ORDER BY client ASC, city ASC");
				}else{
					clientsList = clientsService.getAll("FROM Clients WHERE clientid =" + clientid);
				}
				forModel.put("clientsList", clientsList);

				return new ModelAndView("clients", forModel);
			}
		return new ModelAndView("login");
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/addNewClients")
	public void addNewClients(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int status = (req.getParameter("status") == null) ? 0
						: Functions.toInt(req.getParameter("status").trim()),
					companyid = (req.getParameter("company") == null) ? 0
						: Functions.toInt(req.getParameter("company").trim()),
					typeofperson = (req.getParameter("typeofperson") == null) ? 0
						: Functions.toInt(req.getParameter("typeofperson").trim());
				String client = (req.getParameter("client") == null) ? "" : req.getParameter("client").trim(),
					address1 = (req.getParameter("address1") == null) ? "" : req.getParameter("address1").trim(),
					city = (req.getParameter("city") == null) ? "" : req.getParameter("city").trim(),
					country = (req.getParameter("country") == null) ? "" : req.getParameter("country").trim(),
					email = (req.getParameter("email") == null) ? "" : req.getParameter("email").trim(),
					phone = (req.getParameter("phone") == null) ? "" : req.getParameter("phone").trim(),
					state = (req.getParameter("state") == null) ? "" : req.getParameter("state").trim(),
					cp = (req.getParameter("cp") == null) ? "" : req.getParameter("cp").trim(),
					cellphone = (req.getParameter("cellphone") == null) ? "" : req.getParameter("cellphone").trim(),
					photo = (req.getParameter("photo") == null) ? "" : req.getParameter("photo").trim(),
					birthdate = (req.getParameter("birthdate") == null) ? "" : req.getParameter("birthdate").trim(),
					comments = (req.getParameter("comments") == null) ? "" : req.getParameter("comments").trim(),
					webpage = (req.getParameter("webpage") == null) ? "" : req.getParameter("webpage").trim(),
					arrSN = (req.getParameter("arrSN") == null) ? "" : req.getParameter("arrSN").trim(),
					contactperson = (req.getParameter("contactperson") == null) ? "" : req.getParameter("contactperson").trim(),
					rel_with = (req.getParameter("rel_with") == null) ? "" : req.getParameter("rel_with").trim(),
					ref_by = (req.getParameter("ref_by") == null) ? "" : req.getParameter("ref_by").trim();
				if (Functions.isEmpty(client) || Functions.isEmpty(address1) || Functions.isEmpty(email)) {
					try {
						res.getWriter().write("msg_empty_data");
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				// Guardamos archivos relacionados con el registro
				String[] nameFiles = null;
				Enumeration enumeration = req.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String parameterName = (String) enumeration.nextElement();
					if (parameterName.contains("fileuploadx_") && !Functions.isEmpty(req.getParameter(parameterName))) {
						if (nameFiles == null)
							nameFiles = new String[1];
						else
							nameFiles = (String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
						nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
					}
				}
				// Mueve archivos a destino
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
						"doctos" + FileSystems.getDefault().getSeparator() + "images/clients", true);
				destinationPath = Functions.addPath(destinationPath, "", true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);

				Clients clients = new Clients();
				clients.setClient(client);
				clients.setAddress1(address1);
				clients.setCity(city);
				clients.setCountry(country);
				clients.setEmail(email);
				clients.setPhone(phone);
				clients.setState(state);
				clients.setZipcode(cp);
				clients.setStatus(status);
				clients.setCellphone(cellphone);
				clients.setPhoto("");
				clients.setPersonafiscalid(typeofperson);
				clients.setWebpage(webpage);
				clients.setContactperson(contactperson);
				clients.setRel_with(rel_with);
				clients.setRef_by(ref_by);
				if (!Functions.isEmpty(birthdate))
					clients.setBirthdate(Functions.parseFecha(birthdate, "yyyy-MM-dd"));
				clients.setComments(comments);
				long newclientid = clientsService.addNewClient(clients);
				if (newclientid > 0) {
					if(companyid==0){
						UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
						companyid=userDto.getCompanyid();
					}
					Companyclients coclient = new Companyclients();
					coclient.setCompanyid(companyid);
					coclient.setClientid((int) newclientid);
					long newId = companyclientService.addNewCClient(coclient);
					if(newId > 0)
						if(paths != null)
							for (File file : paths){
								String[] filename = (file.getName()).split("\\.");
								String extFile = filename[filename.length - 1];
								File f = new File(file.getAbsolutePath());// Renombra el archivo
								f.renameTo(new File(destinationPath + "/" + newclientid + "." + extFile));
								String path = f.getAbsolutePath();
								String onlyfile = f.getName();
								photo = path.replaceAll(onlyfile + "$", "") + newclientid + "." + extFile;
							}
					Clients clientupdate = new Clients();
					clientupdate.setClientid((int) newclientid);
					clientupdate.setClient(client);
					clientupdate.setAddress1(address1);
					clientupdate.setCity(city);
					clientupdate.setCountry(country);
					clientupdate.setEmail(email);
					clientupdate.setPhone(phone);
					clientupdate.setState(state);
					clientupdate.setZipcode(cp);
					clientupdate.setStatus(status);
					clientupdate.setCellphone(cellphone);
					clientupdate.setPhoto(photo);
					clientupdate.setPersonafiscalid(typeofperson);
					clientupdate.setWebpage(webpage);
					clientupdate.setContactperson(contactperson);
					clientupdate.setRel_with(rel_with);
					clientupdate.setRef_by(ref_by);
					
					if (!Functions.isEmpty(birthdate))
						clientupdate.setBirthdate(Functions.parseFecha(birthdate, "yyyy-MM-dd"));
					clientupdate.setComments(comments);
					clientsService.updateClient(clientupdate);
					try {
						if (newId > 0) {
							resp = "msg_data_saved";
							FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
						} else
							resp = "err_record_no_saved";
					} catch (IOException ex) {
						System.out.println("Exception in addNewClient(): " + ex.getMessage());
					}
					//Redes sociales
					String[] arrSNTmp = arrSN.split(" ");
					if(!Functions.isEmpty(arrSNTmp[0]))
						for(int s=0;s<arrSNTmp.length;s++){
							String[] arrSN2 = arrSNTmp[s].split("\\|\\|");
							Socialnetworkclient snc = new Socialnetworkclient();
							snc.setCompanyclientid((int) newId);
							snc.setSocialnetworkid(Functions.toInt(arrSN2[0]));
							snc.setAddress(arrSN2[1]);
				        	long succ = socialnetworkclientService.addNewSNCWork(snc);
							if (succ > 0)
								resp = newclientid+"";
							else
								resp = "err_record_no_saved";
						}
				}
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getClientsById")
	public @ResponseBody Object[] getClientsById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if(sess!=null){
			int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
			if(id>0){
				List<Clients> info = clientsService.getAll("FROM Clients WHERE clientid=" + id);
				data.put("info", info);
	
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int role = userDto.getRole();
				String whereClause = "";
				if (role!=ROLE_SYSADMIN)
					whereClause = " AND companyid="+userDto.getCompanyid();
				List<Companyclients> cclient = companyclientService
					.getAll("FROM Companyclients WHERE clientid=" + id + whereClause);
				String ccid = (cclient.get(0).getCompanyclientid())+"";
				List<Socialnetworks> snw = socialnetworkService.getAll("SELECT snc, snw.socialnetwork FROM Socialnetworkclient AS snc JOIN Socialnetworks AS snw ON snw.socialnetworkid=snc.socialnetworkid WHERE snc.companyclientid="+ccid);
				data.put("snw", snw);

				//TODO Si los datos de Páis, estado y ciudad son númericos, buscará sus descripciones
				String cdIsNum = info.get(0).getCity();
				List<?> pec = null;
				if(Functions.isNumeric(cdIsNum)){
					pec = dao.sqlHQL("SELECT pa.pais, es.estado, cd.ciudad FROM Ciudades cd"
						+ " LEFT JOIN Estados es ON es.estadoid=cd.estadoid"
						+ " LEFT JOIN Paises pa ON pa.paisid=es.paisid"
						+ " WHERE cd.ciudadid=" + cdIsNum, null);
				}
				data.put("pec", pec);
			}
		}
		return new Object[] {data};
	}

	@RequestMapping(value = "/getClients")
	public @ResponseBody Object[] getClients(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			List<Clients> clientsList = clientsService.getAll("FROM Clients WHERE // TODO status=1 ORDER BY client ASC");
			return new Object[] { clientsList };
		}
		return null;
	}

	@RequestMapping(value = "/getClientList")
	@ResponseBody
	public Object[] getClientList(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if(sess!=null){
			String clause = "";
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			int role = userDto.getRole();
			if(Functions.toInt(userDto.getUsertype())==1)	//1=Cliente; 0 ó null = Abogado
				clause = " AND ccid=" + userDto.getLinkedclientid();
			else if(role!=ROLE_SYSADMIN && role!=ROLE_CJADMIN)
				clause = " AND companyid=" + userDto.getCompanyid();
			List<Clients> data = clientsService.getAll("SELECT clientid,client,address1,city,state"
				+ " FROM Clients WHERE clientid IN(SELECT DISTINCT(clientid) FROM Companyclients"
				+ clause.replaceAll("^ AND ", " WHERE ") + ") ORDER BY client ASC, city ASC");
			return new Object[] {data};
		}
		return null;
	}

	@RequestMapping(value = "/getClientListCC")
	@ResponseBody
	public Object[] getClientList2(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			Map<String, Object> dataMap = new HashMap<String, Object>();
			int role= userDto.getRole(), isAdmin = 0, row = 0;
			String query = "SELECT cc.companyclientid, c.client, c.address1, c.city, c.state"
					+ " FROM Clients AS c LEFT JOIN Companyclients AS cc ON cc.clientid=c.clientid"
					+ " LEFT JOIN Companies AS co ON co.companyid=cc.companyid"
					+ " WHERE co.companyid=" + userDto.getCompanyid()
					+ " ORDER BY c.client ASC, co.company ASC";
			if (role==ROLE_SYSADMIN || role==ROLE_CJADMIN){
				query = "SELECT cc.companyclientid, c.client, c.address1, c.city, c.state, co.company"
					+ " FROM Clients AS c"
					+ " LEFT JOIN Companyclients AS cc ON cc.clientid=c.clientid"
					+ " LEFT JOIN Companies AS co ON co.companyid=cc.companyid"
					+ " ORDER BY c.client ASC";
				isAdmin = 1;
			}

			JsonObject data = new JsonObject();
			List<Clients> allData = clientsService.getAll(query);
			Object[] tmp = allData.toArray();
			for (Object cdata:tmp){
				JsonObject rec = new JsonObject();
				Object[] obj= (Object[]) cdata;
				rec.addProperty("ccid", (obj[0]==null?"":obj[0])+"");
				rec.addProperty("client",(obj[1]==null?"":obj[1])+"");
				rec.addProperty("address",(obj[2]==null?"":obj[2])+"");
				rec.addProperty("city", (obj[3]==null?"":obj[3])+"");
				rec.addProperty("state",(obj[4]==null?"":obj[4])+"");
				if(isAdmin==1)
					rec.addProperty("firm",(obj[5]==null?"":obj[5])+"");
				data.add(row+"", rec);
				row += 1;
			}
			dataMap.put("data", Functions.toStr(data));
			return new Object[] {dataMap};
		}
		return null;
	}

	@RequestMapping(value = "/getClientByCCId")
	public @ResponseBody List<Clients> getClientByCCId(int ccid){
		List<Clients> clientdata = null;
		List<Companyclients> cc = companyclientService
			.getAll("FROM Companyclients WHERE companyclientid=" + ccid);
		if(cc.size()>0)
			clientdata = clientsService
				.getAll("FROM Clients WHERE clientid=" + cc.get(0).getClientid());
		return clientdata;
	}

	@RequestMapping(value = "/getRelClientTrial")
	public @ResponseBody Object[] getRelClientTrial(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		int clid = (req.getParameter("clid") == null) ? 0 : Functions.toInt(req.getParameter("clid").trim());
		List<Juicios> data = null;
		if (sess != null){
			HashMap<String, String> hasAccess = commonsCtrll.valAccessClientDocs(req,clid,0);
			if(Functions.isEmpty(hasAccess.get("clientid")))
				return new Object[] {data};
			data = juiciosService.getAll("FROM Juicios WHERE companyclientid IN("
				+ hasAccess.get("companyclientid") + ") AND STATUS!=0 ORDER BY juicioid DESC");
		}
		return new Object[] {data};
	}

	@RequestMapping(value = "/getRelClientAppeal")
	public @ResponseBody Object[] getRelClientAppeal(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		int clid = (req.getParameter("clid") == null) ? 0 : Functions.toInt(req.getParameter("clid").trim());
		List<Apelaciones> data = null;
		if (sess != null){
			HashMap<String, String> hasAccess = commonsCtrll.getAllTrials(req,clid,0);
			if(Functions.isEmpty(hasAccess.get("juiciosid")))
				return new Object[] {data}; 
			data = apelacionesService.getAll("FROM Apelaciones WHERE juicioid IN("
				+ hasAccess.get("juiciosid") + ") ORDER BY apelacionid DESC");
		}
		return new Object[] {data};
	}

	@RequestMapping(value = "/getTrialList")
	@ResponseBody
	public Object[] getTrialList(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		List<Juicios> info = null;
		if (sess != null) {
			int clientid = (req.getParameter("clientid") == null) ? 0 : Functions.toInt(req.getParameter("clientid").trim());

			HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
			clientid = Functions.toInt(acclt.get("clientid"));

			String trials = commonsCtrll.getAllTrials(req,clientid,0).get("juiciosid");
			if(!Functions.isEmpty(trials))
				info = juiciosService.getAll("FROM Juicios WHERE juicioid IN(" + trials
					+ ") AND status=1 ORDER BY juicioid DESC"); // Sólo juicios con estatus de activo (1).
		}
		return new Object[] { info };
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/updateClients")
	public void updateClients(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim()),
					status = (req.getParameter("status") == null) ? 0
						: Functions.toInt(req.getParameter("status").trim()),
					typeofperson = (req.getParameter("typeofperson") == null) ? 0
						: Functions.toInt(req.getParameter("typeofperson").trim());
				String client = (req.getParameter("client") == null) ? "" : req.getParameter("client").trim(),
					address1 = (req.getParameter("address1") == null) ? "" : req.getParameter("address1").trim(),
					city = (req.getParameter("city") == null) ? "" : req.getParameter("city").trim(),
					country = (req.getParameter("country") == null) ? "" : req.getParameter("country").trim(),
					email = (req.getParameter("email") == null) ? "" : req.getParameter("email").trim(),
					phone = (req.getParameter("phone") == null) ? "" : req.getParameter("phone").trim(),
					state = (req.getParameter("state") == null) ? "" : req.getParameter("state").trim(),
					cp = (req.getParameter("cp") == null) ? "" : req.getParameter("cp").trim(),
					cellphone = (req.getParameter("cellphone") == null) ? "" : req.getParameter("cellphone").trim(),
					birthdate = (req.getParameter("birthdate") == null) ? "" : req.getParameter("birthdate").trim(),
					comments = (req.getParameter("comments") == null) ? "" : req.getParameter("comments").trim(),
					photo = (req.getParameter("photo") == null) ? "" : req.getParameter("photo").trim(),
					webpage = (req.getParameter("webpage") == null) ? "" : req.getParameter("webpage").trim(),
					arrSN = (req.getParameter("arrSN") == null) ? "" : req.getParameter("arrSN").trim(),
					contactperson = (req.getParameter("contactperson") == null) ? "" : req.getParameter("contactperson").trim(),
					rel_with = (req.getParameter("rel_with") == null) ? "" : req.getParameter("rel_with").trim(),
					ref_by = (req.getParameter("ref_by") == null) ? "" : req.getParameter("ref_by").trim();

				if (Functions.isEmpty(client) || Functions.isEmpty(address1) || Functions.isEmpty(email)) {
					try {
						res.getWriter().write("msg_empty_data");
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				// Guarda archivos relacionados con el registro
				String[] nameFiles = null;
				Enumeration enumeration = req.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String parameterName = (String) enumeration.nextElement();
					if (parameterName.contains("fileuploadx_") && !Functions.isEmpty(req.getParameter(parameterName))) {
						if (nameFiles == null)
							nameFiles = new String[1];
						else
							nameFiles = (String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
						nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
					}
				}

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
						"doctos" + FileSystems.getDefault().getSeparator() + "images/clients", true);
				destinationPath = Functions.addPath(destinationPath, "", true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);

				if (paths != null) {
					for (File file : paths) {
						String[] filename = (file.getName()).split("\\.");
						String extFile = filename[filename.length - 1];
						File f = new File(file.getAbsolutePath());// Renombra el archivo
						f.renameTo(new File(destinationPath + "/" + id + "." + extFile));
						String path = f.getAbsolutePath();
						String onlyfile = f.getName();
						photo = path.replaceAll(onlyfile + "$", "") + id + "." + extFile;
						break;// Toma solo la primera imagen
					}
				}
				Clients clients = new Clients();
				clients.setClientid(id);
				clients.setClient(client);
				clients.setAddress1(address1);
				clients.setCity(city);
				clients.setCountry(country);
				clients.setEmail(email);
				clients.setPhone(phone);
				clients.setState(state);
				clients.setZipcode(cp);
				clients.setStatus(status);
				clients.setCellphone(cellphone);
				clients.setPhoto(photo);
				clients.setPersonafiscalid(typeofperson);
				clients.setWebpage(webpage);
				clients.setContactperson(contactperson);
				clients.setRel_with(rel_with);
				clients.setRef_by(ref_by);
				if(!Functions.isEmpty(birthdate))
					clients.setBirthdate(Functions.parseFecha(birthdate, "yyyy-MM-dd"));
				clients.setComments(comments);
				clientsService.updateClient(clients);
				resp = "msg_data_saved";

				//Redes sociales
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				List<Companyclients> cclient = companyclientService
					.getAll("FROM Companyclients WHERE companyid="+userDto.getCompanyid()+" AND clientid="+id);
				int ccid = (cclient.get(0).getCompanyclientid());
				String[] arrSNTmp = arrSN.split(" ");
				String validSN="0";
				if(!Functions.isEmpty(arrSNTmp[0])){
					validSN="";
					for(int s=0;s<arrSNTmp.length;s++){
						String[] arrSN2 = arrSNTmp[s].split("\\|\\|");
						Socialnetworkclient snc = new Socialnetworkclient();
						snc.setCompanyclientid(ccid);
						snc.setSocialnetworkid(Functions.toInt(arrSN2[0]));
						snc.setAddress(arrSN2[1]);
						if(arrSN2.length>2){//Actualiza el registro
							snc.setSnid(Functions.toLong(arrSN2[2]));
							socialnetworkclientService.updateSNC(snc);
							validSN+=arrSN2[2]+",";
				        }else{//Agrega en caso de no existir
				        	long succ = socialnetworkclientService.addNewSNCWork(snc);
				        	if(succ > 0)validSN+=succ+",";
				        }
					}
					validSN=validSN.replaceAll(".$", "");
				}
				try{
					FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
				}catch (IOException e1){
					e1.printStackTrace();
				}
				resp = "msg_data_saved";
				//Elimina las redes sociales inexistentes 
				socialnetworkclientService.deleteNotIn(ccid+"",validSN);
				try {
					res.getWriter().write(resp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "/deleteClients")
	public void deleteClients(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
				clientsService.deleteClient(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/companyclients")
	public ModelAndView companyclients(HttpServletRequest req, HttpServletResponse res) {
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
				/*int userid = (int) userDto.getId();
				Users user = new Users();
				user = userService.getUserById(userid);*/

				// Obtiene los privilegios del módulo
				@SuppressWarnings("rawtypes")
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "companyclients");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				String whereClause = "";
				if ((role==ROLE_SYSADMIN||role==ROLE_CJADMIN))
					whereClause = "";
				else
					whereClause = " WHERE companyid=" + userDto.getCompanyid();
				List<Companyclients> coClient = companyclientService
						.getAll("FROM Companyclients" + whereClause);
				forModel.put("coClient", coClient);

				List<Clients> clients = clientsService.getAll("FROM Clients ORDER BY clientid ASC");
				forModel.put("clients", clients);

				List<Companies> companies = companiesService.getAll("FROM Companies ORDER BY companyid ASC");
				forModel.put("companies", companies);

				return new ModelAndView("companyclients", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/addNewCoClient")
	public void addNewCoClient(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int clientid = (req.getParameter("client") == null) ? 0
						: Functions.toInt(req.getParameter("client").trim()),
						companyid = (req.getParameter("company") == null) ? 0
								: Functions.toInt(req.getParameter("company").trim());
				if (clientid == 0 || companyid == 0) {
					try {
						res.getWriter().write("msg_empty_data");
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				List<Companyclients> existRel = companyclientService
						.getAll("FROM Companyclients WHERE companyid=" + companyid + " AND clientid=" + clientid);
				if (existRel.size() > 0) {
					resp = "err_duplicated_companyclient";
				} else {
					Companyclients coclient = new Companyclients();
					coclient.setCompanyid(companyid);
					coclient.setClientid(clientid);
					long succ = companyclientService.addNewCClient(coclient);
					if (succ > 0)
						resp = "msg_data_saved";
				}
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewCClient(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getCoClientById")
	public Object[] getCoClientById(HttpServletRequest req, HttpServletResponse res) {
		int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
		Map<String, Object> data = new HashMap<String, Object>();
		if (id > 0) {
			List<Companyclients> info = companyclientService.getAll("FROM Companyclients WHERE companyclientid=" + id);
			data.put("info", info);
		}
		return new Object[] {data};
	}

	@ResponseBody
	@RequestMapping(value = "/updateCoClient")
	public void updateCoClient(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = (req.getParameter("id") == null) ? 0 : Functions.toInt(req.getParameter("id").trim()),
						clientid = (req.getParameter("client") == null) ? 0
								: Functions.toInt(req.getParameter("client").trim()),
						companyid = (req.getParameter("company") == null) ? 0
								: Functions.toInt(req.getParameter("company").trim());
				if (clientid == 0 || companyid == 0) {
					try {
						res.getWriter().write("msg_empty_data");
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				List<Companyclients> existRel = companyclientService
						.getAll("FROM Companyclients WHERE companyid=" + companyid + " AND clientid=" + clientid);
				if (existRel.size() > 0) {
					resp = "err_duplicated_companyclient";
				} else {
					Companyclients coclient = new Companyclients();
					coclient.setCompanyclientid(id);
					coclient.setCompanyid(companyid);
					coclient.setClientid(clientid);
					companyclientService.updateCClient(coclient);
					resp = "msg_data_saved";
				}
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteCoClient")
	public void deleteCoClient(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
				companyclientService.deleteCClient(id);
			}
		}
	}

	@RequestMapping(value = "/previewuploadImgfile", method = RequestMethod.POST)
	public @ResponseBody String previewuploadImgfile(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Integer id = Functions.toInt(request.getParameter("id"));
		Integer type = Functions.toInt(request.getParameter("type"));

		String imgPath = "images/clientes/";
		@SuppressWarnings("unused")
		String rootPath = Functions.getRootPath() + imgPath;
		JsonArray jsonArray = new JsonArray();
		JsonObject json = null;
		// Getting uploaded files from the request object
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put("idregister", id);
		parameters.put("catalogtype", type);
		List<Clients> clientData = clientsService.getAll("FROM Clients WHERE clientid=" + id);

		Path source = Paths.get(clientData.get(0).getPhoto());
		if (!Functions.isEmpty(source)) {
			json = new JsonObject();
			json.addProperty("name", id + ".jpg");
			json.addProperty("size", 0);
			json.addProperty("location", clientData.get(0).getPhoto());
			jsonArray.add(json);

			String result = new Gson().toJson(jsonArray);
			return result;
		}
		return null;
	}
}