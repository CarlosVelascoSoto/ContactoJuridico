;function addSubPrivilege(e){
	var err='',descr=($('#spDescription').val()).trim(),toUrl=$('#spUrl').val();
	if(descr=='')
		err=i18n('err_enter_description');
	else if(toUrl=='')
		err=i18n('msg_empty_data');
	if(err!=""){
		$('#putErrorOnAddSubPriv').html(err);
		$('#errorOnAddSubPriv').css('display','block');
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='descr='+descr+'&toUrl='+toUrl;
	$.ajax({
		type:"POST",
		url:ctx+"/addNewSubPrivilege",
		data:param,
		async:false,
		success:function(data){
			if(data=='msg_data_saved'){
				swal(i18n("msg_success"),i18n(data),"success");
				location.href=location.pathname+"?language="+ getLanguageURL();
			}else{
				$('#putErrorOnAddSubPriv').html(i18n(data));
				$('#errorOnAddSubPriv').css('display','block');
			}$('#company-modal').modal('toggle');
		},
		error:function(e){
			$('#putErrorOnAddSubPriv').html(i18n('err_record_no_saved'));
			$('#errorOnAddSubPriv').css('display','block');
		}
	});
};

function getSubprivToEdit(id){
	$.ajax({
		type:'POST',
		url:ctx+"/getSubPrivilegeById",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].info[0]||[];
			if(data[0].info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_module'),"error");
			}else{
				$('#edSpId').val(info.menuid);
				$('#edSpDescription').val(info.menu);
				$('#edSpUrl').val(info.link);
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_socialnetwork'),"error");
		}
	});
};

function updateEditPrivilege(){
	var err='',descr=($('#edSpDescription').val()).trim(),toUrl=$('#edSpUrl').val();
	if(descr=='')
		err=i18n('err_enter_description');
	else if(toUrl=='')
		err=i18n('msg_empty_data');
	if(err!=""){
		$('#errorOnEditSubPriv').css('display','block');
		$('#puterrorOnEditSubPriv').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='id='+$('#edSpId').val()+'&descr='+descr+'&toUrl='+toUrl;
	$.ajax({
		type:"POST",
		url:ctx+"/updateSubPrivilege",
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
					location.href=location.pathname+"?language="+ getLanguageURL();
				});
				window.setTimeout(function(){
					location.href=location.pathname+"?language="+ getLanguageURL();
				},3000);
			}else{
				$('#puterrorOnEditSubPriv').html(i18n(data));
				$('#errorOnEditSubPriv').css('display','block');
			}
		},error:function(e){
			$('#puterrorOnEditSubPriv').html(i18n('err_record_no_saved'));
			$('#errorOnEditSubPriv').css('display','block');
		}
	});
};

function deleteSubPrivilege(id,descr){
	var param="id="+id;
	swal({
		title:i18n('msg_sure_to_delete'),
		text:'"'+descr+'"\n'+i18n('msg_will_not_recover_record'),
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
				url:ctx+"/deleteSubPrivilege",
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

$("#addNewSubPrivilege").click(function(){
	$('#errorOnAddSubPrivSubPriv').css('display','none');
	$("#addNewSubPrivilege").attr('href','#subprivileges-modal');
});