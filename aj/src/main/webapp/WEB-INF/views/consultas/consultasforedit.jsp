<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
	.btn-blue-light{margin-top:2px;background-color:#fff;border:1px solid #39c;border-radius:5px}
	.btn-blue-light:hover{color:#39c;-webkit-box-shadow:0 0 5px #ccc;box-shadow:0 0 5px #ccc}
	#edit-clients-modal{position:absolute;top:0;left:0;
		-webkit-box-shadow:0px 0 15px #444;box-shadow:0px 0 15px #444;z-index:10015
	}
	.tabsmodal{position:relative;clear:both;margin:auto;background:#fff;
		-webkit-box-shadow:initial;box-shadow:initial;z-index:1050
	}
	#edit-clients-modal div.firmdatatabs{display:block}
	#edit-clients-modal div.firmdatatabs:not(:first-child){display:none}
	.btnaddCircle{font-size:24px;position:absolute;width:40px;height:40px;top:0;right:30px;
		-webkit-border-radius:25px;-moz-border-radius:25px;border-radius:25px;
		-webkit-justify-content:center;justify-content:center;-webkit-align-items:center;align-items:center;
		-webkit-box-shadow:0px 0px 10px 3px #ddd;box-shadow:0px 0px 10px 3px #ddd;
		background-color:yellowgreen;color:#fff;cursor:pointer
	}
	.row{overflow:inherit !important}
</style>
<div class="row">
	<div class="col-xs-12 col-sm-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label for="edittrialConsList" class="supLlb"><fmt:message key='text.trial' /></label>
			<div class="form-group fieldinfo inlineblock w100p">
				<input type="text" class="form-control listfiltersel c39c" id="edittrialConsList"
					placeholder="<fmt:message key="text.select" />" >
				<i class="material-icons iconlistfilter">arrow_drop_down</i>
				<ul class="ddListImg noimgOnList" id="edittrialCons"></ul>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-4">
		<label for="editmatterCons" class="supLlb"><em>* </em><fmt:message key='text.matter' /></label>
		<div class="form-group fieldinfo inlineblock w100p">
			<input type="text" class="form-control listfiltersel c39c" id="editMatterCons"
				onfocus="optImgListIcon(this);" placeholder="<fmt:message key="text.select" />">
			<i class="material-icons iconlistfilter">arrow_drop_down</i>
			<ul class="ddListImg noimgOnList" id="editmatterCons"></ul>
		</div>
	</div>
		<div class="col-xs-12 col-md-4">
		<div class="form-group fieldinfo inlineblock w100p">
			<label for="editconsFees" class="supLlb"><fmt:message key='text.proposedfees' /></label>
			<input type="number" class="form-control listfiltersel c39c" id="editconsFees" min="0" placeholder="0.00">
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12">
		<div class="form-group fieldinfo inlineblock w100p">
			<label for="editconsultation" class="supLlb"><em>* </em><fmt:message key='text.consultations' /></label>
			<textarea class="form-control c39c" id="editconsultation"></textarea>
		</div>
	</div>
	<div class="col-xs-12">
		<div class="form-group fieldinfo inlineblock w100p">
			<label for="editopinionCons" class="supLlb"><fmt:message key='text.opinion' /></label>
			<textarea class="form-control c39c" id="editopinionCons"></textarea>
		</div>
	</div>
	<div class="col-xs-12">
		<div class="form-group fieldinfo inlineblock w100p">
			<label for="editresumeCons" class="supLlb"><fmt:message key='text.summary' /></label>
			<textarea class="form-control c39c" id="editresumeCons"></textarea>
		</div>
	</div>
</div>

<div class="row">
	<div id="areaEditConsUpload">
		<span class="textContent"><fmt:message key='label.dropzone' /></span>
		<div id='uploadXEditCons' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
	</div>
	<div>
		<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
			onclick="$('#targetDZ').val('#uploadXEditCons')" title="<fmt:message key='text.usecam.browser' />" >
			<i class="material-icons">camera_alt</i>
		</a>
	</div>
</div>