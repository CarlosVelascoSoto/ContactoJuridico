/**
 * 
 */

// search live
var idexecutor_autocomplete;
jQuery(document).ready(
		function() {
			/*
			 $("#rbClient").click(function() {  
			        if($(this).is(':checked')) {  
			        	$("#ls_query").attr('data-type', 'client');
			        }
			        idexecutor_autocomplete = 'ls_query';
			    });  
			 $("#rbProspect").click(function() {  
			        if($(this).is(':checked')) {  
			        	$("#ls_query").attr('data-type', 'opportinity');
			        }  
			        idexecutor_autocomplete = 'ls_query';
			    });  */
			 $("#ls_query").keypress(function() {
					var srch = "searchClientPros", ctrl = 'selClient';
					var dataType = (chkIO("rbClient")) ? "client" : "opportinity";
					$("#ls_query").attr('data-type', dataType);
					//$(this).attr('data-clientid', '');
					idexecutor_autocomplete = 'ls_query'; 
					
					/*
					 
					var query = $(this);
		            var query_id = query.attr('id');
					generateFormHtml(query, ls);
					*/
				});
			

			$("#ls_query_product").keypress(function() {
				var srch = "searchProduct", ctrl = 'selProduct';
				$(this).attr('data-type', 'newClientProduct');
				$(this).attr('data-clientid', '');
				
				var newelement = "<input type=hidden name=currencyid value='"+$('#selCurr').val()+"'/>";
				$("#search_ls_query_product").append(newelement);
				console.log('test' +  $("#search_ls_query_product input[name=currencyid]").val() );
				idexecutor_autocomplete = 'ls_query_product';
			});

			jQuery(".mySearch").ajaxlivesearch({
				loaded_at : new Date(),
				token : Date.now(),
				max_input : 50,
				url : "ajaxLiveAutoComplete.jet",
				async : false,
				onResultClick : function(e, data) {//selecciona con click mouse
					if (idexecutor_autocomplete === 'ls_query') {// autocomplete clientes
						selectedValue(data,idexecutor_autocomplete,'selClient')
					} else if (idexecutor_autocomplete === 'ls_query_product') {
						selectedValue(data,idexecutor_autocomplete,'selProduct')
					}
				},
				onResultEnter : function(e, data) {// selecciona con enter
					if (e.target.id === 'ls_query') {// autocomplete clientes
						selectedValue(data,e.target.id,'selClient')
					} else if (e.target.id === 'ls_query_product') {
						selectedValue(data,e.target.id,'selProduct');
					}
				},
				onAjaxComplete : function(e, data) {
					console.log('onAjaxComplete, auto complete..');
				}
			});

			function selectedValue(data, idSearch, idHiddenInput) {
				// do whatever you want
				var selectedOne = jQuery(data.selected).find('td').eq('1').text();
				// set the input value
				jQuery('#'+idSearch).val(selectedOne);
				jQuery('#'+idHiddenInput).val(jQuery(data.selected).find('td').eq('0').text());
				// hide the result
				jQuery('#'+idSearch).trigger('ajaxlivesearch:hide_result');
				if(idSearch==='ls_query_product'){
					 
					xCtrl('pUnit').innerText=jQuery(data.selected).find('td').eq('3').text(); 
					$('#qtyItems').focus(); 
					xCtrl('qtyItems').select();
					var precio = jQuery(data.selected).find('td').eq('4').text();
					precio = precio.replace(/([0-9]+(\.[0-9]+[1-9])?)(\.?0+$)/,'$1')
					jQuery('#'+idHiddenInput).attr('altcod',jQuery(data.selected).find('td').eq('1').text());
					jQuery('#'+idHiddenInput).attr('unit',jQuery(data.selected).find('td').eq('3').text());
					jQuery('#'+idHiddenInput).attr('price',precio);
				}
			}
			/*function selectedProduct(data) {
				var selectedOne = jQuery(data.selected).find('td').eq('1').text();
				jQuery('#ls_query_product').val(selectedOne);
				jQuery('#selProduct').val(jQuery(data.selected).find('td').eq('0').text());
				jQuery("#ls_query_product").trigger('ajaxlivesearch:hide_result');
			}*/
		});

$('#add-customer-modal').on('hidden.bs.modal', function (e) {
	$(this).find("input,textarea")
            .val('').end()
            .find("input[type=checkbox], input[type=radio]")
            .prop("checked", "").end();
	
});

/*
 *  $("#newModal").on('show.bs.modal', function () {
    alert('The modal will be displayed now!');
  });
 * */
$('#resultContainer').on('hidden.bs.modal', function (e) {
	var isprospect = $('#modalResultClose').attr('prospect');
	if(isprospect!= '' ){
		$('#modalResultClose').removeAttr("prospect");
		$('#quotation-modal').css('display','block');
		$('#shadow-quotation-modal').css('display','block');
		onClickButtonNext('btnNewNext');
	}else{	
		popUpQuotation();
	}
});