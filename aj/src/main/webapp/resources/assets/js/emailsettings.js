function saveNewData(){
	var err="",host=$("#hostname").val(),email=$("#account").val(),psw=$("#password").val(),port=$("#port").val(),alias=$("#alias").val(),
		pattern= /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i,re=new RegExp("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[_#?!@$%^&*-.]).{8,}$");
	if(host=='')err=i18n("err_enter_host");
	else if(email=="")err=i18n("err_enter_email");
	else if(!pattern.test(email))err=i18n("err_enter_valid_email");
	else if(psw=="")err=i18n("err_enter_password");
//	else if(!re.test(psw))err=i18n("err_char_contains_password")
	else if(port=="")err=i18n("err_enter_port");
	else if(alias=="")err=i18n("err_enter_alias");
	if(err!=""){
		$('#putError').html(err);
		$('#addDataError').css('display','block');
		return;
	}$('#addDataError').css('display','none');
	var param="host="+host+"&email="+email+"&psw="+psw+"&port="+port+"&alias="+alias;
	$.ajax({
		type:"POST",
		url:ctx+"/addsmtp.jet",
		data:param,
		async:false,
		success:function(data){
			if(data=="true"){
				$('#addDataError').css('display','none');
				$("#rolename").val("");
				swal(i18n("msg_success"),i18n("msg_record_saved_successfully"),"success");
				Custombox.close();
				location.reload();
			}
			if(data=="empty")
				err=i18n("err_field_empy");
			else if(data=="false")
				err=i18n("err_smtp_exists");
			$('#putError').html(err);
			$('#addDataError').css('display','block');
		},error:function(e){swal(i18n("msg_error"),i18n("err_unable_get_data")+", err.1","error");}
	});
};

$("#addNewDataCancel").click(function(){
	$('#addDataError').css('display','none');
	$("#hostname").val("");
	$("#account").val("");
	$("#password").val("");
	$("#port").val("");
	$("#alias").val("");
});

function editSettingsDetails(smtpid){
	$('#EditDataError').css('display','none');
	var param="smtpid="+smtpid;
	$.ajax({
		type:"POST",
		url:ctx+"/getDatabyid.jet",
		data:param,
		async:false,
		success:function(data){
			if(data.length>0){
				$("#editSmtpid").val(smtpid);
				$("#editHost").val(data[0].host);
				$("#editEmail").val(data[0].accountmail);
				$("#editPassword").val(data[0].passwordmail);
				$("#editPort").val(data[0].port);
				$("#editAlias").val(data[0].aliasmail);
			}else{
				$('#putError').html(i18n("err_unable_get_data"));
				$('#putEditDataError').css('display','block');
			}
		},error:function(e){swal(i18n("msg_error"),i18n("err_unable_get_data")+", (Edit process)","error");}
	});
};

function updateEditData(){
	var smtpid=$("#editSmtpid").val(),host=$("#editHost").val(),email=$("#editEmail").val(),psw=$("#editPassword").val(),
		port=$("#editPort").val(),alias=$("#editAlias").val(),pattern= /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i,
		re=new RegExp("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[_#?!@$%^&*-.]).{8,}$"),err="";
	if(host=='')err=i18n("err_enter_host");
	else if(email=="")err=i18n("err_enter_email");
	else if(!pattern.test(email))err=i18n("err_enter_valid_email");
	else if(psw=="")err=i18n("err_enter_password");
//	else if(!re.test(psw))err=i18n("err_char_contains_password")
	else if(port=="")err=i18n("err_enter_port");
	else if(alias=="")err=i18n("err_enter_alias");
	if(err!=""){
		$('#putEditDataError').html(err);
		$('#EditDataError').css('display','block');
		return;
	}$('#EditDataError').css('display','none');
	var param="smtpid="+smtpid+"&host="+host+"&email="+email+"&psw="+psw+"&port="+port+"&alias="+alias;
	$.ajax({
		type:'POST',
		url:ctx+'/updatedata.jet',
		data:param,
		async:false,
		success:function(resp){
			if(resp=="empty"){
				$('#putEditDataError').html(i18n("err_field_empy"));
				$('#EditDataError').css('display','block');
				return;
			}location.reload();
		},error:function(e){swal(i18n("msg_error"),i18n("err_unable_get_data")+", (Update process)","error");}
	});
};

function deleteSettingsDetails(smtpid,host,email,port,alias){
	$("#delHost").html(host);
	$("#delEmail").html(email);
	$("#delPort").html(port);
	$("#delAlias").html(alias);
	$('#deleteDataButton').attr('onclick','deleteData('+smtpid+')');
};

function deleteData(smtpid){
	var param="smtpid="+smtpid;
	$.ajax({
		type:'POST',
		url:ctx+"/deletedata.jet",
		data:param,
		async:false,
		success:function(resp){
			swal({
				title:i18n('msg_success'),
				text:i18n('msg_record_deleted_successfully'),
				type:"success",
				timer:3000,
				allowEscapeKey:false
			},function(){
				swal.close();
			});
			location.reload();
		},error:function(resp){swal(i18n("msg_error"),i18n("err_unable_get_data")+", (Delete process)","error");}
	});
};

function sendTestButton(smtpid,host,email,port,alias){
	$("#testHost").html(host);
	$("#testEmail").html(email);
	$("#testEmailTo").val(email);
	$("#testPort").html(port);
	$("#testAlias").html(alias);
	$('#sendTestButton').attr('onclick','sendTest('+smtpid+')');
};

function sendTest(smtpid){
	var emailto=$("#testEmailTo").val(),pattern= /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i,
		re=new RegExp("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[_#?!@$%^&*-.]).{8,}$"),err="";
	if(emailto=="")err=i18n("err_enter_email");
	else if(!pattern.test(emailto))err=i18n("err_enter_valid_email");
	if(err!=""){
		$('#putSendTestError').html(err);
		$('#sendTestError').css('display','block');
		return;
	}$('#sendTestError').css('display','none');
	var param="smtpid="+smtpid+"&emailto="+emailto;
	$.ajax({
		type:'POST',
		url:ctx+"/sendTest.jet",
		data:param,
		async:false,
		success:function(res){
			if(res)
				swal(i18n("msg_success"),i18n("msg_test_sent"),"success");
			else
				swal(i18n("msg_error"),i18n("err_no_sent_test"),"error");
		},error:function(resp){swal(i18n("msg_error"),i18n("err_unable_get_data")+", (Send email test)","error");}
	});
};

function getLanguageURL(){
	for(var l="en",q=window.location.search.split("&"),v=0;v<q.length;v++){
		var p=q[v].split("=");
		p[0]=p[0].replace(/[.*+?^${}()|[\]\\]/g,"");
		"language"==p[0]&&(l=p[1])
	}return l;
};

function i18n(msg){
	var i18n_message="";
	//IDIOMAS:si en la URL no contiene la variable "lenguage", asumirá que esta en inglés
	lang=getLanguageURL();
	if(msg=="err_char_contains_password"){
		if(lang=="es"){i18n_message="Una contrase\u00f1a debe contener m\u00ednimo ocho caracteres, al menos un n\u00famero,"+
			" una letra may\u00fascula, una min\u00fascula y alguno de estos s\u00edmbolos:<br> _ . - ^ * #?! @ $ % &";}
		else{i18n_message="A password contains at least eight characters, including at least one number and one lowercase and"+
			" one uppercase letters and, at last, one special character:<br> _ . - ^ * #?! @ $ % &";}

	}else if(msg=="err_enter_alias"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar un nombre corto o alias!";}
		else{i18n_message="Please enter a short name or alias!";}
	}else if(msg=="err_enter_valid_email"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar una direcci\u00f3n de correo electr\u00f3nico v\u00e1lida!";}
		else{i18n_message="Please enter correct email address!";}
	}else if(msg=="err_enter_email"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar un correo electr\u00f3nico!";}
		else{i18n_message="Please enter email!";}
	}else if(msg=="err_enter_host"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el host!";}
		else{i18n_message="Please enter a host data!";}
	}else if(msg=="err_enter_password"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar la contrase\u00f1a!";}
		else{i18n_message="Please enter password!";}
	}else if(msg=="err_enter_port"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el puerto!";}
		else{i18n_message="Please enter a port data!";}
	}else if(msg=="err_field_empy"){
		if(lang=="es"){i18n_message="Algunos campos están incompletos, por favor llene aquellos que son requeridos";}
		else{i18n_message="Some fields are incomplete, please fill those are the required";}
	}else if(msg=="err_no_sent_test"){
		if(lang=="es"){i18n_message="\u00a1Fallo al enviar email de prueba! Favor de verificar la configuraci\u00f3n de la cuenta";}
		else{i18n_message="Failed sending the email test! Please check the settings account";}
	}else if(msg=="err_smtp_exists"){
		if(lang=="es"){i18n_message="\u00a1La configuración SMTP ya existe!";}
		else{i18n_message="SMTP setting already exists!";}
	}else if(msg=="err_unable_get_data"){
		if(lang=="es"){i18n_message="\u00a1No fue posible obtener el registro!";}
		else{i18n_message="Unable to get data record!";}

	}else if(msg=="msg_test_sent"){
		if(lang=="es"){i18n_message="\u00a1Correo de prueba enviado exitosamente! Verifique su bandeja de entrada para confirmarlo.";}
		else{i18n_message="Email successfully sent! Please check your inbox to confirm it.";}
	}else if(msg=="msg_success"){
		if(lang=="es"){i18n_message="\u00a1Correcto!";}
		else{i18n_message="Success!";}
	}else if(msg=="msg_record_deleted_successfully"){
		if(lang=="es"){i18n_message="\u00a1Registro eliminado exitosamente!";}
		else{i18n_message="Record deleted successfully!";}
	}else if(msg=="msg_record_saved_successfully"){
		if(lang=="es"){i18n_message="\u00a1Registro guardado exitosamente!";}
		else{i18n_message="Record saved successfully!";}
	}return i18n_message;
}