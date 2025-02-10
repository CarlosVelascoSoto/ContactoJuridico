;function getCountries(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getCountries",
		async : false,
		success : function(data) {
			var info = data[0];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
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

/** Obtiene el listado de estados mediante el 'paisid'.
@param countryid	Id name del país o el nombre de la lista (tag-id) donde esta el país seleccionado.
@param setstateid	Id name destino donde se mostrará el listado.
@param elemtype		Tipo de elemento a cargar: "select", "ol" o "ul".	*/
function getStatesByCountry(countryid, setstateid, elemtype) {
	var elemOp = 'option';
	$('#' + setstateid).empty();
	if(isNaN(countryid -0))
		countryid=$('#' + countryid + ' li.selected').val()||'';
	if(countryid==''||countryid=='0'){
		swal(i18n('msg_warning'), i18n('msg_select_country'), "info");
		return;
	}
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
	var elemOp = 'option';
	$('#' + setcityid).empty();
	if(isNaN(stateid -0))
		stateid=$('#' + stateid + ' li.selected').val()||'';
	if(stateid==''||stateid=='0'){
		swal(i18n('msg_warning'), i18n('msg_select_state'), "warning");
		return;
	}
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$.ajax({
		type : 'POST',
		url : ctx + '/getCitiesByState',
		data : 'estadoid=' + stateid,
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

function getCompanies(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getCompanies",
		async:false,
		success:function(data){
			var info=data[0];
			if(info.length>0){
				var option=new Option(i18n('msg_select'),'',!1,!0);
				$('#'+id).append(option);
				$(option).attr('disabled','disabled');
				for(i=0;i<info.length;i++){
					option=new Option(info[i].company,info[i].companyid,!0,!1);
					$('#'+id).append(option);
					$(option).attr('title',info[i].company);
				}
			}
		},error:function(e){
			console.log("Error al obtener Compa\u00f1ias. "+e);
		}
	});
};

function getMatterMultiList(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getMatterList",
		async:false,
		success:function(data){
			var info=data[0], multi=document.getElementById(id);;
			if(info.length>0)
				for(i=0;i<info.length;i++)
					multi.options[multi.options.length] = new Option(info[i].materia, info[i].materiaid, 0);
		},error:function(e){
			console.log("Error al obtener Materias. "+e);
		}
	});
//TODO
	var imgfind=new Image();
	imgfind.src='data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4IiB3aWR0aD0iNTg3LjQ3MXB4IiBoZWlnaHQ9IjU4Ny40NzFweCIgdmlld0JveD0iMCAwIDU4Ny40NzEgNTg3LjQ3MSIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTg3LjQ3MSA1ODcuNDcxOyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+PGc+PGc+PHBhdGggZD0iTTIyMC4zMDIsNDQwLjYwNGMxMjEuNDc2LDAsMjIwLjMwMi05OC44MjYsMjIwLjMwMi0yMjAuMzAyQzQ0MC42MDQsOTguODI2LDM0MS43NzcsMCwyMjAuMzAyLDBDOTguODI2LDAsMCw5OC44MjYsMCwyMjAuMzAyQzAsMzQxLjc3Nyw5OC44MjYsNDQwLjYwNCwyMjAuMzAyLDQ0MC42MDR6IE0yMjAuMzAyLDcxLjE0MmM4Mi4yNDcsMCwxNDkuMTU5LDY2LjkxMywxNDkuMTU5LDE0OS4xNTljMCw4Mi4yNDgtNjYuOTEyLDE0OS4xNi0xNDkuMTU5LDE0OS4xNnMtMTQ5LjE2LTY2LjkxMi0xNDkuMTYtMTQ5LjE2QzcxLjE0MiwxMzguMDU1LDEzOC4wNTUsNzEuMTQyLDIyMC4zMDIsNzEuMTQyeiIvPjxwYXRoIGQ9Ik01MjUuNTIzLDU4Ny40NzFjMTYuNTU1LDAsMzIuMTEzLTYuNDQ3LDQzLjgwMS0xOC4xNThjMTEuNjk5LTExLjY4LDE4LjE0Ni0yNy4yMzQsMTguMTQ2LTQzLjc5MWMwLTE2LjU1My02LjQ0Ny0zMi4xMTUtMTguMTUyLTQzLjgyMkw0NDYuNjQzLDM1OS4wMjNjLTMuMjYyLTMuMjYyLTcuNDc1LTUuMDYxLTExLjg1OS01LjA2MWMtNS40NDksMC0xMC40NjUsMi43MTEtMTMuNzYyLDcuNDM4Yy0xNi4yMzgsMjMuMzE4LTM2LjI5Nyw0My4zNzctNTkuNjEzLDU5LjYxNWMtNC4yNTgsMi45NjUtNi45NDcsNy40NjctNy4zNzksMTIuMzUyYy0wLjQyOCw0LjgyOCwxLjM5Myw5LjY2Niw0Ljk5OCwxMy4yN2wxMjIuNjc0LDEyMi42NzZDNDkzLjQwNiw1ODEuMDIzLDUwOC45NjksNTg3LjQ3MSw1MjUuNTIzLDU4Ny40NzF6Ii8+PC9nPjwvZz48Zz48L2c+PGc+PC9nPjxnPjwvZz48Zz48L2c+PGc+PC9nPjxnPjwvZz48Zz48L2c+PGc+PC9nPjxnPjwvZz48Zz48L2c+PGc+PC9nPjxnPjwvZz48Zz48L2c+PGc+PC9nPjxnPjwvZz48L3N2Zz4=';
	$('.lcslt-search-li').css("background-image", "url('" + imgfind.src + "')");
};

function getSocNetworks(id,elemtype){
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getSocNetworks",
		async : false,
		success : function(data) {
			var info = data[0];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for(i=0;i<info.length;i++){
					var im=info[i].imageurl?(info[i].imageurl).replace(/^.*(doctos\/.*$)/,'$1'):'';
					$('#'+id).append('<' + elemOp + ' value="'+ info[i].socialnetworkid + '" title="'+ info[i].mainurl + '" '
						+'data-imagesrc="'+im+'" data-description="'+(info[i].mainurl?info[i].mainurl:'')+'">'
						+ info[i].socialnetwork + '</' + elemOp+ '>');
				}
				$('#' + id).append('<' + elemOp + '></'+ elemOp + '>');
			}
		},error:function(e){
			console.log("Error al obtener redes sociales. "+e);
		}
	});
};

function getCommTypes(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getCommTypesList",
		async : false,
		success : function(data) {
			var info = data[0];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].commtypeid + '" title="'+ info[i].description + '">'
						+ info[i].description + '</' + elemOp+ '>');
				$('#' + id).append('<' + elemOp + '></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_commtype') + '. ' + e);
		}
	});
};

function getCommLabels(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getCommLabelList",
		async : false,
		success : function(data) {
			var info = data[0];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].commlabelid + '" title="'+ info[i].commlabelname + '">'
						+ info[i].commlabelname + '</' + elemOp+ '>');
				$('#' + id).append('<' + elemOp + '></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_commlabel') + '. ' + e);
		}
	});
};

/** @param addedit 	Modal activo. Para nuevo siempre vacio, para modificaciones: 'edit'.	*/
function addlabTo(addedit){
	var recid=$('#'+addedit+'recid').val(),
		commtype=$('#'+addedit+'commtypeid li.selected'),commlabel=$('#'+addedit+'commlabelid li.selected'),
		contactinfo=$('#'+addedit+'contactinfo').val(),addtionalinfo=$('#'+addedit+'addtionalinfo').val(),
		socialnetwork=$('#'+addedit+'socialnetwork li.selected').val()||'';
	if(!commtypeid)commtypeid='0';
	if(!commlabelid)commlabelid='0';
	var vlab=vlawyerAddrBook('',$(commtypeid).val(),$(commlabelid).val(),contactinfo,addtionalinfo,socialnetwork);
	if(vlab.err!=''){
		var onAddEdit=addedit==''?'Add':'Edit';
		togglemodtab($($('#'+addedit+'addtabsmodal li:eq(2)')),'#'+addedit+'addcontactdata');
		$('#' + vlab.tag).parent().addClass('has-error');
		$('#errorOn'+onAddEdit).show();
		$('#putErrorOn'+onAddEdit).html(vlab.err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		$('#'+addedit+'recid').val('');
		return false;
	}
	contactinfo=contactinfo+((addtionalinfo=='')?addtionalinfo:' ('+addtionalinfo+')');
	if(recid==''){
		var tablerow='<tr>'+'<td class="dnone" data-commid=""></td>'
			+'<td data-commtypeid="'+$(commtype).val()+'">'+$(commtype).text()+'</td>'
			+'<td data-commlabelid="'+$(commlabel).val()+'">'+$(commlabel).text()+'</td>'
			+'<td data-addtionalinfo="'+addtionalinfo+'">'+contactinfo+'</td>'
			+'<td><span class="asLink" data-recid="" onclick="retrieveLAB(\'\',this);">'
			+ '<i class="md md-edit"></i></span></td>'
			+'<td><span class="asLink" onclick="deleteLAB(this);"><i class="md md-delete" style="font-size:20px"></span></td>';
		+'</tr>';
		$('#'+addedit+'tablecomm tbody').append(tablerow);
	}else{
		var t='#'+addedit+'tablecomm tbody tr:eq('+recid+') td:eq(';
		$(t+'1)').text($(commtype).text());$(t+'1)').data('commtypeid',$(commtype).val());
		$(t+'2)').text($(commlabel).text());$(t+'2)').data('commlabelid',$(commlabel).val());
		$(t+'3)').text(contactinfo);$(t+'3)').data('addtionalinfo',addtionalinfo);
	}
	$('#'+addedit+'recid').val('');
	$('#'+addedit+'lablist, #'+addedit+'capturelab').toggle();
};

/** Validación de forma de contacto.
	@param addedit 	Modal activo. Para nuevo siempre vacio, para modificaciones: 'edit'.
	@param commtypeid		Id de tipo de comunicación.
	@param commlabelid		Id de tipo de etiqueta.
	@param contactinfo		Texto con información de contacto.
	@param addtionalinfo	Texto con la información adicional.
	@param socialnetwork	Red social seleccionada (sólo si ha sido elegida).
	@returns {JSON}	En caso de existir errore, retorna el mensaje de error y el objeto donde se produjo.	
 */
function vlawyerAddrBook(addedit,commtypeid,commlabelid,contactinfo,addtionalinfo,socialnetwork){
	var tag='contactinfo',err='';
	if(contactinfo=='' && (commtypeid!='0' || commlabelid!='0')){
		err='err_enter_contactinfo';
	}else if(contactinfo!=''){
		if(commtypeid=='0'){
			tag='commtypeid';
			err='err_select_commtype';
		}else if(commlabelid=='0'){
			tag='commlabelid';
			err='err_select_label';
		}else{
			if(contactinfo.indexOf('@')>0){
				if(contactinfo.match(/^(?!\s)[\w\d!#$%&'*+/=?`{|}~^-]+(?:\.[\w\d!#$%&'*+/=?`{|}~^-]+)*@(?:[\w\d-]+\.)+[\w\d]{2,6}$/g)==null)
					err='err_enter_email';
			}else if(['htt','ftp','smt','www'].indexOf(contactinfo.substring(0,3))>=0){
				if(contactinfo.match(/^((https?|ftp|smtp):\/\/)?(www.)?[a-zA-Z0-9_~+:-]+\.[a-zA-Z0-9_-]{2,}(\/[a-zA-Z0-9_~+:#-]+\/?)*(\?.+)?$/g)==null)
					err='err_enter_webpage';
			}
			if(($('#commtypeid li.selected').text()).toLowerCase().indexOf('social')>=0)
				if(socialnetwork==''){
					tag='socialnetwork';
					err='err_select_socialnetwork';
				}
		}
	}
	return {'err':i18n(err),'tag':tag,'errtab':'2'};
};

/** Obtiene el registro de medio de comunicación.
	@param	addedit		Modal activo. Para nuevo siempre vacio, para modificaciones: 'edit'.
	@param	obj			Debe ser "this".
	@param	recid		(opcional) Id del registro ya almacenado.	*/
function retrieveLAB(addedit,obj){
	getCommTypes(addedit+'commtypeid', 'ul');
	getCommLabels(addedit+'commlabelid', 'ul');
	getSocNetworks(addedit+'socialnetwork', 'ul');
	var t='#'+addedit+'tablecomm tbody tr:eq('+$(obj).parent().parent().index()+') ';
	var addtionalinfo=$(t+'td:eq(3)').data('addtionalinfo'),info=$(t+'td:eq(3)').text();
	var rex=new RegExp('(.*)(\\s\\('+addtionalinfo+'.*\\))', 'g');
	getTextDDFilterByVal(addedit+'commtypeid', $(t+'td:eq(1)').data('commtypeid'));
	getTextDDFilterByVal(addedit+'commlabelid', $(t+'td:eq(2)').data('commlabelid'));
	$('#'+addedit+'contactinfo').val(info.replace(rex,'$1'));
	$('#'+addedit+'addtionalinfo').val(addtionalinfo);
	$('#'+addedit+'lablist, #'+addedit+'capturelab').fadeToggle();
	if($('#'+addedit+'socialnetwork').is(':visible'))
		getTextDDFilterByVal(addedit+'socialnetwork', $(t+'td:eq(2)').data('commlabelid'));
	$('#'+addedit+'recid').val($(obj).parent().parent().index());
};

function deleteLAB(o){
	swal({
		title:i18n('msg_are_you_sure'),
		text:i18n('msg_sure_to_delete'),
		type:"warning",
		showCancelButton:true,
		confirmButtonClass:'btn-warning',
		confirmButtonText:i18n('btn_yes_delete_it'),
		closeOnCancel:false
	}).then((isConfirm) => {
		if(isConfirm)
			$(o.parentElement.parentElement).remove();
		else
			swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "warning");
	});
};

function togglemodtab(e,tab){
	if($(e).hasClass('selectedtab'))return;
	$('.tabsmodal li').removeClass('selectedtab');
	if(typeof e=='string')
		e=$(e+' li:first-child');
	$(e).addClass('selectedtab');
	$('.firmdatatabs').hide();
	$(tab).show();
};

/**Obtiene el listado de contactos
@param dataAttr		Nombre del data donde incluirá los datos, ej:
					getCommLabelList('[data-sel="commlab"]','ul');
@param elemtype		Tipo de elemento a cargar; "select", "ol" o "ul".	*/
function getCommLabelList(dataAttr, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$.ajax({
		type : "POST",
		url : ctx + "/getCommLabelList",
		async : false,
		success : function(data) {
			var info = data[0], commLists=$(dataAttr);
			for(c=0;c<commLists.length;c++){
				var id=commLists[c];
				$(id).empty();
				if (info.length > 0) {
					if (elemtype == 'select' || elemtype == '')
						$(id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
					for (i = 0; i < info.length; i++)
						$(id).append('<' + elemOp + ' value="'+ info[i].commlabelid + '" title="'+ info[i].commlabelname + '">'
							+ info[i].commlabelname + '</' + elemOp+ '>');
					$(id).append('<' + elemOp + '>(' + i18n('msg_none') + ')</'+ elemOp + '>');
				}else{
					$(id).append('<' + elemOp + '>' + i18n('msg_no_data') + '</'+ elemOp + '>');
				}
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_commlabel') + '. ' + e);
		}
	});
};

function addLawyerDir(){
	$('input, select').parent().removeClass('has-error');
	var err='',tag = '',errtab='',
		jobpos=$('#jobposition').val(),matspec=$('#speciality').val()||'',
		firstname=$('#firstname').val(),lastname=$('#lastname').val(),firmname=$('#firmname').val(),
		address1=$('#address1').val(),address2=$('#address2').val(),address3=$('#address3').val(),
		zipcode=$('#zipcode').val(),notes=$('#notes').val(),country=$('#country li.selected').val()||'0',
		state=$('#state li.selected').val()||'0',city=$('#city li.selected').val()||'0',
		commtypeid=$('#commtypeid li.selected').val()||'0',commlabelid=$('#commlabelid li.selected').val()||'0',
		contactinfo=$('#contactinfo').val(),addtionalinfo=$('#addtionalinfo').val(),
		socialnetwork=$('#socialnetwork li.selected').val()||'',coid=$('#coid_a').val();
	if(firstname==''){
		tag='firstname';
		err=i18n('err_enter_msg_name');
		errtab='0';
	}else if(address1==''){
		tag='address1';
		err=i18n('err_enter_address');
		errtab='1';
	}
	var vlab=vlawyerAddrBook('',commtypeid,commlabelid,contactinfo,addtionalinfo,socialnetwork);
	if(vlab.err!=''){
		err=vlab.err;
		tag=vlab.tag;
		errtab=vlab.errtab;
	}
	if(socialnetwork!='')contactinfo=socialnetwork;
	if(err!=""){
		if(errtab=='1')
			togglemodtab($($('#addtabsmodal li:eq(1)')),'#addaddresdata');
		else if(errtab=='2')
			togglemodtab($($('#addtabsmodal li:eq(2)')),'#addcontactdata');
		else
			togglemodtab($($('#addtabsmodal li:eq(0)')),'#addstandarddata');
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnAdd').show();
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var t='#tablecomm tbody tr',contactinfo='{';
	for(r=0;r<$(t).length;r++){
		var adtinf=$(t+':eq('+r+') td:eq(3)').data('addtionalinfo');
		var rex=new RegExp('(.*)(\\s\\('+adtinf+'.*\\))', 'g');
		contactinfo+='"'+r+'":{'
			+'"commtype":'+$(t+':eq('+r+') td:eq(1)').data('commtypeid')
			+',"commlabel":'+$(t+':eq('+r+') td:eq(2)').data('commlabelid')
			+',"contactinfo":"'+$(t+':eq('+r+') td:eq(3)').text().replace(rex,'$1')
			+'","addtionalinfo":"'+(adtinf==''?'':adtinf)+'"},';
	}
	contactinfo=contactinfo.replace(/.$/,'')+'}';
	var param='jobpos='+jobpos + '&matspec='+matspec
		+'&fname='+firstname + '&lname='+lastname + '&firmname='+firmname
		+'&address1='+address1 + '&address2='+address2 + '&address3='+address3
		+'&country='+country + '&city='+city + '&state='+state
		+'&zipcode='+zipcode + '&notes='+notes
		+'&contactinfo='+contactinfo + '&coid='+(coid==''?0:coid)
	;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/saveNewLawyerDir",
		data:param,
		async:false,
		success:function(data){
			if(data=='msg_data_saved'){
				swal(i18n("msg_success"),i18n(data),"success");
				location.href=location.pathname+"?language="+ getLanguageURL();
			}else{
				$('#putErrorOnAdd').html(i18n(data));
				$('#errorOnAdd').show();
			}
		},error:function(e){
			$('#putErrorOnAdd').html(i18n('err_record_no_saved'));
			$('#errorOnAdd').show();
		}
	});
};

function getDetailsToEdit(id){
	$('#errorOnEdit, .firmdatatabs').hide();
	$('#editstandarddata').show()
	$.ajax({
		type:'POST',
		url:ctx+"/getLawyerDirById",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data.lawyerInfo[0],adbk=data.addressBook;
			if(info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_lawyer'),"error");
			}else{
				$('#edlawyerid').val(info.lawyerid);
				$('#editfirmname').val(info.company_name);
				$('#editjobposition').val(info.jobposition);
				$('#editspeciality').val(info.jobposition);
				$('#editfirstname').val(info.first_name);
				$('#editlastname').val(info.last_name);
$('#editstatus').val(info.status);
				$('#editaddress1').val(info.address1);
				$('#editaddress2').val(info.address2);
				$('#editaddress3').val(info.address3);
				$('#editzipcode').val(info.zipcode);
				$('#editnotes').val(info.notes);
				
				
const multisel = document.querySelector('select[name=editspeciality]');
new lc_select(multisel, {
	wrap_width : '100%',
	enable_search : true,
	pre_placeh_opt: true,
});
$('#editspeciality').val(info.speciality);

				getMatterMultiList('editspeciality');
				
				getCountries('editcountry','ul');
				if(info.paisid)
					getTextDDFilterByVal('editcountry', info.paisid);
				if(info.estadoid){
					getStatesByCountry('editcountry','editstate','ul');
					getTextDDFilterByVal('editstate', info.estadoid);
				}
				if(info.ciudadid){
					getCitiesByState('editstate','editcity','ul');
					getTextDDFilterByVal('editcity', info.ciudadid);
				}
				getSocNetworks('editsocialnetwork', 'ul');
/*
commtype
commlabel
*/				
				
				var rows='',lwinfo=adbk[0].contactinfo;
				for(a=0;a<adbk.length;a++){
					var dct=data.commtype,dcl=data.commlabel,snwk=data.snwk,commtype='',commlabel='';
					for(var t = 0; t < dct.length; t++)
				        if(adbk[a].commtypeid==dct[t].commtypeid){
				            commtype=dct[t].description;
				            break;
				    	}
				    for(var l = 0; l < dcl.length; l++)
				        if(adbk[a].commlabelid==dcl[l].commlabelid){
				            commlabel=dcl[l].commlabelname;
				            break;
				    	}
				    if(!isNaN(lwinfo - 0))
				    	for(var s = 0; s < snwk.length; s++)
					        if((lwinfo*1)==snwk[s].snid){
					        	lwinfo=snwk[s].address;
					            break;
					    	}
				    rows+='<tr><td class="dnone" data-commid="' + adbk[0].id + '"></td>'
						+'<td data-commtypeid="' + adbk[0].commtypeid + '">' + commtype + '</td>'
						+'<td data-commlabelid="' + adbk[0].commlabelid + '">' + commlabel + '</td>'
						+'<td data-addtionalinfo="' + adbk[0].additionalinfo + '">' + lwinfo
							+ (adbk[0].additionalinfo==''?'':'(' + adbk[0].additionalinfo + ')') + '</td>'
						+'<td><span class="asLink" data-recid="" onclick="retrieveLAB(\'edit\',this);"><i class="md md-edit"></i></span></td>'
						+'<td><span class="asLink" onclick="deleteLAB(this);"><i class="md md-delete" style="font-size:20px"></i></span></td>'
						+'<tr>';
				}
				$('#edittablecomm tbody').append(rows);
//				$('#coid_e').val();

				//togglemodtab('#addtabsmodal','#addstandarddata');

			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_client'),"error");
		}
	});
	try{
		createDropZoneImg('uploadXEditLawyerDir', 'formNewLawyerDir', id,  '6');
	}catch (e){
		clearTemp();
		$('#areaEditLawyerDirUpload').html('');
		$('#areaEditLawyerDirUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'
			+'<div id="uploadXEditLawyerDir" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZoneImg('uploadXEditLawyerDir', 'formNewLawyerDir', id,  '6');
	}
	$("#uploadXEditLawyerDir").addClass("dropzone");
};

function updateData(){
	$('input, select').parent().removeClass('has-error');
	var err='',tag = '',errtab='',
		jobpos=$('#editjobposition').val(),matspec=$('#editspeciality').val()||'',
		firstname=$('#editfirstname').val(),lastname=$('#editlastname').val(),firmname=$('#editfirmname').val(),
		address1=$('#editaddress1').val(),address2=$('#editaddress2').val(),address3=$('#editaddress3').val(),
		zipcode=$('#editzipcode').val(),notes=$('#editnotes').val(),country=$('#editcountry li.selected').val()||'0',
		state=$('#editstate li.selected').val()||'0',city=$('#editcity li.selected').val()||'0',
		commtypeid=$('#editcommtypeid li.selected').val()||'0',commlabelid=$('#editcommlabelid li.selected').val()||'0',
		contactinfo=$('#editcontactinfo').val(),addtionalinfo=$('#editaddtionalinfo').val(),
		socialnetwork=$('#editsocialnetwork li.selected').val()||'',coid=$('#coid_e').val();
	if(firstname==''){
		tag='firstname';
		err=i18n('err_enter_msg_name');
		errtab='0';
	}else if(address1==''){
		tag='address1';
		err=i18n('err_enter_address');
		errtab='1';
	}
	vlab=vlawyerAddrBook('',commtypeid,commlabelid,contactinfo,addtionalinfo,socialnetwork);
	if(vlab.err!=''){
		err=vlab.err;
		tag=vlab.tag;
		errtab=vlab.errtab;
	}
	if(socialnetwork!='')contactinfo=socialnetwork;
	if(err!=""){
		if(errtab=='1')
			togglemodtab($($('#editaddtabsmodal li:eq(1)')),'#editaddaddresdata');
		else if(errtab=='2')
			togglemodtab($($('#editaddtabsmodal li:eq(2)')),'#editaddcontactdata');
		else
			togglemodtab($($('#editaddtabsmodal li:eq(0)')),'#editaddstandarddata');
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnEdit').show();
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var t='#edittablecomm tbody tr',contactinfo='{';
	for(r=0;r<$(t).length;r++){
		var adtinf=$(t+':eq('+r+') td:eq(3)').data('addtionalinfo');
		var rex=new RegExp('(.*)(\\s\\('+adtinf+'.*\\))', 'g');
		contactinfo+='"'+r+'":{'
			+'"commid":'+$(t+':eq('+r+') td:eq(0)').data('commid')
			+ ',"commtype":'+$(t+':eq('+r+') td:eq(1)').data('commtypeid')
			+ ',"commlabel":'+$(t+':eq('+r+') td:eq(2)').data('commlabelid')
			+ ',"contactinfo":"'+$(t+':eq('+r+') td:eq(3)').text().replace(rex,'$1')
			+'","addtionalinfo":"'+(adtinf==''?'':adtinf)+'"},';
	}
	contactinfo=contactinfo.replace(/.$/,'')+'}';
	var param='lawyerid='+$('#edlawyerid').val() + '&jobpos='+jobpos + '&matspec='+matspec
		+'&fname='+firstname + '&lname='+lastname + '&firmname='+firmname
		+'&address1='+address1 + '&address2='+address2 + '&address3='+address3
		+'&country='+country + '&city='+city + '&state='+state
		+'&zipcode='+zipcode + '&notes='+notes
		+'&contactinfo='+contactinfo + '&coid='+(coid==''?0:coid)
	;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/updateLawyerDir",
		data:param,
		async:false,
		success:function(data){
			if(data=='msg_data_saved'){
				swal(i18n("msg_success"),i18n(data),"success");
				location.href=location.pathname+"?language="+ getLanguageURL();
			}else{
				$('#putErrorOnEdit').html(i18n(data));
				$('#errorOnEdit').show();
			}
		},error:function(e){
			$('#putErrorOnEdit').html(i18n('err_record_no_saved'));
			$('#errorOnEdit').show();
		}
	});
};

$('#addNewLawyerDir').click(function(){
	$('.firmdatatabs').not(':first').hide();
	$('#errorOnAdd').hide();
	const multisel = document.querySelector('select[name=speciality]');
	new lc_select(multisel, {
		wrap_width : '100%',
		enable_search : true,
		pre_placeh_opt: true,
	});
	$("#addNewLawyerDir").attr('href','#lawyerdir-modal');
	getCountries('country','ul');
	getTextDDFilterByVal('country', 1);
	togglemodtab('#addtabsmodal','#addstandarddata');
	getMatterMultiList('speciality');
	getSocNetworks('socialnetwork', 'ul');
	try{
		myDropzone = createDropZoneImg("uploadXLawyerDir", "formNewLawyerDir", '', '6');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}catch (e){
		clearTemp();
		$('#areaLawyerDirUpload').html('');
		$('#areaLawyerDirUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXLawyerDir" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZoneImg("uploadXLawyerDir", "formNewLawyerDir", '', '6');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}
	$("#uploadXLawyerDir").addClass("dropzone");
});

$('#addLawyerDirCancel').click(function(){
	forceclose('#lawyerdir-modal');
});

function forceclose(obj){
	$(obj).fadeOut(200);
	$(obj).removeClass('fornw');
	$('.custombox-overlay, .modal-backdrop, .custombox-modal-wrapper').hide();
};

function deleteLawyerDir(id){
	var param="id="+id;
	swal({
		title:i18n('msg_are_you_sure'),
		text:i18n('msg_will_not_recover_record'),
		type:"warning",
		showCancelButton:true,
		confirmButtonClass:'btn-warning',
		confirmButtonText:i18n('btn_yes_delete_it'),
		closeOnConfirm:false,
		closeOnCancel:false
	}).then((isConfirm) => {
		if(isConfirm){
			$.ajax({
				type:'POST',
				url:ctx+"/deleteLawyerDir",
				data:param,
				async:false,
				success:function(data){
					swal({
						title:i18n('msg_deleted'),
						text:i18n('msg_record_deleted'),
						type:"success"
					}, function(){
						location.href=location.pathname+"?language="+ getLanguageURL();
					});
				},error:function(resp){
					swal(i18n('msg_warning'), i18n('err_dependence_on_delete'),"warning");
				}
			});
		}else{
			swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "warning");
		}
	});
};

function clearTemp(){
	$('input[name^="fileuploadx_"]').each(function(i){
		$(this).val('');
	});
	$.ajax({
		type:'POST',
		url:ctx+"/deleteTempPath",
		async:false,
		success:function(data){
			if(!data)console.log("Not cleared");
		}
	});
};

/** @param addedit = Modal activo. Para nuevo siempre vacio, para modificaciones: 'edit'.	*/
function modallab(addedit){
	var commlist=addedit+'commtypeid',lablist=addedit+'commlabelid';
	$('#'+commlist+',#'+lablist).prev().prev().val('');
	getCommTypes(commlist,'ul');
	getCommLabels(lablist,'ul');
	getSocNetworks(addedit+'socialnetwork', 'ul');
	getTextDDFilterByVal(addedit+'commtypeid','');
	getTextDDFilterByVal(addedit+'commlabelid','');
	$('#'+addedit+'contactinfo, #'+addedit+'addtionalinfo').val('');
	$('#'+addedit+'capturelab, #'+addedit+'lablist').toggle();
};

/** @param addedit	Modal activo. Para nuevos registros=vacio, para modificaciones='edit'.
 	@param tx		Texto de la opción elegida.	*/
function isSN(addedit,tx){
	var s='#'+addedit+'socnt',g='#'+addedit+'adtinfo';
	$(s+','+g).hide();
	$(tx.indexOf('social')<0?g:s).show();
};
$('#inputLAB').on('keyup input propertychange paste change clear blur', function(){
	isSN(this.id.replace(/inputLAB/,''),$(this).val());
});
$('#commtypeid').on('DOMNodeInserted click blur', 'li', function(){
	isSN(($(this).parent().attr('id')).replace(/commtypeid/,''),$(this).text());
});

/* limpia inputs */
$('#client-modal, #edit-modal').on('hidden.bs.modal',function(e){
	$(this).find("input,textarea").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked","").end();
});

$(document).ready(function(){
	$(".modal-demo").on('hide.bs.modal', function(){
		clearTemp();
	});
	$("#edit-modal").on('shown.bs.modal', function () {
/*		var photo=$('#edPhotoTmp').val();
		if(photo!=''){
			$('.dz-details').hide();
			var photo1=photo.match(/[^\\/:*?"<>|\r\n]+$/);
			$('#uploadXEditLawyerDir'+' div div a img').attr('src', 'doctos/images/lawyerdir'+photo1);
		}*/
	});
	/*var clid=getVarURL('clid');
	if(clid!=''){
		sessionStorage.setItem('clid',clid);
		getDetailsToEdit(clid);
	}
	if(getVarURL('nw')!=''){
		$('#addNewLawyerDir').trigger('click');
		$("#lawyerdir-modal").addClass('fornw');
		getCompanies('company');
	}
	history.replaceState({}, document.title, location.pathname+'?language='+getLanguageURL());*/

	$('#findLawyerDirList').DataTable({
		scrollCollapse:true,
		autoWidth:true,
		searching:false,
		paging:false,
		columnDefs:[{'width':'auto','targets':'_all'}]
	});
	document.getElementsByName('lcslt-search')[0].placeholder=i18n('msg_search');
});