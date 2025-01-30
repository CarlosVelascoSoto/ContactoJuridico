package com.aj.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.util.Calendar;
import java.util.Date;
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

import com.aj.model.Clients;
import com.aj.model.Consultas;
import com.aj.model.ECalendar;
import com.aj.model.Juicios;
import com.aj.model.Menu;
import com.aj.model.Movimientos;
import com.aj.model.Privileges;
import com.aj.model.Uploadfiles;
import com.aj.service.AccessDbJAService;
import com.aj.service.AmparosService;
import com.aj.service.ApelacionesService;
import com.aj.service.ClientsService;
import com.aj.service.CompanyclientsService;
import com.aj.service.ConsultasService;
import com.aj.service.ECalendarService;
import com.aj.service.JuiciosService;
import com.aj.service.MovimientosService;
import com.aj.service.RecursosService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;

@Controller
public class ConsultasController {
	@Autowired
	private CommonController commonsCtrll;

	@Autowired
	public ApelacionesService apelacionesService;

	@Autowired
	public AmparosService amparosService;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	public CompanyclientsService companyclientsService;

	@Autowired
	public ConsultasService consultasService;

	@Autowired
	public ECalendarService eCalendarService;

	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public MovimientosService movimientosService;

	@Autowired
	public RecursosService recursosService;

	@SuppressWarnings("rawtypes")
	@Autowired
	public AccessDbJAService dao;
	
	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	public static final int ROLE_SYSADMIN= 1, ROLE_CJADMIN = 2, ROLE_FIRMADMIN=3;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/consultas")
	public @ResponseBody ModelAndView consultasPage(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP_1.1.
				res.setHeader("Pragma", "no-cache");// HTTP1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");

				// Obtiene los privilegios del módulo
				Map<String, Object> consultas = new HashMap<String, Object>();
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "consultas");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				consultas.put("menu", menu);

				int clid= (req.getParameter("clid")== null) ? 0 : Functions.toInt(req.getParameter("clid").trim()),
					rid = (req.getParameter("rid") == null) ? 0 : Functions.toInt(req.getParameter("rid").trim());
// FIXME:
				String allIdCons = "";
/*				String allIdCons = commonsCtrll
					.checkAccessDoc(req, clid, 0, "consultaid", rid, "", false).get("consultas");

				Map<?, ?> info= (HashMap<?, ?>) commonsCtrll
					.checkAccessDoc(req,0,0,"re",rid,"","related","u.role").get(0);*/
				
				List<Consultas> laywerCons = null;
				if(!Functions.isEmpty(allIdCons))
					laywerCons = consultasService.getAll("SELECT o.consultaid, o.consulta, o.fecha,"
					+ " o.opinion, o.resumen, cl.client, m.materia, j.juicio, o.juicioid"
					+ " FROM Consultas AS o"
					+ " LEFT JOIN Juicios AS j ON j.juicioid=o.juicioid"
					+ " LEFT JOIN Clients AS cl ON cl.clientid=o.clienteid"
					+ " LEFT JOIN Materias AS m ON m.materiaid=o.materiaid"
					+ " WHERE o.consultaid IN(" + allIdCons + ") ORDER BY o.consultaid DESC");
				consultas.put("consultas", laywerCons);
				return new ModelAndView("consultas", consultas);
			}
		return new ModelAndView("login");
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/addNewConsulta")
	public @ResponseBody void addNewConsulta(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_unable_save_orby_session";
		validateData:{
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int clientid = (req.getParameter("clientid") == null) ? 0 : Functions.toInt(req.getParameter("clientid").trim()),
					materia = (req.getParameter("materia") == null) ? 0 : Functions.toInt(req.getParameter("materia").trim()),
					juicio = (req.getParameter("juicio") == null) ? 0 : Functions.toInt(req.getParameter("juicio").trim());
				String opiniones = (req.getParameter("opiniones") == null) ? "null" : req.getParameter("opiniones").trim(),
					resumen= (req.getParameter("resumen") == null) ? "null" : req.getParameter("resumen").trim(),
					consulta = (req.getParameter("consulta") == null) ? "" : req.getParameter("consulta").trim();
				BigDecimal honorarios = (req.getParameter("honorarios") == null)
					? BigDecimal.valueOf(0) : new BigDecimal(req.getParameter("honorarios").trim());

				if(Functions.isEmpty(consulta)||clientid==0||materia==0){
					resp = "err_"+(materia==0?"select_matter":clientid==0?"select_client":"enter_consult");
					break validateData;
				}

				// Confirma que el usuario tenga acceso al cliente
// FIXME
/*				String allIdCons = commonsCtrll
					.checkAccessDoc(req, clid, 0, "consultaid", rid, "", false).get("consultas");

				HashMap<String, String> acClt = commonsCtrll.checkAccessDoc(req, clientid, 0, "", 0, "", false);*/
				HashMap<String, String> acClt = null;


				if(Functions.isEmpty(acClt.get("clientid"))){
					resp = "err_record_no_saved";
					break validateData;
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
				Date now = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(now);
				String fechaStr = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR)  + ":"
					+ calendar.get(Calendar.MINUTE);
				Consultas consultas = new Consultas();
				consultas.setAbogadoid(Functions.toLong(acClt.get("id")));
				consultas.setConsulta(consulta);
				consultas.setHonorarios(honorarios);
				consultas.setResumen(resumen);
				consultas.setOpinion(opiniones);
				consultas.setClienteid(clientid);
				consultas.setJuicioid(juicio==0?null:juicio);
				consultas.setMateriaid(materia);
				consultas.setFecha(Functions.parseFecha(fechaStr, "yyyy-MM-dd HH:mm"));
				long succ = consultasService.addNewConsulta(consultas);

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
				"doctos" + FileSystems.getDefault().getSeparator() + "Consultas", true);
				destinationPath = Functions.addPath(destinationPath, "" + succ, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);

				Uploadfiles entidad = null;
				if(paths!=null)
					for (File file : paths) {
						entidad = new Uploadfiles();
						entidad.setCatalogtype(11);
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(succ));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
					}
				try {
					if (succ > 0) {
						resp="msg_data_saved";
						FileUtils.deleteDirectory(new File(tmpPath));
					} else
						resp="err_record_no_saved";
				} catch (IOException ex) {
					System.out.println("Exception in addNewConsulta(): " + ex.getMessage());
				}
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @RequestMapping(value = "/getClientConsList")
	public @ResponseBody Object[] getClientConsList(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		List<Consultas> data = null;
		if(sess != null){
			int clid = (req.getParameter("clid")== null) ? 0 : Functions.toInt(req.getParameter("clid").trim());
			
// FIXME
/*			String allIdCons = commonsCtrll
				.checkAccessDoc(req, clid, 0, "consultaid", rid, "", false).get("consultas");

			String cliCons = commonsCtrll.checkAccessDoc(req, clid, 0, "", 0, "", false).get("consultas");*/
			String cliCons = "";
			if(!Functions.isEmpty(cliCons))
				data = consultasService.getAll("SELECT cn.consultaid, cn.consulta, cn.fecha,"
				+ " j.juicio, m.materia FROM Consultas AS cn"
				+ " LEFT JOIN Juicios AS j ON j.juicioid=cn.juicioid"
				+ " LEFT JOIN Materias AS m ON m.materiaid=cn.materiaid"
				+ " WHERE cn.consultaid IN(" + cliCons + ") ORDER BY cn.consultaid DESC");
		}
		return new Object[] {data};
	}

	@RequestMapping(value = "/getDetailsByConsId")
	public @ResponseBody Object[] getDetailsByConsId(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		int consId = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
		List<Consultas> info = null;
		if(sess != null){
// FIXME
/*			String allIdCons = commonsCtrll
				.checkAccessDoc(req, clid, 0, "consultaid", rid, "", false).get("consultas");

			String cliCons = commonsCtrll.checkAccessDoc(req, 0, 0, "consultaid", consId, "", false).get("consultas");*/
			String cliCons = "";

			if(!Functions.isEmpty(cliCons))
				info = consultasService.getAll("FROM Consultas WHERE consultaid=" + consId);
		}
		data.put("detail", info);
		return new Object[] { data };
	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateConsultation")
	public @ResponseBody void updateConsultation(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_unable_save_orby_session";
		validateData:{
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int consultaid = (req.getParameter("consultaid") == null) ? 0
						: Functions.toInt(req.getParameter("consultaid").trim()),
					clientid = (req.getParameter("clientid") == null) ? 0
						: Functions.toInt(req.getParameter("clientid").trim()),
					materia = (req.getParameter("materia") == null) ? 0
						: Functions.toInt(req.getParameter("materia").trim()),
					juicio = (req.getParameter("juicio") == null) ? 0
						: Functions.toInt(req.getParameter("juicio").trim());
				String opiniones = (req.getParameter("opinion") == null) ? "null"
					: req.getParameter("opinion").trim(),
				resumen= (req.getParameter("resumen") == null) ? "null" : req.getParameter("resumen").trim(),
				consulta = (req.getParameter("consulta") == null) ? "" : req.getParameter("consulta").trim();
				BigDecimal honorarios = (req.getParameter("honorarios") == null)
					? BigDecimal.valueOf(0) : new BigDecimal(req.getParameter("honorarios").trim());

				if(Functions.isEmpty(consulta)||clientid==0||materia==0){
					resp="err_"+(materia==0?"select_matter":clientid==0?"select_client":"enter_consult");
					break validateData;
				}

				//Confirma que el usuario tenga acceso a la relación cliente-documento
// FIXME
/*			String allIdCons = commonsCtrll
					.checkAccessDoc(req, clid, 0, "consultaid", rid, "", false).get("consultas");

				String acClt = commonsCtrll.checkAccessDoc(req, clientid, 0, "consultaid", consultaid, "", false).get("consultas");
				*/
				String acClt = "";
				if(Functions.isEmpty(acClt)){
					resp = "err_record_no_saved";
					break validateData;
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
				List<Consultas> info = consultasService.getAll("FROM Consultas WHERE consultaid="+consultaid);
				Calendar calendar = Calendar.getInstance();
				Date tmpDate = info.get(0).getFecha();
				if(tmpDate==null)
					tmpDate=new Date();
				calendar.setTime(tmpDate);
				String fechaStr = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR)  + ":"
					+ calendar.get(Calendar.MINUTE);
				Consultas consultas = new Consultas();
				consultas.setConsultaid(consultaid);
				consultas.setAbogadoid(info.get(0).getAbogadoid());
				consultas.setConsulta(consulta);
				consultas.setHonorarios(honorarios);
				consultas.setResumen(resumen);
				consultas.setOpinion(opiniones);
				consultas.setClienteid(clientid);
				consultas.setJuicioid(juicio==0?null:juicio);
				consultas.setMateriaid(materia);
				consultas.setFecha(Functions.parseFecha(fechaStr, "yyyy-MM-dd HH:mm"));
				consultasService.updateConsulta(consultas);

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
				"doctos" + FileSystems.getDefault().getSeparator() + "Consultas", true);
				destinationPath = Functions.addPath(destinationPath, "" + consultaid, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				if(paths!=null)
					for (File file : paths) {
						entidad = new Uploadfiles();
						entidad.setCatalogtype(11);
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(consultaid));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
					}
				try {
					if(consultaid > 0){
						resp = "msg_data_saved";
						FileUtils.deleteDirectory(new File(tmpPath));// Proceso para eliminar carpeta temporal
					}else{
						res.getWriter().write("err_record_no_saved");
					}
				} catch (IOException ex) {
					System.out.println("Exception in updateConsultation(): " + ex.getMessage());
				}
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteConsultation")
	public void deleteConsultation(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int consultaid = req.getParameter("consultaid") == "" ? 0 : Functions.toInt(req.getParameter("consultaid").trim());
				consultasService.deleteConsulta(consultaid);
			}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@RequestMapping(value = "/consultasdashboard")
	public @ResponseBody ModelAndView consultasdashboard(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> forModel = new HashMap<String, Object>();
		if (sess != null)
		if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
			HashMap<String, String> cons = new HashMap<>();
			List<Clients> client = null;
			List<Movimientos> movList = null;
			List<ECalendar> schedules = null;
			List<Uploadfiles> doctos = null;
			String relDocs = "", juicioid = "";
			validateData:{
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP1.1.
				res.setHeader("Pragma", "no-cache");// HTTP1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
				int id = (req.getParameter("rid") == null) ? 0 : Functions.toInt(req.getParameter("rid").trim());
				
				// Obtiene los privilegios del módulo
				HashMap<Object, Object> parameters = new HashMap();
				String urlMethod="consultasdashboard";
				List<Privileges> lp = null;
				parameters.put("urlMethod", urlMethod);
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				forModel.put("menu", menu);
				forModel.put("listp", lp);
				
				if(id==0)break validateData;
// FIXME
/*			String allIdCons = commonsCtrll
					.checkAccessDoc(req, clid, 0, "consultaid", rid, "", false).get("consultas");

				HashMap<String, String> info = commonsCtrll.checkAccessDoc(req, 0, 0, "consultaid", id, "", false);*/
				HashMap<String, String> info = null;
				
				if(!Functions.isEmpty(info.get("consultas"))){
					List<Object[]> allData = (List<Object[]>) (List) dao
						.sqlHQL("SELECT cn.consultaid, cn.consulta, cn.fecha, cn.opinion,"
						+ " cn.resumen, cn.honorarios, cn.clienteid, cn.juicioid, cn.abogadoid, cn.materiaid,"
						+ " j.juicio, m.materia, u.first_name, u.last_name"
						+ " FROM Consultas AS cn"
						+ " LEFT JOIN Juicios AS j ON j.juicioid=cn.juicioid"
						+ " LEFT JOIN Materias AS m ON m.materiaid=cn.materiaid"
						+ " LEFT JOIN Users AS u ON u.id=cn.abogadoid"
						+ " WHERE cn.consultaid=" + id,null);
					Object[] tmp = allData.toArray();
				    for (Object record : tmp){
				    	Object[] data = (Object[]) record;
				    	juicioid = data[7]+"";
				    	cons.put("consultaid",data[0]+"");
						cons.put("consulta",data[1]+"");
						cons.put("fecha",data[2]+"");
						cons.put("opinion",data[3]+"");
						cons.put("resumen",data[4]+"");
						cons.put("honorarios", data[5]+"");
						cons.put("clienteid",data[6]+"");
						cons.put("juicioid",juicioid);
						cons.put("abogadoid",data[8]+"");
						cons.put("materiaid",data[9]+"");
						cons.put("juicio",data[10]+"");
						cons.put("materia",data[11]+"");
						cons.put("first_name",data[12]+"");
						cons.put("last_name",data[13]+"");
						break;
					}
				    client = clientsService.getAll("FROM Clients where clientid=" + info.get("clientid"));
				    relDocs = info.get("related");
					movList = movimientosService.getAll("FROM Movimientos WHERE consultaid=" + id
						+ " ORDER BY LEAST (fechapresentacion,fechaacuerdo,fechanotificacion)");
					if (movList != null)if (movList.size()>0){
						String movsIds = "";
						for (int i = 0; i < movList.size(); i++)
							movsIds += movList.get(i).getMovimientoid() + ",";
						movsIds = movsIds.replaceAll(",$", "");
						doctos = dao.sqlHQL("FROM Uploadfiles WHERE idregister IN(" + movsIds
							+ ") AND path!='' AND catalogtype=7", null);	// 7=Movimientos
						for (Uploadfiles doc : doctos) {
							doc.setPath(doc.getPath().substring(doc.getPath().indexOf("doctos")));
							doc.setImg(Functions.findExtentionFile(doc.getFilename()));
						}
						if (movsIds != "")
							schedules = eCalendarService.getAll("FROM ECalendar WHERE movimientoid IN(" + movsIds + ")");
					}

					// Permisos de moven caso de ser un juicio compartido

					HashMap<String, String> shMovPriv = new HashMap<>();
					if(!Functions.isEmpty(juicioid)){
						List<Juicios> juicio = juiciosService.getAll("FROM Juicios Where juicioid=" + juicioid);
						UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
						String originUsr = juicio.get(0).getUserid()==userDto.getId()
							|| (juicio.get(0).getAbogadoasignado()).equals(userDto.getId()+"")
							|| userDto.getRole()==ROLE_SYSADMIN || userDto.getRole()==ROLE_CJADMIN
							|| userDto.getRole()==ROLE_FIRMADMIN?"own":"shr";
						shMovPriv.put("origin",originUsr);
						if(juicio.get(0).getUserid()!=userDto.getId()){
							String query = "SELECT mp.menuid, mp.privilegesid "
								+ "FROM SharedDockets AS sh "
								+ "LEFT JOIN Menuprivileges AS mp ON mp.shareddocketid=sh.shareddocketid "
								+ "LEFT JOIN Menu AS m ON m.menuid=mp.menuid "
								+ "WHERE sh.juicioid=" + juicioid
								+ " AND menu='Movimientos de juicios'"	// Privilegios de movimientos
								+ " AND (sh.userid=" + userDto.getId()
								+ " OR sh.emailexternaluser='" + userDto.getEmail() + "'"
								+ ") ORDER BY mp.menuid ASC, mp.privilegesid ASC";
							List<?> shdata = dao.sqlHQL(query, null);
							tmp = shdata.toArray();
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
				}
			}
			forModel.put("client", client);
			forModel.put("cons", cons);
			forModel.put("relDocs", relDocs);
			forModel.put("movList", movList);
			forModel.put("schedules", schedules);
			forModel.put("doctos", doctos);
			return new ModelAndView("consultasdashboard", forModel);
		}
		return new ModelAndView("login");
	}
}