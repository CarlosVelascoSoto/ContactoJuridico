;function getCountries(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getCountries",
		async:false,
		success:function(data){
			var info=data[0];
			if(info.length>0){
				var option=new Option(i18n('msg_select'),'',!1,!0);
				$('#'+id).append(option);
				$(option).attr('disabled','disabled');
				for(i=0;i<info.length;i++){
					option=new Option(info[i].ciudad,info[i].paisid,!0,!1);
					$('#'+id).append(option);
					$(option).attr('title',info[i].ciudad);
				}
			}
		},error:function(e){
			console.log("Error al obtener Pa\u00edses. "+e);
		}
	});
};

function getCiudades(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getCiudades",
		async:false,
		success:function(data){
			var info=data[0].cdList;
			if(info.length>0){
				var option=new Option(i18n('msg_select'),'',!1,!0);
				$('#'+id).append(option);
				$(option).attr('disabled',!0);
				for(i=0;i<info.length;i++){
					option=new Option(info[i].ciudad,info[i].ciudadid,!0,!1);
					$('#'+id).append(option);
					$(option).attr('title',info[i].ciudad);
				}
			}
		},error:function(e){
			console.log("Error al obtener Ciudades. "+e);
		}
	});
};

function readURL(input,id){
    if(input.files && input.files[0]){
        var reader=new FileReader();
        reader.onload=function (e){
            $('#'+id).attr('src',e.target.result);
            if(id.match(/^ed/)!=null)
            	$('#edPhotoTmp').val('');
        };
        reader.readAsDataURL(input.files[0]);
    }
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

function getSocNetworks(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getSocNetworks",
		async:false,
		success:function(data){
			var info=data[0];
			if(info.length>0){
				$('#'+id).append('<option value="" selected disabled>'+i18n('msg_select')+'</option>');
				for(i=0;i<info.length;i++){
					var im=info[i].imageurl?(info[i].imageurl).replace(/^.*(doctos\/.*$)/,'$1'):'';
					$('#'+id).append('<option value="'+info[i].socialnetworkid+'" title="'+info[i].mainurl
					+'" data-imagesrc="'+im+'" data-description="'+(info[i].mainurl?info[i].mainurl:'')+'">'
					+info[i].socialnetwork+'</option>');
				}
			}
		},error:function(e){
			console.log("Error al obtener redes sociales. "+e);
		}
	});
};

function fnAddNewClient(){
	$('#errorOnAdd').css('display','none');
	$("#addNewClient").attr('href','#clients-modal');
	$("#status").val($("#status select option:first").val());
	getCompanies('company');
	try{
		myDropzone = createDropZoneImg("uploadXClient", "formNewClient", '', '6');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}catch (e){
		clearTemp();
		$('#areaClientUpload').html('');
		$('#areaClientUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXClient" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZoneImg("uploadXClient", "formNewClient", '', '6');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}
	$("#uploadXClient").addClass("dropzone");
};

function addClients(e){
	$('input, select').parent().removeClass('has-error');
	var err='',tag = '',client=$('#client').val(),address1=$('#address1').val(),city=$('#city').val(),
		state=$('#state').val(),country=$('#country').val(),cp=$('#cp').val()
		email=$('#email').val(),phone=$('#phone').val(),status=$('#status option:selected').val(),
		cellphone=$('#cellphone').val(),birthdate=$('#birthdate').val(),comments=$('#comments').val(),
		company=$('#company option:selected').val(),photo=$('#photo').val(),
		typeofperson=$('#typeofperson option:selected').val(),webpage=$('#webpage').val(),arrSN='',
		contactperson=$('#contactperson').val();
	company=company||'0';
	photo=photo||'';
	typeofperson=typeofperson||'';
	if(client==''){
		tag='client';
		err=i18n('err_enter_clientname');
	}else if(typeofperson==''){
		tag='typeofperson';
		err=i18n('err_select_persontype');
	}else if(address1==''){
		tag='address1';
		err=i18n('err_enter_address');
	}else if(city==''){
		tag='city';
		err=i18n('err_select_city');
	}else if(state==''){
		tag='state';
		err=i18n('err_select_state');
	}else if(country==''){
		tag='country';
		err=i18n('err_select_country');
	}else if(cp==''){
		tag='cp';
		err=i18n('err_enter_zipcode');
	}else if(email.match(/^(?!\s)[\w\d!#$%&'*+/=?`{|}~^-]+(?:\.[\w\d!#$%&'*+/=?`{|}~^-]+)*@(?:[\w\d-]+\.)+[\w\d]{2,6}$/g)==null){
		tag='email';
		err=i18n('err_enter_email');
	}else if(cellphone==''&&phone==''){
		tag='phone';
		err=i18n('err_enter_phone_cell');
	}else if(status==''){
		tag='status';
		err=i18n('err_select_status');
	}else if(company==''){
		tag='company';
		err=i18n('err_select_firm');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}  
	$("#snTableListOnNew tr").each(function (){
		if(this.hasAttribute('data-snrow-OnNew'))
			arrSN+=this.getAttribute('data-snrow-OnNew')+' ';
	});
	arrSN=arrSN.replace(/.$/, "");
	var param='client='+client+'&address1='+address1+'&city='+city
		+'&state='+state+'&country='+country+'&cp='+cp+'&cellphone='+cellphone
		+'&email='+email+'&phone='+phone+'&status='+status+'&company='+company
		+'&birthdate='+birthdate+'&comments='+comments+'&photo='+photo
		+'&typeofperson='+typeofperson+'&webpage='+webpage+'&arrSN='+arrSN
		+'&contactperson='+contactperson;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/addNewClients",
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

/**	Establece la fecha a los campos de bootstrap-datepicker
* @param	e		{String}	Id input tag fecha
* @param	efix	{String}	Id input tag Fix fecha.
* @return	utdDate	{String} 	Fecha en formato UTC o milisegundos
* @author	GRSR		*/
function setBootstrapUtcDate(e,efix,utdDate){
	if(utdDate==null||utdDate=='')return;
	var d=new Date(utdDate);
	var newD=d.getFullYear()+"-"+twoDigits(d.getMonth()+1)+"-"+twoDigits(d.getDate());
	$('#'+efix).val(setFormatPatternDate(d,getFormatPatternDate("")));
	$('#'+e).val(newD);
};

function getDetailsToEdit(id){
	$('#errorOnEdit').hide();
	$.ajax({
		type:'POST',
		url:ctx+"/getClientsById",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].info[0];
			if(info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_client'),"error");
			}else{
				$('#edCliId').val(info.clientid);
				$('#edTypeofperson').val(info.personafiscalid==''?'':info.personafiscalid);
				$('#edClient').val(info.client);
				$('#edAddress1').val(info.address1);
				$('#edCity').val(info.city);
				$('#edState').val(info.state);
				$('#edCountry').val(info.country);
				$('#edCp').val(info.zipcode);
				$('#edEmail').val(info.email);
				$('#edCellphone').val(info.cellphone);
				$('#edPhone').val(info.phone);
				$('#edStatus').val(info.status);
				setBootstrapUtcDate('edBirthdate','edBirthdateFix',info.birthdate);
				$('#edComments').val(info.comments);
				$('#edPhoto').attr('src',info.photo);
				$('#edPhotoTmp').val(info.photo);
				$('#edWebpage').val(info.webpage);
				$('#edContactperson').val(info.contactperson);
				
				var snw=data[0].snw,onAddEdit='OnEdit',action='edit';
				$('#snTableListOnEdit tbody').empty();
				for(s=0;s<snw.length;s++){
					var id=snw[s][0].snid,
						snid=snw[s][0].socialnetworkid,
						snName=snw[s][1],
						snAddress=snw[s][0].address;
					if(snid!=''&&snAddress!=''){
						var fullURL='https://www.'+snName.toLowerCase()+'.com/'+snAddress.replace(/^(([^:/?#]+):)?(\/\/([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?/g,'$5').replace(/^\//,'');
						var newsn='<tr data-snrow-'+onAddEdit.toLowerCase()+'="'+snid+'||'+snAddress+'||'+id+'"><td>'+snName+'</td>'
							+'<td><a href="'+fullURL+'" rel="noopener noreferrer" target="_blanck" style="cursor:pointer">'+snAddress+'</a></td>'
							+'<td class="tac"><a href="#" data-snidEdit-'+onAddEdit+onAddEdit+'="'+snid+'" class="table-action-btn" title="'+i18n('msg_edit')+' '+snAddress
							+'" onclick="editSN(\''+snid+'||'+snAddress+'||'+id+'\',\'edit\',\''+onAddEdit+'\');$(\'reeditsn\').val(\'1\')"><i class="md md-edit"></i></a></td>'
							+'<td class="tac"><a href="#" data-snidDel-'+onAddEdit+'="'+snid+'" class="table-action-btn" title="'+i18n('msg_delte')+' '+snAddress
							+'" onclick="deleteSN(\''+snid+'||'+snAddress+'||'+id+'\',\''+onAddEdit+'\');"><i class="md md-delete"></i></a></td></tr>';
					}
					$('#snTableList'+onAddEdit+' tbody:last-child').append(newsn);
				}
				if(typeof sessionStorage.clid!= 'undefined'){
					sessionStorage.removeItem('clid');
					$('#edit-modal').modal('show');
				}
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_client'),"error");
		}
	});
	try{
		createDropZoneImg('uploadXEditClient', 'formNewClient', id,  '6');
	}catch (e){
		clearTemp();
		$('#areaEditClientUpload').html('');
		$('#areaEditClientUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'
			+'<div id="uploadXEditClient" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZoneImg('uploadXEditClient', 'formNewClient', id,  '6');
	}
	$("#uploadXEditClient").addClass("dropzone");
};

function updateData(){
	$('input').parent().removeClass('has-error');
	var err='',client=$('#edClient').val(),address1=$('#edAddress1').val(),city=$('#edCity').val(),
		state=$('#edState').val(),country=$('#edCountry').val(),cp=$('#edCp').val(),
		email=$('#edEmail').val(),phone=$('#edPhone').val(),status=$('#edStatus option:selected').val(),
		cellphone=$('#edCellphone').val(),birthdate=$('#edBirthdate').val(),comments=$('#edComments').val(),
		photo=$('#edPhoto').val()==''?$('#edPhotoTmp').val():$('#edPhoto').val(),
		typeofperson=$('#edTypeofperson option:selected').val(),webpage=$('#edWebpage').val(),arrSN="",
		contactperson=$('#edContactperson').val();
	typeofperson=typeofperson||'';
	if(client==''){
		tag='edClient';
		err=i18n('err_enter_clientname');
	}else if(typeofperson==''){
		tag='edTypeofperson';
		err=i18n('err_select_persontype');
	}else if(address1==''){
		tag='edAddress1';
		err=i18n('err_enter_address');
	}else if(city==''){
		tag='edCity';
		err=i18n('err_select_city');
	}else if(state==''){
		tag='edState';
		err=i18n('err_select_state');
	}else if(country==''){
		tag='edCountry';
		err=i18n('err_select_country');
	}else if(cp==''){
		tag='edCp';
		err=i18n('err_enter_zipcode');
	}else if(email.match(/^(?!\s)[\w\d!#$%&'*+/=?`{|}~^-]+(?:\.[\w\d!#$%&'*+/=?`{|}~^-]+)*@(?:[\w\d-]+\.)+[\w\d]{2,6}$/g)==null){
		tag='edEmail';
		err=i18n('err_enter_email');
	}else if(cellphone==''&&phone==''){
		tag='edPhone';
		err=i18n('err_enter_phone_cell');
	}else if(status==''){
		tag='edStatus';
		err=i18n('err_select_status');
	}else if(company==''){
		tag='edCompany';
		err=i18n('err_select_firm');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.modal').animate({scrollTop:0},'1000');
		return false;
	}
	$("#snTableListOnEdit tr").each(function (){
		if(this.hasAttribute('data-snrow-OnEdit'))
			arrSN+=this.getAttribute('data-snrow-OnEdit')+' ';
	});
	arrSN=arrSN.replace(/.$/,"");
	var param='id='+$('#edCliId').val()+'&client='+client+'&address1='+address1+'&city='+city
		+'&state='+state+'&country='+country+'&cp='+cp+'&cellphone='+cellphone
		+'&email='+email+'&phone='+phone+'&status='+status
		+'&birthdate='+birthdate+'&comments='+comments+'&photo='+photo
		+'&typeofperson='+typeofperson+'&webpage='+webpage+'&arrSN='+arrSN
		+'&contactperson='+contactperson;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/updateClients",
		data:param,
		async:false,
		success:function(data){
			if(data=="msg_data_saved"){
				swal({
					title:i18n('msg_success'),
					text:i18n(data),
					type:"success",
					html:true,
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

$('#addNewClient').click(function(){
	fnAddNewClient();
});

$('#addClientsCancel').click(function(){
	forceclose('#clients-modal');
});

function forceclose(obj){
	$(obj).fadeOut(200);
	$(obj).removeClass('fornw');
	$('.custombox-overlay, .modal-backdrop, .custombox-modal-wrapper').hide();
};

function deleteClient(id){
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
				url:ctx+"/deleteClients.jet",
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

function modalsn(action,onAddEdit){
	getSocNetworks(action+'SnList'+onAddEdit);
	$('#'+action+'SnList'+onAddEdit+',#'+action+'SnAddress'+onAddEdit).val('');
	$('#'+action+'SN-'+onAddEdit+'-modal').modal('toggle');
};

function fnAddSN(action,onAddEdit){
	var snid=$('#'+action+'SnList'+onAddEdit).val(),err='',tag='',
		snName=$('#'+action+'SnList'+onAddEdit+' option:selected').text(),
		snAddress=$('#'+action+'SnAddress'+onAddEdit+'').val();
	if(snid==''){
		tag=action+'SnList'+onAddEdit;
		err=i18n('err_select_option_list');
	}else if(!/^\S+$/.test(snAddress)||snAddress==''){
		tag=action+'SnAddress'+onAddEdit;
		err=i18n('err_address_no_spaces');
	}
	if(err!=''){
		$('#' + tag).parent().addClass('has-error');
		swal({title:i18n('msg_error'),text:err,type:"error"});
		return;
	}
	var id='',fullURL='https://www.'+snName.toLowerCase()+'.com/'+snAddress.replace(/^(([^:/?#]+):)?(\/\/([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?/g,'$5').replace(/^\//,'');
	if($('#reeditsn').val()!=''){
		var sndata=$('#reeditsn').val().split('||');
		id=(typeof sndata[2]=='undefined'||sndata[2]=='')?'':'||'+sndata[2];
		$($('#reeditsn').val()).remove();
		$('#reeditsn').val('');
	}
	var newsn='<tr data-snrow-'+onAddEdit.toLowerCase()+'="'+snid+'||'+snAddress+id+'"><td>'+snName+'</td>'
		+'<td><a href="'+fullURL+'" rel="noopener noreferrer" target="_blanck" style="cursor:pointer">'+snAddress+'</a></td>'
		+'<td class="tac"><a href="#" data-snidEdit-'+onAddEdit+onAddEdit+'="'+snid+'" class="table-action-btn" title="'+i18n('msg_edit')+' '+snAddress
		+'" onclick="editSN(\''+snid+'||'+snAddress+'\',\'edit\',\''+onAddEdit+'\');$(\'reeditsn\').val(\'1\')"><i class="md md-edit"></i></a></td>'
		+'<td class="tac"><a href="#" data-snidDel-'+onAddEdit+'="'+snid+'" class="table-action-btn" title="'+i18n('msg_delte')+' '+snAddress
		+'" onclick="deleteSN(\''+snid+'||'+snAddress+'\',\''+onAddEdit+'\');"><i class="md md-delete"></i></a></td></tr>';
	$('#snTableList'+onAddEdit+' tbody:last-child').append(newsn);
	$('#'+action+'SN-'+onAddEdit+'-modal').modal('toggle');
};

function editSN(rowidname,action,onAddEdit){
	getSocNetworks(action+'SnList'+onAddEdit);
	var sndata=rowidname.split('||');
	var snid=sndata[0],snAddress=sndata[1];
	$('#'+action+'SnList'+onAddEdit).val(snid);
	$('#'+action+'SnAddress'+onAddEdit).val(snAddress);
	$('#reeditsn').val('[data-snrow-'+onAddEdit+'="'+rowidname+'"]');
	$('#'+action+'SN-'+onAddEdit+'-modal').modal('toggle');
};

function deleteSN(rowid,onAddEdit){
	var snrow=$('[data-snrow-'+onAddEdit+'="'+rowid+'"]');
	var snName=$(snrow[0]).find("td:eq(0)").text(),
		snAddress=$(snrow[0]).find("td:eq(1)").text();
	swal({
		title:i18n('msg_delete'),
		text:i18n('msg_sure_to_delete')+' \n'+snName+': '+snAddress,
		type:"warning",
		showCancelButton:true,
		confirmButtonClass:'btn-warning',
		confirmButtonText:i18n('btn_yes_delete_it'),
		closeOnConfirm:false,
		closeOnCancel:false
	}).then((isConfirm) => {
		if(isConfirm){
			$(snrow[0]).remove();
			swal(i18n('msg_deleted'), i18n('msg_record_deleted'), "success");
			swal.close();
		}else{
			swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "info");
		}
	});
};

function clearTemp(){
	$('input[name^="fileuploadx_"]').each(function(i){
		$(this).val('');
	});
	$.ajax({
		type:'POST',
		url:ctx+"/deleteTempPath.jet",
		async:false,
		success:function(data){
			if(!data)console.log("Not cleared");
		}
	});
};
/*
$('input').blur(function() {
	$(this).parent().removeClass('has-error');
	if (this.id == 'AcccionText') {
		getWayByTTId($('#accion li.selected').val(), 'actionway');
	} else if (this.id == 'addSnListOnEdit') {
		getWayByTTId($('#editaccion li.selected').val(), 'editactionway');
	}
});*/

/* limpia inputs */
$('#client-modal, #edit-modal').on('hidden.bs.modal',function(e){
	$(this).find("input,textarea").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked","").end();
});

$(document).ready(function(){
	$(".modal-demo").on('hide.bs.modal', function(){
		clearTemp();
	});
	$("#edit-modal").on('shown.bs.modal', function () {
		var photo=$('#edPhotoTmp').val();
		if(photo!=''){
			$('.dz-details').css('display','none');
			var photo1=photo.match(/[^\\/:*?"<>|\r\n]+$/);
			$('#uploadXEditClient'+' div div a img').attr('src', 'doctos/images/clients/'+photo1);
		}
	});
	var clid=getVarURL('clid');
	if(clid!=''){
		sessionStorage.setItem('clid',clid);
		getDetailsToEdit(clid);
	}
	if(getVarURL('nw')!=''){
		$('#addNewClient').trigger('click');
		$("#clients-modal").addClass('fornw');
		getCompanies('company');
	}
	history.replaceState({}, document.title, location.pathname+'?language='+getLanguageURL());

	$('#findClientList').DataTable({
		scrollCollapse:true,
		autoWidth:true,
		searching:false,
		paging:false,
		columnDefs:[{'width':'auto','targets':'_all'}]
	});
});