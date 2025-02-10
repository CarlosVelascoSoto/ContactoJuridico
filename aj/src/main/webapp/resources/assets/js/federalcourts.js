function addCourt(e){
	var err='',descr=($('#court').val()).trim(),cdid=$('#citycourt li.selected').val();
	cdid=cdid||0;
	if(descr=='')
		err=i18n('msg_empty_data');
	else if(cdid==0)
		err=i18n('err_enter_city');
	if(err!=""){
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var param='description='+descr+'&cdid='+cdid+'&tipojuzgado=2';
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
	$('#errorOnEdit').hide();
	$.ajax({
		type:'POST',
		url:ctx+"/getCourtById",
		data:"id="+id,
		async:false,
		success:function(data){
			if(data[0].length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_court'),"error");
				return;
			}
			var info=data[0].info[0];
			var city=info.ciudadid;
			city=city||'';
			$('#edCourtId').val(info.juzgadoid);
			$('#edCourt').val(info.juzgado);
			if(city!='')city=info.ciudadid;
			getCiudades('edCitycourt');
			getTextDDFilterByVal('edCitycourt',city);
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_court'),"error");
		}
	});
};

function updateCourt(){
	var err='',descr=($('#edCourt').val()).trim(),cdid=$('#edCitycourt li.selected').val();
	cdid=cdid||0;
	if(descr=='')
		err=i18n('msg_empty_data');
	else if(cdid==0)
		err=i18n('err_enter_city');
	if(err!=""){
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		e.disabled=false;
		return false;
	}
	var param='id='+$('#edCourtId').val()+'&descr='+descr+'&cdid='+cdid+'&tipojuzgado=2';
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

$("#addNewCourt").click(function(){
	$('#errorOnAdd').css('display','none');
	$("#addNewCourt").attr('href','#court-modal');
	getCiudades('citycourt');
});