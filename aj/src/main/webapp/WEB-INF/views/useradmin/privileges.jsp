<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!--i18n-->
<fmt:setBundle basename="messages" />
<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">
<script src="https://kit.fontawesome.com/447df55f6c.js" crossorigin="anonymous"></script>


<link rel="stylesheet" href="resources/css/privileges.css">
<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />
<style> 
	.dt-buttons.btn-group a{margin-right:10px;}
	.unuse{background-color:#eee;}
	.tac, th{text-align:center;}
	.capitalize{text-transform:capitalize}
	/*DropDown (ini)*/
	select{-webkit-appearance:none;moz-appearance:none;appearance:none}
	.select-wrapper{display:table-cell; background-color:#FFF;}
	.select-wrapper:after{
		content:"\25bc";
		position:absolute;
		top:13px;
		right:10px;
		font-size:14px;
		color:#39c;
		z-index:2;
	}
	.ddsencillo, .select-wrapper{
		position:relative;
		display:inline-block;
		height:42px;
		border-radius:7px;
		background-color:#FFF;
		cursor:pointer;
	}
	.ddsencillo::-ms-expand {display: none;}
	.ddsencillo{
		width:100%;
		padding:10px 40px 10px 10px;
		-webkit-appearance:none;
		-moz-appearance:none;
		-ms-appearance:none;
		appearance:none;
		border:1px solid #bbb;
		background-color:transparent;
		z-index:3;
	}/*DropDown (fin)*/
	.b-chk{position:relative; width:100%; top:-10px; margin:10px;}
	.b-chkAll{
		position:relative;
		width:210px;
		height:25px;
		top:9px;
		margin:10px;
		display:inline-block;
	}
	.switch{
		position:relative;
		display:inline-block;
		width:40px;
		height:20px;
	}
	.slider{
		position:absolute;
		cursor:pointer;
		top:0;
		left:0;
		right:-5px;
		bottom:0;
		background-color:#ccc;
		-webkit-transition:.4s;
		transition:.4s;
	}
	.slider:before{
		position:absolute;
		content:"";
		height:12px;
		width:12px;
		left:3px;
		bottom:4px;
		background-color:white;
		-webkit-transition:.4s;
		transition:.4s;
	}
	@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

	.switch input{display:none;}
	.slider.round{border-radius:34px;}
	.slider.round:before{border-radius:50%;}
	input:checked ~ .slider{background-color:#9acd32;}
	input:focus ~ .slider{box-shadow:0 0 1px #9acd32;}
	input:checked ~ .slider:before{
		-webkit-transform:translateX(26px);
		-ms-transform:translateX(26px);
		transform:translateX(26px);
	}
	#editPrivilegeRoleid::-ms-expand{display:none;}
	#editPrivilegeRoleid{-moz-appearance:none;-webkit-appearance:none;}
</style>
 <!--==============================================================-->
 <!--Start right Content here-->
 <!--==============================================================-->
 <div class="content-page">
	<div class="content"><!--Start content-->
		<div class="container">
			<div id="loadingModal" style="display:none; position: fixed; top:0; left:0; right:0; bottom:0; background-color: rgba(0,0,0,0.5); z-index: 9999;">
				<div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); text-align: center;">
				  <div style="display: inline-block; width: 80px; height: 80px; border: 8px solid #fff; border-top-color: #3498db; border-radius: 50%; animation: spin 2s linear infinite;"></div>
				  <div id="loadingText" style="margin-top: 20px; color: #fff; font-size: 24px;">Copiando permisos</div>
				  <div id="doneIcon" style="display: none; margin-top: 20px;font-size: 20%;"><i class="fa fa-check" style="color: #fff; font-size: 24px;"></i></div>
				</div>
			  </div>
			  
			<div class="row"><!--Page-Title-->
				<div class="col-sm-12">
					<h4 class="page-title capitalize" data-tg-order="0"><fmt:message key="text.privileges" /></h4>
					<ol class="breadcrumb"></ol>
					<div >
						<a href="#addrole-modal" onclick="$('#rolename').val('');"
							data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200" data-overlayColor="#36404a">
							<i class="material-icons" style="color:#39c; position: absolute;bottom: 9px;">&#xe147;</i>
						</a>
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
						<label for="roleList" style="padding-left: 28px;"><fmt:message key="text.role" />:&nbsp;</label>
						<div class="select-wrapper">
							<select class="form-control ddsencillo c39c" id="roleList">
								<c:forEach var="rl" items="${listr}">
									<option value="${rl.roleid}"  ${roleuser==rl.roleid ? 'selected' : ''} >${rl.rolename}</option>
								</c:forEach>
							</select>
						</div>
						<button type="button" onclick="swal(i18n('msg_information'),i18n('msg_create_or_select_role'), 'info');" style="border: 0px solid transparent;background-color: transparent;">
							<i class="material-icons" style="color: #39c;position: absolute;display: inline-flex;bottom: 8px;">&#xe88e;</i>
						</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[0, "desc"]]' class="table table-striped table-bordered">
							<caption>Dashboard</caption>
							<button id="edit" style="all:unset;background-color: #5FBEAA;margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;" onclick="setEvents()">Editar</button>
							<button onclick="openModalCopy()" id="copy" value="open" style="all:unset;background-color: #5FBEAA;margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;">Copiar permisos</button>
							<!--Modal para copiar permisos-->
							<div id="modalcopy" style="position: absolute;top: 0;left: 0;z-index: 9999;padding: 25px; background-color: #212121; border-radius: 10px; display:none;">
								<div class="col-sm-12">
									<h4 style="color: #FFF;" id="nameModal"></h4>
									<ol class="breadcrumb"></ol>
									<div >
										<label for="roleList"><fmt:message key="text.role" />:&nbsp;</label>
										<div class="select-wrapper">
											<select class="form-control ddsencillo c39c" id="roleList" onchange="createObjectPermiso();getValidation()">
												<c:forEach var="rl" items="${listr}">
													<option value="${rl.roleid}"  ${roleuser==rl.roleid ? 'selected' : ''}  ${roleuser==rl.roleid ? 'disabled' : ''}>${rl.rolename}</option>
												</c:forEach>
											</select>
										</div>
										<button id="sendcopy" style="all:unset;background-color: #5FBEAA;margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;" >Copiar</button>
										<button value="closed" style="all:unset;background-color: #8B0000; margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;" onclick="openModalCopy()">Cancelar</button>
									</div>
									
								</div>
								
							</div>
							<button id="cancel" style="display: none;" onclick="cancelar()">Cancelar</button>
							<thead>
								<tr>
									<th style="text-align:left;">Nombre del m&oacute;dulo</th>
									<c:forEach var="permiso" items="${listpermisos}">
										<th style="text-align:left;">${permiso.privilege}</th>
									</c:forEach>
									
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
										<th style="text-align:left;">Todos</th>
									</c:if>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach var="menu" items="${listmenu}">
									<tr id="${menu.menuid}">
										<td >${menu.menu}</td>
										<c:forEach var="permiso" items="${listpermisos}">
											<c:set var="count" value="0" scope="page" />
											<c:forEach var="pu" items="${listuser}">
												<c:if test="${pu.menuid==menu.menuid && pu.privilegesid==permiso.privilegesid}">
													<c:set var="count" value="1" scope="page" />
													<td>
														<label style=" cursor: default;">
															<input type="checkbox" style="display: none;" id="mp_${permiso.privilegesid}" onchange="changeTxt();changePrivileges(); isChange()" class="checkPrivileges" menuid="${menu.menuid}" value="${permiso.privilegesid}">
															<div style="user-select: none;color: #212121;"><fmt:message key="status.active" /></div>
														</label>
													</td>
												</c:if>
											</c:forEach>
											<c:if test="${count==0}">
												<td>
													<label style="cursor: default;">
														<input type="checkbox" style="display: none;" id="mp_${permiso.privilegesid}" onchange="changeTxt();changePrivileges(); isChange()" class="checkPrivileges" menuid="${menu.menuid}" value="${permiso.privilegesid}">
														<div style="user-select: none;color: #212121;"><fmt:message key="status.desactive" /></div>
													</td>
												</label>
											</c:if>
										</c:forEach>
										<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
										<td class="tac">
											<i class="fa-sharp fa-solid fa-check-double checksAll" menuid="${menu.menuid}"></i>
										</td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>						
					</div>
				</div>
			</div>
			<div class="hidden-xs" style="height:300px;"></div><!--Demo only-->
		</div><!--container-->
	</div><!--content-->
	<footer class="footer text-right">
		<fmt:message key="text.copyright" />
	</footer>
</div>

<div id="edit-privilege-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-privilege-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="privileges.update" /></h4> 
			</div>
			<div class="modal-body">
				<form id="formeditpermisos">
					<input type="hidden" id="editdataid" name="editdataid"/>
					<input type="hidden" id="roleid" name="roleid"/>
					
					<div class="form-group row ">
						<div class="col-xs-12">
							<div style="display:none;" id="EditPrivilegeError" class="alert alert-danger fade in">
								<a href="#" class="close" aria-label="close" onclick="$('#EditPrivilegeError').toggle();">&times;</a>
								<p id="putEditPrivilegeError"></p>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<p id="editRoleTitle"></p>
								<span id="editRoleName" class="form-control unuse"></span>
							</div>
							<div class="form-group">
								<label class="switch">
									<input type="checkbox" id="editallPriv" name="edit" onclick="allChecks();" />
									<span class="slider round"></span>
								</label>
								<label for="editallPriv" class="switch">
									<span class="sr-only b-chkAll"><fmt:message key="privileges.allprivileges" /></span>
								</label>
							</div>
							<div id="div_edit_permiso" style="padding-left:15px;">
								
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" onclick="updateEditPrivilege();" class="btn btn-info waves-effect waves-light">
					<fmt:message key="button.savechanges" />
				</button>
			</div>
		</div>
	</div>
</div><!--/.modal-->
<!--==============================================================-->
<!--End Right content here-->
<!--==============================================================-->
<script src="resources/assets/js/jquery.min.js"></script>
<!-- Modal-Effect -->
<script src="https://unpkg.com/@sjmc11/tourguidejs/dist/tour.js" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="resources/assets/plugins/custombox/js/custombox.min.js"></script>
<script src="resources/assets/plugins/custombox/js/legacy.min.js"></script>
<script src="resources/assets/js/jetaccess.js"></script>
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
<script src="resources/assets/js/complementos.js"></script>


<script type="text/javascript">
	TableManageButtons.init();

	function checks(id){
		var chk=$("#"+id).is(':checked'),priv=$("input[name^='mp_']");
		for(o=0; o<priv.length; o++)
			if(priv[o].checked==!1){
				chk=!1; 
				break;
			}
		$('#editallPriv').prop("checked", chk);
	}

	function allChecks(){
		$("input[name^='mp_']").prop("checked", $('#editallPriv').is(':checked'));
	}
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
		//loadPriv();
	});
</script>