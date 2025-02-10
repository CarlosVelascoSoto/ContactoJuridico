;function getVarURL(v) {
	var querys = (location.search).replace(/^./, '');
	var vars = querys.split('&');
	for (i = 0; i < vars.length; i++) {
		var pair = vars[i].split('=');
		if (pair[0] == v)
			return pair[1];
	}return '';
};

function i18n(msg){
	var i18n_message='',lang='es';
	if(msg=='err_char_contains_password')
		i18n_message=lang=='es'?'Una contrase\u00f1a debe contener \nm\u00ednimo ocho caracteres, \nal menos un n\u00famero,'+
			' \nuna letra may\u00fascula, \nuna letra min\u00fascula y \nalguno de estos s\u00edmbolos:<br> _ . - ^ * #?! @ $ % &'
			:'A password contains at least eight characters, including at least one number and one lowercase and'+
			' one uppercase letters and, at last, one special character:<br> _ . - ^ * #?! @ $ % &';
	else if(msg=='err_confirm_email')
		i18n_message=lang=='es'?'\u00a1Falta ingresar la confirmaci\u00f3n del correo electr\u00f3nico!':'Please enter email confirmation!';
	else if(msg=='err_enter_addrdest')
		i18n_message=lang=='es'?'Falta ingresar el nombre o lugar de destino, por ejemplo: Ofcina':'Please enter bussines or destination name, example: Office';
	else if(msg=='err_enter_address')
		i18n_message=lang=='es'?'Falta ingresar la direcci\u00f3n':'Please enter address!';
	else if(msg=='err_enter_confirmpsw')
		i18n_message=lang=='es'?'Falta de ingresar la confirmaci\u00f3n de contrase\u00f1a':'please enter the password confirmation';
	else if(msg=='err_enter_correct_email')
		i18n_message=lang=='es'?'\u00a1Favor de ingresar una direcci\u00f3n de correo electr\u00f3nico v\u00e1lida!':'Please enter correct email address!';
	else if(msg=='err_enter_email')
		i18n_message=lang=='es'?'\u00a1Favor de ingresar un correo electr\u00f3nico!':'Please enter email!';
	else if(msg=='err_email_exists')
		i18n_message=lang=='es'?'\u00a1El correo electr\u00f3nico ya existe en otro usuario!':'Email already exists in another user!';
	else if(msg=='err_enter_firm')
		i18n_message=lang=='es'?'\u00a1Favor de ingresar un nombre de firma o compa\u00f1ia':'Please enter firm name or company name!';
	else if(msg=='err_enter_firstname')
		i18n_message=lang=='es'?'Falta ingresar el nombre':'Please enter firstname!';
	else if(msg=='err_enter_lastname')
		i18n_message=lang=='es'?'Falta ingresar el apellido':'Please enter lastname!';
	else if(msg=='err_enter_password')
		i18n_message=lang=='es'?'\u00a1Favor de ingresar una contrase\u00f1a!':'Please enter a password!';
	else if(msg=='err_enter_username')
		i18n_message=lang=='es'?'\u00a1Favor de ingresar el nombre de usuario!':'Please enter username!';
	else if(msg=='err_firm_exists')
		i18n_message=lang=='es'?"\u00a1La firma ya existe! En caso de necesitar un nuevo usuario, favor de contactar al administrador de la firma para crear su usuario":"Firm already exists! If you need a new user, please contact the firm's administrator for creating your user";
	else if(msg=='err_invalid_input')
		i18n_message=lang=='es'?'El dato ingresado contiene un valor, caracter o formato no v\u00e1lido':'The data entered has an invalid format or value';
	else if(msg=='err_invalid_input_noreq')
		i18n_message=lang=='es'?'Informaci\u00f3n opcional pero contiene un valor, caracter o formato no v\u00e1lido':'Optional information, but the data entered has an invalid format or value';
	else if(msg=='err_not_same_user_pass')
		i18n_message=lang=='es'?'\u00a1La contrase\u00f1a debe ser distinta al nombre de usuario!':'The password must be different from about the user name!';
	else if(msg=='err_one_more_empty')
		i18n_message=lang=='es'?'\u00a1Favor de completar todods los campos necesarios con valores v\u00e1lidos!':'Please fill in all required fields with valid values!';
	else if(msg=='err_passwords_not_equal')
		i18n_message=lang=='es'?'\u00a1La contrase\u00f1a y la confirmaci\u00f3n de contrase\u00f1a son diferentes!':'Password and password confirmation are different!';
	else if(msg=='err_required')
		i18n_message=lang=='es'?'Esta informaci\u00f3n es requerida, \u00a1favor de completarla!':'This information is required, please fill it!';
	else if(msg=="err_rewrite_password")
		i18n_message=lang=='es'?'La contrase\u00f1a debe contener m\u00ednimo ocho caracteres y al menos un n\u00famero'
		:"A password contains at least eight characters, including at least one number";
	else if(msg=='err_unable_get_user')
		i18n_message=lang=='es'?'\u00a1No fue posible obtener el registro del usuario o la sesi\u00f3n ha terminado!':'Unable to get user record or the session has ended!';
	else if(msg=='err_unable_save')
		i18n_message=lang=='en'?'Unable to save data! Please, ask to System Administrator.':'\u00a1No fue posible guardar la informaci\u00f3n! Favor de consultar al administrador del sistema';
	else if(msg=='err_user_exists')
		i18n_message=lang=='es'?'\u00a1El usuario ya existe!':'User Name already exists!';
	else if(msg=='err_user_pass_incorrect')
		i18n_message=lang=='es'?'\u00a1El usuario o la contrase\u00f1a son incorrectos, favor de rectificar!':'The username or password is incorrect, please check them!';
	else if(msg=='err_username_6characters')
		i18n_message=lang=='es'?'\u00a1Favor de ingresar un nombre de usuario con al menos 6 caracteres!':'Please enter username with at least 6 characters!';

	else if(msg=='msg_error')
		i18n_message=lang=='es'?'Error':'Error';
	else if(msg=='msg_login')
		i18n_message=lang=='es'?'Ingresar':'Login';
	else if(msg=='msg_not_signed')
		i18n_message=lang=='es'?'\u00a1Favor de ingresar o firmarse con un usuario!':'Please login with a user!';
	else if(msg=='msg_recover_password')
		i18n_message=lang=='es'?'Recuperar contrase\u00f1a':'Recover password';
	else if(msg=='msg_select_option')
		i18n_message=lang=='es'?'Seleccione una opci\u00f3n':'Select an option';
	else if(msg=='msg_success')
		i18n_message=lang=='es'?'\u00a1Correcto!':'Success!';
	else if(msg=='msg_user_saved_successfully')
		i18n_message=lang=='es'?'\u00a1Datos almacenados exitosamente!':'User saved successfully!';
	else if(msg=='msg_warning')
		i18n_message=lang=='es'?'\u00a1Advertencia!':'Warning!';
	return i18n_message;
};

function toggleEye(eye,inputname){
	var i=document.getElementById(inputname);
	i.type=i.type==='text'?'password':'text';
	eye.innerHTML='visibility'+(i.type==='text'?'':'_off');
	i.focus();
};

function returntostart(){
	$('#newaccount').animate({left:'400px'},{duration:250}).toggle();
	$('#startpage').animate({left:'0px'},{duration:250});
};

function createAccount(e){
	var err='',tag='',username=$('#username').val(),fname=$('#firstname').val(),lname=$('#lastname').val(),
		pass=$('#createpass').val(),reppass=$('#repeatcreatepass').val(),email=$('#email').val(),firmname=$('#firmname').val(),
		patternmail=/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
		psw=new RegExp('^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$');
	if(username==''){
		tag='#username';
		err=i18n('err_enter_username');
	}else if(username.length<6){
		tag='#username';
		err=i18n('err_username_6characters');
	}else if(fname==''){
		tag='#firstname';
		err=i18n('err_enter_firstname');
	}else if(lname==''){
		tag='#lastname';
		err=i18n('err_enter_lastname');
	}else if(pass==''){
		tag='#createpass';
		err=i18n('err_enter_password');
	}else if(username==pass){
		tag='#createpass';
		err=i18n('err_not_same_user_pass');
	}else if(!psw.test(pass)){
		tag='#createpass';
		err=i18n('err_rewrite_password');
	}else if(reppass==''){
		tag='#repeatcreatepass';
		err=i18n('err_enter_confirmpsw');
	}else if(pass!=reppass){
		tag='#repeatcreatepass';
		err=i18n('err_passwords_not_equal');
	}else if(email==''){
		tag='#email';
		err=i18n('err_enter_email');
	}else if(!patternmail.test(email)){
		tag='#email';
		err=i18n('err_enter_correct_email');
	}else if(firmname==''){
		tag='#firmname';
		err=i18n('err_enter_firm');
	}
	if(err!=''){
		$(tag).parent().addClass('has-error');
		$('#newaccountError').show();
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return;
	}
	$('#newaccountError').hide();
	var param='uname='+username+'&fname='+fname+'&lname='+lname+'&pass='+pass+'&email='+email+'&firmname='+firmname;
	$.ajax({
		type:'POST',
		url:ctx+'/createnewuser',
		data:param,
		async:false,
		success:function(data){
			if(data=='msg_user_saved_successfully'){
				swal({
					title : i18n('msg_success'),
					type : 'success',
					html : i18n(data),
					timer : 3000,
					allowEscapeKey : true
				}, function() {
					returntostart();
				});
				window.setTimeout(function() {
					returntostart();
				}, 2000);
				$('#newaccount input').val('');
			}else{
				$('#putErrorOnAdd').html(i18n(data));
				$('#newaccountError').show();
				$('.custombox-modal-open').animate({scrollTop:0},'1000');
			}
		},error:function(e){
			$('#putEditUserError').html(i18n('err_unable_save')+' (1)');
			$('#newaccountError').show();
			$('.custombox-modal-open').animate({scrollTop:0},'1000');
		}
	});
};

$('input').blur(function(){
	$(this).parent().removeClass('has-error');
});