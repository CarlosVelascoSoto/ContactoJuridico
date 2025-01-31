<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<title><fmt:message key="text.consults"/> - Contacto jur&iacute;dico</title>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker-v1.10/css/bootstrap-datepicker3.min.css">

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
<script src="resources/assets/js/jquery.min.js"></script>

<style>
	#errorOnAddClient, #errorOnAddConsultation, #errorOnEditCons{display:none}
	#add-consulting-modal{position:absolute;height:90vh !important;margin:auto;
		top:0px;left:0px;right:0px;overflow:auto;z-index:1050
	}
	#add-clients-modal{position:absolute;top:0;left:0;
		-webkit-box-shadow:0px 0 15px #444;box-shadow:0px 0 15px #444;z-index:10015
	}
	#add-clients-modal div.firmdatatabs{display:block}
	#add-clients-modal div.firmdatatabs:not(:first-child){display:none}
	.tabsmodal{position:relative;clear:both;margin:auto;background:#fff;
		-webkit-box-shadow:initial;box-shadow:initial;z-index:1050
	}
</style>

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
 						<div class="btn-group pull-right m-t-15">
 							<a href="#" id="addNewConsultation" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg">
								<fmt:message key="button.new" />
							</a>
						</div>
					</c:if>
					<h4 class="page-title capitalize"><fmt:message key="text.consults" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="card-box table-responsive">
						<div class="table-subcontrols">
							<button type="button" id="btnfilter" class="btn btn-filter trn2ms" title="<fmt:message key="text.filtersbycolumns" />"
								onclick="$('#datatable-buttons thead tr:eq(1)').toggle();"><span class="glyphicon glyphicon-filter"></span> 
							</button>
							<button id="clearfilters" class="btn clearfilters trn2ms" title="<fmt:message key="text.clearfiters" />"
								onclick="cleanTableFilters(this)"><span class="glyphicon glyphicon-filter"></span><i class="material-icons">close</i>
							</button>
						</div>
						<table id="datatable-buttons" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.client" /></th>
									<th><fmt:message key="text.trial" /></th>
									<th><fmt:message key="text.matter" /></th>
									<th><fmt:message key="text.consultation" /></th>
									<th><fmt:message key="text.date" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="c" items="${consultas}">
								<tr>
									<td><a class="asLink c39c trn2ms"
										title="<fmt:message key='text.viewdetails' />&nbsp;-&nbsp;<fmt:message key='text.consultation' />&nbsp;${c[5]}"
										href="consultasdashboard?language=${language}&rid=${c[0]}">${c[5]}</a></td>
									<td>
									<c:if test="${empty c[8]}">- - - - -</c:if>
									<c:if test="${not empty c[8]}">
										<a class="asLink c39c trn2ms"
											title="<fmt:message key="text.viewdetails" />&nbsp;-&nbsp;<fmt:message key='text.trial' />&nbsp;${c[7]}"
											href="trialsdashboard?language=${language}&rid=${c[8]}">${c[7]}</a>
									</c:if>
									</td>
									<td>${c[6]}</td>
									<td>${c[1]}</td>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${c[2]}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${c[2]}" /></c:otherwise>
										</c:choose>
									</td>
									<td class="tac">
										<a href="#" id="${c[0]}" data-toggle="modal" data-target="#edit-consulting-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsConsultations(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="add-consulting-modal" class="modal-demo">
	<button type="button" class="cleanFields" data-reset="#add-consulting-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
		<span class="glyphicon glyphicon-erase asLink"></span>
	</button>
	<button type="button" class="close" onclick="$('#add-consulting-modal').modal('hide');" title="<fmt:message key='button.close' />" style="position:relative">
		<span>&times;</span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /></h4>
	<div class="custom-modal-text text-left p-0">
		<div class="panel-body">
			<form method="post" id="formAddCons">
				<div class="form-group inlineblock">
					<div class="row">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnAddConsultation" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddConsultation').toggle();">&times;</a>
								<p id="putErrorOnAddConsultation"></p>
							</div>
						</div>
					</div>
					<div class="row" data-subgroupcons="consClientList">
						<div class="col-sm-11">
							<div class="form-group inlineflex w100p">
								<label for="consClient" class="supLlb"><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="consClient" data-cons="addclientMod"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<div class="containTL" style="padding-left:15px">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="consClientList"></table>
								</div>
							</div>
						</div>
						<div class="col-xs-1 p-0">
							<button type="button" class="btn-blue-light trn2ms c39c" id="addClientCons"
								value="<fmt:message key='button.newcustomer'/>" title="<fmt:message key='button.add.client'/>">
								<i class="material-icons">person_add</i>
							</button>
						</div>
					</div>
<jsp:include page="consultasfornew.jsp" flush="true"/>
				</div>
			</form>
			<div class="modal-footer">
				<button type="button" onclick="return addConsultation(this);" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="$('#add-consulting-modal').modal('hide');" id="addConsultCancel" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="edit-consulting-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="edit-consulting-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="cleanFields" data-reset="#edit-consulting-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
					<span class="glyphicon glyphicon-erase asLink"></span>
				</button>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title"><fmt:message key="consultation.update" /></h4>
			</div>
			<div class="modal-body">
				<form id="formEditCons">
					<div class="form-group ">
						<div id="errorOnEditCons" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEditCons').toggle();">&times;</a>
							<p id="putErrorOnEditCons"></p>
							<input type="hidden" id="editconsultId">
						</div>
					</div>
					
					<div class="row" data-subgroupcons="editconsClientList">
						<div class="col-sm-11">
							<div class="form-group inlineflex w100p">
								<label for="editconsClient" class="supLlb"><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="editconsClient" data-cons="editclientMod"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<div class="containTL">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="editconsClientList"></table>
								</div>
							</div>
						</div>
						<div class="col-xs-1 p-0">
							<button type="button" class="btn-blue-light trn2ms c39c" id="editClientCons"
								value="<fmt:message key='button.newcustomer'/>" title="<fmt:message key='button.add.client'/>">
								<i class="material-icons">person_add</i>
							</button>
						</div>
					</div>
<jsp:include page="consultasforedit.jsp" flush="true"/>
				</form>
				<button type="button" onclick="return updateConsultation();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="add-clients-modal" class="modal-demo">
	<button type="button" class="close" onclick="$('#add-clients-modal').hide();"
		style="position:relative;margin:0;color:#fff !important">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key='text.client' /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<div class="col-xs-12">
				<div id="errorOnAddClient" class="alert alert-danger fade in">
				<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddClient').toggle();">&times;</a>
				<p id="putErrorOnAddClient"></p>
				</div>
			</div>
			<form>
				<jsp:include page="/WEB-INF/views/catalogs/clientsfornew.jsp" flush="true"/>
			</form>
			<button type="button" onclick="return addClients('home-cons');" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" class="btn btn-danger waves-effect waves-light m-l-10"
				data-dismiss="modal" aria-hidden="true" onclick="$('#add-clients-modal').hide();">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>

	<script src="resources/local/catalogs/clients.js"></script>
</div>

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

<!--script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script-->
<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>
<script src="resources/assets/js/customDropZone.js"></script>
<script src="resources/local/consultas/consultas.js"></script>

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
	});
	$('.modal-demo').on('hide.bs.modal', function(){
		clearTemp();
	});
</script>