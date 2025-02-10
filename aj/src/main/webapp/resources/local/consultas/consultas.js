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

function getClientListTab(targetList,abbr) {
	$('.containTL table').empty();
	abbr=abbr||'clie';
	$.ajax({
		type : "POST",
		url : ctx + "/getClientList",
		async : false,
		dataType : 'JSON',
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				var tablelist = '<tr><th style="display:none;"></th>'
					+ '<th>' + i18n('msg_client') + '</th>'
					+ '<th>' + i18n('msg_address') + '</th>'
					+ '<th>' + i18n('msg_city') + '</th></tr>';
				for (i = 0; i < info.length; i++)
					tablelist += '<tr class="rowopt"><td style="display:none">'
					+ '<input type="radio" name="rowline" data-val="'
					+ info[i][1] + '" id="' + abbr + info[i][0] + '"></td>'
					+ '<td>' + info[i][1] + '</td>'
					+ '<td>' + info[i][2] + '</td>'
					+ '<td>' + info[i][3] + ', ' + info[i][4] + '</td></tr>';
				$(targetList).append(tablelist);
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_client') + '. ' + e);
		}
	});
};

function getMaterias(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getMaterias",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].materiaid + '" title="'+ info[i].materia + '">'
						+ info[i].materia + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ '></' + elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_matter') + '. ' + e);
		}
	});
};

function getTrials(id, clientid, elemtype){
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	clientid=clientid||0;
	$.ajax({
		type : "POST",
		url : ctx + "/getTrialList",
		data:"clientid="+clientid,
		async : false,
		success : function(data) {
			var info=data[0]||[];
			if (info.length>0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].juicioid + '" title="'+ info[i].juicio + '">'
						+ info[i].juicio + '</' + elemOp+ '>');
			}else{
				$('#' + id).append('<'+ elemOp+ '>' + i18n('msg_no_data_client') + '</' + elemOp + '>');
			}
			$('#' + id).append('<'+ elemOp+ '></' + elemOp + '>');
		},error : function(e) {
			console.log(i18n('err_unable_get_trial') + '. ' + e);
		}
	});
};

function loadHomeConsulting(){
	$('#consClient').val('');
	getClientListTab('#consClientList','consClie');
	getMaterias('matterCons','ul');
	returnConsHome();
	$('#consultationList').empty();
};

function getClientConsList(clid,abbr,listid){
	$(listid).empty();
	$.ajax({
		type:"POST",
		data:"clid="+clid,
		url:ctx+"/getClientConsList",
		async:false,
		success:function(data){
			var info=data[0]||[],tablelist='<tr><th>'+i18n('msg_no_data_client')+'</th></tr>';
			if(info.length>0){
				tablelist = '<tr><th class="dnone"></th>'
					+'<th>'+i18n('msg_consultation')+'</th>'
					+'<th>'+i18n('msg_date')+'</th>'
					+'<th>'+i18n('msg_trial')+'</th>'
					+'<th>'+i18n('msg_matter')+'</th>'
					+'<th>'+i18n('msg_edit')+'</th></tr>';
				for(i=0; i<info.length; i++)
					tablelist+='<tr class="rowopt trn2ms">'
						+'<td style="display:none">'
						+ '<input type="radio" name="rowline" data-val="'+info[i][0]
						+ '" id="'+abbr+info[i][0]+'"></td>'
						+'<td class="asLink trn2ms c39c" onclick="showConsHome('+info[i][0]+')">'+info[i][1]+'</td>'
						+'<td>'+formatDateTime(info[i][2])+'</td>'
						+'<td>'+(info[i][3]==null?'- - - - -':info[i][3])+'</td>'
						+'<td>'+info[i][4]+'</td>'
						+'<td style="text-align:center"><a href="#" id="'+info[i][0]+'" class="asLink" title="'
						+ i18n('msg_matter')+'" onclick="getDetailsConsHome('+info[i][0]
						+ ');"><i class="md md-edit"></i></a>'
					+'</td></tr>';
			}
			$(listid).append(tablelist);
		},error:function(e){
			console.log(i18n('err_unable_get_consultations') + '. ' + e);
		}
	});
};

function addConsultation(mod){
	$('input, select, textarea').parent().removeClass('has-error');
	$('#errorOnAddCons, .containTL').hide();
	var err='',tag='', clid='', trialid=$('#trialCons li.selected').val()||'0',
		matter=$('#matterCons li.selected').val()||'0',
		consFees=$('#consFees').val()||0,consultation=$('#consultation').val(),
		opinionCons=$('#opinionCons').val(),resume=$('#resumeCons').val();
	clid=$('#consClientList [name=rowline]:checked')[0];
	clid=(typeof clid=='undefined')? '0':(clid.id).replace(/[^0-9]/g,'');
	if(clid=='0'){
		tag='consClient';
		err='err_select_client';
	}else if(matter==''){
		tag='addMatterCons';
		err='err_enter_consult';
	}else if(consFees<0){
		if(!/^\d+(\.[0-9]{1,4})?$/g.test(consFees)){
			tag='consFees';
			err='err_enter_only_numbers';
		}else{
			consFees=leftZeros(consFee,1);
		}
	}else if(consultation==''){
		tag='consultation';
		err='err_enter_consult';
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#putErrorOnAddCons').html(i18n(err));
		$('#errorOnAddCons').show();
		$('#add-consulting-modal, #consulting-modal, [data-groupcons="newconsultations"],'
			+' .custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='clientid='+clid + '&juicio='+trialid + '&materia='+matter
		+'&honorarios='+consFees + '&consulta='+consultation
		+'&opiniones='+opinionCons + '&resumen='+resume;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+='&'+$(this).attr('name')+'='+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/addNewConsulta",
		data:param,
		async:false,
		success:function(data){
			if(data=="msg_data_saved"){
				swal(i18n("msg_success"),i18n(data),"success");
				if(mod=='home'){
					returnConsHome();
					getClientListTab('#consClientList','consClie');
					$('#consClie' + clid).prop('checked',true);
					$('#consClie').val($('#consClie' + clid).parent().next().text());
					$('#consClie' + clid).parent().parent().css('backgroundColor',"ivory");
					getClientConsList(clid,'cons','#consultationList');
				}else{
					location.href=location.href.replace(/^(.*)\#.*$/, '$1');
				}
				window.setTimeout(function(){
					if(mod=='home'){
						returnConsHome();
						getClientListTab('#consClientList','consClie');
						$('#consClie' + clid).prop('checked',true);
						$('#consClie').val($('#consClie' + clid).parent().next().text());
						$('#consClie' + clid).parent().parent().css('backgroundColor',"ivory");
						getClientConsList(clid,'cons','#consultationList');
					}else{
						location.href=location.href.replace(/^(.*)\#.*$/, '$1');
					}
				},3000);
			}else{
				$('#putErrorOnAddCons').html(i18n(data));
				$('#errorOnAddCons').show();
				return null;
			}
		},error:function(e){
			$('#putErrorOnAddCons').html(i18n('err_record_no_saved'));
			$('#errorOnAddCons').show();
		}
	});
};

function showConsHome(id){
	/*$('[data-groupcons], [data-consultation="editclient"]').hide();
	$('[data-groupcons="detailsconsultations"]').show();
	$('#editconsultId').val(id);
	getDetailsConsultations(id);
	$('#titleHomeCons').html(' - ' + i18n('msg_edit'));*/
	location.href=ctx+'/consultasdashboard?language='+ getLanguageURL()+'&rid='+id;
};

function getDetailsConsHome(id){
	$('#formEditCons input, #formEditCons select, #formEditCons textarea').val('');
	$('[data-groupcons], [data-consultation="editclient"]').hide();
	$('[data-groupcons="detailsconsultations"]').show();
	$('#editconsultId').val(id);
	getDetailsConsultations(id);
	$('#titleHomeCons').html(' - ' + i18n('msg_edit'));
};

function getDetailsConsultations(id){
	$('#errorOnEditCons, .containTL').hide();
	$('#edit-consulting-modal input, #edit-consulting-modal select, #edit-consulting-modal textarea').val('');
	$('input, select, textarea').parent().removeClass('has-error');
	$.ajax({
		type:'POST',
		data:"id="+id,
		url:ctx+"/getDetailsByConsId",
		data:'id='+id,
		async:false,
		success:function(data){
			var info=data[0].detail[0]||[];
			if(info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_consultations'),"error");
				return;
			}
			var clid=info.clienteid;
			$('#editconsultId').val(info.consultaid);
			getClientListTab('#editconsClientList','editconsClient');
			$('#editconsClient' + clid).prop('checked',true);
			$('#editconsClient').val($('#editconsClient' + clid).parent().next().text());
			$('#editconsClient' + clid).parent().parent().css('backgroundColor',"ivory");

			
			if(info.materiaid!='')
				getTextDDFilterByVal('editmatterCons', info.materiaid);
			$('#editconsFees').val(info.honorarios);

			getTrials('edittrialCons',clid,'ul');
			getTextDDFilterByVal('edittrialCons', info.juicioid);
			getMaterias('editmatterCons','ul');
			if(info.materiaid!='')
				getTextDDFilterByVal('editmatterCons', info.materiaid);
			$('#editconsultation').val(info.consulta);
			$('#editopinionCons').val(info.opinion);
			$('#editresumeCons').val(info.resumen);
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_protections'),"error");
		}
	});
	try{
		createDropZone('uploadXEditCons', 'formEditCons', id, 11);
	}catch (e){
		$('#areaEditConsUpload').html('');
		$('#areaEditConsUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'
			+'<div id="uploadXEditCons" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZone('uploadXEditCons', 'formEditCons', id, 11);
	}
	$("#uploadXEditCons").addClass("dropzone");
};

function updateConsultation(mod){
	var err='',tag='',consultaid=$('#editconsultId').val(),
		clid=$('#editconsClientList [name=rowline]:checked')[0].id,
		consFees=$('#editconsFees').val()||0,trialid=$('#edittrialCons li.selected').val()||'0',
		matter=$('#editmatterCons li.selected').val()||'',consulta=$('#editconsultation').val(),
		opinion=$('#editopinionCons').val(),resumen=$('#editresumeCons').val();
	clid=(typeof clid=='undefined')? '0':(clid).replace(/[^0-9]/g,'');
	if(clid=='0'){
		tag='editconsClient';
		err='err_select_client';
	}else if(matter==''){
		tag='editMatterCons';
		err='err_enter_consult';
	}else if(consFees<0){
		if(!/^\d+(\.[0-9]{1,4})?$/g.test(consFees)){
			tag='editconsFees';
			err='err_enter_only_numbers';
		}else{
			consFees=leftZeros(consFee,1);
		}
	}else if(consulta==''){
		tag='editconsultation';
		err='err_enter_consult';
	}
	if(err!=''){
		$('#' + tag).parent().addClass('has-error');
		$('#putErrorOnEditCons').html(i18n(err));
		$('#errorOnEditCons').show();
		$('.modal, [data-groupcons="detailsconsultations"]').animate({scrollTop:0},'1000');
		return false;
	}
	var param='consultaid='+consultaid + '&clientid='+clid
		+'&honorarios='+consFees + '&juicio='+trialid + '&materia='+matter
		+'&consulta='+consulta + '&opinion='+opinion + '&resumen='+resumen;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+='&'+$(this).attr('name')+'='+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/updateConsultation",
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
					if(mod=='home'){
						returnConsHome();
						getClientListTab('#consClientList','consClie');
						$('#consClie' + clid).prop('checked',true);
						$('#consClie').val($('#consClie' + clid).parent().next().text());
						$('#consClie' + clid).parent().parent().css('backgroundColor',"ivory");
						getClientConsList(clid,'cons','#consultationList');
					}else{
						location.href=location.href.replace(/^(.*)\#.*$/, '$1');
					}
				});
				window.setTimeout(function(){
					if(mod=='home'){
						returnConsHome();
						getClientListTab('#consClientList','consClie');
						$('#consClie' + clid).prop('checked',true);
						$('#consClie').val($('#consClie' + clid).parent().next().text());
						$('#consClie' + clid).parent().parent().css('backgroundColor',"ivory");
						getClientConsList(clid,'cons','#consultationList');
					}else{
						location.href=location.href.replace(/^(.*)\#.*$/, '$1');
					}
				},3000);
			}else{
				$('#putErrorOnEditCons').html(i18n('err_record_no_saved'));
				$('#errorOnEditCons').show();
			}
		},error:function(e){
			$('#putErrorOnEditCons').html(i18n('err_record_no_saved'));
			$('#errorOnEditCons').show();
		}
	});
};

function deleteConsultation(id){
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
				url:ctx+"/deleteProtection",
				data:param,
				async:false,
				success:function(data){
					if(data=="false"){
						swal(i18n('msg_warning'),i18n('err_on_delete'),"warning");
						return;
					}
					swal({
						title:i18n('msg_deleted'),
						text:i18n('msg_record_deleted'),
						type:"success",
						timer:3000,
						allowEscapeKey:false
					},function(){
						location.href=location.href.replace(/^(.*)\#.*$/, '$1');
					});
				},error:function(resp){
					swal(i18n('msg_warning'),i18n('err_on_delete'),"warning");
				}
			});
		}else{
			swal(i18n('msg_cancelled'),i18n('msg_record_safe'),"warning");
		}
	});
};

function returnConsHome(){
	$('[data-groupcons],#prevClientCons,#errorOnAddCons').hide();
	$(' .rowopt').css('backgroundColor','#fff');
	$('#matterCons li, #trialCons li').removeClass('selected');
	$('[data-groupcons="newconsultations"] input, [data-groupcons="newconsultations"] textarea').val('');
	$('[data-groupcons="consultations"],[data-subgroupcons],[data-groupcons="footer"]').show();
	$('#homeAddConsultation').removeClass('keepPressed');
	$('#titleHomeCons').html('');
};

function leftZeros(number,leftZeros){
	leftZeros=leftZeros||0;
	const parts=number.toString().split('.');
	var integerPart=parts[0],
		decimalPart=parts[1]||'';
	integerPart = integerPart.padStart(leftZeros,'0');
	return decimalPart=''?integerPart:integerPart+'.'+decimalPart;
};

$(window).load(function(){
	$('#consClient').on('click keyup input paste change delete', function(e) {
		filtext(e.target.value, '#consClientList');
		$('.containTL, #consClientList').show();
	});

	$('#consHome').on('click keyup input paste change delete', function(e) {
		$('[data-cons], .containTL-cons').hide();
		$('[data-groupcons="consultations"]').show();
		$("#prevClientCons").prop("disabled",false);
		var clid=$('#consClientList [name=rowline]:checked')[0];
		clid=(typeof clid=='undefined')? '0':(clid.id).replace(/[^0-9]/g,'');
		if(clid==0){
			$('#prevClientCons').prop('disabled',false);
			$('[data-groupcons="newconsultations"]').hide();
			$('[data-groupcons="consultations"]').show();
			return;
		}
		getClientConsList(clid,'cons','#consultationList');
		$('#prevClientCons').prop('disabled',true);
		filtext(e.target.value, '#consClientList');
		$('.containTL-cons').show();
	});

	$('#prevClientCons').on('click', function(e) {
		$('[data-groupcons="newconsultations"]').hide();
		$('[data-groupcons="consultations"]').show();
	});

	$('#viewClientCons').on('click', function(e) {
		var clid=$('#consClientList [name=rowline]:checked')[0];
		clid=(typeof clid=='undefined')? '0':(clid.id).replace(/[^0-9]/g,'');
		if(clid>0){
			getClientConsList(clid,'cons','#consultationList');
			$('#prevClientCons').prop('disabled',true);
			$('[data-groupcons="consultations"]').hide();
			$('[data-groupcons="newconsultations"]').show();
		}else{
			$('#prevClientCons').prop('disabled',false);
			$('[data-groupcons="newconsultations"]').hide();
			$('[data-groupcons="consultations"]').show();
		}
	});

	$('#trialConsList').on('click keyup input paste change delete', function(e) {
		var clid=$('#consClientList [name=rowline]:checked')[0];
		clid=(typeof clid=='undefined')? '0':(clid.id).replace(/[^0-9]/g,'');
		if(clid==0){
			$('#consClient').parent().addClass('has-error');
			$('#putErrorOnAddCons').html(i18n('msg_select_clientfirst'));
			$('.custombox-modal-open').animate({scrollTop:0},'1000');
			$('#errorOnAddCons').show();
			return;
		}
		getTrials('trialCons',clid,'ul');
		optImgListIcon(this);
	});
	$('#homeAddClientCons,#addClientCons,#editClientCons').on('click', function(e){
		var tab=$('#clientmodaltabs li:first-child');
		$('.tabsmodal li').removeClass('selectedtab');
		$(tab).addClass('selectedtab');
		$('#add-clients-modal div.firmdatatabs').hide();
		$('#add-clients-modal div.firmdatatabs:eq(0)').show();
		getCountries('clieCountry', 'ul');
		$('#add-clients-modal').modal('show').show();
	});
	$('#homeAddConsultation').on('click', function(e){
		if($('#homeAddConsultation').hasClass('keepPressed')){
			returnConsHome();
		}else{
			$('[data-groupcons],[data-subgroupcons="consultationsList"]').hide();
			$('[data-groupcons="newconsultations"],[data-groupcons="consultations"]').show();
			$('#titleHomeCons').html(' - '+i18n('msg_new_record'));
			$('#homeAddConsultation').addClass('keepPressed');
			try{
				myDropzone=createDropZone('uploadXAddCons', 'formAddCons','','');
				myDropzone.on('sending', function(file, xhr, formData){/*...*/});
			}catch (e){
				clearTemp();
				$('#areaAddConsUpload').html('');
				$('#areaAddConsUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
					'<div id="uploadXAddCons" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
				myDropzone=createDropZone('uploadXAddCons', 'formAddCons','','');
				myDropzone.on('sending', function(file, xhr, formData){/*...*/});
			}
			$('#uploadXAddCons').addClass('dropzone');
		}
	});

	$('#consCancel, #consEditCancel').on('click', function(e){
		returnConsHome();
	});

	document.addEventListener('click',function(e){
		if (e.target.nodeName == 'TD') {
			var tpe=e.target.parentElement,targetId='',rb;
			var tList=tpe.parentElement.parentElement.id;
			if (tList=='consClientList'|| tList=='editconsClientList')
				targetId=(tList=='consClientList'?'cons':'editcons')+'Client';
			if(targetId!=''){
				$(' .rowopt').css('backgroundColor','#fff');
				tpe.style.backgroundColor="ivory";
				rb=$(tpe).find('input:radio[name=rowline]')[0];
				rb.checked=true;
				$('#'+targetId).val($(rb).attr('data-val'));
				$('.containTL').hide();
				if($('#'+targetId).data('cons')=='clienthome'){
					if (typeof $('#'+tList+' [name=rowline]:checked')[0] != 'undefined')
						clientid = $('#'+tList+' [name=rowline]:checked')[0].id;
					clid = (typeof clientid == 'undefined') ? '' : clientid.replace(/[^0-9]/g,'');
					getClientConsList(clid,'cons','#consultationList');
				}else if($('#'+targetId).data('cons')=='editclientMod'){
					var err='',clid=$('#editconsClientList [name=rowline]:checked')[0].id
					clid=(typeof clid=='undefined')?'0':clid.replace(/[^0-9]/g,'');
					if(clid==0){
						$('#'+targetId).empty();
						$('#editconsClient').parent().addClass('has-error');
						$('#putErrorOnEditCons').html(i18n('msg_select_clientfirst'));
						$('#errorOnEditCons').show();
						$('#edit-consulting-modal, .custombox-modal-open').animate({scrollTop:0},'1000');
						var option=new Option(i18n('msg_select_clientfirst'),'',!1,!0);
						$('#'+targetId).append(option);
						return;
					}else{
						getTrials('edittrialCons', clid, 'ul')
					}
				}
			}
			return e=null;
		}
	}, false);

	$("#addNewConsultation").click(function(){
		$('#errorOnAddConsultation, .containTL').hide();
		$('input, select, textarea').parent().removeClass('has-error');
		$('#add-consulting-modal').modal('show');
		getClientListTab('#consClientList','consClie');
		getMaterias('matterCons','ul');

		try{
			myDropzone=createDropZone('uploadXAddCons', 'formAddCons','','');
			myDropzone.on('sending', function(file, xhr, formData){/*...*/});
		}catch (e){
			clearTemp();
			$('#areaAddConsUpload').html('');
			$('#areaAddConsUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
				'<div id="uploadXAddCons" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
			myDropzone=createDropZone('uploadXAddCons', 'formAddCons','','');
			myDropzone.on('sending', function(file, xhr, formData){/*...*/});
		}
		$('#uploadXAddCons').addClass('dropzone');
	});

	$('#editconsClient').on('click keyup input paste change delete', function(e) {
		filtext(e.target.value, '#editconsClientList');
		$('html, body').animate({scrollTop : 0}, '300');
		var id = e.target.id, input, filter, table, tr, i;
		input = e.target;
		filter = input.value.toUpperCase();
		table = document.getElementById(id + 'List');
		tr = $('#' + id + 'List .rowopt');
		for (i = 0; i < tr.length; i++)
			if (tr[i].innerText.toUpperCase().indexOf(filter) >= 0)
				tr[i].style.display = "";
			else
				tr[i].style.display = "none";
		$('.containTL').show();
	});

	// Polyfill padStart
	if(!String.prototype.padStart){
	    String.prototype.padStart=function padStart(targetLength,padString){
	        targetLength=targetLength>>0; //Trunca si el nuvero o es convertido a un non-number para 0;
	        padString=String((typeof padString!=='undefined'?padString:' '));
	        if(this.length>targetLength){
	            return String(this);
	        }else{
	            targetLength=targetLength-this.length;
	            if(targetLength>padString.length)
	                padString+=padString.repeat(targetLength/padString.length); //append to original to ensure we are longer than needed
	            return padString.slice(0,targetLength)+String(this);
	        }
	    };
	}

	var clid=getVarURL('clid');
	if(clid!=''){
		history.replaceState({}, document.title, location.pathname+'?language='+getLanguageURL());
	}
});