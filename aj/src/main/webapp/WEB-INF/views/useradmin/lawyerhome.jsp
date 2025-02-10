<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="resources/assets/js/jquery.min.js"></script>
. . .
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

<script src="resources/assets/js/home.js"></script>

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<p class="page-title-alt fs16" style="margin:0">
						${language=='en'?'':'&iexcl;'}<fmt:message key="home.dashboard.welcome" /> ${first_name} ${last_name}!
					</p>
				</div>
			</div>
			<hr style="border-top:1px solid #C6CDDA">
			<div class="row">
				<c:if test="${role==2}">
				<div class="col-sm-12 flex" style="width:253px" onclick="location.href='/aj/companies?language='+getLanguageURL();">
					<div class="widget-panel widget-style-2 waves-effect bg-white optCard">
						<div class="row center-box" style="height:100%;min-height:78px;margin:auto">
							<div class="col-xs-6" style="margin:auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize" style="color:#343a40"><fmt:message key='text.firms' /></span>
								<p class="fs12 mfortotal">${companies.size()}</p>
							</div>
							<div class="col-xs-6" style="margin:auto 10px auto 5px">
								<img src="resources/assets/images/user-home.png" class="flex dashboxclient bg-blueicon asLink" style="float:right"
									title="<fmt:message key='text.customerdata'/>" alt="<fmt:message key='text.customerdata'/>" />
							</div>
						</div>
					</div>
				</div>
				</c:if>

				<c:if test="${role!=2}">
				<div class="col-sm-12 flex" style="width:253px">
					<div class="widget-panel widget-style-2 waves-effect bg-white optCard" data-toggle="modal" data-target="#findclient-modal">
						<div class="row center-box" style="height:100%;min-height:78px;margin:auto">
							<div class="col-xs-12" style="margin:auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize" style="color:#343a40"><fmt:message key='text.clients' /></span>
								<p class="fs12 mfortotal">${client.size()}</p>
							</div>
							<div class="col-xs-6" style="margin:auto 10px auto 5px">
								<img src="resources/assets/images/user-home.png" class="flex dashboxclient bg-blueicon asLink" style="float:right"
									title="<fmt:message key='text.customerdata'/>" alt="<fmt:message key='text.customerdata'/>" />
							</div>
						</div>
					</div>
				</div>
				</c:if>

				<div class="col-sm-12 flex" style="width:253px">
					<div class="widget-panel widget-style-2 waves-effect bg-white optCard" data-toggle="modal"
						data-target="#filter-commonlaw-modal" id="dllimenu-trials">
						<div class="row center-box" style="height:100%;min-height:78px;margin:auto">
							<div class="col-xs-12" style="margin:auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize" style="color:#343a40"><fmt:message key="text.commonjurisdiction" /></span>
								<c:set var="trcount" value="${fn:split(trials, ',')}" />
								<p class="fs12 mfortotal">${fn:length(trcount)}</p>
							</div>
							<div class="col-xs-6" style="margin:auto 10px auto 5px">
								<div class="bg-green1icon">
									<img src="resources/assets/images/trials.png" class="flex dashboxclient asLink" style="float:right"
										title="<fmt:message key="text.commonjurisdiction" />" alt="<fmt:message key="text.commonjurisdiction" />" />
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-12 flex" style="width:253px">
					<div class="widget-panel widget-style-2 waves-effect bg-white optCard" data-toggle="modal"
						data-target="#filter-feddoc-modal" id="dllimenu-protections">
						<div class="row center-box" style="height:100%;min-height:78px;margin:auto">
							<div class="col-xs-12" style="margin:auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize" style="color:#343a40"><fmt:message key="text.cjf" /></span>
								<p class="fs12 mcjftotal"></p>
							</div>
							<div class="col-xs-6" style="margin:auto 10px auto 5px">
								<div class="bg-purpleicon">
									<img src="resources/assets/images/consejo-de-la-judicatura-federal-2.png" class="flex dashboxclient asLink" style="float:right"
										title="<fmt:message key="text.cjf" />" alt="CJF" />
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-12 flex" style="width:253px">
					<div class="widget-panel widget-style-2 waves-effect bg-white optCard" data-home="calendar">
						<div class="row center-box" style="height:100%;min-height:78px;margin:auto">
							<div class="col-xs-12" style="margin:auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize" style="color:#343a40"><fmt:message key='text.schedule' /></span>
								<p class="fs12 mfortotal">${schedules.size()==null||schedules.size()<=0?0:schedules.size()}</p>
							</div>
							<div class="col-xs-6" style="margin:auto 10px auto 5px">
								<img src="resources/assets/images/cal-home.png" class="flex dashboxclient bg-darkblueicon asLink"
									style="float:right" title="<fmt:message key='calendar.title'/>" alt="CAL" />
							</div>
						</div>
					</div>
				</div>

				<c:if test="${role==2}">
					<div class="col-sm-12 flex" style="width:253px">
						<div class="widget-panel widget-style-2 waves-effect bg-white optCard" data-home="importCatalogs">
							<div class="row center-box" style="height:100%;min-height:78px;margin:auto">
								<div class="col-xs-6" style="margin:auto 5px auto 10px">
									<span class="m-0 font-600 fs15 capitalize" style="color:#343a40"><fmt:message key='text.catalogs' /></span>
									<p class="fs12 mfortotal"></p>
								</div>
								<div class="col-xs-6" style="margin:auto 10px auto 5px">
									<img src="resources/assets/images/logo_sm.png" class="flex dashboxclient bg-blueicon asLink" style="float:right"
										title="<fmt:message key='text.importcatalogs'/>" alt="<fmt:message key='text.importcatalogs'/>" />
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</div>
			<jsp:include page="homenotifications.jsp" flush="true"/>
		</div>
	</div>
</div>

<div class="scrClientSel flex">
	<div>
		<button type="button" class="closePop" onclick="$('.scrClientSel div').css('display','none');">&times;</button>
		<h4 style="margin:25px 0 0 0"><fmt:message key="text.selectedclient" /></h4><br>
		<p id="scrClientSel"></p>
	</div>
</div>

<div id="findclient-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="findclient-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="color:#fff !important">
			<i class="material-icons">&#xe5cd;</i>
		</button>
		<h4 class="custom-modal-title"></h4>
		<div class="modal-content" style="max-height:640px;background-color:#EBEFF2">
			<div class="modal-body p-0">
				<div data-list="findClient">
					<div class="contAddCli fs11 inlineblock dnone">
						<span class="btnAddCli fs14">+</span>
						<span style="padding:3px 4px 0 4px"><fmt:message key="text.add" /> <fmt:message key="text.client" /></span>
					</div>
					<button type="button" id="btnfilter" class="btn btn-info btn-filter" title="<fmt:message key="text.filtersbycolumns" />"
						onclick="$('#findClientList thead tr:eq(1)').toggle();"><span class="glyphicon glyphicon-filter"></span> 
					</button>
					<button id="clearfilters" class="btn btn-secondary clearfilters" title="<fmt:message key="text.clearfiters" />"
						onclick="cleanTableFilters(this)"><span class="glyphicon glyphicon-filter"></span><i class="material-icons">close</i>
					</button>
					<table class="table tablelist display responsive" id="findClientList">
						<thead>
							<tr>
								<th class="dnone"></th>
								<th><fmt:message key="text.client" /></th>
								<th><fmt:message key="text.address" /></th>
								<th><fmt:message key="text.city" /></th>
								<th><fmt:message key="text.state" /></th>
							</tr>
						</thead>
						<tbody>
							<c:set var="bgc" value="${fn:split('96AEFF,FDD299,88DCD7,FFBCCD,9ACD32,FFF58F,BCC0D3,E7C0FC,b3e6ff,ccccff,b8dc6f', ',')}"/>
							<c:set var="bgcounter" value="0" />
							<c:forEach var="reg" items="${client}">
								<tr class="rowopt">
									<td class="dnone"><input type="radio" name="rowline" data-val="${reg.client}" id="clie${reg.clientid}"></td>
									<td class="flex" style="padding-right:5px">
										<c:set var="noShorts" value="" />
										<c:set var="photo" value="${reg.photo}" />
										<c:forTokens items="${reg.client}" delims=" " var="c">
											<c:set var="hasShort" value="0" />
											<c:forTokens items="DE,DEL,LA,LAS,EL,LOS,Y,AND,THE,OF" delims="," var="i">
												<c:if test="${c.toUpperCase()==i}"><c:set var="hasShort" value="${hasShort+1}" /></c:if>
											</c:forTokens>
											<c:if test="${hasShort==0}"><c:set var="noShorts" value="${noShorts}_${c.toUpperCase()}" /></c:if>
										</c:forTokens>
										<c:set var="arrName" value="${fn:split(noShorts,'_')}" />
										<c:set var="n" value="${(fn:length(arrName))==1?1:(fn:length(arrName))>3?2:1}" />
										
										<div class="inline-block">
											<span class="smallprofile ${photo.indexOf('/')<0?'inlineflex':''}" style="background-color:#${bgc[bgcounter]}">
												<c:if test="${photo!=''}">
													<c:catch var="catchException">
														<c:if test="${photo.indexOf('/')>=0}">
															<img src="${photo.substring(photo.indexOf('doctos/'), photo.length())}"
																class="rounded-circle smallprofile3 fs14 avatar-lg img-thumbnail" title="${reg.client}"
																alt="${arrName[0].substring(0,1)}${arrName[n].substring(0,1)}" onerror="this.classList.add('-noimg');"
																style="border:1px solid #${bgc[bgcounter]};background-color:#${bgc[bgcounter]}"/>
														</c:if>
													</c:catch>
													<c:if test="${catchException!=null || photo.indexOf('/')<0}"><c:set var="photo" value="" /></c:if>
												</c:if>
												<c:if test="${photo==''}">
													${arrName[0].substring(0,1)}${arrName[n].substring(0,1)}
												</c:if>
											</span>
										</div>
										<div>
											<span class="smallprofile2">${reg.client}</span>
											<span class="dnone">
												<c:set var="clientName" value="${reg.client}" />
												<% Object clientName=pageContext.getAttribute("clientName");
													String str=((String) clientName).toLowerCase();
													str=str.replaceAll("[äáâà]","a").replaceAll("[ëéêè]","e").replaceAll("[ïíîì]","i").replaceAll("[öóôò]","o").replaceAll("[üúûù]","u").replaceAll("[ÿýŷỳ]","y").replaceAll("[ñ]","n").replaceAll("[ÄÁÂÀ]","A").replaceAll("[ËÉÊÈ]","E").replaceAll("[ÏÍÎÌ]","I").replaceAll("[ÖÓÔÒ]","O").replaceAll("[ÜÚÛÙ]","U").replaceAll("[ŶÝŶỲ]","Y").replaceAll("[Ñ]","N");
													request.setAttribute("normalizeClient",str); %>
												<c:out value="${normalizeClient}"/>
											</span>
										</div>
										<c:set var="bgcounter" value="${(bgcounter+1>9)?0:bgcounter+1}"/>
									</td>
									<td>${reg.address1}</td>
									<td>${reg.city}</td>
									<td>${reg.state}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="row btnnext">
					<div class="col-xs-12 col-sm-2">
						<button type="button" id="nextClientData" class="btn btn-default waves-effect waves-light" disabled>
							<fmt:message key="text.continue" />
						</button>
					</div>
					<div class="col-xs-12 col-sm-10">
						<p class="selClientFooter"></p>
						<input type="hidden" id="selClientId">
					</div>
				</div>
				
				<div class="form-group dnone" data-client="forInfo">
					<div class="col-12">
						<div id="errorOnFind" class="alert alert-danger fade in" style="display:none">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnFind').toggle();">&times;</a>
							<p id="putErrorOnFind"></p>
						</div>
					</div>
					<div class="row">
						<div class="col-12">
							<h4><fmt:message key="text.client" />: <span class="mb-5 selClient"></span></h4>
						</div>
						<div class="col-12"></div>
						<div class="col-12"><h4 class="mt-5"><fmt:message key="text.select" />...</h4></div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-4">
							<div class="row">
								<button type="button" id="hClientProfile" class="btn btn-orange waves-effect waves-light"
									data-toggle="modal" data-target="#profileclient-modal">
									<fmt:message key="text.profile" />
								</button>
								<button type="button" id="hClientEdit" class="btn btn-orange waves-effect waves-light">
									<fmt:message key="text.edit" /> <fmt:message key="text.client" />
								</button>
							</div>
							<div class="row">
								<div class="homeImgSelCli" id="profileBigImg"></div>
							</div>
						</div>
						<div class="col-sm-12 col-md-8" style="top:-10px">
							<div class="row boxlabeltop">
								<span class="labeltopbtn" style="min-height:auto !important"><fmt:message key="text.proceedings" />:</span>
							</div>
							<div class="row">
								<button type="button" id="hjuicios" class="btn btn-gray br7 waves-effect waves-light">
									<span style="text-transform:capitalize"><fmt:message key="text.trials" />&nbsp;<span id="counthjuicios">(0)</span></span>
								</button>
								<button type="button" id="hapelaciones" class="btn btn-gray br7 waves-effect waves-light">
									<span style="text-transform:capitalize"><fmt:message key="text.appeals" />&nbsp;<span id="counthapelaciones">(0)</span></span>
								</button>
								<button type="button" id="hprotections" class="btn btn-gray br7 waves-effect waves-light">
									<span style="text-transform:capitalize"><fmt:message key="text.protectionsdirect" />&nbsp;<span id="counthprotections">(0)</span></span>
								</button>
								<button type="button" id="hindprotections" class="btn btn-gray br7 waves-effect waves-light">
									<span style="text-transform:capitalize"><fmt:message key="text.indprotections" />&nbsp;<span id="counthindprotections">(0)</span></span>
								</button>
								<button type="button" id="hresources" class="btn btn-gray br7 waves-effect waves-light">
									<span style="text-transform:capitalize"><fmt:message key="text.resources" />&nbsp;<span id="counthresources">(0)</span></span>
								</button>
								<button type="button" id="hstudyAdvisory" class="btn btn-gray br7 waves-effect waves-light">
									<span style="text-transform:capitalize"><fmt:message key="text.studies" />/<fmt:message key="text.consultations" />&nbsp;<span id="countHStudyConsultancies">(0)</span></span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer dnone">
				<button type="button" id="backToSearch" class="btn btn-default waves-effect waves-light dnone">
					<fmt:message key="button.back" />
				</button>
				<button type="button" id="cancelSearch" class="btn btn-default waves-effect waves-light" 
					 data-dismiss="modal" aria-hidden="true">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="profileclient-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span>&times;</span></button>
		<h4 class="custom-modal-title"><fmt:message key="text.customerdata" /></h4>
		<div class="modal-content" id="profileData">
			<div class="modal-body p-0"></div>
			<div class="modal-footer dnone">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12">
						<div class="form-group inlineflex w100p">
							<label for="client" class="supLlb"><fmt:message key='text.clientname' /></label>
							<input type="text" class="form-control c39c" id="client" autocomplete="off" readonly>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12">
						<div class="form-group inlineflex w100p">
							<label for="typeofperson" class="supLlb"><fmt:message key='text.typeofperson' /></label>
							<input type="text" class="form-control c39c" id="typeofperson" autocomplete="off" readonly>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12">
						<div class="form-group inlineflex w100p">
							<label for="address1" class="supLlb"><fmt:message key='text.address' /></label>
							<input type="text" class="form-control c39c" id="address1" autocomplete="off" readonly>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-6 col-md-6">
						<div class="form-group fieldinfo inlineblock">
							<label for="city" class="supLlb"><fmt:message key='text.city' /></label>
							<input type="text" class="form-control c39c" id="city" autocomplete="off" readonly>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-6">
						<div class="form-group inlineflex w100p">
							<label for="state" class="supLlb"><fmt:message key='text.state' /></label>
							<input type="text" class="form-control c39c" id="state" autocomplete="off" readonly>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-6 col-md-6">
						<div class="form-group fieldinfo inlineblock">
							<label for="country" class="supLlb"><fmt:message key='text.country' /></label>
							<input type="text" class="form-control c39c" id="country" autocomplete="off" readonly>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-6">
						<div class="form-group inlineflex w100p">
							<label for="cp" class="supLlb"><fmt:message key='text.zipcode' /></label>
							<input type="text" class="form-control c39c" id="cp" autocomplete="off" readonly>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-6 col-md-6">
						<div class="form-group inlineflex w100p">
							<label for="email" class="supLlb"><fmt:message key='text.email' /></label>
							<input type="text" class="form-control c39c" id="email" autocomplete="off" readonly>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-6">
						<div class="form-group inlineflex w100p">
							<label for="phone" class="supLlb"><fmt:message key='text.phone' /></label>
							<input type="text" class="form-control c39c" id="phone" autocomplete="off" readonly>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-4 col-md-4">
						<div class="form-group fieldinfo inlineblock w100p">
							<label class="supLlb" for="cellphone"><fmt:message key='text.cellphone' /></label>
							<input type="text" class="form-control c39c" id="cellphone" autocomplete="off" readonly>
						</div>
					</div>
					<div class="col-xs-12 col-sm-4 col-md-4">
						<div class="form-group inlineflex w100p">
							<label for="status" class="supLlb"><fmt:message key='text.status' /></label>
							<input type="text" class="form-control c39c" id="status" autocomplete="off" readonly>
						</div>
					</div>
					<div class="col-xs-12 col-sm-4 col-md-4">
						<div class="form-group fieldinfo inlineblock w100p">
							<label class="supLlb" for="birthdateFix"><fmt:message key='text.birthday' /></label>
							<input type="hidden" id="birthdate">
							<input type="text" class="form-control c39c" id="birthdateFix" autocomplete="off" readonly>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12">
						<div class="form-group inlineflex w100p">
							<label class="supLlb" for="contactperson"><fmt:message key='text.contactperson' /></label>
							<input type="text" class="form-control c39c" id="contactperson" autocomplete="off" pattern="(https?://)?([^/:]+(\\:(\\d+))?)(/\\.*)?">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12">
						<div class="form-group inlineflex w100p">
							<label class="supLlb"><fmt:message key='text.website' /></label>
							<a href="#" id="webpage" target="_blank" title="<fmt:message key="text.gotowebpage" />" class="inputmask" rel="noopener noreferrer"></a>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"><h3 class="h3snTitle"><fmt:message key="text.socialnetworks"/></h3></div>
					<div class="col-xs-12">
						<div class="card-box table-responsive" style="padding:0">
							<table id="snTableListOnHome" data-order='[[0,"desc"]]' class="table table-striped table-bordered table-responsive">
								<thead>
									<tr>
										<th class="tac"><fmt:message key="text.socialnetwork" /></th>
										<th class="tac"><fmt:message key="text.address" /></th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12">
						<div class="form-group inlineflex w100p">
							<label for="comments" class="supLlb"><fmt:message key='text.comments' /></label>
							<textarea class="form-control c39c txArea4r" id="comments"></textarea>
						</div>
					</div>
				</div>
				<button type="button" id="cancelViewProfile" class="btn btn-default waves-effect waves-light"
					class="close" data-dismiss="modal" aria-hidden="true">
					<fmt:message key="button.close" />
				</button>
			</div>
		</div>
	</div>
</div>

<div id="filter-commonlaw-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="filter-commonlaw-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<div class="row m-0" style="background-color:#4D6EFF">
			<div class="col-xs-12 col-sm-4 filtertitle">
				<h4 class="custom-modal-title"><fmt:message key="text.searchtrialsby" /></h4>
			</div>
			<div>
				<button type="button" class="closetrailbox flex" data-dismiss="modal" aria-hidden="true"><i class="material-icons">&#xe5cd;</i></button>
			</div>
		</div>
		<div class="modal-content p-0 mc-findtrial">
			<form id="home-ddul-cl">
				<div class="modal-body p-0">
					<div class="row">
						<div class="col-xs-12 col-sm-4 filtersbox">
							<div class="flex filtertrialcard">
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
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-yellow">
										<img src="resources/assets/images/materias.png" class="flex dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.matter' />" alt="<fmt:message key='text.matter' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.matter" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddl-cl-matter');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-cl-matter');"
										onkeyup="filterli(this, 'ddl-cl-matter');">
								</div>
								<div class="ddlimenu"><ul id="ddl-cl-matter" data-filter="commonlaw"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-orange">
										<img src="resources/assets/images/ciudad-jurisdiccional.svg" class="flex dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.city' />" alt="<fmt:message key='text.city' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.city" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddl-cl-city');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-cl-city');"
										onkeyup="filterli(this, 'ddl-cl-city');">
								</div>
								<div class="ddlimenu"><ul id="ddl-cl-city" data-filter="commonlaw"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-carmin">
										<img src="resources/assets/images/courthouse.png" class="flex dashboxclient asLink" onclick="getCourts('ddl-cl-court','ddul','cl_');toggleArrow(thisddl-cl-courturt');"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.court' />" alt="<fmt:message key='text.court' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.court" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddl-cl-court');">keyboard_arrow_down</i>
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
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-green">
										<img src="resources/assets/images/expedientes.png" class="flex dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.proceedings' />" alt="<fmt:message key='text.proceedings' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.proceedings" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg"
										onclick="getProceedings('ddl-cl-proceedings','ddul','cl_');toggleArrow(this,'#ddl-cl-proceedings');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="getProceedings('ddl-cl-proceedings','ddul','cl_');toggleArrow(this,'#ddl-cl-proceedings');"
										onkeyup="filterli(this, 'ddl-cl-proceedings');">
								</div>
								<div class="ddlimenu"><ul id="ddl-cl-proceedings" data-filter="commonlaw"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-purple">
										<img src="resources/assets/images/estatus-juicios.png" class="flex dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.status' />" alt="<fmt:message key='text.status' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.status" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddl-cl-status');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-cl-status');"
										onkeyup="filterli(this, 'ddl-cl-status');">
								</div>
								<div class="ddlimenu"><ul id="ddl-cl-status" data-filter="commonlaw"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-brown">
										<img src="resources/assets/images/abogados.png" class="flex dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.lawyer' />" alt="<fmt:message key='text.lawyer' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.lawyer" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddl-cl-lawyer');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-cl-lawyer');"
										onkeyup="filterli(this, 'ddl-cl-lawyer');">
								</div>
								<div class="ddlimenu"><ul id="ddl-cl-lawyer" data-filter="commonlaw"></ul></div>
							</div>
						</div>

						<div class="col-xs-12 col-sm-8" style="padding:0 5px 0 25px !important">
							<div class="col-xs-12 bg-white trailbox p-0" style="overflow:auto">
								<table class="table tablelist display responsive homeTableList" id="commonLawList">
								<thead>
									<tr>
										<th><fmt:message key="text.proceedings" /></th>
										<th><fmt:message key="text.client" /></th>
										<th><fmt:message key="text.court" /></th>
										<th><fmt:message key="text.matter" /></th>
										<th><fmt:message key="text.city" /></th>
										<th><fmt:message key="text.status" /></th>
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

<div id="filter-feddoc-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="filter-feddoc-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<div class="row m-0" style="background-color:#4D6EFF">
			<div class="col-xs-12 col-sm-4 filtertitle">
				<h4 class="custom-modal-title"><fmt:message key="text.searchtrialsby" /></h4>
			</div>
			<div>
				<button type="button" class="closefeddocbox flex" data-dismiss="modal" aria-hidden="true"><i class="material-icons">&#xe5cd;</i></button>
			</div>
		</div>
		<div class="modal-content p-0 mc-findtrial">
			<form id="home-ddul-fd">
				<div class="modal-body p-0">
					<div class="row">
						<div class="col-xs-12 col-sm-4 filtersbox">
							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-blue">
										<img src="resources/assets/images/cliente-juicios.png" class="flex dashboxclient asLink" style="float:right" 
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.client' />" alt="<fmt:message key='text.client' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.client" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddl-feddoc" onclick="toggleArrow(this,'#ddl-ft-client');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-ft-client');" onkeyup="filterli(this, 'ddl-ft-client');">
								</div>
								<div class="ddlimenu"><ul id="ddl-ft-client" data-filter="fedtrial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-green">
										<img src="resources/assets/images/juicios-amparo.png" class="flex dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.federaltrialtype' />"
											alt="<fmt:message key='text.federaltrialtype' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.federaltrialtype" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddl-feddoc"
										onclick="toggleArrow(this,'#ddl-fd-typetrial');">
										keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddl-fd-typetrial');" onkeyup="filterli(this, 'ddl-fd-typetrial');">
								</div>
								<div class="ddlimenu"><ul id="ddl-fd-typetrial" data-filter="fedtrial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-green">
										<img src="resources/assets/images/juicios-amparo.png" class="flex dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.search' /> <fmt:message key='text.document' />"
											alt="<fmt:message key='text.document' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.document" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddl-feddoc"
onclick="toggleArrow(this,'#ddl-ft-NumDoc');getNumberDoc('ddl-ft-NumDoc','ul','ft_');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
onfocus="toggleArrow(this,'#ddl-ft-NumDoc');getNumberDoc('ddl-ft-NumDoc','ul','ft_');"
										onkeyup="filterli(this, 'ddl-ft-NumDoc');">
								</div>
								<div class="ddlimenu"><ul id="ddl-ft-NumDoc" data-filter="fedtrial"></ul></div>
							</div>

							<div class="flex filtertrialcard" style="background-color:#eee">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-purple">
										<img src="resources/assets/images/juicios-amparo.png" class="flex dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.civilproceedings' />"
											alt="<fmt:message key='text.civilproceedings' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.civilproceedings" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddl-feddoc">keyboard_arrow_down</i>
<input type="search" class="form-control input-sm ddlisearch">
								</div>
								<div class="ddlimenu"><ul id="ddl-fd-proccivil" data-filter="fedtrial"></ul></div>
							</div>

							<div class="flex filtertrialcard" style="background-color:#eee">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="inlineflex frameicon fr-brown">
										<img src="resources/assets/images/juicios-amparo.png" class="flex dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.criminalproceedings' />"
											alt="<fmt:message key='text.criminalproceedings' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlfilters-subtitle" style="min-height:auto !important"><fmt:message key="text.criminalproceedings" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddl-feddoc">keyboard_arrow_down</i>
<input type="search" class="form-control input-sm ddlisearch">
								</div>
								<div class="ddlimenu"><ul id="ddl-fd-proccriminal" data-filter="fedtrial"></ul></div>
							</div>
						</div>

						<div class="col-xs-12 col-sm-8" style="padding:0 5px 0 5px !important">
							<div class="col-xs-12 bg-white trailbox p-0" style="overflow:auto">
								<table class="table tablelist display responsive homeTableList" id="federalDocList">
								<thead>
									<tr>
										<th><fmt:message key="text.federaltrialtype" /></th>
										<th><fmt:message key="text.documentnumber" /></th>
										<th><fmt:message key="text.client" /></th>
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

<script>
	var table;
	jQuery(document).ready(function($){
//		$('#findClientList thead tr').clone(true).appendTo( '#findClientList thead' );
		$('#findClientList thead tr:eq(1)').css('display','none');
		$('#findClientList thead tr:eq(1) th').each( function (i) {
			var title = i18n('msg_filter_by') + ' ' + $(this).text();
			$(this).html('<input type="text" class="inputFilter" name="filterby" placeholder="' + title + '">');
			$( 'input', this ).on( 'keyup input paste change delete cut clear', function () {
				table = $('#findClientList').DataTable();
				if ( table.column(i).search() !== this.value ){
					table.column(i).search( this.value ).draw();
					this.focus();
					var allfilters=$('[name=filterby]'),hasfilters=!1;
					for(f=0; f<allfilters.length;f++)
						if(this.value!=''){
							hasfilters=!0;
							break;
						}
					if(hasfilters)
						$('#clearfilters').addClass('btn-warning')
							.removeClass('btn-secondary');
					else
						$('#clearfilters').addClass('btn-secondary')
							.removeClass('btn-warning');
				}
			});
		});

		$('#findClientList').DataTable({
			"lengthMenu":[[5, 10, 25, 50, -1],[5, 10, 25, 50, i18n('msg_all')]],
			paging:true,
			scrollCollapse:true,
			autoWidth:true,
			searching:true,
			orderCellsTop: true,
			fixedHeader: true,
			pagingType:"simple_numbers",
			'dom':'<"col-sm-3"f><tr><"row"<"col-xs-12 col-sm-12 col-md-4 fs12"i><"col-xs-12 col-sm-6 col-md-3 xs-center-items"l><"col-xs-12 col-sm-6 col-md-5 xs-center-items"p>>',
			columnDefs:[{'width':'auto','targets':'_all'}]
		});
		$('#findTrialList').DataTable({
			paging:false,
			scrollCollapse:true,
			autoWidth:true,
			searching:false,
			orderCellsTop: true,
			fixedHeader: true,
			columnDefs:[{'width':'auto','targets':'_all'}]
		});
	});

	function cleanTableFilters(e){
   	 	$('[name=filterby]').val('');
   	 	table.search('').columns().search('').draw();
   	 	$(e).addClass('btn-secondary').removeClass('btn-warning');
	};
</script>