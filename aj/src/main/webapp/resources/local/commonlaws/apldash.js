;$("#addNewMove").click(function(){
	setNewMove();
	getActType('actType');
});

$("#addNewAppealDash").click(function(){
	$('#errorOnAddApl,.containTL,[data-v="trial"]').hide();
	$("#addNewAppeal").attr('href','#appeal-modal');
	$("#apladhesiva").val('');
	getRooms('roomApl','ul');
	getMaterias('matterApl','ul');
	getStates('aplState','ul');
	$('#appeal-modal').modal('show').slideDown();
	var tid=$('#trial').val();
	$('#trialApl').html('<li title="'+tid+'" class="selected" value="'+tid+'">'+tid+'</li>');
	try{
		createDropZone('uploadXAppeal','formAddAppeal','','');//id 2
	}catch (e){
		clearTemp();
		$('#areaAppealUpload').html('');
		$('#areaAppealUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
			'<div id="uploadXAppeal" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZone('uploadXAppeal','formAddAppeal','','');
	}
	$("#uploadXAppeal").addClass("dropzone");
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