<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
	[data-list]{diaplay:none;max-width:840px}
</style>

<div id="resource-modal" class="modal-demo">
	<button type="button" class="cleanFields" data-reset="#resource-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
		<span class="glyphicon glyphicon-erase asLink"></span>
	</button>
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	
	<h4 class="custom-modal-title"
		style="text-transform:capitalize"><fmt:message key="button.additem" /> <fmt:message key="text.resource" /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form id="formAddResource">
				<div class="form-group ">
					<div class="col-xs-12" style="max-height:150px">
						<div style="display:none;" id="errorOnAddRsc" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close"
								onclick="$('#errorOnAddRsc').toggle();">&times;</a>
							<p id="puterrorOnAddRsc"></p>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<div class="form-group inlineflex w100p">
								<label for="resourcenumber" class="supLlb"><fmt:message key='text.resourcenumber' /></label>
								<input type="text" class="form-control c39c" id="resourcenumber">
							</div>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<div class="form-group inlineflex w100p">
								<label for="resourcetype" class="supLlb"><em>* </em><fmt:message key='text.resourcetype' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="resourcetype">
										<option value="" selected disabled><fmt:message key="text.select" /></option>
										<option value="1"><fmt:message key="text.review" /></option>
										<option value="2"><fmt:message key="text.complaint" /></option>
										<option value="3"><fmt:message key="text.claim" /></option>
										<option value="4"><fmt:message key="text.unconformity" /></option>
										<option value="5"><fmt:message key="text.hindrance" /></option>
									</select>
								</div>
							</div>
						</div>
					</div>
	
					<div class="row" data-clientfor="resource">
						<div class="col-sm-12"><h3><fmt:message key='text.originprotection' /></h3></div>
					</div>
					<div class="row" data-clientfor="resource">
						<div class="col-xs-12 col-sm-12 col-md-6">
							<div class="form-group inlineflex w100p">
								<label for="clientRsc" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="clientRsc"
									onblur="$('#originType').val('')" placeholder="<fmt:message key="text.select" />">
								<div class="containTL" data-list="clientRsc">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="clientRscList"></table>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-3">
							<div class="form-group inlineflex w100p">
								<label for="originType"
									class="supLlb"><em>* </em><fmt:message key='text.origintype' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="originType">
										<option value="" selected><fmt:message key="text.select" /></option>
										<option value="1"><fmt:message key="text.directprotection" /></option>
										<option value="2"><fmt:message key="text.indprotection" /></option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-3">
							<label for="originTypeId"
								class="supLlb"><em>* </em><fmt:message key='text.originprotection' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" data-list="addProt"
									onfocus="getProtections('originTypeId','ul');"
									placeholder="<fmt:message key="text.select" />" >
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="originTypeId"></ul>
							</div>
						</div>
					</div>
	
					<div class="row"><div class="col-sm-12"><h3><fmt:message key='text.resource' /></h3></div></div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb"
									for="recurring"><em>* </em><fmt:message key='text.recurring' /></label>
								<input type="text" class="form-control c39c" id="recurring">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb"
									for="resolutionAppl"><fmt:message key='text.resolutionAppl' /></label>
								<input type="text" class="form-control c39c" id="resolutionAppl">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb"
									for="datenoticeApplRs"><fmt:message key='text.datenoticeApplRs' /></label>
								<input type="hidden" id="datenoticeApplRs">
								<input type="text" class="form-control c39c" id="datenoticeApplRsFix"
									data-date="add" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb"
									for="interpositionRvwdate"><fmt:message key='text.interpositionRvwdate' /></label>
								<input type="hidden" id="interpositionRvwdate">
								<input type="text" class="form-control c39c" id="interpositionRvwdateFix"
									data-date="add" autocomplete="off">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15"
									for="dateadmissionApProc"><fmt:message key='text.dateadmissionApProc' /></label>
								<input type="hidden" id="dateadmissionApProc">
								<input type="text" class="form-control c39c" id="dateadmissionApProcFix"
									data-date="add" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15"
									for="daterefcollcourt"><fmt:message key='text.daterefcollcourt' /></label>
								<input type="hidden" id="daterefcollcourt">
								<input type="text" class="form-control c39c" id="daterefcollcourtFix"
									data-date="add" autocomplete="off">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15"
									for="resourcesent"><fmt:message key='text.resourcesent' /></label>
								<input type="text" class="form-control c39c" id="resourcesent" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15"
									for="dateadmissionCllCourt"><fmt:message key='text.dateadmissionCllCourt' /></label>
								<input type="hidden" id="dateadmissionCllCourt">
								<input type="text" class="form-control c39c" id="dateadmissionCllCourtFix"
									data-date="add" autocomplete="off">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15"
									for="notifdateadmissionCllCourt"><fmt:message key='text.notifdateadmissionCllCourt' /></label>
								<input type="hidden" id="notifdateadmissionCllCourt">
								<input type="text" class="form-control c39c" id="notifdateadmissionCllCourtFix"
									data-date="add" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="adhesiveRvwAplDate"><fmt:message key='text.adhesiveRvwAplDate' /></label>
								<input type="hidden" id="adhesiveRvwAplDate">
								<input type="text" class="form-control c39c" id="adhesiveRvwAplDateFix"
									data-date="add" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15" for="dateshiftpresent"><fmt:message key='text.dateshiftpresent' /></label>
								<input type="hidden" id="dateshiftpresent">
								<input type="text" class="form-control c39c" id="dateshiftpresentFix"
									data-date="add" autocomplete="off">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb"
									for="sessiondateprojectRslc"><fmt:message key='text.sessiondateprojectRslc' /></label>
								<input type="hidden" id="sessiondateprojectRslc">
								<input type="text" class="form-control c39c" id="sessiondateprojectRslcFix"
									data-date="add" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15"
									for="resolutiondate"><fmt:message key='text.resolutiondate' /></label>
								<input type="hidden" id="resolutiondate">
								<input type="text" class="form-control c39c" id="resolutiondateFix"
									data-date="add" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div id="areaNewRecUpload">
							<span class="textContent"><fmt:message key='label.dropzone' /></span>
							<div id='uploadXAddResource'
								class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
						</div>
					</div>
					<div>
						<a data-toggle="modal" data-target="#camera-modal"
							class="btn-opencam flex trn2ms asLink noselect"
							onclick="$('#targetDZ').val('#uploadXAddResource')"
							title="<fmt:message key='text.usecam.browser' />" >
							<i class="material-icons">camera_alt</i>
						</a>
					</div>
				</div>
				<button type="button" onclick="return addResource(this);" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="Custombox.close();" id="addResourceCancel" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.cancel" />
				</button>
			</form>
		</div>
	</div>
</div>