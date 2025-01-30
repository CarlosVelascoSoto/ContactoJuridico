package com.aj.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aj.model.Menu;
import com.aj.service.AccessDbJAService;
import com.aj.service.PrivilegesService;
import com.aj.service.RolesService;
import com.aj.service.UserService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;
import com.ecwid.maleorang.MailchimpClient;
import com.ecwid.maleorang.MailchimpException;
import com.ecwid.maleorang.MailchimpObject;
import com.ecwid.maleorang.method.v3_0.campaigns.CampaignActionMethod;
import com.ecwid.maleorang.method.v3_0.campaigns.CampaignInfo;
import com.ecwid.maleorang.method.v3_0.campaigns.CampaignInfo.Type;
import com.ecwid.maleorang.method.v3_0.campaigns.DeleteCampaignMethod;
import com.ecwid.maleorang.method.v3_0.campaigns.EditCampaignMethod;
import com.ecwid.maleorang.method.v3_0.campaigns.content.ContentInfo;
import com.ecwid.maleorang.method.v3_0.campaigns.content.SetCampaignContentMethod;
import com.ecwid.maleorang.method.v3_0.lists.CampaignDefaultsInfo;
import com.ecwid.maleorang.method.v3_0.lists.ContactInfo;
import com.ecwid.maleorang.method.v3_0.lists.EditListMethod;
import com.ecwid.maleorang.method.v3_0.lists.ListInfo;
import com.ecwid.maleorang.method.v3_0.lists.members.DeleteMemberMethod;
import com.ecwid.maleorang.method.v3_0.lists.members.EditMemberMethod;
import com.ecwid.maleorang.method.v3_0.lists.members.MemberInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import kotlin.collections.EmptyList;

@Controller
@ComponentScan("com.aj")
public class CampaignsController{
	@Autowired
	RolesService rolesService;

	@Autowired
	UserService userService;

	@Autowired
	PrivilegesService privilegesService;
	
	@Autowired
	public AccessDbJAService dao;

	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	//***MailChimp
	private static final String API_KEY="6af80a62e9971b32c97082c9fb05fadb-us7";
	private static final String DC = "us7"; //API PREFIX
	private static final String URLAPI=".api.mailchimp.com/3.0";

	@RequestMapping(value="/testMailChimp",method={RequestMethod.POST,RequestMethod.POST})
	public String testMailChimp(HttpServletRequest request,HttpServletResponse response){
		String succ="false";

		

/*			String emailToSend=request.getParameter("emails")==null?"":request.getParameter("emails").trim();
			String campaign=request.getParameter("campaign")==null?"":request.getParameter("campaign").trim();
			

// Envio de TEST
			emailToSend="gustavo.santoscoy@jetaccess.com";
			String data="{"+
			  "\"test_emails\":[\""+emailToSend+"\"],"+
			  "\"send_type\":\"html\""+
			  "}";

			List<String> resp=curlChimp("POST","campaigns/"+campaign+"/actions/test",data);
*/
		String lista="0a56893228";
/* Alta de campo correcto

		String data="{"+
		  "\"tag\":\"Birthday\","+
		  "\"name\":\"BIRTHDAY\","+
		  "\"type\":\"url\","+
		  "\"public\":true}";
		
	List<String> resp = curlChimp("POST","lists/"+lista+"/merge-fields",data);
		  
		  data="{"+
				  "\"tag\":\"Website\","+
				  "\"name\":\"WEBSITE\","+
				  "\"type\":\"text\","

		//Accepted values: text, number, address, phone, date, url, imageurl, radio, dropdown, birthday, zip."
*/
		
		String data="{"+
				  "\"tag\":\"Status\","+
				  "\"name\":\"STATUS\","+
				  "\"type\":\"radio\","+
				  "\"public\":true},"+
				  "\"options\":[{"+
				      "\"choices\":[\"subscribed\",\"unsubscribed\",\"non-subscribed\",\"pending\",\"cleaned\"]"+
				  "}]}";
		
		List<String> resp = curlChimp("POST","lists/"+lista+"/merge-fields",data);
		System.out.println("Merge id: "+ Functions.toStr(resp));
		if(resp.indexOf("400")<0)
			succ="true";

/*			String data="{"+
			  "\"name\":\"ADDRESS\","+
			  "\"type\":\"text\","+
			  "\"name\":\"PHONE\","+
			  "\"type\":\"phone\","+
			  "\"options\":[{"+
			      "\"phone_format\":\"international\"}],"+
			  "\"name\":\"BIRTHDAY\","+
			  "\"type\":\"birthday\","+
			  "\"options\":[{"+
			      "\"date_format\":\"MM/dd\"}],"+
			  "\"name\":\"WEBSITE\","+
			  "\"type\":\"website\","+
			  "\"name\":\"STATUS\","+
			  "\"type\":\"radio\","+
			  "\"options\":[{"+
			      "\"choices\":[\"subscribed\",\"unsubscribed\",\"non-subscribed\",\"pending\",\"cleaned\"]"+
			  "}]}";*/
		
        return succ;
	}

	@RequestMapping(value="/campaigns",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView campaigns(HttpServletRequest req,HttpServletResponse res){
		HttpSession sess=req.getSession(false);
		if(sess!=null)
			if(sess.getAttribute("isLogin")!=null&&(((String) sess.getAttribute("isLogin")).equals("yes"))){
				res.setHeader("Cache-Control","no-cache,no-store,must-revalidate");//HTTP 1.1
				res.setHeader("Pragma","no-cache");//HTTP 1.0
				res.setDateHeader("Expires",0);
				res.setContentType("text/html; charset=UTF-8");
				res.setCharacterEncoding("UTF-8");
				UserDTO userDto=(UserDTO) sess.getAttribute("UserDTO");
				boolean toAdd=false;
				int role=userDto.getRole(),userid=(int) userDto.getId(),camp4field=0,dateType=0;
				String toDate=Functions.isEmpty(req.getParameter("toDate"))?"":req.getParameter("toDate").trim(),
					fromDate=Functions.isEmpty(req.getParameter("fromDate"))?"":req.getParameter("fromDate").trim(),
					filterdateby=Functions.isEmpty(req.getParameter("filterdateby"))?"":req.getParameter("filterdateby").trim();

				//Obtiene los privilegios del módulo
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "campaigns");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String,Object> forModel=new HashMap<String,Object>();
				forModel.put("menu", menu);
				
				List<String> list = curlChimp("GET", "campaigns", "title, id, create_time, send_time");
				List<String> resumeList = new ArrayList<String>();
				Date f=null,t=null;
				try{
					if(toDate.equals(""))toDate=Functions.formateaFecha(new Date(),"yyyy-MM-dd");
					if(fromDate.equals(""))fromDate="1901-01-01";
					f=new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
					t=new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
					dateType=filterdateby=="s"?3:2;//Búsqueda por "created" (2o.campo) o "sent" (3er.campo)
				}catch (ParseException e1){
					e1.printStackTrace();
				}
				for(int i=0; i<list.size(); i++){
					camp4field++;
					if(camp4field==1){
						try{
							String[] data=list.get(i+dateType).split("\\|");
							String dCreatedSent=data[1].substring(0,10);
							Date d=new SimpleDateFormat("yyyy-MM-dd").parse(dCreatedSent);
							if(d.compareTo(f)>=0 && d.compareTo(t)<=0)
								toAdd=true;
							else
								toAdd=false;
						}catch (ParseException e){
							e.printStackTrace();
						}
					}else if(camp4field==4){
						camp4field=0;//Cada registro mailchimp se mostrarán 4 campos, el contador se resetea para indicar un nuevo registro
					}
					if(toAdd){
						String[] items=list.get(i).split("\\|");
						String value=(items.length==1)?"":items[1];
						resumeList.add(value);
					}
				}
/*				List<String> listAuto = getDataMailChimp("automations", "", "title, id, create_time, send_time");
				for(int i=0; i<listAuto.size(); i++){
					String[] items=listAuto.get(i).split("\\|");
					String value=(items.length==1)?"":items[1];
					resumeList.add(value);
				}*/
				forModel.put("campData",resumeList);
				return new ModelAndView("campaigns",forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value="/createCampaign",method={RequestMethod.POST,RequestMethod.POST})
	public boolean createCampaign(HttpServletRequest request,HttpServletResponse response){
		String title=request.getParameter("title")==null?"":request.getParameter("title").trim(),
			subject_line=request.getParameter("subject_line")==null?"":request.getParameter("subject_line").trim(),
			from_name=request.getParameter("from_name")==null?"":request.getParameter("from_name").trim(),
			reply_to=request.getParameter("reply_to")==null?"":request.getParameter("reply_to").trim(),
			list_id=request.getParameter("list_id")==null?"":request.getParameter("list_id").trim();
		int template_id=request.getParameter("template_id")==""?0:Functions.toInt(request.getParameter("template_id").trim());
		boolean succ=true;
		try{
			MailchimpClient client = new MailchimpClient(API_KEY);
		    EditCampaignMethod.Create campaign = new EditCampaignMethod.Create();
		    campaign.type=Type.REGULAR;
		    campaign.settings = new CampaignInfo.SettingsInfo();
		    campaign.settings.title=title;
		    campaign.settings.subject_line=subject_line;
		    campaign.settings.from_name=from_name;
		    campaign.settings.reply_to=reply_to;
		    campaign.settings.template_id=template_id;
		    
		    // Configura la lista para recibir
		    CampaignInfo.RecipientsInfo recipientsInfo = new CampaignInfo.RecipientsInfo();
		    recipientsInfo.list_id=list_id;
		    
		    campaign.recipients = recipientsInfo;	//Unifica los datos
		    CampaignInfo campaignInfo = client.execute(campaign);	//Crear campaña
		    System.out.println("Id de la nueva campaña: "+campaignInfo.id);
		}catch (Exception e){
			e.printStackTrace();
			succ=false;
		}return succ;
	}
	
	@ResponseBody
	@RequestMapping(value="/updateCampaign",method={RequestMethod.POST,RequestMethod.POST})
	public boolean updateCampaign(HttpServletRequest request,HttpServletResponse response){
		int template_id=request.getParameter("template_id")==""?0:Functions.toInt(request.getParameter("template_id").trim());
		String campaignid=request.getParameter("campaignid")==""?"":request.getParameter("campaignid").trim(),
			subject_line=request.getParameter("subject_line")==null?"":request.getParameter("subject_line").trim(),
			from_name=request.getParameter("from_name")==null?"":request.getParameter("from_name").trim(),
			reply_to=request.getParameter("reply_to")==null?"":request.getParameter("reply_to").trim(),
			list_id=request.getParameter("list_id")==null?"":request.getParameter("list_id").trim(),
			campaignType=request.getParameter("type")==null?"":request.getParameter("type").trim();
		boolean succ=true;
		
		//Validar datos, que no esten nulos y que sean válidos
		
		try{
			String data="{\"recipients\":"+
				"{\"list_id\":\""+list_id+"\"},"+
				"\"type\":\""+campaignType+"\","+
				"\"settings\":"+
				"{\"subject_line\":\""+subject_line+"\","+
				"\"reply_to\":\""+reply_to+"\","+
				"\"from_name\":\""+from_name+"\","+
				"\"template_id\":"+template_id+"}"+
			"}";
			List<String> resp = curlChimp("PATCH","campaigns/"+campaignid, data);
			System.out.println(resp);
		}catch (Exception e){
			e.printStackTrace();
			succ=false;
		}return succ;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteCampaign",method={RequestMethod.POST,RequestMethod.POST})
	public boolean deleteCampaign(HttpServletRequest request,HttpServletResponse response){
		String campaignid=request.getParameter("campaignid")==""?"":request.getParameter("campaignid").trim();
		boolean succ=true;
		try{
			MailchimpClient client = new MailchimpClient(API_KEY);
			DeleteCampaignMethod delcamp = new DeleteCampaignMethod(campaignid);
			CampaignInfo campaignInfo = (CampaignInfo) client.execute(delcamp);
			
			//curlChimp("DELETE","campaigns/"+campaignid,"");
			System.out.println("Eliminada: " + campaignInfo);
		}catch (Exception e){
			e.printStackTrace();
			succ=false;
		}return succ;
	}
	
	@ResponseBody
	@RequestMapping(value="/createList",method={RequestMethod.POST,RequestMethod.POST})
	public boolean createList(HttpServletRequest request,HttpServletResponse response){
		String name=request.getParameter("name")==null?"":request.getParameter("name").trim(),
			permission_reminder=request.getParameter("permission")==null?"":request.getParameter("permission").trim(),
			company = request.getParameter("company")==null?"":request.getParameter("company").trim(),
			city = request.getParameter("city")==null?"":request.getParameter("city").trim(),
			address=request.getParameter("address")==null?"":request.getParameter("address").trim(),
			country =request.getParameter("country")==null?"":request.getParameter("country").trim(),
			zip = request.getParameter("zip")==null?"":request.getParameter("zip").trim(),
			state=request.getParameter("state")==null?"":request.getParameter("state").trim(),
			from_name =request.getParameter("from_name")==null?"":request.getParameter("from_name").trim(),
			from_email=request.getParameter("from_email")==null?"":request.getParameter("from_email").trim(),
			language = request.getParameter("language")==null?"":request.getParameter("language").trim(),
			subject = request.getParameter("subject")==null?"":request.getParameter("subject").trim();
		boolean succ=true;
		try{
	        MailchimpClient client = new MailchimpClient(API_KEY);
	        // create new CreateList
	        EditListMethod.Create editListMethod = new EditListMethod.Create();
	        editListMethod.name = name;
	        editListMethod.permission_reminder = permission_reminder;
	        editListMethod.email_type_option = true;
	        editListMethod.visibility = "prv";

	        // Create contactInfo
	        ContactInfo contactInfo = new ContactInfo();
	        contactInfo.company = company;
	        contactInfo.city = city;
	        contactInfo.address1 = address;
	        contactInfo.country = country;
	        contactInfo.zip = zip;
	        contactInfo.state = state;

	        // CampaignDefaultInfo
	        CampaignDefaultsInfo campaignDefaultsInfo = new CampaignDefaultsInfo();
	        campaignDefaultsInfo.from_name = from_name;
	        campaignDefaultsInfo.from_email = from_email;
	        campaignDefaultsInfo.language = language;
	        campaignDefaultsInfo.subject = subject;

	        // Implement
	        editListMethod.contact = contactInfo;
	        editListMethod.campaign_defaults = campaignDefaultsInfo;

	        // Run"
	        ListInfo listInfo = client.execute(editListMethod);
	        System.out.println("Nuevo id List: " + listInfo.id);

	        // Merge-fields
	        String data="{"+
			  "\"name\":\"ADDRESS\","+
			  "\"type\":\"text\","+
			  "\"name\":\"PHONE\","+
			  "\"type\":\"phone\","+
			  "\"options\":[{"+
			      "\"phone_format\":\"international\"}],"+
			  "\"name\":\"BIRTHDAY\","+
			  "\"type\":\"birthday\","+
			  "\"options\":[{"+
			      "\"date_format\":\"MM/dd\"}],"+
			  "\"name\":\"WEBSITE\","+
			  "\"type\":\"website\","+
			  "\"name\":\"STATUS\","+
			  "\"type\":\"radio\","+
			  "\"options\":[{"+
			      "\"choices\":[\"subscribed\",\"unsubscribed\",\"non-subscribed\",\"pending\",\"cleaned\"]"+
			  "}]}";
	        curlChimp("POST","lists/"+listInfo.id+"/merge-fields",data);
		}catch (Exception e){
			e.printStackTrace();
			succ=false;
		}
		return succ;
	}

	@ResponseBody
	@RequestMapping(value="/addTemplate",method={RequestMethod.POST,RequestMethod.POST})
	public String addTemplate(HttpServletRequest request,HttpServletResponse response){
		String templatename=request.getParameter("templatename")==""?"":request.getParameter("templatename").trim(),
			templatecode=request.getParameter("templatecode")==null?"":request.getParameter("templatecode").trim();
		String succ="1";
		if(templatename.equals("")){
			succ="n";
		}else if(templatecode.equals("")){
			succ="c";
		}else{
			try{
				String jsonstr="{"+
					"\"name\":\""+templatename+"\","+
					"\"html\":\""+templatecode+"\"}";
				List<String> resp = curlChimp("POST","templates",jsonstr);
				for(int i=0; i<resp.size(); i++){
					String detail=resp.toString();
					if(detail.contains("status")){
						int st=detail.indexOf("status");
						System.out.println(st+"");
					}
				}
				succ=resp.toString();
			}catch (Exception e){
				e.printStackTrace();
				succ="0";
			}
		}return succ;
	}

	@ResponseBody
	@RequestMapping(value="/setContentEmailAndSend",method={RequestMethod.GET,RequestMethod.POST})
    public static boolean setContentEmailAndSend(HttpServletRequest request,HttpServletResponse response){
        MailchimpClient client = new MailchimpClient(API_KEY);
        String campaignid=request.getParameter("campaignid")==null?"":request.getParameter("campaignid").trim();
//	        String list=request.getParameter("list")==null?":request.getParameter("list").trim();
        boolean campSent=true;

        // Configuración de contenido del email
        SetCampaignContentMethod setCampaignContentMethod = new SetCampaignContentMethod(campaignid);
        setCampaignContentMethod.mapping.put("plain_text","Contexto de campaña");
        
        try{
			ContentInfo contentInfo = client.execute(setCampaignContentMethod);	// Verifica datos completos
			System.out.println(contentInfo);
		} catch (IOException | MailchimpException e) {
			campSent=false;
			e.printStackTrace();
		}

        // Enviar campaña
        CampaignActionMethod.Send send = new CampaignActionMethod.Send(campaignid);
        try{
        	MailchimpObject chimpcode = client.execute(send);
			System.out.println(chimpcode); //Response 204 No Content = campaña enviada
			campSent=true;
		}catch(IOException | MailchimpException e){
			campSent=false;
			e.printStackTrace();
		}
        return campSent;
    }

	@ResponseBody
	@RequestMapping(value="/resendCampaign",method={RequestMethod.GET,RequestMethod.POST})
    public static boolean ResendCampaign(HttpServletRequest request,HttpServletResponse response){
        boolean campSent = false;
        return campSent;
    }

	@RequestMapping(value="/sendTestCampaign",method={RequestMethod.POST,RequestMethod.POST})
	public String sendTestCampaign(HttpServletRequest request,HttpServletResponse response){
		String campaign=request.getParameter("campaign")==null?"":request.getParameter("campaign").trim(),
			emailToSend=request.getParameter("emails")==null?"":request.getParameter("emails").trim(),
			extra=request.getParameter("extra")==null?"":request.getParameter("extra").trim();
		if((emailToSend.equals(""))&&(extra.equals("")))return "email";
		if(campaign.equals(""))return "campaign";
		if(!extra.equals(""))
			emailToSend=(emailToSend.equals(""))?extra:emailToSend+","+extra;
		int emailcount=emailToSend.split(",").length;
		if(emailcount>6)return "max-email";
		String data="{\"test_emails\":[\""+emailToSend.replace(" ","")+"\"],"+
		  "\"send_type\":\"html\"}";
		List<String> resp=curlChimp("POST","campaigns/"+campaign+"/actions/test",data);
		return resp.toString();
	}

	public String suscribeMember(HttpServletRequest request,HttpServletResponse response){
		String listid=request.getParameter("listid")==null?"":request.getParameter("listid").trim(),
			email=request.getParameter("email")==null?"":request.getParameter("email").trim(),
			fname=request.getParameter("fname")==null?"":request.getParameter("fname").trim(),
			lname=request.getParameter("lname")==null?"":request.getParameter("lname").trim(),
			address=request.getParameter("address")==null?"":request.getParameter("address").trim(),
			phone=request.getParameter("phone")==null?"":request.getParameter("phone").trim(),
			birthday=request.getParameter("birthday")==null?"":request.getParameter("birthday").trim(),
			status=request.getParameter("status")==null?"subscribed":(request.getParameter("status").trim()).toLowerCase(),
			wesite=request.getParameter("wesite")==null?"":request.getParameter("wesite").trim();
		if((email.equals(""))||(fname.equals(""))){
			return "";
		}
		if(birthday.equals("undefined"))birthday="";
		if(listid.equals("")){
			
		}

		MailchimpClient client=new MailchimpClient(API_KEY);
		try{
			EditMemberMethod.CreateOrUpdate method=new EditMemberMethod.CreateOrUpdate(listid,email);
			method.status=status;
			method.merge_fields=new MailchimpObject();
			method.merge_fields.mapping.put("EMAIL",email);
			method.merge_fields.mapping.put("FNAME",fname);
			method.merge_fields.mapping.put("LNAME",lname);
			method.merge_fields.mapping.put("ADDRESS",address);
			method.merge_fields.mapping.put("PHONE",phone);
			method.merge_fields.mapping.put("BIRTHDAY",birthday);
			method.merge_fields.mapping.put("WEBSITE",wesite);
			method.merge_fields.mapping.put("STATUS",status);

			MemberInfo member=client.execute(method);
			System.out.println("Datos almacenados correctamente: "+member);
			return "T";
		}catch(IOException|MailchimpException e){
			e.printStackTrace();
		}finally{
			try{
				client.close();
			}catch (IOException e){
				e.printStackTrace();
				return "F";
			}
		}return "F";
	}

	@ResponseBody
	@RequestMapping(value="/deteleSuscriber",method={RequestMethod.GET,RequestMethod.POST})
	public String deleteSuscribeMember(HttpServletRequest request,HttpServletResponse response){
		String listid=request.getParameter("listid")==null?"":request.getParameter("listid").trim(),
			memid=request.getParameter("memid")==null?"":request.getParameter("memid").trim(),
			email=request.getParameter("email")==null?"":request.getParameter("email").trim();
		if(memid.equals("")){
			return "";
		}

		//List<String> resp = curlChimp("DELETE","lists/"+listid+"/members/"+memid,""); //hash member (id)
		/*System.out.println("Suscriptor eliminado: "+email);
		return "T";*/

		MailchimpClient client=new MailchimpClient(API_KEY);
		DeleteMemberMethod method= new DeleteMemberMethod(listid, email);
		try{
			MemberInfo member=(MemberInfo) client.execute(method);
			System.out.println("Suscriptor eliminado: "+email);
			return "T";
		}catch(IOException | MailchimpException e){
			e.printStackTrace();
		}
		return "F";
	}

	@ResponseBody
	@RequestMapping(value="/setemailchimp",method={RequestMethod.POST,RequestMethod.POST})
	public Object setemailchimp(HttpServletRequest request,HttpServletResponse response){
		HttpSession sess=request.getSession(false);
		if(sess!=null){
			if(sess.getAttribute("isLogin")!=null &&(((String)sess.getAttribute("isLogin")).equals("yes"))){
				UserDTO userDto=(UserDTO) sess.getAttribute("UserDTO");
				int role=userDto.getRole(),userid=0;
				String apikey="",dc="",urlapi="",email="";
				try{
					Map<String, String> chimpdata=new HashMap<String,String>();
					chimpdata.put("apikey", API_KEY);
					chimpdata.put("dc", DC);
					chimpdata.put("urlapi", URLAPI);
					chimpdata.put("email", email);
					return chimpdata;
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}return null;
	}

	/**"
	 * Obtiene datos atravez de la API de Mail Chimp.<br>
	 * * Los parámetros deberán estar en formato <b>URL Search</b>, ejemplo: 
	 * from=lists<b>&amp;</b>query=name,id<b>&amp;</b>typeList=detail
	 * @param 	from	-> Texto indicando el endpoint a buscar (lists, campaigns, members, 
	 * 			templates, etc.), ejemplo: "from=lists"
	 * @param	query	-> Query indicando los datos a obtener.<ul>
	 * 			<li>Para traer todas las coincidencias, se puede indicar una sóla 
	 * 				Propiedad; para traer varias Propiedades deberán ir separados por comas: 
	 * 				query=prop1, prop2, prop_n </li>
	 * 			<li>En caso de necesitar un valor específico de 
	 * 				cualquier Propiedad, deberán añadir doble 'dos puntos', seguido del 
	 * 				valor. Ejemplo de la Propiedad llamada "type" cuyo valor sólo se 
	 * 				requiere los que contengan "user": query=type<b>::</b>user</li></ul>
	 * @param	typeList -> Forma de retornar los datos. Puede ser "<b>detail</b>" para 
	 * 			retornar la Propiedad y su valor separados por pipe (Propiedad|valor), 
	 * 			si éste valor esta vació o es omitido totalmente, retornará sólo los 
	 * 			valores, sin referencia Propiedad.
	 * @see		https://developer.mailchimp.com/documentation/mailchimp/reference/overview
	 * @return	List: Retorna un List&lt;String> list.
	 * @throws	EmptyList List vacío en caso de errores o sin resultados.
	 * @author GRSR
	 */
	@ResponseBody
	@RequestMapping(value="/getList",method={RequestMethod.POST,RequestMethod.POST})
	public List<String> getList(HttpServletRequest request,HttpServletResponse response){
		String from=request.getParameter("from")==null?"":request.getParameter("from").trim();
		String query=request.getParameter("query")==null?"":request.getParameter("query").trim();
		String typeList=request.getParameter("typeList")==null?"":request.getParameter("typeList").trim();

		List<String> list=curlChimp("GET", from, query);
		if(typeList.equals("")){
			List<String> resumeList = new ArrayList<String>();
			for(int i=0; i<list.size(); i++){
				String[] items=list.get(i).split("\\|");
				String value=(items.length==1)?"":items[1];
				resumeList.add(value);
			}
			return resumeList;
		}
		return list;
	}

	/**
	 * Realiza una acción atravez de la API de Mail Chimp.<br>
	 * @param 	httpMethod	-> Se refiere a una de las acciones HTTP verbs soportadas: 
	 * 			"GET", "POST", "PATCH", "PUT" o "DELETE".
	 * @param 	endpoint	-> Texto indicando en dónde ejecutará la acción (campaigns,
	 * 			lists, members, templates, etc.). Si se requiere indicar IDs, deberán
	 * 			incluirse como un sólo path: lists/12345/member/12345/notes/12345 
	 * @param	data -> Se refiere a dos posibles datos: <ul>
	 * 			<li>En el caso de que "<b>httpMethod</b>" sea "POST", "PATCH", "PUT" o 
	 * 			"DELETE", "<b>data</b>" deberá contener una estructura JSON de acuerdo a la 
	 * 			acción solicitada.</li>
	 * 			<li>Sí el método es "GET", se deberá indicar la información a obtener:<br>
	 * 				* Para traer todas las coincidencias, se puede indicar una sola 
	 * 				Propiedad; para traer varias Propiedades deberán ir separados por comas: 
	 * 				prop1, prop2, prop_n <br>* En caso de necesitar un valor específico de 
	 * 				cualquier Propiedad, deberán añadir doble 'dos puntos', seguido del 
	 * 				valor, ejemplo de la Propiedad llamada "type" cuyo valor sólo se requiere 
	 * 				los que contengan "user": type<b>::</b>user<br>* Para traer todos los 
	 * 				datos, se deberá indicar solamente un asterisco <b>*</b> .</li></ul>
	 * @see		https://developer.mailchimp.com/documentation/mailchimp/reference/overview
	 * @return	List&lt;String&gt;- Retorna un String con estructura JSON.
	 * @throws	JSONString Retornará una cadena JSON con los detalles del error.
	 * @author GRSR
	 */
	public List<String> curlChimp(String httpMethod, String endpoint, String data){
		String jsonStr="";
		httpMethod=httpMethod.toUpperCase();
		endpoint=("/"+endpoint.toLowerCase()).replace("//","/");
		List<String> finaldata = new ArrayList<String>();
		try{
	        URL url = new URL("https://"+DC+URLAPI+"/"+endpoint.toLowerCase());
	        String userpass = "user" + ":" + API_KEY;
	        String basicAuth= "Basic "+javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        conn.setConnectTimeout(15000);
			conn.setReadTimeout(60000);

			if(httpMethod.equals("PATCH")){
				conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
				httpMethod="POST";
			}
			conn.setRequestMethod(httpMethod);
			conn.setAllowUserInteraction(false);//nuevo

			byte bytes[] = data.getBytes("UTF-8");

			if(httpMethod.equals("DELETE")){
				conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;");
			}else{
				conn.addRequestProperty("Content-Type","application/json; charset=utf-8");
			}
			conn.setRequestProperty("Content-Length", Integer.toString(bytes.length));
			conn.setRequestProperty ("Authorization", basicAuth);

			if(!httpMethod.equals("GET")){
				conn.getOutputStream().write(bytes);
			}

			int responceCode = conn.getResponseCode();
			InputStream is = responceCode == 200? conn.getInputStream() : conn.getErrorStream();
			
			if((is!=null)||responceCode!=204){
				Reader reader = new InputStreamReader(is, "UTF-8");
				StringBuilder sb = new StringBuilder();
				char buf[] = new char[4096];
				int cnt;
				while ((cnt = reader.read(buf)) >= 0) {
					sb.append(buf, 0, cnt);
				}
				jsonStr=sb.toString();
			}

			if(!httpMethod.equals("GET")){
	    		finaldata.add(jsonStr);
	    		return finaldata;
			}
		}catch (IOException e){
			e.printStackTrace();
			return null;
		}

		data=data.toLowerCase();

		
			
		try{
			Gson gson1 = new Gson();
			String data1 = gson1.toJson(jsonStr);
			JsonArray jsonArray = new JsonParser().parse(data1).getAsJsonArray();
			System.out.println("Data: "+ jsonArray );
		}catch(Exception ex){
			System.out.println("Errores: "+ex.getMessage());
		}
		
		
		
		String searching=data;
		searching = searching.replace(", ", " ").replace(","," ").replace("  "," ");
		String[] searchingArr=searching.split(" ");
		
		Map<String, Object> allTreeMap = new LinkedHashMap<String, Object>();
		//Map<String, Integer> mapIni = new LinkedHashMap<String, Integer>();
		Map<String, Integer> mapEnd = new LinkedHashMap<String, Integer>();
		List<Integer> levelskey = new ArrayList<Integer>();
		List<Integer> levelssqr = new ArrayList<Integer>();
		int numkey=0, numsqr=0;
		// Organiza coordenadas de inicio y fin de bloques {} y []
		for(int i=0; i<jsonStr.length(); i++){
			if(i+2>jsonStr.length()){
				mapEnd.put("1,1",i+1);
				break;
			}
			String c2=jsonStr.substring(i,i+2);
			if(c2.equals("{\"")){
				numkey++;
				//mapIni.put("1,"+numkey, i+1);
				levelskey.add(numkey);
			}else if((c2.equals("},"))||(c2.equals("}]"))||(c2.equals("}}"))){
				int count=numkey+1;
				boolean ok=false;
				while(!ok){
					count--;
					for(int l=levelskey.size(); l>0; l--){
   						if(levelskey.get(l-1)==count){
							levelskey.set(l-1,(count)*(-1));
							ok=true;
							break;
						}
					}
				}
				mapEnd.put("1,"+count, i+1);
			}else if((c2.equals("[{"))||(c2.equals("[\""))){
				numsqr++;
				//mapIni.put("2,"+numsqr, i+1);
				levelssqr.add(numsqr);
			}else if((c2.equals("],"))||(c2.equals("]}"))){
				int count=numsqr+1;
				boolean ok=false;
				while(!ok){
					count--;
					for(int l=levelssqr.size(); l>0; l--){
						if(levelssqr.get(l-1)==count){
							levelssqr.set(l-1,(count)*(-1));
							ok=true;
							break;
						}
					}
					if(count<(-1)){
						return finaldata;
					}
				}
				mapEnd.put("2,"+count, i+1);
			}
		}

		// Construye el árbol organizado por path
		int levelkey=0, ini=0, end=0, levelsqr=0; numkey=1; numsqr=0; 
		String val="", keypath="";
		//boolean getsqrval=false;
		for(int i=0; i<jsonStr.length()+1; i++){
			if(i+2>jsonStr.length()){
				break;
			}
			String c2=jsonStr.substring(i,i+2);
			if(c2.equals("\":")){		//Termina key + inicio de nuevo valor
				String[] pathParts=keypath.split("/");
				List<String> pathsWitoutEmpty=new ArrayList<String>(Arrays.asList(pathParts));
				pathsWitoutEmpty.removeAll(Arrays.asList("", null));
				String path=""; keypath="";
				if((levelkey==0)&&(pathsWitoutEmpty.size()>0)){
					levelkey++;
				}
				for(int p=0; p<(levelkey); p++){
					keypath+="/"+pathsWitoutEmpty.get(p);// pathParts[p];
				}
				path="/"+jsonStr.substring(ini, i);
				keypath+=path;
				ini=i+3;
			}else if(c2.equals("{\"")){	// Inicia Nuevo nivel + asigna nuevo valor con rango { }
				if(i>0){
					numkey++;
					levelkey++;
					Iterator it = mapEnd.keySet().iterator();
					String compareKey="";
			    	while(it.hasNext()){
			    		Object key = it.next();
			    		compareKey=key.toString();
			    		if(compareKey.equals("1,"+numkey)){
			    			String mapToStr = mapEnd.get(key).toString();
			    			end=Functions.toInt(mapToStr);
			    			break;
			    		}
			    	}
					val=jsonStr.substring(ini, end);
				}
				ini=i+2;
			}else if(c2.equals(",\"")){	// Mismo nivel + Nuevo key + Inicio de nuevo key
				String c_3=jsonStr.substring(i-1,i);
				if(c_3.equals("\"")){
					val=jsonStr.substring(ini, i-1);
				}else{
					val=jsonStr.substring(ini-1, i);
				}
				if(val.equals("")){
					val="~"; //Caracter temporal para indicar que es un espacio en blanco válido
				}else if(val.substring(val.length()-1, val.length()).equals("}")){
					val=val.substring(0, val.length()-1);
				}
				ini=i+2;
			}else if((c2.equals("[{"))||(c2.equals("[\""))){	//Nuevo valor tipo array + asignación valor con rango [ ]
				numsqr++;
				levelsqr++;
				Iterator it = mapEnd.keySet().iterator();
				String compareKey="";
		    	while(it.hasNext()){
		    		Object key = it.next();
		    		compareKey=key.toString();
		    		if(compareKey.equals("2,"+numsqr)){
		    			String mapToStr = mapEnd.get(key).toString();
		    			end=Functions.toInt(mapToStr);
		    			break;
		    		}
		    	}
		    	val=jsonStr.substring(ini-1, end);
	    	}else if((c2.equals("},"))||(c2.equals("}]"))||(c2.equals("}}"))){	//Regresa nivel
				levelkey--;
			}else if((c2.equals("],"))||(c2.equals("]}"))){		//Termina array
				levelsqr--;
			}
			if((!val.equals(""))&&(!keypath.contains("["))&&(!keypath.contains("{"))){
				if(val.length()==1){
					val=val.replace("~", ""); //Caracter temporal para indicar que es un espacio en blanco válido
				}
				String repeatKey="";
				if(allTreeMap.containsKey(keypath)){
					int nKeys=1;
					Iterator it = allTreeMap.keySet().iterator();
					while(it.hasNext()){
						if(allTreeMap.containsKey(keypath+"("+nKeys+")")){
							nKeys++;
						}else{
							break;
						}
					}
					repeatKey="("+nKeys+")";
				}
				String noUnicode = val;
				val = StringEscapeUtils.unescapeJava(noUnicode);
				allTreeMap.put(keypath+repeatKey, val);
//System.out.println(keypath+repeatKey + "=" +val); //Resultado por cada path
				val="";
			}
		}

		// Obtiene los datos de acuerdo a como fueron solicitados
		List<String> result = new ArrayList<String>();
		if(data.equals("\\*")){
			result.add(Functions.toStr(allTreeMap));
			return result;
		}
		int loops=0;
		boolean hasSpecific = false;
		for(int n=0; n<searchingArr.length; n++){
			boolean voidVal=true;
			Iterator itree = allTreeMap.keySet().iterator();
			while(itree.hasNext()){
	    		Object objKey = itree.next();
	    		String[] pathParts=Functions.toStr(objKey).split("/");

	    		// Pendientes:
	    		// 1) Validar valores nulos
	    		// 2) Búsqueda por path
	    		// 3) Traer todo el nodo (*) solicitado

	    		// Búsqueda individual
	    		String item=pathParts[pathParts.length-1];
	    		if(item.contains("(")){
	    			item=item.substring(0,item.indexOf("("));
	    		}
	    		String keyComp ="";
	    		if(searchingArr[n].contains("::")){
	    			String[] specific=searchingArr[n].split("::");
	    			keyComp = specific[0];
	    			hasSpecific=true;
	    		}else{
	    			keyComp = searchingArr[n];
	    		}
	    		if((item.equals(keyComp))||(searchingArr[n]==objKey.toString())){
	    			String value = allTreeMap.get(objKey)+"";
	    			result.add(keyComp+"|"+value);
    				voidVal=false;
    				if(item.equals(searchingArr[0])){
    	    			loops++;
    	    		}
	    		}
			}
			// validar valores nulos, ej: content en campaigns, que se conozca exáctamente de que elemento es
			// abc=sin content, def=sin content, ghi=content, jlk=sin content: content se asigna mal a "abc"
			if(voidVal){
				result.add(searchingArr[n]+"|");
			}
    	}
		//Alinea los valores de acuerdo a cada nuevo segmento/concurrencia encontrada
		int count=0;
		for(int f=0; f<result.size()-1; f++){	//for (String i : vars) {
			if(count>=loops)
				break;
			for(int a=0; a<result.size(); a+=loops){
				finaldata.add(result.get(f+a));
			}
			count++;
		}

		//En caso de datos específicos, eliminará los registros que coincidan con lo solicitado
		if(hasSpecific){
			int reg=0,init=0;
			for(int a=0; a<finaldata.size(); a++){
				if(reg>=searchingArr.length){
					reg=0;
					init=a;
				}
				if(searchingArr[reg].contains("::")){
					String tmpVal=(searchingArr[reg]).replaceAll("::", "\\|");
					if(!finaldata.get(a).equals(tmpVal)){
						//System.err.println(reg + ": " + tmpVal+" != "+ finaldata.get(a));
	    				for(int d=0; d<searchingArr.length; d++){
	    					finaldata.remove(init);
	    				}
	    				a=init-1;
		    			reg=(-1);
	    			}
				}
				reg++;
			}
		}
		return finaldata;
	}
}