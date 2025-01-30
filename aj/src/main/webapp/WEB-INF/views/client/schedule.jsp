<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="aPermiso" scope="page" value="${menu.menuid}_1" />
<c:set var="ePermiso" scope="page" value="${menu.menuid}_2" />
<c:set var="dPermiso" scope="page" value="${menu.menuid}_3" />
<c:set var="rPermiso" scope="page" value="${menu.menuid}_4" />

<link rel="stylesheet" type="text/css" href="resources/assets/plugins/fullcalendar/fullcalendar.min.css">
<link rel="stylesheet" type="text/css" media="print" href="resources/assets/plugins/fullcalendar/fullcalendar.print.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/plugins/sweet-alert2/sweetalert2.min.css">
<link rel="stylesheet" type="text/css" href="resources/assets/css/ecalendar.css">

<style>
	.sweet-overlay{z-index:10010 !important}
	.showSweetAlert{-webkit-box-shadow:0 0 15px #444;box-shadow:0 0 15px #444;z-index:10015 !important}
</style>

<div class="content-page m-0">
	<div class="container">
		<div id='homecalendar' style="max-width:1200px;min-height:350px;margin:0 auto;background-color:#fff"></div>
	</div>
</div>

<div id="event-data-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="event-data-modal" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" title="<fmt:message key='button.close' />">&times;</button>
				<h4 class="modal-title"><fmt:message key="text.movement" /></h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-6 col-sm-6 col-md-6">
						<div class="form-group fieldinfo inlineblock" style="width:100%">
							<label for="userEventAction" class="supLlb"><fmt:message key='text.actiontype' /></label>
							<span class="form-control c39c" id="userEventAction"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12">
						<div class="form-group fieldinfo inlineblock" style="width:100%">
							<label for="userEventMovement" class="supLlb"><fmt:message key='text.movement' /></label>
							<span class="form-control c39c" id="userEventMovement"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12">
						<div class="form-group fieldinfo inlineblock" style="width:100%">
							<label for="userEventDescr" class="supLlb"><fmt:message key='text.obserservations' /></label>
							<textarea class="form-control c39c txArea4r" id="userEventDescr" style="resize:none;background-color:#fff" readonly></textarea>
						</div>
					</div>
				</div>
				<div class="form-group" style="background-color:#F4F9FF">
					<div class="row"><div class="col-xs-12 col-sm-12 col-md-12"><h3><fmt:message key='text.activity' /></h3></div></div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="form-group fieldinfo inlineblock" style="width:100%">
								<label for="userEventUseractivity" class="supLlb"><fmt:message key='text.activity' /></label>
								<span class="form-control c39c" id="userEventUseractivity"></span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-3 col-sm-3 col-md-3" style="padding-left:10%">
							<label for="userEventDateStart"><fmt:message key='text.start' /></label>
						</div>
						<div class="col-xs-5 col-sm-5 col-md-5">
							<div class="form-group fieldinfo inlineblock" style="width:100%">
								<label for="userEventDateStart" class="supLlb"><fmt:message key='text.date' /></label>
								<span class="form-control c39c" id="userEventDateStart"></span>
							</div>
						</div>
						<div class="col-xs-4 col-sm-4 col-md-4">
							<div class="form-group fieldinfo inlineblock" style="width:100%">
								<label for="userEventStartTime" class="supLlb"><fmt:message key='text.time' /></label>
								<span class="form-control c39c" id="userEventStartTime"></span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-3 col-sm-3 col-md-3" style="padding-left:10%">
							<label for="userEventDateEnd"><fmt:message key='text.start' /></label>
						</div>
						<div class="col-xs-5 col-sm-5 col-md-5">
							<div class="form-group fieldinfo inlineblock" style="width:100%">
								<label for="userEventDateEnd" class="supLlb"><fmt:message key='text.date' /></label>
								<span class="form-control c39c" id="userEventDateEnd"></span>
							</div>
						</div>
						
						<div class="col-xs-4 col-sm-4 col-md-4">
							<div class="form-group fieldinfo inlineblock" style="width:100%">
								<label for="userEventEndTime" class="supLlb"><fmt:message key='text.time' /></label>
								<span class="form-control c39c" id="userEventEndTime"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modals -->
<div id="movement-modal" class="sub-modal" style="display:none">
	<div class="modal-dialog">
		<button type="button" class="close" onclick="$('#movement-modal').hide();" style="margin:10px;color:#fff">
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
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group inlineflex w100p">
									<label for="descriptionFSel" class="supLlb"><span class="titleFromFSel" style="text-transform:capitalize"></span></label>
									<input type="text" class="form-control c39c" id="descriptionFSel" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="row dnone" id="tocitycourt">
							<div class="col-xs-12">
								<label for="citycourt" class="supLlb"><fmt:message key='text.city' /></label>
								<div class="form-group inlineflex w100p">
									<input type="text" class="form-control listfiltersel" placeholder="<fmt:message key="text.select" />" >
									<i class="material-icons iconlistfilter">arrow_drop_down</i>
									<ul class="ddListImg noimgOnList" id="citycourt"></ul>
								</div>
							</div>
						</div>
					</div>
				</form>
				<button type="button" onclick="return addFromFilterSel('ed');" class="btn btn-default waves-effect waves-light">
					<fmt:message key="button.save" />
				</button>
				<button type="button" onclick="validDDFilter();$('#movement-modal').modal('hide');" class="btn btn-danger waves-effect waves-light m-l-10">
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>

<script src="resources/assets/js/jquery.min.js"></script>
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

<script src="resources/assets/plugins/fullcalendar/lib/jquery.min.js"></script>
<script src='resources/assets/plugins/fullcalendar/lib/moment.min.js'></script>
<script src="resources/assets/plugins/fullcalendar/lib/jquery-ui.min.js"></script>
<script src='resources/assets/plugins/fullcalendar/fullcalendar.min.js'></script>
<script src='resources/assets/plugins/fullcalendar/locale-all.js'></script>

<!-- rrule lib 
<script src='https://cdn.jsdelivr.net/npm/rrule@2.6.4/dist/es5/rrule.min.js'></script>
-->
<script src='resources/assets/js/complementos.js'></script>
<script src="resources/assets/js/i18n_AJ.js"></script>

<script>
	$(document).ready(function() {
		$.noConflict(false);
		polyfill("classList,toLocaleDateString");
		$('#homecalendar').fullCalendar({
			header: {
				left:'',
				center: 'title',
				right:''
			},
			height: 500,
			titleFormat: 'MMMM YYYY',
			defaultDate: new Date(),
			locale:getLanguageURL(),
			defaultView: 'listYear',
			firstDay: 0,
			weekNumbers: true,
			displayEventTime: true,
			navLinks: true,
			eventLimit: true,
			showNonCurrentDates: true,
			eventLimitClick: 'popover',
			scrollTime: '09:00:00',
			views: {
		        agendaDay: {
		            titleFormat: 'DD MMMM YYYY'
		        },
		        month: {
		            titleFormat: 'MMMM YYYY'
		        }
		    },
		    footer: {
				left: 'prev today next',
		    	center: '',
		    	right: 'listYear,agendaDay'
			},
			eventColor: '#378006',
			events: [
				{
					title: 'Delta',
					start: '2023-01-05T08:00:00',
					end: '2023-01-05T10:00:00',
					displayOrder: 2,
				},
				{
					title: 'Alpha',
					start: '2023-01-05T10:30:00',
					end: '2023-01-05T12:30:00',
					displayOrder: 3,
				},
				{
					title: 'Bravo',
					start: '2023-01-05T13:00:00',
					end: '2023-01-05T15:00:00',
					displayOrder: 1,
				},
				{
					title: 'Charlie',
					start: '2023-01-05T17:00:00',
					end: '2023-01-05T19:00:00',
					displayOrder: 4,
				},
				{
					title: 'Seguimiento de copias',
					start: '2023-02-15T10:30:00',
					end: '2023-02-17T12:30:00',
					color: 'red',
					eventColor:'purple'
				}, 		        {
					title: 'Test',
					color: 'orange',
					start: '20230327',
					end: '20230327'
				}, 		        
				{
					title: 'Test2',
					color: 'violet',
					start: '20230207',
					end: '20230208'
				}, 		        {
					title: 'Test3',
					color: 'cyan',
					start: '20230130',
					end: '20230131'
				}
			]
		})
	});

/*	function formatTimeStamp(usrDate,fmt){//fmt:Retornar formato, "d"=fecha, "t"=hora, "ts"=hora con segundos
		var eDate=new Date(usrDate),res;
		var h=eDate.getHours(),n="0"+eDate.getMinutes(),s="0"+eDate.getSeconds(),
			y=eDate.getFullYear(),m="0"+(eDate.getMonth()+1),d="0"+eDate.getDate();
		if(fmt.lowerCase="t")
			res=h.substr(-2)+':'+n.substr(-2);
		else if(fmt.lowerCase="ts")
			res=h.substr(-2)+':'+n.substr(-2)+':'+d.substr(-2);
		else
			res=y+'-'+m.substr(-2)+'-'+d.substr(-2);
		return res;
	};

	function fncLoadEvents (){
		$('#homecalendar').fullCalendar({
			header: {
				left: 'prev today next',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			height: 500,
			titleFormat: 'MMMM YYYY',
			defaultDate: new Date(),
			hiddenDays: [0, 6],
			firstDay: 0,
			weekNumbers: true,
			weekNumberTitle: 'Sem.',
			displayEventTime: false,
			navLinks: true,
			eventLimit: true,
			showNonCurrentDates: true,
			eventLimitClick: 'popover',
			scrollTime: '09:00:00',
			businessHours: {
				dow: [ 1, 2, 3, 4, 5 ],
				start: '9:00',
				end: '19:00'
			},
			views: {
		        agendaDay: {
		            titleFormat: 'DD MMMM YYYY'
		        }
		    },
			eventColor: '#378006'
		});
	};*/
</script>