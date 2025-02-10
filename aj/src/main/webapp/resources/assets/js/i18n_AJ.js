function i18n(msg){
	var i18n_message='', lang=getLanguageURL();
	// IDIOMAS:si en la URL no contiene la variable 'lenguage', asumirá que esta en español
	if(msg=='btn_continue')
		i18n_message=lang=='en'?'Continue':'Continuar';
	else if(msg=='btn_yes_clean_it')
		i18n_message=lang=='en'?'Yes, clean it!':'\u00a1S\u00ed, limpiar!';
	else if(msg=='btn_yes_continue')
		i18n_message=lang=='en'?'Yes, continue':'\u00a1S\u00ed, continuar';
	else if(msg=='btn_yes_delete_it')
		i18n_message=lang=='en'?'Yes, delete it!':'\u00a1S\u00ed, eliminar!';
	else if(msg=='btn_yes_remove_it')
		i18n_message=lang=='en'?'Yes, mark as read!':'\u00a1S\u00ed, marcar como le\u00eddo!';
	else if(msg=='msg_accion'||msg=='msg_action')
		i18n_message=lang=='en'?'Action':'Acci\u00f3n';
	else if(msg=='msg_active')
		i18n_message=lang=='en'?'Active':'Activo';
	else if(msg=='msg_actor')
		i18n_message=lang=='en'?'Actor':'Actor';
	else if(msg=='msg_addnew')
		i18n_message=lang=='en'?'Add new':'Agregar nuevo';
	else if(msg=='msg_added_document')
		i18n_message=lang=='en'?'Added document':'Documento agregado';
	else if(msg=='msg_address')
		i18n_message=lang=='en'?'Address':'Direcci\u00f3n';
	else if(msg=='msg_all')
		i18n_message=lang=='en'?'All':'Todo';
	else if(msg=='msg_are_you_sure')
		i18n_message=lang=='en'?'Are you sure?':'\u00bfEsta seguro?';
	else if(msg=='msg_appeal')
		i18n_message=lang=='en'?'Appeal':'Apelaci\u00f3n';
	else if(msg=='msg_cancelled')
		i18n_message=lang=='en'?'Cancelled process':'Proceso cancelado';
	else if(msg=='msg_circuit')
		i18n_message=lang=='en'?'Circuit':'Circuito';
	else if(msg=='msg_city')
		i18n_message=lang=='en'?'City':'Ciudad';
	else if(msg=='msg_client')
		i18n_message=lang=='en'?'Client':'Cliente';
	else if(msg=='msg_settled')
		i18n_message=lang=='en'?'Settled':'Concluido';
	else if(msg=='msg_changed_record')
		i18n_message=lang=='en'?'Changed record':'Registro con cambios';
	else if(msg=='msg_changes')
		i18n_message=lang=='en'?'Changes':'Cambios';
	else if(msg=='msg_character_client')
		i18n_message=lang=='en'?'Character client':'Car\u00e1cter del cliente';
	else if(msg=='msg_check_results')
		i18n_message=lang=='en'?'Please check the results, in case of failures we suggest start again the process'
			:'Por favor, verifique los resultados, en caso de fallas sugerimos repetir el proceso';
	else if(msg=='msg_clear_schedule_data')
		i18n_message=lang=='en'?'Do you want to clear the data in the Activity area?':'\u00bfDesea limpiar los datos del area Actividad?';
	else if(msg=='msg_closeandkeep')
		i18n_message=lang=='en'?'Close and keep remaining':'Cerrar y seguir recordando';
	else if(msg=='msg_complaining')
		i18n_message=lang=='en'?'Complaining':'Quejoso';
	else if(msg=='msg_consultation')
		i18n_message=lang=='en'?'Consultation':'Consulta';
	else if(msg=='msg_corporationperson')
		i18n_message=lang=='en'?'Corporate person':'Persona moral';
	else if(msg=='msg_country')
		i18n_message=lang=='en'?'Country':'Pa\u00eds';
	else if(msg=='msg_court')
		i18n_message=lang=='en'?'Court':'Juzgado';
	else if(msg=='msg_court_not_assigned')
		i18n_message=lang=='en'?'Court not assigned':'Juzgado no asignado';
	else if(msg=='msg_create')
		i18n_message=lang=='en'?'Create':'Crear';
	else if(msg=='msg_create_or_select_role')
			i18n_message=lang=='en'?'Create or select a role':'Crea o selecciona un rol';
	else if(msg=='msg_data_saved')
		i18n_message=lang=='en'?'Data saved successfully!':'\u00a1Datos almacenados exitosamente!';
	else if(msg=='msg_data_saved_no_email')
		i18n_message=lang=='en'?'Data saved successfully but email with schedule was not sent!'
			:'\u00a1Datos almacenados exitosamente pero el correo para recordatorio de agenda no pudo ser enviado!';
	else if(msg=='msg_date')
		i18n_message=lang=='en'?'Date':'Fecha';
	else if(msg=='msg_defendant')
		i18n_message=lang=='en'?'Defendant':'Demandado';
	else if(msg=='msg_delete')
		i18n_message=lang=='en'?'Delete':'Eliminar';
	else if(msg=='msg_deleted')
		i18n_message=lang=='en'?'Deleted':'Eliminado';
	else if(msg=='msg_delete_schedule_data')
		i18n_message=lang=='en'?'Do you want to delete the scheduled data?':'\u00bfDesea eliminar los datos de agenda?';
	else if(msg=='msg_deleted')
		i18n_message=lang=='en'?'Deleted!':'\u00a1Eliminado!';
	else if(msg=='msg_description')
		i18n_message=lang=='en'?'Description':'Descripci\u00f3n';
	else if(msg=='msg_direct_protection')
		i18n_message=lang=='en'?'Direct protection':'Amparo directo';
	else if(msg=='msg_distpart')
		i18n_message=lang=='en'?'District/Party':'Distrito/Partido';
	else if(msg=='msg_document_number')
		i18n_message=lang=='en'?'Document number':'N\u00famero de documento';
	else if(msg=='msg_edit')
		i18n_message=lang=='en'?'Edit':'Editar';
	else if(msg=='msg_empty_data')
		i18n_message=lang=='en'?'Please fill all required data!':'\u00a1Favor de completar los datos requeridos!';
	else if(msg=='msg_enter_jsp')
		i18n_message=lang=='en'?'Enter .JSP file name (without extention)':'Indicar el nombre del .JSP (sin extensi\u00f3n)';
	else if(msg=='msg_error')
		i18n_message=lang=='en'?'Error':'Error';
	else if(msg=='msg_external')
		i18n_message=lang=='en'?'External':'Externa';
	else if(msg=='msg_filter_by')
		i18n_message=lang=='en'?'Filter by':'Filtrar por';
	else if(msg=='msg_finished_process')
		i18n_message=lang=='en'?'Finished process':'Proceso terminado';
	else if(msg=='msg_firm')
		i18n_message=lang=='en'?'Firm':'Firma';
	else if(msg=='msg_goto_record')
		i18n_message=lang=='en'?'Go to record':'Ir al registro';
	else if(msg=='msg_hour')
		i18n_message=lang=='en'?'Hour':'Hora';
	else if(msg=='msg_hung')
		i18n_message=lang=='en'?'Hung':'Suspendido';
	else if(msg=='msg_inactive')
		i18n_message=lang=='en'?'Inactive':'Inactivo';
	else if(msg=='msg_incidental')
		i18n_message=lang=='en'?'Incidental':'Incidental';
	else if(msg=='msg_indirect_protection')
		i18n_message=lang=='en'?'Indirect protection':'Amparo indirecto';
	else if(msg=='msg_indirect_protection_num')
		i18n_message=lang=='en'?'Indirect protection number':'N\u00famero de amparo indirecto';
	else if(msg=='msg_indirect_protection_num')
		i18n_message=lang=='en'?'Indirect protection':'Amparo indirecto';
	else if(msg=='msg_individualperson')
		i18n_message=lang=='en'?'Individual person':'Persona f\u00edsica';
	else if(msg=='msg_info_stop_sharing')
		i18n_message=lang=='en'?'After continuing, remember to press \'save button\' to apply changes'
			:'Despu\u00e9s de continuar, recuerde presionar el \'botón guardar\' para aplicar los cambios';
	else if(msg=='msg_information')
		i18n_message=lang=='en'?'Information':'Informaci\u00f3n';
	else if(msg=='msg_interestedtrdparty')
		i18n_message=lang=='en'?'Interested third party':'Tercero interesado';
	else if(msg=='msg_judge')
		i18n_message=lang=='en'?'Judge':'Juez';
	else if(msg=='msg_lateral_menu')
		i18n_message=lang=='en'?'Root lateral menu':'Ra\u00edz del men\u00fa lateral';
	else if(msg=='msg_lawyer')
		i18n_message=lang=='en'?'Lawyer':'Abogado';
	else if(msg=='msg_lawyercounterpart')
		i18n_message=lang=='en'?'Lawyer counter part':'Abogado contraparte';
	else if(msg=='msg_lawyerassigned')
		i18n_message=lang=='en'?'Lawyer assigned':'Abogado asignado';
	else if(msg=='msg_local')
		i18n_message=lang=='en'?'Local':'Local';
	else if(msg=='msg_mandatory')
		i18n_message=lang=='en'?'Mandatory':'Obligatorio';
	else if(msg=='msg_markasread')
		i18n_message=lang=='en'?'Mark as read':'Marcar como le\u00eddo';
	else if(msg=='msg_mark_all_asread')
		i18n_message=lang=='en'?'Mark all as read':'Marcar todos como le\u00eddos';
	else if(msg=='msg_matter')
		i18n_message=lang=='en'?'Matter':'Materia';
	else if(msg=='msg_menu_bar')
		i18n_message=lang=='en'?'Menu bar':'Barra de men\u00fa';
	else if(msg=='msg_menu_bar_info')
		i18n_message=lang=='en'?'Bar menu name':'Nombre de la barra de men\u00fa';
	else if(msg=='msg_minutes')
		i18n_message=lang=='en'?'Minutes':'Minutos';
	else if(msg=='msg_module')
		i18n_message=lang=='en'?'Module':'M\u00f3dulo';
	else if(msg=='msg_movement')
		i18n_message=lang=='en'?'Movement':'Movimiento';
	else if(msg=='msg_need_save')
		i18n_message=lang=='en'?'Changes need to be saved':'Se necesita guardar cambios';
	else if(msg=='msg_new_record')
		i18n_message=lang=='en'?'New record':'Nuevo registro';
	else if(msg=='msg_no_choices')
		i18n_message=lang=='en'?'No choices to select from':'Sin opciones para elegir';
	else if(msg=='msg_no_data')
		i18n_message=lang=='en'?'No data':'Sin datos';
	else if(msg=='msg_no_data_client')
		i18n_message=lang=='en'?'No data for this client':'Sin datos para este cliente';
	else if(msg=='msg_no_data_city')
		i18n_message=lang=='en'?'No data for this city':'Sin datos para esta ciudad';
	else if(msg=='msg_no_data_matter')
		i18n_message=lang=='en'?'No data for this matter':'Sin datos para esta materia';
	else if(msg=='msg_none')
		i18n_message=lang=='en'?'None':'Ninguna';
	else if(msg=='msg_not_confirmed')
		i18n_message=lang=='en'?'Has not been confirmed':'No ha sido confirmado';
	else if(msg=='msg_not_notified')
		i18n_message=lang=='en'?'Has not been notified':'No ha sido notificado';
	else if(msg=='msg_notification')
        i18n_message=lang=='en'?'Notification':'Notificaci\u00f3n';
	else if(msg=='msg_notificatios_as_read')
		i18n_message=lang=='en'?'The notification will be removed and marked as read.'
			:'La notificaci\u00f3n ser\u00e1 removida y marcada como le\u00edda';
	else if(msg=='msg_numberof')
		i18n_message=lang=='en'?'Number of':'N\u00famero de';
	else if(msg=='msg_ok')
		i18n_message=lang=='en'?'Ok':'Aceptar';
	else if(msg=='msg_opendocument')
		i18n_message=lang=='en'?'Open document':'Abrir documento';
	else if(msg=='msg_option')
		i18n_message=lang=='en'?'Option':'Opci\u00f3n';
	else if(msg=='msg_optional')
		i18n_message=lang=='en'?'Optional':'Opcional';

	else if(msg=='msg_options_todo')
		i18n_message=lang=='en'?'Select the needed options to allow the user to do for this trial'
			:'Elige qué opciones le permitirás realizar con respecto a este juicio';

	else if(msg=='msg_others')
		i18n_message=lang=='en'?'Others':'Otros';
	else if(msg=='msg_principal')
		i18n_message=lang=='en'?'Principal':'Principal';
	else if(msg=='msg_pruebas')
		i18n_message=lang=='en'?'Evidences':'Pruebas';
	else if(msg=='msg_privileges')
		i18n_message=lang=='en'?'Privileges':'Privilegios';
	else if(msg=='msg_privilegesin')
		i18n_message=lang=='en'?'Privileges at':'Privilegios en';
	else if(msg=='msg_proceedings')
		i18n_message=lang=='en'?'Proceedings':'Expediente';
	else if(msg=='msg_protection')
		i18n_message=lang=='en'?'Protection':'Amparo';
	else if(msg=='msg_protection_number')
		i18n_message=lang=='en'?'Protection number':'N\u00famero de amparo';
	else if(msg=='msg_record_deleted')
		i18n_message=lang=='en'?'Record deleted!':'\u00a1Registro eliminado!';
	else if(msg=='msg_record_safe')
		i18n_message=lang=='en'?'Your record is safe':'Tu registro esta seguro';
	else if(msg=='msg_read')
		i18n_message=lang=='en'?'Read!':'Le\u00eddo';
	else if(msg=='msg_reference')
		i18n_message=lang=='en'?'Reference!':'\u00a1Referencia!';
	else if(msg=='msg_resource')
		i18n_message=lang=='en'?'Resource':'Recurso';
	else if(msg=='msg_resource_number')
		i18n_message=lang=='en'?'Resource number':'Número de recurso';
	else if(msg=='msg_search')
		i18n_message=lang=='en'?'Search':'Buscar';
	else if(msg=='msg_schedule_to_delete')
		i18n_message=lang=='en'?'Schedule information will be deleted when pressing the "Save" button'
			:'La informaci\u00f3n de la agenda se eliminar\u00e1 al presionar el bot\u00f3n "Guardar"';
	else if(msg=='msg_select')
		i18n_message=lang=='en'?'Select...':'Seleccionar...';
	else if(msg=='msg_select_client')
		i18n_message=lang=='en'?'Please, select a client':'Seleccione un cliente';
	else if(msg=='msg_select_clientfirst')
		i18n_message=lang=='en'?'Please, first select a client':'Favor de seleccionar primero un cliente';
	else if(msg=='msg_select_country')
		i18n_message=lang=='en'?'Please, first select a country.':'Seleccione primero un pa\u00eds.';
	else if(msg=='msg_select_pagename')
		i18n_message=lang=='en'?'Please select the page name to be displayed'
			:'\u00a1Favor de seleccionar el nombre de p\u00e1gina a ser mostrada!';
	else if(msg=='msg_select_parent')
		i18n_message=lang=='en'?'By not selecting a parent menu, you are indicating that it will be a menu bar'
			:'\u00a1Al no seleccionar un men\u00fa origen, esta indicando que ser\u00e1 una barra de men\u00fa!';
	else if(msg=='msg_select_file')
		i18n_message=lang=='en'?'Select a file':'Seleccione un archivo';
	else if(msg=='msg_select_state')
		i18n_message=lang=='en'?'Please, first select a state.':'Seleccione primero un estado.';
	else if(msg=='msg_select_trialtypeparties')
		i18n_message=lang=='en'?'Select a trial type to capture parties':'Seleccione un tipo de juicio para capturar las partes';
	else if(msg=='msg_select_way')
		i18n_message=lang=='en'?'Select a way':'Seleccione una v\u00eda';
	else if(msg=='msg_selected_client')
		i18n_message=lang=='en'?'Selected client':'Cliente seleccionado';
	else if(msg=='msg_show_info')
		i18n_message=lang=='en'?'Show information':'Mostrar informaci\u00f3n';
	else if(msg=='msg_state')
		i18n_message=lang=='en'?'State':'Estado';
	else if(msg=='msg_status')
		i18n_message=lang=='en'?'Status':'Estatus';
	else if(msg=='msg_submenu_group')
		i18n_message=lang=='en'?'Nuevo Submenu group':'Nuevo Submen\u00fa agrupador';
	else if(msg=='msg_submenu_group_info')
		i18n_message=lang=='en'?'Create a submenu (does not run any page)':'Crea un submen\u00fa (no ejecuta ninguna p\u00e1gina)';
	else if(msg=='msg_success')
		i18n_message=lang=='en'?'Success!':'\u00a1Correcto!';
	else if(msg=='msg_stop_sharing')
		i18n_message=lang=='en'?'Are you sure to stop sharing?':'\u00bfEsta seguro de dejar de compartir?';
	else if(msg=='msg_sure_to_delete')
		i18n_message=lang=='en'?'Are you sure to delete this record?':'\u00bfEsta seguro de eliminar este registro?';
	else if(msg=='msg_title')
		i18n_message=lang=='en'?'Title':'T\u00edtulo';
	else if(msg=='msg_to_select')
		i18n_message=lang=='en'?'Seleccionar':'Elegir';
	else if(msg=='msg_topheading_menu')
		i18n_message=lang=='en'?'Root top-heading menu':'Ra\u00edz del men\u00fa superior';
	else if(msg=='msg_trial')
		i18n_message=lang=='en'?'Trial':'Juicio';
	else if(msg=='msg_tjgral')
		i18n_message=lang=='en'?'General Trial type':'Tipo de juicio general';
	else if(msg=='msg_trialtype')
		i18n_message=lang=='en'?'Trial type':'Tipo de juicio';
	else if(msg=='msg_unexpected_sharing')
		i18n_message=lang=='en'?'Unable to share! Please contact the person who created or shared the document'
			:'\u00a1No se puede compartir! Favor de contactar a la persona que cre\u00f3 o comparti\u00f3 el documento';
	else if(msg=='msg_notregistered')
		i18n_message=lang=='en'?'Not registered in system':'No registrado en sistema';
	else if(msg=='msg_user')
		i18n_message=lang=='en'?'User':'Usuario';
	else if(msg=='msg_viewdetails')
		i18n_message=lang=='en'?'Viwe details':'Ver detalles';
	else if(msg=='msg_warning')
		i18n_message=lang=='en'?'Warning!':'\u00a1Advertencia!';
	else if(msg=='msg_way')
		i18n_message=lang=='en'?'Way':'V\u00eda';
	else if(msg=='msg_week')
		i18n_message=lang=='en'?'Week':'Sem.';
	else if(msg=='msg_will_not_recover_record')
		i18n_message=lang=='en'?'By continuing you will not be able to recover this record!'
			:'\u00a1Al continuar no podr\u00e1 recuperar este registro!';
	else if(msg=='msg_your_account')
		i18n_message=lang=='en'?'Your account':'T\u00fa cuenta';

	else if(msg=='err_address_no_spaces')
		i18n_message=lang=='en'?'Please enter a valid address without spaces or without accented characters!'
			:'\u00a1Favor de ingresar una direcci\u00f3n v\u00e1lida y sin espacios ni acentos!';
	else if(msg=='err_confirm_origintrial_appeal')
		i18n_message=lang=='en'?'Please confirm neather trial or appeal as origin!'
			:'\u00a1Favor de confirmar un juicio o apelaci\u00f3n origen!';
	else if(msg=='err_dependence_on_delete')
		i18n_message=lang=='en'?'This item can’t be deleted because it has dependencies!'
			:'\u00a1Este registro no se puede eliminar porque tiene dependencias!';
	else if(msg=='err_duplicated_companyclient')
		i18n_message=lang=='en'?'The indicated company-client relationship already exists!'
			:'\u00a1La relaci\u00f3n compa\u00f1ia-cliente indicada ya existe!';
	else if(msg=='err_duplicated_data')
		i18n_message=lang=='en'?'The information entered already exists!':'\u00a1El dato ingresado ya existe!';
	else if(msg=='err_duplicated_socialnetwork')
		i18n_message=lang=='en'?'The social network already exists!':'\u00a1La red social indicada ya existe!';
	else if(msg=='err_enter_activity')
		i18n_message=lang=='en'?'You have entered some data in activity area, please complete other fields!'
			:'\u00a1Ha igresado datos en el \u00e1rea de actividad, favor de completar los dem\u00e1s datos!';
	else if(msg=='err_enter_actor')
		i18n_message=lang=='en'?'Please enter actor!':'\u00a1Favor de ingresar un actor!';
	else if(msg=='err_enter_address')
		i18n_message=lang=='en'?'Please enter an address!':'\u00a1Favor de ingresar una direcci\u00f3n!';
	else if(msg=='err_enter_apadhesive')
		i18n_message=lang=='en'?'Please indicate if this is an adhesive appeal!'
			:'\u00a1Favor de indicar si es una apelaci\u00f3n adhesiva!';
	else if(msg=='err_enter_businessname')
		i18n_message=lang=='en'?'Please enter business name!':'\u00a1Favor de ingresar el nombre fiscal!';
	else if(msg=='err_enter_city')
		i18n_message=lang=='en'?'Please enter a city!':'\u00a1Favor de ingresar la ciudad!';
	else if(msg=='err_enter_claimedact')
		i18n_message=lang=='en'?'Please enter claimed act!':'\u00a1Favor de ingresar un acto reclamado!';
	else if(msg=='err_enter_clientname')
		i18n_message=lang=='en'?'Please enter the client name!':'\u00a1Favor de ingresar el nombre del cliente!';
	else if(msg=='err_enter_companyname')
		i18n_message=lang=='en'?'Please enter the company name!':'\u00a1Favor de ingresar el nombre de la compa\u00f1ia!';
	else if(msg=='err_enter_complaining')
		i18n_message=lang=='en'?'Please enter a complaning data!':'\u00a1Favor de ingresar un dato en quejoso!';
	else if(msg=='err_enter_consult')
		i18n_message=lang=='en'?'Please enter consult information!':'\u00a1Favor de ingresar la informaci\u00f3n de la consulta!';
	else if(msg=='err_enter_contactinfo')
		i18n_message=lang=='en'?'Please enter contact information!':'\u00a1Favor de ingresar la informaci\u00f3n de contacto!';
	else if(msg=='err_enter_court')
		i18n_message=lang=='en'?'Please enter a court!':'\u00a1Favor de ingresar un juzgado!';
	else if(msg=='err_enter_curp')
		i18n_message=lang=='en'?'Please enter a valid CURP!':'\u00a1Favor de ingresar un CURP v\u00e1lido!';
	else if(msg=='err_enter_defendant')
		i18n_message=lang=='en'?'Please enter defendant!':'\u00a1Favor de ingresar un demandado!';
	else if(msg=='err_enter_description')
		i18n_message=lang=='en'?'Please enter a description!':'\u00a1Favor de ingresar una descripci\u00f3n!';
	else if(msg=='err_enter_districtnum')
		i18n_message=lang=='en'?'Please enter the district number!':'\u00a1Favor de ingresar el n\u00famero de distrito!';
	else if(msg=='err_enter_email')
		i18n_message=lang=='en'?'Please enter a valid email.':'\u00a1Favor de ingresar un email v\u00e1lido.';
	else if(msg=='err_enter_enddate')
		i18n_message=lang=='en'?'Please complete the schedule! End date is missing or incorrect data.'
			:'\u00a1Favor de completar la agenda! Falta fecha de termino o tiene formato incorrecto.';
	else if(msg=='err_enter_endtime')
		i18n_message=lang=='en'?'Please complete the schedule! End time (hour/minute) is missing.'
			:'\u00a1Favor de completar la agenda! Falta hora/minuto de termino.';
	else if(msg=='err_handle_exists')
		i18n_message=lang=='en'?'Data handle already exists!':'\u00a1El dato "toca" ya existe!';
	else if(msg=='err_enter_filename')
		i18n_message=lang=='en'?'Please enter a file name! Avoid using the following characters \u2216 \u0022 / : * ? \u0022 < >'
			:'\u00a1Favor de indicar un nombre de archivo v\u00e1lido\u0021 Evite utilizar los siguientes caracteres \u2216 \u0022 / : * ? \u0022 < >';
	else if(msg=='err_enter_firmname')
		i18n_message=lang=='en'?'Please enter the firm name!':'\u00a1Favor de ingresar el nombre de la firma!';
	else if(msg=='err_enter_handle')
		i18n_message=lang=='en'?'Please enter "handle" data!':'\u00a1Favor de ingresar el dato "toca"!';
	else if(msg=='err_enter_matter')
		i18n_message=lang=='en'?'Please enter a matter!':'\u00a1Favor de ingresar una materia!';
	else if(msg=='err_enter_menutitle')
		i18n_message=lang=='en'?'Please enter a menu title!':'\u00a1Favor de ingresar un nombre de men\u00fa!';
	else if(msg=='err_enter_msg_name')
		i18n_message=lang=='en'?'Please enter a name':'\u00a1Favor de ingresar un nombre!';
	else if(msg=='err_enter_pagename')
		i18n_message=lang=='en'?'Please enter the page name!':'\u00a1Favor de ingresar el nombre de la p\u00e1gina!';
	else if(msg=='err_enter_movement')
		i18n_message=lang=='en'?'The movement field is required!':'\u00a1El campo movimiento es obligatorio!';
	else if(msg=='err_enter_notebooktype')
		i18n_message=lang=='en'?'Please select notebook type!':'\u00a1Favor de seleccionar tipo de cuaderno!';
	else if(msg=='err_enter_onedescription')
		i18n_message=lang=='en'?'Please enter at least one description!':'\u00a1Favor de ingresar al menos una descripci\u00f3n!';
	else if(msg=='err_enter_only_numbers')
		i18n_message=lang=='en'?'Please enter only numbers!':'\u00a1Favor de ingresar s\u00f3lo n\u00fameros!';
	else if(msg=='err_enter_origin_act')
		i18n_message=lang=='en'?'Please select the origin of the claimed act!':'\u00a1Favor de seleccionar el origen del acto reclamado!';
	else if(msg=='err_enter_originprot')
		i18n_message=lang=='en'?'Please enter an origin protection!':'\u00a1Favor de ingresar un amparo origen!';
	else if(msg=='err_enter_partynum')
		i18n_message=lang=='en'?'Please enter the party number!':'\u00a1Favor de ingresar el n\u00famero de partido!';
	else if(msg=='err_enter_phone')
		i18n_message=lang=='en'?'Please either enter phone number or choose "None" from the list'
			:'Favor de ingresar un n\u00famero telef\u00f3nico o seleccione de la lista "Ninguno"';
	else if(msg=='err_enter_phone_cell')
		i18n_message=lang=='en'?'Please either enter phone number or cell phone number'
			:'Favor de ingresar un n\u00famero telef\u00f3nico o de celular';
	else if(msg=='err_enter_proceedings')
		i18n_message=lang=='en'?'Please enter proceedings!':'\u00a1Favor de ingresar un expediente!';
	else if(msg=='err_enter_protnum')
		i18n_message=lang=='en'?'Please enter a protection number!':'\u00a1Favor de ingresar un n\u00famero de amparo!';
	else if(msg=='err_enter_prottype')
		i18n_message=lang=='en'?'Please select protection type!':'\u00a1Favor de seleccionar tipo de amparo!';
	else if(msg=='err_enter_recurring')
		i18n_message=lang=='en'?'Please enter recurring!':'\u00a1Favor de ingresar recurrente!';
	else if(msg=='err_enter_resolution')
		i18n_message=lang=='en'?'Please enter resolution!':'\u00a1Favor de ingresar una resoluci\u00f3n!';
	else if(msg=='err_enter_resourcenum')
		i18n_message=lang=='en'?'Please enter resource number!':'\u00a1Favor de ingresar un n\u00famero de recurso!';
	else if(msg=='err_enter_respauth')
		i18n_message=lang=='en'?'Please enter Responsible authority!':'\u00a1Favor de ingresar una autoridad responsable!';
	else if(msg=='err_enter_rfc')
		i18n_message=lang=='en'?'Please enter a valid RFC!':'\u00a1Favor de ingresar un RFC v\u00e1lido!';
	else if(msg=='err_enter_shortname')
		i18n_message=lang=='en'?'Please enter a shortname!':'\u00a1Favor de ingresar un nombre corto!';
	else if(msg=='err_enter_socnetwork')
		i18n_message=lang=='en'?'Please either enter the information about the social network selected or choose "None" from the list'
			:'Favor de ingresar el dato de la red social seleccionada o elija de la lista "Ninguno"';
	else if(msg=='err_enter_speaker')
		i18n_message=lang=='en'?'Please enter speaker!':'\u00a1Favor de ingresar un ponente!';
	else if(msg=='err_enter_startdate')
		i18n_message=lang=='en'?'Please complete the schedule! Dates or times may be missing or have incorrect data, otherwise, we suggest leaving all "Schedule Activity" data empty!'
			:'\u00a1Favor de completar los datos de la agenda! Puede que las fechas u horas contengan datos incorrectos o faltantes, de lo contrario, sugerimos dejar todos los datos de Actividad vac\u00edos.';
	else if(msg=='err_enter_starttime')
		i18n_message=lang=='en'?'Please complete the schedule! Start time (hour+minute) is missing.':'\u00a1Favor de completar la agenda! Falta hora/minuto inicial.';
	else if(msg=='err_enter_third')
		i18n_message=lang=='en'?'Please enter third party!':'\u00a1Favor de ingresar un tercero al caso!';
	else if(msg=='err_enter_title')
		i18n_message=lang=='en'?'Please enter a title!':'\u00a1Favor de ingresar un t\u00edtulo!';
	else if(msg=='err_enter_trial')
		i18n_message=lang=='en'?'Please enter trial!':'\u00a1Favor de ingresar un juicio!';
	else if(msg=='err_enter_trialtype')
		i18n_message=lang=='en'?'Please enter trial type!':'\u00a1Favor de ingresar un tipo juicio!';
	else if(msg=='err_enter_webpage')
		i18n_message=lang=='en'?'Please enter a valid web page!':'\u00a1Favor de ingresar una p\u00e1gina web v\u00e1lida!';
	else if(msg=='err_enter_zipcode')
		i18n_message=lang=='en'?'Please enter zip code!':'\u00a1Favor de ingresar c\u00f3digo postal!';
	else if(msg=='err_notification_fail')
		i18n_message=lang=='en'?'Error getting notifications, it may be because your session timed out!'
			:'\u00a1Error al obtener las notificaciones, posiblemente la sesi\u00f3n termin\u00f3.\n';
	else if(msg=='err_on_delete')
		i18n_message=lang=='en'?'Unable to delete registry!':'\u00a1No fue posible eliminar el registro!';
	else if(msg=='err_on_save')
		i18n_message=lang=='en'?'Unable to save registry!':'\u00a1No fue posible guardar el registro!';
	else if(msg=='err_opencambrowser')
		i18n_message=lang=='en'?'Unable to open camera or file browser! It may be because your session timed out. If this problem persists, please contact system support.'
			:'\u00a1No fue posible abrir la c\u00e1mara! Posiblemente la sesi\u00f3n se cerr\u00f3. Si este problema persiste, p\u00f3ngase en contacto con el soporte de sistema.';
	else if(msg=='err_record_no_saved')
		i18n_message=lang=='en'?'Fail to save the data! It may be because your session timed out'
			:'\u00a1Fallo al guardar los datos! Posiblemente la sesi\u00f3n se cerr\u00f3';
	else if(msg=='err_record_no_saved_support')
		i18n_message=lang=='en'?'Fail to save the data! It may be because your session timed out. If this problem persists, please contact system support.'
			:'\u00a1Fallo al guardar los datos! Posiblemente la sesi\u00f3n se cerr\u00f3. Si este problema persiste, p\u00f3ngase en contacto con el soporte de sistema.';
	else if(msg=='err_select_accion')
		i18n_message=lang=='en'?'Please select an action!':'\u00a1Favor de seleccionar una acci\u00f3n!';
	else if(msg=='err_select_acttype')
		i18n_message=lang=='en'?'Please select an action type!':'\u00a1Favor de seleccionar un tipo de actuaci\u00f3n!';
	else if(msg=='err_select_allrequired')
		i18n_message=lang=='en'?'Please select all data required!':'\u00a1Favor de seleccionar todos los datos obligatorios!';
	else if(msg=='err_select_appealO')
		i18n_message=lang=='en'?'Please select an origin appeal!':'\u00a1Favor de seleccionar una apelaci\u00f3n origen!';
	else if(msg=='err_select_catalog')
		i18n_message=lang=='en'?'Please select a catalog!':'\u00a1Favor de seleccionar un cat\u00e1logo!';
	else if(msg=='err_select_city')
		i18n_message=lang=='en'?'Please select a city!':'\u00a1Favor de seleccionar una ciudad!';
	else if(msg=='err_select_client')
		i18n_message=lang=='en'?'Please select a client!':'\u00a1Favor de seleccionar un cliente!';
	else if(msg=='err_select_commtype')
		i18n_message=lang=='en'?'Please select a communication type!':'\u00a1Favor de seleccionar un tipo de comunicaci\u00f3n!';
	else if(msg=='err_select_company')
		i18n_message=lang=='en'?'Please select the company!':'\u00a1Favor de seleccionar la compa\u00f1ia!';
	else if(msg=='err_select_commtype')
		i18n_message=lang=='en'?'Please select a comunication type!':'\u00a1Favor de seleccionar un tipo de comunicación!';
	else if(msg=='err_select_country')
		i18n_message=lang=='en'?'Please select a country!':'\u00a1Favor de seleccionar un pa\u00eds!';
	else if(msg=='err_select_court')
		i18n_message=lang=='en'?'Please select a court!':'\u00a1Favor de seleccionar un juzgado!';
	else if(msg=='err_select_custchar')
		i18n_message=lang=='en'?'Please select customer character!':'\u00a1Favor de seleccionar el car\u00e1cter del cliente!';
	else if(msg=='err_select_firm')
		i18n_message=lang=='en'?'Please select firm!':'\u00a1Favor de seleccionar la firma!';
	else if(msg=='err_select_label')
		i18n_message=lang=='en'?'Please select a label!':'\u00a1Favor de seleccionar una etiqueta!';
	else if(msg=='err_select_lawyer')
		i18n_message=lang=='en'?'Please enter a lawyer!':'\u00a1Favor de ingresar un abogado!';
	else if(msg=='err_select_lawyercounterpart')
		i18n_message=lang=='en'?'Please enter a lawyer counter part!':'\u00a1Favor de ingresar un abogado contraparte!';
	else if(msg=='err_select_matter')
		i18n_message=lang=='en'?'Please select a matter!':'\u00a1Favor de seleccionar una materia!';
	else if(msg=='err_select_option_list')
		i18n_message=lang=='en'?'Please select an option from the list!':'\u00a1Favor de seleccionar una opci\u00f3n de la lista!';
	else if(msg=='err_select_origintype')
		i18n_message=lang=='en'?'Please select an origin type!':'\u00a1Favor de seleccionar un tipo de origen!';
	else if(msg=='err_select_origintrial')
		i18n_message=lang=='en'?'Please select an origin trial!':'\u00a1Favor de seleccionar un juicio origen!';
	else if(msg=='err_select_persontype')
		i18n_message=lang=='en'?'Please select person type!':'\u00a1Favor de seleccionar tipo de persona!';
	else if(msg=='err_select_resourcetype')
		i18n_message=lang=='en'?'Please select a resource type!':'\u00a1Favor de seleccionar un tipo de recurso!';
	else if(msg=='err_select_room')
		i18n_message=lang=='en'?'Please enter courtroom!':'\u00a1Favor de ingresar una sala!';
	else if(msg=='err_select_socialnetwork')
		i18n_message=lang=='en'?'Please select a social network!':'\u00a1Favor de seleccionar una red social!';
	else if(msg=='err_select_status')
		i18n_message=lang=='en'?'Please select a status!':'\u00a1Favor de seleccionar un estatus!';
	else if(msg=='err_select_state')
		i18n_message=lang=='en'?'Please select a state!':'\u00a1Favor de seleccionar un estado!';
	else if(msg=='err_select_typeofperson')
		i18n_message=lang=='en'?'Please select the type of person':'\u00a1Favor de seleccionar el tipo de persona!';
	else if(msg=='err_select_trial')
		i18n_message=lang=='en'?'Please select trial!':'\u00a1Favor de seleccionar un juicio!';
	else if(msg=='err_select_trialtype')
		i18n_message=lang=='en'?'Please select trial type!':'\u00a1Favor de seleccionar un tipo de juicio!';
	else if(msg=='err_select_way')
		i18n_message=lang=='en'?'Please select a way!':'\u00a1Favor de seleccionar una v\u00eda!';
	else if(msg=='err_startDT_mayor_endDT')
		i18n_message=lang=='en'?'Either the start date or time is older than the end time':'La fecha u hora inicial es anterior a la final';
	else if(msg=='err_unable_get_activitytype')
		i18n_message=lang=='en'?'Unable to get activity type! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el tipo de actividad! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_appeal')
		i18n_message=lang=='en'?'Unable to get appeal':'\u00a1No fue posible obtener datos de la apelaci\u00f3n';
	else if(msg=='err_unable_get_calendar')
		i18n_message=lang=='en'?'Unable to load calendar data! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible cargar datos del calendario! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_circuit')
		i18n_message=lang=='en'?'Unable to get circuit! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el circuito! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_city')
		i18n_message=lang=='en'?'Unable to get city name! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener la ciudad! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_client')
		i18n_message=lang=='en'?'Unable to get client! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el cliente! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_clgcourt')
		i18n_message=lang=='en'?'Unable to get collegiate court! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el tribunal colegiado! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_commlabel')
		i18n_message=lang=='en'?'Unable to get communication label! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener la etiqueta de comunicaci\u00f3n! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_commtype')
		i18n_message=lang=='en'?'Unable to get the "communication type"! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el "tipo de comunicaci\u00f3n!" Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_company')
		i18n_message=lang=='en'?'Unable to get company! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener la compa\u00f1ia! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_consultations')
		i18n_message=lang=='en'?'Unable to get consultations! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener las consultas! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_country')
		i18n_message=lang=='en'?'Unable to get country! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el pa\u00eds! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_court')
		i18n_message=lang=='en'?'Unable to get court name! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el nombre del juzgado! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_federalcourt')
		i18n_message=lang=='en'?'Unable to get federal court! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el juzgado federal! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_firm')
		i18n_message=lang=='en'?'Unable to get firm data! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener la firma! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_jrdorgan')
		i18n_message=lang=='en'?'Unable to get jurisdictional organ! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el \u00f3rgano jurisdiccional! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_judges')
		i18n_message=lang=='en'?'Unable to get judge names! We suggest to re-login or contact your administrator'
				:'\u00a1No fue posible obtener los jueces! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_lawyer')
		i18n_message=lang=='en'?'Unable to get lawyer! We suggest to re-login or contact your administrator'
				:'\u00a1No fue posible obtener el abogado! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_matter')
		i18n_message=lang=='en'?'Unable to get matter! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener la materia! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_menu')
		i18n_message=lang=='en'?'Unable to get menu data! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el men\u00fa! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_module')
		i18n_message=lang=='en'?'Unable to get module data! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el m\u00f3dulo! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_movement')
		i18n_message=lang=='en'?'Unable to get movement data! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el registro de movimiento! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_notifications')
		i18n_message=lang=='en'?'Unable to get notifications!':'\u00a1No fue posible obtener las notificaciones!';
	else if(msg=='err_unable_get_organtype')
		i18n_message=lang=='en'?'Unable to get organ type! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el tipo de \u00f3rgano! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_protections')
		i18n_message=lang=='en'?'Unable to get potections! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener amparos! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_region')
		i18n_message=lang=='en'?'Unable to get region! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener la regi\u00f3n! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_resource')
		i18n_message=lang=='en'?'Unable to get the resource! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el recurso! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_room')
		i18n_message=lang=='en'?'Unable to get courtroom! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener la sala! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_state')
		i18n_message=lang=='en'?'Unable to get state! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el estado! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_socialnetwork')
		i18n_message=lang=='en'?'Unable to get the social network! We suggest to re-login or contact your administrator':
			'\u00a1No fue posible obtener la red social! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_trial')
		i18n_message=lang=='en'?'Unable to get trial information! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener datos del juicio! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_trialtype')
		i18n_message=lang=='en'?'Unable to get trial type information! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener los datos del tipo de juicio! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_unitarycourt')
		i18n_message=lang=='en'?'Unable to get unitary court! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener el tribunal unitario! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_get_way')
		i18n_message=lang=='en'?'Unable to get way! We suggest to re-login or contact your administrator'
			:'\u00a1No fue posible obtener la v\u00eda! Sugerimos reiniciar sesión o contactar a su administrador';
	else if(msg=='err_unable_saveby_privileges')
		i18n_message=lang=='en'?'Unable to save data! Please, ask to System Administrator about privileges.'
			:'\u00a1No fue posible guardar la informaci\u00f3n! Favor de consultar al administrador para ver privilegios';
	else if(msg=='err_unable_save_orby_session')
		i18n_message=lang=='en'?'Unable to save data! maybe either your session expired or invalid data.'
			:'\u00a1No fue posible guardar la informaci\u00f3n! posiblemente su sessión expir\u00f3 o alg\u00fan dato es incorrecto';
	else if(msg=='err_user_exists')
		i18n_message=lang=='en'?'The user already exists!':'\u00a1El usuario ya existe!';
	else if(msg=='err_website_not_valid')
		i18n_message=lang=='en'?'website is not valid!':'\u00a1P\u00e1gina web no es v\u00e1lida!';

	else if(msg=='max_files_exceeded')
		i18n_message=lang=='en'?'Not can upload more files.':'No es posible subir m\u00e1s archivos.';
	else if(msg=='confirm_delete_file')
		i18n_message=lang=='en'?'In addition to deleting the file, this change is saved and you will no longer be able to recover it. Do you want to continue?.'
  			:'Adem\u00e1s de eliminar el archivo, se guarda este cambio y ya no podr\u00e1 recuperarlo \u00bfdesea continuar?';
	else if(msg=='delete_label')
		i18n_message=lang=='en'?'Delete':'Eliminar';	
	else if(msg=='msg_upload_area')
		i18n_message=lang=='en'?'Drag and drop the file into the dotted area':'Arrastra y suelta el archivo en la zona punteada';

	else if(msg=='agreementdate')
		i18n_message=lang=='en'?'Agreement date':'Fecha de acuerdo';
	else if(msg=='filingdate')
		i18n_message=lang=='en'?'Filing date':'Fecha de presentaci\u00f3n'
	else if(msg=='notificationdate')
        i18n_message=lang=='en'?'Notification date':'Fecha de notificaci\u00f3n';
	else if(msg=='writtendocument')
		i18n_message=lang=='en'?'Written documento':'Escrito';
	else if(msg=='officialletter')
		i18n_message=lang=='en'?'Official Letter':'Oficio';
	else if(msg=='accord')
		i18n_message=lang=='en'?'Accord':'Acuerdo';
	else if(msg=='judgment')
		i18n_message=lang=='en'?'Judgment':'Sentencia';
	return i18n_message;
}