;function getEstados(id, elemtype) {
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
				$('#' + id).append('<'+ elemOp+ ' ></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_state')+'. '+e);
		}
	});
};

function getCitiesByState(id, elemtype, stateid) {
	if(stateid==''||stateid==null){
	$('#ErrorSelectState').show();
	$('#putErrorSelectState').html(i18n('msg_select_state'));	
	return;
	}
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type: "POST",
		url : ctx + "/getCitiesByState",
		data: "estadoid=" + stateid,
		async:false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].ciudadid + '" title="'+ info[i].ciudad + '">'
						+ info[i].ciudad + '</' + elemOp+ '>');
			}
		},
		error : function(e) {
			console.log(i18n('err_unable_get_city')+'. '+e);
		}
	});
};

function getCityById(cityid) {
	var info=null;
	$.ajax({
		type : "POST",
		url : ctx + "/getCityById",
		data:"id=" + cityid,
		async : false,
		success : function(data) {
			info=data[0][0];
		},error : function(e) {
			console.log(i18n('err_unable_get_city')+'. '+e);
		}
	});
	return info;
};

function addUnitaryCourt(){
	var err='',descr=($('#unitarycourt').val()).trim(),city=$('#cityUnitCourt li.selected').val();
	if(descr=='')
		err=i18n('msg_empty_data');
	else if(city==''||city==null)
		err=i18n('err_select_city');
	if(err!=""){
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		
		return false;
	}
	var param='description='+descr+'&ciudadid='+city;
	$.ajax({
		type:"POST",
		url:ctx+"/saveNewUnitaryCourt",
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
		url:ctx+"/getUnitaryCourtById",
		data:"id="+id,
		async:false,
		success:function(data){
			if(data[0].length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_unitarycourt'),"error");
				return;
			}
			var info=data[0][0];
			$('#edUnitaryCourtId').val(info.tribunalUnitarioid);
			$('#edUnitaryCourt').val(info.tribunalUnitario);
			getEstados('edStatecourt','ul');
			var stateinfo=getCityById(info.ciudadid);
			getTextDDFilterByVal('edStatecourt', stateinfo.estadoid);
			getCitiesByState('edCityUnitCourt','ul',stateinfo.estadoid);
			getTextDDFilterByVal('edCityUnitCourt', info.ciudadid);
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_unitarycourt'),"error");
		}
	});
};

function updateUnitaryCourt(){
	var err='',descr=($('#edUnitaryCourt').val()).trim(),city=$('#edCityUnitCourt li.selected').val();
	if(descr=='')
		err=i18n('msg_empty_data');
	else if(city==''||city==null)
		err=i18n('err_select_city');
	if(err!=""){
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		e.disabled=false;
		return false;
	}
	var param='id='+$('#edUnitaryCourtId').val()+'&descr='+descr+'&ciudadid='+city;
	$.ajax({
		type:"POST",
		url:ctx+"/updateUnitaryCourt",
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

function deleteUnitaryCourt(id){
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
				url:ctx+"/deleteUnitaryCourt",
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

$("#addNewUnitaryCourt").click(function(){
	$('#ErrorSelectState').hide();
	$('#errorOnAdd').css('display','none');
	$("#addNewUnitaryCourt").attr('href','#unitarycourt-modal');
	getEstados('statecourt','ul');
});