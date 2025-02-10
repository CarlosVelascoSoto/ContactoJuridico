;function setNewMove(){
	$('input, select').parent().removeClass('has-error');
	$('#addNewMove').attr('href','#moves-modal');
	$('#newMoveForm')[0].reset();
	$('#errorOnAddMov').hide();
	$('#moves-modal').modal('show').slideDown();
	try {
		myDropzone = createDropZone("uploadXMovs", "newMoveForm", '', '');
		myDropzone.on('sending', function(file, xhr, formData){/*...*/});
	} catch (e) {
		clearTemp();
		$('#areaMovsUpload').html('');
		$('#areaMovsUpload').html('<span class="textContent">' + i18n('msg_upload_area') + '</span>'
			+ '<div id="uploadXMovs" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZone("uploadXMovs", "newMoveForm", '', '');
		myDropzone.on('sending', function(file, xhr, formData) {/*...*/});
	}
	$("#uploadXMovs").addClass("dropzone");
};

function getActType(id){
	$('#'+id).find('option').remove().end();
	$.ajax({
		type:"POST",
		url:ctx+"/getActType",
		async:false,
		success:function(data){
			var info=data[0];
			if(info.length>0){
				var option=new Option(i18n('msg_select'),'',!1,!0);
				$('#'+id).append(option);
				$(option).attr('disabled',!0);
				for(i=0;i<info.length;i++){
					option=new Option(info[i].tipoactuacion,info[i].tipoactuacionid,!0,!1);
					$('#'+id).append(option);
					$(option).attr('title',info[i].tipoactuacion);
				}
			}
		},error:function(e){
			console.log(i18n('err_unable_get_activitytype')+'. '+e);
		}
	});
};

function getMovToEditDash(id) {
	$('#errorOnEditMov').removeClass('alert-warning').addClass('alert-danger');
	$.ajax({
		type : "POST",
		url : ctx + "/getMovement",
		data : 'id=' + id,
		async : false,
		success : function(data) {
			var mov = data[0].mov[0]||[], cal = data[0].cal[0]||[];
			if (typeof mov == 'undefined') {
				swal(i18n("msg_error"), i18n('err_unable_get_movement'), "error");
				return;
			}
			getActType('edActType');
			$('#edMovId').val(mov.movimientoid);
			$('#edMovement').val(mov.movimiento);
			var o = mov.tipoactuacionid;
			$('#edActType').val(o);
			if (o == '1' || o == '2') {
				$('.actype1').removeClass('dnone');
				$('.actype2').addClass('dnone');
			} else if (o == '3' || o == '4') {
				$('.actype1').addClass('dnone');
				$('.actype2').removeClass('dnone');
			}
			$('#edCuaderno').val(mov.cuaderno);
			if (mov.fechapresentacion != null)
				setdp('#edFilingdate',mov.fechapresentacion);
				//setBootstrapUtcDate('edFilingdate', 'edFilingdateFix', mov.fechapresentacion);
			if (mov.fechaacuerdo != null)
				setdp('#edAgreementdate',mov.fechaacuerdo);
				//setBootstrapUtcDate('edAgreementdate', 'edAgreementdateFix', mov.fechaacuerdo);
			if (mov.fechanotificacion != null)
				setdp('#edNotificationdate',mov.fechanotificacion);
				//setBootstrapUtcDate('edNotificationdate','edNotificationdateFix',mov.fechanotificacion);
			$('#edMovObserv').val(mov.observaciones);

			if (typeof cal != 'undefined') {
				if(cal.length<=0)return;
				var ac = cal.appointment;
				ac = ac.substring(ac.indexOf(' - ') + 3, ac.length);
				$('#edActivity').val(ac);
				setdp('#edStartschd',cal.dateini);
				setdp('#edEndschd',cal.dateend)
				/*setBootstrapUtcDate('edStartschd', 'edStartschdFix', cal.dateini);
				setBootstrapUtcDate('edEndschd', 'edEndschdFix', cal.dateend);*/
				var d1 = new Date(cal.dateini), d2 = new Date(cal.dateend);
				$('#edStarttimehour').val(d1.getHours() < 10 ? '0' + d1.getHours() : d1.getHours());
				$('#edStarttimeminute').val(d1.getMinutes() < 10 ? '0' + d1.getMinutes() : d1.getMinutes());
				$('#edEndtimehour').val(d2.getHours() < 10 ? '0' + d2.getHours() : d2.getHours());
				$('#edEndtimeminute').val(d2.getMinutes() < 10 ? '0' + d2.getMinutes() : d2.getMinutes());
			}
		},error : function(e) {
			swal(i18n("msg_error"), i18n('err_unable_get_movement'), "error");
		}
	});
	try {
		createDropZone('uploadXEditMov', 'editMoveForm', id, 7);
	} catch (e) {
		clearTemp();
		$('#areaEditMovUpload').html('');
		$('#areaEditMovUpload').html('<span class="textContent">' + i18n('msg_upload_area') + '</span>'
			+ '<div id="uploadXEditMov" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZone('uploadXEditMov', 'editMoveForm', id, 7);
	}
	$("#uploadXEditMov").addClass("dropzone");
};

function delActivityDash() {
	swal({
		title : i18n('msg_are_you_sure'),
		text : i18n('msg_delete_schedule_data'),
		type : "warning",
		showCancelButton : true,
		confirmButtonClass : 'btn-warning',
		confirmButtonText : i18n('btn_yes_delete_it'),
		closeOnConfirm : false,
		closeOnCancel : false
	}).then((isConfirm) => {
		if (isConfirm) {
			$('#edActivity, #edStartschd, #edStartschdFix,#edEndschd,#edEndschdFix').val('');
			$('#edStarttimehour,#edStarttimeminute,#edEndtimehour,#edEndtimeminute').val('0');
			$('#errorOnEditMov').removeClass('alert-danger').addClass('alert-warning');
			$('#puterrorOnEditMov').html(i18n('msg_schedule_to_delete'));
			$('#errorOnEditMov').show();
		} else {
			alert(i18n('msg_cancelled'));
		}
	});
};

function saveEditMovDash() {
	var err = "", reftype = $('#referencetype').val(), refid = $('#referenceid').val(),
		movement = $('#edMovement').val(), filingdate = $('#edFilingdate').val(),
		cuaderno = $('#edCuaderno').val(), agreementdate = $('#edAgreementdate').val(),
		notificationdate = $('#edNotificationdate').val(),
		actType = $('#edActType option:selected').val(), act = $('#edActivity').val(),
		movObserv = $('#edMovObserv').val(),
		startschd = (typeof $('#edStartschd').val() == 'undefined') ? '': $('#edStartschd').val(),
		endschd = (typeof $('#edEndschd').val() == 'undefined') ? '' : $('#edEndschd').val(),
		stHour = $('#edStarttimehour').val() || '', stMinute = $('#edStarttimeminute').val() || '',
		endHour = $('#edEndtimehour').val() || '', endMinute = $('#edEndtimeminute').val() || '';
	var starttime = stHour + ':' + stMinute, endtime = endHour + ':' + endMinute;
	cuaderno = cuaderno || '';
	if (actType == '')
		err = 'err_select_acttype';
	else if (cuaderno == '')
		err = 'err_enter_notebooktype';
	else if (movement == '')
		err = 'err_enter_movement';
	else if (act != '' || startschd != '' || endschd != '' || stHour != ''
			|| stMinute != '' || endHour != '' || endMinute != '') {
		if (act == '')
			err = 'err_enter_activity';
		else if (startschd == '')
			err = 'err_enter_startdate';
		else if (endschd == '')
			err = 'err_enter_enddate';
		else if (stHour == '' || stMinute == '')
			err = 'err_enter_starttime';
		else if (endHour == '' || endMinute == '')
			err = 'err_enter_endtime';
		else {
			var d1 = new Date(startschd + 'T' + starttime + ':00Z'),
				d2 = new Date(endschd + 'T' + endtime + ':00Z');
			if (d1 >= d2)
				err = 'err_startDT_mayor_endDT';
		}
	}
	if (err != "") {
		$('#puterrorOnEditMov').html(i18n(err));
		$('#errorOnEditMov').show();
		return false;
	}
	var param = 'id=' + $('#edMovId').val() + '&reftype=' + reftype + '&refid=' + refid
		+ '&cuaderno=' + cuaderno + "&movement=" + movement
		+ "&filingdate=" + filingdate + '&agreementdate=' + agreementdate
		+ "&notificationdate=" + notificationdate + "&actType=" + actType
		+ "&movObserv=" + movObserv + "&act=" + act + "&fini=" + $('#edStartschd').val()
		+ "&ffin=" + endschd + "&tini=" + starttime + "&tfin=" + endtime;
	$('input[name^="fileuploadx_"]').each(function(i) {
		param += "&" + $(this).attr("name") + "=" + $(this).val();
	});
	$.ajax({
		type : "POST",
		url : ctx + "/saveEditMove",
		data : param,
		async : false,
		success : function(data) {
			if (data == "msg_data_saved") {
				swal(i18n("msg_success"), i18n(data), "success");
				location.href = location.href.replace(/^(.*)\#.*$/, '$1');
			} else if (data = "msg_data_saved_no_email") {
				swal(i18n("msg_warning"), i18n(data), "warning");
				location.href = location.href.replace(/^(.*)\#.*$/, '$1');
			} else {
				$('#puterrorOnEditMov').html(i18n(data));
				$('#errorOnEditMov').show();
			}
		}, error : function(e) {
			$('#puterrorOnEditMov').html(i18n('err_record_no_saved'));
			$('#errorOnEditMov').show();
		}
	});
};

function delMovDash(id) {
	swal({
		title : i18n('msg_are_you_sure'),
		text : i18n('msg_delete'),
		type : "warning",
		showCancelButton : true,
		confirmButtonClass : 'btn-warning',
		confirmButtonText : i18n('btn_yes_delete_it'),
		closeOnConfirm : false,
		closeOnCancel : false
	}).then((isConfirm) => {
		if (isConfirm) {
			$.ajax({
				type : "POST",
				url : ctx + "/deleteMovement",
				data : 'id=' + id,
				async : false,
				success : function(data) {
					var f5 = location.href.replace(/^(.*)\#.*$/, '$1');
					swal({
						title : i18n('msg_success'),
						text : i18n('msg_deleted'),
						type : "success",
						timer : 3000,
						allowEscapeKey : false
					}, function() {
						location.href = f5;
					});
					window.setTimeout(function() {location.href = f5;}, 3000);
				},
				error : function(resp) {
					swal(i18n('msg_warning'), i18n('err_on_delete'), "warning");
				}
			});
		} else {
			swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "warning");
		}
	});
};



// function filterMove(){
// 	let input=document.getElementById("movList_filter").querySelector("input");
// 	let movimiento=localStorage.getItem("movimiento");
// 	var event = new Event('input', { bubbles: true });
// 	input.dispatchEvent(event);
// 	if(movimiento!=null){
// 		input.value=movimiento, input.dispatchEvent(event);
// 		localStorage.removeItem('movimiento'),// Hace scroll down a la posición de 1000px
// 			window.scrollTo(0, 1000);
// 		document.querySelector('#movList').querySelector(".odd").setAttribute("style","background-color:#ffe52b99!important;");
// 		document.getElementById("movList_filter").querySelector("input").setAttribute("style","background-color:#ffe52b99!important;");
// 		color();
// 	}
// };
const filtrar=()=>{
	let movimientoId=localStorage.getItem("movimiento")
	let input=document.querySelectorAll(".inputFilter")[0]
	if(movimientoId!=null){
	input.value=movimientoId
	var inputEvent = new Event('input');
    input.dispatchEvent(inputEvent);
	localStorage.removeItem("movimiento")
	window.scrollTo(0, 1000);
	color()}
};

function color(){
	let transparent=99;
	let idint= setInterval(() => {
		transparent-=11;
		document.querySelector('#movList').querySelector(".odd").setAttribute("style",`background-color:#ffe52b${transparent}!important;`);
		document.getElementById("movList_filter").querySelector("input").setAttribute("style",`background-color:#ffe52b${transparent}!important;`);
		if(transparent==0){clearInterval(idint);}
	}, 100);
};
window.addEventListener("load", filtrar);

function setdpM(id,d,inPattern,outPattern){
	var newdate=formatDateTime(d,inPattern,outPattern);
	if(this.id=='startschdFix'&&$('#endschd').val()=='')
		setBootstrapUtcDate('endschd','endschdFix',newdate);
	else if(this.id=='edStartschdFix'&&$('#edEndschd').val()=='')
		setBootstrapUtcDate('edEndschd','edEndschdFix',newdate);
	if(id!=''&&(d!=null&&d!='')){
		var dt=formatDateTime(d,inPattern,outPattern);
		$(id).datepicker('setDate',dt);
		dt=formatDateTime(d,inPattern,'yyyy-MM-dd');
		$(id+'Fix').datepicker('setDate',dt);
	}
};

$('.clientDataRow').on('click',function(e){
	$('.displaydata').toggleClass('arrowdata');
	$('.clientdivdata2').fadeToggle('fast');
});

$('.cleanMovAct').on('click', function() {
	swal({
		title:'',
		text :i18n('msg_clear_schedule_data'),
		type :"warning",
		showCancelButton : true,
		confirmButtonClass:'btn-warning',
		confirmButtonText : i18n('btn_yes_clean_it'),
		closeOnConfirm: false,
		closeOnCancel : false
	}).then((isConfirm) => {
		if (isConfirm){
			$('#activity,#edActivity,[data-date="addMovAct"],[data-date="editMovAct"],[data-time]').val('');
			$('#puterrorOnAddMov,#puterrorOnEditMov').html('');
			$('#errorOnAddMov,#errorOnEditMov').hide();
			$('input, select').parent().removeClass('has-error');
		}
		swal.close();
	});
});

$('#actType').on('change',function(){
	var o=$('#actType option:selected').val();
	if(o=='1'||o=='2'){
		$('.actype1').removeClass('dnone');
		$('.actype2').addClass('dnone');
	}else if(o=='3'||o=='4'){
		$('.actype1').addClass('dnone');
		$('.actype2').removeClass('dnone');
	}
});
$('#edActType').on('change',function(){
	var o=$('#edActType option:selected').val();
	if(o=='1'||o=='2'){
		$('.actype1').removeClass('dnone');
		$('.actype2').addClass('dnone');
	}else if(o=='3'||o=='4'){
		$('.actype1').addClass('dnone');
		$('.actype2').removeClass('dnone');
	}
});

$("#starttimehour").on('change', function(){
	if($("#endtimehour").val()=='')
		$('#endtimehour').val($("#starttimehour").val());
});
$("#starttimeminute").on('change', function(){
	if($("#endtimeminute").val()=='')
		$('#endtimeminute').val($("#starttimeminute").val());
});

$("#edStarttimehour").on('change', function(){
	if($("#edEndtimehour").val()=='')
		$('#edEndtimehour').val($("#edStarttimehour").val());
});
$("#edStarttimeminute").on('change', function(){
	if($("#edEndtimeminute").val()=='')
		$('#edEndtimeminute').val($("#edStarttimeminute").val());
});

var listItems='<option value="" title="'+i18n('msg_hour')+'" selected>'+i18n('msg_select')+'</option>';
for(i=0;i<24;i++){
	var h=i<10?'0'+i:i+'';
	listItems+='<option value="'+h+'">'+h+'</option>';
};
$('[data-time="hour"]').append(listItems);
listItems='<option value="" title="'+i18n('msg_minutes')+'" selected>'+i18n('msg_select')+'</option>';
for(i=0;i<60;i+=5){
	var m=i<10?'0'+i:i+'';
	listItems+='<option value="'+m+'">'+m+'</option>';
};
$('[data-time="minute"]').append(listItems);