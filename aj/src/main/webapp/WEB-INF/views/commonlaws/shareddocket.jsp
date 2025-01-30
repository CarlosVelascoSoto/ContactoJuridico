<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script src="resources/assets/js/jquery.min.js"></script>
<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<!-- link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
 -->
<link rel="stylesheet" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">

<style>
	[data-edShared]{padding:10px}
	[data-edShared="2"],[data-edShared="3"]{display:none}
	input[name=shdsetUserFirm]{width:50px;}
	.boxupper{margin-bottom:30px;-webkit-box-shadow:0 0 15px #ccc;box-shadow:0 0 15px #ccc}
	#shdsetUserLocal,#shdsetUserExt{width:auto;height:auto;margin-right:5px}
	/*switches (ini)*/
	.b-chk{position:relative; width:100%; top:-10px; margin:10px;}
	.b-chkAll{
		position:relative;
		width:210px;
		height:25px;
		top:9px;
		margin:10px;
		display:inline-block;
	}
	.switch{
		position:relative;
		display:inline-block;
		width:40px;
		height:20px;
	}
	.slider{
		position:absolute;
		cursor:pointer;
		top:0;
		left:0;
		right:-5px;
		bottom:0;
		background-color:#ccc;
		-webkit-transition:.4s;
		transition:.4s;
	}
	.slider:before{
		position:absolute;
		content:"";
		height:12px;
		width:12px;
		left:3px;
		bottom:4px;
		background-color:white;
		-webkit-transition:.4s;
		transition:.4s;
	}
	.switch input{display:none;}
	.slider.round{border-radius:34px;}
	.slider.round:before{border-radius:50%;}
	input:checked ~ .slider{background-color:#9acd32;}
	input:focus ~ .slider{box-shadow:0 0 1px #9acd32;}
	input:checked ~ .slider:before{-webkit-transform:translateX(26px);
		-ms-transform:translateX(26px);transform:translateX(26px)
	}
	/*switches (fin)*/
	#shdk-modal{position:fixed;overflow:auto}
	.form-control[readonly]{background-color:transparent}
	.flat_input{border:1px solid transparent;background-color:transparent}
	.closeShared{font-size:20px;font-weight:900;position:absolute;width:30px;
		right:45px;border:1px solid transparent;-webkit-border-radius:50px;
		-moz-border-radius:50px;border-radius:50px;background-color:#fff;
		color:#000;z-index:1
	}
	.closeShared:hover{-webkit-box-shadow:0 0 10px #444;box-shadow:0 0 10px #444;
		border:1px solid #000;color:#39c
	}
</style>

<script src="resources/assets/js/complementos.js"></script>

<div class="row" data-edShared="1">
	<table id="setsp-datatable-buttons" data-order='[[0, "desc"]]' class="table table-striped table-bordered">
		<thead>
			<tr>
				<th class="tac"><fmt:message key="text.lawyerassigned" /></th>
				<th class="tac"><fmt:message key="text.firm" /></th>
				<th class="tac"><fmt:message key="text.viewdetails" /></th>
				<th class="tac shkdEd"><fmt:message key="text.edit" /></th>
				<th class="tac shkdDel"><fmt:message key="text.stopsharing" /></th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
</div>

<div class="row" data-edShared="1">
	<div class="col-xs-12">
		<div class="form-group inlineblock" style="float:right">
			<input type="button" class="btn btn-primary" id="shdsetAddUser" value="<fmt:message key="text.assignlawyer"/>">
		</div>
	</div>
</div>

<div class="row boxupper">
	<input type="hidden" id="shTrialid">
	<div data-edShared="2">
		<button type="button" class="m-0 closeShared trn2ms"
			onclick="$('[data-edshared=2]').hide();$('[data-edShared=1]').fadeIn();"
			title="<fmt:message key="button.close" />">
			&times;
		</button>
		<div class="row">
			<div class="col-xs-12 tac">
				<p><fmt:message key="text.lawyertoassign" /></p>
			</div>
			<div class="col-xs-12 col-sm-6">
				<input type="radio" class="form-control inlineblock" id="shdsetUserLocal" name="shdsetUserFirm" checked>
				<label for="shdsetUserLocal" class="asLink"><fmt:message key="text.internallawyer" /></label>
			</div>
			<div class="col-xs-12 col-sm-6">
				<input type="radio" class="form-control inlineblock" id="shdsetUserExt" name="shdsetUserFirm">
				<label for="shdsetUserExt" class="asLink"><fmt:message key="text.externaluserfirm" /></label>
			</div>
		</div>
		<div class="row" style="margin-top:20px">
			<div class="col-xs-12">
				<div class="form-group fieldinfo inlineblock w100p" data-shdsetUserFirm="loc">
					<input type="text" class="form-control listfiltersel"
						placeholder="Abogado de la misma firma" onfocus="$(this).select();">
					<i class="material-icons iconlistfilter">arrow_drop_down</i>
					<ul class="ddListImg noimgOnList" id="shdsetUserList"></ul>
				</div>
				<div class="form-group fieldinfo inlineblock w100p" data-shdsetUserFirm="ext">
					<input type="text" class="form-control c39c w100p" id="shdsetUserEmail"
						placeholder="<fmt:message key='text.user.email' />"autocomplete="off">
				</div>
			</div>
		</div>
		<div class="row" style="margin-top:20px">
			<div class="col-xs-12 col-sm-6">
				<input type="button" id="shdsetGo2_1" class="form-control c39c"
					value="<fmt:message key="button.close" />" style="position:relative;rigth:0">
			</div>
			<div class="col-xs-12 col-sm-6">
				<input type="button" id="shdsetGo3" class="form-control c39c"
					value="<fmt:message key="button.next" />" style="position:relative;rigth:0">
			</div>
		</div>
	</div>
	
	<div data-edShared="3">
		<button type="button" class="closeShared trn2ms" title="<fmt:message key="button.close" />"
			onclick="$('[data-edShared=3]').slideUp();$('[data-edShared=1]').fadeIn();">&times;
		</button>
		<div class="row">
			<div class="col-sm-12">
				<div class="table-responsive" style="margin:10px">
					<table id="table-setsp" data-order='[[0, "desc"]]' class="table table-striped table-bordered"></table>						
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-6">
				<input type="button" id="shdsetGo3_2" class="form-control c39c"
					value="<fmt:message key="button.previous" />" style="position:relative;float-left;buttom:0">
			</div>
			<div class="col-xs-12 col-sm-6">
				<input type="button" id="shdsetFinish" class="form-control c39c"
					value="<fmt:message key="button.finish" />" style="position:relative;float-rigth;buttom:0">
			</div>
		</div>
	</div>
</div>

<div id="shdkinfo-modal" class="sub-modal" style="display:none">
	<div class="modal-dialog">
		<button type="button" class="close" onclick="$('#shdkinfo-modal').modal('hide');" style="margin:10px;color:#fff">
			<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
		</button>
		<h4 class="custom-modal-title capitalize"><fmt:message key="text.sharetrialinfo" /></h4>
		<div class="custom-modal-text text-left" style="padding-top:0">
			<div class="panel-body">
				<div class="form-group" style="margin-bottom:0">
					<div class="row"><div class="col-xs-12"><h3 style="margin-bottom:20px"><fmt:message key='text.user.datashare' /></h3></div></div>
					<div class="row">
						<table id="dt-shareduser" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead></thead>
							<tbody>
								<tr>
									<td><fmt:message key='text.user.name' /></td>
									<td><input type="text" class="flat_input w100p" id="info_username" readonly></td>
								</tr>
								<tr>
									<td><fmt:message key='text.firm' /></td>
									<td><input type="text" class="flat_input w100p" id="info_companyname" readonly></td>
								</tr>
								<tr>
									<td><fmt:message key='text.emailaddress' />
										<br>(<fmt:message key='text.externalfirmonly' />)</td>
									<td><input type="text" class="flat_input w100p" id="info_email" readonly></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="row"><div class="col-xs-12"><h3 style="margin-bottom:20px"><fmt:message key='text.user.origindata' /></h3></div></div>
					<div class="row">
						<table id="dt-originuser" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead></thead>
							<tbody>
								<tr>
									<td><fmt:message key='text.userwhoshared' /></td>
									<td><input type="text" class="flat_input w100p" id="info_owneruser" readonly></td>
								</tr>
								<tr>
									<td><fmt:message key='text.firm' /></td>
									<td><input type="text" class="flat_input w100p" id="info_firmusershare" readonly></td>
								</tr>
								<tr>
									<td><fmt:message key='text.dateshared' /></td>
									<td><input type="text" class="flat_input w100p" id="info_shareddate" readonly></td>
								</tr>
								<tr>
									<td><fmt:message key='text.confirmationdate' /></td>
									<td><input type="text" class="flat_input w100p" id="info_confirmationdate"
										title="<fmt:message key='text.confirm.dateinfo' />" readonly></td>
								</tr>
								<tr>
									<td><fmt:message key='text.notificationdate' /></td>
									<td><input type="text" class="flat_input w100p" id="info_notificationdate"
										title="<fmt:message key='text.notif.shareddateinfo' />" readonly></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="$('#shdkinfo-modal').modal('hide');" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>

<script>
	function populateSP(){
		$.ajax({
			type:'POST',
			url:ctx+"/getSubPrivileges",
			async:false,
			success:function(data){
				var mp=data[0].mp,
					subpriv=data[0].subpriv,
					priv=data[0].priv,
					datarow='<caption class="tac" style="color:dodgerblue">'+i18n('msg_privileges')+'</caption><thead><tr>'
						+'<th style="text-align:left">'+i18n('msg_options_todo')+'</th>'
				$('#table-setsp').html('');
				if(subpriv.length==0)return;
				for(i=0;i<priv.length;i++){
					datarow+='<th class="tac">'+(i==0?'Agregar':priv[i].privilege)+'<br>'
						+'<label class="switch">'
						+'<input type="checkbox" name="setsp_'+priv[i].privilegesid+'" id="setsp_0_'+priv[i].privilegesid
						+'" onclick="checksp(id,\'setsp_0_'+priv[i].privilegesid+'\');"><span class="slider round"></span></label></th>';
				}
				datarow+='</tr></thead><tbody>';
				for(s=0;s<subpriv.length;s++){
					datarow+='<tr><td>'+subpriv[s].menu+'</td>';
					for(p=0;p<priv.length;p++){
						var ischeck='0';
						for(m=0;m<mp.length;m++)
							if(mp[m].menuid==subpriv[m].menuid && mp[m].privilegesid==priv[p].privilegesid)
								ischeck='1';
						datarow+='<td><label class="switch">'
							+'<input type="checkbox" name="setsp_'+priv[p].privilegesid+'" id="setsp_'
							+ subpriv[s].menuid+'_'+priv[p].privilegesid
							+'" onclick="checksp(id,\'setsp_0_'+priv[p].privilegesid+'\');" '+(ischeck==0?'':'ckecked')
							+'><span class="slider round"></span></label></td>';
					}
					datarow+='</tr>';
				}
				$('#table-setsp').append(datarow+'</tr></tbody>');
			},error:function(resp){
				swal(i18n('msg_warning'), i18n('text.subprivileges'),"warning");
			}
		});
	};
	
	/** Obtiene la configuración de permisos del usuario
		(es obligatorio que exista uno de estos valores: "locUsrid" o "extUsr").
		@param	locUsrid	{id}		(Opcional) Id del usuario de la firma local.
		@param	extUsr		{string}	(Opcional) Email del usuario de la firma externa.
		@param	data		{string}	Configuración de privilegios.	*/
	function retrieveSP(locUsrid,extUsr,data){
		$('[data-edShared]').slideUp();
		$('input [type=checkbox][name*="sp_"]').prop("checked",false);
		$('#shdsetAddUser').trigger('click');
		$('[data-shdsetuserfirm]').hide();
		if(/([0]|[^0-9]+)/g.test(extUsr)){
			$('#shdsetUserExt').prop('checked',true);
			$('#shdsetUserEmail').val(extUsr);
			sessionStorage.setItem('reSet','[data-spuser-set="ext='+extUsr+'"]');
			$('[data-shdsetuserfirm="ext"]').show();
		}else{
			$('#shdsetUserLocal').prop('checked',true);
			getTextDDFilterByVal('shdsetUserList',locUsrid);
			sessionStorage.setItem('reSet','[data-spuser-set="loc='+locUsrid+'"]');
			$('[data-shdsetuserfirm="loc"]').show();
		}
		var allsp=data.split('|');
		for(p=0;p<allsp.length;p++){
			var sptmp0=allsp[p].split('=');
			var sptmp1=sptmp0[0].split('_');
			var spid=sptmp1[0],prvid=sptmp1[1],status=sptmp0[1];
			$('#setsp_'+spid+'_'+prvid).prop('checked',(status=='true'?!0:!1));
		}
	};
	
	function checksp(id,allchks){
		var namesp=$('#'+id).attr('name');
		if(/_0_/.test(id))
			$('input[name="'+namesp+'"]').prop('checked',$('#'+id).is(':checked'));
		var chlg=$('input[name="'+namesp+'"]:checked').length;
		$('#'+allchks).next().css('background-color',(chlg==$('input[name="'+namesp+'"]').length)?'#9acd32':chlg==0?'#ccc':'#20b2aa');
	};
	
	function deletesp(o){
		swal({
			title:i18n('msg_stop_sharing'),
			text:i18n('msg_info_stop_sharing'),
			type:"warning",
			showCancelButton:true,
			confirmButtonClass:'btn-warning',
			confirmButtonText:i18n('btn_yes_continue'),
			closeOnCancel:false
		}).then(function(isConfirm) {
			if(isConfirm){
				$('[data-edshared=2]').slideUp('fast');
//				$(o.parentElement.parentElement).remove();
				$(o).parent().parent().remove();
			}else{
				swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "warning");
			}
		});
	};

function getDetailsSP(data){
		var shdk=data[0].shdk,shpriv=data[0].shpriv;
		$('#setsp-datatable-buttons tbody').html('');
		$('[data-edshared]').hide();
		$('[data-edshared="1"]').show();
		if(shdk==null)return;
		for(s=0;s<shdk.length;s++){
			var locUsrId=shdk[s].userid,locUsrName='',extUsr=shdk[s].emailexternaluser,allspriv='',datarow='';
			getLawyerList('shdsetUserList','ul',0);
			if(extUsr!=''){
				$('#shdsetUserExt').prop('checked',true);
				$('#shdsetUserEmail').val(extUsr);
			}else{
				$('#shdsetUserLocal').prop('checked',true);
				getTextDDFilterByVal('shdsetUserList', locUsrId);
				locUsrName=$('#shdsetUserList li.selected').text();
			}
			for(p=0;p<shpriv.length;p++){
				var tmpSD=shpriv[p].split('|');
				var shdkid=tmpSD[0],prv=tmpSD[1],mid=tmpSD[2];
				if(shdk[s].shareddocketid != (shdkid*1))
					continue;
				allspriv+=mid+'_'+prv+'=true|';
/*				if(shdk[s].emailexternaluser!=''&&shdk[s].userid)
					allspriv+='_'+shdk[s].userid;
				allspriv+='|';*/
			}
			allspriv=allspriv.replace(/\|$/,'');
			//datarow+='<tr data-spuser-set=>'
			datarow+='<tr>'
				+'<td><span data-spuser-set="'+(extUsr!=''?'ext='+extUsr+'">'+extUsr:'loc='+locUsrId+'">'+locUsrName)+'</span></td>'
				+'<td><span>'+i18n((extUsr!=''?'msg_external':'msg_local'))+'</span></td>'
				+'<td class="tac"><span class="asLink" onclick="getSharedInfo('+locUsrId+',\''+extUsr+'\')">'
				+ '<i class="fa fa-vcard-o" style="font-size:20px"></i></span></td>'
				+'<td class="tac shkdEd"><span class="asLink" data-allsp="set"'
				+ ' onclick="retrieveSP(\''+locUsrId+'\',\''+extUsr+'\',\''+allspriv+'\');">'
				+ '<i class="md md-edit"></i></span></td>'
				+'<td class="tac shkdDel"><span class="asLink" onclick="deletesp(this);"><i class="md md-delete" style="font-size:20px"></i></span></td></tr>';
			$('#setsp-datatable-buttons').append(datarow);
		}
		getTextDDFilterByVal('shdsetUserList','0');
	};

	$('#shdsetAddUser').on('click', function(){
		var pos=$('#setsp-datatable-buttons').position();
		$('#edit-modal').animate({scrollTop:pos.top},1000);
		$('#shdsetUserLocal').prop('checked',true);
		getLawyerList('shdsetUserList','ul',0);
		getTextDDFilterByVal('shdsetUserList','');
		$('input[name^=setsp_]').prop('checked',false);
		$('[data-shdsetUserFirm="ext"]').hide();
		$('[data-edShared="1"]').slideUp();
		$('[data-edShared="2"]').slideDown();
		$('[data-shdsetUserFirm="loc"]').show();
		populateSP();
		if(sessionStorage.getItem('reSet')!=null){
			$('[data-shdsetuserfirm]').hide();
			$('[data-shdsetuserfirm="loc"]').show();
		}
	});

	$('input[name=shdsetUserFirm]').on('change',function(e){
		if(this.id=='shdsetUserLocal'){
			$('[data-shdsetuserfirm="ext"]').fadeOut();
			$('[data-shdsetuserfirm="loc"]').fadeIn();
		}else{
			$('[data-shdsetuserfirm="loc"]').fadeOut();
			$('[data-shdsetuserfirm="ext"]').fadeIn();
			$('#shdsetUserEmail').focus();
		}
	});

	$('#shdsetGo2_1').on('click', function(){
		$('[data-edShared="2"]').hide();
		$('[data-edShared="1"]').fadeIn();
		sessionStorage.removeItem('reSet');
	});
	$('#shdsetGo3').on('click', function(){
		var err='',reSet=sessionStorage.getItem('reSet');
		if($('#shdsetUserLocal').is(':checked')){
			if(($('#shdsetUserList li.selected').length)!=1)
				err='err_select_option_list';
			else
				if(reSet==null){
					var idExists=$('#shdsetUserList li.selected').val();
					if($('[data-spuser-set="loc='+idExists+'"]').length>0)
						err='err_user_exists';
				}
		}else if($('#shdsetUserExt').is(':checked')){
			if(($('#shdsetUserEmail').val())==''||
				!/^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/.test($('#shdsetUserEmail').val())
				){
				err='err_enter_email';
				$('#shdsetUserEmail').parent().addClass('has-error')
			}else{
				if(reSet==null){
					var idExists=$('#shdsetUserEmail').val();
					if($('[data-spuser-set="ext='+idExists+'"]').length>0)
						err='err_user_exists';
				}
			}
		}
		if(err!=''){
			swal(i18n('msg_error'),i18n(err),'error');
			return;
		}
		$('[data-edShared="2"]').slideUp();
	    $('[data-edShared="3"]').slideDown();
	});
	$('#shdsetGo3_2').on('click', function(){
		$('[data-edShared="3"]').slideUp();
	    $('[data-edShared="2"]').slideDown();
	});
	$('#shdsetFinish').on('click', function(){
		$('[data-edShared="3"]').hide();
		$('[data-edShared="1"]').fadeIn();
		var trEdited=null,locUsrId='',locUsrName='',extUsr='',allEditsp=$('input[name^="setsp_"]:checked'),
			allspriv='',datarow='',reSet=sessionStorage.getItem('reSet'),noName='';
		if(reSet!=null){
			sessionStorage.removeItem('reSet');
			trEdited=$(reSet).parent().parent();
		}
		if($('#shdsetUserLocal').is(':checked')){
			locUsrId=$('#shdsetUserList li.selected').val();
			locUsrName=$('#shdsetUserList li.selected').text();
			$('#shdsetUserEmail').val('');
			if(locUsrName=='')
				noName='l';
		}else if($('#shdsetUserExt').is(':checked')){
			extUsr=$('#shdsetUserEmail').val();
			getTextDDFilterByVal('shdsetUserEmail','');
			if(extUsr=='')
				noName='e';
		}
		if(!noName==''){
			$('#shdsetUser'+(noName==='l'?'List':'Email')).parent().addClass('has-error');
			$('#shdsetUser'+(noName==='l'?'Local':'Ext')).trigger('click');
			$('[data-edShared="1"], [data-edShared="3"]').hide();
			$('[data-edShared="2"]').show();
			swal(i18n('msg_error'),i18n('msg_empty_data'),"error");
			return;
		}
		for(p=0;p<allEditsp.length;p++){
			var id=allEditsp[p].id;
			if(!/_0_/.test(id))
				allspriv+=id.replace('setsp_','')+'='+$('#'+id).prop('checked')+'|';
		}
		allspriv=allspriv.replace(/\|$/,'');
		if(trEdited==null)
			datarow='<tr>';
		datarow+='<td><span data-spuser-set="'+(locUsrId!=''?'loc='+locUsrId+'">'
			+ locUsrName:'ext='+extUsr+'">'+extUsr)+'</span></td>'
			+'<td><span>'+i18n((locUsrId!=''?'msg_local':'msg_external'))+'</span></td>'
			+'<td class="tac"><span class="asLink" style="cursor:not-allowed" title="'+i18n('msg_need_save')
			+'"><i class="fa fa-vcard-o" style="font-size:20px"></i></span></td>'
			+'<td class="tac shkdEd"><span class="asLink" data-allsp="set"'
			+' onclick="retrieveSP(\''+locUsrId+'\',\''+extUsr+'\',\''+allspriv
			+ '\');"><i class="md md-edit"></i></span></td>'
			+'<td class="tac shkdDel"><span class="asLink" onclick="deletesp(this);">'
			+ '<i class="md md-delete" style="font-size:20px"></i></span></td>';
		if(trEdited==null)
			$('#setsp-datatable-buttons').append(datarow+'</tr>');
		else
			trEdited.html(datarow);
		$('#shdsetUserEmail').val('');
		getTextDDFilterByVal('shdsetUserList','0');
	});

	function getShUser(id){
		$('#shdsetAddUser,.shkdEd,.shkdDel,#setsp-datatable-buttons,[data-edshared="1"],#btnupdateshare').hide();
		$.ajax({
			type:'POST',
			url:ctx+"/getShUser",
			data:'id='+id,
			async:false,
			success:function(data){
				var ctprv=data[0].ctprv;
				if(ctprv.length==0){
					swal(i18n('msg_information'),i18n('msg_unexpected_sharing'),"info");
					$('#shdk-modal').modal('hide');
					return;
				}
				if(typeof getDetailsSP==='function')getDetailsSP(data);
				var count=$('#shdk');
				if(count.length>0){
					for(s=0;s<ctprv.length;s++){
						if(ctprv[s]==1)
							$('#shdsetAddUser,#btnupdateshare').show();
						else if(ctprv[s]==2)
							$('.shkdEd,#btnupdateshare').show();
						else if(ctprv[s]==3)
							$('.shkdDel,#btnupdateshare').show();
						else if(ctprv[s]==4)
							$('#setsp-datatable-buttons,[data-edshared="1"]').show();
					}
					$('#shTrialid').val(id);
				}
			},error:function(resp){
				swal(i18n('msg_warning'),i18n('msg_unexpected_sharing'),"error");
			}
		});
	};

	function updateSharedTrial(){
		var allAssignedLawyer=$('[data-allsp="set"]'),lawyerassigned='';
		if(allAssignedLawyer.length>0)
			for(a=0;a<allAssignedLawyer.length;a++){
				var tmpstr=$(allAssignedLawyer[a]).attr('onclick');
				lawyerassigned+=(tmpstr.replace(/^.*\('(.*)'\).*$/g,'$1').replace(/','/g,","))+'~';
			}
			lawyerassigned=lawyerassigned.replace(/~$/,'');
		$.ajax({
			type:'POST',
			url:ctx+"/updateSharedTrial",
			data:'lawyerassigned='+lawyerassigned+'&edid='+$('#shTrialid').val(),
			async:false,
			success:function(data){
				if(data=="true"){
					swal({
						title:i18n('msg_success'),
						text:i18n('msg_data_saved'),
						type:"success",
						timer:3000,
						allowEscapeKey:false
					},function(){});
					window.setTimeout(function(){},3000);
				}else{
					swal(i18n('msg_error'),i18n('err_record_no_saved'),"error");
				}
			},error:function(e){
				swal(i18n('msg_error'),i18n('err_record_no_saved'),"error");
			}
		});
		$('#shdk-modal').modal('toggle');
		$('#shdkinfo-modal').modal('hide');
	};

	function getSharedInfo(id,email){
		var param='id1='+id+'&email='+email+'&id2='+$('#shTrialid').val();
		$.ajax({
			type:'POST',
			url:ctx+"/getSharedInfo",
			data:param,
			async:false,
			success:function(data){
				var info=data[0];
				if(info.username!=null){
					$('#info_username').val(info.username==''?'('+i18n('msg_notregistered')+')':info.username);
					$('#info_companyname').val(info.companyname==''?'('+i18n('msg_notregistered')+')':info.companyname);
					$('#info_email').val(info.email);

					$('#info_owneruser').val(info.owneruser);
					$('#info_firmusershare').val(info.firmusershare);
					$('#info_shareddate').val(info.shareddate==null?i18n('msg_not_confirmed')
						:formatDateTime(info.shareddate));
					$('#info_confirmationdate').val(info.confirmationdate==null?i18n('msg_not_confirmed')
						:formatDateTime(info.confirmationdate));
					$('#info_notificationdate').val(info.notificationdate==null?i18n('msg_not_notified')
						:formatDateTime(info.notificationdate));
					$('#shdkinfo-modal').modal('show');
				}else{
					swal(i18n('msg_error'),i18n('err_unable_get_trial'),"error");
				}
			},error:function(e){
				swal(i18n('msg_error'),i18n('err_unable_get_trial'),"error");
			}
		});
	};
</script>