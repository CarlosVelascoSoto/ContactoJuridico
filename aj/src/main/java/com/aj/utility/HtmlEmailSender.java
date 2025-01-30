package com.aj.utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;			//Para adjuntar archivo
import javax.mail.Authenticator;
//import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;				//Para Adjuntar archivo
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;	//Para adjuntar archivo
import javax.mail.util.ByteArrayDataSource;	//Para adjuntar archivo

import com.aj.model.Smtpmail;

public class HtmlEmailSender{

	public void sendHtmlEmail(String toAddress, String subject, String message, final Smtpmail smtp, String bcc)
			throws AddressException, MessagingException{
		// sets SMTP server properties
		Properties properties=new Properties();
		properties.put("mail.smtp.host", smtp.getHost());
		properties.put("mail.smtp.port", smtp.getPort());
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// creates a new session with an authenticator
		Authenticator auth=new Authenticator(){
			public PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(smtp.getAccountmail(), smtp.getPasswordmail());
			}
		};
		Session session=Session.getInstance(properties, auth);
		// session.setDebug(true);

		// creates a new e-mail message
		MimeMessage msg=new MimeMessage(session);
		msg.setHeader("Content-Type", "text/html; charset=UTF-8");
		if(!Functions.isEmpty(smtp.getAliasmail())){
			try{
				msg.setFrom(new InternetAddress(smtp.getAccountmail(), smtp.getAliasmail()));
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}else{
			msg.setFrom(new InternetAddress(smtp.getAccountmail()));
		}
		InternetAddress[] toAddresses={ new InternetAddress(toAddress) };
		InternetAddress[] toAddressesBCC=null;
		if(!Functions.isEmpty(bcc)){
			String arr[]=Functions.toStr(bcc).split("\\;");
			toAddressesBCC=new InternetAddress[arr.length + 1];
			for(int i=0; i < arr.length; i++){
				toAddressesBCC[i]=new InternetAddress(arr[i]);
			}
			toAddressesBCC[arr.length]=new InternetAddress("wf@jetaccess.com");
		}else{
			toAddressesBCC=new InternetAddress[]{ new InternetAddress("wf@jetaccess.com") };
		}
		System.out.println("MAIL subject: "+subject);
		for(InternetAddress address : toAddresses){
			System.out.println("toAddress:" + address.getAddress());
		}
		for(InternetAddress address : toAddressesBCC){
			System.out.println("toAddressesBCC:" + address.getAddress());
		}
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setRecipients(Message.RecipientType.BCC, toAddressesBCC);
		msg.setSubject(subject, "utf-8");
		msg.setSentDate(new Date());
		// set plain text message
		msg.setContent(message, "text/html; charset=utf-8");

		// sends the e-mail
//TODO:DESACTIVAR COMENTARIO			Transport.send(msg);
	}

	public static void main(String user, String uemail, String ucompany, String upassw, String uphone, String ucountry,
			String uindustry, String uactivity, String ulevel, String msg, Smtpmail smtp, String bcc, String subject){
		String mailTo=uemail;		// outgoing message information
		// -------------------------------
		// Permite conectarse a la base de datos de ventas, inicialmente sólo para
		// hacer una consulta de una tabla, porsteriormente para insertar un registro
		dbConnect.main(user, uemail, ucompany, upassw, uphone, ucountry, uindustry, uactivity, ulevel);
		HtmlEmailSender mailer=new HtmlEmailSender();
		try{
			mailer.sendHtmlEmail(mailTo, subject, msg, smtp, bcc);
			System.out.println("Email enviado.");
		}catch(Exception ex){
			System.out.println("Falla en envio de email.");
			ex.printStackTrace();
		}
	}

	public static void main(String user, String uemail, String msg, Smtpmail smtp, String bcc, String subject){
		String mailTo=uemail;		// outgoing message information
		// String subject="Confirma tu correo electr\u00F3nico";

		// -------------------------------
		// Permite conectarse a la base de datos de ventas, inicialmente solo para
		// hacer una consulta de una tabla, porsteriormente para insertar un registro
		dbConnect dbconnect=new dbConnect("", "");
		dbconnect.main(user, uemail);
		// -------------------------------------------
		HtmlEmailSender mailer=new HtmlEmailSender();
		try{
			mailer.sendHtmlEmail(mailTo, subject, msg, smtp, bcc);
			System.out.println("Email enviado.");
		}catch(Exception ex){
			System.out.println("Falla en envio de email.");
			ex.printStackTrace();
		}
	}

	//Envio Email estándar 
	public static void SendHtmlEmailStd(String toAddress,String subject,String message,String bcc,String nameFileAndExt,String data,final Smtpmail smtp) throws AddressException,MessagingException{
		// Propiedades SMTP server
		Properties properties=new Properties();
		properties.put("mail.smtp.host",smtp.getHost());
		properties.put("mail.smtp.port",smtp.getPort());
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable", "true");

		// Crea nueva sesión con authenticator
		Authenticator auth=new Authenticator(){
			public PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(smtp.getAccountmail(), smtp.getPasswordmail());
			}
		};
		Session session=Session.getInstance(properties,auth);
		// session.setDebug(true);

		// Crea un nuevo mensaje de email
		MimeMessage msg=new MimeMessage(session);
		msg.setHeader("Content-Type","text/html; charset=UTF-8");
		if(!Functions.isEmpty(smtp.getAliasmail())){
			try{
				msg.setFrom(new InternetAddress(smtp.getAccountmail(), smtp.getAliasmail()));
			}catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}else{
			msg.setFrom(new InternetAddress(smtp.getAccountmail()));
		}

		//Email de destino
		InternetAddress[] toAddresses={new InternetAddress(toAddress)};

		System.out.println("MAIL subject: "+subject);
		for (InternetAddress address : toAddresses) {
			System.out.println("toAddress:" + address.getAddress());
		}
		InternetAddress[] toAddressesBCC=null;
		if(!Functions.isEmpty(bcc)){
			String arr[]=Functions.toStr(bcc).split("\\;");
			toAddressesBCC=new InternetAddress[arr.length + 1];
			for(int i=0; i < arr.length; i++){
				toAddressesBCC[i]=new InternetAddress(arr[i]);
		   }toAddressesBCC[arr.length]=new InternetAddress(bcc);
		}
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject, "utf-8");
		msg.setSentDate(new Date());
		msg.setContent(message, "text/html; charset=utf-8");// Establece un mensaje de texto plano
		
		MimeBodyPart messageBodyPart=new MimeBodyPart();	// creates message part
        messageBodyPart.setContent(message,"text/html; charset=utf-8");// set plain text message
 
        Multipart multipart=new MimeMultipart();			// creates multi-part
        multipart.addBodyPart(messageBodyPart);
 
        MimeBodyPart attachPart=new MimeBodyPart();			// adds attachments
        DataSource ds;
        try{
			ds=new ByteArrayDataSource(data, "application/x-any");
			attachPart.setDataHandler(new DataHandler(ds));
			attachPart.setFileName(nameFileAndExt);
		}catch(IOException e){
			e.printStackTrace();
		}
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);	// sets the multi-part as e-mail's content
		
//TODO:DESACTIVAR COMENTARIO	        Transport.send(msg);	// Enviar email
	}
}