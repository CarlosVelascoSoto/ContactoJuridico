<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<title><fmt:message key="text.resource"/>: ${rec[0].recurso} - Contacto jur&iacute;dico</title>

<c:set var="nMov" value="${shmovpriv.origin=='own'?1:0}" />
<c:set var="eMov" value="${shmovpriv.origin=='own'?1:0}" />
<c:set var="dMov" value="${shmovpriv.origin=='own'?1:0}" />
<c:set var="vMov" value="${shmovpriv.origin=='own'?1:0}" />
<c:forTokens items="${shmovpriv['shpriv']}" delims="," var="p">
	<c:if test="${p==1}"><c:set var="nMov" value="1" /></c:if>
	<c:if test="${p==2}"><c:set var="eMov" value="1" /></c:if>
	<c:if test="${p==3}"><c:set var="dMov" value="1" /></c:if>
	<c:if test="${p==4}"><c:set var="vMov" value="1" /></c:if>
</c:forTokens>

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
<link rel="stylesheet" type="text/css" href="resources/local/general/dash/movements.css">
<link rel="stylesheet" type="text/css" href="resources/local/general/dash/choices.min.css">

<c:if test="${ctr==0}"><style>.dt-buttons{display:none;}</style></c:if>
<style>
	.newmov{padding:10px 25px;float:right;-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px}
	.originProtTable{width:100%}
	.custombox-modal-container{width:70% !important}
</style>

<div class="content-page">
	<div class="container-fluid">
		<div class="row topblank"></div>
		<div class="row">
			<div class="col-lg-12 col-xl-12">
				<div class="card-box">
					<div>
						<table class="w100p">
							<thead>
								<tr>
									<th class="blueTitle capitalize" colspan="4">
										<span style="padding-left:10px"><fmt:message key='text.resources'/></span><br>
										<span style="font-size:18px;padding-left:10px">
											<c:choose>
												<c:when test="${rec[0].recursotipo=='1'}"><fmt:message key="text.review" /></c:when>
												<c:when test="${rec[0].recursotipo=='2'}"><fmt:message key="text.complaint" /></c:when>
												<c:when test="${rec[0].recursotipo=='3'}"><fmt:message key="text.claim" /></c:when>
												<c:when test="${rec[0].recursotipo=='4'}"><fmt:message key="text.unconformity" /></c:when>
												<c:when test="${rec[0].recursotipo=='4'}"><fmt:message key="text.hindrance" /></c:when>
											</c:choose>
										</span><br><br>
										<input type="hidden" id="referencetype" value="recursoid">
										<input type="hidden" id="referenceid" value="${rec[0].recursoid}">
									</th>
								</tr>
							</thead>
							<tbody style="border-radius:2px;border:1px solid #eee">
								<tr style="font-weight:bold;border-collapse:collapse;border-bottom:1px solid #aaa">
									<th><fmt:message key="text.resource" /></th>
									<th><fmt:message key="text.recurring" /></th>
									<th><fmt:message key="text.resolutionAppl" /></th>
									<th><fmt:message key="text.datenoticeApplRs" /></th>
								</tr>
								<tr>
									<td class="blueTitle"><span style="font-size:20px">${rec[0].recurso}</span></td>
									<td>${rec[0].recurrente}</td>
									<td>${rec[0].resolucionrecurrida}</td>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${rec[0].fechanotificacionresolucionrecurrida}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${rec[0].fechanotificacionresolucionrecurrida}" /></c:otherwise>
										</c:choose>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div style="margin:10px 0">
						<table class="originProtTable table table-striped table-bordered table-responsive">
							<thead>
								<tr><th><h3 class="blueTitle capitalize" style="padding-left:10px"><fmt:message key='text.originprotection'/></h3></th></tr>
							</thead>
							<tbody style="border-radius:2px;border:1px solid #eee">
								<tr>
									<c:if test="${amparo[0].amparotipo==1}">
										<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />" href="protectiondashboard?language=${language}&rid=${amparo[0].amparoid}">${amparo[0].amparo}</a></td>
										<td><fmt:message key='text.directprotection'/></td>
									</c:if>
									<c:if test="${amparo[0].amparotipo==2}">
										<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />" href="indprotectiondashboard?language=${language}&rid=${amparo[0].amparoid}">${amparo[0].amparo}</a></td>
										<td><fmt:message key='text.indprotection'/></td>
									</c:if>
								</tr>
							</tbody>
						</table>
					</div>

					<div style="margin:10px 0">
						<table class="w100p">
							<tbody style="border-radius:2px;border:1px solid #eee">
								<tr style="font-weight:bold;border-collapse:collapse;border-bottom:1px solid #aaa">
									<td><fmt:message key='text.interpositionRvwdate'/></td>
									<td><fmt:message key='text.dateadmissionApProc'/></td>
									<td><fmt:message key='text.daterefcollcourt'/></td>
									<td><fmt:message key='text.resourcesent'/></td>
								</tr>
								<tr>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${rec[0].fechainterposicionrecurso}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${rec[0].fechainterposicionrecurso}" /></c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${rec[0].fechaadmisiontramiterecurso}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${rec[0].fechaadmisiontramiterecurso}" /></c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${rec[0].fecharemisionaltribunalcolegiado}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${rec[0].fecharemisionaltribunalcolegiado}" /></c:otherwise>
										</c:choose>
									</td>
									<td>${rec[0].recursoturnadoa}</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div style="margin:25px 0">
						<table class="w100p">
							<tbody style="border-radius:2px;border:1px solid #eee">
								<tr style="font-weight:bold;border-collapse:collapse;border-bottom:1px solid #aaa">
									<td><fmt:message key='text.dateadmissionCllCourt'/></td>
									<td><fmt:message key='text.notifdateadmissionCllCourt'/></td>
									<td><fmt:message key='text.adhesiveRvwAplDate'/></td>
									<td><fmt:message key='text.dateshiftpresent'/></td>
								</tr>
								<tr>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${rec[0].fechaadmisiontribunalcolegiado}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${rec[0].fechaadmisiontribunalcolegiado}" /></c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${rec[0].fechanotificacionadmisiontribunalcolegiado}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${rec[0].fechanotificacionadmisiontribunalcolegiado}" /></c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${rec[0].fecharecursorevisionadhesivo}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${rec[0].fecharecursorevisionadhesivo}" /></c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${rec[0].fechaturnoaponencia}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${rec[0].fechaturnoaponencia}" /></c:otherwise>
										</c:choose>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div style="margin:25px 0">
						<table class="w100p">
							<tbody style="border-radius:2px;border:1px solid #eee">
								<tr style="font-weight:bold;border-collapse:collapse;border-bottom:1px solid #aaa">
									<td><fmt:message key='text.sessiondateprojectRslc'/></td>
									<td><fmt:message key='text.resolutiondate'/></td>
								</tr>
								<tr>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${rec[0].fechasesionproyectoresolucion}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${rec[0].fechasesionproyectoresolucion}" /></c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${rec[0].fecharesolucion}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${rec[0].fecharesolucion}" /></c:otherwise>
										</c:choose>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div style="margin:10px 0">
						<h3 class="blueTitle capitalize" style="padding-left:10px"><fmt:message key='text.relatedmatters'/></h3>
						<div style="max-height:300px;overflow:auto;border:1px solid #eee">
							<table class="relatedmatters table table-striped table-bordered table-responsive">
							<tbody>
								<c:forTokens items="${relDocs}" var="row" delims="^">
								<tr>
									<c:set var="docInfo" value="${fn:split(row, '|')}"/>
									<c:set var="noNum" value="${not empty docInfo[2]?docInfo[2]:'- - - - -'}" />
									
									<c:if test="${docInfo[0]=='ju'}">
										<td><fmt:message key='text.trial' /></td>
										<td><a class="asLink c39c trn2ms"
											title="<fmt:message key='text.viewdetails' /> - <fmt:message key='text.trial' /> ${noNum}"
											href="trialsdashboard?language=${language}&rid=${docInfo[1]}">${noNum}</a></td>
									</c:if>
									<c:if test="${docInfo[0]=='ap'}">
										<td><fmt:message key='text.appeal' /></td>
										<td><a class="asLink c39c trn2ms"
											title="<fmt:message key='text.viewdetails' /> - <fmt:message key='text.appeal' /> ${noNum}"
											href="appealsdashboard?language=${language}&rid=${docInfo[1]}">${noNum}</a></td>
									</c:if>
									<c:if test="${docInfo[0]=='ad'}">
										<td><fmt:message key='text.directprotection' /></td>
										<td><a class="asLink c39c trn2ms"
											title="<fmt:message key='text.viewdetails' /> - <fmt:message key='text.directprotection' /> ${noNum}"
											href="protectiondashboard?language=${language}&rid=${docInfo[1]}">${noNum}</a></td>
									</c:if>
									<c:if test="${docInfo[0]=='ai'}">
										<td><fmt:message key='text.indprotection' /></td>
										<td><a class="asLink c39c trn2ms"
											title="<fmt:message key='text.viewdetails' /> - <fmt:message key='text.indprotection' /> ${noNum}"
											href="indprotectiondashboard?language=${language}&rid=${docInfo[1]}">${noNum}</a></td>
									</c:if>
									<c:if test="${docInfo[0]=='re'}">
										<td><fmt:message key='text.resource' /></td>
										<td><a class="asLink c39c trn2ms"
											title="<fmt:message key='text.viewdetails' /> - <fmt:message key='text.resource' /> ${noNum}"
											href="resourcedashboard?language=${language}&rid=${docInfo[1]}">${noNum}</a></td>
									</c:if>
									<c:if test="${docInfo[0]=='cn'}">
										<td><fmt:message key='text.consultation' /></td>
										<td><a class="asLink c39c trn2ms"
											title="<fmt:message key='text.viewdetails' /> - <fmt:message key='text.consultation' /> ${noNum}"
											href="consultasdashboard?language=${language}&rid=${docInfo[1]}">${noNum}</a></td>
									</c:if>
								</tr>
								</c:forTokens>
							</tbody>
							</table>
						</div>
					</div>
					
				</div>
			</div>
		</div>

		<c:if test="${vMov==1}">
		<div class="row">
			<div class="col-lg-12 col-xl-12">
				<div class="card-box" style="min-height:450px">
					<div class="m-b-15">
						<span class="mb-3 titlemov"><fmt:message key='text.movements'/></span>
						<c:if test="${nMov==1}">
						<button type="button" id="addNewMove" class="btn btn-blue waves-effect waves-light waves-input-wrapper newbtnright">
							<fmt:message key='button.new' />
						</button>
						</c:if>
					</div>
					<div class="card-box table-responsive">
						<div class="table-subcontrols">
							<div class="row inlineflex filter_mov_acttype">
								<select id="choices-multiple-notebooktype"
									placeholder="<fmt:message key="text.notebooktype" />..." multiple>
									<option value="<fmt:message key='text.principal'/>"><fmt:message key='text.principal'/></option>
									<option value="<fmt:message key='text.incidental'/>"><fmt:message key='text.incidental'/></option>
									<option value="<fmt:message key='text.evidences'/>"><fmt:message key='text.evidences'/></option>
								</select>
							</div>
							<button type="button" id="btnfilter" class="btn btn-info btn-filter trn2ms" title="<fmt:message key="text.filtersbycolumns" />"
								onclick="$('#movList thead tr:eq(1)').toggle();"><span class="glyphicon glyphicon-filter"></span> 
							</button>
							<button id="clearfilters" class="btn clearfilters trn2ms" title="<fmt:message key="text.clearfiters" />"
								onclick="cleanTableFilters(this)"><span class="glyphicon glyphicon-filter"></span><i class="material-icons">close</i>
							</button>
						</div>
						<table id="movList" data-order='[[1,"asc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th class="dnone">id</th>
									<th class="tac">No.</th>
									<th><fmt:message key="text.movement" /></th>
									<th><fmt:message key="text.notebooktype" /></th>
									<th class="tac mw100p"><fmt:message key="text.filingdate" /></th>
									<th class="tac mw100p"><fmt:message key="text.agreementdate" /></th>
									<th class="tac mw100p"><fmt:message key="text.notificationdate" /></th>
									<th class="tac"><fmt:message key="text.obserservations" /></th>
									<th class="tac mw100p"><fmt:message key="text.schedule" /></th>
									<th class="tac"><fmt:message key="text.document" /></th>
									<c:if test="${eMov==1}"><c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
										<th><fmt:message key="text.edit" /></th></c:if>
									</c:if>
									<c:if test="${dMov==1}">
										<th class="tac" style="display:none !important"><fmt:message key="text.delete" /></th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="mov" items="${movList}" varStatus="count">
								<tr>
									<td class="dnone">${mov.movimientoid}</td>
									<td class="tac"><c:out value="${count.index+1}" /></td>
									<td>${mov.movimiento}</td>
									<td class="capitalize">${mov.cuaderno}</td>
									<td class="tac capitalize">
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yy" value="${mov.fechapresentacion}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yy" value="${mov.fechapresentacion}" /></c:otherwise>
										</c:choose>
									</td>
									<td class="tac capitalize">
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yy" value="${mov.fechaacuerdo}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yy" value="${mov.fechaacuerdo}" /></c:otherwise>
										</c:choose>
									</td>
									<td class="tac capitalize">
										<c:choose>
											<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yy" value="${mov.fechanotificacion}" /></c:when>
											<c:otherwise><fmt:formatDate pattern="MMM dd yy" value="${mov.fechanotificacion}" /></c:otherwise>
										</c:choose>
									</td>
									<td>${mov.observaciones}</td>
									<td>
										<c:forEach var="eCal" items="${schedules}">
											<c:if test="${eCal.movimientoid==mov.movimientoid}">
												<fmt:formatDate pattern="yyyy-MM-dd" value="${eCal.dateini}" />&nbsp;<fmt:formatDate pattern="HH:mm" value="${eCal.dateini}" />&nbsp;hrs - ${eCal.appointment} 
											</c:if>
										</c:forEach>
									</td>
									<td style="display:inlineflex">
										<c:forEach var="doc" items="${doctos}" varStatus="count">
											<c:if test="${mov.movimientoid==doc.idregister}">
												<div class="tac"><a href="${doc.path}" title="${doc.filename}" target="_blank" class="link">
													<img alt="${doc.filename}" title="${doc.filename}" src="${doc.img}" width="30px" height="30px"
														onerror="this.src='${doc.path}'"><br>
													${doc.filename}</a>
												</div>
											</c:if>
										</c:forEach>
									</td>
									<c:if test="${eMov==1}"><c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${mov.movimientoid}" data-toggle="modal" data-target="#moves-edit-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getMovToEditDash(id);">
											<i class="md md-edit"></i>
										</a>
									</td>
									</c:if></c:if>
									<c:if test="${dMov==1}">
									<td class="dnone">
										<a href="#" id="${mov.movimientoid}" class="table-action-btn"
											title="<fmt:message key='text.delete' />"
											onclick="delMovDash(id);">
											<i class="md md-edit"></i>
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
		</div>
		</c:if>
	</div>
</div>

<c:if test="${nMov==1}">
<div id="moves-modal" class="modal-demo">
	<button type="button" class="close" onclick="$('#moves-modal').modal('hide').slideUp();" style="position:relative">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>

	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key="text.movement" /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form id="newMoveForm" class="inlineblock">
				<div class="form-group inlineblock w100p">
					<div class="col-xs-12 alert alert-danger fade in" id="errorOnAddMov" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddMov').toggle();">&times;</a>
						<p id="puterrorOnAddMov"></p>
						<input type="hidden" id="trial" value="${juicio[0].juicioid}">
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="actType" class="supLlb"><em>* </em><fmt:message key='text.actiontype' /></label>
							<div class="select-wrapper w100p">
								<select class="form-control ddsencillo c39c" id="actType"></select>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="cuaderno" class="supLlb"><em>* </em><fmt:message key='text.notebooktype' /></label>
							<div class="select-wrapper w100p">
								<select class="form-control ddsencillo c39c" id="cuaderno">
									<option disabled selected><fmt:message key="text.select"/></option>
									<option value="principal"><fmt:message key="text.principal"/></option>
									<option value="incidental"><fmt:message key="text.incidental"/></option>
									<option value="pruebas"><fmt:message key="text.evidences"/></option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="movement" class="supLlb"><em>* </em><fmt:message key='text.movement' /></label>
							<input type="text" class="form-control c39c" id="movement" autocomplete="off">
						</div>
					</div>
					<div class="actype1">
						<div class="col-xs-12 col-sm-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="filingdate" class="supLlb"><fmt:message key='text.filingdate' /></label>
								<input type="hidden" name="fromDate" id="filingdate" />
								<input type="text" class="form-control c39c" id="filingdateFix" data-date="addMov" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="actype2 dnone">
						<div class="col-xs-12 col-sm-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="agreementdate" class="supLlb"><fmt:message key='text.agreementdate' /></label>
								<input type="hidden" name="fromDate" id="agreementdate" />
								<input type="text" class="form-control c39c" id="agreementdateFix" data-date="addMov" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="notificationdate" class="supLlb"><fmt:message key='text.notificationdate' /></label>
								<input type="hidden" name="fromDate" id="notificationdate" />
								<input type="text" class="form-control c39c" id="notificationdateFix" data-date="addMov" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="movObserv" class="supLlb"><fmt:message key='text.obserservations' /></label>
							<textarea class="form-control c39c txArea4r" id="movObserv"></textarea>
						</div>
					</div>
				</div>
				<div class="form-group inlineblock" style="background-color:#F4F9FF">
					<div class="col-xs-11">
						<h3><fmt:message key='text.scheduleactivity' /></h3>
					</div>
					<div class="col-xs-1 m-t-20">
						<button type="button" class="cleanMovAct" title="<fmt:message key='text.clear' />" class="p-0">
							<span class="glyphicon glyphicon-erase asLink"></span>
						</button>
					</div>
					<div class="col-xs-12">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="activity" class="supLlb"><fmt:message key='text.activity' /></label>
							<input type="text" class="form-control c39c" id="activity" autocomplete="off">
						</div>
					</div>
					<div class="col-xs-6 col-sm-2" style="padding-left:3%">
						<label for="startschdFix"><fmt:message key='text.start' /></label>
					</div>
					<div class="col-xs-6 col-sm-4">
						<div class="form-group fieldinfo inlineblock" style="width:80%">
							<label for="startschdFix" class="supLlb"><fmt:message key='text.date' /></label>
							<input type="hidden" id="startschd" data-date="addMovAct">
							<input type="text" class="form-control c39c" id="startschdFix" data-date="addMovAct"
								title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group fieldinfo inlineblock w100p">
							<div class="select-wrapper w48p">
								<label for="starttimehour" class="supLlb"><fmt:message key='text.hours' /></label>
								<select class="form-control ddsencillo c39c" data-time="hour" id="starttimehour"></select>
							</div>
							<div class="select-wrapper w48p">
								<label for="starttimeminute" class="supLlb"><fmt:message key='text.minutes' /></label>
								<select class="form-control ddsencillo c39c" data-time="minute" id="starttimeminute"></select>
							</div>
						</div>
					</div>

					<div class="col-xs-2" style="padding-left:3%">
						<label for="endschdFix"><fmt:message key='text.ends' /></label>
					</div>
					<div class="col-xs-10 col-sm-4">
						<div class="form-group fieldinfo inlineblock" style="width:80%">
							<label for="endschdFix" class="supLlb"><fmt:message key='text.date' /></label>
							<input type="hidden" id="endschd" data-date="addMovAct">
							<input type="text" class="form-control c39c" id="endschdFix" data-date="addMovAct"
								title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group fieldinfo inlineblock w100p">
							<div class="select-wrapper w48p">
								<label for="endtimehour" class="supLlb"><fmt:message key='text.hours' /></label>
								<select class="form-control ddsencillo c39c" data-time="hour" id="endtimehour"></select>
							</div>
							<div class="select-wrapper w48p">
								<label for="endtimeminute" class="supLlb"><fmt:message key='text.minutes' /></label>
								<select class="form-control ddsencillo c39c" data-time="minute" id="endtimeminute"></select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group inlineblock w100p">
					<div id="areaMovsUpload"><span class="textContent"><fmt:message key='label.dropzone' /></span>
						<div id='uploadXMovs' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
					</div>
					<div>
						<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
						onclick="$('#targetDZ').val('#uploadXMovs')" title="<fmt:message key='text.usecam.browser' />" >
							<i class="material-icons">camera_alt</i>
						</a>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addMovementDash(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="$('#moves-modal').modal('hide').slideUp();" id="addMovementCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>
</c:if>

<c:if test="${eMov==1}"><c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
<div id="moves-edit-modal" class="modal-demo">
	<button type="button" class="close" onclick="$('#moves-edit-modal').modal('hide').slideUp();" style="position:relative;">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>

	<h4 class="custom-modal-title"><fmt:message key="text.edit" /> <fmt:message key="text.movement" /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form id="editMoveForm" class="inlineblock">
				<div class="form-group inlineblock w100p">
					<div class="col-xs-12 alert alert-danger fade in" id="errorOnEditMov" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEditMov').toggle();">&times;</a>
						<p id="puterrorOnEditMov"></p>
						<input type="hidden" id="edTrial" value="${juicio[0].juicioid}">
						<input type="hidden" id="edMovId">
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="edActType" class="supLlb"><fmt:message key='text.actiontype' /></label>
							<div class="select-wrapper w100p">
								<select class="form-control ddsencillo c39c" id="edActType"></select>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="edCuaderno" class="supLlb"><fmt:message key='text.notebooktype' /></label>
							<div class="select-wrapper w100p">
								<select class="form-control ddsencillo c39c" id="edCuaderno">
									<option disabled selected><fmt:message key="text.select"/></option>
									<option value="principal"><fmt:message key="text.principal"/></option>
									<option value="incidental"><fmt:message key="text.incidental"/></option>
									<option value="pruebas"><fmt:message key="text.evidences"/></option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="edMovement" class="supLlb"><fmt:message key='text.movement' /></label>
							<input type="text" class="form-control c39c" id="edMovement" autocomplete="off">
						</div>
					</div>
					<div class="actype1">
						<div class="col-xs-12 col-sm-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="edFilingdate" class="supLlb"><fmt:message key='text.filingdate' /></label>
								<input type="hidden" name="fromDate" id="edFilingdate" />
								<input type="text" class="form-control c39c" id="edFilingdateFix" data-date="editMov" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="actype2 dnone">
						<div class="col-xs-12 col-sm-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="edAgreementdate" class="supLlb"><fmt:message key='text.agreementdate' /></label>
								<input type="hidden" name="fromDate" id="edAgreementdate" />
								<input type="text" class="form-control c39c" id="edAgreementdateFix" data-date="editMov" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="form-group fieldinfo inlineblock w100p">
								<label for="edNotificationdate" class="supLlb"><fmt:message key='text.notificationdate' /></label>
								<input type="hidden" name="fromDate" id="edNotificationdate" />
								<input type="text" class="form-control c39c" id="edNotificationdateFix" data-date="editMov" title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="edMovObserv" class="supLlb"><fmt:message key='text.obserservations' /></label>
							<textarea class="form-control c39c txArea4r" id="edMovObserv"></textarea>
						</div>
					</div>
				</div>
				<div class="form-group inlineblock" style="background-color:#F4F9FF">
					<div class="col-xs-11">
						<h3><fmt:message key='text.scheduleactivity' /></h3>
					</div>
					<div class="col-xs-1 m-t-20">
						<button type="button" class="cleanMovAct" title="<fmt:message key='text.clear' />" class="p-0">
							<span class="glyphicon glyphicon-erase asLink"></span>
						</button>
					</div>
					<div class="col-xs-12">
						<div class="form-group fieldinfo inlineblock w100p">
							<label for="edActivity" class="supLlb"><fmt:message key='text.activity' /></label>
							<input type="text" class="form-control c39c" id="edActivity" autocomplete="off">
						</div>
					</div>
					<div class="col-xs-6 col-sm-2" style="padding-left:3%">
						<label for="edStartschdFix"><fmt:message key='text.start' /></label>
					</div>
					<div class="col-xs-6 col-sm-4">
						<div class="form-group fieldinfo inlineblock" style="width:80%">
							<label for="edStartschdFix" class="supLlb"><fmt:message key='text.date' /></label>
							<input type="hidden" id="edStartschd" data-date="editMovAct">
							<input type="text" class="form-control c39c" id="edStartschdFix" data-date="editMovAct"
								title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group fieldinfo inlineblock w100p">
							<div class="select-wrapper w48p">
								<label for="edStarttimehour" class="supLlb"><fmt:message key='text.hours' /></label>
								<select class="form-control ddsencillo c39c" data-time="hour" id="edStarttimehour"></select>
							</div>
							<div class="select-wrapper w48p">
								<label for="edStarttimeminute" class="supLlb"><fmt:message key='text.minutes' /></label>
								<select class="form-control ddsencillo c39c" data-time="minute" id="edStarttimeminute"></select>
							</div>
						</div>
					</div>

					<div class="col-xs-2" style="padding-left:3%">
						<label for="edEndschdFix"><fmt:message key='text.ends' /></label>
					</div>
					<div class="col-xs-10 col-sm-4">
						<div class="form-group fieldinfo inlineblock" style="width:80%">
							<label for="edEndschdFix" class="supLlb"><fmt:message key='text.date' /></label>
							<input type="hidden" id="edEndschd" data-date="addMovAct">
							<input type="text" class="form-control c39c" id="edEndschdFix" data-date="editMovAct"
								title="<fmt:message key='text.selectenterdate'/>" autocomplete="off">
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group fieldinfo inlineblock w100p">
							<div class="select-wrapper w48p">
								<label for="edEndtimehour" class="supLlb"><fmt:message key='text.hours' /></label>
								<select class="form-control ddsencillo c39c" data-time="hour" id="edEndtimehour"></select>
							</div>
							<div class="select-wrapper w48p">
								<label for="edEndtimeminute" class="supLlb"><fmt:message key='text.minutes' /></label>
								<select class="form-control ddsencillo c39c" data-time="minute" id="edEndtimeminute"></select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group inlineblock w100p">
					<div id="areaEditMovUpload"><span class="textContent"><fmt:message key='label.dropzone' /></span>
						<div id='uploadXEditMov' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
					</div>
					<div>
						<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
						onclick="$('#targetDZ').val('#uploadXEditMov')" title="<fmt:message key='text.usecam.browser' />" >
							<i class="material-icons">camera_alt</i>
						</a>
					</div>
				</div>
			</form>
			<button type="button" onclick="return saveEditMovDash(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="$('#moves-edit-modal').modal('hide').slideUp();" id="editMovementCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>
</c:if></c:if>

<jsp:include page="resourcefornew.jsp" flush="true"/>

<script src="resources/assets/js/jquery.min.js"></script>
<script src="resources/assets/plugins/bootstrap-datepicker-v1.10/js/bootstrap-datepicker.min.js"></script>

<div class="modal" id="camera-modal" role="dialog" aria-labelledby="camera-modal" aria-hidden="true">
	<button type="button" class="close _mod" onclick="$('#camera-modal').modal('hide');">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<jsp:include page="/WEB-INF/views/general/cam/cam.jsp" flush="true"/>
</div>
<a class="btnScrollUp inlineblock blackCircle trn3ms"><i class="material-icons">&#xe316;</i></a>

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
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>
<script src="resources/assets/js/customDropZone.js"></script>

<script src="resources/assets/js/complementos.js"></script>
<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/local/general/dash/movements.js"></script>
<script src="resources/local/general/dash/choices.min.js"></script>
<script src="resources/local/commonlaws/juicios.js"></script>
<script src="resources/local/cjf/resources.js"></script>
<script src="resources/local/cjf/rscdash.js"></script>

<c:if test="${nMov==1}">
<script>
	function addMovementDash(e){
		$('input, select').parent().removeClass('has-error');
		var err = "", reftype = $('#referencetype').val(), refid = $('#referenceid').val(),
			movement = $('#movement').val(), cuaderno = $('#cuaderno').val(),
			filingdate = $('#filingdate').val(), agreementdate = $('#agreementdate').val(),
			notificationdate = $('#notificationdate').val(), actType = $('#actType option:selected').val(),
			act = $('#activity').val(), movObserv = $('#movObserv').val(),
			startschd = (typeof $('#startschd').val() == 'undefined') ? '' : $('#startschd').val(),
			endschd = (typeof $('#endschd').val() == 'undefined') ? '' : $('#endschd').val(),
			stHour = $('#starttimehour').val() || '', stMinute = $('#starttimeminute').val() || '',
			endHour = $('#endtimehour').val() || '', endMinute = $('#endtimeminute').val() || '';
		var starttime = stHour + ':' + stMinute, endtime = endHour + ':' + endMinute;
		cuaderno = cuaderno || '';
		if(actType == ''){
			tag='actType';
			err = 'err_select_acttype';
		}else if(cuaderno == ''){
			tag='cuaderno';
			err = 'err_enter_notebooktype';
		}else if(movement == ''){
			tag='movement';
			err = 'err_enter_movement';
		}else if(act != '' || startschd != '' || endschd != '' || stHour != ''
				|| stMinute != '' || endHour != '' || endMinute != '') {
			if(act == ''){
				tag='activity';
				err = 'err_enter_activity';
			}else if(startschd == ''){
				tag='startschd';
				err = 'err_enter_startdate';
			}else if(endschd == ''){
				tag='endschd';
				err = 'err_enter_enddate';
			}else if(stHour == '' || stMinute == ''){
				tag=stHour==''?'starttimehour':'starttimeminute';
				err = 'err_enter_starttime';
			}else if(endHour == '' || endMinute == ''){
				tag=tag=stHour==''?'endtimehour':'endtimeminute';
				err = 'err_enter_endtime';
			}else{
				var d1 = new Date(startschd + 'T' + starttime + ':00Z'),
					d2 = new Date(endschd + 'T' + endtime + ':00Z');
				if(d1 >= d2){
					tag='endtimehour';
					err = 'err_startDT_mayor_endDT';
				}
			}
		}
		if(err != ""){
			$('#' + tag).parent().addClass('has-error');
			$('#puterrorOnAddMov').html(i18n(err));
			$('#errorOnAddMov').show();
			$('#moves-modal').animate({scrollTop : 0}, '1000');
			return false;
		}
		var param = 'reftype=' + reftype + '&referenceid=' + refid + '&cuaderno=' + cuaderno
			+ "&movement=" + movement + "&filingdate=" + filingdate + '&agreementdate=' + agreementdate
			+ "&notificationdate=" + notificationdate + "&actType=" + actType
			+ "&movObserv=" + movObserv + "&act=" + act + "&fini=" + $('#startschd').val()
			+ "&ffin=" + endschd + "&tini=" + starttime + "&tfin=" + endtime;
		$('input[name^="fileuploadx_"]').each(function(i) {
			param += "&" + $(this).attr("name") + "=" + $(this).val();
		});
		$.ajax({
			type : "POST",
			url : ctx + "/addNewMove",
			data : param,
			async : false,
			success : function(data) {
				if(data == "msg_data_saved") {
					swal(i18n("msg_success"), i18n(data), "success");
					location.href = location.href.replace(/^(.*)\#.*$/, '$1');
				} else if(data = "msg_data_saved_no_email") {
					swal(i18n("msg_warning"), i18n(data), "warning");
					location.href = location.href.replace(/^(.*)\#.*$/, '$1');
				} else {
					$('#puterrorOnAddMov').html(i18n(data));
					$('#errorOnAddMov').show();
				}
			},error : function(e) {
				$('#puterrorOnAddMov').html(i18n('err_record_no_saved'));
				$('#errorOnAddMov').show();
			}
		});
	};
</script>
</c:if>

<script type="text/javascript">
	var dttable=null;
	jQuery(document).ready(function($){
		let dtId1='#movList', txtfnd=i18n('msg_search');
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

		// Filtros Tipo de Cuaderno (ini)
		dttable=$(dtId1).DataTable();
		$('#choices-multiple-notebooktype').on('change',function (){
			if($(this).val()==null)
				dttable.columns(3).search("",!0,!1).draw();
			else
				dttable.columns(3).search($(this).val().join('|'),!0,!1).draw();
		});
		 var multipleCancelButton = new Choices('#choices-multiple-notebooktype',{
			 removeItemButton: true,
			 searchResultLimit:5
		});
		// Filtros Tipo de Cuaderno (fin)

		var lang=getLanguageURL();
		$('[data-date]').datepicker({
			autoclose:true,
			calendarWeeks:true,
			clearBtn:true,
			dateFormat:(getFormatPatternDate(lang)).toLowerCase(),
			daysOfWeekHighlighted:"0",
			format:(getFormatPatternDate(lang)).toLowerCase(),
			language:lang,
			todayBtn:true,
			todayHighlight:true,
			weekStart:1
		});
		$('[data-date]').datepicker().on('changeDate blur clearDate',function(){
			let id=(this.id).replace(/Fix$/ig,'');
			$('#'+id).val(this.date==null?'':this.date);
			let newdate=$(this).datepicker('getDate');
			setBootstrapUtcDate(id,this.id,newdate);
			if(this.id=='startschdFix'&&$('#endschd').val()=='')
				setBootstrapUtcDate('endschd','endschdFix',newdate);
			else if(this.id=='edStartschdFix'&&$('#edEndschd').val()=='')
				setBootstrapUtcDate('edEndschd','edEndschdFix',newdate);
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
/*	$('.modal-demo').on('hide.bs.modal', function(){
		clearTemp();
	});*/
</script>