<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- i18n...-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages" />
<html lang="es-MX">
<meta charset="utf-8">
<meta name="language" content="Spanish">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/basic.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/dropzone.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

<style>
	.dt-buttons.btn-group a{margin-right:10px;}

	.inlineflex{display:-webkit-inline-flex;display:-webkit-inline-box;display:-moz-inline-box;display:-ms-inline-flexbox;display:inline-flex}
	.tac{text-align:center}
	.dnone{display:none}
	.trn2ms{-webkit-transition:0.2s;-moz-transition:0.2s;-ms-transition:0.2s;-o-transition:0.2s;transition:0.2s}
    .c39c{color:#39c}
    .asLink{cursor:pointer}
    .asLink:hover,.az{color:#1B71D4}
	.sweet-overlay{z-index:10010 !important}
	.showSweetAlert{z-index:10011 !important}
	.capitalize{text-transform:capitalize}
	#proceedings{
        background-color: #FFFFFF;
	    border: 1px solid #E3E3E3;
	    border-radius: 4px;
	    padding: 7px 12px;
	    height: 38px;
	    max-width: 100%
	}
	.supLlb{position:absolute;font-weight:400;font-size:12px;top:-9px;left:20px;padding:0 5px;background-color:#fff;z-index:4}
	.containTL{
		position:absolute;
		width:100%;
		max-height:160px;
		top:40px;
		overflow:auto;
		background-color:#fff;
		-webkit-box-shadow:0 0 15px #ccc;
		box-shadow:0 0 15px #ccc;
		z-index:5;
	}
	.closecontainTL{
		position:-webkit-sticky !important;position:sticky !important;
		width:30px;height:30px;top:5px !important;right:5px !important;
		-webkit-border-radius:50%;-moz-border-radius:50%;border-radius:50%;
		-webkit-box-shadow:0 0 5px #444;box-shadow:0 0 5px #444;
		background-color:#fff !important;color:#000 !important
	}
	.closecontainTL:hover{-webkit-box-shadow:0 0 5px #000;box-shadow:0 0 5px #000;}
	.tablelist{width:100%;border:1px solid #ddd;border-collapse:collapse;cursor:pointer}
	.tablelist th, .tablelist td{text-align:left;padding:5px}
	.tablelist tr{border-bottom:1px solid #ddd;cursor:pointer}
	.tablelist tr.header, .tablelist tr:hover{background-color:#f1f1f1;}
	.asField{margin-bottom:30px;padding:10px;border-radius:5px;border:1px solid #ccc}

	/*DropDown (ini)*/
	select{-webkit-appearance:none;moz-appearance:none;appearance:none}
	.select-wrapper{display:table-cell; background-color:#FFF;}
	.select-wrapper:after{
		content:"\25bc";
		position:absolute;
		top:13px;
		right:10px;
		font-size:14px;
		color:#39c;
		z-index:2;
	}
	.ddsencillo, .select-wrapper{
		position:relative;
		display:inline-block;
		height:42px;
		border-radius:7px;
		background-color:#FFF;
		cursor:pointer;
	}
	.ddsencillo::-ms-expand {display: none;}
	.ddsencillo{
		width:100%;
		padding:10px 40px 10px 10px;
		-webkit-appearance:none;
		-moz-appearance:none;
		-ms-appearance:none;
		appearance:none;
		border:1px solid #bbb;
		background-color:transparent;
		z-index:3;
	}/*DropDown (fin)*/
	/*DropDown Filter (ini)*/
	.listfiltersel{width:100%;padding-right:20px}
	.iconlistfilter{font-size:25px;position:absolute;max-height:300px;top:5px;right:13px;overflow:auto;
		background-color:#fff;color:#39c;cursor:pointer}
	.iconlistfilter:focus{background-color:lavender}
    .ddListImg{position:absolute;display:none;width:100%;max-height:30vh;min-height:100px;top:40px;overflow-y:auto;
    	-webkit-box-shadow:0 0 15px #aaa;box-shadow:0 0 15px #aaa;background-color:#fff;z-index:21}
    .ddListImg li{display:-webkit-box;display:-moz-box;display:-ms-flexbox;display:-webkit-flex;display:flex;
      border-bottom:1px solid #dedede;-webkit-align-items:center;align-items:center;min-height:54px;
      background-color:#fff;cursor:pointer}
    .ddListImg li:hover,.ddListImg li.selected{background-color:#cce6ff}
    .ddListImg img{width:34px;margin:5px 10px;vertical-align:middle}
    .noimgOnList{padding:0;min-height:34px}
    .noimgOnList li{min-height:34px;padding:0 10px}
    .addnewoplist{-webkit-justify-content:center;justify-content:center}
    /*DropDown Filter (fin)*/
	.w100p{width:100%}

/*Menú personalizado (ini)*/
      .menu {
        /*width: 120px;  //Se puede personalizar el ancho del menú y/o el tamaño máximo o mínimo*/
        position: fixed;display: none;font-family: "Roboto", san-serif;margin: 0px;
        -webkit-box-shadow: 0 0px 15px #ddd;box-shadow: 0 0px 15px #ddd;background-color:#fff;
        -webkit-transition:all .2s ease;-moz-transition:all .2s ease;-ms-transition: .2s ease;
        -o-transition:all .2s ease;transition: 0.2s display ease-in;z-index: 1
      }
      .menu .menu-options{margin: 5px;padding: 10px 0;list-style: none;color:#0066aa;z-index: 1}
      .menu .menu-options .menu-option{
        font-weight: 500;font-size: 14px;padding: 10px 40px 10px 20px;
        -webkit-transition: 350ms;-moz-transition: 350ms;-ms-transition: 350ms;-o-transition: 350ms;transition: 350ms;
        z-index: 1;cursor: pointer
      }
      .menu .menu-options .menu-option:hover{background: yellowgreen}
      /*Menú personalizado (fin)*/

      /*Menú contextual (ini)*/
      .addMinus{display: none;width:20px;height:20px;margin:0px 5px;text-align:center;
        -webkit-border-radius:50%;-moz-border-radius:50%;-ms-border-radius:50%;-o-border-radius:50%;border-radius:50%;
        -webkit-box-shadow:1px 5px 10px #444;box-shadow:1px 5px 10px #444;
        -webkit-transition:350ms;-moz-transition:350ms;-ms-transition:350ms;-o-transition:350ms;transition:350ms;
        color:#fff;cursor:pointer
      }
      .addOp{background-color: #39c}
      .addOp:hover{background-color: skyblue}
      .substractOp{background-color: crimson}
      .substractOp:hover{background-color: tomato}
      /*Menú contextual (fin)*/
      
	.file-dropzone{border: 1px dashed #ddd;min-height: 100px;height: auto;}
	.dz-error-mark svg,.dz-success-mark svg{width:24px;height:24px;}
	.dz-image .link{display:-moz-inline-grid;display:-ms-inline-grid;display:inline-grid;z-index:1}
	.dropzone .dz-preview .dz-details{opacity:0.8}
	.dropzone .dz-preview .dz-image img{width:90%;height:100%;max-height:120px;margin:0 auto;z-index:0;
		-o-object-fit:contain;object-fit:contain;cursor:pointer;
		-webkit-transition:0.1s;-moz-transition:0.1s;-ms-transition:0.1s;-o-transition:0.1s;transition:0.1s}
	.dropzone .dz-preview .dz-progress,
	.dropzone .dz-preview:not(.dz-processing) .dz-progress{min-height:20px;letter-spacing:2px}
	.dropzone .dz-preview .dz-error-message {top: 150px!important;}
	.dropzone .dz-preview.dz-image-preview .dz-details{position:relative;padding:0;background-color:rgba(0,0,0,0.1)}
	.dropzone .dz-preview .dz-details .dz-filename span, .dropzone .dz-preview .dz-details .dz-size span{background-color:transparent}

	#catalog-modal{overflow:auto}
	#catalogList{width:100%;max-width:280px}
	.boxprvwimport{max-height:270px;border:1px solid #000;overflow:auto}
	.previewimport{background-color:#eee}
	.previewimport thead, .previewimport tbody{background-color:#eee}
	.previewimport, .previewimport th, .previewimport tr, .previewimport td{
		padding:1px 10px; border:1px solid #000;border-collapse:collapse
	}
	.previewimport th, .previewimport td{background-color:#fff}
	#external-modal .modal-dialog{-webkit-box-shadow:0 0 15px #444;box-shadow:0 0 15px #444}
	.stupload{text-align:center;cursor:help}
</style>
<div class="menu">
	<ul class="menu-options">
		<li class="menu-option" id="liMenuOp1"><fmt:message key="text.edit" /></li>
		<li class="menu-option" id="liMenuOp2">Agregar columna</li>
		<li class="menu-option" id="liMenuOp3">Eliminar fila</li>
		<li class="menu-option" id="liMenuOp4"></li>
		<li class="menu-option" id="liMenuOp5">Opci&oacute;n 5</li>
	</ul>
</div>
<!--==============================================================-->
<!-- Start right Content here -->
<!--==============================================================-->
<div class="content-page">
	<div class="content">
		<div class="container">
			<form id="formUploadCatalogs">
				<div class="row">
					<div class="col-sm-12">
						<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
	 						<div class="btn-group pull-right m-t-15">
	 							<a href="#" id="importFile" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
									<fmt:message key="text.import" />
								</a>
							</div>
						</c:if>
						<h4 class="page-title capitalize"><fmt:message key="text.importcatalogs" /></h4>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="card-box table-responsive">
							<table id="datatable-buttons" data-order='[[1,"desc"]]' class="table table-striped table-bordered"></table>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="hidden-xs" style="height: 400px;"></div>
	</div>
</div>
<footer class="footer text-right">
	<fmt:message key="text.copyright" />
</footer>

<!-- Modal -->
<div id="catalog-modal" class="modal fade" role="dialog" aria-labelledby="catalog-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog" style="width:80vw">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="text.import" /></h4>
			</div>
			<div class="modal-body">
				<form id="formsnnew">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnUpload" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnUpload').toggle();">&times;</a>
							<p id="putErrorOnUpload"></p>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-5">
								<label for="catalogList" class="supLlb"><fmt:message key='text.catalog' /></label>
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control listfiltersel" onblur="getHeaders();" placeholder="<fmt:message key="text.select" />" >
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="catalogList">
										<c:forEach var="c" items="${catalog}">
											<c:set var="headercol" value="${fn:split(c.value,',')}" />
											<li data-value="${headercol[1]}" title="${headercol[0]}">${headercol[0]}</li>
										</c:forEach>
										<li></li>
									</ul>
								</div>
							</div>
							<div class="col-xs-12 col-sm-7">
								<p>Seleccionar un archivo.</p>
								<input type="file" id="selectedFile"><br>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<p>Delimitadores</p>
								<label><input type="radio" id="commaDelimit" value="," name="delimiters" checked> Coma</label><br>
								<label><input type="radio" id="semicolonDelimit" value=";" name="delimiters"> Punto y coma</label><br>
								<label><input type="radio" id="spaceDelimit" value=" " name="delimiters"> Espacio</label><br>
								<label><input type="radio" id="tabularDelimit" value="\t" name="delimiters"> Tabular</label><br>
								<label><input type="radio" id="otherDelimit" value="|" name="delimiters"> Otro:</label>
								<label><input type="text" id="personalDelimit" value="|"></label>
							</div>
						</div>
						<div class="row">
							<h4>Vista previa <span style="font-size:12px;color:steelblue">(Has coincidir las columnas)</span></h4>
							<div id="originalImport" class="dnone"></div>
							<div class="col-xs-12 boxprvwimport">
								<table id="previewimport" class="previewimport">
									<thead></thead>
									<tbody></tbody>
								</table>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<label>Iniciar importación desde la fila: <input type="number" id="startFromRow" value="1" min="1"></label>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return startImport();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.start" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="external-modal" class="modal fade" role="dialog" aria-labelledby="external-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<form id="formsnnew">
					<div class="form-group">
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<label for="externalList1" class="supLlb"><fmt:message key='text.externalcatalog' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="externalList1" onchange="getExternalHeaders();"></select>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="externalList2" class="supLlb"><fmt:message key='text.requiredColumn' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="externalList2"></select>
								</div>
							</div>
						</div>
						<div class="row" style="margin-top:20px">
							<div class="col-xs-12 col-sm-6">
								<label for="externalList3" class="supLlb"><fmt:message key='text.showcolinfo' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="externalList3"></select>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="externalList4" class="supLlb"><fmt:message key='text.defaultvalue' /></label>
								<div class="select-wrapper w100p" id="resultList">
									<select class="form-control ddsencillo c39c" id="externalList4"></select>
								</div>
							</div>
						</div>
						<input type="hidden" id="idxCol">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return applyCells();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.ok" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="reedit-modal" class="modal fade" role="dialog" aria-labelledby="reedit-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="oldvalue" class="supLlb">Valor anterior</label>
								<input type="text" class="form-control c39c" id="oldvalue" readonly>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="newvalue" class="supLlb">Nuevo valor</label>
								<input type="text" class="form-control c39c" id="newvalue" autocomplete="off">
							</div>
						</div>
					</div>
					<input type="hidden" id="idxRow">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return setNewValue();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.ok" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->

<!--==============================================================-->
<!-- End Right content here -->
<!--==============================================================-->
<script src="resources/assets/js/jquery.min.js"></script>
<!-- Modal-Effect -->
<script src="resources/assets/plugins/custombox/js/custombox.min.js"></script>
<script src="resources/assets/plugins/custombox/js/legacy.min.js"></script>

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
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/customDropZone.js"></script>
<!-- script src="${pageContext.request.contextPath}/web-resources/libs/sweet-alert2/sweetalert2.min.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script> -->
<script src="resources/assets/js/importCatalogs.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});

	var target = document.getElementById("uploadXUploadCatalogs"), texthtml="";

    target.addEventListener("dragover", function(event) {
        event.preventDefault();
    }, false);
    target.addEventListener("drop", function(event) {
        event.preventDefault();
        var i = 0, files = event.dataTransfer.files, len = files.length;
        for (; i < len; i++){
            texthtml+="Filename: " + files[i].name + "<br>";
            texthtml+="Type: " + files[i].type + "<br>";
            texthtml+="Size: " + files[i].size + " bytes" + "<br><br>";
        }
        document.getElementById("getdragdropfiles").innerHTML=texthtml;
    }, false);
</script>