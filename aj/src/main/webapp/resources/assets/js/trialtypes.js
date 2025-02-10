;function getMaterias(id, selType) {
	var elemOp = 'option';
	if (selType == 'ul' || selType == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getMaterias",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (selType == 'select' || selType == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].materiaid + '" title="'+ info[i].materia + '">'
						+ info[i].materia + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Matter\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
			}
		},
		error : function(e) {
			console.log(i18n('err_unable_get_matter')+'. '+e);
		}
	});
};

function addTrialType(e){
	var err='',tag='',descr=($('#addtrialtype').val()).trim(),descren=($('#addtrialtypeen').val()).trim(),
		generaltrialid=$('#generaltrialid').val(),matter=$('#addmatter li.selected').val(),
		reqactor= $("#addrequiresactor").prop('checked'),reqdef= $("#addrequiresdefendant").prop('checked'),
		reqthird= $("#addrequiresthird").prop('checked'),actionid = $('#addAccion li.selected').val();
	if(matter=='' || matter==null){
		tag='addmatter';
		err=i18n('err_select_matter');
	}else if(actionid=='' || actionid==null){
		tag='addAccion';
		err=i18n('err_select_accion');
	}else if(descr==''){
		tag='addtrialtype';
		err=i18n('err_enter_trialtype');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnadd').css('display','block');
		$('#putErrorOnadd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var rows = $('#add-manage-columnsdata tbody tr'), allColumns = '';
	for(i=0; i<rows.length;i++){
		var coldata = $('#add-manage-columnsdata tbody tr:eq(' + i + ') td:eq(3) button');
		allColumns+=coldata.data('title') +'|' + coldata.data('info') +'|' + coldata.data('req') +'|'
			+ coldata.data('titleen') +'|' + coldata.data('infoen') +'|' + coldata.data('length') +'|'
			+ coldata.data('needded') +'~';
//FIXME cambiar method por "name=" v.254
	}
	var param='tipojuicio='+descr + '&tipojuicioen='+descren + '&matterid='+matter + '&actiontrialid='+actionid
		+ '&generaltrialid='+generaltrialid + '&reqactor='+(reqactor*1) + '&reqdef='+(reqdef*1)
		+ '&reqthird='+(reqthird*1)	+ '&allColumns='+allColumns.replace(/.$/,'');
	$.ajax({
		type:"POST",
		url:ctx+"/saveNewTrialType",
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

function getDetailsToEdit(id){
	$('#errorOnedit, [data-customcol="edit"]').hide();
	$('#edit-trialtype-modal input[type=text]').val('');
	$('#edit-trialtype-modal tbody, #edit-trialtype-modal ul').empty();
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
			if(tjacc!=null){
				getTextDDFilterByVal('editmatter', tjacc.materiaid);
				getAccionByMatterId('editAccion', '#editmatter li.selected', 'ul', 'edit');
				getTextDDFilterByVal('editAccion', info.accionid);
				getWayByActionId(info.accionid, 'editactionway');
			}
			$("#editrequiresactor").prop('checked',(info.requiereactor==1?!0:!1));
			$("#editrequiresdefendant").prop('checked',(info.requieredemandado==1?!0:!1));
			$("#editrequiresthird").prop('checked',(info.requieretercero==1?!0:!1));
			$('#edit-trialtype-modal tbody').empty();
			for(c=0;c<custcol.length;c++)
				$('#edit-trialtype-modal tbody').append('<tr><td>' + custcol[c].titulo + '</td><td>' + custcol[c].descripcion + '</td><td>'
					+ i18n(custcol[c].obligatorio==1?'msg_mandatory':'msg_optional')
					+ '<td class="tac"><button class="btn-icon-base asLink" type="button" title="' + i18n('msg_edit') + '" onclick="editColumn(this,\'edit\'\);"'
					+ ' data-colid="' + custcol[c].customcolumnid + '" data-title="' + custcol[c].titulo + '" data-info="' + custcol[c].descripcion
					+ '" data-req="' + (custcol[c].obligatorio==1?'true':'false') + '" data-titleen="' + custcol[c].tituloen
					+ '" data-infoen="' + custcol[c].descripcionen + '" data-length="' + custcol[c].longitud
					+ '" data-needded="' + custcol[c].masdeuno + '"><i class="md md-edit"></i></button></td>'
					+ '<td class="tac"><button class="btn-icon-base asLink" type="button" title="' + i18n('msg_delete')
					+ '" data-title="'+ custcol[c].titulo + '" onclick="removeColumn(this,\'edit\');"><i class="md md-delete"></i></button></td></td></tr>');
			getWayByActionId($('#editAccion li.selected').val(),'editactionway');
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_trialtype'),"error");
		}
	});
};

function updateTrialType(){
	var err='',tag='',descr=($('#edittrialtype').val()).trim(),descren=($('#edittrialtypeen').val()).trim(),
		generaltrialid=$('#editgeneraltrialid').val(),matter=$('#editmatter li.selected').val(),
		reqactor= $("#editrequiresactor").prop('checked'),reqdef= $("#editrequiresdefendant").prop('checked'),
		reqthird= $("#editrequiresthird").prop('checked'),actionid = $('#editAccion li.selected').val();
	if(matter=='' || matter==null){
		tag='editmatter';
		err=i18n('err_select_matter');
	}else if(actionid=='' || actionid==null){
		tag='editAccion';
		err=i18n('err_select_accion');
	}else if(descr==''){
		tag='edittrialtype';
		err=i18n('err_enter_trialtype');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnedit').css('display','block');
		$('#putErrorOnedit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var rows = $('#edit-manage-columnsdata tbody tr'), allColumns = '';
	for(i=0; i<rows.length;i++){
		var coldata = $('#edit-manage-columnsdata tbody tr:eq(' + i + ') td:eq(3) button');
		allColumns+=(coldata.data('colid')==null?0:coldata.data('colid')) +'|'
			+ coldata.data('title') +'|' + coldata.data('info') +'|' + coldata.data('req') +'|'
			+ coldata.data('titleen') +'|' + coldata.data('infoen') +'|'
			+ coldata.data('length') +'|' + coldata.data('needded') +'~';
	//FIXME cambiar method por "name=" v.254
	}
	var param='tipojuicioid='+$('#edittrialTypeId').val() + '&tipojuicio='+descr
		+ '&tipojuicioen='+descren + '&matterid='+matter + '&actiontrialid='+actionid
		+ '&generaltrialid='+generaltrialid + '&reqactor='+(reqactor*1) + '&reqdef='+(reqdef*1)
		+ '&reqthird='+(reqthird*1)	+ '&allColumns='+allColumns.replace(/.$/,'');
	$.ajax({
		type:"POST",
		url:ctx+"/updateTrialType",
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

function deleteTrialType(id){
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
				url:ctx+"/deleteTrialType",
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

function addoncolumn(e,addEdit,action){
	var finishsetup='',
		addonLeft='<div class="input-group" data-group="addon" >'
			+'<span class="input-group-addon asLink" onclick="addoncolumn(this,\'' + addEdit + '\',\'remove\');">-</span>',
		addonRigth='<span class="input-group-addon asLink" data-group="addon" onclick="addoncolumn(this,\'' + addEdit + '\',\'insert\');">+</span></div>',
		baseTop='<div class="col-xs-12" data-' + addEdit + 'column="length"><div class="form-group inlineflex w100p">',
		baseMiddle='<label for="' + addEdit + 'samplecolumn" class="supLlb" data-' + addEdit + 'column="title">' + i18n('msg_title') + '</label>'
			+'<input class="form-control c39c" id="' + addEdit + 'samplecolumn" data-' + addEdit + 'column="input" placeholder="Descripción" autocomplete="off" type="text">',
		baseBottom='</div></div>';
	finishsetup = baseTop + ($('#' + addEdit + 'moreasneedded').prop('checked')?addonLeft + baseMiddle + addonRigth:baseMiddle) + baseBottom;
	if(action=='append'){
		$('#' + addEdit + 'groupsample').append(finishsetup);
	}else if(action=='insert'){
		$('#' + addEdit + 'groupsample').append(finishsetup);
	}else if(action=='remove'){
		var gr=$('.input-group-addon');
		if(gr.length>2)
			$(e).parent().parent().parent().remove();
	}
	var ln = $('#' + addEdit + 'lencolumn').val();
	if(ln<1)ln=1;
	else if(ln>12)ln=12;
	$('[data-' + addEdit + 'column="length"]').removeClass();
	$('[data-' + addEdit + 'column="length"]').addClass('col-xs-12 col-sm-' + ln);
	$('[data-' + addEdit + 'column="title"]').html($('#' + addEdit + 'titlecolumn').val());
};

/** Obtiene la configuración del campo (es obligatorio que exista uno de estos valores: "locUsrid" o "extUsr").
@param	addEdit		{string}	Indica el modal de donde será aplicado: "add" o "edit".	*/
function editColumn(e, addEdit){
	var titlecol = $(e).data('title'),descrcol = $(e).data('info'),reqcol = $(e).data('req'),
		titlecolen = $(e).data('titleen'),descrcolen = $(e).data('infoen'),
		lencol = $(e).data('length'),needded = $(e).data('needded');
	$('#' + addEdit + 'rowidx').val($(e).parent().parent().index());
	$('#' + addEdit + 'titlecolumn').val(titlecol);
	$('#' + addEdit + 'descriptioncolumn').val(descrcol);
	$('#' + addEdit + 'requiredcolumn').prop('checked', reqcol);
	$('#' + addEdit + 'titlecolumnen').val(titlecolen);
	$('#' + addEdit + 'descriptioncolumnen').val(descrcolen);
	$('#' + addEdit + 'lencolumn').val(lencol);
	$('#' + addEdit + 'moreasneedded').prop('checked', needded);
	$('#' + addEdit + 'samplecolumn').attr('placeholder', descrcol);
	$('#' + addEdit + 'samplecolumn').prop('title', descrcol);
	$('#' + addEdit + 'samplecolumn').parent().parent().removeClass();
	$('#' + addEdit + 'samplecolumn').parent().parent().addClass('col-xs-12 col-sm-' + lencol);
	$('[data-customcol=' + addEdit + ']').show('fast');

	var addonLeft='<div class="input-group" data-group="addon" >'
			+'<span class="input-group-addon asLink" onclick="addoncolumn(this,\'' + addEdit + '\',\'remove\');">-</span>',
		addonRigth='<span class="input-group-addon asLink" data-group="addon" onclick="addoncolumn(this,\'' + addEdit + '\',\'insert\');">+</span></div>',
		baseTop='<div class="col-xs-12" data-' + addEdit + 'column="length"><div class="form-group inlineflex w100p">',
		baseMiddle='<label for="' + addEdit + 'samplecolumn" class="supLlb" data-' + addEdit + 'column="title">' + i18n('msg_title') + '</label>'
			+'<input class="form-control c39c" id="' + addEdit + 'samplecolumn" data-' + addEdit + 'column="input" placeholder="Descripción" autocomplete="off" type="text">',
		baseBottom='</div></div>',
		allinputs = $('[data-' + addEdit + 'column="input"]'),finishsetup = '';
	for(i=0;i<allinputs.length;i++){
		var val=allinputs[i].value;
		var column = '';
		if(column.indexOf('value=')>0)
			column = baseMiddle.replace('value="(.*)"','value="$1"');
		else if(val=='' || val==null)
			column = baseMiddle;
		else
			column = baseMiddle.replace('<input ','<input value="' + val + '" ');
		finishsetup=baseTop + ($('#' + addEdit + 'moreasneedded').prop('checked')?addonLeft + column + addonRigth:column) + baseBottom;
	}
	if(finishsetup=='')
		finishsetup=baseTop + column + baseBottom;
	$('#' + addEdit + 'groupsample').html(finishsetup);
	var ln = $('#' + addEdit + 'lencolumn').val();
	if(ln<1)ln=1;
	else if(ln>12)ln=12;
	$('[data-' + addEdit + 'column="length"]').removeClass();
	$('[data-' + addEdit + 'column="length"]').addClass('col-xs-12 col-sm-' + ln);
	$('[data-' + addEdit + 'column="title"]').html($('#' + addEdit + 'titlecolumn').val());
	document.getElementById(addEdit + 'titlecolumn').focus();
//TODO
//var pos=$('#' + addEdit + '-manage-columnsdata').position();
//$('#' + addEdit + '-trialtype-modal').animate({scrollTop:0},1000);
};

function removeColumn(e,addEdit){
	var titleCol = $(e).data('title');
	swal({
		title:i18n('msg_are_you_sure'),
		text:i18n('msg_sure_to_delete') + '\n"' + titleCol + '"',
		type:"warning",
		showCancelButton:true,
		confirmButtonClass:'btn-warning',
		confirmButtonText:i18n('btn_yes_delete_it'),
		closeOnCancel:false
	}).then((isConfirm) => {
		if(isConfirm){
			$('[data-customcol="' + addEdit + '"]').hide();
			$(e.parentElement.parentElement).remove();
		}else{
			swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "warning");}
	});
};

$('#addtitlecolumn, #edittitlecolumn').on('keyup', function(e){
	var addEdit = this.id.indexOf('add')>=0?'add':'edit';
	$('[data-' + addEdit + 'column="title"]').html($(this).val());
});

$('#adddescriptioncolumn, #editdescriptioncolumn').on('keyup', function(e){
	var addEdit = this.id.indexOf('add')>=0?'add':'edit';
	$('#' + addEdit + 'samplecolumn').attr('placeholder', $(this).val());
	$('#' + addEdit + 'samplecolumn').attr('title', $(this).val());
});

$('#addlencolumn, #editlencolumn').on('keyup input paste change delete', function(e){
	var ln=$(this).val(), addEdit = this.id.indexOf('add')>=0?'add':'edit';
	if(ln<1){
		ln=1;
		$(this).val(ln);
	}else if(ln>12){
		ln=12;
		$(this).val(ln);
	}
	$('[data-' + addEdit + 'column="length"]').removeClass();
	$('[data-' + addEdit + 'column="length"]').addClass('col-xs-12 col-sm-' + ln);
	$('[data-' + addEdit + 'column="title"]').html($('#' + addEdit + 'titlecolumn').val());
});

$('#addColumnData, #editColumnData').on('click', function(){
	var addEdit = this.id.indexOf('add')>=0?'add':'edit';
	$('#' + addEdit + 'titlecolumn').val('T\u00edtulo');
	$('#' + addEdit + 'descriptioncolumn').val('Descripci\u00f3n');
	$('#' + addEdit + 'rowidx, #' + addEdit + 'titlecolumnen, #' + addEdit + 'descriptioncolumnen').val('');
	$('#' + addEdit + 'requiredcolumn, #' + addEdit + 'moreasneedded').prop('checked', false);
	$('#' + addEdit + 'lencolumn').val('6');
	$('[data-customcol=' + addEdit + ']').fadeIn('fast', function(){});
	$('#' + addEdit + 'samplecolumn').attr('placeholder', i18n('msg_description'));
	$('#' + addEdit + 'samplecolumn').attr('title', i18n('msg_description'));
	$('#' + addEdit + 'samplecolumn').parent().parent().removeClass();
	$('#' + addEdit + 'samplecolumn').parent().parent().addClass('col-xs-12 col-sm-6');
	var ln = $('#' + addEdit + 'lencolumn').val();
	if(ln<1)ln=1;
	else if(ln>12)ln=12;
	$('[data-' + addEdit + 'column="length"]').removeClass();
	$('[data-' + addEdit + 'column="length"]').addClass('col-xs-12 col-sm-' + ln);
	$('[data-' + addEdit + 'column="title"]').html($('#' + addEdit + 'titlecolumn').val());
	document.getElementById(addEdit + 'titlecolumn').focus();
//TODO
	//var pos=$('#' + addEdit + '-manage-columnsdata').position();
	//$('#' + addEdit + '-trialtype-modal').animate({scrollTop:0},1000);
});

$('#addmoreasneedded, #editmoreasneedded').on('click', function(){
	var addEdit = this.id.indexOf('add')>=0?'add':'edit';
	var addonLeft='<div class="input-group" data-group="addon" >'
			+'<span class="input-group-addon asLink" onclick="addoncolumn(this,\'' + addEdit + '\',\'remove\');">-</span>',
		addonRigth='<span class="input-group-addon asLink" data-group="addon" onclick="addoncolumn(this,\'' + addEdit + '\',\'insert\');">+</span></div>',
		baseTop='<div class="col-xs-12" data-' + addEdit + 'column="length"><div class="form-group inlineflex w100p">',
		baseMiddle='<label for="' + addEdit + 'samplecolumn" class="supLlb" data-' + addEdit + 'column="title">' + i18n('msg_title') + '</label>'
			+'<input class="form-control c39c" id="' + addEdit + 'samplecolumn" data-' + addEdit + 'column="input" placeholder="Descripción" autocomplete="off" type="text">',
		baseBottom='</div></div>',
		allinputs = $('[data-' + addEdit + 'column="input"]'),finishsetup = '';
	for(i=0;i<allinputs.length;i++){
		var val=allinputs[i].value;
		var column = '';
		if(column.indexOf('value=')>0)
			column = baseMiddle.replace('value="(.*)"','value="$1"');
		else if(val=='' || val==null)
			column = baseMiddle;
		else
			column = baseMiddle.replace('<input ','<input value="' + val + '" ');
		finishsetup=baseTop + ($(this).prop('checked')?addonLeft + column + addonRigth:column) + baseBottom;
	}
	if(finishsetup=='')
		finishsetup=baseTop + baseMiddle + baseBottom;
	$('#' + addEdit + 'groupsample').html(finishsetup);
	var ln = $('#' + addEdit + 'lencolumn').val();
	if(ln<1)ln=1;
	else if(ln>12)ln=12;
	$('[data-' + addEdit + 'column="length"]').removeClass();
	$('[data-' + addEdit + 'column="length"]').addClass('col-xs-12 col-sm-' + ln);
	$('[data-' + addEdit + 'column="title"]').html($('#' + addEdit + 'titlecolumn').val());
});

$('#addtypecolumn, #edittypecolumn').on('change', function(){
	var inputtype = $(this).val();
	if(inputtype=='checkbox'||inputtype=='radio'){
		$('#addrequiredcolumn').prop('check',false)
	}
	$('#addsamplecolumn')[0].type=inputtype;
});

$('#addInsertColumn, #editInsertColumn').on('click', function(){
	var err='', addEdit = this.id.indexOf('add')>=0?'add':'edit';
	var rowidx = $('#' + addEdit + 'rowidx').val(),
		titlecol=$('#' + addEdit + 'titlecolumn').val(),
		infocol=$('#' + addEdit + 'descriptioncolumn').val(),
		reqcol=$('#' + addEdit + 'requiredcolumn').prop('checked'),
		titlecolen=$('#' + addEdit + 'titlecolumnen').val(),
		infocolen=$('#' + addEdit + 'descriptioncolumnen').val(),
		lencol=$('#' + addEdit + 'lencolumn').val(),
		needded=$('#' + addEdit + 'moreasneedded').prop('checked');
	if(titlecol == ''){
		$('#' + addEdit + 'titlecolumn').parent().addClass('has-error');
		err = 'err_enter_title';
	}
	if(err != ""){
		$('#errorOn' + addEdit).show();
		$('#putErrorOn' + addEdit).html(i18n(err));
		$('.custombox-modal-open').animate({scrollTop : 0}, '1000');
		return;
	}
	$('#errorOn' + addEdit).hide();
	if(rowidx==''){
		$('#' + addEdit + '-trialtype-modal tbody').append('<tr><td>' + titlecol + '</td><td>' + infocol + '</td><td>'
			+ i18n(reqcol?'msg_mandatory':'msg_optional')
			+ '<td class="tac"><button class="btn-icon-base asLink" type="button" title="' + i18n('msg_edit')
			+ '" onclick="editColumn(this,\'' + addEdit + '\'\);" data-title="' + titlecol + '" data-info="' + infocol
			+ '" data-req="' + reqcol + '" data-titleen="' + titlecolen + '" data-infoen="' + infocolen + '" data-length="'
			+ lencol + '" data-needded="' + needded + '"><i class="md md-edit"></i></button></td>'
			+ '<td class="tac"><button class="btn-icon-base asLink" type="button" title="' + i18n('msg_delete')
			+ '" data-title="'+ titlecol + '" onclick="removeColumn(this,\'' + addEdit + '\');">'
			+ '<i class="md md-delete"></i></button></td></td></tr>');
	}else{
		var modifydata = '#' + addEdit + '-manage-columnsdata tbody tr:eq(' + rowidx + ') td:eq';
		$(modifydata + '(0)').html(titlecol);
		$(modifydata + '(1)').html(infocol);
		$(modifydata + '(2)').html(i18n(reqcol?'msg_mandatory':'msg_optional'));
		$(modifydata + '(3) button').data('title',titlecol).data('info',infocol).data('req',reqcol)
			.data('titleen',titlecolen).data('infoen',infocolen).data('length',lencol).data('needded',needded);
	}
	$('#' + addEdit + 'rowidx').val('');
	$('[data-customcol=' + addEdit + ']').fadeToggle('fast', function(){});
});

$("#addNewTrialType").click(function(){
	$('#errorOnadd, [data-customcol="add"], #setway, #errorOnadd, [data-customcol="add"]').hide();
	$('#add-trialtype-modal tbody, #add-trialtype-modal ul').empty();
	$('#add-trialtype-modal input[type=text]').val('');
	$('#addactionway').html('');
	$('#formaddtrialtype:input[type=radio], #formaddtrialtype:input[type=checkbox]').prop('checked', false);
	$("#addNewTrialType").attr('href','#add-trialtype-modal');
	getMaterias('addmatter','ul');
});

/**
@param Id				Id del tag donde se cargará la lista.
@param matteridElem 	Nombre del tag de materia para obtener el valor.
						(este nombre necesitará de "#" y/o "li.selected").
@param selType			Tipo de listado a cargar: "select", "ul", "ol".
@param addEdit			Módulo de donde fue llamado "add" o "edit"	*/
function getAccionByMatterId(id, matteridElem, selType, addEdit) {
	var elemOp = 'option', matterid = $(matteridElem).val();
	if (selType == 'ul' || selType == 'ol')
		elemOp = 'li';
	if(!(/^[1-9][0-9]*$/.test(matterid)))
		matterid=0;
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getAccionByMatterId",
		data : 'matterid='+matterid, 
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (selType == 'select' || selType == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].accionid + '" title="'+ info[i].descripcion
						+ '" onclick="getWayByActionId(value,\'' + addEdit + 'actionway\');">'
						+ info[i].descripcion + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Accion\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
			}else{
				$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_no_data_matter') + '</'+ elemOp + '>'
					+'<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Accion\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_trialtype')+'. '+e);
		}
	});
};

/**
@param actionid		Valor 'accionid'.
@param wayidElem	Nombre del elemento a colocar el texto de la vía.	*/
function getWayByActionId(actionid, wayidElem) {
	$('#' + wayidElem).html('');
	if (actionid=='' || actionid==null)
		return;
	$.ajax({
		type : "POST",
		url : ctx + "/getWayByActionId",
		data : 'id='+actionid, 
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info!=null && info.length > 0)
				$('#' + wayidElem).html(info[0].via);
		},error : function(e) {
			console.log(i18n('err_unable_get_way')+'. '+e);
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
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].viaid + '" title="'+ info[i].via + '">'
						+ info[i].via + '</' + elemOp+ '>');
				$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Way\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
			}else{
				$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_no_data') + '</'+ elemOp + '>'
					+'<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Way\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_way')+'. '+e);
		}
	});
};

/**
@param id		{int} Id del elemento 'ul', 'ol', 'select'.
@param seType	{string} Tipo de dato a agregar. Debe corresponder al mensaje
           		("msg_xxx") establecido en el título i18n.	*/
function openFilterSelModal(id, selType) {
	$('#errorOnAddFromFSel, #setway').hide();
	$('#addFrom-filtersel-modal input[type=text]').val('');
	$('#addFrom-filtersel-modal ul').empty();
	$('.titleFromFSel').html(i18n('msg_' + selType.toLowerCase()));
	$('#addFrom-filtersel-modal').modal('show');
	$('#descriptionFSel').focus();
	$('#selIdFSel').val(id);
	$('#selTypeFSel').val(selType);
	if(selType=='Accion')
		$('#setway').show();
};

function addFromFilterSel() {
	var id = $('#selIdFSel').val(), selType = $('#selTypeFSel').val(), addEdit = 'add';
	$('#errorOnAddFromFSel').hide();
	$('.titleFromFSel').html(i18n('msg_' + selType.toLowerCase()));
	$('#addFrom-filtersel-modal').modal('show');
	var act='addAccion', mat='addmatter', ae='add';
	if($('#edit-trialtype-modal').css('display')=='block')
		addEdit ='edit';
	var err='', param='',description = $('#descriptionFSel').val(),
		descriptionen = $('#descriptionFSelen').val(),
		matterid = $('#' + addEdit + 'matter li.selected').val(),
		way = $('#addway li.selected').val();
	matterid=matterid||0;
	if (description == '')
		err = 'msg_empty_data';
	param='description='+description;
	if (selType == 'Accion'){
		if (matterid==0)
			err = "err_select_matter";
		else
			param+='&descriptionen='+descriptionen + '&matterid='+matterid + '&wayid='+way;
		getAccionByMatterId(addEdit+'Accion', '#'+addEdit+'matter li.selected', 'ul', addEdit);
	}else if (selType == 'Way'){
		param+='&descriptionen='+descriptionen;
	}
	if (err != '') {
		$('#putErrorOnAddFromFSel').html(i18n(err));
		$('#errorOnAddFromFSel').show();
		return;
	}
	$.ajax({
		type : "POST",
		url : ctx + "/saveNew" + selType,
		data : param,
		async : false,
		success : function(data) {
			if (data == 'msg_data_saved') {
				swal({
					title : i18n('msg_success'),
					text : i18n('msg_data_saved'),
					type : "success",
					timer : 3000,
					allowEscapeKey : false
				}, function() {});
				window.setTimeout(function() {}, 3000);
				$('#addFrom-filtersel-modal').modal('hide');
				if (selType == 'Matter'){
					getMaterias(id, 'ul');
				}else if (selType == 'Accion'){
					getAccionByMatterId(addEdit+'Accion', '#'+addEdit+'matter li.selected', 'ul', addEdit);
					getWayByActionId($('#'+addEdit+'Accion li.selected').val(), addEdit+'actionway');
				}else if (selType == 'Way'){
					$('#' + addEdit + 'actionway').html(way);
					return;
				}
				$('#' + id).prev().prev().val(description);
				var fsel = $('#' + id + ' li');
				for (i = 0; i < fsel.length; i++)
					if (fsel[i].title == description.trim()) {
						$(fsel[i]).addClass('selected');
						break;
					}
			} else {
				$('#putErrorOnAddFromFSel').html(i18n(data));
				$('#errorOnAddFromFSel').show();
			}
		},
		error : function(e) {
			$('#putErrorOnAddFromFSel').html(i18n('err_record_no_saved'));
			$('#errorOnAddFromFSel').show();
		}
	});
};

$('input').blur(function(){
	$(this).parent().removeClass('has-error');
	var addEdit = 'add';
	if(this.id=='editAcccionText')
		addEdit='edit';
	getWayByActionId($('#'+addEdit+'Accion li.selected').val(),addEdit+'actionway');
});