;/**	Establece la fecha en los campos de bootstrap
* @param	e		{String}	Id input tag fecha
* @param	efix	{String}	Id input tag Fix fecha.
* @return	utdDate	{String} 	Fecha en formato UTC	*/
function setBootstrapUtcDate(e,efix,utdDate){
	if(utdDate==null||utdDate=='')return;
	var d=new Date(utdDate);
	var newD=d.getFullYear()+"-"+twoDigits(d.getMonth()+1)+"-"+twoDigits(d.getDate());
	$('#'+efix).val(setFormatPatternDate(d,getFormatPatternDate("")));
	$('#'+e).val(newD);
};

function getDetailsToEdit(id){
	$('#errorOnEditNtfy').hide();
	var id=$('#edNid').val();
	$('#edit-notification-modal input[type=text], #edit-notification-modal textarea').val('');
	if(id==''){
		$('#edNid').parent().addClass('has-error');
		swal(i18n('msg_warning'),i18n('err_unable_get_notifications'),"error");
		return false;
	}
	$.ajax({
		type:'POST',
		data:"id="+id,
		url:ctx+"/getNotificationById",
		data:'id='+id,
		async:false,
		success:function(data){
			var ntfy=data[0].ntfy, mod=data[0].mod, ct=data[0].clients;
			if(ntfy.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_notifications'),"error");
				return;
			}
			ntfy=ntfy[0];
			$('#editmoduleref, #editClient').html('');
			$('#editaction').val(ntfy.actiontypeid);
			setBootstrapUtcDate('editcapturedate','editcapturedateFix',ntfy.capturedate);
			for(m=0; m<mod.length; m++)
				$('#editmoduleref').append('<li value="'+ mod[m][0] + '" title="'+ mod[m][2] + '">'
					+ (mod[m][1]).replace(/\.jet$/,'') + '</li>');
			$('#editmoduleref').append('<li></li>');
			getTextDDFilterByVal('editmoduleref', ntfy.moduleref);
			
			$('#editrefid').val(ntfy.referenceid);
			$('#editreference').val(ntfy.reference);

			for(c=0; c<ct.length; c++)
				$('#editClient').append('<li value="'+ct[c][0] + '" title="'+ct[c][1] 
					+ ' ('+ct[c][2]+')'+'">' + ct[c][1]+'</li>');
			$('#editClient').append('<li></li>');
			getTextDDFilterByVal('editClient', ntfy.companyclientid);

			var url=new RegExp(location.pathname,'ig');
			var toUrl=(location.href).replace(url,'/aj/'+$('#editmoduleref li.selected').text());
			toUrl+=(((location.href).indexOf('?')>0)?'&':'?') + 'rid='+ntfy.referenceid;
			$("#editToLink").prop("href",toUrl);

			var nt=ntfy.actionsdetails;
			var jsonStr=JSON.parse(nt);
			$('#editActionsDetails').val(JSON.stringify(jsonStr,null,3));
			//$('#editActionsDetails').val(($('#editActionsDetails').val()).replace(/^\[(.*)\]$/,'$1'));

			nt=ntfy.confirmations;
			jsonStr=JSON.parse(nt);
			$('#editConfirmation').val(JSON.stringify(jsonStr,null,3));

/*			var ghij=ntfy.actionsdetails.replace(/\\/ig,"");
			jsonStr=JSON.parse(ghij);
			$('#editActionsDetails').val(JSON.stringify(jsonStr,null,3));
			//var klm=ntfy.confirmations.replace(/\\/ig,"")
			var klm=ntfy.confirmations
			jsonStr=JSON.parse(klm);
			$('#editConfirmation').val(JSON.stringify(jsonStr,null,3));*/
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_notifications'),"error");;
		}
	});
};

function updateNtfyDirect(){
	var err='', actiontypeid=$('#editaction').val(),
		capturedate=$('#editcapturedate').val(),
		moduleref=$('#editmoduleref li.selected').val(),
		referenceid=$('#editrefid').val(),
		reference=$('#editreference').val(),
		actionsdetails=$('#editActionsDetails').val(),
		confirmations=$('#editConfirmation').val(),
		ccid=$('#editClient li.selected').val();
	if(actiontypeid==''){
		tag='editaction';
	}else if(capturedate==''){
		tag='editcapturedate';
	}else if(moduleref==''){
		tag='editmoduleref';
	}else if(referenceid==''){
		tag='editrefid';
	}else if(reference==''){
		tag='editreference';
	}else if(actionsdetails==''){
		tag='editActionsDetails';
	}else if(confirmations==''){
		tag='editConfirmation';
	}else if(ccid==''){
		tag='editClient';
	}
	if(err!=''){
		$('#' + tag).parent().addClass('has-error');
		$('#puterrorOnEditNtfy').html(i18n('msg_empty_data'));
		$('#errorOnEditNtfy').show();
		$('.modal').animate({scrollTop:0},'1000');
		return false;
	}
	actionsdetails=JSON.stringify(actionsdetails);
	confirmations=JSON.stringify(confirmations);
	var param='nid='+$('#edNid').val() + '&actiontypeid='+actiontypeid
		+'&capturedate='+capturedate + '&moduleref='+moduleref
		+'&referenceid='+referenceid + '&reference='+reference
		+'&actionsdetails='+actionsdetails + '&confirmations='+confirmations
		+'&ccid='+ccid;
	$.ajax({
		type:"POST",
		url:ctx+"/updateNtfyDirect",
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
					location.href=location.href.replace(/^(.*)\#.*$/, '$1');
				});
				window.setTimeout(function(){
					location.href=location.href.replace(/^(.*)\#.*$/, '$1');
				},3000);
			}else{
				$('#putErrorOnEditProt').html(i18n('err_record_no_saved'));
				$('#errorOnEditProt').show();
			}
		},error:function(e){
			$('#putErrorOnEditProt').html(i18n('err_record_no_saved'));
			$('#errorOnEditProt').show();
		}
	});
};