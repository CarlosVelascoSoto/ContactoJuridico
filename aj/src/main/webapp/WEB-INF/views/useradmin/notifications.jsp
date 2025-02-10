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

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row m-b-10"><!-- Page-Title -->
				<div class="col-sm-12">
					<h4 class="page-title capitalize"><fmt:message key="home.notification.title" /></h4>
				</div>
			</div>
			<div class="row m-t-10">
				<div class="col-sm-12">
					<div class="form-group inlineflex w100p">
						<label for="edNid" class="supLlb">* <fmt:message key='text.id' /></label>
						<input type="text" class="form-control c39c" id="edNid" autocomplete="off">
					</div>
					<div class="btn-group pull-right m-t-15">
						<a href="#" id="editNid" data-toggle="modal" data-target="#edit-notification-modal" 
							class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"
							data-overlayColor="#36404a" title="<fmt:message key='text.get' />"
							onclick="getDetailsToEdit();"><fmt:message key='text.get' />
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<footer class="footer text-right">
	<fmt:message key="text.copyright" />
</footer>

<div id="edit-notification-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-notification-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="home.notification.title" /></h4>
			</div>
			<div class="modal-body">
				<form id="formsnedit">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEditNtfy" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEditNtfy').toggle();">&times;</a>
								<p id="puterrorOnEditNtfy"></p>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="editaction" class="supLlb"><fmt:message key='text.action' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="editaction">
										<option value="0"><fmt:message key="text.new" /></option>
										<option value="1"><fmt:message key="text.edited" /></option>
										<option value="2"><fmt:message key="text.deleted" /></option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6">
							<div class="form-group fieldinfo inlineblock" style="width:100%">
								<label for="editcapturedate" class="supLlb"><fmt:message key='text.capturedate' /></label>
								<input type="hidden" name="capturedate" id="editcapturedate" />
								<input type="text" class="form-control c39c" id="editcapturedateFix" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12">
							<label for="editmoduleref" class="supLlb"><fmt:message key='text.module' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel"
									placeholder="<fmt:message key="text.select" />"  autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editmoduleref"></ul>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12 col-sm-3">
							<div class="form-group inlineflex w100p">
								<label for="editrefid" class="supLlb"><fmt:message key='text.reference' /> (<fmt:message key='text.id' />)</label>
								<input type="text" class="form-control c39c" id="editrefid" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6">
							<div class="form-group inlineflex w100p">
								<label for="editreference" class="supLlb">
									<fmt:message key='text.proceedings' />/<fmt:message key='text.document' />
								</label>
								<input type="text" class="form-control c39c" id="editreference" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-3">
							<div class="form-group inlineflex w100p m-0">
								<a id="editToLink" target="_blank" class="m-t-2" style="margin-top:10px"
									href="javascript:void()">
									<fmt:message key='text.linktorigin' />&nbsp;<i class="fa fa-external-link"></i>
								</a>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label for="editClient" class="supLlb"><fmt:message key='text.client' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editClient"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="editActionsDetails" class="supLlb"><fmt:message key='text.action' /></label>
								<textarea class="form-control c39c txArea4r" id="editActionsDetails"></textarea>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12">
							<div class="form-group inlineflex w100p">
								<label for="editConfirmation" class="supLlb"><fmt:message key='text.confirmation' /></label>
								<textarea class="form-control c39c txArea4r" id="editConfirmation"></textarea>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateNtfyDirect();" class="btn btn-default waves-effect waves-light">
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
<script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="./web-resources/libs/sweet-alert2/sweetalert2.min.js"></script>


<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/complementos.js"></script>
<script src="resources/assets/js/globalfunctions.js"></script>
<script src="resources/assets/js/notifications.js"></script>

<script type="text/javascript">
	TableManageButtons.init();
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});

	var dateOp={weekStart:1,daysOfWeekHighlighted:"0",language:lang,autoclose:true,
		todayBtn:true,clearBtn:true,calendarWeeks:true,todayHighlight:true},
		lang=getLanguageURL();
	$('#datatable-buttons_filter').css('float','left');
	$('.dt-buttons').css('float','right');
	$(document).ready(function (){
		$('#datatable-buttons').DataTable();
	});
	
	$('#editcapturedateFix').datepicker(dateOp);

	$("#editcapturedateFix").datepicker().on('changeDate',function(e){$('#editcapturedate').val(dp(e.date));});
	$("#editcapturedateFix").datepicker().on('blur change',function(e){
		$('#editcapturedate').val(simpleChangePatt("yyyy-MM-dd",$("#editcapturedateFix").val()));
	});
</script>