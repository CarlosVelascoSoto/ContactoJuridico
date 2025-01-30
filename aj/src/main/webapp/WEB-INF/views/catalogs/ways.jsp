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

	/*DropDown Filter (ini)*/
	.listfiltersel{width:100%;padding-right:20px}
	.iconlistfilter{font-size:25px;position:absolute;max-height:300px;top:5px;right:13px;overflow:auto;
		background-color:#fff;color:#39c;cursor:pointer}
	.iconlistfilter:focus{background-color:lavender}
    .ddListImg{position:absolute;display:none;width:100%;max-height:30vh;min-height:100px;overflow-y:auto;
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
</style>
<!--==============================================================-->
<!-- Start right Content here -->
<!--==============================================================-->
<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row"><!-- Page-Title -->
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addNewWay" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.ways" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[1,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.way" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="d" items="${data}">
								<tr>
									<td>${d.via}</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${d.viaid}" data-toggle="modal" data-target="#edit-way-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsToEdit(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" id="${d.viaid}" onclick="deleteWay(id);"><i class="md md-close"></i></a>
									</td>
									</c:if>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="hidden-xs" style="height: 400px;"></div>
	</div>
</div>
<footer class="footer text-right">
	<fmt:message key="text.copyright" />
</footer>

<!-- Modal -->
<div id="way-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form id="formsnnew">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnAdd" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAdd').toggle();">&times;</a>
						<p id="putErrorOnAdd"></p>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="way" class="supLlb"><fmt:message key='text.way' /></label>
								<input type="text" class="form-control c39c" id="way" autocomplete="off">
							</div>
						</div>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addWay(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addWayCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-way-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-way-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="way.update" /></h4>
				<input type="hidden" id="edWayId">
			</div>
			<div class="modal-body">
				<form id="formsnedit">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEdit" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEdit').toggle();">&times;</a>
								<p id="putErrorOnEdit"></p>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="edWay" class="supLlb"><fmt:message key='text.way' /></label>
								<input type="text" class="form-control c39c" id="edWay" autocomplete="off">
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateWay();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.close" />
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
<script src="${pageContext.request.contextPath}/web-resources/libs/sweet-alert2/sweetalert2.min.js"></script>
<script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script>
<script src="resources/assets/js/ways.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
</script>