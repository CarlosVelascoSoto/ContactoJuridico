<%@page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!--(i18n)-->
<fmt:setBundle basename="messages" />
<link href="resources/assets/plugins/custombox/css/custombox.css" rel="stylesheet">
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
<c:set var="roleid" scope ="page" value="${role}" />
<c:set var="userfirm" scope ="page" value="${userfirm}" />
<style>
	.dt-buttons.btn-group a{margin-right:10px;}
	.unuse{background-color:#ccc;}
	.--fc000{color:#000;}
	.sweet-overlay{z-index:10010 !important}
	.showSweetAlert{z-index:10011 !important}
	.capitalize{text-transform:capitalize}
</style>
<!--==============================================================-->
<!--Start right Content here-->
<!--==============================================================-->
 <div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
					<div class="btn-group pull-right m-t-15">
						<a href="#addrole-modal" class="btn btn-default btn-md waves-effect waves-light m-b-30" onclick="$('#rolename').val('');"
							data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200" data-overlayColor="#36404a">
							<fmt:message key="button.add.role" />
						</a>
					</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.roles" /></h4>
					<ol class="breadcrumb"></ol>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-page-length="20" data-order='[[3, "desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.name" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>								
								<c:forEach var="reg" items="${tabla}">
								<tr>
									<td>${reg.rolename}</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td>
										<a href="#" class="table-action-btn" data-toggle="modal" data-target="#edit-role-modal" onclick="getRoleDetails('${reg.roleid}');">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td>
										<a href="#" class="table-action-btn" data-toggle="modal" data-target="#delete-role-modal" onclick="deleteRoleDetails('${reg.roleid}','${reg.rolename}');">
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

<!--Modal-->
<div id="addrole-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" title="<fmt:message key='button.close' />">
		<span>&times;</span>
	</button>
	<h4 class="custom-modal-title">
		<span class="sr-only" style="position:relative;"><fmt:message key="button.add.role" /></span>
	</h4>
	<div class="custom-modal-text text-left">
		<form method="post" id="formAddRole">
			<div class="form-group ">
				<div class="col-xs-12">
					<div style="display:none;" id="addRoleError" class="alert alert-danger fade in">
						<a href="#" class="close --fc000" aria-label="close" onclick="$('#addRoleError').toggle();">&times;</a>
						<p id="putError"></p>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="form-group inlineflex w100p">
						<label for="rolename" class="supLlb"><fmt:message key="text.role" /></label>
						<input type="text" class="form-control c39c" id="rolename" placeholder="<fmt:message key='field.role.name' />" autocomplete="off">
					</div>
				</div>
			</div>
			<c:choose>
				<c:when test="${roleid<=2}">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div>
								<label for="firm"><fmt:message key="text.firm" /></label>
								<div class="select-wrapper">
									<select class="form-control ddsencillo c39c" id="firm">
										<option value="0"><fmt:message key="text.all" /></option>
										<c:forEach var="f" items="${firmas}">
											<option value="${f.companyid}">${f.company}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>
				</c:when>
				<c:when test="${roleid>2}">
					<input type="hidden" id="firm" value="${userfirm}">
				</c:when>
			</c:choose>
			<button type="button" onclick="saveNewRole(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addNewRoleCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>
</div>

<div id="edit-role-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-role-modal" aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" title="<fmt:message key='button.close' />">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key='role.update' /></h4> 
			</div>
			<div class="modal-body">
				<div class="form-group row ">
					<div class="col-xs-12">
						<div style="display:none;" id="EditRoleError" class="alert alert-danger fade in">
							<a href="#" class="close --fc000" aria-label="close" onclick="$('#EditRoleError').toggle();">&times;</a>
							<p id="putEditRoleError"></p>
							<input id="editRoleId" type="hidden">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<div class="form-group inlineflex w100p">
							<label for="editRoleName" class="supLlb"><fmt:message key="text.role" /></label>
							<input type="text" class="form-control c39c" id="editRoleName" placeholder="<fmt:message key='field.role.name' />" autocomplete="off">
						</div>
					</div>
				</div>
				<c:choose>
					<c:when test="${roleid<=2}">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<div>
									<label for="editFirm"><fmt:message key="text.firm" /></label>
									<div class="select-wrapper">
										<select class="form-control ddsencillo c39c" id="editFirm">
											<option value="0"><fmt:message key="text.all" /></option>
											<c:forEach var="f" items="${firmas}">
												<option value="${f.companyid}">${f.company}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
					</c:when>
					<c:when test="${roleid>2}">
						<input type="hidden" id="editFirm" value="${userfirm}">
					</c:when>
				</c:choose>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" onclick="updateEditRole();" class="btn btn-info waves-effect waves-light">
					<fmt:message key="button.savechanges" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="delete-role-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="custom-width-modalLabel" aria-hidden="true" style="display: none;">
	<div class="modal-dialog" style="width:35%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize" id="custom-width-modalLabel">
					<fmt:message key="panel.popup.msg2delete" />
				</h4>
			</div>
			<div class="modal-body">
				<h4><b><fmt:message key="text.name" />:</b></h4> <p id="delRoleName"></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" id="deleteRoleButton" class="btn btn-primary waves-effect waves-light">
					<fmt:message key="button.delete" />
				</button>
			</div>
		</div><!--/.modal-content-->
	</div><!--/.modal-dialog-->
</div><!--/.modal-->
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
<script src="resources/assets/js/jetaccess.js"></script>

<script type="text/javascript">
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
		var table=$('#datatable-fixed-header').DataTable({fixedHeader:true});
		var table=$('#datatable-fixed-col').DataTable({
			scrollY:"300px",
			scrollX:true,
			scrollCollapse:true,
			paging:true,
			fixedColumns:{
				leftColumns:1,
				rightColumns:1
			}
		});

		$(document).on("keydown", function (e) {
		    if (e.key === "Enter") {
		      // Buscar el modal activo
		      const $activeModal = $(".modal.active");
		      if ($activeModal.length) {
		        // Ejecutar el botón "Aceptar" del modal activo
		        $activeModal.find(".btn-default").trigger("click");
		        e.preventDefault(); // Prevenir el comportamiento predeterminado
		      }
		    }
		  });

	});
	TableManageButtons.init();
</script>