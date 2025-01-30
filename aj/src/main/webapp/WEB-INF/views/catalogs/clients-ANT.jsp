<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.aj.utility.UserDTO"%>
<%	UserDTO userDto=(UserDTO) request.getSession().getAttribute("UserDTO");
	int role=userDto.getRole();
	pageContext.setAttribute("userrole",role);%>

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
<c:set var="userrole" scope="page" value="${userrole}" />

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
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/basic.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/dropzone.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">

<style>
	.tablelist{width:100%;border:1px solid #ddd;border-collapse:collapse;cursor:pointer}
	.tablelist th, .tablelist td{text-align:left;padding:5px}
	.tablelist tr{border-bottom:1px solid #ddd;cursor:pointer}
	.tablelist tr.header, .tablelist tr:hover{background-color:#f1f1f1;}
	.asField{margin-bottom:30px;padding:10px;border-radius:5px;border:1px solid #ccc}

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
	.fornw{position:fixed;display:block;height:90vh;top:2%;right:0;left:0;
		margin:auto;overflow-y:auto;z-index:10010
	}
	.btnaddCircle{font-size:24px;position:absolute;width:40px;height:40px;top:0;right:30px;
		-webkit-border-radius:25px;-moz-border-radius:25px;border-radius:25px;
		-webkit-justify-content:center;justify-content:center;-webkit-align-items:center;align-items:center;
		-webkit-box-shadow:0px 0px 10px 3px #ddd;box-shadow:0px 0px 10px 3px #ddd;
		background-color:yellowgreen;color:#fff;cursor:pointer}
	.uppermodal{z-index:10010}
	.dd-option-image{max-width:34px}
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
 							<a href="#" id="addNewClient" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" data-onOpen="console.log('test'); " class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
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
										<a href="#" id="${cl.clientid}" data-toggle="modal" data-target="#edit-modal" 
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
<footer class="footer text-right">
	<fmt:message key="text.copyright" />
</footer>

<!-- Modal -->
<div id="clients-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();forceclose('#clients-modal');" style="position:relative;color:#aaa">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key='text.client' /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form role="form" id="formNewClient" enctype="multipart/form-data">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnAdd" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAdd').toggle();">&times;</a>
						<p id="putErrorOnAdd"></p>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="client" class="supLlb"><fmt:message key='text.clientname' /></label>
								<input type="text" class="form-control c39c" id="client" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="typeofperson" class="supLlb"><fmt:message key='text.typeofperson' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="typeofperson">
										<option value="" selected disabled><fmt:message key='text.select' /></option>
										<option value="1"><fmt:message key='text.individualperson' /></option>
										<option value="2"><fmt:message key='text.corporationperson' /></option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="address1" class="supLlb"><fmt:message key='text.address' /></label>
								<input type="text" class="form-control c39c" id="address1" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock">
								<label for="city" class="supLlb"><fmt:message key='text.city' /></label>
								<input type="text" class="form-control c39c" id="city" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group inlineflex w100p">
								<label for="state" class="supLlb"><fmt:message key='text.state' /></label>
								<input type="text" class="form-control c39c" id="state" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock">
								<label for="country" class="supLlb"><fmt:message key='text.country' /></label>
								<input type="text" class="form-control c39c" id="country" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group inlineflex w100p">
								<label for="cp" class="supLlb"><fmt:message key='text.zipcode' /></label>
								<input type="text" class="form-control c39c" id="cp" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group inlineflex w100p">
								<label for="email" class="supLlb"><fmt:message key='text.email' /></label>
								<input type="text" class="form-control c39c" id="email" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group inlineflex w100p">
								<label for="phone" class="supLlb"><fmt:message key='text.phone' /></label>
								<input type="text" class="form-control c39c" id="phone" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="cellphone"><fmt:message key='text.cellphone' /></label>
								<input type="text" class="form-control c39c" id="cellphone" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<div class="form-group inlineflex w100p">
								<label for="status" class="supLlb"><fmt:message key='text.status' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="status">
										<option value="" selected disabled><fmt:message key='text.select' /></option>
										<option value="1"><fmt:message key='status.active' /></option>
										<option value="2"><fmt:message key='status.desactive' /></option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="birthdate"><fmt:message key='text.birthday' /></label>
								<input type="hidden" id="birthdate">
								<input type="text" class="form-control c39c" id="birthdateFix" autocomplete="off">
							</div>
						</div>
					</div>
					<c:if test="${userrole==1||userrole==2}">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="company" class="supLlb"><fmt:message key='text.firm' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="company"></select>
								</div>
							</div>
						</div>
					</div>
					</c:if>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label class="supLlb" for="contactperson"><fmt:message key='text.contactperson' /></label>
								<input type="text" class="form-control c39c" id="contactperson" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label class="supLlb" for="webpage"><fmt:message key='text.website' /></label>
								<input type="text" class="form-control c39c" id="webpage" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
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
								<table id="snTableListOnNew" data-order='[[0,"desc"]]' class="table table-striped table-bordered table-responsive">
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
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="comments" class="supLlb"><fmt:message key='text.comments' /></label>
								<textarea class="form-control c39c txArea4r" id="comments"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div id="areaClientUpload"><span class="textContent"><fmt:message key='label.dropzone' /></span>
						<div id="uploadXClient" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addClients(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addClientsCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none;overflow:auto">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="client.update" /></h4>
				<input type="hidden" id="edCliId">
			</div>
			<div class="modal-body">
				<form role="form" id="formEditClient">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEdit" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEdit').toggle();">&times;</a>
								<p id="putErrorOnEdit"></p>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="edClient" class="supLlb"><fmt:message key='text.clientname' /></label>
								<input type="text" class="form-control c39c" id="edClient" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="edTypeofperson" class="supLlb"><fmt:message key='text.typeofperson' /></label>
								<select class="form-control ddsencillo c39c" id="edTypeofperson">
									<option value="" selected disabled><fmt:message key='text.select' /></option>
									<option value="1"><fmt:message key='text.individualperson' /></option>
									<option value="2"><fmt:message key='text.corporationperson' /></option>
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="edAddress1" class="supLlb"><fmt:message key='text.address' /></label>
								<input type="text" class="form-control c39c" id="edAddress1" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock">
								<label for="edCity" class="supLlb"><fmt:message key='text.city' /></label>
								<input type="text" class="form-control c39c" id="edCity" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group inlineflex w100p">
								<label for="edState" class="supLlb"><fmt:message key='text.state' /></label>
								<input type="text" class="form-control c39c" id="edState" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock">
								<label for="edCountry" class="supLlb"><fmt:message key='text.country' /></label>
								<input type="text" class="form-control c39c" id="edCountry" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group inlineflex w100p">
								<label for="edCp" class="supLlb"><fmt:message key='text.zipcode' /></label>
								<input type="text" class="form-control c39c" id="edCp" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group inlineflex w100p">
								<label for="edEmail" class="supLlb"><fmt:message key='text.email' /></label>
								<input type="text" class="form-control c39c" id="edEmail" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group inlineflex w100p">
								<label for="edPhone" class="supLlb"><fmt:message key='text.phone' /></label>
								<input type="text" class="form-control c39c" id="edPhone" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edCellphone"><fmt:message key='text.cellphone' /></label>
								<input type="text" class="form-control c39c" id="edCellphone" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<div class="form-group inlineflex w100p">
								<label for="edStatus" class="supLlb"><fmt:message key='text.status' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="edStatus">
										<option value="" selected disabled><fmt:message key='text.select' /></option>
										<option value="1"><fmt:message key='status.active' /></option>
										<option value="2"><fmt:message key='status.desactive' /></option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edBirthdate"><fmt:message key='text.birthday' /></label>
								<input type="hidden" id="edBirthdate">
								<input type="text" class="form-control c39c" id="edBirthdateFix" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label class="supLlb" for="edContactperson"><fmt:message key='text.contactperson' /></label>
								<input type="text" class="form-control c39c" id="edContactperson" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label class="supLlb" for="edWebpage"><fmt:message key='text.website' /></label>
								<input type="text" class="form-control c39c" id="edWebpage" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<h3 class="capitalize"><fmt:message key="text.socialnetworks"/></h3>
							<div class="inlineflex btnaddCircle" onclick="modalsn('add','OnEdit');">+</div>
						</div>
						<div class="col-xs-12">
							<div class="card-box table-responsive" style="padding:0">
								<table id="snTableListOnEdit" data-order='[[0,"desc"]]' class="table table-striped table-bordered table-responsive">
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
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group inlineflex w100p">
								<label for="edComments" class="supLlb"><fmt:message key='text.comments' /></label>
								<textarea class="form-control c39c txArea4r" id="edComments"></textarea>
							</div>
						</div>
					</div>
					<div class="row">
						<input type='hidden' id="edPhotoTmp">
						<div id="areaEditClientUpload"><span class="textContent"><fmt:message key='label.dropzone' /></span>
							<div id='uploadXEditClient' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
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

<input type="hidden" id="reeditsn">
<div id="addSN-OnNew-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none;">
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
<div id="editSN-OnNew-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none">
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
<div id="addSN-OnEdit-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none;">
	<div class="modal-dialog" style="-webbkit-box-shadow:0 0 15px #000;box-shadow:0 0 15px #000;background-color:#fff">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title"><fmt:message key="text.addsocialnetwork" /></h4>
		</div>
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12">
						<div class="form-group inlineflex w100p">
							<label for="addSnListOnEdit" class="supLlb"><fmt:message key='text.socialnetwork' /></label>
							<div class="select-wrapper w100p">
								<select class="form-control ddsencillo c39c" id="addSnListOnEdit"></select>
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<div class="form-group inlineflex w100p">
							<label for="addSnAddressOnEdit" class="supLlb"><fmt:message key='text.address' /></label>
							<input type="text" class="form-control c39c" id="addSnAddressOnEdit" autocomplete="off" pattern="^[\S]*$">
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

<div id="editSN-OnEdit-modal" class="modal fade uppermodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none">
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
<script src="${pageContext.request.contextPath}/web-resources/libs/sweet-alert2/sweetalert2.min.js"></script>
<script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="resources/assets/plugins/dropzone/dropzoneImg.js"></script>

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/customDropZoneImg.js"></script>
<script src="resources/assets/js/complementos.js"></script>
<script src="resources/assets/js/clients.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	var lang=getLanguageURL();
	var dateOp={weekStart:1,daysOfWeekHighlighted:"0",language:lang,autoclose:true,
		todayBtn:true,clearBtn:true,calendarWeeks:true,todayHighlight:true};
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
	$('#birthdateFix,edBirthdateFix').datepicker(dateOp);
	$("#birthdateFix").datepicker().on('changeDate',function(e){$('#birthdate').val(dp(e.date));});
	$("#birthdateFix").datepicker().on('blur change',function(e){
		$('#birthdate').val(simpleChangePatt("yyyy-MM-dd",$("#birthdateFix").val()));
	});
	$("#edBirthdateFix").datepicker().on('changeDate',function(e){$('#edBirthdate').val(dp(e.date));});
	$("#edBirthdateFix").datepicker().on('blur change',function(e){
		$('#edBirthdate').val(simpleChangePatt("yyyy-MM-dd",$("#edBirthdateFix").val()));
	});
</script>