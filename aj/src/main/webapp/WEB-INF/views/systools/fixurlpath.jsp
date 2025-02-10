<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<title>Corrección de URL Paths - Contacto jur&iacute;dico</title>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">

<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">

<style>
	#datatable-buttons th{text-align:center}
	#datatable-buttons th:nth-child(1),#datatable-buttons th:nth-child(5),
	#datatable-buttons th:nth-child(7){max-width:20px}
	.path{width:90%}
	.pathhelp{font-size:24px;position:relative;top:7px}
	.pathhelptip{display:none;position:absolute;margin:10px;padding:10px;
		-Webkit-border-radius:13px;-moz-border-radius:13px;border-radius:13px;
		-webkit-box-shadow:0 0 15px #aaa;box-shadow:0 0 15px #aaa;
		border:1px solid #000;background-color:#fff;color:#000;cursor:pointer;z-index:2}
	.pathhelptip::before{content:'';display:block;position:absolute;width:0;height:0;
	    left:40px;bottom:100%;border:10px solid transparent;border-bottom-color:#000}
	.pathhelptip:after{content:'';display:block;position:absolute;width:0;height:0;
	    left:41px;bottom:100%;border:9px solid transparent;border-bottom-color:#fff}
	#forcebase{margin-right:10px;cursor:pointer}
	.pathslist{max-height:50vh;min-height:230px;overflow:auto}
</style>

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row" style="margin-bottom:30px">
				<div class="col-sm-12">
					<h4 class="page-title">Correcci&oacute;n de path-url</h4>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-10">
					<div class="inlineflex path">
						<label for="baseurl" class="supLlb">URL base actual real</label>
						<input type="text" class="form-control c39c" id="baseurl" value="${dbPath}" style="background-color:#fefefe"
							title="Indicar ruta absoluta hasta la carpeta 'doctos'" placeholder="myurl/doctos/">
					</div>
					<div class="inlineflex pathhelp">
						<i class="material-icons asLink">help_outline</i>
					</div>
					<p class="pathhelptip">
						Path configurado como base para almacenar los documentos. El sistema analizar&aacute; las rutas de archivos en DB y mostrar&aacute; aquellas rutas que sean diferentes a la base.
					</p>
				</div>
				<div class="col-sm-2">
					<button type="button" id="searchurls" class="btn btn-primary waves-effect waves-light"
						title="Encontrar URLs que no contengan la URL base.">Analizar URLs</button>
				</div>
			</div>
			<div class="row" style="margin-bottom:30px">
				<div class="col-sm-12">
					<label>
						<input type="checkbox" id="forcebase" checked>
						Forzar correcci&oacute;n de registros utilizando esta ruta
					</label>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-2"><p>Resultados</p></div>
				<div class="col-sm-7 tar"><input type="checkbox" id="allpaths"
					onchange="toggleUrlChecks(this);" style="margin:0 10px 0 5px">
					<label for="allpaths" class="asLink">Seleccionar todos</label>
				</div>
				<div class="col-sm-3 tar">
					<button type="button" id="changesToFixUrl" class="btn btn-default waves-effect waves-light">
						Corregir seleccionados <span id="totFiles"></span>
					</button>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="card-box table-responsive pathslist">
						<div class="table-subcontrols">
							<button type="button" id="btnfilter" class="btn btn-filter trn2ms" title="<fmt:message key="text.filtersbycolumns" />"
								onclick="$('#datatable-buttons thead tr:eq(1)').toggle();"><span class="glyphicon glyphicon-filter"></span> 
							</button>
							<button id="clearfilters" class="btn clearfilters trn2ms" title="<fmt:message key="text.clearfiters" />"
								onclick="cleanTableFilters(this)"><span class="glyphicon glyphicon-filter"></span><i class="material-icons">close</i>
							</button>
						</div>
						<table id="datatable-buttons" data-order='[[3,1,"asc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Cambiar</th>
									<th>Path</th>
									<th>Nombre de archivo</th>
									<th>Tipo</th>
									<th>Info</th>
									<th>Acci&oacute;n</th>
									<th>Ir</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="resources/assets/js/jquery.min.js"></script>

<a class="btnScrollUp inlineblock blackCircle trn3ms"><i class="material-icons">&#xe316;</i></a>

<script src="resources/assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.buttons.min.js"></script>
<script src="resources/assets/plugins/datatables/buttons.bootstrap.min.js"></script>
<script src="resources/assets/plugins/datatables/jszip.min.js"></script>
<script src="resources/assets/plugins/datatables/pdfmake.min.js"></script>
<script src="resources/assets/plugins/datatables/vfs_fonts.js"></script>
<script src="resources/assets/plugins/datatables/buttons.html5.min.js"></script>
<script src="resources/assets/plugins/datatables/buttons.print.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.fixedHeader.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.keyTable.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.responsive.min.js"></script>
<script src="resources/assets/plugins/datatables/responsive.bootstrap.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.scroller.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.colVis.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.fixedColumns.min.js"></script>
<script src="resources/assets/pages/datatables.init.js"></script>

<!-- Modal-Effect -->
<script src="resources/assets/plugins/custombox/js/custombox.min.js"></script>
<script src="resources/assets/plugins/custombox/js/legacy.min.js"></script>

<script src="resources/assets/js/i18n_AJ.js"></script>

<script>
	function searchurls(e){
		var baseurl=$('#baseurl').val();
		if(baseurl.indexOf('doctos')<0){
			swal(i18n('msg_error'),'Es necesario incluir la carpeta "doctos".',"error");
			return;
		}
		$.ajax({
			type:"POST",
			url:ctx+"/searchUrltoFix",
			data:"baseUrl="+baseurl,
			async:false,
			dataType:'JSON',
			success:function(data){
				var info=data.urls,tabid='#datatable-buttons';
				$(tabid+' tbody').remove();
				if(info.length>0){
					var tablelist='';
					for(i=0;i<info.length;i++){
						var c=info[i].catalogtype,folderArr=(info[i].path).split('/');
						var catType=c=='1'?'Juicios':c=='2'?'Apelaciones':c=='3'?'Amparos':c=='4'?'Amparos indirectos'
								:c=='5'?'Recursos':c=='6'?'Clientes':c=='7'?'Movimientos':'',
							catPage=['','juicios','apelaciones','protections','indprotections','resources','clients',''],
							folders=['','Juicios','Apelaciones','Amparos','AmparosInd','Recursos','images','Movimientos'];
						var pg=catPage[c];
						for(f=0;f<folderArr.length;f++)
							if(folderArr[f]==folders[c]){
								id=folderArr[f+1];
								break;
							}
						//location.href = ctx+'/'+pg+'.jet?language='+ getLanguageURL()+'&clid='+id;
						var gopage=ctx+'/'+pg+'?language='+ getLanguageURL();
	
						var status='';
						tablelist+='<tr><td class="tac"><input type="checkbox" id="'+info[i].uploadfileid
								+'" data-ids="'+info[i].uploadfileid+'" onchange="recalcTotFiles();"></td>'
							+'<td>'+info[i].path+'</td>'
							+'<td>'+info[i].filename+'</td>'
							+'<td class="tac">'+catType+'</td>'
							+'<td class="tac"><span class="asLink" onclick="searchUrlInfo('+info[i].idregister
								+');" title="Informaci&oacute;n adicional"><i class="material-icons">info_outline</i></span></td>'
							+'<td class="tac"><span data-status="'+info[i].uploadfileid+'" style="cursor:default">'+status+'</span></td>'
							+'<td class="tac"><a href="'+gopage+'" target="_blank" title="Ir al módulo inicial '
								+catType+'" data-url="'+info[i].path+'"><i class="fa fa-external-link"></i></a></td>'
							+'</tr>';
					}
					$(tabid).append(tablelist);
				}
			},
			error:function(er){
				swal(i18n('msg_warning'),'No se encontraron datos o la sesi\u00f3n ha caducado',"error");
			}
		});
	};
	
	function searchUrlInfo(id){
		$.ajax({
			type:"POST",
			url:ctx+"/searchUrlInfo",
			data:"id="+id,
			async:false,
			dataType:'JSON',
			success:function(data){
				if($.isEmptyObject(data)){
					swal(i18n('msg_warning'),'No se encontraron datos o la sesi\u00f3n ha caducado',"error");
					return;
				}
				var info="Origen: "+data.origen+"\n"
					+"Expediente: "+data.expediente+"\n"
					+"Dato adicional: "+data.otros;
				swal(i18n("msg_information"),info,"info");
			},
			error:function(er){
				swal(i18n('msg_warning'),'No se encontraron datos o la sesi\u00f3n ha caducado',"error");
			}
		});
	};
	
	function changesToFixUrl(e){
		var param="",baseurl=$('#baseurl').val(),allids=$('[data-ids]:checked'),ids='',err='',forcebase=$('#forcebase').is(':checked');
		if(baseurl.indexOf('doctos')<0){
			swal(i18n('msg_error'),'Es necesario incluir la carpeta "doctos".',"error");
			return;
		}
		for(i=0;i<allids.length;i++)
			ids+=allids[i].id+',';
		ids=ids.replace(/.$/,'');
		if(ids=='')
			err='No ha seleccionado un registro a corregir.';
		else if(baseurl=='')
			err='Favor de indicar la URL base, esta será establecida para corregir aquellas que esten incorrectas.';
		if(err!=''){
			swal(i18n('msg_error'),err,"error");
			return;
		}
		param="baseUrl="+baseurl+"&ids="+ids+"&forcebase="+forcebase;
		$.ajax({
			type:"POST",
			url:ctx+"/changesToFixUrl",
			data:param,
			async:false,
			dataType:'JSON',
			success:function(data){
				if($.isEmptyObject(data)){
					swal(i18n('msg_warning'),'No se pudo realizar ningun cambio',"error");
					return;
				}
				var nrow=(data.actions).split("|"),c='',
					infostatus=['Registro previamente corregido','Registro corregido','Registro no corregido',
			            'Archivo y registro corregidos','Movido pero sin cambios en registro'],
					infotitle=['Registro ya fue corregido previamente, se recomienda dar click al botón \'Analizar URLs\' para actualizar esta lista.',
						'La ruta o archivo no existían en disco, el path en DB se dejó en blanco.',
						'La ruta o archivo no existían en disco, pero no se pudo limpiar el path en DB.',
						'El archivo se encontró en una ruta distinta a la \'url-base\' y fue movido correctamente.',
						'El archivo se encontró en una ruta distinta a la \'url-base\' y fue movido correctamente pero no se pudo cambiar el path en DB.'];
				for(i=0;i<nrow.length;i++){
					var ndata=nrow[i].split("=");
					var id=ndata[0],st=ndata[1];
					c=(st=='1'||st=='3')?'green':(st=='2'||st=='4')?'#daa520':'#797979';
					$('[data-status="'+id+'"]').css('color',c);
					$('[data-status="'+id+'"]').html(infostatus[st]);
					$('[data-status="'+id+'"]').prop('title',infotitle[st]+' (ID='+id+')');
					document.getElementById(id).checked=false;
				}
				swal(i18n('msg_success'),'Proceso terminado.',"success");
			},
			error:function(er){
				swal(i18n('msg_warning'),'No se encontraron datos o la sesi\u00f3n ha caducado',"error");
			}
		});
	};
	
	function toggleUrlChecks(e){
		var checkBoxes=$('[data-ids]'),status=e.checked;
		checkBoxes.prop('checked',status);
		recalcTotFiles();
	};
	
	function recalcTotFiles(){
		var allids=$('[data-ids]:checked');
		var tot=allids.length<1?'':'('+allids.length+')';
		$('#totFiles').html(tot);
	};

	$('#searchurls').on('click', function() {
		searchurls(this);
	});

	$('#changesToFixUrl').on('click', function() {
		changesToFixUrl(this);
		$('#totFiles').html('');
	});
	
	$('.pathhelptip, .pathhelp i').on('click', function() {
		$('.pathhelptip').fadeToggle(250);
	});
</script>


<script type="text/javascript">
	jQuery(document).ready(function($){
		let dtId1='#datatable-buttons', txtfnd=i18n('msg_search');
		let dtId0=dtId1.replace(/^[#\.]/,''), rx=new RegExp(txtfnd, 'ig');
		$(dtId1+' thead tr').clone(true).appendTo(dtId1+' thead');
		$(dtId1+' thead tr:eq(1)').css('display','none');
		$(dtId1+' thead tr:eq(1) th').each( function (i) {
			var title=i18n('msg_filter_by') + ' ' + $(this).text();
			$(this).html('<input type="text" class="inputFilter" name="filterby" placeholder="' + title + '">');
			$('input', this).on('keyup input paste change delete cut clear', function (){
				table=$(dtId1).DataTable();
				if(table.column(i).search() !== this.value){
					table.column(i).search(this.value).draw();
					this.focus();
					var allfilters=$('[name=filterby]'),hasfilters=!1;
					for(f=0; f<allfilters.length;f++)
						if(this.value!=''){
							hasfilters=!0;
							break;
						}
					hasfilters?$("#clearfilters").addClass("clearfilters-active")
						:$("#clearfilters").removeClass("clearfilters-active");
				}
			});
		});

		$(dtId1).DataTable({
			"lengthMenu":[[5, 10, 25, 50, -1],[5, 10, 25, 50, i18n('msg_all')]],
			paging:true,
			pageLength:10,
			scrollCollapse:true,
			autoWidth:true,
			searching:true,
			orderCellsTop: true,
			fixedHeader: true,
			pagingType:"simple_numbers",'dom':
				'<"col-sm-3"f><tr><"row"<"col-xs-12 col-sm-12 col-md-4 fs12"i>'
				+'<"col-xs-12 col-sm-6 col-md-3 xs-center-items"l>'
				+'<"col-xs-12 col-sm-6 col-md-5 xs-center-items"p>>',
			columnDefs:[{'width':'auto','targets':'_all'}]
		});
	
		replaceTextInHtmlBlock($(dtId1+'_filter label'), txtfnd+':', 
	    	'<i class="material-icons searchClIcon" onclick="$(\''
	  			+dtId1+'_filter [aria-controls='+dtId0+']\').focus()">&#xe8b6;</i>');
	    $(dtId1+'_filter [aria-controls='+dtId0+']').attr('placeholder', txtfnd);
		$(dtId1+'_filter label').html().replace(rx,'');
		$(dtId1+'_filter input')[0].focus();
	});
	$('.modal-demo').on('hide.bs.modal', function(){
		clearTemp();
	});
</script>