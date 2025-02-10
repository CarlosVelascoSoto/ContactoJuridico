<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--i18n-->
<%@page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />
<html lang="es-MX">
<meta charset="UTF-8">
<meta name="language" content="Spanish">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />
<style>
	
	.dt-buttons.btn-group a{margin-right:10px;}
	em{color:red;}
	.tac, th{text-align:center;vertical-align:middle;}
	.--fc000{color:#000;}
	.swal2-modal{box-shadow:0px 0px 20px 10px #444;-webkit-box-shadow:0px 0px 20px 10px #444;}
	.swal2-container{z-index:10000;}
	.unuse{background-color:#eee;color:#8c8c8c;}
	.wb{height:auto;word-break:break-all;}
	.capitalize{text-transform:capitalize}
</style>
<!--==============================================================-->
<!--Start right Content here-->
<!--==============================================================-->
 <div class="content-page">
	<div class="content"><!--Start content-->
		<div class="container">
			<div class="row"><!--Page-Title-->
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
					<div class="btn-group pull-right m-t-15">
						<a href="#addData-modal" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"
							data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200" data-overlayColor="#36404a">
							<fmt:message key="button.new" />
						</a>
					</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="email.settings" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class=""></div>
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-page-length="20" data-order='[[0, "asc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>SMTP</th>
									<th>Host</th>
									<th><fmt:message key="email.account" /></th>
									<th><fmt:message key="email.port" /></th>
									<th><fmt:message key="email.alias" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="button.sendtest" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="data" items="${edata}">
								<tr>
									<td class="tac">${data.smtpid}</td>
									<td>${data.host}</td>
									<td>${data.accountmail}</td>
									<td class="tac">${data.port}</td>
									<td>${data.aliasmail}</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" data-toggle="modal" data-target="#edit-data-modal" onclick="editSettingsDetails('${data.smtpid}');">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" data-toggle="modal" data-target="#send-test-modal"
										 onclick="sendTestButton('${data.smtpid}','${data.host}','${data.accountmail}','${data.port}','${data.aliasmail}');">
											<i class="md md-email"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" data-toggle="modal" data-target="#delete-data-modal"
										 onclick="deleteSettingsDetails('${data.smtpid}','${data.host}','${data.accountmail}','${data.port}','${data.aliasmail}');">
											<i class="md md-close"></i>
										</a>
									</td>
									</c:if>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="hidden-xs" style="height: 300px;"></div><!--Demo only-->
		</div>
	</div>
</div>
<footer class="footer text-right">
	<fmt:message key="text.copyright" />
</footer>

<!--Modal-->
<div id="addData-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" title="<fmt:message key='button.close' />">
		<span>&times;</span>
	</button>
	<h4 class="custom-modal-title">
		<span class="sr-only" style="position:relative;"><fmt:message key="button.additem" /></span>
	</h4>
	<div class="custom-modal-text text-left">
		<form method="post">
			<div class="form-group ">
				<div class="col-xs-12">
					<div style="display:none;" id="addDataError" class="alert alert-danger fade in">
						<a href="#" class="close --fc000" aria-label="close" onclick="$('#addDataError').toggle();">&times;</a>
						<p id="putError"></p>
					</div>
				</div>
			</div>
			<div class="form-group">
				<em>* </em><label for="hostname">Host</label>
				<input type="text" class="form-control" id="hostname" placeholder="<fmt:message key='email.host.name' />" required>
			</div>
			<div class="form-group">
				<em>* </em><label for="account"><fmt:message key="email.account" /></label>
				<input type="text" class="form-control" id="account" placeholder="<fmt:message key='field.emailaddress.msg' />"
					required pattern="^\w+[\w._-]*@\w+[\w._-]*\.+[\w._-]{2,}[^.,@\s]$">
			</div>
			<div class="form-group">
				<em>* </em><label for="password"><fmt:message key="text.password" /></label>
				<input type="password" class="form-control" id="password" placeholder="<fmt:message key='field.password.msg' />" required>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<em>* </em><label for="port"><fmt:message key="email.port" /></label>
					<input type="number" class="form-control" id="port" value="587" step="10" placeholder="<fmt:message key='email.port' />" required pattern="^\d{0,6}$">
				</div>
			</div>
			<div class="col-xs-8">
				<div class="form-group">
					<em>* </em><label for="alias"><fmt:message key="email.alias" /></label>
					<input type="text" class="form-control" id="alias" placeholder="<fmt:message key='email.alias' />" required>
				</div>
			</div>
			<button type="button" onclick="saveNewData();" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addNewDataCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>
</div>

<div id="edit-data-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-data-modal" aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" title="<fmt:message key='button.close' />">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key='text.updatesettings' /></h4>
			</div>
			<div class="modal-body">
				<div class="form-group row ">
					<div class="col-xs-12">
						<div style="display:none;" id="EditDataError" class="alert alert-danger fade in">
							<a href="#" class="close --fc000" aria-label="close" onclick="$('#EditDataError').toggle();">&times;</a>
							<p id="putEditDataError"></p>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<input id="editSmtpid" type="hidden" class="form-control unuse" disabled>
							<em>* </em><label for="editHost" class="control-label">Host</label>
							<input id="editHost" type="text" class="form-control" placeholder="<fmt:message key='email.host.name' />" required>
						</div>
						<div class="form-group">
							<em>* </em><label for="editEmail" class="control-label"><fmt:message key="email.account" /></label>
							<input id="editEmail" type="text" class="form-control" placeholder="<fmt:message key='field.emailaddress.msg' />"
								required pattern="^\w+[\w._-]*@\w+[\w._-]*\.+[\w._-]{2,}[^.,@\s]$">
						</div>
						<div class="form-group">
							<em>* </em><label for="editPassword" class="control-label"><fmt:message key="text.password" /></label>
							<input id="editPassword" type="password" class="form-control" placeholder="<fmt:message key='field.password.msg' />" required>
						</div>
					</div>
					<div class="col-xs-4">
						<div class="form-group">
							<em>* </em><label for="editPort"><fmt:message key="email.port" /></label>
							<input type="number" class="form-control" id="editPort" step="10" placeholder="<fmt:message key='email.port' />" required pattern="^\d{0,6}$">
						</div>
					</div>
					<div class="col-xs-8">
						<div class="form-group">
							<em>* </em><label for="editAlias"><fmt:message key="email.alias" /></label>
							<input type="text" class="form-control" id="editAlias" placeholder="<fmt:message key='email.alias' />" required>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" onclick="updateEditData();" class="btn btn-info waves-effect waves-light">
					<fmt:message key="button.savechanges" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="delete-data-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="custom-width-modalLabel" aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="custom-width-modalLabel">
					<fmt:message key="panel.popup.msg2delete" />
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group row ">
					<div class="row">
						<div class="col-md-12">
							<h4>Host</h4>
							<p id="delHost" class="form-control unuse wb"></p>
							<h4><fmt:message key="email.account" /></h4>
							<p id="delEmail" class="form-control unuse wb"></p>
							<h4><fmt:message key="email.port" /></h4>
							<p id="delPort" class="form-control unuse"></p>
							<h4><fmt:message key="email.alias" /></h4>
							<p id="delAlias" class="form-control wb"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" id="deleteDataButton" class="btn btn-primary waves-effect waves-light">
					<fmt:message key="button.delete" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="send-test-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="custom-width-modalLabel" aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="custom-width-modalLabel">
					<fmt:message key="button.sendtest" />
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group ">
					<div class="col-xs-12">
						<div style="display:none;" id="sendTestError" class="alert alert-danger fade in">
							<a href="#" class="close --fc000" aria-label="close" onclick="$('#sendTestError').toggle();">&times;</a>
							<p id="putSendTestError"></p>
						</div>
					</div>
				</div>
				<div class="form-group row ">
					<div class="row">
						<div class="col-xs-2">
							<div class="form-group">
								<label for="testEmail"><fmt:message key="text.emailfrom" /></label>
							</div>
						</div>
						<div class="col-xs-10">
							<div class="form-group">
								<div id="testEmail" class="form-control unuse wb"></div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-2">
							<div class="form-group">
								<em>* </em><label for="testEmailTo"><fmt:message key="text.emailto" /></label>
							</div>
						</div>
						<div class="col-xs-10">
							<div class="form-group">
								<input type="text" id="testEmailTo" class="form-control" placeholder='<fmt:message key="field.emailaddress.msg" />' />
							</div>
						</div>
						<div class="col-xs-12">
							<div class="form-group">
								<label for="testHost">Host:</label>
								<div id="testHost" class="form-control unuse wb"></div>
							</div>
						</div>
						<div class="col-xs-4">
							<div class="form-group">
								<label for="testPort"><fmt:message key="email.port" /></label>
								<span id="testPort" class="form-control unuse"></span>
							</div>
						</div>
						<div class="col-xs-8">
							<div class="form-group">
								<label for="testAlias"><fmt:message key="email.alias" /></label>
								<div id="testAlias" class="form-control unuse wb"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" id="sendTestButton" class="btn btn-warning waves-effect waves-light">
					<fmt:message key="button.sendtest" />
				</button>
			</div>
		</div>
	</div>
</div>
<!--==============================================================-->
<!--End Right content here-->
<!--==============================================================-->
<script src="resources/assets/js/jquery.min.js"></script>
<!--Modal-Effect-->
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
<script src="resources/assets/js/emailsettings.js"></script>

<script>
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
	TableManageButtons.init();

	$(document).ready(function (){
		$('#datatable').dataTable();
		$('#datatable-keytable').DataTable({keys:true});
		$('#datatable-responsive').DataTable();
		$('#datatable-colvid').DataTable({
			"dom":'C<"clear">lfrtip',
			"colVis":{"buttonText":"Change columns"}
		});
		$('#datatable-scroller').DataTable({
			deferRender:true,
			scrollY:380,
			scrollCollapse:true,
			scroller:true
		});
	});
</script>