polyfill("classList,toLocaleDateString");

function togglemodtab(e,tab){
	if($(e).hasClass('selectedtab'))return;
	$('.tabsmodal li').removeClass('selectedtab');
	if(typeof e=='string')
		e=$(e+' li:first-child');
	$(e).addClass('selectedtab');
	$(tab).show();
};
function loadCalendar($, year=new Date().getFullYear(), month=null){

	var jsonObj=[];
	//var currentDate = new Date();
	//var year = currentDate.getFullYear();
	$.ajax({
		type:'POST',
		dataType:'json',
		contentType:'text/html',
		url: "loadSchedules?year=" + year,
		async:false,
		success:function(data){
			var cal=data.calDates;
			for(var c in cal){
				if(cal[c][2]==null)continue;
				var usrEv={},min,link='',origin='',oid=0,clss='';
				usrEv["id"]=cal[c][0];
				usrEv["title"]=(!(cal[c][1])?cal[c][1]:cal[c][8]);
				usrEv["start"]=cal[c][2];
				usrEv["end"]=cal[c][3];
				usrEv["startdate"]=formatDateTime(cal[c][2],null,"FFFF");
				usrEv["starttime"]=formatDateTime(cal[c][2],null,'HH:mm');
				usrEv["enddate"]=formatDateTime(cal[c][3],null,"FFFF");
				usrEv["endtime"]=formatDateTime(cal[c][3],null,'HH:mm');
				usrEv["activity"]=(cal[c][1]).replace(/(.*) \- (.*)/,'$2');
				usrEv["description"]=cal[c][4]||'';
				usrEv["action"]=i18n(cal[c][5]==1?'writtendocument':cal[c].action==2
					?'officialletter':cal[c].action==3?'Accord':'Judgment');
				usrEv["status"]=cal[c][6];
				usrEv["movid"]=cal[c][7];
				usrEv["movement"]=cal[c][8];
				usrEv["movobservations"]=cal[c][12];
				usrEv["filingdate"]=i18n(cal[c][9]);
				usrEv["agreementdate"]=i18n(cal[c][10]);
				usrEv["notificationdate"]=i18n(cal[c][11]);
				usrEv["notebooktype"]=i18n('msg_'+cal[c][13]);
				if(/^[0-9]+$/.test(cal[c][14])){
					oid=14;
					doc=19;
					link='trials';
					origin='trial';
					clss='fc-trial';
				}else if(/^[0-9]+$/.test(cal[c][15])){
					oid=15;
					doc=20;
					let m='',l='';
					clss='fc-prot';
					cal[c][23]==2&&(m='in',l='ind',clss='fc-ind');
					origin=m+'direct_protection';
					link=l+'protection';
				}else if(/^[0-9]+$/.test(cal[c][16])){
					oid=16;
					doc=21;
					link='appeals';
					origin='appeal';
					clss='fc-apl';
				}else if(/^[0-9]+$/.test(cal[c][17])){
					oid=17;
					doc=22;
					link='resource';
					origin=link;
					clss='fc-rsc';
				}
				usrEv["link"]=link+'dashboard';
				usrEv["originid"]=cal[c][oid];
				usrEv["document"]=cal[c][doc];
				usrEv["origin"]=i18n('msg_'+origin);
				
				usrEv["acttype"]=cal[c][18];
				usrEv["client"]=cal[c][24];
				usrEv["className"]=clss;
				usrEv["movpath"]=cal[c][25];
				usrEv["movfile"]=cal[c][26];
				jsonObj.push(usrEv);
			}
		},error : function(e) {
			swal(i18n("msg_error"), i18n('err_unable_get_calendar'),"error");
		}
	});

	$('#calendar').fullCalendar({
		header: {
			left: 'prev,today,next',
			center: 'prevYear , title , nextYear',
			right: 'month,agendaWeek,agendaDay,listYear'
		},
		
		customButtons:{
			prevYear: {
				text: '-', // Inserta el ícono directamente en el texto
				click: function () {
					// Obtener el año actual
					var yearCurrent = $('#calendar').fullCalendar('getDate').year();
					var month = $('#calendar').fullCalendar('getDate').format('MM');
					// Cambiar al siguiente año
					var newYear = yearCurrent - 1;
					//$('#calendar').fullCalendar('refetchEvents');

					//$('#calendar').fullCalendar('gotoDate', newYear +'-'+month +'-01');

					$('#calendar').fullCalendar("destroy")
					loadCalendar($, newYear, month)
				}
			}
			,
			nextYear: {
				text: '+',
				click: function () {
					// Obtener el año actual
					var yearCurrent = $('#calendar').fullCalendar('getDate').year();
					var month = $('#calendar').fullCalendar('getDate').format('MM');
					var newYear = yearCurrent + 1;
					// Lógica para cargar el nuevo año
					$('#calendar').fullCalendar("destroy")
					loadCalendar($, newYear, month)
				}
			}
		
		},
		businessHours: {
			dow: [ 1, 2, 3, 4, 5 ],
			start: '9:00',
			end: '19:00'
		},
		defaultDate:year==new Date().getFullYear()?new Date():new Date(year,month-1, 1),
		displayEventTime:false,
		editable:false,
		eventClick: function(calEvent){
			var d1=new Date(calEvent.start),d2=new Date(calEvent.end);
			var t1=d1.getHours(),t2=d2.getHours();
			$('#event-activity').html(calEvent.activity);
			$('#event-startdate').html(calEvent.startdate);
			$('#event-starttime').html(calEvent.starttime);
			$('#event-enddate').html(calEvent.enddate);
			$('#event-endtime').html(calEvent.endtime);

			$('#event-title').html(calEvent.title);
			$('#event-action').html(calEvent.acttype);
			$('#event-notebooktype').html(calEvent.notebooktype);
			$('#event-description').html(calEvent.description);
			$('#event-filingdate').html(calEvent.filingdate);
			$('#event-agreementdate').html(calEvent.agreementdate);
			$('#event-movobservations').html(calEvent.movobservations);

			$('#event-client').html(calEvent.client);
			$('#event-origin').html(calEvent.origin);
			$('#event-link').attr('href','#');
			$('#event-link').attr('onClick','localStorage.setItem("movimiento","'
				+calEvent.movid+'");'+'location.href="'+ctx+'/'+calEvent.link
				+'?rid='+calEvent.originid+'&language='+getLanguageURL()+'"'
			);
			if(calEvent.movfile==null||calEvent.movfile=='')
				$('#mov-docs').html('<span>'+i18n('msg_no_data')+'</span>');
			else
				$('#mov-docs').html('<a class="asLink" href="'+calEvent.movpath
					+'" target="_blank" rel="noopener noreferrer" title="'+i18n('msg_opendocument')
					+'">'+calEvent.movfile+' <i class="fa fa-external-link"></i></a>');
			$('#event-document').html(calEvent.document);
            jQuery('#event-mov-modal').modal();
		},
		eventLimit: true,
		eventLimitClick: 'popover',
		events:jsonObj,
		firstDay: 1,
		height: 'auto',
//		hiddenDays: [0, 6],
		locale:getLanguageURL(),
		navLinks: true,
		scrollTime: '09:00:00',
		showNonCurrentDates: true,
		titleFormat: 'MMMM YYYY',
		weekNumbers: true,
		weekNumberTitle:i18n('msg_week'),
		views:{
			month:{titleFormat:'MMMM YYYY'},
			agendaDay:{
				titleFormat:(getFormatPatternDate()
					.replace(/\//g,' '))
					.replace(/d+/g,'DD')
					.replace(/M+/g,'MMMM')
					.replace(/y+/g,'YYYY')
			}
	    }
	});
}


jQuery(document).ready(function($){
	loadCalendar($)
});


window.onmousemove=function (e){
	var tooltip=document.getElementById('tipsubject');
	tooltip.style.display="none";
	if(!/fc-title/g.test(e.target.classList[0]))return;
	tooltip.style.display="block";
	var t=e.target.innerText,x=(e.pageX+20)+'px',y=(e.pageY+20)+'px';
	tooltip.innerText=t;
	tooltip.style.top=y;
	tooltip.style.left=x;
};









