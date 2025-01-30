<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.aj.utility.UserDTO"%>
<%	UserDTO userDto=(UserDTO) request.getSession().getAttribute("UserDTO");
	int role=userDto.getRole();%>
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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web-resources/libs/sweet-alert2/sweetalert2.min.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">

<style>
/*	.containTL{
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
	.asField{margin-bottom:30px;padding:10px;border-radius:5px;border:1px solid #ccc}*/
	#court-modal,#edit-court-modal{overflow:auto}
	.btnaddCircle{font-size:24px;position:absolute;width:40px;height:40px;top:0;right:30px;
		-webkit-border-radius:25px;-moz-border-radius:25px;border-radius:25px;
		-webkit-justify-content:center;justify-content:center;-webkit-align-items:center;align-items:center;
		-webkit-box-shadow:0px 0px 10px 3px #ddd;box-shadow:0px 0px 10px 3px #ddd;
		background-color:yellowgreen;color:#fff;cursor:pointer}
	.uppermodal{z-index:10010}
</style>
<!--==============================================================-->
<!-- Start right Content here -->
<!--==============================================================-->
<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row"><!-- Page-Title -->
				<div class="col-xs-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addNewCourt" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.courts" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[1,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.court" /></th>
									<th><fmt:message key="text.city" /></th>
									<th><fmt:message key="text.state" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="d" items="${data}">
								<tr>
									<td>${d.juzgado}</td>
									<c:set var="tds" value="0" />
									<c:forEach var="c" items="${cd}">
										<c:if test="${c.ciudadid==d.ciudadid}">
											<td>${c.ciudad}</td>
											<c:set var="tds" value="${tds + 1}"/>
											<c:forEach var="s" items="${states}">
												<c:if test="${s.estadoid==c.estadoid}"><td>${s.estado}</td></c:if>
												<c:set var="tds" value="${tds + 1}"/>
											</c:forEach>
										</c:if>
									</c:forEach>
									<c:if test="${tds==1}"><td></td></c:if>
									<c:if test="${tds==0}"><td></td><td></td></c:if>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${d.juzgadoid}" data-toggle="modal" data-target="#edit-court-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsToEdit(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" id="${d.juzgadoid}" onclick="deleteCourt(id);"><i class="md md-close"></i></a>
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
<div id="court-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key="text.court"/></h4>
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
								<label for="court" class="supLlb"><fmt:message key='text.court' /></label>
								<input type="text" class="form-control c39c" id="court" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row" style="margin-bottom:15px">
						<div class="col-xs-6 col-sm-2">
							<div class="form-group inlineflex w100p">
								<label for="district" class="inlineflex inputnextrb">
								<input type="radio" class="form-control c39c" id="district" value="d" name="distpart"
									onchange="setDistPart(this,'#boxdistpartnum','');">
								<fmt:message key='text.district' /></label>
							</div>
						</div>
						<div class="col-xs-6 col-sm-2">
							<div class="form-group inlineflex w100p">
								<label for="party" class="inlineflex inputnextrb">
								<input type="radio" class="form-control c39c" id="party" value="p" name="distpart"
									onchange="setDistPart(this,'#boxdistpartnum','');">
								<fmt:message key='text.party' /></label>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4">
							<div class="form-group inlineflex w100p">
								<label for="notdef" class="inlineflex inputnextrb">
								<input type="radio" class="form-control c39c" id="notdef" value="" name="distpart" checked
									onchange="if($(this).prop('checked'))$('#boxdistpartnum').hide();">
								<fmt:message key='text.undefined' /></label>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4" id="boxdistpartnum">
							<div class="form-group inlineflex w100p">
								<label for="distpartnum" class="supLlb"><fmt:message key='text.number' /></label>
								<input type="text" class="form-control c39c" id="distpartnum">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label for="matter" class="supLlb"><fmt:message key='text.matter' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key='text.select' />" >
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="matter" style="top:40px"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label for="citycourt" class="supLlb"><fmt:message key='text.city' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key='text.select' />" >
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="citycourt" style="top:40px"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<h4><fmt:message key='text.judgenames' /></h4>
							<div class="inlineflex btnaddCircle" onclick="addcolumn();">+</div>
						</div>
						<div class="col-xs-12">
							<div class="card-box table-responsive" style="max-height:30vh;min-height:100px;padding:0;overflow:auto">
								<table id="addColumns" data-order='[[0,"desc"]]' class="table table-striped table-bordered table-responsive">
									<thead>
										<tr>
											<th><fmt:message key="text.judge" /></th>
											<th class="tac"><fmt:message key="text.edit" /></th>
											<th class="tac"><fmt:message key="text.delete" /></th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addCourt(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addCourtCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-court-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-court-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="court.update" /></h4>
			</div>
			<div class="modal-body">
				<form id="formsnedit">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEdit" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEdit').toggle();">&times;</a>
								<p id="putErrorOnEdit"></p>
								<input type="hidden" id="editcourtId">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="editcourt" class="supLlb"><fmt:message key='text.court' /></label>
								<input type="text" class="form-control c39c" id="editcourt" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row" style="margin-bottom:15px">
						<div class="col-xs-6 col-sm-2">
							<div class="form-group inlineflex w100p">
								<label for="editdistrict" class="inlineflex inputnextrb">
								<input type="radio" class="form-control c39c" id="editdistrict" value="d" name="editdistpart"
									onchange="setDistPart(this,'#editboxdistpartnum','edit');">
								<fmt:message key='text.district' /></label>
							</div>
						</div>
						<div class="col-xs-6 col-sm-2">
							<div class="form-group inlineflex w100p">
								<label for="editparty" class="inlineflex inputnextrb">
								<input type="radio" class="form-control c39c" id="editparty" value="p" name="editdistpart"
									onchange="setDistPart(this,'#editboxdistpartnum','edit');">
								<fmt:message key='text.party' /></label>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4">
							<div class="form-group inlineflex w100p">
								<label for="editnotdef" class="inlineflex inputnextrb">
								<input type="radio" class="form-control c39c" id="editnotdef" value="" name="editdistpart" checked
									onchange="if($(this).prop('checked'))$('#editboxdistpartnum').hide();">
								<fmt:message key='text.undefined' /></label>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4" id="editboxdistpartnum">
							<div class="form-group inlineflex w100p">
								<label for="editdistpartnum" class="supLlb"><fmt:message key='text.number' /></label>
								<input type="text" class="form-control c39c" id="editdistpartnum">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label for="editmatter" class="supLlb"><fmt:message key='text.matter' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key='text.select' />" >
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editmatter" style="top:40px"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label for="editcitycourt" class="supLlb"><fmt:message key='text.city' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key='text.select' />" >
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editcitycourt" style="top:40px"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<h4><fmt:message key='text.judgenames' /></h4>
							<div class="inlineflex btnaddCircle" onclick="addcolumn();">+</div>
						</div>
						<div class="col-xs-12">
							<div class="card-box table-responsive" style="max-height:30vh;min-height:100px;padding:0;overflow:auto">
								<table id="editColumns" data-order='[[0,"desc"]]' class="table table-striped table-bordered table-responsive">
									<thead>
										<tr>
											<th><fmt:message key="text.judge" /></th>
											<th class="tac"><fmt:message key="text.edit" /></th>
											<th class="tac"><fmt:message key="text.delete" /></th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateCourt();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="column-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" aria-labelledby="column-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog" style="-webbkit-box-shadow:0 0 15px #000;box-shadow:0 0 15px #000;background-color:#fff">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title capitalize"><fmt:message key="button.additem" /> <fmt:message key="text.judge" /></h4>
			<input type="hidden" id="rowColumnsIdx"><input type="hidden" id="rowColAddEdit">
		</div>
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12">
						<div class="form-group inlineflex w100p">
							<label for="colDescription" class="supLlb"><fmt:message key='text.judge' /></label>
							<input type="text" class="form-control c39c" id="colDescription" autocomplete="off" pattern="^[\S]*$">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" onclick="fnSetColumn()" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.ok" />
			</button>
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
				<fmt:message key="button.close" />
			</button>
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
<script src="resources/assets/js/courts.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
</script>