CREATE TABLE uploadfiles (
    uploadfileid integer NOT NULL,
    path character varying,
    catalogtype integer,
    filename character varying,
    idregister integer not null, 
    CONSTRAINT uploadfileid_pkey PRIMARY KEY (uploadfileid)
);

comment on column uploadfiles.catalogtype is '1 = Juicios, 2 = Apelaciones, 3 = Amparos derivados, 4 = Amparos indirectos, 5 = Recursos';
comment on column uploadfiles.idregister is 'id del registro correspondiente al catalogo';
ALTER TABLE uploadfiles OWNER TO postgres;

CREATE SEQUENCE uploadfiles_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE uploadfiles_seq
  OWNER TO postgres;
  
  
 -- 28/09/2020 scripts para modulos de menus 
 /*
  vis = vista
  ctr = control
  newadd = agregar
  edt = editar
  del = eliminar
  pcs = 
  cnf = configuracion
  --> 
  * */
 
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS privileges;
DROP TABLE IF EXISTS menuprivileges;

 CREATE TABLE menu (
    menuid integer NOT NULL,
    menu character varying,
    menuparentid integer NOT NULL,
    icon character varying,
    link character varying,
    tipomenu integer NOT NULL,
    orden integer not null,
    CONSTRAINT menu_pkey PRIMARY KEY (menuid)
);
comment on column menu.tipomenu is '1 = menu superior, 2 = menu lateral';
ALTER TABLE menu OWNER TO postgres;


 CREATE TABLE privileges (
    privilegesid integer NOT NULL,
    privilege character varying,     
    CONSTRAINT privileges_pkey PRIMARY KEY (privilegesid)
);
ALTER TABLE privileges OWNER TO postgres;


CREATE TABLE menuprivileges (
    menuprivilegesid integer NOT NULL,
    privilegesid integer NOT NULL,
    menuid integer NOT NULL,
    roleid integer NOT NULL,
    CONSTRAINT menuprivileges_pkey PRIMARY KEY (menuprivilegesid)
);
ALTER TABLE menuprivileges OWNER TO postgres;


CREATE SEQUENCE menuid_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE menuid_seq
  OWNER TO postgres;
  
    
CREATE SEQUENCE menuprivileges_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE menuprivileges_seq
  OWNER TO postgres;
  
  
-- grupos lateral
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(1,'Dashboard',0,'ti-home','home.jet',2,1);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(2,'Calendario',0,'ti-calendar','ecalendar.jet',2,2);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(3,'Juicios (Submenu)',0,'ti-filter','',2,3);
-- menus lateral
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(4,'Amparo derivado de un Juicio',3,'ti-layout-tab','protections.jet',2,1);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(5,'Recursos',3,'ti-layout-accordion-merged','resources.jet',2,2);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(6,'Amparos indirectos',3,'ti-layout-tab-v','indprotections.jet',2,3);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(7,'Apelaciones',3,'ti-agenda','apelaciones.jet',2,4);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(8,'Juicios',3,'ti-filter','juicios.jet',2,5);
-- menu top
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(9,'Campa�ias',0,'ti-user','companies',1,1);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(10,'Privileges',0,'ti-id-badge','privileges',1,2);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(11,'Email Settings',0,'ti-email',' emailsettings',1,3);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(12,'Roles',0,'ti-id-badge','roles',1,4);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(13,'Campaigns',0,'ti-widget','campaigns',1,5);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(14,'Clientes',0,'ti-user','clients',1,6);
insert into menu (menuid, menu, menuparentid,icon, link,tipomenu,orden)values(15,'Usuarios',0,'ti-user','addnewuser',1,7);

ALTER SEQUENCE menuid_seq RESTART WITH 16;

insert into privileges (privilegesid, privilege) values (1, 'Crear');
insert into privileges (privilegesid, privilege) values (2, 'Editar');
insert into privileges (privilegesid, privilege) values (3, 'Eliminar');
insert into privileges (privilegesid, privilege) values (4, 'Vista');
ALTER SEQUENCE privileges_seq RESTART WITH 5;

ALTER SEQUENCE menuprivileges_seq RESTART WITH 10;
insert into menuprivileges (menuprivilegesid, privilegesid, menuid, roleid) values (1, 4, 1, 1); -- Dashboard
insert into menuprivileges (menuprivilegesid, privilegesid, menuid, roleid) values (2, 1, 2, 1); -- Calendario
insert into menuprivileges (menuprivilegesid, privilegesid, menuid, roleid) values (3, 2, 2, 1); -- Calendario
insert into menuprivileges (menuprivilegesid, privilegesid, menuid, roleid) values (4, 3, 2, 1); -- Calendario
insert into menuprivileges (menuprivilegesid, privilegesid, menuid, roleid) values (5, 4, 2, 1); -- Calendario
insert into menuprivileges (menuprivilegesid, privilegesid, menuid, roleid) values (6, 1, 15, 1); -- usuario menu top
insert into menuprivileges (menuprivilegesid, privilegesid, menuid, roleid) values (7, 2, 15, 1); -- usuario menu top
insert into menuprivileges (menuprivilegesid, privilegesid, menuid, roleid) values (8, 3, 15, 1); -- usuario menu top
insert into menuprivileges (menuprivilegesid, privilegesid, menuid, roleid) values (9, 4, 15, 1); -- usuario menu top


-- 01/Abr/2021	Script para relacionar el cliente con los amparos

ALTER TABLE amparos ADD COLUMN companyclientid integer;
ALTER TABLE amparos ADD CONSTRAINT amparos_companyclientid_fkey FOREIGN KEY (companyclientid) REFERENCES companyclients(companyclientid);

-- 08/Abr/2021	Script para capturar el tipo de cuaderno
ALTER TABLE movimientos ADD COLUMN cuaderno varchar;

-- 15/Abr/2021 Complemento para redes sociales
ALTER TABLE socialnetworks ADD COLUMN mainurl varchar;
ALTER TABLE socialnetworks ADD COLUMN imageurl varchar;

-- 15/Abr/2021 Complemento para incluir página web en el cliente
ALTER TABLE clients ADD COLUMN webpage varchar;

-- 23/Abr/2021 Complemento para capturar el nombre de contacto
ALTER TABLE clients ADD COLUMN contacto varchar;

-- 03/May/2021 Complemento para asignar el abogado principal (userid)
	ALTER TABLE juicios ADD COLUMN userid integer;
	ALTER TABLE juicios ADD CONSTRAINT juicios_userid_fkey FOREIGN KEY (userid) REFERENCES users(id);

	-- Nueva tabla para compartir expedientes
	CREATE TABLE shareddockets (
		shareddocketid integer NOT NULL,
		juicioid integer,
		usuarioidautoriza integer,
		emailexternaluser varchar,
		userid integer,
		shareddate timestamp without time zone,
		confirmationdate timestamp without time zone,
		notificationdate timestamp without time zone
	);
	ALTER TABLE shareddockets OWNER TO postgres;
	
	CREATE SEQUENCE shareddockets_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1;
	ALTER TABLE shareddockets_seq OWNER TO postgres;

-- 11/May/2021 Cambio de fkey para no obligar al usuario la captura de todas los campos de redes sociales.
	ALTER TABLE companies DROP CONSTRAINT companies_socialnetworkid1_fkey;
    ALTER TABLE companies DROP CONSTRAINT companies_socialnetworkid2_fkey;
    ALTER TABLE companies DROP CONSTRAINT companies_socialnetworkid3_fkey;
    ALTER TABLE companies DROP CONSTRAINT companies_socialnetworkid4_fkey;
	
	
-- 25/May/2021 Para filtrado de juzgados de acuerdo a la ciudad
ALTER TABLE juzgados ADD COLUMN ciudadid integer;

-- 31/May/2021 Para compartir expedientes.
ALTER TABLE menuprivileges ADD COLUMN shareddocketid integer;

-- 20/Jul/2021
	-- Cambio de nombre de columna
	ALTER TABLE paises RENAME ciudad TO pais;
	ALTER SEQUENCE paisid_seq RESTART WITH 4;

	-- Nueva tabla de estados
	CREATE TABLE estados (
		estadoid integer NOT NULL,
		estado character varying NOT NULL,
		paisid integer NOT NULL,
		companyid integer,
		CONSTRAINT estados_pkey PRIMARY KEY (estadoid)
	);
	ALTER TABLE estados OWNER TO postgres;

	CREATE SEQUENCE estados_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE estados_seq OWNER TO postgres;
	ALTER TABLE estados ADD CONSTRAINT estados_paisid_fkey FOREIGN KEY (paisid) REFERENCES paises(paisid);

	-- Relación de ciudades y estados
	ALTER TABLE ciudades ADD COLUMN estadoid integer;
	ALTER TABLE ciudades ADD CONSTRAINT ciudades_estadoid_fkey FOREIGN KEY (estadoid) REFERENCES estados(estadoid);

	-- Órganos jurisdiccionales
	CREATE TABLE organos(
		organoid integer NOT NULL,
		organo character varying NOT NULL,
		ciudadid int NOT NULL,
		CONSTRAINT organos_pkey PRIMARY KEY (organoid)
	);
	ALTER TABLE organos OWNER TO postgres;

	CREATE SEQUENCE organos_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE organos_seq OWNER TO postgres;
	ALTER TABLE organos ADD CONSTRAINT organos_ciudadid_fkey FOREIGN KEY (ciudadid) REFERENCES ciudades(ciudadid);

	-- Circuitos
	CREATE TABLE circuitos(
		circuitoid integer NOT NULL,
		circuito character varying NOT NULL,
		estadoid integer NOT NULL,
		CONSTRAINT circuitos_pkey PRIMARY KEY (circuitoid)
	);
	ALTER TABLE circuitos OWNER TO postgres;

	CREATE SEQUENCE circuitos_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE circuitos_seq OWNER TO postgres;
	ALTER TABLE circuitos ADD CONSTRAINT circuitos_estadoid_fkey FOREIGN KEY (estadoid) REFERENCES estados(estadoid);

	-- Regiones
	CREATE TABLE regiones(
		regionid integer NOT NULL,
		region character varying NOT NULL,
		CONSTRAINT regiones_pkey PRIMARY KEY (regionid)
	);
	ALTER TABLE regiones OWNER TO postgres;

	CREATE SEQUENCE regiones_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE regiones_seq OWNER TO postgres;

	-- Tipo de órganos
	CREATE TABLE tipoorganos(
	  tipoorganoid integer NOT NULL,
	  tipoorgano character varying NOT NULL,
	  CONSTRAINT tipoorganos_pkey PRIMARY KEY (tipoorganoid)
	);
	ALTER TABLE tipoorganos OWNER TO postgres;
	
	CREATE SEQUENCE tipoorganos_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1
	;
	ALTER TABLE tipoorganos_seq OWNER TO postgres;

-- 03/Ago/2021
	-- Identificar el tipo de juzgado
	ALTER TABLE juzgados ADD COLUMN tipojuzgado integer;

-- 05/Ago/2021
	-- Relación de ciudadid para tribunales
	ALTER TABLE tribunalcolegiado ADD COLUMN ciudadid integer;
	ALTER TABLE tribunalunitario ADD COLUMN ciudadid integer;
	
	ALTER TABLE tribunalcolegiado ADD CONSTRAINT tribunalcolegiado_ciudadid_fkey FOREIGN KEY (ciudadid) REFERENCES ciudades(ciudadid);
	ALTER TABLE tribunalunitario ADD CONSTRAINT tribunalunitario_ciudadid_fkey FOREIGN KEY (ciudadid) REFERENCES ciudades(ciudadid);

	ALTER TABLE amparos ADD COLUMN tipodemandaturnadaa character varying;

-- 20/Ago/2021
	-- Relación de recursos a un documento de amparo
	ALTER TABLE recursos ADD COLUMN tipoorigen integer;
	ALTER TABLE recursos ADD COLUMN tipoorigenid integer;

-- 31/Ago/2021
	-- Tipo de juicios
	CREATE TABLE tipojuicios(
		tipojuicioid integer NOT NULL,
		tipojuicio character varying NOT NULL,
		tipojuicioen character varying,
		materiaid integer NOT NULL,
		requiereactor integer,
		requieredemandado integer,
		requieretercero integer,
		CONSTRAINT tipojuicios_pkey PRIMARY KEY (tipojuicioid)
	);
	ALTER TABLE tipojuicios OWNER TO postgres;

	CREATE SEQUENCE tipojuicios_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE tipojuicios_seq OWNER TO postgres;
	ALTER TABLE tipojuicios ADD CONSTRAINT tipojuicios_materiaid_fkey FOREIGN KEY (materiaid) REFERENCES materias(materiaid);

	-- Columnas personalizadas para el Tipo de Juicio
	CREATE TABLE customcolumns(
		customcolumnid integer NOT NULL,
		titulo character varying NOT NULL,
		descripcion character varying,
		obligatorio integer,
		tituloen character varying,
		descripcionen character varying,
		tipodecolumna character varying NOT NULL,
		ligadoatabla character varying NOT NULL,
		nombrepk character varying NOT NULL,
		idpkreferenced integer NOT NULL,
		longitud integer,
		regex character varying,
		masdeuno integer,
		CONSTRAINT customcolumns_pkey PRIMARY KEY (customcolumnid)
	);
	ALTER TABLE customcolumns OWNER TO postgres;
	CREATE SEQUENCE customcolumns_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE customcolumns_seq OWNER TO postgres;

	-- Relación de datos "customcolumns" y su valor
	CREATE TABLE customcolumnsvalues(
		customcolumnvalueid integer NOT NULL,
		customcolumnid integer NOT NULL,
		assignedvalue character varying NOT NULL,
		savedon character varying NOT NULL,
		idreferenced integer NOT NULL,
		CONSTRAINT customcolumnsvalues_pkey PRIMARY KEY (customcolumnvalueid)
	);
	ALTER TABLE customcolumnsvalues OWNER TO postgres;
	CREATE SEQUENCE customcolumnsvalues_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE customcolumnsvalues_seq OWNER TO postgres;
	ALTER TABLE customcolumnsvalues ADD CONSTRAINT customcolumnsvalues_customcolumnid_fkey FOREIGN KEY (customcolumnid) REFERENCES customcolumns(customcolumnid);

-- 14/Sep/2021
	-- Relación de acciones para los tipos de juicios
	CREATE TABLE tipojuiciosaccion (
		accionid integer NOT NULL,
		descripcion character varying NOT NULL,
		descripcionen character varying,
		materiaid integer NOT NULL,
		viaid integer,
		CONSTRAINT tipojuiciosaccion_pkey PRIMARY KEY (accionid)
	);
	ALTER TABLE tipojuiciosaccion OWNER TO postgres;
	CREATE SEQUENCE tipojuiciosaccion_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE tipojuiciosaccion_seq OWNER TO postgres;
	
	-- Ajuste de relacion materiaid por descripción general de tipo de juicio.
	ALTER TABLE tipojuicios RENAME COLUMN materiaid TO tipojuiciogeneralid;

	-- Gestión de vías
	CREATE TABLE vias (
		viaid integer NOT NULL,
		via character varying NOT NULL,
		viaen character varying,
		CONSTRAINT vias_pkey PRIMARY KEY (viaid)
	);
	ALTER TABLE vias OWNER TO postgres;
	CREATE SEQUENCE vias_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE vias_seq OWNER TO postgres;
	
	ALTER TABLE juicios DROP CONSTRAINT juicios_juiciotipoid_fkey;

-- 30/Sep/2021
	-- Ajuste por cambio de nombre en columna
	ALTER TABLE tipojuicios DROP CONSTRAINT tipojuicios_materiaid_fkey;

-- 12/Oct/2021
	-- Etiquetas de comunicación
	CREATE TABLE communicationlabels (
		commlabelid integer NOT NULL,
		commlabelname character varying NOT NULL,
		CONSTRAINT communicationlabels_pkey PRIMARY KEY (commlabelid)
	);
	ALTER TABLE communicationlabels OWNER TO postgres;
	CREATE SEQUENCE communicationlabels_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE communicationlabels_seq OWNER TO postgres;
	
	-- Captura de datos fiscales
	CREATE TABLE fiscalsdata (
		fiscaldataid integer NOT NULL,
		origintype integer NOT NULL,
		originid integer NOT NULL,
		businessname character varying NOT NULL,
		commercialname character varying,
		personafiscalid integer NOT NULL,
		rfc character varying NOT NULL,
		curp character varying,
		samedata integer,
		address1 character varying NOT NULL,
		address2 character varying,
		address3 character varying,
		zipcode character varying NOT NULL,
		ciudadid integer NOT NULL,
		startdate timestamp without time zone,
		enddate timestamp without time zone,
		CONSTRAINT fiscalsdata_pkey PRIMARY KEY (fiscaldataid)
	);
	ALTER TABLE fiscalsdata OWNER TO postgres;
	CREATE SEQUENCE fiscalsdata_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE fiscalsdata_seq OWNER TO postgres;
	
	-- Captura de datos adicionales de comunicación
	ALTER TABLE companies 
		ADD COLUMN communicationlabel1 integer,
		ADD COLUMN phone2 character varying,
		ADD COLUMN communicationlabel2 integer,
		ADD COLUMN phone3 character varying,
		ADD COLUMN communicationlabel3 integer,
		ADD COLUMN webpage character varying
	;
	
	-- Captura de datos adicionales de comunicación
	ALTER TABLE clients
		ADD COLUMN address2 character varying,
		ADD COLUMN address3 character varying,
		ADD COLUMN branchname character varying,
		ADD COLUMN communicationlabel1 integer,
		ADD COLUMN phone2 character varying,
		ADD COLUMN communicationlabel2 integer,
		ADD COLUMN phone3 character varying,
		ADD COLUMN communicationlabel3 integer,
		ADD COLUMN startdate timestamp without time zone,
		ADD COLUMN enddate timestamp without time zone,
		ADD COLUMN changedbyid integer
	;

-- 28/Oct/2021
	-- Definición de partidos o distritos
	ALTER TABLE juzgados
		ADD COLUMN materiaid integer,
		ADD COLUMN partido integer,
		ADD COLUMN distrito character varying
	;

	-- Relación de jueces con el juzgado
	CREATE TABLE jueces (
		juezid integer NOT NULL,
		nombre character varying NOT NULL,
		juzgadoid integer NOT NULL,
		CONSTRAINT jueces_pkey PRIMARY KEY (juezid)
	);
	ALTER TABLE jueces OWNER TO postgres;
	CREATE SEQUENCE jueces_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE jueces_seq OWNER TO postgres;

-- 11/Nov/2021
	-- Asignación de Juez
	ALTER TABLE juicios ADD COLUMN juezid integer;

-- 09/Feb/2022
	ALTER TABLE users ADD COLUMN zipcode integer;
	ALTER TABLE users ADD COLUMN cellphone character varying;

-- 05/Abr/2022
	-- Notificaciones
	CREATE TABLE notifications(
		notificationid integer NOT NULL,
		userid integer NOT NULL,
		actiontypeid integer NOT NULL,
		capturedate timestamp without time zone,
		moduleref integer NOT NULL,
		reference character varying,
		actionsdetails character varying,
		confirmations character varying,
		companyclientid integer,
		referenceid integer,
		CONSTRAINT notifications_userid_fkey FOREIGN KEY (userid) REFERENCES users(id)
	);
	ALTER TABLE notifications OWNER TO postgres;

	CREATE SEQUENCE notifications_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE notifications_seq OWNER TO postgres;
	
-- Columnas relacionadas
	CREATE TABLE relatedcolumns(
		relcolumnid integer NOT NULL,
		columnname1 character varying NOT NULL,
		columnname2 character varying,
		messagejs character varying,
		messagejsp character varying,
		relfromtable character varying NOT NULL,
		relfromcolumn character varying NOT NULL,
		relfromdescription character varying,
		relsubtable character varying,
		relsubcolumn character varying,
		relsubdescription character varying,
		CONSTRAINT relatedcolumns_pkey PRIMARY KEY (relcolumnid)
	);
	ALTER TABLE relatedcolumns OWNER TO postgres;
	
	CREATE SEQUENCE relatedcolumn_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE relatedcolumn_seq OWNER TO postgres;

-- Medios de comunicación
	

-- Formas de contacto
	

-- Directorio de abogados

-- Corrección de role-companyid
	ALTER TABLE role DROP CONSTRAINT role_companyid_fkey, ADD CONSTRAINT role_companyid_fkey FOREIGN KEY (companyid) REFERENCES companies(companyid) DEFERRABLE INITIALLY DEFERRED;

-- Acceso a clientes como usuarios
	ALTER TABLE users ADD COLUMN usertype integer;			-- 0 o null = Abogado; 1=Cliente
	ALTER TABLE users ADD COLUMN linkedclientid integer;

-- Nuevos datos para clientes
	ALTER TABLE clients ADD COLUMN rel_with varchar;
	ALTER TABLE clients ADD COLUMN ref_by varchar;

-- Modificación a los números de expedientes, estos pueden estar nulos
	ALTER TABLE Juicios ALTER COLUMN juicio DROP NOT NULL
	ALTER TABLE Apelaciones ALTER COLUMN toca DROP NOT NULL;
	ALTER TABLE Amparos ALTER COLUMN amparo DROP NOT NULL;
	ALTER TABLE Recursos ALTER COLUMN recurso DROP NOT NULL;

-- Tabla de consultas
	CREATE TABLE Consultas (
		consultaid integer NOT NULL,
		clienteid integer NOT NULL,
		juicioid integer,
		abogadoid integer NOT NULL,
		materiaid integer NOT NULL,
		consulta character varying NOT NULL,
		opinion character varying,
		resumen character varying,
		fecha timestamp without time zone,
		honorarios numeric(10,2),
		CONSTRAINT consultas_pkey PRIMARY KEY (consultaid)
	);
	ALTER TABLE consultas OWNER TO postgres;

	CREATE SEQUENCE consultaid_seq
		INCREMENT 1
		MINVALUE 1
		MAXVALUE 9223372036854775807
		START 1
		CACHE 1
	;
	ALTER TABLE consultaid_seq OWNER TO postgres;
	ALTER TABLE consultas ADD CONSTRAINT consultas_clienteid_fkey FOREIGN KEY (clienteid) REFERENCES clients(clientid);
	ALTER TABLE consultas ADD CONSTRAINT consultas_juicioid_fkey FOREIGN KEY (juicioid) REFERENCES juicios(juicioid);
	ALTER TABLE consultas ADD CONSTRAINT consultas_materiaid_fkey FOREIGN KEY (materiaid) REFERENCES materias(materiaid);

--8/Ago/2023 Corrección de tipos 
	ALTER TABLE consultas ALTER COLUMN consulta TYPE character varying;
	ALTER TABLE consultas ALTER COLUMN resumen TYPE character varying;
	ALTER TABLE consultas ALTER COLUMN opinion TYPE character varying;

-- 13/Sep/2023 Incluir consultas en movimientos
	ALTER TABLE movimientos ADD COLUMN consultaid integer;