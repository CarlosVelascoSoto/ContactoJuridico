;function addRelCol(e){
	var err='',tag='',colname=$('#addcolname').val(),alias=$('#addalias').val(),
		msgjs=$('#addmsgjs').val(),msgjsp=$('#addmsgjsp').val(),
		fromTable=$('#addfromTable').val(),fromCol=$('#addfromCol').val(),descr=$('#addgetdescr').val(),
		fromTable2=$('#addfromTable2').val(),fromCol2=$('#addfromCol2').val(),descr2=$('#addgetdescr2').val();
	if(colname==''){
		tag='addcolname';
		err=i18n('err_enter_msg_name');
	}else if(addmsgjs=='' && addmsgjsp==''){
		tag='addmsgjs';
		err=i18n('err_enter_onedescription');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnadd').css('display','block');
		$('#putErrorOnadd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='colname='+colname + '&alias='+alias + '&msgjs='+msgjs + '&msgjsp='+msgjsp
		+ '&fromTable='+fromTable + '&fromCol='+fromCol + '&addgetdescr='+descr
		+ '&fromTable2='+fromTable2 + '&fromCol2='+fromCol2 + '&addgetdescr2='+descr2;
	$.ajax({
		type:"POST",
		url:ctx+"/saveNewRelCols",
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
				$('#putErrorOnadd').html(i18n(data));
				$('#errorOnadd').css('display','block');
			}
		},
		error:function(e){
			$('#putErrorOnadd').html(i18n('err_record_no_saved'));
			$('#errorOnadd').css('display','block');
		}
	});
};

function getDetailsRelCol(id){
	$('#errorOnedit, [data-customcol="edit"]').hide();
	$('#edit-relcol-modal input[type=text]').val('');
	$('#editactionway').html('');
	$('#formsnedit:input[type=radio], #formsnedit:input[type=checkbox]').prop('checked', false);
	$.ajax({
		type:'POST',
		url:ctx+"/getTrialTypeById",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].info[0]||[];
			if(info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_trialtype'),"error");
				return;
			}
			var tjacc=data[0].tjacc[0], custcol=(data[0].custcol==null?null:data[0].custcol);
			getMaterias('editmatter', 'ul');
			$('#edittrialTypeId').val(info.tipojuicioid);
			$('#edittrialtype').val(info.tipojuicio);
			$('#edittrialtypeen').val(info.tipojuicioen);
			/*if(tjacc!=null){
				getTextDDFilterByVal('editmatter', tjacc.materiaid);
				getAccionByMatterId('editAccion', '#editmatter li.selected', 'ul', 'edit');
				getTextDDFilterByVal('editAccion', info.accionid);
				getWayByActionId(info.accionid, 'editactionway');
			}*/
			getWayByActionId($('#editAccion li.selected').val(),'editactionway');
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_trialtype'),"error");
		}
	});
};

function updateRelCols(){
	var err='',tag='',descr=($('#edittrialtype').val()).trim(),descren=($('#edittrialtypeen').val()).trim(),
		generaltrialid=$('#editgeneraltrialid').val(),matter=$('#editmatter li.selected').val(),
		reqactor= $("#editrequiresactor").prop('checked'),reqdef= $("#editrequiresdefendant").prop('checked'),
		reqthird= $("#editrequiresthird").prop('checked'),actionid = $('#editAccion li.selected').val();
	if(colname==''){
		tag='addcolname';
		err=i18n('err_enter_msg_name');
	}else if(addmsgjs=='' && addmsgjsp==''){
		tag='addmsgjs';
		err=i18n('err_enter_onedescription');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnedit').css('display','block');
		$('#putErrorOnedit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param = 'id='+id + '&colname='+colname + '&alias='+alias + '&msgjs='+msgjs + '&msgjsp='+msgjsp
	+ '&fromTable='+fromTable + '&fromCol='+fromCol + '&addgetdescr='+descr
	+ '&fromTable2='+fromTable2 + '&fromCol2='+fromCol2 + '&addgetdescr2='+descr2;
	$.ajax({
		type:"POST",
		url:ctx+"/updateRelCols",
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
				$('#putErrorOnedit').html(i18n(data));
				$('#errorOnedit').css('display','block');
			}
		},error:function(e){
			$('#putErrorOnedit').html(i18n('err_record_no_saved'));
			$('#errorOnedit').css('display','block');
		}
	});
};

function deleteRelCols(id){
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
	},function(isConfirm){
		if(isConfirm){
			$.ajax({
				type:'POST',
				url:ctx+"/deleteRelCols",
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

$("#addNewRelatedColumn").click(function(){
	$('#errorOnadd').hide();
	$('#add-relcol-modal input[type=text]').val('');
});

$('input').blur(function(){
	$(this).parent().removeClass('has-error');
	var addEdit = 'add';
	if(this.id=='editAcccionText')
		addEdit='edit';
	getWayByActionId($('#'+addEdit+'Accion li.selected').val(),addEdit+'actionway');
});