$("#addNewMove").click(function() {
	$('input, select').parent().removeClass('has-error');
	$("#addNewMove").attr('href', '#moves-modal');
	$("#trialtype").append('<>' + i18n('err_select_matter') + '</>')
	var trialid = getVarURL('id');
	if (trialid != '')
		$('#trial').val(trialid);
	$('#newMoveForm')[0].reset();
	$('#errorOnAddMov').hide();
	$('#moves-modal').modal('show').slideDown();
	getActType('actType');
	try {
		myDropzone = createDropZone("uploadXMovs", "newMoveForm", '', '');
		myDropzone.on('sending', function(file, xhr, formData) {
			// formData.append('idx', $('#usersession').val());
		});
	} catch (e) {
		clearTemp();
		$('#areaMovsUpload').html('');
		$('#areaMovsUpload').html('<span class="textContent">' + i18n('msg_upload_area') + '</span>'
			+ '<div id="uploadXMovs" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZone("uploadXMovs", "newMoveForm", '', '');
		myDropzone.on('sending', function(file, xhr, formData) {
			// formData.append('idx', $('#usersession').val());
		});
	}
	$("#uploadXMovs").addClass("dropzone");
});

$("#addNewProtDash").click(function(){
	$('#errorOnAddProt').hide();
	$("#addNewProtDash").attr('href','#protection-modal');
	$('#protection-modal').modal('show').slideDown();
	
	$('#protClient').find('option').remove().end();
	$('#protClient').append('<option value="'+$('#clientid').val()+'">'+$('#clientname').html()+'</option>');
	$('#selecttrialO').find('option').remove().end();
	$('#selecttrialO').append('<option value="'+$('#trial').val()+'">'+$('#trialname').html()+'</option>');
	//getAppeals('appealList');
getRelClientAppeal($('#clientid').val(),'#appealList');
	$('#divappeal').hide();
	$('#divtrialOrigin').show();
	try{
		myDropzone = createDropZone("uploadXProt", "formAddProt", '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}catch (e){
		clearTemp();
		$('#areaNewProtUpload').html('');
		$('#areaNewProtUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXProt" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		myDropzone = createDropZone("uploadXProt", "formAddProt", '', '');
		myDropzone.on('sending', function(file, xhr, formData){
			//formData.append('idx', $('#usersession').val());
		});
	}
	$("#uploadXProt").addClass("dropzone");
});

Dropzone.options.uploadXMovs={
	init:function(){
		this.on('addedfile', function(file){
			var preview = document.getElementsByClassName('dz-preview'),
				imageName = document.createElement('span');
			preview = preview[preview.length - 1];
			imageName.innerHTML = file.name;
			preview.insertBefore(imageName, preview.firstChild);
		});
	}
};