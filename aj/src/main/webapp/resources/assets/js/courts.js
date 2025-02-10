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
				$('#' + id).append('<'+ elemOp+ '></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_matter') + '. ' + e);
		}
	});
};

function addCourt(e){
	$('input').parent().removeClass('has-error');
	var err='',descr=($('#court').val()).trim(),cdid=$('#citycourt li.selected').val()||0,
		distpart=$('input[name="distpart"]:checked').val(),distpartnum=$('#distpartnum').val(),
		matter=$('#matter li.selected').val()||0,distrito='',partido=0,
		rowjudges=$('#addColumns tbody tr'),judges='';
	if(descr==''){
		tag='court';
		err=i18n('err_enter_court');
	}else if(distpart=='d' && distpartnum==''){
		tag='distpartnum';
		err=i18n('err_enter_districtnum');
	}else if(distpart=='p' && distpartnum==''){
		tag='distpartnum';
		err=i18n('err_enter_partynum');
	}else if(cdid==0){
		tag='citycourt';
		err=i18n('err_enter_city');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	if(rowjudges.length>0)
		for(j=0;j<rowjudges.length;j++)
			judges+=$(rowjudges[j]).find("td:eq(0)").html().trim()+'~';
	if(distpart=='d')distrito=distpartnum;
	else if(distpart=='p')partido=distpartnum;
	var param='description='+descr + '&tipojuzgado=1' + '&distrito='+distrito + '&partido='+partido
		+ '&materiaid='+matter + '&cdid='+cdid + '&judges='+judges.replace(/.$/g,'');
	$.ajax({
		type:"POST",
		url:ctx+"/saveNewCourt",
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
	$('#errorOnEditm').show();
	$('#editColumns tbody').html('');
	$.ajax({
		type:'POST',
		url:ctx+"/getCourtById",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].info[0]||[],judges=data[0].judges||[];
			if(info==null){
				swal(i18n('msg_warning'),i18n('err_unable_get_court'),"error");
				return;
			}
			getMaterias('editmatter', 'ul');
			getCiudades('editcitycourt','ul');
			var opt='',city=info.ciudadid,matter=info.materiaid,
				dpnum=info.distrito?(opt='district',info.distrito):(info.partido>0?(opt='party',info.partido):(opt='notdef',!1));
			$('#editcourtId').val(info.juzgadoid);
			$('#editcourt').val(info.juzgado);
			$('#editboxdistpartnum').css('display',(dpnum?'block':'none'));
			$('#edit'+opt).attr('checked','checked');
			$('#editdistpartnum').val(dpnum);
			if(matter)getTextDDFilterByVal('editmatter',matter);
			if(city)getTextDDFilterByVal('editcitycourt',city);
			$('#rowColAddEdit').val('edit');
			if(judges!=null){
				for(j=0;j<judges.length;j++){
					var newcol='<tr data-judgeid="'+judges[j].juezid+'"><td>'+judges[j].nombre+'</td>'
						+'<td class="tac"><span class="table-action-btn asLink" title="'+i18n('msg_edit')
						+'" onclick="editColumn(this);"><i class="md md-edit"></i></span></td>'
						+'<td class="tac"><span class="table-action-btn asLink" title="'+i18n('msg_delte')
						+'" onclick="delteColumn(this);"><i class="md md-delete"></i></span></td></tr>';
					$('#editColumns tbody:last-child').append(newcol);
				}
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_court'),'error');
		}
	});
};

function updateCourt(){
	$('input').parent().removeClass('has-error');
	var tag='',err='',descr=($('#editcourt').val()).trim(),cdid=$('#editcitycourt li.selected').val()||0,
		distpart=$('input[name="editdistpart"]:checked').val(),distpartnum=$('#editdistpartnum').val(),
		matter=$('#editmatter li.selected').val()||0,rowjudges=$('#editColumns tbody tr'),judges='';
	if(descr==''){
		tag='editcourt';
		err=i18n('err_enter_court');
	}else if(distpart=='d' && distpartnum==''){
		tag='editdistpartnum';
		err=i18n('err_enter_districtnum');
	}else if(distpart=='p' && distpartnum==''){
		tag='editdistpartnum';
		err=i18n('err_enter_partynum');
	}else if(cdid==0){
		tag='editcitycourt';
		err=i18n('err_enter_city');
	}
	if(err!=""){
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	if(rowjudges.length>0)
		for(j=0;j<rowjudges.length;j++){
			var jid=$(rowjudges[j]).data('judgeid');
			jid=jid==null?'':'|'+jid;
			judges+=$(rowjudges[j]).find("td:eq(0)").html().trim()+jid+'~';
		}
	var param='id='+$('#editcourtId').val() + '&descr='+descr + '&tipojuzgado=1' + '&distpart='+distpart
		+ '&distpartnum='+distpartnum + '&matter='+matter + '&cdid='+cdid + '&judges='+judges.replace(/.$/g,'');
	$.ajax({
		type:"POST",
		url:ctx+"/updateCourt",
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

function deleteCourt(id){
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
				url:ctx+"/deleteCourt",
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

function getCiudades(id){
	$('#' + id).empty();
	$.ajax({
		type:'POST',
		url:ctx+"/getCiudades",
		async:false,
		success:function(data){
			if(data[0].length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_city'),"error");
				return;
			}
			var cdList=data[0].cdList;
			for(i=0;i<cdList.length;i++)
				$('#'+id).append('<li value="'+cdList[i].ciudadid+'" title="'+cdList[i].ciudad+'"><span>'+cdList[i].ciudad+'</span></li>');
			$('#'+id).append('<li>&nbsp;</li>');
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_city'),"error");
		}
	});
};

function setDistPart(e,obj,addEdit){
	if($(e).prop('checked')){
		$('label[for='+addEdit+'distpartnum]').text(i18n('msg_numberof')+' '+($(e).parent().text().trim()));
		$('#'+addEdit+'boxdistpartnum').show();
		$('#'+addEdit+'distpartnum').val('');
	}
};

//	setCustColumnByTT(data[0].custCol, data[0].colvalues, tt, ttacc, 'edit');

/** Obtiene y despliega los campos de captura y sus correspondientes valores.
@param custcol		Lista de customNames.
@param ccvalues		Lista de valores customcolumn. Si no se requiere, colocar un Array vacío.
@param trialtype	Lista de "tipos de juicios".
@param addEdit		Modal de donde será aplicado: "add" o "edit".	*/
function setjudge(custcol, ccvalues, trialtype, addEdit) {
	$('#' + addEdit.toLowerCase() + 'Trialtypecolumns').empty();
	var i = 0, finishsetup = '', assignedvalue='0', ccvid=0, setComplete=0;
	setComplete=ccvalues.length>0?ccvalues.length:custcol.length;
	do{
		var idx=0;
		if(ccvalues.length>0){
			do{
				if(custcol[idx].customcolumnid==ccvalues[i].customcolumnid)
					break;
				idx++;
			}while(custcol[idx]!=null);
			ccvid = ccvalues[i].customcolumnvalueid;
			assignedvalue = ' value="' + ccvalues[i].assignedvalue + '"';
		}else{
			idx=i;
		}
		var titlecol = custcol[idx].titulo, descrcol = custcol[idx].descripcion, reqcol = custcol[idx].obigatorio,
			titlecolen=custcol[idx].tituloen,descrcolen=custcol[idx].descripcionen,lencol=custcol[idx].longitud,
			needded = custcol[idx].masdeuno, inputtype =custcol[idx].tipodecolumna;
		if (titlecolen == '')titlecolen = titlecol;
		if (descrcolen == '')descrcolen = descrcol;
		var addonLeft = '<div class="input-group" data-group="addon" >' + '<span class="input-group-addon asLink" onclick="judgecolumn(this,\''
				+ addEdit + '\',\'remove\');">-</span>',
			addonRigth = '<span class="input-group-addon asLink" data-group="addon" onclick="judgecolumn(this,\''+ addEdit + '\',\'insert\');">+</span></div>',
			baseTop = '<div class="col-xs-12 col-sm-'+ lencol+ '" data-'+ addEdit+ 'column="length"><div class="form-group inlineflex w100p">',
			baseMiddle = '<label class="supLlb" data-'+ addEdit+ 'column="title">' + ('en' == getLanguageURL() ? titlecolen: titlecol) + '</label>'
				+ '<input type="'+ inputtype + '" class="form-control c39c"'
				+ (ccvid>0?' data-' + addEdit + 'ccvalueid="' + ccvid + '"':'')
				+ ' data-'+ addEdit+ 'column="input"'
				+ ' data-'+ addEdit+ 'custcol="'+ custcol[idx].customcolumnid + '"'
				+ assignedvalue
				+ ' title="'+ ('en' == getLanguageURL() ? descrcolen: descrcol) + '"'
				+ ' placeholder="'+ ('en' == getLanguageURL() ? descrcolen: descrcol) + '"'
				+ ' autocomplete="off"'
				+ (reqcol == 0 ? '' : ' required') + '>',
			baseBottom = '</div></div>';
		finishsetup += baseTop + (needded == 1 ? addonLeft + baseMiddle + addonRigth : baseMiddle) + baseBottom;
		i++;
	}while(i<setComplete);
	$('#' + addEdit.toLowerCase() + 'Trialtypecolumns').append(finishsetup);
};

function fnSetColumn(){
	$('input').parent().removeClass('has-error');
	var err='',tag='',addEdit = $('#rowColAddEdit').val(),
		descr=$('#colDescription').val(), rcidx=$('#rowColumnsIdx').val();
	if(descr==''){
		tag='colDescription';
		err=i18n('msg_empty_data');
	}
	if(err!=''){
		$('#' + tag).parent().addClass('has-error');
		swal({title:i18n('msg_error'),text:err,type:"error"});
		return;
	}
	if(rcidx==''){
		var newcol='<tr><td>'+descr+'</td>'
			+'<td class="tac"><span class="table-action-btn asLink" title="'+i18n('msg_edit')
			+'" onclick="editColumn(this);"><i class="md md-edit"></i></span></td>'
			+'<td class="tac"><span class="table-action-btn asLink" title="'+i18n('msg_delte')
			+'" onclick="delteColumn(this);"><i class="md md-delete"></i></span></td></tr>';
		$('#'+addEdit+'Columns tbody:last-child').append(newcol);
	}else{
		$('#'+addEdit+'Columns tr:eq('+rcidx+') td:eq(0)').html(descr);
	}
	$('#column-modal').modal('toggle');
	$(rowColumnsIdx).val('');
};

function addcolumn(){
	$('#colDescription, #rowColumnsIdx').val('');
	$('#column-modal').modal('toggle');
};

function editColumn(obj){
	var descr=$(obj).parent().prev().html();
	$('#rowColumnsIdx').val(($(obj).closest('tr').index())+1);
	$('#colDescription').val(descr);
	$('#column-modal').modal('toggle');
};

function delteColumn(obj){
	var idx=($(obj).closest('tr').index())+1,addEdit=$('#rowColAddEdit').val();
	var descr=$('#'+addEdit+'Columns tr:eq('+idx+') td:eq(0)').html();
	swal({
		title:i18n('msg_delete'),
		text:i18n('msg_sure_to_delete')+' \n'+descr,
		type:"warning",
		showCancelButton:true,
		confirmButtonClass:'btn-warning',
		confirmButtonText:i18n('btn_yes_delete_it'),
		closeOnConfirm:false,
		closeOnCancel:false
	}).then((isConfirm) => {
		if(isConfirm){
			$('#'+($('#rowColAddEdit').val())+'Columns tr:eq('+idx+')').remove();
			swal(i18n('msg_deleted'), i18n('msg_record_deleted'), "success");
			swal.close();
		}else{
			swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "info");
		}
	});
};

$("#addNewCourt").click(function(){
	$('#errorOnAdd,#boxdistpartnum').hide();
	$("#addNewCourt").attr('href','#court-modal');
	getCiudades('citycourt');
	getMaterias('matter', 'ul');
	$('#rowColAddEdit').val('add');
	$('#addColumns tbody').html('');
});