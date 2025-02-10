;function getClientListTab(targetList,abbr) {
	$('.containTL table').empty();
	abbr=abbr||'cidProt';
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

/*;function getClientListTab(targetList,abbr) {
	$('.containTL table').empty();
	
	abbr=abrr||'cidProt';
	
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
};*/

function getCourtById(id) {
	var info=null;
	if(!isNaN(id-0)){
		$.ajax({
			type : "POST",
			url : ctx + "/getCourtById",
			data:"id=" + id,
			async : false,
			success : function(data) {
				info=data[0].info[0];
			},error : function(e) {
				console.log(i18n('err_unable_get_federalcourt')+'. '+e);
			}
		});
	}
	return info;
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
				$('#' + id).append('<'+ elemOp+ '></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_state')+'. '+e);
		}
	});
};

function getCitiesByState(id, elemtype, filterState) {
	var elemOp = 'option', url = 'getCitiesByState', state = '0';
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
		async : false,
		data : 'estadoid=' + state,
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

function getAllCourtsByCity(id, elemtype, cityid, courttype) {
	if(!(/^[1-9][0-9]*$/.test(cityid)))
		cityid=0;
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type:"POST",
		url:ctx+"/getAllCourtsByCity",
		data:'cityid='+cityid+'&courttype='+courttype,
		async:false,
		success:function(data){
			var trib=data[0]||[],hasdata='';
			if(trib.length>0)
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
			if(trib['unitarios']!=null)
				if(trib.unitarios.length>0){
					for (i = 0; i < trib.unitarios.length; i++)
						$('#' + id).append('<' + elemOp + ' value="' + trib.unitarios[i].tribunalUnitarioid
							+ '" title="'+ trib.unitarios[i].tribunalUnitario + '" data-turnada="unitario">'
							+ trib.unitarios[i].tribunalUnitario + '</' + elemOp+ '>');
					hasdata+='u';
				}
			if(trib['colegiados']!=null)
				if(trib.colegiados.length>0){
					for (i = 0; i < trib.colegiados.length; i++)
						$('#' + id).append('<' + elemOp + ' value="'+ trib.colegiados[i].tribunalcolegiadoid
							+ '" title="'+ trib.colegiados[i].tribunalcolegiado + '" data-turnada="colegiado">'
							+ trib.colegiados[i].tribunalcolegiado + '</' + elemOp+ '>');
					hasdata+='c';
				}
			if(trib['federales']!=null)
				if(trib.federales.length>0){
					for (i = 0; i < trib.federales.length; i++)
						$('#' + id).append('<' + elemOp + ' value="'+ trib.federales[i].juzgadoid
							+ '" title="'+ trib.federales[i].juzgado + '" data-turnada="federal">'
							+ trib.federales[i].juzgado + '</' + elemOp+ '>');
					hasdata+='f';
				}
			if(hasdata=='')
				$('#' + id).append('<'+ elemOp+ '>' + i18n('msg_no_data_city') + '</'+ elemOp + '>');
			$('#' + id).append('<'+ elemOp+ '></'+ elemOp + '>');
		},error:function(e){
			console.log(i18n('err_unable_get_federalcourt') + '. ' + e);
		}
	});
};

function getCitytByIdnType(id, courttype) {
	var info=null;
	$.ajax({
		type:"POST",
		url:ctx+"/getCitytByIdnType",
		data:'id='+id+'&courttype='+courttype,
		async:false,
		success:function(data){
			info=data[0];
		},error:function(e){
			console.log(i18n('err_unable_get_court') + '. ' + e);
		}
	});
	return info;
};

/*function getTrials(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getTrialList",
		async:false,
		success:function(data){
			var info=data[0]||[];
			if (info.length>0) {
				var option=new Option(i18n('msg_select'),'',!1,!0);
				$('#'+id).append(option);
				$(option).attr('disabled','disabled');
				for(i=0;i<info.length;i++){
					option=new Option(info[i].juicio,info[i].juicioid,!0,!1);
					$('#'+id).append(option);
					$(option).attr('title',info[i].juicio);
				}
			}
		},error:function(e){
			console.log(i18n('err_unable_get_trial') + '. ' + e);
		}
	});
};*/

function getRelClientTrial(clid,listid){
	$(listid).find('option').remove().end();
	$.ajax({
		type:"POST",
		data:"clid="+clid,
		url:ctx+"/getRelClientTrial",
		async:false,
		success:function(data){
			var info=data[0]||[];
			if(info.length>0){
				var option=new Option(i18n('msg_select'),'',!1,!0);
				$(listid).append(option);
				$(option).attr('disabled','disabled');
				for(i=0;i<info.length;i++){
					option=new Option(info[i].juicio,info[i].juicioid,!0,!1);
					$(listid).append(option);
					$(option).attr('title',info[i].juicio);
				}
			}else{
				var option=new Option(i18n('msg_no_data_client'),'',!1,!0);
				$(listid).append(option);
			}
		},error:function(e){
			console.log(i18n('err_unable_get_client') + '. ' + e);
		}
	});
};

function getRelClientAppeal(clid,listid){
	$.ajax({
		type:"POST",
		data:"clid="+clid,
		url:ctx+"/getRelClientAppeal",
		async:false,
		success:function(data){
			$(listid).find('option').remove().end();
			var info=data[0]||[];
			if(info.length>0){
				var option=new Option(i18n('msg_select'),'',!1,!0);
				$(listid).append(option);
				$(option).attr('disabled','disabled');
				for(i=0;i<info.length;i++){
					option=new Option(info[i].toca,info[i].apelacionid,!0,!1);
					$(listid).append(option);
					$(option).attr('title',info[i].toca);
				}
			}else{
				var option=new Option(i18n('msg_no_data_client'),'',!1,!0);
				$(listid).append(option);
			}
		},error:function(e){
			console.log(i18n('err_unable_get_client') + '. ' + e);
		}
	});
};

function addProtection(e){
	$('input, select').parent().removeClass('has-error');
	$('#errorOnAddProt, .containTL').hide();
	var err='',tag='',apelacionid=$('#appealList option:selected').val(),
		clid='',custchar=$('input[name=custchar]:checked').val(),
		clientName=$('#protClient').val(), handle=$('#handle').val(),
		origintype=$('input[name=origintype]:checked').val(),
		trialid=$('#selecttrialO option:selected').val(),protection=$('#protection').val(),
		respauth=$('#respauth').val(),claimedact=$('#claimedact').val(),
		demandprotsent=$('#demandprotsent li.selected').val()||'',
		tipodemandaturnadaa=$('#demandprotsent li.selected').attr('data-turnada')||'',
		stay=$('#stay').val(),dateclaimedact=$('#dateclaimedact').val(),dateclaimedactNtn=$('#dateclaimedactNtn').val(),
		filingdatelawsuit=$('#filingdatelawsuit').val(),admissiondate=$('#admissiondate').val(),
		admissionnotifdate=$('#admissionnotifdate').val(),adhesiveaDirPtDate=$('#adhesiveaDirPtDate').val(),
		dateshiftpresent=$('#dateshiftpresent').val(),projectjudgmentdate=$('#projectjudgmentdate').val(),
		judgmentdate=$('#judgmentdate').val(),judgmentnotifdate=$('#judgmentnotifdate').val(),
		reviewresourcedate=$('#reviewresourcedate').val();
	clid=$('#protClientList [name=rowline]:checked')[0];
	clid=(typeof clid=='undefined')? '0':(clid.id).replace(/[^0-9]/g,'');
	if(clid=='0'){
		tag='protClient';
		err='err_select_client';
	}else if(claimedact==''){
		tag='claimedact';
		err='err_enter_claimedact';
	}else if(respauth==''){
		tag='respauth';
		err='err_enter_respauth';
	}
	if(origintype=='Juicio'||origintype==null){
		trialid=(typeof trialid=='undefined')?'':trialid;
		if(trialid==''||origintype==null){
			tag='selecttrialO';
			err='err_select_origintrial';
			if(origintype==null){
				err='err_confirm_origintrial_appeal';
				$('#origintype1').prop('checked',true);
				$('#selecttrialO').val('')
				$('#divappeal').hide();
				$('#divtrialOrigin').show();
			}
		}
	}else if(origintype=='Apelación'){
		apelacionid=(typeof apelacionid=='undefined')?'':apelacionid;
		if(apelacionid==''){
			tag='appealList';
			err='err_select_appealO';
		}
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#puterrorOnAddProt').html(i18n(err));
		$('#errorOnAddProt').show();
		$('#protection-modal, .custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='clientid='+clid+'&custchar='+custchar+'&clientName='+clientName
		+'&origintype='+origintype+'&trialid='+trialid+'&apelacionid='+apelacionid
		+'&protection='+protection+'&respauth='+respauth+'&claimedact='+claimedact
		+'&demandprotsent='+demandprotsent+'&tipodemandaturnadaa='+tipodemandaturnadaa
		+'&stay='+stay+'&dateclaimedact='+dateclaimedact
		+'&dateclaimedactNtn='+dateclaimedactNtn+'&filingdatelawsuit='+filingdatelawsuit
		+'&admissiondate='+admissiondate+'&admissionnotifdate='+admissionnotifdate
		+'&adhesiveaDirPtDate='+adhesiveaDirPtDate+'&dateshiftpresent='+dateshiftpresent
		+'&projectjudgmentdate='+projectjudgmentdate+'&judgmentdate='+judgmentdate
		+'&judgmentnotifdate='+judgmentnotifdate+'&reviewresourcedate='+reviewresourcedate;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/addNewProtection",
		data:param,
		async:false,
		success:function(data){
			if(data=="msg_data_saved"){
				swal(i18n("msg_success"),i18n(data),"success");
				location.href=location.href.replace(/^(.*)\#.*$/, '$1');
			}else{
				$('#puterrorOnAddProt').html(i18n(data));
				$('#errorOnAddProt').show();
				return null;
			}
			$('#protection-modal').modal('toggle');
		},error:function(e){
			$('#puterrorOnAddProt').html(i18n('err_record_no_saved'));
			$('#errorOnAddProt').show();
		}
	});
};

function getAppealsTab(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getAppealList",
		async:false,
		success:function(data){
			var info=data[0]||[];
			if(info.length>0){
				var tablelist='<tr><th style="display:none;"></th>'
					+'<th>'+i18n('msg_appeal')+'</th></tr>';
				for(i=0;i<info.length;i++){
					tablelist+='<tr class="rowopt"><td style="display:none;"><input type="radio" name="rowline" data-val="'
						+info[i].toca+'" id="appeal'+info[i].apelacionid+'"></td>'
						+'<td>'+info[i].toca+'</td>';
				}
				$('#'+id+'List').append(tablelist);
			}
		},error:function(e){
			console.log("Error al obtener Apelaciones. "+e);
		}
	});
};

function getAppeals(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getAppealList",
		async:false,
		success:function(data){
			var info=data[0]||[];
			if(info.length>0){
				var option=new Option(i18n('msg_select'),'',!1,!0);
				$('#'+id).append(option);
				$(option).attr('disabled','disabled');
				for(i=0;i<info.length;i++){
					option=new Option(info[i].toca,info[i].apelacionid,!0,!1);
					$('#'+id).append(option);
					$(option).attr('title',info[i].toca);
				}
			}
		},error:function(e){
			console.log("Error al obtener Apelaciones. "+e);
		}
	});
};

function getAppealsDash(id,trial){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getAppealListDash",
		data:'trial='+trial,
		async:false,
		success:function(data){
			var info=data[0]||[];
			if(info.length>0){
				var option=new Option(i18n('msg_select'),'',!1,!0);
				$('#'+id).append(option);
				$(option).attr('disabled','disabled');
				for(i=0;i<info.length;i++){
					option=new Option(info[i].toca,info[i].apelacionid,!0,!1);
					$('#'+id).append(option);
					$(option).attr('title',info[i].toca);
				}
			}
		},error:function(e){
			console.log("Error al obtener Apelaciones. "+e);
		}
	});
};

function getDetailsToEdit(id){
	$('#errorOnEditProt, .containTL, #edDivAppealOrigin, #edDivTrialOrigin').hide();
	$('input, select').parent().removeClass('has-error');
	$.ajax({
		type:'POST',
		data:"id="+id,
		url:ctx+"/getDetailsByProt",
		data:'id='+id,
		async:false,
		success:function(data){
			var info=data[0].detail[0]||[];
			if(info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_protections'),"error");
				return;
			}
			$('#editProtection-modal select, #editProtection-modal input[type="text"]')
				.not('input[type="hidden"]').val('');
			$('*[id*=date]').val('');
			$('#edid').val(info.amparoid);
			var clid=data[0].client,cte='',custchar='';
			getClientListTab('#edProtClientList','cidProt');
			$('#edProtClient').val(data[0].clientName);
			$('#edProtClientList #cidProt' + clid).prop('checked',true);
			$('#cidProt' + clid).parent().parent().css('backgroundColor',"ivory");

			getRelClientTrial(clid,'#edSelecttrialO');
			getRelClientAppeal(clid,'#edAppealList');
			if(info.juicioid!=null){
				$("#edOrigintype1").prop("checked",true);
				$('#edSelecttrialO').val(info.juicioid);
				$('#edDivTrialOrigin').show();
			}else if(info.apelacionid!=null){
				$("#edOrigintype2").prop("checked",true);
				$('#edAppealList').val(info.apelacionid);
				$('#edDivAppealOrigin').show();
			}
			custchar=info.quejoso==data[0].clientName?'1':info.tercero==data[0].clientName?'2':'';
			if(custchar!='')
				document.getElementById('edCustchar'+custchar).checked=true;
			$('#edProtection').val(info.amparo);
			$('#edAppealList').val(info.apelacionid);
			$('#edRespauth').val(info.autoridadresponsable);
			$('#edClaimedact').val(info.actoreclamado);
			$('#edStay').val(info.suspension);

			setdp('#edDateclaimedact',info.fechaactoreclamado);
			setdp('#edDateclaimedactNtn',info.fechanotificacionactoreclamado);
			setdp('#edFilingdatelawsuit',info.fechapresentaciondemanda);
			setdp('#edAdmissiondate',info.fechadmision);
			setdp('#edAdmissionnotifdate',info.fechanotificaciondmision);
			setdp('#edAdhesiveaDirPtDate',info.fechaamparodirectoadhesivo);
			setdp('#edDateshiftpresent',info.fechaturnoaponencia);
			setdp('#edProjectjudgmentdate',info.fechasesionproyectosentencia);
			setdp('#edJudgmentdate',info.fechasentencia);
			setdp('#edJudgmentnotifdate',info.fechanotificacionsentencia);
			setdp('#edReviewresourcedate',info.fecharecursorevision);
			
			getEstados('edStateProt','ul');
			var tipoturnada=info.tipodemandaturnadaa||'', demandaturnada=info.demandaamparoturnadaa||'';
			if(tipoturnada!=''&&demandaturnada!=''){
				var reg=getCitytByIdnType(demandaturnada,tipoturnada);
				if(reg==null)return;
				var cityinfo=getCourtById(reg[0].estadoid);
				getTextDDFilterByVal('edStateProt', reg[0].estadoid);
				getCitiesByState('edCityProt','ul',reg[0].estadoid);
				getTextDDFilterByVal('edCityProt', reg[0].ciudadid);
				getAllCourtsByCity('edDemandprotsent', 'ul', reg[0].ciudadid, 'unitarios,colegiados');
				$('#edDemandprotsent [data-turnada=' + tipoturnada
					+ '] [value="' + demandaturnada + '"]').addClass('selected');
				$('#edDemandprotsent [data-turnada="'+tipoturnada
					+ '"][value="'+demandaturnada+'"]').addClass('selected');
				$('[data-edlist="edDemandprotsent"]').val($('#edDemandprotsent li.selected').text());
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_protections'),"error");
		}
	});
	try{
		createDropZone('uploadXEditProt','formEditProt',id,3);
	}catch (e){
		clearTemp();
		$('#areaProtUpload').html('');
		$('#areaProtUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXEditProt" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZone('uploadXEditProt','formEditProt',id,3);
	}
	$("#uploadXEditProt").addClass("dropzone");
};

function updateData(){
	var err='',tag='',apelacionid=$('#edAppealList option:selected').val(),
		amparoid=$('#edid').val(),protection=$('#edProtection').html(),clientid='',
		protection=$('#edProtection').val();
		custchar=$('input[name=edCustchar]:checked').val(),
		clientName=$('#edProtClient').val(),origintype=$('input[name=edOrigintype]:checked').val(),
		trialid=$('#edSelecttrialO option:selected').val(),respauth=$('#edRespauth').val(),
		claimedact=$('#edClaimedact').val(),demandprotsent=$('#edDemandprotsent li.selected').val()||'',
		tipodemandaturnadaa=$('#edDemandprotsent li.selected').attr('data-turnada')||'',
		stay=$('#edStay').val(),dateclaimedact=$('#edDateclaimedact').val()
		dateclaimedactNtn=$('#edDateclaimedactNtn').val(),
		filingdatelawsuit=$('#edFilingdatelawsuit').val(),admissiondate=$('#edAdmissiondate').val(),
		admissionnotifdate=$('#edAdmissionnotifdate').val(),adhesiveaDirPtDate=$('#edAdhesiveaDirPtDate').val(),
		dateshiftpresent=$('#edDateshiftpresent').val(),projectjudgmentdate=$('#edProjectjudgmentdate').val(),
		judgmentdate=$('#edJudgmentdate').val(),judgmentnotifdate=$('#edJudgmentnotifdate').val(),
		reviewresourcedate=$('#edReviewresourcedate').val();
	clientid=$('#edProtClientList [name=rowline]:checked')[0];
	clientid=(typeof clientid=='undefined')? '0':(clientid.id).replace(/[^0-9]/g,'');
	if(clientid=='0'){
		tag='edProtClient';
		err='err_select_client';
	}else if(protection==''){
		err='err_unable_save_orby_session';
	}else if(claimedact==''){
		tag='edClaimedact';
		err='err_enter_claimedact';
	}else if(respauth==''){
		tag='edRespauth';
		err='err_enter_respauth';
	}
	if(origintype=='Juicio'){
		trialid=(typeof trialid=='undefined')?'':trialid;
		if(trialid==''){
			tag='edSelecttrialO';
			err=i18n('err_select_origintrial');
		}
	}else if(origintype=='Apelación'){
		apelacionid=(typeof apelacionid=='undefined')?'':apelacionid;
		if(apelacionid==''){
			tag='edAppealList';
			err='err_select_appealO';
		}
	}
	if(err!=''){
		$('#' + tag).parent().addClass('has-error');
		$('#puterrorOnEditProt').html(i18n(err));
		$('#errorOnEditProt').show();
		$('.modal').animate({scrollTop:0},'1000');
		return false;
	}
	var param='amparoid='+amparoid+'&apelacionid='+apelacionid
		+'&protection='+protection+'&clientid='+clientid+'&custchar='+custchar
		+'&clientName='+clientName+'&origintype='+origintype+'&trialid='+trialid
		+'&respauth='+respauth+'&claimedact='+claimedact
		+'&demandprotsent='+demandprotsent+'&tipodemandaturnadaa='+tipodemandaturnadaa
		+'&stay='+stay+'&dateclaimedact='+dateclaimedact
		+'&dateclaimedactNtn='+dateclaimedactNtn+'&filingdatelawsuit='+filingdatelawsuit
		+'&admissiondate='+admissiondate+'&admissionnotifdate='+admissionnotifdate
		+'&adhesiveaDirPtDate='+adhesiveaDirPtDate+'&dateshiftpresent='+dateshiftpresent
		+'&projectjudgmentdate='+projectjudgmentdate+'&judgmentdate='+judgmentdate
		+'&judgmentnotifdate='+judgmentnotifdate+'&reviewresourcedate='+reviewresourcedate;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/updateProtection",
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
					location.href=location.href.replace(/^(.*)\#.*$/, '$1');
				});
				window.setTimeout(function(){
					location.href=location.href.replace(/^(.*)\#.*$/, '$1');
				},3000);
			}else{
				$('#putErrorOnEditProt').html(i18n('err_record_no_saved'));
				$('#errorOnEditProt').show();
			}
		},error:function(e){
			$('#putErrorOnEditProt').html(i18n('err_record_no_saved'));
			$('#errorOnEditProt').show();
		}
	});
};

function deleteProtection(id){
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

$("#addNewProtection").click(function(){
	$('#errorOnAddProt, #divappeal, .containTL').hide();
	$('input, select').parent().removeClass('has-error');
	$('#divtrialOrigin').show();
	$("#addNewProtection").attr('href','#protection-modal');
	getClientListTab('#protClientList','cidProt');
	getEstados('protState','ul');
	try{
		myDropzone = createDropZone("uploadXProt", "formAddProt", '', '');
		myDropzone.on('sending', function(file, xhr, formData){/*...*/});
	}catch (e){
		clearTemp();
		$('#areaNewProtUpload').html('');
		$('#areaNewProtUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXProt" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZone("uploadXProt", "formAddProt", '', '');
		myDropzone.on('sending', function(file, xhr, formData){/*...*/});
	}
	$("#uploadXProt").addClass("dropzone");
});

$("input[type=radio][name=origintype]").click(function(){
	var origintype=$("input[name=origintype]:checked").val()
	if(origintype=='Juicio'){
		$('#divappeal').hide();
		$('#divtrialOrigin').fadeIn('fast');
	}else{
		$('#divtrialOrigin').hide();
		$('#divappeal').fadeIn('fast');
	}
});

$("input[type=radio][name=edOrigintype]").click(function(){
	var origintype=$("input[name=edOrigintype]:checked").val()
	if(origintype=='Juicio'){
		$('#edDivAppealOrigin').hide();
		$('#edDivTrialOrigin').fadeIn('fast');
	}else{
		$('#edDivTrialOrigin').hide();
		$('#edDivAppealOrigin').fadeIn('fast');
	}
});

$('#protClient').on('click keyup input paste change delete', function(e) {
	filtext(e.target.value, '#protClientList');
	$('.containTL').show();
});

$('#edProtClient').on('click keyup input paste change delete', function(e) {
	filtext(e.target.value, '#edProtClientList');
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

$('#protection-modal, #editProtection-modal').on('hidden.bs.modal',function(e){
	$(this).find("input,textarea").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked","").end();
});

document.addEventListener('click',function(e){
	if (e.target.nodeName == 'TD') {
		var tpe=e.target.parentElement,targetId='',rb;
		var tList=tpe.parentElement.parentElement.id;
		if (tList=='protClientList' || tList=='edProtClientList')
			targetId=(tList=='protClientList'?'prot':'edProt')+'Client';
/*		else if(tList=='trialAplList' || tList=='edTrialAplList')
			targetId=tList=='trialAplList'?'trialApl':'edTrialApl';*/
		if(targetId!=''){
			$(' .rowopt').css('backgroundColor','#fff');
			tpe.style.backgroundColor="ivory";
			rb=$(tpe).find('input:radio[name=rowline]')[0];
			rb.checked=true;
			$('#'+targetId).val($(rb).attr('data-val'));
			$('.containTL').hide();
			if(targetId=='protClient'||targetId=='edProtClient'){
				var origin1='#selecttrialO', origin2='#appealList',showEmpty='#divAppealOrigin',
					clid=$('#protClientList [name=rowline]:checked')[0];
				$('#divTrialOrigin, #divAppealOrigin').hide();
				if($('input[name=origintype]:checked').val()=='Juicio')
					showEmpty='#divTrialOrigin';
				if(targetId=='edProtClient'){
					origin1='#edSelecttrialO', origin2='#edAppealList',showEmpty='#edDivAppealOrigin',
					clid=$('#edProtClientList [name=rowline]:checked')[0];
					if($('input[name=edOrigintype]:checked').val()=='Juicio')
						showEmpty='#edDivTrialOrigin';
				}
				clid=(typeof clid=='undefined')?'0':(clid.id).replace(/[^0-9]/g,'');
				$(origin1+', '+origin2).empty();
				if(clid>0){
					getRelClientTrial(clid,origin1);
					getRelClientAppeal(clid,origin2);
				}
				$(origin1+', '+origin2).parent().addClass('has-error');
				$(showEmpty).show();
			}
		}
	}
}, false);

Dropzone.options.uploadXProt={
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

Dropzone.options.uploadXEditProt={
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

$(window).load(function(){
	var clid=getVarURL('clid');
	if(clid!=''){
		history.replaceState({}, document.title, location.pathname+'?language='+getLanguageURL());
	}
});