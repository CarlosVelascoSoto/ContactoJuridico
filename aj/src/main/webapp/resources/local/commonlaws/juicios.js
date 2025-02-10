;function getClientListTab(targetList,abbr) {
	$('.containTL table').empty();
	abbr=abbr||'clie';
	$.ajax({
		type : "POST",
		url : ctx + "/getClientList",
		async : false,
		dataType : 'JSON',
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				var tablelist = '<tr><th style="display:none;"></th>'
					+ '<th>' + i18n('msg_client') + '</th>'
					+ '<th>' + i18n('msg_address') + '</th>'
					+ '<th>' + i18n('msg_city') + '</th></tr>';
				for (i = 0; i < info.length; i++)
					tablelist += '<tr class="rowopt"><td style="display:none">'
					+ '<input type="radio" name="rowline" data-val="'
					+ info[i][1] + '" id="' + abbr + info[i][0] + '"></td>'
					+ '<td>' + info[i][1] + '</td>'
					+ '<td>' + info[i][2] + '</td>'
					+ '<td>' + info[i][3] + ', ' + info[i][4] + '</td></tr>';
				$(targetList).append(tablelist);
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_client') + '. ' + e);
		}
	});
};

;function getRooms(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getRooms",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="' + info[i].salaid+ '" title="' + info[i].sala + '">'
						+ info[i].sala + '</' + elemOp + '>');
				/*$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Room\');"><b>'
					+ i18n('msg_addnew') + '</b></' + elemOp + '>');*/
				$('#' + id).append('<'+ elemOp+ '></' + elemOp + '>');
				
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_room') + '. ' + e);
		}
	});
};

function getCourts(id, elemtype, cityid, state) {
	var elemOp = 'option', url = 'getCourtsByCity';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	if (isNaN(cityid - 0)) {
		state = state || '0';
		url = 'getCourtsByState';
	}
	cityid = cityid || '0';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/" + url,
		data : 'cityid=' + cityid + '&stateid=' + state,
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].juzgadoid + '" title="'+ info[i].juzgado + '">'
						+ info[i].juzgado + '</' + elemOp + '>');
				/*$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Court\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');*/
				$('#' + id).append('<'+ elemOp+ '></' + elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_court') + '. ' + e);
		}
	});
};

/**
@param court		Id destino donde se cargará el listado.
@param matter		Id del listado donde tomará la materia.
@param state		Id del listado donde tomará el estado.
@param city			Id del listado donde tomará la ciudad.
@param distpart		Id del listado donde tomará el distrito o el partido.
@param elemtype		Tipo de elemento a cargar; "select", "ol" o "ul".	*/
function getCourtsFilters(court, matter, state, city, distpart, tipojuzgado, elemtype) {
	var elemOp = 'option',param='',liFilters1=['matter','state','city'],liFilters2=[matter,state,city];
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + court).empty();
	for(o=0;o<liFilters1.length;o++)
		param+='&'+liFilters1[o]+'='+ ($('#'+liFilters2[o]+' li.selected').val()||0);
	param+='&distpart=' +$('input[name="'+distpart+'opt"]:checked').val() +'|'+ $('#'+distpart).val()
		+'&tipojuzgado='+tipojuzgado;
	$.ajax({
		type : "POST",
		url : ctx + "/getCourtsFilters",
		data : param.replace(/^./g,''),
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + court).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + court).append('<' + elemOp + ' value="'+ info[i].juzgadoid + '" title="'+ info[i].juzgado
						+ '" data-cdid="' + info[i].ciudadid + '" >' + info[i].juzgado + '</' + elemOp + '>');
				$('#' + court).append('<'+ elemOp+ '></' + elemOp + '>');
			}else{
				$('#' + court).append('<'+ elemOp+ '>' + i18n('msg_no_data') + '</' + elemOp + '>');
			}
		},error : function(e) {
			$('#' + court).append('<'+ elemOp+ '>' + i18n('msg_no_data') + '</' + elemOp + '>');
			console.log(i18n('err_unable_get_court') + '. ' + e);
		}
	});
};

function getCountries(id, elemtype) {
	var elemOp = 'option';
	if (elemtype=='ul' || elemtype=='ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getCountries",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype=='select' || elemtype=='')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].paisid + '" title="'+ info[i].pais + '">'
						+ info[i].pais + '</' + elemOp+ '>');
				$('#' + id).append('<' + elemOp + '></'+ elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_country') + '. ' + e);
		}
	});
};

function getEstados(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getStates",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].estadoid + '" title="'+ info[i].estado + '">'
						+ info[i].estado + '</' + elemOp+ '>');
				/*$('#' + id).append('<' + elemOp + ' value=""><b>'+ i18n('msg_addnew') + '</b></'+ elemOp + '>');*/
				$('#' + id).append('<'+ elemOp+ '></' + elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_state') + '. ' + e);
		}
	});
};

function getCiudades(id, elemtype, filterState) {
	var elemOp = 'option', url = 'getCiudades', state = '0';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	if (isNaN(filterState - 0)) {
		state = $('#' + filterState + ' li.selected').val();
		state = state || 0;
		url = 'getCitiesByState';
	}
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/" + url,
		data : 'estadoid=' + state,
		async : false,
		success : function(data) {
			var info = null;
			if (isNaN(filterState - 0))
				info = data[0];
			else
				info = data[0].cdList;
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].ciudadid + '" title="'+ info[i].ciudad + '">'
						+ info[i].ciudad + '</' + elemOp + '>');
				/*$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'City\');"><b>'
					+ i18n('msg_addnew') + '</b></' + elemOp + '>');*/
				$('#' + id).append('<'+ elemOp+ '></' + elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_city') + '. ' + e);
		}
	});
};

function getMaterias(id, elemtype) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getMaterias",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].materiaid + '" title="'+ info[i].materia + '">'
						+ info[i].materia + '</' + elemOp+ '>');
				/*$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Matter\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');*/
				$('#' + id).append('<'+ elemOp+ '></' + elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_matter') + '. ' + e);
		}
	});
};
/*
function getActType(id) {
	$('#' + id).find('option').remove().end();
	$.ajax({
		type : "POST",
		url : ctx + "/getActType",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				var option = new Option(i18n('msg_select'), '', !1, !0);
				$('#' + id).append(option);
				$(option).attr('disabled', !0);
				for (i = 0; i < info.length; i++) {
					option = new Option(info[i].tipoactuacion, info[i].tipoactuacionid, !0, !1);
					$('#' + id).append(option);
					$(option).attr('title', info[i].tipoactuacion);
				}
			}
		},error : function(e) {
			console.log("Error al obtener Tipos de Actividad. " + e);
		}
	});
};*/

function getClientListSel(id) {
	$('#' + id).find('option').remove().end();
	$.ajax({
		type : "POST",
		url : ctx + "/getClientList",
		async : false,
		dataType : 'JSON',
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				var option = new Option(i18n('msg_select'), '', !1, !0);
				$('#cliS' + id).append(option);
				$(option).attr('disabled', !0);
				for (i = 0; i < info.length; i++) {
					option = new Option(info[i][2], info[i][1], !0, !1);
					$('#cliS' + id).append(option);
					$(option).attr('title', info[i][2] + ',' + info[i].city + ',' + info[i].country);
				}
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_client') + '. ' + e);
		}
	});
};

function getJudgesByCourtid(courtid,judgeid,elemtype){
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	$('#' + judgeid).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getJudgesByCourtid",
		data : 'courtid='+courtid,
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					$('#' + judgeid).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + judgeid).append('<' + elemOp + ' value="'+ info[i].juezid + '" title="'+ info[i].nombre + '">'
						+ info[i].nombre + '</' + elemOp+ '>');
				$('#' + judgeid).append('<'+ elemOp+ '></' + elemOp + '>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_judges') + '. ' + e);
		}
	});
};

document.addEventListener('click',function(e){
	if (e.target.nodeName == 'TD') {
		var tpe=e.target.parentElement,targetId='',rb;
		var tList=tpe.parentElement.parentElement.id;
		if (tList=='trialClientList' || tList=='editTrialClientList')
			targetId=(tList=='trialClientList'?'trial':'editTrial')+'Client';
		else if (tList=='protClientList' || tList=='edProtClientList')
			targetId=(tList=='protClientList'?'prot':'edProt')+'Client';
		if(targetId!=''){
			$(' .rowopt').css('backgroundColor','#fff');
			tpe.style.backgroundColor="ivory";
			rb=$(tpe).find('input:radio[name=rowline]')[0];
			rb.checked=true;
			$('#'+targetId).val($(rb).attr('data-val'));
			$('.containTL').hide();
			if(targetId=='protClient'||targetId=='edProtClient'){
				var origin1='#selecttrialO', origin2='#appealList',showEmpty='#divAppealOrigin',
					clid=$('#protClientList [name=rowline]:checked')[0];
				$('#divTrialOrigin, #divAppealOrigin').hide();
				if($('input[name=origintype]:checked').val()=='Juicio')
					showEmpty='#divTrialOrigin';
				if(targetId=='edProtClient'){
					origin1='#edSelecttrialO', origin2='#edAppealList',showEmpty='#edDivAppealOrigin',
					clid=$('#edProtClientList [name=rowline]:checked')[0];
					if($('input[name=edOrigintype]:checked').val()=='Juicio')
						showEmpty='#edDivTrialOrigin';
				}
				clid=(typeof clid=='undefined')?'0':(clid.id).replace(/[^0-9]/g,'');
				$(origin1+', '+origin2).empty();
				if(clid>0){
					getRelClientTrial(clid,origin1);
					getRelClientAppeal(clid,origin2);
				}
				$(origin1+', '+origin2).parent().addClass('has-error');
				$(showEmpty).show();
			}
		}
	}
}, false);
/*
document.addEventListener('click',function(e){
	if (e.target.nodeName == 'TD') {
		var tpe=e.target.parentElement,targetId='',rb;
		var tList=tpe.parentElement.parentElement.id;
		if (tList=='trialClientList' || tList=='edClientList')
			targetId=(tList=='trialClientList'?'c':'edC')+'clientList';
		if(targetId!=''){
			$(' .rowopt').css('backgroundColor','#fff');
			tpe.style.backgroundColor="ivory";
			rb=$(tpe).find('input:radio[name=rowline]')[0];
			rb.checked=true;
			$('#'+targetId).val($(rb).attr('data-val'));
			$('.containTL').hide();
		}
	}
}, false);

document.addEventListener('click',function(e) {
	if (e.target.nodeName == "TD") {
		if (e.target.parentElement.parentElement.parentElement.id != 'clientList')
			return;
		$('.rowopt').css('backgroundColor', '#fff');
		e.target.parentElement.style.backgroundColor = "ivory";
		var xy = e.target.parentElement.firstElementChild.firstElementChild;
		xy.checked = true;

		$('.containTL').hide();
		var rb = $('#clientList [name=rowline]:checked');
		var text = $(rb).attr('data-val');
		$('#client').val(text);
	}
}, false);

document.addEventListener('click', function(e) {
	if (e.target.nodeName == "TD") {
		if (e.target.parentElement.parentElement.parentElement.id != 'editTrialClientList')
			return;
		$('.rowopt').css('backgroundColor', '#fff');
		e.target.parentElement.style.backgroundColor = "ivory";
		var xy = e.target.parentElement.firstElementChild.firstElementChild;
		xy.checked = true;

		$('.containTL').hide();
		var rb = $('#editTrialClientList [name=rowline]:checked');
		var text = $(rb).attr('data-val');
		$('#editTrialClient').val(text);
	}
}, false);*/

function addTrial(e) {
	$('#errorOnAddTrial, .containTL').hide();
	$('input, select').parent().removeClass('has-error');
	validDDFilter();
	var err = '', tag = '', actor = $('#actor').val(), defendant = $('#defendant').val(),
		clientid = '', clientname = $('#client').val(),
		proceedings = $('#proceedings').val(), court = $('#court li.selected').val()||'0',
		third = $('#third').val(), matter = $('#matter li.selected').val()||'0',
		city = $('#city li.selected').val()||$('#court li.selected').attr('data-cdid')||'0',
		lawyer = $('#lawyer').val(), trialtypeid = $('#trialtype li.selected').val()||'0',
		lawyerassigned = $('#lawyerassigned li.selected').val()||'0',
		lawyercounterpart = $('#lawyercounterpart').val(), valideFirm = 'true',
		allCustCol = $('[data-addcolumn="input"]'), dataCustCol = '',
		judgename=$('#judges li.selected').val()||'0', valideUser,
		countryid=$('#countryid li.selected').val()||'1';
	if (typeof $('#trialClientList [name=rowline]:checked')[0] != 'undefined')
		clientid = $('#trialClientList [name=rowline]:checked')[0].id;
	clientid = (typeof clientid == 'undefined') ? '' : clientid.replace(/[^0-9]/g,'');
	lawyerassigned = lawyerassigned || '';
	if (clientid == '' || clientname == ''){
		tag='client';
		err = 'err_select_client';
	}else if (matter==''||matter=='0'){
		tag='matter';
		err = 'err_select_matter';
	}else if (court==''||court=='0'){
		tag='court';
		err = 'err_select_court';
	}else if (city == ''||city=='0'){
		tag='city';
		err = 'err_select_city';
	}else if (lawyer == ''){
		tag='lawyer';
		err = 'err_select_lawyer';
	}
	for (a = 0; a < allCustCol.length; a++)
		dataCustCol += $(allCustCol[a]).data('addcustcol') + '|' + $(allCustCol[a]).val() + '~';
	if (err != "") {
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnAddTrial').show();
		$('#putErrorOnAddTrial').html(i18n(err));
		$('.custombox-modal-open').animate({scrollTop : 0}, '1000');
		return false;
	}
	valideFirm = '&valideFirm=' + ($('#valideFirm').length===0 ?'0':$('#valideFirm li.selected').val());
	valideUser='&valideUser=' + ($('#valideUser').length===0 ?'0':$('#valideUser li.selected').val());
	var param = 'actor='+actor + '&defendant='+defendant + '&clientid='+clientid
		+'&proceedings='+proceedings +'&court='+court + '&city='+city+ '&third='+third
		+'&trialtypeid='+trialtypeid+'&matter='+matter + '&lawyer='+lawyer
		+'&lawyerassigned='+lawyerassigned  + '&lawyercounterpart='+lawyercounterpart
		+ valideFirm + valideUser +'&dataCustCol='+(dataCustCol.replace('.$', ''))
		+'&judgename='+judgename + '&countryid='+countryid;
	$('input[name^="fileuploadx_"]').each(function(i) {
		param += "&" + $(this).attr("name") + "=" + $(this).val();
	});
	
	$.ajax({
		type : "POST",
		url : ctx + "/addNewTrial",
		data : param,
		async : false,
		success : function(data) {
			if (data == 'msg_data_saved') {
				swal(i18n("msg_success"), i18n(data), "success");
				location.href = location.href.replace(/^(.*)\#.*$/, '$1');
			} else {
				$('#putErrorOnAddTrial').html(i18n(data));
				$('#errorOnAddTrial').show();
			}
			$('#trial-modal').modal('toggle');
			
		},
		error : function(er) {
			$('#putErrorOnAddTrial').html(i18n('err_record_no_saved'));
			$('#errorOnAddTrial').show();
		}
	});
};

$('#trialClient').on('focus keyup input paste change delete', function(e) {
	filtext(e.target.value, '#trialClientList');
	$('.containTL').show();
});

$('#editTrialClient').on('focus keyup input paste change delete', function(e) {
	filtext(e.target.value, '#editTrialClientList');/*
	$('html, body').animate({scrollTop : 0}, '300');
	var id = e.target.id, input, filter, table, tr, i;
	input = e.target;
	filter = input.value.toUpperCase();
	table = document.getElementById(id + 'List');
	tr = $('#c' + id + 'List .rowopt');
	for (i = 0; i < tr.length; i++)
		if (tr[i].innerText.toUpperCase().indexOf(filter) >= 0)
			tr[i].style.display = "";
		else
			tr[i].style.display = "none";*/
	$('.containTL').show();
});

function getDetailsToEdit(id) {
	var param = "id=" + id;
	$('#errorOnEditTrial, [data-addInputforcol], [data-editinputforcol], .containTL').hide();
	$.ajax({
		type : 'POST',
		url : ctx + "/getDetailsByTrial",
		data : param,
		async : false,
		success : function(data) {
			var info = data[0].detail[0]||[];
			if (info.length == 0) {
				swal(i18n('msg_warning'), i18n('err_unable_get_trails'), "error");
				return;
			}
			$('#edit-modal select, #edit-modal input[type="text"]')
				.not('input[type="hidden"]').val('');
			$('*[id*=date]').val('');
			$('#edid').val(info.juicioid);
			var clid=data[0].clientId;
			getClientListTab('#editTrialClientList');
			$('#editTrialClient').val(data[0].clientName);
			$('#editTrialClientList #clie' + clid).prop('checked',true);
			$('#clie' + clid).parent().parent().css('backgroundColor',"ivory");
			
			$('#editproceedings').val(info.juicio);
			$('#editactor').val(info.actor);
			$('#editdefendant').val(info.demandado);
			$('#editthird').val(info.tercero);
			$('#editstatus').val(info.status);
			getMaterias('editmatter', 'ul');
			if(info.materiaid != ''){
				getTextDDFilterByVal('editmatter', info.materiaid);

				getAccionByMatterId('editaccion','#editmatter li.selected', 'ul', 'edit');
				if (data[0].ttacc!=null) {
					var ttacc = data[0].ttacc[0];
					getTextDDFilterByVal('editaccion', ttacc.accionid);

					var tt = data[0].tt_info[0];
					if (tt != null) {
						$('#editactionway').html(data[0].wayname);
						getTrialTypesList(ttacc.accionid, 'edittrialtype', 'edit', 'ul', null);
						getTextDDFilterByVal('edittrialtype', info.juiciotipoid);
						setCustColumnByTT(data[0].custCol, data[0].colvalues, tt, ttacc, 'edit');
					}
				}
			}
			getCountries('edcountry','ul');
			getTextDDFilterByVal('edcountry', info.paisid);
			getCiudades('editcity', 'ul');
			getTextDDFilterByVal('editcity', info.ciudadid);
			getEstados('editstate', 'ul');
			getTextDDFilterByVal('editstate', data[0].estadoid);

			getCourts('editcourt', 'ul', $('#editcity li.selected').val());
			getTextDDFilterByVal('editcourt', info.juzgadoid);
			$('#editlawyer').val(info.abogado);
			$('#editlawyercounterpart').val(info.abogadocontraparte);
			getLawyerList('editlawyerassigned', 'ul',1);
			getTextDDFilterByVal('editlawyerassigned', info.abogadoasignado);
			if (info.juezid!=null && info.juezid>0){
				getJudgesByCourtid($('#editcourt li.selected').val(),'editjudges','ul');
				getTextDDFilterByVal('editjudges', info.juezid);
			}
			var validUser=($('#editValideUser').length)-0;
			if(validUser!=0)
				getTextDDFilterByVal('editValideUser', info.userid);
			validDDFilter();
			if(typeof setFirmTrue==='function')
				if($('#editValideFirm').length != 0)
					setFirmTrue(info);
		},error : function(resp) {
			swal(i18n('msg_warning'), 'Err-01: ' + i18n('err_unable_get_trial'), 'error');
		}
	});
	try {
		createDropZone('uploadXdivEdit', 'formjuicioedit', id, 1);
	} catch (e) {
		clearTemp();
		$('#areaTrialUpload').html('');
		$('#areaTrialUpload').html('<span class="textContent">' + i18n('msg_upload_area')+ '</span>'
			+ '<div id="uploadXdivEdit" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div></div>');
		createDropZone('uploadXdivEdit', 'formjuicioedit', id, 1);
	}
	$("#uploadXdivEdit").addClass("dropzone");
};

function updateData() {
	$('input').parent().removeClass('has-error');
	validDDFilter();
	var err = '', tag = '', edid = $('#edid').val(),
		actor = $('#editactor').val(), defendant = $('#editdefendant').val(),
		clientid = $('#editTrialClientList [name=rowline]:checked')[0].id,
		clientname = $('#editTrialClient').val(), proceedings = $('#editproceedings').val(),
		status=$('#editstatus').val(),court = $('#editcourt li.selected').val()||'0',
		third =$('#editthird').val(), matter= $('#editmatter li.selected').val()||'0',
		city = $('#editcity li.selected').val()||$('#edittrialtype li.selected').val()||'0',
		lawyer=$('#editlawyer').val(), lawyercounterpart=$('#editlawyercounterpart').val(),
		lawyerassigned = $('#editlawyerassigned li.selected').val()||'0',
		valideFirm = 'true', trialtypeid = $('#edittrialtype li.selected').val()||'0',
		allCustCol = document.querySelectorAll('[data-editcolumn="input"]'),
		dataCustCol = '', valideUser, judgename=$('#editjudges li.selected').val()||'0',
		countryid=$('#editcountryid li.selected').val()||'1';
	if (typeof $('#editTrialClientList [name=rowline]:checked')[0] != 'undefined')
		clientid = $('#editTrialClientList [name=rowline]:checked')[0].id;
	clientid = (typeof clientid == 'undefined') ? '' : clientid.replace(/[^0-9]/g,'');
	lawyerassigned = lawyerassigned || '';
	if (clientid == '' || clientname == '')
		err = 'err_select_client';
	if (err == '') {
		if (matter==''||matter=='0'){
			tag='editmatter';
			err = 'err_select_matter';
		}else if (court==''||court=='0'){
			tag='editcourt';
			err = 'err_select_court';
		}else if (city == ''||city=='0'){
			tag='editcity';
			err = 'err_select_city';
		}else if (lawyer == ''){
			tag='editlawyer';
			err = 'err_select_lawyer';
		}
	}
	for (a = 0; a < allCustCol.length; a++) {
		var ccvalueid=($(allCustCol[a]).data('editccvalueid')==null?'':'|'+$(allCustCol[a]).data('editccvalueid'));
		dataCustCol += $(allCustCol[a]).data('editcustcol') + '|' + $(allCustCol[a]).val() + ccvalueid +'~';
	}
	if (err != "") {
		$('#' + tag).parent().addClass('has-error');
		$('#errorOnEditTrial').show();
		$('#putErrorOnEditTrial').html(i18n(err));
		$('.modal').animate({scrollTop : 0}, '1000');
		return false;
	}
	valideFirm='&valideFirm='+($('#editValideFirm').length===0?'0':$('#editValideFirm li.selected').val());
	valideUser='&valideUser=' + ($('#editValideUser').length===0 ?'0':$('#editValideUser li.selected').val());
	var param='edid='+edid + '&actor='+actor + '&defendant='+defendant + '&clientid='+clientid
		+'&proceedings='+proceedings + '&court='+court + '&status='+status + '&countryid='+countryid
		+'&city='+city + '&third='+third + '&matter='+matter + '&lawyer='+lawyer
		+'&lawyerassigned='+lawyerassigned + '&lawyercounterpart='+lawyercounterpart
		+valideFirm + valideUser + '&trialtypeid='+trialtypeid
		+'&dataCustCol='+(dataCustCol.replace('.$', '')) + '&judgename='+judgename;
	$('input[name^="fileuploadx_"]').each(function(i) {
		param += "&" + $(this).attr("name") + "=" + $(this).val();
	});
	$.ajax({
		type : "POST",
		url : ctx + "/updateTrial",
		data : param,
		async : false,
		success : function(data) {
			if (data == "true") {
				swal({
					title : i18n('msg_success'),
					text : i18n('msg_data_saved'),
					type : "success",
					timer : 3000,
					allowEscapeKey : false
				}, function() {
					location.href = location.pathname + "?language=" + getLanguageURL();
				});
				window.setTimeout(function() {
					location.href = location.pathname + "?language=" + getLanguageURL();
				}, 3000);
			} else {
				$('#putErrorOnEditTrial').html(i18n('err_record_no_saved'));
				$('#errorOnEditTrial').show();
			}
		},error : function(e) {
			$('#putErrorOnEditTrial').html(i18n('err_record_no_saved'));
			$('#errorOnEditTrial').show();
		}
	});
};

/** Establece la fecha a los campos de bootstrap-datepicker
@param e		Id input tag fecha
@param efix		Id input tag Fix fecha.
@return utdDate Fecha en formato UTC o milisegundos	*/
/*function setBootstrapUtcDate(e, efix, utdDate) {
	if (utdDate == null || utdDate == '')
		return;
	var d = new Date(utdDate);
	var newD = d.getFullYear() + "-" + twoDigits(d.getMonth() + 1) + "-" + twoDigits(d.getDate());
	$('#' + efix).val(setFormatPatternDate(d, getFormatPatternDate("")));
	$('#' + e).val(newD);
};
*/
$("#addNewJuicio").click(function() {
	$('#errorOnAddTrial, [data-addInputforcol], [data-editinputforcol], .containTL').hide();
	$('input, select').parent().removeClass('has-error');
//	$('#addTrialtypecolumns, #formjuicionew ul').empty();
//	$('#addTrialtypecolumns').append('<span style="font-size:12px;position:relative;top:-15px;margin-left:30px">('+i18n('msg_select_trialtypeparties')+')</span>');
	$('#addNewJuicio').attr('href', '#trial-modal');
	$('#ErrorSelectMatter').hide();
	getClientListTab('#trialClientList');
	getMaterias('matter', 'ul');
	getEstados('state', 'ul');
	getLawyerList('lawyerassigned', 'ul',1);
	getCountries('country','ul');
	getTextDDFilterByVal('country', '1');

	try {
		myDropzone = createDropZone("uploadXdiv","formjuicionew", '', '');
		myDropzone.on('sending', function(file, xhr, formData) {
			// formData.append('idx', $('#usersession').val());
		});
	} catch (e) {
		clearTemp();
		$('#areaJuicioUpload').html('');
		$('#areaJuicioUpload').html('<span class="textContent">' + i18n('msg_upload_area') + '</span>'
			+ '<div id="uploadXdiv" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
		myDropzone = createDropZone("uploadXdiv", "formjuicionew", '', '');
		myDropzone.on('sending', function(file, xhr, formData) {
			// formData.append('idx', $('#usersession').val());
		});
	}
	$("#uploadXdiv").addClass("dropzone");
	
});

/**
@param Id		Id del tag donde se cargará la lista.
@param seType	Tipo de dato a agregar. Debe corresponder al mensaje i18n("msg_xxx").	*/
function openFilterSelModal(id, selType) {
	$('#errorOnAddFromFSel').hide();
	$('#addFrom-filtersel-modal input[type=text]').val('');
	$('#tocitycourt, #toactionway').addClass('dnone');
	if (selType.toLocaleLowerCase() == 'court') {
		$('#tocitycourt').removeClass('dnone');
		getCiudades('citycourt', 'ul');
	}else if (selType.toLocaleLowerCase() == 'accion') {
		$('#toactionway, #fornewactionway').removeClass('dnone');
		getWayList('fornewactionway', selType, 'ul')
	}
	$('.titleFromFSel').html(i18n('msg_' + selType.toLowerCase()));
	$('#addFrom-filtersel-modal').modal('show');
	$('#descriptionFSel').focus();
	$('#selIdFSel').val(id);
	$('#selTypeFSel').val(selType);
};

function addFromFilterSel() {
	$('input').parent().removeClass('has-error');
	var id = $('#selIdFSel').val(), selType = $('#selTypeFSel').val();
	$('#errorOnAddFromFSel').hide();
	$('.titleFromFSel').html(i18n('msg_' + selType.toLowerCase()));
	$('#addFrom-filtersel-modal').modal('show');
	var description = $('#descriptionFSel').val(), err = '', param = '';
	if (description == '') {
		err = 'msg_empty_data';
	} else if ($('#tocitycourt').is(':visible')) {
		var cdid = $('#citycourt li.selected').val(), stateid = $('#statecourt li.selected').val();
		if (cdid == 0 || cdid == null)
			err = 'err_enter_city';
		else if (stateid == 0 || stateid == null)
			err = 'err_select_state';
		param = 'ciudad=' + description + '&ciudadid=' + cdid + '&estadoid=' + stateid;
	} else if ($('#toactionway').is(':visible')) {
		var wayid = $('#fornewactionway li.selected').val(), mAddEd = '';
		if (wayid == 0 || wayid == null)
			err = 'msg_select_way';
		mAddEd=$('#trial-modal').css('display')=='block'?'matter':'editmatter';
		param = 'description='+description + '&descriptionen='+description + '&wayid='+wayid
			+ '&matterid=' + $('#' + mAddEd + ' li.selected').val();
		selType='Accion';
	} else {
		param = 'description='+description;
	}
	if (err != '') {
		$('#putErrorOnAddFromFSel').html(i18n(err));
		$('#errorOnAddFromFSel').show();
		return;
	}
	$.ajax({
		type : "POST",
		url : ctx + "/saveNew" + selType,
		data : param,
		async : false,
		success : function(data) {
			if (data == 'msg_data_saved') {
				swal({
					title : i18n('msg_success'),
					text : i18n('msg_data_saved'),
					type : "success",
					timer : 3000,
					allowEscapeKey : false
				}, function() {});
				window.setTimeout(function() {}, 3000);
				$('#addFrom-filtersel-modal').modal('hide');
				$('#tocitycourt').addClass('dnone');
				if (selType == 'Court')
					getCourts('court', 'ul', $('#city li.selected').val());
				else if (selType == 'Matter')
					getMaterias(id, 'ul');
				else if (selType == 'City')
					getCiudades(id, 'ul');
				else if (selType == 'Room')
					getRooms(id, 'ul');
				$('#' + id).prev().prev().val(description);
				var fsel = $('#' + id + ' li');
				for (i = 0; i < fsel.length; i++)
					if (fsel[i].value == data) {
						$(fsel[i]).addClass('selected');
						break;
					}
			} else {
				$('#putErrorOnAddFromFSel').html(i18n(data));
				$('#errorOnAddFromFSel').show();
			}
		},error : function(e) {
			$('#putErrorOnAddFromFSel').html(i18n('err_record_no_saved'));
			$('#errorOnAddFromFSel').show();
		}
	});
};

function getLawyerList(id, elemtype,sid) {
	var elemOp = 'option';
	if (elemtype == 'ul' || elemtype == 'ol')
		elemOp = 'li';
	sid=sid||0;
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		data : "sid="+sid,
		url : ctx + "/getLawyerList",
		async : false,
		success : function(data) {
			var firm = data[0].companyList||[], users = data[0].lawyerList||[];
			if (elemtype == 'select' || elemtype == '')
				$('#' + id).append('<' + elemOp + ' value="0" selected disabled>'
					+ i18n('msg_select') + '</' + elemOp + '>');
			for (i = 0; i < Object.keys(users).length; i++) {
				var company = '';
				for (f = 0; f < Object.keys(firm).length; f++)
					if (users[i].companyid == firm[f].companyid) {
						company = firm[f].company;
						break;
					}
				$('#' + id).append('<' + elemOp + ' value="' + users[i].id + '" title="'
					+ company + '">' + users[i].first_name + ' '
					+ users[i].last_name + '</' + elemOp + '>');
			}
			$('#' + id).append('<' + elemOp + '></' + elemOp + '>');
		},error : function(e) {
			console.log(i18n('err_unable_get_lawyer') + e);
		}
	});
};

function getTrialTypeById(ttid, addEdit) {
	var err = '', elemOp = 'option';
	if (ttid == 0 || ttid == null)
		err = 'err_select_trialtype';
	if (err != '') {
		swal(i18n('msg_warning'), i18n(err), "info");
		return;
	}
	
	$.ajax({
		type : "POST",
		url : ctx + "/getCustColByTrialType",
		data : 'id=' + ttid,
		async : false,
		success : function(data) {
			var info=data[0].info[0]||[];
			if(info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_trialtype'),"error");
				return;
			}
			var ccval=[];
			setCustColumnByTT(data[0].custcol, ccval, info, null, addEdit);
			return data;
		},error : function(e) {
			console.log(i18n('err_unable_get_trialtype') + '. ' + e);
		}
	});
};

/** Carga el listado de "tipos de juicio" de acuerdo a la acción.
@param accionid		Id de la acción.
@param listElement	Nombre del elemento donde se cargará la lista.
@param addEdit		Modal de donde será aplicado: "add" o "edit".
@param elemtype		Tipo de listado a cargar: "select", "ul" u "ol".
@param dataValues	(opcional) Indica los valores almacenados para ser colocados donde corresponden.
					En caso de no necesitarlo, indicarlo con <b>null</b>.	*/
function getTrialTypesList(accionid, listElement, addEdit, elemtype, dataValues) {
	var err='',elemOp='option';
	if(elemtype == 'ul'||elemtype=='ol')
		elemOp = 'li';
	$('#'+addEdit+'Trialtypecolumns').empty();
	$('#'+listElement).empty();
	if(!(/^[1-9][0-9]*$/.test(accionid)))
		accionid=0;
	6
	
	$.ajax({
		type : "POST",
		url : ctx + "/getTrialTypesList",
		data : 'accionid=' + accionid,
		async : false,
		success : function(data) {
			var list = data[0]||[], opt = '';
			if (list.length > 0) {
				if (elemtype == 'select' || elemtype == '')
					opt = '<' + elemOp + ' value="0" selected disabled>' + i18n('msg_select') + '</' + elemOp + '>';
				if (list.length == 1) {
					opt += '<' + elemOp + ' value="' + list[0].tipojuicioid + '" title="' + list[0].tipojuicio
						+ '" onclick="var t=getTrialTypeById(value, \''+ addEdit + '\');">'
						+ list[0].tipojuicio + '</' + elemOp + '>';
				} else {
					for (i = 0; i < list.length; i++)
						opt += '<' + elemOp + ' value="' + list[i].tipojuicioid
							+ '" title="' + list[i].tipojuicio
							+ '" onclick="var t=getTrialTypeById(value, \''+ addEdit + '\');">'
							+ list[i].tipojuicio + '</' + elemOp + '>';
				}
			} else {
				var opt = '<' + elemOp + ' value="0" disabled selected>' + i18n('msg_no_data_matter') + '</' + elemOp + '>';
			}
			$('#' + listElement).append(opt);
		},error : function(e) {
			console.log(i18n('err_unable_get_trialtype') + '. ' + e);
		}
	});
};

/** Obtiene y despliega los campos de captura y sus correspondientes valores.
@param custcol		Lista de customNames.
@param ccvalues		Lista de valores customcolumn. Si no se requiere, colocar un Array vacío.
@param trialtype	Lista de "tipos de juicios".
@param ttsac		Lista de "Tipos de juicios acción".
@param addEdit		Modal de donde será aplicado: "add" o "edit".	*/
function setCustColumnByTT(custcol, ccvalues, trialtype, ttsac, addEdit) {
	$('#' + addEdit.toLowerCase() + 'Trialtypecolumns').empty();
	var i = 0, finishsetup = '', assignedvalue='0', ccvid=0, setComplete=0,
		reqactor = (trialtype.requiereactor == 0 ? 'none' : 'block'),
		reqdef = (trialtype.requieredemandado==0 ? 'none' : 'block'),
		reqthird = (trialtype.requieretercero==0 ? 'none' : 'block');
	$('[data-' + addEdit + 'Inputforcol="actor"]').css('display',reqactor);
	$('[data-' + addEdit + 'Inputforcol="def"]').css('display',reqdef);
	$('[data-' + addEdit + 'Inputforcol="third"]').css('display',reqthird);
	setComplete=ccvalues.length>0?ccvalues.length:custcol.length;
	if(custcol.length>0){
		do{
			var idx=0;
			if(ccvalues.length>0 && custcol.length>0){
				do{
					if(custcol[idx].customcolumnid==ccvalues[i].customcolumnid)
						break;
					idx++;
				}while(custcol[idx]!=null);
				ccvid = ccvalues[i].customcolumnvalueid;
				assignedvalue = ' value="' + ccvalues[i].assignedvalue + '"';
			}else{
				idx=i;
			}
			var titlecol = custcol[idx].titulo, descrcol = custcol[idx].descripcion, reqcol = custcol[idx].obigatorio,
				titlecolen=custcol[idx].tituloen,descrcolen=custcol[idx].descripcionen,lencol=custcol[idx].longitud,
				needded = custcol[idx].masdeuno, inputtype =custcol[idx].tipodecolumna;
			if (titlecolen == '')titlecolen = titlecol;
			if (descrcolen == '')descrcolen = descrcol;
			var addonLeft = '<div class="input-group" data-group="addon" >' + '<span class="input-group-addon asLink" onclick="addoncolumn(this,\''
					+ addEdit + '\',\'remove\');">-</span>',
				addonRigth = '<span class="input-group-addon asLink" data-group="addon" onclick="addoncolumn(this,\''+ addEdit + '\',\'insert\');">+</span></div>',
				baseTop = '<div class="col-xs-12 col-sm-'+ lencol+ '" data-'+ addEdit+ 'column="length"><div class="form-group inlineflex w100p">',
				baseMiddle = '<label class="supLlb" data-'+ addEdit+ 'column="title">' + ('en' == getLanguageURL() ? titlecolen: titlecol) + '</label>'
					+ '<input type="'+ inputtype + '" class="form-control c39c"'
					+ (ccvid>0?' data-' + addEdit + 'ccvalueid="' + ccvid + '"':'')
					+ ' data-'+ addEdit+ 'column="input"'
					+ ' data-'+ addEdit+ 'custcol="'+ custcol[idx].customcolumnid + '"'
					+ assignedvalue
					+ ' title="'+ ('en' == getLanguageURL() ? descrcolen: descrcol) + '"'
					+ ' placeholder="'+ ('en' == getLanguageURL() ? descrcolen: descrcol) + '"'
					+ ' autocomplete="off"'
					+ (reqcol == 0 ? '' : ' required') + '>',
				baseBottom = '</div></div>';
			finishsetup += baseTop + (needded == 1 ? addonLeft + baseMiddle + addonRigth : baseMiddle) + baseBottom;
			i++;
		}while(i<setComplete);
	}
	$('#' + addEdit.toLowerCase() + 'Trialtypecolumns').append(finishsetup);
};

/** Obtiene los campos de captura correctos.
@param ttid			Id del tipo de juicio.
@param trialtype	Nombre del elemento donde esta el listado de tipo de juicio.
@param addEdit		Modal de donde será aplicado: "add" o "edit".
@param dataValues	(List) Valores de las columnas personalizadas.	*/
function getCCByTrialType(ttid, trialtype, addEdit, dataValues) {
	var err = '', tag = '';
	$('#' + addEdit.toLowerCase() + 'Trialtypecolumns').empty();
	if (ttid == 0 || ttid == null) {
		tag = trialtype;
		err = 'err_select_trialtype';
	}
	if (err != '') {
		$('#' + tag).parent().addClass('has-error');
		swal(i18n('msg_warning'), i18n(err), "info");
		return;
	}
	$.ajax({
		type : "POST",
		url : ctx + "/getCCByTrialType",
		data : 'ttid=' + ttid,
		async : false,
		success : function(data) {
			var trialtype = data[0]||[];
			if (trialtype.length <= 0) {
				addEdit = addEdit == '' ? 'Add' : 'Edit';
				$('#errorOn' + addEdit).show();
				$('#putErrorOn' + addEdit).html(i18n('err_unable_get_trialtype'));
				$('.custombox-modal-open').animate({scrollTop : 0}, '1000');
				return false;
			}
			var colnames = data[0].colnames, finishsetup = '',
				reqactor = (trialtype[0].requiereactor == 0 ? 'none' : 'block'),
				reqdef = (trialtype[0].requieredemandado==0 ? 'none' : 'block'),
				reqthird = (trialtype[0].requieretercero==0 ? 'none' : 'block');
			$('[data-' + addEdit + 'Inputforcol="actor"]').css('display',reqactor);
			$('[data-' + addEdit + 'Inputforcol="def"]').css('display', reqdef);
			$('[data-' + addEdit + 'Inputforcol="third"]').css('display',reqthird);
			if (colnames.length > 0) {
				var forloop = colnames.length;
				if (dataValues != null)
					forloop = dataValues.length;
				for (i = 0; i < forloop; i++) {
					var titlecol = colnames[i].titulo, descrcol = colnames[i].descripcion,
						reqcol = colnames[i].obigatorio, titlecolen = colnames[i].tituloen,
						descrcolen = colnames[i].tipodecolumna, lencol = colnames[i].longitud,
						needded = colnames[i].masdeuno, column = '', colvalue = '';
					var addonLeft = '<div class="input-group" data-group="addon" >'
							+ '<span class="input-group-addon asLink" onclick="addoncolumn(this,\'' + addEdit + '\',\'remove\');">-</span>',
						addonRigth = '<span class="input-group-addon asLink" data-group="addon" onclick="addoncolumn(this,\'' + addEdit + '\',\'insert\');">+</span></div>',
						baseTop = '<div class="col-xs-12 col-sm-'+ lencol + '" data-' + addEdit + 'column="length"><div class="form-group inlineflex w100p">',
						baseMiddle = '<label class="supLlb" data-' + addEdit + 'column="title">' + ('en' == getLanguageURL() ? titlecolen : titlecol) + '</label>'
							+ '<input type="' + colnames[i].tipodecolumna + '" class="form-control c39c" data-' + addEdit + 'column="input" data-custcol="' + colnames[i].customcolumnid
							+ '" title="' + ('en' == getLanguageURL() ? descrcolen : descrcol) + '" placeholder="' + ('en' == getLanguageURL() ? descrcolen : descrcol)
							+ '" autocomplete="off"' + (reqcol == 0 ? '' : ' required') + '>',
						baseBottom = '</div></div>';
					if (titlecolen == '')titlecolen = titlecol;
					if (descrcolen == '')descrcolen = descrcol;

					// Datos almacenados
					if (dataValues != null) {
						for (v = 0; v < dataValues.length; v++)
							if (dataValues[v].customcolumnid == colnames[i].customcolumnid) {
								column = baseMiddle.replace('<input ','<input value="' + dataValues[v].assignedvalue + '" ');
								delete dataValues[0];
							}
					} else {
						column = baseMiddle.replace('value="(.*)"','value="$1"');
					}
					finishsetup += baseTop + (needded == 1 ? addonLeft + column + addonRigth : column) + baseBottom;
				}
				$('#' + addEdit.toLowerCase() + 'Trialtypecolumns').append(finishsetup);
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_trialtype') + '. ' + e);
			$('[data-editInputforcol]').show();
		}
	});
};

function addoncolumn(e, addEdit, action) {
	var finishsetup = '', setColumn = $(e).parent().parent().parent();
	var addonLeft = '<div class="' + $(setColumn).attr('class') + '">', newcolumn = $(setColumn).html();
	newcolumn = newcolumn.replace(' value=".*" ', '');
	finishsetup += addonLeft + newcolumn + '</div></div>';
	finishsetup=finishsetup.replace(/data-(edit)?ccvalueid="\d*"/,'');
	if (action == 'append') {
		$(finishsetup).appendTo('#' + addEdit.toLowerCase() + 'Trialtypecolumns');
	} else if (action == 'insert') {
		$(finishsetup).appendTo('#' + addEdit.toLowerCase() + 'Trialtypecolumns');
	} else if (action == 'remove') {
		var gr = $('.input-group-addon');
		if (gr.length > 2) {
			swal({
				title : i18n('msg_are_you_sure'),
				text : i18n('msg_will_not_recover_record'),
				type : "warning",
				showCancelButton : true,
				confirmButtonClass : 'btn-warning',
				confirmButtonText : i18n('btn_yes_delete_it'),
				closeOnConfirm : false,
				closeOnCancel : false
			}).then((isConfirm) => {
				if (isConfirm) {
					$(setColumn).remove();
					swal.close();
				} else {
					swal(i18n('msg_cancelled'), i18n('msg_record_safe'), "warning");
				}
			});
		}
	}
};

function multifilters(mffilter,e){
	$('.mf-container').not('#'+mffilter).hide();
	$('#'+mffilter).slideToggle('fast');
	$('#current-mf').val(e);
	sessionStorage.setItem('key', e)
	$('.custombox-modal-open').animate({
		scrollTop: $('.multifilters').offset().top - $('.custombox-modal-open').offset().top
			+ $('.custombox-modal-open').scrollTop() - 180}, 250, function(){
			var id=mffilter.replace(/^mf-/,'');
			$('#'+id).show();
			$('#input'+id).focus();
		}
	);
};

/**	@param e	Evento del objeto o keyCode.
	@param id	Id único, objeto o nombre de Grupo de elementos a procesar.
	@param rb	Grupo de checkbox/radio a tomar valores.	*/
function applycourtfilter(e,id,rb){
	var k=e.key||e.keyCode||e.which||e,txt='';
	if(k==='Enter'||k===13){
		if(rb!=''&&rb!=null){
			var num=$('#'+id).val();
			txt=$('input[name='+id+'opt]:checked').attr('title');
			if(txt==null)return;
			$('label[for='+id+']').text(i18n('msg_numberof')+' '+txt);
			if(num=='')return;
			txt+=' '+num;
		}else{
			if(typeof e==='string'){
				txt=id.target.title;
				id=$(id.target).parent()[0].id;
			}else if(e.target.tagName=='INPUT'){
				txt=$('#'+id+' li.selected').html();
			}
			$('#mf-'+id).slideUp();
		}
		if(txt=='')return;
		$('#mf-clear-'+id).show();
		$('#span-'+id).html(txt);
	}
};

function clearcourtfilter(id,t){
	$('#span-'+id).html(i18n('msg_'+(id.replace(/^edit/,''))));
	$(t).hide();
	$('#'+id).empty();
	$('#'+id).val('');
	$('#'+id).prev().prev().val('');
	$('#mf-'+id).slideUp();
}

$(function(){
	$(document).on('click', '.ddListImg li', function(e){
		applycourtfilter('Enter',e);
	});
});

$('#addmoreasneedded, #editmoreasneedded').on('click', function() {
	var addEdit = this.id.indexOf('add') >= 0 ? 'add' : 'edit';
	var addonLeft = '<div class="input-group" data-group="addon" >' + '<span class="input-group-addon asLink" onclick="addoncolumn(this,\'' + addEdit + '\',\'remove\');">-</span>',
		addonRigth = '<span class="input-group-addon asLink" data-group="addon" onclick="addoncolumn(this,\'' + addEdit + '\',\'insert\');">+</span></div>',
		baseTop = '<div class="col-xs-12" data-' + addEdit + 'column="length"><div class="form-group inlineflex w100p">',
		baseMiddle = '<label class="supLlb" data-' + addEdit + 'column="title">' + i18n('msg_title') + '</label>' + '<input class="form-control c39c" data-' + addEdit
			+ 'column="input" placeholder="Descripción" autocomplete="off" type="text">',
		baseBottom = '</div></div>', allinputs = $('[data-' + addEdit + 'column="input"]'), finishsetup = '';
	for (i = 0; i < allinputs.length; i++) {
		var val = allinputs[i].value;
		var column = '';
		if (column.indexOf('value=') > 0)
			column = baseMiddle.replace('value="(.*)"','value="$1"');
		else if (val == '' || val == null)
			column = baseMiddle;
		else
			column = baseMiddle.replace('<input ', '<input value="' + val + '" ');
		finishsetup += baseTop + ($(this).prop("checked") ? addonLeft + column + addonRigth : column) + baseBottom;
	}
	$('#addgroupsample').html('').html(finishsetup);
	var ln = $('#' + addEdit + 'lencolumn').val();
	if (ln < 1)
		ln = 1;
	else if (ln > 12)
		ln = 12;
	$('[data-' + addEdit + 'column="length"]').removeClass();
	$('[data-' + addEdit + 'column="length"]').addClass('col-xs-12 col-sm-' + ln);
	$('[data-' + addEdit + 'column="title"]').html($('#' + addEdit + 'titlecolumn').val());
});

/**
@param Id				Id del tag donde se cargará la lista.
@param matteridElem		Nombre del tag de materia para obtener el valor.
						(este nombre necesitará de "#" y/o "li.selected").
@param selType			Tipo de listado a cargar: "select", "ul", "ol".
@param addEdit			Módulo de donde fue llamado "add" o "edit".	*/
function getAccionByMatterId(id, matteridElem, selType, addEdit) {
	var elemOp = 'option', matterid = $(matteridElem).val();
	if(selType=='ul' || selType=='ol')
		elemOp = 'li';
	if(!(/^[1-9][0-9]*$/.test(matterid)))
		matterid=0;
//	$("#actionText").focus(function(){
	if(matterid==''||matterid=='0'){
		$(matteridElem.replace('li.selected','')).parent().addClass('has-error');
	 	$('#ErrorSelectMatter').show();
		$('#putErrorSelectMatter').html(i18n('err_enter_matter'));	
		return;
	}
//		}); 
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getAccionByMatterId",
		data : 'matterid=' + matterid,
		async : false,
		success : function(data){
			var info = data[0]||[];
			if(info.length > 0){
				if (selType == 'select' || selType == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].accionid + '" title="'+ info[i].descripcion
						+ '" onclick="getWayByTTId(value,\''+ addEdit + 'actionway\');">'+ info[i].descripcion + '</'+ elemOp + '>');
			}else{
				$('#' + id).append('<' + elemOp + ' value="0" selected disabled>'+ i18n('msg_no_data_matter') + '</'+ elemOp + '>');
			}
			$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\'' + id + '\',\'accion\');"><b>'
				+ i18n('msg_addnew') + '</b></' + elemOp + '>');
		},error : function(e){
			console.log(i18n('err_unable_get_trialtype') + '. ' + e);
		}
	});
};

/**	@param actionid		Valor de 'accionid'.
	@param wayidElem	Nombre del elemento a colocar el texto de la vía.	*/
function getWayByTTId(actionid, wayidElem) {
	$('#' + wayidElem).html('');
	if (actionid == '' || actionid == null)
		return;
	$.ajax({
		type : "POST",
		url : ctx + "/getWayByTTId",
		data : 'actionid=' + actionid,
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info != null && info.length > 0){
				if($('#edit-modal').css('display')=='block')
					wayidElem='editactionway';
				$('#' + wayidElem).html(info[0].via);
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_way') + '. ' + e);
		}
	});
};

/**	@param Id		Id del tag donde se cargará la lista.
	@param addEdit	Indica el modal de donde será aplicado: "add" o "edit".
	@param selType	Tipo de listado a cargar: "select", "ul", "ol"	*/
function getWayList(id, addEdit, selType) {
	var elemOp = 'option';
	if (selType == 'ul' || selType == 'ol')
		elemOp = 'li';
	$('#' + id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getWayList",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (selType == 'select' || selType == '')
					$('#' + id).append('<' + elemOp+ ' value="0" selected disabled>'+ i18n('msg_select') + '</'+ elemOp + '>');
				for (i = 0; i < info.length; i++)
					$('#' + id).append('<' + elemOp + ' value="'+ info[i].viaid+ '" title="' + info[i].via
						+ '">' + info[i].via + '</'+ elemOp + '>');
			} else {
				$('#' + id).append('<'+ elemOp+ ' value="0" selected disabled>'+ i18n('msg_no_data')
					+ '</'+ elemOp+ '>'+ '<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Way\');"><b>'
					+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
			}
			$('#' + id).append('<'+ elemOp+ ' value="" onclick="openFilterSelModal(\''+ id + '\',\'Way\');"><b>'
				+ i18n('msg_addnew') + '</b></'+ elemOp + '>');
		},error : function(e) {
			console.log(i18n('err_unable_get_way') + '. ' + e);
		}
	});
};

$('#trialClient').on('click keyup input paste change delete', function(e) {
	filtext(e.target.value, '#trialClientList');
	$('.containTL').show();
});

$('#editTrialClient').on('click keyup input paste change delete', function(e) {
	filtext(e.target.value, '#editTrialClient');
	$('html, body').animate({scrollTop : 0}, '300');
	var id = e.target.id, input, filter, table, tr, i;
	input = e.target;
	filter = input.value.toUpperCase();
	table = document.getElementById(id + 'List');
	tr = $('#' + id + 'List .rowopt');
	for (i = 0; i < tr.length; i++)
		if (tr[i].innerText.toUpperCase().indexOf(filter) >= 0)
			tr[i].style.display = "";
		else
			tr[i].style.display = "none";
	$('.containTL').show();
});

Dropzone.options.uploadXdiv={
	init: function() {
		this.on('addedfile', function(file){
			var preview = document.getElementsByClassName('dz-preview'),
				imageName = document.createElement('span');
			preview = preview[preview.length - 1];
			imageName.innerHTML = file.name;
			preview.insertBefore(imageName, preview.firstChild);
		});
	}
};

Dropzone.options.uploadXdivEdit={
	init: function() {
		this.on('addedfile', function(file){
			var preview = document.getElementsByClassName('dz-preview'),
				imageName = document.createElement('span');
			preview = preview[preview.length - 1];
			imageName.innerHTML = file.name;
			preview.insertBefore(imageName, preview.firstChild);
		});
	}
};

$('#actionText,#editActionText').blur(function() {
	if(this.id=='actionText')
		getWayByTTId($('#accion li.selected').val(), 'actionway');
	else if(this.id=='editActionText')
		getWayByTTId($('#editaccion li.selected').val(), 'editactionway');
});

/*
$('#trial-modal, #edit-modal').on('hidden.bs.modal',function(e) {
	$(this).find("input,textarea").val('').end()
		.find("input[type=checkbox], input[type=radio]").prop("checked","").end();
});*/

$(window).load(function() {
	var clid = getVarURL('clid');
	if (clid != '')
		history.replaceState({}, document.title, location.pathname + '?language=' + getLanguageURL());
});