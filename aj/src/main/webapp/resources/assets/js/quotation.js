var CCURR = "$", NDEC = 2, CDEC = ".", CMIL = ",";
DATEFP = getFormatPatternDate();

function loadDefault() {
	polyfill("object","indexOf","children","firstElementChild","lastElementChild","addDays","number.toLocaleDateString");
	$('#selDate').attr("placeholder", DATEFP);
	$('#selValidityDate').attr("placeholder", DATEFP);
};

function clearSelect(id) {
	/*var sel = document.getElementById(id);
	var len = sel.options.length;
	for (i = 0; i < len; i++)
		sel.remove(sel.options[0]);*/
	$('#'+id).val('');
};

$("#addNewCustomer").click(function() {
	$('#quotation-modal').css('display','none'); 
	$('#shadow-quotation-modal').css('display','none');
	
	//$('#addUserErrorLead').css('display', 'none');
	$("#addNewCustomer").attr('data-target', '#add-customer-modal');
	$('#add-customer-modal').modal('toggle');
});


$("#selCurr").on("keyup", function(e) {
	if (e.keyCode == 13)
		xCtrl("selDate").focus();
});
$("#selProduct").on("keyup", function(e) {
	if (e.keyCode == 13)
		xCtrl("qtyItems").focus();
});
$("#qtyItems").on("keyup", function(e) {
	if (e.keyCode == 13)
		fnAddItem();
});

function popUpQuotation() {
	$("#quotation-modal").css('display', 'fixed');
	$("#shadow-quotation-modal").css('display', 'fixed');
	//addNextBackBtn("");
	$('#selClient').val('');
	$('#selDate').val(setFormatPatternDate("", DATEFP));
	$('#fixSelDate').val(setFormatPatternDate("", "yyyy-MM-dd"));
	$
			.ajax({
				type : "POST",
				url : ctx + "/getCurrency.jet",
				async : false,
				dataType : "json",
				success : function(data) {
					if (data != null) {
						var cr = document.getElementById("selCurr"), len = cr.options.length, sel = !0;
						for (i = 0; i < len; i++)
							cr.remove(cr.options[0]);
						for ( var d in data) {
							var option = new Option(data[d].currency,
									data[d].currencyid, !0, sel);
							option.title = data[d].shortname;
							$('#selCurr').append(option);
							sel = !1;
						}
						$('#selCurr').trigger('change');
						$('#newquote2').css('display', 'none');
						$('#newquote1').css('display', 'block');
						$('#shadow-quotation-modal').css('display', 'block');
						$('#quotation-modal').css('display', 'block');
						$('#ls_query').val('');

						window.stop();
						// throw '';
						return;
					} else {
						swal(i18n("msg_error"),
								i18n("err_display_currency_list"), "error");
					}
				}
			});
};

function fnAddItem() {
	$('#listQuot').css('display', 'block');
	var prod = $('#selProduct');
	var qty = $('#qtyItems').val();
	var desc = $('#ls_query_product').val();
	var altcod = prod.attr('altcod');
	var unit = prod.attr('unit');
	var pu = prod.attr('price');
	pu = parseFloat(pu).toFixed(NDEC)
	var id = prod.val();
	var r = ((xCtrl('listQuot').rows.length == null) ? 1
			: xCtrl('listQuot').rows.length) - 1;

	var qtyAvl = 0;

	if (valDoubleData("listQuot", id))
		return null;
	var shipdate = setFormatPatternDate("", DATEFP);
	var dynamictable = "<tr id='row"
			+ id
			+ "'>"
			+ "<td class='tac'>"
			+ r
			+ "</td>"
			+ "<td>"
			+ altcod
			+ "</td>"
			+ "<td>"
			+ desc
			+ "</td>"
			+ "<td class='tac'>"
			+ unit
			+ "</td>"
			+ // Unidad
			"<td><div class='select-wrapper' style='width:85px;' onclick='xCtrl('tax"
			+ id
			+ "').focus();'>"
			+ "<select id='tax"
			+ id
			+ "' onchange='recalc("
			+ id
			+ ");' class='liveList ddsencillo' style='width:85px;' required></select></div>"
			+ "</td>"
			+ "<td class='tac'><input type='text' name='qty' id='qty"
			+ id
			+ "' value='"
			+ qty
			+ "' onchange='recalc("
			+ id
			+ ",id)' class='inNumShort' pattern='[0-9]*(["
			+ CDEC
			+ "]?[0-9]+)?' /></td>"
			+ "<td class='tac'><input type='text' name='pu' id='pu"
			+ id
			+ "' value='"
			+ pu
			+ "' onchange='recalc("
			+ id
			+ ",id)' class='inNumShort' pattern='[0-9]*(["
			+ CDEC
			+ "]?[0-9]+)?' /></td>"
			+ "<td class='tac'><input type='text' name='ship' id='shipdate"
			+ id
			+ "' value='"
			+ shipdate
			+ "' class='inDate tac' placeholder='"
			+ getFormatPatternDate()
			+ "' /></td>"
			+ "<td class='tac' id='extPrice"
			+ id
			+ "'>0</td>"
			+ "<td class='tac'>"
			+ "0"
			+ "</td>"
			+ // disponible
			"<td class='tac p0_25'>"
			+ "0"
			+ "</td>"
			+ // icono semáforo
			"<td class='tac p0_25'><div id='del"
			+ id
			+ "' name='prodId' class='secTrash' onclick='delProdRow(id);' >"
			+ "<span class='trash' style='top:5px;'><span class='spanTrash'></span><i class='iTrash'></i></span></div></td></tr>";
	var temp = document.getElementById("listQuot").lastElementChild;
	$(dynamictable).insertAfter(temp);
	var sel = true;
	var data = getVat();
	for ( var d in data) {
		var option = new Option(data[d].headertext, (data[d].percentage) / 100,
				true, sel);
		$('#tax' + id).append(option);
		// $('#tax'+id).data("vatid",data[d].vatid);
		$('#tax' + id).attr("name", "vatid");
		xCtrl("tax" + id).options[d].setAttribute("data-vatid", data[d].vatid);
		sel = false;
	}
	recalc(id);
	$('#ls_query_product').focus();
	
};

function recalc(id, fieldId) {
	fieldId = fieldId || "";
	if (fieldId != "") {
		var val = fillDec($("#" + fieldId).val(), NDEC);
		$("#" + fieldId).val(val);
	}
	productsCalc: {
		if (id == "total") {
			break productsCalc;
		}
		// línea actual
		var selTax = xCtrl("tax" + id);
		var tax = selTax.options[selTax.selectedIndex].value;
		var qty = onlyNums($('#qty' + id).val());
		var pu = onlyNums($('#pu' + id).val());
		var calc = fixJSMult(qty, pu).toFixed(NDEC);
		var total = currencyFmt(calc);// =Number(calc).toLocaleString(getLanguageURL());
		var ep = "#extPrice" + id;
		$(ep).text(total);
		0 > total ? $(ep).addClass("negMoney") : $(ep).removeClass("negMoney");

		var elem = document.getElementById('listQuot').children;
		var sub = 0, iva = 0;
		for (i = 2; i < elem.length; i++) {// 0=thead, 1=tfoot
			var e = elem[i].children;
			var eId = (e[8].id).substring(8, (e[8].id).lenght);
			var ep = onlyNums($('#extPrice' + eId).text());
			selTax = xCtrl("tax" + eId);
			var valTax = selTax.options[selTax.selectedIndex].value;
			var totTax = (valTax == '0') ? 0 : fixJSMult(ep, valTax);
			sub += ep;
			iva += totTax;
			$('#subtotal').text(CCURR + currencyFmt(sub.toFixed(NDEC)));
			$('#taxiva').text(CCURR + currencyFmt(iva.toFixed(NDEC)));
			$('#gTotal').text(CCURR + currencyFmt((sub + iva).toFixed(NDEC)));
		}
		if (elem.length == 0) {
			$("#discountText").css("display", "none");
			$("#discount").css("display", "block");
		}
	}
	var fSubt = onlyNums($('#subtotal').text()), fTax = onlyNums($('#taxiva')
			.text()), fTot, fTotDsct, fpd = onlyNums($('#percentdiscount')
			.val());
	fTotDsct = (fSubt + fTax) * (fpd / 100);
	fTot = (fSubt + fTax) - fTotDsct;
	$('#discount').text(CCURR + currencyFmt(fTotDsct.toFixed(NDEC)));
	$('#gTotal').text(CCURR + currencyFmt(fTot.toFixed(NDEC)));
};

function delProdRow(id) {
	var rid = id.substring(3, id.lenght);
	swal({
		title : i18n("msg_are_you_sure"),
		text : i18n("msg_delete_item"),
		type : "warning",
		showCancelButton : true,
		confirmButtonClass : 'btn-warning',
		confirmButtonText : i18n("btn_delete"),
		closeOnConfirm : false,
		closeOnCancel : false
	}, function(isConfirm) {
		if (isConfirm) {
			$("#pu" + rid).val("0");
			recalc(rid);
			$("#row" + rid).remove();
			reIndexTable("listQuot");
			swal.close();
		}
	});
};

function reIndexTable(tableId) {
	var elem = document.getElementById(tableId).children;
	for (i = 2; i < elem.length; i++) {// 0=thead,1=tfoot
		elem[i].firstElementChild.innerHTML = (elem[i].rowIndex);
	}
};

function valDoubleData(tableId, id) {
	var elem = document.getElementById(tableId).children;
	var rid = "row" + id;
	var resp = 0;
	for (i = 1; i < elem.length; i++) {
		if (elem[i].id == rid) {
			resp = 1;
			var prod = $("#selProduct");
			var desc = prod.val();
			var newQty = $('#qtyItems').val();
			var oldQty = $('#qty' + id).val();
			var prodExists = "<div id='doublerow' class='doubleRow'>"
					+ "<div id='errDoubleRow' class='alert alert-danger fade in'><span>"
					+ i18n('err_double_prod')
					+ ":<br><b>"
					+ desc
					+ "</b></span></div><br>"
					+ "<div><span>"
					+ i18n('msg_item')
					+ ":"
					+ elem[i].rowIndex
					+ "</span><br><span>"
					+ i18n('msg_quantity')
					+ ":"
					+ oldQty
					+ "</span></div><br>"
					+ "<span><b>"
					+ i18n('msg_to_do')
					+ "</b></span><br>"
					+ "<input type='radio' id='prod_add' name='prodExist' checked/><span> "
					+ i18n('msg_add_quantities')
					+ "</span><br>"
					+ "<input type='radio' name='prodExist' /><span> "
					+ i18n('msg_replace_quantity')
					+ "</span><br>"
					+ "<div style='margin:15px 0px 10px 0px; text-align:center;'>"
					+ "<input type='button' value='"
					+ i18n('btn_apply')
					+ "' class='btn btn-default waves-effect waves-light' style='margin-right:10px;' onclick='addReplQty("
					+ id
					+ ");' />"
					+ "<input type='button' value='"
					+ i18n('btn_cancel')
					+ "' class='btn btn-danger waves-effect waves-light' style='margin-left:10px;' onclick='addReplQty();' />"
					+ "</div></div>";
			$(prodExists).insertBefore("#" + tableId);
			break;
		}
	}
	return resp;
};

function addReplQty(id) {
	if ((id != "") && (id != null) && (id != undefined)) {
		var newQ = Number(xCtrl('qtyItems').value);
		var oldQ = xCtrl('qty' + id);
		oldQ.value = (chkIO('prod_add')) ? newQ + Number(oldQ.value) : newQ;
	}
	$('#doublerow').remove();
};

$("#selectcontact").change(function() {
	var companyValue = $('#selectcompany option:selected').val();
	if (companyValue == '0') {
		// swal("Warning!", "Please select company name!", "error");
		$('#putErrorNewQuotation').html(i18n('err_enter_companyname'));
		$('#addQuotationError').css('display', 'block');
		$('select[name="selectcontact"]:first').val('0');
	}
});

$("#percentdiscount")
		.on(
				"cut paste blur",
				function(e) {
					var val = fillDec((this).value, NDEC), show = val == "0.00" ? "none"
							: "block";
					(this).value = val;
					$("#discountText").css("display", show);
					$("#discount").css("display", show);
					recalc("total", (this).id);
				});


function addNextBackBtn(id) {
	if(chkIO("rbProspect")){
		$('#quotation-modal').css('display','none'); 
		//$('#shadow-quotation-modal').css('display','none');
		$("#addNewCustomer").attr('data-target', '#add-customer-modal');
		$('#add-customer-modal').modal('toggle');
		$('#addNewCompanyCancel').attr('prospect', 'true');
		
		$('#client_client').val($('#ls_query').val());
		$('#client_currency').val($('#selCurr').val());
		$('#client_paymenttype').val('3');
		$('#data_payterms').val('3');
		
	}else{
		onClickButtonNext(id);
	}
};


function onClickButtonNext(id) {
	if (id == 'btnNewNext') {
		if (($('#selDate').val() == "") || ($('#selClient').val() == "")
				|| ($('#selCurr').val() == "")) {
			$('#putErrorNewQuotation').html(i18n('err_client_prospect_no_empty'));
			$('#addQuotationError').css('display', 'block');
		} else {
			var data;
			if (chkIO("rbProspect")) {
				data = getClientData("clientid=" + $('#selClient').val()
						+ "&query=newClientData2&type=opportinity");
			} else {
				data = getClientData("clientid=" + $('#selClient').val()
						+ "&query=newProspectData2&type=client");
			}
			var daysQuot = 15;
			if (Object.keys(data).length > 0) {
				$('#addQuotationError').css('display', 'none');
				$('#btnNewNext').css('display', 'none');
				$('#btnNewBack').css('display', 'inline-block');
				$('#btnNewAdd').css('display', 'inline-block');
				$('#selName').text($('#ls_query').val());
				$('#addNewCustomer').css('display', 'none');

				$('#newquote1').toggle();
				$('#newquote2').toggle();
				$('#selDir').text(data[0].address);
				$('#selCol').text(data[0].address2);
				$('#selState').text(data[0].state);
				$('#selZipCode').text(data[0].zipcode);
				$('#selCountry').text(data[0].country);
				$('#selPhone').text(data[0].phone);
				$('#email1').text(data[0].email);
				$('#email2').text(data[0].alteremail);
				$('#selCurrencyText').text(
						xCtrl('selCurr').selectedOptions[0].text);

				// verificar aqui las sumas de fechas
				$('#selDateText').text($('#selDate').val());
				getAllToSel("payterms", "selPayterms", 1, 0);
				getAllToSel("incoterm", "selIncoterm", 1, 0);
			} else {
				$('#putErrorNewQuotation').html(
						i18n('err_client_prospect_no_found'));
				$('#addQuotationError').css('display', 'block');
			}
		}
	} else {
		$('#newquote1').toggle();
		$('#newquote2').toggle();
		$('#btnNewNext').css('display', 'inline-block');
		$('#btnNewBack').css('display', 'none');
		$('#btnNewAdd').css('display', 'none');
		$('#addNewCustomer').css('display', 'inline-block');
	}
};

function getClientData(param) {
	var res = null;
	$.ajax({
		type : 'POST',
		url : ctx + "/getDefaultNewData1.jet",
		data : param,
		async : false,
		success : function(data) {
			res = data;
		}
	});
	return res;
};

function getVat() {
	var res = null;
	$.ajax({
		type : 'POST',
		url : ctx + "/getVat.jet",
		async : false,
		success : function(data) {
			res = data;
		}
	});
	return res;
};

function addQuotation() {
	$('#addQuotationError').css('display', 'none');
	var rowItems = xCtrl(">listQuot tr"), err = "", idVals = "", allId = xCtrl(":prodId"), prodVals = "", allQty = xCtrl(":qty"), rowqty = 0, qtyVals = "", allPU = xCtrl(":pu"), Vals = "", allPU = xCtrl(":pu"), rowpu = 0, puVals = "", allShip = xCtrl(":ship"), rowship = 0, shipVals = "", allVat = xCtrl(":vatid"), vatsId = "", pd = parseInt($(
			'#percentdiscount').val());
	for (var q = 0; q < allQty.length; q++) {
		if ($(allQty[q]).val() == "")
			rowqty = q + 1;
		prodVals += (allQty[q].id).substr(3, (allQty[q].id.length)) + "%7C";
		qtyVals += $(allQty[q]).val() + "%7C";
	}
	if (rowqty < 1) {
		for (var p = 0; p < allPU.length; p++) {
			if ($(allPU[p]).val() == "")
				rowpu = p + 1;
			puVals += $(allPU[p]).val() + "%7C";
		}
	}
	if ((rowqty < 1) && (rowpu < 1)) {
		for (var s = 0; s < allShip.length; s++) {
			if ($(allShip[s]).val() == "")
				rowship = s + 1;
			shipVals += $(allShip[s]).val() + "%7C";
		}
		for (var i = 0; i < allId.length; i++)
			idVals += (allId[i].id).substr(3, (allId[i].id).length) + "%7C";
		for (var v = 0; v < allVat.length; v++) {
			var selTmp = xCtrl(allVat[v].id);
			var id = selTmp.options[selTmp.selectedIndex]
					.getAttribute('data-vatid');
			vatsId += id + "%7C";
		}
	}
	if ((pd == null) || (isNaN(pd)))
		pd = 0;
	if ($('selCurrencyText').text == "") {
		err = i18n('err_select_currency');
	} else if (prodVals.lenght > 3) {
		err = i18n('err_add_1item');
	} else if (rowqty > 0) {
		err = i18n('err_qty_empty') + rowqty;
	} else if (rowpu > 0) {
		err = i18n('err_pu_empty') + rowpu;
	} else if (rowship > 0) {
		err = i18n('err_ship_empty') + rowpu;
	} else if (pd < 0) {
		err = i18n('err_discount_negative');
	}
	if (err != "") {
		swal(i18n("msg_error"), err, "error");
		return false;
	} else {
		var param = "clientid=" + $('#selClient').val() + "&currency="
				+ $('#selCurr').val() + "&soid=" + $('#selIncoterm').val()
				+ "&date=" + $('#fixSelDate').val() + "&comments="
				+ $('#qComments').val() + "&percentdiscount=" + pd.toString()
				+ "&selPayterms=" + $('#selPayterms').val() + "&selShipMode="
				+ $('#selShipMode').val() + "&selIncoterm="
				+ $('#selIncoterm').val() + "&selValidityDate="
				+ $('#selValidityDate').val() + "&qtyVals=" + qtyVals
				+ "&puVals=" + puVals + "&shipVals=" + shipVals + "&idVals="
				+ idVals + "&vatsId=" + vatsId;
		$.ajax({
			type : "POST",
			url : ctx + "/addQuotation.jet",
			data : param,
			async : false,
			success : function(data) {
				if (data) {
					swal({
						title : i18n('msg_success'),
						text : i18n('msg_record_saved_successfully'),
						type : "success",
						html : true,
						timer : 3000,
						allowEscapeKey : false
					}, function() {
						swal.close();
					});
					swal(i18n("msg_success"),
							i18n("msg_record_saved_successfully"), "success");
					window.location = ctx + "/quotation.jet" + "?language="
							+ getLanguageURL();
				} else {
					swal(i18n("msg_error"), i18n("err_empty_field"), "error");
				}
			},
			error : function(e) {
				swal(i18n("msg_error"), ("ERROR:" + e), "error");
			}
		});
	}
};

$("#addQuotationCancel").click(function() {
	$('#addQuotationError').css('display', 'none');
	$('#quotation-modal').css('display', 'none');
	$('#shadow-quotation-modal').css('display', 'none');
});

function getAllToSel(q, target, val, text) {
	$
			.ajax({
				type : 'POST',
				url : ctx + "/getAllExData.jet",
				data : "data=" + q,
				async : false,
				success : function(data) {
					var sOp = document.getElementById(target);
					var len = sOp.options.length;
					var sel = true;
					for (i = 0; i < len; i++) {
						sOp.remove(sOp.options[0]);
					}
					for ( var d in data) {
						var arr = Object.values(data[d]);
						var option = new Option(arr[1], arr[0],true, sel);
						$('#' + target).append(option);
						sel = false;
					}
				}
			});
}

$('.table-action-btn.deleteQuotation').click(
		function() {
			var id = $(this).attr('id');
			swal({
				title : i18n('msg_are_you_sure'),
				text : i18n('msg_will_not_recover_record'),
				type : "warning",
				showCancelButton : true,
				confirmButtonClass : 'btn-warning',
				confirmButtonText : i18n('btn_yes_delete_it'),
				closeOnConfirm : false,
				closeOnCancel : false
			}, function(isConfirm) {
				if (isConfirm) {
					deleteQuotation(id);
					swal({
						title : i18n('msg_deleted'),
						text : i18n('msg_record_deleted'),
						type : "success"
					}, function() {
						window.location = ctx + "/quotation.jet" + "?language="
								+ getLanguageURL();
					});
				} else {
					swal(i18n('msg_cancelled'), i18n('msg_record_safe'),
							"warning");
				}
			});
		});

function i18n(msg) {
	var i18n_message = "";
	// IDIOMAS:si en la URL no contiene la variable "lenguage", asumirá que esta
	// en inglés
	lang = getLanguageURL();
	if (msg == "btn_apply") {
		if (lang == "es") {
			i18n_message = "Aplicar";
		} else {
			i18n_message = "Apply";
		}
	} else if (msg == "btn_cancel") {
		if (lang == "es") {
			i18n_message = "Cancelar";
		} else {
			i18n_message = "Cancel";
		}
	} else if (msg == "btn_delete") {
		if (lang == "es") {
			i18n_message = "\u00a1S\u00ed, eliminar!";
		} else {
			i18n_message = "Yes, delete it!";
		}

	} else if (msg == "msg_add_quantities") {
		if (lang == "es") {
			i18n_message = "Sumar ambas cantidades";
		} else {
			i18n_message = "Add both quantities";
		}
	} else if (msg == "msg_are_you_sure") {
		if (lang == "es") {
			i18n_message = "\u00bfEsta seguro?";
		} else {
			i18n_message = "Are you sure?";
		}
	} else if (msg == "msg_cancelled") {
		if (lang == "es") {
			i18n_message = "Cancelado";
		} else {
			i18n_message = "";
		}
	} else if (msg == "msg_record_saved_successfully") {
		if (lang == "es") {
			i18n_message = "\u00a1Registro guardado exitosamente!";
		} else {
			i18n_message = "Record saved successfully!";
		}
	} else if (msg == "msg_deleted") {
		if (lang == "es") {
			i18n_message = "\u00a1Eliminado!";
		} else {
			i18n_message = "Deleted!";
		}
	} else if (msg == "msg_item") {
		if (lang == "es") {
			i18n_message = "Partida";
		} else {
			i18n_message = "Item";
		}
	} else if (msg == "msg_quantity") {
		if (lang == "es") {
			i18n_message = "Cantidad";
		} else {
			i18n_message = "Quantity";
		}
	} else if (msg == "msg_replace_quantity") {
		if (lang == "es") {
			i18n_message = "Sustituir por la nueva cantidad";
		} else {
			i18n_message = "Replace for the new quantity";
		}
	} else if (msg == "msg_success") {
		if (lang == "es") {
			i18n_message = "\u00a1Correcto!";
		} else {
			i18n_message = "Success!";
		}
	} else if (msg == "msg_to_do") {
		if (lang == "es") {
			i18n_message = "¿Qu\u00e9 desea hacer?";
		} else {
			i18n_message = "Witch action you want to do?";
		}
	} else if (msg == "msg_error") {
		if (lang == "es") {
			i18n_message = "\u00a1Error!";
		} else {
			i18n_message = "Error!";
		}

	} else if (msg == "err_client_prospect_no_empty") {
		if (lang == "es") {
			i18n_message = "\u00a1Debe ingresar el cliente!";
		} else {
			i18n_message = "The customer name cannot be empty!";
		}
	} else if (msg == "err_client_prospect_no_found") {
		if (lang == "es") {
			i18n_message = "\u00a1Datos del cliente o prospecto no encontrado!";
		} else {
			i18n_message = "Data client or prospect not found!";
		}
	} else if (msg == "msg_delete_item") {
		if (lang == "es") {
			i18n_message = "\u00bfDesea eliminar \u00e9ste registro?";
		} else {
			i18n_message = "Do you want to delete this record?";
		}
	} else if (msg == "err_display_currency_list") {
		if (lang == "es") {
			i18n_message = "\u00a1Error en la conexi\u00f3n al mostrar el listado de monedas!";
		} else {
			i18n_message = "Error in databse connection while displaying the currency list!";
		}
	} else if (msg == "err_double_prod") {
		if (lang == "es") {
			i18n_message = "\u00a1El producto o servicio ya est\u00e1 en el listado!";
		} else {
			i18n_message = "Product or service is already in list!";
		}
	} else if (msg == "err_empty_field") {
		if (lang == "es") {
			i18n_message = "\u00a1Uno o m\u00e1s datos est\u00e1n vac\u00edos, favor de corregirlo!";
		} else {
			i18n_message = "One or more data are empty, please fill them!";
		}

	} else if (msg == "err_add_1item") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de ingresar al menos una partida!";
		} else {
			i18n_message = "Please add at least one line item!";
		}
	} else if (msg == "err_discount_negative") {
		if (lang == "es") {
			i18n_message = "El descuento general no debe ser negativo";
		} else {
			i18n_message = "The general discount must not be negative";
		}
	} else if (msg == "err_pu_empty") {
		if (lang == "es") {
			i18n_message = "El precio esta vac\u00edo en la partida ";
		} else {
			i18n_message = "The price is empty on the line ";
		}
	} else if (msg == "err_qty_empty") {
		if (lang == "es") {
			i18n_message = "La cantidad esta vac\u00eda en la partida ";
		} else {
			i18n_message = "The quantity is empty on the line ";
		}
	} else if (msg == "msg_record_deleted") {
		if (lang == "es") {
			i18n_message = "\u00a1Registro eliminado!";
		} else {
			i18n_message = "Record deleted!";
		}
	} else if (msg == "msg_record_safe") {
		if (lang == "es") {
			i18n_message = "Tu registro esta seguro";
		} else {
			i18n_message = "Your record is safe:)";
		}
	} else if (msg == "err_select_currency") {
		if (lang == "es") {
			i18n_message = "\u00a1Favor de seleccionar una moneda!";
		} else {
			i18n_message = "Please select a currency!";
		}
	} else if (msg == "err_ship_empty") {
		if (lang == "es") {
			i18n_message = "La fecha de embarque esta vac\u00eda en la partida ";
		} else {
			i18n_message = "The ship date is empty on the line ";
		}
	} else if (msg == "msg_will_not_recover_record") {
		if (lang == "es") {
			i18n_message = "\u00a1Si acepta, no podr\u00e1 recuperar este registro!";
		} else {
			i18n_message = "You will not be able to recover this record!";
		}
	}
	return i18n_message;
};


$('#addNewCompanyCancel').click(function () {
		var isprospect = $('#addNewCompanyCancel').attr('prospect');
		if(isprospect!= '' ){
			$('#addNewCompanyCancel').removeAttr("prospect");
			//$('#add-customer-modal').css('display','none');
			$('#quotation-modal').css('display','block');
		}
    });


$('#saveNewClient').click(
        function () {
        	
            var URLTOSEND = "editclient";
            if ($('#client_clientid').val() === '') {
                URLTOSEND = "newclient";
            }

            $.ajax({
                type: "POST",
                url: URLTOSEND,
                data: $('#newclient').serialize(),
                dataType: "json",
                success: function (res) {
                    if (res.validated) {
                        //Set response
                    	//si viene de un prospecto
	                    var isprospect = $('#addNewCompanyCancel').attr('prospect');
						if(isprospect!= '' ){
							$('#selClient').val(res.idreturn);
							$('#modalResultClose').attr('prospect', 'true');
						}
                        //$('#resultContainer pre code').text(JSON.stringify(res.client));
                        $('#add-customer-modal').modal('hide');
                        swal(i18n('msg_success'), i18n('msg_record_saved_successfully'),"success");
                        $('#shadow-quotation-modal').css('display','none');
                    } else {
                        //Set error messages
                        $("div.alert").remove();
                        var tabx = 2;
                        $.each(res.errorMessages, function (key, value) {
                            //$('input[name=' + key + ']').after('<span class="help-inline text-danger">' + value + '</span>');
                        	if(key.includes("client.")){
                        		tabx = 1;
                        	}	
                        		
                            $('input[id=' + key.replace('.','_') + ']').after('<div class="alert alert-danger" role="alert"> <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> <span class="sr-only">Error:</span>  ' + value + ' </div>');
                            $('select[id=' + key.replace('.','_') + ']').after('<div class="alert alert-danger" role="alert"> <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> <span class="sr-only">Error:</span>  ' + value + ' </div>');
                        });
                        
                        $('.nav-tabs a[href="#tab' + tabx + '"]').tab('show');
                        
                    }
                },
            });
        }
);