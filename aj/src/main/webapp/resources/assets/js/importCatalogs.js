;function getHeaders(){
	$('#previewimport thead').empty();
	$.ajax({
		type : 'POST',
		url : ctx + '/getHeaders',
		data : 'forheader=' + $('#catalogList li.selected').data('value'),
		async : false,
		success : function(data) {
			if (data.length<0)return;
			var cont=1, list='<th><select data-localColumn="xXx" onchange="getLocalColumn(this);">'
				+ '<option disabled>' + i18n('msg_select') + '</option>';
			for(i=0;i<data.length;i++){
				var info=data[i].split(', '),
					datatype=(info[1].split('=')[1]),
					isnullable=(info[2].split('=')[1]);
				list+='<option value="' + info[0] + '">' + info[0] + '</option>';
			}
			list+='<option value="ext">(tabla externa)</option>'
				+ '<option>(omitir)</option></select></th>';
			var preview='', cont=1;
			for(i=0;i<data.length;i++){
				var info=data[i].split(', '), tmp=list;
				var rex=new RegExp('(value="' + info[0] + '")', 'g');
				var listtmp=tmp.replace(rex,'$1 selected').replace('xXx', cont);
				preview=preview+listtmp;
				cont++;
			}
			$('#previewimport thead').append('<tr>'+preview+'</tr>');
		},error : function(e) {
			console.log(i18n('err_select_catalog')+'. '+e);
		}
	});
};

function splitFile(){
	$('#previewimport tbody').empty();
	var allRows=($('#originalImport').html()).split('\n'),
		delimiter=$('input[name="delimiters"]:checked');
	var delm=delimiter[0].value;
	for(r=0;r<allRows.length;r++){
		var allCols=allRows[r].split(delm), isEmptyCol=0, preview='<tr>';
		for(c=0;c<allCols.length;c++){
			preview+='<td data-value="' + allCols[c] + '" on>'+allCols[c]+'</td>';
			isEmptyCol+=allCols[c].length;
		}
		if(isEmptyCol>0)
			$('#previewimport tbody').append(preview+'</tr>');
	}
};

var fileToLoad;
$('#selectedFile').on('change', function(){
	fileToLoad=document.getElementById("selectedFile").files[0];
	var fileReader=new FileReader();
	fileReader.onload=function(fileLoadedEvent){
		$('#originalImport').html(fileLoadedEvent.target.result);
		splitFile();
		$('#startFromRow').val('1');
	};
	fileReader.readAsText(fileToLoad, "UTF-8");
});

function getLocalColumn(e){
	if($(e).val()!='ext')return;
	$('#externalList1, #externalList2, #externalList3, #externalList4').html('');
	$('#idxCol').val(e.getAttribute('data-localColumn'));
	$.ajax({
		type : "POST",
		url : ctx + "/linkToExternal",
		async : false,
		success : function(info) {
			if (info.length<0)return;
			$('#externalList1').append('<option value="0" selected disabled>' + i18n('msg_select') + '</option>');
			for (var i in info) {
				var opt = info[i].split(",");
				$('#externalList1').append('<option value="'+ opt[1] + '" title="'+ opt[0] + '">' + opt[0] + '</option>');
			}
			$('#external-modal').modal('show');
		},error : function(e) {
			console.log(i18n('err_select_catalog')+'. '+e);
		}
	});
};

function getExternalHeaders(){
	$('#externalList2, #externalList3').html('');
	$.ajax({
		type : "POST",
		url : ctx + "/getHeadersExt",
		data : "forheader=" + $('#externalList1 option:selected').val(),
		async : false,
		success : function(data) {
			if (data.length<0)return;
			var list='<option disabled>' + i18n('msg_select') + '</option>';
			for(i=0;i<data.length;i++){
				var info=data[i].split(', '),
					datatype=(info[1].split('=')[1]),
					isnullable=(info[2].split('=')[1]);
				list+='<option value="' + info[0] + '">' + info[0] + '</option>';
			}
			$('#externalList2, #externalList3').append(list);
			$("#externalList2").val($("#externalList2 option:eq(1)").val());
			$("#externalList3").val($("#externalList3 option:eq(2)").val());
			$('#startFromRow').val('1');
		},error : function(e) {
			console.log(i18n('err_select_catalog')+'. '+e);
		}
	});
};

function getListInfo(){
	var cat=$('#externalList1').val(),
		val=$('#externalList2').val(),
		info=$('#externalList3').val(),
		ddlist='<select>';
	if(cat=='' || cat==null)return;
	if(info=='' || info==null)info=val;
	$.ajax({
		type : "POST",
		url : ctx + "/getListInfo",
		data : "catalog=" + cat + "&val=" + val + "&info=" + info,
		async : false,
		success : function(info) {
			if (info.length<0)return;
			ddlist+='<option value="0" disabled>' + i18n('msg_select') + '</option>';
			for (i=0; i<info.length; i++){
				if(info[i][1]==null)
					ddlist+='<option value="' + info[i] + '">' + ((val==info)?info[i]:info[i]) + '</option>';
				else
					ddlist+='<option value="' + info[i][0] + '">' + ((val==info)?info[i][0]:info[i][1]) + '</option>';
			}
		},error : function(e) {
			console.log(i18n('err_select_catalog')+'. '+e);
		}
	});
	return ddlist+='</select>';
};

function applyCells(){
	var err="",headerNum=$('#idxCol').val()*1;
		cat=$('#externalList1').val(),
		val=$('#externalList2').val(),
		info=$('#externalList3').val(),
		dflt=$('#externalList4').val();
	if(cat=='' || cat==null)
		err='err_select_option_list';
	else if(val=='' || val==null)
		err='err_select_option_list';
	if(err!=''){
		swal({title:i18n('msg_error'),text:i18n(err),type:'error'});
		return;
	}
	var table=document.getElementById("previewimport"), list=getListInfo();
	if(dflt!=''||dflt!=null){
		var rex=new RegExp('(value="' + dflt + '")', 'g');
		list=list.replace(rex,'$1 selected');
		list=list.replace('<select','<select data-value="" ');
	}
	for (var i in table.rows) {
		var row = table.rows[i], idxCol=0;
		for (var j in row.cells) {
			idxCol++;
			if(idxCol>headerNum)continue;
			var col = row.cells[j];
			if(row.cells[0].tagName=='TH')continue;
			if(col.tagName=='TD'){
				if(idxCol==headerNum)
					$(col).html(list);
			}else{
				$(row).append('<td>' + (idxCol==headerNum?list:'') + '</td>');
			}
		}
	}
	$('[data-localColumn="' + headerNum + '"]').val(val);
	$('#external-modal').modal('hide');
};

function setNewValue(){
	var newvalue=$('#newvalue').val();
};

function startImport(){
	$('td').filter('[data-col="status"]').remove();
	var cat=($('#catalogList li.selected').data('value')).toLowerCase(), err='', url, param='';
	if(cat==''||cat==null)
		err='err_select_catalog';
	else if(fileToLoad==null || fileToLoad.size === 0)
		err='msg_select_file';
	if(err!=''){
		swal({title:i18n('msg_error'),text:i18n(err),type:'error'});
		return;
	}
	var table=$('#previewimport thead tr [data-localcolumn]'), registro={}, headers=[],
		startFrom=$('#startFromRow').val()-1,totCols=table.length, rownum=0, totRecs=0,
		totFail=0, totDpl=0, totSkipped=0;
	headers.push('');
	for(i=0; i<table.length; i++)
		if(table[i].value!="(omitir)")
			headers.push(table[i].value);
	$($('#previewimport thead tr')[0]).prepend('<td class="stupload" data-col="status">Estatus</td>');

	table=$('#previewimport tbody tr');
	for(i=0; i<table.length; i++){
		if(i<startFrom){
		//if($($('#previewimport tbody tr')[rownum]).css('textDecoration')!='none'){
			info='Omitido por el usuario';
			shortresp='>Omitido';
			totSkipped++;
		}else{
			var row=$('#previewimport tbody tr:eq('+i+') td'),info='',shortresp='',resp='';
			for(t=0; t<row.length; t++){
				var value='';
				if(row[t].tagName=='TD')
					value=row[t].getAttribute('data-value');
				else if(row[t].tagName=='SELECT')
					value=row[t].value;
				else
					value=null;
				param+=headers[t+1] + '=' + value + '&';
			}
			param=param.replace(/.$/,'');

			if(cat=='paises'){
				url='addNewCountry';
			}else if(cat=='ciudades'){
				url='saveNewCity';
			}else if(cat=='estados'){
				url='addNewState';
			}else if(cat=='juzgados'){
				url='saveNewCourt';
				param=param.replace('ciudadid','cdid');
				param=param.replace(/^juzgado/g,'description');
				param+='&judges=';
			}else if(cat=='tribunalcolegiado'){
				url='saveNewCollegiateCourt';
				param=param.replace(/^tribunalcolegiado/,'description');
			}else if(cat=='tribunalunitario'){
				param=param.replace(/^tribunalunitario/,'description');
				url='saveNewUnitaryCourt';
			}
			$.ajax({
				type : "POST",
				url : ctx + "/" + url,
				data : param,
				async : false,
				success : function(response) {
					if (response == 'msg_data_saved') {
						shortresp='>Registrado';
						totRecs++;
					}else if (response == 'err_record_no_saved') {
						shortresp=' style="color:red" >Error';
						totFail++;
					}else if (response == 'msg_empty_data') {
						shortresp=' style="color:goldenrod" >Incompleto';
						totFail++;
					}else if (response == 'err_duplicated_data') {
						shortresp=' style="color:#39c" >Existente';
						totDpl++;
					}else{
						shortresp=' style="color:red" >Error';
						info='err_record_no_saved';
						totFail++;
					}
					resp=response;
				},error : function(e) {
					shortresp='style="color:red" >Error';
					resp='err_record_no_saved';
					console.log(i18n('err_record_no_saved')+'. '+e);
					totFail++;
				}
			});
		}
		var results='<td class="stupload" data-col="status"><span title="' + i18n(resp) + '"' + shortresp + '</span></td>';
		$($('#previewimport tbody tr')[rownum]).prepend(results);
		rownum++;
		param='';
	}
	swal(i18n('msg_finished_process'), i18n('msg_check_results')
		+'\nNúmero de filas: '+ (totRecs+totFail+totDpl+totSkipped)
		+'\nRegistrados: '+totRecs
		+'\nCon error: '+totFail
		+'\nDuplicados: '+totDpl
		+'\nOmitidos: '+totSkipped, 'info');
};

$('input[name="delimiters"], #personalDelimit').on('change keyup', function(){
	splitFile();
});

$('#personalDelimit').on('keyup input paste change delete', function(){
	$('#otherDelimit').val(this.value);
});

$("#importFile").click(function() {
	$('#catalog-modal').modal('show');
});

$('#startFromRow').on('blur', function(){if((this.value*1)<1)this.value='1';});
$('#startFromRow').on('keyup input paste change delete', function(){
	var avoidrows=(this.value)-1;
	var norow=$('#previewimport tr');
	$('#previewimport tr').css('text-decoration','').css('color','');
	for(r=0;r<avoidrows;r++){
		$(norow[r+1]).css('text-decoration','line-through').css('color','darkred');
	}
});
$('#externalList4').on('focus', function(){
	if($('#externalList1').val()=='' || $('#externalList1').val()==null){
		swal(i18n('msg_error'), i18n('err_select_catalog'), 'error');
		return;
	}
	$(this).html('');
	$(this).append(getListInfo().replace(/<\/?select>/g,''));
});
/* limpia inputs */
$('#catalog-modal').on('hidden.bs.modal',function(e) {
	$(this).find("input,textarea").val('').end().find(
		"input[type=checkbox], input[type=radio]").prop("checked","").end();
});