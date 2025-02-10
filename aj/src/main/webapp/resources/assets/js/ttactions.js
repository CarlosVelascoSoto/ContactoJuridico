;function getMaterias(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getMaterias",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].materiaid + '" title="'+ info[i].materia + '">'
						+ info[i].materia + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Matter\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_matter') + '. ' + e);
		}
	});
};

/**
@param Id		Id del tag donde se cargará la lista.
@param addEdit	Indica el modal de donde será aplicado: "add" o "edit".
@param selType	Tipo de listado a cargar: "select", "ul", "ol"	*/
function getWayList(id, addEdit, selType) {
	var elemOp = 'option';
	if (selType == 'ul' || selType == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getWayList",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (selType == 'select' || selType == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].viaid+ '" title="' + info[i].via
						+ '">' + info[i].via + '</'+ elemOp + '>');
			} else {
				$('#' + id).append('<'+ elemOp+ ' value="0" selected disabled>'+ i18n('msg_no_data')
					+ '</'+ elemOp+ '>'+ '<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Way\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
			}
			$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Way\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
		},error : function(e) {
			console.log(i18n('err_unable_get_way') + '. ' + e);
		}
	});
};

function addAction(e){
	var err='',descr=($('#action').val()).trim(), matter=$('#matter li.selected').val(), wayid=$('#way li.selected').val();
	if(descr=='')
		err=i18n('msg_empty_data');
	else if(matter==''||matter==null)
		err=i18n('err_select_matter');
	else if(wayid==''||wayid==null)
		err=i18n('msg_select_way');
	if(err!=""){
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='description='+descr + '&descriptionen='+descr + '&matterid='+matter + '&wayid='+wayid;
	$.ajax({
		type:"POST",
		url:ctx+"/saveNewAccion",
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
		url:ctx+"/getAccionById",
		data:"id="+id,
		async:false,
		success:function(data){
			if(data[0].length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_action'),"error");
				return;
			}
			var info=data[0][0];
			$('#edActionId').val(info.accionid);
			$('#edAction').val(info.descripcion);
			getMaterias('edMatter','ul');
			getTextDDFilterByVal('edMatter', info.materiaid=info.materiaid||'');
			getWayList('edWay', '', 'ul');
			getTextDDFilterByVal('edWay', info.viaid=info.viaid||'');
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_action'),"error");
		}
	});
};

function updateAction(){
	var err='',descr=($('#edAction').val()).trim(), matter=$('#edMatter li.selected').val(), wayid=$('#edWay li.selected').val();
	if(descr=='')
		err=i18n('msg_empty_data');
	else if(matter==''||matter==null)
		err=i18n('err_select_matter');
	else if(wayid==''||wayid==null)
		err=i18n('msg_select_way');
	if(err!=""){
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		e.disabled=false;
		return false;
	}
	var param='accion='+$('#edActionId').val() + '&descr='+descr + '&descren='+descr + '&matterid='+matter + '&wayid='+wayid;
	$.ajax({
		type:"POST",
		url:ctx+"/updateAccion",
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

function deleteAction(id){
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
				url:ctx+"/deleteAccion",
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

$("#addNewAction").click(function(){
	$('#errorOnAdd').css('display','none');
	$("#addNewAction").attr('href','#action-modal');
	getMaterias('matter','ul');
	getWayList('way', '', 'ul');
});