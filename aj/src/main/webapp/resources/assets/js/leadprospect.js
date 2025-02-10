var u=location.pathname, lpPage=(u.indexOf("opportunity")>0)?"opportunity":"leadprospect";

$("#addCompanyId").click(function(){
	$('#addUserErrorLead').css('display','none');
	$("#addCompanyId").attr('data-target','#add-company-modal');
});

$("#addCompanyId1").click(function(){
	$('#addUserErrorLead').css('display','none');
	$("#addCompanyId1").attr('data-target','#add-company-modal');
});

$("#addContactId").click(function() {
	$('#addUserErrorLead').css('display', 'none');
	$("#addContactId").attr('data-target', '#add-contact-modal');
});

$("#addResourceId").click(function() {
	$('#addUserErrorLead').css('display', 'none');
	$("#addResourceId").attr('data-target', '#add-source-modal');
});

$("#addResourceId1").click(function() {
	$('#addUserErrorLead').css('display', 'none');
	$("#addResourceId1").attr('data-target', '#add-source-modal');
});

$("#addContactId1").click(function() {
	$('#addUserErrorLead').css('display', 'none');
	$("#addContactId1").attr('data-target', '#edit-contact-modal');
	var companyValue = $('#idlead1').val();
	if (companyValue == (i18n('msg_select_company'))) {
		$('#putEditOppError').html(i18n('err_enter_companyname'));
		$('#editOppError').css('display', 'block');
		return false;
	}
});

$("#addContactId1").click(function() {
	$("#addContactId1").attr('data-target', '#add-contact-modal');
	var companyValue = $('#selectcompany1 option:selected').text();
	if (companyValue == (i18n('msg_select_company'))) {
		$('#putEditOppError').html(i18n('err_enter_companyname'));
		$('#editOppError').css('display', 'block');
		return false;
	}
});

$("#addNewOpportunityId").click(function() {
	$("#addNewOpportunityId").attr('href', '#lead-prospect-modal');
	getCompanyNames("selectcompany");
	getCurrency("selectcurrency");
	getSourceNames("selectsource","");
	getDefaultWorkflow();
	$('select[name="selectCompany"]:first').val('0');
	$('select[name="selectContact"]:first').val('0');
	$('#selectstatus').val('1');
	$('select[name="selectworkflow"]:first').val('0');	
	setTimeout(function(){ $( "#lead" ).focus(); },1500);
});

$("#selectcontact").change(function() {
	var companyValue = $('#selectcompany option:selected').val();
	if (companyValue == '0') {
		$('#putErrorLead').html(i18n('err_enter_companyname'));
		$('#addUserErrorLead').css('display', 'block');
		$('select[name="selectcontact"]:first').val('0');
	}
});

/*
$("#selectcompany").change(function() { // Select contacts on change of company
	var companyValue = $('#selectcompany option:selected').val();
	if (companyValue == '0') {
		$('#putErrorLead').html(i18n('err_enter_companyname'));
		$('#addUserErrorLead').css('display', 'block');
		$('select[name="selectcontact"]:first').val('0');
	} else {
		$('#addUserErrorLead').css('display', 'none');
		getContactNames(companyValue, "selectcontact", '');
	}
});

$("#selectcompany1").change(function() {
	var companyValue = $('#selectcompany1 option:selected').val();
	if (companyValue == '0') {
		$('#putEditOppError').html(i18n('err_select_company'));
		$('#editOppError').css('display', 'block');
		$('select[name="selectcontact1"]:first').val('0');
	} else {
		getContactNames(companyValue, "selectcontact1", '');
	}
});*/

function addCompany(){
	var companyName=$('#companyName').val();
	var rfc=$('#rfc').val();
	if(companyName==""){
		$('#putErrorCompany').html(i18n('err_enter_companyname'));
		$('#addCompanyError').css('display','block');
		return false;
	}else {
		var param="companyName="+companyName;
		$.ajax({
			type:"POST",
			url:ctx+"/getcompanycrmbyname.jet",
			async:false,
			data :param,
			success:function(data) {
				result = data;
			}
		});
		if(result!=0){
			$('#putErrorCompany').html(i18n('err_duplicate_companyname'));
			$('#addCompanyError').css('display','block');
			return false;
		}
	
	}
	
	var param="company="+companyName+"&rfc="+rfc;
	$.ajax({
		type :"POST",
		url  :ctx+"/addNewCompany.jet",
		data :param,
		async:false,
		success:function(data){
			if(data=="rfc_duplicado"){
				swal(i18n('Warning!'),i18n('err_record_no_saved_rfc_duplicate'),"error");
			}else if(data=="false"){
				swal(i18n('Warning!'),i18n('err_company_no_saved'),"error");
			}else{
				$('#add-company-modal').modal('toggle');
				$("#selectcompany").removeClass("bcfff");
				$("#selectcompany1").removeClass("bcfff");
				$("#selectcompany").addClass("added",4000);
				$("#selectcompany1").addClass("added",4000);
				$('#companyName').val("");
				$('#rfc').val("");
				getCompanyNames("selectcompany",data);
				getCompanyNames("selectcompany1",data);
				setTimeout(function(){
				},4000);
				$("#selectcompany").removeClass("added");
			    $("#selectcompany1").removeClass("added");
			    $("#selectcompany").addClass("bcfff");
			    $("#selectcompany1").addClass("bcfff");
				
			    
			    $("#lead").val(companyName);
			    $("#idlead").val(data);
			    $("#dbred").val(0);
			    $("#ls_query").val(companyName);
			}
		},error:function(e){swal(i18n('Warning!'),i18n('err_company_no_saved'),"error");}
	});
};

$("#addNewCompanyCancel").click(function() {
	$('#addCompanyError').css('display', 'none');
	$('#companyName').val("");
	$("#username").val("");
	$("#password").val("");
	$("#email").val("");
});

function getCompanyNames(ctrl,idSelected) {
	$.ajax({
		type:"POST",
		url:ctx+"/getAllCompanyList.jet",
		async:false,
		success:function(data) {
			$("#"+ctrl).html('');
			var listItems='<option value="0" selected disabled>'+i18n('msg_select_company') + '</option>';
			$.each(data,function(idx,obj){
				if(idSelected==obj.id)
					listItems+='<option value="'+obj.id+'" selected="selected">'+obj.name+'</option>';
				else
					listItems+='<option value="'+obj.id+'">'+obj.name+'</option>';
			});
			$('#'+ctrl).append(listItems);
			$("#selectcompany1").html('');
			$('#selectcompany1').append(listItems);
		},error:function(data){
			$('#putErrorCompany').html(i18n('err_unable_get_company'));
			$('#addCompanyError').css('display', 'block');
		}
	});
};

function getCurrency(ctrl, idSelected) {
	$.ajax({
		type : "POST",
		url : ctx + "/getCurrency.jet",
		async : false,
		success : function(data) {
			$("#" + ctrl).html('');
			var listItems = '<option value="0" selected disabled>'
					+ i18n('msg_select_currency') + '</option>';
			$.each(data, function(idx, obj) {
				if(idSelected == obj.currencyid)
					listItems += '<option value="' + obj.currencyid + '" selected="selected">' + obj.currency+ '</option>';
				else
					listItems += '<option value="' + obj.currencyid + '">' + obj.currency+ '</option>';
			});
			$('#' + ctrl).append(listItems);
			//$("#selectcompany1").html('');
			//$('#selectcompany1').append(listItems);
		},
		error : function(data) {
			//$('#putErrorCompany').html(i18n('err_unable_get_company'));
			//$('#addCompanyError').css('display', 'block');
		}
	});
};

function getDefaultWorkflow() {
	$.ajax({
		type : "POST",
		url : ctx + "/getdefaultworkflow",
		async : false,
		success : function(data) {
			$('#workflowdefault').val(data.workflowid);
		},
		error : function(data) {
			//$('#putErrorCompany').html(i18n('err_unable_get_company'));
			//$('#addCompanyError').css('display', 'block');
		}
	});
};

function addContact() {
	var param="",contactAddress=contactAddress=$('#contactAddress').val(),companyValue=$('#idlead').val(),dbred=$('#dbred').val(), companyName=$('#ls_query').val();
	if((typeof dbred=='undefined')||(dbred=='')||(dbred==null)){
		dbred='';
		if($('#dbred1').val() != undefined)
			dbred=$('#dbred1').val();
	}
	if((typeof companyValue=='undefined')||(companyValue=='')||(companyValue==null)){
		companyValue='';
		if($('#idlead1').val() != undefined)
		companyValue=$('#idlead1').val();
	}
	
	if((typeof companyName=='undefined')||(companyName=='')||(companyName==null)){
		companyName='';
		if($('#lead1').val() != undefined)
		companyName=$('#lead1').val();
	}
	
	if(companyValue ==='' && companyName ===''){
		$('#putErrorContact').html(i18n('err_enter_companyname'));
		$('#addContactError').css('display','block');
		return false;
	}
	
	param="contactAddress="+contactAddress+"&companyValue="+companyValue+"&companyName="+companyName+"&dbred="+dbred;
	if(contactAddress==""){
		swal(i18n('msg_warning'),i18n('err_provide_address'),"warning");
	}else{
		$.ajax({
			type:"POST",
			url:ctx+"/addcontact.jet",
			data:param,
			async:false,
			success:function(data){
				var isred = 1;
				if(data.correcto=="false"){
					swal("Warning!",i18n("err_record_no_saved"),"Error");
				}else{
					if(data.idcompany > 0){
						$('#lead').val(data.companyName);
						$('#lead1').val(data.companyName);
						$('#idlead').val(data.idcompany);
						$('#idlead1').val(data.idcompany);
						isred = 0;
						companyValue = data.idcompany;
					}
					$('#dbred').val(isred);
					
					$('#add-contact-modal').modal('toggle');
					$("#selectcontact").removeClass("bcfff");
					$("#selectcontact1").removeClass("bcfff");
					$("#selectcontact").addClass("added",4000);
					$("#selectcontact1").addClass("added",4000);
					$('#contactAddress').val("");
					getContactNames(companyValue, "selectcontact", data.idcontacto, isred);
					getContactNames(companyValue, "selectcontact1", data.idcontacto, isred);
					setTimeout(function(){
					},4000);
					$("#selectcontact").removeClass("added");
				    $("#selectcontact1").removeClass("added");
				    $("#selectcontact").addClass("bcfff");
				    $("#selectcontact1").addClass("bcfff");
				}
			},error:function(e){swal("Warning!",i18n("err_record_no_saved"),"Error");}
		});
	}
};

function getContactNames(companyValue, ctrl, idSelected, dbRed) {
	var param = "companyValue=" + companyValue+"&dbred="+dbRed;
	$.ajax({
		type : 'POST',
		url : ctx + '/selectcontact.jet',
		data : param,
		async : false,
		success : function(data) {
			$("#" + ctrl).html('');
			var listItems = '<option value="0" selected disabled>' + i18n('msg_select_contact') + '</option>';
			if(dbRed === 1){
				$.each(data, function(idx, obj) {
					if(idSelected == obj.contactid)
						listItems += '<option value="' + obj.contactid + '" selected="selected">' + obj.name+ '</option>';
					else
						listItems += '<option value="' + obj.contactid + '">' + obj.name+ '</option>';
					
				});
			}else{
				$.each(data, function(idx, obj) {
					if(idSelected == obj.id)
						listItems += '<option value="' + obj.id + '" selected="selected">' + obj.address+ '</option>';
					else
						listItems += '<option value="' + obj.id + '">' + obj.address+ '</option>';
					
				});
			}
			$("#" + ctrl).append(listItems);
			return false;
		},
		error : function(e) {
			swal("Warning!", i18n("err_unable_get_company"), "Error");
		}
	});
};

function addSource() {
	var param="",source=$('#source').val();
	param="source="+source;
	if(source==""){
		swal(i18n('msg_warning'),i18n('err_provide_source'),"warning");
	}else{
		$.ajax({
			type:"POST",
			url:ctx+"/addsource.jet",
			data:param,
			async:false,
			success:function(data){
				if(data=="false"){
					swal("Warning!",i18n("err_record_no_saved"),"Error");
				}else{
					$('#add-source-modal').modal('toggle');
					$("#selectsource").removeClass("bcfff");
					$("#selectsource1").removeClass("bcfff");
					$("#selectsource").addClass("added",4000);
					$("#selectsource1").addClass("added",4000);
					$('#source').val("");
					getSourceNames("selectsource", data);
					getSourceNames("selectsource1", data);
					setTimeout(function(){
					},4000);
					$("#selectsource").removeClass("added");
				    $("#selectsource1").removeClass("added");
				    $("#selectsource").addClass("bcfff");
				    $("#selectsource1").addClass("bcfff");
				}
			},error:function(e){swal("Warning!",i18n("err_record_no_saved"),"Error");}
		});
	}
};

function getSourceNames(ctrl, idSelected) {
	$.ajax({
		type : 'POST',
		url : ctx + '/selectsource.jet',
		async : false,
		success : function(data) {
			$("#" + ctrl).html('');
			var listItems = '<option value="0" selected disabled>'
					+ i18n('msg_select_source') + '</option>';
			$.each(data, function(idx, obj) {
				if(idSelected == obj.opportunitysourceid)
					listItems += '<option value="' + obj.opportunitysourceid + '" selected="selected">' + obj.source+ '</option>';
				else
					listItems += '<option value="' + obj.opportunitysourceid + '">' + obj.source+ '</option>';
				
			});
			$("#" + ctrl).append(listItems);
			return false;
		},
		error : function(e) {
			swal("Warning!", i18n("err_unable_get_company"), "Error");
		}
	});
};

$("input[id^='rfc']").blur(function() {
	var thisid = $(this).attr("id");
	var rfc = $(this).val();
	var param='rfc='+rfc;
	
	if(rfc!= ''){
		$.ajax({
			type:"POST",
			url:ctx+"/findRfc.jet",
			data:param,
			async:false,
			success:function(data){
				if(data=="true"){
					$('#putErrorCompany').html(i18n('err_record_no_saved_rfc_duplicate'));
					$('#addCompanyError').css('display','block');
					return false;
				}else{
					$('#putErrorCompany').html('');
					$('#addCompanyError').css('display', 'none');
				}
			},error:function(e){
				
			}
		});
	}
});

function addOpportunity(){
	//var selSalesP=$('#selectstatus option:selected').val();
	var err="",lead=$('#lead').val(), idlead=$('#idlead').val(),leadInfo=$('#leadInfo').val(),source="",selectcompany="",selectcontact="",
		selectstatus=1,selectworkflow="",quantification=0,qualifiedcheck="false",product="",comment="",
		selectcurrency="", wfprocessstageid="", selectsubstatus="", workflowdefault=$('#workflowdefault').val(),
		dbred = $('#dbred').val(), lsquery = $('#ls_query').val(), leadprospect = $('#leadprospect').val();
	
	$('#addUserErrorLead').css('display','block');
	
	if(leadprospect===undefined)
		leadprospect=0;
	
	if(leadprospect==='1')
	{
		lead = lsquery;
		if(leadInfo=='')
			leadInfo = lsquery;
	}
	if(lead==""){
		err=i18n('err_enter_lead');
	}else if(leadInfo==""){
		err=i18n('err_enter_leadinfo');
	}
	if(idControl('additionalInfo').style.display=="block"){
		var pattern=/^\d+(\.\d{1,2})?$/,
			source=$('#selectsource').val(),
			quantification=$('#quantification').val(),
			quantification=quantification.replace(',',''),
			qualifiedcheck=$('#qualifiedcheck').is(":checked"),
			product=$('#product').val(),
			comment=$('#commentArea').val(),
			selectcompany='',
			selectcontact=$('#selectcontact option:selected').val(),
			selectstatus=$('#selectstatus option:selected').val(),
			selectsubstatus=$('#selectsubstatus option:selected').val(),
			selectworkflow=$('#selectworkflow option:selected').val(),
			selectcurrency=$('#selectcurrency option:selected').val();
			wfprocessstageid = $('input[name=wfprocessstageid]:checked').val();
		if( ((idlead=="")||(idlead=="0")) || lsquery==''){
			err=i18n('err_select_company');
		}else if((selectcontact=="")||(selectcontact=="0")){
			err=i18n('err_select_contact');
		}else if(source==""){
			err=i18n('err_enter_source');
		}else if(quantification==""){
			err=i18n('err_enter_quantification');
		}else if(!pattern.test(quantification)){
			err=i18n('err_enter_quantdecimal');
		}else if(selectcurrency==""){
			err=i18n('err_select_currency');
		}else if(product==""){
			err=i18n('err_enter_product');
		}else if((selectstatus=="")||(selectstatus==0)){
			err=i18n('err_select_status');
		/*}else if((selectsubstatus=="")||(selectsubstatus==0)){
			err=i18n('err_select_substatus');*/
		}else if((selectworkflow=="")||(selectworkflow=="0")){
			err=i18n('err_select_workflow');}
	}if(err!=""){
		$('#putErrorLead').html(err);
		return false;
	}$('#addUserErrorLead').css('display','none');
	
	if(selectsubstatus=='undefined')
		selectsubstatus ='';
	var param='lead='+lead+"&idlead="+idlead+"&leadInfo="+leadInfo+"&selectcompany="+selectcompany+"&selectcontact="+selectcontact
		+"&source="+source+"&selectstatus="+selectstatus+"&quantification="+quantification+"&qualifiedcheck="+qualifiedcheck
		+"&product="+product+"&selectworkflow="+selectworkflow+"&comment="+comment+"&selectsubstatus="+selectsubstatus
		+ "&currency="+selectcurrency+"&wfprocessstageid="+wfprocessstageid
		+"&workflowdefault="+workflowdefault+"&dbred="+dbred+"&leadprospect="+leadprospect;// +"&sales="+selSalesP;
	$.ajax({
		type:"POST",
		url:ctx+"/addopportunity.jet",
		data:param,
		async:false,
		success:function(data){
			if(data=="true"){
				swal({
					title:i18n('msg_success'),
					text :i18n('msg_data_saved'),
					type :"success",
					html :true,
					timer:3000,
					allowEscapeKey:false
				},function(){
					swal.close();
				});
				$('#lead-prospect-modal').modal('toggle');
				location.reload();
			}else {
				$('#putErrorLead').html(i18n('err_record_no_saved'));
				$('#addUserErrorLead').css('display','block');
			}
		},error:function(e){
			$('#putErrorLead').html(i18n('err_record_no_saved'));
			$('#addUserErrorLead').css('display','block');
		}
	});
};

$("#addOpportunityCancel").click(function() {
	$('#addUserErrorLead').css('display', 'none');
	$('#lead').val("");
	$('#leadInfo').val("");
	$('#selectcompany').val("0");
	$('#selectcontact').val("0");
	$('#source').val("");
	$('#selectstatus').val("0");
	$('#quantification').val("");
	$('#product').val("");
	$('#selectworkflow').val("0");
	$('#commentArea').val("");
});

$("#addOpportunityCancel2").click(
	function(){
		window.location=ctx+"/"+lpPage+".jet"+"?language="+getLanguageURL();
	}
);

$("#additionalInfoBtn").click(function() {
	$('#addUserErrorLead').css('display', 'none');
	$('#putErrorLead').html('');
	if (idControl('additionalInfo').style.display == "none") {
		$('#additionalInfo').css('display', 'block');
		$('.custombox-modal-container').removeAttr('margin-top');
		$('.custombox-modal-container').css('margin-top', '5% !important');
		$('#leadOpph4').html(i18n('btn_add_opportunity'));
		$('#additionalInfoBtn').html(i18n('btn_add_prospect'));
	} else {
		$('#leadOpph4').html(i18n('btn_add_prospect'));
		$('#additionalInfoBtn').html(i18n('btn_add_opportunity'));
		$('.custombox-modal-container').removeAttr('margin-top');
		$('.custombox-modal-container').css('margin-top', '18% !important');
		$('#additionalInfo').css('display', 'none');
		/*$('#selectcompany').val("0");
		$('#selectcontact').val("0");
		$('#source').val("");
		$('#selectstatus').val("0");
		$('#quantification').val("");
		$('#product').val("");
		$('#selectworkflow').val("0");
		$('#commentArea').val("");
		*/
	}
});

/* limpia inputs*/
$('#lead-prospect-modal').on('hidden.bs.modal', function (e) {
	$(this).find("input,textarea")
            .val('').end()
            .find("input[type=checkbox], input[type=radio]")
            .prop("checked", "").end();
	
});

$("#editAdditionalInfoBtn").click(function() {
	$('#editOppError').css('display', 'none');
	$('#putEditOppError').html('');
	if (idControl('editAdditionalInfo').style.display == "none") {
		$('#editAdditionalInfo').css('display', 'block');
		$('#editLeadOpph4').html(i18n('btn_add_opportunity'));
		$('#editAdditionalInfoBtn').html(i18n('msg_only_edit_lead'));
	} else {
		$('#editLeadOpph4').html(i18n('btn_edit_prospect'));
		$('#editAdditionalInfoBtn').html(i18n('btn_add_opportunity'));
		$('#editAdditionalInfo').css('display', 'none');
		$('#selectcompany1').val("0");
		$('#selectcontact1').val("0");
		$('#selectsource1').val("0");
		$('#selectstatus1').val("0");
		$('#quantification1').val("");
		$('#selectsubstatus1').val($("#selectsubstatus1 option:first").val());
		$('#product1').val("");
		$('#selectworkflow1').val("0");
		$('#comment').val("");
	}
});

function assignLead(id, sp) {
	sp = sp || "0";
	$('#actualSalesPerson').html(i18n('msg_anyone'));
	$('#assign-modal').modal('toggle');
	$
			.ajax({
				type : 'POST',
				url : ctx + "/getSalesPerson.jet",
				async : false,
				success : function(data) {
					var ctrl = "selectAssignTo";
					$("#" + ctrl).html('');
					var listItems = '<option value="0" selected disabled>'
							+ i18n('msg_select_salesperson') + '</option>';
					$.each(data, function(idx, obj) {
						listItems += '<option value="' + obj.id + '">'
								+ obj.first_name + ' ' + obj.last_name
								+ '</option>';
					});
					$('#' + ctrl).append(listItems);
					$('#' + ctrl).html('');
					$('#' + ctrl).append(listItems);
					for (i = 0; i < data.length; i++) {
						if (sp == data[i].id) {
							$('#actualSalesPerson').html(
									data[i].first_name + ' '
											+ data[i].last_name);
							$('#tempId').val(data[i].id);
							break;
						}
					}
				},
				error : function(data) {
					swal(i18n('msg_error'), i18n('err_unable_get_salesperson'),
							"error");
				}
			});
	$('#tempId').val(id);
};

function updatesalesperson() {
	var lead = $('#tempId').val(), sp = idControl('selectAssignTo').value;
	if ((sp == "") || (sp == "0")) {
		swal(i18n('msg_warning'), i18n('msg_select_salesperson'), "warning");
	} else {
		var param = "leadid=" + lead + "&salesperson=" + sp;
		$.ajax({
			type : "POST",
			url : ctx + "/updatesalesperson.jet",
			data : param,
			async : false,
			success : function(data) {
				if (data == "true") {
					swal({
						title : i18n('msg_success'),
						text : i18n('msg_data_saved'),
						type : "success",
						html : true,
						timer : 3000,
						allowEscapeKey : false
					}, function() {
						swal.close();
					});
					window.location = ctx + "/" + lpPage + ".jet"
							+ "?language=" + getLanguageURL();
				} else {
					swal(i18n('msg_error'), i18n('err_record_no_saved'),
							"error");
				}
			},
			error : function(e) {
				swal(i18n('msg_error'), i18n('err_record_no_saved'), "error");
			}
		});
	}
};

function deleteOpportunity(oppId) {
	var param = "oppId=" + oppId;
	swal({
		title : i18n('msg_are_you_sure'),
		text : i18n('msg_will_not_recover_record'),
		type : "warning",
		showCancelButton : true,
		confirmButtonClass : 'btn-warning',
		confirmButtonText : i18n('btn_yes_delete_it'),
		closeOnConfirm : false,
		closeOnCancel : false
	}, function(isConfirm) {
		if (isConfirm) {
			$.ajax({
				type : 'POST',
				url : ctx + "/deleteopportunity.jet",
				data : param,
				async : false,
				success : function(data) {
					swal({
						title : i18n('msg_deleted'),
						text : i18n('msg_record_deleted'),
						type : "success"
					}, function() {
						window.location = ctx + "/" + lpPage + ".jet"
								+ "?language=" + getLanguageURL();
					});
				},
				error : function(resp) {
					swal(i18n('msg_warning'), i18n('err_dependence_on_delete'),
							"warning");
				}
			});
		} else {
			swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "warning");
		}
	});
}

$(".table-action-btn.editOpportunity").click(function() {
	var id = $(this).attr('id');
	getCurrency("selectcurrency2");
	getOpportunityDetails(id);
});

$('#selectworkflow').change(function(){
	$.ajax({
        url: ctx+"/getworkflow",
        data: { "workflowid": $(this).val() },
        type: "post",
        success: function(data){
        	$("#radiosWf").html('');
        	var checked;
        	$.each(data.lwfp, function(ind1) {
        		$.each(data.lp, function(ind2) {
        			if(data.lp[ind2].processid===data.lwfp[ind1].processid){
        				if(data.lp[ind2].processid==1){
        					checked='checked';
        				}else{
        					checked='';
        				}
        				$("#radiosWf").append('<label class="radio-inline"><input type="radio" name="wfprocessstageid" id="wfprocessstageid'
        						+data.lwfp[ind1].wfprocessstageid+'" value="'+data.lwfp[ind1].wfprocessstageid+'" '+checked+'> '+data.lp[ind2].name+'</label>');
        			}
            	});
        	});
        }
    });
});

$('#selectworkflow1').change(function(){
	$.ajax({
        url: ctx+"/getworkflow",
        data: { "workflowid": $(this).val() },
        type: "post",
        success: function(data){
        	$("#radiosWf1").html('');
        	var checked;
        	$.each(data.lwfp, function(ind1) {
        		$.each(data.lp, function(ind2) {
        			if(data.lp[ind2].processid===data.lwfp[ind1].processid){
        				if(data.lp[ind2].processid==1){
        					checked='checked';
        				}else{
        					checked='';
        				}
        				$("#radiosWf1").append('<label class="radio-inline"><input type="radio" name="wfprocessstageid1" id="wfprocessstageid1'
        						+data.lwfp[ind1].wfprocessstageid+'" value="'+data.lwfp[ind1].wfprocessstageid+'" '+checked+'> '+data.lp[ind2].name+'</label>');
        			}
            	});
        	});
        }
    });
});

function getOpportunityDetails(oppId) {
	var param="oppId="+oppId;
	$.ajax({
		type:'POST',
		url:ctx+"/getOpportunityDetailById.jet",
		data:param,
		async:false,
		success:function(data){
			var strArr = data[0].split("$$");
			if(strArr[0]=="false"){
				swal(i18n('msg_warning'), i18n('err_unable_get_opportunity'),"error");
			}else{
				var st=strArr[12]==""||strArr[12]=="0"?1:strArr[12];
				$('#editOppId').val(strArr[1]);
				$('#lead1').val(strArr[2]);
				$('#leadInfo1').val(strArr[3]);
				$('#source1').val(strArr[6]);
				if (strArr[8] == '1')
					$('input[name=qualifiedcheck1]').prop('checked', true);
				else
					$('input[name=qualifiedcheck1]').prop('checked', false);
				$('#product1').val(strArr[9]);
				$('#selectworkflow1').val(strArr[10]);
				$('#comment').val(strArr[11]);
				getCompanyNames("selectcompany1");
												
				var idcompanyRedOrCrm=strArr[4];
				var isred = 1;
				if(strArr[17]!= '0'){
					isred=0;
					idcompanyRedOrCrm=strArr[17];
				}
				$('#idlead1').val(idcompanyRedOrCrm);				
				$('#dbred1').val(isred);
				getContactNames(idcompanyRedOrCrm, "selectcontact1",'', isred);
				
				$('#selectcontact1').val(strArr[5]);
				$('#selectstatus1').val(strArr[12]);
				$('#editOppDate').val(strArr[13]);
				$("#quantification1").val(addCommas(strArr[14]));
				$("#selectcurrency2").val(strArr[15]);
				
				if(strArr[16] != 'null' && strArr[16]!= '' && strArr[16]!= 0)
					$('#selectsubstatus1').val(strArr[16]);
				else
					$('#selectsubstatus1').val($("#selectsubstatus1 option:first").val());
				
				
				getSourceNames("selectsource1",strArr[6]);
				/*
				 $("#wfprocessstgeid").val(strArr[16]);
					$("#workflowid").val(strArr[17]);
					if(strArr[17] != 0 && strArr[16]!=1){
						$('#selectworkflow1').attr('disabled',true);
					}else{
						$('#selectworkflow1').attr('disabled',false);
					}
				 * */
				
				$('#selectworkflow1').attr('disabled',false);
				$("#radiosWf1").html('');
				var idradiox;
				if(strArr[10]!='0'){ // si tiene un workflowid
					var checked;
		        	$.each(data[3], function(ind1) {
		        		$.each(data[2], function(ind2) {
		        			idradiox = 'wfprocessstageid1'+data[2][ind2].processid;
		        			$("#radiosWf1").append('<label class="radio-inline"><input type="radio" name="wfprocessstageid1" id="'
	        						+idradiox+'" value="'+data[2][ind2].processid+'"> '+data[2][ind2].name+'</label>');
		        			
		        			if(data[2][ind2].processid===data[3][ind1].processid){
		        				if(data[1].wfprocessstageid==data[3][ind1].wfprocessstageid){
		        					//checked='checked';
		        					$("#"+idradiox).prop("checked", true); 
		        					if(data[3][ind1].processid != 0 && data[3][ind1].processid != 1){
		        						$('#selectworkflow1').attr('disabled',true);
		        					}else{
		        						$('#selectworkflow1').attr('disabled',false);
		        					}
		        				}
		        			}
		        			
		        			
		            	});
		        	});
				}
				
			}
		},error:function(resp) {
			swal(i18n('msg_warning'), i18n('err_unable_get_opportunity'),"error");
		}
	});
}

function addCommas(nStr){
    nStr += '';
    x = nStr.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}

function updateOpportunity() {
	var editOppId = $('#editOppId').val(), lead = $('#lead1').val(), leadInfo = $('#leadInfo1').val(), 
		selectcompany = "", selectcontact = "", selectstatus = 0, selectsubstatus=0, selectworkflow = "", source = "", 
			quantification = 0, qualifiedcheck = "false", product = "", comment = "", qualification = "", 
			selectcurrency="", dbred = $('#dbred').val();
	// var selSalesP=$('#selectstatus1 option:selected').val();
	$('#editOppError').css('display', 'block');
	if (lead == "") {
		$('#putEditOppError').html(i18n('err_enter_lead'));
		return false;
	} else if (leadInfo == "") {
		$('#putEditOppError').html(i18n('err_enter_leadinfo'));
		return false;
	}
	if (idControl('editAdditionalInfo').style.display == "block") {
		var edterr="",pattern = /^\d+(\.\d{1,2})?$/;
		source=$('#selectsource1').val()
		quantification = $('#quantification1').val().replace(',','');
		qualifiedcheck = $('#qualifiedcheck1').is(":checked");
		product = $('#product1').val();
		comment = $('#comment').val();
		selectcontact = $('#selectcontact1 option:selected').val();
		selectstatus = $('#selectstatus1 option:selected').val();
		selectsubstatus = $('#selectsubstatus1 option:selected').val();		
		selectworkflow = $('#selectworkflow1 option:selected').val();
		selectcurrency = $('#selectcurrency2 option:selected').val();
		if ((selectcontact == "") || (selectcontact == "0")) {
			edterr=i18n('err_select_contact');
		} else if (source == "") {
			edterr=i18n('err_enter_source');
		} else if (quantification == "") {
			edterr=i18n('err_enter_quantification');
		} else if (!pattern.test(quantification)) {
			edterr=i18n('err_enter_quantdecimal');
		} else if (selectcurrency=="" || selectcurrency== undefined) {
			edterr=i18n('err_select_currency');
		} else if (product == "") {
			edterr=i18n('err_enter_product');
		} else if ((selectworkflow == "") || (selectworkflow == "0") || (selectworkflow== undefined)) {
			edterr=i18n('err_select_workflow');
		} else if ((selectstatus=="")||(selectstatus==0)){
			edterr=i18n('err_select_status');
		/*} else if ((selectsubstatus=="")||(selectsubstatus==0)){
			edterr=i18n('err_select_substatus');*/
		}
		if(edterr!=""){
			$('#putEditOppError').html(edterr);
			return false;
		}
	}
	$('#editOppError').css('display', 'none');
	
	if(selectsubstatus=='undefined')
		selectsubstatus ='';
	
	var wfprocessstageid = $('input[name=wfprocessstageid1]:checked').val();
	var param = "editOppId=" + editOppId + "&lead=" + lead + "&leadinfo="
		+ leadInfo + "&selectcompany=" + selectcompany + "&selectcontact="
		+ selectcontact + "&source=" + source + "&selectstatus="
		+ selectstatus + "&quantification=" + quantification
		+ "&qualifiedcheck=" + qualifiedcheck + "&product=" + product
		+ "&selectworkflow=" + selectworkflow + "&comment=" + comment
		+ "&qualification=" + qualification+ "&currency=" + selectcurrency+ "&selectsubstatus=" + selectsubstatus
		+ "&workflowid=" + $("#workflowid").val()+ "&wfprocessstageid=" + wfprocessstageid+ "&dbred=" + dbred;// +"&sales="+selSalesP;
	$.ajax({
		type : "POST",
		url : ctx + "/updateopportunity.jet",
		data : param,
		async : false,
		success : function(data) {
			if (data == "true") {
				swal({
					title : i18n('msg_success'),
					text : i18n('msg_data_saved'),
					type : "success",
					html : true,
					timer : 3000,
					allowEscapeKey : false
				}, function() {
					swal.close();
				});
				$('#edit-opportunity-modal').modal('toggle');
				window.location = ctx + "/" + lpPage + ".jet" + "?language="
						+ getLanguageURL();
			}else {
				$('#putEditOppError').html(i18n('err_record_no_saved'));
				$('#editOppError').css('display', 'block');
			}
		},
		error : function(e) {
			$('#putEditOppError').html(i18n('err_record_no_saved'));
			$('#editOppError').css('display', 'block');
		}
	});
}

/*
 * function getSalesPerson(selOp){ clearSelect(selOp); $.ajax({ type:"POST",
 * url:ctx+"/getsalesperson.jet", async:false, success:function(data){ var
 * listItems='',sel=true; for(var d in data){ var sp=data[d]; var option=new
 * Option(sp[1]+" "+sp[2],sp[0],true,sel); $('#'+selOp).append(option);
 * sel=false; } },error:function(data){swal(i18n('msg_error'),
 * i18n('err_unable_get_salesperson'), "error");} }); }
 */
/*
function i18n(msg){
	var i18n_message = "";
	// IDIOMAS:si en la URL no contiene la variable "lenguage", asumirá que esta en inglés
	lang = getLanguageURL();
	if (msg == "btn_add_prospect") {
		if (lang == "es") {
			i18n_message = "Atrás";
		} else {
			i18n_message = "Back";
		}
	} else if (msg == "btn_add_opportunity") {
		if (lang == "es") {
			i18n_message = "Agregar oportunidad";
		} else {
			i18n_message = "Add opportunity";
		}
	} else if (msg == "btn_edit_prospect") {
		if (lang == "es") {
			i18n_message = "Editar prospecto";
		} else {
			i18n_message = "Edit lead";
		}
	} else if (msg == "btn_yes_delete_it") {
		if (lang == "es") {
			i18n_message = "\u00a1S\u00ed, eliminar!";
		} else {
			i18n_message = "Yes, delete it!";
		}

	} else if (msg == "err_char_contains_password") {
		if (lang == "es") {
			i18n_message = "\u00a1Una contrase\u00f1a debe contener m\u00ednimo ocho caracteres, "
					+ "al menos un n\u00famero, una letra may\u00fascula, una min\u00fascula y un caracter especial!";
		} else {
			i18n_message = "A password contains at least eight characters, including at least one number and "
					+ "one lowercase and one uppercase letters and one special character!";
		}
	} else if (msg == "err_company_no_saved") {
		if (lang == "es") {
			i18n_message = "\u00a1Fallo al guardar la compa\u00f1ia!";
		} else {
			i18n_message = "Company saved failed!";
		}
	} else if (msg == "err_dependence_on_delete") {
		if (lang == "es") {
			i18n_message = "\u00a1Este registro no se puede eliminar porque tiene dependencias!";
		} else {
			i18n_message = "This item can’t be deleted because it has dependencies!";
		}
	} else if (msg == "err_enter_companyname") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de ingresar el nombre de compa\u00f1ia!";
		} else {
			i18n_message = "Please enter company name!";
		}
	} else if (msg == "err_duplicate_companyname") {
		if (lang == "es") {
			i18n_message = "\u00a1El nombre que ingreso ya existe, intente con uno distinto!";
		} else {
			i18n_message = "That company name is duplicate, try with another!";
		}	
	} else if (msg == "err_enter_lead") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de ingresar un nombre!";
		} else {
			i18n_message = "Please enter lead!";
		}
	} else if (msg == "err_enter_leadinfo") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de ingresar informaci\u00f3n del prospecto!";
		} else {
			i18n_message = "Please enter lead information!";
		}
	} else if (msg == "err_enter_product") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de indicar un producto!";
		} else {
			i18n_message = "Please enter product!";
		}
	} else if (msg == "err_enter_quantdecimal") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de ingresar un n\u00famero entero o valor decimal para cuatificaci\u00f3n!";
		} else {
			i18n_message = "Please enter number or decimal value in quatification!";
		}
	} else if (msg == "err_enter_quantification") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de ingresar cuantificaci\u00f3n!";
		} else {
			i18n_message = "Please enter quantification!";
		}
	} else if (msg == "err_enter_source") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de ingresar la fuente!";
		} else {
			i18n_message = "Please enter Source!";
		}
	} else if (msg == "err_enter_rfc") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de ingresar el RFC!";
		} else {
			i18n_message = "Please enter RFC!";
		}
	} else if (msg == "err_provide_address") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de indicar la informaci\u00f3n del contacto!";
		} else {
			i18n_message = "Please provide contact information";
		}
	} else if (msg == "err_provide_source") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de indicar la informaci\u00f3n de fuente!";
		} else {
			i18n_message = "Please provide source information";
		}
	} else if (msg == "err_record_no_saved") {
		if (lang == "es") {
			i18n_message = "\u00a1Fallo al guardar los datos!";
		} else {
			i18n_message = "Fail to save the data!";
		}
	} else if (msg == "err_record_no_saved_rfc_duplicate") {
		if (lang == "es") {
			i18n_message = "\u00a1El RFC ya se encuentra registrado en el sistema!";
		} else {
			i18n_message = "The RFC is duplicate in the system!";
		}	
	} else if (msg == "err_select_company") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de seleccionar una compa\u00f1ia!";
		} else {
			i18n_message = "Please select company!";
		}
	} else if (msg == "err_select_currency") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de seleccionar una moneda!";
		} else {
			i18n_message = "Please select currency!";
		}
	} else if (msg == "err_select_contact") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de seleccionar un contacto!";
		} else {
			i18n_message = "Please select contact!";
		}
	} else if (msg == "err_select_workflow") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de ingresar un flujo!";
		} else {
			i18n_message = "Please select workflow!";
		}
	} else if (msg == "err_select_status") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de seleccionar un estatus!";
		} else {
			i18n_message = "Please select an status!";
		}
	} else if (msg == "err_select_substatus") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de seleccionar un subestatus!";
		} else {
			i18n_message = "Please select an sub-status!";
		}	
	} else if (msg == "err_unable_get_company") {
		if (lang == "es") {
			i18n_message = "\u00a1No fue posible obtener las compa\u00f1ias!";
		} else {
			i18n_message = "Unable to get companies!";
		}
	} else if (msg == "err_unable_get_opportunity") {
		if (lang == "es") {
			i18n_message = "\u00a1No fue posible obtener la oportunidad!";
		} else {
			i18n_message = "Unable to get opportunity!";
		}
	} else if (msg == "err_unable_get_salesperson") {
		if (lang == "es") {
			i18n_message = "\u00a1No fue posible obtener los vendedores!";
		} else {
			i18n_message = "Unable to get sales person!";
		}

	} else if (msg == "msg_anyone") {
		if (lang == "es") {
			i18n_message = "Ninguno";
		} else {
			i18n_message = "Any one";
		}
	} else if (msg == "msg_are_you_sure") {
		if (lang == "es") {
			i18n_message = "\u00bfEsta seguro?";
		} else {
			i18n_message = "Are you sure?";
		}
	} else if (msg == "msg_company_saved") {
		if (lang == "es") {
			i18n_message = "\u00a1Nombre de compa\u00f1ia guardada exitosamente!";
		} else {
			i18n_message = "Company saved successfully!";
		}
	} else if (msg == "msg_cancelled") {
		if (lang == "es") {
			i18n_message = "Cancelado";
		} else {
			i18n_message = "";
		}
	} else if (msg == "msg_contact_saved") {
		if (lang == "es") {
			i18n_message = "\u00a1Contacto guardado exitosamente!";
		} else {
			i18n_message = "Contact saved successfully!";
		}
	} else if (msg == "msg_data_saved") {
		if (lang == "es") {
			i18n_message = "\u00a1Datos almacenados exitosamente!";
		} else {
			i18n_message = "Data saved successfully!";
		}
	} else if (msg == "msg_deleted") {
		if (lang == "es") {
			i18n_message = "\u00a1Eliminado!";
		} else {
			i18n_message = "Deleted!";
		}
	} else if (msg == "msg_error") {
		if (lang == "es") {
			i18n_message = "\u00a1Error!";
		} else {
			i18n_message = "Error!";
		}
	} else if (msg == "msg_select_company") {
		if (lang == "es") {
			i18n_message = "Favor de seleccionar una compa\u00f1ia";
		} else {
			i18n_message = "Please, select a company";
		}
	} else if (msg == "msg_select_currency") {
		if (lang == "es") {
			i18n_message = "Favor de seleccionar una moneda";
		} else {
			i18n_message = "Please, select a currency";
		}	
	} else if (msg == "msg_select_contact") {
		if (lang == "es") {
			i18n_message = "Selecciona un contacto";
		} else {
			i18n_message = "Select Contact";
		}
	} else if (msg == "msg_select_source") {
		if (lang == "es") {
			i18n_message = "Selecciona la fuente";
		} else {
			i18n_message = "Select Source";
		}
	} else if (msg == "msg_select_salesperson") {
		if (lang == "es") {
			i18n_message = "Selecciona un agente de ventas";
		} else {
			i18n_message = "Select a sales person";
		}
	} else if (msg == "msg_only_edit_lead") {
		if (lang == "es") {
			i18n_message = "S\u00f3lo editar prospecto";
		} else {
			i18n_message = "Edit Lead";
		}
	} else if (msg == "msg_record_deleted") {
		if (lang == "es") {
			i18n_message = "\u00a1Registro eliminado!";
		} else {
			i18n_message = "Record deleted!";
		}
	} else if (msg == "msg_record_safe") {
		if (lang == "es") {
			i18n_message = "Tu registro esta seguro";
		} else {
			i18n_message = "Your record is safe:)";
		}
	} else if (msg == "msg_success") {
		if (lang == "es") {
			i18n_message = "\u00a1Correcto!";
		} else {
			i18n_message = "Success!";
		}
	} else if (msg == "msg_warning") {
		if (lang == "es") {
			i18n_message = "\u00a1Advertencia!";
		} else {
			i18n_message = "Warning!";
		}
	} else if (msg == "msg_will_not_recover_record") {
		if (lang == "es") {
			i18n_message = "\u00a1Si acepta, no podr\u00e1 recuperar este registro!";
		} else {
			i18n_message = "You will not be able to recover this record!";
		}
	}return i18n_message;
}*/