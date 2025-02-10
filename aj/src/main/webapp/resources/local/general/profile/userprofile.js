;/** Obtiene el listado de estados mediante el 'paisid'.
@param countryid	Puede ser el id del país o el nombre de la lista (tag-id) donde esta el país seleccionado.
@param setstateid	Nombre/id del elemento donde se mostrará el listado.
@param elemtype		Tipo de elemento a cargar; "select", "ol" o "ul".	*/
function getStatesByCountry(countryid, setstateid, elemtype){
	var elemOp = 'option';
	$('#' + setstateid).empty();
	$('#ErrorSelectSomething').hide();
	if(isNaN(countryid -0))
		countryid=$('#' + countryid + ' li.selected').val()||'';
	if(countryid==''||countryid=='0'){
	 	$('#ErrorSelectSomething').show();
		$('#putErrorSelectSomething').html(i18n('msg_select_country'));	
		return;
	}
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$.ajax({
		type : "POST",
		url : ctx + "/getStatesByCountry",
		data : 'countryid=' + countryid,
		async : false,
		success : function(data){
			var info = data[0];
			if (info.length > 0){
				if (elemtype == 'select' || elemtype == '')
					$('#' + setstateid).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + setstateid).append('<' + elemOp + ' value="'+ info[i].estadoid + '" title="'+ info[i].estado + '">'
						+ info[i].estado + '</' + elemOp+ '>');
				$('#' + setstateid).append('<' + elemOp + '></'+ elemOp + '>');
			}
		},error : function(e){
			swal(i18n("msg_error"), i18n("err_unable_get_state") + '. ' + e, "error");
		}
	});
};

/** Obtiene el listado de estados mediante el 'paisid'.
@param stateid		Id del estado o el nombre de la lista (tag-id) donde esta el estado seleccionado.
@param setcityid	Nombre/id del elemento donde se mostrará el listado.
@param elemtype		Tipo de elemento a cargar; "select", "ol" o "ul".	*/
function getCitiesByState(setcityid, elemtype, stateid){
	var elemOp = 'option';
	$('#' + setcityid).empty();
	$('#ErrorSelectSomething').hide();
	if(isNaN(stateid -0))
		stateid=$('#' + stateid + ' li.selected').val()||'';
	if(stateid==''||stateid=='0'){
		$('#ErrorSelectSomething').show();
		$('#putErrorSelectSomething').html(i18n('msg_select_state'));	
		return;
	}
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$.ajax({
		type: "POST",
		url : ctx + "/getCitiesByState",
		data: "estadoid=" + stateid,
		async:false,
		success : function(data){
			var info = data[0];
			if (info.length > 0){
				if (elemtype == 'select' || elemtype == '')
					$('#' + setcityid).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + setcityid).append('<' + elemOp + ' value="'+ info[i].ciudadid + '" title="'+ info[i].ciudad + '">'
						+ info[i].ciudad + '</' + elemOp+ '>');
				$('#' + setcityid).append('<' + elemOp + '></'+ elemOp + '>');
			}
		},error : function(e){
			swal(i18n("msg_error"), i18n("err_unable_get_city") + '. ' + e, "error");
		}
	});
};

function getUserInfoProfile(){
	$.ajax({
		type:'POST',
		url:ctx + '/getUserInfoProfile',
		async:false,
		success:function(data){
			var info=data[0].info;
			if(info.length>0){
				if(!isNaN(info[0].country-0))
					getTextDDFilterByVal('country', info[0].country);
				else
					setValDDFilterByText('country', info[0].country);

				getStatesByCountry('country', 'state', 'ul');
				if(!isNaN(info[0].state-0))
					getTextDDFilterByVal('state', info[0].state);
				else
					setValDDFilterByText('state', info[0].state);

				getCitiesByState('city', 'ul', 'state');
				if(!isNaN(info[0].city-0))
					getTextDDFilterByVal('city', info[0].city);
				else
					setValDDFilterByText('city', info[0].city);

				validDDFilter();
			}
		},error:function(er){
			swal-+(i18n("msg_error"), i18n("err_unable_get_user") + " (1) " + er, "error");
		}
	});
};

function savechanges(){
	$('input').parent().removeClass('has-error');
	var tag='', fname = $('#first_name').val(), lname = $('#last_name').val(),
		zipcode=($("#zipcode").val()).replace(/[^0-9]+/g,""),
		country=$("#country li.selected").val(), state=$("#state li.selected").val(),
		city=$("#city li.selected").val(), err = "";
	country=!1===!!+country?'':country;
	state=!1===!!+state?'':state;
	city=!1===!!+city?'':city;
	zipcode=!1===!!+zipcode?'0':zipcode;
	if(fname == ''){
		tag='first_name';
		err = "err_enter_firstname";
	}else if(lname == ''){
		tag='last_name';
		err = "err_enter_lastname";
	}
	if(err!=''){
		if(tag!='')
			$('#' + tag).parent().addClass('has-error');
		window.scrollTo(0,0);
		swal(i18n("msg_error"), i18n(err), "error");
		return;
	}
	var param = "lang="+($('input[name=flaglang]:checked').val())
		+ "&firstName="+fname + "&lastName="+lname + '&photo='+$('#photo').val()
		+ "&phone="+$("#phone").val() + "&cellphone="+$("#cellphone").val()
		+ "&address="+$("#address").val() + "&zipcode="+zipcode
		+ "&country="+country + "&state="+state + "&city="+city;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+='&'+$(this).attr('name')+'='+$(this).val();
	});
	$.ajax({
		type:'POST',
		url:ctx + '/updateProfile',
		data:param,
		async:false,
		success:function(data){
			if(data == "True"){
				swal(i18n("msg_success"), i18n("msg_user_saved_successfully"), "success");
				window.location = ctx + "/profile" + "?language=" + $('input[name=flaglang]:checked').val();
			}else{
				swal(i18n("msg_error"), i18n(data), "error");
			}
		},error:function(er){
			swal(i18n("msg_error"), i18n("err_unable_get_user") + " (1) " + er, "error");
		}
	});
};

function i18n(msg){
	var i18n_message = "", lang = getLanguageURL();
	// IDIOMAS:si en la URL no contiene la variable "lenguage", asumirá que esta en español
	if(msg=='msg_empty_data')
		i18n_message=lang=='en'?'Please fill all required data!':'\u00a1Favor de completar los datos requeridos!';

	else if(msg == "err_enter_firstname")
		i18n_message = lang == "en"?"Please enter firstname!":"\u00a1Favor de ingresar el nombre(s)";
	else if(msg == "err_enter_lastname")
		i18n_message = lang == "en"?"Please enter lastname!":"\u00a1Favor de ingresar el apellido(s)";
	else if(msg=='err_on_save')
		i18n_message=lang=='en'?'Unable to save registry!':'\u00a1No fue posible guardar el registro!';
	else if(msg=='err_unable_get_city')
		i18n_message=lang=='en'?'Unable to get city name!':'\u00a1No fue posible obtener la ciudad!';
	else if(msg=='err_unable_get_country')
		i18n_message=lang=='en'?'Unable to get country!':'\u00a1No fue posible obtener el pa\u00eds!';
	else if(msg=='err_unable_get_state')
		i18n_message=lang=='en'?'Unable to get state!':'\u00a1No fue posible obtener el estado!';
	else if(msg == "err_unable_get_user")
		i18n_message = lang == "en"?"Unable to get user record!":"\u00a1No fue posible obtener el registro del usuario!";
	
	else if(msg == "msg_error")
		i18n_message = lang == "en"?"Error":"Error";
	else if(msg=='msg_select')
		i18n_message=lang=='en'?'Select...':'Seleccionar...';
	else if(msg=='msg_select_country')
		i18n_message=lang=='en'?'Please, first select a country.':'Seleccione primero un pa\u00eds.';
	else if(msg=='msg_select_state')
		i18n_message=lang=='en'?'Please, first select a state.':'Seleccione primero un estado.';
	else if(msg == "msg_success")
		i18n_message = lang == "en"?"Success!":"\u00a1Correcto!";
	else if(msg == "msg_user_saved_successfully")
		i18n_message = lang == "en"?"User saved successfully!":"\u00a1Usuario guardado exitosamente!";
	else if(msg == "msg_warning")
		i18n_message = lang == "en"?"Warning!":"\u00a1Advertencia!";

    else if(msg=='max_files_exceeded')
  		i18n_message=lang=='en'?'Not can upload more files.':'No es posible subir m\u00e1s archivos.';
    else if(msg=='delete_label')
  		i18n_message=lang=='en'?'Delete':'Eliminar';
  	else if(msg=='msg_upload_area')
  		i18n_message=lang=='en'?'Drag and drop the file into the dotted area':'\u00a1Arrastra y suelta el archivo en la zona punteada';
	return i18n_message;
};

$('#profstate').on('focus', function(){
	getStatesByCountry('country', 'state', 'ul');
});

$('#profcity').on('focus', function(){
	getCitiesByState('city', 'ul', 'state');
});

$('#savechanges').on('click', function(){
	savechanges();
});

$(document).ready(function(){
	var elemOp = 'option', elemOp = 'li', id = 'country', elemtype = 'ul';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getCountries",
		async : false,
		success : function(data){
			var info = data[0];
			if (info.length > 0){
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].paisid + '" title="'+ info[i].pais + '">'
						+ info[i].pais + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ ' ></'+ elemOp + '>');
			}
		},error : function(e){
			swal(i18n("msg_error"), i18n("err_unable_get_country"), "error");
		}
	});
	getUserInfoProfile();
	var observer = new MutationObserver(function(mutations) {
	    mutations.forEach(function(mutation) {
	        console.log(mutation)
	        if (mutation.addedNodes && mutation.addedNodes.length > 0) {
	            // element added to DOM
	            var hasClass = [].some.call(mutation.addedNodes, function(el) {
	                return el.classList.contains('dz-started')
	            });
	            if(hasClass){
	                $('#areaEditUserUpload >span').show();
	            }else{
	            	$('#areaEditUserUpload >span').hide();
	            }
	        }
	    });
	});
	var config = {
	    attributes: true,
	    childList: true,
	    characterData: true
	};
	observer.observe(document.getElementById('uploadXEdituser'), config);
	$('.dz-remove').on('click',function(){
		$('#areaEditUserUpload >span').show();
	})
	$('#areaEditUserUpload >span').css('display',(($('#photo').val()=='')?'block':'none'));
});