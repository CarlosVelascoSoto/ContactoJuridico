<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<title><fmt:message key="text.schedule"/> - Contacto jur&iacute;dico</title>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<link rel="stylesheet" href="resources/assets/plugins/custombox/css/custombox.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/fullcalendar/fullcalendar.min.css">
<link rel="stylesheet" type="text/css" media="print" href="resources/assets/plugins/fullcalendar/fullcalendar.print.min.css">
<!---link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css"-->
<link rel="stylesheet" type="text/css" href="resources/local/general/profile/ecalendar.css">

<div class="content-page">
	<div class="content">
		<div class="container">
			<!--div class="row">
				<div class="col-sm-12">
					<h4 class="page-title"><fmt:message key="text.schedule" /></h4>
				</div>
			</div-->
			<div id="calendar"></div>
		</div>
	</div>
</div>

<div id="event-mov-modal" class="modal-demo modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="event-title"></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			<div class="row m-l-5">
				<span><fmt:message key="text.client" />:&nbsp;
					<span class="c39c" id="event-client"></span>
				</span>
			</div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <div class="row container-tabsmodal">
						<ul id="eventmodaltabs" class="tabsmodal">
							<li class="selectedtab trn2ms" onclick="togglemodtab(this,'#tab-activity');"><fmt:message key="text.activity" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#tab-movement');"><fmt:message key="text.movement" /></li>
							<li class="trn2ms" onclick="togglemodtab(this,'#tab-documents');"><fmt:message key="text.documents" /></li>
						</ul>
					</div>

					<div id="tab-activity" class="firmdatatabs p-20">
						<div class="row">
							<table class="w100p">
							<tbody>
								<tr>
									<td><span><fmt:message key="text.activity" /></span></td>
									<td><span class="c39c" id="event-activity"></span></td>
								</tr>
								<tr>
									<td><span><fmt:message key="text.startdate" /></span></td>
									<td><span class="c39c" id="event-startdate"></span></td>
								</tr>
								<tr>
									<td><span><fmt:message key="text.starttime" /></span></td>
									<td><span class="c39c" id="event-starttime"></span></td>
								</tr>
								<tr>
									<td><span><fmt:message key="text.enddate" /></span></td>
									<td><span class="c39c" id="event-enddate"></span></td>
								</tr>
								<tr>
									<td><span><fmt:message key="text.endtime" /></span></td>
									<td><span class="c39c" id="event-endtime"></span></td>
								</tr>
							</tbody>
							</table>
						</div>
					</div>
					<div id="tab-movement" class="firmdatatabs p-20">
						<div class="row">
							<table class="w100p">
							<tbody>
								<tr>
									<td><span><fmt:message key="text.action" /></span></td>
									<td><span class="c39c" id="event-action"></span></td>
								</tr>
								<tr>
									<td><span><fmt:message key="text.notebooktype" /></span></td>
									<td><span class="c39c" id="event-notebooktype"></span></td>
								</tr>
								<tr>
									<td><span><fmt:message key="text.description" /></span></td>
									<td><span class="c39c" id="event-description"></span></td>
								</tr>
								<tr>
									<td><span><fmt:message key="text.filingdate" /></span></td>
									<td><span class="c39c" id="event-filingdate"></span></td>
								</tr>
								<tr>
									<td><span><fmt:message key="text.agreementdate" /></span></td>
									<td><span class="c39c" id="event-agreementdate"></span></td>
								</tr>
								<tr>
									<td><span><fmt:message key="text.obserservations" /></span></td>
									<td><span class="c39c" id="event-movobservations"></span></td>
								</tr>
							</tbody>
							</table>
						</div>
	 				</div>

	 				<div id="tab-documents" class="firmdatatabs p-20">
						<div class="row" id="mov-docs">
							<div class="inlineflex">
								
							</div>
						</div>
	 				</div>
	 				<div class="row m-l-5">
						<span class="c39c" id="event-origin"></span>&nbsp;
						<a class="asLink" id="event-link" rel="noopener noreferrer">
							<span class="c39c" id="event-document"></span>&nbsp;
							<i class="fa fa-external-link"></i>
						</a>
					</div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<span id="tipsubject">...</span>

<!--script src="resources/assets/js/jquery.min.js"></script-->
<script src="resources/assets/plugins/fullcalendar/lib/jquery.min.js"></script>
<script src='resources/assets/plugins/fullcalendar/lib/moment.min.js'></script>
<script src="resources/assets/plugins/fullcalendar/lib/jquery-ui.min.js"></script>
<script src='resources/assets/plugins/fullcalendar/fullcalendar.min.js'></script>
<script src='resources/assets/plugins/fullcalendar/locale-all.js'></script>
<!--script src="resources/assets/plugins/sweet-alert2/sweetalert2.min.js"></script-->

<!-- Modal-Effect -->
<script src="resources/assets/plugins/custombox/js/custombox.min.js"></script>
<script src="resources/assets/plugins/custombox/js/legacy.min.js"></script>

<script src="resources/assets/js/i18n_AJ.js"></script>
<script src="resources/assets/js/complementos.js"></script>
<script src='resources/local/general/profile/ecalendar.js'></script>