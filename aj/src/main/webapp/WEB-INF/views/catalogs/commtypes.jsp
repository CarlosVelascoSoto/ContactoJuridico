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

<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedHeader.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/responsive.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/scroller.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.colVis.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/datatables/fixedColumns.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">

<style>
	.microtip{font-size:12px;color:#444}
	.microtip tr, .microtip th, .microtip td{border:1px solid black;border-collapse:collapse}
	.microtip th{font-weight:900}
	.microtip td{padding:3px 5px}
	.microtip th, .microtip td:first-child{text-align:center}
	.microtip b{color:mediumslateblue}
	.helpaction{font-size:14px;margin-left:5px;padding:0 4px;border:1px solid #fff;
		-webkit-border-radius:50%;-moz-border-radius:50%;border-radius:50%;
		-webkit-box-shadow:0 0 2px #000;box-shadow:0 0 2px #000;background-color:#444;color:#fff
	}
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
 							<a href="#" id="addNewCommType" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.communicationtypes" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="card-box table-responsive">
						<table id="datatable-buttons" data-order='[[1,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.communicationtype" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="c" items="${comm}">
								<tr>
									<td>${c.description}</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${c.commtypeid}" data-toggle="modal" data-target="#edit-commtype-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsToEdit(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" id="${c.commtypeid}" onclick="deleteCommTypes(id);"><i class="md md-close"></i></a>
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
<div id="commtype-modal" class="modal-demo">
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form id="formsnnew">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnAdd" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAdd').toggle();">&times;</a>
						<p id="putErrorOnAdd"></p>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="commtype" class="supLlb">*<fmt:message key='text.communicationtype' /></label>
								<input type="text" class="form-control c39c" id="commtype" autocomplete="off">
							</div>
						</div>
						<div class="row"><h5><fmt:message key="text.directaction"/>&nbsp;
							<button class="helpaction" type="button" title="<fmt:message key='text.help'/>"
								onclick="togglehelp('#addhelp')">?</button></h5>
						</div>
						<div class="row m-t-10">
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="hrefAction" class="supLlb">href=</label>
									<input type="text" class="form-control c39c" id="hrefAction" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<div class="form-group inlineflex w100p">
									<label for="onclickAction" class="supLlb">onclick=</label>
									<input type="text" class="form-control c39c" id="onclickAction" autocomplete="off">
								</div>
							</div>
							<div class="col-xs-12">
								<table class="microtip" id="addhelp">
									<caption>Forma de uso.</caption>
									<thead>
										<tr>
											<th>Comando</th>
											<th><fmt:message key="text.action"/></th>
											<th>Ejemplo</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>contactinfo</td>
											<td>Tomar&aacute; la informaci&oacute;n almacenada de 'forma de contacto'.</td>
											<td>tel:<b>contactinfo</b></td>
										</tr>
										<tr>
											<td>message</td>
											<td>Mostrar&aacute; un espacio para permitir al usuario ingresar un texto limitado a 256 caracteres y sin formatos.
											<td>tel:contactinfo?<b>message</b></td>
										</tr>
										<tr>
											<td>rtf</td>
											<td>Mostrar&aacute; un espacio para permitir al usuario ingresar un texto sin l&iacute;mite de caracteres y con opci&oacute;n a establecer formatos (colores, tamaño, estilos, etc.).
											<td>tel:contactinfo?<b>rtf</b></td>
										</tr>
										<tr>
											<td>data-info="x"</td>
											<td>Tomar&aacute; un texto de una de estas formas:
												<ul>
													<li>S&iacute; "x" es un n&uacute;mero entero y mayor a cero, como resultado mostrar&aacute; un espacio para permitir al usuario ingresar un texto limitado a 256 caracteres y sin formato.</li> 
													<li>S&iacute; "x" es un texto cualquiera, como resultado tomar&aacute; el texto del objeto que contenga dicha propiedad.</li>
												</ul>
											<td>email:mailto:correo@dominio.com?cc:<b>data-info="1"</b>&bcc:<b>data-info="2"</b>
												<br><br><br>miFuncion('<b>data-info="var_01"</b>')</td>
										</tr>
										<tr>
											<td>Corchetes [ ]</td>
											<td>(S&oacute;lo para href) Indicar&aacute; que un dato sea opcional a incluirlo o no.</td>
											<td>tel:<b>[</b>contactinfo<b>]</b><b>[</b>?message<b>]</b></td>
										</tr>
									</tbody>
									<tfoot>
										<tr>
											<td colspan="3">NOTA: Si no es necesario incluir una acci&oacute;n directa, omitir esta tabla.</td>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
				</div>
			</form>
			<button type="button" onclick="return saveNewCommTypes(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addCommTypeCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-commtype-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-commtype-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="commtype.update" /></h4>
				<input type="hidden" id="edCommTypeId">
			</div>
			<div class="modal-body">
				<form id="formsnedit">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEdit" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEdit').toggle();">&times;</a>
								<p id="putErrorOnEdit"></p>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="edCommType" class="supLlb">* <fmt:message key='text.communicationtype' /></label>
								<input type="text" class="form-control c39c" id="edCommType" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row"><h5><fmt:message key="text.directaction"/>&nbsp;
						<button class="helpaction" type="button" title="<fmt:message key='text.help'/>"
							onclick="togglehelp('#edhelp')">?</button></h5>
					</div>
					<div class="row m-t-10">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="edHrefAction" class="supLlb">href=</label>
								<input type="text" class="form-control c39c" id="edHrefAction" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="edOnclickAction" class="supLlb">onclick=</label>
								<input type="text" class="form-control c39c" id="edOnclickAction" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12">
							<table class="microtip" id="edhelp">
								<caption>Forma de uso.</caption>
								<thead>
									<tr>
										<th>Comando</th>
										<th><fmt:message key="text.action"/></th>
										<th>Ejemplo</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>contactinfo</td>
										<td>Tomar&aacute; la informaci&oacute;n almacenada de 'forma de contacto'.</td>
										<td>tel:<b>contactinfo</b></td>
									</tr>
									<tr>
										<td>message</td>
										<td>Mostrar&aacute; un espacio para permitir al usuario ingresar un texto limitado a 256 caracteres y sin formatos.
										<td>tel:contactinfo?<b>message</b></td>
									</tr>
									<tr>
										<td>rtf</td>
										<td>Mostrar&aacute; un espacio para permitir al usuario ingresar un texto sin l&iacute;mite de caracteres y con opci&oacute;n a establecer formatos (colores, tamaño, estilos, etc.).
										<td>tel:contactinfo?<b>rtf</b></td>
									</tr>
									<tr>
										<td>data-info="x"</td>
										<td>Tomar&aacute; un texto de una de estas formas:
											<ul>
												<li>S&iacute; "x" es un n&uacute;mero entero y mayor a cero, como resultado mostrar&aacute; un espacio para permitir al usuario ingresar un texto limitado a 256 caracteres y sin formato.</li> 
												<li>S&iacute; "x" es un texto cualquiera, como resultado tomar&aacute; el texto del objeto que contenga dicha propiedad.</li>
											</ul>
										<td>email:mailto:correo@dominio.com?cc:<b>data-info="1"</b>&bcc:<b>data-info="2"</b>
											<br><br><br>miFuncion('<b>data-info="var_01"</b>')</td>
									</tr>
									<tr>
										<td>Corchetes [ ]</td>
										<td>(S&oacute;lo para href) Indicar&aacute; que un dato sea opcional a incluirlo o no.</td>
										<td>tel:<b>[</b>contactinfo<b>]</b><b>[</b>?message<b>]</b></td>
									</tr>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="3">NOTA: Si no es necesario incluir una acci&oacute;n directa, omitir esta tabla.</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateCommType();" class="btn btn-default waves-effect waves-light">
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

<script src="${pageContext.request.contextPath}/web-resources/libs/sweet-alert2/sweetalert2.min.js"></script>
<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script>
<script src="resources/assets/js/commtypes.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
</script>