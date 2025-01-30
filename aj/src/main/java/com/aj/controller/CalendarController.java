package com.aj.controller;

import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aj.model.Amparos;
import com.aj.model.Apelaciones;
import com.aj.model.ECalendar;
import com.aj.model.Menu;
import com.aj.model.Recursos;
import com.aj.model.Users;
import com.aj.service.AccessDbJAService;
import com.aj.service.AmparosService;
import com.aj.service.ApelacionesService;
import com.aj.service.ECalendarService;
import com.aj.service.JuiciosService;
import com.aj.service.MovimientosService;
import com.aj.service.PrivilegesService;
import com.aj.service.RecursosService;
import com.aj.service.UserService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;

@Controller
public class CalendarController {
	@Autowired
	private CommonController commonsCtrll;

	@Autowired
	PrivilegesService privilegesService;

	@Autowired
	public UserService userService;

	@Autowired
	ECalendarService eCalendarService;

	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public ApelacionesService apelacionesService;

	@Autowired
	public MovimientosService movimientosService;

	@Autowired
	public AmparosService amparosService;

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ecalendar")
	public ModelAndView ecalendar(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession(false);
		if(sess!=null){
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				/*UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int role = userDto.getRole();*/
				response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate"); // HTTP 1.1.
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				response.setDateHeader("Expires", 0);
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");

				// Obtiene los privilegios del módulo
				@SuppressWarnings("rawtypes")
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "ecalendar");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);
				return new ModelAndView("ecalendar", forModel);
			}
		}return new ModelAndView("login");
	}

	@RequestMapping(value="/loadCalendarEvents")
	public @ResponseBody Object[] loadCalendarEvents(HttpServletRequest request,HttpServletResponse response){
		HttpSession sess=request.getSession(false);
		List<ECalendar> calendarEvent = null;
		if(sess!=null)
			if (sess.getAttribute("isLogin")!=null&&(((String) sess.getAttribute("isLogin")).equals("yes"))){
				UserDTO userDto=(UserDTO) sess.getAttribute("UserDTO");
				int role=userDto.getRole();
				String query="",userFilter="";
				@SuppressWarnings("unused")
				Map<Integer,String> userNames=new HashMap<Integer,String>();

				//Obtiene los eventos del calendario de acuerdo al rol de usuario y/o niveles inferiores
				if(role!=ROLE_SYSADMIN){
					if(role==ROLE_CJADMIN){
						query="WHERE role<>"+ROLE_SYSADMIN;
					}else{
						query="WHERE companyid="+userDto.getCompanyid();
						if(role!=ROLE_FIRMADMIN)
							query+=" AND id="+userDto.getId();
					}
					List<Users> userList=userService.getAll("FROM Users " + query);
					query="";
					if(userList.size()>0){
						for(Users usr : userList){
							//String lname=Functions.isEmpty(usr.getLast_name())?"":" "+usr.getLast_name();
							//userNames.put((int) usr.getId(),usr.getFirst_name()+lname);
							userFilter+=usr.getId()+",";
						}
						query=" WHERE userid IN(" + userFilter.replaceAll(".$","") + ")";
					}
				}
				calendarEvent=eCalendarService.getAll("FROM ECalendar" + query);
			}
		return new Object[]{calendarEvent};
	}

	@RequestMapping(value="/loadSchedules")
	public @ResponseBody HashMap<String, Object> loadSchedules(HttpServletRequest req, HttpServletResponse res,
	@RequestParam("year") int year){
		HashMap<String, Object> calDates = new HashMap<String, Object>();
		HashMap<String, String> trialsData = commonsCtrll.getAllTrials(req,0,0),
			acclt = commonsCtrll.valAccessClientDocs(req,0,0);
		List<ECalendar> eCal = null;
		String trialIds=trialsData.get("juiciosid"), aplIds="", protIds="", rscIds="",
			whereClause="", ccids=acclt.get("companyclientid"), query = 
			"SELECT e.calendarid, e.appointment, e.dateini, e.dateend, e.comments, e.action, e.status,"
			+ " e.movimientoid, m.movimiento, m.fechapresentacion,"
			+ " m.fechaacuerdo, m.fechanotificacion, m.observaciones, m.cuaderno,"
			+ " m.juicioid, m.amparoid, m.apelacionid, m.recursoid, ta.tipoactuacion,"
			+ " j.juicio, am.amparo, ap.toca, r.recurso, am.amparotipo, cl.client"
+ ", uf.path, uf.filename"
			+ " FROM ECalendar AS e"
			+ " LEFT JOIN Movimientos AS m ON m.movimientoid=e.movimientoid"
			+ " LEFT JOIN Juicios AS j ON j.juicioid=m.juicioid"
			+ " LEFT JOIN Apelaciones AS ap ON ap.apelacionid=m.apelacionid"
			+ " LEFT JOIN Amparos AS am ON am.amparoid=m.amparoid"
			+ " LEFT JOIN Recursos AS r ON r.recursoid=m.recursoid"
			+ " LEFT JOIN TipoActuacion AS ta ON ta.tipoactuacionid=m.tipoactuacionid"
			+ " LEFT JOIN Companyclients AS cc ON cc.companyclientid ="
			+ 	" COALESCE(j.companyclientid, am.companyclientid)"
			+ " LEFT JOIN Clients AS cl ON cl.clientid=cc.clientid"
			+ " LEFT JOIN Uploadfiles AS uf ON uf.idregister=m.movimientoid"
			+ " WHERE year(e.dateini) BETWEEN "+(year-1) 
			+ " AND " + (year+1)
			+ " AND e.movimientoid IN"
			+ "(SELECT DISTINCT(m.movimientoid) FROM Movimientos AS m";

		//Juicios
		if(!Functions.isEmpty(trialsData.get("juiciosid"))){
			query += " LEFT JOIN Juicios AS j ON m.juicioid=j.juicioid";
			whereClause += " OR (j.juicioid IN (" + trialsData.get("juiciosid")
				+ ") OR j.companyclientid=" + ccids + ")";
		}

		//Apelaciones
		List<Apelaciones> aplTmp = apelacionesService
			.getAll("SELECT apelacionid FROM Apelaciones WHERE juicioid IN(" + trialIds + ")");
		if(aplTmp.size()>0){
			query += " LEFT JOIN Apelaciones AS ap ON ap.apelacionid=m.apelacionid";
			aplIds+=(Functions.toStr(aplTmp)).replaceAll("[^0-9,]","");
			whereClause += " OR m.apelacionid IN(" + aplIds + ")";
		}

		//Amparos
		List<Amparos> ampTmp = amparosService
			.getAll("SELECT amparoid FROM Amparos WHERE companyclientid IN("
				+ "SELECT DISTINCT(companyclientid) FROM Companyclients WHERE companyid="
				+ acclt.get("companyid") + ")");
		if(ampTmp.size()>0){
			query += " LEFT JOIN Amparos AS am ON am.amparoid=m.amparoid";
			protIds = (Functions.toStr(ampTmp)).replaceAll("[^0-9,]","");
			whereClause += " OR m.amparoid IN(" + protIds + ")";
		}

		//Recursos
		if(!Functions.isEmpty(protIds)){
			List<Recursos> rscTmp = recursosService
				.getAll("SELECT recursoid FROM Recursos WHERE tipoorigenid IN(" + protIds + ")" );
			if(rscTmp.size()>0){
				query += " LEFT JOIN Recursos AS r ON r.recursoid=m.recursoid";
				rscIds = (Functions.toStr(rscTmp)).replaceAll("[^0-9,]","");
				whereClause += " OR m.recursoid IN(" + rscIds + ")";
			}
		}
		query += " WHERE " + (whereClause.replaceAll("^ OR ","")) + ")";
		eCal = eCalendarService.getAll(query);
		calDates.put("calDates", eCal);
		return calDates;
	}

	/** Convierte un formato de 12 a 24 horas formato
	@param time			Valor con cualquiera de estos formatos de hora "HH:mm aa", "HH:mm", "utc"
	@param typeTime		"24h" o ""
	@return formato "HHmm00Z"	*/
	public String conv12hrTo24hr(String time, String typeTime) {
		String[] varT1 = time.split(":");
		String[] varT2 = varT1[1].split(" ");
		if (varT2[1].contains("AM")) {
			if (varT1[0].contains("12")) {
				varT1[0] = "00";
			}
		} else {
			String nHr = varT1[0];
			if ((Functions.toInt(nHr) >= 1) && (Functions.toInt(nHr) < 12)) {
				Integer newHr = Functions.toInt(nHr) + 12;
				nHr = Functions.toStr(newHr);
				varT1[0] = nHr;
			}
		}
		if (typeTime.equals("utc")) {
			return varT1[0] + varT2[0] + "00";
		}
		return varT1[0] + ":" + varT2[0];
	}

	/** Convierte la fecha y hora a formato estandar para base de datos.
	<br>Reglas: <br>
		1) El formato deberá ser año-mes-dia, de lo contrario:<ul>
			<li>1.1) Si orden es cambiado, se deberá cambiar el orden del array "varD".</li>
			<li>1.2) Si se cambia el símbolo "-", deberá hacerce el cambio en "chD".</li><br>
		2) El número de dígitos es indistinto, excepto el año que debe ser de 4.<br>
		3) Si la fecha contiene otro dato (como hora o UTF), serán omitidos.<br>
		4) La hora puede ser en formato 12 horas (am/pm separado por un espacio) ó 24 horas<ul>
			<li>4.1 Tanto en la hora como minutos, son indistintos el número de decimales.</li><br>
		5) Sí la fecha o la hora no son indicados, se tomará del sistema.
		@param date		Fecha
		@param time		Hora	*/
	public String formatToAMDhm(String date, String time) {
		String chD = "-";
		if ((time.equals("")) || (time.equals("undefined"))) {
			Date now = new Date();
			time = now.getHours() + ":" + now.getMinutes();
		}
		if ((date.equals("")) || (date.equals("undefined"))) {
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			date = calendar.get(Calendar.YEAR) + chD + (calendar.get(Calendar.MONTH) + 1) + chD
					+ calendar.get(Calendar.DAY_OF_MONTH);
		}
		if (!time.contains("M")) {
			String am_pm = (Functions.toInt(time.substring(0, time.indexOf(":"))) < 12) ? " AM" : " PM";
			time = time + am_pm;
		}
		String[] varT1 = time.split(":");
		String[] varT2 = varT1[1].split(" ");
		if (Functions.toInt(varT1[0]) > 12) {
			varT1[0] = String.valueOf(Functions.toInt(varT1[0]) - 12);
			varT2[1] = "PM";
		} else if (Functions.toInt(varT1[0]) == 0) {
			varT1[0] = "12";
			varT2[1] = "AM";
		}
		if ((Functions.toInt(varT1[0]) < 10) && (varT1[0].length() < 2)) {
			varT1[0] = "0" + varT1[0];
		} // Corrección de formato de horas
		if ((Functions.toInt(varT2[0]) < 10) && (varT2[0].length() < 2)) {
			varT2[0] = "0" + varT2[0];
		} // Corrección de formato de minutos
		if (date.contains(" ")) { // Corrección de fecha
			date = date.substring(0, (date.indexOf(" ")));
		}
		String[] varD = date.split(chD);
		String m = (Functions.toInt(varD[1]) < 10 && (varD[1].length() < 2)) ? "0" + varD[1] : varD[1];
		String d = (Functions.toInt(varD[2]) < 10 && (varD[2].length() < 2)) ? "0" + varD[2] : varD[2];
		String fixedDate = varD[0] + "/" + m + "/" + d + " " + varT1[0] + ":" + varT2[0] + " " + varT2[1];
		return fixedDate;
	}

	/**
	Crea y guarda el archivo ".ics"<br>
	Los formatos de fecha deberán estar previamente formateados en
	"dd/MM/aaaa HH:mm ap" ZoneId tzName=fechaIni.getZone(); ZoneOffset
	tzNum=getTimezoneOffset(); 26-9-2018 10-30-40	*/
	public String writeICSData(String startDate, String endDate, String emailFrom, String emailTo,
			String strDescripcion, String strSummary, String dateAddress, int clientId, int followupid) {
		String cr = "\r\n";// salto de linea
		String xTemp = "", t = "", data = "BEGIN:VCALENDAR" + cr;
		if (strDescripcion == null) {
			strDescripcion = "";
		}
		if (strSummary == null) {
			strSummary = "Temporal";
		}
		data += "PRODID:-//Contacto Jurídico//ES" + cr;
		xTemp = formatToAMDhm("", "");
		t = conv12hrTo24hr(xTemp.substring(11, 19), "utc");
		data += "DTSTAMP:" + xTemp.substring(0, 4) + xTemp.substring(5, 7) + xTemp.substring(8, 10) + "T" + t + cr;
		data += fncLine75c("UID:",
				startDate.replaceAll("/", "dh").replaceAll(":", "dd").replaceAll("\u0020", "X") + endDate.replaceAll("/", "dh")
						.replaceAll(":", "dd").replaceAll("\u0020", "_").substring(5, endDate.length() + 2)
						+ "@jetaccess.com")
				+ cr;
		data += "VERSION:2.0" + cr;
		data += "CALSCALE:GREGORIAN" + cr;
		data += "METHOD:REQUEST" + cr;

		data += "BEGIN:VTIMEZONE" + cr;
		data += "TZID:America/Mexico_City" + cr; // Configurable
		data += "X-LIC-LOCATION:America/Mexico_City" + cr; // Configurable

		data += "BEGIN:DAYLIGHT" + cr;
		data += "TZOFFSETFROM:-0600" + cr; // Configurable
		data += "TZOFFSETTO:-6000" + cr; // Configurable
		data += "TZNAME:CDT" + cr;
		data += "DTSTART:" + "19700405T020000Z" + cr; // Configurable
		data += "RRULE:" + "FREQ=YEARLY;BYMONTH=4;BYDAY=1SU" + cr; // Configurable de acuerdo al país
		data += "END:DAYLIGHT" + cr;

		data += "BEGIN:STANDARD" + cr;
		data += "TZOFFSETFROM:-0600" + cr;
		data += "TZOFFSETTO:-0600" + cr;
		data += "TZNAME:CST" + cr;
		data += "DTSTART:" + "19701025T020000Z" + cr; // Configurable
		data += "RRULE:" + "FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU" + cr; // Configurable
		data += "END:STANDARD" + cr;

		data += "END:VTIMEZONE" + cr;

		data += "BEGIN:VEVENT" + cr;
		xTemp = formatToAMDhm("", "");
		t = conv12hrTo24hr(xTemp.substring(11, 19), "utc");
		data += "DTSTAMP:" + xTemp.substring(0, 4) + xTemp.substring(5, 7) + xTemp.substring(8, 10) + "T" + t + cr;
		data += "UID:AJ-c" + String.valueOf(clientId) + "l" + String.valueOf(followupid) + emailFrom + cr;
		data += "CREATED:" + xTemp.substring(0, 4) + xTemp.substring(5, 7) + xTemp.substring(8, 10) + "T" + t + cr;

		data += fncLine75c("LOCATION:", dateAddress) + cr; // Configurable
		data += fncLine75c("DESCRIPTION:", strDescripcion) + cr;
		data += "CLASS:PRIVATE" + cr;
		t = conv12hrTo24hr(startDate.substring(11, 19), "utc");
		data += "DTSTART:" + startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10) + "T"
				+ t + cr;
		t = conv12hrTo24hr(endDate.substring(11, 19), "utc");
		data += "DTEND:" + endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10) + "T" + t + cr;
		xTemp = "EMAIL=" + emailFrom + ";CUTYPE=INDIVIDUAL" + ";ROLE=CHAR" + ";PARTSTAT=ACCEPTED";
		data += fncLine75c("ATTENDEE:", xTemp) + cr;
		xTemp = "EMAIL=" + emailTo + ";CUTYPE=INDIVIDUAL" + ";ROLE=REQ-PARTICIPANT" + ";PARTSTAT=NEEDS-ACTION"
				+ ";RSVP=TRUE:mailto:" + emailFrom;
		xTemp = "SENT-BY=mailto:" + emailFrom;
		data += fncLine75c("ORGANIZER;", xTemp) + cr;
		data += "TRANSP:OPAQUE" + cr;
		data += fncLine75c("SUMMARY:", strSummary) + cr;
		data += "END:VEVENT" + cr;

		data += "END:VCALENDAR";
		return data;
	}

	public String fncLine75c(String objetoICS, String texto) {
		texto = texto.trim();
		String splitProp = "", sp = "\u0020";
		Integer l = 0;
		if (texto.length() + objetoICS.length() > 73) {
			splitProp = objetoICS + texto.substring(0, 73 - objetoICS.length()) + "\r\n";
			l = 73 - objetoICS.length();
		} else {
			splitProp = objetoICS;
			sp = "";
		}
		while (l + 72 < texto.length()) {
			splitProp += sp + texto.substring(l, l + 72) + "\r\n";
			l += 72;
		}
		return splitProp + sp + texto.substring(l, texto.length());
	}
}