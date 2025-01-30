package com.aj.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aj.model.Menu;
import com.aj.model.SmtpMailCRM;
import com.aj.service.AccessDbJAService;
import com.aj.service.PrivilegesService;
import com.aj.service.SmtpMailService;
import com.aj.utility.Functions;
import com.aj.utility.HtmlEmailSender;
import com.aj.utility.UserDTO;

@Controller
@ComponentScan("com.aj")
public class SmtpMailController{


	@Autowired
	PrivilegesService privilegesService;

	@Autowired
	SmtpMailService smtpmailService;
	
	@Autowired
	AccessDbJAService dao;
	
	
	@ResponseBody
	@RequestMapping(value="/emailsettings",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView emailsettings(HttpServletRequest request,HttpServletResponse response){
		HttpSession sess=request.getSession(false);
		if(sess!=null){
			if(sess.getAttribute("isLogin")!=null &&(((String)sess.getAttribute("isLogin")).equals("yes"))){
				UserDTO userDto=(UserDTO) sess.getAttribute("UserDTO");
				int role=userDto.getRole();
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//HTTP 1.1.
				response.setHeader("Pragma", "no-cache");//HTTP 1.0.
				response.setDateHeader("Expires", 0);
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				try{//Obtiene los privilegios del módulo
					
					HashMap<Object, Object> parameters = new HashMap();
					parameters.put("urlMethod", "emailsettings");
					Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
					Map<String,Object> forModel=new HashMap<String,Object>();
					forModel.put("menu",menu);
					List<SmtpMailCRM> settingList=smtpmailService.getAll("FROM SmtpMailCRM");
					forModel.put("edata",settingList);
					
					return new ModelAndView("emailsettings",forModel);
				}catch (Exception err){
					System.out.println("Nombre del m\u00f3dulo no existe o no se han asignado privilegios: "+err);
				}
			}
		}return new ModelAndView("login");
	}

	@RequestMapping(value="/addsmtp")
	@ResponseBody
	public void addsmtp(HttpServletRequest request,HttpServletResponse response){
		HttpSession sess=request.getSession(false);
		if(sess!=null){
			if(sess.getAttribute("isLogin")!=null&&(((String) sess.getAttribute("isLogin")).equals("yes"))){
				SmtpMailCRM smtpemail=new SmtpMailCRM();
				int port=Functions.isEmpty(request.getParameter("port"))?0:Functions.toInt(request.getParameter("port").trim());
				String host=Functions.isEmpty(request.getParameter("host"))?"":request.getParameter("host").trim(),
					email=Functions.isEmpty(request.getParameter("email"))?"":request.getParameter("email").trim(),
					psw=Functions.isEmpty(request.getParameter("psw"))?"":request.getParameter("psw").trim(),
					alias=Functions.isEmpty(request.getParameter("alias"))?"":request.getParameter("alias").trim();
				if((Functions.isEmpty(host))||(Functions.isEmpty(email))||(Functions.isEmpty(psw))
					||(Functions.isEmpty(alias))||(port==0))
					try{response.getWriter().write("empty");}
					catch (IOException e1){e1.printStackTrace();}
				smtpemail.setHost(host);
				smtpemail.setAccountmail(email);
				//smtpemail.setPasswordmail(ScriptCommon.encode(psw));
				smtpemail.setPasswordmail(psw);
				smtpemail.setPort(port);
				smtpemail.setAliasmail(alias);
				long succ=smtpmailService.addNewSmtp(smtpemail);
				try{
					if(succ>0)
						response.getWriter().write("true");
					else
						response.getWriter().write("false");
				}catch(IOException e){
					System.err.println("Exception in addNewRole()"+e.getMessage());
				}
			}
		}
	}

	@RequestMapping(value="/getDatabyid")
	@ResponseBody
	public List<SmtpMailCRM> getDatabyid(HttpServletRequest request,HttpServletResponse response){
		HttpSession sess=request.getSession(false);
		if(sess!=null){
			if(sess.getAttribute("isLogin")!=null&&(((String) sess.getAttribute("isLogin")).equals("yes"))){
				Long smtpid=Functions.isEmpty(request.getParameter("smtpid"))?0L:Functions.toLong(request.getParameter("smtpid").trim());
				List<SmtpMailCRM> settigsdata=smtpmailService.getAll("FROM SmtpMailCRM WHERE smtpid="+smtpid);
				/*try{
					String psw=ScriptCommon.decode(settigsdata.get(0).getPasswordmail());
					settigsdata.get(0).setPasswordmail(psw);
				}catch(Exception e){
					System.out.println("El password no estaba codificado: "+e.getMessage());
				}*/
				return settigsdata;
			}
		}return null;
	}
	
	@RequestMapping(value="/updatedata")
	public void updateRole(HttpServletRequest request,HttpServletResponse response){
		HttpSession sess=request.getSession(false);
		if(sess!=null){
			if(sess.getAttribute("isLogin")!=null&&(((String) sess.getAttribute("isLogin")).equals("yes"))){
				int port=Functions.isEmpty(request.getParameter("port"))?0:Functions.toInt(request.getParameter("port").trim());
				Long smtpid=Functions.isEmpty(request.getParameter("smtpid"))?0L:Functions.toLong(request.getParameter("smtpid").trim());
				String host=Functions.isEmpty(request.getParameter("host"))?"":request.getParameter("host").trim(),
					email=Functions.isEmpty(request.getParameter("email"))?"":request.getParameter("email").trim(),
					psw=Functions.isEmpty(request.getParameter("psw"))?"":request.getParameter("psw").trim(),
					alias=Functions.isEmpty(request.getParameter("alias"))?"":request.getParameter("alias").trim();
				if((Functions.isEmpty(host))||(Functions.isEmpty(email))||(Functions.isEmpty(psw))
					||(Functions.isEmpty(alias))||(port==0)){
					try{response.getWriter().write("empty");}
					catch (IOException e1){e1.printStackTrace();}
					return;
				}
				SmtpMailCRM emailsettings=new SmtpMailCRM();
				try{
					emailsettings.setSmtpid(smtpid);
					emailsettings.setHost(host);
					emailsettings.setAccountmail(email);
					//emailsettings.setPasswordmail(ScriptCommon.encode(psw));
					emailsettings.setPasswordmail(psw);
					emailsettings.setPort(port);
					emailsettings.setAliasmail(alias);
					smtpmailService.updateSmtpDetails(emailsettings);
				}catch(Exception e){
					System.err.println("Exception in UserController [updateRole()]::"+e.getMessage());
				}
			}
		}
	}

	@RequestMapping(value="/deletedata")
	public void deleteRole(HttpServletRequest request,HttpServletResponse response){
		HttpSession sess=request.getSession(false);
		if(sess!=null){
			if(sess.getAttribute("isLogin")!=null&&(((String) sess.getAttribute("isLogin")).equals("yes"))){
				Long smtpid=Functions.isEmpty(request.getParameter("smtpid"))?0L:Functions.toLong(request.getParameter("smtpid").trim());
				smtpmailService.deleteSmtp(smtpid);
			}
		}
	}

/*	@RequestMapping(value="/sendTest")
	@ResponseBody
	public boolean sendTest(HttpServletRequest request,HttpServletResponse response){
		boolean sent=false;
		HttpSession sess=request.getSession(false);
		if(sess!=null){
			if(sess.getAttribute("isLogin")!=null&&(((String) sess.getAttribute("isLogin")).equals("yes"))){
				int smtpid=Functions.isEmpty(request.getParameter("smtpid"))?0:Functions.toInt(request.getParameter("smtpid").trim());
				String emailto=Functions.isEmpty(request.getParameter("emailto"))?"":request.getParameter("emailto").trim();
				
				List<SmtpMailCRM> smtpbyid=smtpmailService.getAll("FROM SmtpMailCRM WHERE smtpid="+smtpid);
				/ *try{
					String psw=ScriptCommon.decode(smtpbyid.get(0).getPasswordmail());
					smtpbyid.get(0).setPasswordmail(psw);
				}catch(Exception e){
					System.out.println("El password no estaba codificado: "+e.getMessage());
				}* /
				int port=smtpbyid.get(0).getPort();
				SmtpMailCRM smtp=smtpmailService.getSmptEmail(smtpid);
				String subj="JetAccess - email Test";
				String msg="<!DOCTYPE html><html><head><meta charset='utf-8'><meta content='text/html; charset=utf-8' http-equiv='content-type'><meta name='language' content='Spanish'><title>JetAccess - Env&iacute;o de pruba - Configuraci&oacute;n smtp</title>"+
					"<style type='text/css'>.nounderline,.nounderline:hover,.nounderline:active,.nounderline:focus{outline:none;text-decoration:none}.nounderline::-moz-focus-inner{border:0}</style></head>"+
					"<body style='font-family:Helvetica Neue,Helvetica,Roboto,Arial,sans-serif;background:#ccc;'><div style='width:100%;text-align:center;'><div style='width:100%;max-width:640px;display:inline-block;background-color:#fff;border-radius:5px;-moz-border-radius:5px;-webkit-border-radius:5px;margin:10px;padding:10px'><a class='nounderline' style='padding-top:40px;padding-bottom:40px;font-size:32px;color:green;' href='http://www.jetaccess.com' title='Ir al sitio de JetAccess'>JetAccess</a></div>"+
					"<div style='width:100%;max-width:640px;display:inline-block;background-color:#fff;border-radius:5px;-moz-border-radius:5px;-webkit-border-radius:5px;margin:10px;padding:10px'><h1 style='padding-top:40px;padding-bottom:40px;'>Env&iacute;o de prueba desde de JetAccess<br></h1><h2>S&iacute; usted puede ver este mensaje &iexcl;su cuenta de correo ha sido correctamente configurada!</h2><hr>"+
					"<p>Este email ha sido enviado desde de JetAccess como prueba de la correcta configuraci&oacute;n de la siguiente cuenta:<br><br></p>"+
					"<table style='display:inline-block;text-align:justify'><tr><td style='padding-right:15px'>Correo de origen</td><td style='padding-right:15px'>"+smtpbyid.get(0).getAccountmail()+"</td></tr><tr><td style='padding-right:15px'>Nombre del Host</td><td style='padding-right:15px'>"+smtpbyid.get(0).getHost()+"</td></tr><tr><td style='padding-right:15px'>Puerto SMTP</td><td style='padding-right:15px'>"+smtpbyid.get(0).getPort()+"</td></tr><tr><td style='padding-right:15px'>Alias del correo</td><td style='padding-right:15px'>"+smtpbyid.get(0).getAliasmail()+"</td></tr></table></div></body></html>";
				try{
					HtmlEmailSender.crmSendHtmlEmail(emailto, subj, msg, smtp);
					sent=true;
				}catch (MessagingException e){
					e.printStackTrace();
				}	
			}
		}return sent;
	}*/
}