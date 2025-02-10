function jsTestMailChimp(){
	//var param="from=templates&query=id,name,type::user,date_created,thumbnail&typeList=detail";
	var param="emails=gustavo.santoscoy@jetaccess.com&campaign=";
	$.ajax({
		type:'POST',
		url:ctx+"/testMailChimp.jet",
		//url:ctx+"/getList.jet",
		data:param,
		async:false,
		success:function(data){
			if(data=="true"){
				alert("Correcto");
			}
		},error:function(e){
			alert(i18n("Fall\u00f3"));
		}
	});
};

function fillSelect(idSelect,param){
	var cols=param.split(",");
	$.ajax({
		type:'POST',
		url:ctx+"/getList.jet",
		data:param,
		async:false,
		success:function(data){
			if(data!=null){
				clearSelect(idSelect);
				var option=new Option(i18n('msg_select_option'),0,true,true);
				$('#'+idSelect).append(option);
				$("#"+idSelect +" option:selected").attr('disabled','disabled')
				for(var d=0; d<data.length; d+=cols.length){//cols=columnas solicitadas en param
					var option=new Option(data[d],data[d+1],true,false);
					$('#'+idSelect).append(option);
				}
			}else{
				swal(i18n("msg_error"),i18n("err_unable_get_list"),"error");
			}
		}
	});
};

function loadDeleteCampaign(campaignid,campaignname){
	$('#delCampaignName').text(campaignname);
	$('#delCampaignId').val(campaignid);
//	fillSelect("selEditEmailList","from=lists&query=name,id");
//	fillSelect("selEdittemplateList","from=templates&query=name,id,type::user");
	$.ajax({
		type:'POST',
		url:ctx+"/getList.jet",
//		data:"from=campaigns/"+campaignId+"&query=subject_line,from_name,reply_to,list_id,template_id",
		data:"from=campaigns/"+campaignid+"&query=subject_line,from_name,reply_to",
		async:false,
		success:function(data){
			if(data!=null){
				$('#delSubject').text(data[0]);
				$('#delFrom_name').text(data[1]);
				$('#delReply_to').text(data[2]);
//				$('#seldelEmailList').val(data[3]);
//				$('#seldeltemplateList').val(data[4]);
			}else{
				swal(i18n("msg_error"),i18n("err_unable_get_campaign"),"error");
			}
		}
	});
};

$("#addcampaign").click(function(){
	$("#addcampaign").attr('href','#addcampaign-modal');
	fillSelect("selEmailList","from=lists&query=name,id")
	fillSelect("seltemplateList","from=templates&query=name,id,type::user");
});

function createCampaign(){
	//validar correo
	
	var campaignname=$("#campaignname").val(),
	subject=$("#subject").val(),
	fromname=$("#from_name").val(),
	replyto=$("#reply_to").val(),
	idEndpoint=$("#selEmailList").val();
	template_id=$("#seltemplateList").val();
	if(campaignname==''){
		$('#putError').html(i18n("err_campaign_empty"));
		$('#addCampaignError').css('display','block');
		return false;
	}else{
		$('#addCampaignError').css('display','none');
		var param="title="+campaignname+"&subject_line="+subject+"&from_name="+fromname+
			"&reply_to="+replyto+"&list_id="+idEndpoint+"&template_id="+template_id;
		$.ajax({
			type:"POST",
			url:ctx+"/createCampaign.jet",
			data:param,
			async:false,
			success:function(data){
				if(data!=null){
					$("#campaignname").val("");
					swal(i18n("msg_success"),i18n("msg_campaign_saved_successfully"),"success");
					Custombox.close();
					window.location=ctx+"/campaigns.jet"+"?language="+getLanguageURL();
				}else{
					$('#putError').html(i18n("err_campaign_exists"));
					$('#addCampaignError').css('display','block');
				}
			},error:function(e){swal(i18n("msg_error"),i18n("err_unable_get_campaign")+", err.1","error");}
		});				
	}		
};

$("#addNewCampaignCancel").click(function(){
	$('#addCampaignError').css('display','none');
	$("#campaignname").val("");
});

$("#editCampaignCancel").click(function(){
	fillSelect('selEditList','from=lists&query=name,id');
	fillLists("allmem");
});

function editCampaignDetails(campaignId,campaignName){
	$('#EditCampaignError').css('display','none');
	$('#putEditCampaignError').html("");
	$('#editCampaignName').html(campaignName);
	$('#editCampaignId').val(campaignId);
	fillSelect("selEditEmailList","from=lists&query=name,id");
	fillSelect("selEdittemplateList","from=templates&query=name,id,type::user");
	$.ajax({
		type:'POST',
		url:ctx+"/getList.jet",
		data:"from=campaigns/"+campaignId+"&query=subject_line,from_name,reply_to,list_id,template_id",
		async:false,
		success:function(data){
			if(data!=null){
				$('#editSubject').val(data[0]);
				$('#editFrom_name').val(data[1]);
				$('#editReply_to').val(data[2]);
				$('#selEditEmailList').val(data[3]);
				$('#selEdittemplateList').val(data[4]);
			}else{
				swal(i18n("msg_error"),i18n("err_unable_get_list"),"error");
			}
		}
	});
};

function updateEditCampaign(){
	// Validar correo
	var campaignId=$("#editCampaignId").val(),
		campaignname=$("#editCampaignName").text(),
		subject_line=$('#editSubject').val(),
		from_name=$('#editFrom_name').val(),
		reply_to=$('#editReply_to').val(),
		list_id=$('#selEditEmailList').val(),
		template_id=$('#selEdittemplateList').val();
	if(subject_line==''){
		$('#putEditCampaignError').html(i18n("err_campaign_empty"));
		$('#EditCampaignError').css('display','block');
		return false;
	}else{
		var param="campaignid="+campaignId+"&campaignname="+campaignname+"&subject_line="+subject_line+
			"&from_name="+from_name+"&reply_to="+reply_to+"&list_id="+list_id+"&template_id="+template_id+
			"&type=regular";
		$.ajax({
			type:'POST',
			url:ctx+'/updateCampaign.jet',
			data:param,
			async:false,
			success:function(data){
				if(data){
					$('#EditCampaignError').css('display','none');
					swal({
						title:i18n('msg_success'),
						text:i18n('msg_campaign_saved_successfully'),
						type:"success",
						timer:3000,
						allowEscapeKey:false
					},function(){
						swal.close();
					});
					$('#edit-campaign-modal').modal('toggle');
					window.location=ctx+"/campaigns.jet"+"?language="+getLanguageURL();
				}else{
					swal({
						title:i18n('msg_error'),
						text:i18n('err_record_no_saved'),
						type:"warning",
						timer:3000,
						allowEscapeKey:false
					},function(){
						swal.close();
					});
				}
			},error:function(e){swal(i18n("msg_error"),i18n("err_unable_get_campaign")+", err.3","error");}
		});
	}
};


function sendCampaign(typesend){
	var campaignId=$("#sendCampaignId").val(),campaignList=$("#selSendListCampaign").val(),
		process="",param="";
	if(typesend=="test"){
		var allList=xCtrl(":sendtest"),emails="",maxtest=0,cont=!0;
		for(var i=0; i<allList.length; i++){
			var idch=xCtrl(allList[i].getAttribute("data-id"));
			if(idch.checked){
				emails+=allList[i].innerText+",";
				maxtest++;
				if(maxtest>6)cont=!1
			}
		}emails=emails.substring(0,emails.length-1);
		var extra=$("#extraTestEmail").val();
		if(extra!="")emails=emails==""?extra:emails+","+extra;
		var emailcount=emails.split(",").length;
		if(emailcount>6)cont=!1;
		if(!cont){
			alert("¡No se puede enviar a m\u00e1s de 6 direcciones en una sola campa\u00f1a!");
			return null;
		}
		process='/sendTestCampaign.jet';
		param="campaign="+campaignId+"&emails="+emails+"&extra="+extra;
	}else if(typesend=="send"){
		swal({
			title:i18n('msg_are_you_sure'),
			text:i18n('msg_sure_to_send'),
			type:"warning",
			showCancelButton:true,
			confirmButtonClass:'btn-warning',
			confirmButtonText:i18n('btn_yes_send_it'),
			closeOnConfirm:false,
			closeOnCancel:false
		}).then((isConfirm) => {
			if(isConfirm){
				param="campaignid="+campaignId+"&list="+campaignList;
				process='/setContentEmailAndSend.jet';
			}else{
				swal(i18n('msg_cancelled'), i18n('msg_campaign_not_sent'), "warning");
				return null;
			}
		});
	}else if(typesend=="resend"){	// Pendiente la configuración de *** "REENVÍO DE CAMPAÑA" ***
		process='/resendCampaign.jet';
	}
	$.ajax({
		type:'POST',
		url:ctx+process,
		data:param,
		async:false,
		success:function(sent){
			if(sent){
				swal({ 
					title:i18n('msg_sent'),
					text:i18n('msg_campaign_sent'),
					type:"success" 
				},
				function(){
					window.location=ctx+"/campaigns.jet"+"?language="+getLanguageURL();
				});
			}swal(i18n("msg_error"),i18n("err_unable_get_campaign")+", err.send campaign","error");
		}
	});
};

function deleteCampaign(){
	swal({
		title:i18n('msg_are_you_sure'),
		text:i18n('msg_sure_to_delete'),
		type:"warning",
		showCancelButton:true,
		confirmButtonClass:'btn-warning',
		confirmButtonText:i18n('btn_yes_delete_it'),
		closeOnConfirm:false,
		closeOnCancel:false
	}).then((isConfirm) => {
		if(isConfirm){
			var param="campaignid="+$('#delCampaignId').val();
			$.ajax({
				type:'POST',
				url:ctx+"/deleteCampaign.jet",
				data:param,
				async:false,
				success:function(data){
					if(data){
						swal({ 
							title:i18n('msg_deleted'),
							text:i18n('msg_campaign_deleted'),
							type:"success" 
							},
							function(){
								window.location=ctx+"/campaigns.jet"+"?language="+getLanguageURL();
							});
					}swal(i18n("msg_error"),i18n("err_unable_get_campaign")+", err.delete campaign","error");
				},error:function(resp){swal(i18n("msg_error"),i18n("err_unable_get_campaign")+", err.2","error");}
			});
		}else{
			swal(i18n('msg_cancelled'), i18n('msg_campaign_not_deleted'), "warning");
		}
	});
};
function loadDefSend(id, name){
	$("#sendCampaignId").val(id);
	$("#sendCampaignName").text(name);
	
	var param="from=campaigns&query=campaigns/"+id+"/send_time";
	$.ajax({
		type:'POST',
		url:ctx+"/getList.jet",
		data:param,
		async:false,
		success:function(data){
			var st="<tr><td class='tac' style='width:545px'>"+i18n("msg_no_sent")+"</td></tr>";
			if(data.length>0){st="";
				for(var d=0; d<data.length; d++)
					if(data[d]!="")st+="<tr><td class='tac' style='width:545px'>"+data[d]+"</td></tr>";
			}else{
				
			}
		}
	});
};

$("#newPermissionReminder").on("keyup",function(e){
	var keys=[9,13,34];
	if(keys.indexOf(e.keyCode)>0){
		$('#toggle-newListTab2').prop("checked",true);
		xCtrl("companyName").focus();
	}
});

$("#companyCountry").on("keyup",function(e){
	var keys=[9,13,34];
	if(keys.indexOf(e.keyCode)>0){
		$('#toggle-newListTab3').prop("checked",true);
		$('#newListFromName').focus(); 
	}
});

$("#createNewList").click(function(){
	$('#addCampaignError').css('display','none');
	$("#newListMessages").css("display","none");
	$('#newListError').css('display','none');
	$("#putMessagesNewList").html("msg_data_saved");
	$('#putErrorNewList').html('');
	$("#newlistName").val(""),
	$("#newPermissionReminder").val(""),
	$("#companyName").val(""),
	$("#companyCityName").val(""),
	$("#companyAddress").val(""),
	$("#companyCountry").val(""),
	$("#companyZip").val(""),
	$("#companyState").val(""),
	$("#newListFromName").val(""),
	$("#newListFromEmail").val(""),
	$("#newListSubject").val("");
	$('#email-list-modal').css('display','none');
	$('#create-list-modal').css('display','block');
});

function createList(){
	$('#newListError').css('display','none');
	$('#putErrorNewList').html('');
	var err="",rex=new RegExp('^\\w+[\\w._-]*@\\w+[\\w._-]*\\.+[\\w._-]{2,}$','g'),
		name=$("#newlistName").val(),
		permission=$("#newPermissionReminder").val(),
		company=$("#companyName").val(),
		address=$("#companyAddress").val(),
		city=$("#companyCityName").val(),
		zip=$("#companyZip").val(),
		state=$("#companyState").val(),
		country =$("#companyCountry").val(),
		from_name =$("#newListFromName").val(),
		from_email=$("#newListFromEmail").val(),
		subject=$("#newListSubject").val(),
		language=getLanguageURL();
	if(name==""){
		err=i18n('err_listname_empty');
	}else if(permission==""){
		err=i18n("err_permission_empty");
	}else if(company==""){
		err=i18n("err_company_empty");
	}else if(address==""){
		err=i18n("err_address_empty");
	}else if(city==""){
		err=i18n("err_city_empty");
	}else if(zip==""){
		err=i18n("err_zip_empty");
	}else if(state==""){
		err=i18n("err_state_empty");
	}else if(country==""){
		err=i18n("err_country_empty");
	}else if(from_name==""){
		err=i18n('err_from_name_empty');
	}else if(!rex.test(from_email)){
		err=i18n("err_enter_correct_email");
	}else if(from_email==""){
		err=i18n('err_enter_email');
	}else if(subject==""){
		err=i18n('err_subject_empty');
	}
	if(err!=""){
		$('#putErrorNewList').html(err);
		$('#newListError').css('display','block');
		return false;
	}else{
		$('#newListError').css('display','none');
		var param="name="+name+"&permission="+permission+"&company="+company+
			"&address="+address+"&city="+city+"&zip="+zip+"&state="+state+
			"&country="+country+"&from_name="+from_name+"&from_email="+from_email+
			"&subject="+subject+"&language="+language;
		$.ajax({
			type:"POST",
			url:ctx+"/createList.jet",
			data:param,
			async:false,
			success:function(data){
				if(data!=null){
					$("#putMessagesNewList").html("msg_data_saved");
					$("#newListMessages").css("display","block");

					fillSelect("selEditList","from=lists&query=name,id");
					$('#email-list-modal').css('display','none');
					$('#create-list-modal').css('display','block');
				}else{
					$('#putErrorNewList').html(i18n("err_unable_get_list")+"-Err.1");
					$('#newListError').css('display','block');
				}
			},error:function(e){
				$('#putErrorNewList').html(i18n("err_unable_get_list")+"-Err.2");
				$('#newListError').css('display','block');}
		});				
	}
};

$("#selEditList").change(function(){
	fillLists("allmem");
});

function fillLists(tabtarget){
	$('#'+tabtarget).css('display','block');
	$('#buttonsNewSubscriber').css('display','block');
	$('#editmem').css('display','none');
	$('#addCampaignError').css('display','none');
	$("#memberStatus").html('');
	$("#addMemberSubs").attr('data-target','#email-list-modal');
	$('#addcampaign-modal').css('display','none');
	$('#buttonsSaveSubscriber').css('display','none');
	$('#email-list-modal').css('display','block');
	var listid=$('#selEditList').val();
	var param="from=lists/"+listid+"/members&query=unique_email_id,email_address,merge_fields&typeList=detail";
	$.ajax({
		type:"POST",
		url:ctx+"/getList.jet",
		data:param,
		async:false,
		success:function(data){
			$("#tablemembers").remove();
			//Construcción de tabla incidencias y encabezados
			var leadTable="<table id='tablemembers' class='tableOut'><thead><tr class='trows'>"+
				"<th class='tac no-plus'></th>"+
				"<th class='tac'>"+i18n("msg_email")+"</th>"+
				"<th class='tac'>"+i18n("msg_fname")+"</th>"+
				"<th class='tac'>"+i18n("msg_lname")+"</th>"+
				"<th class='tac'>"+i18n("msg_address")+"</th>"+
				"<th class='tac'>"+i18n("msg_phone")+"</th>"+
				"<th class='tac'>"+i18n("msg_bday")+"</th>"+
				"<th class='tac'>"+i18n("msg_website")+"</th>" +
				"<th class='tac'>"+i18n("msg_status")+"</th>" +
				"<th class='tac'>"+i18n("msg_edit")+"</th>" +
				"<th class='tac'>"+i18n("msg_delete")+"</th>" +
				"</tr></thead><tbody>";
			if(data.length>0){
				//Inserta insidencias
				var item=1;
				for(var d=0; d<data.length; d+=3){
					var iddata=(data[d]).split("\|");
					var idmem=iddata[1];
					var emaildata=(data[d+1]).split("\|");
					var mergefields=(data[d+2]).replace("\"").split("\|");
					var merge=(mergefields[1]).split('\",\"'),fname="",lname="",address="",phone="",bday="",website="",status="";
					for(var v=0; v<merge.length; v++){
						var kval=merge[v];
						kval=kval.split('\":\"');
						var key=kval[0].replace("\"\"","\"").replace("undefined","");
						var val=kval[1].replace("\"\}").replace("\}").replace("\"\"","\"").replace("undefined","");
						if(key=="FNAME"){fname=val;
						}else if(key=="LNAME"){lname=val;
						}else if(key=="ADDRESS"){address=val;
						}else if(key=="PHONE"){phone=val;
						}else if(key=="BIRTHDAY"){bday=val;
						}else if(key=="WEBSITE"){website=val;
						}else if(key=="STATUS"){status=val;}
					}
					leadTable+="<tr class='trows'>"+
						"<td class='tac no-plus' data-label='Item'>"+(item)+"</td>"+
						"<td data-label='"+"email"+"'>"+emaildata[1]+"</td>"+
						"<td data-label='"+"fname"+"'>"+fname+"</td>"+
						"<td data-label='"+"lname"+"'>"+lname+"</td>"+
						"<td data-label='"+"address"+"'>"+address+"</td>"+
						"<td data-label='"+"phone"+"'>"+phone+"</td>"+
						"<td class='tac' data-label='"+"b-day"+"'>"+bday+"</td>"+
						"<td data-label='"+"website"+"'>"+website+"</td>"+
						"<td data-label='"+"status"+"'>"+status+"</td>"+
						"<td class='tac' data-label='"+i18n('btn_edit')+"'><a class='table-action-btn' onclick='editmemberdata(\""+
							listid+"\",\""+emaildata[1]+"\",\""+fname+"\",\""+lname+"\",\""+phone+"\",\""+address+"\",\""+bday+"\",\""+status+"\",\""+website+
							"\");' ><i class='md md-edit'></i></a></td>"+
						"<td class='tac' data-label='"+i18n('btn_delete')+
							"'><a class='table-action-btn' onclick='deletemember(\""+
							listid+"\",\""+emaildata[1]+"\",\""+idmem+
							"\");' ><i class='md md-close'></i></a></td></tr>";
					//"'><a class='table-action-btn' onclick='deletemember(\""+listid+"\",\""+emaildata[1]+"\",\""idmem+"\");' ><i class='md md-close'></i></a></td></tr>";
					//"'><a class='table-action-btn' onclick='deletemember(\""+idmem+"\");' ><i class='md md-close'></i></a></td></tr>";
					item++;
				}
			}
			leadTable+="</tbody></table>";
			$(leadTable).appendTo( "#"+tabtarget );
		},error:function(e){swal(i18n('msg_warning'),i18n('err_unable_get_list')+"(1)","error");}
	});
};

function sendTestModal(){
	var s1=xCtrl("send-campaign-modal").style,s2=xCtrl("test-campaign-modal").style;
	if(s1.display=="block"){
		s1.display="none";s2.display="block";
		$("#testCampaignName").text($("#sendCampaignName").text());
		try{$("#tableSelMemTest").html('');
			$("#tableSelMemTest").css('display','none');
		}catch(e){}
		fillSelect("selTestEmailList","from=lists&query=name,id");
	}else{
		s1.display="block";s2.display="none";
	}
};

$("#selTestEmailList").change(function(){
	var listid=$('#selTestEmailList').val();
	var param="from=lists/"+listid+"/members&query=unique_email_id,email_address,merge_fields&typeList=detail";
	$.ajax({
		type:"POST",
		url:ctx+"/getList.jet",
		data:param,
		async:false,
		success:function(data){
			$("#tableSelMemTest").remove();
			//Construcción de tabla incidencias y encabezados
			var leadTable="<table id='tableSelMemTest' class='tableOut'><thead><tr class='trows'>"+
				"<th class='tac no-plus'></th>"+
				"<th class='tac'>"+"email"+"</th>"+
				"<th class='tac'>"+"fname"+"</th>"+
				"<th class='tac'>"+"lname"+"</th>"+
				"<th class='tac'>"+"address"+"</th>"+
				"</tr></thead><tbody>";
			if(data.length>0){
				//Inserta insidencias
				var item=1;
				for(var d=0; d<data.length; d+=3){
					var iddata=(data[d]).split("\|");
					var idmem=iddata[1];
					var emaildata=(data[d+1]).split("\|");
					var mergefields=(data[d+2]).replace("\"").split("\|");
					var merge=(mergefields[1]).split('\",\"'),fname="",lname="",address="";
					for(var v=0; v<merge.length; v++){
						var kval=merge[v];
						kval=kval.split('\":\"');
						var key=kval[0].replace("\"\"","\"").replace("undefined","");
						var val=kval[1].replace("\"\}").replace("\}").replace("\"\"","\"").replace("undefined","");
						if(key=="FNAME"){fname=val;
						}else if(key=="LNAME"){lname=val;
						}else if(key=="ADDRESS"){address=val;}
					}
					leadTable+="<tr class='trows'>"+
						"<td style='cursor:pointer;' data-label='Item' class='tac no-plus' onclick='togglechk(\"mem"+d+"\");'><input type='checkbox' id='mem"+d+"'/></td>"+
						"<td style='cursor:pointer;' data-label='"+"email"+"' onclick='togglechk(\"mem"+d+"\");' name='sendtest' data-id='mem"+d+"'>"+emaildata[1]+"</td>"+
						"<td style='cursor:pointer;' data-label='"+"fname"+"' onclick='togglechk(\"mem"+d+"\");'>"+fname+"</td>"+
						"<td style='cursor:pointer;' data-label='"+"lname"+"' onclick='togglechk(\"mem"+d+"\");'>"+lname+"</td>"+
						"<td style='cursor:pointer;' data-label='"+"address"+"' onclick='togglechk(\"mem"+d+"\");'>"+address+"</td>"+
						"</tr>";
					item++;
				}
			}
			leadTable+="</tbody></table>";
			$(leadTable).appendTo( "#tosendtest" );
		},error:function(e){swal(i18n('msg_warning'),i18n('err_unable_get_list')+"(1)","error");}
	});
});

function togglechk(e){
	xCtrl(e).checked=(xCtrl(e).checked)?!1:!0;
	var allList=xCtrl(":sendtest");
	var emails="",maxtest=0;
	for(var i=0; i<allList.length; i++){
		var idch=xCtrl(allList[i].getAttribute("data-id"));
		if(idch.checked){
			emails+=allList[i].innerText+",";
			maxtest++;
			if(maxtest>6)
				alert("El env\u00edo de pruebas debe ser de un máximo de 6 direcciones")
		}
	}emails=emails.substring(0,emails.length-1);
};

$("#addNewMemberCancel").click(function(){
	$('#addcampaign-modal').css('display','block');
	$('#email-list-modal').css('display','none');
});

$("#editMemberCancel").click(function(){
	fillSelect('selEditList','from=lists&query=name,id');
	fillLists("allmem");
});

function editmemberdata(listid,email,name,lname,phone,address,bday,st,web){
	name=name||"";lname=lname||"";phone=phone||"";address=address||"";
	bday=bday||"";st=st||"";web=web||"";
	
	$('#addMemberError').css('display','none');
	$('#editmem').css('display','block');
	$('#buttonsNewSubscriber').css('display','none');
	$('#buttonsSaveSubscriber').css('display','block');
	$('#allmem').css('display','none');
	
	$('#memberEmail').val(email);
	$('#memberName').val(name);
	$('#memberLastName').val(lname);
	$('#memberPhone').val(phone);
	$('#memberAddress').val(address);
	$('#memberBirthday').val(bday);
	$('#memberWebsite').val(web);
	var status=[i18n("msg_subscribed"),i18n("msg_unsubscribed"),i18n("msg_non_subscribed"),i18n("msg_cleaned"),i18n("msg_pending")],
		valSt=["subscribed","unsubscribed","cleaned","non-subscribed","pending"],
		info=[i18n("msg_subscribed_info"),i18n("msg_unsubscribed_info"),i18n("msg_non_subscribed_info"),i18n("msg_cleaned_info"),
	         i18n("msg_pending_info")],sel=!0;
	$("#memberStatus").html('');
	for(var i in status){
		var option=new Option(status[i],valSt[i],!0,sel);
		$('#memberStatus').append(option);
		$(option).attr('title',info[i]);
		sel=!1;
	}
	if(st==""){
		$('#memberStatus').val("pending");
	}else{
		$('#memberStatus').val(st);
	}
}

function suscribeMember(){
	var err="",email=$('#memberEmail').val(),fname=$('#memberName').val(),
		rex=new RegExp('^\\w+[\\w._-]*@\\w+[\\w._-]*\\.+[\\w._-]{2,}$','g');
	if(email==""){
		err=i18n('err_enter_email');
	}else if(!rex.test(email)){	
		err=i18n("err_enter_correct_email");
	}else if(fname==""){
		err=i18n('err_name_empty');
	}
	if(err!=""){
		$('#putErrorMember').html(err);
		$('#addMemberError').css('display','block');
		return false;
	}else{
		var param="listid="+$('#selEditList').val()+"&email="+email+"&fname="+fname+"&lname="
			+$('#memberLastName').val()+"&phone="+($('#memberPhone').val()).replace(new RegExp("[^0-9]?","g"),"")
			+"&address="+$('#memberAddress').val()+"&birthday="+$('#memberBirthday').val()
			+"&status="+$('#memberStatus').val()+"&wesite="+$('#memberWebsite').val();
		$.ajax({
			type:'POST',
			url:ctx+"/suscribeMember.jet",
			data:param,
			async:false,
			success:function(data){
				if(data=="T"){
					$('#putMessageMember').html(i18n("msg_data_saved"));
					$('#addMemberMessages').css('display','block');
					$('#memberEmail').val("");
					$('#memberName').val("");
					$('#memberLastName').val("");
					$('#memberPhone').val("");
					$('#memberAddress').val("");
					$('#memberBirthday').val("");
					$('#memberStatus').val('subscribed');
				}else if(data==""){
					$('#putErrorMember').html(i18n("err_required_field"));
					$('#addMemberError').css('display','block');
				}else{
					$('#putErrorMember').html(i18n("err_record_no_saved"));
					$('#addMemberError').css('display','block');
				}
			}
		});
	}
};

function deletemember(listid,email,memid){
	var param="listid="+listid+"&email="+email+"&memid="+memid;
	$.ajax({
		type:"POST",
		url:ctx+"/deteleSuscriber.jet",
		data:param,
		async:false,
		success:function(data){
			if(data=="T"){
				alert("eliminado");
				/*$('#putTemplateError').html(i18n("err_template_empty"));
				$('#templateError').css('display','block');
			}else if(data=="c"){
				$('#putTemplateError').html(i18n("err_templatecode_empty"));
				$('#templateError').css('display','block');
			}else if(data=="1"){
				$('#upload-template-modal').modal('toggle');
				//window.location=ctx+"/campaigns.jet"+"?language="+getLanguageURL();
				fillSelect("seltemplateList","from=templates&query=name,id,type::user");
				swal(i18n("msg_success"),i18n("msg_campaign_saved_successfully"),"success");
				Custombox.close();*/
			}else{
				$('#putTemplateError').html(i18n("err_template_exists"));
				
				$('#templateError').css('display','block');
			}
		},error:function(e){swal(i18n("msg_error"),i18n("err_unable_get_list")+", err.1","error");}
	});
};

$("#uploadTemplate").click(function(){
	$('#upload-template-modal').modal('toggle');
});

$('#templateFile').on('change', function () {
    fileChosen(this,xCtrl('templateCode'));
});

function readTextFile(file,callback,encoding) {
	var reader = new FileReader();
    reader.addEventListener('load',function (e){
    	callback(this.result);
    });
    if(encoding)reader.readAsText(file, encoding);
    else reader.readAsText(file);
};

function fileChosen(input, output) {
	if (input.files && input.files[0])
		readTextFile(input.files[0],function (str){
			output.value = str;});
};

function createTemplate(){
	var templatename=$("#newTemplateName").val(),
		templatecode=$("#templateCode").val();
	if(templatename==''){
		$('#putTemplateError').html(i18n("err_template_empty"));
		$('#templateError').css('display','block');
		return false;
	}else if(templatecode==''){
		$('#putTemplateError').html(i18n("err_templatecode_empty"));
		$('#templateError').css('display','block');
		return false;
	}else{
		$('#templateError').css('display','none');
		var param="templatename="+templatename+"&templatecode="+encodeURI(templatecode);
		$.ajax({
			type:"POST",
			url:ctx+"/addTemplate.jet",
			data:param,
			async:false,
			success:function(data){
				if(data=="n"){
					$('#putTemplateError').html(i18n("err_template_empty"));
					$('#templateError').css('display','block');
				}else if(data=="c"){
					$('#putTemplateError').html(i18n("err_templatecode_empty"));
					$('#templateError').css('display','block');
				}else if(data=="1"){
					$('#upload-template-modal').modal('toggle');
					//window.location=ctx+"/campaigns.jet"+"?language="+getLanguageURL();
					fillSelect("seltemplateList","from=templates&query=name,id,type::user");
					swal(i18n("msg_success"),i18n("msg_campaign_saved_successfully"),"success");
					Custombox.close();
				}else{
					$('#putTemplateError').html(i18n("err_template_exists"));
					
					$('#templateError').css('display','block');
				}
			},error:function(e){swal(i18n("msg_error"),i18n("err_unable_get_campaign")+", err.1","error");}
		});				
	}		
};

//********************************Cambiar de acuerdo a campos de campa\u00f1a****************************
$("#search").keypress(function( event ){
	if( event.which==13 ){
		searchCampaigns();
	}							
});

function searchCampaigns(){
	var search=$("#search").val();
	if(search==''){
		swal(i18n("msg_error"),i18n("err_enter_text2search"),"error");
		return false;
	}
	var param="search="+search;
	$.ajax({
		type:'POST',
		url:ctx+'/search.jet',
		data:param,
		async:false,
		success:function(result){
			$("#pagination").css('display', 'none');
			$("tr:has(td)").remove();
			$.each(result,function(i, item){
				$('#campaignList').append(
				'<tr class=""><td><div class="checkbox checkbox-primary m-r-15"><input id="checkbox2" type="checkbox" ><label for="checkbox2"></label></div>'
					+'<img src="resources/assets/images/users/avatar-2.jpg" alt="contact-img" title="contact-img" class="img-circle thumb-sm" /></td><td>'
					+ item.campaignid
					+ '</td><td>'
					+ item.campaignname
					+ '</td><td>'
					+(item.status==1?'<span class="label label-success">Active</span>':'<span class="label label-danger">Deactive</span>')+'</td><td>'
					+'<a href="#" onclick="editCampaignDetails(\''+item.id+'\',\''+item.campaignname+'\');" class="table-action-btn" data-toggle="modal" data-target="#edit-campaign-modal">'
					+'<i class="md md-edit"></i></a>'
					+'<a href="#" onclick="deleteCampaignDetails(\''+item.id+'\',\''+item.campaignname+'\');" class="table-action-btn" data-toggle="modal" data-target="#delete-campaign-modal">'
					+'<i class="md md-close"></i></a></td></tr>'
				);
			});
		},error:function(ex){swal(i18n("msg_error"),i18n("err_unable_get_campaign")+", err.4","error");}
	});
	return false;
};

function i18n(msg){
	var i18n_message="";
	//IDIOMAS:si en la URL no contiene la variable "lenguage", asumirá que esta en inglés
	lang=getLanguageURL();
	if(msg=="btn_create_list"){
		if(lang=="es"){i18n_message="Crear nueva lista";
		}else{i18n_message="Create new list";}
	}else if(msg=="btn_delete"){
		if(lang=="es"){i18n_message="Eliminar";}
		else{i18n_message="Delete";}
	}else if(msg=="btn_edit"){
		if(lang=="es"){i18n_message="Editar";}
		else{i18n_message="Edit";}
	}else if(msg=="btn_yes_send_it"){
		if(lang=="es"){i18n_message="\u00a1S\u00ed, enviar!";
		}else{i18n_message="Yes, send it!";}	
	}else if(msg=="btn_yes_delete_it"){
		if(lang=="es"){i18n_message="\u00a1S\u00ed, eliminar!";
		}else{i18n_message="Yes, delete it!";}

	}else if(msg=="err_address_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el domicilio de la compa\u00f1ia!";}
		else{i18n_message="Please enter the company address!";}
	}else if(msg=="err_campaign_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el nombre de la campa\u00f1a!";}
		else{i18n_message="Please enter a campaign name!";}
	}else if(msg=="err_city_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar la ciudad de la compa\u00f1ia!";}
		else{i18n_message="Please enter the company city!";}
	}else if(msg=="err_company_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el nombre de tu comap\u00f1ia!";}
		else{i18n_message="Please enter your company name!";}
	}else if(msg=="err_country_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el pa\u00eds de la compa\u00f1ia!";}
		else{i18n_message="Please enter the company country!";}
	}else if(msg=="err_enter_correct_email"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar una direcci\u00f3n de correo electr\u00f3nico v\u00e1lida!";}
		else{i18n_message="Please enter correct email address!";}
	}else if(msg=="err_enter_email"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar un correo electr\u00f3nico!";}
		else{i18n_message="Please enter email!";}
	}else if(msg=="err_from_name_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el nombre del remitente!";}
		else{i18n_message="Please enter a sender name!";}
	}else if(msg=="err_listname_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar un nombre de lista!";}
		else{i18n_message="Please enter a list name!";}
	}else if(msg=="err_name_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el nombre!";}
		else{i18n_message="Please enter a name!";}
	}else if(msg=="err_permission_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar un recordatorio a los usuarios por estar suscritos!";}
		else{i18n_message="Please enter a reminder to users because of their subscribe!";}
	}else if(msg=="err_record_no_saved"){
		if(lang=="es"){i18n_message="\u00a1Fallo al guardar los datos!";}
		else{i18n_message="Fail to save the data!";}
	}else if(msg=="err_required_field"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar los datos indicados con asteriscos!";}
		else{i18n_message="Please enter data indicated by asterisks!";}
	}else if(msg=="err_select_campaign"){
		if(lang=="es"){i18n_message="\u00a1Favor de seleccionar una campa\u00f1a!";}
		else{i18n_message="Please enter select a campaign!";}
	}else if(msg=="err_sendig_campaign"){
		if(lang=="es"){i18n_message="\u00a1Error al enviar la campa\u00f1a!";}
		else{i18n_message="Error on sending the campaign!";}
	}else if(msg=="err_state_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el estado de la compa\u00f1ia!";}
		else{i18n_message="Please enter the company state!";}
	}else if(msg=="err_subject_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar un asunto por default!";}
		else{i18n_message="Please enter a default subject!";}
	}else if(msg=="err_templatecode_empty"){
		if(lang=="es"){i18n_message="\u00a1El c\u00f3digo HTML no debe estar vac\u00edo!";}
		else{i18n_message="HTML code must not be empty!";}
	}else if(msg=="err_template_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el nombre del plantilla!";}
		else{i18n_message="Please enter a template name!";}
	}else if(msg=="err_unable_get_list"){
		if(lang=="es"){i18n_message="\u00a1No fue posible obtener el listado!";}
		else{i18n_message="Unable to get list data!";}
	}else if(msg=="err_unable_get_campaign"){
		if(lang=="es"){i18n_message="\u00a1No fue posible obtener informaci\u00f3n de la campa\u00f1a!";}
		else{i18n_message="Unable to get campaign information!";}
	}else if(msg=="err_unable_get_templates"){
		if(lang=="es"){i18n_message="\u00a1No fue posible obtener las plantillas!";}
		else{i18n_message="Unable to get templates!";}
	}else if(msg=="err_zip_empty"){
		if(lang=="es"){i18n_message="\u00a1Favor de ingresar el c\u00f3digo postal de la compa\u00f1ia!";}
		else{i18n_message="Please enter the company zip code!";}
		
	}else if(msg=="msg_address"){
		if(lang=="es"){i18n_message="Direcci\u00f3n";}
		else{i18n_message="Address";}
	}else if(msg=="msg_are_you_sure"){
		if(lang=="es"){i18n_message="\u00bfEsta seguro?";}
		else{i18n_message="Are you sure?";}
	}else if(msg=="msg_bday"){
		if(lang=="es"){i18n_message="Cumplea\u00f1os";}
		else{i18n_message="Birthday";}
	}else if(msg=="msg_campaign_deleted"){
		if(lang=="es"){i18n_message="\u00bfCampa\u00f1a eliminada!";}
		else{i18n_message="Campaign deleted!";}
	}else if(msg=="msg_campaign_not_deleted"){
		if(lang=="es"){i18n_message="\u00a1Campa\u00f1a no eliminada!";}
		else{i18n_message="Campaign not deleted!";}
	}else if(msg=="msg_campaign_not_sent"){
		if(lang=="es"){i18n_message="\u00a1Campa\u00f1a no enviada!";}
		else{i18n_message="Campaign not sent!";}
	}else if(msg=="msg_campaign_sent"){
		if(lang=="es"){i18n_message="\u00bfCampa\u00f1a enviada!";}
		else{i18n_message="Campaign sent!";}
	}else if(msg=="msg_cleaned"){
		if(lang=="es"){i18n_message="Sin respuesta";}
		else{i18n_message="Cleaned";}
	}else if(msg=="msg_cleaned_info"){
		if(lang=="es"){i18n_message="Correo no entregable y que ha tenido rechazos o rebotes por alguna causa";}
		else{i18n_message="A cleaned contact has a non-deliverable email address, which might be misspelled or invalid";}
	}else if(msg=="msg_data_saved"){
		if(lang=="es"){i18n_message="\u00a1Datos almacenados exitosamente!";}
		else{i18n_message="Data saved successfully!";}
	}else if(msg=="msg_delete"){
		if(lang=="es"){i18n_message="Eliminar";}
		else{i18n_message="Delete";}
	}else if(msg=="msg_delete_list_advice"){
		if(lang=="es"){i18n_message="\u00a1Esto elimina todos los datos de la lista, incluidas las cancelaciones de suscripci\u00f3n, las quejas de abuso y los rebotes. Si vuelve a importar los contactos de la lista y los env\u00eda m\u00e1s tarde, puede resultar en altos \u00edndices de rebote y quejas de abuso que pueden llevar a la suspensi\u00f3n de la cuenta.!";}
		else{i18n_message="This deletes all list data, including unsubscribes, abuse complaints, and bounces. If you re-import the list contacts and send to them later, it may result in high bounce rates and abuse complaints that can lead to account suspension.";}
	}else if(msg=="msg_deleted"){
		if(lang=="es"){i18n_message="\u00a1Eliminado!";}
		else{i18n_message="Deleted!";}
	}else if(msg=="msg_edit"){
		if(lang=="es"){i18n_message="Editar";}
		else{i18n_message="Edit";}
	}else if(msg=="msg_email"){
		if(lang=="es"){i18n_message="Email";}
		else{i18n_message="Email";}
	}else if(msg=="msg_error"){
		if(lang=="es"){i18n_message="\u00a1Error!";}
		else{i18n_message="Error!";}
	}else if(msg=="msg_fname"){
		if(lang=="es"){i18n_message="Nombre";}
		else{i18n_message="First name";}
	}else if(msg=="msg_lname"){
		if(lang=="es"){i18n_message="Apellido";}
		else{i18n_message="Last name";}
	}else if(msg=="msg_non_subscribed"){
		if(lang=="es"){i18n_message="No subscrito";}
		else{i18n_message="Non-subscribed";}
	}else if(msg=="msg_non_subscribed_info"){
		if(lang=="es"){i18n_message="Alguien que ha interactuado en una tienda online pero no se ha suscrito para recibir las campa\u00f1as";}
		else{i18n_message="Someone who has interacted with your online store, but hasn’t opted in to receive email marketing campaigns";}
	}else if(msg=="msg_pending"){
		if(lang=="es"){i18n_message="Pendiente";}
		else{i18n_message="Pending";}
	}else if(msg=="msg_pending_info"){
		if(lang=="es"){i18n_message="\u00a1Pendiente por clasificar!";}
		else{i18n_message="Pending to classify!";}
	}else if(msg=="msg_phone"){
		if(lang=="es"){i18n_message="Tel\u00e9fono";}
		else{i18n_message="Phone";}
	}else if(msg=="msg_record_deleted"){
		if(lang=="es"){i18n_message="\u00a1Registro eliminado!";}
		else{i18n_message="Record deleted!";}
	}else if(msg=="msg_record_safe"){
		if(lang=="es"){i18n_message="Tu registro esta seguro";}
		else{i18n_message="Your record is safe:)";}
	}else if(msg=="msg_select_option"){
		if(lang=="es"){i18n_message="Elegir...";}
		else{i18n_message="Select...";}
	}else if(msg=="msg_select_one_more_options"){
		if(lang=="es"){i18n_message="Elige una o m\u00e1s opciones";}
		else{i18n_message="Select one or more options";}
	}else if(msg=="msg_no_sent"){
		if(lang=="es"){i18n_message="No env\u00edada";}
		else{i18n_message="No sent";}
	}else if(msg=="msg_sent_on"){
		if(lang=="es"){i18n_message="Fecha de env\u00edo";}
		else{i18n_message="Sent on";}
	}else if(msg=="msg_status"){
		if(lang=="es"){i18n_message="Estatus";}
		else{i18n_message="Status";}
	}else if(msg=="msg_subscribed"){
		if(lang=="es"){i18n_message="Subscrito";}
		else{i18n_message="Subscribed";}
	}else if(msg=="msg_subscribed_info"){
		if(lang=="es"){i18n_message="Persona que ha permitido recibir tus campa\u00f1as";}
		else{i18n_message="Someone who has opted in to receive your email marketing";}
	}else if(msg=="msg_success"){
		if(lang=="es"){i18n_message="\u00a1Correcto!";}
		else{i18n_message="Success!";}
	}else if(msg=="msg_sure_to_delete"){
		if(lang=="es"){i18n_message="\u00bfDesea esta campa\u00f1a?";}
		else{i18n_message="Are you sure to delete this campaign?";}
	}else if(msg=="msg_sure_to_send"){
		if(lang=="es"){i18n_message="Una vez enviada la campa\u00f1a no se podr\u00e1 detener o cancelar, \u00bfdesea continuar?";}
		else{i18n_message="Once the campaign is sent, you can't stop or cancel it, do you want to continue?";}
	}else if(msg=="msg_unsubscribed"){
		if(lang=="es"){i18n_message="Inactivo";}
		else{i18n_message="Unsubscribed";}
	}else if(msg=="msg_unsubscribed_info"){
		if(lang=="es"){i18n_message="Persona que opt\u00f3 por recibir tus campa\u00f1as pero esta inactivo actualmente";}
		else{i18n_message="Someone who used to receive your email marketing but has opted out";}
	}else if(msg=="msg_warning"){
		if(lang=="es"){i18n_message="\u00a1Advertencia!";}
		else{i18n_message="Warning!";}
	}else if(msg=="msg_website"){
		if(lang=="es"){i18n_message="Sitio web";}
		else{i18n_message="Website";}
	}return i18n_message;
}