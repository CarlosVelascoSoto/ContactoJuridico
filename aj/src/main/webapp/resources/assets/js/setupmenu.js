var items;

function getVarURL(v){
	var querys=(location.search).replace(/^./, "");
	var vars=querys.split("&");
	for(i=0;i<vars.length;i++){
		var pair=vars[i].split("=");
		if(pair[0]==v)
			return pair[1];
	}return "";
};

function getMenuList(id, tipo){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getMenuList",
		async:false,
		success:function(data){
			var tipomenu = $("input[name='"+tipo+"']:checked").val();
			var info=data[0].detail,htmlcode="<option value='0' >Root</option>",st=$('#'+id);
			if(info==null){
				console.log("No fue posible cargar los estados");
				return 0;
			}
			
			if(tipomenu==2) // solo para menu lateral
			for(s=0;s<info.length;s++){
				if(tipomenu == info[s].tipomenu)
				htmlcode+="<option value='"+info[s].menuid+"'>"+info[s].menu+"</option>";
			}
			
			for(f=0;f<st.length;f++)
				st[f].innerHTML=htmlcode;
		},error:function(e){
			swal(i18n('msg_warning'),i18n('err_unable_get_menu'),"error");
		}
	});
};

$('input[type=radio][name=tipomenu]').change(function() {
    getMenuList('parentmenu', 'tipomenu');
	var tipomenu = $("input[name='tipomenu']:checked").val();
	getOptParentMenu('addnew',$('#parentmenu').val(),tipomenu);
});

$('input[type=radio][name=edtipomenu]').change(function() {
    getMenuList('edParentmenu', 'edtipomenu');
	var tipomenu = $("input[name='edtipomenu']:checked").val();
	getOptParentMenu('edit',$('#edParentmenu').val(),tipomenu);
});


/** Obtiene los datos en el módulo indicado
 * 
 * @param action	Acción realizada:
 * 					'addnew' 	Establece los valores en altas
 * 					'edit'		Establece los valores en edición
 * @param id		{integer}	Indica el id del modulo
 */
function getOptParentMenu(action,id, tipomenu){
	//if(id=='')return;
	$.ajax({
		type:"POST",
		url:ctx+"/getOptParentMenu",
		data:'id='+id+'&tipo='+tipomenu,
		async:false,
		success:function(data){
			var menuOptions=data[0].options,
				dndoptions='',ordOpt=action=='addnew'?'orderOptions':'edOrderOptions',
				menutitle=action=='addnew'?'menutitle':'edMenutitle';
			$('#'+ordOpt).html('');
			for(i=0;i<menuOptions.length;i++){
				dndoptions+='<div id="'+menuOptions[i].menuid+'"draggable="true" class="dndBox inlineflex w100p">'+menuOptions[i].menu+'</div>';
			}
			if(action=='addnew')
				dndoptions+='<div id="-1" draggable="true" class="dndBox inlineflex w100p">'+$('#'+menutitle).val()+'</div>';

			$('#'+ordOpt).html(dndoptions);
			setItemsDnD(action);
		},error:function(e){
			console.log("Error al obtener opciones. "+e);
		}
	});
};

function addMenu(){
	var err='',menutitle=$('#menutitle').val(),menuicon=$('#menuicon').val(),
		parentmenu=$('#parentmenu').val(),link_=$('#link').val(),
		tipomenu = $("input[name='tipomenu']:checked").val();
	if(parentmenu==null)parentmenu=0;
	if(menutitle=='')
		err=i18n('err_enter_menutitle');
	if(err!=""){
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var ordermenu=$('#orderOptions div'),orderIds='';
	for(i=0;i<ordermenu.length;i++){
		orderIds+=ordermenu[i].id+',';
	}
	var param='menutitle='+menutitle+'&icon='+menuicon+'&parent='+parentmenu
		+'&link='+link_+'&tipomenu='+tipomenu+'&orderIds='+orderIds.replace(/.$/,'');
	$.ajax({
		type:"POST",
		url:ctx+"/addNewMenu",
		data:param,
		async:false,
		success:function(data){
			if(data=='msg_data_saved'){
				swal(i18n("msg_success"),i18n(data),"success");
				location.href=location.pathname+"?language="+ getLanguageURL();
			}else{
				$('#putErrorOnAdd').html(i18n(data));
				$('#errorOnAdd').css('display','block');
			}
		},
		error:function(e){
			$('#putErrorOnAdd').html(i18n('err_record_no_saved'));
			$('#errorOnAdd').css('display','block');
		}
	});
};

function getDataByMenuId(id){
	$.ajax({
		url:ctx+"/getDataByMenuId",
		type:'POST',
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].detail[0];
			if(info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_menu'),"error");
			}else{
				$('#edContainer').fadeOut(10);
				
				$('#edMenuId').val(info.menuid)
				$('#edMenutitle').val(info.menu);
				$('#edMenuicon').val(info.icon);
				$('#edParentmenu').val(info.menuparentid);
				$('#edlink').val(info.link);
				
				if(info.tipomenu==1)
					$("#edtop").prop("checked", true);
				else if(info.tipomenu==2)
					$("#edlat").prop("checked", true);
				else
					$("#muser").prop("checked", true);
				
				getMenuList('edParentmenu', 'edtipomenu');
				getOptParentMenu('edit',info.menuparentid, info.tipomenu);
				setItemsDnD('edit');
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_menu'),"error");
		}
	});
};

function updateMenu(){
	var err='',menutitle=$('#edMenutitle').val(),menuicon=$('#edMenuicon').val(),
		parentmenu=$('#edParentmenu').val(),link_=$('#edlink').val(),
		tipomenu = $("input[name='edtipomenu']:checked").val();;
	if(parentmenu==null)parentmenu=0;
	if(menutitle=='')
		err=i18n('err_enter_menutitle');
	if(err!=""){
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		return false;
	}
	var ordermenu=$('#edOrderOptions div'),orderIds='';
	for(i=0;i<ordermenu.length;i++)
		orderIds+=ordermenu[i].id+',';
	var param='id='+$('#edMenuId').val()+'&menutitle='+menutitle+'&icon='+menuicon+'&parent='+parentmenu
		+'&link='+link_+'&tipomenu='+tipomenu+'&orderIds='+orderIds.replace(/.$/,'');
	$.ajax({
		type:"POST",
		url:ctx+"/updateMenu",
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
				$('#putErrorOnEdit').html(i18n('err_record_no_saved'));
				$('#errorOnEdit').css('display','block');
			}
		},error:function(e){
			$('#putErrorOnEdit').html(i18n('err_record_no_saved'));
			$('#errorOnEdit').css('display','block');
		}
	});
};

$("#addNewMenu").click(function(){
	$('#errorOnAdd').css('display','none');
	$("#addNewMenu").attr('href','#menu-modal');
	$('#menuicon').val('');
	$('#container').fadeOut(10);
	getMenuList('parentmenu', 'tipomenu');
});

$('#menutitle').on('change', function(){
	var tipomenu = $("input[name='tipomenu']:checked").val();
	getOptParentMenu('addnew',$('#parentmenu').val(), tipomenu);
});

$('#edMenutitle').on('change', function(){
	var tipomenu = $("input[name='edtipomenu']:checked").val();
	getOptParentMenu('edit',$('#edParentmenu').val(), tipomenu);
});

$('#parentmenu').on('change', function(){
	var tipomenu = $("input[name='tipomenu']:checked").val();
	getOptParentMenu('addnew',$('#parentmenu').val(), tipomenu);
});

$('#edParentmenu').on('change', function(){
	var tipomenu = $("input[name='edtipomenu']:checked").val();
	getOptParentMenu('edit',$('#edParentmenu').val(), tipomenu);
});

function deleteMenu(id){
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
				url:ctx+"/deleteMenu",
				data:param,
				async:false,
				success:function(data){
					swal({
						title:i18n('msg_deleted'),
						text:i18n('msg_record_deleted'),
						type:"success"
					}, function(){
						location.href=location.pathname+"?language="+ getLanguageURL();
					});
				},error:function(resp){
					swal(i18n('msg_warning'), i18n('err_dependence_on_delete'),"warning");
				}
			});
		}else{
			swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "warning");
		}
	});
};

function setItemsDnD(module){
	var container=module=='addnew'?'orderOptions':module=='edit'?'edOrderOptions':'';
	if(container=='')return;
	items=document.querySelectorAll('#'+container+' .dndBox');
	items.forEach(function(item){
		item.addEventListener('dragstart', handleDragStart, false);
		item.addEventListener('dragenter', handleDragEnter, false);
		item.addEventListener('dragover', handleDragOver, false);
		item.addEventListener('dragleave', handleDragLeave, false);
		item.addEventListener('drop', handleDrop, false);
		item.addEventListener('dragend', handleDragEnd, false);
	});
};

/* limpia inputs */
$('#menus-modal, #edit-modal').on('hidden.bs.modal',function(e){
	$(this).find("input,textarea").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked","").end();
});

$('#menuicon').on('focus',function(e){
	$('#container').fadeToggle(500);
});

$('#edMenuicon').on('focus',function(e){
	$('#edContainer').fadeToggle(500);
});

$('.icon-container').on('click',function(e){
	$('#menuicon, #edMenuicon').val((e.target.textContent).trim());
	$('#container, #edContainer').fadeOut(500);
});

  var dragSrcEl=null;
  function handleDragStart(e){
    this.style.opacity='0.4';
    dragSrcEl=this;
    e.dataTransfer.effectAllowed='move';
    e.dataTransfer.setData('text/html', this.innerHTML);
  };

  function handleDragOver(e){
    if(e.preventDefault)
      e.preventDefault();
    e.dataTransfer.dropEffect='move';
    return false;
  };

  function handleDragEnter(e){this.classList.add('over');}
  function handleDragLeave(e){this.classList.remove('over');}
  function handleDrop(e){
    if(e.stopPropagation)e.stopPropagation();
    if(dragSrcEl != this){
      dragSrcEl.innerHTML=this.innerHTML;
	  this.innerHTML=e.dataTransfer.getData('text/html');
	  var tmpid=dragSrcEl.attributes.getNamedItem("id").value;
	  //console.log("src:"+dragSrcEl.attributes.getNamedItem("id").value);
	  //console.log("transfer:"+e.currentTarget.attributes.getNamedItem("id").value);
	  
	  var attr = document.createAttribute ("id");
      attr.value = this.attributes.getNamedItem("id").value;
	  dragSrcEl.attributes.setNamedItem(attr);
	  	  
	  var attr = document.createAttribute ("id");
      attr.value = tmpid;
	  this.attributes.setNamedItem(attr);
    }return false;
  };

  function handleDragEnd(e){
    this.style.opacity='1';
    items.forEach(function (item){
      item.classList.remove('over');
    });
  };
  
  setItemsDnD();