function getListInvoiceDue(id){
	var param='id='+id;
	$('#tableInvoice').remove();
	$.ajax({
		type:'POST',
		url:ctx+"/getInvoiceDue",
		data:param,
		async:false,
		success:function(data){
			if(data!=null){
				$("#clientInvoiceErr").css('display','none');
				//Construcción de tabla incidencias y encabezados
				var finalTable="<table class='tableIn' id='tableInvoice'><tr>"+
					"<th class='fwb'>"+i18n('msg_invoice')+"</th>"+
					"<th class='fwb'>"+i18n('msg_client')+"</th>"+
					"<th class='fwb'>"+i18n('msg_email')+"</th>"+
					"<th class='fwb'>"+i18n('msg_expiration_date')+"</th>"+
					"<th class='fwb'>"+i18n('msg_balance')+"</th>"+
					"<th class='fwb'>"+i18n('msg_currency')+"</th>"+
					"</tr>"
				//Insertar insidencias
				for(var r in data){
					var fecha=dateToShow(new Date(data[r].vencimiento)),
						email=(data[r].email==null)?" class='tac'><i style='font-size:12px;'>"+i18n('msg_no_data')+"</i>":"\u003E"+data[r].email;
					finalTable+="<tr>"+ //Detalle
					"<td class='tac'>"+data[r].factura+"</td>"+
					"<td>"+data[r].cliente+"</td>"+
					"<td"+email+"</td>"+
					"<td class='tac'>"+fecha+"</td>"+
					"<td class='tar'>"+data[r].saldo+"</td>"+
					"<td class='tac'>"+data[r].moneda+"</td></tr>";
				}
				//Inserta resultados en la tabla
				finalTable+="</table>";
				$(finalTable).insertAfter("#invList");
			}else{
				$("#clientInvoiceErr").css('display','block');
				$("#putErrorClientInvoice").val(i18n('msg_any_invoice'));
			}
		},error: function(data){
			$("#clientInvoiceErr").css('display','block');
			$("#putErrorClientInvoice").val(i18n('err_unable_get_data'));
		}
	});
}


document.addEventListener("click",function(e){var obj=e.target;});

function i18n(msg){
	var i18n_message="";
	//IDIOMAS: si en la URL no contiene la variable "lenguage", asumirá que esta en inglés
	lang=getLanguageURL();
	if(msg=="msg_any_invoice"){
		if(lang=="es"){i18n_message="No se encontraron facturas pendientes";}
		else{i18n_message="No pending invoices were found";}
	}else if(msg=="msg_balance"){
		if(lang=="es"){i18n_message="Saldo";}
		else{i18n_message="Balance";}

	}else if(msg=="err_unable_get_data"){
		if(lang=="es"){i18n_message="\u00a1No fue posible obtener datos!";}
		else{i18n_message="Unable to get data!";}
	}return i18n_message;
}