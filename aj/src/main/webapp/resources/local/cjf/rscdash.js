;getClientListTab('#clientRscList');

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


function getAppealsTab(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getAppealList",
		async:false,
		success:function(data){
			var info=data[0]||[];
			if(info.length>0){
				var tablelist='<tr><th style="display:none;"></th>'
					+'<th>'+i18n('msg_appeal')+'</th></tr>';
				for(i=0;i<info.length;i++){
					tablelist+='<tr class="rowopt"><td style="display:none;"><input type="radio" name="rowline" data-val="'
						+info[i].toca+'" id="appeal'+info[i].apelacionid+'"></td>'
						+'<td>'+info[i].toca+'</td>';
				}
				$('#'+id+'List').append(tablelist);
			}
		},error:function(e){
			console.log("Error al obtener Apelaciones. "+e);
		}
	});
};

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