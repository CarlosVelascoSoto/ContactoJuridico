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
import com.aj.model.Consultas;
import com.aj.model.CustomColumns;
import com.aj.model.CustomColumnsValues;
import com.aj.model.ECalendar;
import com.aj.model.Estados;
import com.aj.model.Juicios;
import com.aj.model.Juzgados;
import com.aj.model.Materias;
import com.aj.model.Menu;
import com.aj.model.Movimientos;
import com.aj.model.Privileges;
import com.aj.model.Recursos;
import com.aj.model.TipoJuicios;
import com.aj.model.TipoJuiciosAccion;
import com.aj.model.Uploadfiles;
import com.aj.model.Users;
import com.aj.model.Vias;
import com.aj.service.AccessDbJAService;
import com.aj.service.AmparosService;
import com.aj.service.ApelacionesService;
import com.aj.service.CiudadesService;
import com.aj.service.ClientsService;
import com.aj.service.CompanyclientsService;
import com.aj.service.ConsultasService;
import com.aj.service.CustomColumnsService;
import com.aj.service.CustomColumnsValuesService;
import com.aj.service.ECalendarService;
import com.aj.service.EstadosService;
import com.aj.service.FiscalsdataService;
import com.aj.service.JuiciosService;
import com.aj.service.JuzgadosService;
import com.aj.service.MateriasService;
import com.aj.service.MovimientosService;
import com.aj.service.PrivilegesService;
import com.aj.service.RecursosService;
import com.aj.service.TipoJuiciosAccionService;
import com.aj.service.TipoJuiciosService;
import com.aj.service.UserService;
import com.aj.service.ViasService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class JuiciosController {
	@Autowired
	private CommonController commonsCtrll;

	@Autowired
	private NotificationsController notificationsCtrll;

	@Autowired
	public AmparosService amparosService;

	@Autowired
	public ApelacionesService apelacionesService;

	@Autowired
	public CiudadesService ciudadesService;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	public CompanyclientsService companyclientsService;

	@Autowired
	public ConsultasService consultasService;

	@Autowired
	public CustomColumnsService customcolumnsService;
	
	@Autowired
	public CustomColumnsValuesService customcolumnsvaluesService;

	@Autowired
	public ECalendarService eCalendarService;

	@Autowired
	public EstadosService estadosService;

	@Autowired
	public FiscalsdataService fiscalsdataService;
	
	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public JuzgadosService juzgadosService;
	
	@Autowired
	public MateriasService materiasService;

	@Autowired
	public MovimientosService movimientosService;

	@Autowired
	public PrivilegesService privilegesService;
	
	@Autowired
	public RecursosService recursosService;

	@Autowired
	public TipoJuiciosAccionService tipojuiciosaccionService;

	@Autowired
	public TipoJuiciosService tipojuiciosService;

	@Autowired
	public ViasService viasService;

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

	@RequestMapping(value = "/juicios")
	public ModelAndView juiciosPage(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP_1.1.
				res.setHeader("Pragma", "no-cache");// HTTP1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int userid = (int) userDto.getId(),
					clientid = (req.getParameter("clid") == null) ? 0
						: Functions.toInt(req.getParameter("clid").trim()),//Variable para indicar análisis de un sólo cliente (en caso de ser solicitado desde Home)
					trialid = (req.getParameter("rid") == null) ? 0
						: Functions.toInt(req.getParameter("rid").trim());	//Variable para indicar análisis de un juicio (en caso de ser solicitado por su id)

				List<Juicios> allTrials = null;
				List<Companyclients> companycli = null;
				List<Juzgados> courtList = null;
				List<Materias> matList = null;
				List<Ciudades> cityList = null;
				List<Clients> clients = null;

				// Obtiene los privilegios del módulo
				String urlMethod="juicios";
				Map<String, Object> forModel = new HashMap<String, Object>();
				@SuppressWarnings({ "unchecked", "rawtypes" })
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", urlMethod);
				//Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE SUBSTRING(link, 0, LOCATE('.', link))=:urlMethod", parameters);
				@SuppressWarnings("unchecked")
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				forModel.put("menu", menu);

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));

				// Obtiene los juicios de acuerdo al usuario
				HashMap<String, String> trialsData = commonsCtrll.getAllTrials(req,clientid,trialid);
				String whereClause = "", ccids = trialsData.get("companyclientsid"),
					trials = trialsData.get("juiciosid"), clientIds = trialsData.get("clientsid");
				whereClause=(Functions.isEmpty(trials))?" WHERE juicioid=0":" WHERE juicioid IN(" + trials + ")";
				allTrials = juiciosService.getAll("FROM Juicios" + whereClause);

				// Obtiene los datos necesarios sólo de los juicios obtenidos.
				if(allTrials!=null)
					if(allTrials.size()>0){
						String courtIds = "",matIds = "",cityIds = "";
						for (int i = 0; i < allTrials.size(); i++){
							courtIds+=allTrials.get(i).getJuzgadoid()+",";
							matIds += allTrials.get(i).getMateriaid()+",";
							cityIds+= allTrials.get(i).getCiudadid() +",";
						}
						companycli=companyclientsService.getAll("FROM Companyclients WHERE companyclientid IN(" + ccids + ")");
						clients = clientsService.getAll("FROM Clients WHERE clientid IN(" + clientIds + ") ORDER BY client ASC");
						courtList=juzgadosService.getAll("FROM Juzgados WHERE juzgadoid IN(" + courtIds.replaceAll(".$","")
							+ ") AND tipojuzgado!=2 ORDER BY juzgado ASC"); //tipojuzgado:2=Juzgados federales; 1 o vacío = Fuero común
						matList = materiasService.getAll("FROM Materias WHERE materiaid IN(" + matIds.replaceAll(".$","") + ") ORDER BY materia ASC");
						cityList= ciudadesService.getAll("FROM Ciudades WHERE ciudadid IN (" + cityIds.replaceAll(".$","")+ ") ORDER BY ciudad ASC");
					}
				forModel.put("juicios", allTrials);
				forModel.put("ccli", companycli);
				forModel.put("clients", clients);
				forModel.put("juzgados",courtList);
				forModel.put("materias",matList);
				forModel.put("cities", cityList);
				forModel.put("usersession",userid);
				forModel.put("rl",userDto.getRole());

//TODO Test nuevo
/*HashMap<String, String> allIdTmp = commonsCtrll.checkAccessDoc(req, clientid, 0, "juicioid", trialid, "", false);

System.out.println("Juicios: "+allIdTmp);*/

				return new ModelAndView("juicios", forModel);
			}
		return new ModelAndView("login");
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addNewTrial")
	public @ResponseBody void addNewTrial(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_unable_save_orby_session";
		valideData:{
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int userid = (int) userDto.getId(), companyid = userDto.getCompanyid(), ccid = 0,
					countryid = (req.getParameter("countryid") == null) ? 0
						: Functions.toInt(req.getParameter("countryid").trim()),
					clientid = (req.getParameter("clientid") == null) ? 0
						: Functions.toInt(req.getParameter("clientid").trim()),
					court = (req.getParameter("court") == null) ? 0
						: Functions.toInt(req.getParameter("court").trim()),
					matter = (req.getParameter("matter") == null) ? 0
						: Functions.toInt(req.getParameter("matter").trim()),
					city = (req.getParameter("city") == null) ? 0
						: Functions.toInt(req.getParameter("city").trim()),
					trialtypeid = (req.getParameter("trialtypeid") == null) ? 0
						: Functions.toInt(req.getParameter("trialtypeid").trim()),
					firm = (req.getParameter("valideFirm") == null) ? 0
						: Functions.toInt(req.getParameter("valideFirm").trim()),
					judgename = (req.getParameter("judgename") == null) ? 0
						: Functions.toInt(req.getParameter("judgename").trim()),
					reasignuserid = (req.getParameter("valideUser") == null) ? 0
						: Functions.toInt(req.getParameter("valideUser").trim());
				String proceedings = (req.getParameter("proceedings") == null) ? "" : req.getParameter("proceedings").trim(),
					//actordef = (req.getParameter("actordef") == null) ? "" : req.getParameter("actordef").trim(),
					actor = (req.getParameter("actor") == null) ? "null" : req.getParameter("actor").trim(),
					defendant = (req.getParameter("defendant") == null) ? "null" : req.getParameter("defendant").trim(),
					third = (req.getParameter("third") == null) ? "" : req.getParameter("third").trim(),
					lawyer = (req.getParameter("lawyer") == null) ? "" : req.getParameter("lawyer").trim(),
					lawyercounterpart = (req.getParameter("lawyercounterpart") == null) ? ""
						: req.getParameter("lawyercounterpart").trim(),
					lawyerassigned = (req.getParameter("lawyerassigned") == null) ? ""
						: req.getParameter("lawyerassigned").trim(),
					dataCustCol = (req.getParameter("dataCustCol") == null) ? ""
						: req.getParameter("dataCustCol").trim();

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));

				if (clientid == 0 || court == 0 || matter == 0 || city == 0 || Functions.isEmpty(lawyer)){
					if(clientid==0)
						resp="err_select_client";
					else if(court == 0)
						resp="err_select_court";
					else if(matter == 0)
						resp="err_select_matter";
					else if(city == 0)
						resp="err_select_city";
					else if(Functions.isEmpty(lawyer))
						resp="err_select_lawyer";
					break valideData;
				}

				if(firm==0){
					firm=companyid;
				}else if(firm!=companyid){
					if((userDto.getRole()!=ROLE_SYSADMIN && userDto.getRole()!=ROLE_CJADMIN))
						firm=companyid;
				}

				// guardamos archivos relacionados con el registro
				String[] nameFiles = null;
				Enumeration enumeration = req.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String parameterName = (String) enumeration.nextElement();
					// System.out.println(parameterName + ":"
					// +req.getParameter(parameterName));
					if (parameterName.contains("fileuploadx_") && !Functions.isEmpty(req.getParameter(parameterName))) {
						if (nameFiles == null)
							nameFiles = new String[1];
						else
							nameFiles = (String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
						nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
					}
				}

				// Verifica que exista la relación cliente-firma
				List<Companyclients> existCC = companyclientsService
					.getAll("FROM Companyclients WHERE companyid=" + companyid + " AND clientid=" + clientid);
				if(userDto.getRole()==ROLE_SYSADMIN || userDto.getRole()==ROLE_CJADMIN){
					if(firm<=0){
						ccid = existCC.get(0).getCompanyclientid();
					}else{
						// Si la relación cliente-firma no existe, la crea
						if(existCC.size()==0){
							Companyclients cclient = new Companyclients();
							cclient.setCompanyid(companyid);
							cclient.setClientid(clientid);
							ccid=companyclientsService.addNewCClient(cclient);
						}else{
							ccid = existCC.get(0).getCompanyclientid();
						}
					}
				}else{
					ccid = existCC.get(0).getCompanyclientid();
				}

				//Valida los datos que se deben capturar de acuerdo al "tipo de juicio"
				if(trialtypeid>0){
					List<TipoJuicios> ttval = tipojuiciosService.getAll("FROM TipoJuicios WHERE tipojuicioid=" + trialtypeid);
					actor = ttval.get(0).getRequiereactor() == 0 ? "" : actor;
					defendant=ttval.get(0).getRequieredemandado()==0?"":defendant;
					third = ttval.get(0).getRequieretercero()==0 ? "" : third;
				}
				if(reasignuserid!=0)
					userid=reasignuserid;
				Juicios juicios = new Juicios();
				juicios.setJuicio(proceedings);
				juicios.setCompanyclientid((int) ccid);
				juicios.setJuzgadoid(court);
				juicios.setMateriaid(matter);
				juicios.setJuiciotipoid(trialtypeid);
				juicios.setCiudadid(city);
				juicios.setPaisid(countryid);
				juicios.setActor(actor);
				juicios.setDemandado(defendant);
				juicios.setTercero(third);
				juicios.setStatus(1); // Default: 1=activo
				juicios.setAbogado(lawyer);
				juicios.setAbogadocontraparte(lawyercounterpart);
				juicios.setClientecaracter("_"); //Default
				juicios.setAbogadoasignado(lawyerassigned);
				juicios.setUserid(userid);
				if(judgename>0)
					juicios.setJuezid(judgename);
				long succ = juiciosService.addNewJuicio(juicios);

				if(!Functions.isEmpty(dataCustCol))
					commonsCtrll.saveOrUpdateCustomColumns("juicios", Functions.toInt(succ+""),dataCustCol);

				// mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator() + "Juicios", true);
				destinationPath = Functions.addPath(destinationPath, "" + succ, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				JsonObject jsonFiles = new JsonObject();
				JsonObject fileRec = new JsonObject();
				if(paths!=null){
					int idx = 0;
					for (File file : paths) {
						entidad = new Uploadfiles();
						entidad.setCatalogtype(1);//1=juicios
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(succ));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
//TODO Notify add-trial (ini)
						JsonObject fileData = new JsonObject();
			    		fileData.addProperty("name", file.getName());
			    		fileData.addProperty("size", file.length());
			    		fileData.addProperty("status", "1");	//1=Nuevo
			    		fileRec.add(idx+"", fileData);
			    		idx++;
					}
					jsonFiles.add("file", fileRec);
				}

				try {
					if (succ > 0) {
						resp="msg_data_saved";
						FileUtils.deleteDirectory(new File(tmpPath));// elimina carpeta temporal

						List<Juicios> oldarray = new ArrayList<Juicios>();
						juicios.setJuicioid((int) succ);
						@SuppressWarnings("unchecked")
						Menu modRefId = (Menu) dao
							.sqlHQLEntity("FROM Menu WHERE menu NOT LIKE 'Compartir%' AND (link LIKE 'juicios' OR link LIKE 'juicios.jet')", null);
						Gson gson = new Gson();
					    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(juicios);
						long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 1,	//1=Juicios
							(int) succ, ccid, userid, 0,			//0=nuevo
							lawyerassigned, firm, jsonFiles, proceedings, oldjson, newdata);
						if(nsucc<1)
							System.err.println("Sin cambios o no se pudo almacenar la notificación del juicio " + proceedings);
					} else
						resp="err_record_no_saved";
				} catch (IOException ex) {
					System.out.println("Exception in addNewTrial(): " + ex.getMessage());
				}
//TODO Notify add (fin)
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getDetailsByTrial")
	public @ResponseBody Object[] getDetailsByTrial(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if(sess==null)return new Object[] {data};
		int trialid = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());

		if(trialid==0)return new Object[] {data};
		String estadoid = "", wayname = "";


//FIXME: Nuevo		
//		HashMap<String, String> dataTrial = commonsCtrll.checkAccessDoc(req,0,0,"juicioid",trialid,"","","").get(0);

//	ANT
		HashMap<String, String> dataTrial = commonsCtrll.getAllTrials(req, 0, trialid);
		
		
		List<Juicios> infoTrial = null;
		List<TipoJuicios> tt_info = null;
		List<TipoJuiciosAccion> ttacc = null;
		List<CustomColumns> custCol = null;
		List<CustomColumnsValues> colvalues = null;

//Nuevo	trialid = Functions.strToInt(dataTrial.get("juicios"));
		trialid = Functions.strToInt(dataTrial.get("juiciosid"));
		if(trialid==0)return new Object[] {data};
		infoTrial = juiciosService.getAll("FROM Juicios WHERE juicioid=" + trialid);
		tt_info = tipojuiciosService
			.getAll("FROM TipoJuicios WHERE tipojuicioid=" + infoTrial.get(0).getJuiciotipoid());
		if(tt_info!=null)if(tt_info.size()>0){
			ttacc = tipojuiciosaccionService
				.getAll("FROM TipoJuiciosAccion WHERE accionid=" + tt_info.get(0).getAccionid());
			if(ttacc.size()>0){
				List<Vias> wayinfo = viasService
					.getAll(("FROM Vias WHERE viaid=" + ttacc.get(0).getViaid()));
				wayname = wayinfo.get(0).getVia();
			}
		}
		custCol = customcolumnsService.getAll("FROM CustomColumns " 
			+ " WHERE ligadoatabla='tipojuicios' AND nombrepk='tipojuicioid' AND idpkreferenced="
			+ infoTrial.get(0).getJuiciotipoid() + " ORDER BY customcolumnid ASC");
		colvalues = customcolumnsvaluesService.getAll("FROM CustomColumnsValues"
			+ " WHERE savedon='juicios' AND idreferenced=" + trialid
			+ " ORDER BY customcolumnvalueid ASC");
		List<Estados> edo = estadosService.getAll("SELECT e.estadoid FROM Estados AS e"
			+ " LEFT JOIN Ciudades AS c ON c.estadoid=e.estadoid WHERE ciudadid="
			+ infoTrial.get(0).getCiudadid());
		estadoid = edo.get(0)+"";
		List<Clients> cteData = clientsService.getAll("FROM Clients WHERE clientid=" + dataTrial.get("clientsid")); 
		
/*		HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
		clientid = Functions.toInt(dataTrial.get("clientid"));

		HashMap<String, String> dataTrial = commonsCtrll.getAllTrials(req, clientid, trialid);
		List<Juicios> infoTrial = null;
		List<TipoJuicios> tt_info = null;
		List<TipoJuiciosAccion> ttacc = null;
		List<CustomColumns> custCol = null;
		List<CustomColumnsValues> colvalues = null;
		if(!Functions.isEmpty(dataTrial.get("juiciosid"))){
			infoTrial = juiciosService.getAll("FROM Juicios WHERE juicioid=" + trialid);
			List<Clients> clients = clientsService.getAll("SELECT client FROM Clients WHERE clientid=" + dataTrial.get("clientsid"));
			data.put("clientId", dataTrial.get("clientsid"));
			data.put("clientName", clients.get(0)+"");
			tt_info = tipojuiciosService.getAll("FROM TipoJuicios WHERE tipojuicioid=" + infoTrial.get(0).getJuiciotipoid());
			if(tt_info!=null)
				if(tt_info.size()>0){
					ttacc = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion WHERE accionid=" + tt_info.get(0).getAccionid());
					if(ttacc.size()>0){
						List<Vias> wayinfo = viasService.getAll(("FROM Vias WHERE viaid=" + ttacc.get(0).getViaid()));
						wayname = wayinfo.get(0).getVia();
					}
				}
			custCol = customcolumnsService.getAll("FROM CustomColumns " 
				+ "WHERE ligadoatabla='tipojuicios' AND nombrepk='tipojuicioid' AND idpkreferenced="
				+ infoTrial.get(0).getJuiciotipoid() + " ORDER BY customcolumnid ASC");
			colvalues = customcolumnsvaluesService.getAll("FROM CustomColumnsValues"
				+ " WHERE savedon='juicios' AND idreferenced=" + trialid
				+ " ORDER BY customcolumnid ASC, customcolumnvalueid ASC");
			List<Estados> edo = estadosService.getAll("SELECT e.estadoid FROM Estados AS e "
				+ "LEFT JOIN Ciudades AS c ON c.estadoid=e.estadoid WHERE ciudadid=" + infoTrial.get(0).getCiudadid());
			estadoid = edo.get(0)+"";
		}*/
		data.put("clientId", dataTrial.get("clientsid"));
		data.put("clientName", cteData.get(0).getClient());
		data.put("detail", infoTrial);
		data.put("dataTrial", dataTrial);
		data.put("tt_info", tt_info);
		data.put("ttacc", ttacc);
		data.put("estadoid", estadoid);
		data.put("wayname", wayname);
		data.put("custCol", custCol);
		data.put("colvalues", colvalues);
		return new Object[] {data};
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateTrial")
	public @ResponseBody void updateTrial(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_unable_save_orby_session";
		valideData:{
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int userid = 0, ccid = 0, trialid = (req.getParameter("edid")==null)?0
						: Functions.toInt(req.getParameter("edid").trim()),
					countryid = (req.getParameter("countryid")==null)?0
						: Functions.toInt(req.getParameter("countryid").trim()),
					status = (req.getParameter("status")==null)?1
						: Functions.toInt(req.getParameter("status").trim()),
					clientid = (req.getParameter("clientid")==null)?0
						: Functions.toInt(req.getParameter("clientid").trim()),
					court = (req.getParameter("court")==null)?0
						: Functions.toInt(req.getParameter("court").trim()),
					matter = (req.getParameter("matter")==null)?0
						: Functions.toInt(req.getParameter("matter").trim()),
					city = (req.getParameter("city")==null)?0
						: Functions.toInt(req.getParameter("city").trim()),
					firm = (req.getParameter("valideFirm")==null)?0
						: Functions.toInt(req.getParameter("valideFirm").trim()),
					trialtypeid = (req.getParameter("trialtypeid")==null)?0
						: Functions.toInt(req.getParameter("trialtypeid").trim()),
					judgename = (req.getParameter("judgename")==null)?0
						: Functions.toInt(req.getParameter("judgename").trim()),
					reasignuserid = (req.getParameter("valideUser")==null)?0
						: Functions.toInt(req.getParameter("valideUser").trim());
				String oldColValues = "",
					proceedings = (req.getParameter("proceedings")==null)?"":req.getParameter("proceedings").trim(),
					actor = (req.getParameter("actor")==null)?"null":req.getParameter("actor").trim(),
					defendant = (req.getParameter("defendant")==null)?"null":req.getParameter("defendant").trim(),
					third = (req.getParameter("third")==null)?"":req.getParameter("third").trim(),
					lawyer = (req.getParameter("lawyer")==null)?"":req.getParameter("lawyer").trim(),
					lawyercounterpart = (req.getParameter("lawyercounterpart")==null)?""
						: req.getParameter("lawyercounterpart").trim(),
					lawyerassigned = (req.getParameter("lawyerassigned")==null)?""
						: req.getParameter("lawyerassigned").trim(),
					dataCustCol = (req.getParameter("dataCustCol")==null)?""
						: req.getParameter("dataCustCol").trim();
				
				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));
				
				if (clientid == 0 || court == 0 || matter == 0 || city == 0 || Functions.isEmpty(lawyer)){
					if(clientid==0)
						resp="err_select_client";
					else if(court == 0)
						resp="err_select_court";
					else if(matter == 0)
						resp="err_select_matter";
					else if(city == 0)
						resp="err_select_city";
					else if(Functions.isEmpty(lawyer))
						resp="err_select_lawyer";
					break valideData;
				}
//TODO Notify Update Juicio-1 (una línea)
				List<Juicios> olddata = juiciosService.getAll("FROM Juicios WHERE juicioid=" + trialid);

				// Valores de columnas personalizadas
				String custCol = "SELECT ccv.customcolumnvalueid, ccm.titulo, ccv.assignedvalue"
					+ " FROM CustomColumnsValues AS ccv"
					+ " LEFT JOIN CustomColumns AS ccm ON ccm.customcolumnid=ccv.customcolumnid"
					+ " WHERE ccv.savedon='juicios' AND ccv.idreferenced=" + trialid
					+ " ORDER BY ccv.customcolumnvalueid ASC";
				List<?> dataCol = dao.sqlHQL(custCol, null);
				Object[] tmp = dataCol.toArray();
				if(dataCol.size()>0){
					for (Object cdata:tmp){
						Object[] obj= (Object[]) cdata;
						oldColValues += obj[0] + "|" + obj[1] + "|" + obj[2] + "~";
					}
					oldColValues = "\"customcolumns\":\"" + oldColValues.replaceAll("~$","") + "\"";
				}

				// guardamos archivos relacionados con el registro
				String[] nameFiles = null;
				Enumeration enumeration = req.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String parameterName = (String) enumeration.nextElement();
					// System.out.println(parameterName + ":"
					// +req.getParameter(parameterName));
					if (parameterName.contains("fileuploadx_") && !Functions.isEmpty(req.getParameter(parameterName))) {
						if (nameFiles == null)
							nameFiles = new String[1];
						else
							nameFiles = (String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
						nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
					}
				}

				List<Juicios> currentTrial = juiciosService.getAll("FROM Juicios WHERE juicioid=" + trialid);
				// En caso de firma reasignada
				if(firm!=userDto.getCompanyid()){
					if((userDto.getRole()!=ROLE_SYSADMIN && userDto.getRole()!=ROLE_CJADMIN))
						firm=userDto.getCompanyid();
					// Verifica si existe la relación cliente-firma, sino, la crea
					List<Companyclients> existCC = companyclientsService
						.getAll("FROM Companyclients WHERE companyid=" + firm + " AND clientid=" + clientid);
					if(existCC.size()==0){
						Companyclients cclient = new Companyclients();
						cclient.setCompanyid(firm);
						cclient.setClientid(clientid);
						ccid=companyclientsService.addNewCClient(cclient);
					}else{
						ccid=existCC.get(0).getCompanyclientid();
					}
				}else{
					ccid=currentTrial.get(0).getCompanyclientid();
					//NOTE*** Sí se desea dar de alta clientes dentro de "edición de juicio", habilitar este bloque:
					/*firm=userDto.getCompanyid();
					// Verifica si existe la relación cliente-firma, sino, la crea
					List<Companyclients> existCC = companyclientsService
						.getAll("FROM Companyclients WHERE companyid=" + firm + " AND clientid=" + clientid);
					if(existCC.size()==0){
						Companyclients cclient = new Companyclients();
						cclient.setCompanyid(firm);
						cclient.setClientid(clientid);
						ccid=companyclientsService.addNewCClient(cclient);
					}else{
						ccid=existCC.get(0).getCompanyclientid();
					}*/
				}

				//Valida los datos que se deben capturar de acuerdo al "tipo de juicio"
				if(trialtypeid>0){
					List<TipoJuicios> ttval = tipojuiciosService.getAll("FROM TipoJuicios WHERE tipojuicioid=" + trialtypeid);
					actor = ttval.get(0).getRequiereactor() == 0 ? "" : actor;
					defendant=ttval.get(0).getRequieredemandado()==0?"":defendant;
					third = ttval.get(0).getRequieretercero()==0 ? "" : third;
				}
				userid = reasignuserid!=0?reasignuserid:currentTrial.get(0).getUserid();
				/*if(reasignuserid!=0)
					userid=reasignuserid;
				else
					userid = currentTrial.get(0).getUserid();*/
				Juicios juicio = new Juicios();
				juicio.setJuicioid(trialid);
				juicio.setJuicio(proceedings);
				juicio.setCompanyclientid(ccid);
				juicio.setJuzgadoid(court);
				juicio.setMateriaid(matter);
				juicio.setJuiciotipoid(trialtypeid);
				juicio.setCiudadid(city);
				juicio.setPaisid(countryid);
				juicio.setActor(actor);
				juicio.setDemandado(defendant);
				juicio.setTercero(third);
				juicio.setStatus(status);
				juicio.setAbogado(lawyer);
				juicio.setAbogadocontraparte(lawyercounterpart);
				juicio.setClientecaracter("_");	//Default
				juicio.setUserid(userid);
				juicio.setAbogadoasignado(lawyerassigned);
				if(judgename>0)
					juicio.setJuezid(judgename);
				juiciosService.updateJuicioDetails(juicio);

				if(!Functions.isEmpty(dataCustCol))
					commonsCtrll.saveOrUpdateCustomColumns("juicios", trialid, dataCustCol+"");

				// mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator() + "Juicios", true);
				destinationPath = Functions.addPath(destinationPath, "" + trialid, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				JsonObject jsonFiles = new JsonObject();
				JsonObject fileRec = new JsonObject();
				if(paths!=null){
					int idx = 0;
					for (File file : paths) {
						entidad = new Uploadfiles();
						entidad.setCatalogtype(1);//1=juicios
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(trialid));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
//TODO Notify Update Juicio-2 (ini)
//						System.out.println("Size long: "+ file.length());
//						System.out.println("Size short:"+ Functions.bytesFormatter(file.length()));

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
				resp="msg_data_saved";
				List<Juicios> oldarray = new ArrayList<Juicios>(olddata);
				Gson gson = new Gson();
			    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(juicio), newColValues = "";
				Menu modRefId = (Menu) dao
					.sqlHQLEntity("FROM Menu WHERE menu NOT LIKE 'Compartir%' AND (link LIKE 'juicios' OR link LIKE 'juicios.jet')", null);
			    
			    dataCol = dao.sqlHQL(custCol, null);
				tmp = dataCol.toArray();
				if(dataCol.size()>0){
					for (Object cdata:tmp){
						Object[] obj= (Object[]) cdata;
						newColValues += obj[0] + "|" + obj[1] + "|" + obj[2] + "~";
					}
					newColValues = "\"customcolumns\":\"" + newColValues.replaceAll("~$","") + "\"";
				}
				if(!Functions.isEmpty(oldColValues))
					oldjson=oldjson.replaceAll("\\}\\]",",") + oldColValues + "}]";
				if(!Functions.isEmpty(newColValues))
					newdata=newdata.replaceAll("\\}",",") + newColValues + "}";

				long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 1,	//1=Juicios
					trialid, ccid, userid, 1,	//1=Edición
					lawyerassigned, firm, jsonFiles, proceedings, oldjson, newdata);
				if(nsucc<1)
					System.err.println("Sin cambios o no se pudo almacenar la notificación del juicio " + proceedings);
				resp = "true";
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteTrial")
	public void deleteTrial(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int trialid = req.getParameter("id") == "" ? 0 : Functions.toInt(req.getParameter("id").trim());
				juiciosService.deleteJuicio(trialid);
			}
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/trialsdashboard")
	public ModelAndView trialsdashboard(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP_1.1.
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				int clientid = 0, juicioid = (req.getParameter("rid") == null) ? 0
					: Functions.toInt(req.getParameter("rid").trim());

				List<Privileges> lp = null;
				Map<String, Object> forModel = new HashMap<String, Object>();
				HashMap<Object, Object> parameters = new HashMap();

				// Obtiene los privilegios del módulo
				String urlMethod="trialsdashboard";
				parameters.put("urlMethod", urlMethod);
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				forModel.put("menu", menu);
				forModel.put("listp", lp);

				if(juicioid==0)return new ModelAndView("trialsdashboard", forModel);

				List<Juicios> juicio = null;
				List<Juzgados> courtList = null;
				List<Materias> matList = null;
				List<Ciudades> cityList = null;
				List<Movimientos> movList = null;
				List<Clients> client = null;
				List<Apelaciones> appeals = null;
				List<Amparos> amparos = null;
				List<Recursos> recursos = null;
				List<Consultas> cons = null;
				List<Users> userList = null;
				List<ECalendar> schedules = null;
				List<TipoJuicios> trialtype = null;
				List<CustomColumns> custcol = null;
				List<CustomColumnsValues> custval = null;


// Código original
				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));

// FIXME Nueva implementación de seguridad
/*				HashMap<String, String> acclt = commonsCtrll
					.checkAccessDoc(req, clientid, 0, "juicioid", juicioid, "", true);
*/
				clientid = Functions.toInt(acclt.get("clientid"));

				HashMap<String, String> isShared = commonsCtrll.getAllTrials(req,clientid,juicioid);
				String trialid= (String) isShared.get("juiciosid"),
					clientsid = (String) isShared.get("clientsid"),
					userasig = "", ampids = "";

				String query = "WHERE juicioid=";
				if(Functions.isEmpty(trialid)){
					query+="0";
					clientsid="0";
				}else{
					query+=trialid;
				}
				if(!Functions.isEmpty(trialid)){
					juicio = juiciosService.getAll("FROM Juicios " + query);
					courtList=juzgadosService.getAll("FROM Juzgados WHERE juzgadoid=" + juicio.get(0).getJuzgadoid());
					matList = materiasService.getAll("FROM Materias WHERE materiaid=" + juicio.get(0).getMateriaid());
					cityList= ciudadesService.getAll("FROM Ciudades WHERE ciudadid = " + juicio.get(0).getCiudadid());
					movList=movimientosService.getAll("FROM Movimientos " + query + " ORDER BY LEAST (fechapresentacion,fechaacuerdo,fechanotificacion)");
					client = clientsService.getAll("FROM Clients WHERE clientid IN(" + clientsid + ")");
					appeals= apelacionesService.getAll("FROM Apelaciones " + query + " ORDER BY apelacionid ASC");
					amparos= amparosService.getAll("FROM Amparos " + query + " ORDER BY amparoid ASC");
					cons=consultasService.getAll("FROM Consultas WHERE juicioid="+trialid);

					// Recursos
					for (int i = 0; i < amparos.size(); i++)
						ampids += amparos.get(i).getAmparoid() + ",";
					ampids = ampids.replaceAll(".$", "");
					if(!Functions.isEmpty(ampids))
						recursos =recursosService
							.getAll("FROM Recursos WHERE tipoorigen IN(1,2) AND tipoorigenid IN(" + ampids + ")");

					// Abogado asignado
					String abAsig=juicio.get(0).getAbogadoasignado();
					if(!Functions.isEmpty(abAsig)){
						if(!abAsig.equals("0")){
							userList=userService.getAll("FROM Users WHERE id=" + juicio.get(0).getAbogadoasignado());
							userasig=userList.get(0).getFirst_name() + " " + userList.get(0).getLast_name();
						}
					}
				}
				forModel.put("juicio", juicio);
				forModel.put("juzgados", courtList);
				forModel.put("materias", matList);
				forModel.put("cities", cityList);
				forModel.put("movList", movList);
				forModel.put("client", client);
				forModel.put("appeals", appeals);
				forModel.put("amparos", amparos);
				forModel.put("recursos", recursos);
				forModel.put("userasig", userasig);
				forModel.put("cons", cons);

				String movsIds = "";
				if (movList != null) {
					for (int i = 0; i < movList.size(); i++)
						movsIds += movList.get(i).getMovimientoid() + ",";
					movsIds = movsIds.replaceAll(".$", "");
				}
				parameters.clear();
				parameters.put("catalogtype", 7);//7=Movimientos
				query = "idregister=0";
				if (!Functions.isEmpty(movsIds))
					query = "idregister in(" + movsIds + ")";

				List<Uploadfiles> doctos = dao.sqlHQL("FROM Uploadfiles WHERE "
					+ query + " AND path!='' AND catalogtype=:catalogtype", parameters);
				for (Uploadfiles doc : doctos) {
					doc.setPath(doc.getPath().substring(doc.getPath().indexOf("doctos")));
					doc.setImg(Functions.findExtentionFile(doc.getFilename()));
				}
				forModel.put("doctos", doctos);

				if(juicio!=null){
					int ttid = juicio.get(0).getJuiciotipoid();
					trialtype = tipojuiciosService.getAll("FROM TipoJuicios WHERE tipojuicioid=" + ttid);
					custcol = customcolumnsService.getAll("FROM CustomColumns " 
						+ "WHERE ligadoatabla='tipojuicios' AND nombrepk='tipojuicioid' AND idpkreferenced="
						+ ttid + " ORDER BY customcolumnid ASC");
					custval = customcolumnsvaluesService.getAll("FROM CustomColumnsValues "
						+ "WHERE savedon='juicios' AND idreferenced=" + juicioid);
				}
				forModel.put("trialtype", trialtype);
				forModel.put("custcol", custcol);
				forModel.put("custval", custval);

				if (movsIds != "")
					schedules = eCalendarService.getAll("FROM ECalendar WHERE movimientoid IN(" + movsIds + ")");
				forModel.put("schedules", schedules);

				// Permisos de movimientos caso de ser un juicio compartido
				HashMap<String, String> shMovPriv = new HashMap<>();
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				String originUsr = juicio.get(0).getUserid()==userDto.getId()
					|| (juicio.get(0).getAbogadoasignado()).equals(userDto.getId()+"")
					|| userDto.getRole()==ROLE_SYSADMIN || userDto.getRole()==ROLE_CJADMIN
					|| userDto.getRole()==ROLE_FIRMADMIN?"own":"shr";
				shMovPriv.put("origin",originUsr);
				if(juicio.get(0).getUserid()!=userDto.getId()){
					query = "SELECT mp.menuid, mp.privilegesid "
						+ "FROM SharedDockets AS sh "
						+ "LEFT JOIN Menuprivileges AS mp ON mp.shareddocketid=sh.shareddocketid "
						+ "LEFT JOIN Menu AS m ON m.menuid=mp.menuid "
						+ "WHERE sh.juicioid=" + juicioid
						+ " AND menu='Movimientos de juicios'"	// Privilegios de movimientos
						+ " AND (sh.userid=" + userDto.getId()
						+ " OR sh.emailexternaluser='" + userDto.getEmail() + "'"
						+ ") ORDER BY mp.menuid ASC, mp.privilegesid ASC";
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
				forModel.put("shmovpriv", shMovPriv);

				return new ModelAndView("trialsdashboard", forModel);
			}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/getTrial")
	public @ResponseBody Object[] getTrial(HttpServletRequest req,HttpServletResponse res){
		HttpSession sess = req.getSession(false);
		List<Juicios> info = null;
		if (sess != null) {
			int trial = (req.getParameter("trial") == null) ? -0
				: Functions.toInt(req.getParameter("trial").trim());
			String tid = commonsCtrll.getAllTrials(req,0,trial).get("juiciosid");
			if(!Functions.isEmpty(tid))
				info = juiciosService.getAll("FROM Juicios WHERE juicioid IN("+tid+")");
		}
		return new Object[] { info };
	}
}