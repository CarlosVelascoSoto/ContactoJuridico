<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<title><fmt:message key="text.clients"/> - Contacto jur&iacute;dico</title>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />
<c:set var="userrole" scope="page" value="${userrole}" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker-v1.10/css/bootstrap-datepicker3.min.css">

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

<style>
	.load-img{width:100%;max-width:100%;max-height:100%;justify-content:center;align-items:center;border:1px solid #ccc}
	.load-input{padding:10px}
	.dz-upload {display: block;height: 20px;margin: 0;padding: 0;text-align:center;
	    -webkit-box-shadow:inset 0 1px 0 rgba(255,255,255,0.5);-moz-box-shadow:inset 0 1px 0 rgba(255,255,255,0.5);box-shadow:inset 0 1px 0 rgba(255,255,255,0.5);
	    -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;
	    border: 1px solid #0078a5;background-color: #5C9ADE;
	    background: -moz-linear-gradient(top, #00adee 10%, #0078a5 90%);
	    background: -webkit-gradient(linear, left top, left bottom, color-stop(0.1, #00adee), color-stop(0.9, #0078a5));
	    color: #ffffff
	}
	.tabsmodal{position:relative;clear:both;margin:auto;background:#fff;
		-webkit-box-shadow:initial;box-shadow:initial;z-index:1050
	}
	.tabsmodal{position:relative;clear:both;margin:auto;background:#fff;
		-webkit-box-shadow:initial;box-shadow:initial;z-index:1050
	}
	.close{color:#000 !important}
	#add-consulting-modal{display:none}
	.uppermodal{z-index:10017}
	#edit-clients-modal,#errorOnAddClient,#errorOnEditClient,#editSN-OnNew-modal,#editSN-OnEdit-modal{display:none}

	.btnaddCircle{font-size:24px;position:absolute;width:40px;height:40px;top:0;right:30px;
		-webkit-border-radius:25px;-moz-border-radius:25px;border-radius:25px;
		-webkit-justify-content:center;justify-content:center;-webkit-align-items:center;align-items:center;
		-webkit-box-shadow:0px 0px 10px 3px #ddd;box-shadow:0px 0px 10px 3px #ddd;
		background-color:yellowgreen;color:#fff;cursor:pointer}
	.uppermodal{z-index:10017}
	.dd-option-image{max-width:34px}
</style>
<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addNewClient" data-animation="fadein" data-plugin="custommodal"
 								data-overlaySpeed="200" data-overlayColor="#36404a"
 								class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.client" /></h4>
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
								onclick="cleanTableFilters(this)">
								<span class="glyphicon glyphicon-filter"></span><i class="material-icons">close</i>
							</button>
						</div>
						<table id="datatable-buttons" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.client" /></th>
									<th><fmt:message key="text.address" /></th>
									<th><fmt:message key="text.city" /></th>
									<th><fmt:message key="text.state" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="cl" items="${clientsList}">
								<tr>
									<td>${cl.client}</td>
									<td>${cl.address1}</td>
									<td>${cl.city}</td>
									<td>${cl.state}</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${cl.clientid}" data-toggle="modal" data-target="#edit-clients-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsToEdit(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" id="${cl.clientid}" onclick="deleteClient(id);"><i class="md md-close"></i></a>
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

<div id="add-clients-modal" class="modal-demo">
	<button type="button" class="close" onclick="$('#add-clients-modal').hide();Custombox.close();"
		style="position:relative;margin:0;color:#fff">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key='text.client' /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<div class="col-xs-12">
				<div style="display:none;" id="errorOnAddClient" class="alert alert-danger fade in">
				<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddClient').toggle();">&times;</a>
				<p id="putErrorOnAddClient"></p>
				</div>
			</div>
			<form id="formNewClient">
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
					<input type="tel" class="form-control c39c" id="clieCellphone" autocomplete="off">
				</div>
			</div>
			<div class="col-xs-12 col-sm-6">
				<div class="form-group inlineflex w100p">
					<label for="cliePhone" class="supLlb"><fmt:message key='text.phone' /></label>
					<input type="tel" class="form-control c39c" id="cliePhone" autocomplete="off">
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
					<input type="url" class="form-control c39c" id="clieWebpage" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
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
<div id="addSN-OnNew-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" aria-labelledby="addSN-OnNew-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog" style="-webbkit-box-shadow:0 0 15px #000;box-shadow:0 0 15px #000;background-color:#fff">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
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
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
				<fmt:message key="button.close" />
			</button>
		</div>
	</div>
</div>

<div id="editSN-OnNew-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" aria-labelledby="editSN-OnNew-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog" style="-webbkit-box-shadow:0 0 15px #000;box-shadow:0 0 15px #000;background-color:#fff">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
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
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
				<fmt:message key="button.close" />
			</button>
		</div>
	</div>
</div>
			</form>
			<button type="button" onclick="return addClients('');" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10"
				data-dismiss="modal" aria-hidden="true" onclick="$('#add-clients-modal').hide();Custombox.close();">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-clients-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-clients-modal" aria-hidden="true" style="overflow:auto">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="cleanFields" data-reset="#edit-clients-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
					<span class="glyphicon glyphicon-erase asLink"></span>
				</button>
				<button type="button" class="close closeModal" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title"><fmt:message key="client.update" /></h4>
			</div>
			<div class="modal-body">
				<div class="col-xs-12">
					<div id="errorOnEditClient" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEditClient').toggle();">&times;</a>
						<p id="putErrorOnEditClient"></p>
						<input type="hidden" id="edCliId">
					</div>
				</div>
				<form id="formEditClient">
					<div class="form-group m-0">
						<div class="row container-tabsmodal m-0">
							<ul id="edClientmodaltabs" class="tabsmodal">
								<li class="selectedtab" onclick="togglemodtab(this,'#tab-edit-basic-info');"><fmt:message key="text.generaldata" /></li>
								<li class="trn2ms" onclick="togglemodtab(this,'#tab-edit-contact-ways');"><fmt:message key="text.contactways" /></li>
								<li class="trn2ms" onclick="togglemodtab(this,'#tab-edit-additional');"><fmt:message key="text.additionalinfo" /></li>
							</ul>
						</div>
					</div>
					<div class="form-group">
						<div id="tab-edit-basic-info" class="firmdatatabs p-20">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group inlineflex w100p">
										<label for="edClieName" class="supLlb"><em>* </em><fmt:message key='text.clientname' /></label>
										<input type="text" class="form-control c39c" id="edClieName" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group inlineflex w100p">
										<label for="edClieTypeofperson" class="supLlb"><em>* </em><fmt:message key='text.typeofperson' /></label>
										<div class="select-wrapper w100p">
											<select class="form-control ddsencillo c39c" id="edClieTypeofperson">
												<option value="" selected disabled><fmt:message key='text.select' /></option>
												<option value="1"><fmt:message key='text.individualperson' /></option>
												<option value="2"><fmt:message key='text.corporationperson' /></option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group inlineflex w100p">
										<label for="edClieEmail" class="supLlb"><em>* </em><fmt:message key='text.email' /></label>
										<input type="text" class="form-control c39c" id="edClieEmail" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group inlineflex w100p">
										<label for="edClieAddress1" class="supLlb"><em>* </em><fmt:message key='text.address' /></label>
										<input type="text" class="form-control c39c" id="edClieAddress1" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group inlineflex w100p">
										<label class="supLlb" for="edClieRel_with"><fmt:message key='text.rel_with' /></label>
										<input type="text" class="form-control c39c" id="edClieRel_with" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group inlineflex w100p">
										<label class="supLlb" for="edClieRef_by"><fmt:message key='text.ref_by' /></label>
										<input type="text" class="form-control c39c" id="edClieRef_by" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group inlineflex w100p">
										<label for="edClieStatus" class="supLlb"><em>* </em><fmt:message key='text.status' /></label>
										<div class="select-wrapper w100p">
											<select class="form-control ddsencillo c39c" id="edClieStatus">
												<option value="" selected disabled><fmt:message key='text.select' /></option>
												<option value="1"><fmt:message key='status.active' /></option>
												<option value="2"><fmt:message key='status.desactive' /></option>
												<option value="3"><fmt:message key='status.suspended' /></option>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
					
						<div id="tab-edit-contact-ways" class="firmdatatabs p-20">
							<div class="row">
								<div class="col-xs-12 col-sm-6">
									<div class="form-group fieldinfo inlineblock w100p">
										<label class="supLlb" for="edClieCellphone"><fmt:message key='text.cellphone' /></label>
										<input type="tel" class="form-control c39c" id="edClieCellphone" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<div class="form-group inlineflex w100p">
										<label for="edCliePhone" class="supLlb"><fmt:message key='text.phone' /></label>
										<input type="tel" class="form-control c39c" id="edCliePhone" autocomplete="off">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group inlineflex w100p">
										<label class="supLlb" for="edClieContactperson"><fmt:message key='text.contactperson' /></label>
										<input type="text" class="form-control c39c" id="edClieContactperson" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group inlineflex w100p">
										<label class="supLlb" for="edClieWebpage"><fmt:message key='text.website' /></label>
										<input type="url" class="form-control c39c" id="edClieWebpage" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<h3 class="capitalize"><fmt:message key="text.socialnetworks"/></h3>
									<div class="inlineflex btnaddCircle" onclick="modalsn('edit','OnEdit');">+</div>
								</div>
								<div class="col-xs-12">
									<div class="card-box table-responsive p-0">
										<table id="clieSnTableListOnEdit" data-order='[[0,"desc"]]'
											class="table table-striped table-bordered table-responsive">
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
					
						<div id="tab-edit-additional" class="firmdatatabs p-20">
							<div class="row">
								<div class="col-xs-12 col-sm-6">
									<label for="edClieCountry" class="supLlb"><fmt:message key='text.country' /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel c39c" onfocus="optImgListIcon(this);"
											placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="edClieCountry"></ul>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<label for="edClieState" class="supLlb"><fmt:message key='text.state' /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel c39c"
											onfocus="optImgListIcon(this);getStatesByCountry('edClieCountry','edClieState','ul');"
											placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="edClieState"></ul>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<label for="edClieCity" class="supLlb"><fmt:message key='text.city' /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel c39c" data-tip="edit-dir-city-court"
											onfocus="getCitiesByState('edClieCity','ul',$('#edClieState li.selected').val());"
											placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="edClieCity"></ul>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6">
									<div class="form-group inlineflex w100p">
										<label for="edClieCp" class="supLlb"><fmt:message key='text.zipcode' /></label>
										<input type="text" class="form-control c39c" id="edClieCp" autocomplete="off">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 col-sm-4">
									<div class="form-group fieldinfo inlineblock w100p">
										<label class="supLlb" for="edClieBirthdate"><fmt:message key='text.birthday' /></label>
										<input type="hidden" id="edClieBirthdate">
										<input type="text" class="form-control c39c" id="edClieBirthdateFix"
											data-date="editClient" autocomplete="off">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group inlineflex w100p">
										<label for="edClieComments" class="supLlb"><fmt:message key='text.comments' /></label>
										<textarea class="form-control c39c txArea4r" id="edClieComments"></textarea>
									</div>
								</div>
								<h3 class="capitalize"><fmt:message key="text.profilepicture"/></h3>
								<div class="row">
									<div id="areaEditClientUpload">
										<div id='uploadXClientEdit'
											class="dz-default dz-message file-dropzone text-center well col-sm-12">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateClient();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>



<!-- ed/add -->
<input type="hidden" id="edReeditsn">





<div id="editSN-OnNew-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" aria-labelledby="editSN-OnNew-modal" aria-hidden="true">
	<div class="modal-dialog" style="-webbkit-box-shadow:0 0 15px #000;box-shadow:0 0 15px #000;background-color:#fff">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title capitalize"><fmt:message key="text.addsocialnetwork" /></h4>
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
			<button type="button" onclick="fnAddSN('add','OnEdit')" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.additem" />
			</button>
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
				<fmt:message key="button.close" />
			</button>
		</div>
	</div>
</div>

<div id="editSN-OnEdit-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" aria-labelledby="editSN-OnEdit-modal" aria-hidden="true">
	<div class="modal-dialog" style="-webbkit-box-shadow:0 0 15px #000;box-shadow:0 0 15px #000;background-color:#fff">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title"><fmt:message key="text.edit" /> <fmt:message key="text.socialnetwork" /></h4>
			<input type="hidden" id="originalSNIdOnEdit">
		</div>
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12">
						<div class="form-group inlineflex w100p">
							<label for="editSnListOnEdit" class="supLlb"><fmt:message key='text.socialnetwork' /></label>
							<div class="select-wrapper w100p">
								<select class="form-control ddsencillo c39c" id="editSnListOnEdit"></select>
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<div class="form-group inlineflex w100p">
							<label for="editSnAddressOnEdit" class="supLlb"><fmt:message key='text.address' /></label>
							<input type="text" class="form-control c39c" id="editSnAddressOnEdit" autocomplete="off" pattern="^[\S]*$">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" onclick="fnAddSN('edit','OnEdit')" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.ok" />
			</button>
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
				<fmt:message key="button.close" />
			</button>
		</div>
	</div>
</div>

<script src="resources/assets/js/jquery.min.js"></script>
<script src="resources/assets/plugins/bootstrap-datepicker-v1.10/js/bootstrap-datepicker.min.js"></script>

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
<script src="resources/assets/js/customDropZoneImg.js"></script>
<script src="resources/assets/js/complementos.js"></script>
<script src="resources/local/catalogs/clients.js"></script>

<script type="text/javascript">
	function getVarURL(v){
		var querys=(location.search).replace(/^./, "");
		var vars=querys.split("&");
		for(i=0;i<vars.length;i++){
			var pair=vars[i].split("=");
			if(pair[0]==v)
				return pair[1];
		}return "";
	};

	jQuery.fn.DataTable.ext.type.search.string = function ( indata ) {
		return ! indata ? ''
			: typeof indata === 'string'
			? indata.replace(/\n/g,' ')
				.replace(/[ÄÁÂÀ]/g,'A').replace(/[äáâà]/g,'a')
				.replace(/[ËÉÊÈ]/g,'E').replace(/[ëéêè]/g,'e')
				.replace(/[ÏÍÎÌ]/g,'I').replace(/[ïíîì]/g,'i')
				.replace(/[ÖÓÔÒ]/g,'O').replace(/[öóôò]/g,'o')
				.replace(/[ÜÚÛÙ]/g,'U').replace(/[üúûù]/g,'u')
				.replace(/[ŶÝŶỲ]/g,'Y').replace(/[ÿýŷỳ]/g,'y')
				.replace(/[Ñ]/g,'N').replace(/[ñ]/g,'n')
				.replace(/[Ç]/g,'C').replace(/[ç]/g,'c')
			: indata;
	};

	jQuery(document).ready(function($){
		let dtId1='#datatable-buttons', txtfnd=i18n('msg_search'), dttable;
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

	$('#edit-modal').on('shown.bs.modal', function () {
		var photo=$('#edPhotoTmp').val();
		if(photo!=''){
			$('.dz-details').css('display','none');
			var photoId=photo.match(/[^\\/:*?"<>|\r\n]+$/);
			$('#uploadXdivEdit'+' div div a img').attr('src', 'doctos/images/imgfoldername/'+photoId);
		}
    });

	var clid=getVarURL('clid');
	if(clid!=''){
		sessionStorage.setItem('clid',clid);
		getDetailsToEdit(clid);
	}
	if(getVarURL('nw')!=''){
		$('#addNewClient').trigger('click');
		$("#add-clients-modal").addClass('fornw');
		getCompanies('company');
	}
	history.replaceState({}, document.title, location.pathname+'?language='+getLanguageURL());
</script>