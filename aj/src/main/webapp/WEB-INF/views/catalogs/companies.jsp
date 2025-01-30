<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- i18n...-->
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages" />
<html lang="es-MX">
<meta charset="utf-8">
<meta name="language" content="Spanish">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">

<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">

<link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/basic.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/dropzone.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">

<style>
	.alert{padding-right:25px}
</style>
<!--==============================================================-->
<!-- Start right Content here -->
<!--==============================================================-->
<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row"><!-- Page-Title -->
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addNewCompany" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" data-onOpen="console.log('test'); " class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.firms" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.firm" /></th>
									<th><fmt:message key="text.address" /></th>
									<th><fmt:message key="text.city" /></th>
									<th><fmt:message key="text.state" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="co" items="${compList}">
								<tr>
									<td>${co.company}</td>
									<td>${co.address1}</td>
									<td>
										<c:if test="${!co.city.matches('[0-9]+')}">
												${co.city}
										</c:if>
										<c:if test="${co.city.matches('[0-9]+')}">
											<c:forEach var="ct" items="${citiesList}">
											<c:if test="${ct.ciudadid==co.city}">
												${ct.ciudad}
											</c:if>
										</c:forEach>
										</c:if>
									</td>
									<td>
										<c:if test="${!co.state.matches('[0-9]+')}">
												${co.state}
										</c:if>
										<c:if test="${co.state.matches('[0-9]+')}">
											<c:forEach var="ct" items="${citiesList}">
												<c:if test="${st.estadoid==co.state}">
													${st.estado}
												</c:if>
											</c:forEach>
										</c:if>
									</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${co.companyid}" data-toggle="modal" data-target="#edit-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsToEdit(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" id="${co.companyid}" onclick="deleteCompany(id);"><i class="md md-close"></i></a>
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
<footer class="footer text-right">
	<fmt:message key="text.copyright" />
</footer>

<!-- Modal -->
<div id="company-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key='text.firm' /></h4>
	<div class="custom-modal-text text-left p-t-0">
		<div class="panel-body">
			<form id="formNewCompany">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnAdd" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAdd').toggle();">&times;</a>
							<p id="putErrorOnAdd"></p>
						</div>
					</div>
					<div class="row container-tabsmodal">
						<ul id="addtabsmodal" class="tabsmodal">
							<li class="selectedtab trn2ms" onclick="togglemodtab(this,'#addstandarddata');"><fmt:message key="text.generaldata" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#addfiscaldata');"><fmt:message key="text.businessdata" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#addcontactdata');"><fmt:message key="text.contactways" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#addlogodata');"><fmt:message key='text.logo' /></li>
						</ul>
					</div>

					<div id="addstandarddata" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="company" class="supLlb"><fmt:message key='text.firmname' /></label>
									<input type="text" class="form-control c39c" id="company" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="shortname" class="supLlb"><fmt:message key='text.shortname' /> (<fmt:message key='text.optional' />)</label>
									<input type="text" class="form-control c39c" id="shortname" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="address1" class="supLlb"><fmt:message key='text.address' /></label>
									<input type="text" class="form-control c39c" id="address1" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="address2" class="supLlb"><fmt:message key="text.colonydelegationtown" /></label>
									<input type="text" class="form-control c39c" id="address2" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="address3" class="supLlb"><fmt:message key='text.address' /> (<fmt:message key="text.additionalinfo" />)</label>
									<input type="text" class="form-control c39c" id="address3" autocomplete="off">
								</div>
							</div>
						</div>
	
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="cp" class="supLlb"><fmt:message key='text.zipcode' /></label>
									<input type="text" class="form-control c39c" id="cp" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="country" class="supLlb"><fmt:message key="text.country" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="country"></ul>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock">
									<label for="state" class="supLlb"><fmt:message key="text.state" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" onfocus="getStatesByCountry('country','state','ul')" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="state"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="city" class="supLlb"><fmt:message key="text.city" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" onfocus="getCitiesByState('state', 'city', 'ul');" placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="city"></ul>
								</div>
							</div>
						</div>
	 				</div>

					<div id="addfiscaldata" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="rfc" class="supLlb"><fmt:message key='text.rfc' /></label>
									<input type="text" class="form-control c39c" id="rfc" autocomplete="off" onblur="value=value.replace(/[^a-zA-Z0-9]/gi,'').toUpperCase();">
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="curp" class="supLlb">CURP</label>
									<input type="text" class="form-control c39c" id="curp" onblur="value=value.replace(/[^a-zA-Z0-9]/gi,'').toUpperCase();" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="fiscalpersontype" class="supLlb"><fmt:message key='text.fiscalpersontype' /></label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="fiscalpersontype">
											<option value="0" disabled selected><fmt:message key="text.select" /></option>
											<option value="1"><fmt:message key="text.naturalperson" /></option>
											<option value="2"><fmt:message key="text.legalentity" /></option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 inlineflex" style="vertical-align:middle">
								<input type="checkbox" id="samedata" class="form-control c39c" style="width:auto;margin:0 10px 0 0" autocomplete="off">
								<label for="samedata" style="font-weight:400;cursor:pointer"><fmt:message key='text.sameinfoasgeneral' /></label>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="businessname" class="supLlb"><fmt:message key='text.businessname' /></label>
									<input type="text" class="form-control c39c" id="businessname" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="commercialname" class="supLlb"><fmt:message key='text.commercialname' /></label>
									<input type="text" class="form-control c39c" id="commercialname" autocomplete="off">
								</div>
							</div>
						</div>
							
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="fiscaladdress1" class="supLlb"><fmt:message key='text.fiscaladdress' /></label>
									<input type="text" class="form-control c39c" id="fiscaladdress1" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="fiscaladdress2" class="supLlb"><fmt:message key='text.colonydelegationtown' /></label>
									<input type="text" class="form-control c39c" id="fiscaladdress2" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="fiscaladdress3" class="supLlb"><fmt:message key='text.address' /> (<fmt:message key="text.additionalinfo" />)</label>
									<input type="text" class="form-control c39c" id="fiscaladdress3" autocomplete="off">
								</div>
							</div>
						</div>
		
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="fiscalcp" class="supLlb"><fmt:message key='text.zipcode' /></label>
									<input type="text" class="form-control c39c" id="fiscalcp" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="fiscalcountry" class="supLlb"><fmt:message key="text.country" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="fiscalcountry"></ul>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock">
									<label for="fiscalstate" class="supLlb"><fmt:message key="text.state" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel"
											onfocus="getStatesByCountry('fiscalcountry','fiscalstate','ul')"
											placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="fiscalstate"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="fiscalcity" class="supLlb"><fmt:message key="text.city" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel"
										onfocus="getCitiesByState('fiscalstate','fiscalcity', 'ul');"
										placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="fiscalcity"></ul>
								</div>
							</div>
						</div>
					</div>

					<div id="addcontactdata" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock">
									<label for="commlabelid1" class="supLlb"><fmt:message key="text.phone" /> 1</label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="commlabelid1" data-sel="commlab"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="phone1" class="supLlb"><fmt:message key='text.number' /> 1</label>
									<input type="text" class="form-control c39c" id="phone1" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock">
									<label for="commlabelid2" class="supLlb"><fmt:message key="text.phone" /> 2</label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="commlabelid2" data-sel="commlab"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="phone2" class="supLlb"><fmt:message key='text.number' /> 2</label>
									<input type="text" class="form-control c39c" id="phone2" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock">
									<label for="commlabelid3" class="supLlb"><fmt:message key="text.phone" /> 3</label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="commlabelid3" data-sel="commlab"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="phone3" class="supLlb"><fmt:message key='text.number' /> 3</label>
									<input type="text" class="form-control c39c" id="phone3" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="email" class="supLlb"><fmt:message key='text.email' /></label>
									<input type="text" class="form-control c39c" id="email" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="webpage" class="supLlb"><fmt:message key='text.webpage' /></label>
									<input type="text" class="form-control c39c" id="webpage" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="snetworkname1" class="supLlb"><fmt:message key='text.socialnetwork' /> 1</label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="snetworkname1" data-sel="snw"></select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control c39c" id="snetwAcct1" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="snetworkname2" class="supLlb"><fmt:message key='text.socialnetwork' /> 2</label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="snetworkname2" data-sel="snw"></select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control c39c" id="snetwAcct2" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="snetworkname3" class="supLlb"><fmt:message key='text.socialnetwork' /> 3</label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="snetworkname3" data-sel="snw"></select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control c39c" id="snetwAcct3" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="snetworkname4" class="supLlb"><fmt:message key='text.socialnetwork' /> 4</label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="snetworkname4" data-sel="snw"></select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control c39c" id="snetwAcct4" autocomplete="off">
								</div>
							</div>
						</div>
					</div>

					<div id="addlogodata" class="firmdatatabs">
						<div id="areaCompanyUpload">
							<span class="textContent"><fmt:message key='label.dropzone' /></span>
							<div id="uploadXCompany" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
						</div>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addCompany();" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addCompanyCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-modal" class="modal fade" role="dialog" aria-labelledby="edit-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="firm.update" /></h4>
			</div>
			<div class="modal-body">
				<form id="formEditCompany">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEdit" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEdit').toggle();">&times;</a>
								<p id="putErrorOnEdit"></p>
								<input type="hidden" id="editcompId">
							</div>
						</div>
					</div>

					<div class="row container-tabsmodal">
						<ul id="edittabsmodal" class="tabsmodal">
							<li class="selectedtab trn2ms" onclick="togglemodtab(this,'#editstandarddata');"><fmt:message key="text.generaldata" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#editfiscaldata');"><fmt:message key="text.businessdata" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#editcontactdata');"><fmt:message key="text.contactways" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#editlogodata');"><fmt:message key='text.logo' /></li>
						</ul>
					</div>

					<div id="editstandarddata" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editcompany" class="supLlb"><fmt:message key='text.firmname' /></label>
									<input type="text" class="form-control c39c" id="editcompany" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editshortname" class="supLlb"><fmt:message key='text.shortname' /> (<fmt:message key='text.optional' />)</label>
									<input type="text" class="form-control c39c" id="editshortname" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editaddress1" class="supLlb"><fmt:message key='text.address' /></label>
									<input type="text" class="form-control c39c" id="editaddress1" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editaddress2" class="supLlb"><fmt:message key="text.colonydelegationtown" /></label>
									<input type="text" class="form-control c39c" id="editaddress2" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editaddress3" class="supLlb"><fmt:message key='text.address' /> (<fmt:message key="text.additionalinfo" />)</label>
									<input type="text" class="form-control c39c" id="editaddress3" autocomplete="off">
								</div>
							</div>
						</div>
	
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="editcp" class="supLlb"><fmt:message key='text.zipcode' /></label>
									<input type="text" class="form-control c39c" id="editcp" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="editcountry" class="supLlb"><fmt:message key="text.country" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="editcountry"></ul>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock">
									<label for="editstate" class="supLlb"><fmt:message key="text.state" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" onfocus="getStatesByCountry('editcountry','editstate','ul')" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="editstate"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="editcity" class="supLlb"><fmt:message key="text.city" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" onfocus="getCitiesByState('editstate', 'editcity', 'ul');" placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="editcity"></ul>
								</div>
							</div>
						</div>
	 				</div>

					<div id="editfiscaldata" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="editrfc" class="supLlb"><fmt:message key='text.rfc' /></label>
									<input type="text" class="form-control c39c" id="editrfc" autocomplete="off" onblur="value=value.replace(/[^a-zA-Z0-9]/gi,'').toUpperCase();">
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="editcurp" class="supLlb">CURP</label>
									<input type="text" class="form-control c39c" id="editcurp" onblur="value=value.replace(/[^a-zA-Z0-9]/gi,'').toUpperCase();">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editfiscalpersontype" class="supLlb"><fmt:message key='text.fiscalpersontype' /></label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="editfiscalpersontype">
											<option value="0" disabled selected><fmt:message key="text.select" /></option>
											<option value="1"><fmt:message key="text.naturalperson" /></option>
											<option value="2"><fmt:message key="text.legalentity" /></option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 inlineflex" style="vertical-align:middle">
								<input type="checkbox" id="editsamedata" class="form-control c39c" style="width:auto;margin:0 10px 0 0" autocomplete="off">
								<label for="editsamedata" style="font-weight:400;cursor:pointer"><fmt:message key='text.sameinfoasgeneral' /></label>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editbusinessname" class="supLlb"><fmt:message key='text.businessname' /></label>
									<input type="text" class="form-control c39c" id="editbusinessname" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editcommercialname" class="supLlb"><fmt:message key='text.commercialname' /></label>
									<input type="text" class="form-control c39c" id="editcommercialname" autocomplete="off">
								</div>
							</div>
						</div>
							
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editfiscaladdress1" class="supLlb"><fmt:message key='text.fiscaladdress' /></label>
									<input type="text" class="form-control c39c" id="editfiscaladdress1" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editfiscaladdress2" class="supLlb"><fmt:message key='text.colonydelegationtown' /></label>
									<input type="text" class="form-control c39c" id="editfiscaladdress2" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editfiscaladdress3" class="supLlb"><fmt:message key='text.address' /> (<fmt:message key="text.additionalinfo" />)</label>
									<input type="text" class="form-control c39c" id="editfiscaladdress3" autocomplete="off">
								</div>
							</div>
						</div>
		
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="editfiscalcp" class="supLlb"><fmt:message key='text.zipcode' /></label>
									<input type="text" class="form-control c39c" id="editfiscalcp" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="editfiscalcountry" class="supLlb"><fmt:message key="text.country" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="editfiscalcountry"></ul>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock">
									<label for="editfiscalstate" class="supLlb"><fmt:message key="text.state" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel"
											onfocus="getStatesByCountry('editfiscalcountry','editfiscalstate','ul')"
											placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="editfiscalstate"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<label for="editfiscalcity" class="supLlb"><fmt:message key="text.city" /></label>
								<div class="form-group fieldinfo inlineblock w100p">
									<input type="text" class="form-control listfiltersel"
										onfocus="getCitiesByState('editfiscalstate','editfiscalcity', 'ul');"
										placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="editfiscalcity"></ul>
								</div>
							</div>
<!-- 
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editfiscalstartdate" class="supLlb">Inicio de operaciones con este RFC</label>
									<input type="text" class="form-control c39c" id="editfiscalstartdate" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editfiscalenddate" class="supLlb">T&eacute;rmino de operaciones con este RFC</label>
									<input type="text" class="form-control c39c" id="editfiscalenddate" autocomplete="off">
								</div>
							</div>
-->
						</div>
					</div>

					<div id="editcontactdata" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock">
									<label for="editcommlabelid1" class="supLlb"><fmt:message key="text.phone" /> 1</label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="editcommlabelid1" data-sel="editcommlab"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="editphone1" class="supLlb"><fmt:message key='text.number' /> 1</label>
									<input type="text" class="form-control c39c" id="editphone1" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock">
									<label for="editcommlabelid2" class="supLlb"><fmt:message key="text.phone" /> 2</label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="editcommlabelid2" data-sel="editcommlab"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="editphone2" class="supLlb"><fmt:message key='text.number' /> 2</label>
									<input type="text" class="form-control c39c" id="editphone2" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock">
									<label for="editcommlabelid3" class="supLlb"><fmt:message key="text.phone" /> 3</label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />">
										<i class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="editcommlabelid3" data-sel="editcommlab"></ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<label for="editphone3" class="supLlb"><fmt:message key='text.number' /> 3</label>
									<input type="text" class="form-control c39c" id="editphone3" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editemail" class="supLlb"><fmt:message key='text.email' /></label>
									<input type="text" class="form-control c39c" id="editemail" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="editwebpage" class="supLlb"><fmt:message key='text.webpage' /></label>
									<input type="text" class="form-control c39c" id="editwebpage" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editsnetworkname1" class="supLlb"><fmt:message key='text.socialnetwork' /> 1</label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="editsnetworkname1" data-sel="snw"></select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control c39c" id="editsnetwAcct1" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editsnetworkname2" class="supLlb"><fmt:message key='text.socialnetwork' /> 2</label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="editsnetworkname2" data-sel="snw"></select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control c39c" id="editsnetwAcct2" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editsnetworkname3" class="supLlb"><fmt:message key='text.socialnetwork' /> 3</label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="editsnetworkname3" data-sel="snw"></select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control c39c" id="editsnetwAcct3" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editsnetworkname4" class="supLlb"><fmt:message key='text.socialnetwork' /> 4</label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="editsnetworkname4" data-sel="snw"></select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control c39c" id="editsnetwAcct4" autocomplete="off">
								</div>
							</div>
						</div>
					</div>

					<div id="editlogodata" class="firmdatatabs">
						<div class="row m-0">
							<div id="areaEditCompUpload"><span style="font-size:12px;font-weight:400"><fmt:message key='label.dropzone' /></span>
								<div id="uploadXEditCompany" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
							</div>
						</div>
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
<!-- Modal -->

<!--==============================================================-->
<!-- End Right content here -->
<!--==============================================================-->
<script src="resources/assets/js/jquery.min.js"></script>
<!-- Modal-Effect -->
<script src="resources/assets/plugins/custombox/js/custombox.min.js"></script>
<script src="resources/assets/plugins/custombox/js/legacy.min.js"></script>

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

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/plugins/sweet-alert2/sweetalert2.min.js"></script>
<script src="resources/assets/plugins/dropzone/dropzoneImg.js"></script>
<script src="resources/assets/js/customDropZoneImg.js"></script>
<script src="resources/assets/js/unorm.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script>
<script src="resources/assets/js/companies.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	var dateOp={weekStart:1,daysOfWeekHighlighted:"0",language:lang,autoclose:true,
		todayBtn:true,clearBtn:true,calendarWeeks:true,todayHighlight:true};
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
</script>