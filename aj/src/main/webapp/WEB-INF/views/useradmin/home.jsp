<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<title>Contacto jur&iacute;dico</title>

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
<!-- link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"-->

<!-- link rel="stylesheet" type="text/css" href="resources/css/globalcontrols.css"-->
<link rel="stylesheet" type="text/css" href="resources/css/home.css">

<c:if test="${role==2}">
<style>
	html,body{background-image:url('resources/assets/images/geometry.png')}
	.filterby{display:none}
	[data-client="forInfo"]{padding:0 10px}
</style>
</c:if>

<style>
	/* Consultas (ini) */
	#errorOnAddClient, #errorOnAddConsultation{display:none}
	.mr10px{margin-right:10px !important}
	.keepPressed{-webkit-box-shadow:inset 0 0 5px;box-shadow:inset 0 0 5px}
	.consClose{position:absolute;margin:13px 18px 0 0;top:0;right:0;color:#fff !important}
	#consultationList tr:hover{-webkit-box-shadow:0 3px 10px #aaa;box-shadow:0 3px 10px #aaa}
	#consultationList tr:nth-child(even){background-color:#f4f8fb}
	#consultationList th{text-align:center}
	/* Consultas (fin) */

	.odd{background-color: #B50000!important}
	.containerLogo{
	    position: relative;
	    width: 20%;
	    height: 100vh;
	    margin: 0 auto;
	    display: flex;
		z-index: 0;
	    justify-content: center;
	    align-items: center;
		background: none;
	}
	.containerLogo img{
	    position: absolute;
	    top: 50%;
	    left: 50%;
	    transform: translate(-50%, -50%);
	    width: 100%;
	    height: auto;
	}
	.image1{
	    z-index: 2000;
	}
	.image2{
	    z-index: 1000;
	}
	.btn-small-info{font-size:18px;-webkit-border-radius:50%;-moz-border-radius:50%;border-radius:50%;
		border:0px solid transparent;background-color:transparent;color: #39c}
	.hx-client{vertical-align:middle;-webkit-align-items:center;align-items:center}
	.home-client-title2{position:absolute;font-size:20px;margin:10px;color:#fff}
	.home-row-client{-webkit-justify-content:space-between;justify-content:space-between;border:1px solid #eee}
	.home-row-client:hover{-webkit-border-radius:10px;-moz-border-radius:10px;border-radius:10px;
		-webkit-box-shadow:0 0 15px #ddd;box-shadow:0 0 15px #ddd}
	@media (max-width: 572px){
		[data-client=forInfo] div{display:block;text-align:center}
		#profileBigImg{width:50px;margin:auto}
		.homeImgSelCli img{max-width:50px}
	}
	#addClient1{
		position: absolute;left: 207px;top: 11px;background: transparent;border: transparent;
		}
	@media (max-width: 768px){
		#addClient1{
			left:10px !important;
			z-index: 100;
		}
	}
</style>
<div id="popup-notification" style="position:fixed; top:20px; right:20px; width:300px; z-index:9999;"></div>
<script>
function showPopup(title, body, links, origin, image, ititle, ialt){
            var popup = $('<div class="popup-message"></div>'),
              lefticon = $('<div class="popup-lefticon"><img src="'+image
//Agregar el círculo que tendrá el avatar
                +'" title="'+ititle+'" alt="'+ialt+'"></div>'),
              middlemsg=$('<div class="popup-middlemsg"></div>')
              header = $('<div class="popup-header"></div>').text(title),
              content = $('<div class="popup-body"></div>').text(body),
              linkContainer = $('<div class="popup-links"></div>'),
              blankbase = $('<div class="popup-blankbase"></div>');
              rewrite=$('<a href="'+origin+'" target="_self" rel="noopener noreferrer"'
                +'alt="Editar" style="float:left"><i class="fa fa-edit"></i></a>');

            middlemsg.append(header).append(header).append(content).append(linkContainer);

            links.forEach(function(link){
                var extension = link.url.split('.').pop().toLowerCase();
                var icon = getNtfyFileIcon(extension);
                linkContainer.append($('<a href="' + link.url + '" target="_blank" rel="noopener noreferrer">'
                  + icon + ' ' + link.text + '</a><br>'));
            });

            var buttons = $('<div class="popup-buttons"></div>');
            var seenButton = $('<button class="seen">Visto</button>').click(function(){
                removePopup(popup);
            });
            var laterButton = $('<button class="show-later">Ok</button>').click(function(){
                removePopup(popup);
            });

            buttons.append(rewrite).append(seenButton).append(laterButton);
            //popup.append(header).append(lefticon).append(content).append(linkContainer).append(blankbase).append(buttons);
            popup.append(header).append(lefticon).append(middlemsg).append(blankbase).append(buttons);


            // Add arrow to toggle expansion
            var arrow = $('<div class="popup-arrow"><i class="fas fa-chevron-down"></i></div>');
            arrow.click(function(e){
                e.stopPropagation(); // Prevent the popup from closing when clicking the arrow
                popup.toggleClass('popup-expanded');
            });
            popup.append(arrow);

            $('#popup-notification').append(popup);

            // Prevent hiding when mouse is over the popup
            popup.hover(
                function(){
                    clearTimeout(popup.data('timeout'));
                },
                function(){
                    startHideTimer(popup);
                }
            );

            setTimeout(function(){
                popup.addClass('show');
            }, 10);

            // Start the hide timer
            startHideTimer(popup);
        }
</script>

<div class="content-page">
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<p class="page-title-alt fs16 m-0">
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
									title="<fmt:message key='text.customerdata'/>" alt="<fmt:message key='text.customerdata'/>">
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
									title="<fmt:message key='text.customerdata'/>" alt="<fmt:message key='text.customerdata'/>">
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
								<c:set var="trcount" value="${fn:split(trials, ',')}"/>
								<p class="fs12 mfortotal">${fn:length(trcount)}</p>
							</div>
							<div class="col-xs-6" style="margin:auto 10px auto 5px">
								<div class="bg-green1icon">
									<img src="resources/assets/images/trials.png" class="flex dashboxclient asLink" style="float:right"
										title="<fmt:message key="text.commonjurisdiction" />" alt="<fmt:message key="text.commonjurisdiction" />">
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
										title="<fmt:message key="text.cjf" />" alt="CJF">
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
									style="float:right" title="<fmt:message key='calendar.title'/>" alt="CAL">
							</div>
						</div>
					</div>
				</div>

				<c:if test="${role==1}">
					<div class="col-sm-12 flex" style="width:253px">
						<div class="widget-panel widget-style-2 waves-effect bg-white optCard" data-home="importCatalogs">
							<div class="row center-box" style="height:100%;min-height:78px;margin:auto">
								<div class="col-xs-6" style="margin:auto 5px auto 10px">
									<span class="m-0 font-600 fs15 capitalize" style="color:#343a40"><fmt:message key='text.catalogs' /></span>
									<p class="fs12 mfortotal"></p>
								</div>
								<div class="col-xs-6" style="margin:auto 10px auto 5px">
									<img src="resources/assets/images/logo_sm.png" class="flex dashboxclient bg-blueicon asLink" style="float:right"
										title="<fmt:message key='text.importcatalogs'/>" alt="<fmt:message key='text.importcatalogs'/>">
								</div>
							</div>
						</div>
					</div>
				</c:if>

				<div class="col-sm-12 flex" style="width:253px">
					<div class="widget-panel widget-style-2 waves-effect bg-white optCard" data-toggle="modal"
						data-target="#consulting-modal" onclick="loadHomeConsulting();">
						<div class="row center-box" style="height:100%;min-height:78px !important;
							margin:auto !important;-webkit-box-shadow:0 0 15px #444;box-shadow:0 0 15px #444">
							<div class="col-xs-12" style="margin:auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize" style="color:#343a40"><fmt:message key='text.consultations' /></span>
								<p class="fs12 mfortotal">0</p>
							</div>
							<div class="col-xs-6" style="margin:auto 10px auto 5px">
								<div class="bg-bluegreenicon">
									<img src="resources/assets/images/logo_consultas.png" class="flex dashboxclient asLink" style="float:right"
										title="<fmt:message key="text.consultations" />" alt="<fmt:message key='text.consultations' />">
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-12 flex" style="width:253px">
					<!-- div class="widget-panel widget-style-2 waves-effect bg-white optCard" data-home="calendar">
						<div class="row center-box" style="height:100%;min-height:78px;margin:auto">
							<div class="col-xs-12" style="margin:auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize" style="color:#343a40"><fmt:message key='text.contracts' /></span>
								<p class="fs12 mfortotal">${schedules.size()==null||schedules.size()<=0?0:schedules.size()}</p>
							</div>
							<div class="col-xs-6" style="margin:auto 10px auto 5px">
								<div class="bg-brown">
									<img src="resources/assets/images/logoContratos.png" class="flex dashboxclient asLink" style="float:right"
										title="<fmt:message key="text.cjf" />" alt="CJF">
								</div>
							</div>
						</div>
					</div-->
				</div>
				<div class="col-sm-12 flex" style="width:253px">
					<!-- div class="widget-panel widget-style-2 waves-effect bg-white optCard" data-home="calendar">
						<div class="row center-box" style="height:100%;min-height:78px;margin:auto">
							<div class="col-xs-12" style="margin:auto 5px auto 10px">
								<span class="m-0 font-600 fs15 capitalize" style="color:#343a40"><fmt:message key='text.corporate' /></span>
								<p class="fs12 mfortotal">${schedules.size()==null||schedules.size()<=0?0:schedules.size()}</p>
							</div>
							<div class="col-xs-6" style="margin:auto 10px auto 5px">
								<div class="bg-yellow">
									<img src="resources/assets/images/logoCharge1.png" class="flex dashboxclient asLink" style="float:right"
										title="<fmt:message key="text.cjf" />" alt="CJF">
								</div>
							</div>
						</div>
					</div-->
				</div>
			</div>
			<jsp:include page="homenotifications.jsp" flush="true"/>
		</div>
	</div>
</div>

<div id="findclient-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="findclient-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<div class="row m-0" style="height:51px;background-color:#4D6EFF">
			<span class="home-client-title2"><fmt:message key="text.client" /></span>
			<button type="button" class="close m-t-10 m-r-10" data-dismiss="modal" aria-hidden="true">
				<i class="material-icons" style="top:0;bottom:0;color:#fff">&#xe5cd;</i>
			</button>
		</div>
		<div class="modal-body p-0" style="background-color:#fff;overflow:auto">
			<div data-list="findClient">
				<button type="button" id="btnfilter" class="btn btn-info btn-filter" title="<fmt:message key="text.filtersbycolumns" />"
					onclick="$('#findClientList thead tr:eq(1)').toggle();"><span class="glyphicon glyphicon-filter"></span> 
				</button>
				<button id="clearfilters" class="btn btn-secondary clearfilters" title="<fmt:message key="text.clearfiters" />"
					onclick="cleanTableFilters(this)"><span class="glyphicon glyphicon-filter"></span><i class="material-icons">close</i>
				</button>
				<button id="addClient1" value="<fmt:message key='button.newcustomer'/>" title="<fmt:message key='button.add.client'/>">
					<i class="material-icons" style="color:#39c;">&#xe147;</i>
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
									<c:set var="shortName" value="${arrName[0].substring(0,1)}${arrName[n].substring(0,1)}" />

									<div class="inline-block">
										<span class="smallprofile inlineflex" style="background-color:#${bgc[bgcounter]}">
										<c:if test="${photo=='' || empty photo}">${shortName}</c:if>
										<c:if test="${not empty photo}">
											<c:catch var="catchException">
												<c:if test="${photo.indexOf('/')>=0}">
													<img src="${photo.substring(photo.indexOf('doctos/'), photo.length())}"
														class="rounded-circle smallprofile3 fs14 avatar-lg img-thumbnail"
														title="${reg.client}" onerror="this.classList.add('-noimg');"
														alt="${shortName}"
														style="border:1px solid #${bgc[bgcounter]};background-color:#${bgc[bgcounter]}"/>
												</c:if>
											</c:catch>
											<c:if test="${catchException!=null}"><c:set var="photo" value="" />${shortName}</c:if>
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
					<button type="button" id="nextClientData" class="btn btn-default waves-effect waves-light">
						<fmt:message key="text.continue" />
					</button>
				</div>
				<div class="col-xs-12 col-sm-10">
					<p class="selClientFooter"></p>
					<input type="hidden" id="selClientId">
				</div>
			</div>

			<div class="form-group dnone" data-client="forInfo" style="padding-left:10px">
				<div class="col-12">
					<div id="errorOnFind" class="alert alert-danger fade in" style="display:none">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnFind').toggle();">&times;</a>
						<p id="putErrorOnFind"></p>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
<!--h4 class="inlineflex h4-client"><fmt:message key="text.client" />.</h4-->
						<h3 class="flex hx-client">
							<button type="button" id="hClientProfile" class="btn-small-info trn2ms inlineflex"
								data-toggle="modal" data-target="#profileclient-modal" title="<fmt:message key='text.additionalinfo' />">
								<i class="material-icons" style="font-size:21px">info_outline</i>
							</button>
							<fmt:message key="text.client" var="clientText" />
							<button type="button" id="hClientEdit" class="btn-small-info trn2ms inlineflex"
								title="<fmt:message key='text.edit' /> ${fn:toLowerCase(clientText)}">
								<i class="fa fa-edit" style="font-size:18px"></i>
							</button>
							<span class="mb-5 selClient"></span>
						</h3>
					</div>
				</div>
				<div class="row inlineflex">
					<div><div class="homeImgSelCli" id="profileBigImg"></div></div>
					<div class="col-sm-12">
						<div class="row">
							<h4 class="mt-5"><fmt:message key="text.select" /></h4>
							<button type="button" id="hjuicios" class="btn-newwhite br7 trn2ms waves-effect waves-light">
								<span style="text-transform:capitalize"><fmt:message key="text.trials" />&nbsp;<span id="counthjuicios">(0)</span></span>
							</button>
							<button type="button" id="hapelaciones" class="btn-newwhite br7 trn2ms waves-effect waves-light">
								<span style="text-transform:capitalize"><fmt:message key="text.appeals" />&nbsp;<span id="counthapelaciones">(0)</span></span>
							</button>
							<button type="button" id="hprotections" class="btn-newwhite br7 trn2ms waves-effect waves-light">
								<span style="text-transform:capitalize"><fmt:message key="text.protectionsdirect" />&nbsp;<span id="counthprotections">(0)</span></span>
							</button>
							<button type="button" id="hindprotections" class="btn-newwhite br7 trn2ms waves-effect waves-light">
								<span style="text-transform:capitalize"><fmt:message key="text.indprotections" />&nbsp;<span id="counthindprotections">(0)</span></span>
							</button>
							<button type="button" id="hresources" class="btn-newwhite br7 trn2ms waves-effect waves-light">
								<span style="text-transform:capitalize"><fmt:message key="text.resources" />&nbsp;<span id="counthresources">(0)</span></span>
							</button>
							<button type="button" id="hconsultas" class="btn-newwhite br7 trn2ms waves-effect waves-light">
								<span style="text-transform:capitalize">
									<fmt:message key="text.studies" />/<fmt:message key="text.consultations" />&nbsp;<span id="counthconsultas">(0)</span>
								</span>
							</button>
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

<div id="profileclient-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="display:none">
	<div class="modal-dialog" style="-webkit-box-shadow:0 0 15px #444;box-shadow:0 0 15px #444">
		<button type="button" class="close m-r-10" style="font-size:32px;color:yellow !important"
			data-dismiss="modal" aria-hidden="true"><span>&times;</span>
		</button>
		<h4 class="custom-modal-title"><fmt:message key="text.customerdata" /></h4>
		<div class="modal-content" id="profileData">
			<div class="modal-body p-0">
				<div class="form-group m-0">
					<div class="row container-tabsmodal m-0">
						<ul id="clientmodaltabs" class="tabsmodal">
							<li class="selectedtab"
								onclick="togglemodtab(this,'#tab-homeclient-basic-info');"><fmt:message
									key="text.generaldata" /></li>
							<li class="trn2ms"
								onclick="togglemodtab(this,'#tab-homeclient-contact-ways');"><fmt:message
									key="text.contactways" /></li>
							<li class="trn2ms"
								onclick="togglemodtab(this,'#tab-homeclient-additional');"><fmt:message
									key="text.additionalinfo" /></li>
						</ul>
					</div>
				</div>
				<div class="form-group">
					<div id="tab-homeclient-basic-info" class="firmdatatabs p-20" style="overflow:auto">
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-client"><fmt:message key='text.clientname' /></label>
							<span id="pfrl-client"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-email"><fmt:message key='text.email' /></label>
							<span id="pfrl-email"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-address1"><fmt:message key='text.address' /></label>
							<span id="pfrl-address1"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-country"><fmt:message key='text.country' /></label>
							<span id="pfrl-country"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-state"><fmt:message key='text.state' /></label>
							<span id="pfrl-state"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-city"><fmt:message key='text.city' /></label>
							<span id="pfrl-city"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-cp"><fmt:message key='text.zipcode' /></label>
							<span id="pfrl-cp"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-status"><fmt:message key='text.status' /></label>
							<span id="pfrl-status" style="color:#191970"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-clieRel_with"><fmt:message key='text.rel_with' /></label>
							<span id="pfrl-clieRel_with"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-clieRef_by"><fmt:message key='text.ref_by' /></label>
							<span id="pfrl-clieRef_by"></span>
						</div>
					</div>

					<div id="tab-homeclient-contact-ways" class="firmdatatabs p-20" style="overflow:auto">
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-cellphone"><fmt:message key='text.cellphone' /></label>
							<span id="pfrl-cellphone"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-phone"><fmt:message key='text.phone' /></label>
							<span id="pfrl-phone"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-contactperson"><fmt:message key='text.contactperson' /></label>
							<span id="pfrl-contactperson"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-webpage"><fmt:message key='text.website' /></label>
							<span id="pfrl-webpage"></span>
						</div>
							<div class="col-xs-12 flex">
								<h3 class="capitalize"><fmt:message key="text.socialnetworks" /></h3>								
							</div>
							<div class="col-xs-12 flex trn2ms home-row-client">
								<div class="card-box table-responsive" style="padding:0">
									<table id="pfrl-snTableListOnHome" data-order='[[0,"desc"]]' class="table table-striped table-bordered table-responsive">
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
					</div>

					<div id="tab-homeclient-additional" class="firmdatatabs p-20" style="overflow:auto">
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-typeofperson"><fmt:message key='text.typeofperson' /></label>
							<span id="pfrl-typeofperson"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="clieBirthdate"><fmt:message key='text.birthday' /></label>
							<span id="pfrl-birthdate"></span>
							<input type="hidden" id="pfrl-birthdateFix">
							<span id="pfrl-birthdateFix"></span>
						</div>
						<div class="col-xs-12 flex trn2ms home-row-client">
							<label for="pfrl-comments"><fmt:message key='text.comments' /></label>
							<!--textarea class="form-control c39c txArea4r" id="pfrl-comments"></textarea-->
							<span id="pfrl-comments"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="filter-commonlaw-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="filter-commonlaw-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<div class="row m-0" style="height:auto;background-color:#4D6EFF">
			<div class="col-xs-12 col-sm-4 filtertitle">
				<h4 class="custom-modal-title"><fmt:message key="text.searchbyproceedings" /></h4>
			</div>
			<div>
				<button type="button" class="closetrailbox flex" data-dismiss="modal" aria-hidden="true"><i class="material-icons">&#xe5cd;</i></button>
			</div>
		</div>
		<div class="modal-content p-0 mc-findtrial">
			<form id="home-ddul-cl">
				<div class="modal-body p-0">
					<div class="row modal-list-container" >
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

						<div class="col-xs-12 col-sm-8 modal-left-list">
							<div class="bg-white trailbox p-0" style="overflow:auto">
								<table class="table tablelist display responsive homeTableList" id="commonLawList">
								<thead>
									<tr>
										<th><fmt:message key="text.proceedings"/></th>
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
		<div class="row m-0" style="height:51px;background-color:#4D6EFF">
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
					<div class="row modal-list-container">
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

						<div class="col-xs-12 col-sm-8 modal-left-list">
							<div class="bg-white trailbox p-0" style="overflow:auto">
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

<input type="hidden" id="homefortrails">
<input type="hidden" id="homefeddoc">
<input type="hidden" id="feddoclist">

<div id="findtrial-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="findtrial-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<div class="row m-0" style="height:51px;background-color:#4D6EFF">
			<div class="col-xs-12 col-sm-4 filtertitle">
				<h4 class="custom-modal-title"><fmt:message key="text.searchtrialsby" /></h4>
			</div>
			<div class="col-xs-12 col-sm-8">
				<button type="button" class="closetrailbox" data-dismiss="modal" aria-hidden="true"><i class="material-icons">&#xe5cd;</i></button>
			</div>
		</div>
		<div class="modal-content p-0 mc-findtrial">
			<form id="hometrial-ddul">
				<div class="modal-body p-0">
					<div class="row">
						<div class="col-xs-12 col-sm-4 filtersbox">
							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-blue">
										<img src="resources/assets/images/cliente-juicios.png" class="dashboxclient asLink" style="float:right" 
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.client' />" alt="<fmt:message key='text.client' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.client" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddlmf-homeclient');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddlmf-homeclient');"
										onkeyup="filterli(this, 'ddlmf-homeclient');">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homeclient" data-filter="trial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-yellow">
										<img src="resources/assets/images/materias.png" class="dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.matter' />" alt="<fmt:message key='text.matter' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.matter" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddlmf-homematter');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddlmf-homematter');"
										onkeyup="filterli(this, 'ddlmf-homematter');">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homematter" data-filter="trial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-orange">
										<img src="resources/assets/images/ciudad-jurisdiccional.svg" class="dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.city' />" alt="<fmt:message key='text.city' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.city" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddlmf-homecity');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddlmf-homecity');"
										onkeyup="filterli(this, 'ddlmf-homecity');">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homecity" data-filter="trial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-carmin">
										<img src="resources/assets/images/courthouse.png" class="dashboxclient asLink" onclick="getddliCourts('ddlmf-homecourt','ddul');toggleArrow(this,'#ddlmf-homecourt');"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.court' />" alt="<fmt:message key='text.court' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.court" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddlmf-homecourt');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="getddliCourts('ddlmf-homecourt','ddul');toggleArrow(this,'#ddlmf-homecourt');"
										onkeyup="filterli(this, 'ddlmf-homecourt');">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homecourt" data-filter="trial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-green">
										<img src="resources/assets/images/expedientes.png" class="dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.proceedings' />" alt="<fmt:message key='text.proceedings' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.proceedings" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg"
										onclick="getProceedings('ddlmf-homeproceedings','ddul');toggleArrow(this,'#ddlmf-homeproceedings');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="getProceedings('ddlmf-homeproceedings','ddul');toggleArrow(this,'#ddlmf-homeproceedings');"
										onkeyup="filterli(this, 'ddlmf-homeproceedings');">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homeproceedings" data-filter="trial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-purple">
										<img src="resources/assets/images/estatus-juicios.png" class="dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.status' />" alt="<fmt:message key='text.status' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.status" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddlmf-homestatus');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddlmf-homestatus');"
										onkeyup="filterli(this, 'ddlmf-homestatus');">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homestatus" data-filter="trial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-brown">
										<img src="resources/assets/images/abogados.png" class="dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.lawyer' />" alt="<fmt:message key='text.lawyer' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.lawyer" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" onclick="toggleArrow(this,'#ddlmf-homelawyer');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddlmf-homelawyer');"
										onkeyup="filterli(this, 'ddlmf-homelawyer');">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homelawyer" data-filter="trial"></ul></div>
							</div>
						</div>

						<div class="col-xs-12 col-sm-8" style="padding:0 5px 0 25px !important">
							<div class="col-xs-12 bg-white trailbox p-0" style="overflow:auto">
								<table class="table tablelist display responsive homeTableList" id="findtrialList">
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

<div id="findfeddoc-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="findfeddoc-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<div class="row m-0" style="height:51px;background-color:#4D6EFF">
			<div class="col-xs-12 col-sm-4 filtertitle">
				<h4 class="custom-modal-title"><fmt:message key="text.searchtrialsby" /></h4>
			</div>
			<div class="col-xs-12 col-sm-8">
				<button type="button" class="closefeddocbox" data-dismiss="modal" aria-hidden="true"><i class="material-icons">&#xe5cd;</i></button>
			</div>
		</div>
		<div class="modal-content p-0 mc-findtrial">
			<form id="homefeddoc-ddul">
				<div class="modal-body p-0">
					<div class="row">
						<div class="col-xs-12 col-sm-4 filtersbox">
							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-blue">
										<img src="resources/assets/images/cliente-juicios.png" class="dashboxclient asLink" style="float:right" 
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.client' />" alt="<fmt:message key='text.client' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.client" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddlmf-homefed" onclick="toggleArrow(this,'#ddlmf-homefedclient');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddlmf-homefedclient');"
										onkeyup="filterli(this, 'ddlmf-homefedclient');">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homefedclient" data-filter="fedtrial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-green">
										<img src="resources/assets/images/juicios-amparo.png" class="dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.federaltrialtype' />"
											alt="<fmt:message key='text.federaltrialtype' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.federaltrialtype" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddlmf-homefed"
										onclick="toggleArrow(this,'#ddlmf-hometypetrial');">
										keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddlmf-hometypetrial');"
										onkeyup="filterli(this, 'ddlmf-hometypetrial');">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-hometypetrial" data-filter="fedtrial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-green">
										<img src="resources/assets/images/juicios-amparo.png" class="dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.search' /> <fmt:message key='text.document' />"
											alt="<fmt:message key='text.document' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.protectionnumber" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddlmf-homefed"
										onclick="toggleArrow(this,'#ddlmf-homefedNumDoc');$('#homefeddoc').val('feddoclist');getNumberDoc('ddlmf-homefedNumDoc','ddul');">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch"
										onfocus="toggleArrow(this,'#ddlmf-homefedNumDoc');$('#homefeddoc').val('feddoclist');getNumberDoc('ddlmf-homefedNumDoc','ddul');"
										onkeyup="filterli(this, 'ddlmf-homefedNumDoc');">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homefedNumDoc" data-filter="fedtrial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-purple">
										<img src="resources/assets/images/juicios-amparo.png" class="dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.civilproceedings' />"
											alt="<fmt:message key='text.civilproceedings' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.civilproceedings" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddlmf-homefed">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homecivil" data-filter="fedtrial"></ul></div>
							</div>

							<div class="flex filtertrialcard">
								<div class="col-xs-4" style="margin:auto 9px auto auto">
									<div class="frameicon fr-brown">
										<img src="resources/assets/images/juicios-amparo.png" class="dashboxclient asLink" style="float:right"
											title="<fmt:message key='text.searchby' /> <fmt:message key='text.criminalproceedings' />"
											alt="<fmt:message key='text.criminalproceedings' />">
									</div>
								</div>
								<div class="seach_liicon">
									<span class="ddlmf-subtitle" style="min-height:auto !important"><fmt:message key="text.criminalproceedings" /></span>
									<i class="fa fa-search"></i>
									<i class="material-icons arr_rg" data-close="ddlmf-homefed">keyboard_arrow_down</i>
									<input type="search" class="form-control input-sm ddlisearch">
								</div>
								<div class="ddlimenu"><ul id="ddlmf-homecriminal" data-filter="fedtrial"></ul></div>
							</div>
						</div>

						<div class="col-xs-12 col-sm-8" style="padding:0 5px 0 5px !important">
							<div class="col-xs-12 bg-white trailbox p-0" style="overflow:auto">
								<table class="table tablelist display responsive homeTableList" id="findFedDocList">
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

<div id="consulting-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog" style="max-height:90vh;min-height:360px;overflow:auto;background-color:#fff">
		<h4 class="custom-modal-title"><fmt:message key="text.consultations" />
			<span id="titleHomeCons" style="color:#00fa9a"></span>
		</h4>
		<button type="button" class="cleanFields inlineflex consCleanFields"
			data-reset="#consulting-modal" title="<fmt:message key='text.cleandata' />"
			style="width:40px;height:40px;top: 7px;-webkit-justify-content:center;justify-content:center;
				-webkit-align-items:center;align-items:center">
			<span class="glyphicon glyphicon-erase asLink inlineflex"
				style="-webkit-align-items:center;align-items:center"></span>
		</button>
		<button type="button" class="close consClose" data-dismiss="modal" aria-hidden="true">
			<span>&times;</span>
		</button>

		<div class="col-xs-12">
			<div style="display:none;" id="errorOnAddConsultation" class="alert alert-danger fade in">
				<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddConsultation').toggle();">&times;</a>
				<p id="putErrorOnAddConsultation"></p>
			</div>
		</div>

		<div data-groupcons="consultations" class="p-20 p-b-0">
			<div class="row" data-subgroupcons="consClientList">
				<div class="col-xm-12 col-sm-10">
					<div class="form-group inlineflex w100p">
						<label for="consClient" class="supLlb"><em>* </em><fmt:message key='text.client' /></label>
						<input type="text" class="form-control c39c" id="consClient" data-cons="clienthome"
							placeholder="<fmt:message key="text.select" />" autocomplete="off">
						<div class="containTL m-l-15">
							<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
								<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
							</button>
							<table class="table tablelist" id="consClientList"></table>
						</div>
					</div>
				</div>
				<div class="col-xs-11 col-sm-1 p-0">
					<button type="button" class="btn-blue-light trn2ms c39c" id="homeAddClientCons"
						value="<fmt:message key='button.newcustomer'/>" title="<fmt:message key='button.add.client'/>">
						<i class="material-icons">person_add</i>
					</button>
				</div>
				<div class="col-xs-1 col-sm-1 p-0">
					<button type="button" class="btn-blue-light trn2ms c39c" id="homeAddConsultation"
						value="<fmt:message key='button.newcustomer'/>" title="<fmt:message key='button.add.consultation'/>">
						<span style="font-weight:900;position:absolute;top:3px;left:8px;color:#fff">+</span>
						<i class="material-icons">folder_shared</i>
					</button>
				</div>
			</div>
			<div class="row" data-subgroupcons="consultationsList" style="min-height:160px">
				<div class="col-sm-12">
					<div class="form-group inlineflex w100p" style="overflow:auto">
						<table class="table tablelist" id="consultationList"></table>
					</div>
				</div>
			</div>
		</div>

		<div data-groupcons="newconsultations" style="max-height:85vh">
			<form method="post" id="formAddCons">
				<div class="form-group inlineblock p-20 p-t-0">
					<div class="row m-b-10">
						<div id="errorOnAddCons" class="alert alert-danger fade in">
							<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnAddCons').toggle();">&times;</a>
							<p id="putErrorOnAddCons"></p>
						</div>
					</div>
	<jsp:include page="/WEB-INF/views/consultas/consultasfornew.jsp" flush="true"/>
					<div class="row">
						<button type="button" onclick="return addConsultation('home');"
							class="btn btn-default waves-effect waves-light">
							<fmt:message key="button.save" />
						</button>
						<button type="button" id="consCancel" class="btn btn-danger waves-effect waves-light m-l-10">
							<fmt:message key="button.cancel" />
						</button>
					</div>
				</div>
			</form>
		</div>

		<div data-groupcons="detailsconsultations" style="padding-top:20px">
			<div class="form-group inlineblock p-20 p-t-0">
				<div class="row m-b-10">
					<div id="errorOnEditCons" class="alert alert-danger fade in">
						<a href="#" class="close" style="color:#000" aria-label="close" onclick="$('#errorOnEditCons').toggle();">&times;</a>
						<p id="putErrorOnEditCons"></p>
						<input type="hidden" id="editconsultId">
					</div>
				</div>
				<form id="formEditCons">
					<div class="form-group inlineflex w100p" data-consultation="editclient">
						<label for="editconsClientList" class="supLlb"><fmt:message key='text.client' /></label>
						<input type="text" class="form-control c39c" id="editconsClientList" data-cons="clienthome"
							placeholder="<fmt:message key="text.select" />" autocomplete="off">
						<div class="containTL  m-l-15">
							<button type="button" class="close closecontainTL" onclick="$('.containTL').hide();">
								<span>&times;</span><span class="sr-only"><fmt:message key="button.close" /></span>
							</button>
							<table class="table tablelist" id="editconsClient"></table>
						</div>
					</div>
<jsp:include page="/WEB-INF/views/consultas/consultasforedit.jsp" flush="true"/>
				</form>
				<div class="row">
					<button type="button" onclick="return updateConsultation('home');"
						class="btn btn-default waves-effect waves-light">
						<fmt:message key="button.save" />
					</button>
					<button type="button" id="consEditCancel" class="btn btn-danger waves-effect waves-light m-l-10">
						<fmt:message key="button.cancel" />
					</button>
				</div>
			</div>
		</div>

		<div class="modal-footer inlineflex w100p" data-groupcons="footer" style="position:relative;bottom:0">
			<div>
				<button type="button" onclick="forceclose('#consulting-modal');"
					class="btn btn-warning waves-effect waves-light m-l-10">
					<fmt:message key="button.close" />
				</button>
			</div>
			<div>
				<button type="button" id="prevClientCons" class="btn btn-primary waves-effect waves-light m-r-10">
					<fmt:message key="button.back" />
				</button>
			</div>
		</div>
	</div>

	<script src="resources/local/consultas/consultas.js"></script>
</div>

<div id="add-clients-modal" class="modal-demo" style="max-height:95vh !important;overflow:auto">
	<button type="button" class="close" onclick="$('#add-clients-modal').hide();$('.custombox-overlay, .modal-backdrop').hide();custombox.close();"
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
				data-dismiss="modal" aria-hidden="true" onclick="$('#add-clients-modal').hide();$('.custombox-overlay, .modal-backdrop').hide();custombox.close();">
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>

	<script src="resources/local/catalogs/clients.js"></script>

</div>

<div id="notify-detail-modal" class="modal fade" role="dialog" aria-labelledby="notify-detail-modal" aria-hidden="true" style="display:none">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title capitalize">
Detalles de cambios
				</h4>
				<input type="hidden" id="notifyshown">
			</div>
			<div class="modal-body p-0">
				<form id="formnotifydetail">
					<div class="row">
						<div class="col-sm-12">
							<table>
								<tbody>
									<tr>
										<td>
											<span class="mr10px">&Aacute;rea</span>
										</td>
										<td><span id="ntfyarea"></span></td>
									</tr>
									<tr>
										<td></td>
										<td><span id="ntfydoc"></span></td>
									</tr>
									<tr>
										<td>
											<span class="mr10px">Cliente</span>
										</td>
										<td><span id="ntfyclient"></span></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group inlineflex w100p">
								<table id="tablentfydetail" class="table table-striped table-bordered">
									<thead>
										<tr>
											<td><fmt:message key="text.information" /></td>
											<td><fmt:message key="text.newinformation" /></td>
											<td><fmt:message key="text.oldinformation" /></td>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
							<div class="col-sm-12" style="font-size:12px">
								<div>
									<span id="ntfyact"></span> - <span id="ntfydate"></span>
								</div>
								<div>
									<b><fmt:message key="text.madeby" />: </b><span id="ntfyfromuser"></span>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<input type="hidden" id="ntfyid_module"></input>
				<input type="hidden" id="ntfyrefid_module"></input>
				<button type="button" onclick="return notifyAsRead($('#ntfyid_module').val());"
					class="btn btn-default waves-effect waves-light"><fmt:message key="text.markasread" />
				</button>
				<input type="hidden" id="ntfyrefid_module"></input>
				<button type="button" onclick="return notifyAsRead($('#ntfyid_module').val(),$('#ntfyrefid_module').val(),0,null);"
					class="btn btn-default waves-effect waves-light"><fmt:message key="text.markasread" />
				</button>
				<button type="button" class="btn btn-danger waves-effect waves-light m-l-10" data-dismiss="modal">
					<fmt:message key="button.close" />
				</button>
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

<script src="resources/assets/js/jquery.min.js"></script>
<script src="resources/assets/plugins/bootstrap-datepicker-v1.10/js/bootstrap-datepicker.min.js"></script>

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
<script src="ruta/a/jquery.dataTables.js"></script>

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/plugins/dropzone/dropzone.js"></script>
<script src="resources/assets/js/customDropZone.js"></script>
<script src="resources/assets/js/complementos.js"></script>
<script src="resources/assets/js/home.js"></script>

<script>

function getMovementId(nid) {
	let objetivo=event.target.getAttribute("value")
	fetch(ctx + '/getNotifyDetail', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: 'id=' + nid
  })
    .then(response => response.json())
    .then(info => {
	let arrayId=(JSON.parse(info.actionsdetails));
  
	var objetoEncontrado =Object.values(arrayId).find(function(objeto){
 		return objeto.field === 'movimientoid';
	});
	localStorage.removeItem("movimiento");
	let movimientoid=null;
	if(objetoEncontrado!=null){
		movimientoid=objetoEncontrado.newdata;
		localStorage.setItem("movimiento", movimientoid);
	}
	console.log(objetoEncontrado);
	window.location.href = objetivo;
	})
    .catch(error => {
		console.log(error);
    });
  }
// Filtros segun su id del documento
function objetoFiltrado(datos) {
	let objetoFiltrado = {};
  Object.values(datos).forEach(objeto => {
    var key = objeto.idref; // Utilizar idref como clave en lugar de proceedings
    if (objetoFiltrado[key]) {
      objetoFiltrado[key].push(objeto);
    } else {
      objetoFiltrado[key] = [objeto];
    }
  });
  return objetoFiltrado;
}


function setMovement(movimiento){
	localStorage.setItem("movimiento", movimiento)
};


	polyfill('find');
	var table,dbntfy,seleccion, hideInfo, dispararEvento, clearTable,deleteRow, notifyAsRead, notificaciones, uploadNotify, aggEvento;
	jQuery(document).ready(function($){
		$('div.firmdatatabs').hide();
		$('div.firmdatatabs:eq(0)').show();
		
		$('#findClientList thead tr:eq(1)').css('display','none');
		$('#findClientList thead tr:eq(1) th').each( function (i) {
			var title = i18n('msg_filter_by') + ' ' + $(this).text();
			$(this).html( '<input type="text"  class="inputFilter" name="filterby" placeholder="' + title + '">' );
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
			responsive: true,
			paging:true,
			scrollCollapse:true,
			autoWidth:true,
			searching:true,
			orderCellsTop: true,
			pagingType:"simple_numbers",
			'dom':'<"col-sm-3"f><tr><"row"<"col-xs-12 col-sm-12 col-md-4 fs12"i><"col-xs-12 col-sm-6 col-md-3 xs-center-items"l><"col-xs-12 col-sm-6 col-md-5 xs-center-items"p>>',
				columnDefs: [
    { 'width': 'auto', 'targets': '_all' },
    {
      targets: '_all',
      createdCell: function(td, cellData, rowData, row, col) {
        $(td).css('white-space', 'normal');
      }
    }
  ]
		});
		$('#dashboard-notifications').DataTable({
  "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, i18n('msg_all')]],
  responsive: false,
  paging: true,
  scrollCollapse: true,
  autoWidth: true,
  searching: true,
  orderCellsTop: true,
  pageLength:-1,
  pagingType: "simple_numbers",
  'dom': '<"col-sm-3"f><tr><"row"<"col-xs-12 col-sm-12 col-md-4 fs12"i><"col-xs-12 col-sm-6 col-md-3 xs-center-items"l><"col-xs-12 col-sm-6 col-md-5 xs-center-items"p>>',
  columnDefs: [
    { 'width': 'auto', 'targets': '_all' },
    {
      targets: '_all',
      createdCell: function(td, cellData, rowData, row, col) {
        $(td).css('white-space', 'normal');
      }
    }
  ]
});

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

	deleteRow=(id,objetivo)=>{
		var table = $('#dashboard-notifications').DataTable();
		let value=objetivo.getAttribute("value");
		if(value!=null){
			let row = $(objetivo).closest('tr');
			let tbody=$("#"+id).find("tbody");
			row.remove();
			if(tbody.children().length==0){
				let parent= $("#parent"+id).closest('tr');
				let child=$("#"+id);
				table.row(child).remove();
				table.row(parent).remove();
			}
		}else{
			let row = $(objetivo).closest('tr');
			table.row(row).remove();
			let child=$("#"+id);
			table.row(child).remove();
			$('#dashtotnotif').html(' ('+($('#dashtotnotif').val())-1+')');
		}table.draw();
	}


	function drawNotifications(notifications) {
		let notifyArray = [];  
		Object.values(notifications).forEach(element => {
			let arrayVacio = [];
			let check = '<i class="fa-solid fa-check-to-slot" onclick="notifyAsRead('+element[0].notificationid+','+element[0].idref+')"></i>';
			let link=`<a  style="all:unset; color:#476E94; cursor:pointer;" id="parent`+element[0].idref+`" 
			href="`+element[0].link+`?language=es&rid=`+element[0].idref+`&nid=`+element[0].notificacionid+`"rel="noopener noreferrer" title="`+element[0].proceedings+`">`+element[0].proceedings+`</a>`
			arrayVacio.push(link);
		    arrayVacio.push(element[0].courtname == "" ? "Juzgado no asignado" : element[0].courtname);
		    arrayVacio.push(element[0].clientname);
		    arrayVacio.push(element[0].date);
		    arrayVacio.push('<i class="flechaDown fa-sharp fa-solid fa-chevron-down asLink" onclick="hideInfo('+element[0].idref+',setStyles())" style="color:#476E94; transform:rotate(0deg); font-size:2vh; padding:2vh 6vh 2vh 6vh; transition:all 0.7s;"></i>');
		    arrayVacio.push(check);
			arrayVacio.push(element[0].idref);
			notifyArray.push(arrayVacio);
		});
		let table = $('#dashboard-notifications').DataTable();
		notifyArray.forEach(element => {
			let rowData = element;
			let tabla='';
			let especific=notifications[element[6]];
			especific.forEach(elemento=>{
				tabla+=`<tr style="color:#000;"><td><a style="cursor:pointer;" onclick="getMovementId('`+elemento.notificationid+`')" 
				value="`+elemento.link+`?language=es&rid=`+elemento.idref+`&nid=`+elemento.notificacionid+`"rel="noopener noreferrer">`+elemento.description+`</a></td><td>`+elemento.modulename+`</td><td>`+elemento.date+`</td><td style="text-align:center;"><i style="color:#28567F; cursor:pointer;" class="fa-sharp fa-solid fa-circle-info" onclick="infonotify('`+elemento.notificationid+`')"></i></td><td><i class="fa-solid fa-check" onclick="notifyAsRead('`+elemento.notificationid+`')" value="0")"></i></td></tr>`
			})
			let childRowContent = '<table style="background-color:#aaa; color:#000;" class="table table-bordered"> <thead><tr style="color:#000;"> <td>Descripcion</td> <td>Movimiento</td> <td>Fecha</td> <td>Ver info</td> <td>Marcar como leida</td> </tr></thead> <tbody>'+tabla+'</tbody></table>';
			let rowIndex = table.row.add(rowData).draw().index();
			let row = table.row(rowIndex);
			let childRow=row.child(childRowContent).show();
			let siguienteElemento = $(childRow.node()).next();
			siguienteElemento.attr('id', element[6]);
			hideInfo(element[6]);
			table.order([[3, 'desc']]).draw();
		});
		$('#dashtotnotif').html(' ('+notifyArray.length+')');
	};

	 aggEvento=()=> {
		let hijos = document.querySelectorAll(".flechaDown");
	hijos.forEach(element => {
	  element.parentNode.addEventListener("click", function(event) {
	    try {
	      dispararEvento(event);
	    } catch (error) {
	      // * * * * *
	    }
	  });
	});
	};



	 notifyAsRead=(id,idref)=>{
		let objetivo=event.target
		id==id||$('#notifyshown').val();
		swal({
			title:i18n('msg_are_you_sure'),
			text:i18n('msg_notificatios_as_read'),
			type:"warning",
			showCancelButton:true,
			confirmButtonClass:'btn-warning',
			confirmButtonText:i18n('btn_yes_remove_it'),
			closeOnConfirm:false,
			closeOnCancel:false
		}).then((isConfirm) => {
	        if (isConfirm) {
				$.ajax({
					type:'POST',
					url:ctx+'/notifyAsRead',
					data:'id='+id,
					async:false,
					success:function(data){
						if(data)
							location.href = location.href.replace(/^(.*)\#.*$/, '$1');
						else
							swal(i18n('msg_warning'),i18n('err_notification_fail')+e,'error');
					},error:function(e){
						swal(i18n('msg_warning'),i18n('err_notification_fail')+e,'error');
					}
				});
	        }
		});
	};
	function compararClaves(objeto1, objeto2) {
	  var clavesNoComunes = {};

	  for (var clave in objeto2) {
	    if (!objeto1.hasOwnProperty(clave)) {
	      clavesNoComunes[clave] = objeto2[clave];
	    }
	  }

	  return clavesNoComunes;
	};

	function cambiarKeys(objetoDeObjetos, propiedadClave) {
	  const objetoTransformado = {};

	  for (const clave in objetoDeObjetos) {
	    if (objetoDeObjetos.hasOwnProperty(clave)) {
	      const objeto = objetoDeObjetos[clave];
	      const nuevoNombre = objeto[propiedadClave];

	      objetoTransformado[nuevoNombre] = objeto;
	    }
	  }

	  return objetoTransformado;
	};
	function filtrarNuevasNotificaciones(objeto, array) {
	  const nuevoObjeto = {};

	  array.forEach((clave) => {
	    if (objeto.hasOwnProperty(clave)) {
	      nuevoObjeto[clave] = objeto[clave];
	    }
	  });

	  return nuevoObjeto;
	};


	uploadNotify=()=>{
		let table = $('#dashboard-notifications').DataTable();
		let newNotify;
		let nuevasNotificaciones;
		fetch(ctx + "/getHomeNotifications", {
	      headers: {
	          "Content-Type": "application/json;charset=UTF-8"
	      }
	  })
	  .then(response => response.json())
	  .then(data => {
		newNotify=cambiarKeys(data,"notificationid");
		let notify=cambiarKeys(notificaciones, "notificationid");
		nuevasNotificaciones=compararClaves(notify, newNotify);
		if(Object.keys(nuevasNotificaciones).length!=0){
			notificaciones=newNotify;
			let actualizacion=(cambiarKeys(nuevasNotificaciones, "idref"));
			let notifyActual=objetoFiltrado(newNotify);
			let upNotify=filtrarNuevasNotificaciones(notifyActual, Object.keys(actualizacion));
			Object.keys(upNotify).forEach(element=>{console.log(element)});
			changeListLength(-1);
			Object.keys(upNotify).forEach(element=>{
				let fila=document.getElementById("parent"+element).parentNode.parentNode;
				if(fila){
					fila.setAttribute("id","row"+element);
					table.row('#row'+ element).remove().draw();
				}
			});
			drawNotifications(upNotify);
			allColor();
			aggEvento();
			changeListLength(10); 
			table.order([[3, 'desc']]).draw();
			$('#dashtotnotif').html(' ('+(Object.keys(nuevasNotificaciones).length)+')');
		}
	}).catch(error => console.error(error));
	}

	setInterval(function() {
	  uploadNotify;
	}, 10000);


	clearTable = () => {
	  let table = $('#dashboard-notifications').DataTable();
	  table.rows().remove();	// Eliminar todas las filas
	  table.draw();				// Volver a renderizar la DataTable
	};

	let rowChild;
	 hideInfo=(childRowId,event)=> {
	  let childRow = $('#' + childRowId);
	  if (childRow.is(':visible')) {
		childRow.hide();
	  } else {
		childRow.show();
	  }
	}

	 dispararEvento=()=>{
	  // Obtenemos el elemento objetivo (target) del evento
	  var elementoObjetivo = event.target;

	  // Obtenemos el primer hijo del elemento objetivo
	  var primerHijo = elementoObjetivo.firstElementChild;

	  // Creamos un nuevo evento de tipo 'click'
	  var eventoClick = new MouseEvent('click', {
	    'view': window,
	    'bubbles': true,
	    'cancelable': true
	  });

	  // Disparamos el evento click en el primer hijo
	  primerHijo.dispatchEvent(eventoClick);
	}

	function getNotify() {
	  fetch(ctx + "/getHomeNotifications", {
	      headers: {
	          "Content-Type": "application/json;charset=UTF-8"
	      }
	  })
	  .then(response => response.json())
	  .then(data => {
		console.log(data);
		notificaciones=data;
		console.log(data);
		let notifications=objetoFiltrado(data);
		drawNotifications(notifications);
		document.getElementById("background-contain").remove();
		allColor();
		aggEvento();
		changeListLength(10);
	})
	  .catch(error => console.error(error));
	};
	getNotify()
	});
	function changeListLength(numero){
		var input= document.getElementsByName("dashboard-notifications_length")[0];
		input.value=numero;
		var eventDispatcher=new Event("change");
		input.dispatchEvent(eventDispatcher);
	};

	function setStyles(){
		if(event.target.style.transform=='rotate(0deg)')
			event.target.style.transform='rotate(180deg)'
		else
			event.target.style.transform='rotate(0deg)'
	};

	function changeColor(fila, color){
		let filas=document.querySelectorAll(fila);
		filas.forEach(element=>{element.setAttribute("style","background-color: "+color+"!important;"); })
	};
	function allColor(){
		changeColor(".odd", '#fff');
		changeColor('.even','#EBEFF2');
	};

	window.addEventListener("load", allColor)
		function cleanTableFilters(e){   
	   	 	$('[name=filterby]').val('');
	   	 	table.search('').columns().search('').draw();
	   	 	$(e).addClass('btn-secondary').removeClass('btn-warning');
		};
	function togglemodtab(e,tab){
		if($(e).hasClass('selectedtab'))return;
		$('.tabsmodal li').removeClass('selectedtab');
		if(typeof e=='string')
			e=$(e+' li:first-child');
		$(e).addClass('selectedtab');
		$(tab).show();
	};
</script>
<script>
	$('#addClient1').on('click', function(e){
		var tab=$('#clientmodaltabs li:first-child');
		$('.tabsmodal li').removeClass('selectedtab');
		$(tab).addClass('selectedtab');
		$('#add-clients-modal div.firmdatatabs').hide();
		$('#add-clients-modal div.firmdatatabs:eq(0)').show();
		getCountries('clieCountry', 'ul');
		$('#add-clients-modal').modal('show').show();
	});
</script>
</body>
</html>