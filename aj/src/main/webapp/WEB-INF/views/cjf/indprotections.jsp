<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<title><fmt:message key="text.indprotections"/> - Contacto jur&iacute;dico</title>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker-v1.10/css/bootstrap-datepicker3.min.css">
<!--link rel="stylesheet" href="resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.css">
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

<style>
	#proceedings{max-width:100%;height:38px;padding:7px 12px;border:1px solid #E3E3E3;
	    -webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px;background-color:#FFFFFF}
	#indprotection-modal{margin:0 !important}
	#errorOnAddIndProt{display:none}
	.custombox-modal-container{width:70% !important;max-width:850px}
	.calIcon{position:absolute;width:auto;bottom:25px;right:10px;background-color:transparent;
		border:1px solid transparent;cursor:pointer}
	#edit-indprotection-modal > .modal-dialog{width:100%;max-width:800px}
	/*	.containTL{width:100%}
	#datatable-buttons th{text-align:center;padding-left:20px}
	.modal{height:auto !important;max-height:100vh !important}*/

	@media screen and (max-width: 1024px){
		.custombox-modal-container{width:100% !important}
	}
</style>
<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addNewProtInd" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" data-onOpen="console.log('test'); " class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title" style="text-transform:capitalize"><fmt:message key="text.indprotections" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="card-box table-responsive">
						<div class="table-subcontrols">
							<button type="button" id="btnfilter" class="btn btn-info btn-filter trn2ms" title="<fmt:message key="text.filtersbycolumns" />"
								onclick="$('#datatable-buttons thead tr:eq(1)').toggle();"><span class="glyphicon glyphicon-filter"></span> 
							</button>
							<button id="clearfilters" class="btn clearfilters trn2ms" title="<fmt:message key="text.clearfiters" />"
								onclick="cleanTableFilters(this)"><span class="glyphicon glyphicon-filter"></span><i class="material-icons">close</i>
							</button>
						</div>
						<table id="datatable-buttons" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.protection" /></th>
									<th><fmt:message key="text.organrecognizes" /></th>
									<th><fmt:message key="text.city" /></th>
									<th><fmt:message key="text.complaining" /></th>
									<th><fmt:message key="text.interestedtrdparty" /></th>
									<th><fmt:message key="text.claimedact" /></th>
									<th><fmt:message key="text.responsibleauthority" /></th>
									<th><fmt:message key="text.status" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="am" items="${amparos}">
								<tr>
									<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />" href="indprotectiondashboard?language=${language}&rid=${am.amparoid}">${am.amparo}</a></td>
									<c:set var="tds" value="0" />
									<c:choose>
										<c:when test="${am.tipodemandaturnadaa=='colegiado'}">
											<c:forEach var="clg" items="${colgcourts}">
												<c:if test="${am.demandaamparoturnadaa==clg.tribunalcolegiadoid}">
													<td>${clg.tribunalcolegiado}</td>
													<c:set var="tds" value="${tds + 1}"/>
													<c:forEach var="cd" items="${cities}">
														<c:if test="${clg.ciudadid==cd[0]}">
															<td>${cd[1]}</td>
															<c:set var="tds" value="${tds + 1}"/>
														</c:if>
													</c:forEach>
												</c:if>
											</c:forEach>
										</c:when>
										<c:when test="${am.tipodemandaturnadaa=='unitario'}">
											<c:forEach var="unit" items="${unitcourts}">
												<c:if test="${am.demandaamparoturnadaa==unit.tribunalUnitarioid}">
													<td>${unit.tribunalUnitario}</td>
													<c:set var="tds" value="${tds + 1}"/>
													<c:forEach var="cd" items="${cities}">
														<c:if test="${unit.ciudadid==cd[0]}">
															<td>${cd[1]}</td>
															<c:set var="tds" value="${tds + 1}"/>
														</c:if>
													</c:forEach>
												</c:if>
											</c:forEach>
										</c:when>
										<c:when test="${am.tipodemandaturnadaa=='federal'}">
											<c:forEach var="jz" items="${fedcourts}">
												<c:if test="${am.demandaamparoturnadaa==jz.juzgadoid}">
													<td>${jz.juzgado}</td>
													<c:set var="tds" value="${tds + 1}"/>
													<c:forEach var="cd" items="${cities}">
														<c:if test="${jz.ciudadid==cd[0]}">
															<td>${cd[1]}</td>
															<c:set var="tds" value="${tds + 1}"/>
														</c:if>
													</c:forEach>
												</c:if>
											</c:forEach>
										</c:when>
									</c:choose>
									<c:if test="${tds==1}"><td></td></c:if>
									<c:if test="${tds==0}"><td></td><td></td></c:if>
									<td>${am.quejoso}</td>
									<td>${am.tercero}</td>
									<td>${am.actoreclamado}</td>
									<td>${am.autoridadresponsable}</td>
									<td></td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${am.amparoid}" data-toggle="modal" data-target="#edit-indprotection-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsToEdit(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
										<td class="tac">
											<a href="#" class="table-action-btn" id="${am.amparoid}" onclick="deleteProtection(id);"><i class="md md-close"></i></a>
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

<div id="indprotection-modal" class="modal-demo">
	<button type="button" class="cleanFields" data-reset="#indprotection-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
		<span class="glyphicon glyphicon-erase asLink"></span>
	</button>
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;color:#000">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>

	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key="text.indprotections" /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form id="formAddIndProt">
				<div class="form-group ">
					<div class="col-xs-12">
						<div id="errorOnAddIndProt" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddIndProt').toggle();">&times;</a>
							<p id="puterrorOnAddIndProt"></p>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group inlineflex w100p">
								<label for="indProtClientList" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="indProtClient"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<div class="containTL">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="indProtClientList"></table>
								</div>
							</div>
						</div>
					</div>
					<jsp:include page="indprotectionfornew.jsp" flush="true"/>
				</div>
			</form>
			<button type="button" onclick="return addIndProtection(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addIndProtectCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-indprotection-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-indprotection-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="cleanFields" data-reset="#edit-indprotection-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
					<span class="glyphicon glyphicon-erase asLink"></span>
				</button>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title"><fmt:message key="indprotection.update" /></h4>
			</div>
			<div class="modal-body">
				<form id="formEditIndProt">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEditIndProt" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEdit').toggle();">&times;</a>
								<p id="puterrorOnEditIndProt"></p>
								<input type="hidden" id="edIndId">
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group inlineflex w100p">
								<label for="edIndProtClientList" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="edIndProtClient"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<div class="containTL">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="edIndProtClientList"></table>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-6 col-sm-3 col-md-2">
							<p style="margin-left:10px;"><fmt:message key="text.clientis" />: </p>
						</div>
						<div class="col-xs-6 col-sm-3 col-md-2">
							<div class="form-group fieldinfo inlineflex w100p">
								<input type="radio" name="edIndCustQ3" id="edIndCustQ3_q" value="<fmt:message key='text.complaining' />" checked>
								<label for="edIndCustQ3_q" style="padding:2px 0 0 10px"><fmt:message key="text.complaining" /></label>
							</div>
						</div>
						<div class="col-xs-6 col-sm-6 col-md-3">
							<div class="form-group fieldinfo inlineflex w100p">
								<input type="radio" name="edIndCustQ3" id="edIndCustQ3_3"value="<fmt:message key='text.interestedtrdparty' />">
								<label for="edIndCustQ3_3" style="padding:2px 0 0 10px"><fmt:message key="text.interestedtrdparty" /></label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-md-6" id="edOpCustQ3_q">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndComplaining"><fmt:message key='text.complaining' /></label>
								<input type="text" class="form-control c39c" id="edIndComplaining">
							</div>
						</div>
						<div class="col-xs-12 col-md-6" id="edOpCustQ3_3">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndTrdparty"><fmt:message key='text.interestedtrdparty' /></label>
								<input type="text" class="form-control c39c" id="edIndTrdparty">
							</div>
						</div>
						<div class="col-xs-12 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndClaimedact"><fmt:message key='text.claimedact' /></label>
								<input type="text" class="form-control c39c" id="edIndClaimedact">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12 col-md-4">
							<div class="form-group inlineflex w100p">
								<label for="edIndProtection" class="supLlb"><fmt:message key='text.protectionnumber' /></label>
								<input type="text" class="form-control c39c" id="edIndProtection">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-md-7">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="edIndOrigintype" class="supLlb"><em>* </em> <fmt:message key="text.originclaimedact" /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="edIndOrigintype">
										<option value="" selected disabled><fmt:message key='text.select'/></option>
										<option value="0"><fmt:message key="text.thisprotnottrialappeal" /></option>
										<option value="1"><fmt:message key="text.protectionderivestrial" /></option>
										<option value="2"><fmt:message key="text.protectionderivesappeal" /></option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-md-5 dnone" id="divEdIndAppealOrigin">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="edIndAppealList" class="supLlb"><em>* </em> <fmt:message key='text.selectappealO' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="edIndAppealList"></select>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-md-5 dnone" id="divEdIndTrialOrigin">
							<div class="form-group fieldinfo inlineflex w100p">
								<label class="supLlb" for="edIndSelecttrialO"><em>* </em> <fmt:message key='text.selecttrialO' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="edIndSelecttrialO"></select>
								</div>
							</div>
						</div>
					</div>

					<div class="row"><div class="col-xs-12 col-md-12"><h3><fmt:message key='text.responsibleauthority' /></h3></div></div><br><br>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndRespauth"><em>* </em> <fmt:message key='text.responsibleauthority' /></label>
								<input type="text" class="form-control c39c" id="edIndRespauth">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-3">
							<label for="edStateIndProt" class="supLlb"><fmt:message key='text.state' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="edStateIndProt"></ul>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-4">
							<label for="edCityIndProt" class="supLlb"><fmt:message key='text.city' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" data-tip="edit-ind-city-court")
									onfocus="getCitiesByState('edCityIndProt','ul',$('#edStateIndProt li.selected').val());"
									placeholder="<fmt:message key="text.select" />">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="edCityIndProt"></ul>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-1 inlineflex quickTip trn3ms">
							<button type="button" data-tip="edit-ind-city-court"><i class="material-icons">lightbulb_outline</i></button>
							<span data-tip="edit-ind-city-court"><fmt:message key='tip.filtercourts' /></span>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-4">
							<label for="edIndDemandprotsent" class="supLlb"><fmt:message key='text.demandprotsent' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" data-edlist="edIndDemandprotsent" data-tip="edit-ind-city-court"
									onfocus="getAllCourtsByCity('edIndDemandprotsent','ul',$('#edCityIndProt li.selected').val(), 'unitarios,colegiados,federales');"
									placeholder="<fmt:message key="text.select" />">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="edIndDemandprotsent"></ul>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-md-8">
						<div class="form-group fieldinfo inlineblock w100p">
							<label class="supLlb t-15" for="edIndDateclaimedactNtn"><fmt:message key='text.dateclaimedactNtn' /></label>
							<input type="hidden" id="edIndDateclaimedactNtn">
							<input type="text" class="form-control c39c" data-date="edit" id="edIndDateclaimedactNtnFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15" for="edIndFilingdatelawsuit"><fmt:message key='text.filingdatelawsuit' /></label>
								<input type="hidden" id="edIndFilingdatelawsuit">
								<input type="text" class="form-control c39c" id="edIndFilingdatelawsuitFix" data-date="edit" autocomplete="off">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndAutoadmissiondate"><fmt:message key='text.autoadmissiondate' /></label>
								<input type="hidden" id="edIndAutoadmissiondate">
								<input type="text" class="form-control c39c" id="edIndAutoadmissiondateFix" data-date="edit"
									title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15" for="edIndAdmissionnotifdate"><fmt:message key='text.admissionnotifdate' /></label>
								<input type="hidden" id="edIndAdmissionnotifdate">
								<input type="text" class="form-control c39c" id="edIndAdmissionnotifdateFix" data-date="edit"
									title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndConsthearingdate"><fmt:message key='text.consthearingdate' /></label>
								<input type="hidden" id="edIndConsthearingdate">
								<input type="text" class="form-control c39c" id="edIndConsthearingdateFix" data-date="edit"
									title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndJudgment"><fmt:message key='text.judgment' /></label>
								<input type="text" class="form-control c39c" id="edIndJudgment" autocomplete="off">
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndRsrcreviewjudgment"><fmt:message key='text.rsrcreviewjudgment' /></label>
								<input type="text" class="form-control c39c" id="edIndRsrcreviewjudgment" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndProvsusp"><fmt:message key='text.provsusp' /></label>
								<input type="text" class="form-control c39c" id="edIndProvsusp" autocomplete="off">
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-6 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndIncidentalhearingdate"><fmt:message key='text.incidentalhearingdate' /></label>
								<input type="hidden" id="edIndIncidentalhearingdate">
								<input type="text" class="form-control c39c" id="edIndIncidentalhearingdateFix" data-date="edit"
									title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edIndAdjournmentjudgment"><fmt:message key='text.adjournmentjudgment' /></label>
								<input type="text" class="form-control c39c" id="edIndAdjournmentjudgment" autocomplete="off">
							</div>
						</div>
					</div>
	
					<c:if test="${(role==1 || role==2)}">
						<div class="row"><div class="col-sm-12">
							<h4 style="color:red"><fmt:message key='text.reasign' />:</h4></div></div>
						<div class="row"><div class="col-sm-12"><h4><fmt:message key='text.firm' /></h4></div></div>
						<div class="row">
							<div class="col-xs-12 col-sm-12">
								<label for="f2" class="supLlb"><fmt:message key='text.firm' /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel c39c"
										onfocus="getCompanies('f2','ul');" autocomplete="off"
										placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="f2"></ul>
								</div>
							</div>
						</div>
						<script>
							function getCompanies(id,elemtype){
								var elemOp='option';
								if(elemtype=='ul'||elemtype=='ol')
									elemOp='li';
								$('#'+id).empty();
								$.ajax({
									type:"POST",
									url:ctx+"/getCompanies",
									async:false,
									success:function(data){
										var info=data[0];
										if(info.length>0){
											if(elemtype=='select'||elemtype=='')
												$('#'+id).append('<'+elemOp+' value="-1" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
											for(i=0;i<info.length;i++)
												$('#'+id).append('<'+elemOp+' value="'+info[i].companyid+'" title="'+info[i].address1+'">'+info[i].company+'</'+elemOp+'>');
											$('#'+id).append('<'+elemOp+' value=""></'+elemOp+'>');
										}
									},error:function(e){
										console.log(i18n('err_unable_get_firm')+'. '+e);
									}
								});
							};
							function setFirmTrue(record){
								getCompanies('f2','ul');
								$.ajax({
									type:"POST",
									url:ctx+"/getCompClientByCCid",
									data:"id="+record.companyclientid,
									async:false,
									success:function(data){
										if(data.length>0){
											var info=data[0].detail[0];
											getTextDDFilterByVal('f2',info.companyid);
										}
									},error:function(e){
										console.log(i18n('err_unable_get_firm')+'. '+e);
									}
								});
							};
						</script>
					</c:if>

					<div class="row">
						<div id="areaIndProtUpload"><span class="textContent"><fmt:message key='label.dropzone' /></span>
							<div id='uploadXEditIndProt' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
						</div>
					</div>
					<div>
						<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
						onclick="$('#targetDZ').val('#uploadXEditIndProt')" title="<fmt:message key='text.usecam.browser' />" >
							<i class="material-icons">camera_alt</i>
						</a>
					</div>
				</form>
			</div>
			<button type="button" onclick="return updateIndProt();" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
				<fmt:message key="button.close" />
			</button>
		</div>
	</div>
</div>

<script src="resources/assets/js/jquery.min.js"></script>
<script src="resources/assets/plugins/bootstrap-datepicker-v1.10/js/bootstrap-datepicker.min.js"></script>

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

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>
<script src="resources/assets/js/customDropZone.js"></script>
<script src="resources/assets/js/complementos.js"></script>
<script src="resources/local/cjf/indprotection.js"></script>

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
				'<"col-sm-3"f><tr><"row"<"col-xs-12 col-md-4 fs12"i>'
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

		var lang=getLanguageURL();
		$('[data-date]').datepicker({
			autoclose:true,
			calendarWeeks:true,
//			clearBtn:true,
			dateFormat:(getFormatPatternDate(lang)).toLowerCase(),
			daysOfWeekHighlighted:"0",
			format:(getFormatPatternDate(lang)).toLowerCase(),
			language:lang,
			todayBtn:true,
			todayHighlight:true,
			weekStart:1
		});
		$('[data-date]').datepicker().on('changeDate blur clearDate',function(){
			var id=(this.id).replace(/Fix$/ig,'');
			$('#'+id).val(this.date==null?'':this.date);
			setBootstrapUtcDate(id,this.id,$(this).datepicker('getDate'));
		});
		$(function(){
			setdp=function(targetId,d,inPattern,outPattern){
				if(targetId!=''&&(d!=null&&d!='')){
					var dt=formatDateTime(d,inPattern,outPattern);
					$(targetId+'Fix').datepicker('setDate',dt);
					dt=formatDateTime(d,inPattern,'yyyy-MM-dd');
					$(targetId).val(dt);
				}
			};
		});
	});
	$('.modal-demo').on('hide.bs.modal', function(){
		clearTemp();
	});

	$("#indOrigintype").on('change', function(){
		var clid=$('#indProtClientList [name=rowline]:checked')[0],
			err='',targetId='';
		clid=(typeof clid=='undefined')? '0':(clid.id).replace(/[^0-9]/g,'');
		if(this.value==1){
			targetId='#indSelecttrialO';
			if(clid==0)
				err='msg_select_clientfirst';
			else
				getRelClientTrial(clid,targetId);
		}else if(this.value==2){
			targetId='#indAppealList';
			if(clid==0)
				err='msg_select_clientfirst';
			else
				getRelClientAppeal(clid,targetId);
		}
		if(err!=''){
			$(targetId).empty();
			$('#indProtClient').parent().addClass('has-error');
			$('#puterrorOnAddIndProt').html(i18n(err));
			$('#errorOnAddIndProt').show();
			$('#indprotection-modal, .custombox-modal-open').animate({scrollTop:0},'1000');
		}
	});
</script>