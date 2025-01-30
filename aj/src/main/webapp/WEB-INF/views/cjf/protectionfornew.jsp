<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row"><div class="col-sm-12"><h3><fmt:message key='text.selectcustchar' /></h3></div></div>
<div class="row">
	<div class="col-xs-6 col-sm-6 col-md-3">
		<div class="form-group fieldinfo inlineflex w100p">
			<input type="radio" name="custchar" id="custchar1" value="Quejoso" checked>
			<label for="custchar1"><fmt:message key='text.complaining' /></label>
		</div>
	</div>
	<div class="col-xs-6 col-sm-6 col-md-3">
		<div class="form-group fieldinfo inlineflex w100p">
			<input type="radio" name="custchar" id="custchar2" value="Tercero">
			<label for="custchar2"><fmt:message key='text.interestedtrdparty' /></label>
		</div>
	</div>
</div>

<div class="row" data-prot="dash"><div class="col-sm-12"><h3><em>* </em><fmt:message key="text.selecttrialAppealO" /></h3></div></div>
<div class="row" data-prot="dash">
	<div class="col-xs-6 col-sm-6 col-md-3">
		<div class="form-group fieldinfo inlineflex w100p">
			<input type="radio" name="origintype" id="origintype1" value="Juicio" checked>
			<label for="origintype1"><fmt:message key='text.trial' /></label>
		</div>
	</div>
	<div class="col-xs-6 col-sm-6 col-md-3">
		<div class="form-group fieldinfo inlineflex w100p">
			<input type="radio" name="origintype" id="origintype2" value="Apelación">
			<label for="origintype2"><fmt:message key='text.appeal' /></label>
		</div>
	</div>

	<div class="col-xs-6 col-sm-6 col-md-6" id="divappeal">
		<div class="form-group fieldinfo inlineblock w100p">
			<label for="appealList" class="supLlb"><em>* </em><fmt:message key='text.selectappealO' /></label>
			<div class="select-wrapper w100p">
				<select class="form-control ddsencillo c39c" id="appealList"></select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6" id="divtrialOrigin">
		<div class="form-group fieldinfo inlineflex w100p">
			<label class="supLlb" for="selecttrialO"><em>* </em><fmt:message key='text.selecttrialO' /></label>
			<div class="select-wrapper w100p">
				<select class="form-control ddsencillo c39c" id="selecttrialO"></select>
			</div>
		</div>
	</div>
</div>
<div class="row" style="margin-top:30px;margin-bottom:30px">
	<div class="col-xs-12 col-md-5">
		<div class="form-group inlineflex w100p">
			<label for="protection" class="supLlb"><fmt:message key='text.protectionnumber' /></label>
			<input type="text" class="form-control c39c" id="protection">
		</div>
	</div>
	<div class="col-xs-12">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="claimedact"><em>* </em><fmt:message key='text.claimedact' /></label>
			<input type="text" class="form-control c39c" id="claimedact">
		</div>
	</div>
</div>

<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.responsibleauthority' /></h3></div></div><br><br>
<div class="row">
	<div class="col-xs-8">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="respauth"><em>* </em><fmt:message key='text.responsibleauthority' /></label>
			<input type="text" class="form-control c39c" id="respauth">
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb t-15" for="dateclaimedact"><fmt:message key='text.dateclaimedact' /></label>
			<input type="hidden" id="dateclaimedact">
			<input type="text" class="form-control c39c" id="dateclaimedactFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-md-8">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb t-15" for="dateclaimedactNtn"><fmt:message key='text.dateclaimedactNtn' /></label>
			<input type="hidden" id="dateclaimedactNtn">
			<input type="text" class="form-control c39c" id="dateclaimedactNtnFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb t-15" for="filingdatelawsuit"><fmt:message key='text.filingdatelawsuit' /></label>
			<input type="hidden" id="filingdatelawsuit">
			<input type="text" class="form-control c39c" id="filingdatelawsuitFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-6 col-md-3">
		<label for="protState" class="supLlb"><fmt:message key='text.state' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c" onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="protState"></ul>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4">
		<label for="protCity" class="supLlb"><fmt:message key='text.city' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c" data-tip="add-dir-city-court"
				onfocus="getCitiesByState('protCity','ul',$('#protState li.selected').val());"
				placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="protCity"></ul>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-1 inlineflex quickTip trn3ms">
		<button type="button" data-tip="add-dir-city-court"><i class="material-icons">lightbulb_outline</i></button>
		<span data-tip="add-dir-city-court"><fmt:message key='tip.filtercourts' /></span>
	</div>
	<div class="col-xs-12 col-sm-6 col-md-4">
		<label for="demandprotsent" class="supLlb"><fmt:message key='text.demandprotsent' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c" data-tip="add-dir-city-court"
				onfocus="getAllCourtsByCity('demandprotsent','ul',$('#protCity li.selected').val(), 'unitarios,colegiados');"
				placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="demandprotsent"></ul>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="admissiondate"><fmt:message key='text.admissiondate' /></label>
			<input type="hidden" id="admissiondate">
			<input type="text" class="form-control c39c" id="admissiondateFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb t-15" for="admissionnotifdate"><fmt:message key='text.admissionnotifdate' /></label>
			<input type="hidden" id="admissionnotifdate">
			<input type="text" class="form-control c39c" id="admissionnotifdateFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb t-15" for="stay"><fmt:message key='text.stay' /></label>
			<input type="text" class="form-control c39c" id="stay">
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="adhesiveaDirPtDate"><fmt:message key='text.adhesiveaDirPtDate' /></label>
			<input type="hidden" id="adhesiveaDirPtDate">
			<input type="text" class="form-control c39c" id="adhesiveaDirPtDateFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb t-15" for="dateshiftpresent"><fmt:message key='text.dateshiftpresent' /></label>
			<input type="hidden" id="dateshiftpresent">
			<input type="text" class="form-control c39c" id="dateshiftpresentFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
</div>
<hr>
<div class="row">
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="projectjudgmentdate"><fmt:message key='text.projectjudgmentdate' /></label>
			<input type="hidden" id="projectjudgmentdate">
			<input type="text" class="form-control c39c" id="projectjudgmentdateFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="judgmentdate"><fmt:message key='text.judgmentdate' /></label>
			<input type="hidden" id="judgmentdate">
			<input type="text" class="form-control c39c" id="judgmentdateFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="judgmentnotifdate"><fmt:message key='text.judgmentnotifdate' /></label>
			<input type="hidden" id="judgmentnotifdate">
			<input type="text" class="form-control c39c" id="judgmentnotifdateFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label class="supLlb" for="reviewresourcedate"><fmt:message key='text.reviewresourcedate' /></label>
			<input type="hidden" id="reviewresourcedate">
			<input type="text" class="form-control c39c" id="reviewresourcedateFix" data-date="add" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
		</div>
	</div>
</div>
<hr>
<div class="row">
	<div id="areaNewProtUpload"><span class="textContent">
		<fmt:message key='label.dropzone' /></span>
		<div id='uploadXProt' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
	</div>
</div>
<div>
	<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
	onclick="$('#targetDZ').val('#uploadXProt')" title="<fmt:message key='text.usecam.browser' />" >
		<i class="material-icons">camera_alt</i>
	</a>
</div>