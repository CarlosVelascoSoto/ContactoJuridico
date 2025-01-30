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
<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">

<style>
	.containTL{
		width:100%;
		max-height:160px;
	}
    .ddListImg{top:40px}
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
 							<a href="#" id="addNewClgCourt" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.collegiatecourts" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[1,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.collegiatecourt" /></th>
									<th class="tac"><fmt:message key="text.city" /></th>
									<th class="tac"><fmt:message key="text.state" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="d" items="${data}">
								<c:set var="edo" value=""/>
								<c:set var="cd" value=""/>
								<tr>
									<td>
										${d.tribunalcolegiado}
										<c:forEach var="c" items="${cities}">
											<c:if test="${c.ciudadid==d.ciudadid}">
												<c:set var="cd" value="${c.ciudad}"/>
												<c:forEach var="s" items="${states}">
													<c:if test="${s.estadoid==c.estadoid}">
														<c:set var="edo" value="${s.estado}"/>
													</c:if>
												</c:forEach>
											</c:if>
										</c:forEach>
									</td>
									<td class="tac">${cd}</td>
									<td class="tac">${edo}</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${d.tribunalcolegiadoid}" data-toggle="modal" data-target="#edit-clgcourt-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsToEdit(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" id="${d.tribunalcolegiadoid}" onclick="deleteClgCourt(id);"><i class="md md-close"></i></a>
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
<div id="clgcourt-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /></h4>
	<div class="custom-modal-text text-left">
		<div id="ErrorSelectState">
			<a href="#" class="close" style="color:#000;display: flex;position: absolute;right: 34px;top: 82px;" aria-label="close"
			onclick="$('#ErrorSelectState').hide()">&times;</a>
			<p id="putErrorSelectState"
			 style="display: block;background-color: #FCDCDC;border: 1px solid #F05050;color: #F05252;border-radius: 5px;padding: 10px 7px;">
			 </p>
		</div>
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
								<label for="clgcourt" class="supLlb"><fmt:message key='text.collegiatecourt' /></label>
								<input type="text" class="form-control c39c" id="clgcourt" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<label for="statecourt" class="supLlb"><fmt:message key='text.state' /></label>
						<div class="form-group inlineflex w100p">
							<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />" >
							<i class="material-icons iconlistfilter">arrow_drop_down</i>
							<ul class="ddListImg noimgOnList" id="statecourt"></ul>
						</div>
					</div>
					<div class="col-xs-12">
						<label for="cityCollCourt" class="supLlb"><fmt:message key='text.city' /></label>
						<div class="form-group inlineflex w100p">
							<input type="text" class="form-control listfiltersel" onfocus="getCitiesByState('cityCollCourt','ul',$('#statecourt li.selected').val());" placeholder="<fmt:message key="text.select" />" >
							<i class="material-icons iconlistfilter">arrow_drop_down</i>
							<ul class="ddListImg noimgOnList" id="cityCollCourt"></ul>
						</div>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addClgCourt();" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addClgCourtCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-clgcourt-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-clgcourt-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="collegiatecourt.update" /></h4>
				<input type="hidden" id="edClgCourtId">
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
								<label for="edClgCourt" class="supLlb"><fmt:message key='text.collegiatecourt' /></label>
								<input type="text" class="form-control c39c" id="edClgCourt" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<label for="edStatecourt" class="supLlb"><fmt:message key='text.state' /></label>
						<div class="form-group inlineflex w100p">
							<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />" >
							<i class="material-icons iconlistfilter">arrow_drop_down</i>
							<ul class="ddListImg noimgOnList" id="edStatecourt"></ul>
						</div>
					</div>
					<div class="col-xs-12">
						<label for="edCityCollCourt" class="supLlb"><fmt:message key='text.city' /></label>
						<div class="form-group inlineflex w100p">
							<input type="text" class="form-control listfiltersel"
								onfocus="getCitiesByState('edCityCollCourt','ul',$('#edStatecourt li.selected').val());optImgListIcon($('#edCityCollCourt'));"
								placeholder="<fmt:message key="text.select" />" >
							<i class="material-icons iconlistfilter">arrow_drop_down</i>
							<ul class="ddListImg noimgOnList" id="edCityCollCourt"></ul>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateClgCourt();" class="btn btn-default waves-effect waves-light">
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
<script src="resources/assets/js/collegiatecourts.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
</script>