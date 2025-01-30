package com.aj.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aj.model.Amparos;
import com.aj.model.Apelaciones;
import com.aj.model.Ciudades;
import com.aj.model.Clients;
import com.aj.model.Companyclients;
import com.aj.model.Juicios;
import com.aj.model.Juzgados;
import com.aj.model.ECalendar;
import com.aj.model.Materias;
import com.aj.model.Menu;
import com.aj.model.Movimientos;
import com.aj.model.Privileges;
import com.aj.model.Salas;
import com.aj.model.Uploadfiles;
import com.aj.model.Users;
import com.aj.service.AccessDbJAService;
import com.aj.service.AmparosService;
import com.aj.service.ApelacionesService;
import com.aj.service.CiudadesService;
import com.aj.service.ClientsService;
import com.aj.service.CompanyclientsService;
import com.aj.service.ECalendarService;
import com.aj.service.JuiciosService;
import com.aj.service.JuzgadosService;
import com.aj.service.MateriasService;
import com.aj.service.MovimientosService;
import com.aj.service.PrivilegesService;
import com.aj.service.SalasService;
import com.aj.service.UserService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class AppealsController {
	@Autowired
	private CommonController commonsCtrll;

	@Autowired
	private NotificationsController notificationsCtrll;

	@Autowired
	public ApelacionesService apelacionesService;

	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public PrivilegesService privilegesService;

	@Autowired
	public MovimientosService movimientosService;

	@Autowired
	public AmparosService amparosService;

	@Autowired
	public CompanyclientsService companyclientsService;

	@Autowired
	public UserService userService;

	@Autowired
	public JuzgadosService juzgadosService;

	@Autowired
	public CiudadesService ciudadesService;

	@Autowired
	public MateriasService materiasService;

	@Autowired
	public SalasService salasService;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	public ECalendarService eCalendarService;

	@SuppressWarnings("rawtypes")
	@Autowired
	public AccessDbJAService dao;

	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static final int ROLE_SYSADMIN= 1, ROLE_CJADMIN = 2, ROLE_FIRMADMIN=3;

	@RequestMapping(value = "/apelaciones")
	public ModelAndView apelaciones(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP_1.1.
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				int clientid = (req.getParameter("clid") == null) ? 0
						: Functions.toInt(req.getParameter("clid").trim()),//Variable para indicar que proviene de Home
					appealid = (req.getParameter("rid") == null) ? 0
						: Functions.toInt(req.getParameter("rid").trim());	//Variable para indicar análisis de una apelación (en caso de ser solicitado por su id)
				List<Apelaciones> apList = null;
				List<Materias> matList = null;
				List<Ciudades> cityList = null;
				List<Salas> roomList = null;

				// Obtiene los privilegios del módulo
				Map<String, Object> forModel = new HashMap<String, Object>();
				@SuppressWarnings({ "unchecked", "rawtypes" })
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "apelaciones");
				@SuppressWarnings("unchecked")
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				forModel.put("menu", menu);

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));
				
				HashMap<String, String> trialsData = commonsCtrll.getAllTrials(req,clientid,0);
				String allTrials = trialsData.get("juiciosid");
				if(Functions.isEmpty(allTrials))
					allTrials="0";
/*
				List<Juicios> juicios = juiciosService.getAll("FROM Juicios WHERE juicioid IN(" + allTrials + ")");
				forModel.put("juicios", juicios);

				if(juicios.size()>0){*/
				if(!Functions.isEmpty(allTrials)){
					String matIds = "", cityIds = "", roomIds = "", appealquery = "";
					if(appealid>0)
						appealquery=" AND apelacionid="+appealid;
					apList = apelacionesService.getAll("FROM Apelaciones WHERE juicioid IN(" + allTrials + ")"
						+ appealquery + "ORDER BY apelacionid DESC");
					if(apList.size()>0){
						for (int i = 0; i < apList.size(); i++){
							matIds += apList.get(i).getMateriaid()+",";
							cityIds+= apList.get(i).getCiudadid() +",";
							roomIds+=apList.get(i).getSalaid()+",";
						}
						matList = materiasService.getAll("FROM Materias WHERE materiaid IN ("
							+ Functions.noDuplicatesStrArr(matIds.split(",")) + ")");
						cityList = ciudadesService.getAll("FROM Ciudades WHERE ciudadid IN ("
							+ Functions.noDuplicatesStrArr(cityIds.split(","))+ ")");
						roomList = salasService.getAll("FROM Salas WHERE salaid IN ("
							+ Functions.noDuplicatesStrArr(roomIds.split(","))+ ")");
					}
				}
				forModel.put("appeal", apList);
				forModel.put("materias", matList);
				forModel.put("cities", cityList);
				forModel.put("salas", roomList);
				return new ModelAndView("apelaciones", forModel);
			}
		return new ModelAndView("login");
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addNewAppeal")
	public @ResponseBody void addNewAppeal(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "";
		validateData:{
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int trial = (req.getParameter("trial") == null) ? 0
						: Functions.toInt(req.getParameter("trial").trim()),
					room = (req.getParameter("room") == null) ? 0
						: Functions.toInt(req.getParameter("room").trim()),
					matter = (req.getParameter("matter") == null) ? 0
						: Functions.toInt(req.getParameter("matter").trim()),
					city = (req.getParameter("city") == null) ? 0
						: Functions.toInt(req.getParameter("city").trim()),
					apladhesiva = (req.getParameter("apladhesiva") == null) ? 0
						: Functions.toInt(req.getParameter("apladhesiva").trim());
				String clienttype = (req.getParameter("clienttype") == null) ? ""
						: req.getParameter("clienttype").trim(),
					clientName = (req.getParameter("clientName") == null) ? ""
						: req.getParameter("clientName").trim(),
					handle = (req.getParameter("handle") == null) ? "" : req.getParameter("handle").trim(),
					speaker = (req.getParameter("speaker") == null) ? "" : req.getParameter("speaker").trim(),
					resolution = (req.getParameter("resolution") == null) ? ""
						: req.getParameter("resolution").trim();

				resp=(Functions.isEmpty(clientName)?"err_select_client"
					: room==0?"err_select_room":matter==0?"err_select_matter"
					: city==0?"err_select_city":Functions.isEmpty(speaker)?"err_enter_speaker"
					:apladhesiva<0?"err_enter_apadhesive":"");
				if(!Functions.isEmpty(resp))
					break validateData;
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int companyid = userDto.getCompanyid();

				// guardamos archivos relacionados con el registro
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

				List<Apelaciones> existToca = apelacionesService
						.getAll("FROM Apelaciones WHERE toca='" + handle + "' AND companyid=" + companyid);
				if (existToca == null){
					resp = "err_handle_exists";
					break validateData;
				}
				Apelaciones apelacion = new Apelaciones();
				apelacion.setToca(handle);
				apelacion.setCompanyid(companyid);
				apelacion.setJuicioid(trial);
				apelacion.setMateriaid(matter);
				apelacion.setSalaid(room);
				apelacion.setCiudadid(city);
				apelacion.setPaisid(1);// Default
				apelacion.setPonente(speaker);
				apelacion.setResolucion(resolution);
				if (clienttype.equals("Apelante")) {
					apelacion.setApelante(clientName);
				} else if (clienttype.equals("Apelado")) {
					apelacion.setApelado(clientName);
				}
				apelacion.setApelacionadhesiva(apladhesiva);
				long succ = apelacionesService.addNewApelacion(apelacion);

				// mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator() + "Apelaciones", true);
				destinationPath = Functions.addPath(destinationPath, "" + succ, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				JsonObject jsonFiles = new JsonObject();
				JsonObject fileRec = new JsonObject();
				if (paths != null){
					int idx = 0;
					for (File file : paths) {
						entidad = new Uploadfiles();
						entidad.setCatalogtype(2);//2=apelaciones
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(succ));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
//TODO Notify add-apl (ini)
						JsonObject fileData = new JsonObject();
			    		fileData.addProperty("name", file.getName());
			    		fileData.addProperty("size", file.length());
			    		fileData.addProperty("status", "1");	//1=Nuevo
			    		fileRec.add(idx+"", fileData);
			    		idx++;
					}
					jsonFiles.add("file", fileRec);
				}
				try{
					FileUtils.deleteDirectory(new File(tmpPath));
					System.err.println("No se pudo eliminar la carpeta temporal");
				}catch (IOException e){
					e.printStackTrace();
				}
				if (succ > 0) {
					resp="msg_data_saved";
					List<Apelaciones> oldarray = new ArrayList<Apelaciones>();
					apelacion.setApelacionid((int) succ);
					@SuppressWarnings("unchecked")
					Menu modRefId = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link LIKE 'apelaciones' OR link LIKE 'apelaciones.jet'", null);
					Gson gson = new Gson();
				    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(apelacion);
				    long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 2,	//2=apelaciones
						(int) succ, 0, (int) userDto.getId(), 0,	//0=nuevo
						"", (int)userDto.getCompanyid(), jsonFiles, handle, oldjson, newdata);
					if(nsucc<1)
						System.err.println("Sin cambios o no se pudo almacenar la notificación de la apelación " + handle);
				}
			}
		}
		try{
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getDetailsAppeal")
	public @ResponseBody Object[] getDetailsAppeal(HttpServletRequest req, HttpServletResponse res) {
		int id = req.getParameter("id") == null ? 0 
			: Functions.toInt(req.getParameter("id").trim());
		Map<String, Object> data = new HashMap<String, Object>();
		if (id > 0) {
			List<Apelaciones> info = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid=" + id);
			data.put("detail", info);
			List<Juicios> juicio = juiciosService
				.getAll("FROM Juicios WHERE juicioid=" + info.get(0).getJuicioid());
			data.put("juicio", juicio);
			List<Companyclients> cclient = companyclientsService.getAll("FROM Companyclients WHERE companyclientid="
				+ juicio.get(0).getCompanyclientid());
			data.put("ccid", cclient);
			List<Clients> clients = clientsService.getAll("SELECT client FROM Clients WHERE clientid=" + cclient.get(0).getClientid());
			data.put("clientName", clients.get(0)+"");

			int stateid = 0;
			if(info.get(0).getCiudadid()>0){
				List<Ciudades> cd = ciudadesService.getAll("FROM Ciudades WHERE ciudadid=" + info.get(0).getCiudadid());
				stateid=cd.get(0).getEstadoid();
			}
			data.put("stateid", stateid);
		}
		return new Object[] { data };
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateAppeal")
	public void updateAppeal(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "";
		validateData:{
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = (req.getParameter("edid") == null) ? 0 : Functions.toInt(req.getParameter("edid").trim()),
					trialid = (req.getParameter("trial") == null) ? 0
						: Functions.toInt(req.getParameter("trial").trim()),
					room = (req.getParameter("room") == null) ? 0
						: Functions.toInt(req.getParameter("room").trim()),
					matter = (req.getParameter("matter") == null) ? 0
						: Functions.toInt(req.getParameter("matter").trim()),
					city = (req.getParameter("city") == null) ? 0
						: Functions.toInt(req.getParameter("city").trim()),
					apladhesiva = (req.getParameter("apladhesiva") == null) ? 0
						: Functions.toInt(req.getParameter("apladhesiva").trim());
				String handle = (req.getParameter("handle") == null) ? "" : req.getParameter("handle").trim(),
					speaker = (req.getParameter("speaker") == null) ? "" : req.getParameter("speaker").trim(),
					resolution = (req.getParameter("resolution") == null) ? ""
						: req.getParameter("resolution").trim(),
					clienttype = (req.getParameter("clienttype") == null) ? ""
						: req.getParameter("clienttype").trim(),
					clientName = (req.getParameter("clientName") == null) ? ""
						: req.getParameter("clientName").trim();
				resp=(Functions.isEmpty(clientName)?"err_select_client"
					:trialid==0?"err_enter_trial"
					:room==0?"err_select_room"
					:matter==0?"err_select_matter"
					:city==0?"err_select_city"
					:Functions.isEmpty(speaker)?"err_enter_speaker"
					:apladhesiva<0?"err_enter_apadhesive":"");
				if(!Functions.isEmpty(resp))
					break validateData;
//TODO Notify Update Apl-1 (una línea)
List<Apelaciones> olddata =  apelacionesService.getAll("FROM Apelaciones WHERE apelacionid="+id);
				
				// Guarda archivos relacionados con el registro
				String[] nameFiles = null;
				Enumeration enumeration = req.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String parameterName = (String) enumeration.nextElement();
					if(parameterName.contains("fileuploadx_") && !Functions.isEmpty(req.getParameter(parameterName))){
						if (nameFiles == null)
							nameFiles = new String[1];
						else
							nameFiles = (String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
						nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
					}
				}

				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int userid = (int) userDto.getId();
				Users user = new Users();
				user = userService.getUserById(userid);
				int companyid = user.getCompanyid();
				// int countryid=Functions.toInt(user.getCountry());

				Apelaciones apelacion = new Apelaciones();
				apelacion.setApelacionid(id);
				apelacion.setToca(handle);
				apelacion.setCompanyid(companyid);
				apelacion.setJuicioid(trialid);
				apelacion.setMateriaid(matter);
				apelacion.setSalaid(room);
				apelacion.setCiudadid(city);
				apelacion.setPaisid(1);// Default
				apelacion.setPonente(speaker);
				apelacion.setResolucion(resolution);
				if (clienttype.equals("Apelante")) {
					apelacion.setApelante(clientName);
					apelacion.setApelado("");
				} else if (clienttype.equals("Apelado")) {
					apelacion.setApelado(clientName);
					apelacion.setApelante("");
				}
				apelacion.setApelacionadhesiva(apladhesiva);
				apelacionesService.updateApelacion(apelacion);

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator() + "Apelaciones", true);
				destinationPath = Functions.addPath(destinationPath, "" + id, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				JsonObject jsonFiles = new JsonObject();
				JsonObject fileRec = new JsonObject();
				if (paths != null){
					int idx = 0;
					for (File file : paths) {
						entidad = new Uploadfiles();
						entidad.setCatalogtype(2);//2=apelaciones
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(id));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
//TODO Notify Update Apl-2 (ini)
						JsonObject fileData = new JsonObject();
			    		fileData.addProperty("name", file.getName());
			    		fileData.addProperty("size", file.length());
			    		fileData.addProperty("status", "1");	//1=Nuevo
			    		fileRec.add(idx+"", fileData);
			    		idx++;
					}
					jsonFiles.add("file", fileRec);
				}
				try{
					FileUtils.deleteDirectory(new File(tmpPath));
					System.err.println("No se pudo eliminar la carpeta temporal");
				}catch (IOException e){
					e.printStackTrace();
				}
//TODO analizar retorno de RESP en JS (es "true" o "msg_xxx")
				resp="msg_data_saved";
				List<Apelaciones> oldarray = new ArrayList<Apelaciones>(olddata);
				Gson gson = new Gson();
			    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(apelacion);
			    @SuppressWarnings("unchecked")
				Menu modRefId = (Menu) dao
					.sqlHQLEntity("FROM Menu WHERE link LIKE 'apelaciones' OR link LIKE 'apelaciones.jet'", null);

			    List<Juicios> tmptrial = juiciosService.getAll("FROM Juicios WHERE juicioid="
			    	+ apelacion.getJuicioid());
			    Integer ccid = 0;
			    if(tmptrial.size()>0)
			    	ccid = tmptrial.get(0).getCompanyclientid();

				long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 2,	//2=apelaciones
					trialid, ccid, userid, 1,	//1=Edición
					"", companyid, jsonFiles, handle, oldjson, newdata);
				if(nsucc<1)
					System.err.println("Sin cambios o no se pudo almacenar la notificación del amparo " + handle);
//TODO Notify Update-2 (fin)
				resp = "true";
			}
		}}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteAppeal")
	public void deleteAppeal(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int appealid = req.getParameter("id") == "" ? 0 : Functions.toInt(req.getParameter("id").trim());
				apelacionesService.deleteApelacion(appealid);
			}
		}
	}

	@RequestMapping(value = "/getAppealList")
	@ResponseBody
	public Object[] getAppeals(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			int role = userDto.getRole();

			String whereClause = "";
			if(role!=ROLE_SYSADMIN && role!=ROLE_CJADMIN) //Sin resticciones para SysAdmin o supervisor del sistema
				whereClause = " WHERE companyid=" + userDto.getCompanyid();

			List<Apelaciones> list = apelacionesService
				.getAll("FROM Apelaciones" + whereClause + " ORDER BY apelacionid DESC");
			return new Object[] { list };
		}
		return null;
	}

	@RequestMapping(value = "/appealsdashboard")
	public ModelAndView appealsdashboard(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP_1.1.
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				String appealid = (req.getParameter("rid") == null) ? "0" : req.getParameter("rid").trim();
				int clientid = 0;

				// Obtiene los privilegios del módulo
				List<Privileges> lp = null;
				//Long dashboardVis = (long) 0;
				Map<String, Object> forModel = new HashMap<String, Object>();
				
				// se comento temporal x nueva version menu permisos
				/*
				List<Modules> lm = modulesService.getAll("FROM Modules WHERE name='appealsdashboard'");
				if(lm!=null&&lm.size()>0){
					String module = Long.toString(lm.get(0).getModuleid());
					String query = "FROM Privileges WHERE roleid=" + role + " AND module='" + module + "'";
					lp = privilegesService.getAll(query);
					dashboardVis=lp.get(0).getVis();
				}
				if(dashboardVis == 0)
					return new ModelAndView("apelaciones", forModel);
				*/
				forModel.put("listp", lp);
				if(appealid.equals("0"))return new ModelAndView("appealsdashboard", forModel);
				
				List<Apelaciones> mainAppeal = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid=" + appealid);
				
				List<Juicios> juicio = null;
				List<Clients> client = null;
				List<Juzgados> court = null;
				List<Materias> mat = null;
				List<Ciudades> city= null;
				List<Salas> room  = null;
				List<Movimientos> movList = null; 
				List<Apelaciones> appeals = null;
				List<Amparos> protections = null;

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));

				//Analiza si el usuario tiene permisos para ver los datos.
				HashMap<String, String> trialData = commonsCtrll.getAllTrials(req,clientid,mainAppeal.get(0).getJuicioid());
				String originTrial = trialData.get("juiciosid"), whereClause = "";
				
				if(!Functions.isEmpty(originTrial)){
					juicio = juiciosService.getAll("FROM Juicios WHERE juicioid=" + originTrial); 
					client = clientsService.getAll("FROM Clients WHERE clientid=" + trialData.get("clientsid"));
					court = juzgadosService.getAll("FROM Juzgados WHERE juzgadoid="+juicio.get(0).getJuzgadoid());
					mat = materiasService.getAll("FROM Materias WHERE materiaid=" + juicio.get(0).getMateriaid());
					city= ciudadesService.getAll("FROM Ciudades WHERE ciudadid="+mainAppeal.get(0).getCiudadid());
					room= salasService.getAll("FROM Salas WHERE salaid=" + mainAppeal.get(0).getSalaid());
					movList = movimientosService
						.getAll("FROM Movimientos" + " WHERE apelacionid=" + appealid + " ORDER BY LEAST (fechapresentacion,fechaacuerdo,fechanotificacion)");
					appeals = apelacionesService
						.getAll("FROM Apelaciones WHERE juicioid=" + originTrial + " OR apelacionid=" + appealid + " ORDER BY apelacionid ASC");
					protections=amparosService
						.getAll("FROM Amparos WHERE juicioid=" + originTrial + " OR apelacionid=" + appealid + " ORDER BY amparoid ASC");
				}else{
					mainAppeal = null;
				}
				forModel.put("appeal", mainAppeal);
				forModel.put("juicio", juicio);
				forModel.put("client", client);
				forModel.put("juzgado", court);
				forModel.put("materia", mat);
				forModel.put("cities", city);
				forModel.put("sala", room);
				forModel.put("movList", movList);
				forModel.put("appeals", appeals);
				forModel.put("amparos", protections);

				String movsIds = "";
				if (movList != null) {
					for (int i = 0; i < movList.size(); i++)
						movsIds += movList.get(i).getMovimientoid() + ",";
					movsIds = movsIds.replaceAll(".$", "");
				}

				HashMap<String, Object> parameters = new HashMap<>();
				/*parameters.put("idregister", juicioid);
				parameters.put("catalogtype", 2);//2=Apelaciones
				List<Uploadfiles> doctos = dao.sqlHQL(
						"From Uploadfiles where idregister = :idregister and catalogtype=:catalogtype", parameters);*/
				//parameters.put("movsIds", movsIds);
				parameters.put("catalogtype", 7);//7=Movimientos
				whereClause = "idregister=0";
				if (!Functions.isEmpty(movsIds))
					whereClause = "idregister in(" + movsIds + ")";
				@SuppressWarnings("unchecked")
				List<Uploadfiles> doctos = dao.sqlHQL(
						"From Uploadfiles where " + whereClause + " and catalogtype=:catalogtype", parameters);
				for (Uploadfiles doc : doctos) {
					doc.setPath(doc.getPath().substring(doc.getPath().indexOf("doctos")));
					doc.setImg(Functions.findExtentionFile(doc.getFilename()));
					//System.out.println("::appealsdashboard:::" + doc.getPath());
				}
				forModel.put("doctos", doctos);

				List<ECalendar> schedules = null;
				if (movsIds != "")
					schedules = eCalendarService.getAll("FROM ECalendar WHERE movimientoid IN(" + movsIds + ")");
				forModel.put("schedules", schedules);

				// Permisos de movimientos caso de ser un juicio compartido
				HashMap<String, String> shMovPriv = new HashMap<>();
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if(juicio!=null){
					String originUsr = juicio.get(0).getUserid()==userDto.getId()
						|| (juicio.get(0).getAbogadoasignado()).equals(userDto.getId()+"")
						|| userDto.getRole()==ROLE_SYSADMIN || userDto.getRole()==ROLE_CJADMIN
						|| userDto.getRole()==ROLE_FIRMADMIN?"own":"shr", query = "";
					shMovPriv.put("origin",originUsr);
					if(juicio.get(0).getUserid()!=userDto.getId()){
						query = "SELECT mp.menuid, mp.privilegesid "
							+ "FROM SharedDockets AS sh "
							+ "LEFT JOIN Menuprivileges AS mp ON mp.shareddocketid=sh.shareddocketid "
							+ "LEFT JOIN Menu AS m ON m.menuid=mp.menuid "
							+ "WHERE sh.juicioid=" + juicio.get(0).getJuicioid()
							+ " AND menu='Movimientos de juicios'"	// Privilegios de movimientos
							+ " AND (sh.userid=" + userDto.getId()
							+ " OR sh.emailexternaluser='" + userDto.getEmail() + "'"
							+ ") ORDER BY mp.menuid ASC, mp.privilegesid ASC";
						@SuppressWarnings("unchecked")
						List<?> shdata = dao.sqlHQL(query, null);
						Object[] tmp = shdata.toArray();
						if(shdata.size()>0){
							String module = "", privileges = "";
							for (Object cdata:tmp){
								Object[] obj= (Object[]) cdata;
								if(Functions.isEmpty(module)){
									module=(obj[0]+"");
								}else if(!module.equals(obj[0]+"")){
									shMovPriv.put(module,privileges.replaceAll(",$",""));
									module=(obj[0]+"");
									privileges="";
								}
								privileges+=(obj[1]+",");
							}
							shMovPriv.put("shpriv",privileges.replaceAll(",$",""));
						}
					}
				}
				forModel.put("shmovpriv", shMovPriv);

				return new ModelAndView("appealsdashboard", forModel);
			}
		return new ModelAndView("login");
	}
}