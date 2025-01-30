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
import com.aj.model.ECalendar;
import com.aj.model.Juicios;
import com.aj.model.Juzgados;
import com.aj.model.Materias;
import com.aj.model.Menu;
import com.aj.model.Movimientos;
import com.aj.model.Privileges;
import com.aj.model.Recursos;
import com.aj.model.TipoActuacion;
import com.aj.model.TribunalColegiado;
import com.aj.model.TribunalUnitario;
import com.aj.model.Uploadfiles;
import com.aj.service.AccessDbJAService;
import com.aj.service.ApelacionesService;
import com.aj.service.AmparosService;
import com.aj.service.CiudadesService;
import com.aj.service.ClientsService;
import com.aj.service.CompanyclientsService;
import com.aj.service.ECalendarService;
import com.aj.service.JuiciosService;
import com.aj.service.JuzgadosService;
import com.aj.service.MateriasService;
import com.aj.service.MovimientosService;
import com.aj.service.RecursosService;
import com.aj.service.TipoActuacionService;
import com.aj.service.TribunalColegiadoService;
import com.aj.service.TribunalUnitarioService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class IndirectProtectionsController {
	@Autowired
	private CatalogsController catalogsCtrll;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	private CommonController commonsCtrll;

	@Autowired
	private NotificationsController notificationsCtrll;

	@Autowired
	public ApelacionesService apelacionesService;

	@Autowired
	public AmparosService amparosService;

	@Autowired
	public CiudadesService ciudadesService;

	@Autowired
	public CompanyclientsService companyclientsService;

	@Autowired
	public ECalendarService eCalendarService;

	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public JuzgadosService juzgadosService;

	@Autowired
	public RecursosService recursosService;

	@Autowired
	public MateriasService materiasService;

	@Autowired
	public MovimientosService movimientosService;

	@Autowired
	public TipoActuacionService tipoactuacionService;

	@Autowired
	public TribunalColegiadoService tribunalCService;

	@Autowired
	public TribunalUnitarioService tribunalUnitService;

	@SuppressWarnings("rawtypes")
	@Autowired
	public AccessDbJAService dao;

	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static final int ROLE_SYSADMIN= 1;
	public static final int ROLE_CJADMIN = 2;
	public static final int ROLE_FIRMADMIN=3;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/indprotections")
	public @ResponseBody ModelAndView indprotections(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");//HTTP1.1.
				res.setHeader("Pragma", "no-cache");//HTTP1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int role = userDto.getRole(), ccid = 0,
					clientid = (req.getParameter("clid") == null) ? 0
						: Functions.toInt(req.getParameter("clid").trim());//Variable para indicar que proviene de Home

				// Obtiene los privilegios del módulo
				@SuppressWarnings("rawtypes")
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "indprotections");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);
				forModel.put("role", role);

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));
				ccid = Functions.toInt(acclt.get("companyclientid"));

				HashMap<String, String> shrTrials = commonsCtrll.getAllTrials(req,clientid,0);
				String whereClauseCli="", ccids = "", juiciosIds = shrTrials.get("juiciosid"), apelIds = "";

				if (!(role==ROLE_SYSADMIN||role==ROLE_CJADMIN))
					whereClauseCli += " WHERE companyid=" + userDto.getCompanyid();
				if(clientid>0)
					whereClauseCli+= (Functions.isEmpty(whereClauseCli)?" WHERE":" AND") + " companyclientid="+ccid;
				List<Companyclients> ccclient = companyclientsService.getAll("FROM Companyclients" + whereClauseCli);
				for (int i = 0; i < ccclient.size(); i++)
					ccids += ccclient.get(i).getCompanyclientid() + ",";
				ccids = ccids.replaceAll(".$", "");
				if(!Functions.isEmpty(shrTrials.get("companyclientsid")))
					ccids += "," + shrTrials.get("companyclientsid");

				if(!Functions.isEmpty(shrTrials.get("juiciosid"))){
					List<Apelaciones> appeals = apelacionesService.getAll("FROM Apelaciones" 
						+ (Functions.isEmpty(juiciosIds)?"":" WHERE juicioid IN(" + juiciosIds + ")"));
					for (int i = 0; i < appeals.size(); i++)
						apelIds += appeals.get(i).getApelacionid() + ",";
					apelIds = apelIds.replaceAll(".$", "");
				}

				String whereClause=(Functions.isEmpty(ccids)?"":" AND (companyclientid IN(" + ccids + ")");
				whereClause+=(Functions.isEmpty(juiciosIds)?"":" OR juicioid IN(" + juiciosIds + ")");
				whereClause+=(Functions.isEmpty(apelIds)?"":" OR apelacionid IN(" + apelIds + ")");
				List<Amparos> amparos = amparosService.getAll("FROM Amparos WHERE amparotipo=2" //amparotipo=2 (indirectos)
					+ whereClause + ")");
				forModel.put("amparos", amparos);

				//Obtiene datos para "Órgano reconoce" y "ciudad"
				String clgIds = "0,", unitIds = "0,", fedIds = "0,";
				List<Ciudades> cities = null;
				List<TribunalColegiado> colgcourts = null;
				List<TribunalUnitario> unitcourts = null;
				List<Juzgados> fedcourts = null;
				if(amparos!=null)
					if (amparos.size()>0){
						for (int i = 0; i < amparos.size(); i++){
							String tdta=amparos.get(i).getTipodemandaturnadaa()+"";
							int turnTo = Functions.toInt(amparos.get(i).getDemandaamparoturnadaa());
							if(turnTo>0)
								if(tdta.equals("colegiado"))
									clgIds+=turnTo+",";
								else if(tdta.equals("unitario"))
									unitIds+=turnTo+",";
								else if(tdta.equals("federal"))
									fedIds+=turnTo+",";
						}
						clgIds = clgIds.replaceAll(".$", "");
						unitIds= unitIds.replaceAll(".$","");
						fedIds = fedIds.replaceAll(".$", "");
						
						cities = ciudadesService.getAll("SELECT DISTINCT(c.ciudadid), c.ciudad FROM Ciudades AS c "
							+ "JOIN TribunalColegiado AS tc ON tc.ciudadid=c.ciudadid "
							+ "JOIN TribunalUnitario AS tu ON tu.ciudadid=c.ciudadid "
							+ "JOIN Juzgados AS j ON j.ciudadid=c.ciudadid "
							+ "WHERE tc.tribunalcolegiadoid IN(" + clgIds + ") OR tu.tribunalUnitarioid IN(" + unitIds + ") OR j.juzgadoid IN(" + fedIds + ")");
						colgcourts= tribunalCService.getAll("FROM TribunalColegiado WHERE tribunalcolegiadoid IN(" + clgIds + ")");
						unitcourts= tribunalUnitService.getAll("FROM TribunalUnitario WHERE tribunalunitarioid IN(" + unitIds + ")");
						fedcourts = juzgadosService.getAll("FROM Juzgados WHERE tipojuzgado=2 AND juzgadoid IN(" + fedIds + ")");
					}
				forModel.put("cities", cities);
				forModel.put("colgcourts", colgcourts);
				forModel.put("unitcourts", unitcourts);
				forModel.put("fedcourts", fedcourts);
				return new ModelAndView("indprotections", forModel);
			}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/addNewIndProtection")
	public @ResponseBody void addNewIndProtection(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		validateData:{
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO user = (UserDTO) sess.getAttribute("UserDTO");
				int clientid = (req.getParameter("clientid") == null) ? 0
						: Functions.toInt(req.getParameter("clientid").trim()),
					apelacionid = Functions.toInt(req.getParameter("apelacionid")),
					prottype = 2,
					juicioid = Functions.toInt(req.getParameter("trialid"));
				String /*custchar = (req.getParameter("custchar") == null) ? "" : req.getParameter("custchar").trim(),
					trialappeal = (req.getParameter("trialappeal") == null) ? ""
						: req.getParameter("trialappeal").trim(),*/
					origintype = (req.getParameter("origintype") == null) ? ""
						: req.getParameter("origintype").trim(),
					protection = (req.getParameter("protection") == null) ? ""
						: req.getParameter("protection").trim(),
					notebooktype = "principal",
					complaining = (req.getParameter("complaining") == null) ? ""
						: req.getParameter("complaining").trim(),
					interestedtrdparty = (req.getParameter("interestedtrdparty") == null) ? ""
						: req.getParameter("interestedtrdparty").trim(),
					claimedact = (req.getParameter("claimedact") == null) ? ""
						: req.getParameter("claimedact").trim(),
					respauth = (req.getParameter("respauth") == null) ? "" : req.getParameter("respauth").trim(),
					dateclaimedactNtn = (req.getParameter("dateclaimedactNtn") == null) ? ""
						: req.getParameter("dateclaimedactNtn").trim(),
					filingdatelawsuit = (req.getParameter("filingdatelawsuit") == null) ? ""
						: req.getParameter("filingdatelawsuit").trim(),
					demandprotsent = (req.getParameter("demandprotsent") == null) ? ""
						: req.getParameter("demandprotsent").trim(),
					admissiondate = (req.getParameter("admissiondate") == null) ? ""
						: req.getParameter("admissiondate").trim(),
					admissionnotifdate = (req.getParameter("admissionnotifdate") == null) ? ""
						: req.getParameter("admissionnotifdate").trim(),
					consthearingdate = (req.getParameter("consthearingdate") == null) ? ""
						: req.getParameter("consthearingdate").trim(),
					judgment = (req.getParameter("judgment") == null) ? "" : req.getParameter("judgment").trim(),
					rsrcreviewjudgment = (req.getParameter("rsrcreviewjudgment") == null) ? ""
						: req.getParameter("rsrcreviewjudgment").trim(),
					susp = (req.getParameter("provsusp") == null) ? "" : req.getParameter("provsusp").trim(),
					incidentalhearingdate = (req.getParameter("incidentalhearingdate") == null) ? ""
						: req.getParameter("incidentalhearingdate").trim(),
					adjournmentjudgment = (req.getParameter("adjournmentjudgment") == null) ? ""
						: req.getParameter("adjournmentjudgment").trim(),
					tipodemandaturnadaa = (req.getParameter("tipodemandaturnadaa") == null) ? ""
						: req.getParameter("tipodemandaturnadaa").trim();

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));

				if(Functions.isEmpty(clientid)){
					resp="err_select_client";
					break validateData;
				}else if(origintype=="1" && juicioid==0){
					resp="err_select_origintrial";
					break validateData;
				}else if(origintype=="2" && apelacionid==0){
					resp="err_select_appealO";
					break validateData;
				}else if(Functions.isEmpty(respauth)){
					resp="err_enter_respauth";
					break validateData;
				}
				String[] nameFiles = null;
				@SuppressWarnings("rawtypes")
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
				//Si no existe la relacion cliente-compañia, la crea.
				List<Companyclients> compclient = companyclientsService.getAll("FROM Companyclients WHERE companyid="
					+ user.getCompanyid() + " AND clientid=" + clientid);
				long ccid = 0;
				if (compclient.size() == 0) {
					Companyclients newcclient = new Companyclients();
					newcclient.setCompanyid(user.getCompanyid());
					newcclient.setClientid(clientid);
					ccid = companyclientsService.addNewCClient(newcclient);
				} else {
					ccid = compclient.get(0).getCompanyclientid();
				}
				Amparos amparos = new Amparos();
				amparos.setAmparo(protection);
				amparos.setQuejoso(complaining);
				if(apelacionid>0)
				amparos.setApelacionid(apelacionid);
				amparos.setAutoridadresponsable(respauth);
				amparos.setActoreclamado(claimedact);
				if(!Functions.isEmpty(dateclaimedactNtn))
					amparos.setFechanotificacionactoreclamado(Functions.parseFecha(dateclaimedactNtn, "yyyy-MM-dd"));
				amparos.setTercero(interestedtrdparty);
				if(!Functions.isEmpty(filingdatelawsuit))
					amparos.setFechapresentaciondemanda(Functions.parseFecha(filingdatelawsuit, "yyyy-MM-dd"));
				amparos.setDemandaamparoturnadaa(demandprotsent);
				amparos.setTipodemandaturnadaa(tipodemandaturnadaa);
				if(!Functions.isEmpty(admissiondate))
					amparos.setFechadmision(Functions.parseFecha(admissiondate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(admissionnotifdate))
					amparos.setFechanotificaciondmision(Functions.parseFecha(admissionnotifdate, "yyyy-MM-dd"));
				amparos.setAmparotipo(prottype);// 2=Amparo indirecto
				if(!Functions.isEmpty(consthearingdate))
					amparos.setFechaaudicienciaconstitucional(Functions.parseFecha(consthearingdate, "yyyy-MM-dd"));
				amparos.setRecursorevisioncontrasentencia(rsrcreviewjudgment);
				amparos.setSentenciadefinitiva(adjournmentjudgment);
				amparos.setSuspensionprovisional(susp);
				amparos.setCuaderno(notebooktype);// Valor por default = "principal"
				amparos.setSentencia(judgment);
				if(!Functions.isEmpty(incidentalhearingdate))
					amparos.setFechaaudienciaincidental(Functions.parseFecha(incidentalhearingdate, "yyyy-MM-dd"));
				if(juicioid>0)
					amparos.setJuicioid(juicioid);
				amparos.setCompanyclientid((int) ccid);
				long succ = amparosService.addNewAmparo(amparos);

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator() + "AmparosInd", true);
				destinationPath = Functions.addPath(destinationPath, "" + succ, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				JsonObject jsonFiles = new JsonObject();
				JsonObject fileRec = new JsonObject();
				if(paths!=null){
					int idx = 0;
					for (File file : paths) {
						entidad = new Uploadfiles();
						entidad.setCatalogtype(4);	//4=Amparos indirectos
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(succ));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
//TODO Notify add-IndProt (ini)
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
	
						List<Amparos> oldarray = new ArrayList<Amparos>();
						amparos.setApelacionid((int) succ);
						@SuppressWarnings("unchecked")
						Menu modRefId = (Menu) dao
							.sqlHQLEntity("FROM Menu WHERE link LIKE 'indprotections' OR link LIKE 'indprotections.jet'", null);
						Gson gson = new Gson();
					    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(amparos);
					    long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 4,	//4=amparos indirectos
							(int) succ, 0, (int) user.getId(), 0,	//0=nuevo
							"", (int)user.getCompanyid(), jsonFiles, protection, oldjson, newdata);
						if(nsucc<1)
							System.err.println("Sin cambios o no se pudo almacenar la notificación del amparo indirecto "
								+ protection);
					}
				} catch (IOException ex) {
					System.err.println("Sin cambios o no se pudo almacenar la notificación");
				}
				//Notify New (fin)
			}
		}}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewProtection(): " + ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDetailsByIndProt")
	public @ResponseBody Object[] getDetailsByIndProt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		List<Amparos> infoProt = null;
/*		List<Apelaciones> appeals = null;
		List<Juicios> trials = null;*/
		List<?> pec = null;
		int cid = 0;	//, juicioid = 0, appealid = 0;
		String ctname = "";
		validateData:{
		if(sess != null)
			if(sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
				int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim()),
					ccidA = 0, targetccid = 0;	//, companyid = 0
				if (id==0)
					break validateData;

				infoProt = amparosService.getAll("FROM Amparos WHERE amparoid=" + id);
				ccidA = infoProt.get(0).getCompanyclientid();

				// Verifica si el usuario/firma tiene acceso a ver el amparo
				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,0,ccidA);
				targetccid = Functions.toInt(acclt.get("companyclientid"));
				cid = Functions.toInt(acclt.get("clientid"));
				if(targetccid!=ccidA){
					ccidA=targetccid;
					infoProt=null;
					break validateData;
				}

				List <Clients> tmpct = clientsService.getAll("FROM Clients WHERE clientid=" + cid);
				ctname=tmpct.get(0).getClient();
				boolean turned = false;
				String turn=(infoProt.get(0).getTipodemandaturnadaa().toLowerCase()),
					query=" AS x LEFT JOIN Ciudades AS c ON c.ciudadid=x.ciudadid"
						+ " LEFT JOIN Estados AS e ON e.estadoid=c.estadoid"
						+ " LEFT JOIN Paises AS p ON p.paisid=e.paisid";
				int turnTo = Functions.toInt(infoProt.get(0).getDemandaamparoturnadaa());
				if(turnTo>0){
					if(turn.equals("unitario")){
						turned = true;
						query="TribunalUnitario" + query
							+ " WHERE x.tribunalUnitarioid="+turnTo;
					}else if(turn.equals("colegiado")){
						turned = true;
						query="TribunalColegiado" + query
							+ " WHERE x.tribunalcolegiadoid="+turnTo;
					}else if(turn.equals("federal")){
						turned = true;
						query="Juzgados" + query
							+ " WHERE x.juzgadoid=" + turnTo;
					}else if(infoProt.get(0).getJuicioid()!=null){
						turned = true;
	/*					juicioid = infoProt.get(0).getJuicioid();
						trials = juiciosService.getAll("FROM Juicios WHERE juicioid=" + juicioid);*/
						query="Juicios" + query + " WHERE x.juicioid=" + infoProt.get(0).getJuicioid();
	/*				}else if(infoProt.get(0).getApelacionid() != null){
						appealid = infoProt.get(0).getApelacionid();
						appeals = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid="
							+ appealid + " AND companyid=" + acclt.get("companyid"));*/
					}
					if(turned)
						pec = dao.sqlHQL("SELECT p.paisid, e.estadoid, c.ciudadid FROM " + query, null);
				}
			}
		}
		data.put("detail", infoProt);					
//		data.put("appeal", appeals);
//		data.put("trials", trials);
		data.put("cid", cid);
		data.put("clientName", ctname);
		data.put("pec", pec);
		return new Object[] { data };
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/updateIndProtection")
	public void updateIndProtection(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		validateData:{
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO user = (UserDTO) sess.getAttribute("UserDTO");
				int amparoid = (req.getParameter("amparoid") == null) ? 0
						: Functions.toInt(req.getParameter("amparoid").trim()),
					clientid = (req.getParameter("clientid") == null) ? 0
						: Functions.toInt(req.getParameter("clientid").trim()),
					apelacionid = (req.getParameter("apelacionid") == null) ? 0
						: Functions.toInt(req.getParameter("apelacionid").trim()),
					prottype = 2,
					juicioid = (req.getParameter("trialid") == null) ? 0
						: Functions.toInt(req.getParameter("trialid").trim()),
					firm = (req.getParameter("f2") == null) ? 0
						: Functions.toInt(req.getParameter("f2").trim()),
					companyid = user.getCompanyid();
				long ccid = 0;
				String /*custchar = (req.getParameter("custchar") == null) ? "" : req.getParameter("custchar").trim(),
					trialappeal = (req.getParameter("trialappeal") == null) ? ""
						: req.getParameter("trialappeal").trim(),*/
					origintype = (req.getParameter("origintype") == null) ? ""
						: req.getParameter("origintype").trim(),
					protection = (req.getParameter("protection") == null) ? ""
						: req.getParameter("protection").trim(),
					notebooktype = "principal",//Valor por default
					complaining = (req.getParameter("complaining") == null) ? ""
						: req.getParameter("complaining").trim(),
					interestedtrdparty = (req.getParameter("interestedtrdparty") == null) ? ""
						: req.getParameter("interestedtrdparty").trim(),
					claimedact = (req.getParameter("claimedact") == null) ? ""
						: req.getParameter("claimedact").trim(),
					respauth = (req.getParameter("respauth") == null) ? "" : req.getParameter("respauth").trim(),
					dateclaimedactNtn = (req.getParameter("dateclaimedactNtn") == null) ? ""
						: req.getParameter("dateclaimedactNtn").trim(),
					filingdatelawsuit = (req.getParameter("filingdatelawsuit") == null) ? ""
						: req.getParameter("filingdatelawsuit").trim(),
					demandprotsent = (req.getParameter("demandprotsent") == null) ? ""
						: req.getParameter("demandprotsent").trim(),
					admissiondate = (req.getParameter("admissiondate") == null) ? ""
						: req.getParameter("admissiondate").trim(),
					admissionnotifdate = (req.getParameter("admissionnotifdate") == null) ? ""
						: req.getParameter("admissionnotifdate").trim(),
					consthearingdate = (req.getParameter("consthearingdate") == null) ? ""
						: req.getParameter("consthearingdate").trim(),
					judgment = (req.getParameter("judgment") == null) ? "" : req.getParameter("judgment").trim(),
					rsrcreviewjudgment = (req.getParameter("rsrcreviewjudgment") == null) ? ""
						: req.getParameter("rsrcreviewjudgment").trim(),
					susp = (req.getParameter("provsusp") == null) ? "" : req.getParameter("provsusp").trim(),
					incidentalhearingdate = (req.getParameter("incidentalhearingdate") == null) ? ""
						: req.getParameter("incidentalhearingdate").trim(),
					adjournmentjudgment = (req.getParameter("adjournmentjudgment") == null) ? ""
						: req.getParameter("adjournmentjudgment").trim(),
					tipodemandaturnadaa = (req.getParameter("tipodemandaturnadaa") == null) ? ""
						: req.getParameter("tipodemandaturnadaa").trim();
				if(Functions.isEmpty(clientid)){
					resp="err_select_client";
					break validateData;
				}else if(origintype=="1" && juicioid==0){
					resp="err_select_origintrial";
					break validateData;
				}else if(origintype=="2" && apelacionid==0){
					resp="err_select_appealO";
					break validateData;
				}else if(Functions.isEmpty(respauth)){
					resp="err_enter_respauth";
					break validateData;
				}

				List<Amparos> currentprot = amparosService.getAll("FROM Amparos WHERE amparoid="+amparoid);
				if(user.getRole()==ROLE_SYSADMIN || user.getRole()==ROLE_CJADMIN){
					if(firm<=0){
						ccid=currentprot.get(0).getCompanyclientid();
					}else{
						companyid=firm;
						List<Companyclients> existCC = companyclientsService
							.getAll("FROM Companyclients WHERE companyid=" + companyid + " AND clientid=" + clientid);
		
						//Si no existe la relacion cliente-compañia, la crea.
						if (existCC.size() == 0) {
							Companyclients newcclient = new Companyclients();
							newcclient.setCompanyid(user.getCompanyid());
							newcclient.setClientid(clientid);
							ccid = companyclientsService.addNewCClient(newcclient);
						}else{
							ccid = existCC.get(0).getCompanyclientid();
						}
					}
				}else{
					ccid = currentprot.get(0).getCompanyclientid();
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
//TODO Notify Update Amp.Indir-1 (una línea)
				List<Amparos> olddata = amparosService.getAll("FROM Amparos WHERE amparoid="+amparoid);

				Amparos amparos = new Amparos();
				amparos.setAmparoid(amparoid);
				amparos.setAmparo(protection);
				amparos.setQuejoso(complaining);
				amparos.setApelacionid(apelacionid==0?null:apelacionid);
				amparos.setAutoridadresponsable(respauth);
				amparos.setActoreclamado(claimedact);
				if(!Functions.isEmpty(dateclaimedactNtn))
					amparos.setFechanotificacionactoreclamado(Functions.parseFecha(dateclaimedactNtn, "yyyy-MM-dd"));
				amparos.setTercero(interestedtrdparty);
				if(!Functions.isEmpty(filingdatelawsuit))
					amparos.setFechapresentaciondemanda(Functions.parseFecha(filingdatelawsuit, "yyyy-MM-dd"));
				amparos.setDemandaamparoturnadaa(demandprotsent);
				amparos.setTipodemandaturnadaa(tipodemandaturnadaa);
				if(!Functions.isEmpty(admissiondate))
					amparos.setFechadmision(Functions.parseFecha(admissiondate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(admissionnotifdate))
					amparos.setFechanotificaciondmision(Functions.parseFecha(admissionnotifdate, "yyyy-MM-dd"));
				amparos.setAmparotipo(prottype);// 2=Amparo indirecto
				if(!Functions.isEmpty(consthearingdate))
					amparos.setFechaaudicienciaconstitucional(Functions.parseFecha(consthearingdate, "yyyy-MM-dd"));
				amparos.setRecursorevisioncontrasentencia(rsrcreviewjudgment);
				amparos.setSentenciadefinitiva(adjournmentjudgment);
				amparos.setSuspensionprovisional(susp);
				amparos.setCuaderno(notebooktype);// Valor por default = "principal"
				amparos.setSentencia(judgment);
				if(!Functions.isEmpty(incidentalhearingdate))
					amparos.setFechaaudienciaincidental(Functions.parseFecha(incidentalhearingdate, "yyyy-MM-dd"));
				if(juicioid>0)
					amparos.setJuicioid((juicioid==0?null:juicioid));
				amparos.setCompanyclientid((int) ccid);
				amparosService.updateAmparo(amparos);

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator() + "AmparosInd", true);
				destinationPath = Functions.addPath(destinationPath, "" + amparoid, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				JsonObject jsonFiles = new JsonObject();
				JsonObject fileRec = new JsonObject();
				if(paths!=null){
					int idx = 0;
					for (File file : paths) {
						entidad = new Uploadfiles();
						entidad.setCatalogtype(4);//4=Amparos indirectos
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(amparoid));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
//TODO Notify Update Amp.Indir-2 (ini)
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
				List<Amparos> oldarray = new ArrayList<Amparos>(olddata);
				Gson gson = new Gson();
			    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(amparos);
			    @SuppressWarnings("unchecked")
				Menu modRefId = (Menu) dao
					.sqlHQLEntity("FROM Menu WHERE link LIKE 'indprotections' OR link LIKE 'indprotections.jet'", null);
				long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 4,	//4=amparos indirectos
					amparoid, (int) ccid, (int) user.getId(), 1,	//1=Edición
					"", firm, jsonFiles, protection, oldjson, newdata);
				if(nsucc<1)
					System.err.println("Sin cambios o no se pudo almacenar la notificación del amparo " + protection);
				resp = "msg_data_saved";
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	@ResponseBody
	@RequestMapping(value = "/indprotectiondashboard")
	public ModelAndView indprotectiondashboard(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int companyid = userDto.getCompanyid(), role = userDto.getRole(), clientid = 0;

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));

				String trialid = "", whereClause = "", shrTrials = commonsCtrll.getAllTrials(req,clientid,0).get("juiciosid"),
					amparoid = (req.getParameter("rid") == null) ? "" : req.getParameter("rid").trim(),
					hasSharedTrial = "", amparoturnadaa = "";

				// Obtiene los privilegios del módulo
				List<Privileges> lp = null;
				Map<String, Object> forModel = new HashMap<String, Object>();
				
				// se comento temporal x nueva version menu permisos
				/*
				List<Modules> lm = modulesService.getAll("FROM Modules WHERE name='indprotectiondashboard'");
				if(lm!=null&&lm.size()>0){
					String module = Long.toString(lm.get(0).getModuleid());
					String query = "FROM Privileges WHERE roleid=" + role + " AND module='" + module + "'";
					lp = privilegesService.getAll(query);
					dashboardVis=lp.get(0).getVis();
				}
				if(dashboardVis == 0)
					return new ModelAndView("indprotections", forModel);
				*/
				forModel.put("listp", lp);

				if(amparoid.equals("0"))return new ModelAndView("indprotectiondashboard", forModel);

				if (role>ROLE_SYSADMIN)
					whereClause = " WHERE companyid=" + userDto.getCompanyid();
				List<Companyclients> ccclient = companyclientsService
					.getAll("FROM Companyclients" + whereClause);
				String ccids = "";
				for (int i = 0; i < ccclient.size(); i++)
					ccids += ccclient.get(i).getCompanyclientid() + ",";
				ccids = ccids.replaceAll(".$", "");

				if(!Functions.isEmpty(shrTrials))
					hasSharedTrial+=" OR juicioid IN(" + shrTrials + ")";
				
				List<Amparos> amparo = amparosService.getAll("FROM Amparos WHERE amparoid='"
					+ amparoid + "' AND (companyclientid IN(" + ccids + ")" + hasSharedTrial + ")");
				forModel.put("amparo", amparo);

				List<Amparos> amparos2 = null;
				List<Apelaciones> appeal = null;
				List<Juicios> juicio = null;
				List<Juzgados> juzgado = null;
				List<Materias> materia = null;
				List<Ciudades> ciudad = null;
				List<Movimientos> movList = null;
				List<ECalendar> schedules = null;
				List<Recursos> rsc = null;
				if (amparo!=null) {
					if (!amparo.isEmpty()) {
						if (amparo.get(0).getApelacionid() != null) {
							int appealid = amparo.get(0).getApelacionid();
							appeal = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid=" + appealid
								+ " AND companyid=" + companyid + " ORDER BY apelacionid ASC");
							if (appeal!=null)
								if (!appeal.isEmpty())
									if (appeal.get(0).getJuicioid() != null)
										trialid = appeal.get(0).getJuicioid()+"";
						}
						if (amparo.get(0).getJuicioid() != null)
							if(!trialid.equals(amparo.get(0).getJuicioid()+""))
								trialid += (Functions.isEmpty(trialid)?"":",") + amparo.get(0).getJuicioid();
						if(!Functions.isEmpty(trialid)){
							juicio = juiciosService
								.getAll("FROM Juicios WHERE juicioid IN(" + trialid + ") AND (companyclientid IN(" + ccids + ")"
								+ hasSharedTrial + ")");
							amparos2 = amparosService.getAll("FROM Amparos WHERE juicioid IN(" + trialid
								+ ") AND amparoid != " + amparoid + " AND (companyclientid IN(" + ccids + ")"
								+ hasSharedTrial + ")");
							forModel.put("amparos2", amparos2);
						}
						movList = movimientosService
							.getAll("FROM Movimientos" + " WHERE amparoid=" + amparoid
								+ " ORDER BY LEAST (fechapresentacion,fechaacuerdo,fechanotificacion)");

						int turnTo = Functions.toInt(amparo.get(0).getDemandaamparoturnadaa());
						if(amparo.get(0).getTipodemandaturnadaa()!=null && turnTo>0)
							amparoturnadaa = catalogsCtrll.getCourtNameByIdnType(turnTo+"",amparo.get(0).getTipodemandaturnadaa());
						rsc = recursosService.getAll("FROM Recursos WHERE tipoorigen=2"	//2=Amparo indirecto
							+ " AND tipoorigenid=" + amparo.get(0).getAmparoid());

						ccclient = companyclientsService.getAll("FROM Companyclients WHERE companyclientid="
							+ amparo.get(0).getCompanyclientid());
						clientid = ccclient.get(0).getClientid();
					}
				}
				forModel.put("appeal", appeal);
				forModel.put("clientid", clientid);
				String movsIds = "";
				if (movList != null) {
					for (int i = 0; i < movList.size(); i++)
						movsIds += movList.get(i).getMovimientoid() + ",";
					movsIds = movsIds.replaceAll(".$", "");
					if (movsIds != "")
						schedules = eCalendarService.getAll("FROM ECalendar WHERE movimientoid IN(" + movsIds + ")");
				}
				if (!Functions.isEmpty(trialid) && juicio.size() > 0) {
					int juzgadoid = juicio.get(0).getJuzgadoid();
					juzgado = juzgadosService.getAll("FROM Juzgados WHERE juzgadoid=" + juzgadoid);

					int materiaid = juicio.get(0).getJuzgadoid();
					materia = materiasService.getAll("FROM Materias WHERE materiaid=" + materiaid);

					int ciudadid = juicio.get(0).getJuzgadoid();
					ciudad = ciudadesService.getAll("FROM Ciudades WHERE ciudadid=" + ciudadid);
				}

				List<TipoActuacion> actList = tipoactuacionService
					.getAll("FROM TipoActuacion ORDER BY tipoactuacionid ASC");
				forModel.put("juzgado", juzgado);
				forModel.put("materia", materia);
				forModel.put("ciudad", ciudad);
				forModel.put("juicio", juicio);
				forModel.put("movList", movList);
				forModel.put("actList", actList);
				forModel.put("schedules", schedules);
				forModel.put("turnadaa",amparoturnadaa);
				forModel.put("recursos",rsc);
				
				HashMap<String, Object> parameters = new HashMap<>();
				//parameters.put("movsIds", movsIds);
				parameters.put("catalogtype", 7);//7=Movimientos
				whereClause = "idregister=0";
				if (!Functions.isEmpty(movsIds))
					whereClause = "idregister in(" + movsIds + ")";
				@SuppressWarnings("unchecked")
				List<Uploadfiles> doctos = dao.sqlHQL("FROM Uploadfiles WHERE "
					+ whereClause + " AND path!='' AND catalogtype=:catalogtype", parameters);
				for (Uploadfiles doc : doctos) {
					doc.setPath(doc.getPath().substring(doc.getPath().indexOf("doctos")));
					doc.setImg(Functions.findExtentionFile(doc.getFilename()));
					System.out.println("::indprotectiondashboard:::" + doc.getPath());
				}
				forModel.put("doctos", doctos);

				// Permisos de movimientos caso de ser un juicio compartido
				HashMap<String, String> shMovPriv = new HashMap<>();
/*				if(juicio!=null){
					String originUsr = juicio.get(0).getUserid()==userDto.getId()
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
*/
//FIXME: Lineas temporales para Movimientos
				shMovPriv.put("origin","own");
				shMovPriv.put("shpriv","1,2,3,4");
				forModel.put("shmovpriv", shMovPriv);

				return new ModelAndView("indprotectiondashboard", forModel);
			}
		return new ModelAndView("login");
	}
}