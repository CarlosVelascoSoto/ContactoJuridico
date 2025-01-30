<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="row"><div class="col-sm-12"><h3><fmt:message key='text.selectcustchar' /></h3></div></div>
<div class="row">
	<div class="col-xs-6 col-sm-4 col-md-2">
		<p style="margin-left:10px;"><fmt:message key="text.clientis" />: </p>
	</div>
	<div class="col-xs-6 col-sm-4 col-md-2">
		<div class="form-group fieldinfo inlineflex w100p">
			<input type="radio" name="custQ3" id="custQ3_q" value="<fmt:message key='text.complaining' />" checked>
			<label for="custQ3_q" style="padding:2px 0 0 10px"><fmt:message key='text.complaining' /></label>
		</div>
	</div>
	<div class="col-xs-6 col-sm-4 col-md-3">
		<div class="form-group fieldinfo inlineflex w100p">
			<input type="radio" name="custQ3" id="custQ3_3"value="<fmt:message key='text.interestedtrdparty' />">
			<label for="custQ3_3" style="padding:2px 0 0 10px"><fmt:message key="text.interestedtrdparty" /></label>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-md-6 dnone" id="opCustQ3_q">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="complaining"><fmt:message key='text.complaining' /></label>
			<input type="text" class="form-control c39c" id="complaining">
		</div>
	</div>
	<div class="col-xs-12 col-md-6" id="opCustQ3_3">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="interestedtrdparty"><fmt:message key='text.interestedtrdparty' /></label>
			<input type="text" class="form-control c39c" id="interestedtrdparty">
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="indclaimedact"><fmt:message key='text.claimedact' /></label>
			<input type="text" class="form-control c39c" id="indclaimedact">
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-4">
		<div class="form-group inlineflex w100p">
			<label for="indprotection" class="supLlb"><fmt:message key='text.protectionnumber' /></label>
			<input type="text" class="form-control c39c" id="indprotection">
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-7">
		<div class="form-group fieldinfo inlineflex w100p">
			<label for="indOrigintype" class="supLlb"><em>* </em><fmt:message key="text.originclaimedact" /></label>
			<div class="select-wrapper w100p">
				<select class="form-control ddsencillo c39c" id="indOrigintype">
					<option value="" selected disabled><fmt:message key='text.select'/></option>
					<option value="0"><fmt:message key="text.thisprotnottrialappeal" /></option>
					<option value="1"><fmt:message key="text.protectionderivestrial" /></option>
					<option value="2"><fmt:message key="text.protectionderivesappeal" /></option>
				</select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-5 dnone" id="divAppealOrigin">
		<div class="form-group fieldinfo inlineblock w100p">
			<label for="indAppealList" class="supLlb"><em>* </em><fmt:message key='text.selectappealO' /></label>
			<div class="select-wrapper w100p">
				<select class="form-control ddsencillo c39c" id="indAppealList"></select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-5 dnone" id="divTrialOrigin">
		<div class="form-group fieldinfo inlineflex w100p">
			<label class="supLlb" for="indSelecttrialO"><em>* </em><fmt:message key='text.selecttrialO' /></label>
			<div class="select-wrapper w100p">
				<select class="form-control ddsencillo c39c" id="indSelecttrialO"></select>
			</div>
		</div>
	</div>
</div>

<div class="row"><div class="col-xs-12 col-sm-12"><h3 style="margin:20px 0"><fmt:message key='text.responsibleauthority' /></h3></div></div>
<div class="row">
	<div class="col-xs-12">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="indrespauth"><em>* </em><fmt:message key='text.responsibleauthority' /></label>
			<input type="text" class="form-control c39c" id="indrespauth">
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-6 col-md-8">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb t-15" for="indDateclaimedactNtn"><fmt:message key='text.dateclaimedactNtn' /></label>
			<input type="hidden" id="indDateclaimedactNtn">
			<input type="text" class="form-control c39c" id="indDateclaimedactNtnFix" data-date="add" autocomplete="off">
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb t-15" for="indFilingdatelawsuit"><fmt:message key='text.filingdatelawsuit' /></label>
			<input type="hidden" id="indFilingdatelawsuit">
			<input type="text" class="form-control c39c" id="indFilingdatelawsuitFix" data-date="add" autocomplete="off">
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label for="indState" class="supLlb"><fmt:message key='text.state' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c"
				onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="indState"></ul>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4">
		<label for="indCity" class="supLlb"><fmt:message key='text.city' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c" data-tip="add-ind-city-court"
				onfocus="getCitiesByState('indCity','ul',$('#indState li.selected').val());"
				placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="indCity"></ul>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-1 inlineflex quickTip trn3ms">
		<button type="button" data-tip="add-ind-city-court"><i class="material-icons">lightbulb_outline</i></button>
		<span data-tip="add-ind-city-court"><fmt:message key='tip.filtercourts' /></span>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4">
		<label for="indDemandprotsent" class="supLlb"><fmt:message key='text.demandprotsent' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c" data-tip="add-ind-city-court"
				onfocus="getAllCourtsByCity('indDemandprotsent','ul',$('#indCity li.selected').val(), 'unitarios,colegiados,federales');"
				placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="indDemandprotsent"></ul>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="autoadmissiondate"><fmt:message key='text.autoadmissiondate' /></label>
			<input type="hidden" id="autoadmissiondate">
			<input type="text" class="form-control c39c" id="autoadmissiondateFix" data-date="add"
				title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb t-15" for="indAdmissionnotifdate"><fmt:message key='text.admissionnotifdate' /></label>
			<input type="hidden" id="indAdmissionnotifdate">
			<input type="text" class="form-control c39c" id="indAdmissionnotifdateFix" data-date="add"
				title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="consthearingdate"><fmt:message key='text.consthearingdate' /></label>
			<input type="hidden" id="consthearingdate">
			<input type="text" class="form-control c39c" id="consthearingdateFix" data-date="add"
				title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="judgment"><fmt:message key='text.judgment' /></label>
			<input type="text" class="form-control c39c" id="judgment" autocomplete="off">
		</div>
	</div>
	
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="rsrcreviewjudgment"><fmt:message key='text.rsrcreviewjudgment' /></label>
			<input type="text" class="form-control c39c" id="rsrcreviewjudgment" autocomplete="off">
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="provsusp"><fmt:message key='text.provsusp' /></label>
			<input type="text" class="form-control c39c" id="provsusp" autocomplete="off">
		</div>
	</div>
	
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="incidentalhearingdate"><fmt:message key='text.incidentalhearingdate' /></label>
			<input type="hidden" id="incidentalhearingdate">
			<input type="text" class="form-control c39c" id="incidentalhearingdateFix" data-date="add"
				title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="adjournmentjudgment"><fmt:message key='text.adjournmentjudgment' /></label>
			<input type="text" class="form-control c39c" id="adjournmentjudgment" autocomplete="off">
		</div>
	</div>
</div>

<div class="row">
	<div id="areaNewIndProtUpload"><span class="textContent"><fmt:message key='label.dropzone' /></span>
		<div id='uploadXIndProt' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
	</div>
</div>
<div>
	<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
	onclick="$('#targetDZ').val('#uploadXIndProt')" title="<fmt:message key='text.usecam.browser' />" >
		<i class="material-icons">camera_alt</i>
	</a>
</div>