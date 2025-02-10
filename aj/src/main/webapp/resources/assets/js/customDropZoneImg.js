function createDropZoneImg(divid, formid, idrow, type, maxfiles){
	maxfiles=!1===!!+maxfiles?15:maxfiles;
	myDropzone = new Dropzone(
	"#"+divid,
	{
	url : "uploadfile",
	addRemoveLinks : true,
	uploadMultiple : false,
	timeout:300000,
	maxFiles : maxfiles,
	maxFilesize: 30720, //MB
	acceptedFiles: "image/*,.jpge,.jpg,.png",
	dictMaxFilesExceeded : i18n('max_files_exceeded'),
	maxfilesexceeded: function (file) {
        this.removeAllFiles();
        this.addFile(file);
    },
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
			if(obj.attr('src').indexOf('doctos/')<0)
				if(obj.attr('src').indexOf('base64')<0)
					obj.attr("src", findExtentionFile(dataUrl)).attr({width: '10%', height: '10%'});
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
		/*
				if(response[0].name!=''){
					$('#uploadXEditClient'+' div div a img').attr('src', response[0].location+"/"+response[0].name);
				}*/
			
			}
		}
	});
	
	return myDropzone;
}

function findExtentionFile(archivo){
	if(archivo.endsWith(".png") || archivo.endsWith(".jpg") || archivo.endsWith(".jpge")){
		return "resources/assets/images/picture.png";
	}
	return null;
}