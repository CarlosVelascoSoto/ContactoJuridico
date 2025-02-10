;function addWay(e){
	var err='',descr=($('#way').val()).trim();
	if(descr=='')
		err=i18n('msg_empty_data');
	if(err!=""){
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='description='+descr + '&descriptionen='+descr;
	$.ajax({
		type:"POST",
		url:ctx+"/saveNewWay",
		data:param,
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
	$('#errorOnEdit').hide();
	$.ajax({
		type:'POST',
		url:ctx+"/getWayById",
		data:"id="+id,
		async:false,
		success:function(data){
			if(data[0].length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_way'),"error");
				return;
			}
			var info=data[0][0];
			$('#edWayId').val(info.viaid);
			$('#edWay').val(info.via);
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_way'),"error");
		}
	});
};

function updateWay(){
	var err='',descr=($('#edWay').val()).trim();
	if(descr=='')
		err=i18n('msg_empty_data');
	if(err!=""){
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		e.disabled=false;
		return false;
	}
	var param='viaid='+$('#edWayId').val() + '&description='+descr + '&descriptionen='+descr;;
	$.ajax({
		type:"POST",
		url:ctx+"/updateWay",
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

function deleteWay(id){
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
				url:ctx+"/deleteWay",
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

$("#addNewWay").click(function(){
	$('#errorOnAdd').css('display','none');
	$("#addNewWay").attr('href','#way-modal');
});