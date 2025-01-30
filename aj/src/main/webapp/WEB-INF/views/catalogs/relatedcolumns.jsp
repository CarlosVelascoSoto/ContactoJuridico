<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.List"%>

<!-- i18n...-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages" />
<html lang="es-MX">
<meta charset="utf-8">
<meta name="language" content="Spanish">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

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
    .asLink:hover{color:#1B71D4}
    .btn-icon-base{border:1px solid transparent;background-color:transparent;color:#337ab7}
    .btn-icon-base:hover{color:#337AB7}
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
	.table-hover tbody tr:hover td, .table-hover tbody tr:hover th {background-color:#e6f3ff}

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

	#add-manage-columnsdata,#edit-manage-columnsdata tbody tr:hover{-webkit-box-shadow:0 0 5px #ccc;box-shadow:0 0 5px #ccc;}
	.custombox-modal-container{margin-top:50px !important}
	[data-customcol=add],[data-customcol=edit]{display:none;padding:15px 20px 15px 10px;border:1px solid #aaa}
	#setway{display:none}
	#addgroupsample, #editgroupsample{padding:20px 0 10px 0;-webkit-border-radius:5px;-moz-border-radius:5px;
		border-radius:5px;border:1px solid #444;background-color:#fbfbfb}
	#edit-relcol-modal{overflow:auto}
	.sub-modal{
		position:absolute;
		width:100%;
		height:100vh;
		max-height:100%;
		top:5.5%;
		bottom:0;
		z-index:10012
	}
	.sub-modal>div{
		background-color:#fff;
		-webkit-box-shadow:0 0 15px #444;
		box-shadow:0 0 15px #444;
	}
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
 							<a href="#" id="addNewRelatedColumn" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.relatedcolumns" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[1,"desc"]]' class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th><fmt:message key="text.columnname" /></th>
									<th><fmt:message key="text.alias" /></th>
									<th><fmt:message key="text.toget" /></th>
									<th><fmt:message key="text.message" /></th>
									<th><fmt:message key="text.edit" /></th>
									<th><fmt:message key="text.delete" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="d" items="${data}">
								<tr>
									<td>${d.tipojuicio}</td>
									<c:set var="hasTJAcc" scope="page" value="${false}" />
									<c:forEach var="tjacc" items="${tjacc}">
										<c:if test="${tjacc.accionid==d.accionid}">
											<td>${tjacc.descripcion}</td>
											<td>
												<c:forEach var="m" items="${matter}">
													<c:if test="${m.materiaid==tjacc.materiaid}">${m.materia}</c:if>
													<c:set var="hasTJAcc" scope="page" value="${true}" />
												</c:forEach>
											</td>
										</c:if>
									</c:forEach>
									<td class="tac">
										<a href="#" id="${d.tipojuicioid}" data-toggle="modal" data-target="#edit-relcol-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsRelCol(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									<td class="tac">
										<a href="#" class="table-action-btn" id="${d.tipojuicioid}" onclick="deleteTrialType(id);"><i class="md md-close"></i></a>
									</td>
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
<div id="add-relcol-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;color:#444">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key="text.relatedcolumns" /></h4>
	<div class="custom-modal-text text-left p-t-0">
		<div class="panel-body">
			<form id="formaddtrialtype">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnadd" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnadd').toggle();">&times;</a>
						<p id="putErrorOnadd"></p>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="addcolname" class="supLlb"><fmt:message key='text.columnname' /></label>
								<input type="text" class="form-control c39c" id="addcolname" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="addalias" class="supLlb"><fmt:message key='text.alias' /></label>
								<input type="text" class="form-control c39c" id="addalias" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12"><h5 class="m-b-10"><fmt:message key='text.message' /></h5> (<fmt:message key='text.key' />)</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="addmsgjs" class="supLlb">JS</label>
								<input type="text" class="form-control c39c" id="addmsgjs" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="addmsgjsp" class="supLlb">JSP</label>
								<input type="text" class="form-control c39c" id="addmsgjsp" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12"><h5 class="m-b-10"><fmt:message key='text.description' /></h5> 1</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="addfromTable" class="supLlb"><fmt:message key='text.fromtable' /></label>
								<input type="text" class="form-control c39c" id="addfromTable" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="addfromCol" class="supLlb"><fmt:message key='text.fromcolumn' /></label>
								<input type="text" class="form-control c39c" id="addfromCol" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="addgetdescr" class="supLlb"><fmt:message key='text.description' /></label>
								<input type="text" class="form-control c39c" id="addgetdescr" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12"><h5 class="m-b-10"><fmt:message key='text.secondtable' /></h5></div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="addfromTable2" class="supLlb"><fmt:message key='text.fromtable' /></label>
								<input type="text" class="form-control c39c" id="addfromTable2" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="adddata2get2" class="supLlb"><fmt:message key='text.fromcolumn' /></label>
								<input type="text" class="form-control c39c" id="adddata2get2" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="addgetdescr2" class="supLlb"><fmt:message key='text.description' /></label>
								<input type="text" class="form-control c39c" id="addgetdescr2" autocomplete="off">
							</div>
						</div>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addRelCol(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addRelColCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-relcol-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-relcol-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="data.update" /></h4>
				<input type="hidden" id="editRelColId">
			</div>
			<div class="modal-body">
				<form>
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnedit" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnedit').toggle();">&times;</a>
							<p id="putErrorOnedit"></p>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editcolname" class="supLlb"><fmt:message key='text.columnname' /></label>
									<input type="text" class="form-control c39c" id="editcolname" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editalias" class="supLlb"><fmt:message key='text.alias' /></label>
									<input type="text" class="form-control c39c" id="editalias" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12"><h5 class="m-b-10"><fmt:message key='text.message' /></h5> (<fmt:message key='text.key' />)</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-md-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editmsgjs" class="supLlb">JS</label>
									<input type="text" class="form-control c39c" id="editmsgjs" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-md-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editmsgjsp" class="supLlb">JSP</label>
									<input type="text" class="form-control c39c" id="editmsgjsp" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12"><h5 class="m-b-10"><fmt:message key='text.description' /></h5> 1</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editfromTable" class="supLlb"><fmt:message key='text.fromtable' /></label>
									<input type="text" class="form-control c39c" id="editfromTable" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editfromCol" class="supLlb"><fmt:message key='text.fromcolumn' /></label>
									<input type="text" class="form-control c39c" id="editfromCol" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editgetdescr" class="supLlb"><fmt:message key='text.description' /></label>
									<input type="text" class="form-control c39c" id="editgetdescr" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12"><h5 class="m-b-10"><fmt:message key='text.secondtable' /></h5></div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-md-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editfromTable2" class="supLlb"><fmt:message key='text.fromtable' /></label>
									<input type="text" class="form-control c39c" id="editfromTable2" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-md-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editdata2get2" class="supLlb"><fmt:message key='text.fromcolumn' /></label>
									<input type="text" class="form-control c39c" id="editdata2get2" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editgetdescr2" class="supLlb"><fmt:message key='text.description' /></label>
									<input type="text" class="form-control c39c" id="editgetdescr2" autocomplete="off">
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateRelCols();" class="btn btn-default waves-effect waves-light">
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

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script>
<script src="resources/assets/js/relatedcolumns.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
</script>