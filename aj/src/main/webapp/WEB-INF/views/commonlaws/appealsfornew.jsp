<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.selectcustchar' /></h3></div></div>
	<div class="row">
		<div class="col-xs-6 col-sm-6 col-md-3">
			<div class="form-group fieldinfo inlineflex w100p">
				<input type="radio" name="custtype" id="custtype1" value="Apelado" checked>
				<label for="custtype1"><fmt:message key='text.respondent' /></label>
			</div>
		</div>
		<div class="col-xs-6 col-md-3">
			<div class="form-group fieldinfo inlineflex w100p">
				<input type="radio" name="custtype" id="custtype2" value="Apelante">
				<label for="custtype2"><fmt:message key='text.appellant' /></label>
			</div>
		</div>
	</div>

	<div class="row" data-v="trial"><div class="col-xs-12"><h3><fmt:message key='text.selecttrialO' /></h3></div></div>
	<div class="row" data-v="trial">
		<div class="col-xs-12">
			<div class="form-group fieldinfo inlineblock w100p">
				<label for="trialApl" class="supLlb"><em>* </em><fmt:message key='text.trial' /></label>
				<div class="form-group fieldinfo inlineblock w100p">
					<input type="text" class="form-control listfiltersel c39c" id="trialAplList"
						placeholder="<fmt:message key="text.select" />" >
				<i class="material-icons iconlistfilter">arrow_drop_down</i>
				<ul class="ddListImg noimgOnList" id="trialApl"></ul>
			</div>
		</div>
	</div>
</div>

<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.appealidentifier' /></h3></div></div>
<div class="row">
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label for="handle" class="supLlb"><fmt:message key='text.handle' /></label>
			<input type="text" class="form-control c39c" id="handle">
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<label for="roomApl" class="supLlb"><em>* </em><fmt:message key='text.room' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c"
				onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="roomApl"></ul>
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<label for="matterApl" class="supLlb"><em>* </em><fmt:message key='text.matter' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c"
				onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="matterApl"></ul>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-6">
		<label for="aplState" class="supLlb"><fmt:message key='text.state' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c"
				onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="aplState"></ul>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6">
		<label for="aplCity" class="supLlb"><em>* </em><fmt:message key='text.city' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c"
				onfocus="getCitiesByState('aplCity','ul','aplState');"
				placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="aplCity"></ul>
		</div>
	</div>
</div>

<div class="row"><div class="col-xs-12"><h3><fmt:message key='text.parties' /></h3></div></div>
<div class="row">
	<div class="col-xs-12 col-md-8">
		<div class="form-group fieldinfo inlineblock w100p">
			<label for="speaker" class="supLlb"><em>* </em><fmt:message key='text.speaker' /></label>
			<input type="text" class="form-control c39c" id="speaker">
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock">
			<label for="apladhesiva" class="supLlb"><em>* </em><fmt:message key='text.adhesiveappeal' /></label>
			<div class="select-wrapper w100p">
				<select class="form-control ddsencillo c39c" id="apladhesiva">
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
			<label for="resolution" class="supLlb"><fmt:message key='text.resolution' /></label>
			<input type="text" class="form-control c39c" id="resolution">
		</div>
	</div>
</div>

<div class="row">
	<div id="areaAppealUpload"><span class="textContent">
		<fmt:message key='label.dropzone' /></span>
		<div id='uploadXAppeal' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
	</div>
	<div>
		<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
		onclick="$('#targetDZ').val('#uploadXAppeal')" title="<fmt:message key='text.usecam.browser' />" >
			<i class="material-icons">camera_alt</i>
		</a>
	</div>
</div>