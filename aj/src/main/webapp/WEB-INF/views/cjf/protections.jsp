<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<title><fmt:message key="text.protectionsdirect"/> - Contacto jur&iacute;dico</title>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker-v1.10/css/bootstrap-datepicker3.min.css">

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
	#protection-modal{margin:0 !important}
	.custombox-modal-container{width:70% !important;max-width:850px}
	#editProtection-modal > .modal-dialog{width:80%;min-width:320px;max-width:850px}
	input[type=radio]{width:30px}

	#divtrialOrigin, #edDivTrialOrigin{display:block}
	#divappeal, #edDivAppealOrigin{display:none}
	@media screen and (max-width:610px){
		#protection-modal{width:100%;margin:0}
	}
	@media screen and (max-width:1024px){
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
 							<a href="#" id="addNewProtection" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" data-onOpen="console.log('test'); " class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title" style="text-transform:capitalize"><fmt:message key="text.protectiondirect" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
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
									<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />" href="protectiondashboard?language=${language}&rid=${am.amparoid}">${am.amparo}</a></td>
									<c:set var="hasCourt" value="0" />
									<c:choose>
										<c:when test="${am.tipodemandaturnadaa=='colegiado'}">
											<c:forEach var="clg" items="${colgcourts}">
												<c:if test="${am.demandaamparoturnadaa==clg.tribunalcolegiadoid}">
													<td>${clg.tribunalcolegiado}</td>
													<c:forEach var="cd" items="${cities}">
														<c:if test="${clg.ciudadid==cd[0]}">
															<td>${cd[1]}</td>
															<c:set var="hasCourt" value="1" />
														</c:if>
													</c:forEach>
												</c:if>
											</c:forEach>
										</c:when>
										<c:when test="${am.tipodemandaturnadaa=='unitario'}">
											<c:forEach var="unit" items="${unitcourts}">
												<c:if test="${am.demandaamparoturnadaa==unit.tribunalUnitarioid}">
													<td>${unit.tribunalUnitario}</td>
													<c:forEach var="cd" items="${cities}">
														<c:if test="${unit.ciudadid==cd[0]}">
															<td>${cd[1]}</td>
															<c:set var="hasCourt" value="1" />
														</c:if>
													</c:forEach>
													<c:set var="hasCourt" value="1" />
												</c:if>
											</c:forEach>
										</c:when>
										<c:when test="${am.tipodemandaturnadaa=='federal'}">
											<c:forEach var="jz" items="${fedcourts}">
												<c:if test="${am.demandaamparoturnadaa==jz.juzgadoid}">
													<td>${jz.juzgado}</td>
													<c:forEach var="cd" items="${cities}">
														<c:if test="${jz.ciudadid==cd[0]}">
															<td>${cd[1]}</td>
															<c:set var="hasCourt" value="1" />
														</c:if>
													</c:forEach>
													<c:set var="hasCourt" value="1" />
												</c:if>
											</c:forEach>
										</c:when>
									</c:choose>
									<c:if test="${hasCourt==0}">
										<td></td><td></td>
									</c:if>
									<td>${am.quejoso}</td>
									<td>${am.tercero}</td>
									<td>${am.actoreclamado}</td>
									<td>${am.autoridadresponsable}</td>
									<td><!-- Estatus --></td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${am.amparoid}" data-toggle="modal" data-target="#editProtection-modal" 
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
		<div class="hidden-xs" style="height:400px;"></div>
	</div>
</div>

<div id="protection-modal" class="modal-demo">
	<button type="button" class="cleanFields" data-reset="#protection-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
		<span class="glyphicon glyphicon-erase asLink"></span>
	</button>
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;color:#000">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title" style="text-transform:capitalize"><fmt:message key="button.additem" /> <fmt:message key="text.protectiondirect" /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form id="formAddProt">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnAddProt" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddProt').toggle();">&times;</a>
							<p id="puterrorOnAddProt"></p>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group inlineflex w100p">
								<label for="protClientList" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="protClient"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<div class="containTL">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="protClientList"></table>
								</div>
							</div>
						</div>
					</div>
					<jsp:include page="protectionfornew.jsp" flush="true"/>

				</div>
			</form>
			<button type="button" onclick="return addProtection(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addProtectCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="editProtection-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="editProtection-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="cleanFields" data-reset="#editProtection-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
					<span class="glyphicon glyphicon-erase asLink"></span>
				</button>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title"><fmt:message key="protection.update" /></h4>
				<input type="hidden" id="edid">
			</div>
			<div class="modal-body">
				<form id="formEditProt">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEditProt" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEditProt').toggle();">&times;</a>
								<p id="puterrorOnEditProt"></p>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.protectiondirect' /></h3></div></div>
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group inlineflex w100p">
									<label for="edProtClientList" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
									<input type="text" class="form-control c39c" id="edProtClient"
										placeholder="<fmt:message key="text.select" />" autocomplete="off">
									<div class="containTL">
										<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
											<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
										</button>
										<table class="table tablelist" id="edProtClientList"></table>
									</div>
								</div>
							</div>
						</div>
						<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.selectcustchar' /></h3></div></div>
						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-3">
								<div class="form-group fieldinfo inlineflex w100p">
									<input type="radio" name="edCustchar" id="edCustchar1" value="Quejoso" title="<fmt:message key='text.complaining' />">
									<label for="edCustchar1"><fmt:message key='text.complaining' /></label>
								</div>
							</div>
							<div class="col-xs-6 col-sm-6 col-md-3">
								<div class="form-group fieldinfo inlineflex w100p">
									<input type="radio" name="edCustchar" id="edCustchar2" value="Tercero" title="<fmt:message key='text.interestedtrdparty' />">
									<label for="edCustchar2"><fmt:message key='text.interestedtrdparty' /></label>
								</div>
							</div>
						</div>
	
						<div class="row"><div class="col-xs-12"><h3><em>* </em><fmt:message key="text.selecttrialAppealO" /></h3></div></div>
						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-3">
								<div class="form-group fieldinfo inlineflex w100p">
									<input type="radio" name="edOrigintype" id="edOrigintype1" value="Juicio">
									<label for="edOrigintype1"><fmt:message key='text.trial' /></label>
								</div>
							</div>
							<div class="col-xs-6 col-sm-6 col-md-3">
								<div class="form-group fieldinfo inlineflex w100p">
									<input type="radio" name="edOrigintype" id="edOrigintype2" value="Apelación">
									<label for="edOrigintype2"><fmt:message key='text.appeal' /></label>
								</div>
							</div>
							<div class="col-xs-12 col-md-6" id="edDivTrialOrigin">
								<div class="form-group fieldinfo inlineflex w100p">
									<label class="supLlb" for="edSelecttrialO"><em>* </em><fmt:message key='text.selecttrialO' /></label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="edSelecttrialO"></select>
									</div>
								</div>
							</div>
							<div class="col-xs-6 col-sm-6 col-md-6" id="edDivAppealOrigin">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="edAppealList" class="supLlb"><em>* </em><fmt:message key='text.originappeal' /></label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="edAppealList"></select>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-md-5">
							<div class="form-group inlineflex w100p">
								<label for="edProtection" class="supLlb"><fmt:message key='text.protectionnumber' /></label>
								<input type="text" class="form-control c39c" id="edProtection">
							</div>
						</div>
						<div class="row" style="margin-top:30px;margin-bottom:30px">
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb" for="edClaimedact"><em>* </em><fmt:message key='text.claimedact' /></label>
									<input type="text" class="form-control c39c" id="edClaimedact">
								</div>
							</div>
						</div>
	
						<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.responsibleauthority' /></h3></div></div><br><br>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb" for="edRespauth"><em>* </em><fmt:message key='text.responsibleauthority' /></label>
									<input type="text" class="form-control c39c" id="edRespauth">
								</div>
							</div>
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb t-15" for="edDateclaimedact"><fmt:message key='text.dateclaimedact' /></label>
									<input type="hidden" id="edDateclaimedact">
									<input type="text" class="form-control c39c" data-date="edit" id="edDateclaimedactFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
						</div>
	
						<div class="row">
							<div class="col-xs-12 col-md-8">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb t-15" for="edDateclaimedactNtn"><fmt:message key='text.dateclaimedactNtn' /></label>
									<input type="hidden" id="edDateclaimedactNtn">
									<input type="text" class="form-control c39c" data-date="edit" id="edDateclaimedactNtnFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb t-15" for="edFilingdatelawsuit"><fmt:message key='text.filingdatelawsuit' /></label>
									<input type="hidden" id="edFilingdatelawsuit">
									<input type="text" class="form-control c39c" data-date="edit" id="edFilingdatelawsuitFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6 col-md-3">
								<label for="edStateProt" class="supLlb"><fmt:message key='text.state' /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel c39c"
										onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="edStateProt"></ul>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-4">
								<label for="edCityProt" class="supLlb"><fmt:message key='text.city' /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel c39c" data-tip="edit-dir-city-court"
										onfocus="getCitiesByState('edCityProt','ul',$('#edStateProt li.selected').val());"
										placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="edCityProt"></ul>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-1 inlineflex quickTip trn3ms">
								<button type="button" data-tip="edit-dir-city-court"><i class="material-icons">lightbulb_outline</i></button>
								<span data-tip="edit-dir-city-court"><fmt:message key='tip.filtercourts' /></span>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-4">
								<label for="edDemandprotsent" class="supLlb"><fmt:message key='text.demandprotsent' /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel c39c" data-edlist="edDemandprotsent" data-tip="edit-dir-city-court"
										onfocus="getAllCourtsByCity('edDemandprotsent','ul',$('#edCityProt li.selected').val(), 'unitarios,colegiados');"
										placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="edDemandprotsent"></ul>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb" for="edAdmissiondate"><fmt:message key='text.admissiondate' /></label>
									<input type="hidden" id="edAdmissiondate" data-date="edit">
									<input type="text" class="form-control c39c" id="edAdmissiondateFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb t-15" for="edAdmissionnotifdate"><fmt:message key='text.admissionnotifdate' /></label>
									<input type="hidden" id="edAdmissionnotifdate" data-date="edit">
									<input type="text" class="form-control c39c" id="edAdmissionnotifdateFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb t-15" for="edStay"><fmt:message key='text.stay' /></label>
									<input type="text" class="form-control c39c" id="edStay">
								</div>
							</div>
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb" for="edAdhesiveaDirPtDate"><fmt:message key='text.adhesiveaDirPtDate' /></label>
									<input type="hidden" id="edAdhesiveaDirPtDate" data-date="edit">
									<input type="text" class="form-control c39c" id="edAdhesiveaDirPtDateFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb t-15" for="edDateshiftpresent"><fmt:message key='text.dateshiftpresent' /></label>
									<input type="hidden" id="edDateshiftpresent" data-date="edit">
									<input type="text" class="form-control c39c" id="edDateshiftpresentFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
						</div>
	
						<div class="row">
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb" for="edProjectjudgmentdate"><fmt:message key='text.projectjudgmentdate' /></label>
									<input type="hidden" id="edProjectjudgmentdate" data-date="edit">
									<input type="text" class="form-control c39c" id="edProjectjudgmentdateFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb" for="edJudgmentdate"><fmt:message key='text.judgmentdate' /></label>
									<input type="hidden" id="edJudgmentdate" data-date="edit">
									<input type="text" class="form-control c39c" id="edJudgmentdateFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb" for="edJudgmentnotifdate"><fmt:message key='text.judgmentnotifdate' /></label>
									<input type="hidden" id="edJudgmentnotifdate" data-date="edit">
									<input type="text" class="form-control c39c" id="edJudgmentnotifdateFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-md-4">
								<div class="form-group fieldinfo inlineblock w100p">
									<label class="supLlb" for="edReviewresourcedate"><fmt:message key='text.reviewresourcedate' /></label>
									<input type="hidden" id="edReviewresourcedate" data-date="edit">
									<input type="text" class="form-control c39c" id="edReviewresourcedateFix" data-date="edit" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div id="areaProtUpload"><span class="textContent"><fmt:message key='label.dropzone' /></span>
							<div id='uploadXEditProt' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
						</div>
					</div>
					<div>
						<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
						onclick="$('#targetDZ').val('#uploadXEditProt')" title="<fmt:message key='text.usecam.browser' />" >
							<i class="material-icons">camera_alt</i>
						</a>
					</div>
				</form>
			</div>
			<div class="modal-footer">
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
<!-- >script src="resources/assets/js/jquery.slimscroll.js"></script-->
<!-- Modal-Effect -->
<script src="resources/assets/plugins/custombox/js/custombox.min.js"></script>
<script src="resources/assets/plugins/custombox/js/legacy.min.js"></script>

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>
<script src="resources/assets/js/customDropZone.js"></script>
<script src="resources/assets/js/complementos.js"></script>
<script src="resources/local/cjf/protection.js"></script>

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
		/*$('[data-date]').datepicker().on('changeDate blur clearDate',function(){
			var id=(this.id).replace(/Fix$/ig,''),newdate=$(this).datepicker('getDate');
			$('#'+id).val(this.date==null?'':this.date);
			setBootstrapUtcDate(id,this.id,newdate);
			if(this.id=='startschdFix'&&$('#endschd').val()=='')
				setBootstrapUtcDate('endschd','endschdFix',newdate);
			else if(this.id=='edStartschdFix'&&$('#edEndschd').val()=='')
				setBootstrapUtcDate('edEndschd','edEndschdFix',newdate);
		});*/
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
</script>
<script>
$('#selecttrialO,#edSelecttrialO, #appealList,#edAppealList').on('focus', function(){
	var id=this.id,s1='edSelecttrialO',s2='edAppealList';
	var e1='#puterrorOn'+(id==s1||id==s2?'Edit':'Add')+'Prot',e2='#errorOn'+(id==s1||id==s2?'Edit':'Add')+'Prot',
		ct=(id==s1||id==s2?'#edP':'#p')+'rotClient',m=(id==s1||id==s2?'#editP':'#p')+'rotection-modal';
	var clid=$(ct+'List [name=rowline]:checked')[0];
	clid=(typeof clid=='undefined')?'0':(clid.id).replace(/[^0-9]/g,'');
	if(clid==0){
		$(ct).parent().addClass('has-error');
		$(e1).html(i18n('msg_select_clientfirst'));
		$(m+', .custombox-modal-open').animate({scrollTop:0},'1000');
		var option=new Option(i18n('msg_select_clientfirst'),'',!1,!0);
		$('#'+id).empty().append(option);
	}
});
</script>