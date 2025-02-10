<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src='resources/assets/js/complementos.js'></script>
<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/mainhome.js"></script>

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<p class="page-title-alt fs16" style="margin: 0">
						${language=='en'?'':'&iexcl;'}
						<fmt:message key="home.dashboard.welcome" />
						${first_name} ${last_name}!
					</p>
				</div>
			</div>
			<hr style="border-top: 1px solid #C6CDDA">
			<div class="row">
				<c:if test="${role==2}">
					<div class="col-sm-12 flex" style="width: 253px"
						onclick="location.href='/aj/companies?language='+getLanguageURL();">
						<div
							class="widget-panel widget-style-2 waves-effect bg-white optCard">
							<div class="row center-box"
								style="height: 100%; min-height: 78px; margin: auto">
								<div class="col-xs-6" style="margin: auto 5px auto 10px">
									<span class="m-0 font-600 fs15 capitalize"
										style="color: #343a40"><fmt:message key='text.firms' /></span>
									<p class="fs12 mfortotal">${companies.size()}</p>
								</div>
								<div class="col-xs-6" style="margin: auto 10px auto 5px">
									<img src="resources/assets/images/user-home.png"
										class="flex dashboxclient bg-blueicon asLink"
										style="float: right"
										title="<fmt:message key='text.customerdata'/>"
										alt="<fmt:message key='text.customerdata'/>" />
								</div>
							</div>
						</div>
					</div>
				</c:if>

				<div class="col-sm-12 flex" style="width: 253px">
					<div
						class="widget-panel widget-style-2 waves-effect bg-white optCard"
						data-toggle="modal" data-target="#filter-commonlaw-modal"
						id="dllimenu-trials">
						<div class="row center-box"
							style="height: 100%; min-height: 78px; margin: auto">
							<div class="col-xs-12" style="margin: auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize"
									style="color: #343a40"><fmt:message
										key="text.commonjurisdiction" /></span>
								<c:set var="trcount" value="${fn:split(trials, ',')}" />
								<p class="fs12 mfortotal">${fn:length(trcount)}</p>
							</div>
							<div class="col-xs-6" style="margin: auto 10px auto 5px">
								<div class="bg-green1icon">
									<img src="resources/assets/images/trials.png"
										class="flex dashboxclient asLink" style="float: right"
										title="<fmt:message key="text.commonjurisdiction" />"
										alt="<fmt:message key="text.commonjurisdiction" />" />
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-12 flex" style="width: 253px">
					<div
						class="widget-panel widget-style-2 waves-effect bg-white optCard"
						data-toggle="modal" data-target="#filter-feddoc-modal"
						id="dllimenu-protections">
						<div class="row center-box"
							style="height: 100%; min-height: 78px; margin: auto">
							<div class="col-xs-12" style="margin: auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize"
									style="color: #343a40"><fmt:message key="text.cjf" /></span>
								<p class="fs12 mcjftotal"></p>
							</div>
							<div class="col-xs-6" style="margin: auto 10px auto 5px">
								<div class="bg-purpleicon">
									<img
										src="resources/assets/images/consejo-de-la-judicatura-federal-2.png"
										class="flex dashboxclient asLink" style="float: right"
										title="<fmt:message key="text.cjf" />" alt="CJF" />
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-12 flex" style="width: 253px">
					<div
						class="widget-panel widget-style-2 waves-effect bg-white optCard"
						data-home="calendar">
						<div class="row center-box"
							style="height: 100%; min-height: 78px; margin: auto">
							<div class="col-xs-12" style="margin: auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize"
									style="color: #343a40"><fmt:message key='text.schedule' /></span>
								<p class="fs12 mfortotal">${schedules.size()==null||schedules.size()<=0?0:schedules.size()}</p>
							</div>
							<div class="col-xs-6" style="margin: auto 10px auto 5px">
								<img src="resources/assets/images/cal-home.png"
									class="flex dashboxclient bg-darkblueicon asLink"
									style="float: right"
									title="<fmt:message key='calendar.title'/>" alt="CAL" />
							</div>
						</div>
					</div>
				</div>

				<c:if test="${role==2}">
					<div class="col-sm-12 flex" style="width: 253px">
						<div
							class="widget-panel widget-style-2 waves-effect bg-white optCard"
							data-home="importCatalogs">
							<div class="row center-box"
								style="height: 100%; min-height: 78px; margin: auto">
								<div class="col-xs-6" style="margin: auto 5px auto 10px">
									<span class="m-0 font-600 fs15 capitalize"
										style="color: #343a40"><fmt:message key='text.catalogs' /></span>
									<p class="fs12 mfortotal"></p>
								</div>
								<div class="col-xs-6" style="margin: auto 10px auto 5px">
									<img src="resources/assets/images/logo_sm.png"
										class="flex dashboxclient bg-blueicon asLink"
										style="float: right"
										title="<fmt:message key='text.importcatalogs'/>"
										alt="<fmt:message key='text.importcatalogs'/>" />
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</div>
			<jsp:include page="homenotifications.jsp" flush="true" />
		</div>
	</div>
</div>

<div id="filter-commonlaw-modal" class="modal fade" tabindex="-1"
	role="dialog" aria-labelledby="filter-commonlaw-modal"
	aria-hidden="true" style="display: none">
	<div class="modal-dialog">
		<div class="row m-0" style="background-color: #4D6EFF">
			<div class="col-xs-12 col-sm-4 filtertitle">
				<h4 class="custom-modal-title">
					<fmt:message key="text.searchtrialsby" />
				</h4>
			</div>
			<div>
				<button type="button" class="closetrailbox flex"
					data-dismiss="modal" aria-hidden="true">
					<i class="material-icons">&#xe5cd;</i>
				</button>
			</div>
		</div>
		<div class="modal-content p-0 mc-findtrial">
			<form id="home-ddul-cl">
				<div class="modal-body p-0">
					<div class="row">
						<div class="col-xs-12 col-sm-4 filtersbox">
							<!--div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-blue">
										<img src="resources/assets/images/cliente-juicios.png" class="flex dashboxclient asLink" style="float:right" 
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.client' />" alt="<fmt:message key='text.client' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.client" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddl-cl-client');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-cl-client');" onkeyup="filterli(this, 'ddl-cl-client');">
								</div>
								<div class="ddlimenu"><ul id="ddl-cl-client" data-filter="commonlaw"></ul></div>
							</div-->

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin: auto 9px auto auto">
									<div class="inlineflex frameicon fr-yellow">
										<img src="resources/assets/images/materias.png"
											class="flex dashboxclient asLink" style="float: right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.matter' />"
											alt="<fmt:message key='text.matter' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle"
										style="min-height: auto !important"><fmt:message
											key="text.matter" /></span> <i class="fa fa-search"></i> <i
										class="material-icons arr_rg"
										onclick="toggleArrow(this,'#ddl-cl-matter');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-cl-matter');"
										onkeyup="filterli(this, 'ddl-cl-matter');">
								</div>
								<div class="ddlimenu">
									<ul id="ddl-cl-matter" data-filter="commonlaw"></ul>
								</div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin: auto 9px auto auto">
									<div class="inlineflex frameicon fr-orange">
										<img src="resources/assets/images/ciudad-jurisdiccional.svg"
											class="flex dashboxclient asLink" style="float: right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.city' />"
											alt="<fmt:message key='text.city' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle"
										style="min-height: auto !important"><fmt:message
											key="text.city" /></span> <i class="fa fa-search"></i> <i
										class="material-icons arr_rg"
										onclick="toggleArrow(this,'#ddl-cl-city');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-cl-city');"
										onkeyup="filterli(this, 'ddl-cl-city');">
								</div>
								<div class="ddlimenu">
									<ul id="ddl-cl-city" data-filter="commonlaw"></ul>
								</div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin: auto 9px auto auto">
									<div class="inlineflex frameicon fr-carmin">
										<img src="resources/assets/images/courthouse.png"
											class="flex dashboxclient asLink"
											onclick="getCourts('ddl-cl-court','ddul','cl_');toggleArrow(thisddl-cl-courturt');"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.court' />"
											alt="<fmt:message key='text.court' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle"
										style="min-height: auto !important"><fmt:message
											key="text.court" /></span> <i class="fa fa-search"></i> <i
										class="material-icons arr_rg"
										onclick="toggleArrow(this,'#ddl-cl-court');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="getCourts('ddl-cl-court','ddul','cl_');toggleArrow(this,'#ddl-cl-court');"
										onkeyup="filterli(this, 'ddl-cl-court');">
								</div>
								<div class="ddlimenu">
									<input type="hidden" id="forcourt" value="">
									<ul id="ddl-cl-court" data-filter="commonlaw"></ul>
								</div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin: auto 9px auto auto">
									<div class="inlineflex frameicon fr-green">
										<img src="resources/assets/images/expedientes.png"
											class="flex dashboxclient asLink" style="float: right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.proceedings' />"
											alt="<fmt:message key='text.proceedings' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle"
										style="min-height: auto !important"><fmt:message
											key="text.proceedings" /></span> <i class="fa fa-search"></i> <i
										class="material-icons arr_rg"
										onclick="getProceedings('ddl-cl-proceedings','ddul','cl_');toggleArrow(this,'#ddl-cl-proceedings');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="getProceedings('ddl-cl-proceedings','ddul','cl_');toggleArrow(this,'#ddl-cl-proceedings');"
										onkeyup="filterli(this, 'ddl-cl-proceedings');">
								</div>
								<div class="ddlimenu">
									<ul id="ddl-cl-proceedings" data-filter="commonlaw"></ul>
								</div>
							</div>
						</div>

						<div class="col-xs-12 col-sm-8"
							style="padding: 0 5px 0 25px !important">
							<div class="col-xs-12 bg-white trailbox p-0"
								style="overflow: auto">
								<table class="table tablelist display responsive homeTableList"
									id="commonLawList">
									<thead>
										<tr>
											<th><fmt:message key="text.proceedings" /></th>
											<th><fmt:message key="text.court" /></th>
											<th><fmt:message key="text.matter" /></th>
											<th><fmt:message key="text.city" /></th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<div id="filter-feddoc-modal" class="modal fade" tabindex="-1"
	role="dialog" aria-labelledby="filter-feddoc-modal" aria-hidden="true"
	style="display: none">
	<div class="modal-dialog">
		<div class="row m-0" style="background-color: #4D6EFF">
			<div class="col-xs-12 col-sm-4 filtertitle">
				<h4 class="custom-modal-title">
					<fmt:message key="text.searchtrialsby" />
				</h4>
			</div>
			<div>
				<button type="button" class="closefeddocbox flex"
					data-dismiss="modal" aria-hidden="true">
					<i class="material-icons">&#xe5cd;</i>
				</button>
			</div>
		</div>
		<div class="modal-content p-0 mc-findtrial">
			<form id="home-ddul-fd">
				<div class="modal-body p-0">
					<div class="row">
						<div class="col-xs-12 col-sm-4 filtersbox">
							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin: auto 9px auto auto">
									<div class="inlineflex frameicon fr-green">
										<img src="resources/assets/images/juicios-amparo.png"
											class="flex dashboxclient asLink" style="float: right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.federaltrialtype' />"
											alt="<fmt:message key='text.federaltrialtype' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle"
										style="min-height: auto !important"><fmt:message
											key="text.federaltrialtype" /></span> <i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddl-feddoc"
										onclick="toggleArrow(this,'#ddl-fd-typetrial');$('#dllititletypetrial').html(i18n('msg_document_number'));">
										keyboard_arrow_down</i> <input type="search"
										class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-fd-typetrial');"
										onkeyup="filterli(this, 'ddl-fd-typetrial');">
								</div>
								<div class="ddlimenu">
									<ul id="ddl-fd-typetrial" data-filter="fedtrial"></ul>
								</div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin: auto 9px auto auto">
									<div class="inlineflex frameicon fr-green">
										<img src="resources/assets/images/juicios-amparo.png"
											class="flex dashboxclient asLink" style="float: right"
											title="<fmt:message key='text.search' /> <fmt:message key='text.document' />"
											alt="<fmt:message key='text.document' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle"
										style="min-height: auto !important" id="dllititletypetrial"><fmt:message
											key="text.document" /></span> <i class="fa fa-search"></i> <i
										class="material-icons arr_rg" data-close="ddl-feddoc"
										onclick="toggleArrow(this,'#ddl-ft-NumDoc');getNumberDoc('ddl-ft-NumDoc','ul','ft_');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-ft-NumDoc');getNumberDoc('ddl-ft-NumDoc','ul','ft_');"
										onkeyup="filterli(this, 'ddl-ft-NumDoc');">
								</div>
								<div class="ddlimenu">
									<ul id="ddl-ft-NumDoc" data-filter="fedtrial"></ul>
								</div>
							</div>
						</div>

						<div class="col-xs-12 col-sm-8"
							style="padding: 0 5px 0 5px !important">
							<div class="col-xs-12 bg-white trailbox p-0"
								style="overflow: auto">
								<table class="table tablelist display responsive homeTableList"
									id="federalDocList">
									<thead>
										<tr>
											<th><fmt:message key="text.federaltrialtype" /></th>
											<th><fmt:message key="text.documentnumber" /></th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<jsp:include page="/WEB-INF/views/client/schedule.jsp" flush="true" />

<input type="hidden" id="ctid" value="${ctid}">

<script>
	$.noConflict();
	var table,dbntfy;
</script>