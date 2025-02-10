function saveNewModule(e){
	e.disabled=true;
	var modulename=$("#modname").val(),descr=$("#description").val(),aliasTitle=$('#alias').val(),
		modtype=$('input[name=jspmenu]:checked'),err='';
	if(aliasTitle=='')
		err=i18n("err_enter_title");
	else
		if(modtype[0].id=='jspname'){
			if(modulename=='')
				err=i18n("err_enter_pagename");
			else if(descr=='')
				err=i18n("err_enter_description");
		}else if(modtype[0].id=='ismenu'){
			descr='(Submenu)';
			modulename=aliasTitle;
		}
	if(err!=''){
		$('#putError').html(err);
		$('#addModuleError').css('display','block');
		e.disabled=false;
		return false;
	}
	var param="name="+modulename+"&descr="+descr+"&aliasTitle="+aliasTitle;
	$.ajax({
		type:"POST",
		url:ctx+"/addmodule",
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
				},function(){
					location.href=location.pathname+"?language="+ getLanguageURL();
				});
				window.setTimeout(function(){
					location.href=location.pathname+"?language="+ getLanguageURL();
				},3000);
			}else{
				$('#putError').html(i18n(data));
				$('#addModuleError').css('display','block');
				e.disabled=false;
			}
		},error:function(e){
			$('#putError').html(i18n("err_unable_get_module"));
			$('#addModuleError').css('display','block');
			e.disabled=false;
		}
	});
};

function editModuleDetails(moduleId){
	$.ajax({
		type:"POST",
		url:ctx+"/getModuleById",
		data:"id="+moduleId,
		async:false,
		success:function(data){
			if(data[0].data==""){
				swal(i18n("msg_error"),i18n("err_unable_get_module")+" (1)","error");
			}else{
				var info=data[0].data[0];
				$('#EditModuleError').css('display','none');
				$('#editModuleId').val(info.moduleid);
				$('#editModuleName').val(info.name);
				$('#editAlias').val(info.alias);
				$('#editDescription').val(info.description);
				if(info.description=='(Submenu)'){
					document.getElementById("edIsmenu").checked=true;
					$('#editDescription,#editModuleName').attr('disabled', true);
					$('#editDescription,#editModuleName').val('');
				}else{
					document.getElementById("edJspname").checked=true;
					$('#editDescription,#editModuleName').attr('disabled', false);
					e.disabled=false;
				}
			}
		},error:function(resp){
			swal(i18n("msg_error"),i18n("err_unable_get_module")+" (1)","error");
			e.disabled=false;
		}
	});
};

function updateEditModule(e){
	e.disabled=true;
	var id=$('#editModuleId').val(),modulename=$("#editModuleName").val(),descr=$("#editDescription").val(),
		aliasTitle=$('#editAlias').val(),modtype=$('input[name=edJspmenu]:checked'),err='';
	if(aliasTitle==''){
		err=i18n("err_enter_title");
	}else{
		if(modtype[0].id=='edJspname'){
			if(modulename=='')
				err=i18n("err_enter_jspname");
			else if(descr=='')
				err=i18n("err_enter_description");
		}else if(modtype[0].id=='edIsmenu'){
			descr='(Submenu)';
			modulename=aliasTitle;
		}
	}
	if(err!=''){
		$('#putEditError').html(err);
		$('#EditModuleError').css('display','block');
		e.disabled=false;
		return false;
	}
	var param="id="+id+"&name="+modulename+"&descr="+descr+"&aliasTitle="+aliasTitle;
	$.ajax({
		type:'POST',
		url:ctx+'/updatemodule',
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
				},function(){
					location.href=location.href.replace(/^(.*)\#.*$/, '$1');
				});
				window.setTimeout(function(){ 
					location.href=location.href.replace(/^(.*)\#.*$/, '$1');
				},3000);
			}else{
				$('#'+putErr).html(i18n(data));
				$('#EditModuleError').css('display','block');
			}
		},error:function(e){
			$('#'+putErr).html(i18n("err_unable_get_module")+" (2)");
			$('#EditModuleError').css('display','block');;
		}
	});
};

function deleteModuleId(moduleId,modulename){
	$('#delmoduleid').text(modulename);
	$('#deleteModuleButton').attr('onclick','deleteModule('+moduleId+')');
};

function deleteModule(moduleId){
	var param="id="+moduleId;
	$.ajax({
		type:'POST',
		url:ctx+"/deletemodule",
		data:param,
		async:false,
		success:function(data){
			if(data)
				window.location=ctx+"/modules"+"?language="+getLanguageURL();
			else
				swal(i18n("msg_error"),i18n("err_unable_get_module")+", err.1","error");
		},error:function(){swal(i18n("msg_error"),i18n("err_unable_get_module")+", err.2","error");}
	});
};

$('#addbtn').on('click', function(){
	document.getElementById("jspname").checked=true;
});

$("#addNewModuleCancel").click(function(){
	$('#addModuleError').css('display','none');
	$("#name, #description").val("");
});

$('#jspname, #ismenu').on('change', function(){
	var modtype=$('input[name=jspmenu]:checked');
	if(modtype[0].id=='jspname'){
		$('#jspnamediv').fadeIn();
		$('#description,#modname').attr('disabled', false);
	}else if(modtype[0].id=='ismenu'){
		$('#jspnamediv').fadeOut();
		$('#description,#modname').attr('disabled', true);
		$('#description,#modname').val('');
	}
});

$('#edJspname, #edIsmenu').on('change', function(){
	var modtype=$('input[name=edJspmenu]:checked');
	if(modtype[0].id=='edJspname'){
		$('#edJspnamediv').fadeIn();
		$('#editDescription,#editModuleName').attr('disabled', false);
	}else if(modtype[0].id=='edIsmenu'){
		$('#edJspnamediv').fadeOut();
		$('#editDescription,#editModuleName').attr('disabled', true);
		$('#editDescription,#editModuleName').val('');
	}
});