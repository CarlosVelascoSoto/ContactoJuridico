function i18n(info){
	var i18n_info='', lang=getLanguageURL();
	// IDIOMAS:si en la URL no contiene la variable 'lenguage', asumir\u00e1 que esta en espa\u00f1ol
	if(info=='abogado')
		i18n_info=lang=='en'?'Lawyer':'Abogado';
	else if(info=='abogadoasignado')
		i18n_info=lang=='en'?'Lawyer assigned':'Abogado asignado';
	else if(info=='abogadocontraparte')
		i18n_info=lang=='en'?'Lawyer counter part':'Abogado contraparte';
	else if(info=='action')
		i18n_info=lang=='en'?'Action':'Acci\u00f3n';
	else if(info=='actor')
		i18n_info=lang=='en'?'Actor':'Actor';
	else if(info=='actoreclamado')
		i18n_info=lang=='en'?'Claimed act':'Acto reclamado';
	else if(/^address/.test(info))
		i18n_info=lang=='en'?'Address':'Direcci\u00f3n';
	else if(info=='amparo')
		i18n_info=lang=='en'?'Protection':'Amparo';
	else if(info=='amparotipo')
		i18n_info=lang=='en'?'Protection type':'Tipo de amparo';
	else if(info=='apelacionadhesiva')
		i18n_info=lang=='en'?'Adhesive appeal':'Apelaci\u00f3n adhesiva';
	else if(info=='apelado')
		i18n_info=lang=='en'?'Respondent':'Apelado';
	else if(info=='apelante')
		i18n_info=lang=='en'?'Appellant':'Apelante';
	else if(info=='appointment')
		i18n_info=lang=='en'?'Appointment':'Cita';
	else if(info=='assignedvalue')
		i18n_info=lang=='en'?'Assigned value':'Valor asignado';
	else if(info=='autoridadresponsable')
		i18n_info=lang=='en'?'Responsible authority':'Autoridad responsable';
	else if(info=='birthdate')
		i18n_info=lang=='en'?'Birthdate':'Cumplea\u00f1os';
	else if(info=='catalogtype')
		i18n_info=lang=='en'?'Catalog type':'Tipo de cat\u00e1logo';
	else if(info=='cellphone')
		i18n_info=lang=='en'?'Mobile phone':'N\u00famero de celular';
	else if(info=='circuito')
		i18n_info=lang=='en'?'Circuit':'Circuito';
	else if(info=='city'||info=='ciudad')
		i18n_info=lang=='en'?'City':'Ciudad';
	else if(info=='client')
		i18n_info=lang=='en'?'Client':'Cliente';
	else if(info=='clientecaracter')
		i18n_info=lang=='en'?'Customer character':'Car\u00e1cter del cliente';
	else if(info=='comments')
		i18n_info=lang=='en'?'Comments':'Comentarios';
	else if(/^(com)+(.*)(label)+/.test(info))
		i18n_info=lang=='en'?'Communication label':'Etiqueta de comunicaci\u00f3n';
	else if(info=='company'||info=='company_name')
		i18n_info=lang=='en'?'Firm':'Firma';
	else if(info=='confirmationdate')
		i18n_info=lang=='en'?'Confirmation date':'Fecha de confirmaci\u00f3n';
	else if(info=='contactperson')
		i18n_info=lang=='en'?'Contact person':'Persona de contacto';
	else if(info=='country'||info=='pais')
		i18n_info=lang=='en'?'Country':'Pa\u00eds';
	else if(info=='created')
		i18n_info=lang=='en'?'Created':'Creado';
	else if(info=='cuaderno')
		i18n_info=lang=='en'?'Notebook':'Cuaderno';
	else if(info=='dateend'||info=='enddate')
		i18n_info=lang=='en'?'End date':'Fecha final';
	else if(info=='dateini'||info=='startdate')
		i18n_info=lang=='en'?'Start date':'Fecha inicial';
	else if(info=='demandaamparoturnadaa')
		i18n_info=lang=='en'?'Demand for protection turned to'
			:'Demanda de amparo turnada a';
	else if(info=='demandado')
		i18n_info=lang=='en'?'Defendant':'Demandado';
	else if(/^descripcion(en)?/.test(info))
		i18n_info=lang=='en'?'Description':'Descripci\u00f3n';
	else if(info=='distrito')
		i18n_info=lang=='en'?'District':'Distrito';
	else if(info=='email')
		i18n_info=lang=='en'?'Email':'Correo electr\u00f3nico';
	else if(info=='emailexternaluser')
		i18n_info=lang=='en'?'External user email':'Correo electr\u00f3nico de usuario externo';
	else if(info=='estado'||info=='state')
		i18n_info=lang=='en'?'State':'Estado';
	else if(info=='fechaactoreclamado')
		i18n_info=lang=='en'?'Claimed act date':'Fecha acto reclamado';
	else if(info=='fechaacuerdo')
		i18n_info=lang=='en'?'Agreement date':'Fecha de acuerdo';
	else if(info=='fechaamparodirectoadhesivo')
		i18n_info=lang=='en'?'Adhesive protection date':'Fecha de amparo adhesivo';
	else if(info=='fechaaudicienciaconstitucional')
		i18n_info=lang=='en'?'Constitutional hearing date':'Fecha audiencia constitucional';
	else if(info=='fechaaudienciaincidental')
		i18n_info=lang=='en'?'Incidental hearing date':'Fecha audiencia incidental';
	else if(info=='fechadmision')
		i18n_info=lang=='en'?'Admission date':'Fecha de admisi\u00f3n';
	else if(info=='fechanotificacion')
		i18n_info=lang=='en'?'Notification date':'Fecha de notificaci\u00f3n';
	else if(info=='fechanotificacionactoreclamado')
		i18n_info=lang=='en'?'Notification date of the claimed act'
			:'Fecha de notificaci\u00f3n del acto reclamado';
	else if(info=='fechanotificaciondmision')
		i18n_info=lang=='en'?'Admission notification date'
			:'Fecha de notificaci\u00f3n de admisi\u00f3n';
	else if(info=='fechanotificacionsentencia')
		i18n_info=lang=='en'?'Judgment notification date'
			:'Fecha de notificaci\u00f3n de sentencia';
	else if(info=='fechapresentacion')
		i18n_info=lang=='en'?'Filing date':'Fecha de presentaci\u00f3n';
	else if(info=='fechapresentaciondemanda')
		i18n_info=lang=='en'?'Filing date of the lawsuit'
			:'Fecha de presentaci\u00f3n de demanda';
	else if(info=='fecharecursorevision')
		i18n_info=lang=='en'?'Review resource date':'Fecha de revisi\u00f3n de recurso';
	else if(info=='fechasentencia')
		i18n_info=lang=='en'?'Judgment date':'Fecha de sentencia';
	else if(info=='fechasesionproyectosentencia')
		i18n_info=lang=='en'?'Session date project resolution'
			:'Fecha de sesi\u00f3n proyecto de resoluci\u00f3n';
	else if(info=='fechaturnoaponencia')
		i18n_info=lang=='en'?'Shift to presentation date':'Fecha turno a ponencia';
	else if(info=='filename')
		i18n_info=lang=='en'?'File name':'Nombre de archivo';
	else if(info=='first_name'||info=='name'||info=='nombre')
		i18n_info=lang=='en'?'First name':'Nombre';
	else if(/^image/.test(info))
		i18n_info=lang=='en'?'Image':'Imagen';
	else if(info=='incidental')
		i18n_info=lang=='en'?'Incidental':'Incidental';
	else if(info=='juicio')
		i18n_info=lang=='en'?'Trial':'Juicio';
	else if(info=='juzgado')
		i18n_info=lang=='en'?'Court':'Juzgado';
	else if(info=='language')
		i18n_info=lang=='en'?'Language':'Idioma';
	else if(info=='last_name')
		i18n_info=lang=='en'?'Last name':'Apellido';
	else if(info=='materia')
		i18n_info=lang=='en'?'Matter':'Materia';
	else if(info=='movimiento')
		i18n_info=lang=='en'?'Movement':'Movimiento';
	else if(info=='notificationdate')
		i18n_info=lang=='en'?'Notification date':'Fecha de notificaci\u00f3n';
	else if(info=='obligatorio')
		i18n_info=lang=='en'?'Required':'Obligatorio';
	else if(info=='observaciones')
		i18n_info=lang=='en'?'Observations':'Observaciones';
	else if(/^organo[s]?/.test("organo"))
		i18n_info=lang=='en'?'Organ':'\u00d3rgano';
	else if(info=='partido')
		i18n_info=lang=='en'?'Party':'Partido';
	else if(info=='principal')
		i18n_info=lang=='en'?'Principal':'Principal';
	else if(info=='prueba')
		i18n_info=lang=='en'?'Evidences':'Prueba';
	else if(/^phone/.test(info))
		i18n_info=lang=='en'?'Phone':'Tel\u00e9fono';
	else if(info=='photo'||info=='photo_name')
		i18n_info=lang=='en'?'Photo':'Fotograf\u00eda';
	else if(info=='ponente')
		i18n_info=lang=='en'?'Speaker':'Ponente';
	else if(info=='quejoso')
		i18n_info=lang=='en'?'Complaining':'Quejoso';
	else if(info=='recursorevisioncontrasentencia')
		i18n_info=lang=='en'?'Resource review against judgment'
			:'Recurso revisi\u00f3n contra sentencia';
	else if(info=='requiereactor')
		i18n_info=lang=='en'?'Actor required':'Requiere actor';
	else if(info=='requieredemandado')
		i18n_info=lang=='en'?'Requires defendant':'Requiere demandado';
	else if(info=='requieretercero')
		i18n_info=lang=='en'?'Third party required':'Requiere tercero interesado';
	else if(info=='resolucion')
		i18n_info=lang=='en'?'Resolution':'Resoluci\u00f3n';
	else if(info=='rfc')
		i18n_info=lang=='en'?'RFC':'RFC';
	else if(info=='role')
		i18n_info=lang=='en'?'Role':'Rol';
	else if(info=='sala')
		i18n_info=lang=='en'?'Room':'Sala';
	else if(info=='sentencia')
		i18n_info=lang=='en'?'Judgment':'Sentencia';
	else if(info=='sentenciadefinitiva')
		i18n_info=lang=='en'?'Adjournment judgment':'Suspensi\u00f3n definitiva';
	else if(info=='shareddate')
		i18n_info=lang=='en'?'Date on which user was notificated for \'received\' status'
			:'Fecha de notifici\u00f3n de \'recibido\' al usuario';
	else if(info=='shortname')
		i18n_info=lang=='en'?'Short name':'Nombre corto';
	else if(/^socialnetwork/.test(info))
		i18n_info=lang=='en'?'Social network':'Red social';
	else if(info=='status')
		i18n_info=lang=='en'?'Status':'Estatus';
	else if(info=='suspension')
		i18n_info=lang=='en'?'Suspension':'Suspensi\u00f3n';
	else if(info=='suspensionprovisional')
		i18n_info=lang=='en'?'Provisional suspension':'Suspensi\u00f3n provisional';
	else if(info=='tercero')
		i18n_info=lang=='en'?'Interested third party':'Tercero interesado';
	else if(info=='tipoactuacion')
		i18n_info=lang=='en'?'Action type':'Tipo de actuaci\u00f3n';
	else if(info=='tipodemandaturnadaa')
		i18n_info=lang=='en'?'Type of demand referred to':'Tipo de demanda turnada a';
	else if(/^tipojuicio/.test(info))
		i18n_info=lang=='en'?'Trial type':'Tipo de juicio';
	else if(info=='tipojuzgado')
		i18n_info=lang=='en'?'Court type':'Tipo de juzgado';
	else if(info=='tipoorgano')
		i18n_info=lang=='en'?'Organ type':'Tipo de \u00f3rgano';
	else if(/^titulo/.test(info))
		i18n_info=lang=='en'?'Title':'T\u00edtulo';
	else if(info=='toca')
		i18n_info=lang=='en'?'Handle':'Toca';
	else if(info=='updated')
		i18n_info=lang=='en'?'Updated':'Actualizado';
	else if(info=='username')
		i18n_info=lang=='en'?'User name':'Nombre de usuario';
	else if(info=='usertype')
		i18n_info=lang=='en'?'User type':'Tipo de usuario';
	else if(info=='usuarioidautoriza')
		i18n_info=lang=='en'?'Authorizing user':'Usuario autoriza';
	else if(/^via/.test(info))
		i18n_info=lang=='en'?'Way':'V\u00eda';
	else if(info=='webpage')
		i18n_info=lang=='en'?'Web page':'P\u00e1gina web';
	else if(info=='zipcode')
		i18n_info=lang=='en'?'Zip code':'C\u00f3digo postal';

	return i18n_info;
}