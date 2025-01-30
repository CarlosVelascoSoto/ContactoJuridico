<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
	.tabsmodal{position:relative;clear:both;margin:auto;background:#fff;
		-webkit-box-shadow:initial;box-shadow:initial;z-index:1050
	}
	#add-consulting-modal{display:none}
	.uppermodal{z-index:10017}
</style>
<script>
	function togglemodtab(e,tab){
		if($(e).hasClass('selectedtab'))return;
		$('.tabsmodal li').removeClass('selectedtab');
		if(typeof e=='string')
			e=$(e+' li:first-child');
		$(e).addClass('selectedtab');
		$(tab).show();
	};
</script>

<div class="form-group m-0">
	<div class="row container-tabsmodal m-0">
		<ul id="clientmodaltabs" class="tabsmodal">
			<li class="selectedtab" onclick="togglemodtab(this,'#tab-add-basic-info');"><fmt:message key="text.generaldata" /></li>
			<li class="trn2ms" onclick="togglemodtab(this,'#tab-add-contact-ways');"><fmt:message key="text.contactways" /></li>
			<li class="trn2ms" onclick="togglemodtab(this,'#tab-add-additional');"><fmt:message key="text.additionalinfo" /></li>
		</ul>
	</div>
</div>
<div class="form-group">
	<div id="tab-add-basic-info" class="firmdatatabs p-20">
		<div class="row">
			<div class="col-xs-12">
				<div class="form-group inlineflex w100p">
					<label for="clieName" class="supLlb"><em>* </em><fmt:message key='text.clientname' /></label>
					<input type="text" class="form-control c39c" id="clieName" autocomplete="off">
				</div>
			</div>
			<div class="col-xs-12">
				<div class="form-group inlineflex w100p">
					<label for="clieTypeofperson" class="supLlb"><em>* </em><fmt:message key='text.typeofperson' /></label>
					<div class="select-wrapper w100p">
						<select class="form-control ddsencillo c39c" id="clieTypeofperson">
							<option value="" selected disabled><fmt:message key='text.select' /></option>
							<option value="1"><fmt:message key='text.individualperson' /></option>
							<option value="2"><fmt:message key='text.corporationperson' /></option>
						</select>
					</div>
				</div>
			</div>
			<div class="col-xs-12">
				<div class="form-group inlineflex w100p">
					<label for="clieEmail" class="supLlb"><em>* </em><fmt:message key='text.email' /></label>
					<input type="text" class="form-control c39c" id="clieEmail" autocomplete="off">
				</div>
			</div>
			<div class="col-xs-12">
				<div class="form-group inlineflex w100p">
					<label for="clieAddress1" class="supLlb"><em>* </em><fmt:message key='text.address' /></label>
					<input type="text" class="form-control c39c" id="clieAddress1" autocomplete="off">
				</div>
			</div>
			<div class="row">
					<div class="col-xs-12 col-sm-6">
						<label for="clieCountry" class="supLlb"><fmt:message key='text.country' /></label>
						<div class="form-group fieldinfo inlineblock w100p">
							<input type="text" class="form-control listfiltersel c39c" onfocus="optImgListIcon(this);"
								placeholder="<fmt:message key="text.select" />">
							<i class="material-icons iconlistfilter">arrow_drop_down</i>
							<ul class="ddListImg noimgOnList" id="clieCountry"></ul>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<label for="clieState" class="supLlb"><fmt:message key='text.state' /></label>
						<div class="form-group fieldinfo inlineblock w100p">
							<input type="text" class="form-control listfiltersel c39c"
								onfocus="optImgListIcon(this);getStatesByCountry('clieCountry','clieState','ul');"
								placeholder="<fmt:message key="text.select" />">
							<i class="material-icons iconlistfilter">arrow_drop_down</i>
							<ul class="ddListImg noimgOnList" id="clieState"></ul>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<label for="clieCity" class="supLlb"><fmt:message key='text.city' /></label>
						<div class="form-group fieldinfo inlineblock w100p">
							<input type="text" class="form-control listfiltersel c39c" data-tip="add-dir-city-court"
								onfocus="getCitiesByState('clieCity','ul',$('#clieState li.selected').val());"
								placeholder="<fmt:message key="text.select" />">
							<i class="material-icons iconlistfilter">arrow_drop_down</i>
							<ul class="ddListImg noimgOnList" id="clieCity"></ul>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group inlineflex w100p">
							<label for="clieCp" class="supLlb"><fmt:message key='text.zipcode' /></label>
							<input type="text" class="form-control c39c" id="clieCp" autocomplete="off">
						</div>
					</div>
				</div>
			<div class="col-xs-12">
				<div class="form-group inlineflex w100p">
					<label class="supLlb" for="clieRel_with"><fmt:message key='text.rel_with' /></label>
					<input type="text" class="form-control c39c" id="clieRel_with" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
				</div>
			</div>
			<div class="col-xs-12">
				<div class="form-group inlineflex w100p">
					<label class="supLlb" for="clieRef_by"><fmt:message key='text.ref_by' /></label>
					<input type="text" class="form-control c39c" id="clieRef_by" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
				</div>
			</div>
		</div>
	</div>

	<div id="tab-add-contact-ways" class="firmdatatabs p-20">
		<div class="row">
			<div class="col-xs-12 col-sm-6">
				<div class="form-group fieldinfo inlineblock w100p">
					<label class="supLlb" for="clieCellphone"><fmt:message key='text.cellphone' /></label>
					<input type="text" class="form-control c39c" id="clieCellphone" autocomplete="off">
				</div>
			</div>
			<div class="col-xs-12 col-sm-6">
				<div class="form-group inlineflex w100p">
					<label for="cliePhone" class="supLlb"><fmt:message key='text.phone' /></label>
					<input type="text" class="form-control c39c" id="cliePhone" autocomplete="off">
				</div>
			</div>
			<div class="col-xs-12">
				<div class="form-group inlineflex w100p">
					<label class="supLlb" for="clieContactperson"><fmt:message key='text.contactperson' /></label>
					<input type="text" class="form-control c39c" id="clieContactperson" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<div class="form-group inlineflex w100p">
					<label class="supLlb" for="clieWebpage"><fmt:message key='text.website' /></label>
					<input type="text" class="form-control c39c" id="clieWebpage" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<h3 class="capitalize"><fmt:message key="text.socialnetworks"/></h3>
				<div class="inlineflex btnaddCircle" onclick="modalsn('add','OnNew');">+</div>
			</div>
			<div class="col-xs-12">
				<div class="card-box table-responsive" style="padding:0">
					<table id="clieSnTableListOnNew" data-order='[[0,"desc"]]' class="table table-striped table-bordered table-responsive">
						<thead>
							<tr>
								<th><fmt:message key="text.socialnetwork" /></th>
								<th><fmt:message key="text.address" /></th>
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

	<div id="tab-add-additional" class="firmdatatabs p-20">
		<div class="row">
			<div class="col-xs-12 col-sm-4">
				<div class="form-group fieldinfo inlineblock w100p">
					<label class="supLlb" for="clieBirthdate"><fmt:message key='text.birthday' /></label>
					<input type="hidden" id="clieBirthdate">
					<input type="text" class="form-control c39c" id="clieBirthdateFix"
						data-date="addClient" autocomplete="off">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<div class="form-group inlineflex w100p">
					<label for="clieComments" class="supLlb"><fmt:message key='text.comments' /></label>
					<textarea class="form-control c39c txArea4r" id="clieComments"></textarea>
				</div>
			</div>
<h3 class="capitalize dnone"><fmt:message key="text.profilepicture"/></h3>
<div class="row dnone">
				<div id="areaNewClientUpload">
					<div id='uploadXClientNew' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<input type="hidden" id="reeditsn">
<div id="addSN-OnNew-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" data-bs-backdrop='static'
	aria-labelledby="addSN-OnNew-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog" style="-webbkit-box-shadow:0 0 15px #000;box-shadow:0 0 15px #000;background-color:#fff">
		<div class="modal-header">
			<button type="button" class="close" onclick="$('#addSN-OnNew-modal').modal('hide');" aria-hidden="true">&times;</button>
			<h4 class="modal-title capitalize"><fmt:message key="text.addsocialnetwork" /></h4>
		</div>
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12">
						<div class="form-group inlineflex w100p">
							<label for="addSnListOnNew" class="supLlb"><fmt:message key='text.socialnetwork' /></label>
							<div class="select-wrapper w100p">
								<select class="form-control ddsencillo c39c" id="addSnListOnNew"></select>
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<div class="form-group inlineflex w100p">
							<label for="addSnAddressOnNew" class="supLlb"><fmt:message key='text.address' /></label>
							<input type="text" class="form-control c39c" id="addSnAddressOnNew" autocomplete="off" pattern="^[\S]*$">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" onclick="fnAddSN('add','OnNew')" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.additem" />
			</button>
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10"
				onclick="$('#addSN-OnNew-modal').modal('hide');">
				<fmt:message key="button.close" />
			</button>
		</div>
	</div>
</div>

<div id="editSN-OnNew-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" data-bs-backdrop='static'
	aria-labelledby="editSN-OnNew-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog" style="-webbkit-box-shadow:0 0 15px #000;box-shadow:0 0 15px #000;background-color:#fff">
		<div class="modal-header">
			<button type="button" class="close" onclick="$('#editSN-OnNew-moda').modal('hide');" aria-hidden="true">&times;</button>
			<h4 class="modal-title"><fmt:message key="text.edit" /> <fmt:message key="text.socialnetwork" /></h4>
			<input type="hidden" id="originalSNIdOnNew">
		</div>
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12">
						<div class="form-group inlineflex w100p">
							<label for="editSnListOnNew" class="supLlb"><fmt:message key='text.socialnetwork' /></label>
							<div class="select-wrapper w100p">
								<select class="form-control ddsencillo c39c" id="editSnListOnNew"></select>
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<div class="form-group inlineflex w100p">
							<label for="editSnAddressOnNew" class="supLlb"><fmt:message key='text.address' /></label>
							<input type="text" class="form-control c39c" id="editSnAddressOnNew" autocomplete="off" pattern="^[\S]*$">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" onclick="fnAddSN('edit','OnNew')" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.ok" />
			</button>
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10"
				onclick="$('#editSN-OnNew-moda').modal('hide');">
				<fmt:message key="button.close" />
			</button>
		</div>
	</div>
</div>