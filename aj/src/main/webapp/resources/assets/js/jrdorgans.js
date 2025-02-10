function getCiudades(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getCiudades",
		async : false,
		success : function(data) {
			var info = data[0].cdList;
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].ciudadid + '" title="'+ info[i].ciudad + '">'
						+ info[i].ciudad + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'City\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
			}
		},
		error : function(e) {
			console.log(i18n('err_unable_get_city')+'. '+e);
		}
	});
};

function addJrdOrgan(e){
	var err='',descr=($('#jrdorgan').val()).trim(),city=$('#city li.selected').val();
	city=city||'';
	if(descr=='')
		err=i18n('msg_empty_data');
	else if(city=='')
		err=i18n('err_select_city');
	if(err!=""){
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='organo='+descr+'&ciudadid='+city;
	$.ajax({
		type:"POST",
		url:ctx+"/saveNewJrdOrgan",
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
		url:ctx+"/getJrdOrganById",
		data:"id="+id,
		async:false,
		success:function(data){
			if(data[0].length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_jrdorgan'),"error");
				return;
			}
			var info=data[0][0];
			$('#edJrdOrganId').val(info.organoid);
			$('#edJrdOrgan').val(info.organo);
			getCiudades('edCity','ul');
			getTextDDFilterByVal('edCity', info.ciudadid);
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_jrdorgan'),"error");
		}
	});
};

function updateJrdOrgan(){
	var err='',descr=($('#edJrdOrgan').val()).trim(),city=$('#edCity li.selected').val();
	city=city||'';
	if(descr=='')
		err=i18n('msg_empty_data');
	else if(city=='')
		err=i18n('err_select_city');
	if(err!=""){
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		e.disabled=false;
		return false;
	}
	var param='organoid='+$('#edJrdOrganId').val()+'&organo='+descr+'&ciudadid='+city;
	$.ajax({
		type:"POST",
		url:ctx+"/updateJrdOrgan",
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

function deleteJrdOrgan(id){
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
				url:ctx+"/deleteJrdOrgan",
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

$("#addNewJrdOrgan").click(function(){
	$('#errorOnAdd').css('display','none');
	$("#addNewJrdOrgan").attr('href','#jrdorgan-modal');
	getCiudades('city','ul');
});