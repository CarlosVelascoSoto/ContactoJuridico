package com.aj.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aj.model.Amparos;
import com.aj.model.Apelaciones;
import com.aj.model.Clients;
import com.aj.model.Companyclients;
import com.aj.model.ECalendar;
import com.aj.model.Juicios;
import com.aj.model.Juzgados;
import com.aj.model.Menu;
import com.aj.model.Movimientos;
import com.aj.model.Notifications;
import com.aj.model.Recursos;
import com.aj.model.SharedDockets;
import com.aj.model.TribunalColegiado;
import com.aj.model.TribunalUnitario;
import com.aj.model.Uploadfiles;
import com.aj.model.Users;
import com.aj.service.AccessDbJAService;
import com.aj.service.AmparosService;
import com.aj.service.ApelacionesService;
import com.aj.service.ClientsService;
import com.aj.service.CompanyclientsService;
import com.aj.service.JuiciosService;
import com.aj.service.JuzgadosService;
import com.aj.service.MovimientosService;
import com.aj.service.NotificationsService;
import com.aj.service.RecursosService;
import com.aj.service.SharedDocketsService;
import com.aj.service.TribunalColegiadoService;
import com.aj.service.TribunalUnitarioService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class NotificationsController {
	@Autowired
	private UserController userCtrll;

	@Autowired
	public ApelacionesService apelacionesService;
	
	@Autowired
	public AmparosService amparosService;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	public CompanyclientsService companyclientsService;
	@Autowired
	public TribunalColegiadoService colegiadoService;
	
	@Autowired
	public TribunalUnitarioService unitarioService;
	
	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public JuzgadosService juzgadosService;

	@Autowired
	public MovimientosService movimientosService;

	@Autowired
	public NotificationsService notificationsService;

	@Autowired
	public RecursosService recursosService;

	@Autowired
	public SharedDocketsService sharedDocketsService;

	@Autowired
	public ClientsController clientsCtrll;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	public AccessDbJAService dao;

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	CommonController commonsCtrll;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static final int ROLE_SYSADMIN= 1;
	public static final int ROLE_CJADMIN = 2;
	public static final int ROLE_FIRMADMIN=3;

	@RequestMapping(value = "/notifications")
	public @ResponseBody ModelAndView notifications(HttpServletRequest req, HttpServletResponse res) {
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
		return new ModelAndView("notifications");
	}

	/** Obtiene todos los usuarios autorizados para recibir sus notificaciones.
		@param req		- HttpServletRequest.
		@param req		- HttpServletResponse.
		@param userid	- Usuario quien captura el movimiento.
		@param firmid	- Id de la firma.
		@param lawyerassigned	- Id del abogado asignado (en caso de que sea asignado).
		@param catid	- Identificador de tabla donde se origina el cambio: 1=juicios; 2=apelaciones; 3=amparos directos; 4=amparos indirectos;
							5=recursos; 6=Clientes; 7=movimientos; 8=Logo Firma; 9=Clientes; 10=Usuarios; 11=Calendario;
		@param moduleid	- Id del módulo de acuerdo a la tabla "MENU" (para obtener el link).
		@param refid	- Id del registro modificado.
		@return	Formato JSON con el formato para notificar a los usuarios. 	*/
	@SuppressWarnings("unchecked")
	public @ResponseBody String notifUsersAdmins(HttpServletRequest req, HttpServletResponse res,
		int userid, int firmid, String lawyerassigned, int catid, int moduleid, int refid){
		Map<String, String> allUsers = new HashMap<String, String>();
		Integer hasTrial = 0, hasApl = 0, hasProt = 0, hasRsc = 0;
		String queryTmp = "", userids = "";
			//, tableName = "" , tableMId = "", tableUser = "", tableData = "";
		
	
		//Obtiene el origen del documento
	
	//FIXME - comparar "catid", crear variable/tabla para almacenar las referencias de los IDs en lugar de hacer comparativos con valores fijos*/
	
		if(catid==7){
			List<Movimientos> entityMov = movimientosService.getAll("FROM Movimientos WHERE movimientoid=" + refid);
			hasTrial= entityMov.get(0).getJuicioid()==null?0:entityMov.get(0).getJuicioid();
			hasApl=entityMov.get(0).getApelacionid()==null?0:entityMov.get(0).getApelacionid();
			hasProt = entityMov.get(0).getAmparoid()==null?0:entityMov.get(0).getAmparoid();
			hasRsc = entityMov.get(0).getRecursoid()==null?0:entityMov.get(0).getRecursoid();
		}
		if(catid==5 || hasRsc>0){				//Recursos
			queryTmp = " SELECT a.juicioid, a.apelacionid FROM Amparos AS a "
				+ "LEFT JOIN Recursos AS r ON r.tipoorigenid = a.amparoid "
				+ "WHERE tipoorigen IN (1,2) AND recursoid=" + (hasRsc==0?refid:hasRsc);
			List<Amparos> entityTmp = amparosService.getAll(queryTmp);
			if(entityTmp.size()>0){
				Object[] tmp = entityTmp.toArray();
				for (Object cdata:tmp){
					Object[] obj= (Object[]) cdata;
					if(!(obj[0]==null))
						hasTrial=(Integer)obj[0];
					if(!(obj[1]==null))
						hasApl= (Integer) obj[1];
				}
			}
		}
		
		if(catid==3 || catid==4 || hasProt>0){	//Amparos
			List<Amparos> entityTmp = amparosService
				.getAll("FROM Amparos WHERE amparoid=" +(hasProt==0?refid:hasProt));
			if(entityTmp.size()>0){
				Integer a_jid = entityTmp.get(0).getJuicioid();
				Integer a_amp = entityTmp.get(0).getApelacionid();
				if(a_jid!=null)
					hasTrial = a_jid;
				if(a_amp!=null)
					hasApl = a_amp;
			}
	//TODO Agregar companyid, userid
		}
		if(catid==2 || hasApl>0){				//Apelaciones
			queryTmp = "SELECT j.userid, j.abogadoasignado, j.juicioid FROM Juicios AS j "
				+ "LEFT JOIN Apelaciones AS a ON a.juicioid=j.juicioid WHERE a.apelacionid="
				+ (hasApl==0?refid:hasApl);
	//TODO Agregar companyid, userid
			List<Juicios> entityTmp = juiciosService.getAll(queryTmp);
			Object[] tmp = entityTmp.toArray();
			for (Object cdata:tmp){
				Object[] obj= (Object[]) cdata;
				if(!(obj[0]==null))
					allUsers.put(Functions.toStr(obj[0]),"x");
				if(!(obj[1]==null))
					allUsers.put(Functions.toStr(obj[1]),"");
				if(!(obj[2]==null)){
					hasTrial = (Integer) obj[2];
					hasProt = 0;
				}
			}
		}
		if(catid==1 || hasTrial>0){				//Juicios
			String extEmail = "";
			List<Juicios> entityTmp = juiciosService.getAll("FROM Juicios WHERE juicioid="
				+ (hasTrial==0?refid:hasTrial));
			if(entityTmp.size()>0){
				String j_aid = entityTmp.get(0).getAbogadoasignado();
				if(!Functions.isEmpty(j_aid))
					allUsers.put(j_aid,"");
				allUsers.put(entityTmp.get(0).getUserid()+"","x");
			}
	
			// Usuarios por id a quienes se les compartió el juicio.
			List<SharedDockets> localSD = sharedDocketsService
				.getAll("FROM SharedDockets WHERE juicioid=" + (hasTrial==0?refid:hasTrial));
			for(int l=0; l<localSD.size(); l++){
				allUsers.put(localSD.get(l).getUserid()+"","x");
				allUsers.put(localSD.get(l).getUsuarioidautoriza()+"","");
				if(!Functions.isEmpty(localSD.get(l).getEmailexternaluser()))
					extEmail+=localSD.get(l).getEmailexternaluser() + ",";
			}
	
			// Usuarios por email a quienes se les compartió el juicio.
			if(!Functions.isEmpty(extEmail)){
				List<Users> extByEmail = dao.sqlHQL("FROM Users WHERE email in('"
					+ extEmail.replaceAll(",","") + "') AND status=1", null);
				if(entityTmp.size()>0)
					allUsers.put(extByEmail.get(0).getId()+"","");
			}
		}
	
		// Incluye a los usuarios con rol de Administradores de firma
		List<Users> entityTmp = dao.sqlHQL("FROM Users WHERE role=" + ROLE_FIRMADMIN
			+ " AND companyid=" + firmid + " AND id!=" + userid, null);
		for(int f=0; f<entityTmp.size(); f++)
			allUsers.put(entityTmp.get(f).getId()+"","");
		allUsers.remove(userid+"");// Excluye al usuario quien realizó el cambio para evitar auto-enviarse notificaciones
		for (Object key : allUsers.keySet())
			userids+= "\"" + key + "\":{\"confirmed\":\"\",\"notified\":\"\"},";
		userids = "{" + userids.replaceAll(".$","") + "}";
		return userids;
	}
	
	
	/** Agrega una nueva notificación.
		@param req			- HttpServletRequest.
		@param res			- HttpServletResponse.
		@param moduleid		- (int) Id del módulo de acuerdo a la tabla "MENU" (para obtener el link).
		@param catid		- (int) Identificador de tabla donde se origina el cambio: 1=juicios; 2=apelaciones; 3=amparos directos; 4=amparos indirectos; 5=recursos; 6=Clientes; 7=movimientos; 8=Logo Firma; 9=Clientes; 10=Usuarios; 11=Calendario; 
		@param refid		- (int) Id del registro capturado/eliminado.
		@param ccid			- (Opcional) Número "companyclientid" acuerdo a la relación cliente-firma o cero (0) si no se requiere.
		@param userid		- (int) Id del usuario que capturó.
		@param actiontypeid	- (int) 0=Nuevo, 1=Editado, 2=Eliminado.
		@param lawyerassigned	- (Opcional-String) Texto con el Id de abogado asignado, de lo contrario indicar con cadena vacía: "".
		@param firmid		- (int) Firma del usuario quien registra.
		@param addedFiles	- (JsonObject) Archivos anexados y separados por pipe '|'.
		@param description	- (String) Descripción del registro, por ejemplo, número de folio del documento.
		@param oldJSON		- (String) Entidad del registro antes de guardar cambios.
		@param newJSON		- (String) Entidad del registro después de haber guardado cambios.
		@return Id del registro después de guardar datos, o cero ("0") para procesos de eliminar.	*/
	@SuppressWarnings("unchecked")
	public @ResponseBody long addNotificationDB(HttpServletRequest req, HttpServletResponse res, int moduleid,
		int catid, int refid, int ccid, int userid, int actiontypeid, String lawyerassigned, int firmid,
		JsonObject addedFiles, String description, String oldJSON, String newJSON){
		oldJSON = oldJSON.replaceAll("^\\[(.*)\\]$","$1");
		newJSON = newJSON.replaceAll("^\\[(.*)\\]$","$1");
		Map<String,Object> oldmap = null, newmap = null;
		try{
			newmap = new ObjectMapper().readValue(newJSON, HashMap.class);
			if(!Functions.isEmpty(oldJSON))
				oldmap = new ObjectMapper().readValue(oldJSON, HashMap.class);
		}catch(JsonParseException e){e.printStackTrace();
		}catch(JsonMappingException e){e.printStackTrace();
		}catch(IOException e){e.printStackTrace();}
		JsonObject main = new JsonObject();
		
		// Comparativo de datos con cambios (ini)
		int idx=0;
		if(Functions.isEmpty(oldJSON)){
	    	for(Entry<String, Object> entry : newmap.entrySet()){
				String key = entry.getKey();
				String newvalue = Functions.toStr(newmap.get(key));
				if(!Functions.isEmpty(newvalue)){
					JsonObject data = new JsonObject();
					data.addProperty("field", key);
					data.addProperty("newdata", newvalue);
					data.addProperty("olddata", "");
					main.add(idx+"", data);
					idx++;
				}
			}
    	}else{
    		for(Entry<String, Object> entry : oldmap.entrySet()){
    			String key=entry.getKey();
    			
/*				String oldvalue_original=(entry.getValue()).toString(),
					newvalue_original=(newmap.get(key)).toString();
				if(!oldvalue_original.equals(newvalue_original)){*/
				String oldvalue_original=entry.getValue() != null ? entry.getValue().toString() : null;
				String newvalue_original=newmap.get(key) != null ? newmap.get(key).toString() : null;
				if(!Objects.equals(oldvalue_original, newvalue_original)){

					JsonObject data = new JsonObject();
					if(!key.equals("customcolumns")){
						data.addProperty("field", key);
						data.addProperty("newdata", newvalue_original);
						data.addProperty("olddata", oldvalue_original);
						main.add(idx+"", data);
					}else{
						String oldvalues = oldvalue_original, newvalues = newvalue_original;
						if(!oldvalues.equals(newvalues)){		// "CustomColumns" Analiza y muestra diferencias
							String[] strTmp = oldvalues.split("~");
							List<String> ovAll = new ArrayList<>(Arrays.asList(strTmp));
							strTmp = newvalues.split("~");
							List<String> nvAll = new ArrayList<>(Arrays.asList(strTmp));
							int oSize=ovAll.size(),others=0;
							for(int o=0; o<oSize; o++){
								String oData = ovAll.get(o);
								int idxO = nvAll.indexOf(oData);
								boolean oDel = false;
								if(Functions.isEmpty(oData)){	// Remueve valores vacíos
									oDel=true;
								}else if(idxO>=0){				// Remueve valores duplicados (registros sin cambios)
									oDel=true;
									nvAll.remove(idxO);
								}else{							// Analiza y muestra valores con cambios
									String[] arrO = oData.split("\\|");
									String baseDatO = arrO[0]+"|"+arrO[1];
									for(String element : nvAll)
										if(element.contains(baseDatO)){
											String[] arrN = element.split("\\|");
											data = new JsonObject();
											data.addProperty("field", arrO[1]);
											data.addProperty("newdata",arrN[2]);
											data.addProperty("olddata",arrO[2]);
											main.add(idx+"", data);
											idx++;
											oDel=true;
											nvAll.remove(element);
											break;
										}
								}
								if(oDel){
									ovAll.remove(o);
									oSize--;
									o--;
								}
							}
							do{	// Muestra otros cambios
								List<String> xAll=others==1?nvAll:ovAll;
								for(int x=0; x<xAll.size(); x++){
									String xData = xAll.get(x);
									if(!Functions.isEmpty(xData)){
										String[] arrX = xData.split("\\|");
										data = new JsonObject();
										data.addProperty("field", arrX[1]);
										data.addProperty("newdata", others==1?arrX[2]:"");
										data.addProperty("olddata", others==1?"":arrX[2]);
										main.add(idx+"", data);
										idx++;
									}
								}
								others++;
							}while(others<2);
						}
					}
					idx++;
				}
    		}
    	}
// TODO: Las modificaciones de FIRM_USER que no se reflejen a sí mismo
//FIXME: Dar de alta el módulo de "relatedcolumns"
		if(addedFiles!=null)if(addedFiles.size()>0)
			main.add(idx+"", addedFiles);
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		String cdate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
			+ calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR)  + ":"
			+ calendar.get(Calendar.MINUTE);

		Notifications entity = new Notifications();
		entity.setActionsdetails(main+"");
		entity.setActiontypeid(actiontypeid);
		entity.setCapturedate(Functions.parseFecha(cdate, "yyyy-MM-dd HH:mm"));
		if(ccid>0)
			entity.setCompanyclientid(ccid);
		entity.setReferenceid(refid);
		entity.setModuleref(moduleid);
		entity.setReference(description);
		entity.setUserid((long) userid);
		String nu=notifUsersAdmins(req, res, userid, firmid, lawyerassigned, catid, moduleid, refid);
		entity.setConfirmations(nu);
		long succ = notificationsService.addNewNotification(entity);
		return succ;
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewNotification")
	public void saveNewNotification(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String circuitname = (req.getParameter("notification") == null) ? "" : req.getParameter("notification").trim();
				Long estado = (req.getParameter("estadoid") == null) ? 0 : Functions.toLong(req.getParameter("estadoid"));
				if (Functions.isEmpty(circuitname) || estado==0) {
					resp = "msg_empty_data";
				}else{
					List<Notifications> existsNotification = notificationsService.getAll("FROM Notifications WHERE UPPER(notification)='"+circuitname.toUpperCase()+"'");
					if(existsNotification.size()<1){
						Notifications circuit = new Notifications();
						long succ = notificationsService.addNewNotification(circuit);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewNotification(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updateNotification")
	public void updateNotification(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String circuit = (req.getParameter("notification") == null) ? "" : req.getParameter("notification").trim();
				Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id")),
					state = (req.getParameter("estadoid") == null) ? 0 : Functions.toLong(req.getParameter("estadoid"));
				if (Functions.isEmpty(circuit)) {
					resp="msg_empty_data";
				}else if(state == 0){
					resp="err_SELECT_state";
				}else{
					List<Notifications> existsNotification = notificationsService.getAll("FROM Notifications WHERE UPPER(notification)='"
						+ circuit.toUpperCase()+"' AND estadoid=" + state);
					if(existsNotification.size()<2){
						if(existsNotification.size()==1 && existsNotification.get(0).getNotificationid() != id){
							resp="err_duplicated_data";
						}else{
							Notifications entity= new Notifications();
/*							entity.setNotificationoid(id);
							entity.setNotificationo(circuit);
							entity.setEstadoid(state);
*/							notificationsService.updateNotification(entity);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateNotification(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteNotification")
	public void deleteNotification(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				notificationsService.deleteNotification(id);
			}
		}
	}

	/** Obtiene las notificaciones que no han sido mostradas al usuario.	*/
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/refreshNtfy")
	public @ResponseBody Object[] refreshNtfy(HttpServletRequest req, HttpServletResponse res){
	HttpSession sess = req.getSession(false);
	Map<Integer, Object> data = new HashMap<Integer, Object>();
	valideData:{
	if(sess != null){
		UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
		if(userDto==null)
			return new Object[] {data};
		long uid = userDto.getId();
	
		String msgNtfyId = req.getParameter("msgNtfyId") == null ? "" : req.getParameter("msgNtfyId").trim(),
			clientname = "", allids = "", tmpNtfy = "";
		if(Functions.isEmpty(msgNtfyId) || msgNtfyId.equals("undefined"))
			msgNtfyId="0";
		tmpNtfy = " OR (confirmations LIKE '%\"" + uid
			+ "\":{\"confirmed\":\"\",%' AND notificationid IN(" + msgNtfyId.replaceAll("undefined","0") + "))";
		List<Notifications> notifications = null;
		notifications = notificationsService.getAll("FROM Notifications WHERE (confirmations like'%\""
			+ uid + "\":{\"confirmed\":\"\",\"notified\":\"\"}%')" + tmpNtfy);
	
		for(int n=0;n<notifications.size();n++){
			Map<String,String> rec=new HashMap<String,String>();
			allids+=notifications.get(n).getNotificationid()+",";
			if(notifications.get(n).getCompanyclientid()!=null && notifications.get(n).getCompanyclientid()>0){
				List<Clients> clientdata = null;
				Integer ccid = notifications.get(n).getCompanyclientid();
				List<Companyclients> cc = companyclientsService
					.getAll("FROM Companyclients WHERE companyclientid=" + ccid );
				if(cc.size()>0)
					clientdata = clientsService
						.getAll("FROM Clients WHERE clientid=" + cc.get(0).getClientid());
				clientname=clientdata.get(0).getClient();
			}
			Integer modid = notifications.get(n).getModuleref();
			rec.put("notificationid",notifications.get(n).getNotificationid()+"");
			rec.put("actiontypeid",notifications.get(n).getActiontypeid()+"");
			rec.put("clientname",clientname);
			rec.put("document",notifications.get(n).getReference());
			
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
				menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE menuid=" + modid, null);
			else
				menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link LIKE '%" + target + "%'", null);
			moddata.addProperty("modulename",menutmp.getMenu());
			moddata.addProperty("link",menutmp.getLink());
			rec.put("area",moddata+"");
			rec.put("actionsdetails",notifications.get(n).getActionsdetails());
	
			Integer idref = notifications.get(n).getReferenceid(), juicioid = 0,
				cclientid = 0, apelacionid = 0, amparoid = 0, recursoid = 0;
			String link = menutmp.getLink(), proceedings ="", recNum = "";
	
			if(link.equals("trialsdashboard") || link.equals("appealsdashboard")
				|| link.equals("protectiondashboard") || link.equals("indprotectiondashboard")
				|| link.equals("resourcedashboard")){
				List<Movimientos> dataMov = movimientosService.getAll("FROM Movimientos WHERE movimientoid="+idref);
				if(dataMov.get(0).getJuicioid()!=null){
					juicioid = dataMov.get(0).getJuicioid();
					idref = juicioid;
				}
				if(dataMov.get(0).getApelacionid()!=null){
					apelacionid = dataMov.get(0).getApelacionid();
					idref = apelacionid;
				}
				if(dataMov.get(0).getAmparoid()!=null){
					amparoid = dataMov.get(0).getAmparoid();
					idref = amparoid;
				}
				if(dataMov.get(0).getRecursoid()!=null){
					recursoid = dataMov.get(0).getRecursoid();
					idref = recursoid;
				}
			}else if(link.equals("resources.jet") || link.equals("resources") || recursoid>0){
				List<Recursos> dataRsc = recursosService.getAll("FROM Recursos WHERE recursoid=" + idref);
				recursoid = dataRsc.get(0).getRecursoid();
				recNum = dataRsc.get(0).getRecurso();
				if(dataRsc.get(0).getTipoorigenid()>0){	//1=Amparo directo; 2=Amparo indirecto
					List<Amparos> ampTmp = amparosService
						.getAll("FROM Amparos WHERE amparoid=" + dataRsc.get(0).getTipoorigenid());
					if(!Functions.isEmpty(ampTmp.get(0).getCompanyclientid()))
						cclientid = ampTmp.get(0).getCompanyclientid();
				}
			}
			if(link.equals("protections") || link.equals("indprotections")
			|| link.equals("protections.jet") || link.equals("indprotections.jet")
			|| amparoid>0){
				List<Amparos> dataProt = amparosService.getAll("FROM Amparos WHERE amparoid=" + idref);
				if(dataProt.size()>0){
					amparoid = dataProt.get(0).getAmparoid()==null?0:dataProt.get(0).getAmparoid();
					apelacionid = dataProt.get(0).getApelacionid()==null?0:dataProt.get(0).getApelacionid();
					juicioid = dataProt.get(0).getJuicioid()==null?0:dataProt.get(0).getJuicioid();
					proceedings = dataProt.get(0).getAmparo()==null?"":dataProt.get(0).getAmparo();
				}
			}
			if(link.equals("apelaciones.jet") || link.equals("apelaciones") || apelacionid>0){
				List<Apelaciones> dataApl = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid="+idref);
				if(dataApl.size()>0){
					proceedings = dataApl.get(0).getToca()==null?"":dataApl.get(0).getToca();
					apelacionid = dataApl.get(0).getApelacionid()==null?0:dataApl.get(0).getApelacionid();
					juicioid = dataApl.get(0).getJuicioid()==null?0:dataApl.get(0).getJuicioid();
				}
			}
			if(link.equals("juicios.jet") || link.equals("juicios") || juicioid>0 || link.equals("Compartir juicio")){
				String proceedingsTmp = proceedings;
				if(juicioid>0)
					idref=juicioid;
				List<Juicios> dataTrial = juiciosService.getAll("FROM Juicios WHERE juicioid=" + idref);
				if(dataTrial.size()>0){
					proceedings = dataTrial.get(0).getJuicio();
					cclientid = dataTrial.get(0).getCompanyclientid();
				}
				if(apelacionid>0){
					idref=apelacionid;
					proceedings=proceedingsTmp;
				}
				if(amparoid>0 && recursoid==0){
					idref=amparoid;
					proceedings=proceedingsTmp;
				}
			}
			if(recursoid>0 && amparoid==0){
				idref=recursoid;
				proceedings=recNum;
			}
			
			rec.put("idref",Functions.toStr(idref));
			data.put(n,rec);
		}
		//Marca las notificaciones como "notificadas"
		String[] nids = allids.replaceAll(",$","").split(",");
		for(int a=0;a<nids.length;a++)
			if (!Functions.isEmpty(nids[a]))
				updateNotifyStatusDate2(Functions.toInt(nids[a]),"notified",req);
	}}
	return new Object[] { data };
	}
	
	/** Actualiza la fechas de notificaciones.
	@param nid			Notification ID.
	@param setStatus	Corresponde a la fecha a establecer:<ul>
						<li>"notified" (Usuario sólo abrió la notificación)</li>
						<li>"confirmed" (Notificación marcada como leída)</li></ul>
	@param req			HttpServletRequest
	@return 'False' sólo si la sesión expiró.	*/
	@RequestMapping(value = "/updateNotifyStatusDate2")
	public @ResponseBody boolean updateNotifyStatusDate2(int nid, String setStatus, HttpServletRequest req){
		HttpSession sess = req.getSession(false);
		if(sess == null)return false;
		UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
		List<Notifications> notify = notificationsService.getAll("FROM Notifications WHERE confirmations LIKE '%\""
				+ userDto.getId() + "\":{%' AND notificationid=" + nid);
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(notify.get(0).getConfirmations());
		JsonElement userStatus = json.getAsJsonObject(userDto.getId()+"").get(setStatus);
		String hasDate = "";
		if(userStatus!=null)
			hasDate = Functions.toStr(userStatus).replaceAll("\"","");
		if(Functions.isEmpty(hasDate)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			String month = "0" + (calendar.get(Calendar.MONTH) + 1),
				day = "0" + calendar.get(Calendar.DAY_OF_MONTH), hour = "0" + calendar.get(Calendar.HOUR),
				minutes = "0" + calendar.get(Calendar.MINUTE);
			String cdate = calendar.get(Calendar.YEAR) + "-" + month.substring(month.length() - 2)
				+ "-" + day.substring(day.length() - 2) + " " + hour.substring(hour.length() - 2)
				+ ":" + minutes.substring(minutes.length() - 2) + ":00";
			json.getAsJsonObject(userDto.getId()+"").addProperty(setStatus, cdate);

			Notifications ntfyTmp = new Notifications();
			ntfyTmp.setNotificationid(notify.get(0).getNotificationid());
			ntfyTmp.setUserid(notify.get(0).getUserid());
			ntfyTmp.setActionsdetails(notify.get(0).getActionsdetails());
			ntfyTmp.setCapturedate(notify.get(0).getCapturedate());
			ntfyTmp.setModuleref(notify.get(0).getModuleref());
			ntfyTmp.setReference(notify.get(0).getReference());
			ntfyTmp.setActionsdetails(notify.get(0).getActionsdetails());
			ntfyTmp.setConfirmations(Functions.toStr(json));
			ntfyTmp.setCompanyclientid(notify.get(0).getCompanyclientid());
			ntfyTmp.setReferenceid(notify.get(0).getReferenceid());
			notificationsService.updateNotification(ntfyTmp);
		}
		return true;
	}

	// ********** Notificaciones (ini) **********
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getHomeNotifications") // Notificaciones para dashboard-Home
	public @ResponseBody Map<Integer, Object> getHomeNotifications(HttpServletRequest req){
		HttpSession sess = req.getSession(false);
	    Map<Integer,Object> m1=new HashMap<Integer,Object>();
	    if(sess != null){
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			int trialmod= userCtrll.getMenuIdByName("juicios","juicios.jet","Compartir"),
				applmod = userCtrll.getMenuIdByName("apelaciones","apelaciones.jet",""),
				dprotmod= userCtrll.getMenuIdByName("protections","protections.jet",""),
				iprotmod= userCtrll.getMenuIdByName("indprotections","indprotections.jet",""),
				rscmod = userCtrll.getMenuIdByName("resources","resources.jet",""),
				moduleref=0;
			List<Notifications> notifications = notificationsService.
				getAll("FROM Notifications WHERE confirmations LIKE '%\"" + userDto.getId()
					+ "\":{\"confirmed\":\"\",%' ORDER BY notificationid DESC");
			for(int n=0;n<notifications.size(); n++){
				Integer idJuicio, idAmparo, idRecurso, idApelacion, modid=notifications.get(n).getModuleref(),
					idref = notifications.get(n).getReferenceid(), cclientid = 0, juzgadoid = 0, juicioid = 0,
					apelacionid = 0, amparoid = 0, recursoid = 0;
				Menu menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE menuid=" + modid, null);
				String modulename = "", tipoJuzg="", target = "", link = menutmp.getLink(), proceedings ="",
					descrMov = "", courtname = "", clientname = "- - - - -", recNum = "";
				moduleref=notifications.get(n).getModuleref();

				if(link.equals("ecalendar.jet")) {
					ECalendar agenda=(ECalendar) dao.sqlHQLEntity("FROM ECalendar WHERE calendarid="+idref, null);
					if(agenda==null)continue;
					Movimientos movement=(Movimientos) dao.sqlHQLEntity("FROM Movimientos WHERE movimientoid="+agenda.getMovimientoid(), null);
					idApelacion=movement.getApelacionid();
					idJuicio = movement.getJuicioid();
					idAmparo = movement.getAmparoid();
					idRecurso= movement.getRecursoid();
					if(idJuicio!=null){
						Juicios juicio=(Juicios)dao.sqlHQLEntity("From Juicios WHERE juicioid="+idJuicio, null);
						clientname=Functions.toStr(
							dao.sqlHQL("SELECT c.client FROM Clients AS c"
							+ " INNER JOIN Companyclients AS com ON c.clientid = com.clientid"
							+ " WHERE com.companyclientid=" + juicio.getCompanyclientid(), null));
						clientname = clientname.replaceAll("\\[|\\]|\\{|\\}", "");						
						courtname = Functions.toStr(dao.sqlHQL("SELECT juzgado FROM Juzgados WHERE juzgadoid="
							+ juicio.getJuzgadoid(), null));
						courtname = courtname.replaceAll("\\[|\\]|\\{|\\}", "");						
						descrMov="Creado asunto en agenda para el expediente "+juicio.getJuicio();
						proceedings="Modificacion en agenda para el juicio "+juicio.getJuicio();
					}
					if(idAmparo!=null){
						Amparos amparo=(Amparos)dao.sqlHQLEntity("FROM Amparos WHERE amparoid="+idAmparo, null);
						tipoJuzg=amparo.getTipodemandaturnadaa();
						int turnTo = Functions.toInt(amparo.getDemandaamparoturnadaa());
						if(turnTo>0){
							if(Functions.toInt(amparo.getJuicioid())>0){
								String juicio = Functions.toStr(dao.sqlHQLEntity("SELECT juzgadoid FROM Juicios"
									+ " WHERE juicioid="+ amparo.getJuicioid(), null));
								courtname = Functions.toStr(dao.sqlHQL("SELECT juzgado FROM Juzgados WHERE juzgadoid="+juicio,null));
								courtname = courtname.replaceAll("\\[|\\]|\\{|\\}", "");
							}
						}else{
							juzgadoid=turnTo;
							if(juzgadoid>0){
								tipoJuzg=amparo.getTipodemandaturnadaa();
								if (tipoJuzg.equals("colegiado")) {
								    List<TribunalColegiado> courtinfo = colegiadoService
							    		.getAll("FROM TribunalColegiado WHERE tribunalcolegiadoid=" + juzgadoid);
								    if (courtinfo != null)
								        if (courtinfo.size() > 0)
								            courtname = courtinfo.get(0).getTribunalcolegiado();
								}else if (tipoJuzg.equals("unitario")) {
								    List<TribunalUnitario> courtinfo = unitarioService
							    		.getAll("FROM TribunalUnitario WHERE tribunalUnitarioid=" + juzgadoid);
								    if (courtinfo != null)
								        if (courtinfo.size() > 0)
								            courtname = courtinfo.get(0).getTribunalUnitario();
								}else if (tipoJuzg.equals("federal")) {
								    List<Juzgados> courtinfo = juzgadosService.getAll("FROM Juzgados WHERE juzgadoid=" + juzgadoid);
								    if (courtinfo != null)
								        if (courtinfo.size() > 0)
								            courtname = courtinfo.get(0).getJuzgado();
								}
							}
						}
						clientname = Functions.toStr(dao.sqlHQL("SELECT c.client FROM Clients AS c"
							+ " INNER JOIN Companyclients AS com ON c.clientid = com.clientid"
							+ " WHERE com.companyclientid="+amparo.getCompanyclientid(), null));
						clientname = clientname.replaceAll("\\[|\\]|\\{|\\}", "");	
						descrMov="Creado asunto en agenda para el expediente "+amparo.getAmparo();
						proceedings="Modificacion en agenda para el juicio "+amparo.getAmparo();
					}
					if(idApelacion!=null){
						Apelaciones apelacion=(Apelaciones)dao.sqlHQLEntity("FROM Apelaciones WHERE apelacionid="
							+ idApelacion, null);
						String juzid = Functions.toStr(dao.sqlHQL("SELECT juzgadoid FROM Juicios WHERE juicioid="
							+ apelacion.getJuicioid(),null));
						juzid = juzid.replaceAll("\\[|\\]|\\{|\\}", "");	
						String clienteid = Functions.toStr(dao.sqlHQL("SELECT companyclientid FROM Juicios WHERE juicioid="
							+ apelacion.getJuicioid(),null));
						clienteid = clienteid.replaceAll("\\[|\\]|\\{|\\}", "");	
						courtname = Functions.toStr(dao.sqlHQL("SELECT juzgado FROM Juzgados WHERE juzgadoid="+juzid,null));
						courtname = courtname.replaceAll("\\[|\\]|\\{|\\}", "");	
						clientname= Functions.toStr(dao.sqlHQL("SELECT c.client FROM Clients AS c"
							+ " INNER JOIN Companyclients AS com ON c.clientid = com.clientid"
							+ " WHERE com.companyclientid="+clienteid, null));
						clientname = clientname.replaceAll("\\[|\\]|\\{|\\}", "");			
						descrMov="Creado asunto en agenda para el expediente "+apelacion.getApelacionadhesiva();
						proceedings="Modificacion en agenda para el juicio "+apelacion.getApelacionadhesiva();
					}
					if(idRecurso!=null){
						Recursos recurso=(Recursos)dao.sqlHQLEntity("FROM Recursos WHERE recursoid="+idRecurso, null);
					}
				}
				if(link.equals("trialsdashboard") || link.equals("appealsdashboard") || link.equals("protectiondashboard")
					|| link.equals("indprotectiondashboard") || link.equals("resourcedashboard")){
					List<Movimientos> dataMov = movimientosService.getAll("FROM Movimientos WHERE movimientoid=" + idref);
					if(dataMov.size()<1)
						continue;
					if(dataMov.get(0).getJuicioid()!=null){
						juicioid = dataMov.get(0).getJuicioid();
						idref = juicioid;
					}
					if(dataMov.get(0).getApelacionid()!=null){
						apelacionid = dataMov.get(0).getApelacionid();
						idref = apelacionid;
					}
					if(dataMov.get(0).getAmparoid()!=null){
						amparoid = dataMov.get(0).getAmparoid();
						idref = amparoid;
					}
					if(dataMov.get(0).getRecursoid()!=null){
						recursoid = dataMov.get(0).getRecursoid();
						idref = recursoid;
						link = "resources";
					}
					descrMov = dataMov.get(0).getMovimiento();
				}
				if(link.equals("resources.jet") || link.equals("resources") || recursoid>0){
					List<Recursos> dataRsc = recursosService.getAll("FROM Recursos WHERE recursoid=" + idref);
					if(dataRsc.size()<1)
						continue;
					recursoid = dataRsc.get(0).getRecursoid();
					recNum = dataRsc.get(0).getRecurso();
					juzgadoid=dataRsc.get(0).getRecursoturnadoa()!=""?0:Functions.toInt(dataRsc.get(0).getRecursoturnadoa());
					if(dataRsc.get(0).getTipoorigenid()>0){	//1=Amparo directo; 2=Amparo indirecto
						List<Amparos> ampTmp = amparosService
							.getAll("FROM Amparos WHERE amparoid=" + dataRsc.get(0).getTipoorigenid());
						if(!Functions.isEmpty(ampTmp.get(0).getCompanyclientid()))
							cclientid = ampTmp.get(0).getCompanyclientid();
					}
					if(Functions.isEmpty(descrMov))
						descrMov = "Recurso registrado";
					if(!Functions.isEmpty(dataRsc.get(0).getRecursoturnadoa()))
						courtname = "Demanda turnada a:\n" + dataRsc.get(0).getRecursoturnadoa();
					List<Juzgados> courtinfo = juzgadosService.getAll("FROM Juzgados WHERE juzgadoid=" + juzgadoid);
					if(courtinfo!=null)
						if(courtinfo.size()>0)
							courtname = courtinfo.get(0).getJuzgado();
				}
				if(link.equals("protections") || link.equals("indprotections")
				|| link.equals("protections.jet") || link.equals("indprotections.jet")
				|| amparoid>0){
					List<Amparos> dataProt = amparosService.getAll("FROM Amparos WHERE amparoid=" + idref);
					if(dataProt.size()<1)
						continue;
					tipoJuzg=dataProt.get(0).getAmparoid()==null?"":dataProt.get(0).getTipodemandaturnadaa();
					juzgadoid=dataProt.get(0).getAmparo()==null?0
						:Functions.toInt(dataProt.get(0).getDemandaamparoturnadaa()); 
					amparoid = dataProt.get(0).getAmparoid()==null?0:dataProt.get(0).getAmparoid();
					apelacionid = dataProt.get(0).getApelacionid()==null?0:dataProt.get(0).getApelacionid();
					juicioid = dataProt.get(0).getJuicioid()==null?0:dataProt.get(0).getJuicioid();
					proceedings = dataProt.get(0).getAmparo()==null?"":dataProt.get(0).getAmparo();
					if(Functions.isEmpty(dataProt.get(0).getQuejoso()))
						clientname = "Tercero interesado: " + dataProt.get(0).getTercero();
					else
						clientname = "Quejoso: " + dataProt.get(0).getQuejoso();

					if(Functions.isEmpty(descrMov)) 
						descrMov = "Amparo registrado";
					if(tipoJuzg.equals("colegiado")) {
					    List<TribunalColegiado> courtinfo = colegiadoService.getAll("FROM TribunalColegiado WHERE tribunalcolegiadoid=" + juzgadoid);
					    if(courtinfo != null)
					        if(courtinfo.size() > 0)
					            courtname = courtinfo.get(0).getTribunalcolegiado();
					}else if(tipoJuzg.equals("unitario")) {
					    List<TribunalUnitario> courtinfo = unitarioService.getAll("FROM TribunalUnitario WHERE tribunalUnitarioid=" + juzgadoid);
					    if(courtinfo != null)
					        if(courtinfo.size() > 0)
					            courtname = courtinfo.get(0).getTribunalUnitario();
					}else if(tipoJuzg.equals("federal")) {
					    List<Juzgados> courtinfo = juzgadosService.getAll("FROM Juzgados WHERE juzgadoid=" + juzgadoid);
					    if(courtinfo != null)
					        if(courtinfo.size() > 0)
					            courtname = courtinfo.get(0).getJuzgado();
					}
				}
				if(link.equals("apelaciones.jet") || link.equals("apelaciones") || apelacionid>0){
					List<Apelaciones> dataApl = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid=" + idref);
					if(dataApl.size()>0){
						proceedings = dataApl.get(0).getToca()==null?"":dataApl.get(0).getToca();
						apelacionid = dataApl.get(0).getApelacionid()==null?0:dataApl.get(0).getApelacionid();
						juicioid = dataApl.get(0).getJuicioid()==null?0:dataApl.get(0).getJuicioid();
						List<Juzgados> courtinfo = juzgadosService.getAll("FROM Juzgados WHERE juzgadoid=" + juzgadoid);
						if(courtinfo!=null)
							if(courtinfo.size()>0)
								courtname = courtinfo.get(0).getJuzgado();
					}
					if(Functions.isEmpty(descrMov))
						descrMov = "Apelaci\u00f3n registrada";
				}
				if(link.equals("juicios.jet") || link.equals("juicios") || juicioid>0 || link.equals("Compartir juicio")){
					String proceedingsTmp = proceedings;
					if(juicioid>0)
						idref=juicioid;
					List<Juicios> dataTrial = juiciosService.getAll("FROM Juicios WHERE juicioid=" + idref);
					if(dataTrial.size()>0){
						proceedings = dataTrial.get(0).getJuicio();
						cclientid = dataTrial.get(0).getCompanyclientid();
						if(juzgadoid==0)
							juzgadoid = dataTrial.get(0).getJuzgadoid();
						if(Functions.isEmpty(descrMov))
							descrMov = "Nuevo juicio registrado";
					}
					if(apelacionid>0){
						idref=apelacionid;
						proceedings=proceedingsTmp;
					}
					if(amparoid>0 && recursoid==0){
						idref=amparoid;
						proceedings=proceedingsTmp;
					}
					if(Functions.isEmpty(descrMov))
						descrMov = "Nuevo registro";
					List<Juzgados> courtinfo = juzgadosService.getAll("FROM Juzgados WHERE juzgadoid=" + juzgadoid);
					if(courtinfo!=null) {
					if(courtinfo.size()>0){
						courtname = courtinfo.get(0).getJuzgado();};};
				}
				if(recursoid>0 && amparoid==0){
					idref=recursoid;
					proceedings=recNum;
				}
				List<Clients> cliendata = clientsCtrll.getClientByCCId(cclientid);
				if(cliendata!=null)
					if(cliendata.size()>0)
						clientname = cliendata.get(0).getClient();
				
//TODO System.out.println("idref ("+idref+"): " + modulename);
				menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE menuid=" + modid, null);
				modulename=menutmp.getMenu();
				target=modid==trialmod?"trialsdashboard":modid==applmod?"appealsdashboard"
					:modid==dprotmod?"protectiondashboard":modid==iprotmod?"indprotectiondashboard"
					:modid==rscmod?"resourcedashboard":target;
				if(Functions.isEmpty(target))
					menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE menuid=" + modid, null);
				else
					menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link LIKE '" + target
						+ "%' OR link LIKE '" + target + ".jet'", null);
				if(Functions.isEmpty(modulename))
					modulename = menutmp.getMenu();
				if(Functions.isEmpty(descrMov))
					descrMov = "Dato registrado";
			    Map<String,String> m2=new HashMap<String,String>();
			    m2.put("modulename",modulename);
			    m2.put("notificationid",notifications.get(n).getNotificationid()+"");
				m2.put("date",notifications.get(n).getCapturedate()+"");
				m2.put("proceedings",proceedings);
				m2.put("link",menutmp.getLink());
				m2.put("courtname",courtname);
				m2.put("clientname",clientname);
				m2.put("description",descrMov);
				m2.put("idref",idref+"");
				m2.put("source",moduleref+"");
			    m1.put(n,m2);
			}
		}
	    return m1;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getNotifyShort")
	public @ResponseBody void getNotifyShort(HttpServletRequest req, HttpServletResponse res){
		HttpSession sess = req.getSession(false);
	    JsonObject json = new JsonObject();
		if(sess != null){
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			int trialmod= userCtrll.getMenuIdByName("juicios","juicios.jet","Compartir"),
				applmod = userCtrll.getMenuIdByName("apelaciones","apelaciones.jet",""),
				dprotmod= userCtrll.getMenuIdByName("protections","protections.jet",""),
				iprotmod= userCtrll.getMenuIdByName("indprotections","indprotections.jet",""),
				rscmod = userCtrll.getMenuIdByName("resources","resources.jet","");
			List<Notifications> notifications = notificationsService.
				getAll("FROM Notifications WHERE confirmations LIKE '%\"" + userDto.getId()
					+ "\":{\"confirmed\":\"\",%' ORDER BY notificationid DESC");
			for(int n=0;n<notifications.size(); n++){
//TODO RelatedColumns

				JsonObject data = new JsonObject();
				Integer modid = notifications.get(n).getModuleref(), idref = notifications.get(n).getReferenceid(),
					cclientid = 0, juzgadoid = 0, juicioid = 0, apelacionid = 0, amparoid = 0, recursoid = 0;
				Menu menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE menuid=" + modid, null);
				String target = "", link = menutmp.getLink(), proceedings ="", descrMov = "", courtname = "",
					clientname = "- - - - -", recNum = "";

				if(link.equals("trialsdashboard") || link.equals("appealsdashboard") || link.equals("protectiondashboard")
					|| link.equals("indprotectiondashboard") || link.equals("resourcedashboard")){
					List<Movimientos> dataMov = movimientosService.getAll("FROM Movimientos WHERE movimientoid=" + idref);
					if(dataMov.get(0).getJuicioid()!=null){
						juicioid = dataMov.get(0).getJuicioid();
						idref = juicioid;
					}
					if(dataMov.get(0).getApelacionid()!=null){
						apelacionid = dataMov.get(0).getApelacionid();
						idref = apelacionid;
					}
					if(dataMov.get(0).getAmparoid()!=null){
						amparoid = dataMov.get(0).getAmparoid();
						idref = amparoid;
					}
					if(dataMov.get(0).getRecursoid()!=null){
						recursoid = dataMov.get(0).getRecursoid();
						idref = recursoid;
					}
					descrMov = dataMov.get(0).getMovimiento();
				}else if(link.equals("resources.jet") || link.equals("resources") || recursoid>0){
					List<Recursos> dataRsc = recursosService.getAll("FROM Recursos WHERE recursoid=" + idref);
					recursoid = dataRsc.get(0).getRecursoid();
					recNum = dataRsc.get(0).getRecurso();
					if(dataRsc.get(0).getTipoorigenid()>0){	//1=Amparo directo; 2=Amparo indirecto
						List<Amparos> ampTmp = amparosService
							.getAll("FROM Amparos WHERE amparoid=" + dataRsc.get(0).getTipoorigenid());
						if(!Functions.isEmpty(ampTmp.get(0).getCompanyclientid()))
							cclientid = ampTmp.get(0).getCompanyclientid();
					}
				}
				if(link.equals("protections") || link.equals("indprotections")
				|| link.equals("protections.jet") || link.equals("indprotections.jet")
				|| amparoid>0){
					List<Amparos> dataProt = amparosService.getAll("FROM Amparos WHERE amparoid=" + idref);
					if(dataProt.size()>0){
						amparoid = dataProt.get(0).getAmparoid()==null?0:dataProt.get(0).getAmparoid();
						apelacionid = dataProt.get(0).getApelacionid()==null?0:dataProt.get(0).getApelacionid();
						juicioid = dataProt.get(0).getJuicioid()==null?0:dataProt.get(0).getJuicioid();
						proceedings = dataProt.get(0).getAmparo()==null?"":dataProt.get(0).getAmparo();
					}
				}
				if(link.equals("apelaciones.jet") || link.equals("apelaciones") || apelacionid>0){
					List<Apelaciones> dataApl = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid=" + idref);
					if(dataApl.size()>0){
						proceedings = dataApl.get(0).getToca()==null?"":dataApl.get(0).getToca();
						apelacionid = dataApl.get(0).getApelacionid()==null?0:dataApl.get(0).getApelacionid();
						juicioid = dataApl.get(0).getJuicioid()==null?0:dataApl.get(0).getJuicioid();
					}
				}
				if(link.equals("juicios.jet") || link.equals("juicios") || juicioid>0 || link.equals("Compartir juicio")){
					String proceedingsTmp = proceedings;
					if(juicioid>0)
						idref=juicioid;
					List<Juicios> dataTrial = juiciosService.getAll("FROM Juicios WHERE juicioid=" + idref);
					if(dataTrial.size()>0){
						proceedings = dataTrial.get(0).getJuicio();
						cclientid = dataTrial.get(0).getCompanyclientid();
						juzgadoid = dataTrial.get(0).getJuzgadoid();
						if(Functions.isEmpty(descrMov))
							descrMov = proceedings;
					}
					if(apelacionid>0){
						idref=apelacionid;
						proceedings=proceedingsTmp;
					}
					if(amparoid>0 && recursoid==0){
						idref=amparoid;
						proceedings=proceedingsTmp;
					}
				}
				if(recursoid>0 && amparoid==0){
					idref=recursoid;
					proceedings=recNum;
				}
							
				List<Juzgados> courtinfo = juzgadosService.getAll("FROM Juzgados WHERE juzgadoid=" + juzgadoid);
				if(courtinfo!=null) if(courtinfo.size()>0)
					courtname = courtinfo.get(0).getJuzgado();
				List<Clients> cliendata = clientsCtrll.getClientByCCId(cclientid);
				if(cliendata!=null) if(cliendata.size()>0)
					clientname = cliendata.get(0).getClient();

				target=modid==trialmod?"trialsdashboard":modid==applmod?"appealsdashboard"
					:modid==dprotmod?"protectiondashboard":modid==iprotmod?"indprotectiondashboard"
					:modid==rscmod?"resourcedashboard":target;
				menutmp = null;
				if(Functions.isEmpty(target))
					menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE menuid=" + modid, null);
				else
					menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link LIKE '" + target
						+ "%' OR link LIKE '" + target + ".jet'", null);
				data.addProperty("modulename",menutmp.getMenu());
				data.addProperty("notificationid",notifications.get(n).getNotificationid());
				data.addProperty("date",notifications.get(n).getCapturedate()+"");
				data.addProperty("proceedings",proceedings);
				data.addProperty("link",menutmp.getLink());
				data.addProperty("courtname",courtname);
				data.addProperty("clientname",clientname);
				data.addProperty("description",descrMov);
				data.addProperty("idref",idref);
				json.add(n+"",data);
			}
		}
		try{
			PrintWriter out = res.getWriter();
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			out.print(json);
			out.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getNotifyDetail")
	public @ResponseBody void getNotifyDetail(HttpServletRequest req, HttpServletResponse res){
		HttpSession sess = req.getSession(false);
	    JsonObject rec = new JsonObject();
	    valideData:{
		if(sess != null){
			Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id").trim());
			if(id==0)
				break valideData;
			String clientname = "";
			List<Notifications> notifications = null;
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			notifications = notificationsService.getAll("FROM Notifications WHERE confirmations LIKE '%\""
				+ userDto.getId() + "\":{\"confirmed\":\"\",%' AND notificationid=" + id);

			if(notifications.get(0).getCompanyclientid()!=null && notifications.get(0).getCompanyclientid()>0){
				List<Clients> clientdata = clientsCtrll.getClientByCCId(notifications.get(0).getCompanyclientid());
				clientname=clientdata.get(0).getClient();
			}
			@SuppressWarnings("unchecked")
			List<Users> tmpUsr = dao.sqlHQL("FROM Users WHERE id=" + notifications.get(0).getUserid(), null);
			Integer modid = notifications.get(0).getModuleref();
			rec.addProperty("notificationid",notifications.get(0).getNotificationid());
			rec.addProperty("actiontypeid",notifications.get(0).getActiontypeid());
			rec.addProperty("clientname",clientname);
			rec.addProperty("document",notifications.get(0).getReference());
			rec.addProperty("date",notifications.get(0).getCapturedate()+"");
			rec.addProperty("username",tmpUsr.get(0).getFirst_name() + " " + tmpUsr.get(0).getLast_name());
			rec.addProperty("idref",notifications.get(0).getReferenceid());
			rec.addProperty("area",userCtrll.getModuleDataById(modid)+"");
			rec.addProperty("actionsdetails",notifications.get(0).getActionsdetails());
/*Sigue:
1-Texto "Realizado por" debe funcionar
2-Analizar el por que se muestran los ids 1215 y/o 1216, 1217
3-Eliminar un registro general/
			Menu menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE menuid=" + modid, null);
			Integer idref = notifications.get(0).getReferenceid(), cclientid,
				juicioid = 0, apelacionid = 0, amparoid = 0, recursoid = 0;
			String link = menutmp.getLink(), proceedings ="", recNum = "";
			if(link.equals("trialsdashboard") || link.equals("appealsdashboard") || link.equals("protectiondashboard")
				|| link.equals("indprotectiondashboard") || link.equals("resourcedashboard")){
				List<Movimientos> dataMov = movimientosService.getAll("FROM Movimientos WHERE movimientoid=" + idref);
				if(dataMov.get(0).getJuicioid()!=null){
					juicioid = dataMov.get(0).getJuicioid();
					idref = juicioid;
				}
				if(dataMov.get(0).getApelacionid()!=null){
					apelacionid = dataMov.get(0).getApelacionid();
					idref = apelacionid;
				}
				if(dataMov.get(0).getAmparoid()!=null){
					amparoid = dataMov.get(0).getAmparoid();
					idref = amparoid;
				}
				if(dataMov.get(0).getRecursoid()!=null){
					recursoid = dataMov.get(0).getRecursoid();
					idref = recursoid;
				}
			}else if(link.equals("resources.jet") || link.equals("resources") || recursoid>0){
				List<Recursos> dataRsc = recursosService.getAll("FROM Recursos WHERE recursoid=" + idref);
				recursoid = dataRsc.get(0).getRecursoid();
				recNum = dataRsc.get(0).getRecurso();
				if(dataRsc.get(0).getTipoorigenid()>0){	//1=Amparo directo; 2=Amparo indirecto
					List<Amparos> ampTmp = amparosService
						.getAll("FROM Amparos WHERE amparoid=" + dataRsc.get(0).getTipoorigenid());
					if(!Functions.isEmpty(ampTmp.get(0).getCompanyclientid()))
						cclientid = ampTmp.get(0).getCompanyclientid();
				}
			}
			if(link.equals("protections") || link.equals("indprotections")
			|| link.equals("protections.jet") || link.equals("indprotections.jet")
			|| amparoid>0){
				List<Amparos> dataProt = amparosService.getAll("FROM Amparos WHERE amparoid=" + idref);
				if(dataProt.size()>0){
					amparoid = dataProt.get(0).getAmparoid()==null?0:dataProt.get(0).getAmparoid();
					apelacionid = dataProt.get(0).getApelacionid()==null?0:dataProt.get(0).getApelacionid();
					juicioid = dataProt.get(0).getJuicioid()==null?0:dataProt.get(0).getJuicioid();
					proceedings = dataProt.get(0).getAmparo()==null?"":dataProt.get(0).getAmparo();
				}
			}
			if(link.equals("apelaciones.jet") || link.equals("apelaciones") || apelacionid>0){
				List<Apelaciones> dataApl = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid=" + idref);
				if(dataApl.size()>0){
					proceedings = dataApl.get(0).getToca()==null?"":dataApl.get(0).getToca();
					apelacionid = dataApl.get(0).getApelacionid()==null?0:dataApl.get(0).getApelacionid();
					juicioid = dataApl.get(0).getJuicioid()==null?0:dataApl.get(0).getJuicioid();
				}
			}
			if(link.equals("juicios.jet") || link.equals("juicios") || juicioid>0 || link.equals("Compartir juicio")){
				String proceedingsTmp = proceedings;
				if(juicioid>0)
					idref=juicioid;
				List<Juicios> dataTrial = juiciosService.getAll("FROM Juicios WHERE juicioid=" + idref);
				if(dataTrial.size()>0){
					proceedings = dataTrial.get(0).getJuicio();
					cclientid = dataTrial.get(0).getCompanyclientid();
				}
				if(apelacionid>0){
					idref=apelacionid;
					proceedings=proceedingsTmp;
				}
				if(amparoid>0 && recursoid==0){
					idref=amparoid;
					proceedings=proceedingsTmp;
				}
			}
			if(recursoid>0 && amparoid==0){
				idref=recursoid;
				proceedings=recNum;
			}
			
			
			//if((menutmp.getLink()).indexOf("dashboard")>1){
				rec.addProperty("idref",idref);
				//rec.addProperty("area",getModuleDataById(notifications.get(0).getModuleref())+"");
			//}

//TODO Aplicar a Archivos
			/*			json.add(n+"",rec);
			for(int n=0;n<notifications.size(); n++){
				//...
			}*/
			boolean r=updateNotifyStatusDate(id, "notified", req);
			if (!r)System.err.println("Error al actualizar la fecha de notificación o la sesión expiró");
		}}
		try{
			PrintWriter out = res.getWriter();
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			out.print(rec);
			out.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/** Actualiza la fechas de notificaciones.
	@param nid			Notification ID.
	@param setStatus	Corresponde a la fecha a establecer el texto:<ul>
						<li>"notified" (Usuario sólo abrió la notificación)</li>
						<li>"confirmed" (Notificación marcada como leída)</li></ul>
	@param req			HttpServletRequest
	@return 'False' sólo si la sesión expiró.	*/
	@RequestMapping(value = "/updateNotifyStatusDate")
	public @ResponseBody boolean updateNotifyStatusDate(Long nid, String setStatus, HttpServletRequest req){
		HttpSession sess = req.getSession(false);
		if(sess == null)return false;
		UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
		List<Notifications> notify = notificationsService.getAll("FROM Notifications WHERE confirmations LIKE '%\""
			+ userDto.getId() + "\":{%' AND notificationid=" + nid);
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(notify.get(0).getConfirmations());
		JsonElement userStatus = json.getAsJsonObject(userDto.getId()+"").get(setStatus);
		String hasDate = Functions.toStr(userStatus).replaceAll("\"","");
		if(Functions.isEmpty(hasDate)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			String month = "0" + (calendar.get(Calendar.MONTH) + 1),
				day = "0" + calendar.get(Calendar.DAY_OF_MONTH), hour = "0" + calendar.get(Calendar.HOUR),
				minutes = "0" + calendar.get(Calendar.MINUTE);
			String cdate = calendar.get(Calendar.YEAR) + "-" + month.substring(month.length() - 2)
				+ "-" + day.substring(day.length() - 2) + " " + hour.substring(hour.length() - 2)
				+ ":" + minutes.substring(minutes.length() - 2) + ":00";
			json.getAsJsonObject(userDto.getId()+"").addProperty(setStatus, cdate);

			Notifications ntfyTmp = new Notifications();
			ntfyTmp.setNotificationid(notify.get(0).getNotificationid());
			ntfyTmp.setUserid(notify.get(0).getUserid());
			ntfyTmp.setActionsdetails(notify.get(0).getActionsdetails());
			ntfyTmp.setCapturedate(notify.get(0).getCapturedate());
			ntfyTmp.setModuleref(notify.get(0).getModuleref());
			ntfyTmp.setReferenceid(notify.get(0).getReferenceid());
			ntfyTmp.setReference(notify.get(0).getReference());
			ntfyTmp.setActionsdetails(notify.get(0).getActionsdetails());
			ntfyTmp.setConfirmations(Functions.toStr(json));
			ntfyTmp.setCompanyclientid(notify.get(0).getCompanyclientid());
			notificationsService.updateNotification(ntfyTmp);
		}
		return true;
	}

	@RequestMapping(value = "/notifyAsRead")
	public @ResponseBody boolean notifyAsRead(HttpServletRequest req, HttpServletResponse res){
		HttpSession sess = req.getSession(false);
		if(sess != null){
			Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id").trim());
			int idref = (req.getParameter("idref") == null) ? 0:Functions.toInt(req.getParameter("idref").trim()),
				modref = (req.getParameter("link")== null) ? 0:Functions.toInt(req.getParameter("link").trim());
			if(idref>0 && modref>0){
				// De una agrupación de notificaciones, marca cada una de las sub-líneas como leidas
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				List<Notifications> notify = notificationsService
					.getAll("SELECT notificationid FROM Notifications WHERE confirmations LIKE '%\""+userDto.getId()
					+"\":{%' AND moduleref="+modref + " AND referenceid="+idref + " ORDER BY notificationid ASC");
				for(int n=0;n<notify.size();n++){
//System.out.println("NID="+notify.get(n));
					updateNotifyStatusDate(Functions.toLong(notify.get(n)+""), "confirmed", req);
				}
				return true;
			}else if(id>0){
				return updateNotifyStatusDate(id, "confirmed", req);
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getNotificationById")
	public @ResponseBody Object[] getNotificationById(HttpServletRequest req, HttpServletResponse res){
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
	    valideData:{
		if(sess != null){
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			if(userDto.getRole()!=ROLE_SYSADMIN)
				break valideData;
			int id = (req.getParameter("id") == null) ? 0:Functions.toInt(req.getParameter("id").trim());
			String query = "";
			List<Notifications> ntfy = dao.sqlHQL("FROM Notifications WHERE notificationid=" + id, null);
			List<Menu> mod = dao.sqlHQL("SELECT menuid, link, menu FROM Menu WHERE link!='' AND link"
				+ " NOT IN('privileges', 'addnewuser', 'roles', '%emailsettings%') ORDER BY link ASC", null);
			data.put("ntfy", ntfy);
			data.put("mod", mod);

			if(Functions.isNumeric(ntfy.get(0).getUserid()+""))
				query = "WHERE u.id=" + ntfy.get(0).getUserid();
			List<?> clients = companyclientsService.getAll("SELECT DISTINCT(cc.companyclientid), c.client, co.company "
				+ "FROM Clients AS c LEFT JOIN Companyclients AS cc ON cc.clientid=c.clientid "
				+ "LEFT JOIN Users AS u ON u.companyid=cc.companyid LEFT JOIN Companies AS co ON co.companyid=cc.companyid "
				+ query + " ORDER BY c.client ASC, co.company ASC");
			data.put("clients", clients);
		}}
	    return new Object[] { data };
	}

	@ResponseBody
	@RequestMapping(value = "/updateNtfyDirect")
	public void updateNtfyDirect(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		validateData:{
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO user = (UserDTO) sess.getAttribute("UserDTO");
				if(user.getRole()!=ROLE_SYSADMIN)
					break validateData;
				int nid = (req.getParameter("nid") == null) ? 0
						: Functions.toInt(req.getParameter("nid").trim()),
					actiontypeid = (req.getParameter("actiontypeid") == null) ? 0
						: Functions.toInt(req.getParameter("actiontypeid").trim()),
					moduleref = (req.getParameter("moduleref") == "") ? 0
						: Functions.toInt(req.getParameter("moduleref").trim()),
					referenceid = (req.getParameter("referenceid") == "") ? 0
						: Functions.toInt(req.getParameter("referenceid").trim()),
					ccid = (req.getParameter("ccid") == "") ? 0
						: Functions.toInt(req.getParameter("ccid").trim());
				String reference = (req.getParameter("reference") == null) ? ""
						: req.getParameter("reference").trim(),
					actionsdetails = (req.getParameter("actionsdetails") == null) ? ""
						: req.getParameter("actionsdetails").trim(),
					confirmations = (req.getParameter("confirmations") == null) ? ""
						: req.getParameter("confirmations").trim(),
					capturedate = (req.getParameter("capturedate") == null) ? ""
						: req.getParameter("capturedate").trim();
				if(actiontypeid==0){
					resp="err_SELECT_accion";
					break validateData;
				}else if(Functions.isEmpty(moduleref)){
					resp="msg_document_number";
					break validateData;
				}else if(Functions.isEmpty(reference) || referenceid==0 ){
					resp="msg_reference";
					break validateData;
				}else if(Functions.isEmpty(actionsdetails)){
					resp="msg_accion";
					break validateData;
				}else if(Functions.isEmpty(confirmations)){
					resp="msg_empty_data";
					break validateData;
				}else if(Functions.isEmpty(capturedate)){
					resp="msg_not_confirmed";
					break validateData;
				}
				List<Notifications> oldNtfy = notificationsService.getAll("FROM Notifications "
					+ "WHERE notificationid=" + nid);
				Notifications newNtfy = new Notifications();
				newNtfy.setNotificationid((long) nid);
				newNtfy.setUserid(oldNtfy.get(0).getUserid());
				newNtfy.setActiontypeid(actiontypeid);
				newNtfy.setCapturedate(Functions.parseFecha(capturedate, "yyyy-MM-dd"));
				newNtfy.setModuleref(moduleref);
				newNtfy.setActionsdetails(actionsdetails);
				newNtfy.setConfirmations(confirmations);
				newNtfy.setReference(reference);
				newNtfy.setCompanyclientid(ccid==0?oldNtfy.get(0).getCompanyclientid():ccid);
				newNtfy.setReferenceid(referenceid);
				notificationsService.updateNotification(newNtfy);
				resp = "msg_data_saved";
			}
		}}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getNotifyDataByRefId")
	public @ResponseBody JsonObject getNotifyDataByRefId(int tableid, int refid){
		String query = "", ids = "";
		JsonObject data = new JsonObject();
		if(tableid==8){
			query = "SELECT j.juicio, jz.juzgado, c.client FROM Juicios AS j"
				+ " LEFT JOIN juzgados AS jz ON jz.juzgadoid=j.juzgadoid"
				+ " LEFT JOIN Companyclients AS cc ON cc.companyclientid=j.companyclientid"
				+ " LEFT JOIN Clients AS c ON c.clientid=cc.clientid"
				+ " WHERE j.juicioid=" + refid;
			List<Juicios> entity = juiciosService.getAll(query);
			System.out.println("entity="+entity);
			if(Functions.isNumeric(entity.get(0)+"")) //  isNumeric() en caso de ser en base numérica.
				ids = entity.get(0) + ",";
			else
				ids = entity.get(0).getJuicio() + ",";
		//ShortHand	ids = (isNumeric(cclient.get(i)+""))?cclient.get(i):cclient.get(i).getClientid() + ",";
			data.addProperty("x",ids);
		}
		System.out.println("data="+data);
		return data;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ntfyListener")
	public @ResponseBody Map<Integer, Object> ntfyListener(HttpServletRequest req, HttpServletResponse res){
		HttpSession sess = req.getSession(false);
		Map<Integer,Object> m1=new HashMap<Integer,Object>();
		if(sess!=null)if(sess.getAttribute("isLogin")!=null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			String avatar = "", query = "FROM Notifications "
 					+ "WHERE confirmations IS NOT NULL AND confirmations<>''"
					+ " AND (confirmations LIKE '%\"" + userDto.getId()
					+ "\":{\"confirmed\":\"\",\"notified\":\"\"}%'"
					+ " OR confirmations LIKE '%\"" + userDto.getId()
					+ "\":{\"confirmed\":\"___________________\",\"notified\":\"\"}%')";
//TODO Las siguientes líneas con para una falsa notificación:
//+ "WHERE notificationid IN(147,1353,1352,1351,1350,1349,1341,428,392)";
					
//TODO
/*	0) Pruebas con cambios de Fco, y Mario, que se reflejen las notificaciones
	1) Sigue probar que sólo aparezca las nuevas notificaciones
	2) Luego replicar para aplicar en Dashboard HOME con un nuevo proceso, ej. "gethomentfy" (msg no leidos u no notificados)
	3) Agrupar las notificaciones en la campanita dentro de de un div con el SQL, traer los msg no leidos/notificados.

Archivos de imagenes para Avatars: home/git/src/main/webapp/resources/assets/images
actionsdetails:
a)	nid=1341	{"0":{"field":"juicioid","newdata":"156","olddata":""},"1":{"field":"usuarioidautoriza","newdata":"162","olddata":""},"2":{"field":"shareddocketid","newdata":"46","olddata":""},"3":{"field":"emailexternaluser","newdata":"acevesabogados@gmail.com","olddata":""},"4":{"field":"userid","newdata":"167","olddata":""},"5":{"field":"shareddate","newdata":"Aug 19, 2024 12:00:00 PM","olddata":""}}
b)	nid=428		{"0":{"field":"movimientoid","newdata":"2566","olddata":""},"1":{"field":"movimiento","newdata":"SE TURNA A PONENCIA","olddata":""},"2":{"field":"tipoactuacionid","newdata":"3","olddata":""},"3":{"field":"cuaderno","newdata":"principal","olddata":""},"4":{"field":"recursoid","newdata":"19","olddata":""},"5":{"field":"fechaacuerdo","newdata":"Mar 16, 2023 12:00:00 AM","olddata":""},"6":{"field":"fechanotificacion","newdata":"Mar 17, 2023 12:00:00 AM","olddata":""}}
c)	nid=392		{"0":{"field":"amparoid","newdata":"90","olddata":""},"1":{"field":"movimientoid","newdata":"2595","olddata":""},"2":{"field":"movimiento","newdata":"INFORME PREVIO","olddata":""},"3":{"field":"tipoactuacionid","newdata":"1","olddata":""},"4":{"field":"cuaderno","newdata":"incidental","olddata":""},"5":{"field":"fechapresentacion","newdata":"Mar 28, 2023 12:00:00 AM","olddata":""}}
*/

			List<Notifications> nList = notificationsService.getAll(query + " ORDER BY notificationid DESC");

			for(int n=0;n<nList.size();n++){
				Map<String,String> m2 = new HashMap<String,String>();
				Integer idref = nList.get(n).getReferenceid(), modId = nList.get(n).getModuleref();
				Menu menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE menuid="+modId, null);
				String courtname = "", abrType = "", dash = "", nid = nList.get(n).getNotificationid()+"",
					linkPage = menutmp.getLink().toLowerCase();

/*				abrType=linkPage.indexOf("juicio")>=0?"ju":linkPage.indexOf("appeal")>=0?"ap"
					:linkPage.indexOf("indprotection")>=0?"ai":linkPage.indexOf("protection")>=0?"ad"
					:linkPage.indexOf("resource")>=0?"re":linkPage.indexOf("consult")>=0?"cn":"";*/

//TODO:
/* Crear una tabla para ajustar estos valores:
	1=juicios; 2=apelaciones; 3=amparos directos; 4=amparos indirectos; 5=recursos; 
	6=Clientes; 7=movimientos; 8=Logo Firma; 9=Clientes; 10=Usuarios; 11=Calendario;	*/

				if(linkPage.indexOf("juicio")>=0){
					abrType = "ju";
					dash = "trialsdashboard";
avatar="resources/assets/images/juicios-amparo.png";
				}else if(linkPage.indexOf("appeal")>=0){
					abrType = "ap";
					dash = "appealsdashboard";
avatar="resources/assets/images/juicios-amparo.png";
				}else if(linkPage.indexOf("protection")>=0){
					abrType = "ad";
					dash = "protectiondashboard";
avatar="resources/assets/images/juicios-amparo.png";
				}else if(linkPage.indexOf("indprotection")>=0){
					abrType = "ai";
					dash = "indprotectiondashboard";
avatar="resources/assets/images/juicios-amparo.png";
				}else if(linkPage.indexOf("resource")>=0){
					abrType = "re";
					dash = "resourcedashboard";
avatar="resources/assets/images/juicios-amparo.png";
				}else if(linkPage.indexOf("consult")>=0){
					abrType = "cn";
					dash = "consultasdashboard";
avatar="resources/assets/images/juicios-amparo.png";
				}
				if(Functions.isEmpty(abrType))
					return m1;

				HashMap<String, String> docData=null;
				//HashMap<String, String> docData=commonsCtrll.checkAccessDoc(req,0,0,abrType,idref,"",false);
				
				if(modId==6||modId==9){
		        	List<Clients> ct = clientsService.getAll("SELECT photo FROM Clients WHERE clientid="
	        			+ docData.get("clientid"));
		        	avatar = Functions.toStr(ct.get(0));
				}

				String jid = docData.get("juicios");
				if(!Functions.isEmpty(jid)){
					query = "SELECT jz.juzgado FROM Juzgados AS jz"
						+ " LEFT JOIN Juicios AS j ON j.juzgadoid=jz.juzgadoid"
						+ " WHERE j.juicioid=" + jid;
					List<?> courtinfo = dao.sqlHQL(query, null);
					if(courtinfo!=null)if(courtinfo.size()>0)
						courtname = Functions.toStr(courtinfo);
				}

				// Archivos adjuntos
				String files = "", docQuery="FROM Uploadfiles WHERE catalogtype="
					+ modId + " AND idregister="  + idref + " ORDER BY path ASC";
				List<Uploadfiles> docList = dao.sqlHQL(docQuery,null);
				for(int d=0; d<docList.size(); d++)
					files+=docList.get(d).getFilename()+"|";
// TODO Archivos adjuntos Por movimientos???
				files=files.replaceAll("\\|$","");
				m2.put("abrType",abrType);
				m2.put("action",nList.get(n).getActiontypeid()+"");
				m2.put("avatar",avatar);
				m2.put("clientname",docData.get("client_name"));
				m2.put("courtname",courtname);
				m2.put("dash",dash);
				m2.put("date",nList.get(n).getCapturedate()+"");
m2.put("description","Descripción proveniente de cada movimiento o del documento registrado");
				m2.put("docname",nList.get(n).getReference());
				m2.put("files",files);
				m2.put("idref",idref+"");
				m2.put("link",menutmp.getLink());
				m2.put("modulename",menutmp.getMenu());
				m2.put("nid",nid);
//				m2.put("source",modid+"");
				m1.put(n,m2);
			}
		}
		return m1;
    }
}