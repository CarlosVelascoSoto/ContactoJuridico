<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<title><fmt:message key="text.appeals"/> - Contacto jur&iacute;dico</title>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<!-- link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css"-->

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
<!-- link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css"-->
<!-- link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"-->
<!-- link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css"-->

<style>
	input[type=radio]{width:30px}
</style>

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addNewAppeal" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg">
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.appeals" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="card-box table-responsive">
						<div class="table-subcontrols">
							<button type="button" id="btnfilter" class="btn btn-filter trn2ms" title="<fmt:message key="text.filtersbycolumns" />"
								onclick="$('#datatable-buttons thead tr:eq(1)').toggle();"><span class="glyphicon glyphicon-filter"></span> 
							</button>
							<button id="clearfilters" class="btn clearfilters trn2ms" title="<fmt:message key="text.clearfiters" />"
								onclick="cleanTableFilters(this)"><span class="glyphicon glyphicon-filter"></span><i class="material-icons">close</i>
							</button>
						</div>
						<table id="datatable-buttons" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.appeal" /></th>
									<th><fmt:message key="text.city" /></th>
									<th><fmt:message key="text.room" /></th>
									<th><fmt:message key="text.matter" /></th>
									<th><fmt:message key="text.speaker" /></th>
									<th><fmt:message key="text.resolutionCnt" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="a" items="${appeal}">
								<tr>
									<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />" href="appealsdashboard?language=${language}&rid=${a.apelacionid}">${a.toca}</a></td>
									<td>
										<c:forEach var="c" items="${cities}">
											<c:if test="${a.ciudadid == c.ciudadid}">
												${c.ciudad}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:forEach var="s" items="${salas}">
											<c:if test="${a.salaid == s.salaid}">
												${s.sala}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:forEach var="m" items="${materias}">
											<c:if test="${a.materiaid == m.materiaid}">
												${m.materia}
											</c:if>
										</c:forEach>
									</td>
									<td>${a.ponente}</td>
									<td>${a.resolucion}</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${a.apelacionid}" data-toggle="modal" data-target="#edit-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDataDetails(id);">
											<i class="md md-edit"></i>
										</a>
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

<div id="appeal-modal" class="modal-demo">
	<button type="button" class="cleanFields" data-reset="#appeal-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
		<span class="glyphicon glyphicon-erase asLink"></span>
	</button>
	<button type="button" class="close" onclick="Custombox.close();" title="<fmt:message key='button.close' />" style="position:relative">
		<span>&times;</span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /></h4>
	<div class="custom-modal-text text-left" style="padding-top:0">
		<div class="panel-body">
			<form method="post" id="formAddAppeal">
				<div class="form-group inlineblock">
					<div class="col-xs-12">
						<div id="errorOnAddApl" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddApl').toggle();">&times;</a>
							<p id="putErrorOnAddApl"></p>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group inlineflex w100p">
								<label for="clientList" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="client"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<div class="containTL">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="clientList"></table>
								</div>
							</div>
						</div>
					</div>
					<jsp:include page="appealsfornew.jsp" flush="true"/>
				</div>
			</form>
			<div class="modal-footer">
				<button type="button" onclick="return addAppeal(this);" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="Custombox.close();" id="addAppealCancel" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="edit-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="cleanFields" data-reset="#edit-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
					<span class="glyphicon glyphicon-erase asLink"></span>
				</button>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title"><fmt:message key="appeal.update" /></h4>
			</div>
			<div class="modal-body">
				<form id="formEditAppeal">
					<div class="form-group ">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEditApl" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEditApl').toggle();">&times;</a>
								<p id="putErrorOnEditApl"></p>
								<input type="hidden" id="edAppealId">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group inlineflex w100p">
								<label for="editclient" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="editclient"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<div class="containTL">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="editClientList"></table>
								</div>
							</div>
						</div>
					</div>

					<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.selectcustchar' /></h3></div></div>
					<div class="row">
						<div class="col-xs-6 col-sm-6 col-md-3">
							<div class="form-group fieldinfo inlineflex w100p">
								<input type="radio" name="edCusttype" id="edCusttype1" value="Apelado" checked>
								<label for="edCusttype1"><fmt:message key='text.respondent' /></label>
							</div>
						</div>
						<div class="col-xs-6 col-sm-6 col-md-3">
							<div class="form-group fieldinfo inlineflex w100p">
								<input type="radio" name="edCusttype" id="edCusttype2" value="Apelante">
								<label for="edCusttype2"><fmt:message key='text.appellant' /></label>
							</div>
						</div>
					</div>

					<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.selecttrialO' /></h3></div></div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="edTrialApl" class="supLlb"><em>* </em><fmt:message key='text.trial' /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel c39" id="edTrialAplList"
										placeholder="<fmt:message key="text.select" />" >
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="edTrialApl"></ul>
								</div>
							</div>
						</div>
					</div>
					
					<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.appealidentifier' /></h3></div></div>

					<div class="col-xs-12 col-md-4">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="handle" class="supLlb"><fmt:message key='text.handle' /></label>
							<input type="text" class="form-control c39c" id="edhandle">
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-md-6">
							<label for="edRoomApl" class="supLlb"><em>* </em><fmt:message key='text.room' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="edRoomApl"></ul>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<label for="edMatterApl" class="supLlb"><em>* </em><fmt:message key='text.matter' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="edMatterApl"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6">
							<label for="edAplState" class="supLlb"><fmt:message key='text.state' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="edAplState"></ul>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6">
							<label for="edAplCity" class="supLlb"><em>* </em><fmt:message key='text.city' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c"
									onfocus="getCitiesByState('edAplCity','ul','edAplState');"
									placeholder="<fmt:message key="text.select" />">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="edAplCity"></ul>
							</div>
						</div>
					</div>

					<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.parties' /></h3></div></div>
					<div class="row">
						<div class="col-xs-12 col-md-8">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="edSpeaker" class="supLlb"><em>* </em><fmt:message key='text.speaker' /></label>
								<input type="text" class="form-control c39c" id="edSpeaker">
							</div>
						</div>
						<div class="col-xs-12 col-md-4">
							<div class="form-group fieldinfo inlineblock">
								<label for="edApladhesiva" class="supLlb"><em>* </em><fmt:message key='text.adhesiveappeal' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="edApladhesiva">
										<option value="-1" selected disabled><fmt:message key='text.select' /></option>
										<option value="1"><fmt:message key='text.yes' /></option>
										<option value="0"><fmt:message key='text.no' /></option>
									</select>
								</div>
							</div>
						</div>
					</div>

					<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.resolutionCnt' /></h3></div></div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group fieldinfo inlineblock">
								<label for="edResolution" class="supLlb"><fmt:message key='text.resolution' /></label>
								<input type="text" class="form-control c39c" id="edResolution">
							</div>
						</div>
					</div>

					<div class="row">
						<div id="areaEditAppealUpload"><span class="textContent">
							<fmt:message key='label.dropzone' /></span>
							<div id='uploadXEditAppeal' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
						</div>
					</div>
					<div>
						<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
						onclick="$('#targetDZ').val('#uploadXEditAppeal')" title="<fmt:message key='text.usecam.browser' />" >
							<i class="material-icons">camera_alt</i>
						</a>
					</div>
				</form>
				<button type="button" onclick="return updateData();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="room-modal" class="sub-modal dnone">
	<div class="modal-dialog">
		<button type="button" class="cleanFields" data-reset="#room-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
			<span class="glyphicon glyphicon-erase asLink"></span>
		</button>
		<button type="button" class="close" onclick="$('#room-modal').addClass('dnone');" style="margin:10px;color:#fff">
			<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
		</button>
		<h4 class="custom-modal-title"><fmt:message key="text.add" /> <fmt:message key="text.room" /></h4>
		<div class="custom-modal-text text-left">
			<div class="panel-body">
				<form>
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnAddRoom" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddRoom').toggle();">&times;</a>
							<p id="putErrorOnAddRoom"></p>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="newroom" class="supLlb"><fmt:message key='text.room' /></label>
									<input type="text" class="form-control c39c" id="newroom" autocomplete="off">
								</div>
							</div>
						</div>
					</div>
				</form>
				<button type="button" onclick="return addRoom();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="$('#room-modal').addClass('dnone');" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="matter-modal" class="sub-modal dnone">
	<div class="modal-dialog">
		<button type="button" class="cleanFields" data-reset="#matter-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
			<span class="glyphicon glyphicon-erase asLink"></span>
		</button>
		<button type="button" class="close" onclick="$('#matter-modal').addClass('dnone');" style="margin:10px;color:#fff">
			<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
		</button>
		<h4 class="custom-modal-title"><fmt:message key="text.add" /> <fmt:message key="text.matter" /></h4>
		<div class="custom-modal-text text-left">
			<div class="panel-body">
				<form>
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnAddMatter" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddMatter').toggle();">&times;</a>
							<p id="putErrorOnAddMatter"></p>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="newmatter" class="supLlb"><fmt:message key='text.matter' /></label>
									<input type="text" class="form-control c39c" id="newmatter" autocomplete="off">
								</div>
							</div>
						</div>
					</div>
				</form>
				<button type="button" onclick="return addMatter();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="$('#matter-modal').addClass('dnone');" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="city-modal" class="sub-modal" style="display:none">
	<div class="modal-dialog">
		<button type="button" class="cleanFields" data-reset="#city-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
			<span class="glyphicon glyphicon-erase asLink"></span>
		</button>
		<button type="button" class="close" onclick="$('#city-modal').addClass('dnone');" style="margin:10px;color:#fff">
			<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
		</button>
		<h4 class="custom-modal-title"><fmt:message key="text.add" /> <fmt:message key="text.city" /></h4>
		<div class="custom-modal-text text-left">
			<div class="panel-body">
				<form>
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnAddCity" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddCity').toggle();">&times;</a>
							<p id="putErrorOnAddCity"></p>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="newcity" class="supLlb"><fmt:message key='text.city' /></label>
									<input type="text" class="form-control c39c" id="newcity" autocomplete="off">
								</div>
							</div>
						</div>
					</div>
				</form>
				<button type="button" onclick="return addCity();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="$('#city-modal').addClass('dnone');" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>
<script src="resources/assets/js/jquery.min.js"></script>

<div class="modal" id="camera-modal" role="dialog" aria-labelledby="camera-modal" aria-hidden="true">
	<button type="button" class="close _mod" onclick="$('#camera-modal').modal('hide');">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<jsp:include page="/WEB-INF/views/general/cam/cam.jsp" flush="true"/>
</div>
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

<!--script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script-->
<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>
<script src="resources/assets/js/customDropZone.js"></script>
<script src="resources/local/commonlaws/apelaciones.js"></script>

<script type="text/javascript">
	var dttable=null;
	jQuery(document).ready(function($){
		let dtId1='#datatable-buttons', txtfnd=i18n('msg_search');
		let dtId0=dtId1.replace(/^[#\.]/,''), rx=new RegExp(txtfnd, 'ig');
		$(dtId1+' thead tr').clone(true).appendTo(dtId1+' thead');
		$(dtId1+' thead tr:eq(1)').css('display','none');
		$(dtId1+' thead tr:eq(1) th').each( function (i) {
			var title=i18n('msg_filter_by') + ' ' + $(this).text();
			$(this).html('<input type="text" class="inputFilter" name="filterby" placeholder="' + title + '">');
			$('input', this).on('keyup input paste change delete cut clear', function (){
				dttable=$(dtId1).DataTable();
				if(dttable.column(i).search() !== this.value){
					dttable.column(i).search(this.value).draw();
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