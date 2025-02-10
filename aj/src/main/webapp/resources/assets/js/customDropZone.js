function createDropZone(divid, formid, idrow, type){
	myDropzone = new Dropzone(
	"#"+divid,
	{
	url : "uploadfile",
	addRemoveLinks : true,
	uploadMultiple : false,
	maxFiles : 15,
	maxFilesize: 30720, //MB
	acceptedFiles: "image/*,audio/*,video/*"
		+",.pdf,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt,.rtf,.odt,.ods,.odp"
		+",.zip,.rar,.rar5,.ace,.gz,.7z"
		+",.mp3,.ogg,.wav,.aac,.aac,.ac3,.wma"
		+",.mp4,.webm,.avi,.3gp,.mpeg,.mpg,.mpeg-2,.mpeg-3,.mpeg-4,.mov,.hevc,.divx,.wmv"
		+",.jpge,.jpg,.png,.webp,.odg,.bmp",
	timeout:1800000,
	dictMaxFilesExceeded : i18n('max_files_exceeded'),
	dictRemoveFileConfirmation  : i18n('confirm_delete_file'),
	dictRemoveFile : i18n('delete_label'),
	renameFile: function (file) {
		var r='',s=(file.name).replace(/[#%&\\/<>*?"':+|`\^]/gi, "_").normalize("NFD").replace(/[\u0300-\u036f]/g, "");
		for(i=0;i<s.length;i++){
			var cp=s.charCodeAt(i);
			r+=(cp>=32&&cp<=126)?s[i]:'_';
		}
		return r;
    },
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
	uploadprogress: function(file, progress, bytesSent) {
	    if (file.previewElement) {
	    	var progressElement = file.previewElement.querySelector("[data-dz-uploadprogress]");
	        var progressBar = file.previewElement.getElementsByClassName("dz-upload")[0];
			progressBar.innerHTML = progress.toFixed(2) + "%";
	    }
	},
	init:function(){
		thisDropzone = this;
		this.on("thumbnail", function(file, dataUrl) {
			//$('.dz-image').last().find('img').attr({width: '10%', height: '10%'});
			var link = $("<a>");
			link.attr("href", dataUrl);
			link.attr("title", file.name);
			link.attr("target", '_blank');
			link.addClass("link");
			var obj = $('.dz-image').last().find('img');
			/*var iconpreview=findExtentionFile(dataUrl);
			if(iconpreview!='--img')
				obj.attr("src", iconpreview).attr({width: '10%', height: '10%'});	*/
			var iconpreview=findExtentionFile(file.name);
			if(iconpreview!='--img')
				obj.attr("src", iconpreview).attr({width:'30px',height:'30px'});
			else
		        obj.attr("src", dataUrl).attr({width:'30px', height:'30px' }); 
			$(obj).appendTo(link);
			$(link).appendTo($('.dz-image').last());
			
        });
		if(idrow != ''){
			$.ajax({
			  url: 'previewuploadfile',
			  type: 'post',
			  data: {'id': idrow,'type':type},
			  dataType: 'json',
			  success: function(response){
				$.each(response, function(key,value) {
				  var mockFile = { name: value.name, size: value.size };
				  myDropzone.emit("addedfile", mockFile);
				  myDropzone.emit("complete", mockFile);
				  myDropzone.emit("thumbnail", mockFile, value.location);
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
	
	/*
	// Update the total progress bar
	myDropzone.on("totaluploadprogress", function(progress) {
		document.querySelector("#total-progress .progress-bar").style.width = progress + "%";
	});
	
	myDropzone.on("sending", function(file) {
		// Show the total progress bar when upload starts
		document.querySelector("#total-progress").style.opacity = "1";
		// And disable the start button
		file.previewElement.querySelector(".start").setAttribute("disabled", "disabled");
	});
	
	// Hide the total progress bar when nothing uploading anymore
	myDropzone.on("queuecomplete", function(progress) {
		document.querySelector("#total-progress").style.opacity = "0";
	});*/
	
	return myDropzone;
}

function findExtentionFile(archivo){
	var thumb='',ext=(archivo.replace(/.*\.(\.*)/g,'$1')).toLowerCase();
	if(ext=="pdf")
		thumb="pdf.png";
	else if(['doc','docx','odt','txt','rtf'].indexOf(ext)>=0)
		thumb="word.png";
	else if(['xls','xlsx','ods'].indexOf(ext)>=0)
		thumb="excel.png";
	else if(['ppt','pptx','odp'].indexOf(ext)>=0)
		thumb="powerpoint.png";
	else if(['jpg','jpeg','png','gif','bmp'].indexOf(ext)>=0)
		return "--img";
	else
		return null;
	return "resources/assets/images/"+thumb;
}
