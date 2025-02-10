;function getCountries(id, elemtype) {
	var elemOp = 'option';
	if (elemtype=='ul' || elemtype=='ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getCountries",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype=='select' || elemtype=='')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].paisid + '" title="'+ info[i].pais + '">'
						+ info[i].pais + '</' + elemOp+ '>');
				$('#' + id).append('<' + elemOp + '></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_country') + '. ' + e);
		}
	});
};

function getEstados(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getStates",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].estadoid + '" title="'+ info[i].estado + '">'
						+ info[i].estado + '</' + elemOp+ '>');
				/*$('#' + id).append('<' + elemOp + ' value=""><b>'+ i18n('msg_addnew') + '</b></'+ elemOp + '>');*/
				$('#' + id).append('<'+ elemOp+ '></' + elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_state') + '. ' + e);
		}
	});
};

function getCiudades(id, elemtype, filterState) {
	var elemOp = 'option', url = 'getCiudades', state = '0';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	if (isNaN(filterState - 0)) {
		state = $('#' + filterState + ' li.selected').val();
		state = state || 0;
		url = 'getCitiesByState';
	}
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/" + url,
		data : 'estadoid=' + state,
		async : false,
		success : function(data) {
			var info = null;
			if (isNaN(filterState - 0))
				info = data[0];
			else
				info = data[0].cdList;
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].ciudadid + '" title="'+ info[i].ciudad + '">'
						+ info[i].ciudad + '</' + elemOp + '>');
				/*$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'City\');"><b>'
					+ i18n('msg_addnew') + '</b></' + elemOp + '>');*/
				$('#' + id).append('<'+ elemOp+ '></' + elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_city') + '. ' + e);
		}
	});
};

/** Obtiene el listado de estados mediante el 'paisid'.
@param countryid	Id name del país o el nombre de la lista (tag-id) donde esta el país seleccionado.
@param setstateid	Id name destino donde se mostrará el listado.
@param elemtype		Tipo de elemento a cargar: "select", "ol" o "ul".	*/
function getStatesByCountry(countryid, setstateid, elemtype) {
	var elemOp = 'option';
	$('#' + setstateid).empty();
	if(isNaN(countryid -0))
		countryid=$('#' + countryid + ' li.selected').val()||'0';
/*	if(countryid==''||countryid=='0'){
		swal(i18n('msg_warning'), i18n('msg_select_country'), "info");
		return;
	}*/
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$.ajax({
		type : "POST",
		url : ctx + "/getStatesByCountry",
		data : 'countryid=' + countryid,
		async : false,
		success : function(data) {
			var info = data[0];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + setstateid).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + setstateid).append('<' + elemOp + ' value="'+ info[i].estadoid + '" title="'+ info[i].estado + '">'
						+ info[i].estado + '</' + elemOp+ '>');
				$('#' + setstateid).append('<' + elemOp + '></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_state') + '. ' + e);
		}
	});
};

/** Obtiene el listado de estados mediante el 'paisid'.
@param stateid		Id name del estado o el nombre de la lista (tag-id) donde esta el estado seleccionado.
@param setcityid	Id name destino donde se mostrará el listado.
@param elemtype		Tipo de elemento a cargar: "select", "ol" o "ul".	*/
function getCitiesByState(stateid, setcityid, elemtype) {
	var elemOp = 'option',state = '0';
	if(elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	state = $('#' + stateid + ' li.selected').val()||'0';
	$('#' + setcityid).empty();
	$.ajax({
		type : 'POST',
		url : ctx + '/getCitiesByState',
		data : 'estadoid=' + state,
		async : false,
		success : function(data) {
			var info=data[0];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + setcityid).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + setcityid).append('<' + elemOp + ' value="'+ info[i].ciudadid + '" title="'+ info[i].ciudad + '">'
						+ info[i].ciudad + '</' + elemOp + '>');
				$('#' + setcityid).append('<'+ elemOp+ '></' + elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_city') + '. ' + e);
		}
	});
};

;function addNewUserCancel() {
	$('.firmdatatabs').hide();
};

function saveNewUser(e) {
	$('input').parent().removeClass('has-error');
	var fname = $('#addFirstName').val(), lname = $('#addLastName').val(), uname = $("#addUserName").val(),
		pass = $("#addpassword").val(), email = $("#addEmail").val(), reppass=$('#addrepeatpass').val(),
		isaddr = $("#isaddr").val(), addc1 = $('#addc1').val(), client = '',
		patternmail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
		psw = new RegExp("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$"), firm = $('#addFirm').val(), err = "", tag='',
		phone=$('#addphone').val(), cellphone=$('#addcellphone').val(),
		address=$('#addaddress').val(), zipcode=$('#addzipcode').val(),
		country=$('#addprofcountry li.selected').val(),state=$('#addprofstate li.selected').val(),
		city=$('#addprofcity li.selected').val(),usertype=$('#addUserType').val(),usrCC='0';
	country=!1===!!+country?'':country;
	state=!1===!!+state?'':state;
	city=!1===!!+city?'':city;
	zipcode=!1===!!+zipcode?'0':zipcode;
	if(fname==''){
		tag='addFirstName';
		err='err_enter_firstname';
	}else if(lname==''){
		tag='addLastName';
		err='err_enter_lastname';
	}else if(uname==''){
		tag='addUserName';
		err='err_enter_username';
	}else if(uname.length<6){
		tag='addUserName';
		err='err_username_6characters';
	}else if(email==''){
		tag='addEmail';
		err='err_enter_email';
	}else if(!patternmail.test(email)){
		tag='addEmail';
		err='err_enter_correct_email';
	}else if(pass==''){
		tag='addpassword';
		err='err_enter_password';
	}else if(!psw.test(pass)){
		tag='addpassword';
		err='err_rewrite_password';
		//err='err_char_contains_password';
	}else if(uname==pass){
		tag='addpassword';
		err='err_not_same_user_pass';
	}else if(pass!=reppass){
		tag='addrepeatpass';
		err='err_passwords_not_equal';
	}else{
		if (typeof $('#addUsrCCList [name=rowline]:checked')[0] != 'undefined')
			usrCC = $('#addUsrCCList [name=rowline]:checked')[0].id;
		usrCC=(typeof usrCC=='undefined')?'':usrCC.replace(/clie/ig,'');
		if(usertype==null&&!(/^\d+$/g).test(usertype)){
			tag='addUserType';
			err='err_select_usertype';
		}else{
			if(usertype==1&&!(/^\d+$/g).test(usrCC)){
				tag='addClient';
				err='err_select_client';
			}
		}
	}
	if(err!=''){
		if(tag!='')
			$('#' + tag).parent().addClass('has-error');
		togglemodtab($('#addtabsmodal >li').get(0),'#addstandard');
		$('#putAddUserError').html(i18n(err));
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		$('#addUserError').show();
		return false;
	}
	var param = "uname="+uname + "&pass="+pass + "&email="+email +"&isaddr="+isaddr
		+"&firstName="+fname + "&lastName="+lname + "&status="+($("#addStatus").val()||'1')
		+"&usertype="+usertype +"&addc1="+addc1 + "&phone="+phone + "&cellphone="+cellphone
		+"&address="+address +"&zipcode="+zipcode + "&country="+country
		+"&state="+state + "&city="+city + "&lang="+getVarURL("language") + "&usrCC="+usrCC;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+='&'+$(this).attr('name')+'='+$(this).val();
	});
	$.ajax({
		type:'POST',
		url:ctx + '/adduser',
		data:param,
		async:false,
		success:function(data){
			if(data=="True"){
				swal(i18n("msg_success"), i18n("msg_user_saved_successfully"), "success");
				window.location = ctx + "/addnewuser" + "?language=" + getLanguageURL();
			}else{
				$('#putAddUserError').html(i18n(data));
				$('#addUserError').show();
			}
		},error:function(er) {
			$('#addUserError').show();
			$('#putAddUserError').html(i18n("err_unable_get_user") + " (1)");
		}
	});
};
	
function deleteUserDetails(userId, user, username, email) {
	$('#delUser').text(user);
	$('#delUserName').text(username);
	$('#delUserEmail').text(email);
	$('#deleteUserButton').attr('onclick', 'deleteUser(' + userId + ')');
};

function deleteUser(userId) {
	$.ajax({
		type:'POST',
		url:ctx + "/deleteuser",
		data:"userId=" + userId,
		async:false,
		success:function(resp) {
			window.location = ctx + "/addnewuser" + "?language=" + getLanguageURL();
		},error:function(resp) {
			swal(i18n("msg_error"), i18n("err_dependence_on_delete") + " (0)", "error");
		}
	});
};

function editUserDetails(userId) {
	document.getElementById('editpassword').type = 'password';
	$('.toggle-input').html('visibility_off');
	$('.firmdatatabs,.clientlist, #editUserError').hide();
	$('#editstandard').show();
	togglemodtab($('#edittabsmodal >li').get(0),'#editstandard');
	$('input, select').parent().removeClass('has-error');
	$.ajax({
		type:"POST",
		url:ctx + "/editUser2",
		data:"userId=" + userId,
		async:false,
		success:function(data){
			var info=data[0].info||[];
			if((info).length>0){
				$('#edit-user-modal input, #edit-user-modal select').val('');
				$('#edit-user-modal ul li').removeClass('selected');
				info=info[0];
				$('#editUserId').val(info.id);
				$('#editFirstName').val(info.first_name);
				$('#editLastName').val(info.last_name);
				$('#editUserName').val(info.username);
				$('#editpassword, #editrepeatpass').val(data[0].psw);
				$('#editEmail').val(info.email);
				$('#iseditr,#editRole').val(info.role);
				$('#editF1,#edc1').val(info.companyid);
				$("#editStatus").val(info.status);
				$("#editUserType").val(info.usertype);
				getClientListCC('editUsrCCList');
				if(info.usertype==1){
					var ccid=info.linkedclientid;
					$('#editClient').val(data[0].clientName);
					$('.clientlist').show();
					$('#clie' + ccid).prop('checked',true);
					$('#clie' + ccid).parent().parent().css('backgroundColor',"ivory");
				}
				$('#editFirm').val(info.company);
				$('#editphoto').val(info.photo_name);
				$('#editphone').val(info.phone);
				$('#editcellphone').val(info.cellphone);
				$('#editaddress').val(info.address);
				$('#editzipcode').val(info.zipcode);
				$('#editUserType').val(info.usertype==null?'':info.usertype);
				getCountries('editcountry','ul');
				if((/^[1-9]\d*$/g).test(info.country)){
					getTextDDFilterByVal('editcountry', info.country);
					if((/^[1-9]\d*$/g).test(info.state)){
						getStatesByCountry('editcountry','editstate','ul')
						getTextDDFilterByVal('editstate', info.state);
						if((/^[1-9]\d*$/g).test(info.city)){
							getCitiesByState('editstate', 'editcity', 'ul')
							getTextDDFilterByVal('editcity', info.city);
						}
					}
				}
			}
		},error:function(data){
			swal(i18n("msg_error"),i18n("err_unable_get_user")+" (4)","error");
		}
	});
	try{
		createDropZoneImg('uploadXEdituser', 'formuseredit',userId,'-10',1);
	}catch (e){
		clearTemp();
		$('#areaEditUserUpload').html('');
		$('#areaEditUserUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'
			+ '<div id="uploadXEdituser" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZoneImg('uploadXEdituser', 'formuseredit',userId,'-10',1);
	}
	$("#uploadXEdituser").addClass("dropzone");
	Dropzone.options.formuseredit = {
		maxFiles: 1,
		accept: function(file, done) {
			done();
		},
		maxfilesexceeded: function(file) {
			this.removeAllFiles();
			this.addFile(file);
		},
		init: function() {
			this.on("maxfilesexceeded", function(file){
				swal(i18n("msg_error"), i18n("max_files_exceeded"), "error");
			});
		}
	};
};

function updateEditUser(e){
	$('input').parent().removeClass('has-error');
	var fname = $('#editFirstName').val(), lname = $('#editLastName').val(), uname = $("#editUserName").val(),
		pass = $("#editpassword").val(), email = $("#editEmail").val(), reppass=$('#editrepeatpass').val(),
		userId= $("#editUserId").val(), iseditr = $("#iseditr").val(), edc1 = $('#edc1').val(),
		patternmail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
		psw = new RegExp("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$"), firm = $('#editFirm').val(), err = "", tag='',
		phone=$('#editphone').val(), cellphone=$('#editcellphone').val(),
		address=$('#editaddress').val(), zipcode=$('#editzipcode').val(),
		country=$('#editcountry li.selected').val(), state=$('#editstate li.selected').val(),
		city=$('#editcity li.selected').val(),usertype=$("#editUserType").val(),usrCC='0';
	country=!1===!!+country?'':country;
	state=!1===!!+state?'':state;
	city=!1===!!+city?'':city;
	zipcode=!1===!!+zipcode?'0':zipcode;
	if(fname==''){
		tag='editFirstName';
		err='err_enter_firstname';
	}else if(lname==''){
		tag='editLastName';
		err='err_enter_lastname';
	}else if(uname==''){
		tag='editUserName';
		err='err_enter_username';
	}else if(uname.length<6){
		tag='editUserName';
		err='err_username_6characters';
	}else if(email==''){
		tag='editEmail';
		err='err_enter_email';
	}else if(!patternmail.test(email)){
		tag='editEmail';
		err='err_enter_correct_email';
	}else if(pass==''){
		tag='editpassword';
		err='err_enter_password';
	}else if(!psw.test(pass)){
		tag='editpassword';
		err='err_rewrite_password';
		//err='err_char_contains_password';
	}else if(uname==pass){
		tag='editpassword';
		err='err_not_same_user_pass';
	}else if(pass!=reppass){
		tag='editrepeatpass';
		err='err_passwords_not_equal';
	}else{
		if (typeof $('#editUsrCCList [name=rowline]:checked')[0] != 'undefined')
			usrCC = $('#editUsrCCList [name=rowline]:checked')[0].id;
		usrCC=(typeof usrCC=='undefined')?'':usrCC.replace(/clie/ig,'');
		if(usertype==null&&!(/^\d+$/g).test(usertype)){
			tag='editUserType';
			err='err_select_usertype';
		}else{
			if(usertype==1&&!(/^\d+$/g).test(usrCC)){
				tag='editClient';
				err='err_select_client';
			}
		}
		if (typeof $('#addUsrCCList [name=rowline]:checked')[0] != 'undefined')
			usrCC = $('#addUsrCCList [name=rowline]:checked')[0].id;
		usrCC=(typeof usrCC=='undefined')?'':usrCC.replace(/clie/ig,'');
	}
	if(err!=''){
		if(tag!='')
			$('#' + tag).parent().addClass('has-error');
		togglemodtab($('#edittabsmodal >li').get(0),'#editstandard');
		$('#putEditUserError').html(i18n(err));
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		$('#editUserError').show();
		return false;
	}
	var param = "userId="+userId + "&uname="+uname + "&pass="+pass + "&email="+email
		+"&iseditr="+iseditr + "&firstName="+fname + "&lastName="+lname
		+"&status="+($("#editStatus").val()||'1') + "&usertype="+usertype +"&edc1="+edc1
		+"&phone="+phone + "&cellphone="+cellphone + "&address="+address +"&zipcode="+zipcode
		+"&country="+country + "&state="+state + "&city="+city
		+"&photo="+$('#editphoto').val() + "&usrCC="+usrCC;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+='&'+$(this).attr('name')+'='+$(this).val();
	});
	$.ajax({
		type:'POST',
		url:ctx + '/updateuser',
		data:param,
		async:false,
		success:function(data){
			if(data=="True"){
				swal(i18n("msg_success"), i18n("msg_user_saved_successfully"), "success");
				window.location = ctx + "/addnewuser" + "?language=" + getLanguageURL();
			}else{
				$('#putEditUserError').html(i18n(data));
				$('#editUserError').show();
			}
		},error:function(er) {
			$('#editUserError').show();
			$('#putEditUserError').html(i18n("err_unable_get_user") + " (5)");
		}
	});
};

function toggleEye(eye, inputname) {
	var i = document.getElementById(inputname);
	i.type = i.type === 'text' ? 'password':'text';
	eye.innerHTML = 'visibility' + (i.type === 'text' ? '':'_off');
	i.focus();
};

//**************** usuarios - clientes (ini)
function getClientListCC(id) {
	$('#' + id + ' tr').remove();
	$.ajax({
		type : "POST",
		url : ctx + "/getClientListCC",
		async : false,
		dataType : 'JSON',
		success : function(data) {
			var info=JSON.parse(data[0].data),lng=Object.keys(info).length;
			if(lng>0){
				var size=Object.keys(info[0]).length,
					tablelist='<tr><th style="display:none;"></th>'
						+'<th>'+i18n('msg_client')+'</th>'
						+'<th>'+(size==6?i18n('msg_firm'):'')+'</th>'
						+'<th>'+i18n('msg_address')+'</th>'
						+'<th>'+i18n('msg_city') +'</th>'
						+'<th>'+i18n('msg_state')+'</th>'
					+'</tr>';
				for (i=0;i<lng;i++) {
					tablelist += '<tr class="rowopt"><td style="display:none;">'
						+'<input type="radio" name="rowline" data-val="'+info[i].client
						+'" id="clie' + info[i].ccid+'"></td>'
						+'<td>'+info[i].client+'</td>'
						+'<td>'+(size==6?info[i].firm:'')+'</td>'
						+'<td>'+info[i].address+'</td>'
						+'<td>'+info[i].city +'</td>'
						+'<td>'+info[i].state+'</td>'
					+'</tr>';
				}
				$('#' + id).append(tablelist);
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_client') + '. ' + e);
		}
	});
};

function i18n(msg){
	var i18n_message="", lang=getLanguageURL();
	// IDIOMAS:si en la URL no contiene la variable "lenguage", asumirá que esta en español
	if(msg=='msg_address')
		i18n_message=lang=='en'?'Address':'Direcci\u00f3n';
	else if(msg=='msg_city')
		i18n_message=lang=='en'?'City':'Ciudad';
	else if(msg=='msg_client')
		i18n_message=lang=='en'?'Client':'Cliente';
	else if(msg=="msg_error")
		i18n_message=lang=="en"?"Error":"Error";
	else if(msg=='msg_firm')
		i18n_message=lang=='en'?'Firm':'Firma';
	else if(msg=="msg_select")
		i18n_message=lang=="en"?"Select...":"Seleccionar...";
	else if(msg=='msg_select_country')
		i18n_message=lang=='en'?'Please, first select a country.':'Seleccione primero un pa\u00eds.';
	else if(msg=="msg_select_role")
		i18n_message=lang=="en"?"Select a role":"Selecciona un rol";
	else if(msg=='msg_select_state')
		i18n_message=lang=='en'?'Please, first select a state.':'Seleccione primero un estado.';
	else if(msg=='msg_state')
		i18n_message=lang=='en'?'State':'Estado';
	else if(msg=="msg_success")
		i18n_message=lang=="en"?"Success!":"\u00a1Correcto!";
	else if(msg=="msg_user_saved_successfully")
		i18n_message=lang=="en"?"User saved successfully!":"\u00a1Usuario guardado exitosamente!";
	else if(msg=="msg_warning")
		i18n_message=lang=="en"?"Warning!":"\u00a1Advertencia!";

	else if(msg=='err_dependence_on_delete')
		i18n_message=lang=='en'?'This item can’t be deleted because it has dependencies!':'\u00a1Este registro no se puede eliminar porque tiene dependencias!';
	else if(msg=='err_contact_admin')
		i18n_message=lang=='en'?'Unable to save data! Please contact the system administrator or your consultant.'
			:'\u00a1No fue posible guardar la informaci\u00f3n! Favor de contactar al administrador del sistema o su consultor.';
	else if(msg=='err_fields_contact_admin')
		i18n_message=lang=='en'?'Unable to save data! Please fill all requiered data or contact the system administrator or your consultant.'
			:'\u00a1No fue posible guardar la informaci\u00f3n! Favor de completar los datos requeridos o contacte al administrador del sistema o su consultor.';
	else if(msg=="err_char_contains_password")
		i18n_message=lang=="en"?"A password contains at least eight characters, including at least one number and one lowercase and"
			+ " one uppercase letters and, at last, one special character: _ . - ^ * #?! @ $ % &"
			:"Una contrase\u00f1a debe contener m\u00ednimo ocho caracteres, al menos un n\u00famero,"
			+ " una letra may\u00fascula, una min\u00fascula y alguno de estos s\u00edmbolos: _ . - ^ * #?! @ $ % &";
	else if(msg=="err_rewrite_password")
		i18n_message=lang=="en"?"A password contains at least eight characters, including at least one number"
			:"La contrase\u00f1a debe contener m\u00ednimo ocho caracteres y al menos un n\u00famero";
	else if(msg=="err_email_exists")
		i18n_message=lang=="en"?"Email already exists in another user!":"\u00a1El correo electr\u00f3nico ya existe en otro usuario!";
	else if(msg=="err_enter_correct_email")
		i18n_message=lang=="en"?"Please enter correct email address!"
			:"\u00a1Favor de ingresar una direcci\u00f3n de correo electr\u00f3nico v\u00e1lida!";
	else if(msg=="err_enter_email")
		i18n_message=lang=="en"?"Please enter email!":"\u00a1Favor de ingresar un correo electr\u00f3nico!";
	else if(msg=="err_enter_firstname")
		i18n_message=lang=="en"?"Please enter firstname!":"\u00a1Favor de ingresar el nombre(s)";
	else if(msg=="err_enter_lastname")
		i18n_message=lang=="en"?"Please enter lastname!":"\u00a1Favor de ingresar el apellido(s)";
	else if(msg=="err_enter_password")
		i18n_message=lang=="en"?"Please enter password!":"\u00a1Favor de ingresar la contrase\u00f1a!";
	else if(msg=="err_enter_username")
		i18n_message=lang=="en"?"Please enter username!":"\u00a1Favor de ingresar el nombre de usuario!";
	else if(msg=="err_firm_not_found")
		i18n_message=lang=="en"?"Firm not found!":"Firma no encontrada";
	else if(msg=="err_not_same_user_pass")
		i18n_message=lang=="en"?"The password must be different from about the user name!"
			:"\u00a1La contrase\u00f1a debe ser distinta al nombre de usuario!";
	else if(msg=='msg_empty_data')
		i18n_message=lang=='en'?'Please fill all required data!':'\u00a1Favor de completar los datos requeridos!';
	else if(msg=='err_select_client')
		i18n_message=lang=='en'?'Please select a client!':'\u00a1Favor de seleccionar un cliente!';
	else if(msg=='err_unable_get_city')
		i18n_message=lang=='en'?'Unable to get city name!':'\u00a1No fue posible obtener la ciudad!';
	else if(msg=='err_unable_get_client')
		i18n_message=lang=='en'?'Unable to get client! We suggest to re-login or contact your administrator':'\u00a1No fue posible obtener el cliente! Sugerimos reiniciar sesi\u00f3n o contactar a su administrador';
	else if(msg=='err_unable_get_country')
		i18n_message=lang=='en'?'Unable to get country!':'\u00a1No fue posible obtener el pa\u00eds!';
	else if(msg=="err_unable_get_role")
		i18n_message=lang=="en"?"Unable to get role record!":"\u00a1No fue posible obtener el registro del rol!";
	else if(msg=='err_unable_get_state')
		i18n_message=lang=='en'?'Unable to get state!':'\u00a1No fue posible obtener el estado!';
	else if(msg=="err_unable_get_user")
		i18n_message=lang=="en"?"Unable to get user record!":"\u00a1No fue posible obtener el registro del usuario!";
	else if(msg=="err_user_exists")
		i18n_message=lang=="en"?"User Name already exists!":"\u00a1El Nombre de Usuario ya existe!";
	else if(msg=="err_user_exists_red")
		i18n_message=lang=="en"?"The user does not exist!":"\u00a1El usuario no se encontr\u00f3!";
	else if(msg=="err_username_6characters")
		i18n_message=lang=="en"?"Please enter username with at least 6 characters!"
			:"\u00a1Favor de ingresar un nombre de usuario con al menos 6 caracteres!";
	else if(msg=='err_select_usertype')
		i18n_message=lang=='en'?'Please select the user type!':'\u00a1Favor de seleccionar el tipo de usuario!';

    else if(msg=='max_files_exceeded')
  		i18n_message=lang=='en'?'Not can upload more files.':'No es posible subir m\u00e1s archivos.';
  	else if(msg=='confirm_delete_file')
  		i18n_message=lang=='en'?'In addition to deleting the file, this change is saved and you will no longer be able to recover it. Do you want to continue?.'
  			:'Adem\u00e1s de eliminar el archivo, se guarda este cambio y ya no podr\u00e1 recuperarlo \u00bfdesea continuar?';
  	else if(msg=='delete_label')
  		i18n_message=lang=='en'?'Delete':'Eliminar';	
  	else if(msg=='msg_upload_area')
  		i18n_message=lang=='en'?'Drag and drop the file into the dotted area':'Arrastra y suelta el archivo en la zona punteada';

  	else if(msg=='err_passwords_not_equal')
		i18n_message=lang=='es'?'\u00a1La contrase\u00f1a y la confirmaci\u00f3n de contrase\u00f1a son diferentes!':'Password and password confirmation are different!';
	return i18n_message;
};

$('#newuser').on('click',function(){
	$('#formaddprofile')[0].reset();
	document.getElementById('addpassword').type = 'password';
	$('.toggle-input').html('visibility_off');
	$('.firmdatatabs, .clientlist').hide();
	$('#addstandard').show();
	togglemodtab($('#addtabsmodal >li').get(0),'#addstandard');
	
	$('#addstandard').show();
	$('#addUserError').hide();
	var p=document.getElementById('addpassword');
	p.type = 'password';
	getCountries('addprofcountry','ul');
	getEstados('addprofstate', 'ul');

	getClientListCC('addUsrCCList');
	$('.toggle-input').html('visibility_off');
	try{
		createDropZoneImg('uploadXAdduser', 'formaddprofile','','',1);
	}catch (e){
		$('#areaAddUserUpload').html('');
		$('#areaAddUserUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'
			+ '<div id="uploadXAdduser" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZoneImg('uploadXAdduser', 'formaddprofile','','',1);
	}
	$("#uploadXAdduser").addClass("dropzone");
	Dropzone.options.formaddprofile = {
		maxFiles: 1,
		accept: function(file, done) {
			done();
		},
		maxfilesexceeded: function(file) {
			this.removeAllFiles();
			this.addFile(file);
		},
		init: function() {
			this.on("maxfilesexceeded", function(file){
				swal(i18n("msg_error"), i18n("max_files_exceeded"), "error");
			});
		}
	};
	$('.custombox-modal-wrapper >.custombox-modal-wrapper-fadein >.custombox-modal-open').css('margin-top','5% !important}');
});

$('#addUserType').on('change', function(e) {
	if($(this).val()==0)
		$('.clientlist').fadeOut(250);
	else
		$('.clientlist').slideDown(250);
});

$('#editUserType').on('change', function(e) {
	if($(this).val()==0)
		$('.clientlist').fadeOut(250);
	else
		$('.clientlist').slideDown(250);
});

document.addEventListener('click',function(e) {
	if (e.target.nodeName == "TD") {
		var parentid=e.target.parentElement.parentElement.parentElement.id,
			applyElem=['addUsrCCList','editUsrCCList'];
		if (applyElem.indexOf(parentid)<0)
			return;
		$('.rowopt').css('backgroundColor', '#fff');
		e.target.parentElement.style.backgroundColor = "ivory";
		var fistEl = e.target.parentElement.firstElementChild.firstElementChild;
		fistEl.checked = true;

		if(parentid=='addUsrCCList'){
			$('.containTL').hide();
			var rb = $('#addUsrCCList [name=rowline]:checked');
			var text = $(rb).attr('data-val');
			$('#addClient').val(text);
		}else if(parentid=='editUsrCCList'){
			$('.containTL').hide();
			var rb = $('#editUsrCCList [name=rowline]:checked');
			var text = $(rb).attr('data-val');
			$('#editClient').val(text);
		}
	}
}, false);

$('#addClient').on('focus keyup input paste change delete', function(e) {
	filtext(e.target.value, '#addUsrCCList');
	$('.containTL').show();
});

$('#editClient').on('focus keyup input paste change delete', function(e) {
	filtext(e.target.value, '#editUsrCCList');
	$('html, body').animate({scrollTop : 0}, '300');
	var id = e.target.id, input, filter, table, tr, i;
	input = e.target;
	filter = input.value.toUpperCase();
	table = document.getElementById(id + 'List');
	tr = $('#c' + id + 'List .rowopt');
	for (i = 0; i < tr.length; i++)
		if (tr[i].innerText.toUpperCase().indexOf(filter) >= 0)
			tr[i].style.display = "";
		else
			tr[i].style.display = "none";
	$('.containTL').show();
});

//***************** usuarios clientes (fin)

/**
@param e	Evento del objeto o keyCode.
@param id	Id único, objeto o nombre de Grupo de elementos a procesar.
@param rb	Grupo de checkbox/radio a tomar valores.	*/
function applycourtfilter(e,id,rb){
	var k=e.key||e.keyCode||e.which||e,txt='';
	if(k==='Enter'||k===13){
		if(rb!=''&&rb!=null){
			var num=$('#'+id).val();
			txt=$('input[name='+id+'opt]:checked').attr('title');
			if(txt==null)return;
			$('label[for='+id+']').text(i18n('msg_numberof')+' '+txt);
			if(num=='')return;
			txt+=' '+num;
		}else{
			if(typeof e==='string'){
				txt=id.target.title;
				id=$(id.target).parent()[0].id;
			}else if(e.target.tagName=='INPUT'){
				txt=$('#'+id+' li.selected').html();
			}
			$('#mf-'+id).slideUp();
		}
		if(txt=='')return;
		$('#mf-clear-'+id).show();
		$('#span-'+id).html(txt);
	}
};

/*
$('#addprofstate').on('focus', function(){
	getStatesByCountry('addprofcountry', 'addprofstate', 'ul');
});

$('#addprofcity').on('focus', function(){
	getCitiesByState('addprofstate', 'addprofcity', 'ul')
});

$('#editprofstate').on('focus', function(){
	getStatesByCountry('editcountry', 'editstate', 'ul');
});

$('#editprofcity').on('focus', function(){
	getCitiesByState('editstate', 'editcity', 'ul')
});
*/
$('#savenewuser').on('click', function(){
	saveNewUser(this);
});

$('#btn-editupdate').on('click', function(){
	updateEditUser(this);
});

$(document).ready(function() {
	$(".modal-demo").on('hide.bs.modal', function() {
		clearTemp();
	});
});

$(function(){
	$(document).on('click', '.ddListImg li', function(e){
		applycourtfilter('Enter',e);
	});
});