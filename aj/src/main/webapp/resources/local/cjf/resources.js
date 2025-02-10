;function getClientListTab(targetList,abbr) {
	$('.containTL table').empty();
	abbr=abbr||'cidRsc';
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

/*;function getClientListTab(id){
	$('.containTL table').empty();
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
					tablelist += '<tr class="rowopt"><td style="display:none;">'
					+ '<input type="radio" name="rowline" data-val="'
					+ info[i][1] + '" id="cidRsc' + info[i][0] + '"></td>'
					+ '<td>' + info[i][1] + '</td>'
					+ '<td>' + info[i][2] + '</td>'
					+ '<td>' + info[i][3] + ', ' + info[i][4] + '</td></tr>';
				$('#' + id).append(tablelist);
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_client') + '. ' + e);
		}
	});
};*/

function getProtections(id, elemtype){
	var originType=$('#'+id.replace('Id','')).val(),elemOp='option',client='clientRsc',clientid='';
	if(originType==''||originType==null){
		$('#' + id).parent().addClass('has-error');
		$('#puterrorOnAddRsc').html(i18n('err_select_origintype'));
		$('#errorOnAddRsc').show();
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return;
	}
	if(id=='edOriginTypeId')
		client='edClientRsc';
	if ($('#' + client + 'List [name=rowline]:checked')[0] != null)
		clientid=$('#' + client + 'List [name=rowline]:checked')[0].id;
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	clientid=clientid.replace(/[^0-9]/g,'');
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getProtectionsByClient",
		data : "origintype=" + originType + "&clientid=" + (clientid==''?'0':clientid),
		async : false,
		success : function(data) {
			if(data[0]==null){
				swal(i18n('msg_warning'), i18n('msg_select_client'), 'info');
				return;
			}
			var info = data[0].list||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].amparoid + '" title="'
						+ i18n('msg_character_client') + ': ' + (info[i].quejoso==''?i18n('msg_interestedtrdparty'):i18n('msg_complaining')) + '">'
						+ info[i].amparo + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ '></'+ elemOp + '>');
			} else {
				$('#' + id).append('<'+ elemOp+ '>' + i18n('msg_no_data_client') + '</'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_client')+'. '+e);
		}
	});
};

function addResource(e){
	$('#eerrorOnAddRsc, .containTL').hide();
	$('input, select').parent().removeClass('has-error');
	var err="",tag='',resourcenumber=$('#resourcenumber').val(),resourcetype=$('#resourcetype option:selected').val(),
		originType=$('#originType').val(),originTypeId=$('#originTypeId li.selected').val()||'',
		recurring=$('#recurring').val(),resolutionAppl=$('#resolutionAppl').val(),datenoticeApplRs=$('#datenoticeApplRs').val(),
		interpositionRvwdate=$('#interpositionRvwdate').val(),dateadmissionApProc=$('#dateadmissionApProc').val(),
		daterefcollcourt=$('#daterefcollcourt').val(),resourcesent=$('#resourcesent').val(),
		dateadmissionCllCourt=$('#dateadmissionCllCourt').val(),notifdateadmissionCllCourt=$('#notifdateadmissionCllCourt').val(),
		adhesiveRvwAplDate=$('#adhesiveRvwAplDate').val(),dateshiftpresent=$('#dateshiftpresent').val(),
		sessiondateprojectRslc=$('#sessiondateprojectRslc').val(),resolutiondate=$('#resolutiondate').val(),
		clientid=$('#clientRscList [name=rowline]:checked')[0];
	clientid=(typeof clientid=='undefined')? '0':(clientid.id).replace(/[^0-9]/g,'');
	if(originType==''||originType==null)originType='';
	if(originTypeId==''||originTypeId==null)originTypeId='0';
	if(resourcetype==''){
		tag='resourcetype';
		err='err_select_resourcetype';
	}else if(clientid=='0'){
		tag='clientRsc';
		err='err_select_client';
	}else if(originType==''){
		tag='originType';
		err='err_select_origintype';
	}else if(originTypeId=='0'){
		tag='originTypeId';
		err='err_enter_originprot';
	}else if(recurring==''){
		tag='recurring';
		err='err_enter_recurring';
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#puterrorOnAddRsc').html(i18n(err));
		$('#errorOnAddRsc').show();
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='resourcenumber='+resourcenumber+'&resourcetype='+resourcetype+'&recurring='+recurring
		+'&originType='+originType+'&originTypeId='+originTypeId
		+'&resolutionAppl='+resolutionAppl+'&datenoticeApplRs='+datenoticeApplRs
		+'&interpositionRvwdate='+interpositionRvwdate
		+'&dateadmissionApProc='+dateadmissionApProc+'&daterefcollcourt='+daterefcollcourt
		+'&resourcesent='+resourcesent+'&dateadmissionCllCourt='+dateadmissionCllCourt
		+'&notifdateadmissionCllCourt='+notifdateadmissionCllCourt
		+'&adhesiveRvwAplDate='+adhesiveRvwAplDate+'&dateshiftpresent='+dateshiftpresent
		+'&sessiondateprojectRslc='+sessiondateprojectRslc
		+'&resolutiondate='+resolutiondate + '&clientid='+clientid;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/addNewResource",
		data:param,
		async:false,
		success:function(data){
			if(data=="msg_data_saved"){
				swal(i18n("msg_success"),i18n(data),"success");
				location.href=location.href.replace(/^(.*)\#.*$/, '$1');
			}else{
				$('#puterrorOnAddRsc').html(i18n(data));
				$('#errorOnAddRsc').show();
				return null;
			}
			$('#resource-modal').modal('toggle');
		},error:function(e){
			$('#puterrorOnAddRsc').html(i18n('err_record_no_saved'));
			$('#errorOnAddRsc').show();
		}
	});
	$('.custombox-modal-open').animate({scrollTop:0},'1000');
};

function getDetailsToEditRec(id){
	$('input, select').parent().removeClass('has-error');
	$('#errorOnEditRsc, .containTL').hide();
	$('#formEditResource input, #formEditResource select, #formEditResource textarea').val('');
	$.ajax({
		type:'POST',
		url:ctx+"/getDetailsByResource",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].resource[0]||[];
			if(data[0].length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_resource'),"error");
				return;
			}
			$('#edit-modal select, #edit-modal input[type="text"]')
				.not('input[type="hidden"]').val('');
			$('*[id*=date]').val('');
			$('#edId').val(info.recursoid);
			$('#edResourcenumber').val(info.recurso);

			var clid=data[0].client;
			getClientListTab('#edClientRscList');
			$('#edClientRsc').val(data[0].clientName);
			$('#edClientRscList #cidRsc' + clid).prop('checked',true);
			$('#cidRsc' + clid).parent().parent().css('backgroundColor',"ivory");

			$('#edResourcetype').val(info.recursotipo);
			$('#edRecurring').val(info.recurrente);
			$('#edResolutionAppl').val(info.resolucionrecurrida);
			$('#edResourcesent').val(info.recursoturnadoa);
			
			setdp('#edDatenoticeApplRs',info.fechanotificacionresolucionrecurrida);
			setdp('#edInterpositionRvwdate',info.fechainterposicionrecurso);
			setdp('#edDateadmissionApProc',info.fechaadmisiontramiterecurso);
			setdp('#edDaterefcollcourt',info.fecharemisionaltribunalcolegiado);
			setdp('#edDateadmissionCllCourt',info.fechaadmisiontribunalcolegiado);
			setdp('#edNotifdateadmissionCllCourt',info.fechanotificacionadmisiontribunalcolegiado);
			setdp('#edAdhesiveRvwAplDate',info.fecharecursorevisionadhesivo);
			setdp('#edDateshiftpresent',info.fechaturnoaponencia);
			setdp('#edSessiondateprojectRslc',info.fechasesionproyectoresolucion);
			setdp('#edResolutiondate',info.fecharesolucion);

			if(info.tipoorigen!=''){
				$('#edOriginType').val(info.tipoorigen);
				$('#edOriginTypeId').val(info.tipoorigenid);
				getProtections('edOriginTypeId','ul');
				getTextDDFilterByVal('edOriginTypeId',info.tipoorigenid);
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_resource'),"error");
		}
	});
	try{
		createDropZone('uploadXEditResource', 'formEditResource', id, 5);
	}catch (e){
		clearTemp();
		$('#areaRscUpload').html('');
		$('#areaRscUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXEditResource" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZone('uploadXEditResource', 'formEditResource', id, 5);
	}
	$("#uploadXEditResource").addClass("dropzone");
};

function updateResoruce(){
	var err="",tag="",resourcenumber=$('#edResourcenumber').val(),
		resourcetype=$('#edResourcetype option:selected').val(),
		originType=$('#edOriginType').val(),originTypeId=$('#edOriginTypeId li.selected').val()||'',
		recurring=$('#edRecurring').val(),resolutionAppl=$('#edResolutionAppl').val(),
		datenoticeApplRs=$('#edDatenoticeApplRs').val(),
		interpositionRvwdate=$('#edInterpositionRvwdate').val(),
		dateadmissionApProc=$('#edDateadmissionApProc').val(),
		daterefcollcourt=$('#edDaterefcollcourt').val(),resourcesent=$('#edResourcesent').val(),
		dateadmissionCllCourt=$('#edDateadmissionCllCourt').val(),
		notifdateadmissionCllCourt=$('#edNotifdateadmissionCllCourt').val(),
		adhesiveRvwAplDate=$('#edAdhesiveRvwAplDate').val(),dateshiftpresent=$('#edDateshiftpresent').val(),
		sessiondateprojectRslc=$('#edSessiondateprojectRslc').val(),
		resolutiondate=$('#edResolutiondate').val(),
		clientid=$('#edClientRscList [name=rowline]:checked')[0];
	clientid=(typeof clientid=='undefined')? '0':(clientid.id).replace(/[^0-9]/g,'');
	if(originType==''||originType==null)originType='0';
	if(originTypeId==''||originTypeId==null)originTypeId='0';
	if(resourcetype==''){
		tag='edResourcetype';
		err='err_select_resourcetype';
	}else if(clientid=='0'){
		tag='edClientRsc';
		err='err_select_client';
	}else if(originType==''){
		tag='edOriginType';
		err='err_select_origintype';
	}else if(originTypeId=='0'){
		tag='edOriginTypeId';
		err='err_enter_originprot';
	}else if(recurring==''){
		tag='edRecurring';
		err='err_enter_recurring';
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#putErrorOnEdit').html(i18n(err));
		$('#errorOnEditRsc').show();
		$('.modal').animate({scrollTop:0},'1000');
		return false;
	}
	var param='id='+$('#edId').val()+'&resourcenumber='+resourcenumber
		+'&resourcetype='+resourcetype+'&recurring='+recurring
		+'&originType='+originType+'&originTypeId='+originTypeId
		+'&resolutionAppl='+resolutionAppl+'&datenoticeApplRs='+datenoticeApplRs
		+'&interpositionRvwdate='+interpositionRvwdate
		+'&dateadmissionApProc='+dateadmissionApProc
		+'&daterefcollcourt='+daterefcollcourt+'&resourcesent='+resourcesent
		+'&dateadmissionCllCourt='+dateadmissionCllCourt
		+'&notifdateadmissionCllCourt='+notifdateadmissionCllCourt
		+'&adhesiveRvwAplDate='+adhesiveRvwAplDate+'&dateshiftpresent='+dateshiftpresent
		+'&sessiondateprojectRslc='+sessiondateprojectRslc
		+'&resolutiondate='+resolutiondate + '&clientid='+clientid;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/updateResource",
		data:param,
		async:false,
		success:function(msg){
			if(msg=='msg_data_saved'){
				swal({
					title:i18n('msg_success'),
					text:i18n(msg),
					type:"success",
					timer:3000,
					allowEscapeKey:false
				},function(){
					location.href=location.href.replace(/^(.*)\#.*$/, '$1');
				});
				window.setTimeout(function(){
					location.href=location.href.replace(/^(.*)\#.*$/, '$1');
				},3000);
			}else{
				$('#putErrorOnEditRsc').html(i18n(msg));
				$('#errorOnEditRsc').show();
			}
		},error:function(e){
			$('#putErrorOnEditRsc').html(i18n('err_record_no_saved'));
			$('#errorOnEditRsc').show();
		}
	});
	$('.custombox-modal-open').animate({scrollTop:0},'1000');
};

function deleteResource(id){
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
				url:ctx+"/deleteResource",
				data:"id="+id,
				async:false,
				success : function(data) {
					var f5 = location.href.replace(/^(.*)\#.*$/, '$1');
					swal({
						title : i18n('msg_success'),
						text : i18n('msg_deleted'),
						type : "success",
						timer : 3000,
						allowEscapeKey : false
					}, function() {
						location.href = f5;
					});
					window.setTimeout(function() {
						location.href = f5;
					}, 3000);
				},error:function(resp){
					swal(i18n('msg_warning'),i18n('err_on_delete'),"warning");
				}
			});
		}else{
			swal(i18n('msg_cancelled'),i18n('msg_record_safe'),"warning");
		}
	});
};

$("#addNewResource").click(function(){
	$('#eerrorOnAddRsc, .containTL').hide();
	$('input, select').parent().removeClass('has-error');
//	$('#formAddResource input, #formAddResource select, #formAddResource textarea').val('');
	$("#addNewResource").attr('href','#resource-modal');
	getClientListTab('#clientRscList');
	getTextDDFilterByVal('originTypeId', '');
	try{
		myDropzone = createDropZone("uploadXAddResource", "formAddResource", '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}catch (e){
		clearTemp();
		$('#areaNewRecUpload').html('');
		$('#areaNewRecUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXAddResource" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZone("uploadXAddResource", "formAddResource", '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}
	$("#uploadXAddResource").addClass("dropzone");
});

$("#addNewResourceDash").click(function(){
	$('#errorOnAddRsc,[data-clientfor="resource"],[data-list="clientRsc"]').hide();
	$("#addNewResourceDash").attr('href','#resource-modal');
	var tablelist = '<tr class="rowopt"><td style="display:none;">'
		+'<input type="radio" name="rowline" data-val="" id="cidRsc'
		+$('#protclientid').val()+'" checked></td></tr>';
	$('#clientRscList').append(tablelist);
	$('#clientRsc').val('Actual')
	$('#originType').val($('#protoriginType').val());
	$('#originTypeId').append('<li value="'+$('#referenceid').val()+'" title="Actual" class="selected">Actual</li>');
	$("#addResourceCancel, #resource-modal button.close").click(function(){$('#resource-modal').modal('hide').slideUp();});
	$('#resource-modal').modal('show').slideDown();
	try{
		myDropzone = createDropZone("uploadXAddResource", "formAddResource", '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}catch (e){
		clearTemp();
		$('#areaNewRecUpload').html('');
		$('#areaNewRecUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXAddResource" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZone("uploadXAddResource", "formAddResource", '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}
	$("#uploadXAddResource").addClass("dropzone");
});

$('#originType, #edOriginType').on('change', function(){
	$('#originTypeId,#edOriginTypeId').empty();
	$('[data-list]').val('');
	var elmt = $('[data-list]');
    elmt.addClass('highlight');
    setTimeout(
        function(){elmt.removeClass('highlight');},2000
    );
});

$('#clientRsc, #edClientRsc').on('focus keyup input paste change delete', function(e) {
	filtext(e.target.value, '#' + e.target.id + 'List');
	$('.containTL').show();
});

/* limpia inputs * /
$('#resource-modal, #edit-modal').on('hidden.bs.modal',function(e){
	$(this).find("input,textarea").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked","").end();
});*/

document.addEventListener('click',function(e){
	if (e.target.nodeName == 'TD') {
		var tpe=e.target.parentElement,targetId='',rb;
		var tList=tpe.parentElement.parentElement.id;
		if (tList=='clientRscList' || tList=='edClientRscList')
			targetId=(tList=='clientRscList'?'c':'edC')+'lientRsc';
		if(targetId!=''){
			$(' .rowopt').css('backgroundColor','#fff');
			tpe.style.backgroundColor="ivory";
			rb=$(tpe).find('input:radio[name=rowline]')[0];
			rb.checked=true;
			$('#'+targetId).val($(rb).attr('data-val'));
			$('.containTL').hide();
		}
	}
}, false);

$(window).load(function(){
	var clid=getVarURL('clid');
	if(clid!='')
		history.replaceState({}, document.title, location.pathname+'?language='+getLanguageURL());
});