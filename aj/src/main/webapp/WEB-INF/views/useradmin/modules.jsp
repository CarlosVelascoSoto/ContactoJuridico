<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!--i18n-->
<fmt:setBundle basename="messages" />
<c:set var="newadd" scope="page" value="${1}" />
<c:set var="vis" scope="page" value="${1}" />
<c:set var="ctr" scope="page" value="${1}" />
<c:set var="edt" scope="page" value="${1}" />
<c:set var="del" scope="page" value="${1}" />
<c:set var="pcs" scope="page" value="${1}" />
<c:set var="cnf" scope="page" value="${1}" />

<link rel="stylesheet" type="text/css" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">
<script src="resources/assets/js/complementos.js"></script>

<style>
	.tac,th{text-align:center;}
	em{color:red}
	.dt-buttons.btn-group a{margin-right:10px;}
	.unuse{background-color:#eee; padding:5px 10px;}
	.b-chk{position:relative; width:100%; top:-10px; margin:10px;}
	.b-chkAll{
		position:relative;
		width:450%;
		height:25px;
		top:9px;
		margin:10px;
		display:inline-block;
	}<c:if test="${ctr==0}">
	.dt-buttons{display:none;}</c:if>
	.slider.round{border-radius:34px;}
	.slider.round:before{border-radius:50%;}
	.sweet-overlay{z-index:10010 !important}
	.showSweetAlert{z-index:10011 !important}
	.capitalize{text-transform:capitalize}
	#editModuleRoleid::-ms-expand{display:none;}
	#editModuleRoleid{
		-moz-appearance:none;
		-webkit-appearance:none;
	}
</style>
 <!--==============================================================-->
 <!--Start right Content here-->
 <!--==============================================================-->
 <div class="content-page">
	<div class="content"><!--Start content-->
		<div class="container">
			<div class="row"><!--Page-Title-->
				<div class="col-sm-12">
					<c:if test="${newadd==1}">
					<div class="btn-group pull-right m-t-15">
						<a href="#addmodule-modal" id="addbtn" class="btn btn-default btn-md waves-effect waves-light m-b-30"  
							data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200" data-overlayColor="#36404a">
							<fmt:message key="button.new" />
						</a>
					</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="module.panel.title" /></h4>
					<ol class="breadcrumb"></ol>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-page-length="20" data-order='[[3, "desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.code" /></th>
									<th><fmt:message key="text.module" /></th>
									<th><fmt:message key="text.description" /></th>
									<c:if test="${edt==1}"><th><fmt:message key="text.edit" /></th></c:if>
								<c:if test="${del==1}"><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="m" items="${list}">
									<tr>
										<td>${m.moduleid}</td>
										<td>${m.alias}</td>
										<td>${m.description}</td>
										<c:if test="${edt==1}">
										<td class="tac">
											<a href="#" class="table-action-btn" data-toggle="modal" data-target="#edit-module-modal" onclick="editModuleDetails('${m.moduleid}');">
												<i class="md md-edit"></i>
											</a>
										</td>
										</c:if>
										<c:if test="${del==1}">
										<td class="tac">
											<a href="#" class="table-action-btn" data-toggle="modal" data-target="#delete-module-modal"
											onclick="deleteModuleId('${m.moduleid}','${m.name}');">
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
			<div class="hidden-xs" style="height:300px;"></div><!--Demo only-->		
		</div> <!--container-->
	</div> <!--content-->
	<footer class="footer text-right">
		<fmt:message key="text.copyright" />
	</footer>
</div>

<div id="addmodule-modal" class="modal-demo">
	<button type="button" class="close" style="top:13px;" onclick="Custombox.close();">
		<span title="<fmt:message key='button.close' />">&times;</span>
	</button>
	<h4 class="custom-modal-title"><span class="sr-only" style="position:relative;"><fmt:message key="button.add.module" /></span></h4>
	<div class="custom-modal-text text-left">
		<form method="post">
			<div class="form-group ">
				<div class="col-xs-12">
					<div style="display:none;" id="addModuleError" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#addModuleError').toggle();">&times;</a>
						<p id="putError"></p>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-xs-12"><span class="sr-only b-chk"><fmt:message key="text.moduletype" /></span><br></div>
				<div class="col-xs-12">
					<div class="col-xs-4">
						<label><input type="radio" id="jspname" name="jspmenu" style="margin-bottom:10px" checked>&emsp;<fmt:message key="text.menu" /></label>
					</div>
					<div class="col-xs-8">
						<label><input type="radio" id="ismenu" name="jspmenu" style="margin-bottom:10px">&emsp;<fmt:message key="text.group" /></label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<em>* </em><span class="sr-only b-chk"><fmt:message key="text.title" /></span><br>
				<input type="text" class="form-control" id="alias" autocomplete="off">
			</div>
			
			<div class="form-group">
				<em>* </em><span><fmt:message key="text.jspname" /></span>
				<input type="text" class="form-control" id="modname" autocomplete="off">
			</div>
			<div class="form-group">
				<em>* </em><span class="sr-only b-chk"><fmt:message key="text.description" /></span><br>
				<input type="text" class="form-control" id="description" autocomplete="off">
			</div>

			<button type="button" onclick="saveNewModule(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>
</div>

<div id="edit-module-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-module-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="module.panel.popup.update.details" /></h4>
			</div>
			<div class="modal-body">
				<div class="form-group row ">
					<div class="col-xs-12">
						<div style="display:none;" id="EditModuleError" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#EditModuleError').toggle();">&times;</a>
							<p id="putEditError"></p>
							<input id="editModuleId" type="hidden">
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-12"><span class="sr-only b-chk"><fmt:message key="text.moduletype" /></span><br></div>
					<div class="col-xs-12">
						<div class="form-group">
							<div class="col-xs-4">
								<label><fmt:message key="text.menu" />&nbsp;<input type="radio" id="edJspname" name="edJspmenu" style="margin-bottom:10px" checked></label>
							</div>
							<div class="col-xs-8">
								<label><fmt:message key="text.group" />&nbsp;<input type="radio" id="edIsmenu" name="edJspmenu" style="margin-bottom:10px"></label>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<em>* </em><label for="editAlias" class="control-label"><fmt:message key="text.title" /></label>
							<input id="editAlias" type="text" class="form-control">
						</div>
						<div class="form-group">
							<em>* </em><span><fmt:message key="text.jspname" /></span>
							<input id="editModuleName" type="text" class="form-control" autocomplete="off">
						</div>
						<div class="form-group">
							<em>* </em><label for="editDescription" class="control-label"><fmt:message key="text.description" /></label>
							<input id="editDescription" type="text" class="form-control">
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" onclick="updateEditModule(this);" class="btn btn-info waves-effect waves-light">
					<fmt:message key="button.savechanges" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="delete-module-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="custom-width-modalLabel" aria-hidden="true" style="display:none;">
	<div class="modal-dialog" style="width:35%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize" id="custom-width-modalLabel">
					<fmt:message key="panel.popup.msg2delete" />
				</h4>
			</div>
			<div class="modal-body">
				<h4><b><fmt:message key="text.module" />:</b></h4>&nbsp;<p id="delmoduleid" class="unuse"></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default waves-effect" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
				<button type="button" id="deleteModuleButton" class="btn btn-primary waves-effect waves-light">
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
<script src="resources/assets/js/modules.js"></script>
<script src="resources/assets/js/i18n_AJ.js"></script>

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
			paging:false,
			fixedColumns:{
				leftColumns:1,
				rightColumns:1
			}
		});
	});
	TableManageButtons.init();
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
</script>