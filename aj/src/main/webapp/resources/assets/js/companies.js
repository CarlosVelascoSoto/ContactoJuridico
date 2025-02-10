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
			var info = data[0]||[];
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
@param countryid	Puede ser el id del país o el nombre de la lista (tag-id) donde esta el país seleccionado.
@param setstateid	Nombre/id del elemento donde se mostrará el listado.
@param elemtype		Tipo de elemento a cargar; "select", "ol" o "ul".	*/
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
			var info = data[0]||[];
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
@param stateid		Puede ser el id del estado o el nombre de la lista (tag-id) donde esta el estado seleccionado.
@param setcityid	Nombre/id del elemento donde se mostrará el listado.
@param elemtype		Tipo de elemento a cargar; "select", "ol" o "ul".	*/
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
			var info=data[0]||[];
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

function getSocNetworks(){
	$.ajax({
		type:'POST',
		url:ctx+"/getSocNetworks",
		async:false,
		success:function(data){
			var info=data[0]||[];
			if(info.length>0){
				var snwLists=$('[data-sel="snw"]');
				for(s=0;s<snwLists.length;s++){
					var option=new Option(i18n('msg_select'),'0',!1,!0),id=snwLists[s].id;
					$('#'+id).find('option').remove().end();
					$('#'+id).append(option);
					$(option).attr('disabled','disabled');
					for(i=0;i<info.length;i++){
						option=new Option(info[i].socialnetwork,info[i].socialnetworkid,!0,!1);
						$('#'+id).append(option);
						$(option).attr('title',info[i].socialnetwork);
					}
					$('#'+id).append('<option value="">'+i18n('msg_none')+'</option>');
				}
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_socialnetwork'),"error");
		}
	});
};

function addCompany(){
	togglemodtab($($('#addtabsmodal li:eq(0)')),'#addstandarddata');
	$('input').parent().removeClass('has-error');
	var err='',tag='',company=$('#company').val(),shortname=$('#shortname').val(),address1=$('#address1').val(),
		address2=$('#address2').val(),address3=$('#address3').val(),cp=$('#cp').val(),
		rfc=($('#rfc').val()).replace(/[^a-zA-Z0-9]/,''),curp=$('#curp').val(),
		samedata=($('#samedata').prop('checked')==!0?1:0),
		fiscalpersontype=$('#fiscalpersontype option:selected').val(),businessname=$('#businessname').val(),
		commercialname=$('#commercialname').val(),fiscaladdress1=$('#fiscaladdress1').val(),
		fiscaladdress2=$('#fiscaladdress2').val(),fiscaladdress3=$('#fiscaladdress3').val(),fiscalcp=$('#fiscalcp').val(),
		country=$('#country li.selected').val(),state=$('#state li.selected').val(),city=$('#city li.selected').val()||'',
		fiscalcountry=$('#fiscalcountry li.selected').val()||'0',fiscalcity=$('#fiscalcity li.selected').val()||'0',
		phone1=$('#phone1').val(),phone2=$('#phone2').val(),phone3=$('#phone3').val(),
		commlabelid1=$('#commlabelid1 li.selected').val()||'',commlabelid2=$('#commlabelid2 li.selected').val()||'',
		commlabelid3=$('#commlabelid3 li.selected').val()||'',email=$('#email').val(),webpage=$('#webpage').val(),
		netw1=$('#snetworkname1 option:selected').val(),netw2=$('#snetworkname2 option:selected').val(),
		netw3=$('#snetworkname3 option:selected').val(),netw4=$('#snetworkname4 option:selected').val(),
		netwAcct1=$('#snetwAcct1').val(),netwAcct2=$('#snetwAcct2').val(),
		netwAcct3=$('#snetwAcct3').val(),netwAcct4=$('#snetwAcct4').val();
	if(businessname!='' && fiscalpersontype=='0'){
		tag='fiscalpersontype';
		err=i18n('err_select_persontype');
		togglemodtab($($('#addtabsmodal li:eq(1)')),'#addfiscaldata');
	}
	if(curp!='')
		if(curp.match(/^([A-Z][AEIOUX][A-Z]{2}\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\d])(\d)$/g)==null){
			tag='curp';
			err=i18n('err_enter_curp');
			togglemodtab($($('#addtabsmodal li:eq(1)')),'#addfiscaldata');
		}
	if(company==''){
		tag='company';
		err=i18n('err_enter_firmname');
	}else if(address1==''){
		tag='address1';
		err=i18n('err_enter_address');
	}else if(cp==''){
		tag='cp';
		err=i18n('err_enter_zipcode');
	}else if(city==''||city=='0'){
		tag='city';
		err=i18n('err_select_city');
	}else if(email==''){
		tag='email';
		err=i18n('err_enter_email');
		togglemodtab($($('#addtabsmodal li:eq(2)')),'#addcontactdata');
	}else if(rfc!=''){
		if(rfc.match(/^[a-zA-Z]{3,4}[0-9]{6}[a-zA-Z0-9]{3}$/g)==null){
			tag='rfc';
			err=i18n('err_enter_rfc');
			togglemodtab($($('#addtabsmodal li:eq(1)')),'#addfiscaldata');
		}
	}
	if(err==''&&fiscalpersontype!='0'){
		if(rfc==''){
			tag='rfc';
			err=i18n('err_enter_rfc');
		}else if(businessname==''){
			tag='businessname';
			err=i18n('err_enter_businessname');
		}else if(fiscaladdress1==''){
			tag='fiscaladdress1';
			err=i18n('err_enter_address');
		}else if(fiscalcp==''){
			tag='fiscalcp';
			err=i18n('err_enter_zipcode');
		}else if(fiscalcity==''||fiscalcity=='0'){
			tag='fiscalcity';
			err=i18n('err_select_city');
		}
		if(err!="")
			togglemodtab($($('#addtabsmodal li:eq(1)')),'#addfiscaldata');
	}
	if(err==""){//Validación de forma de contacto
		if(!netw1)netw1='0';
		if(!netw2)netw2='0';
		if(!netw3)netw3='0';
		if(!netw4)netw4='0';
		if(!commlabelid1)commlabelid1='0';
		if(!commlabelid2)commlabelid2='0';
		if(!commlabelid3)commlabelid3='0';	
		if(commlabelid1!='0' && (phone1==''||phone1==null)){
			tag='phone1';
			err=i18n('err_enter_phone');
		}else if(commlabelid2!='0' && (phone2==''||phone2==null)){
			tag='phone2';
			err=i18n('err_enter_phone');
		}else if(commlabelid3!='0' && (phone3==''||phone3==null)){
			tag='phone3';
			err=i18n('err_enter_phone');
		}else if(netw1>0 && netwAcct1==''){
			tag='snetwAcct1';
			err=i18n('err_enter_socnetwork');
		}else if(netw2>0 && netwAcct2==''){
			tag='snetwAcct2';
			err=i18n('err_enter_socnetwork');
		}else if(netw3>0 && netwAcct3==''){
			tag='snetwAcct3';
			err=i18n('err_enter_socnetwork');
		}else if(netw4>0 && netwAcct4==''){
			tag='snetwAcct4';
			err=i18n('err_enter_socnetwork');
		}
		if(webpage!="")
			if(webpage.match(/^((https?|ftp|smtp):\/\/)?(www.)?[a-zA-Z0-9_~+:-]+\.[a-zA-Z0-9_-]{2,}(\/[a-zA-Z0-9_~+:#-]+\/?)*(\?.+)?$/g)==null){
				tag='webpage';
				err=i18n('err_enter_webpage');
			}
		if(email!='')
			if(email.match(/^(?!\s)[\w\d!#$%&'*+/=?`{|}~^-]+(?:\.[\w\d!#$%&'*+/=?`{|}~^-]+)*@(?:[\w\d-]+\.)+[\w\d]{2,6}$/g)==null){
				tag='email';
				err=i18n('err_enter_email');
			}
		if(err!="")
			togglemodtab($($('#addtabsmodal li:eq(2)')),'#addcontactdata');
	}
	
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='fdid=0' + '&company='+company + '&shortname='+shortname + '&address1='+address1
		+'&address2='+address2 + '&address3='+address3 + '&country='+country + '&state='+state + '&city='+city
		+'&cp='+cp + '&email='+email + '&phone1='+phone1  + '&phone2='+phone2 + '&phone3='+phone3
		+'&commlabelid1='+commlabelid1 + '&commlabelid2='+commlabelid2 + '&commlabelid3='+commlabelid3
		+'&netw1='+netw1 + '&netw2='+netw2 + '&netw3='+netw3
		+'&netw4='+netw4 + '&netwAcct1='+netwAcct1 + '&netwAcct2='+netwAcct2 + '&netwAcct3='+netwAcct3
		+'&netwAcct4='+netwAcct4 + '&rfc='+rfc.toUpperCase() + '&curp='+curp.toUpperCase()
		+'&fiscalpersontype='+fiscalpersontype
		+'&businessname='+businessname + '&commercialname='+commercialname + "&samedata="+samedata
		+'&fiscaladdress1='+fiscaladdress1 + '&fiscaladdress2='+fiscaladdress2 + '&fiscaladdress3='+fiscaladdress3
		+'&fiscalcp='+fiscalcp + '&fiscalcountry='+fiscalcountry + '&fiscalcity='+fiscalcity
		+'&commlabelid1='+commlabelid1 + '&commlabelid2='+commlabelid2 + '&commlabelid3='+commlabelid3
		+'&webpage='+webpage;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/addNewCompanies",
		data:param,
		async:false,
		success:function(data){
			if(data=='msg_data_saved'){
				swal(i18n("msg_success"),i18n(data),"success");
				location.href=location.pathname+"?language="+ getLanguageURL();
			}else{
				$('#putErrorOnAdd').html(i18n(data));
				$('#errorOnAdd').css('display','block');
			}$('#company-modal').modal('toggle');
		},
		error:function(e){
			$('#putErrorOnAdd').html(i18n('err_record_no_saved'));
			$('#errorOnAdd').css('display','block');
		}
	});
};

function getCountryStateByCityId(cityid) {
	var res=null;
	$.ajax({
		type : "POST",
		url : ctx + "/getCountryStateByCityId",
		data : "cityid="+cityid,
		async : false,
		success : function(data) {
			res=data[0];
		},error : function(e) {
			console.log(i18n('err_unable_get_state') + '. ' + e);
		}
	});
	return res;
};

function getDetailsToEdit(id){
	$('#errorOnEdit').hide();
	$('#formEditCompany')[0].reset();
	$.ajax({
		type:'POST',
		url:ctx+"/getCompanyById",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].info[0]||[], fiscal=data[0].fiscal[0]||[];
			if(data[0].info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_firm'),"error");
			}else{
				$('#errorOnAdd').css('display','none');
				getCountries('editcountry','ul');
				getCountries('editfiscalcountry','ul');
				togglemodtab('#edittabsmodal','#editstandarddata');
				getCommLabelList('[data-sel="editcommlab"]','ul');
				getSocNetworks();
				$('#editcompId').val(info.companyid);
				$('#editcompany').val(info.company);
				$('#editshortname').val(info.shortname);
				$('#editaddress1').val(info.address1);
				$('#editaddress2').val(info.address2);
				$('#editaddress3').val(info.address3);
				$('#editcp').val(info.zipcode);
				$('#editrfc').val(info.rfc);
//FIXME Líneas temporales por cambio de forma de guardar ciudad, estado, país:
				if(!isNaN(info.country -0)){
					getTextDDFilterByVal('editcountry', info.country);
				}
				if(!isNaN(info.state -0)){
					getStatesByCountry('editcountry','editstate','ul')
					getTextDDFilterByVal('editstate', info.state);
				}
				if(!isNaN(info.city -0)){
					getCitiesByState('editstate', 'editcity', 'ul');
					getTextDDFilterByVal('editcity', info.city);
				}
				if(fiscal){
					$('#editcurp').val(fiscal.curp);
					$('#editfiscalpersontype').val(fiscal.personafiscalid);
					$('#editsamedata').prop('checked',(fiscal.samedata==1?!0:!1));
					$('#editbusinessname').val(fiscal.businessname);
					$('#editcommercialname').val(fiscal.commercialname);
					$('#editfiscaladdress1').val(fiscal.address1);
					$('#editfiscaladdress2').val(fiscal.address2);
					$('#editfiscaladdress3').val(fiscal.address3);
					$('#editfiscalcp').val(fiscal.zipcode);
					//TODO
					/*$('#editfiscalstartdate').val(fiscal.startdate);
					$('#editfiscalenddate').val(fiscal.enddate);
					$('#editphotoTmp').val(info.companyid);*/

					var csc=getCountryStateByCityId(info.city);
					getTextDDFilterByVal('editfiscalcountry',csc.country[0].paisid);

					getStatesByCountry(csc.country[0].paisid,'editfiscalstate','ul')
					getTextDDFilterByVal('editfiscalstate',csc.state[0].estadoid);
					
					getCitiesByState(csc.state[0].estadoid,'editfiscalcity','ul');
					getTextDDFilterByVal('editfiscalcity',csc.city[0].ciudadid);
				}
				getTextDDFilterByVal('editcommlabelid1',info.communicationlabel1);
				getTextDDFilterByVal('editcommlabelid2',info.communicationlabel2);
				getTextDDFilterByVal('editcommlabelid3',info.communicationlabel3);
				$('#editphone1').val(info.phone);
				$('#editphone2').val(info.phone2);
				$('#editphone3').val(info.phone3);
				$('#editemail').val(info.email);
				$('#editwebpage').val(info.webpage);
				$('#editsnetworkname1').val(info.socialnetworkid1);
				$('#editsnetworkname2').val(info.socialnetworkid2);
				$('#editsnetworkname3').val(info.socialnetworkid3);
				$('#editsnetworkname4').val(info.socialnetworkid4);
				$('#editsnetwAcct1').val(info.socialnetworkacct1);
				$('#editsnetwAcct2').val(info.socialnetworkacct2);
				$('#editsnetwAcct3').val(info.socialnetworkacct3);
				$('#editsnetwAcct4').val(info.socialnetworkacct4);
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_firm'),"error");
		}
	});
	try{
		createDropZoneImg('uploadXEditCompany', 'formEditCompany', id,  8);
	}catch (e){
		clearTemp();
		$('#areaEditCompUpload').html('');
		$('#areaEditCompUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'
			+'<div id="uploadXEditClient" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZoneImg('uploadXEditCompany', 'formEditCompany', id,  8);
	}
	$("#uploadXEditCompany").addClass("dropzone");
}

function updateData(){
	togglemodtab($($('#edittabsmodal li:eq(0)')),'#editstandarddata');
	$('input').parent().removeClass('has-error');
	var err='',tag='',company=$('#editcompany').val(),shortname=$('#editshortname').val(),address1=$('#editaddress1').val(),
		address2=$('#editaddress2').val(),address3=$('#editaddress3').val(),cp=$('#editcp').val(),
		rfc=($('#editrfc').val()).replace(/[^a-zA-Z0-9]/,''),curp=$('#editcurp').val().replace(/[^a-zA-Z0-9]/,''),
		samedata=($('#editsamedata').prop('checked')==!0?1:0),
		fiscalpersontype=$('#editfiscalpersontype option:selected').val(),businessname=$('#editbusinessname').val(),
		commercialname=$('#editcommercialname').val(),fiscaladdress1=$('#editfiscaladdress1').val(),
		fiscaladdress2=$('#editfiscaladdress2').val(),fiscaladdress3=$('#editfiscaladdress3').val(),fiscalcp=$('#editfiscalcp').val(),
		country=$('#editcountry li.selected').val(),state=$('#editstate li.selected').val(),city=$('#editcity li.selected').val()||'',
		fiscalcountry=$('#editfiscalcountry li.selected').val()||'0',fiscalcity=$('#editfiscalcity li.selected').val()||'0',
		phone1=$('#editphone1').val(),phone2=$('#editphone2').val(),phone3=$('#editphone3').val(),
		commlabelid1=$('#editcommlabelid1 li.selected').val()||'',commlabelid2=$('#editcommlabelid2 li.selected').val()||'',
		commlabelid3=$('#editcommlabelid3 li.selected').val()||'',email=$('#editemail').val(),webpage=$('#editwebpage').val(),
		netw1=$('#editsnetworkname1 option:selected').val(),netw2=$('#editsnetworkname2 option:selected').val(),
		netw3=$('#editsnetworkname3 option:selected').val(),netw4=$('#editsnetworkname4 option:selected').val(),
		netwAcct1=$('#editsnetwAcct1').val(),netwAcct2=$('#editsnetwAcct2').val(),
		netwAcct3=$('#editsnetwAcct3').val(),netwAcct4=$('#editsnetwAcct4').val();
	if(businessname!='' && fiscalpersontype=='0'){
		tag='editfiscalpersontype';
		err=i18n('err_select_persontype');
		togglemodtab($($('#edittabsmodal li:eq(1)')),'#editfiscaldata');
	}
	if(curp!='')
		if(curp.match(/^([A-Z][AEIOUX][A-Z]{2}\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\d])(\d)$/g)==null){
			tag='editcurp';
			err=i18n('err_enter_curp');
			togglemodtab($($('#edittabsmodal li:eq(1)')),'#editfiscaldata');
		}
	if(company==''){
		tag='editcompany';
		err=i18n('err_enter_firmname');
	}else if(address1==''){
		tag='editaddress1';
		err=i18n('err_enter_address');
	}else if(cp==''){
		tag='editcp';
		err=i18n('err_enter_zipcode');
	}else if(city==''||city=='0'){
		tag='editcity';
		err=i18n('err_select_city');
	}else if(email==''){
		tag='editemail';
		err=i18n('err_enter_email');
		togglemodtab($($('#edittabsmodal li:eq(2)')),'#editcontactdata');
	}else if(rfc!=''){
		if(rfc.match(/^[a-zA-Z]{3,4}[0-9]{6}[a-zA-Z0-9]{3}$/g)==null){
			tag='editrfc';
			err=i18n('err_enter_rfc');
			togglemodtab($($('#edittabsmodal li:eq(1)')),'#editfiscaldata');
		}
	}
	if(err==''&&fiscalpersontype!='0'){
		if(rfc==''){
			tag='editrfc';
			err=i18n('err_enter_rfc');
		}else if(businessname==''){
			tag='edBusinessname';
			err=i18n('err_enter_businessname');
		}else if(fiscaladdress1==''){
			tag='editfiscaladdress1';
			err=i18n('err_enter_address');
		}else if(fiscalcp==''){
			tag='editfiscalcp';
			err=i18n('err_enter_zipcode');
		}else if(fiscalcity==''||fiscalcity=='0'){
			tag='editfiscalcity';
			err=i18n('err_select_city');
		}
		if(err!="")
			togglemodtab($($('#edittabsmodal li:eq(1)')),'#editfiscaldata');
	}
	if(err==""){//Validación de forma de contacto
		if(!netw1)netw1='0';
		if(!netw2)netw2='0';
		if(!netw3)netw3='0';
		if(!netw4)netw4='0';
		if(!commlabelid1)commlabelid1='0';
		if(!commlabelid2)commlabelid2='0';
		if(!commlabelid3)commlabelid3='0';	
		if(commlabelid1!='0' && (phone1==''||phone1==null)){
			tag='editphone1';
			err=i18n('err_enter_phone');
		}else if(commlabelid2!='0' && (phone2==''||phone2==null)){
			tag='editphone2';
			err=i18n('err_enter_phone');
		}else if(commlabelid3!='0' && (phone3==''||phone3==null)){
			tag='editphone3';
			err=i18n('err_enter_phone');
		}else if(netw1>0 && netwAcct1==''){
			tag='editsnetwAcct1';
			err=i18n('err_enter_socnetwork');
		}else if(netw2>0 && netwAcct2==''){
			tag='editsnetwAcct2';
			err=i18n('err_enter_socnetwork');
		}else if(netw3>0 && netwAcct3==''){
			tag='editsnetwAcct3';
			err=i18n('err_enter_socnetwork');
		}else if(netw4>0 && netwAcct4==''){
			tag='editsnetwAcct4';
			err=i18n('err_enter_socnetwork');
		}
		if(webpage!="")
			if(webpage.match(/^((https?|ftp|smtp):\/\/)?(www.)?[a-zA-Z0-9_~+:-]+\.[a-zA-Z0-9_-]{2,}(\/[a-zA-Z0-9_~+:#-]+\/?)*(\?.+)?$/g)==null){
				tag='editwebpage';
				err=i18n('err_enter_webpage');
			}
		if(email!='')
			if(email.match(/^(?!\s)[\w\d!#$%&'*+/=?`{|}~^-]+(?:\.[\w\d!#$%&'*+/=?`{|}~^-]+)*@(?:[\w\d-]+\.)+[\w\d]{2,6}$/g)==null){
				tag='editemail';
				err=i18n('err_enter_email');
			}
		if(err!="")
			togglemodtab($($('#edittabsmodal li:eq(2)')),'#editcontactdata');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='fdid='+$('#editcompId').val() + '&company='+company + '&shortname='+shortname + '&address1='+address1
		+'&address2='+address2 + '&address3='+address3 + '&country='+country + '&state='+state + '&city='+city
		+'&cp='+cp + '&email='+email + '&phone1='+phone1  + '&phone2='+phone2 + '&phone3='+phone3
		+'&commlabelid1='+commlabelid1 + '&commlabelid2='+commlabelid2 + '&commlabelid3='+commlabelid3
		+'&netw1='+netw1 + '&netw2='+netw2 + '&netw3='+netw3
		+'&netw4='+netw4 + '&netwAcct1='+netwAcct1 + '&netwAcct2='+netwAcct2 + '&netwAcct3='+netwAcct3
		+'&netwAcct4='+netwAcct4 + '&rfc='+rfc.toUpperCase() + '&curp='+curp.toUpperCase()
		+'&fiscalpersontype='+fiscalpersontype
		+'&businessname='+businessname + '&commercialname='+commercialname + "&samedata="+samedata
		+'&fiscaladdress1='+fiscaladdress1 + '&fiscaladdress2='+fiscaladdress2 + '&fiscaladdress3='+fiscaladdress3
		+'&fiscalcp='+fiscalcp + '&fiscalcountry='+fiscalcountry + '&fiscalcity='+fiscalcity
		+'&commlabelid1='+commlabelid1 + '&commlabelid2='+commlabelid2 + '&commlabelid3='+commlabelid3
		+'&webpage='+webpage;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/updateCompanies",
		data:param,
		async:false,
		success:function(data){
			if(data=="msg_data_saved"){
				swal({
					title:i18n('msg_success'),
					text:i18n(data),
					type:"success",
					timer:3000,
					allowEscapeKey:false
				},function(){
					location.href=location.pathname+"?language="+ getLanguageURL();
				});
				window.setTimeout(function(){
					location.href=location.pathname+"?language="+ getLanguageURL();
				},3000);
			}else{
				$('#putErrorOnEdit').html(i18n('err_record_no_saved'));
				$('#errorOnEdit').css('display','block');
			}
		},error:function(e){
			$('#putErrorOnEdit').html(i18n('err_record_no_saved'));
			$('#errorOnEdit').css('display','block');
		}
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
			var info = data[0]||[], commLists=$(dataAttr);
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

$("#addNewCompany").click(function(){
	$('#errorOnAdd').css('display','none');
	$("#addNewCompany").attr('href','#company-modal');
	getSocNetworks();
	getCountries('country','ul');
	getTextDDFilterByVal('country', 1);
	getCountries('fiscalcountry','ul');
	getTextDDFilterByVal('fiscalcountry', 1);
	togglemodtab('#addtabsmodal','#addstandarddata');
	getCommLabelList('[data-sel="commlab"]','ul');
	try{
		myDropzone = createDropZoneImg("uploadXCompany", "formNewCompany", '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}catch (e){
		clearTemp();
		$('#areaCompanyUpload').html('');
		$('#areaCompanyUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXCompany" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZoneImg("uploadXCompany", "formNewCompany", '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}
	$('#uploadXCompany').addClass('dropzone');
});

function deleteCompany(id){
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
				url:ctx+"/deleteCompanies",
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
}

/* limpia inputs */
$('#company-modal, #edit-modal').on('hidden.bs.modal',function(e){
	$(this).find("input,textarea").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked","").end();
});

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

$('#samedata, #editsamedata').on('change', function(){
	if(!$(this).prop('checked'))return;
	var addedit=this.id.indexOf('edit')>=0?'edit':'';
	$('#'+addedit+'businessname').val($('#'+addedit+'company').val());
	$('#'+addedit+'commercialname').val($('#'+addedit+'company').val());
	$('#'+addedit+'fiscaladdress1').val($('#'+addedit+'address1').val());
	$('#'+addedit+'fiscaladdress2').val($('#'+addedit+'address2').val());
	$('#'+addedit+'fiscaladdress3').val($('#'+addedit+'address3').val());
	$('#'+addedit+'fiscalcp').val($('#'+addedit+'cp').val());
	var country=$('#'+addedit+'country li.selected').val()||'';
	if(country!=''){
		getCountries(addedit+'fiscalcountry','ul');
		getTextDDFilterByVal(addedit+'fiscalcountry',country);

		var state=$('#'+addedit+'state li.selected').val()||'';
		if(state!=''){
			getStatesByCountry(addedit+'country',addedit+'fiscalstate','ul')
			getTextDDFilterByVal(addedit+'fiscalstate',state);

			var city=$('#'+addedit+'city li.selected').val()||'';
			if(city!=''){
				getCitiesByState(addedit+'fiscalstate',addedit+'fiscalcity','ul');
				getTextDDFilterByVal(addedit+'fiscalcity',city);
			}
		}
	}
});

$(document).ready(function(){
	$(".modal-demo").on('hide.bs.modal', function(){
		clearTemp();
	});
/*	$("#edit-modal").on('shown.bs.modal', function () {
		var photo=$('#editphotoTmp').val();
		if(photo!=''){
			$('.dz-details').css('display','none');
			var photo1=photo.match(/[^\\/:*?"<>|\r\n]+$/);
			$('#uploadXEditClient'+' div div a img').attr('src', 'doctos/images/companieslogos/'+photo1);
		}
    });*/
});