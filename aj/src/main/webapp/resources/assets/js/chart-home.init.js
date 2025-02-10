var data_plot;
var etiqueta_plot;
	

	$("#idyear").datepicker( {
	    format: " yyyy", // Notice the Extra space at the beginning
	    minViewMode: 2,
	    onSelect: function(dateText) {
	    	$(this).change();
	        console.log("Selected date: " + dateText + "; input's current value: " + this.value);
	        //refreshDashboard(dateText);
	    }
	}).on("change", function() {
		refreshDashboard($('#idyear').val());
	});
	$( '#idyear' ).datepicker( 'setDate', new Date() );
	refreshDashboard($('#idyear').val());
	
	
function refreshDashboard(year){
	$.ajax({
		type:"POST",
		url:"getDashboard",
		data: {"year":year},
		async:false,
		success:function(data){
			var set=data.dashOppMnt;
			var num=set+'';
			var x=num.split('.');
			var x1=x[0];
			var x2=x.length>1 ?'.'+x[1]:'';
			var rgx=/(\d+)(\d{3})/;
			while(rgx.test(x1)){
				x1=x1.replace(rgx,'$1'+','+'$2');
			}$('#dashOppMnt').text(x1+x2);

			$('#dashOppQtyOpen').html(data.dashOppQtyOpen);
			$('#dashOppNumOpen').html(data.dashOppNumOpen);
			$('#dashOppQtyLost').html(data.dashOppQtyLost);
			$('#dashOppNumLost').html(data.dashOppNumLost);
			$('#dashOppQtyWon').html(data.dashOppQtyWon);
			$('#dashOppNumWon').html(data.dashOppNumWon);

			$('#dashQuotQtyOpen').html(data.dashQuotQtyOpen);
			$('#dashQuotNumOpen').html(data.dashQuotNumOpen);
			$('#dashQuotQtyLost').html(data.dashQuotQtyLost);
			$('#dashQuotNumLost').html(data.dashQuotNumLost);
			$('#dashQuotQtyWon').html(data.dashQuotQtyWon);
			$('#dashQuotNumWon').html(data.dashQuotNumWon);
			$('#currencyuser').html(data.moneda);
			
			
			data_plot = JSON.parse(data.grafica);
			//console.log(data.etiquetas);
			etiqueta_plot = JSON.parse(data.etiquetas);
			
		}
	});
}	
	
!function(o) {
	"use strict";
	var t = function() {
		this.$body = o("body")
	};
	t.prototype.createCombineGraph = function(t, a, i) {
		var data = [ {
			data : i[0],
			lines : {show : true}
		}, {
			data : i[1],
			lines : {show : true}
		}], vgrafica = {
			series : {
				shadowSize : 0
			},
			grid : {
				hoverable : !0,
				clickable : !0,
				tickColor : "#f9f9f9",
				borderWidth : 1,
				borderColor : "#eeeeee"
			},
			colors : [ "#4a81d4", "#1abc9c" ],
			tooltip : !0,
			tooltipOpts : {
				defaultTheme : !1
			},
			legend : {
				position : "ne",
				margin : [ 0, -32 ],
				noColumns : 0,
				labelBoxBorderColor : null,
				labelFormatter : function(o, t) {
					return o + "&nbsp;&nbsp;"
				},
				width : 30,
				height : 2
			},
			yaxis : {
				axisLabel : "Opportunity",
				tickColor : "#f5f5f5",
				font : {
					color : "#bdbdbd"
				}
			},
			xaxis : {
				axisLabel : "Stages",
				ticks : a,
				tickColor : "#f5f5f5",
				font : {
					color : "#bdbdbd"
				}
			}
		};
		o.plot(o(t), data, vgrafica)
	}, t.prototype.init = function() {
		this.createCombineGraph("#sales-analytics", etiqueta_plot, data_plot)
	}, o.Dashboard1 = new t, o.Dashboard1.Constructor = t
}(window.jQuery), function(o) {
	"use strict";
	o.Dashboard1.init()
}(window.jQuery);

function addCommas(nStr){
	nStr+='';
	var rgx = /(\d+)(\d{3})/,x=nStr.split('.');
	var x1=x[0];
	var x2=x.length>1?'.'+x[1]:'';
	while(rgx.test(x1))
		x1=x1.replace(rgx,'$1'+','+'$2');
	return x1+x2;
}