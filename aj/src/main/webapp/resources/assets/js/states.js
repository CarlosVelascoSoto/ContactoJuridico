function getCountries(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getCountries",
		async : false,
		success : function(data) {
			var countries = data[0]||[];
			if (elemtype == 'select' || elemtype == '')
				$('#' + id).append('<' + elemOp + ' value="0" selected disabled>'+ i18n('msg_select') + '</' + elemOp + '>');
			for (i = 0; i < Object.keys(countries).length; i++) {
				$('#' + id).append('<' + elemOp + ' value="' + countries[i].paisid + '" title="'
					+ countries[i].pais + '">' + countries[i].pais + '</' + elemOp + '>');
			}
			$('#' + id).append('<' + elemOp + '></' + elemOp + '>');
		},
		error : function(e) {
			console.log(i18n('err_unable_get_country') + '. ' + e);
		}
	});
};

function addState(e){
	var err='',state=($('#state').val()).trim(),country=$('#country li.selected').val();
	country=country||'0';
	if(state=='' || country=='0')
		err=i18n('msg_empty_data');
	if(err!=""){
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='estado='+state+'&paisid='+country;
	$.ajax({
		type:"POST",
		url:ctx+"/addNewState",
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
		url:ctx+"/getStateById",
		data:"id="+id,
		async:false,
		success:function(data){
			var countries=data[0].countries||[],state=data[0].state||[];
			if(countries[0].length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_state'),"error");
				return;
			}
			getCountries('edCountry', 'ul');
			$('#edStateId').val(state[0].estadoid);
			$('#edState').val(state[0].estado);
			getTextDDFilterByVal('edCountry', state[0].paisid);
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_state'),"error");
		}
	});
};

function updateState(){
	var err='',state=($('#edState').val()).trim(),country=$('#edCountry li.selected').val();
	country=country||'0';
	if(state=='' || country=='0')
		err=i18n('msg_empty_data');
	if(err!=""){
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		e.disabled=false;
		return false;
	}
	var param='id='+$('#edStateId').val()+'&estado='+state+'&paisid='+country;
	$.ajax({
		type:"POST",
		url:ctx+"/updateState",
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

function deleteState(id){
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
				url:ctx+"/deleteState",
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

$("#addNewState").click(function(){
	$('#errorOnAdd').css('display','none');
	$("#addNewState").attr('href','#state-modal');
	getCountries('country', 'ul');
});