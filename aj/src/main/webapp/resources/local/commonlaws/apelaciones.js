;function getClientListTab(targetList,abbr) {
	$('.containTL table').empty();
	abbr=abbr||'cidApl';
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

/*;function getClientListTab(id) {
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
					+ info[i][1] + '" id="cidApl' + info[i][0] + '"></td>'
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

function getCitiesByState(id, elemtype, filterState) {
	var elemOp = 'option', url = 'getCitiesByState', state = '0';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	if (isNaN(filterState - 0)) {
		state = $('#' + filterState + ' li.selected').val();
		//url = 'getCitiesByState';
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
				$('#' + id).append('<'+ elemOp+ '></' + elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_city') + '. ' + e);
		}
	});
};

function getStates(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getStates",
		async : false,
		success : function(data) {
			var info = data[0];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].estadoid + '" title="'+ info[i].estado + '">'
						+ info[i].estado + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ '></'+ elemOp + '>');
			}
		},
		error : function(e) {
			console.log(i18n('err_unable_get_state')+'. '+e);
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
			var info = data[0];
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

function getRooms(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getRooms",
		async : false,
		success : function(data) {
			var info = data[0];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].salaid + '" title="'+ info[i].sala + '">'
						+ info[i].sala + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ '></'+ elemOp + '>');
			}
		},
		error : function(e) {
			console.log(i18n('err_unable_get_room')+'. '+e);
		}
	});
};

function getTrial(id,iddestino,ftype){
	ftype=ftype.toLowerCase();
	$.ajax({
		type:"POST",
		url:ctx+"/getTrial",
		data:"trial="+id,
		async:false,
		success:function(data){
			var info=data[0];
			if(info.length>0){
				if(ftype.indexOf('val')>=0)
					$('#'+iddestino).val(info[0].juicio);
				else
					$('#'+iddestino).html(info[0].juicio);
			}
		},error:function(e){
			console.log(i18n('err_unable_get_trial')+'. '+e);
		}
	});
};

function addAppeal(e){
	$('#errorOnAddApl, .containTL').hide();
	var err='',tag='',trial=$('#trialApl li.selected').val()||'0',
		clienttype=$('input[name=custtype]:checked').val(),
		clientName=$('#client').val(), handle=$('#handle').val(), 
		room=$('#roomApl li.selected').val()||'', matter=$('#matterApl li.selected').val()||'',
		city=$('#aplCity li.selected').val()||'', speaker=$('#speaker').val(),
		resolution=$('#resolution').val(), apladhesiva=$('#apladhesiva option:selected').val()||'-1',
		clid=$('#clientList [name=rowline]:checked')[0];
	clid=(typeof clid=='undefined')? '0':(clid.id).replace(/[^0-9]/g,'');
	try{
		var rb=$('#trialAplList [name=rowline]:checked');
		if(rb.length>0)
			trial=rb[0].id;
	}catch(e){}
	if(clid=='0'){
		tag='client';
		err='err_select_client';
	}else if(trial==''||trial=='0'){
		tag='trialApl';
		err='err_enter_trial';
	}else if(room==''||room==null){
		tag='roomApl';
		err='err_select_room';
	}else if(matter==''||matter==null){
		tag='matterApl';
		err='err_select_matter';
	}else if(city==''||city==null){
		tag='aplCity';
		err='err_select_city';
	}else if(speaker==''){
		tag='speaker';
		err='err_enter_speaker';
	}else if(apladhesiva=='-1'||apladhesiva==''){
		tag='apladhesiva';
		err='err_enter_apadhesive';
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#putErrorOnAddApl').html(i18n(err));
		$('#errorOnAddApl').show();
		$('#appeal-modal, .custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='trial='+trial+'&clienttype='+clienttype+'&clientName='+clientName
		+'&handle='+handle+'&room='+room+'&matter='+matter+'&city='+city
		+'&speaker='+speaker+'&resolution='+resolution+'&apladhesiva='+apladhesiva;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/addNewAppeal",
		data:param,
		async:false,
		success:function(data){
			if(data=="msg_data_saved"){
				swal(i18n("msg_success"),i18n(data),"success");
				location.href=location.href.replace(/^(.*)\#.*$/, '$1');
			}else{
				$('#putErrorOnAddApl').html(i18n(data));
				$('#errorOnAddApl').show();
			}
		},error:function(e){
			$('#putErrorOnAddApl').html(i18n('err_record_no_saved'));
			$('#errorOnAddApl').show();
		}
	});
	$('.custombox-modal-open').animate({scrollTop:0},'1000');
};

function getDataDetails(id){
	$('#errorOnEditApl, .containTL').hide();
	$('input, select').parent().removeClass('has-error');
	$.ajax({
		type:'POST',
		url:ctx+"/getDetailsAppeal",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].detail[0];
			if(info.length==0){
				swal(i18n('msg_warning'), i18n('err_unable_get_trails'),"error");
				return;
			}
			$('#edit-modal select, #edit-modal input[type="text"]')
				.not('input[type="hidden"]').val('');
			$('*[id*=date]').val('');
			getClientListTab('#editClientList');
			var clid=data[0].ccid[0].clientid;
			$('#editClientList #cidApl' + clid).prop('checked',true);
			$('#editclient').val(data[0].clientName);
			$('#cidApl' + clid).parent().parent().css('backgroundColor',"ivory");
			getRooms('edRoomApl','ul');
			getMaterias('edMatterApl','ul');
			getStates('edAplState','ul');
			var cte='';
			if(info.apelado){
				document.getElementById('edCusttype1').checked=true;
				cte=info.apelado;
			}else if(info.apelante){
				document.getElementById('edCusttype2').checked=true;
				cte=info.apelante;
			}
			var cid=data[0].ccid[0].clientid,stid=data[0].stateid||0;
			$('#edClient').val(cid);
			getTrials('edTrialApl',cid, 'ul');
			getTextDDFilterByVal('edTrialApl', info.juicioid);
			$('#edhandle').val(info.toca);
			$('#edAppealId').val(info.apelacionid);
			getTextDDFilterByVal('edRoomApl', info.salaid);
			getTextDDFilterByVal('edMatterApl', info.materiaid);
			if(stid>0)
				getTextDDFilterByVal('edAplState', stid);
			getCitiesByState('edAplCity','ul','edAplState');
			getTextDDFilterByVal('edAplCity', info.ciudadid);
			$('#edSpeaker').val(info.ponente);
			$('#edResolution').val(info.resolucion);
			$('#edApladhesiva').val(info.apelacionadhesiva);
		},error:function(resp){
			swal(i18n('msg_warning'), i18n('err_unable_get_trial'),"error");
		}
	});
	try{
		createDropZone('uploadXEditAppeal','formEditAppeal',id,2);
	}catch (e){
		clearTemp();
		$('#areaEditAppealUpload').html('');
		$('#areaEditAppealUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXEditAppeal" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZone('uploadXEditAppeal','formEditAppeal',id,2);
	}
	$("#uploadXEditAppeal").addClass("dropzone");
};

function updateData(){
	$('input').parent().removeClass('has-error');
	var err='',tag='',trial=$('#edTrialApl li.selected').val()||'0',handle=$('#edhandle').val(),
	clienttype=$('input[name=edCusttype]:checked').val(),clientName=$('#editclient').val(),
	room=$('#edRoomApl li.selected').val()||'',matter=$('#edMatterApl li.selected').val()||'',
	city=$('#edAplCity li.selected').val()||'',speaker=$('#edSpeaker').val(),
	resolution=$('#edResolution').val(),apladhesiva=$('#edApladhesiva option:selected').val();
	if(trial==''||trial=='0'){
		tag='edTrialApl';
		err='err_select_trial';
	}else if(room==''||room==null){
		tag='edRoomApl';
		err='err_select_room';
	}else if(matter==''||matter==null){
		tag='edMatterApl';
		err='err_select_matter';
	}else if(city==''||city==null){
		tag='edCity';
		err='err_select_city';
	}else if(speaker==''){
		tag='edSpeaker';
		err='err_enter_speaker';
	}else if(apladhesiva=='-1'){
		tag='edApladhesiva';
		err='err_enter_apadhesive';
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#putErrorOnEditApl').html(i18n(err));
		$('#errorOnEditApl').show();
		$('.modal').animate({scrollTop:0},'1000');
		return false;
	}
	var param='trial='+trial+'&handle='+handle+'&room='+room+'&matter='+matter+'&city='+city
		+'&clienttype='+clienttype+'&clientName='+clientName+'&speaker='+speaker
		+'&resolution='+resolution+'&apladhesiva='+apladhesiva+'&edid='+$('#edAppealId').val();
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/updateAppeal",
		data:param,
		async:false,
		success:function(data){
			if(data=="true"){
				swal({
					title:i18n('msg_success'),
					text:i18n('msg_data_saved'),
					type:"success",
					timer:3000,
					allowEscapeKey:false
				},function(){
					location.href=location.href.replace(/^(.*)\#.*$/,'$1');
				});
				window.setTimeout(function(){ 
					location.href=location.href.replace(/^(.*)\#.*$/,'$1');
				},3000);
			}else{
				$('#putErrorOnEditApl').html(i18n(data));
				$('#errorOnEditApl').show();
			}
		},error:function(e){
			$('#putErrorOnEditApl').html(i18n('err_record_no_saved'));
			$('#errorOnEditApl').show();
		}
	});
	$('.custombox-modal-open').animate({scrollTop:0},'1000');
};

/*$('#appeal-modal, #edit-modal').on('hidden.bs.modal',function (e){
	$(this).find("input,textarea").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked","").end();
});*/

function deleteAppeal(id){
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
				url:ctx+"/deleteAppeal",
				data:param,
				async:false,
				success:function(data){
					swal({
						title:i18n('msg_deleted'),
						text:i18n('msg_record_deleted'),
						type:"success",
						timer:3000,
						allowEscapeKey:false
					},function(){
						location.href=location.href.replace(/^(.*)\#.*$/,'$1');
					});
				},error:function(resp){
					swal(i18n('msg_warning'), i18n('err_on_delete'),"warning");
				}
			});
		}else{
			swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "warning");
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
		},error : function(e) {
			console.log(i18n('err_unable_get_trial') + '. ' + e);
		}
	});
};

$("#addNewAppeal").click(function(){
	$('#errorOnAddApl, .containTL').hide();
	$('input, select').parent().removeClass('has-error');
	$("#addNewAppeal").attr('href','#appeal-modal');
	getClientListTab('#clientList');
	getRooms('roomApl','ul');
	getMaterias('matterApl','ul');
	getStates('aplState','ul');
	try{
		createDropZone('uploadXAppeal','formAddAppeal','','');//id 2
	}catch (e){
		clearTemp();
		$('#areaAppealUpload').html('');
		$('#areaAppealUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXAppeal" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZone('uploadXAppeal','formAddAppeal','','');
	}
	$("#uploadXAppeal").addClass("dropzone");
});

$('#appeal').on('keyup input paste change', function(e){
	var id=e.target.id, input, filter, table, tr, i;
	input=e.target;
	filter=input.value.toUpperCase();
	table=document.getElementById(id+'List');
	tr=$('#'+id+'List .rowopt');
	$('.containTL').removeClass('dnone');
	for(i=1;i<tr.length;i++){
		if(tr[i].innerText.toUpperCase().indexOf(filter) > -1){
			tr[i].style.display="";
		}else{
			tr[i].style.display="none";
		}
	}
});

$('#client').on('click keyup input paste change delete', function(e) {
	filtext(e.target.value, '#clientList');
	$('.containTL').show();
});

$('#editclient').on('click keyup input paste change delete', function(e) {
	filtext(e.target.value, '#editClientList');
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

$('#trialAplList').on('click keyup input paste change delete', function(){
	var clid=$('#clientList [name=rowline]:checked')[0];
	clid=(typeof clid=='undefined')? '0':(clid.id).replace(/[^0-9]/g,'');
	if(clid==0){
		$('#client').parent().addClass('has-error');
		$('#putErrorOnAddApl').html(i18n('msg_select_clientfirst'));
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		$('#errorOnAddApl').show();
		return;
	}
	getTrials('trialApl',clid,'ul');
	optImgListIcon(this);
});

$('#edTrialAplList').on('click keyup input paste change delete', function(){
	var clid=$('#editClientList [name=rowline]:checked')[0];
	clid=(typeof clid=='undefined')? '0':(clid.id).replace(/[^0-9]/g,'');
	if(clid==0){
		$('#editclient').parent().addClass('has-error');
		$('#putErrorOnEditApl').html(i18n('msg_select_clientfirst'));
		$('#errorOnEditApl').show();
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return;
	}
	getTrials('edTrialApl',clid,'ul');
	optImgListIcon(this);
});

$('#trialApl, #edTrialApl').on('keyup input paste change', function(e){
	var id=e.target.id, input, filter, table, tr, i;
	input=e.target;
	filter=input.value.toUpperCase();
	table=document.getElementById(id+'List');
	tr=$('#'+id+'List .rowopt');
	$('.containTL').show();
	for(i=1;i<tr.length;i++){
		if(tr[i].innerText.toUpperCase().indexOf(filter) > -1){
			tr[i].style.display="";
		}else{
			tr[i].style.display="none";
		}
	}
});

Dropzone.options.uploadXAppeal={
	init:function(){
		this.on('addedfile', function(file){
			var preview = document.getElementsByClassName('dz-preview'),
				imageName = document.createElement('span');
			preview = preview[preview.length - 1];
			imageName.innerHTML = file.name;
			preview.insertBefore(imageName, preview.firstChild);
		});
	}
};

Dropzone.options.uploadXEditAppeal={
	init:function(){
		this.on('addedfile', function(file){
			var preview = document.getElementsByClassName('dz-preview'),
				imageName = document.createElement('span');
			preview = preview[preview.length - 1];
			imageName.innerHTML = file.name;
			preview.insertBefore(imageName, preview.firstChild);
		});
	}
};

document.addEventListener('click',function(e){
	if (e.target.nodeName == 'TD') {
		var tpe=e.target.parentElement,targetId='',rb;
		var tList=tpe.parentElement.parentElement.id;
		if (tList=='clientList' || tList=='editClientList')
			targetId=(tList=='clientList'?'':'edit')+'client';
		else if(tList=='trialAplList' || tList=='edTrialAplList')
			targetId=tList=='trialAplList'?'trialApl':'edTrialApl';
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