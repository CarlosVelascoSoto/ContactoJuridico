<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<title><fmt:message key="text.trial"/>: ${juicio[0].juicio} - Contacto jur&iacute;dico</title>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<c:set var="nMov" value="1" />
<c:set var="eMov" value="1" />
<c:set var="dMov" value="1" />
<c:set var="vMov" value="1" />


<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker-v1.10/css/bootstrap-datepicker3.min.css">
<!-- link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css"> -->

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

<style>
/*	.dt-buttons{display:none}*/
	button.closeModal{position:-webkit-sticky !important;position:sticky !important;top:15px;color:#000 !important}	

	#errorOnAddProt,#errorOnAddIndProt,#errorOnAddApl{display:none}
	#wrapper{justify-items:center}
	#protection-modal{position:fixed;width:auto;height:90vh;margin:0 20%;top:0;overflow:auto;z-index:1050}
	.custom-modal-title{background-color:#fff}
/*	.modal,.modal-demo{height:auto !important;max-height:100vh !important}*/
	.parties tr:hover{-webkit-box-shadow:0 0 5px #ccc;box-shadow:0 0 5px #ccc}
/*	#appeal-modal{position:fixed;top:0;margin:0 30%;padding:0 !important;overflow:auto;z-index:1050}*/
/*	.newbtnright{padding:10px 25px;float:right;-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px}*/

	#clientname{text-align:left}
	.clientData{width:auto;min-width:250px;margin-left:5px}
	.displaydata{font-size:20px;font-weight:100;margin-left:5px;
		border:1px solid transparent;background-color:transparent;color:#aaa}
	.clientdivdata{display:table-cell;float:none;vertical-align:middle}
	.clientDataRow .data-client-sm{min-height:50px;
		-webkit-justify-content:center;justify-content:center;
		-webkit-align-self:center;align-self:center;margin:auto}
	.clientdivdata .data-client-sm >*{display:table-cell;vertical-align:middle}
	.clientdivdata,.clientdivdata2{max-width:850px}
	.clientsmdata{vertical-align:middle}
	.clientdivdata2{display:table-cell;float:none;overflow:auto}
	.clientinners{height:100%}
	.noclientdata{display:table-cell;float:none;margin-right:20px;padding:0 10px}
	.clientdivdata2{display:none}
	.clientdivdata,.clientdivdata2{min-height:110px}

	.regbtn{margin:0 30px 20px 0}
	.custombox-modal-container{width:70% !important}
	input[type=radio]{width:30px}

	#isProtDir,#isProtInd{display:none}
	#isProtDir .row{
		max-height:100px;
	}
	/* Multifilter */

	@media screen and (max-width:650px){
		#protection-modal,#appeal-modal,#moves-modal,#moves-edit-modal{width:100%;margin:0}
		.arrowdata{-webkit-transform:rotate(90deg);-ms-transform:rotate(90deg);
			-o-transform:rotate(90deg);transform:rotate(90deg)}
		.clientdivdata{display:flex;margin-bottom:0}
		.data-client-sm{display:inherit}
		.rounded-circle{display:flex;margin:auto}
		.clientsmdata{max-width:370px}
		.clientdivdata2{display:block;margin:20px 0 0 0}
		.clientdivdata2,.noclientdata{display:none !important}
	}
</style>
<div class="content-page">
	<div class="container-fluid">
		<div class="row topblank"></div>
		<div class="row inlineflex">
			<div class="card-box text-center clientdivdata">
				<div class="clientDataRow">
					<div class="col-xs-2 data-client-sm flex clientsmdata">
					<c:set var="noShorts" value="" />
					<c:forTokens items="${client[0].client}" delims=" " var="c">
						<c:set var="hasShort" value="0" />
						<c:forTokens items="DE,DEL,LA,LAS,EL,LOS,Y,AND,THE,OF" delims="," var="i">
							<c:if test="${c.toUpperCase()==i}"><c:set var="hasShort" value="${hasShort+1}" /></c:if>
						</c:forTokens>
						<c:if test="${hasShort==0}"><c:set var="noShorts" value="${noShorts}_${c.toUpperCase()}" /></c:if>
					</c:forTokens>
					<c:set var="nrrName" value="${fn:split(noShorts,'_')}" />
					<c:set var="n" value="${(fn:length(arrName))==1?1:(fn:length(arrName))>3?2:1}" />
					<c:set var="shortName" value="${arrName[0].substring(0,1)}${arrName[n].substring(0,1)}" />
					<c:choose>
						<c:when test="${photo.length()==0 || empty photo}">
							<img src="" class="rounded-circle avatar-lg img-thumbnail"
							alt="${shortName}" title="${client[0].client}"
							style="font-size:26px;width:50px !important;height:50px;min-width:50px;color:#ddd">
						</c:when>
						<c:when test="${photo.length()}">
							<c:if test="${client[0].photo!=''&&client[0].photo.indexOf('doctos/')>=0}">
								<c:set var="photo" value="${client[0].photo}" />
								<img src="${photo.substring(photo.indexOf('doctos/'), photo.length())}"
									class="rounded-circle avatar-lg img-thumbnail" title="${client[0].client}"
									alt="${shortName}" onerror="this.classList.add('-noimg');"
									style="font-size:26px;width:50px;height:50px;min-width:50px;min-width:50px;min-height:50px;color:#ddd">
							</c:if>
						</c:when>
					</c:choose>
					</div>
					<div class="col-xs-7 col-sm-7 col-md-7 col-lg-7 col-7 data-client-sm flex clientsmdata">
						<h3 class="mb-0" id="clientname">${client[0].client}</h3>
						<input id="clientid" type="hidden" value="${client[0].clientid}">
					</div>
					<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 col-1 data-client-sm flex clientsmdata">
						<button class="displaydata trn2ms" title="<fmt:message key='text.displaymoreinfo' />">&#8883;</button>
					</div>
				</div>
			</div>

			<div class="card-box clientdivdata2">
				<div class="text-left mt-3">
					<div class="inlineblock clientData"><p class="text-muted mb-2 font-13"><strong><fmt:message key="text.clientis" />: </strong><span class="ml-2">${juicio[0].clientecaracter}</span></p></div>
					<div class="inlineblock clientData"><p class="text-muted mb-2 font-13"><strong><fmt:message key="text.email" />: </strong><span class="ml-2">${client[0].email}</span></p></div>
					<div class="inlineblock clientData"><p class="text-muted mb-2 font-13"><strong><fmt:message key="text.phone" />: </strong><span class="ml-2 ">${client[0].phone}</span></p></div>
					<div class="inlineblock clientData"><p class="text-muted mb-1 font-13"><strong><fmt:message key="text.cellphone" />: </strong><span class="ml-2">${client[0].cellphone}</span></p></div>
					<div class="inlineblock clientData"><p class="text-muted mb-1 font-13"><strong><fmt:message key="text.birthday" />: </strong>
					<span class="ml-2">
						<c:choose>
							<c:when test="${language=='es'}"><fmt:formatDate pattern="dd MMM yyyy" value="${client[0].birthdate}" /></c:when>
							<c:otherwise><fmt:formatDate pattern="MMM dd yyyy" value="${client[0].birthdate}" /></c:otherwise>
						</c:choose>
					</span></p></div>
					<div class="inlineblock clientData"><p class="text-muted mb-1 font-13"><strong><fmt:message key="text.about" />:</strong> <span class="ml-2">${client[0].comments}</span></p></div>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="card-box">
			<div class="table-responsive">
				<table class="table table-striped table-bordered table-responsive">
					<thead>
						<tr>
							<th class="blueTitle capitalize" colspan="4">
								<span style="padding-left:10px"><fmt:message key='text.trial'/></span>
								<input type="hidden" id="referencetype" value="juicioid">
								<input type="hidden" id="referenceid" value="${juicio[0].juicioid}">
							</th>
						</tr>
					</thead>
					<tbody style="border-radius:2px;border:1px solid #eee">
						<tr style="font-weight:bold;border-collapse:collapse;border-bottom:1px solid #aaa">
							<td><fmt:message key='text.proceedings'/></td>
							<td><fmt:message key='text.court'/></td>
							<td><fmt:message key='text.matter'/></td>
							<td><fmt:message key='text.city'/></td>
						</tr>
						<tr>
							<td class="blueTitle"><span id="trialname" style="font-size:20px;padding-left:10px">${juicio[0].juicio}</span></td>
							<td>${juzgados[0].juzgado}</td>
							<td>${materias[0].materia}</td>
							<td>${cities[0].ciudad}
								<input type="hidden" id="stateidDefault" value="${cities[0].estadoid}">
								<input type="hidden" id="cityidDefault" value="${cities[0].ciudadid}">
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="table-responsive parties" style="margin:10px 0">
				<table class="table table-striped table-bordered table-responsive">
				<thead>
					<tr><th class="blueTitle capitalize" colspan="2"><span style="padding-left:10px"><fmt:message key='text.parties'/></span></th></tr>
				</thead>
				<tbody>
					<c:if test="${trialtype[0].requiereactor!='0'}">
						<tr>
							<td><fmt:message key='text.actor'/></td>
							<td>${juicio[0].actor}</td>
						<tr>
					</c:if>
					<c:if test="${trialtype[0].requieredemandado!='0'}">
						<tr>
							<td><fmt:message key='text.defendant'/></td>
							<td>${juicio[0].demandado}</td>
						<tr>
					</c:if>
					<c:if test="${trialtype[0].requieretercero!='0'}">
						<tr>
							<td><fmt:message key='text.thirdparty'/></td>
							<td>${juicio[0].tercero}</td>
						<tr>
					</c:if>
					<c:forEach var="ccol" items="${custcol}">
						<c:forEach var="cval" items="${custval}">
							<c:if test="${ccol.customcolumnid==cval.customcolumnid}">
								<tr>
									<td>${ccol.titulo}</td>
									<td>${cval.assignedvalue}</td>
								</tr>
							</c:if>
						</c:forEach>
					</c:forEach>
				</tbody>
				</table>
			</div>

			<div class="table-responsive" style="margin:10px 0">
				<table class="table table-striped table-bordered table-responsive">
					<thead>
						<tr>
							<th><h3 class="blueTitle capitalize" style="padding-left:10px"><fmt:message key='text.lawyers'/></h3></th>
						</tr>
					</thead>
					<tbody style="border-radius:2px;border:1px solid #eee">
						<tr style="font-weight:bold;border-collapse:collapse;border-bottom:1px solid #aaa">
							<td><fmt:message key='text.mainlawyer'/></td>
							<td><fmt:message key='text.lawyerassigned'/></td>
							<td><fmt:message key='text.lawyercounterpart'/></td>
						</tr>
						<tr>
							<td>${juicio[0].abogado}</td>
							<td>${userasig}</td>
							<td>${juicio[0].abogadocontraparte}</td>
						</tr>
					</tbody>
				</table>
			</div>

			<h3 class="blueTitle capitalize" style="padding-left:10px"><fmt:message key='text.relatedmatters'/></h3>
			<div class="table-responsive" style="max-height:300px;overflow:auto;border:1px solid #eee">
				<table class="relatedmatters table table-striped table-bordered table-responsive">
					<tbody>
						<c:forEach var="a" items="${appeals}"> 
							<tr>
								<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />"
									href="appealsdashboard?language=${language}&rid=${a.apelacionid}">${a.toca}</a></td>
								<td><fmt:message key='text.appeal'/></td>
							</tr>
						</c:forEach>
						<c:forEach var="am" items="${amparos}"> 
							<tr>
								<c:if test="${am.amparotipo==1}">
									<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />"
										href="protectiondashboard?language=${language}&rid=${am.amparoid}">${am.amparo}</a></td>
									<td><fmt:message key='text.directprotection'/></td>
								</c:if>

								<c:if test="${am.amparotipo==2}">
									<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />"
										href="indprotectiondashboard?language=${language}&rid=${am.amparoid}">${am.amparo}</a></td>
									<td><fmt:message key='text.indprotection'/></td>
								</c:if>
							</tr>
						</c:forEach>
						<c:forEach var="r" items="${recursos}"> 
							<tr>
								<c:if test="${r.recursotipo==1}">
									<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />"
										href="resourcedashboard?language=${language}&rid=${r.recursoid}">${r.recurso}</a></td>
									<td><fmt:message key='text.resource'/> (<small><fmt:message key='text.directprotection'/></small>)</td>
								</c:if>
								<c:if test="${am.recursotipo==2}">
									<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />"
										href="resourcedashboard?language=${language}&rid=${r.recursoid}">${r.recurso}</a></td>
									<td><fmt:message key='text.resource'/> (<small><fmt:message key='text.indprotection'/></small>)</td>
								</c:if>
							</tr>
						</c:forEach>
						<c:forEach var="n" items="${cons}"> 
							<tr>
								<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />"
									href="consultasdashboard?language=${language}&rid=${n.consultaid}">${n.consulta}</a></td>
								<td><fmt:message key='text.consultation'/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<button type="button" id="addNewProtDash" class="btn btn-blue waves-effect waves-light waves-input-wrapper newbtnright regbtn">
			<fmt:message key='button.registerprotection' />
		</button>
		<button type="button" id="addNewAppealDash" class="btn btn-blue waves-effect waves-light waves-input-wrapper newbtnright regbtn">
			<fmt:message key='button.registerappeal' />
		</button>
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

<div id="appeal-modal" class="modal-demo">
	<button type="button" class="cleanFields" data-reset="#appeal-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
		<span class="glyphicon glyphicon-erase asLink"></span>
	</button>
	<button type="button" class="close closeModal" onclick="$('#appeal-modal').modal('hide').slideUp();">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title"><fmt:message key="button.additem" /></h4>
	<div class="custom-modal-text text-left fixTop inlineblock">
		<div class="panel-body fixTop inlineblock">
			<form method="post" id="formAddAppeal" class="fixTop inlineblock">
				<div class="form-group fixTop inlineblock">
					<div class="col-xs-12 fixTop">
						<div id="errorOnAddApl" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddApl').toggle();">&times;</a>
							<p id="putErrorOnAddApl"></p>
							<input type="hidden" id="client" value="${client[0].client}">
						</div>
					</div>
					<table class="dnone" id="clientList"><tbody><tr class="rowopt">
						<td><input type="radio" data-val="${client[0].client}" name="rowline" id="cidProt${client[0].clientid}" checked></td>
					</tr></tbody></table>
					<jsp:include page="appealsfornew.jsp" flush="true"/>
				</div>
			</form>
			<div class="modal-footer">
				<button type="button" onclick="return addAppeal(this);" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="Custombox.close();" class="btn btn-danger waves-effect waves-light m-l-10"
					onclick="$('#appeal-modal').modal('hide').slideUp();""><fmt:message key="button.cancel" />
				</button>
				
			</div>
		</div>
	</div>
</div>

<div id="protection-modal" class="modal-demo">
	<button type="button" class="cleanFields" data-reset="#protection-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
		<span class="glyphicon glyphicon-erase asLink"></span>
	</button>
	<button type="button" class="close" onclick="$('#protection-modal').modal('hide').slideUp();" style="position:relative;z-index:11">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>

	<h4 class="custom-modal-title"><fmt:message key="button.additem" /> <fmt:message key="text.protderivedjudgment" /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body">
			<form id="formAddProt">
				<div class="form-group">
					<div class="col-xs-12">
						<div id="errorOnAddProt" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddProt').toggle();">&times;</a>
							<p id="puterrorOnAddProt"></p>
						</div>
						<div id="errorOnAddIndProt" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddIndProt').toggle();">&times;</a>
							<p id="puterrorOnAddIndProt"></p>
						</div>
					</div>

					<table class="dnone" id="protClientList"><tbody><tr class="rowopt">
						<td><input type="radio" data-val="${client[0].client}" name="rowline" id="cidProt${client[0].clientid}" checked>
						<input type="text" id="protClient" autocomplete="off" value="${client[0].client}"></td>
					</tr></tbody></table>

					<table class="dnone" id="indProtClientList"><tbody><tr class="rowopt">
						<td>
							<input type="radio" data-val="${client[0].client}" name="rowline" id="cidIndProt${client[0].clientid}" checked>
							<input type="text" id="indProtClient" autocomplete="off" value="${client[0].client}">
						</td>
					</table>

					<div class="row">
						<div class="col-xs-12 col-sm-4">
							<span><fmt:message key="text.protectiontype" /></span>
						</div>
						
						<div class="col-xs-12 col-sm-4">
							<div class="form-group fieldinfo inlineflex w100p">
								<input type="radio" name="prottypedash" id="prottypedir" value="d">
								<label for="prottypedir"><fmt:message key='text.direct' /></label>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="form-group fieldinfo inlineflex w100p">
								<input type="radio" name="prottypedash" id="prottypeind" value="i">
								<label for="prottypeind"><fmt:message key='text.indirect' /></label>
							</div>
						</div>
					</div>
					
					<div id="isProtDir">
						<jsp:include page="/WEB-INF/views/cjf/protectionfornew.jsp" flush="true"/>
						<button type="button" onclick="return addProtection(this);" class="btn btn-default waves-effect waves-light">
							<fmt:message key="button.save" />
						</button>
						<button type="button" onclick="$('#protection-modal').modal('hide').slideUp();" id="addProtectCancel" class="btn btn-danger waves-effect waves-light m-l-10">
							<fmt:message key="button.cancel" />
						</button>
					</div>
					<div id="isProtInd">
						<jsp:include page="/WEB-INF/views/cjf/indprotectionfornew.jsp" flush="true"/>
						<button type="button" onclick="return addIndProtection(this);" class="btn btn-default waves-effect waves-light">
							<fmt:message key="button.save" />
						</button>
						<button type="button" onclick="$('#protection-modal').modal('hide').slideUp();" id="addIndProtectCancel" class="btn btn-danger waves-effect waves-light m-l-10">
							<fmt:message key="button.cancel" />
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

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

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/complementos.js"></script>
<script src="resources/local/general/dash/movements.js"></script>
<script src="resources/local/general/dash/choices.min.js"></script>
<script src="resources/local/commonlaws/juicios.js"></script>
<script src="resources/local/commonlaws/apelaciones.js"></script>
<script src="resources/local/commonlaws/apldash.js"></script>
<script src="resources/local/cjf/protection.js"></script>
<script src="resources/local/cjf/indprotection.js"></script>

<script type="text/javascript">
	jQuery(document).ready(function($){
		$('#prottypedir').on('click',function (){
			$('#indProtClientList [name=rowline]:first').prop('checked',false);
			$('#protClientList [name=rowline]:first').prop('checked',true);
		});
		$('#prottypeind').on('click',function (){
			$('#protClientList [name=rowline]:first').prop('checked',false);
			$('#indProtClientList [name=rowline]:first').prop('checked',true);
		});

		$('input[type=radio][name=prottypedash]').click(function(){
			if(this.value=='d'){
		    	$('#isProtDir').fadeIn();
				$('#isProtInd').fadeOut();
		    }else if(this.value=='i'){
		    	$('#isProtInd').fadeIn();
				$('#isProtDir').fadeOut();
		    }
		});
	});
</script>

<script type="text/javascript">
	$('#addNewProtDash').on('click',function(){
		var stateDef=$('#stateidDefault').val(),cid=$('#clientid').val(),tid=$('#trial').val();
		$('input').parent().removeClass('has-error');
		$('#isProtDir,#isProtInd,#errorOnAddProt,#errorOnAddIndProt,#divtrialOrigin,#divappeal').hide();
		//$('[name="prottypedash"]').prop('checked', false);
		//$('#divtrialOrigin').css('display','none');
		//document.getElementById("isProtInd").checked=true;
		$('#prottypedir,#prottypeind,#origintype1,#origintype2,#isProtInd,[name="prottypedash"]')
			.prop('checked',false);
		listCurrentTrial(tid,'#selecttrialO');
		listCurrentTrial(tid,'#indSelecttrialO');
		getRelClientAppeal(cid,'#appealList');
		getRelClientAppeal(cid,'#indAppealList');
		$("#addNewProtDash").attr('href','#protection-modal');
		$('#protection-modal').modal('show').slideDown();

		getEstados('aplState','ul');
		getEstados('indState', 'ul');
		getTextDDFilterByVal('aplState',stateDef);
		getTextDDFilterByVal('indState', stateDef);
		getCitiesByState('aplCity','ul',stateDef);
		getCitiesByState('indCity', 'ul',stateDef);
		getTextDDFilterByVal('aplCity',$('#cityidDefault').val());
		getTextDDFilterByVal('indCity', $('#cityidDefault').val());

		$('#wrap').css('display','inline-grid');
		//$('#protection-modal').css('display','inline-block');
		try{
			myDropzone = createDropZone("uploadXProt", "formAddProt", '', '');
			myDropzone.on('sending', function(file, xhr, formData){
				//formData.append('idx', $('#usersession').val());
			});
		}catch (e){
			clearTemp();
			$('#areaNewProtUpload').html('');
			$('#areaNewProtUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
				'<div id="uploadXProt" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
			myDropzone = createDropZone("uploadXProt", "formAddProt", '', '');
			myDropzone.on('sending', function(file, xhr, formData){
				//formData.append('idx', $('#usersession').val());
			});
		}
		$("#uploadXProt").addClass("dropzone");
		try{
			myDropzone = createDropZone("uploadXIndProt", "formAddProt", '', '');
			myDropzone.on('sending', function(file, xhr, formData){
				//formData.append('idx', $('#usersession').val());
			});
		}catch (e){
			clearTemp();
			$('#areaNewIndProtUpload').html('');
			$('#areaNewIndProtUpload').html('<span class="textContent">'+i18n('msg_upload_area')+'</span>'+
				'<div id="uploadXIndProt" class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>');
			myDropzone = createDropZone("uploadXIndProt", "formAddProt", '', '');
			myDropzone.on('sending', function(file, xhr, formData){
				//formData.append('idx', $('#usersession').val());
			});
		}
		$("#uploadXIndProt").addClass("dropzone");
	});
</script>

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

		// Filtros Tipo de Cuaderno
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
	});
	
	$("#addNewMove").click(function(){
		setNewMove();
		$("#trialtype").append('<>' + i18n('err_select_matter') + '</>')
		var trialid = getVarURL('id');
		if (trialid != '')
			$('#trial').val(trialid);
		getActType('actType');
	});
*/
</script>

<script>
	function listCurrentTrial(id,listid){
		$.ajax({
			type:'POST',
			url :ctx+"/getTrial",
			data:"trial="+id,
			async:false,
			success:function(data){
				if(data[0]==null)
					$(listid).html(new Option(i18n('msg_no_data_client'),'',!1,!0));
				else
					$(listid).html('<option value="" disabled="disabled">'+i18n('msg_select')+'</option>'
						+'<option value="'+data[0][0].juicioid+'" selected>'+data[0][0].juicio+'</option>');
			},error:function(e){
				console.log(i18n('err_unable_get_client') + '. ' + e);
			}
		});
	};
</script>