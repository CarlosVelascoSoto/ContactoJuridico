package com.aj.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.aj.model.ECalendar;
import com.aj.model.Menu;
import com.aj.model.Movimientos;
import com.aj.model.Privileges;
import com.aj.model.Recursos;
import com.aj.model.Uploadfiles;
import com.aj.service.AccessDbJAService;
import com.aj.service.AmparosService;
import com.aj.service.ClientsService;
import com.aj.service.CompanyclientsService;
import com.aj.service.ECalendarService;
import com.aj.service.JuiciosService;
import com.aj.service.MovimientosService;
import com.aj.service.RecursosService;
import com.aj.utility.Functions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class ResourcesController {
	@Autowired
	private CommonController commonsCtrll;

	@Autowired
	private NotificationsController notificationsCtrll;

	@Autowired
	public AmparosService amparosService;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	public CompanyclientsService companyclientsService;

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

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static final int ROLE_SYSADMIN= 1, ROLE_CJADMIN = 2, ROLE_FIRMADMIN=3;

	@SuppressWarnings({"unchecked","rawtypes"})
	@RequestMapping(value = "/resources")
	public ModelAndView resources(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if(sess!=null)if(sess.getAttribute("isLogin")!=null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
			res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP1.1.
			res.setHeader("Pragma", "no-cache");// HTTP 1.0.
			res.setDateHeader("Expires", 0);
			res.setContentType("text/html; charset=utf-8");
			res.setCharacterEncoding("utf-8");
			int clientid = Functions.strToInt(req.getParameter("clid"));

			// Obtiene los privilegios del módulo
			HashMap<Object, Object> parameters = new HashMap();
			parameters.put("urlMethod", "resources");
			Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
			Map<String, Object> forModel = new HashMap<String, Object>();
			forModel.put("menu", menu);

			Map<Integer, Object> listRsc = commonsCtrll.checkAccessDoc(req,clientid,0,"re",0,"","list",
				"re.recurso,am.amparoid,am.amparo,am.amparotipo,re.recursotipo,re.recurrente,re.resolucionrecurrida");
			forModel.put("recursos",listRsc);
			return new ModelAndView("resources", forModel);
		}
		return new ModelAndView("login");
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	@RequestMapping(value = "/addNewResource")
	public @ResponseBody void addNewResource(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		validateData:{if(sess!=null)
			if(sess.getAttribute("isLogin")!=null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
				String resdoc = Functions.toStr(req.getParameter("resourcenumber")),
					resourcetype = Functions.toStr(req.getParameter("resourcetype")),
					recurring = Functions.toStr(req.getParameter("recurring")),
					resolutionAppl = Functions.toStr(req.getParameter("resolutionAppl")),
					datenoticeApplRs = Functions.toStr(req.getParameter("datenoticeApplRs")),
					interpositionRvwdate = Functions.toStr(req.getParameter("interpositionRvwdate")),
					dateadmissionApProc = Functions.toStr(req.getParameter("dateadmissionApProc")),
					daterefcollcourt = Functions.toStr(req.getParameter("daterefcollcourt")),
					resourcesent = Functions.toStr(req.getParameter("resourcesent")),
					dateadmissionCllCourt = Functions.toStr(req.getParameter("dateadmissionCllCourt")),
					notifdateadmissionCllCourt = Functions.toStr(req.getParameter("notifdateadmissionCllCourt")),
					adhesiveRvwAplDate = Functions.toStr(req.getParameter("adhesiveRvwAplDate")),
					dateshiftpresent = Functions.toStr(req.getParameter("dateshiftpresent")),
					sessiondateprojectRslc = Functions.toStr(req.getParameter("sessiondateprojectRslc")),
					resolutiondate = Functions.toStr(req.getParameter("resolutiondate"));
				int originType = Functions.strToInt(req.getParameter("originType")),
					originTypeId = Functions.strToInt(req.getParameter("originTypeId")),
					clientid = Functions.strToInt(req.getParameter("clientid"));

				if(Functions.isEmpty(resourcetype) || Functions.isEmpty(recurring) || clientid==0){
					resp="msg_empty_data";
					break validateData;
				}
				// Valida que el usuario tenga acceso al documento "Tipo origen" del cliente.
				Map<?, ?> infoRec = (HashMap<?, ?>) commonsCtrll.checkAccessDoc(req,clientid,0,"am",
					originTypeId,"","hasaccess","am.amparoid,u.id,u.companyid,cc.companyclientid").get(1);
				if(originTypeId!=Functions.toInt(infoRec.get("am.amparoid")+"")){
					resp="err_unable_get_protections";
					break validateData;
				}
				// Guardamos archivos relacionados con el registro
				String[] nameFiles = null;
				Enumeration enumeration = req.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String parameterName = (String) enumeration.nextElement();
					if(parameterName.contains("fileuploadx_") && !Functions.isEmpty(req.getParameter(parameterName))){
						if(nameFiles == null)
							nameFiles = new String[1];
						else
							nameFiles = (String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
						nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
					}
				}
				Recursos recurso = new Recursos();
				recurso.setRecurso(resdoc);
				recurso.setRecursotipo(resourcetype);
				recurso.setRecurrente(recurring);
				recurso.setResolucionrecurrida(resolutionAppl);
				if(originType>0){
					recurso.setTipoorigen(originType);
					recurso.setTipoorigenid(originTypeId);
				}
				if(!Functions.isEmpty(datenoticeApplRs))
					recurso.setFechanotificacionresolucionrecurrida(Functions.parseFecha(datenoticeApplRs, "yyyy-MM-dd"));
				if(!Functions.isEmpty(interpositionRvwdate))
					recurso.setFechainterposicionrecurso(Functions.parseFecha(interpositionRvwdate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(dateadmissionApProc))
					recurso.setFechaadmisiontramiterecurso(Functions.parseFecha(dateadmissionApProc, "yyyy-MM-dd"));
				if(!Functions.isEmpty(dateadmissionCllCourt))
					recurso.setFechaadmisiontribunalcolegiado(Functions.parseFecha(dateadmissionCllCourt, "yyyy-MM-dd"));
				recurso.setRecursoturnadoa(resourcesent);
				if(!Functions.isEmpty(daterefcollcourt))
					recurso.setFecharemisionaltribunalcolegiado(Functions.parseFecha(daterefcollcourt, "yyyy-MM-dd"));
				if(!Functions.isEmpty(notifdateadmissionCllCourt))
					recurso.setFechanotificacionadmisiontribunalcolegiado(Functions.parseFecha(notifdateadmissionCllCourt, "yyyy-MM-dd"));
				if(!Functions.isEmpty(adhesiveRvwAplDate))
					recurso.setFecharecursorevisionadhesivo(Functions.parseFecha(adhesiveRvwAplDate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(dateshiftpresent))
					recurso.setFechaturnoaponencia(Functions.parseFecha(dateshiftpresent, "yyyy-MM-dd"));
				if(!Functions.isEmpty(sessiondateprojectRslc))
					recurso.setFechasesionproyectoresolucion(Functions.parseFecha(sessiondateprojectRslc, "yyyy-MM-dd"));
				if(!Functions.isEmpty(resolutiondate))
					recurso.setFecharesolucion(Functions.parseFecha(resolutiondate, "yyyy-MM-dd"));
				long succ = recursosService.addNewRecurso(recurso);

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator() + "Recursos", true);
				destinationPath = Functions.addPath(destinationPath, "" + succ, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				JsonObject jsonFiles = new JsonObject();
				JsonObject fileRec = new JsonObject();
				if(paths!=null){
					int idx = 0;
					for (File file : paths) {
						entidad = new Uploadfiles();
						entidad.setCatalogtype(5);//5=recursos;
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(succ));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
//TODO Notify add-Rec (ini)
						JsonObject fileData = new JsonObject();
			    		fileData.addProperty("name", file.getName());
			    		fileData.addProperty("size", file.length());
			    		fileData.addProperty("status", "1");	//1=Nuevo
			    		fileRec.add(idx+"", fileData);
			    		idx++;
					}
					jsonFiles.add("file", fileRec);
				}
				if(succ>0)try{
					resp="msg_data_saved";
					recurso.setRecursoid((int) succ);
					FileUtils.deleteDirectory(new File(tmpPath));// elimina carpeta temporal
					List<Recursos> oldarray = new ArrayList<Recursos>();
					Menu modRefId = (Menu) dao
						.sqlHQLEntity("FROM Menu WHERE link LIKE 'resources' OR link LIKE 'resources.jet'", null);
					Gson gson = new Gson();
				    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(recurso);
					long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 5, //5=recursos
						(int) succ,
Functions.toInt(infoRec.get("cc.companyclientid")+""),
// Functions.toInt(infoRec.get("cc.companyclientid")),
Functions.toInt(infoRec.get("u.id")+""),
// Functions.toInt(infoRec.get("u.id")),
						0, "",		//0=nuevo
Functions.toInt(infoRec.get("u.companyid")+""),
// Functions.toInt(infoRec.get("u.companyid")), 
						jsonFiles, resdoc, oldjson, newdata);
					if(nsucc<1)
						System.err.println("Sin cambios o no se pudo almacenar la notificación del recurso "+resdoc);
				}catch(IOException ex){
					System.err.println("Sin cambios o no se pudo almacenar la notificación");
				}//Notify New (fin)
			}
		}
		try{res.getWriter().write(resp);
		}catch(IOException ex){System.out.println("Exception in addNewResource(): " + ex.getMessage());}
	}

	@RequestMapping(value = "/getDetailsByResource")
	public @ResponseBody Object[] getDetailsByResource(HttpServletRequest req, HttpServletResponse res) {
		int id = Functions.strToInt(req.getParameter("id"));
		Map<String, Object> data = new HashMap<String, Object>();
		Map<?, ?> info = (HashMap<?, ?>) commonsCtrll
			.checkAccessDoc(req,0,0,"re",id,"","hasaccess","c.clientid,c.client").get(1);

		if(id==Functions.strToInt(info.get("re.recursoid")+"")){
			List<Recursos> rsc = recursosService.getAll("FROM Recursos WHERE recursoid="+id);
			data.put("resource", rsc);
			data.put("client", info.get("c.clientid"));
			data.put("clientName", info.get("c.client"));
		}
		return new Object[] {data};
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	@RequestMapping(value = "/updateResource")
	public @ResponseBody void updateResource(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String msg = "err_record_no_saved";
		validateData:{if(sess!=null)
			if(sess.getAttribute("isLogin")!=null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
				int recid = Functions.strToInt(req.getParameter("id")),
					clientid = Functions.strToInt(req.getParameter("clientid")),
					originType = Functions.strToInt(req.getParameter("originType")),
					originTypeId = Functions.strToInt(req.getParameter("originTypeId"));
				String resourcenumber = Functions.toStr(req.getParameter("resourcenumber")),
					adhesiveRvwAplDate = Functions.toStr(req.getParameter("adhesiveRvwAplDate")),
					dateadmissionApProc = Functions.toStr(req.getParameter("dateadmissionApProc")),
					dateadmissionCllCourt = Functions.toStr(req.getParameter("dateadmissionCllCourt")),
					datenoticeApplRs = Functions.toStr(req.getParameter("datenoticeApplRs")),
					daterefcollcourt = Functions.toStr(req.getParameter("daterefcollcourt")),
					dateshiftpresent = Functions.toStr(req.getParameter("dateshiftpresent")),
					interpositionRvwdate = Functions.toStr(req.getParameter("interpositionRvwdate")),
					notifdateadmissionCllCourt = Functions.toStr(req.getParameter("notifdateadmissionCllCourt")),
					sessiondateprojectRslc = Functions.toStr(req.getParameter("sessiondateprojectRslc")),
					recurring = Functions.toStr(req.getParameter("recurring")),
					resourcetype = Functions.toStr(req.getParameter("resourcetype")),
					resolutionAppl = Functions.toStr(req.getParameter("resolutionAppl")),
					resolutiondate = Functions.toStr(req.getParameter("resolutiondate")),
					resourcesent = Functions.toStr(req.getParameter("resourcesent"));

				if(Functions.isEmpty(resourcetype) || Functions.isEmpty(recurring)
				|| originType==0 || clientid==0){
					msg=originType==0?"err_select_origintype":"msg_empty_data";
					break validateData;
				}
				Map<?, ?> infoRec = (HashMap<?, ?>) commonsCtrll
					.checkAccessDoc(req,clientid,0,"re",recid,"","hasaccess","u.id,u.companyid,cc.companyclientid").get(1);
				int rscid = Functions.toInt(infoRec.get("re.recursoid")+"");

				if(recid!=rscid){
					msg="err_unable_get_resource";
					break validateData;
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
//TODO Notify Update Rec-1 (una línea)
				List<Recursos> olddata = recursosService.getAll("FROM Recursos WHERE recursoid="+recid);

				Recursos recurso = new Recursos();
				recurso.setRecursoid(recid);
				recurso.setRecurso(resourcenumber);
				recurso.setRecursotipo(resourcetype);
				recurso.setRecurrente(recurring);
				recurso.setResolucionrecurrida(resolutionAppl);
				if(originType>0){
					recurso.setTipoorigen(originType);
					recurso.setTipoorigenid(originTypeId);
				}
				if(!Functions.isEmpty(datenoticeApplRs))
					recurso.setFechanotificacionresolucionrecurrida(Functions.parseFecha(datenoticeApplRs, "yyyy-MM-dd"));
				if(!Functions.isEmpty(interpositionRvwdate))
					recurso.setFechainterposicionrecurso(Functions.parseFecha(interpositionRvwdate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(dateadmissionApProc))
					recurso.setFechaadmisiontramiterecurso(Functions.parseFecha(dateadmissionApProc, "yyyy-MM-dd"));
				if(!Functions.isEmpty(dateadmissionCllCourt))
					recurso.setFechaadmisiontribunalcolegiado(Functions.parseFecha(dateadmissionCllCourt, "yyyy-MM-dd"));
				recurso.setRecursoturnadoa(resourcesent);
				if(!Functions.isEmpty(daterefcollcourt))
					recurso.setFecharemisionaltribunalcolegiado(Functions.parseFecha(daterefcollcourt, "yyyy-MM-dd"));
				if(!Functions.isEmpty(notifdateadmissionCllCourt))
					recurso.setFechanotificacionadmisiontribunalcolegiado(
							Functions.parseFecha(notifdateadmissionCllCourt, "yyyy-MM-dd"));
				if(!Functions.isEmpty(adhesiveRvwAplDate))
					recurso.setFecharecursorevisionadhesivo(Functions.parseFecha(adhesiveRvwAplDate, "yyyy-MM-dd"));
				if(!Functions.isEmpty(dateshiftpresent))
					recurso.setFechaturnoaponencia(Functions.parseFecha(dateshiftpresent, "yyyy-MM-dd"));
				if(!Functions.isEmpty(sessiondateprojectRslc))
					recurso.setFechasesionproyectoresolucion(Functions.parseFecha(sessiondateprojectRslc, "yyyy-MM-dd"));
				if(!Functions.isEmpty(resolutiondate))
					recurso.setFecharesolucion(Functions.parseFecha(resolutiondate, "yyyy-MM-dd"));
				recursosService.updateRecurso(recurso);

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
					"doctos" + FileSystems.getDefault().getSeparator() + "Recursos", true);
				destinationPath = Functions.addPath(destinationPath, "" + recid, true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
				Uploadfiles entidad = null;
				JsonObject jsonFiles = new JsonObject();
				JsonObject fileRec = new JsonObject();
				if(paths!=null){
					int idx = 0;
					for (File file : paths) {
						entidad = new Uploadfiles();
						entidad.setCatalogtype(5);	//5=recursos;
						entidad.setFilename(file.getName());
						entidad.setIdregister(Functions.toInt(recid));
						entidad.setPath(file.getAbsolutePath());
						juiciosService.addUploaderFile(entidad);
//TODO Notify Update Rec-2 (ini)
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
				}catch (IOException e){
					System.err.println("No se pudo eliminar la carpeta temporal");
					e.printStackTrace();
				}
				List<Recursos> oldarray = new ArrayList<Recursos>(olddata);
				Gson gson = new Gson();
			    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(recurso);
				Menu modRefId = (Menu) dao
					.sqlHQLEntity("FROM Menu WHERE link LIKE 'resources' OR link LIKE 'resources.jet'", null);

			    long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 5,	//5=recursos
					recid, Functions.toInt(infoRec.get("cc.companyclientid")+""),
					Functions.toInt(infoRec.get("u.id")+""), 1, "",	//1=Edición
					Functions.toInt(infoRec.get("u.companyid")+""), jsonFiles, resourcenumber, oldjson, newdata);
				if(nsucc<1)
					System.err.println("Sin cambios o no se pudo almacenar la notificación del recurso " + resourcenumber);
				msg = "msg_data_saved";
			}
		}
		try {
			res.getWriter().write(msg);
		} catch (IOException ex) {
			System.out.println("Exception in updateIndRecurso(): " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteResource")
	public void deleteResource(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if(sess!=null)if(sess.getAttribute("isLogin")!=null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
			int id = Functions.strToInt(req.getParameter("id"));
			Map<?, ?> info= (HashMap<?, ?>) commonsCtrll
				.checkAccessDoc(req,0,0,"re",id,"","hasaccess","u.id,u.companyid").get(1);

			if(id==Functions.toInt(info.get("re.recursoid")+"")){
				JsonObject jsonFiles = new JsonObject();
				List<Recursos> olddata = recursosService.getAll("FROM Recursos WHERE recursoid="+id);				
				List<Recursos> oldarray = new ArrayList<Recursos>(olddata);
				Gson gson = new Gson();
				String oldjson = gson.toJson(oldarray), mClause = "",
					newdata = gson.toJson(new Recursos());

				// Elimina los archivos adjuntos del registro principal y de sus movimientos
				String allMovIds = "";
				try{
					List<Movimientos> movList = movimientosService
						.getAll("SELECT movimientoid FROM Movimientos WHERE recursoid=" + id);
					if(movList!=null && !movList.isEmpty()){
allMovIds = Functions.toStr(movList).replaceAll("\\[","").replaceAll("\\]","").replaceAll(",?$","");
System.out.println("allMovIds="+allMovIds);
mClause = "(idregister in("+ allMovIds + ")" + " AND path!=''AND catalogtype=7)";// 7=Movimientos
					}
					List<Uploadfiles> recDocs = dao.sqlHQL("FROM Uploadfiles WHERE (" + mClause
						+ (Functions.isEmpty(mClause)?"":" OR ")
						+ "(idregister=" + id + " AND path!='' AND catalogtype=5))", null);	// 5=Recursos
					if(recDocs != null && !recDocs.isEmpty()){
						int filesDel = 0, maxRetries = 3, waitTime = 1500; // Milisegundos
						for(int d=0; d<recDocs.size(); d++){
/*
File file = new File(recDocs.get(d).getPath());
System.out.println("Readable: " + file.canRead());
System.out.println("Writable: " + file.canWrite());
System.out.println("File Path: " + file.getAbsolutePath());
System.out.println("Exists: " + file.exists());
System.out.println("Is File: " + file.isFile());
*/

// Path Normalization
//Path path = Paths.get("/home/dev22/C:/temp/folder_ar/doctos/Recursos/30/2 bytes.txt");
Path path = Paths.get(recDocs.get(d).getPath());
File file = path.toFile();
System.out.println("File Path: " + file.getAbsolutePath());
System.out.println("Readable : " + file.canRead());
System.out.println("Writable : " + file.canWrite());
System.out.println("It Exists: " + file.exists());
System.out.println("Is a File: " + file.isFile());



		                    if(file.exists()){
		                    	if(file.delete()){
		                    		filesDel+=dao.updateSqlNative("DELETE FROM Uploadfiles WHERE uploadfileid=" + recDocs.get(d).getUploadfileid(), null);
File folder = file.getParentFile();
System.err.println("Es folder: " + folder.isDirectory());
		                        	if(folder==null || !folder.exists())
		                        		continue;	// No existe la carpeta
                        			for(int i = 0; i<maxRetries; i++){
                        				// Intenta eliminar la carpeta en caso de estar vacía
					                    String[] contents = folder.list();
					                    if(contents!=null && contents.length>0)break; // Carpeta no esta vacía
					                    boolean deleted = folder.delete();
					                    if(!deleted)System.err.println("Fallo al eliminar la carpeta: " + folder);
					                    else break;
					                    try{
					                        Thread.sleep(waitTime);
					                    }catch (InterruptedException e){
					                        Thread.currentThread().interrupt();
					                        System.err.println("Thread interrupted during retry: " + e.getMessage());
					                    }
                        			}
		                        }else{
		                        	System.err.println("Fallo al eliminar el archivo: " + file.getAbsolutePath());
		                        }
	                        }else{
		                    	System.err.println("Archivo o carpeta no encontrados: " + file);
		                    }
						}
						System.out.println("Archivos eliminados: " + filesDel);
					}
					// Elimina el registro principal y los movimientos
					if(!Functions.isEmpty(allMovIds)){
						String[] mids = allMovIds.split(",");
						for(int m=0; m<mids.length; m++)
System.out.println("Movimiento eliminado " + mids[m]);
//movimientosService.deleteMovto(Functions.toLong(mids[m]));
					}
//recursosService.deleteRecurso(id);
System.out.println("Recurso eliminado " + id);

					// Notificación
					Menu modRefId = (Menu) dao
						.sqlHQLEntity("FROM Menu WHERE link LIKE 'resources' OR link LIKE 'resources.jet'", null);
System.out.println(modRefId.getMenuid() + ") modRefId " + modRefId.getLink());
/*					long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 5, //5=recursos
						id, 0, Functions.toInt(info.get("u.id")+""), 2,	//2=Eliminar
						"", Functions.toInt(info.get("u.companyid")+""),
						jsonFiles, olddata.get(0).getRecurso(), oldjson, newdata);
					if(nsucc<0)
						System.err.println("Sin cambios o no se pudo almacenar la notificación del recurso "
							+ olddata.get(0).getRecurso());
*/
				}catch(Exception e){
		        	System.err.println("No se pudieron eliminar los archivos anexos: ");
		            e.printStackTrace();
		        }
			}
		}
	}

	@SuppressWarnings({"unchecked"})
	@RequestMapping(value = "/resourcedashboard")
	public @ResponseBody ModelAndView resourcedashboard(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if(sess!=null)if(sess.getAttribute("isLogin")!=null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
			res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP1.1.
			res.setHeader("Pragma", "no-cache");// HTTP1.0.
			res.setDateHeader("Expires", 0);
			res.setContentType("text/html; charset=utf-8");
			res.setCharacterEncoding("utf-8");
			Map<String, Object> forModel = new HashMap<String, Object>(), relDocs = null;
			int id = Functions.strToInt(req.getParameter("rid"));
			String movsIds = "", shTrials = "", whereClause = "idregister=0", origin = "";

			Map<?, ?> allInfo = (HashMap<?, ?>) commonsCtrll.checkAccessDoc(req,0,0,"re",id,"","related",
				"sd.userid,sd.emailexternaluser");
			Map<Integer, Object> records = (Map<Integer, Object>) allInfo.get(1);
			if(id==0 || id!=Functions.toInt(records.get("re.recursoid")+""))
				return new ModelAndView("resourcedashboard", forModel);
			List<Recursos> recursos = recursosService.getAll("FROM Recursos WHERE recursoid=" + id);
			forModel.put("rec", recursos);

			relDocs = (Map<String, Object>) allInfo.get(0);
			forModel.put("relDocs", relDocs.get("related")+"");

			// Obtiene los privilegios del módulo
			List<Privileges> lp = null;
			forModel.put("listp", lp);
			List<ECalendar> schedules = null;
			List<Movimientos> movList = movimientosService.getAll("FROM Movimientos WHERE recursoid="
				+ id + " ORDER BY LEAST (fechapresentacion,fechaacuerdo,fechanotificacion)");
			forModel.put("movList", movList);
			if(movList!=null){
				for(int i = 0; i < movList.size(); i++)
					movsIds += movList.get(i).getMovimientoid() + ",";
				movsIds = movsIds.replaceAll(".$", "");
				if(movsIds!="")
					schedules = eCalendarService.getAll("FROM ECalendar WHERE movimientoid IN(" + movsIds + ")");
			}
			forModel.put("schedules", schedules);

			HashMap<String, Object> parameters = new HashMap<>();
			parameters.put("catalogtype", 7);	//7=Movimientos
			if (!Functions.isEmpty(movsIds))
				whereClause = "idregister in(" + movsIds + ")";
			List<Uploadfiles> doctos = dao.sqlHQL("FROM Uploadfiles WHERE "
				+ whereClause + " AND path!='' AND catalogtype=:catalogtype", parameters);
			for(Uploadfiles doc : doctos){
				doc.setPath(doc.getPath().substring(doc.getPath().indexOf("doctos")));
				doc.setImg(Functions.findExtentionFile(doc.getFilename()));
				//System.out.println("::resourcedashboard:::" + doc.getPath());
			}
			forModel.put("doctos", doctos);

			// Permisos de movimientos caso de ser un juicio compartido
			Object firstRecord = allInfo.get(0);
			HashMap<String, String> shMovPriv = new HashMap<>();
			origin = records.get("origin")+"";
			if(firstRecord instanceof HashMap){
	        	relDocs= (HashMap<String, Object>) firstRecord;
	        	shTrials = Functions.toStr(relDocs.get("compartidos"));
	        	shMovPriv.put("origin", origin);
	        }
	        if(!Functions.isEmpty(shTrials) && origin.equals("asg")){
	        	String sd_uid = relDocs.get("sd.userid")+"",
	        		sd_uemail = relDocs.get("sd.emailexternaluser")+"";
				String query = "SELECT mp.menuid, mp.privilegesid "
					+ "FROM SharedDockets AS sh "
					+ "LEFT JOIN Menuprivileges AS mp ON mp.shareddocketid=sh.shareddocketid "
					+ "LEFT JOIN Menu AS m ON m.menuid=mp.menuid "
					+ "WHERE sh.juicioid IN(" + shTrials
					+ ")AND menu='Movimientos de juicios'"	// Privilegios de movimientos
					+ " AND (" + (Functions.isEmpty(sd_uid)?"":"sh.userid=" + relDocs.get("sd.userid"))
					+ (Functions.isEmpty(sd_uemail)?"":(Functions.isEmpty(sd_uid)?"":" OR "
					+ " sh.emailexternaluser='" + sd_uemail + "'"))
					+ ") ORDER BY mp.menuid ASC, mp.privilegesid ASC";
				List<?> shdata = dao.sqlHQL(query, null);
				Object[] tmp = shdata.toArray();
				if(shdata.size()>0){
					String module = "", privileges = "";
					for(Object cdata:tmp){
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

//FIXME: Lineas temporales para Movimientos
/*			shMovPriv.put("origin","own");
			shMovPriv.put("shpriv","1,2,3,4");
			forModel.put("shmovpriv", shMovPriv);
*/
			return new ModelAndView("resourcedashboard", forModel);
		}
		return new ModelAndView("login");
	}
}