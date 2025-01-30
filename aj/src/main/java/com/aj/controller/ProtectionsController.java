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
import com.aj.service.RecursosService;
import com.aj.service.TipoActuacionService;
import com.aj.service.TribunalColegiadoService;
import com.aj.service.TribunalUnitarioService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class ProtectionsController {
	@Autowired
	private CatalogsController catalogsCtrll;

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
	public ClientsService clientsService;

	@Autowired
	public CompanyclientsService companyclientsService;

	@Autowired
	public ECalendarService eCalendarService;

	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public JuzgadosService juzgadosService;

	@Autowired
	public MateriasService materiasService;

	@Autowired
	public MovimientosService movimientosService;

	@Autowired
	public RecursosService recursosService;

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

	@SuppressWarnings("unused")
	@RequestMapping(value = "/protections")
	public ModelAndView protections(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP1.1.
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				int role = 0, ccid = 0, companyid = 0, usertype = 0,
					clientid = (req.getParameter("clid") == null) ? 0
						: Functions.toInt(req.getParameter("clid").trim());//Variable para indicar que proviene de Home

				// Obtiene los privilegios del módulo
				@SuppressWarnings({ "unchecked", "rawtypes" })
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "protections");
				@SuppressWarnings("unchecked")
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));
				ccid = Functions.toInt(acclt.get("companyclientid"));
				role = Functions.toInt(acclt.get("role"));
				companyid = Functions.toInt(acclt.get("companyid"));
				usertype = Functions.toInt(acclt.get("usertype"));

				HashMap<String, String> shrTrials = commonsCtrll.getAllTrials(req,clientid,0);
				String whereClauseCli="", ccids = "", juiciosIds = shrTrials.get("juiciosid"), apelIds = "";

				if (!(role==ROLE_SYSADMIN||role==ROLE_CJADMIN)){
					whereClauseCli += " WHERE companyid=" + companyid;
					if(Functions.isEmpty(juiciosIds))
						juiciosIds = "0";
				}
				if(clientid>0)
					whereClauseCli+= (Functions.isEmpty(whereClauseCli)?" WHERE":" AND") + " companyclientid=" + ccid;
				List<Companyclients> ccclient = companyclientsService.getAll("FROM Companyclients" + whereClauseCli);
				for (int i = 0; i < ccclient.size(); i++)
					ccids += ccclient.get(i).getCompanyclientid() + ",";
				ccids = ccids.replaceAll(".$", "");
				if(!Functions.isEmpty(shrTrials.get("companyclientsid")))
					ccids += "," + shrTrials.get("companyclientsid");

				List<Apelaciones> appeals = apelacionesService.getAll("FROM Apelaciones" 
					+ (Functions.isEmpty(juiciosIds)?"":" WHERE juicioid IN(" + juiciosIds + ")"));
				for (int i = 0; i < appeals.size(); i++)
					apelIds += appeals.get(i).getApelacionid() + ",";
				apelIds = apelIds.replaceAll(".$", "");

				String whereClause=(Functions.isEmpty(ccids)?"":" AND (companyclientid IN(" + ccids + ")");
				whereClause+=(Functions.isEmpty(juiciosIds)?"":" OR juicioid IN(" + juiciosIds + ")");
				whereClause+=(Functions.isEmpty(apelIds)?"":" OR apelacionid IN(" + apelIds + ")");
				List<Amparos> amparos = amparosService.getAll("FROM Amparos WHERE amparotipo=1" //amparotipo=1 (directos)
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
/* / ********** Test nuevo validador (ini) ********
				HashMap<String, String> infoApl = valAcsDocs(req, clientid, 0, 0, 0, 0, 0);
				String abc_amp = infoApl.get("amparosid"),
					def_rsc = infoApl.get("recursosid");
// ********** Test nuevo validador (fin) ********/
				return new ModelAndView("protections", forModel);
			}
		return new ModelAndView("login");
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/addNewProtection")
	public void addNewProtection(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		validateData:{
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO user = (UserDTO) sess.getAttribute("UserDTO");
				int clientid = (req.getParameter("clientid") == null) ? 0
						: Functions.toInt(req.getParameter("clientid").trim()),
					prottype = 1,
					juicioid = (req.getParameter("trialid") == "") ? 0
						: Functions.toInt(req.getParameter("trialid").trim()),
					firm = (req.getParameter("f1") == null) ? 0
						: Functions.toInt(req.getParameter("f1").trim()),
					companyid = user.getCompanyid();
				String apelacionid = (req.getParameter("apelacionid") == null) ? ""
						: req.getParameter("apelacionid").trim(),
					custchar = (req.getParameter("custchar") == null) ? "" : req.getParameter("custchar").trim(),
					clientName = (req.getParameter("clientName") == null) ? ""
						: req.getParameter("clientName").trim(),
					origintype = (req.getParameter("origintype") == null) ? ""
						: req.getParameter("origintype").trim(),
					protection = (req.getParameter("protection") == null) ? ""
						: req.getParameter("protection").trim(),
					respauth = (req.getParameter("respauth") == null) ? "" : req.getParameter("respauth").trim(),
					claimedact = (req.getParameter("claimedact") == null) ? ""
						: req.getParameter("claimedact").trim(),
					demandprotsent = (req.getParameter("demandprotsent") == null) ? ""
						: req.getParameter("demandprotsent").trim(),
					stay = (req.getParameter("stay") == null) ? "" : req.getParameter("stay").trim(),

					dateclaimedact = (req.getParameter("dateclaimedact") == null) ? ""
						: req.getParameter("dateclaimedact").trim(),
					dateclaimedactNtn = (req.getParameter("dateclaimedactNtn") == null) ? ""
						: req.getParameter("dateclaimedactNtn").trim(),
					filingdatelawsuit = (req.getParameter("filingdatelawsuit") == null) ? ""
						: req.getParameter("filingdatelawsuit").trim(),
					admissiondate = (req.getParameter("admissiondate") == null) ? ""
						: req.getParameter("admissiondate").trim(),
					admissionnotifdate = (req.getParameter("admissionnotifdate") == null) ? ""
						: req.getParameter("admissionnotifdate").trim(),
					adhesiveaDirPtDate = (req.getParameter("adhesiveaDirPtDate") == null) ? ""
						: req.getParameter("adhesiveaDirPtDate").trim(),
					dateshiftpresent = (req.getParameter("dateshiftpresent") == null) ? ""
						: req.getParameter("dateshiftpresent").trim(),
					projectjudgmentdate = (req.getParameter("projectjudgmentdate") == null) ? ""
						: req.getParameter("projectjudgmentdate").trim(),
					judgmentdate = (req.getParameter("judgmentdate") == null) ? ""
						: req.getParameter("judgmentdate").trim(),
					judgmentnotifdate = (req.getParameter("judgmentnotifdate") == null) ? ""
						: req.getParameter("judgmentnotifdate").trim(),
					reviewresourcedate = (req.getParameter("reviewresourcedate") == null) ? ""
						: req.getParameter("reviewresourcedate").trim(),
					tipodemandaturnadaa = (req.getParameter("tipodemandaturnadaa") == null) ? ""
						: req.getParameter("tipodemandaturnadaa").trim();
				
				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));

				if(Functions.isEmpty(clientid)){
					resp="err_select_client";
					break validateData;
				}else if(Functions.isEmpty(claimedact)){
					resp="err_enter_claimedact";
					break validateData;
				}else if(Functions.isEmpty(respauth)){
					resp="err_enter_respauth";
					break validateData;
				}
				if(origintype.equals("Juicio")){
					if(juicioid==0){
						resp="err_select_origintrial";
						break validateData;
					}
				}else if(origintype.equals("Apelación")){
					if(Functions.isEmpty(apelacionid)){
						resp="err_select_appealO";
						break validateData;
					}
				}
//TODO firm
				if(firm!=0)
					companyid=firm;
				// Si no existe la relación firma-cliente, la crea
				List<Companyclients> existCC = companyclientsService
					.getAll("FROM Companyclients WHERE companyid=" + companyid + " AND clientid=" + clientid);
				long ccid = 0;
				if (existCC.size() == 0) {
					Companyclients cclient = new Companyclients();
					cclient.setCompanyid(companyid);
					cclient.setClientid(clientid);
					ccid = companyclientsService.addNewCClient(cclient);
				} else {
					ccid = existCC.get(0).getCompanyclientid();
				}
//TODO fin firm
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

				//Si no existe la relacion cliente-compañia, la crea.
				List<Companyclients> compclient = companyclientsService.getAll("FROM Companyclients WHERE companyid="
					+ user.getCompanyid() + " AND clientid=" + clientid);
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

				if (custchar.indexOf("Quejoso") >= 0) {
					amparos.setQuejoso(clientName);
					amparos.setTercero("");
				} else {
					amparos.setTercero(clientName);
					amparos.setQuejoso("");
				}

				amparos.setAutoridadresponsable(respauth);
				amparos.setActoreclamado(claimedact);
				if(!Functions.isEmpty(dateclaimedact))
					amparos.setFechaactoreclamado(Functions.parseFecha(dateclaimedact, "yyyy-MM-dd"));
				if(!Functions.isEmpty(dateclaimedactNtn))
					amparos.setFechanotificacionactoreclamado(Functions.parseFecha(dateclaimedactNtn, "yyyy-MM-dd"));
				if(!Functions.isEmpty(filingdatelawsuit))
					amparos.setFechapresentaciondemanda(Functions.parseFecha(filingdatelawsuit, "yyyy-MM-dd"));
				amparos.setDemandaamparoturnadaa(demandprotsent);
				amparos.setTipodemandaturnadaa(tipodemandaturnadaa);
				if(!Functions.isEmpty(admissiondate))
					amparos.setFechadmision(Functions.parseFecha(admissiondate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(admissionnotifdate))
					amparos.setFechanotificaciondmision(Functions.parseFecha(admissionnotifdate, "yyyy-MM-dd"));
				amparos.setSuspension(stay);
				if(!Functions.isEmpty(adhesiveaDirPtDate))
					amparos.setFechaamparodirectoadhesivo(Functions.parseFecha(adhesiveaDirPtDate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(dateshiftpresent))
					amparos.setFechaturnoaponencia(Functions.parseFecha(dateshiftpresent, "yyyy-MM-dd"));
				if(!Functions.isEmpty(projectjudgmentdate))
					amparos.setFechasesionproyectosentencia(Functions.parseFecha(projectjudgmentdate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(judgmentdate))
					amparos.setFechasentencia(Functions.parseFecha(judgmentdate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(judgmentnotifdate))
					amparos.setFechanotificacionsentencia(Functions.parseFecha(judgmentnotifdate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(reviewresourcedate))
					amparos.setFecharecursorevision(Functions.parseFecha(reviewresourcedate, "yyyy-MM-dd"));
				amparos.setAmparotipo(prottype);// 1=Directo,2=Indirecto
				amparos.setCompanyclientid((int) ccid);

				if (origintype.indexOf("Juicio") >= 0)
					amparos.setJuicioid(juicioid);
				else
					amparos.setApelacionid(Functions.toInt(apelacionid));
				long succ = amparosService.addNewAmparo(amparos);

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator() + "Amparos", true);
				destinationPath = Functions.addPath(destinationPath, "" + succ, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				JsonObject jsonFiles = new JsonObject();
				JsonObject fileRec = new JsonObject();
				if(paths!=null){
					int idx = 0;
					for (File file : paths) {
						entidad = new Uploadfiles();
						if(prottype==1)
							entidad.setCatalogtype(3);//3=Amparos directos
						else
							entidad.setCatalogtype(4);//4=Amparos indirectos
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(succ));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
//TODO Notify add-Dprot (ini)
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
					List<Amparos> oldarray = new ArrayList<Amparos>();
					amparos.setApelacionid((int) succ);
					@SuppressWarnings("unchecked")
					Menu modRefId = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link LIKE 'protections' OR link LIKE 'protections.jet'", null);
					Gson gson = new Gson();
				    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(amparos);
				    long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 3,	//3=amparos directos
						(int) succ, 0, (int) user.getId(), 0,	//0=nuevo
						"", (int)user.getCompanyid(), jsonFiles, protection, oldjson, newdata);
					if(nsucc<1)
						System.err.println("Sin cambios o no se pudo almacenar la notificación del amparo " + protection);
				}
				//Notify (fin)
			}
		}}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewProtection(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getDetailsByProt")
	public Object[] getDetailsByProt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		List<Amparos> infoProt = null;
		//List<Apelaciones> appeals = null;
		//List<Juicios> trials = null;
		int cid = 0;	//, juicioid = 0, appealid = 0;
		String ctname = "";
		validateData:{
		if(sess != null)
			if(sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
				int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim()),
					ccidA = 0, targetccid = 0;	// companyid = 0,
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
/*				if(infoProt.get(0).getApelacionid() != null){
					appealid = infoProt.get(0).getApelacionid();
					appeals = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid="
						+ appealid + " AND companyid=" + companyid);
				}else if (infoProt.get(0).getJuicioid() != null){
					juicioid = infoProt.get(0).getJuicioid();
					trials = juiciosService.getAll("FROM Juicios WHERE juicioid=" + juicioid);
				}*/
				List <Clients> tmpct = clientsService.getAll("FROM Clients WHERE clientid=" + cid);
				ctname=tmpct.get(0).getClient();
			}
		}
		data.put("detail", infoProt);					
//		data.put("appeals", appeals);
//		data.put("trials", trials);
		data.put("client", cid);
		data.put("clientName", ctname);
		return new Object[] { data };
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateProtection")
	public @ResponseBody void updateProtection(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		validateData:{
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO user = (UserDTO) sess.getAttribute("UserDTO");
				int amparoid = (req.getParameter("amparoid") == null) ? 0
						: Functions.toInt(req.getParameter("amparoid").trim()),
					clientid = (req.getParameter("clientid") == null) ? 0
						: Functions.toInt(req.getParameter("clientid").trim()),
					prottype = 1, firm = user.getCompanyid(),
					juicioid = (req.getParameter("trialid") == "") ? 0
						: Functions.toInt(req.getParameter("trialid").trim());
				String apelacionid = (req.getParameter("apelacionid") == null) ? ""
						: req.getParameter("apelacionid").trim(),
					protection = (req.getParameter("protection") == null) ? ""
						: req.getParameter("protection").trim(),
					custchar = (req.getParameter("custchar") == null) ? "" : req.getParameter("custchar").trim(),
					clientName = (req.getParameter("clientName") == null) ? ""
						: req.getParameter("clientName").trim(),
					origintype = (req.getParameter("origintype") == null) ? ""
						: req.getParameter("origintype").trim(),
					respauth = (req.getParameter("respauth") == null) ? "" : req.getParameter("respauth").trim(),
					claimedact = (req.getParameter("claimedact") == null) ? ""
						: req.getParameter("claimedact").trim(),
					demandprotsent = (req.getParameter("demandprotsent") == null) ? ""
						: req.getParameter("demandprotsent").trim(),
					stay = (req.getParameter("stay") == null) ? "" : req.getParameter("stay").trim(),
					dateclaimedact = (req.getParameter("dateclaimedact") == null) ? ""
						: req.getParameter("dateclaimedact").trim(),
					dateclaimedactNtn = (req.getParameter("dateclaimedactNtn") == null) ? ""
						: req.getParameter("dateclaimedactNtn").trim(),
					filingdatelawsuit = (req.getParameter("filingdatelawsuit") == null) ? ""
						: req.getParameter("filingdatelawsuit").trim(),
					admissiondate = (req.getParameter("admissiondate") == null) ? ""
						: req.getParameter("admissiondate").trim(),
					admissionnotifdate = (req.getParameter("admissionnotifdate") == null) ? ""
						: req.getParameter("admissionnotifdate").trim(),
					adhesiveaDirPtDate = (req.getParameter("adhesiveaDirPtDate") == null) ? ""
						: req.getParameter("adhesiveaDirPtDate").trim(),
					dateshiftpresent = (req.getParameter("dateshiftpresent") == null) ? ""
						: req.getParameter("dateshiftpresent").trim(),
					projectjudgmentdate = (req.getParameter("projectjudgmentdate") == null) ? ""
						: req.getParameter("projectjudgmentdate").trim(),
					judgmentdate = (req.getParameter("judgmentdate") == null) ? ""
						: req.getParameter("judgmentdate").trim(),
					judgmentnotifdate = (req.getParameter("judgmentnotifdate") == null) ? ""
						: req.getParameter("judgmentnotifdate").trim(),
					reviewresourcedate = (req.getParameter("reviewresourcedate") == null) ? ""
						: req.getParameter("reviewresourcedate").trim(),
					tipodemandaturnadaa = (req.getParameter("tipodemandaturnadaa") == null) ? ""
						: req.getParameter("tipodemandaturnadaa").trim();
				
				if(Functions.isEmpty(clientid)){
					resp="err_select_client";
					break validateData;
				}else if(Functions.isEmpty(claimedact)){
					resp="err_enter_claimedact";
					break validateData;
				}else if(Functions.isEmpty(respauth)){
					resp="err_enter_respauth";
					break validateData;
				}
				if(origintype.equals("Juicio")){
					if(juicioid==0){
						resp="err_select_origintrial";
						break validateData;
					}
				}else if(origintype.equals("Apelación")){
					if(Functions.isEmpty(apelacionid)){
						resp="err_select_appealO";
						break validateData;
					}
				}

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

				//Si no existe la relacion cliente-compañia, la crea.
				List<Companyclients> compclient = companyclientsService
					.getAll("FROM Companyclients WHERE companyid=" + firm + " AND clientid=" + clientid);
				long ccid = 0;
				if (compclient.size() == 0) {
					Companyclients newcclient = new Companyclients();
					newcclient.setCompanyid(firm);
					newcclient.setClientid(clientid);
					ccid = companyclientsService.addNewCClient(newcclient);
				} else {
					ccid = compclient.get(0).getCompanyclientid();
				}
//TODO Notify Update Amp.Dir-1 (una línea)
				List<Amparos> olddata = amparosService.getAll("FROM Amparos WHERE amparoid="+amparoid);

				Amparos amparos = new Amparos();
				amparos.setAmparoid(amparoid);
				amparos.setAmparo(protection);

				if (custchar.indexOf("Quejoso") >= 0) {
					amparos.setQuejoso(clientName);
					amparos.setTercero("");
				} else {
					amparos.setTercero(clientName);
					amparos.setQuejoso("");
				}

				amparos.setAutoridadresponsable(respauth);
				amparos.setActoreclamado(claimedact);
				if(!Functions.isEmpty(dateclaimedact))
					amparos.setFechaactoreclamado(Functions.parseFecha(dateclaimedact, "yyyy-MM-dd"));
				if(!Functions.isEmpty(dateclaimedactNtn))
					amparos.setFechanotificacionactoreclamado(Functions.parseFecha(dateclaimedactNtn, "yyyy-MM-dd"));
				if(!Functions.isEmpty(filingdatelawsuit))
					amparos.setFechapresentaciondemanda(Functions.parseFecha(filingdatelawsuit, "yyyy-MM-dd"));
				amparos.setDemandaamparoturnadaa(demandprotsent);
				amparos.setTipodemandaturnadaa(tipodemandaturnadaa);
				if(!Functions.isEmpty(admissiondate))
					amparos.setFechadmision(Functions.parseFecha(admissiondate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(admissionnotifdate))
					amparos.setFechanotificaciondmision(Functions.parseFecha(admissionnotifdate, "yyyy-MM-dd"));
				amparos.setSuspension(stay);
				if(!Functions.isEmpty(adhesiveaDirPtDate))
					amparos.setFechaamparodirectoadhesivo(Functions.parseFecha(adhesiveaDirPtDate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(dateshiftpresent))
					amparos.setFechaturnoaponencia(Functions.parseFecha(dateshiftpresent, "yyyy-MM-dd"));
				if(!Functions.isEmpty(projectjudgmentdate))
					amparos.setFechasesionproyectosentencia(Functions.parseFecha(projectjudgmentdate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(judgmentdate))
					amparos.setFechasentencia(Functions.parseFecha(judgmentdate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(judgmentnotifdate))
					amparos.setFechanotificacionsentencia(Functions.parseFecha(judgmentnotifdate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(reviewresourcedate))
					amparos.setFecharecursorevision(Functions.parseFecha(reviewresourcedate, "yyyy-MM-dd"));
				amparos.setAmparotipo(prottype);// 1=Directo,2=Indirecto
				amparos.setCompanyclientid((int) ccid);

				if (origintype.indexOf("Juicio") >= 0)
					amparos.setJuicioid(juicioid);
				else
					amparos.setApelacionid(Functions.toInt(apelacionid));
				amparosService.updateAmparo(amparos);

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator() + "Amparos", true);
				destinationPath = Functions.addPath(destinationPath, "" + amparoid, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				JsonObject jsonFiles = new JsonObject();
				JsonObject fileRec = new JsonObject();
				if(paths!=null){
					int idx = 0;
					for (File file : paths) {
						entidad = new Uploadfiles();
						if(prottype==1)
							entidad.setCatalogtype(3);//3=Amparos
						else
							entidad.setCatalogtype(4);//4=Amparos indirectos
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(amparoid));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
//TODO Notify Update Amp.Dir-2 (ini)
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
					.sqlHQLEntity("FROM Menu WHERE link LIKE 'protections' OR link LIKE 'protections.jet'", null);
				long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 3,	//3=amparos directos
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

	@RequestMapping(value = "/deleteProtection")
	public String deleteProtection(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String r = "false";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = req.getParameter("id") == "" ? 0 : Functions.toInt(req.getParameter("id").trim());
				
				List<Recursos> rec = recursosService.getAll("FROM Recursos WHERE tipoorigenid=" + id);
				if(rec.size()<1){
					amparosService.deleteAmparo(id);
					r = "true";
				}
			}
		}
		return r;
	}
	
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/protectiondashboard")
	public ModelAndView protectiondashboard(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");//HTTP1.1.
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				int clientid = 0, ccid = 0, companyid = 0, appealid = 0, trialid = 0, usertype = 0, role = 0;
				String amparoid = (req.getParameter("rid") == null) ? "" : req.getParameter("rid").trim(),
					whereClause = "", amparoturnadaa = "", ccClause = "";
				List<Amparos> amparo = null;
				List<Juicios> juicio = null;
				List<Apelaciones> appeal = null;
				List<Movimientos> movList = null;
				List<Juzgados> juzgado = null;
				List<Materias> materia = null;
				List<Ciudades> ciudad = null;
				List<ECalendar> schedules = null;
				List<Recursos> rsc = null;
				List<TipoActuacion> actList = null;
				List<Uploadfiles> doctos = null;

				// Obtiene los privilegios del módulo
				List<Privileges> lp = null;
				Map<String, Object> forModel = new HashMap<String, Object>();
				HashMap<String, Integer> shortProt = new HashMap<>();
				forModel.put("listp", lp);

				if(amparoid.equals("0"))return new ModelAndView("protectiondashboard", forModel);

				List<Amparos> amparotest = amparosService
					.getAll("SELECT a.companyclientid, a.apelacionid, a.juicioid, cc.companyid FROM Amparos AS a"
						+ " LEFT JOIN Companyclients AS cc ON cc.companyclientid=a.companyclientid"
						+ " WHERE a.amparoid=" + amparoid);
				if(amparotest!=null)
					if(amparotest.size()>0){
						Object[] tmp1 = amparotest.toArray();
						if(amparotest.size()>0)
							for (Object adata:tmp1){
								Object[] obj= (Object[]) adata;
								shortProt.put("companyclientid",(int)obj[0]);
								shortProt.put("apelacionid", (obj[1]==null?0:(int)obj[1]));
								shortProt.put("juicioid", (obj[2]==null?0:(int)obj[2]));
								shortProt.put("companyid",(int)obj[3]);
								break;
							}
						
						HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,0,shortProt.get("companyclientid"));
						usertype = Functions.toInt(acclt.get("usertype"));
						clientid = Functions.toInt(acclt.get("clientid"));
						companyid = Functions.toInt(acclt.get("companyid"));
						ccid = Functions.toInt(acclt.get("companyclientid"));
						role = Functions.toInt(acclt.get("role"));
		
						if(clientid!=0){
							appealid = shortProt.get("apelacionid")==null?0:shortProt.get("apelacionid");
							trialid = shortProt.get("juicioid")==null?0:shortProt.get("juicioid");
							if(appealid>0)
								appeal = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid=" + appealid
									+ " AND companyid=" + companyid + " ORDER BY apelacionid ASC");
							if(trialid>0){
								String accessToTrial = commonsCtrll.getAllTrials(req,clientid,trialid).get("juiciosid");
								juicio = juiciosService.getAll("FROM Juicios WHERE juicioid IN(" + accessToTrial
										+ ") AND companyclientid IN(" + ccid + ")");
								amparo = amparosService.getAll("FROM Amparos WHERE juicioid IN(" + accessToTrial
									+ ") AND amparoid=" + amparoid + ccClause);
								forModel.put("amparos2", amparo);
								int juzgadoid= juicio.get(0).getJuzgadoid(),
									materiaid= juicio.get(0).getMateriaid(),
									ciudadid = juicio.get(0).getCiudadid();
									juzgado= juzgadosService.getAll("FROM Juzgados WHERE juzgadoid="+ juzgadoid);
									materia= materiasService.getAll("FROM Materias WHERE materiaid="+ materiaid);
									ciudad = ciudadesService.getAll("FROM Ciudades WHERE ciudadid=" + ciudadid);
							}
							movList = movimientosService.getAll("FROM Movimientos WHERE amparoid=" + amparoid
								+ " ORDER BY LEAST (fechapresentacion,fechaacuerdo,fechanotificacion)");
							if(amparo!=null)
								if(amparo.size()>0){
									if(amparo.get(0).getTipodemandaturnadaa() != null)
										amparoturnadaa = catalogsCtrll.getCourtNameByIdnType(
											amparo.get(0).getDemandaamparoturnadaa(),amparo.get(0).getTipodemandaturnadaa());
									rsc = recursosService.getAll("FROM Recursos WHERE tipoorigen=1"	//1=Amparo directo
										+ " AND tipoorigenid=" + amparo.get(0).getAmparoid());
								}
						}

						String movsIds = "";
						if (movList != null) {
							for (int i = 0; i < movList.size(); i++)
								movsIds += movList.get(i).getMovimientoid() + ",";
							movsIds = movsIds.replaceAll(".$", "");
							if (movsIds != "")
								schedules = eCalendarService.getAll("FROM ECalendar WHERE movimientoid IN(" + movsIds + ")");
						}
						actList = tipoactuacionService
							.getAll("FROM TipoActuacion ORDER BY tipoactuacionid ASC");
		
						HashMap<String, Object> parameters = new HashMap<>();
						parameters.put("catalogtype", 7);	//7=Movimientos
						whereClause = "idregister=0";
						if (!Functions.isEmpty(movsIds))
							whereClause = "idregister in(" + movsIds + ")";
						doctos = dao.sqlHQL("FROM Uploadfiles WHERE "
							+ whereClause + " AND path!='' AND catalogtype=:catalogtype", parameters);
						for (Uploadfiles doc : doctos) {
							doc.setPath(doc.getPath().substring(doc.getPath().indexOf("doctos")));
							doc.setImg(Functions.findExtentionFile(doc.getFilename()));
							//System.out.println("::protectiondashboard:::" + doc.getPath());
						}
					}

				// Permisos de movimientos caso de ser un juicio compartido
				HashMap<String, String> shMovPriv = new HashMap<>();
/*				if(juicio!=null){
					UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
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

				forModel.put("amparo", amparo);
				forModel.put("appeal", appeal);
				forModel.put("juicio", juicio);
				forModel.put("doctos", doctos);
				forModel.put("ciudad", ciudad);
				forModel.put("movList", movList);
				forModel.put("juzgado", juzgado);
				forModel.put("materia", materia);
				forModel.put("actList", actList);
				forModel.put("clientid", clientid);
				forModel.put("recursos", rsc);
				forModel.put("turnadaa", amparoturnadaa);
				forModel.put("schedules", schedules);
				return new ModelAndView("protectiondashboard", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/getProtectionsByClient")
	public Object[] getProtectionsByClient(HttpServletRequest req, HttpServletResponse res) {
		int clientid = req.getParameter("clientid") == null ? 0 : Functions.toInt(req.getParameter("clientid")),
			origintype=req.getParameter("origintype")==null ? 0 : Functions.toInt(req.getParameter("origintype"));
		Map<String, Object> data = new HashMap<String, Object>();
		if(clientid > 0){
			HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
			clientid = Functions.toInt(acclt.get("clientid"));
			String allTrials = commonsCtrll.getAllTrials(req, clientid, 0).get("juiciosid"),
				whereClause = "", ccids = "0,";
			ccids += acclt.get("companyclientid");
			if(!Functions.isEmpty(allTrials))
				whereClause = " OR juicioid IN(" + allTrials + ")";

			List<Amparos> list = amparosService.getAll("FROM Amparos WHERE amparotipo=" + origintype
				+ " AND (companyclientid IN(" + ccids.replaceAll(",$","") + ")" + whereClause + ")");
			data.put("list", list);
		}
		return new Object[] {data};
	}
}