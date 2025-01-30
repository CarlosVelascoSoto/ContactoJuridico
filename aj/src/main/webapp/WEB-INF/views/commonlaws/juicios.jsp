<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<title><fmt:message key="text.trials"/> - Contacto jur&iacute;dico</title>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<!-- link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css"> -->
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="resources/assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
<!-- rel="stylesheet" type="text/css" href="resources/assets/css/font-awesome.min.css"-->

<!-- link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/icon?family=Material+Icons"-->

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
<!-- link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">

<link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/dropzone.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css">
 -->
<script src="resources/assets/js/complementos.js"></script>
<style>
	#addFrom-filtersel-modal{z-index:10015}
	#edit-modal{overflow:auto}
    #citycourt{top:40px}
    #actionway, #editactionway{height:auto;min-height:38px}

	.sub-modal{position:absolute;width:100%;height:100vh;max-height:100%;top:0;-webkit-align-items:center;align-items:center;
		-webkit-justify-content:center;justify-content:center;z-index:10012}
	.sub-modal>div{background-color:#fff;-webkit-box-shadow:0 0 15px #444;box-shadow:0 0 15px #444;}

	/*Multifilters (Ini)*/
	.mf-container{display:none}
	.multifilters{display:-moz-inline-box;display:inline-block;margin:0 5px 5px 5px;padding:0 10px 0 0;
		-webkit-border-radius:10px;-moz-border-radius:10px;border-radius:10px;cursor:pointer}
	.multifilters:hover{-webkit-box-shadow:0 0 10px #ccc;box-shadow:0 0 10px #ccc;}
	.mf_clear{display:none;padding:0 5px 0 10px;color:#aaa}
	.mf_clear:hover{font-weight:900;color:red}
	.-mf_brown{border:1px solid #D2691E;background-color:#FAEBD7;color:#D2691E}
	.-mf_brown:hover{border:1px solid brown;color:brown}
	.-mf_green{border:1px solid green;background-color:#F0FFF0;color:green}
	.-mf_green:hover{border:1px solid #096709;color:#096709}
	.-mf_blue{border:1px solid #3F91D4;background-color:#D8ECFC;color:#3F91D4}
	.-mf_blue:hover{border:1px solid #008BFF;color:#008BFF}
	/*Multifilters (fin)*/
</style>

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
				<c:if test='${fn:contains(arrayPermisos, aPermiso )}'>
					<div class="btn-group pull-right m-t-15">
						<a href="#" id="addNewJuicio" data-animation="fadein" data-plugin="custommodal" data-overlaySpeed="200"
							data-overlayColor="#36404a" class="btn btn-default btn-md waves-effect waves-light m-b-30 w-lg"> 
							<fmt:message key="button.new" />
						</a>
					</div>
				</c:if>
				<h4 class="page-title capitalize"><fmt:message key="text.trials" /></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="card-box table-responsive">
						<div class="table-subcontrols">
							<button type="button" id="btnfilter" class="btn btn-filter" title="<fmt:message key="text.filtersbycolumns" />"
								onclick="$('#datatable-buttons thead tr:eq(1)').toggle();"><span class="glyphicon glyphicon-filter"></span> 
							</button>
							<button id="clearfilters" class="btn clearfilters" title="<fmt:message key="text.clearfiters" />"
								onclick="cleanTableFilters(this)"><span class="glyphicon glyphicon-filter"></span><i class="material-icons">close</i>
							</button>
						</div>
						<table id="datatable-buttons" data-order='[[0,"desc"]]' class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><fmt:message key="text.proceedings" /></th>
									<th><fmt:message key="text.client" /></th>
									<th><fmt:message key="text.court" /></th>
									<th><fmt:message key="text.matter" /></th>
									<th><fmt:message key="text.city" /></th>
									<th><fmt:message key="text.status" /></th>
									<th class="tac" style="max-width:60px"><fmt:message key="text.sharetrial" /></th>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'><th><fmt:message key="text.edit" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="j" items="${juicios}">
								<tr>
									<td><a class="asLink c39c trn2ms" title="<fmt:message key="text.viewdetails" />" href="trialsdashboard?language=${language}&rid=${j.juicioid}">${j.juicio}</a></td>
									<td>
										<c:forEach var="cc" items="${ccli}">
											<c:if test="${j.companyclientid == cc.companyclientid}">
												<c:forEach var="cl" items="${clients}">
													<c:if test="${cl.clientid == cc.clientid}">
														${cl.client}
													</c:if>
												</c:forEach>
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:forEach var="z" items="${juzgados}">
											<c:if test="${j.juzgadoid == z.juzgadoid}">
												${z.juzgado}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:forEach var="m" items="${materias}">
											<c:if test="${j.materiaid == m.materiaid}">
												${m.materia}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:forEach var="c" items="${cities}">
											<c:if test="${j.ciudadid == c.ciudadid}">
												${c.ciudad}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:choose>
											<c:when test="${j.status==0}"><fmt:message key="status.closeEnd" /></c:when>
											<c:when test="${j.status==1||empty j.status}"><fmt:message key="status.active" /></c:when>
											<c:when test="${j.status==2}"><fmt:message key="status.suspended" /></c:when>
										</c:choose>
									</td>
									<td class="tac">
										<a href="#" id="${j.juicioid}" data-toggle="modal" data-target="#shdk-modal" 
											class="table-action-btn" title="<fmt:message key='text.sharetrial' /> ${j.juicio}" 
											onclick="getShUser(${j.juicioid});">
											<i class="material-icons" style="font-size:14px">share</i>
										</a>
									</td>
									<c:if test='${fn:contains(arrayPermisos, ePermiso )}'>
									<td class="tac">
										<a href="#" id="${j.juicioid}" data-toggle="modal" data-target="#edit-modal" 
											class="table-action-btn" title="<fmt:message key='text.edit' />"
											onclick="getDetailsToEdit(id);">
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
		<div class="hidden-xs" style="height: 400px;"></div>
	</div>
</div>

<!-- Modal -->
<div id="trial-modal" class="modal-demo">
	<button type="button" class="cleanFields" data-reset="#trial-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
		<span class="glyphicon glyphicon-erase asLink"></span>
	</button>
	<button type="button" class="close" onclick="Custombox.close();" style="position:relative;">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<h4 class="custom-modal-title capitalize"><fmt:message key="button.additem" /> <fmt:message key="text.trial" /></h4>
	<div class="custom-modal-text text-left">
		<div class="panel-body p-t-0">
			<form id="formjuicionew">
				<div class="form-group">
					<div id="ErrorSelectMatter">
						<a href="#" class="close" style="color:#000;display: flex;position: absolute;right: 48px;top: 82px;" aria-label="close"
						onclick="$('#ErrorSelectMatter').hide()">&times;</a>  
						<p id="putErrorSelectMatter"
						 style="display: block;background-color: #FCDCDC;border: 1px solid #F05050;color: #F05252;border-radius: 5px;padding: 10px 7px;">
						 </p>
					 </div>
					<div class="col-xs-12">
						<div style="display:none;" id="errorOnAddTrial" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddTrial').toggle();">&times;</a>
						<p id="putErrorOnAddTrial"></p>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group inlineflex w100p">
								<label for="trialClientList" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="trialClient"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<div class="containTL">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="trialClientList"></table>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12"><h4><fmt:message key='text.trialidentifier' /></h4></div>
						<div class="col-xs-12 col-sm-6">
							<div class="form-group inlineblock w100p">
								<label for="proceedings" class="supLlb"><fmt:message key='text.proceedings' /></label>
								<input type="text" class="form-control c39c" id="proceedings" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6">
							<label for="matter" class="supLlb"><em>* </em><fmt:message key='text.matter' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel c39c"
									placeholder="<fmt:message key='text.select' />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="matter" style="top:40px"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6">
							<label for="accion" class="supLlb"><fmt:message key="text.action" /> (<fmt:message key='text.trialtype' />)</label>
							<div class="form-group inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" id="actionText"
									onfocus="getAccionByMatterId('accion', '#matter li.selected', 'ul', '');"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="accion"></ul>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6" style="margin-bottom:20px">
							<span class="supLlb"><fmt:message key='text.way' /></span>
							<span class="form-control" id="actionway" style="display:inherit;background-color:#f3f3f3"></span>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label for="trialtype" class="supLlb"><fmt:message key='text.trialtype' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel c39c"
									placeholder="<fmt:message key='text.select' />" autocomplete="off"
									onfocus="getTrialTypesList($('#accion li.selected').val(),'trialtype','Add','ul',null);">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="trialtype" style="top:40px"></ul>
							</div>
						</div>
					</div>

					<div class="row"><div class="col-xs-12"><h4><fmt:message key='text.parties' /></h4></div></div>
					<div class="row">
						<div class="col-sm-12" data-addInputforcol="actor">
							<div class="form-group inlineblock w100p m-0">
								<label for="actor" class="supLlb"><fmt:message key='text.actor' /></label>
								<input type="text" class="form-control c39c" id="actor" autocomplete="off">
							</div>
						</div>
						<div class="col-sm-12" data-addInputforcol="def">
							<div class="form-group inlineblock w100p m-0">
								<label for="defendant" class="supLlb"><fmt:message key='text.defendant' /></label>
								<input type="text" class="form-control c39c" id="defendant" autocomplete="off">
							</div>
						</div>
						<div class="col-sm-12" data-addInputforcol="third">
							<div class="form-group w100p inlineflex m-0">
								<label for="third" class="supLlb"><fmt:message key='text.thirdparty' /></label>
								<input type="text" class="form-control c39c" id="third" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12" id="addTrialtypecolumns" style="max-height:300px;padding:10px 0 0 0;overflow:auto"></div>
					</div>
					<div class="row">
						<div class="col-xs-12"><h4><fmt:message key='text.selectcourt' /></h4></div>
						<div class="col-xs-12">
							<label for="court" class="supLlb"><em>* </em><fmt:message key='text.court' /></label>
							<div class="form-group inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c"
									onfocus="getCourtsFilters('court','matter','state','city','distpart','1','ul');"
									placeholder="<fmt:message key="text.select" />"  autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="court"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-4">
							<h5 class="tar" style="margin:0 0 20px 0"><fmt:message key="text.filterbycourt" />:</h5>
						</div>

						<div class="col-xs-8 p-0" style="margin-bottom:20px">
							<div class="row" style="padding:0 10px 0 20px">
								<div class="multifilters -mf_brown trn2ms">
									<span class="mf_clear" id="mf-clear-state" title="<fmt:message key="text.clearthisfiter"/>"
										onclick="clearcourtfilter('state',this)">&times;</span>
									<span id="span-state" style="padding-left:10px" onclick="multifilters('mf-state',this)"><fmt:message key="text.state"/></span>
								</div>
							
								<div class="multifilters -mf_green trn2ms">
									<span class="mf_clear" id="mf-clear-city" title="<fmt:message key="text.clearthisfiter"/>"
										onclick="clearcourtfilter('city',this)">&times;</span>
									<span id="span-city" style="padding-left:10px" onclick="multifilters('mf-city',this)"><fmt:message key="text.city"/></span>
								</div>
								<div class="multifilters -mf_blue trn2ms">
									<span class="mf_clear" id="mf-clear-distpart" title="<fmt:message key="text.clearthisfiter"/>"
										onclick="clearcourtfilter('distpart',this)">&times;</span>
									<span id="span-distpart" style="padding-left:10px" onclick="multifilters('mf-distpart',this)">
										<fmt:message key="text.district"/>/<fmt:message key="text.party"/></span>
								</div>
							</div>
							<input type="hidden" id="current-mf">
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 mf-container" id="mf-country">
							<div class="form-group inlineblock w100p">
								<label for="country" class="supLlb" style="background-color:rgba(250,235,215,0.3);color:#D2691E">
									<fmt:message key="text.filterby" /> <fmt:message key='text.country' /></label>
								<div class="form-group inlineblock w100p">
									<input type="text" class="form-control listfiltersel c39c" id="inputcountry" style="background-color:#FAEBD7"
										onkeyup="$(this).keyup(function(e){applycourtfilter(e,'country','');});"
										placeholder="<fmt:message key="text.select" />" autocomplete="off">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="country"></ul>
								</div>
							</div>
						</div>
						<div class="col-xs-12 mf-container" id="mf-state">
							<div class="form-group inlineblock w100p">
								<label for="state" class="supLlb" style="background-color:rgba(250,235,215,0.3);color:#D2691E">
									<fmt:message key="text.filterby" /> <fmt:message key='text.state' /></label>
								<div class="form-group inlineblock w100p">
									<input type="text" class="form-control listfiltersel c39c" id="inputstate" style="background-color:#FAEBD7"
										onfocus="getEstados('state','ul');" autocomplete="off"
										onkeyup="$(this).keyup(function(e){applycourtfilter(e,'state','');});"
										placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="state"></ul>
								</div>
							</div>
						</div>
						<div class="col-xs-12 mf-container" id="mf-city">
							<label for="city" class="supLlb" style="background-color:rgba(240,255,240,0.3)">
								<fmt:message key="text.filterby" /> <fmt:message key='text.city' /></label>
							<div class="form-group inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" id="inputcity" style="background-color:#F0FFF0"
									onfocus="getCiudades('city', 'ul', 'state');"
									onkeyup="$(this).keyup(function(e){applycourtfilter(e,'city','');});"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="city"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 mf-container" id="mf-distpart">
							<div class="col-xs-6 col-sm-2">
								<div class="form-group inlineflex w100p">
									<label for="district" class="inlineflex inputnextrb">
									<input type="radio" class="form-control c39c" id="district" value="d" name="distpartopt"  title="<fmt:message key='text.district' />"
										onchange="applycourtfilter('Enter','distpart','distpartopt');">
										<fmt:message key='text.district' /></label>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2">
								<div class="form-group inlineflex w100p">
									<label for="party" class="inlineflex inputnextrb">
									<input type="radio" class="form-control c39c" id="party" value="p" name="distpartopt" title="<fmt:message key='text.party' />"
										onchange="applycourtfilter('Enter','distpart','distpartopt');">
										<fmt:message key='text.party' /></label>
								</div>
							</div>
							<div class="col-xs-12 col-sm-4">
								<div class="form-group inlineflex w100p">
									<label for="distpart" class="supLlb"><fmt:message key='text.number' /></label>
									<input type="text" class="form-control c39c" id="distpart" autocomplete="off"
										onkeyup="applycourtfilter('Enter','distpart','distpartopt');">
								</div>
							</div>
						</div>
					</div>

					<div class="col-xs-12">
						<label for="judges" class="supLlb"><fmt:message key='text.judge' /></label>
						<div class="form-group inlineblock w100p">
							<input type="text" class="form-control listfiltersel c39c"
								onfocus="getJudgesByCourtid($('#court li.selected').val(),'judges','ul');"
								placeholder="<fmt:message key="text.select" />" autocomplete="off">
							<i class="material-icons iconlistfilter">arrow_drop_down</i>
							<ul class="ddListImg noimgOnList" id="judges"></ul>
						</div>
					</div>
				</div>
				<div class="row"><div class="col-sm-12"><h4><fmt:message key='text.lawyers' /></h4></div></div>
				<div class="row">
					<div class="col-xs-12">
						<div class="form-group w100p inlineflex">
							<label for="lawyer" class="supLlb"><em>* </em><fmt:message key='text.lawyer' /></label>
							<input type="text" class="form-control c39c" id="lawyer" autocomplete="off">
						</div>
					</div>
					<div class="col-xs-12">
						<label for="lawyerassigned" class="supLlb"><fmt:message key='text.lawyerassigned' /></label>
						<div class="form-group inlineblock w100p">
							<input type="text" class="form-control listfiltersel c39c"
								placeholder="<fmt:message key="text.select" />" autocomplete="off">
							<i class="material-icons iconlistfilter">arrow_drop_down</i>
							<ul class="ddListImg noimgOnList" id="lawyerassigned"></ul>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group w100p inlineflex">
							<label for="lawyercounterpart" class="supLlb"><fmt:message key='text.lawyercounterpart' /></label>
							<input type="text" class="form-control c39c" id="lawyercounterpart" autocomplete="off">
						</div>
					</div>
				</div>

				<c:if test="${(role==1 || role==2)}">
					<div class="row"><div class="col-sm-12">
						<h4 style="color:red"><fmt:message key='text.reasign' />:</h4></div></div>
					<div class="row">
						<div class="col-xs-12 col-sm-12">
							<label for="valideUser" class="supLlb">
								<fmt:message key='text.createdby' /> 
								<fmt:message key='text.user' />
							</label>
							<div class="form-group inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c"
									onclick="getLawyerList('valideUser', 'ul',1);"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="valideUser"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12">
							<label for="existFirm" class="supLlb"><fmt:message key='text.firm' /></label>
							<div class="form-group inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c"
									onfocus="getCompanies('existFirm','ul');"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="existFirm"></ul>
							</div>
						</div>
					</div>
					<script>
						function getCompanies(id,elemtype){
							var elemOp='option';
							if(elemtype=='ul'||elemtype=='ol')
								elemOp='li';
							$('#'+id).empty();
							$.ajax({
								type:"POST",
								url:ctx+"/getCompanies",
								async:false,
								success:function(data){
									var info=data[0];
									if(info.length>0){
										if(elemtype=='select'||elemtype=='')
											$('#'+id).append('<'+elemOp+' value="0" selected disabled>'+i18n('msg_select')+'</'+elemOp+'>');
										for(i=0;i<info.length;i++)
											$('#'+id).append('<'+elemOp+' value="'+info[i].companyid+'" title="'+info[i].address1+'">'+info[i].company+'</'+elemOp+'>');
										$('#'+id).append('<'+elemOp+' value=""></'+elemOp+'>');
									}
								},error:function(e){
									console.log(i18n('err_unable_get_firm')+'. '+e);
								}
							});
						};
						function setFirmTrue(record){
							getCompanies('editValideFirm','ul');
							$.ajax({
								type:"POST",
								url:ctx+"/getCompClientByCCid",
								data:"id="+record.companyclientid,
								async:false,
								success:function(data){
									if(data.length>0){
										var info=data[0].detail[0];
										getTextDDFilterByVal('editValideFirm',info.companyid);
										getLawyerList('usonadd', 'ul',1);
										getTextDDFilterByVal('usonadd', info.userid);
										getLawyerList('editValideUser', 'ul',1);
									}
								},error:function(e){
									console.log(i18n('err_unable_get_firm')+'. '+e);
								}
							});
						};
					</script>
				</c:if>
			
				<div class="row">
					<div id="areaJuicioUpload">
						<span class="textContent"><fmt:message key='label.dropzone' /></span>
						<div id='uploadXdiv' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
					</div>
					<div>
						<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
						onclick="$('#targetDZ').val('#uploadXdiv')" title="<fmt:message key='text.usecam.browser' />" >
							<i class="material-icons">camera_alt</i>
						</a>
					</div>
				</div>
			</form>
			<button type="button" onclick="return addTrial(this);" class="btn btn-default waves-effect waves-light">
				<fmt:message key="button.save" />
			</button>
			<button type="button" onclick="Custombox.close();" id="addTrialCancel" class="btn btn-danger waves-effect waves-light m-l-10">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</div>

<div id="edit-modal" class="modal fade" role="dialog" aria-labelledby="edit-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="cleanFields" data-reset="#edit-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
					<span class="glyphicon glyphicon-erase asLink"></span>
				</button>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize"><fmt:message key="trial.update" /> <span id="edProceedings"></span></h4>
			</div>
			<div class="modal-body p-0">
				<form id="formjuicioedit">
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnEditTrial" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEditTrial').toggle();">&times;</a>
								<p id="putErrorOnEditTrial"></p>
								<input type="hidden" id="edid">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group inlineflex w100p">
								<label for="editTrialClientList" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
								<input type="text" class="form-control c39c" id="editTrialClient"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<div class="containTL">
									<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
										<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
									</button>
									<table class="table tablelist" id="editTrialClientList"></table>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12"><h4><fmt:message key='text.trialidentifier' /></h4></div>
						<div class="col-xs-12 col-sm-6">
							<div class="form-group inlineblock w100p">
								<label for="editproceedings" class="supLlb"><fmt:message key='text.proceedings' /></label>
								<input type="text" class="form-control c39c" id="editproceedings">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6">
							<label for="editmatter" class="supLlb"><em>* </em><fmt:message key='text.matter' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel c39c"
									placeholder="<fmt:message key='text.select' />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editmatter" style="top:40px"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6">
							<label for="editaccion" class="supLlb"><fmt:message key="text.action" /> (<fmt:message key='text.trialtype' />)</label>
							<div class="form-group inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c" id="editActionText"
									onfocus="getAccionByMatterId('editaccion', '#editmatter li.selected', 'ul', '');"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editaccion"></ul>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6" style="margin-bottom:20px">
							<span class="supLlb"><fmt:message key='text.way' /></span>
							<span class="form-control" id="editactionway" style="display:inherit;background-color:#f3f3f3"></span>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-8">
							<label for="edittrialtype" class="supLlb"><fmt:message key='text.trialtype' /></label>
							<div class="form-group inlineflex w100p">
								<input type="text" class="form-control listfiltersel c39c"
									placeholder="<fmt:message key='text.select' />" autocomplete="off"
									onfocus="getTrialTypesList($('#editaccion li.selected').val(), 'edittrialtype', 'Edit', 'ul', null);">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="edittrialtype" style="top:40px"></ul>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="form-group inlineblock w100p">
								<label for="editstatus" class="supLlb"><fmt:message key='text.status' /></label>
								<div class="select-wrapper w100p">
									<select class="form-control ddsencillo c39c" id="editstatus">
										<option value="0"><fmt:message key="status.closeEnd" /></option>
										<option value="1"><fmt:message key="status.active" /></option>
										<option value="2"><fmt:message key="status.suspended" /></option>
									</select>
								</div>
							</div>
						</div>
					</div>

					<div class="row"><div class="col-xs-12"><h4><fmt:message key='text.parties' /></h4></div></div>
					<div class="row">
						<div class="col-sm-12" data-editInputforcol="actor">
							<div class="form-group inlineblock w100p m-0">
								<label for="editactor" class="supLlb"><fmt:message key='text.actor' /></label>
								<input type="text" class="form-control c39c" id="editactor" autocomplete="off">
							</div>
						</div>
						<div class="col-sm-12" data-editInputforcol="def">
							<div class="form-group inlineblock w100p m-0">
								<label for="editdefendant" class="supLlb"><fmt:message key='text.defendant' /></label>
								<input type="text" class="form-control c39c" id="editdefendant" autocomplete="off">
							</div>
						</div>
						<div class="col-sm-12" data-editInputforcol="third">
							<div class="form-group w100p inlineflex m-0">
								<label for="editthird" class="supLlb"><fmt:message key='text.thirdparty' /></label>
								<input type="text" class="form-control c39c" id="editthird" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12" id="editTrialtypecolumns" style="max-height:300px;padding:10px 0 0 0;overflow:auto"></div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<h4><fmt:message key='text.selectcourt' /></h4>
						</div>
						<div class="col-xs-12">
							<label for="editcourt" class="supLlb"><em>* </em><fmt:message key='text.court' /></label>
							<div class="form-group inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c"
									onfocus="getCourtsFilters('editcourt','editmatter','editstate','editcity','editdistpart','1','ul');"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editcourt"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-4">
							<h5 class="tar" style="margin:0 0 20px 0"><fmt:message key="text.filterbycourt" />:</h5>
						</div>
						<div class="col-xs-8 p-0" style="margin-bottom:20px">
							<div class="row" style="padding:0 10px 0 20px">
								<div class="col-xs-12 mf-container" id="mf-editcountry">
									<div class="form-group inlineblock w100p">
										<label for="editcountry" class="supLlb" style="background-color:rgba(250,235,215,0.3);color:#D2691E">
											<fmt:message key="text.filterby" /> <fmt:message key='text.country' /></label>
										<div class="form-group inlineblock w100p">
											<input type="text" class="form-control listfiltersel c39c" id="inputeditcountry"
												style="background-color:#FAEBD7" autocomplete="off"
												onkeyup="$(this).keyup(function(e){applycourtfilter(e,'editcountry','');});"
												placeholder="<fmt:message key="text.select" />">
											<i class="material-icons iconlistfilter">arrow_drop_down</i>
											<ul class="ddListImg noimgOnList" id="editcountry"></ul>
										</div>
									</div>
								</div>
								<div class="row" style="padding:0 10px 0 20px">
									<div class="multifilters -mf_brown trn2ms">
										<span class="mf_clear" id="mf-clear-editstate" title="<fmt:message key="text.clearthisfiter"/>"
											onclick="clearcourtfilter('editstate',this)">&times;</span>
										<span id="span-editstate" style="padding-left:10px" onclick="multifilters('mf-editstate',this)"><fmt:message key="text.state"/></span>
									</div>
								
									<div class="multifilters -mf_green trn2ms">
										<span class="mf_clear" id="mf-clear-editcity" title="<fmt:message key="text.clearthisfiter"/>"
											onclick="clearcourtfilter('editcity',this)">&times;</span>
										<span id="span-editcity" style="padding-left:10px" onclick="multifilters('mf-editcity',this)"><fmt:message key="text.city"/></span>
									</div>
									<div class="multifilters -mf_blue trn2ms">
										<span class="mf_clear" id="mf-clear-editdistpart" title="<fmt:message key="text.clearthisfiter"/>"
											onclick="clearcourtfilter('editdistpart',this)">&times;</span>
										<span id="span-editdistpart" style="padding-left:10px" onclick="multifilters('mf-editdistpart',this)">
											<fmt:message key="text.district"/>/<fmt:message key="text.party"/></span>
									</div>
								</div>
								<input type="hidden" id="current-mf">
							</div>
						</div>
						<div class="col-xs-12 mf-container" id="mf-editstate">
							<div class="form-group inlineblock w100p">
								<label for="editstate" class="supLlb" style="background-color:rgba(250,235,215,0.3);color:#D2691E">
									<fmt:message key="text.filterby" /> <fmt:message key='text.state' /></label>
								<div class="form-group inlineblock w100p">
									<input type="text" class="form-control listfiltersel c39c"
										id="inputeditstate" style="background-color:#FAEBD7"
										onfocus="getEstados('editstate','ul');" autocomplete="off"
										onkeyup="$(this).keyup(function(e){applycourtfilter(e,'editstate','');});"
										placeholder="<fmt:message key="text.select" />">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="editstate"></ul>
								</div>
							</div>
						</div>
						<div class="col-xs-12 mf-container" id="mf-editcity">
							<label for="editcity" class="supLlb" style="background-color:rgba(240,255,240,0.3)">
								<fmt:message key="text.filterby" /> <fmt:message key='text.city' /></label>
							<div class="form-group inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c"
									id="inputeditcity" style="background-color:#F0FFF0"
									onfocus="getCiudades('editcity', 'ul', 'state');" autocomplete="off"
									onkeyup="$(this).keyup(function(e){applycourtfilter(e,'editcity','');});"
									placeholder="<fmt:message key="text.select" />">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editcity"></ul>
							</div>
						</div>
						<div class="col-xs-12 mf-container" id="mf-editdistpart">
							<div class="col-xs-6 col-sm-2">
								<div class="form-group inlineflex w100p">
									<label for="editdistrict" class="inlineflex inputnextrb">
									<input type="radio" class="form-control c39c" id="editdistrict" value="d" name="editdistpartopt"  title="<fmt:message key='text.district' />"
										onchange="applycourtfilter('Enter','editdistpart','editdistpartopt');">
										<fmt:message key='text.district' /></label>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2">
								<div class="form-group inlineflex w100p">
									<label for="editparty" class="inlineflex inputnextrb">
									<input type="radio" class="form-control c39c" id="editparty" value="p" name="editdistpartopt" title="<fmt:message key='text.party' />"
										onchange="applycourtfilter('Enter','editdistpart','editdistpartopt');">
										<fmt:message key='text.party' /></label>
								</div>
							</div>
							<div class="col-xs-12 col-sm-4">
								<div class="form-group inlineflex w100p">
									<label for="editdistpart" class="supLlb"><fmt:message key='text.number' /></label>
									<input type="text" class="form-control c39c" id="editdistpart" autocomplete="off"
										onkeyup="applycourtfilter('Enter','editdistpart','editdistpartopt');">
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<label for="editjudges" class="supLlb"><fmt:message key='text.judge' /></label>
							<div class="form-group inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c"
									onfocus="getJudgesByCourtid($('#editcourt li.selected').val(),'editjudges','ul');"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editjudges"></ul>
							</div>
						</div>
					</div>

					<div class="row"><div class="col-sm-12"><h4><fmt:message key='text.lawyers' /></h4></div></div>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group w100p inlineflex">
								<label for="editlawyer" class="supLlb"><em>* </em><fmt:message key='text.lawyer' /></label>
								<input type="text" class="form-control c39c" id="editlawyer" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12">
							<label for="editlawyerassigned" class="supLlb"><fmt:message key='text.lawyerassigned' /></label>
							<div class="form-group inlineblock w100p">
								<input type="text" class="form-control listfiltersel c39c"
									placeholder="<fmt:message key="text.select" />" autocomplete="off">
								<i class="material-icons iconlistfilter">arrow_drop_down</i>
								<ul class="ddListImg noimgOnList" id="editlawyerassigned"></ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group w100p inlineflex">
								<label for="editlawyercounterpart" class="supLlb"><fmt:message key='text.lawyercounterpart' /></label>
								<input type="text" class="form-control c39c"
									id="editlawyercounterpart" autocomplete="off">
							</div>
						</div>
					</div>

					<c:if test="${(rl==1 || rl==2)}">
						<div class="row"><div class="col-sm-12">
							<h4 style="color:red"><fmt:message key='text.reasign' />:</h4></div></div>
						<div class="row">
							<div class="col-xs-12 col-sm-12">
								<label for="editValideUser" class="supLlb">
									<fmt:message key='text.createdby' /> 
									<fmt:message key='text.user' />
								</label>
								<div class="form-group inlineblock w100p">
									<input type="text" class="form-control listfiltersel c39c"
										placeholder="<fmt:message key="text.select" />" autocomplete="off">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="editValideUser"></ul>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-12">
								<label for="editValideFirm" class="supLlb"><fmt:message key='text.firm' /></label>
								<div class="form-group inlineblock w100p">
									<input type="text" class="form-control listfiltersel c39c"
										onfocus="getCompanies('editexistFirm','ul');" autocomplete="off"
										placeholder="<fmt:message key="text.select" />" >
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="editValideFirm"></ul>
								</div>
							</div>
						</div>
					</c:if>

					<div class="row">
						<div id="areaTrialUpload">
							<span class="textContent"><fmt:message key='label.dropzone' /></span>
							<div id='uploadXdivEdit' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
						</div>
						<div>
							<a data-toggle="modal" data-target="#camera-modal" class="btn-opencam flex trn2ms asLink noselect"
							onclick="$('#targetDZ').val('#uploadXdivEdit')" title="<fmt:message key='text.usecam.browser' />" >
								<i class="material-icons">camera_alt</i>
							</a>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" onclick="return updateData();" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="addFrom-filtersel-modal" class="sub-modal" style="display:none">
	<div class="modal-dialog">
		<button type="button" class="cleanFields" data-reset="#addFrom-filtersel-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
			<span class="glyphicon glyphicon-erase asLink"></span>
		</button>
		<button type="button" class="close" onclick="$('#addFrom-filtersel-modal').hide();" style="margin:10px;color:#fff">
			<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
		</button>
		<h4 class="custom-modal-title capitalize"><fmt:message key="text.add" /> <span class="titleFromFSel"></span></h4>
		<div class="custom-modal-text text-left">
			<div class="panel-body">
				<form>
					<div class="form-group">
						<div class="col-xs-12">
							<div style="display:none;" id="errorOnAddFromFSel" class="alert alert-danger fade in">
								<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddFromFSel').toggle();">&times;</a>
								<p id="putErrorOnAddFromFSel"></p>
								<input type="hidden" id="selIdFSel">
								<input type="hidden" id="selTypeFSel">
							</div>
						</div>
						<div class="row dnone" id="toactionway">
							<div class="col-xs-12">
								<label for="fornewactionway" class="supLlb"><fmt:message key='text.way' /></label>
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control listfiltersel c39c"
										onfocus="getWayList('fornewactionway','', 'ul');"
										placeholder="<fmt:message key='text.select' />" autocomplete="off">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="fornewactionway"></ul>
								</div>
								<input type="hidden" id="matter4newaction">
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group inlineflex w100p">
									<label for="descriptionFSel" class="supLlb"><span class="titleFromFSel capitalize"></span></label>
									<input type="text" class="form-control c39c" id="descriptionFSel" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row dnone" id="tocitycourt">
							<div class="col-xs-12">
								<label for="statecourt" class="supLlb"><em>* </em><fmt:message key='text.state' /></label>
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control listfiltersel c39c"
										placeholder="<fmt:message key='text.select' />" autocomplete="off">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="statecourt"></ul>
								</div>
							</div>
							<div class="col-xs-12">
								<label for="citycourt" class="supLlb"><fmt:message key='text.city' /></label>
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control listfiltersel c39c"
										placeholder="<fmt:message key="text.select" />" autocomplete="off">
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="citycourt">
										<li value="" title="Seleccione primero un estado">Seleccione primero un estado</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</form>
				<button type="button" onclick="return addFromFilterSel('ed');" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="validDDFilter();$('#addFrom-filtersel-modal').modal('hide');" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>

<div class="row" id="shdk">
	<div id="shdk-modal" class="sub-modal" style="display:none">
		<div class="modal-dialog">
			<button type="button" class="cleanFields" data-reset="#shdk-modal" title="<fmt:message key='text.cleandata' />" class="p-0">
				<span class="glyphicon glyphicon-erase asLink"></span>
			</button>
			<button type="button" class="close" onclick="$('#shdk-modal').modal('hide');" style="margin:10px;color:#fff">
				<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
			</button>
			<h4 class="custom-modal-title capitalize"><fmt:message key="text.assignlawyer" /></h4>
			<div class="custom-modal-text text-left">
				<div class="panel-body">
					<form>
						<div class="form-group">
							<jsp:include page="shareddocket.jsp" flush="true"/>
						</div>
					</form>
					<button type="button" onclick="return updateSharedTrial();" class="btn btn-default waves-effect waves-light" id="btnupdateshare">
						<fmt:message key="button.save" />
					</button>
					<button type="button" onclick="validDDFilter();$('#shdk-modal').modal('hide');" class="btn btn-danger waves-effect waves-light m-l-10">
						<fmt:message key="button.cancel" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal" id="camera-modal" role="dialog" aria-labelledby="camera-modal" aria-hidden="true">
	<button type="button" class="close _mod" onclick="$('#camera-modal').modal('hide');">
		<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
	</button>
	<jsp:include page="/WEB-INF/views/general/cam/cam.jsp" flush="true"/>
</div>

<a class="btnScrollUp inlineblock blackCircle trn3ms"><i class="material-icons">&#xe316;</i></a>

<!-- script src="resources/assets/js/jquery.min.js"></script-->
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
<!-- >script src="resources/assets/js/jquery.slimscroll.js"></script-->
<!-- Modal-Effect -->
<script src="resources/assets/plugins/custombox/js/custombox.min.js"></script>
<script src="resources/assets/plugins/custombox/js/legacy.min.js"></script>

<script src="resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="resources/assets/js/i18n_AJ.js"></script>
<!-- script src="resources/assets/js/globalfunctions.js"></script-->
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>
<script src="resources/assets/js/customDropZone.js"></script>
<!--script src="resources/assets/js/unorm.js"></script-->
<script src="resources/local/commonlaws/juicios.js"></script>

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
	$(".modal-demo").on('hide.bs.modal', function() {
		clearTemp();
	});
</script>

<script type="text/javascript">
// Script para agregar
</script>

<script type="text/javascript">
// Script para cambios
</script>

<script type="text/javascript">
// Script para eliminar
</script>

<script type="text/javascript">
// Script para detalles
</script>