;function getClientListTab(targetList,abbr) {
	$('.containTL table').empty();
	abbr=abbr||'cidIndProt';
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
					+ info[i][1] + '" id="cidIndProt' + info[i][0] + '"></td>'
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

function getCourtById(id) {
	var info=null;
	if(!isNaN(id-0)){
		$.ajax({
			type : "POST",
			url : ctx + "/getCourtById",
			data:"id=" + id,
			async : false,
			success : function(data) {
				info=data[0][0];
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
			console.log(i18n('err_unable_get_matter')+'. '+e);
		}
	});
};

function getCitiesByState(id, elemtype, filterState){
	var elemOp='option',url='getCitiesByState',state='0';
	if(elemtype=='ul'||elemtype=='ol')
		elemOp='li';
	if(!/^[1-9][0-9]*$/.test(filterState)){
		state=$('#'+filterState+' li.selected').val();
		state=state || 0;
		url='getCitiesByState';
	}
	$('#'+id).empty();
	$.ajax({
		type:"POST",
		url:ctx+"/"+url,
		async:false,
		data:'estadoid='+state,
		success:function(data){
			var info=data[0]||[];
			if(info.length>0){
				if(elemtype=='select' || elemtype=='')
					$('#'+id).append('<'+elemOp + ' value="0" selected disabled>' + i18n('msg_select') + '</'+elemOp+'>');
				for(i=0;i<info.length;i++)
					$('#'+id).append('<'+elemOp + ' value="'+info[i].ciudadid + '" title="'+info[i].ciudad+'">'
						+info[i].ciudad + '</'+elemOp+'>');
				$('#'+id).append('<'+ elemOp+ '></'+elemOp+'>');
			}
		},error:function(e){
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
			info=data[0]||[];
		},error:function(e){
			console.log(i18n('err_unable_get_court') + '. ' + e);
		}
	});
	return info;
};

function getTrials(id){
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
};

/** Carga el listado de juicios del cliente.
@param	clid	Id del cliente.
@param	listid	Elemento 'select' donde se cargará el listado.<br>
				Deberá incluir los prefijos jquery, ejemplo "#", ".", etc */
function getRelClientTrial(clid,listid){
	$.ajax({
		type:"POST",
		data:"clid="+clid,
		url:ctx+"/getRelClientTrial",
		async:false,
		success:function(data){
			$(listid).find('option').remove().end();
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

/** Carga el listado de apelaciones del cliente.
@param	clid	Id del cliente.
@param	listid	Elemento 'select' donde se cargará el listado.<br>
				Deberá incluir los prefijos jquery, ejemplo "#", ".", etc */
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
	$('#errorOnEditIndProt, .containTL').hide();
	$('input, select').parent().removeClass('has-error');
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

function getDetailsToEdit(id){
	$('#errorOnEditIndProt, .containTL').hide();
	$('input, select').parent().removeClass('has-error');
	$.ajax({
		type:'POST',
		url:ctx+"/getDetailsByIndProt",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].detail[0]||[];
			if(info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_protections'),"error");
				return;
			}
			$('#edit-indprotection-modal select, #edit-indprotection-modal input[type="text"]')
				.not('input[type="hidden"]').val('');
			$('*[id*=date]').val('');
			$('#edIndId').val(info.amparoid);
			var clid=data[0].cid,cte='',custchar='';
			getClientListTab('#edIndProtClientList');
			$('#edIndProtClient').val(data[0].clientName);
			$('#edIndProtClientList #cidIndProt' + clid).prop('checked',true);
			$('#cidIndProt' + clid).parent().parent().css('backgroundColor',"ivory");
			
			var trialid=info.juicioid||0,appealid=info.apelacionid||0,
				cte=info.quejoso,custchar='edIndCustQ3_q';
			if(info.tercero==data[0].clientname)
				custchar='edIndCustQ3_3';
			document.getElementById(custchar).checked=true;
			if(custchar=='edIndCustQ3_q'){
				$('#edOpCustQ3_3').show();
				$('#edOpCustQ3_q').hide();
			}else{
				$('#edOpCustQ3_q').show();
				$('#edOpCustQ3_3').hide();
			}
			getRelClientTrial(clid,'#edIndSelecttrialO');
			getRelClientAppeal(clid,'#edIndAppealList');
			if(trialid>0){
				document.getElementById("edIndOrigintype").value="1";
				$('#divEdIndTrialOrigin').removeClass('dnone');
				$('#divEdIndAppealOrigin').addClass('dnone');
				$('#edIndSelecttrialO').val(info.juicioid);
				$('#edIndAppealList').val('0');
			}else if(appealid>0){
				document.getElementById("edIndOrigintype").value="2";
				$('#divEdIndTrialOrigin').addClass('dnone');
				$('#divEdIndAppealOrigin').removeClass('dnone');
				$('#edIndAppealList').val(info.apelacionid);
				$('#edIndSelecttrialO').val('0');
			}else{
				$('#divEdIndAppealOrigin, #divEdIndTrialOrigin').addClass('dnone');
				document.getElementById("edIndOrigintype").value="0";
			}
			$('#edIndProtection').val(info.amparo);
			$('#edIndComplaining').val(info.quejoso);
			$('#edIndTrdparty').val(info.tercero);
			$('#edIndClaimedact').val(info.actoreclamado);
			$('#edIndRespauth').val(info.autoridadresponsable);
			$('#edIndJudgment').val(info.sentencia);
			$('#edIndRsrcreviewjudgment').val(info.recursorevisioncontrasentencia);
			$('#edIndProvsusp').val(info.suspensionprovisional);
			$('#edIndAdjournmentjudgment').val(info.sentenciadefinitiva);

			setdp('#edIndDateclaimedactNtn',info.fechanotificacionactoreclamado);
			setdp('#edIndFilingdatelawsuit',info.fechapresentaciondemanda);
			setdp('#edIndAutoadmissiondate',info.fechadmision);
			setdp('#edIndAdmissionnotifdate',info.fechanotificaciondmision);
			setdp('#edIndConsthearingdate',info.fechaaudicienciaconstitucional);
			setdp('#edIndIncidentalhearingdate',info.fechaaudienciaincidental);

			getEstados('edStateIndProt','ul');
			var pec=data[0].pec==null?[]:data[0].pec[0];
			/*if(pec.length>0){
				var cityinfo=getCourtById(pec[2]);
				getTextDDFilterByVal('edStateIndProt',pec[1]);
				getCitiesByState('edCityIndProt','ul',pec[1]);
				getTextDDFilterByVal('edCityIndProt', pec[2]);
				getAllCourtsByCity('edIndDemandprotsent','ul',pec[2],'unitarios,colegiados,federales');
				$('#edIndDemandprotsent [data-turnada="' + info.tipodemandaturnadaa
					+ '"][value="'+info.demandaamparoturnadaa+'"] ').addClass('selected');
				$('[data-edlist="edIndDemandprotsent"]').val($('#edIndDemandprotsent li.selected').text());
			}*/
			var tipoturnada=info.tipodemandaturnadaa||'', demandaturnada=info.demandaamparoturnadaa||'';
			if(tipoturnada!=''&&demandaturnada!=''){
				var reg=getCitytByIdnType(demandaturnada,tipoturnada);
				if(reg==null)return;
				var cityinfo=getCourtById(reg[0].estadoid);
				getTextDDFilterByVal('edStateIndProt',pec[1]);
				getCitiesByState('edCityIndProt','ul',pec[1]);
				getTextDDFilterByVal('edCityIndProt', pec[2]);
				getAllCourtsByCity('edIndDemandprotsent', 'ul', pec[2], 'unitarios,colegiados,federales');
				$('#edIndDemandprotsent li[data-turnada="' + tipoturnada
					+ '"][value="' + demandaturnada + '"]').addClass('selected');
				$('[data-edlist="edIndDemandprotsent"]').val($('#edIndDemandprotsent li.selected').text());
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_protections'),"error");
		}
	});
	try{
		createDropZone('uploadXEditIndProt','formEditIndProt',id,4);
	}catch (e){
		clearTemp();
		$('#areaIndProtUpload').html('');
		$('#areaIndProtUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXEditIndProt" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZone('uploadXEditIndProt','formEditIndProt',id,4);
	}
	$("#uploadXEditIndProt").addClass("dropzone");
};

function addIndProtection(e){
	$('#errorOnAddIndProt, .containTL').hide();
	$('input, select').parent().removeClass('has-error');
	var err="",tag="",clientid=$('#indProtClientList [name=rowline]:checked')[0],
		origintype=$('#indOrigintype').val(),protection=$('#indprotection').val(),
		trialid=$('#indSelecttrialO option:selected').val(),
		apelacionid=$('#indAppealList option:selected').val(),complaining='',
		interestedtrdparty='',claimedact=$('#indclaimedact').val(),
		respauth=$('#indrespauth').val(),dateclaimedactNtn=$('#indDateclaimedactNtn').val(),
		filingdatelawsuit=$('#indFilingdatelawsuit').val(),
		demandprotsent=$('#indDemandprotsent li.selected').val()||'',
		tipodemandaturnadaa=$('#indDemandprotsent li.selected').attr('data-turnada')||'',
		admissiondate=$('#autoadmissiondate').val(),admissionnotifdate=$('#indAdmissionnotifdate').val(),
		consthearingdate=$('#consthearingdate').val(),judgment=$('#judgment').val(),
		rsrcreviewjudgment=$('#rsrcreviewjudgment').val(),susp=$('#provsusp').val(),
		incidentalhearingdate=$('#incidentalhearingdate').val(),
		adjournmentjudgment=$('#adjournmentjudgment').val();
	clientid=(typeof clientid=='undefined')? '0':(clientid.id).replace(/[^0-9]/g,'');
	if(clientid=='0'){
		tag='indProtClient';
		err='err_select_client';
	}else if(respauth==''){
		tag='indrespauth';
		err='err_enter_respauth';
	}else{
		if(origintype=='1'){
			$('#indAppealList').val('0');
			apelacionid='0';
			trialid=(typeof trialid=='undefined')?'':trialid;
			if(trialid==''){
				tag='indSelecttrialO';
				err='err_select_origintrial';
			}
		}else if(origintype=='2'){
			$('#indSelecttrialO').val('0');
			trialid='0';
			apelacionid=(typeof apelacionid=='undefined')?'':apelacionid;
			if(apelacionid==''){
				tag='indAppealList';
				err='err_select_appealO';
			}
		}else if(origintype=='0'){
			document.getElementById("indOrigintype").value="0";
		}else{
			tag='indOrigintype';
			err='err_enter_origin_act';
		}
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#puterrorOnAddIndProt').html(i18n(err));
		$('#errorOnAddIndProt').show();
		$('#indprotection-modal, .custombox-modal-open, #protection-modal').animate({scrollTop:0},'1000');
		return false;
	}
	var cte=$('#indProtClient').val();
	if(document.getElementById("custQ3_q").checked){
		complaining=cte;
		interestedtrdparty=$('#interestedtrdparty').val();
		
	}else if(document.getElementById("custQ3_3").checked){	
		interestedtrdparty=cte;
		complaining=$('#complaining').val();
	}
	var param='clientid='+clientid+'&apelacionid='+apelacionid+'&protection='
		+protection+'&trialid='+trialid+'&complaining='+complaining
		+'&interestedtrdparty='+interestedtrdparty+'&claimedact='+claimedact
		+'&respauth='+respauth+'&dateclaimedactNtn='+dateclaimedactNtn
		+'&filingdatelawsuit='+filingdatelawsuit+'&demandprotsent='+demandprotsent
		+'&tipodemandaturnadaa='+tipodemandaturnadaa+'&admissiondate='+admissiondate
		+'&admissionnotifdate='+admissionnotifdate+'&consthearingdate='+consthearingdate
		+'&judgment='+judgment+'&rsrcreviewjudgment='+rsrcreviewjudgment
		+'&incidentalhearingdate='+incidentalhearingdate+'&origintype='+origintype
		+'&adjournmentjudgment='+adjournmentjudgment+'&provsusp='+susp;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/addNewIndProtection",
		data:param,
		async:false,
		success:function(data){
			if(data=="msg_data_saved"){
				swal(i18n("msg_success"),i18n(data),"success");
				location.href=location.href.replace(/^(.*)\#.*$/, '$1');
			}else{
				$('#puterrorOnAddIndProt').html(i18n(data));
				$('#errorOnAddIndProt').show();
				return null;
			}
			$('#indprotection-modal').modal('toggle');
		},error:function(e){
			$('#puterrorOnAddIndProt').html(i18n('err_record_no_saved'));
			$('#errorOnAddIndProt').show();
		}
	});
};

function updateIndProt(){
	var err="",tag="",clientid=$('#edIndProtClientList [name=rowline]:checked')[0],
		origintype=$('#edIndOrigintype').val(),protection=$('#edIndProtection').val(),
		trialid=$('#edIndSelecttrialO option:selected').val(),protection=$('#edIndProtection').val(),
		apelacionid=$('#edIndAppealList option:selected').val(),complaining=$('#edIndComplaining').val(),
		interestedtrdparty=$('#edIndTrdparty').val(),claimedact=$('#edIndClaimedact').val(),
		respauth=$('#edIndRespauth').val(),dateclaimedactNtn=$('#edIndDateclaimedactNtn').val()||'',
		filingdatelawsuit=$('#edIndFilingdatelawsuit').val()||'',
		demandprotsent=$('#edIndDemandprotsent li.selected').val()||'',
		tipodemandaturnadaa=$('#edIndDemandprotsent li.selected').attr('data-turnada')||'',
		admissiondate=$('#edIndAutoadmissiondate').val()||'',
		admissionnotifdate=$('#edIndAdmissionnotifdate').val()||'',
		consthearingdate=$('#edIndConsthearingdate').val()||'',judgment=$('#edIndJudgment').val(),
		rsrcreviewjudgment=$('#edIndRsrcreviewjudgment').val(),susp=$('#edIndProvsusp').val(),
		incidentalhearingdate=$('#edIndIncidentalhearingdate').val()||'',
		adjournmentjudgment=$('#edIndAdjournmentjudgment').val(),f2='true';
	clientid=(typeof clientid=='undefined')? '0':(clientid.id).replace(/[^0-9]/g,'');
	if(clientid=='0'){
		tag='edIndProtClient';
		err='err_select_client';
	}else if(respauth==''){
		tag='edIndRespauth';
		err='err_enter_respauth';
	}else{
		if(origintype=='1'){
			$('#edIndAppealList').val('0');
			apelacionid='0';
			trialid=(typeof trialid=='undefined')?'':trialid;
			if(trialid==''){
				tag='edIndSelecttrialO';
				err='err_select_origintrial';
			}
		}else if(origintype=='2'){
			$('#edIndSelecttrialO').val('0');
			trialid='0';
			apelacionid=(typeof apelacionid=='undefined')?'':apelacionid;
			if(apelacionid==''){
				tag='edIndAppealList';
				err='err_select_appealO';
			}
		}else if(origintype=='0'){
			document.getElementById("indOrigintype").value="0";
			trialid='0';
			apelacionid='0';
			$('#edIndSelecttrialO,#edIndAppealList').val('0');
		}else{
			tag='edIndOrigintype';
			err='err_enter_origin_act';
		}
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#puterrorOnEdit').html(err);
		$('#errorOnEdit').show();
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var cte=$('#edIndProtClient').val();
	if(document.getElementById("edIndCustQ3_q").checked){
		complaining=cte;
		interestedtrdparty=$('#edIndTrdparty').val();
	}else if(document.getElementById("edIndCustQ3_3").checked){	
		interestedtrdparty=cte;
		complaining=$('#edIndComplaining').val();
	}
	f2='&f2='+($('#f2').length===0?'0':$('#f2 li.selected').val()||0);
	var param='amparoid='+$('#edIndId').val()+'&apelacionid='+apelacionid
		+'&protection='+protection+'&trialid='+trialid+'&complaining='+complaining
		+'&interestedtrdparty='+interestedtrdparty+'&claimedact='+claimedact
		+'&respauth='+respauth+'&dateclaimedactNtn='+dateclaimedactNtn
		+'&filingdatelawsuit='+filingdatelawsuit+'&demandprotsent='+demandprotsent
		+'&tipodemandaturnadaa='+tipodemandaturnadaa+'&admissiondate='+admissiondate
		+'&admissionnotifdate='+admissionnotifdate+'&consthearingdate='+consthearingdate
		+'&judgment='+judgment+'&rsrcreviewjudgment='+rsrcreviewjudgment
		+'&provsusp='+susp+'&incidentalhearingdate='+incidentalhearingdate
		+'&adjournmentjudgment='+adjournmentjudgment+'&clientid='+clientid+f2;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});	
	$.ajax({
		type:"POST",
		url:ctx+"/updateIndProtection",
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
				$('#puterrorOnEdit').html(i18n('err_record_no_saved'));
				$('#errorOnEdit').show();
			}
		},error:function(e){
			$('#puterrorOnEdit').html(i18n('err_record_no_saved'));
			$('#errorOnEdit').show();
		}
	});
	$('.custombox-modal-open').animate({scrollTop:0},'1000');
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
					if(data="false"){
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

$("#addNewProtInd").click(function(){
	$('#errorOnAddIndProt, .containTL').hide();
	$('input, select').parent().removeClass('has-error');
	$('#divAppealOrigin, #divTrialOrigin').addClass('dnone');
	$("#addNewProtInd").attr('href','#indprotection-modal');
	getEstados('indState','ul');
	getClientListTab('#indProtClientList');
	try{
		myDropzone = createDropZone("uploadXIndProt", "formAddIndProt", '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}catch (e){
		clearTemp();
		$('#areaNewIndProtUpload').html('');
		$('#areaNewIndProtUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXIndProt" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZone("uploadXIndProt", "formAddIndProt", '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}
	$("#uploadXIndProt").addClass("dropzone");
	$('.custombox-modal-open').animate({scrollTop:0},'1000');
});

$("#edIndOrigintype").on('change', function(){
	var clid=$('#edIndProtClientList [name=rowline]:checked')[0],
		err='',targetId='';
	clid=(typeof clid=='undefined')? '0':(clid.id).replace(/[^0-9]/g,'');
	if(this.value==1){
		targetId='#edIndSelecttrialO';
		if(clid==0)
			err='msg_select_clientfirst';
		else
			getRelClientTrial(clid,targetId);
	}else if(this.value==2){
		targetId='#edIndAppealList';
		if(clid==0)
			err='msg_select_clientfirst';
		else
			getRelClientAppeal(clid,targetId);
	}
	if(err!=''){
		$(targetId).empty();
		$('#edIndProtClient').parent().addClass('has-error');
		$('#puterrorOnEditIndProt').html(i18n(err));
		$('#errorOnEditIndProt').show();
		$('#edit-indprotection-modal, .custombox-modal-open').animate({scrollTop:0},'1000');
		var option=new Option(i18n('msg_select_clientfirst'),'',!1,!0);
		$(targetId).append(option);
	}
});


$('#indProtClient').on('click keyup input paste change delete', function(e) {
	filtext(e.target.value, '#indProtClientList');
	$('.containTL').show();
});
$('#edIndProtClient').on('click keyup input paste change delete', function(e) {
	filtext(e.target.value, '#edIndProtClientList');
	$('.containTL').show();
});

$("input[type=radio][name=custQ3]").click(function(){
	$('#complaining,#interestedtrdparty').val('');
	var quejoso=document.getElementById("custQ3_q").checked,
		tercero=document.getElementById("custQ3_3").checked,
		clientIs=$('#indProtClient').val();
	if(quejoso){
		$('#opCustQ3_3').fadeIn('fast');
		$('#opCustQ3_q').fadeOut('fast');
		$('#complaining').val(clientIs);
	}else if(tercero){
		$('#opCustQ3_q').fadeIn('fast');
		$('#opCustQ3_3').fadeOut('fast');
		$('#interestedtrdparty').val(clientIs);
	}
});

$("input[type=radio][name=edIndCustQ3]").click(function(){
	var quejoso=document.getElementById("edIndCustQ3_q").checked,
		tercero=document.getElementById("edIndCustQ3_3").checked,
		clientIs=$('#edIndProtClient').val();
	$('#edIndComplaining,#edIndTrdparty').val('');
	if(quejoso){
		$('#edOpCustQ3_3').fadeIn('fast');
		$('#edOpCustQ3_q').fadeOut('fast');
		$('#edIndComplaining').val(clientIs);
	}else if(tercero){
		$('#edOpCustQ3_q').fadeIn('fast');
		$('#edOpCustQ3_3').fadeOut('fast');
		$('#edIndTrdparty').val(clientIs);
	}
});

$("#indOrigintype").on('change', function(){
	var o=this.value;
	if(o=='1'){
		$('#divAppealOrigin').addClass('dnone').fadeIn('fast');
		$('#divTrialOrigin').removeClass('dnone').fadeIn('fast');
	}else if(o=='2'){
		$('#divTrialOrigin').addClass('dnone').fadeIn('fast');
		$('#divAppealOrigin').removeClass('dnone').fadeIn('fast');
	}else{
		$('#divTrialOrigin, #divAppealOrigin').addClass('dnone').fadeOut('fast');
	}
});

$("#edIndOrigintype").on('change', function(){
	var o=this.value;
	if(o=='1'){
		$('#divEdIndAppealOrigin').addClass('dnone').fadeIn('fast');
		$('#divEdIndTrialOrigin').removeClass('dnone').fadeIn('fast');
	}else if(o=='2'){
		$('#divEdIndTrialOrigin').addClass('dnone').fadeIn('fast');
		$('#divEdIndAppealOrigin').removeClass('dnone').fadeIn('fast');
	}else{
		$('#divEdIndTrialOrigin, #divEdIndAppealOrigin').addClass('dnone').fadeOut('fast');
	}
});

/* limpia inputs * /
$('#indprotection-modal, #edit-indprotection-modal').on('hidden.bs.modal',function(e){
	$(this).find("input,textarea").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked","").end();
});*/

document.addEventListener('click',function(e){
	if (e.target.nodeName == 'TD') {
		var tpe=e.target.parentElement,targetId='',rb;
		var tList=tpe.parentElement.parentElement.id;
		if (tList=='indProtClientList' || tList=='edIndProtClientList')
			targetId=(tList=='indProtClientList'?'ind':'edInd')+'ProtClient';
/*		else if(tList=='trialAplList' || tList=='edTrialAplList')
			targetId=tList=='trialAplList'?'trialApl':'edTrialApl';*/
		if(targetId!=''){
			$(' .rowopt').css('backgroundColor','#fff');
			tpe.style.backgroundColor="ivory";
			rb=$(tpe).find('input:radio[name=rowline]')[0];
			rb.checked=true;
			$('#'+targetId).val($(rb).attr('data-val'));
			$('.containTL').hide();
			if(targetId=='indProtClient'||targetId=='edIndProtClient'){
				$('#indOrigintype').val('');
				$('#divTrialOrigin,#divAppealOrigin,#divEdIndTrialOrigin,#divEdIndAppealOrigin').hide();
				$('#indSelecttrialO,#indAppealList,#edIndSelecttrialO,#edIndAppealList').empty();
			}
		}
	}
}, false);

Dropzone.options.uploadXIndProt={
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