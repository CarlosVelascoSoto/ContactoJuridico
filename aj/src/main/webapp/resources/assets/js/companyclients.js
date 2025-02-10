;function getCompanies(id){
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

function getClients(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getClients",
		async:false,
		success:function(data){
			var info=data[0]||[];
			if(info.length>0){
				var option=new Option(i18n('msg_select'),'',!1,!0);
				$('#'+id).append(option);
				$(option).attr('disabled','disabled');
				for(i=0;i<info.length;i++){
					option=new Option(info[i].client,info[i].clientid,!0,!1);
					$('#'+id).append(option);
					$(option).attr('title',info[i].client);
				}
			}
		},error:function(e){
			console.log("Error al obtener clientes. "+e);
		}
	});
};

function addCoClient(){
	var err='',company=$('#company option:selected').val(),client=$('#client option:selected').val();
	if(company=='')
		err=i18n('err_select_firm');
	else if(client=='')
		err=i18n('err_select_client');
	if(err!=""){
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='company='+company+'&client='+client;
	$.ajax({
		type:"POST",
		url:ctx+"/addNewCoClient",
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

function getCoClientById(id){
	$.ajax({
		type:'POST',
		url:ctx+"/getCoClientById",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].info[0]||[];
			if(data[0].info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_firm'),"error");
			}else{
				getCompanies('edCompany');
				getClients('edClient');
				$('#edCompId').val(info.companyclientid);
				$('#edCompany').val(info.companyid);
				$('#edClient').val(info.clientid);
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_firm'),"error");
		}
	});
}

function updateData(){
	var err='',company=$('#edCompany').val(),client=$('#edClient').val();
	if(company=='')
		err=i18n('err_select_firm');
	else if(client=='')
		err=i18n('err_select_client');
	if(err!=""){
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.modal').animate({scrollTop:0},'1000');
		return false;
	}
	var param='id='+$('#edCompId').val()+'&company='+company+'&client='+client;	
	$.ajax({
		type:"POST",
		url:ctx+"/updateCoClient",
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
				$('#putErrorOnEdit').html(i18n(data));
				$('#errorOnEdit').css('display','block');
			}
		},error:function(e){
			$('#putErrorOnEdit').html(i18n('err_record_no_saved'));
			$('#errorOnEdit').css('display','block');
		}
	});
};

$("#addNewCoClients").click(function(){
	$('#errorOnAdd').css('display','none');
	$("#addNewCoClients").attr('href','#coclients-modal');
	getCompanies('company');
	getClients('client');
});

function deleteCoClient(id){
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
				url:ctx+"/deleteCoClient",
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
}

/* limpia inputs */
$('#company-modal, #edit-modal').on('hidden.bs.modal',function(e){
	$(this).find("input,textarea").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked","").end();
});