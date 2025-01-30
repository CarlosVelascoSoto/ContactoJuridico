<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<title><fmt:message key="text.resources"/> - Contacto jur&iacute;dico</title>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<!--link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker-v1.10/css/bootstrap-datepicker3.min.css"-->
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">

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

<script src="resources/assets/js/complementos.js"></script> 

<style>
	#resource-modal{margin:0 !important}
	.custombox-modal-container{width:70% !important;max-width:850px}
	#edit-modal > .modal-dialog {width:100%;max-width:800px;max-width:850px}
	.custom-modal-text{padding:0 20px}
	#edit-modal{display:none}

	@media screen and (max-width:1024px){
		.custombox-modal-container{width:100% !important}
	}
	@media screen and (max-width:660px){
		.containTL{max-width:605px}
	}
</style>

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addNewResource" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
 								data-overlayColor="#36404a" class="btn btn-default btn-md waves-effect waves-light m-b-60 w-lg"> 
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title" style="text-transform:capitalize"><fmt:message key="text.resources" /></h4>
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
								onclick="cleanTableFilters(this)"><span class="glyphicon glyphicon-filter"></span><i class="material-icons">close</i>
							</button>
						</div>
						<table id="datatable-buttons" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.resource" /></th>
									<th><fmt:message key="text.originprotection" /></th>
									<th><fmt:message key="text.protectiontype" /></th>
									<th><fmt:message key="text.resourcetype" /></th>
									<th><fmt:message key="text.recurring" /></th>
									<th><fmt:message key="text.resolutionAppl" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'><th><fmt:message key="text.delete" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="r" items="${recursos}">
								<c:if test="${r.value['re.recursoid']>0}">
								<tr>
									<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />"
										href="resourcedashboard?language=${language}&rid=${r.value['re.recursoid']}">${r.value["re.recurso"]}</a></td>
									<c:if test="${r.value['am.amparotipo']=='1'}">
										<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />"
											href="protectiondashboard?language=${language}&rid=${r.value['am.amparoid']}">${r.value['am.amparo']}</a></td>
										<td><fmt:message key='text.direct'/></td>
									</c:if>
									<c:if test="${r.value['am.amparotipo']=='2'}">
										<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />"
											href="indprotectiondashboard?language=${language}&rid=${r.value['am.amparoid']}">${r.value['am.amparo']}</a></td>
										<td><fmt:message key='text.indirect'/></td>
									</c:if>
									<td>
									<c:choose>
										<c:when test="${r.value['re.recursotipo']=='1'}"><fmt:message key="text.review" /></c:when>
										<c:when test="${r.value['re.recursotipo']=='2'}"><fmt:message key="text.complaint" /></c:when>
										<c:when test="${r.value['re.recursotipo']=='3'}"><fmt:message key="text.claim" /></c:when>
										<c:when test="${r.value['re.recursotipo']=='4'}"><fmt:message key="text.unconformity" /></c:when>
									</c:choose>
									</td>
									<td>${r.value["re.recurrente"]}</td>
									<td>${empty r.value["re.resolucionrecurrida"]?'- - - - -':r.value["re.resolucionrecurrida"]}</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${r.value['re.recursoid']}" data-toggle="modal"
											data-target="#edit-modal" class="table-action-btn"
											title="<fmt:message key='text.edit' />" onclick="getDetailsToEditRec(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if>
									<c:if test='${fn:contains(arrayPermisos, dPermiso )}'>
									<td class="tac">
										<a href="#" class="table-action-btn" id="${r.value['re.recursoid']}"
											onclick="deleteResource(id);"><i class="md md-close"></i></a>
									</td>
									</c:if>
								</tr>
								</c:if>
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

<jsp:include page="resourcefornew.jsp" flush="true"/>

<div id="edit-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-modal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="cleanFields" data-reset="#edit-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
					<span class="glyphicon glyphicon-erase asLink"></span>
				</button>
				<button type="button" class="close closeModal" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="editResources"><fmt:message key="resource.update" /><span id="edResource"></span></h4>
			</div>
			<div class="modal-body">
				<form id="formEditResource">
					<div class="form-group ">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEditRsc" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEditRsc').toggle();">&times;</a>
								<p id="putErrorOnEditRsc"></p>
								<input type="hidden" id="edId">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<div class="form-group inlineflex w100p">
								<label for="edResourcenumber" class="supLlb"><fmt:message key='text.resourcenumber' /></label>
								<input type="text" class="form-control c39c" id="edResourcenumber">
							</div>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<div class="form-group inlineflex w100p">
								<label for="edResourcetype" class="supLlb"><em>* </em><fmt:message key='text.resourcetype' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="edResourcetype">
										<option value="" selected><fmt:message key="text.select" /></option>
										<option value="1"><fmt:message key="text.review" /></option>
										<option value="2"><fmt:message key="text.complaint" /></option>
										<option value="3"><fmt:message key="text.claim" /></option>
										<option value="4"><fmt:message key="text.unconformity" /></option>
										<option value="5"><fmt:message key="text.hindrance" /></option>
									</select>
								</div>
							</div>
						</div>
					</div>

					<div class="row"><div class="col-sm-12"><h3><fmt:message key='text.originprotection' /></h3></div></div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-6">
							<div class="form-group inlineflex w100p">
								<label for="edClientRsc" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="edClientRsc" onblur="$('#edOriginType').val('')" placeholder="<fmt:message key="text.select" />">
								<div class="containTL" data-list="clientRsc">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="edClientRscList"></table>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-3">
							<div class="form-group inlineflex w100p">
								<label for="edOriginType" class="supLlb"><em>* </em><fmt:message key='text.origintype' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="edOriginType">
										<option value="" selected disabled><fmt:message key="text.select" /></option>
										<option value="1"><fmt:message key="text.directprotection" /></option>
										<option value="2"><fmt:message key="text.indprotection" /></option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-3">
							<label for="edOriginTypeId" class="supLlb"><em>* </em><fmt:message key='text.originprotection' /></label>
							<div class="form-group fieldinfo inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" data-list="editProt"
									onfocus="getProtections('edOriginTypeId','ul');"
									placeholder="<fmt:message key="text.select" />" >
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="edOriginTypeId"></ul>
							</div>
						</div>
					</div>

					<div class="row"><div class="col-sm-12"><h3><fmt:message key='text.resource' /></h3></div></div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edRecurring"><em>* </em><fmt:message key='text.recurring' /></label>
								<input type="text" class="form-control c39c" id="edRecurring">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edResolutionAppl"><fmt:message key='text.resolutionAppl' /></label>
								<input type="text" class="form-control c39c" id="edResolutionAppl">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edDatenoticeApplRs"><fmt:message key='text.datenoticeApplRs' /></label>
								<input type="hidden" id="edDatenoticeApplRs">
								<input type="text" class="form-control c39c" id="edDatenoticeApplRsFix" data-date="edit" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edInterpositionRvwdate"><fmt:message key='text.interpositionRvwdate' /></label>
								<input type="hidden" id="edInterpositionRvwdate">
								<input type="text" class="form-control c39c" id="edInterpositionRvwdateFix" data-date="edit" autocomplete="off">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15" for="edDateadmissionApProc"><fmt:message key='text.dateadmissionApProc' /></label>
								<input type="hidden" id="edDateadmissionApProc">
								<input type="text" class="form-control c39c" id="edDateadmissionApProcFix" data-date="edit" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15" for="edDaterefcollcourt"><fmt:message key='text.daterefcollcourt' /></label>
								<input type="hidden" id="edDaterefcollcourt">
								<input type="text" class="form-control c39c" id="edDaterefcollcourtFix" data-date="edit" autocomplete="off">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15" for="edResourcesent"><fmt:message key='text.resourcesent' /></label>
								<input type="text" class="form-control c39c" id="edResourcesent" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15" for="edDateadmissionCllCourt"><fmt:message key='text.dateadmissionCllCourt' /></label>
								<input type="hidden" id="edDateadmissionCllCourt">
								<input type="text" class="form-control c39c" id="edDateadmissionCllCourtFix" data-date="edit" autocomplete="off">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15" for="edNotifdateadmissionCllCourt"><fmt:message key='text.notifdateadmissionCllCourt' /></label>
								<input type="hidden" id="edNotifdateadmissionCllCourt">
								<input type="text" class="form-control c39c" id="edNotifdateadmissionCllCourtFix" data-date="edit" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edAdhesiveRvwAplDate"><fmt:message key='text.adhesiveRvwAplDate' /></label>
								<input type="hidden" id="edAdhesiveRvwAplDate">
								<input type="text" class="form-control c39c" id="edAdhesiveRvwAplDateFix" data-date="edit" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15" for="edDateshiftpresent"><fmt:message key='text.dateshiftpresent' /></label>
								<input type="hidden" id="edDateshiftpresent">
								<input type="text" class="form-control c39c" id="edDateshiftpresentFix" data-date="edit" autocomplete="off">
							</div>
						</div>
					</div>
	
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb" for="edSessiondateprojectRslc"><fmt:message key='text.sessiondateprojectRslc' /></label>
								<input type="hidden" id="edSessiondateprojectRslc">
								<input type="text" class="form-control c39c" id="edSessiondateprojectRslcFix" data-date="edit" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group fieldinfo inlineblock w100p">
								<label class="supLlb t-15" for="edResolutiondate"><fmt:message key='text.resolutiondate' /></label>
								<input type="hidden" id="edResolutiondate">
								<input type="text" class="form-control c39c" id="edResolutiondateFix" data-date="edit" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div id="areaRscUpload"><span class="textContent"><fmt:message key='label.dropzone' /></span>
							<div id="uploadXEditResource" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
						</div>
					</div>
					<div>
						<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
						onclick="$('#targetDZ').val('#uploadXEditResource')" title="<fmt:message key='text.usecam.browser' />" >
							<i class="material-icons">camera_alt</i>
						</a>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateResoruce();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>

<script src="resources/assets/js/jquery.min.js"></script>

<div class="modal" id="camera-modal" role="dialog" aria-labelledby="camera-modal" aria-hidden="true">
	<button type="button" class="close _mod" onclick="$('#camera-modal').modal('hide');">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<jsp:include page="/WEB-INF/views/general/cam/cam.jsp" flush="true"/>
</div>
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

<!--script src="resources/assets/plugins/bootstrap-datepicker-v1.10/js/bootstrap-datepicker.min.js"></script-->
<script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="resources/assets/js/i18n_AJ.js"></script>
<!-- script src="resources/assets/js/complementos.js"></script-->
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>
<script src="resources/assets/js/customDropZone.js"></script>
<script src="resources/local/cjf/resources.js"></script>

<script type="text/javascript">
	var dttable=null;
	jQuery(document).ready(function($){
		let dtId1='#datatable-buttons', txtfnd=i18n('msg_search');
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
	$('.modal-demo').on('hide.bs.modal', function(){
		clearTemp();
	});
</script>