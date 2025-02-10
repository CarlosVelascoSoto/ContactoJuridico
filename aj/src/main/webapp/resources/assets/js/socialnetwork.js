function addSocialnetwork(e){
	e.disabled=true;
	var err='',snw=($('#socialnetwork').val()).trim(),mainurl=($('#website').val()).trim();;
	if(snw=='')
		err=i18n('msg_empty_data');
	if(err!=""){
		$('#errorOnAdd').css('display','block');
		$('#putErrorOnAdd').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		e.disabled=false;
		return false;
	}
	var param='snw='+snw+'&mainurl='+mainurl;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/addNewSocialnetwork",
		data:param,
		async:false,
		success:function(data){
			e.disabled=false;
			if(data=='msg_data_saved'){
				swal(i18n("msg_success"),i18n(data),"success");
				location.href=location.pathname+"?language="+ getLanguageURL();
			}else{
				$('#putErrorOnAdd').html(i18n(data));
				$('#errorOnAdd').css('display','block');
			}$('#company-modal').modal('toggle');
		},
		error:function(e){
			$('#putErrorOnAdd').html(i18n('err_record_no_saved'));
			$('#errorOnAdd').css('display','block');
			e.disabled=false;
		}
	});
};

function getDetailsToEdit(id){
	$('#errorOnEdit').hide();
	$.ajax({
		type:'POST',
		url:ctx+"/getSocNetworkById",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].info[0]||[];
			if(data[0].info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_socialnetwork'),"error");
			}else{
				$('#edSocialnetworkId').val(info.socialnetworkid);
				$('#edSocialnetwork').val(info.socialnetwork);
				$('#edWebsite').val(info.mainurl);
				$('#edSnLogo').attr('src',info.imageurl);
				$('#edSnLogo').val(info.imageurl);
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_socialnetwork'),"error");
		}
	});
	try{
		createDropZoneImgSN('uploadXSNEdit', 'formsnedit', id, 1000);//1000=Temporal
	}catch (e){
		clearTemp();
		$('#areaEditSNUpload').html('');
		$('#areaEditSNUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'
			+'<div id="uploadXSNEdit" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZoneImgSN('uploadXSNEdit', 'formsnedit', id, 1000);
	}
};

function updateData(){
	var err='',snw=($('#edSocialnetwork').val()).trim(),mainurl=($('#edWebsite').val()).trim(),
		rex=new RegExp("[^:/.](https?:\/\/)?(www\.)?[a-zA-Z0-9_~+:-]*[\.\/][a-zA-Z0-9_~+:.!*'();:@&=+$,/?%#[\]-]*[^.]",'g');
	if(snw=='')
		err=i18n('msg_empty_data');
	else if(rex.test(mainurl))
		err=i18n('err_website_not_valid');
	if(err!=""){
		$('#errorOnEdit').css('display','block');
		$('#putErrorOnEdit').html(err);
		$('.custombox-modal-open').animate({scrollTop:0},'1000');
		e.disabled=false;
		return false;
	}
	var param='id='+$('#edSocialnetworkId').val()+'&snw='+snw+'&mainurl='+mainurl;
	$('input[name^="fileuploadx_"]').each(function(i){
		param+="&"+$(this).attr("name")+"="+$(this).val();
	});
	$.ajax({
		type:"POST",
		url:ctx+"/updateSocNetwork",
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

function deleteSocialnetwork(id){
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
				url:ctx+"/deleteSocNetwork",
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

function clearTemp(){
	$('input[name^="fileuploadx_"]').each(function(i){
		$(this).val('');
	});
	$.ajax({
		type:'POST',
		url:ctx+"/deleteTempPath",
		async:false,
		success:function(data){
			if(!data)console.log("Not cleared");
		}
	});
};

$("#addNewSocialnetwork").click(function(){
	$('#errorOnAdd').css('display','none');
	$("#addNewSocialnetwork").attr('href','#socialnetwork-modal');
	try{
		myDropzone = createDropZoneImgSN('uploadXSNEdit', 'formsnedit', '', ''); 
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}catch (e){
		clearTemp();
		$('#areaSNUpload').html('');
		$('#areaSNUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXSN" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZoneImgSN('uploadXSNEdit', 'formsnedit', '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}
});

function createDropZoneImgSN(divid, formid, idrow, type){
	myDropzone = new Dropzone(
	"#"+divid,
	{
	url : "uploadfile",
	addRemoveLinks : true,
	uploadMultiple : false,
	maxFiles : 1,
	maxFilesize: 150, //MB
	acceptedFiles: "image/.jpge,.jpg,.png",
	dictMaxFilesExceeded : i18n('max_files_exceeded'),
	dictRemoveFileConfirmation  : i18n('confirm_delete_file'),
	dictRemoveFile : i18n('delete_label'),
	removedfile: function(file) {
		$("input[value*='"+file.name+"']").each(function(){	
			$(this).val("");
		});
		
		$.ajax({
		  url: 'deletefile',
		  type: 'post',
		  data: {'id': idrow,'type':type, 'file':file.name},
		  dataType: 'json',
		  success: function(response){
			console.log(response);
		  }
		});
		
		var _ref;
		return (_ref = file.previewElement) != null ? _ref.parentNode.removeChild(file.previewElement) : void 0;        
	},
	init:function(){
		thisDropzone = this;
		this.on("thumbnail", function(file, dataUrl) {
			//$('.dz-image').last().find('img').attr({width: '10%', height: '10%'});
			var link = $("<a>");
			link.attr("href", dataUrl);
			link.attr("title", file);
			link.attr("target", '_blank');
			link.addClass("link");
			var obj = $('.dz-image').last().find('img');
			obj.attr("src", findExtentionFile(dataUrl)).attr({width: '10%', height: '10%'});
			$(obj).appendTo(link);
			$(link).appendTo($('.dz-image').last());
			
        });
		if(idrow != ''){
			$.ajax({
			  url: '/createDropZoneImgSN',
			  type: 'post',
			  data: {'id': idrow,'type':type},
			  dataType: 'json',
			  success: function(response){
				$.each(response, function(key,value) {
				  var mockFile = { name: value.name, size: value.size };
				  myDropzone.emit("addedfile", mockFile);
				  myDropzone.emit("thumbnail", mockFile, value.location);
				  myDropzone.emit("complete", mockFile);
				});
			  }
			});
		}		
	},
	success: function(file, response) {
		var time = new Date().getTime();
		var responseContent = "";
		if (response.length > 0) {
				$('<input>').attr({
					type : 'hidden',
					name : 'fileuploadx_'+time,
					id : 'fileuploadx_'+time,
					value : ""
				}).appendTo($("#"+formid));
				$("#fileuploadx_"+time).val(response[0].name);	
			}
		}
	});
	return myDropzone;
};

function findExtentionFile(archivo){
	if(archivo.endsWith(".png") || archivo.endsWith(".jpg") || archivo.endsWith(".jpge")){
		return "resources/assets/images/picture.png";
	}
	return null;
};

$(document).ready(function(){
	$(".modal-demo").on('hide.bs.modal', function(){
		clearTemp();
	});
	$("#edit-modal").on('shown.bs.modal', function () {
		var photo=$('#edSnLogo').val();
		if(photo!=''){
			$('.dz-details').css('display','none');
			var photoId=photo.match(/[^\\/:*?"<>|\r\n]+$/);
			$('#uploadXSNEdit'+' div div a img').attr('src', 'doctos/images/socialnetworks/'+photoId);
		}
    });
});