<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<title><fmt:message key="user.panel.title" /> - Contacto
	jur&iacute;dico</title>

<link href="resources/assets/plugins/custombox/css/custombox.css"
	rel="stylesheet">

<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/dropzone/basic.css">
<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/dropzone/dropzone.css" />
<link rel="stylesheet" type="text/css"
	href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/globalcontrols.css">

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />
<style>
.dt-buttons {
	display: none
}

.--fc000 {
	color: #000 !important
}

th {
	text-align: center
}

em {
	color: red
}

.label-success, .label-danger, .label-warning, .label-default,
	.label-primary {
	display: block;
	width: 60px;
	margin: 0 auto
}

.custombox-modal-container, #edit-user-modal>.modal-dialog {
	width: 70% !important;
	max-width: 800px !important
}

.toggle-input {
	position: absolute;
	top: 7px;
	right: 20px;
	color: #444;
	cursor: pointer
}

.firmdatatabs {
	display: none
}

#areaAddUserUpload, #areaEditUserUpload {
	margin: 0 10px
}

#uploadXAdduser, #uploadXEdituser {
	min-width: 130px;
	min-height: 130px;
	border: 1px dotted #000 !important
}

.clientlist {
	display: none
}

.msgbg {
	position: absolute;
	left: 0;
	padding: 30px 20px;
	color: #aaa
}

.bg2550 {rgba (255,255,255,0) !important;
	z-index: 1 !important
}

@media screen and (max-width:650px) {
	.custombox-modal-container, #edit-user-modal>.modal-dialog {
		width: auto !important
	}
}
</style>

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-xs-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
						<div class="btn-group pull-right m-t-15">
							<a href="#adduser-modal" id="newuser" data-animation="fadein"
								data-plugin="custommodal" data-overlaySpeed="200"
								data-overlayColor="#36404a"
								class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg">
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize">
						<fmt:message key="user.panel.title" />
					</h4>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="card-box table-responsive">
						<div class="table-subcontrols">
							<button type="button" id="btnfilter" class="btn btn-filter"
								title="<fmt:message key="text.filtersbycolumns" />"
								onclick="$('#datatable-buttons thead tr:eq(1)').toggle();">
								<span class="glyphicon glyphicon-filter"></span>
							</button>
							<button id="clearfilters" class="btn clearfilters"
								title="<fmt:message key="text.clearfiters" />"
								onclick="cleanTableFilters(this)">
								<span class="glyphicon glyphicon-filter"></span><i
									class="material-icons">close</i>
							</button>
						</div>
						<table id="datatable-buttons" data-order='[[3,"desc"]]'
							class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.name" /></th>
									<th><fmt:message key="text.user" /></th>
									<c:if test="${userRole==1&&userRole==2}">
										<th><fmt:message key="text.role" /></th>
									</c:if>
									<th><fmt:message key="text.startdate" /></th>
									<th><fmt:message key="text.status" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
										<th><fmt:message key="text.edit" /></th>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
										<th><fmt:message key="text.delete" /></th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="user" items="${users}">
									<tr>
										<td data-info="Maria">
											${user.first_name}&ensp;${user.last_name}</td>
										<td><a href="#">${user.username}</a></td>
										<c:if test="${userRole==1&&userRole==2}">
											<td><c:forEach var="r" items="${roleList}">
													<c:if test="${user.role == r.roleid}">
												${r.rolename}
											</c:if>
												</c:forEach></td>
										</c:if>
										<td><fmt:formatDate pattern="yyyy-MM-dd"
												value="${user.created}" /></td>
										<td><c:if test="${user.status==1}">
												<span class="label label-success"><fmt:message
														key="status.active" /></span>
											</c:if> <c:if test="${user.status==0}">
												<span class="label label-danger"><fmt:message
														key="status.desactive" /></span>
											</c:if></td>
										<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
											<td class="tac"><a href="#"
												onclick="editUserDetails('${user.id}');"
												class="table-action-btn" data-toggle="modal"
												data-target="#edit-user-modal"> <i class="md md-edit"></i>
											</a></td>
										</c:if>
										<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
											<td class="tac"><a href="#"
												onclick="deleteUserDetails('${user.id}','${user.first_name} ${user.last_name}','${user.username}','${user.email}');"
												class="table-action-btn" data-toggle="modal"
												data-target="#delete-user-modal"> <i class="md md-close"></i>
											</a></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="hidden-xs" style="height: 300px;"></div>
		</div>
	</div>
</div>

<div id="adduser-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();"
		title="<fmt:message key='button.close' />">
		<span>&times;</span>
	</button>
	<h4 class="custom-modal-title">
		<span class="sr-only" style="position: relative;"><fmt:message
				key="button.add.user" /></span>
	</h4>
	<div class="custom-modal-text text-left" style="padding-top: 0">
		<div class="panel-body">
			<form method="post" id="formaddprofile" autocomplete="off">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: none;" id="addUserError"
							class="alert alert-danger fade in">
							<a href="#" class="close --fc000" aria-label="close"
								onclick="$('#addUserError').toggle();">&times;</a>
							<p id="putAddUserError"></p>
						</div>
					</div>

					<div class="row container-tabsmodal">
						<ul id="addtabsmodal" class="tabsmodal">
							<li class="selectedtab trn2ms"
								onclick="togglemodtab(this,'#addstandard');"><fmt:message
									key='text.generaldata' /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#addother');"><fmt:message
									key='text.otherdata' /></li>
						</ul>
					</div>

					<div id="addstandard" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="addFirstName" class="supLlb"><em>* </em>
									<fmt:message key='text.firstname' /></label> <input type="text"
										class="form-control c39c" id="addFirstName"> <input
										type="hidden" id="addUserId">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="addLastName" class="supLlb"><em>* </em>
									<fmt:message key='text.lastname' /></label> <input type="text"
										class="form-control c39c" id="addLastName">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="addUserName" class="supLlb"><em>* </em>
									<fmt:message key='text.user.name' /></label> <input type="text"
										class="form-control c39c" id="addUserName"
										placeholder="<fmt:message key='field.user.name.msg' />">
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="addEmail" class="supLlb"><em>* </em>
									<fmt:message key='text.emailaddress' /></label> <input type="email"
										class="form-control c39c" id="addEmail">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="addpassword" class="supLlb"><em>* </em>
									<fmt:message key='text.password' /></label> <input type="password"
										class="form-control c39c" id="addpassword"
										placeholder="<fmt:message key='field.password.msg' />">
									<i class="material-icons toggle-input"
										onclick="toggleEye(this,'addpassword');">visibility_off</i>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="addrepeatpass" class="supLlb"><em>* </em>
									<fmt:message key='text.repeatpassword' /></label> <input
										type="password" class="form-control c39c" id="addrepeatpass"
										placeholder="<fmt:message key='text.repeatpassword' />"
										autocomplete="off"> <i
										class="material-icons toggle-input"
										onclick="toggleEye(this,'addrepeatpass');">visibility_off</i>
								</div>
							</div>

							<c:if test="${userRole<=3}">
								<div class="col-xs-12 col-sm-6">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="addRole" class="supLlb"><em>* </em>
										<fmt:message key="user.panel.popup.role" /></label>
										<div class="select-wrapper w100p">
											<select class="form-control ddsencillo c39c" id="addRole"
												onchange="$('#isaddr').val(this.value)">
												<option value="0" selected disabled><fmt:message
														key="text.select" /></option>
												<c:forEach var="rl" items="${roleList}">
													<option value="${rl.roleid}">${rl.rolename}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</c:if>
							<input type="hidden" id="isaddr" value=""> <input
								type="hidden" id="addc1" value="${userRole<3?'':userCompany}">
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="addStatus" class="supLlb"><fmt:message
											key="text.status" /></label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="addStatus">
											<option selected disabled><fmt:message
													key="text.select" /></option>
											<option value="1"><fmt:message key="status.active" /></option>
											<option value="0"><fmt:message
													key="status.desactive" /></option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="addUserType" class="supLlb"><em>* </em>
									<fmt:message key="text.usertype" /></label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="addUserType">
											<option selected disabled><fmt:message
													key="text.select" /></option>
											<option value="0"><fmt:message key="text.lawyer" /></option>
											<option value="1"><fmt:message key="text.client" /></option>
										</select>
									</div>
								</div>
							</div>

							<div class="col-sm-12 clientlist">
								<div class="form-group inlineflex w100p">
									<label for="addClient" class="supLlb"><em>* </em>
									<fmt:message key='text.bindclient' /></label> <input type="text"
										class="form-control c39c" id="addClient"
										placeholder="<fmt:message key="text.select" />">
									<div class="containTL">
										<button type="button" class="close closecontainTL"
											onclick="$('.containTL').hide();">
											<span>&times;</span><span class="sr-only"><fmt:message
													key="button.close" /></span>
										</button>
										<table class="table tablelist" id="addUsrCCList"></table>
									</div>
								</div>
							</div>

						</div>
						<c:if test="${userRole<=2}">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="addF1" class="supLlb"><em>* </em>
										<fmt:message key="text.firmname" /></label>
										<div class="select-wrapper w100p">
											<select class="form-control ddsencillo c39c" id="addF1"
												onchange="$('#addc1').val($('#addF1').val());">
												<option value="" disabled selected><fmt:message
														key="text.select" /></option>
												<c:forEach var="cl" items="${compList}">
													<option value="${cl.companyid}">${cl.company}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</div>

					<div id="addother" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12 col-sm-5">
								<div class="col-xs-12 form-group tac m-0"
									style="min-height: 300px">
									<label class="control-label"><fmt:message
											key="text.updateprofilepicture" />:</label>
									<div id="areaAddUserUpload">
										<span class="textContent msgbg"><fmt:message
												key='label.dropzone' /></span>
										<div id='uploadXAdduser'
											class="dz-default dz-message file-dropzone text-center well col-sm-12 bg2550"></div>
									</div>
								</div>
								<div>
									<a data-toggle="modal" data-target="#camera-modal"
										class="btn-opencam flex trn2ms asLink noselect"
										onclick="$('#targetDZ').val('#uploadXAdduser')"
										title="<fmt:message key='text.usecam.browser' />"> <i
										class="material-icons">camera_alt</i>
									</a>
								</div>
							</div>
							<div class="col-xs-12 col-sm-7">
								<div class="col-xs-12">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="addphone" class="supLlb"><fmt:message
												key='text.phone' /></label> <input type="tel"
											class="form-control c39c" id="addphone">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="addcellphone" class="supLlb"><fmt:message
												key='text.cellphone' /></label> <input type="tel"
											class="form-control c39c" id="addcellphone">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="addaddress" class="supLlb"><fmt:message
												key='text.address' /></label> <input type="text"
											class="form-control c39c" id="addaddress">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="addzipcode" class="supLlb"><fmt:message
												key='text.zipcode' /></label> <input type="text"
											class="form-control c39c" id="addzipcode">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group inlineblock w100p">
										<label for="addprofcountry" class="supLlb"> <fmt:message
												key="text.filterby" /> <fmt:message key='text.country' /></label>
										<div class="form-group inlineblock w100p">
											<input type="text" class="form-control listfiltersel c39c"
												id="inputcountry"
												placeholder="<fmt:message key="text.select" />"
												autocomplete="off"> <i
												class="material-icons iconlistfilter">arrow_drop_down</i>
											<ul class="ddListImg noimgOnList" id="addprofcountry"></ul>
										</div>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group inlineblock w100p">
										<label for="addprofstate" class="supLlb"> <fmt:message
												key="text.filterby" /> <fmt:message key='text.state' /></label>
										<div class="form-group inlineblock w100p">
											<input type="text" class="form-control listfiltersel c39c"
												id="inputstate" onfocus="getEstados('addprofstate','ul');"
												autocomplete="off"
												placeholder="<fmt:message key="text.select" />"> <i
												class="material-icons iconlistfilter">arrow_drop_down</i>
											<ul class="ddListImg noimgOnList" id="addprofstate"></ul>
										</div>
									</div>
								</div>
								<div class="col-xs-12">
									<label for="addprofcity" class="supLlb"> <fmt:message
											key="text.filterby" /> <fmt:message key='text.city' /></label>
									<div class="form-group inlineblock w100p">
										<input type="text" class="form-control listfiltersel c39c"
											id="inputcity"
											onfocus="getCiudades('addprofcity', 'ul', 'addprofstate');"
											placeholder="<fmt:message key="text.select" />"
											autocomplete="off"> <i
											class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="addprofcity"></ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<div class="row">
				<div class="col-xs-12 m-t-15">
					<button type="button" id="savenewuser"
						class="btn btn-default waves-effect waves-light">
						<fmt:message key="button.save" />
					</button>
					<button type="button"
						onclick="addNewUserCancel(); Custombox.close();"
						class="btn btn-danger waves-effect waves-light m-l-10">
						<fmt:message key="button.cancel" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="delete-user-modal" class="modal fade" tabindex="-1"
	role="dialog" aria-labelledby="custom-width-modalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog" style="width: 35%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize" id="custom-width-modalLabel">
					<fmt:message key="user.panel.popup.msg2delete" />
				</h4>
			</div>
			<div class="modal-body">
				<h4>
					<b><fmt:message key="text.name" />:</b>
				</h4>
				<p id="delUser"></p>
				<h4>
					<b><fmt:message key="text.user.name" />:</b>
				</h4>
				<p id="delUserName"></p>
				<h4>
					<b><fmt:message key="text.email" />:</b>
				</h4>
				<p id="delUserEmail"></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect"
					data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" id="deleteUserButton"
					class="btn btn-primary waves-effect waves-light">
					<fmt:message key="button.delete" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="edit-user-modal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="edit-user-modal" aria-hidden="true"
	style="display: none">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" title="<fmt:message key='button.close' />">&times;</button>
				<h4 class="modal-title capitalize">
					<fmt:message key="user.panel.popup.update.details" />
				</h4>
			</div>
			<div class="modal-body">
				<form method="post" id="formuseredit" autocomplete="off">
					<div class="form-group row">
						<div class="col-xs-12">
							<div class="form-group">
								<div style="display: none;" id="editUserError"
									class="alert alert-danger fade in">
									<a href="#" class="close --fc000" aria-label="close"
										onclick="$('#editUserError').toggle();">&times;</a>
									<p id="putEditUserError"></p>
								</div>
							</div>
						</div>
					</div>
					<div class="row container-tabsmodal">
						<ul id="edittabsmodal" class="tabsmodal">
							<li class="selectedtab trn2ms"
								onclick="togglemodtab(this,'#editstandard');"><fmt:message
									key='text.generaldata' /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#editother');"><fmt:message
									key='text.otherdata' /></li>
						</ul>
					</div>

					<div id="editstandard" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editFirstName" class="supLlb"><em>* </em>
									<fmt:message key='text.firstname' /></label> <input type="text"
										class="form-control c39c" id="editFirstName"> <input
										type="hidden" id="editUserId">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editLastName" class="supLlb"><em>* </em>
									<fmt:message key='text.lastname' /></label> <input type="text"
										class="form-control c39c" id="editLastName">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editUserName" class="supLlb"><em>* </em>
									<fmt:message key='text.user.name' /></label> <input type="text"
										class="form-control c39c" id="editUserName"
										placeholder="<fmt:message key='field.user.name.msg' />">
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editEmail" class="supLlb"><em>* </em>
									<fmt:message key='text.emailaddress' /></label> <input type="email"
										class="form-control c39c" id="editEmail">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editpassword" class="supLlb"><em>* </em>
									<fmt:message key='text.password' /></label> <input type="password"
										class="form-control c39c" id="editpassword"
										placeholder="<fmt:message key='field.password.msg' />">
									<i class="material-icons toggle-input"
										onclick="toggleEye(this,'editpassword');">visibility_off</i>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editpassword" class="supLlb"><em>* </em>
									<fmt:message key='text.repeatpassword' /></label> <input
										type="password" class="form-control c39c" id="editrepeatpass"
										placeholder="<fmt:message key='text.repeatpassword' />">
									<i class="material-icons toggle-input"
										onclick="toggleEye(this,'editrepeatpass');">visibility_off</i>
								</div>
							</div>

							<c:if test="${userRole<=3}">
								<div class="col-xs-12 col-sm-6">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="editRole" class="supLlb"><em>* </em>
										<fmt:message key="user.panel.popup.role" /></label>
										<div class="select-wrapper w100p">
											<select class="form-control ddsencillo c39c" id="editRole"
												onchange="$('#iseditr').val(this.value)">
												<option value="0" selected disabled><fmt:message
														key="text.select" /></option>
												<c:forEach var="rl" items="${roleList}">
													<option value="${rl.roleid}">${rl.rolename}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</c:if>
							<input type="hidden" id="iseditr" value=""> <input
								type="hidden" id="edc1" value="${userRole<3?'':userCompany}">
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editStatus" class="supLlb"><fmt:message
											key="text.status" /></label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="editStatus">
											<option selected disabled><fmt:message
													key="text.select" /></option>
											<option value="1"><fmt:message key="status.active" /></option>
											<option value="0"><fmt:message
													key="status.desactive" /></option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group fieldinfo inlineblock w100p">
									<label for="editUserType" class="supLlb"><em>* </em>
									<fmt:message key="text.usertype" /></label>
									<div class="select-wrapper w100p">
										<select class="form-control ddsencillo c39c" id="editUserType">
											<option selected disabled><fmt:message
													key="text.select" /></option>
											<option value="0"><fmt:message key="text.lawyer" /></option>
											<option value="1"><fmt:message key="text.client" /></option>
										</select>
									</div>
								</div>
							</div>

							<div class="col-sm-12 clientlist">
								<div class="form-group inlineflex w100p">
									<label for="editClient" class="supLlb"><em>* </em>
									<fmt:message key='text.bindclient' /></label> <input type="text"
										class="form-control c39c" id="editClient"
										placeholder="<fmt:message key="text.select" />">
									<div class="containTL">
										<button type="button" class="close closecontainTL"
											onclick="$('.containTL').hide();">
											<span>&times;</span><span class="sr-only"><fmt:message
													key="button.close" /></span>
										</button>
										<table class="table tablelist" id="editUsrCCList"></table>
									</div>
								</div>
							</div>
						</div>
						<c:if test="${userRole<=2}">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="editF1" class="supLlb"><em>* </em>
										<fmt:message key="text.firmname" /></label>
										<div class="select-wrapper w100p">
											<select class="form-control ddsencillo c39c" id="editF1"
												onchange="$('#editF1').val();">
												<option value="" disabled selected><fmt:message
														key="text.select" /></option>
												<c:forEach var="cl" items="${compList}">
													<option value="${cl.companyid}">${cl.company}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</div>

					<div id="editother" class="firmdatatabs">
						<div class="row">
							<div class="col-xs-12 col-sm-5">
								<div class="col-xs-12">
									<div class="col-xs-12 form-group tac m-0"
										style="min-height: 300px">
										<label class="control-label"><fmt:message
												key="text.updateprofilepicture" />:</label>
										<div id="areaEditUserUpload">
											<span class="textContent msgbg"><fmt:message
													key='label.dropzone' /></span>
											<div id='uploadXEdituser'
												class="dz-default dz-message file-dropzone text-center well col-sm-12 bg2550"></div>
										</div>
									</div>
								</div>
								<input type="hidden" id="editphoto">
							</div>
							<div class="col-xs-12 col-sm-7">
								<div class="col-xs-12">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="editphone" class="supLlb"><fmt:message
												key='text.phone' /></label> <input type="tel"
											class="form-control c39c" id="editphone">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="editcellphone" class="supLlb"><fmt:message
												key='text.cellphone' /></label> <input type="tel"
											class="form-control c39c" id="editcellphone">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="editaddress" class="supLlb"><fmt:message
												key='text.address' /></label> <input type="text"
											class="form-control c39c" id="editaddress">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group fieldinfo inlineblock w100p">
										<label for="editzipcode" class="supLlb"><fmt:message
												key='text.zipcode' /></label> <input type="text"
											class="form-control c39c" id="editzipcode">
									</div>
								</div>
								<div class="col-xs-12">
									<label for="editprofcountry" class="supLlb"><fmt:message
											key="text.country" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel c39c"
											id="editprofcountry"
											placeholder="<fmt:message key="text.select" />"> <i
											class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="editcountry"></ul>
									</div>
								</div>
								<div class="col-xs-12">
									<label for="editprofstate" class="supLlb"><fmt:message
											key="text.state" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel c39c"
											id="editprofstate"
											onfocus="getStatesByCountry('editcountry','editstate','ul')"
											placeholder="<fmt:message key="text.select" />"> <i
											class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="editstate"></ul>
									</div>
								</div>
								<div class="col-xs-12">
									<label for="editprofcity" class="supLlb"><fmt:message
											key="text.city" /></label>
									<div class="form-group fieldinfo inlineblock w100p">
										<input type="text" class="form-control listfiltersel c39c"
											onfocus="getCitiesByState('editstate','editcity','ul');"
											id="editprofcity"
											placeholder="<fmt:message key="text.select" />"> <i
											class="material-icons iconlistfilter">arrow_drop_down</i>
										<ul class="ddListImg noimgOnList" id="editcity"></ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect"
					data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" class="btn btn-info waves-effect waves-light"
					id="btn-editupdate">
					<fmt:message key="button.savechanges" />
				</button>
			</div>
		</div>
	</div>
</div>
<a class="btnScrollUp inlineblock blackCircle trn3ms"><i
	class="material-icons">&#xe316;</i></a>

<script src="resources/assets/js/jquery.min.js"></script>
<script
	src="resources/assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script
	src="resources/assets/plugins/datatables/dataTables.bootstrap.js"></script>
<script
	src="resources/assets/plugins/datatables/dataTables.buttons.min.js"></script>
<script
	src="resources/assets/plugins/datatables/buttons.bootstrap.min.js"></script>
<script src="resources/assets/plugins/datatables/jszip.min.js"></script>
<script src="resources/assets/plugins/datatables/pdfmake.min.js"></script>
<script src="resources/assets/plugins/datatables/vfs_fonts.js"></script>
<script src="resources/assets/plugins/datatables/buttons.html5.min.js"></script>
<script src="resources/assets/plugins/datatables/buttons.print.min.js"></script>
<script
	src="resources/assets/plugins/datatables/dataTables.fixedHeader.min.js"></script>
<script
	src="resources/assets/plugins/datatables/dataTables.keyTable.min.js"></script>
<script
	src="resources/assets/plugins/datatables/dataTables.responsive.min.js"></script>
<script
	src="resources/assets/plugins/datatables/responsive.bootstrap.min.js"></script>
<script
	src="resources/assets/plugins/datatables/dataTables.scroller.min.js"></script>
<script src="resources/assets/plugins/datatables/dataTables.colVis.js"></script>
<script
	src="resources/assets/plugins/datatables/dataTables.fixedColumns.min.js"></script>

<!-- Modal-Effect -->
<script src="resources/assets/plugins/custombox/js/custombox.min.js"></script>
<script src="resources/assets/plugins/custombox/js/legacy.min.js"></script>

<!-- script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script-->
<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script>
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>
<script src="resources/assets/js/customDropZoneImg.js"></script>
<script src="resources/assets/js/addnewuser.js"></script>

<script type="text/javascript">
	jQuery(document).ready(function($){
		let dtId1='#datatable-buttons', txtfnd=i18n('msg_search'), dtdttable;
		let dtId0=dtId1.replace(/^[#\.]/,''), rx=new RegExp(txtfnd, 'ig');
		$(dtId1+' thead tr').clone(true).appendTo(dtId1+' thead');
		$(dtId1+' thead tr:eq(1)').css('display','none');
		$(dtId1+' thead tr:eq(1) th').each( function (i){
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
				'<"col-sm-3"f><tr><"row"<"col-xs-12 col-sm-12 col-md-4 fs12"i>'
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
	});
	$(".modal-demo").on('hide.bs.modal', function() {
		clearTemp();
	});
</script>


