package com.aj.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
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

import com.aj.model.Amparos;
import com.aj.model.Apelaciones;
import com.aj.model.Companies;
import com.aj.model.Companyclients;
import com.aj.model.CustomColumnsValues;
import com.aj.model.ECalendar;
import com.aj.model.Juicios;
import com.aj.model.Menu;
import com.aj.model.Menuprivileges;
import com.aj.model.Movimientos;
import com.aj.model.Notifications;
import com.aj.model.Privileges;
import com.aj.model.Recursos;
import com.aj.model.SharedDockets;
import com.aj.model.Smtpmail;
import com.aj.model.Socialnetworks;
import com.aj.model.Uploadfiles;
import com.aj.model.Users;
import com.aj.service.AccessDbJAService;
import com.aj.service.AmparosService;
import com.aj.service.ApelacionesService;
import com.aj.service.ClientsService;
import com.aj.service.CompaniesService;
import com.aj.service.CompanyclientsService;
import com.aj.service.ConsultasService;
import com.aj.service.CustomColumnsValuesService;
import com.aj.service.ECalendarService;
import com.aj.service.JuiciosService;
import com.aj.service.MovimientosService;
import com.aj.service.NotificationsService;
import com.aj.service.RecursosService;
import com.aj.service.SharedDocketsService;
import com.aj.service.SocialnetworkService;
import com.aj.service.UserService;
import com.aj.utility.Functions;
import com.aj.utility.HtmlEmailSender;
import com.aj.utility.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class CommonController {
	@Autowired
	private CalendarController calendarCtrll;

	@Autowired
	private NotificationsController notificationsCtrll;

	@Autowired
	public AmparosService amparosService;

	@Autowired
	public ApelacionesService apelacionesService;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	public ConsultasService consultasService;

	@Autowired
	public ECalendarService eCalendarService;

	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public CompaniesService companiesService;

	@Autowired
	public CompanyclientsService companyclientsService;

	@Autowired
	public CustomColumnsValuesService customcolumnsvaluesService;
	
	@Autowired
	public MovimientosService movimientosService;

	@Autowired
	public NotificationsService notificationsService;

	@Autowired
	public RecursosService recursosService;

	@Autowired
	public SharedDocketsService sharedDocketsService;

	@Autowired
	public SocialnetworkService socialnetworkService;

	@Autowired
	public ToolsController ToolsCtrll;
	
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

	/** Verifica si el usuario tiene permisos para ver registros de un cliente o en caso que el usuario sea cliente, sólo verá sus documentos.<br>
		Si no es indicado uno de los dos valores "clientid" o "companyclientid", tomará de referancia al usuario activo.
		@param req	HttpServletRequest.
		@param clientid			(Opcional) Id del campo "clientid". Si no es necesario, se deberá indicar un <b>cero</b>.
		@param companyclientid	(Opcional) Id del campo "companyclientid". Si no es necesario, se deberá indicar un <b>cero</b>. 
		@return	HashMap<String, String> con los valores del cliente y del usuario activo (ver más abajo en "Forma de uso").
		@throws HashMap Datos del cliente vacios en caso de que el usuario no tenga acceso a ver los registros del cliente indicado.
		<br><br>*FORMA DE USO* Para obtener alguno de los valores aquí listados, se debe utilizar "get()":
			<ul>
				<li>companyclientid</li>
				<li>companyid</li>
				<li>clientid</li>
				<li>first_name (Nombre de usuario)</li>
				<li>last_name (Apellido de usuario)</li>
				<li>client (nombre del cliente)</li>
				<li>id (Id de usuario)</li>
				<li>role</li>
				<li>usertype (1=cliente; 0 ó null=abogado)</li>
			<ul><dl>
			<dt>Ejemplo 1.</dt>
				<dd>String varName = valAccessClientDocs(req,0,0).get(<b>"companyclientid"</b>);</dd>
			<dt>Ejemplo 2.</dt>
				<dd>HashMap&lt;String, String&gt; miArreglo = valAccessClientDocs(req,client,0);</dd>
				<dd>String nombre = miArreglo.get(<b>"client"</b>);</dd></dl>	*/
	@SuppressWarnings("unchecked")
// TODO Proceso original que será reemplazado por checkAccessDoc
	public HashMap<String, String> valAccessClientDocs(HttpServletRequest req, int clientid, int companyclientid){
		HttpSession sess = req.getSession(false);
		HashMap<String, String> info = new HashMap<>();
		UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
		String clause = "", first_name = "", last_name = "", client = "",
			query = "SELECT cc.companyclientid, cc.clientid, cc.companyid, c.client,"
				+ " u.first_name, u.last_name, u.id, u.role, u.usertype"
				+ " FROM Companyclients AS cc"
				+ " LEFT JOIN Clients AS c ON c.clientid=cc.clientid";
		Integer companyid = userDto.getCompanyid(), role = userDto.getRole(),
			usertype = Functions.toInt(userDto.getUsertype());
		Long id = userDto.getId();
		if(usertype==1){	// 1=cliente es usuario, 0 ó null=abogado es usuario.
			query+=" LEFT JOIN Users AS u ON u.linkedclientid=cc.companyclientid";
			clause = " AND cc.companyclientid=" + userDto.getLinkedclientid();
		}else if(role!=ROLE_SYSADMIN||role!=ROLE_CJADMIN){	// Si el usuario es un abogado.
			query+=" LEFT JOIN Users AS u ON u.companyid=cc.companyid";
			if(companyclientid>0)
				clause = " AND cc.companyclientid=" + companyclientid;
			else if(clientid>0)
				clause+=" AND cc.clientid="+clientid + " AND cc.companyid="+userDto.getCompanyid();
			else
				clause+=" AND cc.companyid="+userDto.getCompanyid();
			clause+=" AND u.id="+userDto.getId();
		}
		clause = clause.replaceAll("^ AND ", " WHERE ");

		List<?> data = dao.sqlHQL(query + clause, null);
		Object[] tmp = data.toArray();
		if(data.size()>0)
			for (Object cdata:tmp){
				Object[] obj= (Object[]) cdata;
				boolean go = false;
				if(clientid>0){
					if(clientid==((Integer) obj[1]) && companyid==(Integer) obj[2])
						go = true;
				}else if(companyclientid>0 || usertype==1){
					if(companyclientid == (Integer) obj[0])
						go = true;
				}
				if(go){
//				|| (role!=ROLE_SYSADMIN || role!=ROLE_CJADMIN)){
					companyclientid = (Integer) obj[0];
					clientid = (Integer) obj[1];
					companyid= (Integer) obj[2];
					client = (String) obj[3];
					first_name= (String) obj[4];
					last_name = (String) obj[5];
					id = (Long) obj[6];
					role=(Integer) obj[7];
					usertype = (Integer) obj[8];
				}
				break;
			}
		info.put("companyclientid",companyclientid+"");
		info.put("clientid",clientid+"");
		info.put("companyid",companyid+"");
		info.put("first_name",(Functions.isEmpty(first_name)?userDto.getFirst_name():first_name));
		info.put("last_name",(Functions.isEmpty(last_name)?userDto.getLast_name():last_name));
		info.put("client",client);
		info.put("id",id+"");
		info.put("role",role+"");
		info.put("usertype",usertype==null?"0":usertype+"");
		return info;
	}

	/** Obtiene los Juicios propios y a los que se tiene acceso a ver, así como asignados o compartidos desde otras firmas.
	@param	req			HttpServletRequest
	@param	clientid	(Opcional) Id de un cliente específico a obtener sus juicios. Si no es requerido, se deberá indicar un <b>cero</b>.
	@param	juicioid	(Opcional) Id de un juicio específico. Analiza si el usuario tiene permiso para ver este juicio. Si no es requerido, se deberá indicar un <b>cero</b>.
	@return	(HashMap)	Arreglo HasMap con cadenas de ids separados por comas de las columnas: <ul>
			<li><b>juiciosid</b> - (String) Ids separados por coma de los juicios el cual tenga acceso el usuario.</li>
			<li><b>clientsid</b> -(String) Ids separados por coma de los clientes correspondientes a los juicios.</li>
			<li><b>companyclientsid</b> - (String) Ids separados por coma de "companyclientid".</li><br>
			*Para obtener las cadenas se debe utilizar "get()" seguido de una de las opciones mencionadas arriba.<br><dl>
			<dt>Ejemplo 1.</dt>
				<dd>String miVariable = getAllTrials(req,0,juicioid).get(<b>"juiciosid"</b>);</dd>
			<dt>Ejemplo 2.</dt>
				<dd>HashMap&lt;String, String&gt; miArreglo = getAllTrials(req,clientid,0);</dd>
				<dd>String misJuicios = miArreglo.get(<b>"juiciosid"</b>);</dd>
				<dd>String misClientes = miArreglo.get(<b>"clientsid"</b>);</dd></dl>
	@throws (HashMap)	Retorna cadenas vacías en caso de no tener permisos para ver juicios.	*/
// TODO Proceso original que será reemplazado por checkAccessDoc
	public HashMap<String, String> getAllTrials(HttpServletRequest req, int clientid, int juicioid) {
		HttpSession sess = req.getSession(false);
		UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
		int role=userDto.getRole(), userid=(int) userDto.getId(), companyid=userDto.getCompanyid(),
			idLinkedClient=userDto.getLinkedclientid()==null?0:userDto.getLinkedclientid();
		String whereClause = "", shrJuicioids = "", shrClientids = "", shCoClientid = "",
			query = "SELECT DISTINCT(j.juicioid), cc.clientid, cc.companyclientid FROM Juicios AS j"
				+ " LEFT JOIN SharedDockets AS sh ON sh.juicioid=j.juicioid"
				+ " LEFT JOIN Companyclients AS cc ON cc.companyclientid=j.companyclientid"
				+ " WHERE cc.clientid>0";
		boolean isUserClient = userDto.getUsertype()==null?false:userDto.getUsertype()==1?true:false;
		if((role!=ROLE_SYSADMIN && role!=ROLE_CJADMIN)){
			whereClause=" AND (j.userid";
			if(role==ROLE_FIRMADMIN){	//Administradores obtienen todos los juicios su propia Firma
				List<Users> allUsersFirm = userService.getAll("FROM Users WHERE companyid=" + companyid);
				String allUserIds1 = "", allUserids2 = "", allUsersEmail = "";
				for (int i = 0; i < allUsersFirm.size(); i++){
					allUserIds1+=((Functions.isNumeric(allUsersFirm.get(i)+""))?allUsersFirm.get(i):allUsersFirm.get(i).getId()) + ",";
					allUserids2+="'" + allUsersFirm.get(i).getId()+"',";
					allUsersEmail+="'"+((Functions.isNumeric(allUsersFirm.get(i)+""))?allUsersFirm.get(i):allUsersFirm.get(i).getEmail()) + "',";
				}
				allUserIds1=allUserIds1.replaceAll(".$","");
				allUserids2=allUserids2.replaceAll(".$","");
				allUsersEmail=allUsersEmail.replaceAll(".$","");
				whereClause+=" IN(" + allUserIds1 + ") OR j.abogadoasignado IN(" + allUserids2
					+ ") OR sh.userid IN(" + allUserIds1 + ") OR sh.emailexternaluser IN(" + allUsersEmail + ")";
			}else if(isUserClient){	//En caso de que el usuario sea cliente
				query = "SELECT DISTINCT (j.juicioid), cc.clientid, cc.companyclientid FROM Juicios AS j"
					+ " LEFT JOIN Companyclients AS cc ON cc.companyclientid=j.companyclientid"
					+ " WHERE cc.companyid=" + userDto.getCompanyid()
					+ " AND (cc.companyclientid=" + idLinkedClient
					+ " OR j.userid=" + userDto.getId();
				whereClause="";
			}else{
				whereClause+="=" + userid + " OR j.abogadoasignado='" + userid + "' OR sh.userid="
					+ userid + " OR sh.emailexternaluser='" + userDto.getEmail() + "'";
			}
			whereClause+=")";
		}
		if(juicioid>0)	//Obtiene un juicio en específico
			whereClause+=" AND j.juicioid=" + juicioid;
		if(clientid>0 && idLinkedClient<1)	//Obtiene un cliente en específico
			whereClause+=" AND cc.clientid="+clientid;
		List<Juicios> allTrials = juiciosService.getAll(query + whereClause);
		if(allTrials.size()>0){
			Object[] tmp = allTrials.toArray();
			for (Object cdata:tmp){
				Object[] obj= (Object[]) cdata;
				int clienteid=(obj[1]==null)?0:(int) obj[1];
				shrJuicioids+=((obj[0]==null)?0:(int) obj[0])+",";
				if(shrClientids.indexOf(","+clienteid+",")<0 && !shrClientids.startsWith(clienteid+","))
	                shrClientids+=clienteid+",";
				//Si no existe el cliente, lo agrega.
				List<Companyclients> existSharedClient = companyclientsService.getAll("FROM Companyclients WHERE clientid="
					+ clienteid + " AND companyid=" + companyid);
				if(existSharedClient.size()==0){
					Companyclients coclient = new Companyclients();
					coclient.setCompanyid(companyid);
					coclient.setClientid(clienteid);
					long newCC = companyclientsService.addNewCClient(coclient);
					shCoClientid+=newCC+",";
				}else{
					if(shCoClientid.indexOf(","+obj[2]+",")<0 && !shCoClientid.startsWith(obj[2]+","))
						shCoClientid+=(obj[2]==null)?0:(int) obj[2]+",";
				}
			}
		}
		HashMap<String, String> sharedIds = new HashMap<>();
		shrClientids=Functions.noDuplicatesStrArr((shrClientids.replaceAll(".$","")).split(","));
		shCoClientid=Functions.noDuplicatesStrArr((shCoClientid.replaceAll(".$","")).split(","));
		sharedIds.put("juiciosid", shrJuicioids.replaceAll(".$",""));
		sharedIds.put("clientsid", shrClientids);
		sharedIds.put("companyclientsid",shCoClientid);
		return sharedIds;
	}

	/** Verifica el acceso a documentos de un cliente y retorna los ids solicitados, en caso que el usuario sea "cliente", verá sólo sus documentos.<br><br>
	<br>* Si no es indicado "clientid" ni "companyclientid", se tomará de referancia todos los clientes que el usuario actual pueda ver.<br>
	@param req	HttpServletRequest.
	@param clientid			[*Opcional ] Id del cliente, si no es necesario se deberá indicar un <b>cero</b> (0).
	@param companyclientid	[*Opcional ] Id del campo "companyclientid", si no es necesario se deberá indicar un <b>cero</b> (0).<br>
	@param reftype			[ Requerido si existe "refid" ] Referencia de dos letras de acuerdo al tipo de documento a analizar:<dl>
	 						<dd>"ju" = Juicios</dd>
	 						<dd>"ap" = Apelaciones</dd>
	 						<dd>"ad" = Amparos directos</dd>
	 						<dd>"ai" = Amparos indirectos</dd>
	 						<dd>"am" = Cualquier amparo (directos o indirectos)</dd>
	 						<dd>"re" = Recursos</dd>
	 						<dd>"cn" = Consultas</dd>
	 						<dd>"sd" = Juicios compartidos</dd></dl>
	@param refid			[ Requerido si existe "reftype" ]Código Id relaciondo al documento con "reftype", si no es requerido dejarlo en cero (0).
	@param status			[ Opcional ] Obtiene los documentos con algúno de los siguientes estatus:<dl>
							<dd>"c" = Concluidos</dd>
							<dd>"a" = Activos</dd>
							<dd>"s" = Suspendido</dd>
							<dd>No indicado = Todos los registros</dd>
	@param procedure		<dd>Tipo de procedimiento a realizar:</ul>
								<li>"hasaccess":  Sólo verifica si tiene acceso al registro indicados por "refid" y "reftype".</li>
								<li>"list":	Obtiene todos los documentos a los que tiene acceso el usuario.</li>
								<li>"related": Obtiene todos los documentos relacionados con el "refid" y "reftype" otorgados.</li></ul>
							</dd></dl>
	@param customCols		[ Opcional ] Columnas adicionales a retornar con sus abreviaturas como prefijos (negritas).
							En caso de requerir más de uno, deberán ir separados por comas:<dl>
							<dd><b>j.</b>columna-de-tabla-juicios</dd>
							<dd><b>ap.</b>columna-de-tabla-apelaciones</dd>
							<dd><b>am.</b>columna-de-tabla-amparos</dd>
							<dd><b>re.</b>columna-de-tabla-recursos</dd>
							<dd><b>cn.</b>columna-de-tabla-consultas</dd>
							<dd><b>c.</b>columna-de-tabla-clientes</dd>
							<dd><b>cc.</b>columna-de-tabla-companyclients</dd>
							<dd><b>u.</b>columna-de-tabla-usuarios</dd>
							<dd><b>sd.</b>columna-de-tabla-shareddockets</dd></dl>
	@return	HashMap<String, String> con los datos solicitados de acuerdo a los privilegios y rol del usuario activo.
	@throws HashMap Datos vacios en caso de que el usuario no tenga acceso a ver los registros solicitados.

	<br><br>*FORMA DE USO*<br>
	Utilizar "get()" para obtener uno o más de los siguientes datos:
	<ul>
		<li> * * * DATOS EN GENERAL (Dato en el reistro uno: mi_variable.get(1) * * *</li>
		<li>origin   (own=Documento personal; asg=Documento asignado; null=Sin permisos/No lo puede ver)</li>
		<li>{@code
			Forma 1:
			Map<?, ?> allInfo = (HashMap<?, ?>) commonsCtrll.checkAccessDoc(req,0,0,"re",id,"","hasaccess","");
			Map<Integer, Object> info = (Map<Integer, Object>) allInfo.get(1);
			String origin = info.get("origin")+"";
		}</li>
		<li>{@code
			Forma 2:
			Map<?, ?> allInfo = (HashMap<?, ?>) commonsCtrll.checkAccessDoc(req,0,0,"re",id,"","hasaccess","");
			Map<Integer, Object> info = (Map<Integer, Object>) allInfo.get(1);
			String origin = ((Map<String, Object>) allInfo.get(1)).get("origin") + "";
		}</li>
		<li> * * * DATOS AL OBTENER "list" (Listados con ids separados por coma) * * * </li>
		<li>juicios</li>
		<li>apelaciones</li>
		<li>directos</li>
		<li>indirectos</li>
		<li>amparos (directos e indirectos)</li>
		<li>recursos</li>
		<li>consultas</li>
		<li>Para obtenerlos, siempre será en el registro cero: mi_variable.get(0)</li>
		<li>{@code
			Forma 1:
			Map<?, ?> allInfo = (HashMap<?, ?>) commonsCtrll.checkAccessDoc(req,0,0,"re",id,"","related",""); 
			Map<String, Object> relDocs = (Map<String, Object>) allInfo.get(0);
			String related = relDocs.get("related")+"");
		}</li>
		<li>{@code
			Forma 2:
			Map<?, ?> allInfo = (HashMap<?, ?>) commonsCtrll.checkAccessDoc(req,0,0,"re",id,"","related","");
			String related = ((Map<String, Object>) allInfo.get(0)).get("related") + "";
		}</li>
		<li> * * * DATOS DE COLUMNAS PERSONALIZADAS (Algunos ejemplos) * * * -</li>
		<li>j.juicioid ó ap.apelacionid ó el id correspondiente al tipo de dato a obtener (default)</li>
		<li>El resto de columnas solicitadas, por ejemplo:</li>
		<li>j.juicio</li>
		<li>ap.toca</li>
		<li>c.client</li>
//FIXME
		<li>clientes_det (Detalles: Datos unificados del cliente con formato: "clienteid|companyclientid|nombre^")</li>
		<li>lawyer_ids</li>
		<li>related (Documentos relacionados con formato: "xx|id|descripción^" donde "xx" corresponde a las abreviaturas <b>reftype</b>)</li>
	<ul><dl>
	<dt>Ejemplos de llamado:</dt>
// FIXME: Crear ejemplos:
		<dd>1)	checkAccessDoc(0,0,"",0,"","")		// Si no es indicado nada, retornará sólo los datos importantes del usuario activo.</dd>
		<dd>2)	checkAccessDoc(<b>10</b>,0,"",0,"","")	// Retornará los datos importantes del cliente con el id "10".</dd>
		<dd>3)	checkAccessDoc(0,0,<b>"ju"</b>,0,"","")	// Retornará todos los juicios.</dd>
		<dd>3)	checkAccessDoc(0,0,<b>"ju",30</b>,"","")	// Retornará los datos sólo del juicio con el id "30".</dd>
		<dd>4)	checkAccessDoc(0,0,"ju",30,"",<b>"related"</b>)	// Retornará todos los relacionado al juicioid=30 y los documentos relacionados como apelaciones, consultas, etc. (útil para dashboards).</dd>
		<dd>Adicionalmente, siempre retornará los datos del usuario activo.</dd></dl>
		Ejemplos completo:<br><br>
		// 1) Obtener un listado de los registros que puede ver el usuario<br>
{@code 	HashMap<Integer, Object> info = checkAccessDoc(req,0,0,"ju",id,"","list","c.clientid,c.client");}<br><br>
		// 2) Obtener un sólo registro -usar ".get(1)"-<br>
{@code	Map<?, ?> info = (HashMap<?, ?>) checkAccessDoc(req,0,0,"re",id,"","hasaccess","c.clientid,c.client").get(1);}<br>
		int id = Functions.toInt(info.get("re.recursoid")+""); //Todos los resultados son String, por lo que se deben convertir al formato adecuado.<br><br>	*/
	@SuppressWarnings("unchecked")
	public @ResponseBody HashMap<Integer, Object> checkAccessDoc(HttpServletRequest req, int clientid,
		int companyclientid, String reftype, int refid, String status, String procedure, String customCols){
		HttpSession sess = req.getSession(false);
		UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
		HashMap<Integer, Object> info = new HashMap<>();
//TODO: **** LÍNEAS TEMPORALES PARA PRUEBAS NUEVO "getShUser" ****
//int testUser = 176;
//userDto = ToolsCtrll.switchUserDTO(testUser);

		String basecols=",sd.userid,j.userid,j.abogadoasignado,sd.emailexternaluser,u.linkedclientid,"
				+ "u.role,cc.companyid", query = "", ju_ids = "", ap_ids = "", ad_ids = "", ai_ids = "",
			re_ids = "", cn_ids = "", sd_ids = "", related = "", nameId = "", origin = "",
			rel_cols=",j.juicioid,j.juicio,ap.apelacionid,ap.toca,am.amparoid,am.amparo,am.amparotipo,"
				+ "re.recursoid,re.recurso,cn.consultaid,cn.consulta,sd.shareddocketid,sd.juicioid",
			userEmail = userDto.getEmail();
		Integer midCols = 0, row = 1, userFirm = userDto.getCompanyid(), userRole=userDto.getRole(),
			usertype = Functions.toInt(userDto.getUsertype());
		Long userid = userDto.getId();

		if(!Functions.isEmpty(customCols))
			customCols=","+(customCols.toLowerCase().replaceAll("^\b*[,.]","").replaceAll("[,.]\b*$","")
				.replaceAll("u.password[,\\s]?",""));	// Evita obtener contraseñas
		if(Functions.isEmpty(procedure))
			procedure="hasaccess";
		else if(!procedure.equals("related"))
			rel_cols="";

		// Sólo valida si el usuario tiene acceso al un documento único
		if(reftype.equals("ju")){
			nameId = "j.juicioid";
			query = "SELECT j.juicioid" + basecols + rel_cols + customCols + " FROM Users u "
			+ "LEFT JOIN Juicios j ON (CAST(j.abogadoasignado AS integer)=u.id OR j.userid=u.id) "
			+ "LEFT JOIN Companyclients cc ON cc.companyclientid=j.companyclientid "
			+ "LEFT JOIN Clients c ON c.clientid=cc.clientid "
			+ "LEFT JOIN SharedDockets sd ON (sd.juicioid=j.juicioid"
+ " AND (sd.userid=" + userid + (Functions.isEmpty(userEmail)?"":" OR sd.emailexternaluser='" + userEmail) + "')) "
//+ " AND (sd.userid=u.id OR sd.emailexternaluser=u.email))"
			+ "LEFT JOIN Users u ON ((u.companyid=" + userFirm
			+ " AND (u.id=" + userid + " OR j.userid=u.id OR CAST(j.abogadoasignado AS integer)=u.id))"
			+ " OR (usertype=1 AND u.linkedclientid=cc.companyclientid) "			// UserType: 1=Cliente es usuario
			+ " OR (sd.userid=u.id OR sd.emailexternaluser=u.email))"
			+((procedure.equals("list")?"":"j.juicioid=" + refid)			// Clausulas WHERE
			+ (Functions.isEmpty(status)?"":" AND j.status="+Arrays.asList("c","a","s").indexOf(status))
			+ (clientid>0?" AND c.clientid="+clientid:"")
			+ (companyclientid>0?" AND cc.companyclientid="+companyclientid:"")
			+" AND (u.companyid=" + userFirm
			+ (usertype==1?" AND u.linkedclientid=cc.companyclientid AND usertype=1"// UserType: 1=Cliente es usuario
				:userRole<=ROLE_FIRMADMIN?"":" AND (u.role=" + userRole
			+ " AND u.id=" + userid + " OR j.userid=u.id OR CAST(j.abogadoasignado AS integer)=u.id)")
			+") OR sd.userid=u.id" + (Functions.isEmpty(userEmail)?"":" OR sd.emailexternaluser=u.email")
//+ ")OR sd.userid=u.id OR sd.emailexternaluser=u.email"
			+ (Functions.isEmpty(status)?"":" AND j.status="+Arrays.asList("c","a","s").indexOf(status))
			).replaceAll("^ AND ", " WHERE ");
		}else if(reftype.equals("ap")){
			nameId = "ap.apelacionid";
			query = "SELECT ap.apelacionid" + basecols + rel_cols + customCols + " FROM Apelaciones ap "
			+ "JOIN Juicios j ON ap.juicioid=j.juicioid "
			+ "LEFT JOIN Companyclients cc ON (cc.companyclientid=j.companyclientid) "
			+ "LEFT JOIN Clients c ON (c.clientid=cc.clientid) "
			+ "LEFT JOIN SharedDockets sd ON (sd.juicioid=j.juicioid"
+ " AND (sd.userid=" + userid + (Functions.isEmpty(userEmail)?"":" OR sd.emailexternaluser='" + userEmail) + "')) "
//+ " AND (sd.userid=u.id OR sd.emailexternaluser=u.email))"
			+ "LEFT JOIN Users u ON ((u.companyid=" + userFirm
			+ " AND (u.id=" + userid + " OR j.userid=u.id OR CAST(j.abogadoasignado AS integer)=u.id))"
			+ " OR (usertype=1 AND u.linkedclientid=(SELECT cc.companyclientid"
			+ " FROM Companyclients cc WHERE cc.companyclientid=j.companyclientid))) "
			+ " OR (sd.userid=u.id OR sd.emailexternaluser=u.email))"
// + " OR (u.role=" + ROLE_FIRMADMIN
			+((procedure.equals("list")?"":"ap.apelacionid=" + refid)		// Clausulas WHERE
			+ (Functions.isEmpty(status)?"":" AND j.status="+Arrays.asList("c","a","s").indexOf(status))
			+ (clientid>0?" AND c.clientid="+clientid:"")
			+ (companyclientid>0?" AND cc.companyclientid="+companyclientid:"")
			+" AND (u.companyid=" + userFirm
			+ (usertype==1?" AND u.linkedclientid=cc.companyclientid AND usertype=1"// UserType: 1=Cliente es usuario
				:userRole<=ROLE_FIRMADMIN?"":" AND (u.role=" + userRole
			+ " AND u.id=" + userid + " OR j.userid=u.id OR CAST(j.abogadoasignado AS integer)=u.id)")
			+") OR sd.userid=u.id OR sd.emailexternaluser=u.email"
			+ (Functions.isEmpty(status)?"":" AND j.status="+Arrays.asList("c","a","s").indexOf(status))
			).replaceAll("^ AND ", " WHERE ");
		}else if(Arrays.asList("ad","ai","am").contains(reftype)){
			nameId = reftype.equals("ai")?"indirectos":reftype.equals("ad")?"directos":"amparos";
			query = "SELECT am.amparoid" + basecols + rel_cols + customCols + " FROM Amparos am "
			+ "LEFT JOIN Juicios j ON am.juicioid=j.juicioid "
			+ "LEFT JOIN Apelaciones ap ON am.apelacionid=ap.apelacionid "
			+ "LEFT JOIN Companyclients cc ON am.companyclientid=cc.companyclientid "
			+ "LEFT JOIN Clients c ON c.clientid=cc.clientid "
			+ "LEFT JOIN SharedDockets sd ON (sd.juicioid=j.juicioid"
			+ " AND (sd.userid=" + userid + (Functions.isEmpty(userEmail)?"":" OR sd.emailexternaluser='" + userEmail) + "')) "
			+ "LEFT JOIN Users u ON ((u.companyid=" + userFirm
			+ " AND (u.id=" + userid + " OR j.userid=u.id OR CAST(j.abogadoasignado AS integer)=u.id))"
			+ " OR (usertype=1 AND u.linkedclientid=cc.companyclientid) "			// UserType: 1=Cliente es usuario
+ " OR (sd.userid=u.id OR sd.emailexternaluser=u.email))"			
//+ " AND (sd.userid=u.id OR sd.emailexternaluser=u.email))"
//+ " OR (u.role=" + ROLE_FIRMADMIN"
			+((procedure.equals("list")?"":" AND am.amparoid=" + refid)		// Clausulas WHERE
			+ (Functions.isEmpty(status)?"":" AND j.status="+Arrays.asList("c","a","s").indexOf(status))
			+ (clientid>0?" AND c.clientid="+clientid:"")
			+ (companyclientid>0?" AND cc.companyclientid="+companyclientid:"")
			+" AND (u.companyid=" + userFirm
			+ (usertype==1?" AND u.linkedclientid=cc.companyclientid AND usertype=1"// UserType: 1=Cliente es usuario
					:userRole<=ROLE_FIRMADMIN?"":" AND (u.role=" + userRole
			+ " AND u.id=" + userid + " OR j.userid=u.id OR CAST(j.abogadoasignado AS integer)=u.id)")
			+") OR sd.userid=u.id" + (Functions.isEmpty(userEmail)?"":" OR sd.emailexternaluser=u.email")
//+ ")OR sd.userid=u.id OR sd.emailexternaluser=u.email"
			+ (Functions.isEmpty(status)?"":" AND j.status="+Arrays.asList("c","a","s").indexOf(status))
			+" AND ((am.juicioid IS NOT NULL AND am.juicioid=j.juicioid)"
			+ " OR (am.apelacionid IS NOT NULL AND am.apelacionid=ap.apelacionid)"
			+ " OR (am.juicioid IS NULL AND am.apelacionid IS NULL AND am.companyclientid=cc.companyclientid))"
			).replaceAll("^ AND ", " WHERE ");

		}else if(reftype.equals("re")){
			nameId = "re.recursoid";
			midCols++; // midCols = 1 columna adicional->cn.abogadoid
			query = "SELECT re.recursoid" + basecols + ",cn.abogadoid" + rel_cols + customCols
			+ " FROM Recursos re "
			+ "LEFT JOIN Amparos am ON (re.tipoorigen IN(1,2) AND re.tipoorigenid=am.amparoid) "
			+ "LEFT JOIN Juicios j ON j.juicioid=am.juicioid "
			+ "LEFT JOIN Apelaciones ap ON ap.apelacionid=am.apelacionid "
			+ "LEFT JOIN Companyclients cc ON cc.companyclientid=am.companyclientid "
			+ "LEFT JOIN Clients c ON cc.clientid=c.clientid "
			+ "LEFT JOIN Consultas cn ON cn.juicioid=j.juicioid "
			+ "LEFT JOIN SharedDockets sd ON sd.juicioid=j.juicioid "
			+ "LEFT JOIN Users u ON ("
			+ (usertype==1?"u.linkedclientid=cc.companyclientid"	// UserType: 1=Cliente es usuario
				:"u.id IN (j.userid, CAST(j.abogadoasignado AS integer), sd.userid)")
			+ " OR sd.emailexternaluser=u.email)"
			+ " WHERE "
			+((procedure.equals("list")?"":nameId + "=" + refid)
			+ (Functions.isEmpty(status)?"":" AND j.status=" + Arrays.asList("c","a","s").indexOf(status))
			+ (clientid>0?" AND c.clientid="+clientid:"")
			+ (companyclientid>0?" AND cc.companyclientid=" + companyclientid
				:usertype==1?" AND cc.companyclientid=" + userDto.getLinkedclientid():"")
			+(!procedure.equals("related")?""
				:" AND am.amparoid IN(SELECT tipoorigenid FROM Recursos WHERE recursoid="+ refid + ")")
			+" AND (cc.companyid=" + userFirm + " AND re.tipoorigen IN(1,2)"
			+(usertype==1?" AND am.companyclientid=" + userDto.getLinkedclientid()
				:(userRole==ROLE_FIRMADMIN?" OR sd.userid=" + userid
					:" AND " + userid + " IN (j.userid, CAST(j.abogadoasignado AS integer), sd.userid)"))
			+" OR sd.emailexternaluser='" + userEmail + "'"
			+" OR (am.companyclientid "	// Amparos que no derivan de juicios o apelaciones
			+ (clientid>0?"IN(SELECT companyclientid FROM Companyclients WHERE clientid="
				+ clientid + " AND companyid=" + userFirm + ")"
				:companyclientid>0?companyclientid
					:"IN(SELECT DISTINCT(companyclientid) FROM Companyclients WHERE companyid="
					+ userFirm + ") AND (am.juicioid=null OR am.juicioid=0)") + ")"
			+ ")").replaceAll("^ AND ", "");

		}else if(reftype.equals("cn")){
			nameId = "cn.consultaid";
			midCols++; // midCols = 1 columna (cn.abogadoid)
			query = "SELECT cn.consultaid" + basecols + ",cn.abogadoid"+ rel_cols + customCols
			+ " FROM Consultas cn "
			+ "LEFT JOIN Juicios j ON cn.juicioid=j.juicioid "
			+ "LEFT JOIN Companyclients cc ON cc.companyclientid=j.companyclientid "
			+ "LEFT JOIN Clients c ON c.clientid=cn.clienteid "
			+ "LEFT JOIN SharedDockets sd ON (sd.juicioid=j.juicioid"
			+ " AND (sd.userid=" + userid + (Functions.isEmpty(userEmail)?"":" OR sd.emailexternaluser='" + userEmail) + "')) "
			+ "LEFT JOIN Users u ON ((u.companyid=" + userFirm
			+ " AND (u.id=" + userid + " OR j.userid=u.id OR CAST(j.abogadoasignado AS integer)=u.id))"
			+ " OR (usertype=1 AND u.linkedclientid=cc.companyclientid)"			// UserType: 1=Cliente es usuario
			+ " OR (sd.userid=u.id OR sd.emailexternaluser=u.email))"
			+((procedure.equals("list")?"":" AND cn.consultaid=" + refid)	// Clausulas WHERE
			+ (clientid>0?" AND c.clientid="+clientid:"")
			+ (companyclientid>0?" AND cc.companyclientid="+companyclientid:"")
			+" AND (u.companyid=" + userFirm
			+ (usertype==1?" AND u.linkedclientid=cc.companyclientid AND usertype=1"// UserType: 1=Cliente es usuario
				:userRole<=ROLE_FIRMADMIN?"":" AND (u.role=" + userRole
			+ " OR j.userid=u.id OR CAST(j.abogadoasignado AS integer)=u.id)")
			+") OR sd.userid=u.id" + (Functions.isEmpty(userEmail)?"":" OR sd.emailexternaluser=u.email")
+ (Functions.isEmpty(status)?"":" AND j.status="+Arrays.asList("c","a","s").indexOf(status))
			+" AND ((cn.juicioid IS NOT NULL AND cn.juicioid=j.juicioid)"
			+ " OR (cn.abogadoid=j.userid OR cn.abogadoid=CAST(j.abogadoasignado AS integer)"
			+ " OR cn.abogadoid = sd.userid))"
			).replaceAll("^ AND ", " WHERE ");
		}
/*Sigue:
1) Aplicar el nuevo query a los demás documentos: juicios, apelaciones, etc.
1) Probar con otros usuarios
2) Probar con otras compañias
3) Replicar en otros módulos
4) Probar documento compartido desde otra firma
*/

		List<?> accessDoc = dao.sqlHQL(query, null);
System.out.println("\nRegistros: "+accessDoc.size());
		HashMap<String, Object> rowInfo = new HashMap<>();
		ArrayList<String> uniqueRecord = new ArrayList<>();
		String[] cus = customCols.replaceAll("^,\\s?","").split(",");
		if(accessDoc.size()==0){
			rowInfo.put(nameId, "0");
			rowInfo.put("origin", null);
			info.put(1,rowInfo);
		}
		for(Object cdata:accessDoc.toArray()){
			Object[] obj = (Object[]) cdata;
			rowInfo = new HashMap<>();
			//Evita duplicidad de registro(id) 
System.out.println("Rec: "+obj[0]+"|"+reftype);
			if(Functions.isEmpty(obj[6]) && Functions.strToInt(obj[7])==userDto.getCompanyid()){
				obj[6] = userDto.getRole();
//obj[] = userDto.getId();
			}
			if(uniqueRecord.contains(reftype+"|"+obj[0]))
	            continue;
			uniqueRecord.add(reftype+"|"+obj[0]);
System.out.println("-------> (" + obj[0] + ") "+obj[9]);
			rowInfo.put(nameId, obj[0]);
			if(procedure.equals("related")){	// Documentos relacionados
				String[] refDoc = {"ju","ap","ad/ai/am","re","cn","sd"},
					dCol = {ju_ids, ap_ids, null, re_ids, cn_ids, sd_ids};
				int[] col = {0, 2, 4, 7, 9, 11};
				int mc=8+midCols;
	            for(int c=0; c<col.length; c++){
	                int idx = Functions.strToInt(obj[mc+col[c]]);
System.out.println("("+idx+"="+refid+")\t..." + " refDoc[" + refDoc[c] + "] = '" + reftype + "' (reftype)");
	                if(idx==0 || (refid==idx
	                	&& (refDoc[c].equals(reftype) || refDoc[c].contains(reftype))))continue;
	                if(c==2){
    					int t = Functions.strToInt(obj[mc+2]+"");
    					related+=(t==2?"ai":"ad")+"|"+obj[mc+col[c]]+"|"+obj[mc+col[c]+1]+"^"+",";;
    					if(t==2)ai_ids+=obj[mc]+",";else ad_ids+=obj[mc]+",";
    				}else{
						related+=(c==0?"ju":c==1?"ap":c==3?"re":c==4?"cn":"sh")
							+"|"+idx+"|"+obj[mc+col[c]+1]+"^,";
						dCol[c]+=idx+",";
    				}
	            }
				HashMap<String, Object> relDocs = new HashMap<>();
				relDocs.put("juicios", Functions.noDuplicatesStr(ju_ids.replaceAll(",\\s?^","")));
				relDocs.put("apelaciones", Functions.noDuplicatesStr(ap_ids.replaceAll(",\\s?^","")));
				relDocs.put("directos", Functions.noDuplicatesStr(ad_ids.replaceAll(",\\s?^","")));
				relDocs.put("indirectos", Functions.noDuplicatesStr(ai_ids.replaceAll(",\\s?^","")));
				relDocs.put("recursos", Functions.noDuplicatesStr(re_ids.replaceAll(",\\s?^","")));
				relDocs.put("consultas", Functions.noDuplicatesStr(cn_ids.replaceAll(",\\s?^","")));
				relDocs.put("compartidos", Functions.noDuplicatesStr(sd_ids.replaceAll(",\\s?^","")));
				relDocs.put("related", Functions.noDuplicatesStr(related.replaceAll(",\\s?^","")).replaceAll("\\^,","^"));
				info.put(0,relDocs);
			}
//TODO: Crear un campo nuevo llamado "userid" para la tabla "Amparos": 
//Recursos desde amparos no relacionados, no se obtienen datos de usuario.
			// Identifica si el usuario tiene permisos total's (own) o limitados por asignación (asg)
			if((ROLE_FIRMADMIN==Functions.strToInt(obj[6]) && userFirm==Functions.strToInt(obj[7]))
			|| (userid+"").equals(obj[2]+"") || (userid+"").equals(obj[3]+"")
			|| ((userid+"").equals(obj[8]+"") && midCols>0))
				origin = "own";
			else if((userid+"").equals(obj[1]+"") || usertype==1	// UserType: 1=Cliente es usuario
			|| (userEmail).equals(obj[4]+""))
				origin = "asg";
			rowInfo.put("origin", origin);

			// Columnas personalizadas
			int rc = rel_cols.isEmpty()?0:(rel_cols.replaceAll("^,\\s?","")
				.replaceAll(",\\s?$","")).split(",").length;
			for(int c=0; c<cus.length; c++)
				rowInfo.put(cus[c], obj[c+8+midCols+rc]+"");
			info.put(row,rowInfo);

			if(procedure.equals("hasaccess"))
				break;
			row++;
		}
//FIXME userDto = ToolsCtrll.switchUserDTO(1);
		return info;
	}

	/** Obtiene los registros buscando en la llave "field" dentro del campo "actionsdetails"-tabla de "Notifications".
	@param	userid		Uno o más ids de usuarios (separados por comas).
	@param	colname		Nombre del campo a buscar dentro de Notificaciones-Details-Field.
	@param	value		(Opcional) Valor exácto a buscar. Si no es necesario dejarlo en cero (0).
	@throws	(0) Cero si no hay coincidencias.	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	public @ResponseBody String getIdsFromActiondetails(String userid, String colname, int value){
		String allIds = "0", query="FROM Notifications WHERE userid IN(" + userid
			+ ") AND (actionsdetails LIKE '%\"field\":\"" + colname + "\",\"newdata\":\"",
			or = " OR actionsdetails LIKE '%\"field\":\"" + colname + "\",\"newdata\":\"";
		query+=value>0?"\"%')"
			:"1%'"+or+"2%'"+or+"3%'"+or+"4%'"+or+"5%'"+or+"6%'"+or+"7%'"+or+"8%'"+or+"9%'";
		//List<Object[]> allRows = (List<Object[]>) (List) dao.sqlHQL(query, null);
		List<Notifications> allRows = notificationsService.getAll(query);
System.out.println("\nRegistros: "+allRows.size());
		if(allRows.size()>0){
			//ArrayList<String> uniqueIds = new ArrayList<>();
			//for(Object[] record : allRows){
			for(int i=0; i<allRows.size(); i++){
				String record = allRows.get(i).getActionsdetails() + "";
System.out.println("\nRegistro: "+record);
				String resRex = record.replaceAll(".*" + colname + "\",\"newdata\":\"(\\d+[0-9]|[1-9]).*","$1");
System.out.println("\nresRex: "+resRex);
				if(resRex.equals(record))
					continue;
				allIds+=","+resRex;
			}
			allIds = Functions.noDuplicatesStr(allIds);
		}
		return allIds;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getShUser")
	public @ResponseBody Object[] getShUser(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if(sess == null)return new Object[] {data};
		int trialid = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());

		HashMap<Integer, Object> trialData = checkAccessDoc(req, 0, 0, "ju", trialid, "", "hasaccess","");
		trialid = 0;//Functions.toInt(trialData.get("juicios"));
		if(trialid==0 || (trialData.get("usr_type")).equals("1"))
			return new Object[] {data};	// Sin permisos para clientes ni usuarios no autorizados

		ArrayList<String> currentTrialPriv = new ArrayList<>(), privForUsers = new ArrayList<>();
		HashMap<String, String> shMovPriv = new HashMap<>();
		List<SharedDockets> usersShared = null;
		List<Companies> companyList = null;
		List<Users> lawyerList = null;
		List<Juicios> infoTrial = juiciosService.getAll("FROM Juicios WHERE juicioid=" + trialid);
		int role =0,// Functions.toInt(trialData.get("usr_role")),
			userid =0,// Functions.toInt(trialData.get("usr_id")),
			originalUser = 0;//infoTrial.get(0).getUserid();

		// Obtiene todos los usuarios a quienes se les han compartido el juicio
		if(originalUser==userid || role==ROLE_SYSADMIN || role==ROLE_CJADMIN || role==ROLE_FIRMADMIN){
//TODO Functions.toLong(trialData.get("companyid"));
			Long companyid = 0L;
			String firmClause = " AND companyid="+companyid, shdkids = "",
				userClause = " AND id IN(" + (trialData.get("lawyer_ids")) + ") AND id<>"+userid;
			usersShared = sharedDocketsService.getAll("FROM SharedDockets WHERE juicioid="+trialid);

			// Toma los privilegios asignados por el usuario origen 
			if((usersShared!=null && usersShared.size()>0) || role==ROLE_FIRMADMIN){
				for(int i = 0; i < usersShared.size(); i++)
					shdkids+=usersShared.get(i).getShareddocketid()+",";
				shdkids=shdkids.replaceAll(",$","");
				if(!Functions.isEmpty(shdkids)){
					List<Menuprivileges> usersPrivileges = dao
						.sqlHQL("FROM Menuprivileges WHERE shareddocketid IN(" + shdkids + ")", null);
					for(Menuprivileges lp : usersPrivileges)
						privForUsers.add(lp.getShareddocketid()+"|"+lp.getPrivilegesid()+"|"+lp.getMenuid());
				}
			}
			lawyerList = userService.getAll("SELECT id, first_name, last_name, companyid FROM Users"
				+ (userClause.replaceAll("^ AND "," WHERE ")) +firmClause+ " ORDER BY first_name ASC, last_name ASC");
			companyList = companiesService.getAll("SELECT companyid, company FROM Companies"
				+ (firmClause.replaceAll("^ AND "," WHERE ")));
		}
		//Obtiene todos documentos compartidos y sus permisos (Sólo administradores o creadores del documento)
		List<Privileges> allPriv = dao.sqlHQL("FROM Privileges", null);
		for (Privileges lp : allPriv)
			currentTrialPriv.add(lp.getPrivilegesid()+"");

		data.put("detail", infoTrial);
		data.put("lawyerList", lawyerList);
		data.put("companyList", companyList);
		data.put("shdk", usersShared);
		data.put("shpriv", privForUsers);
		data.put("ctprv", currentTrialPriv);
		data.put("shmovpriv", shMovPriv);
		return new Object[] {data};
	}
	
	@RequestMapping(value = "/updateSharedTrial")
	public void updateSharedTrial(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		valUpdate:{
		if(sess!=null)
			if(sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))){
				int trialid=(req.getParameter("edid")==null)?0:Functions.toInt(req.getParameter("edid").trim());

				HashMap<Integer, Object> trialData=checkAccessDoc(req,0,0,"ju",trialid,"","hasaccess","");
				String lawyersPriv=(req.getParameter("lawyerassigned")==null)?"":req.getParameter("lawyerassigned").trim(),
					trials="0";//trialData.get("juicios");
				if(Functions.isEmpty(trials))
					break valUpdate;

				// Sólo el usuario creador del documento o administradores podrán aplicar cambios
				int userid = 0,//Functions.toInt(trialData.get("usr_id")),
					role = 0,//Functions.toInt(trialData.get("usr_role")),
					originalUser = 0;//Functions.toInt(trialData.get("unique_ownerid"));
if((originalUser==userid) 
	|| (role==ROLE_SYSADMIN || role==ROLE_CJADMIN || role==ROLE_FIRMADMIN)){
// TODO SHARED DOCS
/* 
a) En el detalle de datos compartidos, analizar que las fechas de notificación y confirmación sean correctas
b) Revisar que los privilegios se apliquen en TrialDashboard

Notificaciones
c) Analizar el popup de notificación
	1) Que marque como notificado
	2) Que marque como leido
	3) Que se vaya al dashboard correcto
d) Que se muestre en el Home-notifications list
	1) Que se vaya al dashboard correcto */
					// Asignación de permisos a abogados
/*//FIXME					firmCtrll.setLawyerSubPrivileges(req, res, trialid, lawyersPriv, userid,
							0,//Functions.toInt(trialData.get("usr_role")),
							originalUser,
							0,//Functions.toInt(trialData.get("companyid")),
						trialData.get("companyclientid"),trialData.get("usr_email"));*/
					resp="true";
				}
			}
		}
		try{
			res.getWriter().write(resp);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getSharedInfo")
	public @ResponseBody Object[] getSharedInfo(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if(sess==null)return new Object[] {data};
		int userid = req.getParameter("id1") == null ? 0 : Functions.toInt(req.getParameter("id1").trim()),
			trialid= req.getParameter("id2") == null ? 0 : Functions.toInt(req.getParameter("id2").trim());
		String email=req.getParameter("email")==null ? "": req.getParameter("email").trim(), clause=" AND ";

		// Analiza si tiene permisos para ver información
		String tid="0";//checkAccessDoc(req,0,0,"ju",trialid,"","hasaccess","").get("juicios");
		if(Functions.isEmpty(tid))
			return new Object[] {data};

		clause+=userid>0?"userid="+userid:"emailexternaluser='" + email + "'";
		List<SharedDockets> shInfo = sharedDocketsService.getAll("FROM SharedDockets WHERE juicioid="+tid+clause);
		if(shInfo.size()>0){
			// Usuario original y datos del documento compartido
			List<Users> infoUser = userService.getAll("FROM Users WHERE id="+shInfo.get(0).getUsuarioidautoriza());
			data.put("owneruser", infoUser.get(0).getFirst_name() + " " + infoUser.get(0).getLast_name());
			data.put("shareddate", shInfo.get(0).getShareddate());
			data.put("email", shInfo.get(0).getEmailexternaluser());
			data.put("confirmationdate", shInfo.get(0).getConfirmationdate());
			data.put("notificationdate", shInfo.get(0).getNotificationdate());
			List<Companies> userAuth = companiesService
				.getAll("FROM Companies WHERE companyid="+infoUser.get(0).getCompanyid());
			data.put("firmusershare", userAuth.get(0).getCompany());

			// Usuario a quién se le compartió
			clause=(userid==0)?"email='"+email+"'":"id="+userid;
			infoUser = userService.getAll("FROM Users WHERE " + clause);
			if(infoUser.size()>0){
				data.put("username", infoUser.get(0).getFirst_name() + " " + infoUser.get(0).getLast_name());
				userAuth = companiesService.getAll("FROM Companies WHERE companyid="+infoUser.get(0).getCompanyid());
				data.put("companyname", userAuth.get(0).getCompany());
			}else{
				data.put("username","");
				data.put("companyname","");
			}
		}
		return new Object[] {data};
	}

	/** Verifica si el año de una fecha contiene 4 dígitos.
		@param dateStr		Fecha con formato "yyyy-MM-dd"
		@throw Retorna True si es fecha válida o False en caso de fecha vacía o con 2 dígitos.	*/
	private boolean isValidYear(String dateStr) {
		boolean isYear = true;
    	if(dateStr.matches("[0-9]{0,2}\\-.*"))
    		isYear = false;
	    return isYear;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addNewMove")
	public @ResponseBody void addNewMove(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int refid = (req.getParameter("referenceid") == null) ? 0
						: Functions.toInt(req.getParameter("referenceid").trim()),
					actType = (req.getParameter("actType") == null) ? 0
						: Functions.toInt(req.getParameter("actType").trim()),
					movTarget = 0;
				String movement = (req.getParameter("movement") == null) ? "" : req.getParameter("movement").trim(),
					filingdate = (req.getParameter("filingdate") == null) ? ""
						: req.getParameter("filingdate").trim(),
					agreementdate = (req.getParameter("agreementdate") == null) ? ""
						: req.getParameter("agreementdate").trim(),
					notifdate = (req.getParameter("notificationdate") == null) ? ""
						: req.getParameter("notificationdate").trim(),
					obs = (req.getParameter("movObserv") == null) ? "" : req.getParameter("movObserv").trim(),
					act = (req.getParameter("act") == null) ? "" : req.getParameter("act").trim(),
					fini = (req.getParameter("fini") == null) ? "" : req.getParameter("fini").trim(),
					ffin = (req.getParameter("ffin") == null) ? "" : req.getParameter("ffin").trim(),
					tini = (req.getParameter("tini") == null) ? "" : req.getParameter("tini").trim(),
					tfin = (req.getParameter("tfin") == null) ? "" : req.getParameter("tfin").trim(),
					reftype = (req.getParameter("reftype") == null) ? "" : req.getParameter("reftype").trim(),
					cuaderno = (req.getParameter("cuaderno") == null) ? "" : req.getParameter("cuaderno").trim();

				if (Functions.isEmpty(movement) || actType == 0)
					try {
						res.getWriter().write("msg_empty_data");
						return;
					} catch (IOException e) {
						e.printStackTrace();
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

				Date adate = null, ndate = null, fdate = null;
				if (actType == 1 || actType == 2) {
					if (!Functions.isEmpty(filingdate))
						fdate = Functions.parseFecha(filingdate, "yyyy-MM-dd");
					if (!isValidYear(filingdate)) {
						try {
                            res.getWriter().write("msg_invalid_year_format");
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
				} else {
					if (!Functions.isEmpty(agreementdate)) {
						adate = Functions.parseFecha(agreementdate, "yyyy-MM-dd");
						if (!isValidYear(agreementdate)) {
							try {
								res.getWriter().write("msg_invalid_year_format");
								return;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					if (!Functions.isEmpty(notifdate)) {
						ndate = Functions.parseFecha(notifdate, "yyyy-MM-dd");
						if (!isValidYear(notifdate)) {
							try {
								res.getWriter().write("msg_invalid_year_format");
								return;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				Movimientos movto = new Movimientos();
				movto.setMovimiento(movement);
				movto.setCuaderno(cuaderno);
				if(!Functions.isEmpty(fdate))
					movto.setFechapresentacion(fdate);
				if(!Functions.isEmpty(adate))
					movto.setFechaacuerdo(adate);
				if(!Functions.isEmpty(ndate))
					movto.setFechanotificacion(ndate);
				movto.setObservaciones(obs);
				movto.setTipoactuacionid(actType);
				
				String subj = "",titleAct="";
				if(reftype.indexOf("juicioid")>=0){
					movto.setJuicioid(refid);
					titleAct = "Juicio " + refid;
					Menu menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link LIKE '%trialsdashboard%'", null);
					movTarget = menutmp.getMenuid();
				}else if(reftype.indexOf("amparoid")>=0){
					movto.setAmparoid(refid);
					List<Amparos> rowdata=amparosService.getAll("FROM Amparos WHERE amparoid="+refid);
					if(rowdata.get(0).getAmparotipo()==1){
						titleAct = "Amparo " + rowdata.get(0).getAmparo();
						Menu menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link LIKE '%protectiondashboard%'", null);
						movTarget = menutmp.getMenuid();
					}else if(rowdata.get(0).getAmparotipo()==2){
						titleAct = "Amparo indirecto" + rowdata.get(0).getAmparo();
						Menu menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link LIKE '%indprotectiondashboard%'", null);
						movTarget = menutmp.getMenuid();
					}
				}else if(reftype.indexOf("apelacionid")>=0){
					movto.setApelacionid(refid);
					List<Apelaciones> rowdata=apelacionesService.getAll("FROM Apelaciones WHERE apelacionid="+refid);
					titleAct = "Apelación " + rowdata.get(0).getToca();
					Menu menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link LIKE '%appealsdashboard%'", null);
					movTarget = menutmp.getMenuid();
				}else if(reftype.indexOf("recursoid")>=0){
					movto.setRecursoid(refid);
					List<Recursos> rowdata=recursosService.getAll("FROM Recursos WHERE recursoid="+refid);
					titleAct = "Recurso " + rowdata.get(0).getRecurso();
					Menu menutmp = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link LIKE '%resourcedashboard%'", null);
					movTarget = menutmp.getMenuid();
				}
				subj = "Agenda jur\u00eddica - " + titleAct + " - " + act;

				long newMovId = movimientosService.addNewMovto(movto);
				try {
					if (newMovId > 0) {
						// Mueve archivos a destino e inserta rows en db
						String rootPath = Functions.getRootPath();
						String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
						String destinationPath = Functions.addPath(rootPath,
							"doctos" + FileSystems.getDefault().getSeparator() + "Movimientos", true);
						destinationPath = Functions.addPath(destinationPath, "" + newMovId, true);
						File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
						Uploadfiles entidad = null;
						if(paths!=null){
							for (File file : paths) {
								entidad = new Uploadfiles();
								entidad.setCatalogtype(7);
								entidad.setFilename(file.getName());
								entidad.setIdregister(Functions.toInt(newMovId));
								entidad.setPath(file.getAbsolutePath());
								juiciosService.addUploaderFile(entidad);
							}
							FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
						}
						
						JsonObject addedFiles = null;
						List<Movimientos> oldarray = new ArrayList<Movimientos>();
						movto.setMovimientoid(newMovId);
						Gson gson = new Gson();
					    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(movto);
					    UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
					    int userid = (int) userDto.getId();
						long nsucc = notificationsCtrll.addNotificationDB(req, res, movTarget, 7, (int) newMovId, 0, // 7=Movimientos
							(int) userid, 0,	//0=nuevo
							"", (int) userDto.getCompanyid(), addedFiles, movement, oldjson, newdata);
						if(nsucc<1)
							System.err.println("Sin cambios o no se pudo almacenar la notificación del movimiento " + newMovId);
						if (Functions.isEmpty(fini)) {
							res.getWriter().write("msg_data_saved");
							return;
						}
						ECalendar ecalendar = new ECalendar();
						ecalendar.setAppointment(titleAct+" - "+act);// "Juicio "+trial+act
						ecalendar.setDateini(Functions.parseFecha(fini + ' ' + tini, "yyyy-MM-dd HH:mm"));
						ecalendar.setDateend(Functions.parseFecha(ffin + ' ' + tfin, "yyyy-MM-dd HH:mm"));
						ecalendar.setFollowupid(0);
						ecalendar.setAction(actType);
						ecalendar.setStatus(1);//1=Activo
						ecalendar.setUserid(userid);
						ecalendar.setMovimientoid((int) newMovId);
						long succ = eCalendarService.addNewCalendarEvent(ecalendar);

						List<ECalendar> arrayCal = new ArrayList<ECalendar>();
						ecalendar.setMovimientoid((int) succ);
						gson = new Gson();
					    oldjson = gson.toJson(arrayCal);
					    newdata = gson.toJson(ecalendar);
						nsucc = notificationsCtrll.addNotificationDB(req, res, 2, 11,	//2=Agenda; 11=Calendar
							(int) succ, 0, (int) userid, 0,		//0=nuevo
							"", (int) userDto.getCompanyid(), addedFiles, titleAct+" - "+act, oldjson, newdata);
						if(nsucc<1)
							System.err.println("Sin cambios o no se pudo almacenar la notificación de agenda " + succ);
						
						String fromAddress = "notifications@jetaccess.com", password = "NC0st4R1c4#$%NS",
								emailTo = userDto.getEmail(),
								bcc = "",	//bcc = "info@jetaccess.com",
								nomArchivo = "iCalc.ics";
						if (succ < 1) {
							res.getWriter().write("msg_data_saved_no_email");
							return;
						}
						String yearSt = fini.substring(0, 4), daySt = fini.substring(8, 10),
								yearEnd = ffin.substring(0, 4), dayEnd = ffin.substring(8, 10);
						int monthnumSt = Functions.toInt(fini.substring(5, 7)),
								monthnumEnd = Functions.toInt(ffin.substring(5, 7));
						String data = calendarCtrll.writeICSData(calendarCtrll.formatToAMDhm(fini, tini), calendarCtrll.formatToAMDhm(ffin, tfin), fromAddress,
								emailTo, subj,
								act + "\nInicia el " + yearSt + "-" + monthnumSt + "-" + daySt + " a las " + tini
										+ " Hrs. y termina el " + yearEnd + "-" + monthnumEnd + "-" + dayEnd + " a las "
										+ tfin + " Hrs.",
								"", userid, (int) newMovId);

						String msg = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
								+ "<html lang=\"es-MX\"><head><meta charset=\"utf-8\"><meta content=\"text/html; charset=utf-8\" http-equiv=\"content-type\"><meta name=\"language\" content=\"Spanish\"><title>JetAccess-Env&iacute;o de pruba-Configuraci&oacute;n smtp</title>"
								+ "<style type=\"text/css\">.nounderline,.nounderline:hover,.nounderline:active,.nounderline:focus{outline:none;text-decoration:none}.nounderline::-moz-focus-inner{border:0}</style>"
								+ "<style>table,td{border:1px solid #AAAAAA;border-collapse:collapse;}</style><style>@media screen and (max-width:420px){.fs14{font-size:13px}.mmmm{display:none}}</style></head>"
								+ "<body style=\"font-family:Helvetica Neue,Helvetica,Roboto,Arial,sans-serif\">"
								+ "<br><center><img src=\" data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACIAAAAiCAYAAAA6RwvCAAAABHNCSVQICAgIfAhkiAAAABl0RVh0U29mdHdhcmUAZ25vbWUtc2NyZWVu"
								+ "c2hvdO8Dvz4AAAKjSURBVFiF3ZdPSypRGMafkdRQ0YmQAddBBG1sEbhRoRCZ0LVfwIWQ36Gv0ELIz+BOiKmN/3AhEkSUIIhEECFE4KQHhqjeu7h4yuvM3DveRqMHDoxnXuf85n3nvPOMQEQEAw2HQ9zc3AAAXC4Xdnd3jUJ11W638fLyAgDY3t6GKIrGwWQiRVEIAAEgSZLMQnUlSRL/v6IoprEOS7doo1aMTjDGoGka/0"
								+ "1EGI/Hli7+ueqapoExBq/XaxjMpaoqZbNZEkWRp/Srx9raGuXzeRqPx1OlmQJJpVK2Afw5ZFnWB7m9vV0YxGR0u93Zh/Xu7k6/djZqMBjwYw5Cxu3ENn1e89ts328DottHBEFAKBSyZcGHhwfdx0AXxO/34/7+3hYQURShqurM/Lcpzc8Gqdfr2Nvbw/Pz8/JAms0mDg4OUKlUkMlklgPSarUgyzIYY/D7/Tg6Olo8yMXF"
								+ "BZLJJEajEXw+H87Oziw5Oksgx8fHup7k8vISiUQCqqrC6/VCURREIhErl/7wI9Vqlb8VA4HAjJU7PDwkABSNRokxxuevrq5ofX2dAJDH46FarWZqCQOBAF+nWq1at4orK797X6PRQDqdhqZp6HQ62N/fx9PTE1ZXV1EulxGLxaxlwmpGiIjy+TyPicfj3By73W46Pz83zcTfMmIJhIgol8tNmRuXy0Wnp6f/BGEGYnnXFA"
								+ "oFZLNZAIDT6USpVIIsy/OV45MMXbyRBEFAsViEw+FAIpFAOp3+b4i5QCYwJycnXwIw0c9+6c0jXppgMMgnVVWFz+ezZUHGGD+WJOnjxGT7vL6+0ubm5sK+acLhML29vc32ESKi6+tr2trash1iZ2eH+v3+VH8RiKad7Pv7O3q9Hh4fH+dMvrkkScLGxgYEQZianwFZln4BgVNKqSq0hoAAAAAASUVORK5CYII=\" "
								+ "style=\"position:absolute;font-size:38px;left:25px\"><span style=\"font-size:28px;font-weight:bold;color:#333333\">Agenda</span><br><br><br><hr><br><br><table>"
								+ "<caption style=\"color:#333333\"><h3><b>Actividad</b></h3><br><p>" + act
								+ "</p><br></caption><tbody><tr><td style=\"border-bottom-color:#F4F9FF;background-color:#F4F9FF\">"
								+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px\">Fecha de inicio</span></td><td style=\"border-right-color:#FFFFFF;border-bottom-color:#FFFFFF\">"
								+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px\">"
								+ yearSt + "-" + ((monthnumSt <= 9) ? "0" : "") + monthnumSt + "-" + daySt
								+ "</span></td><td style=\"border-bottom-color:#FFFFFF\">"
								+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px;text-align:right\" align=\"right\">"
								+ tini + "&nbsp;Hrs.</span></td></tr><tr><td style=\"background-color:#F4F9FF\">"
								+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px\">Fecha de terminaci&oacute;n</span></td><td style=\"border-right-color:#FFFFFF\">"
  								+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px\">"
								+ yearEnd + "-" + ((monthnumEnd <= 9) ? "0" : "") + monthnumEnd + "-" + dayEnd
								+ "</span></td><td>"
								+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px;text-align:right\" align=\"right\">"
								+ tfin + "&nbsp;Hrs.</span></td></tr></tbody></table></center></body></html>";
						try {
							Smtpmail smtp = new Smtpmail();
							smtp.setAccountmail(fromAddress);
							smtp.setPasswordmail(password);
							smtp.setAliasmail("Agenda");
smtp.setHost("mail.jetaccess.com");
							smtp.setPort(587);
							smtp.setSmtpid(0);
							HtmlEmailSender.SendHtmlEmailStd(emailTo, subj, msg, bcc, nomArchivo, data, smtp);

							res.getWriter().write("msg_data_saved");
							return;
						} catch (MessagingException e) {
							e.printStackTrace();
							res.getWriter().write("msg_data_saved_no_email");
							return;
						}
					} else
						res.getWriter().write("err_record_no_saved");
				} catch (IOException ex) {
					System.out.println("Exception in addNewMove(): " + ex.getMessage());
				}
			}
		}
	}
	
	@RequestMapping(value = "/getMovement")
	public @ResponseBody Object[] getMovement(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Map<String, Object> data = new HashMap<String, Object>();
			int id = (req.getParameter("id") == null) ? 0 : Functions.toInt(req.getParameter("id").trim());

			List<Movimientos> mov = movimientosService.getAll("FROM Movimientos WHERE movimientoid=" + id);
			Long movid = mov.get(0).getMovimientoid();
			List<ECalendar> cal = eCalendarService.getAll("FROM ECalendar WHERE movimientoid=" + movid);
			data.put("mov", mov);
			data.put("cal", cal);
			
			return new Object[] { data };
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/saveEditMove")
	public @ResponseBody void saveEditMove(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int movid = (req.getParameter("id") == null) ? 0
						: Functions.toInt(req.getParameter("id").trim()),
					refid = (req.getParameter("refid") == null) ? 0
						: Functions.toInt(req.getParameter("refid").trim()),
					actType = (req.getParameter("actType") == null) ? 0
						: Functions.toInt(req.getParameter("actType").trim()),
					movTarget = 0;
				String movement = (req.getParameter("movement") == null) ? "" : req.getParameter("movement").trim(),
					filingdate = (req.getParameter("filingdate") == null) ? ""
						: req.getParameter("filingdate").trim(),
					agreementdate = (req.getParameter("agreementdate") == null) ? ""
						: req.getParameter("agreementdate").trim(),
					notifdate = (req.getParameter("notificationdate") == null) ? ""
						: req.getParameter("notificationdate").trim(),
					obs = (req.getParameter("movObserv") == null) ? "" : req.getParameter("movObserv").trim(),
					act = (req.getParameter("act") == null) ? "" : req.getParameter("act").trim(),
					fini = (req.getParameter("fini") == null) ? "" : req.getParameter("fini").trim(),
					ffin = (req.getParameter("ffin") == null) ? "" : req.getParameter("ffin").trim(),
					tini = (req.getParameter("tini") == null) ? "" : req.getParameter("tini").trim(),
					tfin = (req.getParameter("tfin") == null) ? "" : req.getParameter("tfin").trim(),
					reftype = (req.getParameter("reftype") == null) ? "" : req.getParameter("reftype").trim(),
					cuaderno = (req.getParameter("cuaderno") == null) ? "" : req.getParameter("cuaderno").trim();

				if (Functions.isEmpty(movement) || actType == 0)
					try {
						res.getWriter().write("msg_empty_data");
						return;
					} catch (IOException e) {
						e.printStackTrace();
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

				Date adate = null, ndate = null, fdate = null;
				if (actType == 1 || actType == 2) {
					if (!Functions.isEmpty(filingdate))
						fdate = Functions.parseFecha(filingdate, "yyyy-MM-dd");
				} else {
					if (!Functions.isEmpty(agreementdate))
						adate = Functions.parseFecha(agreementdate, "yyyy-MM-dd");
					if (!Functions.isEmpty(notifdate))
						ndate = Functions.parseFecha(notifdate, "yyyy-MM-dd");
				}
				Movimientos movto = new Movimientos();
				movto.setMovimientoid((long) movid);
				movto.setMovimiento(movement);
				movto.setCuaderno(cuaderno);
				if(!Functions.isEmpty(fdate))
					movto.setFechapresentacion(fdate);
				if(!Functions.isEmpty(adate))
					movto.setFechaacuerdo(adate);
				if(!Functions.isEmpty(ndate))
					movto.setFechanotificacion(ndate);
				movto.setObservaciones(obs);
				movto.setTipoactuacionid(actType);

				
				String subj = "",titleAct="";
				if(reftype.indexOf("juicioid")>=0){
					movto.setJuicioid(refid);
					titleAct = "Juicio " + refid;
					movTarget = 33;	//33 = Movimientos de juicios
				}else if(reftype.indexOf("amparoid")>=0){
					movto.setAmparoid(refid);
					List<Amparos> rowdata=amparosService.getAll("FROM Amparos WHERE amparoid="+refid);
					if(rowdata.get(0).getAmparotipo()==1){
						titleAct = "Amparo " + rowdata.get(0).getAmparo();
						movTarget = 35;	//35 = Movimientos de amparos directos
					}else if(rowdata.get(0).getAmparotipo()==2){
						titleAct = "Amparo indirecto " + rowdata.get(0).getAmparo();
						movTarget = 36;	//36 = Movimientos de amparos indirectos
					}
				}else if(reftype.indexOf("apelacionid")>=0){
					movto.setApelacionid(refid);
					List<Apelaciones> rowdata=apelacionesService.getAll("FROM Apelaciones WHERE apelacionid="+refid);
					titleAct = "Apelación " + rowdata.get(0).getToca();
					movTarget = 34;	//34 = Movimientos de apelaciones
				}else if(reftype.indexOf("recursoid")>=0){
					movto.setRecursoid(refid);
					List<Recursos> rowdata=recursosService.getAll("FROM Recursos WHERE recursoid="+refid);
					titleAct = "Recurso " + rowdata.get(0).getRecurso();
					movTarget = 37;	//37 = Movimientos de recursos
				}
				subj = "Agenda jur\u00eddica - " + titleAct + " - " + act;

				movimientosService.updateMovto(movto);
				try {
					// Mueve archivos a destino e inserta rows en db
					String rootPath = Functions.getRootPath();
					String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
					String destinationPath = Functions.addPath(rootPath,
						"doctos" + FileSystems.getDefault().getSeparator() + "Movimientos", true);
					destinationPath = Functions.addPath(destinationPath, "" + movid, true);
					File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
					Uploadfiles entidad = null;
					if (paths != null)
						for (File file : paths) {
							entidad = new Uploadfiles();
							entidad.setCatalogtype(7);
							entidad.setFilename(file.getName());
							entidad.setIdregister(Functions.toInt(movid));
							entidad.setPath(file.getAbsolutePath());
							juiciosService.addUploaderFile(entidad);
						}
					JsonObject addedFiles = null;
					List<Movimientos> oldarray = new ArrayList<Movimientos>();
					movto.setMovimientoid((long) movid);
					Gson gson = new Gson();
				    String oldjson = gson.toJson(oldarray), newdata = gson.toJson(movto);
				    UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				    int userid = (int) userDto.getId();
					long nsucc = notificationsCtrll.addNotificationDB(req, res, movTarget, 7, movid, 0,	// 7=Movimientos
						(int) userid, 1,	//1=editar
						"", (int) userDto.getCompanyid(), addedFiles, movement, oldjson, newdata);
					if(nsucc<1)
						System.err.println("Sin cambios o no se pudo almacenar la notificación del movimiento " + movid);
					ECalendar ecalendar = new ECalendar();
					List<ECalendar> schedules = eCalendarService.getAll("FROM ECalendar WHERE movimientoid=" + movid);
					long idCal = 0;
					String ifCalModified = "";
					if(schedules==null||schedules.size()<1){//Agendar nuevo
						if (Functions.isEmpty(fini)) {
							res.getWriter().write("msg_data_saved");
							return;
						}
						ecalendar.setAppointment(titleAct+" - "+act);// "Juicio "+trial+act
						ecalendar.setDateini(Functions.parseFecha(fini + ' ' + tini, "yyyy-MM-dd HH:mm"));
						ecalendar.setDateend(Functions.parseFecha(ffin + ' ' + tfin, "yyyy-MM-dd HH:mm"));
						ecalendar.setFollowupid(0);
						ecalendar.setAction(actType);
						ecalendar.setStatus(1);//1=Activo
						ecalendar.setUserid(userid);
						ecalendar.setMovimientoid((int) movid);
						idCal = eCalendarService.addNewCalendarEvent(ecalendar);

						List<ECalendar> arrayCal = new ArrayList<ECalendar>();
						ecalendar.setMovimientoid((int) idCal);
						gson = new Gson();
					    oldjson = gson.toJson(arrayCal);
					    newdata = gson.toJson(ecalendar);
						nsucc = notificationsCtrll.addNotificationDB(req, res, 2, 11,	//2=Agenda; 11=Calendar
							(int) idCal, 0, (int) userid, 0,	//0=nuevo
							"", (int) userDto.getCompanyid(), addedFiles, titleAct+" - "+act, oldjson, newdata);
						if(nsucc<1)
							System.err.println("Sin cambios o no se pudo almacenar la notificación de agenda " + idCal);

					}else{
						idCal = schedules.get(0).getCalendarid();
						if (Functions.isEmpty(fini)) {//Eliminar cita
							String finiTmp = schedules.get(0).getDateini()+"",ffinTmp = schedules.get(0).getDateend()+"";
							fini = finiTmp.substring(0,10).trim();
							ffin = ffinTmp.substring(0,10).trim();
							tini = finiTmp.substring(11,16).trim();
							tfin = ffinTmp.substring(11,16).trim();
							eCalendarService.deleteCalendar((int) idCal);
							ifCalModified = "Cita cancelada: ";

						} else {//Actualizar cita
							ecalendar.setCalendarid((int) idCal);
							ecalendar.setAppointment(titleAct+" - "+act);// "Juicio "+trial+act
							ecalendar.setDateini(Functions.parseFecha(fini + ' ' + tini, "yyyy-MM-dd HH:mm"));
							ecalendar.setDateend(Functions.parseFecha(ffin + ' ' + tfin, "yyyy-MM-dd HH:mm"));
							ecalendar.setFollowupid(0);
							ecalendar.setAction(actType);
							ecalendar.setStatus(1);//1=Activo
							ecalendar.setUserid(userid);
							ecalendar.setMovimientoid((int) movid);
							eCalendarService.updateCalendar(ecalendar);
							
							Date usrfini = Functions.parseFecha(fini + ' ' + tini, "yyyy-MM-dd HH:mm"),
								usrffin = Functions.parseFecha(ffin + ' ' + tfin, "yyyy-MM-dd HH:mm");
							Date schIni= Functions.parseFecha(schedules.get(0).getDateini()+"","yyyy-MM-dd HH:mm");
							Date schEnd= Functions.parseFecha(schedules.get(0).getDateend()+"","yyyy-MM-dd HH:mm");
							if(
								(!(schIni).equals(usrfini))
								|| (!(schEnd).equals(usrffin))
								|| (!(schedules.get(0).getAction()+"").equals(actType+"")))
								ifCalModified = "Cambios en cita: ";
							List<ECalendar> arrayCal = new ArrayList<ECalendar>();
							ecalendar.setMovimientoid((int) idCal);
							gson = new Gson();
						    oldjson = gson.toJson(arrayCal);
						    newdata = gson.toJson(ecalendar);
							nsucc = notificationsCtrll.addNotificationDB(req, res, 2, 11,	//2=Agenda; 11=Calendar
								(int) idCal, 0, (int) userid, 1,	//1=editar
								"", (int) userDto.getCompanyid(), addedFiles, titleAct+" - "+act + "[" + ifCalModified + "]", oldjson, newdata);
							if(nsucc<1)
								System.err.println("Sin cambios o no se pudo almacenar la notificación de agenda " + idCal);
						}
					}

					String fromAddress = "notifications@jetaccess.com", password = "NC0st4R1c4#$%NS",
							emailTo = userDto.getEmail(), bcc = "info@jetaccess.com",
							nomArchivo = "Calendar.ics";
					if (idCal < 1) {
						res.getWriter().write("msg_data_saved_no_email");
						return;
					}
					String yearSt = fini.substring(0, 4), daySt = fini.substring(8, 10),
							yearEnd = ffin.substring(0, 4), dayEnd = ffin.substring(8, 10);
					int monthnumSt = Functions.toInt(fini.substring(5, 7)),
							monthnumEnd = Functions.toInt(ffin.substring(5, 7));
					String data = calendarCtrll.writeICSData(calendarCtrll.formatToAMDhm(fini, tini), calendarCtrll.formatToAMDhm(ffin, tfin), fromAddress,
							emailTo, subj,
							act + "\nInicia el " + yearSt + "-" + monthnumSt + "-" + daySt + " a las " + tini
									+ " Hrs. y termina el " + yearEnd + "-" + monthnumEnd + "-" + dayEnd + " a las "
									+ tfin + " Hrs.",
							"", userid, (int) movid);

					String msg = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
							+ "<html lang=\"es-MX\"><head><meta charset=\"utf-8\"><meta content=\"text/html; charset=utf-8\" http-equiv=\"content-type\"><meta name=\"language\" content=\"Spanish\"><title>JetAccess-Env&iacute;o de pruba-Configuraci&oacute;n smtp</title>"
							+ "<style type=\"text/css\">.nounderline,.nounderline:hover,.nounderline:active,.nounderline:focus{outline:none;text-decoration:none}.nounderline::-moz-focus-inner{border:0}</style>"
							+ "<style>table,td{border:1px solid #AAAAAA;border-collapse:collapse;}</style><style>@media screen and (max-width:420px){.fs14{font-size:13px}.mmmm{display:none}}</style></head>"
							+ "<body style=\"font-family:Helvetica Neue,Helvetica,Roboto,Arial,sans-serif\">"
							+ "<br><center><img src=\" data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACIAAAAiCAYAAAA6RwvCAAAABHNCSVQICAgIfAhkiAAAABl0RVh0U29mdHdhcmUAZ25vbWUtc2NyZWVu"
							+ "c2hvdO8Dvz4AAAKjSURBVFiF3ZdPSypRGMafkdRQ0YmQAddBBG1sEbhRoRCZ0LVfwIWQ36Gv0ELIz+BOiKmN/3AhEkSUIIhEECFE4KQHhqjeu7h4yuvM3DveRqMHDoxnXuf85n3nvPOMQEQEAw2HQ9zc3AAAXC4Xdnd3jUJ11W638fLyAgDY3t6GKIrGwWQiRVEIAAEgSZLMQnUlSRL/v6IoprEOS7doo1aMTjDGoGka/0"
							+ "1EGI/Hli7+ueqapoExBq/XaxjMpaoqZbNZEkWRp/Srx9raGuXzeRqPx1OlmQJJpVK2Afw5ZFnWB7m9vV0YxGR0u93Zh/Xu7k6/djZqMBjwYw5Cxu3ENn1e89ts328DottHBEFAKBSyZcGHhwfdx0AXxO/34/7+3hYQURShqurM/Lcpzc8Gqdfr2Nvbw/Pz8/JAms0mDg4OUKlUkMlklgPSarUgyzIYY/D7/Tg6Olo8yMXF"
							+ "BZLJJEajEXw+H87Oziw5Oksgx8fHup7k8vISiUQCqqrC6/VCURREIhErl/7wI9Vqlb8VA4HAjJU7PDwkABSNRokxxuevrq5ofX2dAJDH46FarWZqCQOBAF+nWq1at4orK797X6PRQDqdhqZp6HQ62N/fx9PTE1ZXV1EulxGLxaxlwmpGiIjy+TyPicfj3By73W46Pz83zcTfMmIJhIgol8tNmRuXy0Wnp6f/BGEGYnnXFA"
							+ "oFZLNZAIDT6USpVIIsy/OV45MMXbyRBEFAsViEw+FAIpFAOp3+b4i5QCYwJycnXwIw0c9+6c0jXppgMMgnVVWFz+ezZUHGGD+WJOnjxGT7vL6+0ubm5sK+acLhML29vc32ESKi6+tr2trash1iZ2eH+v3+VH8RiKad7Pv7O3q9Hh4fH+dMvrkkScLGxgYEQZianwFZln4BgVNKqSq0hoAAAAAASUVORK5CYII=\" "
							+ "style=\"position:absolute;font-size:38px;left:25px\"><span style=\"font-size:28px;font-weight:bold;color:#333333\">Agenda</span><br><br><br><hr><br><br><table>"
							+ "<caption style=\"color:#333333\"><h3><b>Actividad</b></h3><br><p>" + act
							+ "</p><br></caption><tbody><tr><td style=\"border-bottom-color:#F4F9FF;background-color:#F4F9FF\">"
							+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px\">Fecha de inicio</span></td><td style=\"border-right-color:#FFFFFF;border-bottom-color:#FFFFFF\">"
							+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px\">"
							+ yearSt + "-" + ((monthnumSt <= 9) ? "0" : "") + monthnumSt + "-" + daySt
							+ "</span></td><td style=\"border-bottom-color:#FFFFFF\">"
							+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px;text-align:right\" align=\"right\">"
							+ tini + "&nbsp;Hrs.</span></td></tr><tr><td style=\"background-color:#F4F9FF\">"
							+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px\">Fecha de terminaci&oacute;n</span></td><td style=\"border-right-color:#FFFFFF\">"
							+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px\">"
							+ yearEnd + "-" + ((monthnumEnd <= 9) ? "0" : "") + monthnumEnd + "-" + dayEnd
							+ "</span></td><td>"
							+ "<span class=\"fs14\" style=\"display:block;Margin-top:10px;Margin-right:10px;Margin-bottom:10px;Margin-left:10px;text-align:right\" align=\"right\">"
							+ tfin + "&nbsp;Hrs.</span></td></tr></tbody></table></center></body></html>";
					try {
						Smtpmail smtp = new Smtpmail();
						smtp.setAccountmail(fromAddress);
						smtp.setPasswordmail(password);
						smtp.setAliasmail("Agenda");
smtp.setHost("mail.jetaccess.com");
						smtp.setPort(587);
						smtp.setSmtpid(0);
						HtmlEmailSender.SendHtmlEmailStd(emailTo, ifCalModified+subj, msg, bcc, nomArchivo, data, smtp);

						res.getWriter().write("msg_data_saved");
						return;
					} catch (MessagingException e) {
						e.printStackTrace();
						res.getWriter().write("msg_data_saved_no_email");
						return;
					}
				} catch (IOException ex) {
					System.out.println("Exception in addNewMove(): " + ex.getMessage());
				}
			}
		}
	}

	@RequestMapping(value = "/deleteMovement")
	public void deleteMovement(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int movid = req.getParameter("id") == "" ? 0 : Functions.toInt(req.getParameter("id").trim());
				movimientosService.deleteMovto((long) movid);
			}
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/createDropZoneImgSN", method = RequestMethod.POST)
	public @ResponseBody String createDropZoneImgSN(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Integer id = Functions.toInt(request.getParameter("id")),
			type = Functions.toInt(request.getParameter("type"));

		String imgPath = "images/socialnetworks/";
		String rootPath = Functions.getRootPath() + imgPath;
		JsonArray jsonArray = new JsonArray();
		JsonObject json = null;
		// Getting uploaded files from the request object
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put("idregister", id);
		parameters.put("catalogtype", type);
		List<Socialnetworks> sn = socialnetworkService.getAll("FROM Socialnetworks WHERE socialnetworkid=" + id);

		File tmpfile = null;
		Path source = Paths.get(sn.get(0).getImageurl());
		if (!Functions.isEmpty(source)) {
			json = new JsonObject();
			json.addProperty("name", id + ".jpg");
			json.addProperty("size", 0);
			json.addProperty("location", sn.get(0).getImageurl());
			jsonArray.add(json);
			String result = new Gson().toJson(jsonArray);
			return result;
		}
		return null;
	}

	@RequestMapping(value = "/deleteTempPath", method = RequestMethod.POST)
	public @ResponseBody boolean deleteTempPath(HttpServletRequest req, HttpServletResponse res){
		String rootPath = Functions.getRootPath(),
			tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
		boolean r = false;
		try {
			FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
			r = true;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return r;
	}

	/** Almacena los datos de las columnas personalizadas.
	@param nomTabla 	Nombre de la tabla a la que hace referencia los datos.
	@param idref		Id de referencia del documento (juicioid, apelacionid, amparoid, etc.)
	@param dataCustCol	Arreglo de datos a registrar, debe estar separado por pipe:<ul>
			<li>Para campos nuevos:		"customcolumnid|nuevo_valor_a_guardar"</li>
			<li>Para campos existentes:	"customcolumnid|valor_modificado_a_guardar|customcolumnvalueid"</li></ul>
			<br>
			En caso de íncluir más campos, separar cada bloque por tilde "~", ejemplo:<br> 
			"colId1|valor_editado1|10<b>~</b>colId2|valor_editato2|11<b>~</b>colId3|valor_nuevo"
	@return	Número de registros almacenados.	*/
	public void saveOrUpdateCustomColumns(String nomTabla, int idref, String dataCustCol){
		String[] allRows = dataCustCol.split("~");
		String idsNoDel = ""; // Ids que no serán eliminados
		for(int r=0; r<allRows.length ;r++){
			String[] dataRow = allRows[r].split("\\|");
			String custcolid = dataRow[0].trim(), query="FROM CustomColumnsValues WHERE ",
				colValue=(dataRow.length>1)?dataRow[1].trim():"";
			Long ccval = Functions.toLong(dataRow[2]);
			query+=dataRow.length==3 && ccval>0?"customcolumnvalueid=" + ccval:
				"UPPER(assignedvalue)='" + colValue.toUpperCase() + "' AND savedon='"+nomTabla
				+ "' AND idreferenced="+idref + " AND customcolumnid="+custcolid;
			List<CustomColumnsValues> existsData = customcolumnsvaluesService.getAll(query);
			CustomColumnsValues ccv=new CustomColumnsValues();
			ccv.setCustomcolumnid(Functions.toInt(custcolid));
			ccv.setAssignedvalue(colValue);
			ccv.setSavedon(nomTabla);
			ccv.setIdreferenced(idref);
			long ccvid = 0;
			if(existsData.size()>0){
				ccvid = existsData.get(0).getCustomcolumnvalueid();
				if(!(existsData.get(0).getAssignedvalue()).equals(colValue)){
					ccv.setCustomcolumnvalueid(ccvid);
					customcolumnsvaluesService.updateCustomColumnsValue(ccv);
				}
			}else
				ccvid = customcolumnsvaluesService.addNewCustomColumnsValue(ccv);
			idsNoDel+=ccvid+",";
		}
		@SuppressWarnings("unused")
		int rowdel = customcolumnsvaluesService.deleteByQuery("DELETE FROM CustomColumnsValues WHERE idreferenced="
			+ idref + " AND customcolumnvalueid NOT IN(" + idsNoDel.replaceAll(",$","") + ")");
	}
}