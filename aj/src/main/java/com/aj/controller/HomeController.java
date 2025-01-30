package com.aj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.model.Amparos;
import com.aj.model.Apelaciones;
import com.aj.model.Ciudades;
import com.aj.model.Clients;
import com.aj.model.Companyclients;
import com.aj.model.Consultas;
import com.aj.model.Juicios;
import com.aj.model.Juzgados;
import com.aj.model.Materias;
import com.aj.model.Recursos;
import com.aj.service.AccessDbJAService;
import com.aj.service.AmparosService;
import com.aj.service.ApelacionesService;
import com.aj.service.CiudadesService;
import com.aj.service.ClientsService;
import com.aj.service.CompanyclientsService;
import com.aj.service.ConsultasService;
import com.aj.service.JuiciosService;
import com.aj.service.JuzgadosService;
import com.aj.service.MateriasService;
import com.aj.service.RecursosService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;

@Controller
public class HomeController {
	@Autowired
	public CiudadesService ciudadesService;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	private CommonController commonsCtrll;

	@Autowired
	private ConsultasService consultationService;

	@Autowired
	public AmparosService amparosService;

	@Autowired
	public ApelacionesService apelacionesService;

	@Autowired
	public CompanyclientsService companyclientsService;

	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public JuzgadosService juzgadosService;

	@Autowired
	public RecursosService recursosService;

	@Autowired
	public MateriasService materiasService;

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

	@ResponseBody
	@RequestMapping(value = "/dashboardClient")
	public Object[] dashboardClient(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int role = userDto.getRole(),
					clientid = (req.getParameter("clid") == null) ? 0
						: Functions.toInt(req.getParameter("clid").trim());
				String whereClause = "", allCCids = "0,", 
					clauseCCid = "", allClientsIds = "", whereClause2 = "", ccidClause = "";
				HashMap<String, String> shrids = commonsCtrll.getAllTrials(req,clientid,0);
				List<Juicios> juicios = null;

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));

				// Obtiene los juicios compartidos (autorizados y provenientes firmas externas)
				if(shrids.get("juiciosid")!=null)
					if(shrids.get("juiciosid")=="")
						whereClause=" WHERE juicioid=0";
					else
						whereClause=" WHERE juicioid IN(" + shrids.get("juiciosid") + ")";
// Filtra los clientes que puede ver el usuario
if(clientid==0){
	if (role!=ROLE_SYSADMIN){
		clauseCCid = " LEFT JOIN Users AS u ON u.companyid=cc.companyid ";
		if (role==ROLE_CJADMIN){
			clauseCCid += " WHERE u.role<>"+ROLE_SYSADMIN;
		}else{
			clauseCCid += " WHERE u.role not IN("+ROLE_SYSADMIN + "," + ROLE_CJADMIN;
			if(role!=ROLE_FIRMADMIN)
				clauseCCid += "," + ROLE_FIRMADMIN;
			clauseCCid += ") AND cc.companyid="+userDto.getCompanyid();
		}
	}
}else{
	ccidClause="cc.clientid=" + clientid;
}
clauseCCid+=((Functions.isEmpty(clauseCCid))?" WHERE ": " AND ")+ccidClause;
List<Companyclients> tmpallCCids = companyclientsService
	.getAll("SELECT DISTINCT(cc.companyclientid), cc.clientid FROM Companyclients AS cc"
	+ clauseCCid.replaceAll(" AND\\s?$",""));
if(tmpallCCids.size()>0){
	Object[] tmp = tmpallCCids.toArray();
	for (Object cdata:tmp){
		Object[] obj= (Object[]) cdata;
		int v1 = (obj[0]==null)?0:(int) obj[0],
		   v2 = (obj[1]==null)?0:(int) obj[1];
		allCCids+=v1 + ",";
		if(clientid==0)
			allClientsIds+=v2 + ",";
	}
}
allCCids = allCCids.replaceAll(".$","");
allClientsIds = Functions.isEmpty(allClientsIds)?clientid+"":allClientsIds.replaceAll(".$","");

				juicios = juiciosService.getAll("FROM Juicios" + whereClause);
				data.put("totalTrials", juicios.size());
				
				// Apelaciones
				List<Apelaciones> appeals = apelacionesService.getAll("FROM Apelaciones" + whereClause);
				data.put("totalApl", appeals.size());

				if(!Functions.isEmpty(whereClause))
					whereClause2 = whereClause.replaceAll(" WHERE "," AND(") + " OR companyclientid IN(" + allCCids + "))";
				
				// Amparos directos
				List<Amparos> direct = amparosService.getAll("FROM Amparos WHERE amparotipo=1" + whereClause2);//amparotipo=1 (directos)
				data.put("totalProt", direct.size());

				// Amparos indirectos
				List<Amparos> indirect = amparosService.getAll("FROM Amparos WHERE amparotipo=2" + whereClause2);//amparotipo=2 (inddirectos)
				data.put("totalInd", indirect.size());

				// Recursos
				String clauseRec="", ids="";
				if(direct.size()>0){
					for(int i = 0; i < direct.size(); i++)
						ids += direct.get(i).getAmparoid() + ",";
					clauseRec+=" WHERE (tipoorigen=1 AND tipoorigenid IN(" + ids.replaceAll(".$", "") + "))";
				}
				if(indirect.size()>0){
					ids = "";
					for(int i = 0; i < indirect.size(); i++)
						ids += indirect.get(i).getAmparoid() + ",";
					clauseRec+=((Functions.isEmpty(clauseRec))?" WHERE ":" OR ")
						+ "(tipoorigen=2 AND tipoorigenid IN(" + ids.replaceAll(".$", "") + "))";
				}
				clauseRec=clauseRec.replaceAll("^ OR "," WHERE ");
				if(Functions.isEmpty(clauseRec))
					clauseRec = " WHERE recursoid=0";//Elimina null y obliga retornar 'cero registros'
				//List<Recursos> recursos = recursosService.getAll("FROM Recursos " + clauseRec);
				List<Recursos> recursos = recursosService
					.getAll("SELECT COUNT(*) FROM Recursos " + clauseRec);
				data.put("totResources", recursos.get(0));

				// Consultas
				String query = "SELECT COUNT(*) FROM Consultas WHERE clienteid=" + clientid;
				if(role!=ROLE_SYSADMIN && role!=ROLE_CJADMIN && role!=ROLE_FIRMADMIN)
					query += " AND abogadoid=" + acclt.get("id");
				List<Consultas> cons = consultationService.getAll(query);
				data.put("totalCons", cons.get(0));

				// Datos del cliente
				List<Clients> client = clientsService
					.getAll("FROM Clients where clientid IN(" + allClientsIds + ")");
				data.put("client", client);
			}
		return new Object[] {data};
	}

	/** Filtros para obtener los expedientes desde el apartado Fuero Común */
	@RequestMapping(value = "/getDocsByFilterCL")
	public @ResponseBody Object[] getDocsByFilterCL(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> info = new HashMap<String, Object>();
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String query = "", statusid = "", ccids = "", trials = "", clientIds = "",
					matterid = (req.getParameter("matterid") == null) ? "" : req.getParameter("matterid").trim();
				int clientid = 0, lawyerid = 0, usertype = 0,
					cityid = (req.getParameter("cityid") == null) ? 0 : Functions.toInt(req.getParameter("cityid").trim()),
					courtid= (req.getParameter("courtid")== null) ? 0 : Functions.toInt(req.getParameter("courtid").trim()),
					trialid= (req.getParameter("trialid")== null) ? 0 : Functions.toInt(req.getParameter("trialid").trim());
				HashMap<String, String> trialsData = null;
				List<Clients> clients = null;
				List<Materias> matList = null;
				List<Ciudades> cityList = null;
				List<Juzgados> courtList = null;
				List<Juicios> allTrials = null;
				List<Companyclients> companycli = null;

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));
				usertype = Functions.toInt(acclt.get("usertype")); // 1=Cliente; 0 ó null=Abogado

				trialsData = commonsCtrll.getAllTrials(req,clientid,(trialid>0?trialid:0));
				ccids = trialsData.get("companyclientsid");
				trials = trialsData.get("juiciosid");
				if(usertype==0){
					statusid = (req.getParameter("statusid") == null) ? "" : req.getParameter("statusid").trim();
					lawyerid = (req.getParameter("lawyerid") == null) ? 0 : Functions.toInt(req.getParameter("lawyerid").trim());
					if(clientid==0 && !matterid.matches("[1-9][0-9]*") && cityid==0 && courtid==0 &&
						trialid==0 && !statusid.matches("[1-9][0-9]*") && lawyerid==0)
						return new Object[] { info };
					query+=lawyerid>0?" AND j.abogadoasignado='"+lawyerid+"'":"";
					query+=!statusid.isEmpty()?" AND j.status="+statusid:"";
				}else{
					if(!matterid.matches("[1-9][0-9]*") && cityid==0 && courtid==0 && trialid==0)
						return new Object[] { info };
				}
				query+=Functions.isEmpty(trials)?" AND j.juicioid=0":" AND j.juicioid IN(" + trials + ")";
				query+=cityid >0?" AND j.ciudadid=" +cityid:"";
				query+=courtid>0?" AND j.juzgadoid='"+courtid+"'":"";
				query+=" AND (j.materiaid"+(matterid.matches("[1-9][0-9]*")
					?" IN(" + matterid + ",0)":"!=0") + " OR j.materiaid IS NULL)";
				allTrials = juiciosService.getAll("FROM Juicios AS j" + (query.replaceAll("^ AND", " WHERE ")));

				// Obtiene los datos necesarios sólo de los juicios obtenidos.
				if(allTrials!=null)
					if(allTrials.size()>0){
						String courtIds = "",matIds = "",cityIds = "";
						for (int i = 0; i < allTrials.size(); i++){
							courtIds+=allTrials.get(i).getJuzgadoid()+",";
							matIds += allTrials.get(i).getMateriaid()+",";
							cityIds+= allTrials.get(i).getCiudadid() +",";
						}
						if(usertype==0){
							clients = clientsService.getAll("FROM Clients WHERE clientid IN("
								+ clientIds + ") ORDER BY client ASC");
							companycli=companyclientsService
								.getAll("FROM Companyclients WHERE companyclientid IN(" + ccids + ")");
							info.put("clients", clients);
							info.put("cclid", companycli);
						}
						courtList=juzgadosService.getAll("FROM Juzgados WHERE juzgadoid IN("
							+ courtIds.replaceAll(".$","")+") ORDER BY juzgado ASC");
						matList = materiasService.getAll("FROM Materias WHERE materiaid IN("
							+ matIds.replaceAll(".$","") + ") ORDER BY materia ASC");
						cityList= ciudadesService.getAll("FROM Ciudades WHERE ciudadid IN ("
							+ cityIds.replaceAll(".$","")+ ") ORDER BY ciudad ASC");
					}
				info.put("juicios", allTrials);
				info.put("juzgados",courtList);
				info.put("materias",matList);
				info.put("cities", cityList);
			}
		return new Object[] { info };
	}	

	/** Filtros para obtener los expedientes desde el apartado Consejo de la Judicatura Federal */
	@RequestMapping(value = "/getDocsByFilterFD")
	public @ResponseBody Object[] getDocsByFilterFD(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> info = new HashMap<String, Object>();
		HttpSession sess = req.getSession(false);
		if (sess != null){
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int trialid = (req.getParameter("trialid") == null) ? 0 : Functions.toInt(req.getParameter("trialid").trim()),
					clientid = (req.getParameter("clientid") == null) ? 0 : Functions.toInt(req.getParameter("clientid").trim()),
					courtid = (req.getParameter("courtid") == null) ? 0 : Functions.toInt(req.getParameter("courtid").trim()),
					cityid = (req.getParameter("cityid") == null) ? 0 : Functions.toInt(req.getParameter("cityid").trim()),
					lawyerid = (req.getParameter("lawyerid") == null) ? 0 : Functions.toInt(req.getParameter("lawyerid").trim());

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));

				String statusid = (req.getParameter("statusid") == null) ? "" : req.getParameter("statusid").trim(),
					matterid = (req.getParameter("matterid") == null) ? "" : req.getParameter("matterid").trim();
				HashMap<String, String> trialsData = commonsCtrll.getAllTrials(req,clientid,(trialid>0?trialid:0));
				String trials = trialsData.get("juiciosid"), clientIds = trialsData.get("clientsid"),
					ccids = trialsData.get("companyclientsid"), query = "";
				List<Companyclients> companycli = null;
				List<Juzgados> courtList = null;
				List<Juicios> allTrials = null;
				List<Ciudades> cityList = null;
				List<Recursos> recursos = null;
				List<Materias> matList = null;
				List<Clients> clients = null;
				List<Amparos> amparos = null;
				// Juicios y otros datos
				if(Functions.isEmpty(trials))
					query+=" AND j.juicioid IN(" + trials + ")";

				if(Functions.isNumeric(matterid)){
					if(Functions.toInt(matterid)>0)
						query+=" AND (materiaid IN(" + matterid + ",0) OR materiaid IS NULL)";
					else if(matterid.equals("-1") || matterid.equals(""))
						query+=" AND materiaid=0 OR materiaid IS NULL";
				}
				if(cityid>0)query+=" AND j.ciudadid="+cityid;
				if(lawyerid>0)query+=" AND j.abogadoasignado='"+lawyerid+"'";
				if(!statusid.isEmpty())query+=" AND j.status="+statusid;
				if(courtid>0)query+=" AND j.juzgadoid='"+courtid+"'";
				if(Functions.isEmpty(query) && clientid==0 && trialid>=0)
					if(trialid==0)
						return new Object[] { info };
				if(trialsData.size()>0)
					query+=" AND j.juicioid IN(" + trials + ")";
				allTrials = juiciosService.getAll("FROM Juicios AS j" + (query.replaceAll("^ AND", " WHERE ")));

				// Obtiene los datos necesarios sólo de los juicios obtenidos.
				if(allTrials!=null)
					if(allTrials.size()>0){
						String courtIds = "",matIds = "",cityIds = "";
						for (int i = 0; i < allTrials.size(); i++){
							courtIds+=allTrials.get(i).getJuzgadoid()+",";
							matIds += allTrials.get(i).getMateriaid()+",";
							cityIds+= allTrials.get(i).getCiudadid() +",";
						}
						companycli=companyclientsService
							.getAll("FROM Companyclients WHERE companyclientid IN(" + ccids + ")");
						clients = clientsService
							.getAll("FROM Clients WHERE clientid IN(" + clientIds + ") ORDER BY client ASC");
						courtList=juzgadosService
							.getAll("FROM Juzgados WHERE juzgadoid IN(" + courtIds.replaceAll(".$","")+") ORDER BY juzgado ASC");
						matList = materiasService
							.getAll("FROM Materias WHERE materiaid IN(" + matIds.replaceAll(".$","") + ") ORDER BY materia ASC");
						cityList= ciudadesService
							.getAll("FROM Ciudades WHERE ciudadid IN (" + cityIds.replaceAll(".$","")+ ") ORDER BY ciudad ASC");
					}
				info.put("juicios", allTrials);
				info.put("ccli", companycli);
				info.put("clients", clients);
				info.put("juzgados",courtList);
				info.put("materias",matList);
				info.put("cities", cityList);
				info.put("amparos", amparos);
				info.put("recursos", recursos);
			}
		}
		return new Object[] { info };
	}

	@ResponseBody
	@RequestMapping(value = "/getFedDocsByFilter")
	public Object[] getFedDocsByFilter(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> info = new HashMap<String, Object>();
		HttpSession sess = req.getSession(false);
		if (sess != null){
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int role = userDto.getRole(), clientid=(req.getParameter("clientid") == null) ? 0
					: Functions.toInt(req.getParameter("clientid").trim());
				String allCCids = "", clauseCCid = "", allClientsIds = "", trialClause = "",
					clauseProt = "", clauseRsc = "", ids = "",
					doctype=(req.getParameter("doctype")==null) ? "" : req.getParameter("doctype").trim(),
					docid = (req.getParameter("docid") == null) ? "" : req.getParameter("docid").trim();

				HashMap<String, String> acclt = commonsCtrll.valAccessClientDocs(req,clientid,0);
				clientid = Functions.toInt(acclt.get("clientid"));

				// Obtiene los juicios propios del usuario o compartidos (autorizados y provenientes firmas externas)
				List<Amparos> protections = null;
				List<Recursos> resources = null;
				HashMap<String, String> shrids = commonsCtrll.getAllTrials(req,clientid,0);
				if(shrids.get("juiciosid")!=null){
					trialClause=(shrids.get("juiciosid")=="")?" WHERE (juicioid=0"
						:" WHERE (juicioid IN(" + shrids.get("juiciosid") + ")";
					allCCids+=shrids.get("companyclientsid") + ",";
					allClientsIds+=shrids.get("clientsid") + ",";
				}

		  		// Obtiene los clientes que puede ver el usuario
				if(clientid<=0){
					if (role!=ROLE_SYSADMIN){
						clauseCCid = " LEFT JOIN Users AS u ON u.companyid=cc.companyid ";
			  			if (role==ROLE_CJADMIN){
			  				clauseCCid += " WHERE u.role<>"+ROLE_SYSADMIN;
			  			}else{
			  				clauseCCid += " WHERE u.role not IN("+ROLE_SYSADMIN + "," + ROLE_CJADMIN;
			  				if(role!=ROLE_FIRMADMIN)
			  					clauseCCid += "," + ROLE_FIRMADMIN;
			  				clauseCCid += ") AND cc.companyid="+userDto.getCompanyid();
			  			}
					}
				}else{
					clauseCCid="cc.clientid=" + clientid;
				}
				if(clauseCCid.indexOf("WHERE")<8 && clauseCCid.indexOf("WHERE")==-1)
		        	clauseCCid=" WHERE "+clauseCCid;
				clauseCCid = clauseCCid.replaceAll("^\\s?AND\\s?"," WHERE ");

				List<Companyclients> tmpallCCids = companyclientsService
					.getAll("SELECT DISTINCT(cc.companyclientid), cc.clientid FROM Companyclients AS cc " + clauseCCid);
	  			if(tmpallCCids.size()>0){
					Object[] tmp = tmpallCCids.toArray();
					for (Object cdata:tmp){
						Object[] obj= (Object[]) cdata;
						int v1 = (obj[0]==null)?0:(int) obj[0],
							v2 = (obj[1]==null)?0:(int) obj[1];
						if(allCCids.indexOf(","+v1+",")<0 && !allCCids.startsWith(v1+","))
							allCCids+=v1+",";
						if(allClientsIds.indexOf(","+v2+",")<0 && !allClientsIds.startsWith(v2+","))
							allClientsIds+=v2+",";
					}
				}
	  			allCCids = allCCids.replaceAll(".$","");
	  			allClientsIds = allClientsIds.replaceAll(".$","");
				clauseProt = trialClause + " OR (juicioid IS NULL AND companyclientid IN(" + allCCids + ")))";

				// Amparos
				if(docid.matches("^0*(?!0)\\d+$"))
					clauseProt += " AND amparoid="+docid;
				clauseProt += (doctype.equals("1") || doctype.equals("2"))?" AND amparotipo=" + doctype:"";
				protections = amparosService.getAll("FROM Amparos " + clauseProt);

				// Recursos
				if(doctype.equals("0")||doctype.equals("3")){
					if(docid.matches("^0*(?!0)\\d+$")){
						clauseRsc += " AND recursoid="+docid;
					}else if(protections.size()>0 && protections!=null){
						for(int i = 0; i < protections.size(); i++)
							ids += protections.get(i).getAmparoid() + ",";
						clauseRsc += " AND tipoorigenid IN(" + ids.replaceAll(".$", "") + ")";
					}else{
						clauseRsc += " AND tipoorigen IN(1,2)";
					}
					resources = recursosService.getAll("FROM Recursos " + clauseRsc.replaceAll("^ AND "," WHERE "));
				}

				// Datos del cliente
				List<Clients> clients = clientsService
					.getAll("FROM Clients where clientid IN(" + allClientsIds + ")");
				info.put("clientid", clientid);
				info.put("ccli", tmpallCCids);
				info.put("clients", clients);
				info.put("amparos", protections);
				info.put("recursos", resources);
			}
		}
		return new Object[] { info };
	}
}
