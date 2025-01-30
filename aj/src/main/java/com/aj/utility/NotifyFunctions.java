package com.aj.utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import com.aj.model.Notifications;
import com.aj.service.AccessDbJAService;
import com.aj.service.ClientsService;
import com.aj.service.CompanyclientsService;
import com.aj.service.NotificationsService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NotifyFunctions{

	@Autowired
	public NotificationsService notificationsService;

	@Autowired
	public CompanyclientsService companyclientsService;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	public AccessDbJAService daoJaService;

	public final int ROLE_SYSADMIN= 1;
	public final int ROLE_CJADMIN = 2;
	public final int ROLE_FIRMADMIN=3;

	/*@RequestMapping(value = "/updateNotifications")
	@ResponseBody */
	public Object[] updateNotifications(HttpServletRequest req) {
		HttpSession sess = req.getSession(false);
		List<Notifications> notifications = null;
		Map<String, Object> notify = new HashMap<String, Object>();
		if(sess != null){
			//Notificaciones del usuario actual
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			String query="FROM Notifications WHERE confirmations LIKE '%\"" + userDto.getId()
				+ "\":{\"notified\":\"\",%' ORDER BY notificationid ASC", userids = "";
			notifications = notificationsService.getAll(query);

			//Obtiene nombres de usuarios de acuerdo a las notificaciones
			for(int n=0;n<notifications.size(); n++){
				Gson gson = new Gson();
			    String notifjson = gson.toJson(notifications.get(n).getConfirmations());
			    notifjson = notifjson.replaceAll("^\\[?(.*)\\]?$","$1").replaceAll("^\"?","")
		    		.replaceAll("\"?$","").replaceAll("\\\\","");
				
			    //Convierte el Model a JSON
				JsonParser parserObj = new JsonParser();
// {"70":{"confirmed":"c-setenta","notified":"n-LXX"},"162":{"confirmed":"c-ciento-sesentaydos","notified":"n-CLXII"},"69":{"confirmed":"","notified":""},"68":{"confirmed":"","notified":""}}
			    JsonObject jsonObj = (JsonObject) parserObj.parse(notifjson);  
				System.out.println("jsonObj-a\n"+jsonObj);

				//Obtiene todos los Keys
				List<String> keys = jsonObj.entrySet()
		    	    .stream()
		    	    .map(i -> i.getKey())
		    	    .collect(Collectors.toCollection(ArrayList::new));
		    	keys.forEach(System.out::println);
		    	System.out.println(keys.get(0));
		    	System.out.println(keys);

		    	//Separa los elementos (registros)
		        JsonElement elementObj = parserObj.parse(notifjson);
		        JsonObject allobj = elementObj.getAsJsonObject();
		        Set<Map.Entry<String, JsonElement>> entries = allobj.entrySet();
		        for(Map.Entry<String, JsonElement> entry: entries) {
		        	String uid = entry.getKey();
		        	System.out.println("uid:"+uid);
		        	JsonElement notifdates = entry.getValue();//Confirmaciones
		        	JsonObject usrdatesobj = notifdates.getAsJsonObject();
					JsonElement confirmdate = usrdatesobj.get("notified");
			        System.out.println("confirmdate:"+confirmdate);
			        JsonElement notifdate = usrdatesobj.get("confirmed");
			        System.out.println("notifdate:"+notifdate);

			        if(uid.equals("69")){
			        	JsonObject updateval = new JsonObject();
			        	//updateval.remove("confirmdate");
			        	updateval.addProperty("confirmdate", "c-sesentaynueve");
			        	entry.setValue(updateval);
			        }
		        }
		        
		      //Separa los elementos (registros)
		        JsonElement elementObj2 = parserObj.parse(notifjson);
		        JsonObject allobj2 = elementObj.getAsJsonObject();
		        Set<Map.Entry<String, JsonElement>> entries2 = allobj.entrySet();
		        for(Map.Entry<String, JsonElement> entry2: entries2) {
		        	String uid = entry2.getKey();
		        	System.out.println("uid:"+uid);
		        	JsonElement notifdates = entry2.getValue();//Confirmaciones
		        	JsonObject usrdatesobj = notifdates.getAsJsonObject();
		        	JsonElement confirmdate = usrdatesobj.get("notified");
			        System.out.println("confirmdate:"+confirmdate);
			        JsonElement notifdate = usrdatesobj.get("confirmed");
			        System.out.println("notifdate:"+notifdate);
			        if(uid.equals("69")){
			        	System.err.println("Mirar arriba");
			        }
		        }
			}
		}
		return new Object[] {notify};
	}
}