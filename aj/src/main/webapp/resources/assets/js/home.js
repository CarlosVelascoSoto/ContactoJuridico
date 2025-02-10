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


function getClients(id, elemtype, forfilter) {
	var elemOp = 'option';
	if (elemtype=='ul' || elemtype=='ol' || elemtype=='ddul')
		elemOp = 'li';
	$('#'+id).empty();
	forfilter=forfilter||'';
	$.ajax({
		type : 'POST',
		url : ctx + "/getClientList",
		async : false,
		success : function(data) {
			var info=data[0]||[];
			if(info.length<0||info==null)return;
			if(elemtype=='ddul'){
				for (i = 0; i < info.length; i++)
					$('#'+id).append('<'+elemOp+'><div><label for="ddulclie'
						+forfilter + info[i][0]
						+'" class="ddlilabel w100p"' + ' title="' + info[i][2] + ', ' + info[i][3]
						+', ' + info[i][4] + '">' + info[i][1]
						+'</label><input type="radio" id="ddulclie' + forfilter + info[i][0]
						+'" name="' + forfilter + 'clie" value="'+ info[i][0]
						+'" class="ddliinput" title="' + info[i][2] + ', ' + info[i][3]
						+', ' + info[i][4] + '"><label for="ddulclie'
						+forfilter + info[i][0] + '" class="ddlilabel"></label></div></'+elemOp+'>');
				return;
			}else if(elemtype=='select' || elemtype==''){
				$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
				for (i = 0; i < info.length; i++)
					$('#'+id).append('<'+elemOp+' value="'+ info[i][0] + '" title="'+ info[i][2] + ','
					+ info[i][3] +',' + info[i][4] + '">'
						+ info[i][1] + '</'+elemOp+'>');
			}
			$('#'+id).append('<'+elemOp+'></'+elemOp+'>');
		},error : function(e) {
			console.log(i18n('err_unable_get_client') + '. ' + e);
		}
	});
};

function getMaterias(id, elemtype, forfilter) {
	var elemOp = 'option';
	if (elemtype=='ul' || elemtype=='ol' || elemtype=='ddul')
		elemOp = 'li';
	$('#'+id).empty();
	forfilter=forfilter||'';
	$.ajax({
		type : 'POST',
		url : ctx + "/getMaterias",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if(elemtype=='ddul'){
					for (i = 0; i < info.length; i++)
						$('#'+id).append('<'+elemOp+'><div><label for="ddulmatt' + info[i].materiaid + '" class="ddlilabel w100p">'
							+info[i].materia + '</label><input type="radio" id="ddulmatt' + info[i].materiaid
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

function getEstados(id, elemtype) {
	var elemOp = 'option';
	if (elemtype=='ul' || elemtype=='ol')
		elemOp = 'li';
	$('#'+id).empty();
	$.ajax({
		type : 'POST',
		url : ctx + "/getStates",
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype=='select' || elemtype=='')
					$('#'+id).append('<'+elemOp+' value="0" selected disabled>' +i18n('msg_select')+'</'+elemOp+'>');
				for (i = 0; i < info.length; i++)
					$('#'+id).append('<'+elemOp+' value="' + info[i].estadoid + '" title="' + info[i].estado + '">'
						+ info[i].estado + '</'+elemOp+'>');
				$('#'+id).append('<'+elemOp+'></'+elemOp+'>');
			}
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
		type : 'POST',
		url : ctx + "/" + url,
		data : 'estadoid=' + state,
		async : false,
		success : function(data) {
			var info = null;
			if (isNaN(filterState - 0))
				info = data[0]||[];
			else
				info = data[0].cdList;
			if (info.length > 0) {
				if(elemtype=='ddul'){
					for (i = 0; i < info.length; i++)
						$('#'+id).append('<'+elemOp+'><div><label for="ddulcity'
							+info[i].ciudadid + '" class="ddlilabel w100p">' + info[i].ciudad
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

function fillHomeCities(ids,elemtypes,forfilters) {
	$(ids).empty();
	$.ajax({
		type : 'POST',
		url : ctx + '/getCiudades',
		async : false,
		success : function(data) {
			var info=null, elemOp='option';
			info = data[0].cdList;
			if (info.length > 0) {
				var id=ids.split(','), elemtype=elemtypes.split(','), forfilter=forfilters.split(',');
				for(t=0;t<id.length;t++){
					if (elemtype[t]=='ul' || elemtype[t]=='ol' || elemtype[t]=='ddul')
						elemOp = 'li';
					if(elemtype[t]=='ddul'){
						for (i = 0; i < info.length; i++)
							$(id[t]).append('<'+elemOp+'><div><label for="ddulcity'
								+info[i].ciudadid + '" class="ddlilabel w100p">' + info[i].ciudad
								+'</label><input type="radio" id="ddulcity' + info[i].ciudadid
								+'" name="' + forfilter[t] + 'city" value="'+ info[i].ciudadid
								+'" class="ddliinput"><label for="ddulcity' + info[i].ciudadid
								+'" class="ddlilabel"></label></div></'+elemOp+'>');
					}else{
						if (elemtype[t]=='select' || elemtype[t]=='')
							$(id[t]).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
						for (i = 0; i < info.length; i++)
							$(id[t]).append('<'+elemOp+' value="'+ info[i].ciudadid + '" title="'+ info[i].ciudad + '">'
								+ info[i].ciudad + '</'+elemOp+'>');
						$(id[t]).append('<'+elemOp+'></'+elemOp+'>');
					}
				}
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_city') + '. ' + e);
		}
	});
};

function getCourts(id, elemtype, forfilter) {
	var elemOp = 'option',
		matt = $('input[name=cl_matt]:checked').val(),
		city = $('input[name=cl_city]:checked').val();
	var param = 'matterid='+((matt==null)?'':matt.replace(/^.{8}(.+)$/,'$1'));
		param += '&cityid='+((city==null)?'':city.replace(/^.{8}(.+)$/,'$1'));
	if (elemtype=='ul' || elemtype=='ol' || elemtype=='ddul')
		elemOp = 'li';
	$('#'+id).empty();
	forfilter=forfilter||'';
	$.ajax({
		type : 'POST',
		url : ctx + "/getCourtsByCityMatter",
		data : param,
		async : false,
		success : function(data) {
			var info = data[0]||[];
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
					$('#'+id).append('<'+elemOp+' value="'+info[i].juzgadoid
					+'" title="'+info[i].juzgado+'">'+info[i].juzgado+'</'+elemOp+'>');
			}
			$('#'+id).append('<'+elemOp+'></'+elemOp+'>');
		},error : function(e) {
			console.log(i18n('err_unable_get_court') + '. ' + e);
		}
	});
};

function setBootstrapUtcDate(e,efix,utdDate){
	if(utdDate==null||utdDate=='')return;
	var d=new Date(utdDate);
	var newD=d.getFullYear()+"-"+twoDigits(d.getMonth()+1)+"-"+twoDigits(d.getDate());
	$('#'+efix).val(setFormatPatternDate(d,getFormatPatternDate("")));
	$('#'+e).val(newD);
};

function getDetailsClient(id){
	$.ajax({
		type:'POST',
		url:ctx+"/getClientsById",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0].info[0]||[],pec=data[0].pec||null;
			if(data[0].info.length==0){
				swal(i18n('msg_warning'),i18n('err_unable_get_client'),'error');
			}else{
				$('#pfrl-client').html(info.client);
				$('#pfrl-typeofperson').html(i18n(info.personafiscalid=='1'?'msg_individualperson':'msg_corporationperson'));
				$('#pfrl-address1').html(info.address1);
				if(pec==null){
					$('#pfrl-country').html(info.country);
					$('#pfrl-state').html(info.state);
					$('#pfrl-city').html(pec.city);
				}else{
					$('#pfrl-country').html(pec[0][0]);
					$('#pfrl-state').html(pec[0][1]);
					$('#pfrl-city').html(pec[0][2]);
				}
				$('#pfrl-cp').html(info.zipcode);
				$('#pfrl-email').html(info.email);
				$('#pfrl-cellphone').html(info.cellphone);
				$('#pfrl-phone').html(info.phone);
				$('#pfrl-clieRel_with').html(info.rel_with);
				$('#pfrl-clieRef_by').html(info.ref_by);
				$('#pfrl-status').html(i18n(info.status=='1'?'msg_active':'msg_inactive'));
				setBootstrapUtcDate('pfrl-birthdate','pfrl-birthdateFix',info.birthdate);
				$('#pfrl-comments').html(info.comments);
				/*$('#photo').attr('src',info.photo);
				$('#photoTmp').html(info.photo);*/
				$('#pfrl-contactperson').html(info.contactperson);
				var re=new RegExp(/^http/);
				var fullwebpage='#',w=info.webpage;
				if(w!=''||(typeof w!='undefined')){
					var w1=re.test(w);
					fullwebpage=(w1)?fullwebpage=w:'https://'+w;
				}
				$('#pfrl-webpage').attr('href',fullwebpage);
				$('#pfrl-webpage').html(w);

				var snw=data[0].snw;
				$('#pfrl-snTableListOnHome tbody').empty();
				for(s=0;s<snw.length;s++){
					var id=snw[s][0].snid,
						snid=snw[s][0].socialnetworkid,
						snName=snw[s][1],
						snAddress=snw[s][0].address;
					if(snid!=''&&snAddress!=''){
						var fullURL='https://www.'+snName.toLowerCase()+'.com/'+snAddress.replace(/^(([^:/?#]+):)?(\/\/([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?/g,'$5').replace(/^\//,'');
						var newsn='<tr><td>'+snName+'</td>'
							+'<td><a href="'+fullURL+'" rel="noopener noreferrer" target="_blank" style="cursor:pointer">'+snAddress+'</a></td></tr>';
					}
					$('#pfrl-snTableListOnHome tbody:last-child').append(newsn);
				}
			}
		},error:function(resp){
			swal(i18n('msg_warning'),i18n('err_unable_get_client'),'error');
		}
	});
};

/*function replaceTextInHtmlBlock($element, replaceText, replaceWith){
	var $children = $element.children().detach();
	$element.html($element.text().replace(replaceText, replaceWith));
	$children.each(function(index, me){
		replaceTextInHtmlBlock($(me), replaceText, replaceWith);
	});
	$element.append($children);
};*/

/**@param Id			Id del tag donde se cargará la lista.
 @param matteridElem	Nombre del tag de materia para obtener el valor.
 						(este nombre necesitará de "#" y/o "li.selected").
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
		type : 'POST',
		url : ctx + "/getAccionByMatterId",
		data : 'matterid=' + matterid,
		async : false,
		success : function(data) {
			var info = data[0]||[];
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

function getLawyerList(id, elemtype, forfilter,sid) {
	var elemOp = 'option';
	if (elemtype=='ul' || elemtype=='ol' || elemtype=='ddul')
		elemOp = 'li';
	sid=sid||0;
	$('#'+id).empty();
	forfilter=forfilter||'';
	$.ajax({
		type : 'POST',
		data : 'sid='+sid,
		url : ctx + "/getLawyerList",
		async : false,
		success : function(data) {
			var firm = data[0].companyList||[], users = data[0].lawyerList||[];
			if(Object.keys(users).length > 0){
				if(elemtype=='select' || elemtype=='')
					$('#'+id).append('<'+elemOp + ' value="0" selected disabled>' +i18n('msg_select')+'</'+elemOp+'>');
				for(u = 0; u < Object.keys(users).length; u++){
					var company='';
					for(c=0; c<Object.keys(firm).length; c++)
						if(users[u].companyid==firm[c].companyid){
							company=firm[c].company;
							break;
						}
					if(elemtype=='ddul')
						$('#'+id).append('<'+elemOp+'><div><label for="ddullwyr'+users[u].id
							+'" class="ddlilabel w100p" title="'+company+'">' + users[u].first_name+' '+users[u].last_name
							+'</label><input type="radio" id="ddullwyr'+users[u].id + '" name="'+forfilter+'lwyr" value="'
							+users[u].id+'" class="ddliinput"><label for="ddullwyr' + users[u].id
							+'" class="ddlilabel" title="'+company+'"></label></div></'+elemOp+'>');
					else
						$('#'+id).append('<'+elemOp + ' value="'+users[u].id + '" title="'+company+'">'
							+users[u].first_name+' '+users[u].last_name + '</'+elemOp+'>');
				}
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_lawyer') + e);
		}
	});
};

function getCircuits(id, stateid, elemtype) {
	var elemOp = 'option',stateid=$('#'+stateid+' li.selected').val()||0;
	if (elemtype=='ul' || elemtype=='ol')
		elemOp = 'li';
	$('#'+id).empty();
	$.ajax({
		type : 'POST',
		url : ctx + "/getCircuitsByState",
		data : 'stateid='+stateid,
		async : false,
		success : function(data) {
			var info = data[0]||[];
			if (info.length > 0) {
				if (elemtype=='select' || elemtype=='')
					$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
				for (i = 0; i < info.length; i++)
					$('#'+id).append('<'+elemOp+' value="'+ info[i].circuitoid + '" title="'+ info[i].circuito + '">'
						+ info[i].circuito + '</'+elemOp+'>');
				$('#'+id).append('<'+elemOp+'></'+elemOp+'>');
			}
		},error : function(e) {
			console.log(i18n('err_unable_get_circuit') + '. ' + e);
		}
	});
};

function getProtections(id, elemtype){
	var elemOp = 'option',param = 'trialid=-1',
		clie = $('input[name=cliefeddoc]:checked').val(),
		city = $('input[name=city]:checked').val();
	param += '&clientid='+((clie==null)?0:clie.replace(/^.{8}(.+)$/,'$1'));
	param += '&courtid=' +((cour==null)?0:cour.replace(/^.{8}(.+)$/,'$1'));
	param += '&matterid='+((matt==null)?'':matt.replace(/^.{8}(.+)$/,'$1'));
	param += '&cityid=' + ((city==null)?0:city.replace(/^.{8}(.+)$/,'$1'));
	param += '&lawyerid='+((lwyr==null)?0:lwyr.replace(/^.{8}(.+)$/,'$1'));
	param += '&statusid='+((stat==null)?'':stat.replace(/^.{8}(.+)$/,'$1'));
	if (elemtype=='ul' || elemtype=='ol' || elemtype=='ddul')
		elemOp = 'li';
	$('#'+id).empty();
	$.ajax({
		type : 'POST',
		url : ctx + "/getProtByFilter",
		async : false,
		data : param+'&trialid=-1',
		success : function(data) {
			var info = data[0]||[];
			if (info.juicios != null) {
				var tablelist='',trials=info.juicios,clients=info.clients,ccli=info.ccli;
				if(trials==null)return;
				for (t = 0; t < trials.length; t++)
					setclient:{
						for(cc=0; cc<ccli.length; cc++)
							if(trials[t].companyclientid==ccli[cc].companyclientid)
								for (cl = 0; cl < clients.length; cl++)
									if(ccli[cc].clientid==clients[cl].clientid){
										if(elemtype=='ddul'){
											$('#'+id).append('<'+elemOp+'><div><label for="ddulprcd'
												+trials[t].juicioid + '" class="ddlilabel w100p" title="' + clients[cl].client + '">'
												+trials[t].juicio + '</label><input type="radio" id="ddulprcd' + trials[t].juicioid
												+'" name="prcd" value="'+ trials[t].juicioid + '" class="ddliinput"><label for="ddulprcd'
												+trials[t].juicioid + '" title="' + clients[cl].client
												+'" class="ddlilabel"></label></div></'+elemOp+'>');
											break setclient;
										}else if(elemtype=='select' || elemtype==''){
											$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
										}
										$('#'+id).append('<'+elemOp+' value="'+ trials[t].juicioid + '" title="'+ clients[cl].client + '">'
											+ trials[t].juicio + '</'+elemOp+'>');
										break setclient;
									}
					}
			}
			$('#'+id).append('<'+elemOp+'></'+elemOp+'>');
		},error : function(e) {
			$('#federalDocList tbody').empty();
			console.log(i18n('err_unable_get_trial') + '. ' + e);
		}
	});
};

function getNumberDoc(id, elemtype, forfilter){
	$('#ddl-ft-NumDoc').empty();
	var clie=$('input[name=ft_clie]:checked').val()||0,
		ttyp=$('input[name=ft_typetrial]:checked').val()||0,
		ndoc=$('input[name=ft_feddocnum]:checked').val()||0;
	var elemOp = 'option', param='clientid='+clie + '&doctype='+ttyp + '&docid='+ndoc;
	if (elemtype=='ul' || elemtype=='ol')
		elemOp = 'li';
	$.ajax({
		type : 'POST',
		url : ctx + "/getFedDocsByFilter",
		async : false,
		data : param,
		success : function(data) {
			var info=data[0]||[]; 
			if (info != null){
				var tablelist='',ttype=$('input[name=ft_typetrial]:checked').val()||0,
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
	var prcd=$('input[name=cl_prcd]:checked').val()||0,clie=$('input[name=cl_clie]:checked').val()||0,
		cour=$('input[name=cl_cour]:checked').val()||0,matt=$('input[name=cl_matt]:checked').val()||'',
		city=$('input[name=cl_city]:checked').val()||0,lwyr=$('input[name=cl_lwyr]:checked').val()||0,
		stat=$('input[name=cl_stat]:checked').val()||'';
	var param='clientid='+clie + '&matterid='+matt + '&cityid='+city + '&courtid='+cour
		+'&trialid='+prcd + '&lawyerid='+lwyr + '&statusid='+stat;
	$('#forcourt').val((cour>0?'ddulcour'+cour:''));
	$.ajax({
		type : 'POST',
		url : ctx + "/getDocsByFilterFD",
		async : false,
		data : param,
		success : function(data) {
			var info = data[0]||[];
			if (info.juicios != null) {
				$('#commonLawList').show();
				var tablelist='',trials=info.juicios,clients=info.clients,ccli=info.ccli,
					courts=info.juzgados,matters=info.materias,cities=info.cities;
				if(trials==null)return;
				for(t=0; t<trials.length; t++){
					tablelist += '<tr><td><a href="trialsdashboard?language=es&rid='+trials[t].juicioid
						+'" rel="noopener noreferrer">' + trials[t].juicio + '</a></td>';
					if(ccli.length>0){
						let tcid=ccli.find(object => object.companyclientid===trials[t].companyclientid).clientid;
						tablelist += '<td><a onclick="sessionStorage.setItem(\'clid\', '+tcid
							+');" href="'+ctx+'/juicios?language='+ getLanguageURL()+'&clid='+tcid+'">'
							+(clients.find(object => object.clientid===tcid).client)+'</a></td>';
					}else{
						tablelist+='<td></td>';
					}
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
					tablelist+='<td>'+i18n('msg_'+(trials[t].status==0?'settled':trials[t].status==1?'active':'hung'))+'</td></tr>';
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
	var clie=$('input[name=ft_clie]:checked').val()||0,
		ttyp=$('input[name=ft_typetrial]:checked').val()||0,
		ndoc=$('input[name=ft_feddocnum]:checked').val()||0;
	if(clie==0&&ttyp==0&&ndoc==0)return;
	var param='clientid='+clie + '&doctype='+ttyp + '&docid='+ndoc;
	$.ajax({
		type : 'POST',
		url : ctx + "/getFedDocsByFilter",
		async : false,
		data : param,
		success : function(data){
			var info=data[0]||[]; 
			if(info != null){
				var tablelist='',ttype=$('input[name=ft_typetrial]:checked').val()||0,
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
			$('#federalDocList tbody').empty().append(tablelist);
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
	var clie=$('input[name='+forfilter+'clie]:checked').val(), matt=$('input[name='+forfilter+'matt]:checked').val(),
		city=$('input[name='+forfilter+'city]:checked').val(), cour=$('input[name='+forfilter+'cour]:checked').val(),
		stat=$('input[name='+forfilter+'stat]:checked').val(), lwyr=$('input[name='+forfilter+'lwyr]:checked').val();
	var param='trialid=-1&clientid='+((clie==null)?0:clie.replace(/^.{8}(.+)$/,'$1'))
		+'&courtid=' +(cour==null?0 :cour) + '&matterid='+(matt==null?'':matt) + '&cityid=' + (city==null?0 :city)
		+'&lawyerid='+(lwyr==null?0 :lwyr) + '&statusid='+(stat==null?'':stat);
	if(elemtype=='ul'||elemtype=='ol'||elemtype=='ddul')elemOp='li';
	$('#'+id).empty();
	$.ajax({
		type : 'POST',
		url : ctx + '/getDocsByFilterFD',
		async : false,
		data : param+'&trialid=-1',
		success : function(data) {
			var info = data[0]||[];
			if (info.juicios != null) {
				var tablelist='',trials=info.juicios,clients=info.clients,ccli=info.ccli;
				for (t = 0; t < trials.length; t++){
					let tcid=ccli.find(object => object.companyclientid===trials[t].companyclientid).clientid;
					let tname=clients.find(object => object.clientid===tcid).client;
					if(elemtype=='ddul'){
						$('#'+id).append('<'+elemOp+'><div><label for="ddulprcd'
							+trials[t].juicioid+'" class="ddlilabel w100p" title="'+tname+'">'
							+trials[t].juicio + '</label><input type="radio" id="ddulprcd'
							+trials[t].juicioid+'" name="'+forfilter+'prcd" value="'+trials[t].juicioid
							+'" class="ddliinput"></div></'+elemOp+'>');
					}else if(elemtype=='select' || elemtype==''){
						$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
					}
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
		if(selRb!=null){
			var id=selRb.getAttribute('id').replace(/^clie/,'');
			$('#scrClientSel, .selClient').html(selRb.getAttribute('data-val'));
			$('.selClientFooter').html(i18n('msg_selected_client')+': '+selRb.getAttribute('data-val'));
			$('#selClientId').val(id);
			if($('#selClientId').val()!=''){
				$('#nextClientData').attr('disabled',false);
				$('#nextClientData').removeClass('dnone');
				sessionStorage.setItem('clid', id);
			}else{
				$('#nextClientData').attr('disabled',true);
				$('#nextClientData').addClass('dnone');
				sessionStorage.removeItem('clid');
			}
			$('#nextClientData').trigger('click');
			$( ".scrClientSel div" ).fadeIn(100);
			setTimeout(function(){
				$('.scrClientSel div').fadeOut(100);
		    },1800);
		}
	}
},false);

$('#backToSearch').click(function (){
	$('[data-client="search"]').removeClass('dnone');
	$('[data-client="forInfo"],.modal-footer,#backToSearch').addClass('dnone');
	$('[data-list=findClient], .btnnext').removeClass('dnone');
});

$('#nextClientData').click(function (){
	$('#profileBigImg, #tab-homeclient-basic-info span, #tab-homeclient-contact-ways span,'
		+'#tab-homeclient-additional span, #pfrl-snTableListOnHome tbody').html('');
	$('#counthjuicios,#counthapelaciones,#counthprotections,#counthindprotections'
		+',#counthresources,#countHStudyConsult,#counthconsultas').html('(-)');
	$('[data-client="search"]').addClass('dnone');
	$('[data-client="forInfo"],.modal-footer, #backToSearch').removeClass('dnone');
	$('[data-list=findClient], .btnnext').addClass('dnone');
    $.ajax({
		type:'POST',
		url:ctx+'/dashboardClient',
		data:'clid='+(sessionStorage.clid).replace(/[^0-9]/g,''),
		async:false,
		success:function(data){
			if(data.length>0){
				var c=data[0].client[0]||[];
				var photo=(c.photo).replace(/.*\/(doctos\/.*)/,'$1')||'';
				if(photo!='')
					$('#profileBigImg').append('<img src="'+photo+'" title="'+c.client
						+' alt="'+c.client+'" onerror="this.classList.add(\'dnone\')" />');
/*var im=$('#profileBigImg img'),photo=c.photo||'';
if(photo!=''){
	photo=photo.substring(photo.indexOf('doctos/'),photo.length)
	if(im.length>0){
		$('#profileBigImg img').removeClass('dnone');
		$('#profileBigImg img').attr('src',photo);
	}else{
		$('#profileBigImg').append('<img src="'+photo+'" title="'+c.client
			+' alt="'+c.client+'" onerror="this.classList.add(\'dnone\')" />');
	}
}*/
				$('#counthjuicios').html('('+data[0].totalTrials+')');
				$('#counthapelaciones').html('('+data[0].totalApl+')');
				$('#counthprotections').html('('+data[0].totalProt+')');
				$('#counthindprotections').html('('+data[0].totalInd+')');
				$('#counthresources').html('('+data[0].totResources+')');
				$('#counthconsultas').html('('+data[0].totalCons+')');
			}
		},error:function(e){
			swal(i18n('msg_warning'),"Error al obtener datos del Cliente, posiblemente la sesi\u00f3n termin\u00f3.\n"+e,'error');
		}
	});
});

$('#hClientProfile').click(function (){
	var id=sessionStorage.clid;
	getDetailsClient(id);
});

$('#hClientEdit').click(function (){
	var id=sessionStorage.clid;
	sessionStorage.removeItem('clid');
	$('#findclient-modal').modal('toggle').fadeOut(800);
	location.href = ctx+'/clients?language='+ getLanguageURL()+'&clid='+id;
});

$('.contAddCli, .btnAddCli').click(function (){
	var id=sessionStorage.clid;
	sessionStorage.removeItem('clid');
	$('#findclient-modal').modal('toggle').fadeOut(800);
	location.href = ctx+'/clients?language='+ getLanguageURL()+'&nw=st';
});

$('#hjuicios, #hapelaciones, #hprotections, #hindprotections, #hresources, #hconsultas').click(function (){
	var pg=(this.id).replace(/^./,'/'),cl=sessionStorage.clid;
	sessionStorage.removeItem('clid');
	location.href=location.origin+ctx+pg+'.jet'+location.search+'&clid='+cl;
});

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

function getDashboardNotifications(id){
	$('#'+id+'tbody tr').remove();
	$('#dashtotnotif').html('0');
	$.ajax({
		type:'POST',
		url:ctx+'/getNotifyShort',
		async:false,
		success:function(data){
			if(data==null) return;
			var n=0, tablelist='', acttodo=['msg_new_record','msg_changes','msg_deleted','msg_added_document'];
			for(n=0;n<Object.keys(data).length;n++){
				var info=data[n]||[];
				var d = new Date(info.date),area=info.modulename
				var year=d.getFullYear(), month=('0'+(d.getMonth()+1)).slice(-2), day =('0'+d.getDate()).slice(-2), 
					fmtdate=getFormatPatternDate().replace(/y+/g,'y').replace(/M+/g,'M').replace(/d+/,'d');
				fmtdate=fmtdate.replace('d',day).replace('M',month).replace('y',year),
//TODO i18n p/area:
				area=area.replace(/(s\b|\b)/ig,'').replace(/^Movimientos?\sde\s/ig,'')
				if(area=='apelacione')
					area='Apelaci\u00f3n';
				area=camelize(area);

				dbntfy.row.add([
					'<span style="color:#000">' +area+ '</span><br><a href="' +info.link
						+'?language=es&rid=' +info.idref+ '" rel="noopener noreferrer" title="'
						+i18n('msg_goto_record')+" " +info.proceedings+ '">' +info.proceedings+'</a>'
				    ,(info.courtname==''?'- - - - -':info.courtname), (info.clientname==''?'- - - - -':info.clientname)
				    ,(info.description==''?'- - - - -':info.description) ,fmtdate
				    ,'<i class="material-icons asLink trn2ms tac" onclick="infonotify(' + info.notificationid
				    	+');" style="width:40px;margin:auto 0;padding-left:10px">info_outline</i>' ])
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
		type:'POST',
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
			$('#ntfyfromuser').html(info.username);
			$('#ntfyid_module').val(info.notificationid);

			var jsonact = JSON.parse(info.actionsdetails),actdet='',filemgn='';
			for(a=0;a<Object.keys(jsonact).length;a++)
				if(jsonact[a].field!=null)
					actdet+='<tr><td>' + jsonact[a].field + '</td><td>' + jsonact[a].newdata
						+'</td><td>' + jsonact[a].olddata + '</td></tr>';
				else
					filemgn+='<tr><td>' + 'archivo' + '</td><td>' + 'status'
						+'</td><td>' + jsonact[a].file + '</td></tr>';
			$('#tablentfydetail tbody').html(actdet);

			$('#notify-detail-modal').modal('show');
		},error:function(e){
			swal(i18n('msg_warning'),i18n('err_notification_fail')+e,'error');
		}
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
	};

	$('#findclient-modal').on('show.bs.modal', function (){
		$('[data-client="forInfo"],#backToSearch').addClass('dnone');
		$('[data-client="search"],[data-list=findClient],.btnnext,[data-list=findClient]').removeClass('dnone');
		$('[data-list=findClient]').show();
		$('#nextClientData').attr('disabled',true);
		$('#findClient').val('');
		$('.selClient').html('');
		replaceTextInHtmlBlock($('#findClientList_filter label'), 'Buscar:', 
	    	'<i class="material-icons searchClIcon" onclick="$(\'#findClientList_filter '
			+'[aria-controls=findClientList]\').focus()">&#xe8b6;</i>');
	    $("#findClientList_filter [aria-controls=findClientList]").attr("placeholder", i18n('msg_search'));
	});
	$('#findclient-modal').on('shown.bs.modal', function (){
		$('#findClientList_filter label').html().replace(/Buscar:/g,'');
		$('#findClientList_filter input')[0].focus();
	});
	$('#filter-commonlaw-modal').on('shown.bs.modal', function (){
		getClients('ddl-cl-client', 'ddul', 'cl_');
		getMaterias('ddl-cl-matter','ddul', 'cl_');
		getLawyerList('ddl-cl-lawyer','ddul','cl_',1);
		getCourts('ddl-cl-court', 'ddul', 'cl_');
		fillHomeCities('#ddl-cl-city', 'ddul','cl_');
	});
	$('#filter-feddoc-modal').on('shown.bs.modal', function (){
		getClients('ddl-ft-client','ddul','ft_');
	});
	$('#ddl-cl-status').append('<li><div><label for="ddulstat0" class="ddlilabel w100p">' + i18n('msg_settled')
	+ '</label><input type="radio" id="ddulstat0" name="cl_stat" value="0" '
	+ 'class="ddliinput"><label for="ddulstat0" class="ddlilabel"></label></div></li>')
		.append('<li><div><label for="ddulstat1" class="ddlilabel w100p">' + i18n('msg_active')
	+ '</label><input type="radio" id="ddulstat1" name="cl_stat" value="1" '
	+ 'class="ddliinput"><label for="ddulstat1" class="ddlilabel"></label></div></li>')
		.append('<li><div><label for="ddulstat2" class="ddlilabel w100p">' + i18n('msg_inactive')
	+ '</label><input type="radio" id="ddulstat2" name="cl_stat" value="2" '
	+ 'class="ddliinput"><label for="ddulstat2" class="ddlilabel"></label></div></li>');
	$('#commonLawList').hide();

	$('#ddl-fd-typetrial').append('<li><div><label for="ft_typetrial1" class="ddlilabel w100p">' + i18n('msg_direct_protection')
		+'</label><input type="radio" id="ft_typetrial1" name="ft_typetrial" value="1" class="ddliinput">'
		+'<label for="ft_typetrial1" class="ddlilabel"></label></div></li>')
	.append('<li><div><label for="ft_typetrial2" class="ddlilabel w100p">' + i18n('msg_indirect_protection')
		+'</label><input type="radio" id="ft_typetrial2" name="ft_typetrial" value="2" class="ddliinput">'
		+'<label for="ft_typetrial2" class="ddlilabel"></label></div></li>')
	.append('<li><div><label for="ft_typetrial3" class="ddlilabel w100p">' + i18n('msg_resource')
		+'</label><input type="radio" id="ft_typetrial3" name="ft_typetrial" value="3" class="ddliinput">'
		+'<label for="ft_typetrial3" class="ddlilabel"></label></div></li>');
	$('#federalDocList').hide();
});