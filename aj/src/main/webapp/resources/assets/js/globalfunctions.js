;function getVarURL(v){
	var querys=(location.search).replace(/^./, "");
	var vars=querys.split("&");
	for(i=0;i<vars.length;i++){
		var pair=vars[i].split("=");
		if(pair[0]==v)
			return pair[1];
	}return "";
};

/** Selecciona la opción de una lista de acuerdo al valor indicado y establece
 el texto correspondiente a dicha opción en el input asociado.<br>
@param	id	{int}		Id del listado a verificar.
@param	val {string}	Valor a buscar entre las opciones del listado.<br>

Para obtener los datos de un &lt;li&gt; seleccionado, se puede utilizar una de estas líneas:<ul>
	<li>1) Value:	var valor=$('#myId li.selected').val();</li>
	<li>2) Text:	var valor=$('#myId li.selected').text();</li>
	<li>3) Text:	var valor=$('#myId li.selected span').text();</li></ul><br>
Para obtener el valor no numérico de un &lt;li&gt;, se recomienda utilizar "data-*"<ul>
	<li>a)	&lt;li data-value="GLD"&gt;Guadalajara&lt;li&gt;</li>
	<li>b)	$('#myId li.selected').data('value');</li></ul>*/
function getTextDDFilterByVal(id, val){
	var fAllLi=$('#'+id+' li');
	$(fAllLi).removeClass('selected');
	for(i=0;i<fAllLi.length;i++)
		if (fAllLi[i].value==val){
			var fsel=$('#'+id+' li').eq(i);
			$(fsel).addClass('selected');
			var isInput=$('#'+id).prev().prev();
			if(isInput.get(0).tagName!="INPUT")
				isInput=$(isInput).prev();
			$(isInput).val($(fsel).text());
			return;
		}
};

/** Selecciona la opción de una lista de acuerdo al texto indicado en su input.<br>
	@param	id	{string}	Id del listado a verificar.
	@param	val {string}	Valor a buscar entre las opciones del listado.<br>	*/
function setValDDFilterByText(id, val){
	var fAllLi=$('#'+id+' li');
	$(fAllLi).removeClass('selected');
	for(i=0;i<fAllLi.length;i++)
		if($(fAllLi[i]).html()===val){
			var fsel=$('#'+id+' li').eq(i);
			$(fsel).addClass('selected');
			return;
		}
};

/** Función para ocultar una imagen no válida.<br> Puede indicarse directamente en el 
	evento "<b>onerror()</b>" del elemento "<b>&lt;img&gt;"</b> de esta forma:<br><br>
	&lt;img src="myimage.jpg" onerror="optImgListError(this);" /&gt;<br>
	@param	obj	{objeto}	Objeto donde se muestra la imagen.	*/
function optImgListError(obj){
	$(obj).hide();
	$(obj).parent().css('padding-left', '54px');
};

function validDDFilter(currList){
	if(currList==null){
		var o=$('li.selected');
		for(l=0;l<o.length; l++){
			var ival=$(o[l]).parent().prev().prev();
			if ($(ival).val()==i18n('msg_addnew')){
				$(ival).val('');
			}else if ($(ival).val().trim()==''){
				$('#'+id+' li.selected').removeClass('selected');
				$(ival).val('');
			}else{
				$(ival).val($(o[l]).text());
			}
		}
		return;
	}
	var o=$(currList).find('li'),ival=$(currList).prev().prev(),id=currList.attr("id");
	for(l=0;l<o.length; l++){
		if ($(ival).val()==i18n('msg_addnew')){
			$(ival).val('');
			break;
		}else if ($(ival).val().trim()==''){
			$('#'+id+' li.selected').removeClass('selected');
			$(ival).val('');
			break;
		}else if ($(ival).val().trim()==$(o[l]).text()){
			$('#'+id+' li.selected').removeClass('selected');
			$(o[l]).addClass('selected');
			break;
		}
	}
};

function optImgListIcon(o){
	if(o.parentElement==null)return;
	var a=o.getAttribute('class')+' ';
	a+=o.parentElement.getAttribute('class');
	if(a.indexOf('iconlistfilter')>=0||
		a.indexOf('listfiltersel')>=0||
		a.indexOf('ddListImg')>=0){
		var currList=a.indexOf('listfiltersel')>=0?$(o).next().next():a.indexOf('iconlistfilter')>=0?$(o).next():$(o);
		validDDFilter(currList);
		var arrowicon=$(o.parentElement).find('.iconlistfilter'),
			ddlist=$(o.parentElement).find('.ddListImg'),
			inputsrch=$(o.parentElement).find('input');
		$('.ddListImg li').show();
		if(a.indexOf('listfiltersel')>=0&&($(arrowicon[0]).html()=='arrow_drop_up')){
			$('.ddListImg').hide();
			$('.iconlistfilter').html('arrow_drop_down');
			if($(o).hasClass('listfiltersel')){
				var arrowicon=$(o.parentElement).find('.iconlistfilter'),
					ddlist=$(o.parentElement).find('.ddListImg');
				$(ddlist[0]).show();
				$(arrowicon[0]).html('arrow_drop_up');
			}
			return;
		}
		if($(arrowicon[0]).html()=='arrow_drop_up'){
			$(ddlist[0]).hide();
			$(arrowicon[0]).html('arrow_drop_down');
			validDDFilter(currList);
			$(inputsrch).next().focus();
		}else{
			$('.ddListImg').hide();
			$('.iconlistfilter').html('arrow_drop_down');
			$(ddlist[0]).show();
			$(arrowicon[0]).html('arrow_drop_up');
			$(inputsrch).focus();
		}
		if(o.value.length>0)
			o.setSelectionRange(0,o.value.length);
	}else{
		$('.ddListImg').hide();
		$('.iconlistfilter').html('arrow_drop_down');
	}
};

function optImgListSel(obj){
	obj.parentElement.parentElement.firstElementChild.value=obj.innerText;
	$(obj.parentElement).next().focus();
	$('.ddListImg').hide();
	$('.iconlistfilter').html('arrow_drop_down');
	validDDFilter();
	$(obj).triggerHandler("click");
};

function camelize(str){
	var words=str.split(' '),newstr='';
	for(i=0;i<words.length;i++){
		newstr+=words[i].replace(/(?:^\w|[A-Z]|\b\w|)/g, function(word, index){
			return index===0?word.toUpperCase():word.toLowerCase();
		}).replace(/\s+/g,'')+' ';
	}newstr=newstr.replace(/.$/,'')
		.replace(/(\w+)Ä/g,'$1ä').replace(/(\w+)Á/g,'$1á').replace(/(\w+)Â/g,'$1â').replace(/(\w+)À/g,'$1à')
		.replace(/(\w+)Ë/g,'$1ë').replace(/(\w+)É/g,'$1é').replace(/(\w+)Ê/g,'$1ê').replace(/(\w+)È/g,'$1è')
		.replace(/(\w+)Ï/g,'$1ï').replace(/(\w+)Í/g,'$1í').replace(/(\w+)Î/g,'$1î').replace(/(\w+)Ì/g,'$1ì')
		.replace(/(\w+)Ö/g,'$1ö').replace(/(\w+)Ó/g,'$1ó').replace(/(\w+)Ô/g,'$1ô').replace(/(\w+)Ò/g,'$1ò')
		.replace(/(\w+)Ü/g,'$1ü').replace(/(\w+)Ú/g,'$1ú').replace(/(\w+)Û/g,'$1û').replace(/(\w+)Ù/g,'$1ù')
		.replace(/(\w+)Ŷ/g,'$1ÿ').replace(/(\w+)Ý/g,'$1ý').replace(/(\w+)Ŷ/g,'$1ŷ').replace(/(\w+)Ỳ/g,'$1ỳ')
		.replace(/(\w+)Ñ/g,'$1ñ').replace(/(\w+)Ç/g,'$1ç');
	return newstr
};

/** Retorna un texto normalizado y sin caracteres acentuados.
@param inputText	Cadena de texto a analizar.
@param forFile		Valor '1' = Realiza el análisis exclusivo para nombres de archivo y elimina los caracteres no permitidos.
 					Cualquier otro valor o nulo, realizará el analísis quitando cualquier caracter acentuado. */
function noDiacritics(inputText, forFile){
	if(typeof inputText!="string")return input;
	forFile=forFile||'';
	var r='',s='';
	if(forFile=='1'||forFile==1)
		inputText=inputText.replace(/[#%&\\/<>*?"':+|`\^]/gi, "_");
	s=inputText.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
	for(i=0;i<s.length;i++){
		var cp=s.charCodeAt(i);
		r+=(cp>=32&&cp<=126)?s[i]:'_';
	}
	return r;
};

function clearTemp() {
	$('input[name^="fileuploadx_"]').each(function(i) {
		$(this).val('');
	});
	$.ajax({
		type : 'POST',
		url : ctx + "/deleteTempPath",
		async : false,
		success : function(data) {
			if (!data)
				console.log("Not cleared");
		}
	});
};

/** clientes.
@param	inputText	{string}	Dato ingresado por el usuario.
@param	fromTable	{string}	Tabla donde serán filtrados los datos.	*/
function filtext(inputText,fromTable){
	var filter=noDiacritics(inputText.toUpperCase()),table=$(fromTable),trs;
	trs=table[0].getElementsByTagName('tr');
  	if(inputText!='')
  		for(t=1;t<trs.length;t++){
  			var output=noDiacritics(trs[t].innerText);
  			trs[t].style.display=(output.toUpperCase().indexOf(filter)> -1)?'':'none';
  		}
  	else
  		$(trs).show();
  	$(table).parent().removeClass('dnone').show();
};

function togglemodtab(e,tab){
	if($(e).hasClass('selectedtab'))return;
	$('.tabsmodal li').removeClass('selectedtab');
	if(typeof e=='string')
		e=$(e+' li:first-child');
	$(e).addClass('selectedtab');
	$('.firmdatatabs').hide();
	$(tab).show();
};

function replaceTextInHtmlBlock($element, replaceText, replaceWith){
	var $children = $element.children().detach();
	$element.html($element.text().replace(replaceText, replaceWith));
	$children.each(function(index, me){
		replaceTextInHtmlBlock($(me), replaceText, replaceWith);
	});
	$element.append($children);
};

function cleanTableFilters(e){
 	$('[name=filterby]').val('');
 	dttable.search('').columns().search('').draw();
 	$(e).addClass('btn-secondary').removeClass('btn-warning');
};

function forceclose(obj){
	$(obj).fadeOut(200);
	$(obj).removeClass('fornw');
	$('.custombox-overlay, .modal-backdrop, .custombox-modal-wrapper').hide();
};

// .: Notifications :. \\
function refreshNtfy(){
	setTimeout(function(){
		var d=new Date();
		refreshNtfy();
	}, 30000);
	var msgNtfyId=remindNotify();
	$.ajax({
		type:"POST",
		url:ctx + "/refreshNtfy",
		data:'msgNtfyId='+msgNtfyId,
		async:false,
		success:function(data){
			var info = data[0]||[];
			for(i=0;i<Object.keys(info).length;i++){
				var n = document.createElement("div"),nid=info[i].notificationid;
				if($("#nid"+nid).length>0)
					continue;
				remindNotify(nid);
				n.setAttribute("id","nid"+nid);
				n.classList.add("popupNotify");
				n.classList.add("trn3ms");
				n.classList.add("inlineflex");
				document.getElementById("notification-area").appendChild(n);
				var ntfy='<div class="popupNIcon flex">'
						+'<div class="imgdoc-ntfy flex">'
							+'<img src="resources/assets/images/documentos-juicios.png" '
								+'title="'+i18n('msg_notification')+'" alt="'+i18n('msg_notification')+'"/>'
						+'</div>'			
					+'</div>'
					+'<div class="popupNMsgs">'
						+'<div class="popNHeader">'
							+'<span>'+'<a href="'+JSON.parse(info[i].area).link+'?language=es&rid='+info[i].idref
								+'" rel="noopener noreferrer" title="'+i18n('msg_goto_record')+"&nbsp;"
								+info[i].document+'">'+(i18n(info[i].actiontypeid==0?'msg_new_record':
								info[i].actiontypeid==2?'msg_record_deleted':'msg_changed_record'))+'</a>'
							+'</span>'
						+'</div>'
						+'<div class="popNBody">'
							+'<span><a href="'+JSON.parse(info[i].area).link+'?language=es&rid='+info[i].idref
								+'" rel="noopener noreferrer" title="'+i18n('msg_goto_record')+"&nbsp;"
								+info[i].document+'">'+'<i class="fa fa-external-link"></i>&nbsp;'+info[i].document+'</a>'
							+'</span>'
						+'</div>'
						+'<div class="popNFooter flex">'
							+'<div class="popNFooterText">'
								//Texto footer
							+'</div>'
							+'<div class="popupNBtns inlineflex tar">'
								+'<div class="crossfade">'
									+'<img src="resources/assets/images/keep-notification2.png" alt="x" '
										+'onclick="timeupNotify(\''+nid+'\');" title="'+i18n('msg_closeandkeep') + '" />'
									+'<img class="cf-top" src="resources/assets/images/keep-notification.png" alt="x" '
										+'onclick="timeupNotify(\''+nid+'\');" title="'+i18n('msg_closeandkeep') + '" />'
								+'</div>'
								+'<div class="crossfade">'
									+'<img src="resources/assets/images/notification-checked2.png" '
										+'title="'+i18n('msg_markasread')+'" onclick="stopNotify(\''+nid+'\');" alt="Ok"/>'
									+'<img class="cf-top" src="resources/assets/images/notification-checked.png" '
										+'title="'+i18n('msg_markasread')+'" onclick="stopNotify(\''+nid+'\');" alt="Ok"/>'
								+'</div>'
							+'</div>'
						+'</div>'
					+'</div>';
				$('#nid'+nid).append(ntfy);
				setTimeout(()=>{
					timeupNotify();
				},15000);
			}
		},error : function(e) {
			$('#background-contain').hide();
			console.log(i18n('err_unable_get_notifications') + '. ' + e);
		}
	});
};

function remindNotify(nid){
	if(!(/^\d+$/g).test(nid)){
		nid=getVarURL('nid')||'';
		if((/^\d+$/g).test(nid))
			window.history.replaceState({},document.title,location.href.replace(/(.*)&nid=\d+(.*)/g,'$1$2'));
	}
	var n=localStorage.getItem('msgNtfyId')||'';
	n=(/\d+(\,\d+)?/g).test(n)?n.split(','):[];
	if(nid!=''&&n.indexOf(nid+'')<0){
		n.push(nid);
		localStorage.setItem('msgNtfyId',n);
	}
	return localStorage.getItem('msgNtfyId');
};

function timeupNotify(nid){
	var n=localStorage.getItem('msgNtfyId')||'',idx=0;
	n=(/\d+(\,\d+)?/g).test(n)?n.split(','):[];
	if((/^\d+$/g).test(nid))
		idx=n.indexOf(nid+'');
	else
		nid=n[0];
	if(idx> -1){
		n.splice(idx, 1);
		localStorage.setItem('msgNtfyId',n);
	}
	$('#nid'+nid).fadeOut(300, function(){$('#nid'+nid).remove();});
};

function stopNotify(nid){
	timeupNotify(nid);
	$.ajax({
		type:"POST",
		url:ctx + "/updateNotifyStatusDate2",
		data:'nid='+nid + '&setStatus="confirmed"',
		async:false,
		success:function(data){
			return !0;
		}
	});
};


function getObjRecord(data, keyColumn, value){
	return data.filter(
		function(data) {
			return data[keyColumn] == value;
		}
	);
};

jQuery(document).ready(function($){
	$('.listfiltersel').on('keyup input propertychange paste change', function (e){//focus
		var ddlist=$(this).next().next(),optvisible=[],sel=null;
		var l=ddlist[0].querySelectorAll('li'),k=e.key||e.keyCode||e.which;
		if([13,16,27,37,38,39,40,'Enter','Escape','ArrowUp','ArrowDown'].indexOf(k)>=0){
			if(k==='Enter'||k===13){
				var obj=ddlist[0].getElementsByClassName('selected'),inputs=$('input');
				for(i=0;i<inputs.length;i++)
					if((document.activeElement==inputs[i]) && (i+1<inputs.length)){
						inputs[i+1].focus();
						break;
					}else if($(obj).text()==i18n('msg_addnew')){
						$(obj).trigger('click');
					}
				optImgListSel(obj[0]);
			}else if(k==='Escape'||k===27){
				$(ddlist).hide();
				validDDFilter();
			}else{
				$(ddlist).show();
				for(i=0;i<(l.length);i++)
					if($(l[i]).is(':visible')){
						if($(l[i]).hasClass('selected'))
							sel=i;
						optvisible.push(i);
					}
				$(l).removeClass('selected');
				var subsel=optvisible.indexOf(sel);
				if(k==='ArrowUp'||k===38){
					if(optvisible.indexOf(sel)<=0)
						$(l[optvisible[optvisible.length-1]]).addClass('selected');
					else
						$(l[optvisible[subsel-1]]).addClass('selected');
					$('.ddListImg').scrollTop($(this).next().index() * $(this).outerHeight());
				}else if(k==='ArrowDown'||k===38){
					if(sel==null)sel=-1;
					if(optvisible.indexOf(sel)==optvisible.length-1)
						$(l[optvisible[0]]).addClass('selected');
					else
						$(l[optvisible[subsel+1]]).addClass('selected');
					$('.ddListImg').scrollTop($(this).prev().index() * $(this).outerHeight());
				}
			}
		}else if(k==='Tab'||k===9){
			var o=e.target;
			$('.ddListImg').hide();
			$('.iconlistfilter').html('arrow_drop_down');
			if($(o).hasClass('listfiltersel')){
				var arrowicon=$(o.parentElement).find('.iconlistfilter'),
					ddlist=$(o.parentElement).find('.ddListImg');
				$(ddlist[0]).show();
				$(arrowicon[0]).html('arrow_drop_up');
			}else{
				$('.ddListImg').hide();
				$('.iconlistfilter').html('arrow_drop_down');
			}
		}else{
			var toFind=this.value.toLowerCase();
			$(ddlist).show();
			$(this).next().html('arrow_drop_up');
			for(i=0;i<(l.length)-1;i++){
				var optText=$(l[i]).text().toLowerCase();
				l[i].style.display=((optText.indexOf(toFind)> -1||toFind=='')?'':'none');
			}
		}
	});

	$('.quickTip button, .quickTip span').on('click', function(){
		var added=$(this).data('tip');
		var banner=$('span[data-tip='+added+']'),
			markup=$('input[data-tip='+added+']');
		if($(banner).css('display')=='none')
		    $(markup).addClass('highlight-tip');
		else
		    $(markup).removeClass('highlight-tip');
		$(banner).fadeToggle();
	});

	$("[data-reset]").on('click', function(){
		var fieldsfor=$(this).attr('data-reset');
		$(fieldsfor+' select, '+fieldsfor+' textarea, ' + fieldsfor+' input')
			.not('input[type="hidden"]').val('');
		$('*[id*=date]').val('');
		$('#actionway, #editactionway').html('');
		$('.rowopt').css('background-color',' #fff')
	});

	$('input, select, .select-wrapper, textarea').on('focus',function() {
		$(this).parent().removeClass('has-error');
		$('div.alert-danger.fade.in').slideUp(250);
	});

	$(window).scroll(function(){
		if ($(window).scrollTop() > 300) $('.btnScrollUp').addClass('show');
		else $('.btnScrollUp').removeClass('show');
	});

	$('.btnScrollUp').on('click',function(e){
		e.preventDefault();
		$('html, body').animate({scrollTop:0},'300');
	});

	$(document).on("keydown", function (e) {
			    if (e.key === "Enter") {
			      // Buscar el modal activo
			      const $activeModal = $(".modal.active");
			      if ($activeModal.length) {
			        // Ejecutar el botón "Aceptar" del modal activo
			        $activeModal.find(".btn-default").trigger("click");
			        e.preventDefault(); // Prevenir el comportamiento predeterminado
			      }
			    }
			  });
	
	refreshNtfy();
});

$(function(){
	$(document).on('click', '.ddListImg li', function(e){
		$(this.parentElement.querySelectorAll('li.selected')).removeClass('selected');
		$(this).addClass('selected');
		optImgListSel(this);
	});
	$('.ddListImg li').mouseover(function(){
		$(this.parentElement.querySelectorAll('li.selected')).removeClass('selected');
		$(this).addClass('selected');
	});
});



// Notifications >>
function ntfyListener(){
	$.ajax({
		type:'POST',
		url:ctx+'/ntfyListener',
		async:false,
		success:function(msgs){
		    var qtyN=Object.keys(msgs).length;
		    if(qtyN==0)return;

		    for(n=0;n<qtyN-1;n++){
		    	var at=msgs[n].action,dash,ititle,itip,files=[];
		    	var title=i18n('msg_'+(at=='0'?'new_record':at=='1'?'changed_record'
	    				:at=='2'?'record_deleted':'notification'))
	    				+ (msgs[n].clientname?' - '+msgs[n].clientname:''),
    				origin=ctx+'/'+(msgs[n].link.replace(/(.*)\.jet?/,'$1'))
		    			+'?language='+getLanguageURL()+'&rid='+msgs[n].idref;
		    		dash=ctx+'/'+(msgs[n].link.replace(/(.*)\.jet?/,'$1'))
						+'?language='+getLanguageURL()+'&rid='+msgs[n].idref,
//avatar=msgs[n].avatar==''?'':msgs[n].avatar;
					avatar=msgs[n].avatar||'';
console.log("Mensaje: "+msg[n]);
		    	ititle=msgs[n].modulename;
				itip=ititle;
				var ntfy={
					abrType:msgs[n].abrType,
					avatar:msgs[n].avatar,
					body:msgs[n].docname,
					courtname:msgs[n].courtname,
					date:msgs[n].date,
					files:msgs[n].files,
					idref:msgs[n].idref,
					itip:itip,
					ititle:ititle,
					nid:msgs[n].nid,
					origin:origin,
					dash:msgs[n].dash,
					title:title
				}
		    	popupNtfy(ntfy);
		    }
		},error:function(e){console.log(i18n('err_unable_get_notifications'));}
	});
};

function getNtfyFileIcon(extension){
	var x=extension.toLocaleLowerCase(),ico='',f='r';
	return-1<['json','txt','xml'].indexOf(x)?ico='-alt'
	:-1<['doc','docx','odt','rtf'].indexOf(x)?(ico='-word',f='s')
	:-1<['csv','ods','xls','xlsx'].indexOf(x)?(ico='-excel',f='s')
	:-1<['aac','mp3','ogg','wav','3gp'].indexOf(x)?ico='-audio'
	:-1<'odp otp pps ppsx ppt pptx'.split(' ').indexOf(x)?(ico='-powerpoint',f='s')
	:-1<'avi mkv mp4 mpg mpge mov webm'.split(' ').indexOf(x)?ico='-video'
	:-1<'ace gz iso rar rar5 tar zip 7z'.split(' ').indexOf(x)?ico='-archive'
	:-1<'bmp gif ico jpg jpeg png svg webp'.split(' ').indexOf(x)?(ico='-image',f='s')
	:-1<'aab ade adp app apk applescript bash bat bin chm cmd com command cpl desktop dll docm dmg exe hta htm html inetloc ins isp jar js jse kext ksh lib lnk mde msc msi msp mst nsh pif pkg pl pptm ps1 py scpt scr sct sh shb so sys vb vbe vbs vxd wsc wsf wsh xlsm zsh'.split(' ').indexOf(x)?(ico='-code',f='s')
	:'pdf'==x&&(ico='-pdf'),'<i class="fa'+f+' fa-file'+ico+'"></i>';
	/*var x=extension.toLocaleLowerCase(),ico='',f='r';
	return 0>=['json','txt','xml'].indexOf(x)?ico='-alt'
	:0>=['doc','docx','odt','rtf'].indexOf(x)?(ico='-word',f='s')
	:0>=['csv','ods','xls','xlsx'].indexOf(x)?(ico='-excel',f='s')
	:0>=['aac','mp3','ogg','wav','3gp'].indexOf(x)?ico='-audio'
	:0>=['odp','otp','pps','ppsx','ppt','pptx'].indexOf(x)?(ico='-powerpoint',f='s')
	:0>=['avi','mkv','mp4','mpg','mpge','mov','webm'].indexOf(x)?ico='-video'
	:0>=['ace','gz','iso','rar','rar5','tar','zip','7z'].indexOf(x)?ico='-archive'
	:0>=['bmp','gif','ico','jpg','jpeg','png','svg','webp'].indexOf(x)?(ico='-image',f='s')
	:0>=['aab','ade','adp','app','apk','applescript','bash','bat','bin','chm','cmd','com',
       'command','cpl','desktop','dll','docm','dmg','exe','hta','htm','html','inetloc','ins',
       'isp','jar','js','jse','kext','ksh','lib','lnk','mde','msc','msi','msp','mst','nsh',
       'pif','pkg','pl','pptm','ps1','py','scpt','scr','sct','sh','shb','so','sys','vb','vbe',
       'vbs','vxd','wsc','wsf','wsh','xlsm','zsh'].indexOf(x)?(ico='-code',f='s')
	:x=='pdf'&&(ico='-pdf'),'<i class="fa'+f+' fa-file'+ico+'"></i>';*/
};

function popupNtfy(notification){
	var title=notification.title, body=notification.body,
    	origin=notification.origin, avatar=notification.avatar,
    	ititle=notification.ititle, itip=notification.itip;
    var popup = $('<div class="popup-message"></div>'),
      lefticon = $('<div class="popup-lefticon m-r-10 m-b-5"><img src="'+avatar
        +'" title="'+ititle+'" alt="'+itip+'"></div>'),
      middlemsg=$('<div class="popup-middlemsg"></div>'),
      header=$('<div class="popup-header"></div>').text(title),
      content=$('<a class="popup-body asLink trn2ms" target="_self" title="'
		+i18n('msg_opendocument')+'" rel="noopener noreferrer" href="'
		+notification.dash+'?language='+getLanguageURL()+'&rid='+notification.idref
		+'&nid='+notification.nid+'"></a>').text(body),
      linkContainer = $('<div class="popup-links asLink trn2ms"></div>'),
      blankbase = $('<div class="popup-blankbase"></div>'),
      rewrite=$('<a href="'+origin+'" target="_self" rel="noopener noreferrer" title="'
        +i18n('msg_edit')+'" style="font-size:18px;float:left"><i class="fa fa-edit"></i></a>');
 
    middlemsg.append(header).append(content).append(linkContainer);

     var files=(notification.files).split('|');
     if(isNaN(notification.files - 0))
	     for(f=0; f<files.length;f++){
	    	var fileName=(files[f].match(/[^\/\\&\?]+\.\w{3,4}(?=([\?&].*$|$))/))[0];
	    	var ext=fileName.replace(/.*\.(.*)$/g,'$1');
	        var icon=getNtfyFileIcon(ext);
	        linkContainer.append($('<a href="'+files[f]+'" title="'+fileName
			  +'" class="asLink" target="_blank" rel="noopener noreferrer">'
			  + icon + '&emsp;' + fileName + '</a><br>'));
	     }
    var buttons = $('<div class="popup-buttons"></div>');
    var seenButton = $('<button class="seen" title="'+i18n('msg_markasread')+'">'
    		+i18n('msg_read')+'</button>').click(function(){
        removePopup(popup);
    });
    var laterButton=$('<button class="show-later" title="'+i18n('msg_closeandkeep')+'">'
			+i18n('msg_ok')+'</button>').click(function(){
        removePopup(popup);
    });

    buttons.append(rewrite).append(seenButton).append(laterButton);
    popup.append(header).append(lefticon).append(middlemsg).append(blankbase).append(buttons);

    var arrow = $('<div class="popup-arrow"><i class="fas fa-chevron-down"></i></div>');// Arrow
    arrow.click(function(e){
        e.stopPropagation(); // No auto-closing mouse-over
        popup.toggleClass('popup-expanded');
	    });
	    popup.append(arrow);
//console.log("popup(" + popup[0] +")");
    $('#popup-notification').append(popup);

    popup.hover(	// No hiding onmouseover
        function(){
            clearTimeout(popup.data('timeout'));
        },function(){
            startHideTimer(popup);
        }
    );

    setTimeout(function(){
        popup.addClass('show');
    }, 10);

    startHideTimer(popup); // Time to hide
};

function startHideTimer(popup){
    var timeout = setTimeout(function(){
        removePopup(popup);
    }, 18000);//5000
    popup.data('timeout', timeout);
};

function removePopup(popup){
    popup.removeClass('show');
    setTimeout(function(){
        popup.remove();
    }, 300);
};
//setInterval(ntfyListener, 3000);
//<< Notifications ||



$(document).click(function(e){
	optImgListIcon(e.target);
});