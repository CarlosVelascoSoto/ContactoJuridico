;function saveNewCommTypes(e){
	var err='',tag="",descr=($('#commtype').val()).trim(),
		hrefaction=$('#hrefAction').val().trim(),
		onclicked=$('#onclickAction').val().trim();
	if(descr==''){
		tag='commtype';
		err=i18n('msg_empty_data');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	$.ajax({
		type:"POST",
		url:ctx+"/saveNewCommTypes",
		data:'descr='+descr + '&descr='+descr + '&hrefaction='+hrefaction + '&onclicked'+onclicked,
		async:false,
		success:function(data){
			if(data=='msg_data_saved'){
				swal({
					title:i18n('msg_success'),
					text:i18n(data),
					type:"success",
					timer:3000,
					allowEscapeKey:false
				}, function(){
					location.href=location.pathname+"?language="+ getLanguageURL();
				});
				window.setTimeout(function(){
					location.href=location.pathname+"?language="+ getLanguageURL();
				},3000);
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

function getDetailsToEdit(id){
	$('#errorOnEdit, #edhelp').hide();
	$.ajax({
		type:'POST',
		url:ctx+"/getCommTypeById",
		data:"id="+id,
		async:false,
		success:function(data){
			if(data[0].length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_commtype'),"error");
				return;
			}
			var info=data[0][0];
			$('#edCommTypeId').val(info.commtypeid);
			$('#edCommType').val(info.description);
			$('#edHrefAction').val(info.hrefaction);
			$('#edOnclickAction').val(info.onclickaction);
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_commtype'),"error");
		}
	});
};

function updateCommType(){
	var err='',tag="",descr=($('#edCommType').val()).trim(),
		hrefaction=$('#edHrefAction').val().trim(),
		onclicked=$('#edOnclickAction').val().trim();
	if(descr==''){
		tag='edCommType';
		err=i18n('msg_empty_data');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
	}
	var param='id='+$('#edCommTypeId').val() + '&descr='+descr + '&hrefaction='+hrefaction + '&onclicked'+onclicked;
	$.ajax({
		type:"POST",
		url:ctx+"/updateCommType",
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

function deleteCommTypes(id){
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
				url:ctx+"/deleteCommTypes",
				data:param,
				async:false,
				success:function(data){
					swal({
						title:i18n('msg_deleted'),
						text:i18n('msg_record_deleted'),
						type:"success",
						timer:3000,
						allowEscapeKey:false
					}, function(){
						location.href=location.pathname+"?language="+ getLanguageURL();
					});
					window.setTimeout(function(){
						location.href=location.pathname+"?language="+ getLanguageURL();
					},3000);
				},error:function(resp){
					swal(i18n('msg_warning'), i18n('err_dependence_on_delete'),"warning");
				}
			});
		}else{
			swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "warning");
		}
	});
};

function togglehelp(obj){
	$(obj).fadeToggle('fast');
};

$("#addNewCommType").click(function(){
	$('#errorOnAdd, #addhelp').hide();
	$("#addNewCommType").attr('href','#commtype-modal');
});