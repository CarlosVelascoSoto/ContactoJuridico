// search live
var idexecutor_autocomplete;
jQuery(document).ready(
		function() {
			 $("#ls_query").keypress(function() {
					idexecutor_autocomplete = 'ls_query';
				});
			
			jQuery(".mySearch").ajaxlivesearch({
				loaded_at : new Date(),
				token : Date.now(),
				max_input : 50,
				url : "ajaxLiveAutoCompleteCustomerLead.jet",
				async : false,
				onResultClick : function(e, data) {//selecciona con click mouse
					selectedValue(data,idexecutor_autocomplete);
				},
				onResultEnter : function(e, data) {// selecciona con enter
					selectedValue(data,e.target.id);
				},
				onAjaxComplete : function(e, data) {
					console.log('onAjaxComplete, auto complete..');
				}
			});

			function selectedValue(data, idSearch) {
				// do whatever you want
				var selectedOne = jQuery(data.selected).find('td').eq('1').text();
				var id=jQuery(data.selected).find('td').eq('0').text();
				var isred=jQuery(data.selected).find('td').eq('2').text();
				// set the input value
				jQuery('#'+idSearch).val(selectedOne);
				jQuery('#lead').val(selectedOne);
				jQuery('#idlead').val(id);
				$('#dbred').val(isred);
				// hide the result
				jQuery('#'+idSearch).trigger('ajaxlivesearch:hide_result');
				getContactNames(id, "selectcontact", '', isred);
			}
			
			function getContactNames(companyValue, ctrl, idSelected, dbRed) {
				var param = "companyValue=" + companyValue+"&dbred="+dbRed;
				$.ajax({
					type : 'POST',
					url : ctx + '/selectcontact.jet',
					data : param,
					async : false,
					success : function(data) {
						$("#" + ctrl).html('');
						var listItems = '<option value="0" selected disabled>'
								+ i18n('msg_select_contact') + '</option>';
						$.each(data, function(idx, obj) {
							if(idSelected == obj.id)
								listItems += '<option value="' + obj.contactid + '" selected="selected">' + obj.name+ '</option>';
							else
								listItems += '<option value="' + obj.contactid + '">' + obj.name+ '</option>';
							
						});
						$("#" + ctrl).append(listItems);
						return false;
					},
					error : function(e) {
						swal("Warning!", i18n("err_unable_get_company"), "Error");
					}
				});
			};

		});