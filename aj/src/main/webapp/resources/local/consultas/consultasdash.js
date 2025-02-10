$("#addNewMove").click(function() {
	$('input, select').parent().removeClass('has-error');
	$("#addNewMove").attr('href', '#moves-modal');
	$("#trialtype").append('<>' + i18n('err_select_matter') + '</>')
/*	var trialid = getVarURL('id');
	if (trialid != '')
		$('#trial').val(trialid);*/
	$('#newMoveForm')[0].reset();
	$('#errorOnAddMov').hide();
	$('#moves-modal').modal('show').slideDown();
	getActType('actType');
	try {
		myDropzone = createDropZone("uploadXMovs", "newMoveForm", '', '');
		myDropzone.on('sending', function(file, xhr, formData) {
			//
		});
	} catch (e) {
		clearTemp();
		$('#areaMovsUpload').html('');
		$('#areaMovsUpload').html('<span class="textContent">' + i18n('msg_upload_area') + '</span>'
			+ '<div id="uploadXMovs" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZone("uploadXMovs", "newMoveForm", '', '');
		myDropzone.on('sending', function(file, xhr, formData) {
			//
		});
	}
	$("#uploadXMovs").addClass("dropzone");
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