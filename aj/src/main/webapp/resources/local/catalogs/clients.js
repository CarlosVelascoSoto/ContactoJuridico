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
	@param countryid	Id del país o el nombre de la lista (tag-id).
	@param setstateid	Id donde se cargará el listado.
	@param elemtype		Tipo de elemento a cargar; "select", "ol" o "ul".	*/
function getStatesByCountry(countryid, setstateid, elemtype) {
	var elemOp = 'option';
	$('#' + setstateid).empty();
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	countryid=$('#'+countryid+' li.selected').val()||0;
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

function getCitiesByState(id, elemtype, filterByStateId) {
	var elemOp='option';
	if(elemtype=='ul' || elemtype=='ol')
		elemOp = 'li';
	if(!/^[1-9][0-9]*$/.test(filterByStateId))
		filterByStateId=0;
	$('#' + id).empty();
	$.ajax({
		type : 'POST',
		url : ctx + '/getCitiesByState',
		data : 'estadoid='+filterByStateId,
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].ciudadid + '" title="'+ info[i].ciudad + '">'
						+ info[i].ciudad + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ '></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_city')+'. '+e);
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
			var info=data[0]||[];
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
			var info=data[0]||[];
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
	$("#addNewClient").attr('href','#add-clients-modal');
	var tab=$('#clientmodaltabs li:first-child');
	$('.tabsmodal li').removeClass('selectedtab');
	$(tab).addClass('selectedtab');
	$('#errorOnAddClient,#add-clients-modal div.firmdatatabs').hide();
	$('#add-clients-modal div.firmdatatabs:eq(0)').show();

	getCountries('clieCountry', 'ul');
	$('#add-clients-modal').modal('show');

	//	getClientListTab('#consClientList','cons');
// getCompanies('company');
	/*try{
		myDropzone = createDropZoneImg("uploadXClientNew","formNewClient",'',6);
		myDropzone.on('sending', function(file, xhr, formData){
			// formData.append('idx', $('#usersession').val());
		});
	}catch (e){
		clearTemp();
		$('#areaNewClientUpload').html('');
		$('#areaNewClientUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXClientNew" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZoneImg("uploadXClientNew", "formNewClient",'',6);
		myDropzone.on('sending', function(file, xhr, formData){
			// formData.append('idx', $('#usersession').val());
		});
	}
	$("#uploadXClientNew").addClass("dropzone");*/
	try{
		createDropZoneImg('uploadXClientNew','formNewClient',id,6);
	}catch (e){
		clearTemp();
		$('#areaNewClientUpload').html('');
		$('#areaNewClientUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'
			+'<div id="uploadXClientEdit" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZoneImg('uploadXClientNew','formNewClient',id,6);
	}
	$("#uploadXClientNew").addClass("dropzone");
	var profileDropzone=Dropzone.forElement('#uploadXClientNew');
	Dropzone.options.profileDropzone={
		maxFiles:1,
		accept:function(file, done){
				done();
		},init:function(){
			this.on("addedfile", function(){
				if (this.files[1]!=null)
					this.removeFile(this.files[0]);
			});
		}
	}
};

function addClients(mod,refreshPage){
	$('input, select').parent().removeClass('has-error');
	var err='',tag='',client=$('#clieName').val(),address1=$('#clieAddress1').val(),
		country=$('#clieCountry li.selected').val()||0,state=$('#clieState li.selected').val()||0,
		city=$('#clieCity li.selected').val()||0,cp=$('#clieCp').val(),
		email=$('#clieEmail').val(),phone=$('#cliePhone').val(),
		status=$('#clieStatus option:selected').val()||1,cellphone=$('#clieCellphone').val(),
		birthdate=$('#clieBirthdate').val(),comments=$('#clieComments').val(),
		company=$('#clieCompany option:selected').val()||'0', photo=$('#cliePhoto').val()||'',
		typeofperson=$('#clieTypeofperson option:selected').val()||'',
		webpage=$('#clieWebpage').val(),arrSN='',contactperson=$('#clieContactperson').val(),
		rel_with=$('#clieRel_with').val(),ref_by=$('#clieRef_by').val();
	if(client==''){
		tag='clieName';
		err=i18n('err_enter_clientname');
	}else if(typeofperson==''){
		tag='clieTypeofperson';
		err=i18n('err_select_persontype');
	}else if(email.match(/^(?!\s)[\w\d!#$%&'*+/=?`{|}~^-]+(?:\.[\w\d!#$%&'*+/=?`{|}~^-]+)*@(?:[\w\d-]+\.)+[\w\d]{2,6}$/g)==null){
		tag='clieEmail';
		err=i18n('err_enter_email');
	}else if(address1==''){
		tag='clieAddress1';
		err=i18n('err_enter_address');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnAddClient').show();
		$('#putErrorOnAddClient').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	$("#clieSnTableListOnNew tr").each(function (){
		if(this.hasAttribute('data-snrow-OnNew'))
			arrSN+=this.getAttribute('data-snrow-OnNew')+' ';
	});
	arrSN=arrSN.replace(/.$/, "");
	
	if((state==0||country==0) && city>0){
		$.ajax({
			type:"POST",
			url:ctx+"/getCountryStateByCityId",
			data:"cityid="+city,
			async:false,
			success:function(data){
				state=data[0].city[0].estadoid;
				country=data[0].state[0].paisid;
			},error:function(e){
				$('#putErrorOnAddClient').html(i18n('err_record_no_saved'));
				$('#errorOnAddClient').show();
			}
		});
	}
	var param='client='+client+'&address1='+address1+'&city='+city
		+'&state='+state+'&country='+country+'&cp='+cp+'&cellphone='+cellphone
		+'&email='+email+'&phone='+phone+'&status='+status+'&company='+company
		+'&birthdate='+birthdate+'&comments='+comments+'&photo='+photo
		+'&typeofperson='+typeofperson+'&webpage='+webpage+'&arrSN='+arrSN
		+'&contactperson='+contactperson+'&rel_with='+rel_with+'&ref_by='+ref_by;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/addNewClients",
		data:param,
		async:false,
		success:function(data){
			if(data=='msg_data_saved'||(/[1-9][1-9]*/.test(data))){
				swal({
					title : i18n('msg_success'),
					text : i18n('msg_data_saved'),
					type : "success",
					timer : 3000,
					allowEscapeKey : false
				}, function() {});
				window.setTimeout(function() {}, 3000);
				if(mod=='home-cons'){
					$('#add-clients-modal').hide();
					getClientListTab('#consClientList','consClie');
					$('#consClie'+data).prop('checked',true);
					$('#consClie'+data).parent().parent().css('backgroundColor',"ivory");
					$('#consClient').val($('#consClie'+data).parent().next().text());
					return;
				}else{
					location.href=location.pathname+"?language="+ getLanguageURL();
				}
			}else{
				$('#putErrorOnAddClient').html(i18n(data));
				$('#errorOnAddClient').show();
			}
			//$('#company-modal').modal('toggle');
		},
		error:function(e){
			$('#putErrorOnAddClient').html(i18n('err_record_no_saved'));
			$('#errorOnAddClient').show();
		}
	});
};

function getDetailsToEdit(id){
	$('#errorOnEditClient,#edit-clients-modal div.firmdatatabs').hide();
	var tab=$('#edClientmodaltabs li:first-child');
	$('.tabsmodal li').removeClass('selectedtab');
	$(tab).addClass('selectedtab');
	$('#edit-clients-modal div.firmdatatabs:eq(0)').show();
	getCountries('edClieCountry', 'ul');
	$.ajax({
		type:'POST',
		url:ctx+"/getClientsById",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].info[0]||[];
			if(info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_client'),"error");
			}else{
				$('#edCliId').val(info.clientid);
				$('#edClieTypeofperson').val(info.personafiscalid==''?'':info.personafiscalid);
				$('#edClieName').val(info.client);
				$('#edClieAddress1').val(info.address1);

				getTextDDFilterByVal('edClieCountry', info.country);
				if(/[1-9][1-9]*/.test(info.country))
					getTextDDFilterByVal('edClieCountry', info.country);
				if(/[1-9][1-9]*/.test(info.state)){
					getStatesByCountry('edClieCountry','edClieState','ul');
					getTextDDFilterByVal('edClieState', info.state);
				}
				if(/[1-9][1-9]*/.test(info.city)){
					getCitiesByState('edClieCity','ul',$('#edClieState li.selected').val());
					getTextDDFilterByVal('edClieCity', info.city);
				}

				$('#edClieCp').val(info.zipcode);
				$('#edClieEmail').val(info.email);
				$('#edClieCellphone').val(info.cellphone);
				$('#edCliePhone').val(info.phone);
				$('#edClieStatus').val(info.status);
				setdp('#edClieBirthdate',info.birthdate);
				$('#edClieComments').val(info.comments);
				$('#edCliePhoto').attr('src',info.photo);
				$('#edCliePhotoTmp').val(info.photo);
				$('#edClieWebpage').val(info.webpage);
				$('#edClieContactperson').val(info.contactperson);
				$('#edClieRel_with').val(info.rel_with);
				$('#edClieRef_by').val(info.ref_by);

				var snw=data[0].snw,onAddEdit='OnEdit',action='edit';
				$('#clieSnTableList'+onAddEdit+' tbody').empty();
				for(s=0;s<snw.length;s++){
					var id=snw[s][0].snid,
						snid=snw[s][0].socialnetworkid,
						snName=snw[s][1],
						snAddress=snw[s][0].address;
					if(snid!=''&&snAddress!=''){
						var fullURL='https://www.'+snName.toLowerCase()+'.com/'+snAddress.replace(/^(([^:/?#]+):)?(\/\/([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?/g,'$5').replace(/^\//,'');
						var getsn='<tr data-snrow-'+onAddEdit.toLowerCase()+'="'+snid+'||'+snAddress+'||'+id+'"><td>'+snName+'</td>'
							+'<td><a href="'+fullURL+'" rel="noopener noreferrer" target="_blanck" style="cursor:pointer">'+snAddress+'</a></td>'
							+'<td class="tac"><a href="#" data-snidEdit-'+onAddEdit+onAddEdit+'="'+snid+'" class="table-action-btn" title="'+i18n('msg_edit')+' '+snAddress
							+'" onclick="editSN(\''+snid+'||'+snAddress+'||'+id+'\',\'edit\',\''+onAddEdit+'\');$(\'reeditsn\').val(\'1\')"><i class="md md-edit"></i></a></td>'
							+'<td class="tac"><a href="#" data-snidDel-'+onAddEdit+'="'+snid+'" class="table-action-btn" title="'+i18n('msg_delte')+' '+snAddress
							+'" onclick="deleteSN(\''+snid+'||'+snAddress+'||'+id+'\',\''+onAddEdit+'\');"><i class="md md-delete"></i></a></td></tr>';
					}
					$('#clieSnTableList'+onAddEdit+' tbody:last-child').append(getsn);
				}
				if(typeof sessionStorage.clid!= 'undefined'){
					sessionStorage.removeItem('clid');
					$('#edit-clients-modal').modal('show');
				}
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_client'),"error");
		}
	});
	try{
		createDropZoneImg('uploadXClientEdit','formEditClient',id,6);
	}catch (e){
		clearTemp();
		$('#areaEditClientUpload').html('');
		$('#areaEditClientUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'
			+'<div id="uploadXClientEdit" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZoneImg('uploadXClientEdit','formEditClient',id,6);
	}
	$("#uploadXClientEdit").addClass("dropzone");
	var profileDropzone=Dropzone.forElement('#uploadXClientEdit');
	Dropzone.options.profileDropzone={
		maxFiles:1,
		accept:function(file, done){
			done();
		},init:function(){
			this.on("addedfile", function(){
				if (this.files[1]!=null)
					this.removeFile(this.files[0]);
				});
		}
	}
};

function updateClient(){
	$('input').parent().removeClass('has-error');
	var err='',client=$('#edClieName').val(),address1=$('#edClieAddress1').val(),
		country=$('#edClieCountry li.selected').val()||0,state=$('#edClieState li.selected').val()||0,
		city=$('#edClieCity li.selected').val()||0,cp=$('#edClieCp').val(),
		email=$('#edClieEmail').val(),phone=$('#edCliePhone').val(),status=$('#edClieStatus option:selected').val(),
		cellphone=$('#edClieCellphone').val(),birthdate=$('#edClieBirthdate').val(),comments=$('#edClieComments').val(),
		photo=$('#edCliePhoto').val()==''?$('#edCliePhotoTmp').val():$('#edCliePhoto').val(),
		typeofperson=$('#edClieTypeofperson option:selected').val(),webpage=$('#edClieWebpage').val(),arrSN="",
		company=$('#edClieCompany option:selected').val(),contactperson=$('#edClieContactperson').val(),
		rel_with=$('#edClieRel_with').val(), ref_by=$('#edClieRef_by').val();
	typeofperson=typeofperson||'';
	company=company||'0';
	if(client==''){
		tag='edClieName';
		err=i18n('err_enter_clientname');
	}else if(typeofperson==''){
		tag='edClieTypeofperson';
		err=i18n('err_select_persontype');
	}else if(address1==''){
		tag='edClieAddress1';
		err=i18n('err_enter_address');
	}else if(email.match(/^(?!\s)[\w\d!#$%&'*+/=?`{|}~^-]+(?:\.[\w\d!#$%&'*+/=?`{|}~^-]+)*@(?:[\w\d-]+\.)+[\w\d]{2,6}$/g)==null){
		tag='edClieEmail';
		err=i18n('err_enter_email');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnEditClient').show();
		$('#putErrorOnEditClient').html(err);
		$('.modal').animate({scrollTop:0},'1000');
		return false;
	}
	$("#clieSnTableListOnEdit tr").each(function (){
		if(this.hasAttribute('data-snrow-OnEdit'))
			arrSN+=this.getAttribute('data-snrow-OnEdit')+' ';
	});
	arrSN=arrSN.replace(/.$/, "");
	
	if((state==0||country==0) && city>0){
		$.ajax({
			type:"POST",
			url:ctx+"/getCountryStateByCityId",
			data:"cityid="+city,
			async:false,
			success:function(data){
				state=data[0].city[0].estadoid;
				country=data[0].state[0].paisid;
			},error:function(e){
				$('#putErrorOnEditClient').html(i18n('err_record_no_saved'));
				$('#errorOnEditClient').show();
			}
		});
	}
	var param='id='+$('#edCliId').val()+'&client='+client+'&address1='+address1+'&city='+city
		+'&state='+state+'&country='+country+'&cp='+cp+'&cellphone='+cellphone
		+'&email='+email+'&phone='+phone+'&status='+status
		+'&birthdate='+birthdate+'&comments='+comments+'&photo='+photo
		+'&typeofperson='+typeofperson+'&webpage='+webpage+'&arrSN='+arrSN
		+'&contactperson='+contactperson+'&ref_by='+ref_by+'&rel_with='+rel_with;
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
					timer:3000,
					allowEscapeKey:false
				},function(){
					location.href=location.pathname+"?language="+ getLanguageURL();
				});
				window.setTimeout(function(){
					location.href=location.pathname+"?language="+ getLanguageURL();
				},3000);
			}else{
				$('#putErrorOnEditClient').html(i18n('err_record_no_saved'));
				$('#errorOnEditClient').show();
			}
		},error:function(e){
			$('#putErrorOnEditClient').html(i18n('err_record_no_saved'));
			$('#errorOnEditClient').show();
		}
	});
};

$('#addNewClient').click(function(){
	fnAddNewClient();
});

$('#addClientsCancel').click(function(){
	forceclose('#add-clients-modal');
	Custombox.close();
});

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
				url:ctx+"/deleteClients",
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
	}else if(!/^((https?|ftp|smtp):\/\/)?(www.)?[a-zA-Z0-9_~+:-]+\.[a-zA-Z0-9_-]{2,}(\/[a-zA-Z0-9_~+:#-]+\/?)*(\?.+)?$/.test(snAddress)){
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
	$('#clieSnTableList'+onAddEdit+' tbody:last-child').append(newsn);
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

function selectionRange(id, start, end){
    var field = document.getElementById(id);
    field.focus();
    field.setSelectionRange(start, end);
    console.log("start "+start+", end "+end);
};

$(document).ready(function(){
	$("#addSnListOnNew, #editSnListOnNew").on('change', function(){
		var field=(this.id=='addSnListOnNew'?'add':'edit')+'SnAddressOnNew';
		var addr='',hasData=$('#'+field).val();
		if(hasData==''){
			addr=$('option:selected',this).data('description');
			addr=addr.replace(/\/$/,'');
			hasData='/'+i18n('msg_your_account');
		}
		addr=addr.replace(/^\//,'');
		$('#'+field).val(addr+hasData);
		selectionRange(field,addr.length+1,$('#'+field).val().length);
	});
	$("#addSnListOnEdit, #editSnListOnEdit").on('change', function(){
		var field=(this.id=='addSnListOnEdit'?'add':'edit')+'SnAddressOnEdit';
		var addr='',hasData=$('#'+field).val();
		if(hasData==''){
			addr=$('option:selected',this).data('description');
			addr=addr.replace(/\/$/,'');
			hasData='/'+i18n('msg_your_account');
		}
		addr=addr.replace(/^\//,'');
		$('#'+field).val(addr+hasData);
		selectionRange(field,addr.length+1,$('#'+field).val().length);
	});
});