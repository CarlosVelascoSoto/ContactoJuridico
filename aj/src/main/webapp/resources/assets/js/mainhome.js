;function getMaterias(id, elemtype, forfilter) {
	var elemOp = 'option';
	if (elemtype=='ul' || elemtype=='ol' || elemtype=='ddul')
		elemOp = 'li';
	$('#'+id).empty();
	forfilter=forfilter||'';
	$.ajax({
		type : "POST",
		url : ctx + "/getMaterias",
		async : false,
		success : function(data) {
			var info = data[0];
			if (info.length > 0) {
				if(elemtype=='ddul'){
					for (i = 0; i < info.length; i++)
						$('#'+id).append('<'+elemOp+'><div><label for="ddulmatt' + info[i].materiaid + '" class="ddlilabel w100p">'
							+ info[i].materia + '</label><input type="radio" id="ddulmatt' + info[i].materiaid
							+'" name="' + forfilter + 'matt" value="'+ info[i].materiaid
							+'" class="ddliinput"><label for="ddulmatt' + info[i].materiaid + '" class="ddlilabel"></label></div></'+elemOp+'>');
					$('#'+id).append('<'+elemOp+'><div><label for="ddulmatt-1" class="ddlilabel w100p">' + i18n('msg_others')
						+'</label><input type="radio" id="ddulmatt-1" name="matt" value="-1" class="ddliinput"><label for="ddulmatt-1" class="ddlilabel"></label></div></'+elemOp+'>');
					return;
				}
				if (elemtype=='select' || elemtype=='')
					$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
				for (i = 0; i < info.length; i++)
					$('#'+id).append('<'+elemOp+' value="'+ info[i].materiaid + '" title="'+ info[i].materia + '">'
						+ info[i].materia + '</'+elemOp+'>');
			}
			$('#'+id).append('<'+elemOp+'></'+elemOp+'>');
		},error : function(e) {
			console.log(i18n('err_unable_get_matter') + '. ' + e);
		}
	});
};

function getCiudades(id, elemtype, filterState, forfilter) {
	var elemOp = 'option', url = 'getCiudades', state = '0';
	if (elemtype=='ul' || elemtype=='ol' || elemtype=='ddul')
		elemOp = 'li';
	if (isNaN(filterState - 0)) {
		state = $('#' + filterState + ' li.selected').val();
		state = state || 0;
		url = 'getCitiesByState';
	}
	$('#'+id).empty();
	forfilter=forfilter||'';
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
				if(elemtype=='ddul'){
					for (i = 0; i < info.length; i++)
						$('#'+id).append('<'+elemOp+'><div><label for="ddulcity'
							+ info[i].ciudadid + '" class="ddlilabel w100p">' + info[i].ciudad
							+'</label><input type="radio" id="ddulcity' + info[i].ciudadid
							+'" name="' + forfilter + 'city" value="'+ info[i].ciudadid
							+'" class="ddliinput"><label for="ddulcity' + info[i].ciudadid
							+'" class="ddlilabel"></label></div></'+elemOp+'>');
					return;
				}
				if (elemtype=='select' || elemtype=='')
					$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
				for (i = 0; i < info.length; i++)
					$('#'+id).append('<'+elemOp+' value="'+ info[i].ciudadid + '" title="'+ info[i].ciudad + '">'
						+ info[i].ciudad + '</'+elemOp+'>');
				$('#'+id).append('<'+elemOp+'></'+elemOp+'>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_city') + '. ' + e);
		}
	});
};

function getCourts(id, elemtype, forfilter) {
	var elemOp = 'option',
		matt = $('input[name=ddl-cl-matt]:checked').val(),
		city = $('input[name=ddl-cl-city]:checked').val();
	var param = 'matterid='+((matt==null)?'':matt.replace(/^.{8}(.+)$/,'$1'))
			+'&cityid='+((city==null)?'0':city.replace(/^.{8}(.+)$/,'$1'));
	if (elemtype=='ul' || elemtype=='ol' || elemtype=='ddul')
		elemOp = 'li';
	$('#'+id).empty();
	forfilter=forfilter||'';
	$.ajax({
		type : "POST",
		url : ctx + "/getCourtsByCityMatter",
		data : param,
		async : false,
		success : function(data) {
			var info = data[0];
			if (info.length > 0) {
				if(elemtype=='ddul'){
					for (i = 0; i < info.length; i++)
						$('#'+id).append('<'+elemOp+'><div><label for="ddulcour'+info[i].juzgadoid
							+'" class="ddlilabel w100p">'+info[i].juzgado+'</label><input type="radio" id="ddulcour'
							+info[i].juzgadoid + '" name="'+forfilter+'cour" value="'+info[i].juzgadoid
							+'" class="ddliinput"><label for="ddulcour'+info[i].juzgadoid
							+'" class="ddlilabel"></label></div></'+elemOp+'>');
					if($('#forcourt').val()!='')
						$('#'+$('#forcourt').val()).prop("checked",true);
					return;
				}
				if (elemtype=='select' || elemtype=='')
					$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
				for (i = 0; i < info.length; i++)
					$('#'+id).append('<'+elemOp+' value="'+ info[i].juzgadoid + '" title="'+ info[i].juzgado + '">'
						+ info[i].juzgado + '</'+elemOp+'>');
			}
			$('#'+id).append('<'+elemOp+'></'+elemOp+'>');
		},error : function(e) {
			console.log(i18n('err_unable_get_court') + '. ' + e);
		}
	});
};

/** Establece la fecha a los campos de bootstrap-datepicker
@param e		Id input tag fecha
@param efix		Id input tag Fix fecha.
@return utdDate Fecha en formato UTC o milisegundos.	*/
function setBootstrapUtcDate(e,efix,utdDate){
	if(utdDate==null||utdDate=='')return;
	var d=new Date(utdDate);
	var newD=d.getFullYear()+"-"+twoDigits(d.getMonth()+1)+"-"+twoDigits(d.getDate());
	$('#'+efix).val(setFormatPatternDate(d,getFormatPatternDate("")));
	$('#'+e).val(newD);
};

function replaceTextInHtmlBlock($element, replaceText, replaceWith){
	var $children = $element.children().detach();
	$element.html($element.text().replace(replaceText, replaceWith));
	$children.each(function(index, me){
		replaceTextInHtmlBlock($(me), replaceText, replaceWith);
	});
	$element.append($children);
};

/**
 @param Id				Id del tag donde se cargará la lista.
 @param matteridElem	Nombre del tag de materia para obtener el valor. (este nombre
						necesitará de "#" y/o "li.selected").
 @param selType			Tipo de listado a cargar: "select", "ul", "ol".
 @param addEdit			Módulo de donde fue llamado "add" o "edit".		*/
function getAccionByMatterId(id, matteridElem, selType, addEdit) {
	var elemOp = 'option', matterid = $(matteridElem).val();
	if (selType=='ul' || selType=='ol')
		elemOp = 'li';
	if(!(/^[1-9][0-9]*$/.test(matterid)))
		matterid=0;
	$('#'+id).empty();
	$.ajax({
		type : "POST",
		url : ctx + "/getAccionByMatterId",
		data : 'matterid=' + matterid,
		async : false,
		success : function(data) {
			var info = data[0];
			if (info.length > 0) {
				if (selType=='select' || selType=='')
					$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
				for (i = 0; i < info.length; i++)
					$('#'+id).append('<'+elemOp+' value="'+ info[i].accionid + '" title="'+ info[i].descripcion
						+'">'+ info[i].descripcion + '</'+elemOp+'>');
			} else {
				$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+ i18n('msg_no_data_matter') + '</'+elemOp+'>');
			}
			$('#'+id).append('<'+ elemOp+ '></'+elemOp+'>');
		},error : function(e) {
			console.log(i18n('err_unable_get_trialtype') + '. ' + e);
		}
	});
};

function getNumberDoc(id, elemtype, forfilter){
	$('#ddl-ft-NumDoc').empty();
	var clie=$('input[name=ddl-fd-clie]:checked').val()||0,
		ttyp=$('input[name=ddl-fd-typetrial]:checked').val()||0,
		ndoc=$('input[name=ddl-fd-feddocnum]:checked').val()||0;
	var elemOp = 'option', param='clientid='+clie + '&doctype='+ttyp + '&docid='+ndoc;
	if (elemtype=='ul' || elemtype=='ol')
		elemOp = 'li';
	$.ajax({
		type : "POST",
		url : ctx + "/getFedDocsByFilter",
		async : false,
		data : param,
		success : function(data) {
			var info=data[0]; 
			if (info != null){
				var tablelist='',ttype=$('input[name=ddl-fd-typetrial]:checked').val()||0,
					prot=info.amparos,rsc=info.recursos,clients=info.clients,ccli=info.ccli;
				if(prot!=null&&ttype!=3)	// Amp
					for(a=0; a<prot.length; a++)
						$('#'+id).append('<'+elemOp+'><div><label for="ddulfeddoc'
							+prot[a].amparoid+ '" class="ddlilabel w100p"' + ' title="'
							+i18n(prot[a].amparotipo==1?'msg_direct_protection':'msg_indirect_protection')+'">'
							+ prot[a].amparo+'</label><input type="radio" id="ddulfeddoc' + prot[a].amparoid
							+'" name="'+forfilter+'feddocnum" value="'+prot[a].amparoid
							+'" class="ddliinput" style="display:none" title="'
							+i18n(prot[a].amparotipo==1?'msg_direct_protection':'msg_indirect_protection')
							+'"></div></'+elemOp+'>');
				if(rsc!=null&&(ttype==0||ttype==3))	// Rsc
					for(r=0; r<rsc.length; r++)
						$('#'+id).append('<'+elemOp+'><div><label for="ddulfeddoc'
							+rsc[r].recursoid+ '" class="ddlilabel w100p"' + ' title="'+i18n('msg_resource')+'">'
							+rsc[r].recurso+'</label><input type="radio" id="ddulfeddoc'+rsc[r].recursoid
							+'" name="'+forfilter+'feddocnum" value="'+rsc[r].recursoid
							+'" class="ddliinput" style="display:none" title="'
							+i18n('msg_resource')+'"></div></'+elemOp+'>');
				$('#'+id).append('<'+elemOp+'></'+elemOp+'>');
			}
		},error : function(e) {
			$('#federalDocList tbody').empty();
			console.log(i18n('err_unable_get_trial') + '. ' + e);
		}
	});
};

function commonLawFilter(){
	$('#commonLawList').hide();
	$('#commonLawList tbody').empty();
	var prcd=$('input[name=ddl-cl-prcd]:checked').val()||0, cour=$('input[name=ddl-cl-cour]:checked').val()||0,
		matt=$('input[name=ddl-cl-matt]:checked').val()||'',city=$('input[name=ddl-cl-city]:checked').val()||0;
	var param='matterid='+matt + '&courtid='+cour + '&trialid='+prcd + '&cityid='+city;
	$('#forcourt').val((cour>0?'ddulcour'+cour:''));
	$.ajax({
		type : "POST",
		url : ctx + "/getDocsByFilterCL",
		async : false,
		data : param,
		success : function(data) {
			var info = data[0];
			if (info.juicios != null) {
				$('#commonLawList').show();
				var tablelist='',trials=info.juicios
					courts=info.juzgados,matters=info.materias,cities=info.cities;
				if(trials==null)return;
				for(t=0; t<trials.length; t++){
					tablelist += '<tr><td><a href="trialsdashboard?language=es&rid='+trials[t].juicioid
						+'" rel="noopener noreferrer">' + trials[t].juicio + '</a></td>';
					tablelist+='<td>'+(courts.length>0?courts.find(object => object.juzgadoid===trials[t].juzgadoid).juzgado:'')+'</td>';
					if(matters.length>0){
						let colorpattern = ['24a878','00addc','b3a400','4f5479','ffc06c','a366ff','ff99b3','6666ff','bf8040','7ba428'];
						tablelist += '<td><span class="mattlabel tac flex" style="background-color:#'
							+ colorpattern[(trials[t].materiaid+'').slice(-1)] +'">'
							+ matters.find(object => object.materiaid===trials[t].materiaid).materia + '</span></td>';
					}else{
						tablelist+='<td></td>';
					}
					tablelist+='<td>'+(cities.length>0?cities.find(object => object.ciudadid===trials[t].ciudadid).ciudad:'')+'</td>';
				}
			}
			$('#commonLawList tbody').append(tablelist);
		},error : function(e) {
			$('#commonLawList tbody').empty();
			console.log(i18n('err_unable_get_trial') + '. ' + e);
		}
	});
};

function fedTrialFilter(){
	$('#federalDocList').hide();
	$('#federalDocList tbody').empty();
	var clie=$('input[name=ddl-fd-clie]:checked').val()||0,
		ttyp=$('input[name=ddl-fd-typetrial]:checked').val()||0,
		ndoc=$('input[name=ddl-fd-feddocnum]:checked').val()||0;
	if(clie==0&&ttyp==0&&ndoc==0)return;
	var param='clientid='+clie + '&doctype='+ttyp + '&docid='+ndoc;
	$.ajax({
		type : "POST",
		url : ctx + "/getFedDocsByFilter",
		async : false,
		data : param,
		success : function(data){
			var info=data[0]; 
			if(info != null){
				var tablelist='',ttype=$('input[name=ddl-fd-typetrial]:checked').val()||0,
					prot=info.amparos,rsc=info.recursos,clients=info.clients,ccli=info.ccli;
				if(prot!=null&&ttype!=3)	// Amp
					for(a=0; a<prot.length; a++){
						var relpage='protection',msgtype='direct';
						tablelist+='<tr>';
						if(prot[a].amparotipo==2){
							relpage='indprotection';
							msgtype='indirect';
						}
						tablelist+='<td>'+i18n('msg_'+msgtype+'_protection')+'</td>' + '<td><a href="'
							+ relpage+'dashboard?language=es&rid='+ prot[a].amparoid
							+'" rel="noopener noreferrer">' + prot[a].amparo + '</a></td>';
						for(cc=0; cc<ccli.length; cc++)
							if(prot[a].companyclientid==ccli[cc][0]){
								let tcid=clients.find(object => object.clientid===ccli[cc][1]).clientid;
								tablelist += '<td><a onclick="sessionStorage.setItem(\'clid\', '+tcid+');" href="'
									+ctx+'/' + relpage + 's?language='+ getLanguageURL()+'&clid='+tcid+'">'
									+(clients.find(object => object.clientid===ccli[cc][1]).client)+ '</a></td>';
								tablelist+='</tr>';
								break;
							}
					}
				if(rsc!=null&&(ttype==0||ttype==3))	// Rsc
					for(r=0; r<rsc.length; r++){
						tablelist+='<tr><td>'+i18n('msg_resource')+'</td>'
							+'<td><a href="resourcedashboard?language=es&rid='+rsc[r].recursoid
							+'" rel="noopener noreferrer">'+rsc[r].recurso +'</a></td>';
						tablelist += '<td>';
						
						if(prot.length==0)continue;
						let tccid = prot.find(object => object.amparoid===rsc[r].tipoorigenid).companyclientid;
						for(cc=0; cc<ccli.length; cc++)
							if(tccid==ccli[cc][0]){
								let tcid=clients.find(object => object.clientid===ccli[cc][1]).clientid;
								tablelist+='<a onclick="sessionStorage.setItem(\'clid\', '+tcid+');" href="'
									+ctx+'/resources?language='+getLanguageURL()+'&clid='+tcid+'">'
									+clients.find(object => object.clientid===ccli[cc][1]).client+'</a>';
								break;
							}
						tablelist += '</td></tr>';
					}
				$('#federalDocList').show();
			}
			$('#federalDocList tbody').append(tablelist);
		},error : function(e){
			$('#federalDocList tbody').empty();
			console.log(i18n('err_unable_get_trial') + '. ' + e);
		}
	});
};

function filterli(e,listid){
	var k=e.key||e.keyCode||e.which||e,
		retVoid=['16','Shift','17','Control','27','Escape','33','PageUp','34','PageDown','35','End','36','Home','37','ArrowLeft','38','ArrowUp','39','ArrowRight','40','ArrowDown'];
	if(retVoid.indexOf(k+'')>=0)return;
	var val=e.value.toUpperCase(),lis=$('#'+listid+' li');
    for (i = 0; i < lis.length; i++) {
    	var tx = $(lis[i]).text().toUpperCase(), d=(tx.indexOf(val)>=0 || val=='')?'block':'none';
        $(lis[i]).css('display',d);
    }
};

function toggleArrow(e,obj){
	var icon='keyboard_arrow_up',c='#797979',boxmenu=$(obj).parent();
	var iconobj=$(boxmenu).parent().find('.arr_rg')[0];
	$('.ddlimenu').not(boxmenu).each(function(){$(this).slideUp(250);});
	$(obj).find('li').show();
	if(e.tagName=='INPUT'&& !$(e).hasClass('ddliinput')){
		iconobj=$(e).prev('.arr_rg')[0];
		$(boxmenu).slideDown(250);
	}else if($(e).html()=='close'){
		var inp=$(boxmenu).prev().find('.ddlisearch')[0];
		$(inp).val('');
		$(boxmenu).find('input[type=radio]').prop('checked',false);
		if($($(e)[0]).data('close')=='ddl-feddoc')
			fedTrialFilter();
		else
			commonLawFilter();
		if(($(inp).attr('onfocus')+'').indexOf('getCourts')>=0)
			$('#forcourt').val('');
	}else{
		var a=$(obj).find('.ddliinput:checked');
		if($(iconobj).html()==icon){
			icon=a.length>0?(c='#C63964','close'):'keyboard_arrow_down';
			$(boxmenu).slideToggle(250);
		}else if($(e).html()=='keyboard_arrow_down'){
			$(e).next().focus();
			$(boxmenu).slideDown(250);
		}
	}
	$(iconobj).html(icon).css('color',c);
};

function getProceedings(id, elemtype,forfilter){
	forfilter=forfilter||'';
	var elemOp='option';
	var matt=$('input[name='+forfilter+'matt]:checked').val()||0,
		city=$('input[name='+forfilter+'city]:checked').val()||0, 
		cour=$('input[name='+forfilter+'cour]:checked').val()||0;
	var param=
		'trialid=-1'
		+'&courtid=' +(cour==null?0 :cour) + '&matterid='+(matt==null?'':matt)
		+'&cityid=' + (city==null?0 :city);
	if(elemtype=='ul'||elemtype=='ol'||elemtype=='ddul')elemOp='li';
	$('#'+id).empty();
	$.ajax({
		type : 'POST',
		url : ctx + '/getDocsByFilterCL',
		async : false,
		data : param,
		success : function(data) {
			var info = data[0];
			if (info.juicios != null) {
				var tablelist='',trials=info.juicios,clients=info.clients,ccli=info.ccli;
				for (t = 0; t < trials.length; t++)
					if(elemtype=='ddul'){
						$('#'+id).append('<'+elemOp+'><div><label for="ddulprcd'
							+trials[t].juicioid+ '" class="ddlilabel w100p" title="'+trials[t].juicio+'">'
							+trials[t].juicio + '</label><input type="radio" id="ddulprcd'
							+trials[t].juicioid+ '" name="'+forfilter+'prcd" value="'+trials[t].juicioid
							+'" class="ddliinput"></div></'+elemOp+'>');
					}else if(elemtype=='select' || elemtype==''){
						$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
					}
			}
			$('#'+id).append('<'+elemOp+'></'+elemOp+'>');
		},error : function(e) {
			$('#commonLawList tbody').empty();
			console.log(i18n('err_unable_get_trial') + '. ' + e);
		}
	});
};

document.addEventListener('click',function(e){
	var onObj=($(e.target).hasClass("smallprofile")
		||$(e.target).hasClass("smallprofile2"))?'span':
		(!onObj&&$(e.target).hasClass("smallprofile3"))?'img':'';
	var thisIdTable=null,selRb=null;
	if(e.target.nodeName=='TD'||onObj!=''){
		if(onObj=='img'){
			thisIdTable=e.target.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.id;
			selRb=e.target.parentElement.parentElement.parentElement.parentElement.firstElementChild.firstElementChild;
		}else if(onObj=='span'){
			thisIdTable=e.target.parentElement.parentElement.parentElement.parentElement.parentElement.id;
			selRb=e.target.parentElement.parentElement.parentElement.firstElementChild.firstElementChild;
		}else{
			if((e.target.parentElement.parentElement.parentElement.id).indexOf('clientList')>=0)
				return;
			thisIdTable=e.target.parentElement.parentElement.parentElement.id;
			selRb=e.target.parentElement.firstElementChild.firstElementChild;
		}
		if(selRb!=null)
			var id=selRb.getAttribute('id').replace(/^clie/,'');
	}
},false);

$('[data-home="calendar"]').click(function (){
	location.href = ctx+'/ecalendar?language='+ getLanguageURL();
});

/* Docs Filters (ini) */
$('#closetrailbox').on('click', function(e){
	$('.trailbox').fadeOut();
	$('.ddlimenu input[type=radio]').prop("checked",false);
	$('.ddlisearch').val('');
	$('#commonLawList').hide();
});

$('#closefeddocbox').on('click', function(e){
	$('.trailbox').fadeOut();
	$('.ddlimenu input[type=radio]').prop("checked",false);
	$('.ddlisearch').val('');
	$('#federalDocList').hide();
});

// Docs Filters (ini)
$(document).on('change','#home-ddul-cl .ddliinput, #home-ddul-fd .ddliinput',function(){
	var id='#'+this.id;
	var obj=$(id).closest('ul'),modId=$(id).closest('form')[0].id;
	var pp=$(obj).parent().prev();
	var icon=$(pp).find('.arr_rg')[0],inp=$(pp).find('.ddlisearch')[0];
	toggleArrow(obj,obj[0].id);
	$(icon).html('close').css('color','#C63964');
	$(inp).val(camelize($(id).prev().text()));
	if(modId=='home-ddul-cl')
		commonLawFilter();
	else if(modId=='home-ddul-fd')
		fedTrialFilter();
});
/*
$('#dllimenu-protections').on('click', function(){
	var prottype=$('input[name=ddl-fd-typetrial]:checked').val();
	if(prottype=="1")
		$('#dllititletypetrial').html(i18n('msg_protection_number'));
	else if(prottype=="2")
		$('#dllititletypetrial').html(i18n('msg_indirect_protection_num'));
	else if(prottype=="3")
		$('#dllititletypetrial').html(i18n('msg_resource_number'));
	else
		$('#dllititletypetrial').html(i18n('msg_document_number'));
	$('input[name=ddl-fd-typetrial]:checked').parent().text();
});*/
/* Federal filters (fin) */

function getDashboardNotifications(id){
	$('#'+id+'tbody tr').remove();
	$('#dashtotnotif').html('0');
	$.ajax({
		type:"POST",
		url:ctx+'/getNotifyShort',
		async:false,
		success:function(data){
			if(data==null) return;
			var n=0, tablelist='', acttodo=['msg_new_record','msg_changes','msg_deleted','msg_added_document'];
			for(n=0;n<Object.keys(data).length;n++){
				var info=data[n];
				var d = new Date(info.date),area=info.modulename
				var year=d.getFullYear(), month=('0'+(d.getMonth()+1)).slice(-2), day =('0'+d.getDate()).slice(-2), 
					fmtdate=getFormatPatternDate().replace(/y+/g,'y').replace(/M+/g,'M').replace(/d+/,'d');
				fmtdate=fmtdate.replace('d',day).replace('M',month).replace('y',year),
				area=area.replace(/(s\b|\b)/ig,'').replace(/^Movimientos?\sde\s/ig,'')
				if(area=='apelacione')
					area='Apelaci\u00f3n';
				area=camelize(area);

				dbntfy.row.add([
					'<span style="color:#000">' +area+ '</span><br><a href="' +info.link
						+'?language=es&rid=' +info.idref+ '" rel="noopener noreferrer" title="'
						+ i18n('msg_goto_record')+" " +info.proceedings+ '">' +info.proceedings+
					'</a>'
				    ,(info.courtname==''?'- - - - -':info.courtname), (info.clientname==''?'- - - - -':info.clientname)
				    ,(info.description==''?'- - - - -':info.description) ,fmtdate
				    ,'<i class="material-icons asLink trn2ms tac" onclick="infonotify(' + info.notificationid
				    	+ ');" style="width:40px;margin:auto 0;padding-left:10px">info_outline</i>' ])
				    .draw(false);

			}
			$('#dashtotnotif').html('(' +n+ ')');
			$('#'+id).addClass("display");
			$('#'+id).DataTable();
		},error:function(e){
			swal(i18n('msg_warning'),i18n('err_notification_fail')+e,'error');
		}
	});
};

function infonotify(nid){
	nid=nid.replace(/\D/,'')
	$.ajax({
		type:"POST",
		url:ctx+'/getNotifyDetail',
		data:'id='+nid,
		async:false,
		success:function(info){
			var acttodo=['msg_new_record','msg_changes','msg_deleted','msg_added_document'],d = new Date(info.date);
			var hours=('0'+d.getHours()).slice(-2), min=('0'+d.getMinutes()).slice(-2),
				year=d.getFullYear(), month=('0'+(d.getMonth()+1)).slice(-2), day =('0'+d.getDate()).slice(-2), 
				fmtdate=getFormatPatternDate().replace(/y+/g,'y').replace(/M+/g,'M').replace(/d+/,'d');
			fmtdate=fmtdate.replace('d',day).replace('M',month).replace('y',year);
			var ampm=hours>=12?'pm':'am';
				hours=hours%12;
				hours=hours?hours:12;
			$('#notifyshown').val(info.notificationid);
			$('#ntfyarea').html(JSON.parse(info.area).modulename);
			$('#ntfydoc').html('<a href="' + JSON.parse(info.area).link + '?language=es&rid=' + info.idref
				+'" rel="noopener noreferrer" title="' + i18n('msg_goto_record') + " " +info.document + '">'
				+ info.document + '<i class="fa fa-external-link" style="margin-left:10px"></i></a>');
			$('#ntfyclient').html(info.clientname);
			$('#ntfyact').html(i18n(acttodo[(acttodo[info.actiontypeid]==null?1:info.actiontypeid)]));
			$('#ntfydate').html(fmtdate+' ('+hours+':'+min+' ' + ampm +')');
			$('#ntfyfromuser').html(info.user);

			var jsonact = JSON.parse(info.actionsdetails),actdet='',filemgn='';
			for(a=0;a<Object.keys(jsonact).length;a++)
				if(jsonact[a].field!=null){
					actdet+='<tr><td>' + jsonact[a].field + '</td><td>' + jsonact[a].newdata
						+'</td><td>' + jsonact[a].olddata + '</td></tr>';
				}else{
					filemgn+='<tr><td>' + 'archivo' + '</td><td>' + 'status'
						+'</td><td>' + jsonact[a].file + '</td></tr>';
				}
			$('#tablentfydetail tbody').html(actdet);

			$('#notify-detail-modal').modal('show');
		},error:function(e){
			swal(i18n('msg_warning'),i18n('err_notification_fail')+e,'error');
		}
	});
};

function notifyAsRead(id){
	id==id||$('#notifyshown').val();
	swal({
		title:i18n('msg_are_you_sure'),
		text:i18n('msg_notificatios_as_read'),
		type:"warning",
		showCancelButton:true,
		confirmButtonClass:'btn-warning',
		confirmButtonText:i18n('btn_yes_remove_it'),
		closeOnConfirm:false,
		closeOnCancel:false
	}).then((isConfirm) => {
		$.ajax({
			type:"POST",
			url:ctx+'/notifyAsRead',
			data:'id='+id,
			async:false,
			success:function(data){
				if(data)
					location.href = location.href.replace(/^(.*)\#.*$/, '$1');
				else
					swal(i18n('msg_warning'),i18n('err_notification_fail')+e,'error');
			},error:function(e){
				swal(i18n('msg_warning'),i18n('err_notification_fail')+e,'error');
			}
		});
	});
};

$(window).on('load', function(){
	if(window.innerWidth >= 768){
		$('body').toggleClass('fixed-left').toggleClass('fixed-left-void');
		$('#wrapper').toggleClass('enlarged');
		$('.topbar-left').toggleClass('enlarged-logo');
		$('.navbar-default').toggleClass('large-trial-nav');
		$('.logo span .icon-home-logo').toggleClass('dnone');
		$('footer.footer').addClass('large-trial-nav');
	}

	getMaterias('ddl-cl-matter','ddul', 'ddl-cl-');
	getCiudades('ddl-cl-city', 'ddul','','ddl-cl-');
	getCourts('ddl-cl-court', 'ddul', 'ddl-cl-');
	$('#commonLawList').hide();

	$('#ddl-fd-typetrial').append('<li><div><label for="ddl-fd-typetrial1" class="ddlilabel w100p">' + i18n('msg_direct_protection')
		+'</label><input type="radio" id="ddl-fd-typetrial1" name="ddl-fd-typetrial" value="1" class="ddliinput"><label for="ddl-fd-typetrial1" class="ddlilabel"></label></div></li>')
	.append('<li><div><label for="ddl-fd-typetrial2" class="ddlilabel w100p">' + i18n('msg_indirect_protection')
		+'</label><input type="radio" id="ddl-fd-typetrial2" name="ddl-fd-typetrial" value="2" class="ddliinput"><label for="ddl-fd-typetrial2" class="ddlilabel"></label></div></li>')
	.append('<li><div><label for="ddl-fd-typetrial3" class="ddlilabel w100p">' + i18n('msg_resource')
		+'</label><input type="radio" id="ddl-fd-typetrial3" name="ddl-fd-typetrial" value="3" class="ddliinput"><label for="ddl-fd-typetrial3" class="ddlilabel"></label></div></li>');
	$('#federalDocList').hide();
});