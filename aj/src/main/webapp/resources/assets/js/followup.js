function loadDefault(){
	var d=new Date();
	try{$('#today').html(d.toLocaleDateString(getLanguageURL()));}
	catch(er){$('#today').html(d.toLocaleDateString('en'));}
	var arrayLeads=document.getElementsByClassName("editFollowup");
	polyfill("previousElementSibling","firstElementChild","classList");
	for(var i=0; i< arrayLeads.length; i++){
		var leadid=arrayLeads[i].id;
		var param='leadid='+leadid;
		fupList=new Array();
		eCalList=new Array();
		$.ajax({
			type:"POST",
			url:ctx+"/getleadAndFollowlist.jet",
			data:param,
			async:false,
			success:function(data){
				if(data.length>0){
					followupList=data[0];
					calendarEvent=data[1];
					lstatus=data[2];
					lang=getLanguageURL();
					// Construcción de tabla incidencias y encabezados
					var leadTable="<tr style='position:relative; left:20px;' data-idforfollow="+leadid+">"+
						"<td data-idforfollow="+leadid+" class='no-plus' colspan='7'><p class='nFup' data-idforfollow='"+leadid+"'></p>"+
							"&emsp;"+(followupList.length)+"&nbsp;"+(followupList.length==1?i18n('msg_single_follow_item'):i18n('msg_follow_items'))+
						"<tr data-followHideShow="+leadid+" class='tr2nd' style='display:none; background-color:#fff !important;'>"+
						"<td class='no-plus' colspan='7'>"+
						"<table class='tableIn tresp'><thead><tr class='tr2nd' data-followHideShow="+leadid+">"+
						"<th class='tac fwb no-plus'>Item</th>"+
						"<th class='tac fwb'>"+i18n('msg_date')+"</th>"+
						"<th class='tac fwb'>"+i18n('msg_action')+"</th>"+
						"<th class='tac fwb'>"+i18n('msg_status')+"</th>"+
						"<th class='tac fwb'>"+i18n('msg_date_start')+"</th>"+
						"<th class='tac fwb'>"+i18n('msg_date_end')+"</th>"+
						"<th class='tac fwb'>"+i18n('msg_edit')+"</th></tr></thead><tbody>";
					// Inserta insidencias
					for(var i=0; i< followupList.length; i++){
						var fecha1="",fecha2="",actid="",action=i18n('msg_noaction'),stid="",status="",bc="",
							fecha=setFormatPatternDate(new Date(followupList[i].date),getFormatPatternDate()+" HH:mm"),fupid=followupList[i].followupid;
						for(var c=0; c<calendarEvent.length; c++){
							if((fupid)==(calendarEvent[c].followupid)){
								stid=calendarEvent[c].status;
								actid=calendarEvent[c].action;
								for(var ls=0; ls<lstatus.length; ls++){
									if(lstatus[ls].statusfollowid===stid){
										bc = lstatus[ls].color;
										if(lang==='es'){
											status = lstatus[ls].statuses;
										}else{
											status = lstatus[ls].statusen;
										}
									}
								}
								
								1==actid?(action=i18n("msg_appointment"))
								:2==actid?(action=i18n("msg_phonecall"))
								:3==actid?(action=i18n("msg_mail"))
								:(action=i18n("msg_noaction"));
								fecha1=setFormatPatternDate(new Date(calendarEvent[c].dateini),getFormatPatternDate()+" HH:mm"),
								fecha2=setFormatPatternDate(new Date(calendarEvent[c].dateend),getFormatPatternDate()+" HH:mm");
								break;
							}
						}leadTable+=// Detalle...
						 "<tr class='tr2nd' data-followHideShow="+leadid+">"+
						 "<td class='tac no-plus' data-label='Item'>"+(i+1)+"</td>"+
						 "<td class='tac' data-label='"+i18n('msg_date')+"'>"+fecha+"</td>"+
						 "<td data-label='"+i18n('msg_action')+"'><spam class='actionEdit' onclick='getFollowupDetails("+leadid+","+fupid+");'>"+action+":</spam>"+followupList[i].description+"</td>"+
						 "<td class='tac' data-label='"+i18n('msg_status')+"'><span class='label' style='background-color: "+bc+"' onclick='getFollowupDetails("+leadid+","+fupid+");'>"+status+"</span></td>"+
						 "<td class='tac' data-label='"+i18n('msg_date_start')+"'>"+fecha1+"</td>"+
						 "<td class='tac' data-label='"+i18n('msg_date_end')+"'>"+fecha2+"</td>"+
						 "<td class='tac' data-label='"+i18n('msg_edit')+"'><a class='table-action-btn editOpportunity' onclick='getFollowupDetails("+leadid+","+fupid+");' ><i class='md md-edit'></i></a></td>"+
						 "</tr>";
					}// Inserta resultados en la tabla
					leadTable+="</tbody></table></td></tr><tr data-idforfollow="+leadid+" style='height:0px;'></tr></td></tr>";
					var temp=document.getElementById(leadid).parentElement;
					$(leadTable).insertAfter(temp.parentElement);
				}
			},error:function(e){swal(i18n('msg_warning'),i18n('err_unable_get_followup')+"(1)","error");}
		});
	}
};

document.addEventListener("click", function(e){
	var reload=false,obj=e.target,eActived=e.target.parentElement.parentElement.parentElement;
	try{var isData=e.target.getAttribute("data-idforfollow");
		if(eActived.id=="datatable-buttons"){
			if(isData !=null){
				if(e.target.getAttribute("no-plus") !=null){
					reload=false;}
			}else if(e.target.getAttribute("aria-sort") !=null){
				reload=true;}
		}
	}catch(err){}
	if(!reload){
		try{eActived=e.target.parentElement.parentElement;
			if(eActived.getAttribute("class")=="pagination"){
				if(e.target.parentElement.getAttribute("class").indexOf("disabled") <=0){
					reload=true;}
			}
		}catch(err){swal(i18n('msg_warning'),i18n('err_unable_get_followup')+"(2)","error");}
	}if(!reload){
		try{eActived=e.target.getAttibute("data-idforfollow");
			if(eActived !=""){reload=true;}
		}catch(err){}
	}if(reload){
		$('[data-idforfollow]').remove();
		loadDefault();
	}if(isData!=null){
		var follows=document.querySelectorAll("[data-followHideShow='"+isData+"']");
				
		if(follows[0].style.display == 'table-row'){
			$("td[data-idforfollow='"+isData+"'] p").removeClass('nFminus');
			$("td[data-idforfollow='"+isData+"'] p").addClass('nFup');
		}else{
			$("td[data-idforfollow='"+isData+"'] p").removeClass('nFup');
			$("td[data-idforfollow='"+isData+"'] p").addClass('nFminus');
		}
		
		for(var i=0; i< follows.length; i++){
			follows[i].style.display=(follows[i].style.display=='table-row')?'none':'table-row';
		}
	}
});


function addNewFollowup(obj){
	var d=new Date();
	$('#newFupDate').val(setFormatPatternDate("",getFormatPatternDate("")));
	$('#fixDate').val(formatToDateYMD(d));
	$('#leadId').val(obj.id);
	$('#leadName').text(obj.getAttribute("data-company"));
	$('#followup-modal').modal('toggle');
};

function addFollowup(){
	var bton = $("#savebuttonfollowup");
	bton.attr("disabled", true);
	
	if($('#fixDate').val()==""){
		$('#putErrorFollowup').html(i18n('err_enter_date'));
		$('#addFollowError').css('display','block');
		bton.attr("disabled", false);
		return false;
	}var createddate=setFormatPatternDate("","dd/MM/yyyy HH:mm aa"),
		leadid=$('#leadId').val(),leadname=$('#leadName').text(),status=$('.selectpicker').val(),
		date=$('#fixDate').val(),starttime=$('#startTime').val(),action=$('#fupAction'),
		descr=$('#newDescription').val(),elapsedTime=$('#newDuration').val(),
		sendmail=xCtrl("sendeCal").checked,actionid=action.val();
	
	var actiontext=(actionid==0)?"crm":$(action).find('option:selected').text();
	var dstr=new Date(date+" "+starttime);
	$('#addUserError').css('display','none');
	if(leadid==""){
		$('#putErrorFollowup').html(i18n('err_empty_field'));
		$('#addFollowError').css('display','block');
		bton.attr("disabled", false);
		return false;
	}else if(descr==""){
		$('#putErrorFollowup').html(i18n('err_description'));
		$('#addFollowError').css('display','block');
		bton.attr("disabled", false);
		return false;
	}else if(actionid==1&&elapsedTime<0){
		$('#putErrorFollowup').html(i18n('msg_duration_hours'));
		$('#addFollowError').css('display','block');
		bton.attr("disabled", false);
	}else{
		var param='createddate='+createddate+'&leadid='+leadid+'&leadname='+leadname// '&createdtime='+createdtime+
			+"&status="+status+"&date="+date+'&starttime='+starttime+'&actionid='+actionid+'&actiontext='+actiontext
			+"&description="+descr+'&sendmail='+sendmail+"&elapsedTime="+elapsedTime;// '&enddate='+enddate+'&endtime='+endtime
		$.ajax({
			type:"POST",
			url:ctx+"/addNewFollowup.jet",
			data:param,
			async:false,
			success:function(data){
				$('#addUserErrorFollowup').css('display','none');
				swal({
					title:i18n('msg_success'),
					text:i18n('msg_followup_saved_successfully'),
					type:"success",
					timer:3000,
					allowEscapeKey:false
				},function(){swal.close();});
				location.reload();
			},error:function(e){
				$('#putErrorFollowup').html(i18n('err_unable_get_followup')+"(3)");
				$('#addFollowError').css('display','block');
				bton.attr("disabled", false);
			}
		});
	}
};

$("#addFollowupCancel").click(function(){
	$('#addUserErrorLead').css('display','none');
	$('#lead').val("");
	$('#leadInfo').val("");
	$('#selectcompany').val("0");
	$('#selectcontact').val("0");
	$('#selectstatus').val("0");
	$('#quantification').val("");
	$('#product').val("");
	$('#selectgroup').val("0");
	$('#commentArea').val("");
});

$('.table-action-btn.deleteFollowup').click(function(){
	var id=$(this).attr('id');
	swal({
		title:i18n('msg_are_you_sure'),
		text:i18n('msg_will_not_recover_followup'),
		type:"warning",
		showCancelButton:true,
		confirmButtonClass:'btn-warning',
		confirmButtonText:i18n('btn_yes_delete_it'),
		closeOnConfirm:false,
		closeOnCancel:false
	}).then((isConfirm) => {
		if(isConfirm){
			deleteFollowup(id);
			swal({
				title:i18n('msg_deleted'),
				text:i18n('msg_followup_deleted'),
				type:"success" 
			},function(){
				location.reload();
			});
		}else{swal(i18n('msg_cancelled'), i18n('msg_followup_safe'), "warning");}
	});
});

function deleteFollowup(fupid){
	var param="fupid="+fupid;
	$.ajax({
		type:'POST',
		url:ctx+"/deletefollowup.jet",
		data:param,
		async:false,
		success:function(data){swal(i18n('msg_success'),i18n('msg_deleted'),"success");
		},error:function(resp){swal(i18n('msg_warning'),i18n('err_unable_get_followup'),"warning");}
	});
};

function editStatus(leadid,status,leadname){
	$("#edit-status-fup-modal").modal('toggle');
	$('#editStLeadid').val(leadid);
	$('#editStLeadName').html(leadname);
	$('#editStselectstatus').val(status);
	var edst=xCtrl("editStselectstatus").previousElementSibling.previousElementSibling.firstElementChild.firstElementChild;
	var stext=[0,"lost","open","won"],bg=[0,"info","success","danger"];
	edst.classList.remove("label-danger");
	edst.classList.remove("label-info");
	edst.classList.remove("label-success");
	edst.innerText=i18n("msg_"+stext[status]);
	edst.classList.add("label-"+bg[status]);
	$('#editStselectstatus').val(status);
	picker=$('#editStselectstatus').val();
};

function updateLeadStatus(){
	var param="leadid="+$('#editStLeadid').val()+"&status="+$('#editStselectstatus').val();
	$.ajax({
		type:'POST',
		url:ctx+"/updateLeadStatus.jet",
		data:param,
		async:false,
		success:function(data){
			if(data=="True"){
				swal({
					title:i18n('msg_success'),
					text:i18n('msg_record_updated'),
					type:"success",
					timer:3000,
					allowEscapeKey:false
				},function(){swal.close();});
				location.reload();
			}
		},error:function(resp){swal(i18n('msg_warning'),i18n('err_unable_get_followup'),"warning");}
	});
};

function getFollowupDetails(leadid,fupid){
	var param="leadid="+leadid+"&fupid="+fupid;
	$.ajax({
		type:'POST',
		url:ctx+"/getEditFollowupLead.jet",
		data:param,
		async:false,
		success:function(data){
			leadData=data[0];
			fupData=data[1];
			eCalData=data[2];
			if(followupList[0]=="false"){
				swal(i18n('msg_warning'),i18n('err_unable_get_followup'),"error");
			}else{
				var d=new Date(eCalData[0].dateini);
				var fixD=d.getFullYear()+"-"+twoDigits(d.getMonth()+1)+"-"+twoDigits(d.getDate());
				var fixT=twoDigits(d.getHours())+":"+twoDigits(d.getMinutes());
				$('#editleadid').val(leadid);
				$('#editLeadName').text(leadData[0].lead);
				$('#edifollowupid').val(fupid);
				$('#editDescription').val(fupData[0].description);
				$("#editFupAction").val(eCalData[0].action);
				$('#editFupDate').val(setFormatPatternDate(d,getFormatPatternDate("")));
				$('#editFixDate').val(fixD);
				$('#editStartTime').val(fixT);
				$("#editSendeCal").prop("checked",true);
				$("#edit-followup-modal").modal('toggle');
				if(eCalData[0].action==1){
					$('.editCalData').css('display','block');
					var d2=new Date(eCalData[0].dateend);
					var t1=d.getHours();
					var t2=d2.getHours();
					$('#editDuration').val(t2-t1);
				}
				
				var stid=eCalData[0].status;
				/*var edst=xCtrl("editselectstatus").previousElementSibling.previousElementSibling.firstElementChild.firstElementChild;
				edst.classList.remove("label-danger");
				edst.classList.remove("label-info");
				edst.classList.remove("label-success");
				var stid=eCalData[0].status;
				var stext=[0,"lost","open","won"];
				var bg	= [0,"info","success","danger"];
				edst.innerText=i18n("msg_"+stext[stid]);
				edst.classList.add("label-"+bg[stid]);*/
				//if(lang=="es")
				$('#editselectstatus ').selectpicker('val', stid);
				//$('#editselectstatus').val("Abierto");
				//$('#editselectstatus').selectpicker('refresh');
				//$('.selectpicker').selectpicker('val', 'Abierto');
				picker=$('#editselectstatus').val();
			}
		},error:function(resp){swal(i18n('msg_warning'),i18n('err_unable_get_followup'),"warning");}
	});
};

function updateFollowup(){
	var action=$('#editFupAction');
	var actionid=action[0].value;
	var actiontext=(actionid==0)?"crm":action[0].selectedOptions[0].text,
		elapsedTime=$('#editDuration').val();
	var status=$("#editselectstatus").val(),
		dstr=new Date($('#editFixDate').val()+" "+$('#editStartTime').val());
	var param='leadid='+$('#editleadid').val()+
		'&leadname='+$('#editLeadName').text()+
		'&followupid='+$('#edifollowupid').val()+
		'&description='+$('#editDescription').val()+
		'&status='+status+
		'&actionid='+actionid+
		'&actiontext='+actiontext+
		'&date='+$('#editFixDate').val()+
		'&starttime='+$('#editStartTime').val()+
		'&elapsedTime='+elapsedTime+
		'&sendmail='+xCtrl("editSendeCal").checked;
	if(actionid==1&&elapsedTime<0){
		$('#putErrorFollowup').html(i18n('msg_duration_hours'));
		$('#editFupError').css('display','block');
	}else{
		$.ajax({
			type:"POST",
			url:ctx+"/updatefollowup.jet",
			data:param,
			async:false,
			success:function(data){
				if(data=="True"){
					$('#addUserErrorFollowup').css('display','none');
					swal({
						title:i18n('msg_success'),
						text:i18n('msg_followup_saved_successfully'),
						type:"success",
						timer:3000,
						allowEscapeKey:false
					},function(){
						swal.close();
					});
					$('#edit-followup-modal').modal('toggle');
					location.reload();
				}else{
					$('#addUserErrorFollowup').css('display','block');
					swal({
						title:i18n('msg_error'),
						text:i18n('err_general'),
						type:"warning",
						timer:3000,
						allowEscapeKey:false
					},function(){
						swal.close();
					});
				}
			},error:function(e){swal(i18n('msg_warning'),i18n('err_unable_get_followup'),"warning");}
		});
	}
};

function i18n(msg){
	var i18n_message="";
	// IDIOMAS:si en la URL no contiene la variable "lenguage", asumirá que esta
	// en inglés
	lang=getLanguageURL();
	if(msg=="btn_yes_delete_it"){
		if(lang=="es"){ i18n_message="\u00a1S\u00ed, eliminar!";}
		else{ i18n_message="Yes, delete it!";}

	}else if(msg=="err_description"){
		if(lang=="es"){ i18n_message="\u00a1Favor de ingresar la descripci\u00f3n del seguimiento!";}
		else{ i18n_message="Please fill the description of followup!";}
	}else if(msg=="err_unable_get_followup"){
		if(lang=="es"){ i18n_message="\u00a1No fue posible obtener el seguimiento!";}
		else{ i18n_message="Unable to get Follow-up!";}
	}else if(msg=="err_enter_date"){
		if(lang=="es"){ i18n_message="\u00a1Favor de ingresar una fecha v\u00e1lida!";}
		else{ i18n_message="Please enter correct date!";}
	}else if(msg=="err_empty_field"){
		if(lang=="es"){ i18n_message="\u00a1Uno o varios campos contiene un dato no v\u00e1lido!";}
		else{ i18n_message="Some data are invalid, please correct them!";}
	}else if(msg=="msg_error"){
		if(lang=="es"){ i18n_message="\u00a1Error al procesar los datos!";}
		else{ i18n_message="Error processing data!";}
	}else if(msg=="err_general"){
		if(lang=="es"){ i18n_message="Uno o varios datos no son v\u00e1lidos, \u00a1Favor de corregirlos!";}
		else{ i18n_message="One or several data are not valid, please check them!";}
	}else if(msg=="msg_duration_hours"){
		if(lang=="es"){ i18n_message="Debe ingresar una duración válida en horas";}
		else{ i18n_message="Please enter a valid duration time in hours";}

	}else if(msg=="msg_action"){
		if(lang=="es"){ i18n_message="Acci\u00f3n";}
		else{ i18n_message="Action";}
	}else if(msg=="msg_appointment"){
		if(lang=="es"){ i18n_message="Cita";}
		else{ i18n_message="Appointment";}
	}else if(msg=="msg_are_you_sure"){
		if(lang=="es"){ i18n_message="\u00bfEsta seguro?";}
		else{ i18n_message="Are you sure?";}
	}else if(msg=="msg_cancelled"){
		if(lang=="es"){ i18n_message="Cancelado";}
		else{ i18n_message="";}
	}else if(msg=="msg_date"){
		if(lang=="es"){ i18n_message="Fecha";}
		else{ i18n_message="Date";}
	}else if(msg=="msg_date_end"){
		if(lang=="es"){ i18n_message="Fecha final";}
		else{ i18n_message="End date";}
	}else if(msg=="msg_date_start"){
		if(lang=="es"){ i18n_message="Fecha inicial";}
		else{ i18n_message="Start date";}
	}else if(msg=="msg_deleted"){
		if(lang=="es"){ i18n_message="\u00a1Eliminado!";}
		else{ i18n_message="Deleted!";}
	}else if(msg=="msg_edit"){
		if(lang=="es"){ i18n_message="Editar";}
		else{ i18n_message="Edit";}
	}else if(msg=="msg_single_follow_item"){
		if(lang=="es"){ i18n_message=" dato de seguimiento";}
		else{ i18n_message=" follow-up item";}
	}else if(msg=="msg_follow_items"){
		if(lang=="es"){ i18n_message=" datos de seguimiento";}
		else{ i18n_message=" follow-up items";}
	}else if(msg=="msg_followup_safe"){
		if(lang=="es"){ i18n_message="Tu seguimiento esta seguro!";}
		else{ i18n_message="Your follow-up is safe!";}
	}else if(msg=="msg_followup_saved_successfully"){
		if(lang=="es"){ i18n_message="\u00a1Seguimiento guardado exitosamente!";}
		else{ i18n_message="Follow-up saved successfully!";}
	}else if(msg=="msg_lost"){
		if(lang=="es"){ i18n_message="Perdido";}
		else{ i18n_message="Lost";}
	}else if(msg=="msg_mail"){
		if(lang=="es"){ i18n_message="Email";}
		else{ i18n_message="Email";}
	}else if(msg=="msg_noaction"){
		if(lang=="es"){ i18n_message="Sin acci\u00f3n";}
		else{ i18n_message="No action";}
	}else if(msg=="msg_open"){
		if(lang=="es"){ i18n_message="Abierto";}
		else{ i18n_message="Open";}
	}else if(msg=="msg_phonecall"){
		if(lang=="es"){ i18n_message="Llamada telef\u00f3nica";}
		else{ i18n_message="Phone call";}
	}else if(msg=="msg_status"){
		if(lang=="es"){ i18n_message="Estatus";}
		else{ i18n_message="Status";}
	}else if(msg=="msg_success"){
		if(lang=="es"){ i18n_message="\u00a1Correcto!";}
		else{ i18n_message="Success!";}
	}else if(msg=="msg_record_updated"){
		if(lang=="es"){ i18n_message="\u00a1Registro actualizado!";}
		else{ i18n_message="Updated record!";}
	}else if(msg=="msg_warning"){
		if(lang=="es"){ i18n_message="\u00a1Advertencia!";}
		else{ i18n_message="Warning!";}
	}else if(msg=="msg_will_not_recover_followup"){
		if(lang=="es"){ i18n_message="\u00a1Si acepta, no podr\u00e1 recuperar este seguimiento!";}
		else{ i18n_message="You will not be able to recover this follow-up!";}
	}else if(msg=="msg_won"){
		if(lang=="es"){ i18n_message="Ganado";}
		else{ i18n_message="Won";}
	}return i18n_message;
}