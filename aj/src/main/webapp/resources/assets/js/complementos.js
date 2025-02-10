function idControl(obj){return document.getElementById(obj);};

function xCtrl(obj,idx,ifr){
	idx=idx||"",ifr=ifr||"";
	var o1=obj.substr(0,1),o2=obj.substr(1,obj.length),
		d=""==ifr?document:document.getElementById(ifr).contentWindow.document;
	return":"==o1?""==idx?d.getElementsByName(o2):d.getElementsByName(o2)[idx]
	:"."==o1?""==idx?d.getElementsByClassName(o2):d.getElementsByClassName(o2)[idx]
	:"ñ"==o1?""==idx?d.getElementsByTagName(o2):d.getElementsByTagName(o2)[idx]
	:">"==o1?""==idx?d.querySelector(o2):d.getElementsByClassName(o2)[idx]
	:"*"==o1?""==idx?d.querySelectorAll(o2):d.getElementsByClassName(o2)[idx]
	:"#"==o1?d.getElementById(o2):d.getElementById(obj)
};

function clearSelect(id){
	var sel=document.getElementById(id);
	var len=sel.options.length;
	for(i=0;i<len;i++){sel.remove(sel.options[0]);}
};

function currencyFmt(num){
	CDEC=CDEC||".";
	num+='';//num=Cadena o número - return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",");
	var x=num.split(CDEC);
	var x1=x[0];
	var x2=x.length>1 ? CDEC+x[1]:'';
	var rgx=/(\d+)(\d{3})/;
	while(rgx.test(x1)){
		x1=x1.replace(rgx,'$1'+','+'$2');}
	return x1+x2;
};

function onlyNums(str){
	NDEC=NDEC||2,CDEC=CDEC||".";
	str=str.replace(new RegExp("[^0-9"+CDEC+"]?","g"),"");
	var l=str.length;
	str.substr(l-1,1)==CDEC?(str+="0",l++):""==str&&(str="0");
	l=1>str.indexOf(CDEC)?l:str.indexOf(CDEC)+1;
	str=str.substr(0,l+NDEC);
	return parseFloat(str)
};

function fixJSMult(a,b){
	var k=1000000;
	a*=k; b*=k;
	return(a*b)/(k*k);
};

function fillDec(n,d){
	d=d||2,CDEC=CDEC||".";
	n=onlyNums(n)+"";
	var l=0;
	0<n.indexOf(CDEC)?(a=n.split(CDEC),l=a[1].length):n+=".";
	for(c=l;c<d;c++)n+="0";
	return n;
};

function twoDigits(n){n=(typeof n=="string")?n=parseInt(n):n; return n<10?"0"+n:n;};

function getLanguageURL(){
	for(var l="es",q=window.location.search.split("&"),v=0;v<q.length;v++){
		var p=q[v].split("=");
		p[0]=p[0].replace(/[.*+?^${}()|[\]\\]/g,"");
		"language"==p[0]&&(l=p[1])
	}return l;
};

function getFormatPatternDate(lang){
	lang=lang||getLanguageURL();
	var formats={"es":"dd/MM/yyyy","en":"MM/dd/yyyy",
		"af-ZA":"yyyy/MM/dd","am-ET":"d/M/yyyy", "ar-AE" :"dd/MM/yyyy","ar-BH":"dd/MM/yyyy",
		"ar-DZ":"dd-MM-yyyy","ar-EG":"dd/MM/yyyy","ar-IQ":"dd/MM/yyyy","ar-JO":"dd/MM/yyyy",
		"ar-KW":"dd/MM/yyyy","ar-LB":"dd/MM/yyyy","ar-LY":"dd/MM/yyyy","ar-MA":"dd-MM-yyyy",
		"ar-OM":"dd/MM/yyyy","ar-QA":"dd/MM/yyyy","ar-SA":"dd/MM/yy", "ar-SY" :"dd/MM/yyyy",
		"ar-TN":"dd-MM-yyyy","ar-YE":"dd/MM/yyyy","as-IN":"dd-MM-yyyy","ba-RU":"dd.MM.yy",
		"be-BY":"dd.MM.yyyy","bg-BG":"dd.M.yyyy", "bn-BD":"dd-MM-yy", "bn-IN" :"dd-MM-yy",
		"bo-CN":"yyyy/M/d", "br-FR" :"dd/MM/yyyy","ca-ES":"dd/MM/yyyy","co-FR":"dd/MM/yyyy",
		"cs-CZ":"d.M.yyyy", "cy-GB" :"dd/MM/yyyy","da-DK":"dd-MM-yyyy","de-AT":"dd.MM.yyyy",
		"de-CH":"dd.MM.yyyy","de-DE":"dd.MM.yyyy","de-LI":"dd.MM.yyyy","de-LU":"dd.MM.yyyy",
		"dv-MV":"dd/MM/yy", "el-GR" :"d/M/yyyy", "en-AU" :"d/MM/yyyy", "en-BZ":"dd/MM/yyyy",
		"en-CA":"dd/MM/yyyy","en-GB":"dd/MM/yyyy","en-IE":"dd/MM/yyyy","en-IN":"dd-MM-yyyy",
		"en-JM":"dd/MM/yyyy","en-MY":"d/M/yyyy", "en-NZ" :"d/MM/yyyy", "en-PH":"M/d/yyyy",
		"en-SG":"d/M/yyyy", "en-TT" :"dd/MM/yyyy","en-US":"M/d/yyyy", "en-ZA" :"yyyy/MM/dd",
		"en-ZW":"M/d/yyyy", "es-AR" :"dd/MM/yyyy","es-BO":"dd/MM/yyyy","es-CL":"dd-MM-yyyy",
		"es-CO":"dd/MM/yyyy","es-CR":"dd/MM/yyyy","es-DO":"dd/MM/yyyy","es-EC":"dd/MM/yyyy",
		"es-ES":"dd/MM/yyyy","es-GT":"dd/MM/yyyy","es-HN":"dd/MM/yyyy","es-MX":"dd/MM/yyyy",
		"es-NI":"dd/MM/yyyy","es-PE":"dd/MM/yyyy","es-PR":"dd/MM/yyyy","es-PY":"dd/MM/yyyy",
		"es-SV":"dd/MM/yyyy","es-US":"M/d/yyyy", "es-UY" :"dd/MM/yyyy","es-VE":"dd/MM/yyyy",
		"et-EE":"d.MM.yyyy", "eu-ES":"yyyy/MM/dd","fi-FI":"d.M.yyyy", "fil-PH":"M/d/yyyy",
		"fo-FO":"dd-MM-yyyy","fr-BE":"d/MM/yyyy","fr-CA":"yyyy-MM-dd", "fr-CH":"dd.MM.yyyy",
		"fr-FR":"dd/MM/yyyy","fr-LU":"dd/MM/yyyy","fr-MC":"dd/MM/yyyy","fy-NL":"d-M-yyyy",
		"ga-IE":"dd/MM/yyyy","gd-GB":"dd/MM/yyyy","gl-ES":"dd/MM/yy", "gu-IN" :"dd-MM-yy",
		"he-IL":"dd/MM/yyyy","hi-IN":"dd-MM-yyyy","hr-BA":"d.M.yyyy.","hr-HR" :"d.M.yyyy",
		"hu-HU":"yyyy. MM. dd.","hy-AM":"dd.MM.yyyy","id-ID":"dd/MM/yyyy","ig-NG":"d/M/yyyy",
		"ii-CN":"yyyy/M/d", "is-IS" :"d.M.yyyy", "it-CH" :"dd.MM.yyyy","it-IT":"dd/MM/yyyy",
		"ja-JP":"yyyy/MM/dd","ka-GE":"dd.MM.yyyy","kk-KZ":"dd.MM.yyyy","kl-GL":"dd-MM-yyyy",
		"km-KH":"yyyy-MM-dd","kn-IN":"dd-MM-yy", "ko-KR" :"yyyy-MM-dd","ky-KG":"dd.MM.yy",
		"lb-LU":"dd/MM/yyyy","lo-LA":"dd/MM/yyyy","lt-LT":"yyyy.MM.dd","lv-LV":"yyyy.MM.dd.",
		"mi-NZ":"dd/MM/yyyy","mk-MK":"dd.MM.yyyy","ml-IN":"dd-MM-yy", "mn-MN" :"yy.MM.dd",
		"mr-IN":"dd-MM-yyyy","ms-BN":"dd/MM/yyyy","ms-MY":"dd/MM/yyyy","mt-MT":"dd/MM/yyyy",
		"nb-NO":"dd.MM.yyyy","ne-NP":"M/d/yyyy", "nl-BE" :"d/MM/yyyy", "nl-NL":"d-M-yyyy",
		"nn-NO":"dd.MM.yyyy","oc-FR":"dd/MM/yyyy","or-IN":"dd-MM-yy", "pa-IN" :"dd-MM-yy",
		"pl-PL":"yyyy-MM-dd","ps-AF":"dd/MM/yy", "pt-BR" :"d/M/yyyy", "pt-PT" :"dd-MM-yyyy",
		"rm-CH":"dd/MM/yyyy","ro-RO":"dd.MM.yyyy","ru-RU":"dd.MM.yyyy","rw-RW":"M/d/yyyy",
		"sa-IN":"dd-MM-yyyy","se-FI":"d.M.yyyy", "se-NO" :"dd.MM.yyyy","se-SE":"yyyy-MM-dd",
		"si-LK":"yyyy-MM-dd","sk-SK":"d. M. yyyy","sl-SI":"d.M.yyyy", "sq-AL" :"yyyy-MM-dd",
		"sv-FI":"d.M.yyyy", "sv-SE" :"yyyy-MM-dd","sw-KE":"M/d/yyyy", "ta-IN" :"dd-MM-yyyy",
		"te-IN":"dd-MM-yy", "th-TH" :"d/M/yyyy", "tk-TM" :"dd.MM.yy", "tn-ZA" :"yyyy/MM/dd",
		"tr-TR":"dd.MM.yyyy","tt-RU":"dd.MM.yyyy","ug-CN":"yyyy-M-d", "uk-UA" :"dd.MM.yyyy",
		"ur-PK":"dd/MM/yyyy","vi-VN":"dd/MM/yyyy","wo-SN":"dd/MM/yyyy","xh-ZA":"yyyy/MM/dd",
		"yo-NG":"d/M/yyyy", "zh-CN" :"yyyy/M/d", "zh-HK" :"d/M/yyyy", "zh-MO" :"d/M/yyyy",
		"zh-SG":"d/M/yyyy", "zh-TW" :"yyyy/M/d", "zu-ZA" :"yyyy/MM/dd",
		"arn-CL":"dd-MM-yyyy","dsb-DE":"d. M. yyyy","gsw-FR":"dd/MM/yyyy","hsb-DE":"d. M. yyyy",
		"kok-IN":"dd-MM-yyyy","moh-CA":"M/d/yyyy",	"nso-ZA":"yyyy/MM/dd","prs-AF":"dd/MM/yy",
		"qut-GT":"dd/MM/yyyy","quz-BO":"dd/MM/yyyy","quz-EC":"dd/MM/yyyy","quz-PE":"dd/MM/yyyy",
		"sah-RU":"MM.dd.yyyy","sma-NO":"dd.MM.yyyy","sma-SE":"yyyy-MM-dd","smj-NO":"dd.MM.yyyy",
		"smj-SE":"yyyy-MM-dd","smn-FI":"d.M.yyyy",	"sms-FI":"d.M.yyyy", "syr-SY" :"dd/MM/yyyy",
		"az-Cyrl-AZ":"dd.MM.yyyy","az-Latn-AZ":"dd.MM.yyyy","bs-Cyrl-BA":"d.M.yyyy",
		"bs-Latn-BA":"d.M.yyyy", "ha-Latn-NG" :"d/M/yyyy", "iu-Cans-CA" :"d/M/yyyy",
		"iu-Latn-CA":"d/MM/yyyy","mn-Mong-CN" :"yyyy/M/d", "sr-Cyrl-BA" :"d.M.yyyy",
		"sr-Cyrl-CS":"d.M.yyyy", "sr-Cyrl-ME" :"d.M.yyyy", "sr-Cyrl-RS" :"d.M.yyyy",
		"sr-Latn-BA":"d.M.yyyy", "sr-Latn-CS" :"d.M.yyyy", "sr-Latn-ME" :"d.M.yyyy",
		"sr-Latn-RS":"d.M.yyyy", "tg-Cyrl-TJ" :"dd.MM.yy", "tzm-Latn-DZ":"dd-MM-yyyy",
		"uz-Cyrl-UZ":"dd.MM.yyyy","uz-Latn-UZ":"dd/MM yyyy"};
	return formats[lang]||'dd/MM/yyyy';
};

function strToISODate(strDate,pattern){
	//Convierte un texto con contenido de fecha (strDate) con cierto formato (pattern) a variable Date de JavaScript.
	//"strDate" deberá ser un texto pero con formato de fecha y acorde a un formato (pattern):
	//		dd/MM/yyy ó MM/dd/yyyy ó d/m/yy, d/M/yyyy HH:mm etc, o milisegundos.
	//"pattern" deberá ser un patrón de formato de fecha y en acorde a "strDate", sino se indica o no coincide, será compado
	//		con el formatato americano o en último caso, se comparará con formato ISO:
	//		1) Formato ISO: "yyyy-m-d", "yyyy-mm-dd" o "yyyy-mm-ddTHH:mm:ss"
	//		2) "año, mes, dia, hr, min, seg"
	//Si no se indicó una fecha u hora, se tomará del sistema.
	strDate=strDate.replace(/[.,_:;tT/\s]/g,'-').replace(/[()]/g,'');
	pattern=pattern||"MM-dd-yyyy";
	try{var dParts=strDate.split("-");
		var dVal1=new Date(),fail=!1;
		var d=dVal1.getDate(),M=dVal1.getMonth()-1,y=dVal1.getFullYear(),h=dVal1.getHours(),m=dVal1.getMinutes(),s=dVal1.getSeconds();
		pattern=pattern.replace(/[.,_:/\s]/g,'-');
		var bPattern=pattern.split("-");
		for(i=0; i<dParts.length; i++){
			if(dParts[i]=="")dParts[i]=0;}
		for(i=0; i<bPattern.length; i++){
			if((bPattern[i]!=undefined)&&(bPattern[i].indexOf("d")> -1))if(dParts[i]!=undefined)d=dParts[i];
			if((bPattern[i]!=undefined)&&(bPattern[i].indexOf("M")> -1))if(dParts[i]!=undefined)M=dParts[i];
			if((bPattern[i]!=undefined)&&(bPattern[i].indexOf("y")> -1))if(dParts[i]!=undefined)y=dParts[i];
			if((bPattern[i]!=undefined)&&((bPattern[i].indexOf("H") )> -1)||((bPattern[i].indexOf("h") )> -1))
				if(dParts[i]!=undefined)h=dParts[i];
			if((bPattern[i]!=undefined)&&(bPattern[i].indexOf("m")> -1))if(dParts[i]!=undefined)m=dParts[i];
			if((bPattern[i]!=undefined)&&(bPattern[i].indexOf("s")> -1))if(dParts[i]!=undefined)s=dParts[i];
		}var dValidPat=new Date(twoDigits(y)+"-"+twoDigits(M)+"-"+twoDigits(d)+"T"+twoDigits(h)+":"+twoDigits(m)+":"+twoDigits(s));
		dValidPat.setDate(d);
		for(i=0; i<bPattern.length; i++){
			if(twoDigits(d)!=twoDigits(dValidPat.getDate())){fail=!0; break;}
			if(twoDigits(M)!=twoDigits((dValidPat.getMonth())+1)){fail=!0; break;}
			if(twoDigits(y)!=twoDigits(dValidPat.getFullYear())){fail=!0; break;}
			if(twoDigits(h)!=twoDigits(dValidPat.getHours())){fail=!0; break;}
			if(twoDigits(m)!=twoDigits(dValidPat.getMinutes())){fail=!0; break;}
			if(twoDigits(s)!=twoDigits(dValidPat.getSeconds())){fail=!0; break;}
		}if(dParts[0].length>10){return new Date(parseInt(dParts[0]));}
		if(fail){
			var isoDate=new Date();
			if(dParts[2]!=undefined)isoDate.setDate(dParts[2]);
			if(dParts[1]!=undefined)isoDate.setMonth(dParts[1]-1);
			if(dParts[0]!=undefined)isoDate.setFullYear(dParts[0]);
			if(dParts[3]!=undefined)isoDate.setHours(dParts[3]);
			if(dParts[4]!=undefined)isoDate.setMinutes(dParts[4]);
			if(dParts[5]!=undefined)isoDate.setSeconds(dParts[5]);
			return isoDate;
		}return dValidPat;
	}catch(e){return null;}
};

function setFormatPatternDate(date, patternOut, lang){
	//Requiere de "toLocaleDateString" para obtener los nombres de días y meses en otros idiomas
	//Retorna una cadena con el formato (patternOut) requerido de fecha y/u hora.
	//"date" deberá ser alguno de estos formatos:
	//	1) Texto con formato ISO: "yyyy-m-d", "yyyy-mm-dd" o "yyyy-mm-ddTHH:mm:ss"
	//	2) Texto separado por comas: "año, mes, dia[, hr, min, seg[, milseg]]"
	//	3) Fecha tipo Date
	//	4) Fecha en milisegundos
	//Si no se indicó una fecha/hora, se tomará del sistema.
	//"patternOut" es el tipo de patrón a regresar (Letras estandares para fecha y hora). Si no es indicado, se tomará el inglés
	//Para muestrar "am" o "pm" los formatos son: "a","am","a.m.","A","AM","A.M."
	//"lang" Son dos caracteres indicando el idioma que se tomará como base para extraer los textos (o ideogramas) de los días y meses:
	//	1) Por ejemplo, para el español se ingresará "es"
	//	2) Si no se indica "lang", verificará si existe la variable "language=" en la URL
	//	3) Si no se indica ningún idioma y de ser necesario, tomará el inglés por default.
	patternOut=patternOut||'MM/dd/yyyy';
	date=date||new Date();
	if(typeof date=="string")date=strToISODate(date,patternOut);
	lang=lang||getLanguageURL();
	var a1=0,a2=0,z="",zz="",styear=new Date(date.getFullYear(), 0, 1), monthNames=[], dayOfWeekNames=[];
	var week=Math.ceil( ((((new Date())-styear)/86400000)+styear.getDay()+1) /7);
	for(i=0;i<patternOut.length; i++){
		if((patternOut.substring(i,i+1)=="a")||(patternOut.substring(i,i+1)=="A")||(patternOut.substring(i,i+1)==".")){
			if(a1==0)a1=i;
			if(patternOut.substring(i+1,i+2)==""){
				a2=i+1;
				break;
			}
		}
	}var ampm=patternOut.substring(a1,a2);
	var ap=(ampm=="a")?['a','p']:(ampm=="aa")?['am','pm']:(ampm=="a.a.")?['a.m.','p.m.']:
			(ampm=="A")?['A','P']:(ampm=="AA")?['AM','PM']:(ampm=="A.A.")?['A.M.','P.M.']:['',''];
	try{z=(date.toLocaleTimeString(lang,{timeZoneName:'short'}).split(' ')[1]).split('-')[0];
		zz="("+date.toLocaleTimeString(lang,{timeZoneName:'long'}).split('(')[1];
	}catch(ez){z="";zz="";}
	for(c=0;c<12;c++){
	var convertText=new Date(2000, c, 1),monthname;
	try{monthname=convertText.toLocaleDateString(lang, {month:'long'});
	}catch(em){monthNames=["January","February","March","April","May","June",
		"July","August","September","October","November","December"];break;}
	monthNames[c]=monthname;
	}for(c=0;c<7;c++){
	var convertText=new Date(2000, 01, c),dayname;
	try{dayname=convertText.toLocaleDateString(lang, {weekday:'long'});
	}catch(ed){dayOfWeekNames=["Sunday","Monday","Tuesday",
		"Wednesday","Thursday","Friday","Saturday"];break;}
	dayOfWeekNames[c]=dayname;
	}var day=date.getDate(),	month=date.getMonth(),		year=date.getFullYear(),
		hour=date.getHours(),	minute=date.getMinutes(),	second=date.getSeconds(),
		h=hour % 12,			hh=twoDigits(h),				miliseconds=date.getMilliseconds(),
		HH=twoDigits(hour),		mm=twoDigits(minute),		ss=twoDigits(second),
		aaa=hour<12?ap[0]:ap[1],dd=twoDigits(day),			dddd=dayOfWeekNames[date.getDay()],
		ddd=dddd.substr(0,3),	M=month + 1,				MM=twoDigits(M),
		MMMM=monthNames[month],	MMM=MMMM.substr(0,3),		ww=twoDigits(week),
		yyyy=year + "",			yy=yyyy.substr(2,2);
	return patternOut
		.replace('hh', hh).replace('h', h).replace('HH', HH).replace('H', hour)
		.replace('mm', mm).replace('m', minute)
		.replace('ss', ss).replace('s', second).replace('S', miliseconds)
		.replace('ww', ww).replace('w', week)
		.replace('yyyy',yyyy).replace('yy', yy)
		.replace('dddd',dddd).replace('ddd', ddd).replace('dd', dd).replace('d', day)
		.replace('MMMM',MMMM).replace('MMM', MMM).replace('MM', MM).replace('M', M)
		.replace('zz', zz).replace('z', z)
		.replace(ampm,aaa);
};

function simpleChangePatt(outputPattern,stringDate,inputPattern){
	/* Retorna una cadena de texto que contiene una fecha con formato específico.
	outputPattern	=	Formato a retornar, ejemplo: "yyyy-MM-dd", se puede indicar cualquier separador.
	stringDate		=	Cadena de texto con la fecha separado por diagonales, ejemplo: "25/12/2039".
	inputPattern	=	Formato de entrada que debe correcponder a "stringDate", tomando el ejemplo de aquí arriba,
						el formato deberá ser "dd/MM/yyyy". Si no se indica un formato, se tomará el del sistema.*/
	if(stringDate==""||stringDate==null||outputPattern==""||outputPattern==null)return null;
	inputPattern=inputPattern||getFormatPatternDate();
	var pattern=inputPattern.split("/");
	var dArray=stringDate.split("/");
	var sep=(outputPattern.replace(/[a-zA-Z0-9]/g,"")).substring(0,1);
	var fixDatePat=outputPattern.split(sep);
	var nwd="";
	for(var f=0; f<fixDatePat.length; f++)
	 for(var i in pattern)
	  if(fixDatePat[f]==pattern[i]){
		if((fixDatePat[f].indexOf("y")>=0)&&(dArray[i].length==2))
			dArray[i]="20"+dArray[i];
		nwd+=dArray[i]+sep;
	  }
	return nwd.substring(0,(nwd.length)-1);
};

/**	Establece la fecha en los campos de bootstrap
	@param	e		{String}	Id input tag fecha definitiva.
	@param	efix	{String}	Id input tag Fix o input donde esta el datepicker.
	@return	d		{. . . } 	Fecha en cualquier formato (ver función "formatDateTime" para más detalles).	*/
function setBootstrapUtcDate(e,efix,d){
	var newD='',dFix='';
	if(d instanceof Date && !isNaN(d)){
		dFix=formatDateTime(d);
		newD=formatDateTime(d,'','yyyy-MM-dd');
	}
	$('#'+efix).val(dFix);
	$('#'+e).val(newD);
};

/** Convierte una fecha/hora a texto con un formato o patrón de salida personalizado.
@param d    (Opcional) Fecha/Hora a tratar. Si no es indicado un valor, se tomará como referencia la fecha del sistema.
            El formato puede ser cualquiera de estos estilos:
            Estilo                    Tipo de variable  Ejemplo                     Observaciones
            Fecha                     Date              new Date(miFecha);
            Unix Timestamp            Numérico/String   2176891106                  Se recomienda indicar en "@inPattern" el formato 'T', ejemplo: formatDateTime('-46452','T').
            Milisegundos              Numérico/String   2176891106000               Se recomienda indicar en "@inPattern" el formato 'L', ejemplo: formatDateTime(-46452000,'L').
            UTC corto                 String            '2038/12/25'                Formato 'yyyy-MM-dd' o 'yyyy/MM/dd', aunque se recomienda utilizar diagonales "/" en lugar de guiones (-) sólo cuando es fecha sin horas.
            UTC-ISO con hora          String            '2038-12-25T00:01:59.000Z'  Formato 'yyyy-MM-ddThh:mm.ssssZ', milisegundos (.sss) opcionales.
            UTC-ISO con zona horaria  Sting             '2017-05-11T00:01:59+0800'  Formato 'yyyy-MM-ddThh:mm+-zona'. La zona horaria puede influenciar en el resultado con la variación del día.
            GMT                       String            'Saturday, December 25, 2038 5:58:26 AM'  Texto deberá ser en inglés, respetando comas y espacios.
            GTM con localidad         String            'Saturday, Dec 25, 2038 5:58:26 AM GMT-06:00'  Texto deberá ser en inglés, respetando comas y espacios. La zona horaria puede influenciar en la variación del día.
            GTM con zona horaria      String            'Sat Dec 25 2038 05:58:26 (Central Daylight Time)'  Texto deberá ser en inglés, respetando comas y espacios. La zona horaria puede influenciar en la variación del día.
            Personalizado             String            Se recomienda indicar en "@inPattern" el formato de entrada relacionado con la fecha, por ejemplo, sí se indica solo la fecha sin "@inPattern" cuando
                                                            d="10-10-10", la función no podría identificar si el formato de entrada es "MM-dd-yy", "yy-MM-dd" o "dd-MM-yy".
                                                            ** En caso de no ser indicado '@inPattern' se tomará el formato correspondiente al sistema. **
            Ejemplos: formatDateTime(2176891106); o formatDateTime('2038/12/25');

            *** Cuando este argumento sea una cadena vacía '', null u omitida en su totalidad (sin argumentos "@inPattern" ni "@outPattern"), se tomaŕa la fecha del sistema,
              ejemplos: formatDateTime(); o formatDateTime(null); o formatDateTime('');  ***

@param inPattern   (Opcional) En caso de indicar una fecha personalizada, o fecha en Unix Timestamp o Milisegundos, se recomienda indicar un formato o patrón de entrada para que el sistema comprenda el contexto:
            Formato aceptado    Descripción   Dato que relacionado con la fecha
            T                   Timestamp     Reafirma que la fecha esta en formato "Timestamp".
            L                   Milisegundos  Reafirma que la fecha esta en formato "Milisegundos".
            YY, YYYY            Año           2 ó 4 dígitos.
            M, MM, texto        Mes           1 ó 2 dígitos. Para textos ya sea de abreviado de 3 caracteres o de nombre completo, utilizarlo en inglés y dejar "@inPattern"=null o vacío ('').
            d, dd, texto        Dia           1 ó 2 dígitos. Para textos ya sea de abreviado de 3 caracteres o de nombre completo, utilizarlo en inglés y dejar "@inPattern"=null o vacío ('').
            H, HH               Hora          1 ó 2 dígitos.
            m, mm               Minutos       1 ó 2 dígitos.
            s, ss, sss          Segundos      1, 2 ó 3 dígitos.
            Ejemplos:formatDateTime('10/12/14','MM/dd/yy'); // Aquí ya se relaciona que '10' es el mes, '12' es el dia y '14' el año.
                     formatDateTime('Wednesday 16/June/2038',null,'FFFF');

            *** Cuando el valor sea una cadena vacía '', null u omitido, se tomaŕa el formato de acuerdo al sistema.
              Ejemplos: formatDateTime(miFecha); o formatDateTime(miFecha,''); o formatDateTime(miFecha,null,'');  ***

@param outPattern  (Opcional) Patrón o formato de retorno de fecha/hora. Si no se indica un valor, se tomará el formato del sistema.
			Los formatos o valores aceptados son:
@return     Formato   Descripción   Retorna
            YY        Año           2 digitos.
            yyyy      Año           4 digitos.
            M         Mes           1 dígito (1-12).
            MM        Mes           2 dígitos (01-12). Digito simple se complementa con cero a la izquierda.
            MMM       Mes           3 letras correspondientes a la abreviatura del mes.
            MMMM      Mes           Texto con el nombre completo del mes.
            d         Día           1 dígito (1-31).
            dd        Día           2 dígitos (01-31). Digito simple se complementa con cero a la izquierda.
            ddd       Día           3 letras correspondientes a la abreviatura del día.
            D         Día           1 letra correspondiente al abreviado del dia.
            DD        Día           3 letras correspondiente al abreviado del dia.
            DDD       Día           Texto con el nombre completo del dia.
            H         Hora          1 dígito en horas militares: 0-23.
            HH        Hora          2 dígitos en horas militares: 00-23. Digito simple se complementa con cero a la izquierda.
            m         Minuto        1 dígito.
            mm        Minuto        2 dígitos. Digito simple se complementa con cero a la izquierda.
            s         Segundos      1 dígito.
            ss        Segundos      2 dígitos. Digito simple se complementa con cero a la izquierda.
            S         Milisegundos  1 dígito.
            SS        Milisegundos  2 dígitos.
            SSS       Milisegundos  3 dígitos.
            z         timeZone      Formato de localización corto, ejemplo, 'PST', 'GMT-8', 'GMT-6'
            zz        timeZone      Formato de localización largo, ejemplo, 'Pacific Standard Time', 'Nordamerikanische Westküsten-Normalzeit'
            zzz       timeZone      Formato de localización GMT corto, ejemplo, 'GMT-8'
            zzzz      timeZone      Formato de localización GMT largo, ejemplo, 'GMT-0800' ò 'GMT-08:00'
            F         Full Format   Formato simple "d/M/yy" o estilo de acuerdo al "@lang", ejemplo, en EEUU retornará "M/d/yy".
            FF        Full Format   Formato "d MMM yyyy" o estilo de acuerdo al "@lang", ejemplo, en EEUU retornará "MMM d yyyy".
            FFF       Full Format   Formato completo "d de MMMM del yyyy" o estilo automático de acuerdo a la región, ejemplo, en EEUU retornará "MMMM d, yyyy".
            FFFF      Full Format   Formato extendido "DDDD, d de M del yyyy" o estilo automático de acuerdo a la región, ejemplo, en EEUU retornará "DDDD, M d, yyyy".

            Ejemplo: formatDateTime(new Date(), '', 'dd/MMMM/yyyy'); // Aquí ya se indica retornar el día a 2 dígitos, seguido del nombre del mes completo y el año con 4 dígitos.

            * Cualquier otro caracter o símbolo, será transcrito como tal.
            ** Cuando el valor de retorno 2sea una cadena vacía '', null u omitida, se tomaŕa el formato de acuerdo al sistema.
              ejemplo: formatDateTime(miFecha, miPatt); o formatDateTime(miFecha,miPatt,null); o formatDateTime(miFecha,miPatt,'');

@lang       (Opcional) Establece el idioma para reafirmar los formato "@inPattern" y "@outPattern".
			* Si no se establece un idioma se tomará el del sistema dentro del navegador.
            ** El idioma puede influenciar en la variación de la fecha.
            Ejemplo: formatDateTime(new Date(), '', '', 'en'); o formatDateTime('',null,'d/M/yyyy','ja');

@throws Null en caso de cualquier error de conversión u obtención de datos. */
function formatDateTime(d,inPattern,outPattern,lang){
	inPattern=inPattern||'';
	outPattern=outPattern||getFormatPatternDate(lang);
	d=d||new Date();
	lang=lang||getLanguageURL();
	var fixDate;
	if(typeof(d)=='string' || /^\-?([0-9]{5,15})$/g.test(d) || ['T','L'].indexOf(inPattern)>=0){
		if(/^\-?[0-9]{12,15}$/.test(d) || inPattern=='L')
			d=d*1;
		else if(/^\-?([0-9]{5,11})$/g.test(d) || inPattern=='T')
			d=d*1000;
		if(['T','L',''].indexOf(inPattern))
			try{
				fixDate=new Date(d);
			}catch(e){
				console.warn('Reintententando establecer y emparejar fecha...');
			}
		if(!(fixDate instanceof Date && !isNaN(fixDate))){
			nPattern=inPattern||getFormatPatternDate(lang);
			var str='',chr='',baseChar=outPattern.substring(0,1),auxDate=new Date(),pattValues={},
				initCh=0,isoYear='',isoMonth='',isoDay='',isoHour='',isoMinute='',isoSecond='',isoMiliSec='';
			for(c=1;c<inPattern.length+1;c++){
				chr=inPattern.substring(c-1,c);
				if(chr==baseChar)
					continue;
				if(baseChar=='F'){
					pattValues[baseChar+'']='';
					baseChar=chr;
					continue;
				}else if(['y','M','d','D','H','m','s','S','z'].indexOf(baseChar)>=0){
					var endCh=0;
					if(/^[0-9]{4}$/.test(d.substring(initCh,initCh+4)) && baseChar=='y')
						endCh=4;
					else if(/^[0-9]{3}$/.test(d.substring(initCh,initCh+3)) && baseChar=='S')
						endCh=3;
					else if(/^[0-9]{2}$/.test(d.substring(initCh,initCh+2)))
						endCh=2;
					else if(/^[0-9]$/.test(d.substring(initCh,initCh+1)))
						endCh=1;
					pattValues[baseChar+'']=d.substring(initCh,initCh+endCh);
					initCh+=endCh;
					baseChar=chr;
					continue;
				}
				initCh++;
				baseChar=chr;
			}
			pattValues[baseChar+'']=d.substring(initCh,d.length);
			isoYear =pattValues['y']==null?auxDate.getFullYear():pattValues.y;
			isoMonth=pattValues['M']==null?auxDate.getMonth()-1:pattValues.M-1;
			isoDay = pattValues['d']==null?auxDate.getDate():pattValues.d;
			isoHour= pattValues['H']==null?auxDate.getHours():pattValues.H;
			isoMinute=pattValues['m']==null?auxDate.getMinutes():pattValues.m;
			isoSecond=pattValues['s']==null?auxDate.getSeconds():pattValues.s;
			isoMiliSec=pattValues['S']==null?auxDate.getMilliseconds():pattValues.S;
			if(/^[0-9]{2}$/.test(isoYear))
				isoYear=(auxDate.getFullYear().toString()).substring(0,2)+isoYear;
			try{
				fixDate=new Date(isoYear,isoMonth,isoDay,isoHour,isoMinute,isoSecond,isoMiliSec);
			}catch(e){
				console.error('Error al convertir a fecha. '+e);
			}
		}
	}else{
		try{
			if(/^\-?[0-9]{12,15}$/.test(d) || inPattern=='L')
				d=d*1;
			else if(/^\-?([0-9]{5,11})$/g.test(d) || inPattern=='T')
				d=d*1000;
			fixDate=new Date(d);
		}catch(e){
			console.error('Error al convertir a fecha. '+e);
		}
	}
	if(!(fixDate instanceof Date && !isNaN(fixDate))){
		console.error('No se puede establecer los valores con la fecha o formatos indicados');
		return null;
	}
	var str='',baseChar=outPattern.substring(0,1),chr='',outputData='',
		dateFormat={
			'y':{option:'year',type:['2-digit','2-digit','numeric','numeric']},
			'M':{option:'month',type:['numeric','2-digit','short','long']},
			'd':{option:'day',type:['numeric','2-digit']},
			'D':{option:'weekday',type:['narrow','short','long']},
			'H':{option:'hour',type:['numeric','2-digit']},
			'm':{option:'minute',type:['numeric','2-digit']},
			's':{option:'second',type:['long','short','narrow']},
			'S':{option:'fractionalSecondDigits',type:['1','2','3']},
			'z':{option:'timeZoneName',type:['short','long','shortOffset','longOffset']},
			'F':{option:'dateStyle',type:['short','medium','long','full']}
			//'u':{option:'function',type:'getDay()'},
			//'l':{option:'function',type:'getTime()'},
			//'w':{option:'function',type:'getWeek()'},
			//Intl.DateTimeFormat().resolvedOptions().timeZone
			//\comillas
		};
	for(c=1;c<outPattern.length+1;c++){
		chr=outPattern.substring(c-1,c);
		if(chr==baseChar){
			str+=chr;
			continue;
		}/*else if(chr=="'" || chr=='"'){
			var str1=c,limiter=chr;
			do{
				c++;
				limiter=outPattern.substring(c-1,c);
			}while(limiter!=chr);
			outputData+=outPattern.substring(str1,c-1);
		}*/
		try{
			var idx=str.length>dateFormat[baseChar].type.length?0:str.length-1;
			var opt=dateFormat[baseChar].option,typ=dateFormat[baseChar].type[idx];
			outputData+=fixDate.toLocaleString(lang,{[opt]:typ});
		}catch (e){
			outputData+=str;
		}
		baseChar=chr;
		str=chr;
	}
	try{
		var idx=str.length>dateFormat[baseChar].type.length?0:str.length-1;
		var opt=dateFormat[baseChar].option,typ=dateFormat[baseChar].type[idx];
		outputData+=fixDate.toLocaleString(lang,{[opt]:typ});
	}catch (e){
		outputData+=str;
	}
	return outputData;
};

function chkIO(c){var s=xCtrl(c).checked;return (s||s=="on"||s==1)?!0:!1;};

function IsIt(x){
	/* Forma de usarlo.
	1) Como objeto global:			var is=new IsIt();
	1.1) Utilizar como propiedad: var miResultado=is.what(miDato);
	2) Pasar con valor directo;	var is=new IsIt();
	2.1) Utilizar como propiedad: var miResultado=is.what(miDato);*/
	IsIt.prototype.what=function (x){return typeof x};
	IsIt.prototype.data=function (x){return "Dato recibido: "+x+"\nTipo: "+typeof x+"\nLongitud: "+(typeof x==="object")?x.length:(""+x).length};
	IsIt.prototype.number=function (x){return typeof x==="number"&& isFinite(x)&&Math.floor(x)===x};
	IsIt.prototype.turnNumber=function (x){return x=isNaN(x)?"No se puede convertir "+typeof x:parseInt(x)};
	IsIt.prototype.turnPositive=function isit(x){return x=isNaN(x)?"Variable es "+typeof x:Math.abs(x)};
	IsIt.prototype.boolean=function (x){return typeof x==="boolean"};
	IsIt.prototype.string=function (x){return typeof x==="string"};
};

function dp(d){return String(d).split(" ")[3]+"-"+(new Date(d).getMonth()+1)+"-"+new Date(d).getDate()};

function dateToShow(f){
	// Si no se indicó una fecha(variable DATE o TIMESTAMP),tomará la fecha del sistema
	// Para efectos estéticos, se requiere de "toLocaleDateString"
	var fecha,d=((typeof f=='undefined') ||(f==""))? new Date():new Date(f);
	try{fecha=d.toLocaleDateString(getLanguageURL(),{year:'numeric',month:'2-digit',day:'2-digit'})
	}catch(e){
		try{fecha=d.toLocaleDateString()}
		catch(r){fecha=d.toLocaleDateString('en')}
	}if(fecha=="Invalid Date"){fecha=""}
	return fecha;
};

function dateTimeToShow(f){
	// Si no se indicó una fecha(variable DATE o TIMESTAMP),tomará la fecha del sistema
	// Para efectos estéticos, se requiere de "toLocaleDateString"
	var fecha,d=((typeof f=='undefined') ||(f==""))? new Date():new Date(f);
	try{fecha=d.toLocaleDateString(getLanguageURL(),
		{hour12:true,year:'numeric',month:'2-digit',day:'2-digit',hour:'2-digit',minute:'2-digit'})
		if(fecha=="Invalid Date"){fecha=""}
	}catch(e){
		try{fecha=d.toLocaleDateString()}
		catch(r){fecha=d.toLocaleDateString('en')}
		finally{fecha=(fecha=="Invalid Date")?"":fecha+=","+d.getUTCHours()+":"+d.getUTCMinutes();}
	}return fecha;
};

function formatToDateYMD(f){
	// Si no se indicó una fecha(variable DATE o TIMESTAMP),tomará la fecha del sistema
	// Para efectos estéticos, se requiere de "toLocaleDateString"
	var fecha,d=((typeof f=='undefined') ||(f==""))? new Date():new Date(f);
	var dToS=d.toString();	//Proceso para compatibilidad JS de navegadores
	var dArray=dToS.split(" ");
	var m=d.getMonth()+1;
	var fixD=dArray[3]+"-"+m+"-"+dArray[2];
	return fixD;
};

function formatToTimeHHmm(t){
	// Si no se indicó una hora(variable DATE o TIMESTAMP),tomará la fecha del sistema
	var fecha,f="",
	d=((typeof t=='undefined') ||(t==""))? new Date():new Date(t);
	h=parseInt(d.getHours()),
	m=parseInt(d.getMinutes());
	return f=h<=9?"0"+h.toString():h.toString(),f+=":",f+=m<=9?"0"+m.toString():m.toString()
};

function polyfill(command){
	command=command.replace(" ","");
	var js=command.split(",");
	for(var j=0; j<js.length; j++){
		if(js=="JSON"){
			if(!window.JSON){
				window.JSON={
					parse:function (sJSON){return eval("("+sJSON+")");},
					stringify:function (vContent){
						if(vContent instanceof Object){
							var sOutput="";
							if(vContent.constructor===Array){
								for(var nId=0; nId<vContent.length; sOutput+=this.stringify(vContent[nId])+",",nId++);
									return "["+sOutput.substr(0,sOutput.length-1)+"]";
							}if(vContent.toString!==Object.prototype.toString)
								return "\""+vContent.toString().replace(/"/g, "\\$&")+"\"";
							for (var sProp in vContent)
								sOutput += "\"" + sProp.replace(/"/g, "\\$&") + "\":" + this.stringify(vContent[sProp]) + ",";
							return "{"+sOutput.substr(0,sOutput.length-1)+"}";
						}return typeof vContent==="string"?"\""+vContent.replace(/"/g, "\\$&")+"\"":String(vContent);
					}
				};
			}
		}else if((js=="Storage")||(js=="localStorage")||(js=="sessionStorage")){
			if(typeof(Storage)!=="undefined"){
				window.sessionStorage={_data:{},
					setItem:function(id,val){return this._data[id]=String(val);},
					getItem:function(id){return this._data.hasOwnProperty(id)?this._data[id]:undefined;},
					removeItem:function(id){return delete this._data[id];},
					clear:function(){return this._data={};}
				};
				window.localStorage={_data:{},
					setItem:function(id,val){return this._data[id]=String(val);},
					getItem:function(id){return this._data.hasOwnProperty(id)?this._data[id]:undefined;},
					removeItem:function(id){return delete this._data[id];},
					clear:function(){return this._data={};}
				};
			}
		}else if(js=="Object.keys"){
			if(!Object.keys){
				Object.keys=(function(){
					'use strict';
					var hasOwnProperty=Object.prototype.hasOwnProperty,
						hasDontEnumBug=!({toString:null}).propertyIsEnumerable('toString'),
						dontEnums=['toString','toLocaleString','valueOf','hasOwnProperty','isPrototypeOf','propertyIsEnumerable','constructor'],
						dontEnumsLength=dontEnums.length;
					return function(obj){
						if(typeof obj!=='object'&&(typeof obj!=='function'||obj===null)){
							throw new TypeError('Object.keys called on non-object');}
						var result=[],prop,i;
						for(prop in obj)
							if(hasOwnProperty.call(obj,prop))
							result.push(prop);
						if(hasDontEnumBug)
							for(i=0; i<dontEnumsLength; i++)
							if(hasOwnProperty.call(obj,dontEnums[i]))
								result.push(dontEnums[i]);
						return result;
					};
				}());
			}
		}else if(js=="indexOf"){ //indexOf para Arrays
			if(!Array.prototype.indexOf){
				Array.prototype.indexOf=function indexOf(member,startFrom){
					/*En el modo no estricto, si la variable 'this' es null o indefinida, entonces se establece
					en el objeto ventana. De lo contrario, 'this' se convierte automáticamente en un objeto.
					En modo estricto, si la variable 'this' es nula o indefinida, se lanza 'TypeError'.*/
					if(this==null){throw new TypeError("Array.prototype.indexOf(): No se puede convertir '"+this+"' en objeto");}
					var index=isFinite(startFrom)?Math.floor(startFrom):0,
					that=this instanceof Object?this:new Object(this),
					length=isFinite(that.length)?Math.floor(that.length):0;
					if(index>=length){return -1;}
					if(index<0){index=Math.max(length+index,0);}
					if(member===undefined){
						/*Dado que 'member' no está definido, las claves que no existan tendrán el
							valor de 'same' como 'member' y, por lo tanto, es necesario verificarlas.*/
						do{
							if(index in that && that[index]===undefined){return index;}
						}while(++index <length);
					}else{
						do{
							if(that[index]===member){return index;}
						}while(++index <length);
					}return -1;
				};
			}
		}else if(js=="previousElementSibling"){
			if(!(js in document.documentElement)){
				Object.defineProperty(Element.prototype,js,{
					get:function(){
						var e=this.previousSibling;
						while(e&&1!==e.nodeType)
						e=e.previousSibling;
						return e;
					}
				});
			}
		}else if(js=="nextElementSibling"){
			if(!(js in document.documentElement)){
				Object.defineProperty(Element.prototype,js,{
					get:function(){
						var e=this.nextSibling;
						while(e&&1!==e.nodeType)
						e=e.nextSibling;
						return e;
					}
				});
			}
		}else if(js=="firstElementChild"){
			if(!(js in document.documentElement)){
				Object.defineProperty(Element.prototype,js,{
					get:function(){
						for(var nodes=this.children,n,i=0,l=nodes.length;i<l;++i)
						if(n=nodes[i],1===n.nodeType)return n;
						return null;
					}
				});
			}
		}else if(js=="lastElementChild"){
			if(!(js in document.documentElement)){
				Object.defineProperty(Element.prototype,js,{
					get:function(){
						for(var nodes=this.children,n,i=nodes.length-1;i>=0;--i)
						if(n=nodes[i],1===n.nodeType)return n;
						return null;
					}
				});
			}
		}else if(js=="addDays"){
			Date.prototype.addDays=function(days){
				var date=new Date(this.valueOf());
				date.setDate(date.getDate()+days);
				return date;
			}
		}else if(js=="substractDays"){
			Date.prototype.substractDays=function(days){
				var date=new Date(this.valueOf());
				date.setDate(date.getDate()+days);
				return date;
			}
		}else if(js=="children"){
			(function(constructor){
				if(constructor &&
					constructor.prototype &&
					constructor.prototype.children==null){
					Object.defineProperty(constructor.prototype,'children',{
						get:function(){
						var i=0,node,nodes=this.childNodes,children=[];
						while(node=nodes[i++]){
							if(node.nodeType===1){
								children.push(node);}
						}return children;
					}
					});
				}
			})(window.Node||window.Element);
		}else if(js=="childNode"){
			(function(){
				var buildDOM=function(){
					var nodes=Array.prototype.slice.call(arguments),
						frag=document.createDocumentFragment(),div,node;
					while(node=nodes.shift()){
						if(typeof node=="string"){
							div=document.createElement("div");
							div.innerHTML=node;
							while(div.firstChild){
								frag.appendChild(div.firstChild);}
						}else{frag.appendChild(node);}
					}return frag;
				};
				var proto={
					before:function(){
						var frag=buildDOM.apply(this,arguments);
						this.parentNode.insertBefore(frag,this);},
					after:function(){
						var frag=buildDOM.apply(this,arguments);
						this.parentNode.insertBefore(frag,this.nextSibling);},
					replaceWith:function(){
						if(this.parentNode){
							var frag=buildDOM.apply(this,arguments);
							this.parentNode.replaceChild(frag,this);
						}},
					remove:function(){
						if(this.parentNode){this.parentNode.removeChild(this);}}
				};
				var a=["Element","DocumentType","CharacterData"]; // interface
				var b=["before","after","replaceWith","remove"]; // methods
				a.forEach(function(v){
					b.forEach(function(func){
						if(window[v]){
							if(window[v].prototype[func]){return;}
							window[v].prototype[func]=proto[func];}
					});
				});
			})
		}else if(js=="object"){
			if(!Object.keys){
				Object.keys=(function(){
				'use strict';
				var hasOwnProperty=Object.prototype.hasOwnProperty,
					hasDontEnumBug=!({toString: null}).propertyIsEnumerable('toString'),
					dontEnums=['toString','toLocaleString','valueOf','hasOwnProperty',
					'isPrototypeOf','propertyIsEnumerable','constructor'],
					dontEnumsLength = dontEnums.length;
				return function(obj){
				if(typeof obj!=='object'&&(typeof obj!=='function'||obj===null)){
					throw new TypeError('Object.keys called on non-object');}
				var result = [], prop, i;
				for(prop in obj){
					if(hasOwnProperty.call(obj, prop)){result.push(prop);}}
				if(hasDontEnumBug){
					for(i=0; i<dontEnumsLength; i++){
					if(hasOwnProperty.call(obj, dontEnums[i])){result.push(dontEnums[i]);}}
				}return result;
				};
				}());
			}
		}else if(js=="find"){
			if (!Array.prototype.find) {
				Object.defineProperty(Array.prototype, 'find', {
					value: function(predicate) {
						if (this == null)
							throw new TypeError('"this" is null or not defined');
						var o = Object(this);
						var len = o.length >>> 0;
						if (typeof predicate !== 'function')
							throw new TypeError('predicate must be a function');
						var thisArg = arguments[1], k = 0;
						while (k < len) {
							var kValue = o[k];
							if (predicate.call(thisArg, kValue, k, o))
								return kValue;
							k++;
						}
						return undefined;
					},
					configurable: true,
					writable: true
				});
			}
		}else if(js=="classList"){
			if('document' in self){
				//Soporte total para navegadores sin soporte de classList incluyendo SVGElement.classList en IE<Edge
				if(!('classList' in document.createElement( '_' ))
					|| document.createElementNS && !('classList' in document.createElementNS('http://www.w3.org/2000/svg','g'))){
					(function(view){
						'use strict';
						var DOMEx,checkTokenAndGetIndex,ClassList,classListGetter,classListPropertyDescriptor;
						if(!('Element' in view)){return;}
						//Contenido del código de Vendors para instanciar DOMExceptions
						DOMEx=function(type,message){
							this.name=type;
							this.code=DOMException[type];
							this.message=message;
						};
						// Implementaciones DOMException no permiten toString() en non-DOMExceptions; 'error' en toString() es suficiente.
						DOMEx.prototype=Error.prototype;
						checkTokenAndGetIndex=function(classList,token){
							if(token===''){throw new DOMEx('SYNTAX_ERR','An invalid or illegal string was specified');}
							if(/\s/.test(token)){throw new DOMEx('INVALID_CHARACTER_ERR','String contains an invalid character');}
							return [].indexOf.call(classList,token);
						};
						ClassList=function(elem){
							var trimmedClasses=String.prototype.trim.call(elem.getAttribute('class') || '');
							var classes=trimmedClasses ? trimmedClasses.split( /\s+/ ):[];
							var len=classes.length,i=0;
							for( ; i<len; i++)
								this.push(classes[i]);
							this._updateClassName=function(){
								elem.setAttribute('class',this.toString());
							};
						};
						ClassList.prototype=[];
						classListGetter=function(){return new ClassList(this);};
						ClassList.prototype.item=function(i){return this[i] || null;};
						ClassList.prototype.contains=function(token){
							token+='';
							return checkTokenAndGetIndex(this,token)!== -1;
						};
						ClassList.prototype.add=function(){
							var tokens=arguments,l=tokens.length,i=0,token,updated=false;
							do{ token=tokens[i]+'';
								if(checkTokenAndGetIndex(this,token)=== -1){
									this.push(token);
									updated=true;
								}
							}while(++i <l);
							if(updated){this._updateClassName();}
						};
						ClassList.prototype.remove=function(){
							var tokens=arguments,i=0,l=tokens.length,token,updated=false,index;
							do{ token=tokens[i]+'';
								index=checkTokenAndGetIndex(this,token);
								while(index!== -1){
									this.splice(index,1);
									updated=true;
									index=checkTokenAndGetIndex(this,token);
								}
							}while(++i <l);
							if(updated){this._updateClassName();}
						};
						ClassList.prototype.toggle=function(token,force){
							var result,method;
							token+='';
							result=this.contains(token);
							method=result?force!==true &&'remove':force!==false&&'add';
							if(method){this[method](token);}
							if(force===true || force===false){return force;
							}else{return !result;}
						};
						ClassList.prototype.toString=function(){return this.join(' ');};
						if(Object.defineProperty){
							classListPropertyDescriptor={
								'get':classListGetter,
								'enumerable':true,
								'configurable':true
							};
							try{Object.defineProperty(view.Element.prototype,'classList',classListPropertyDescriptor);
							}catch(exception){ //IE 8 no soporta 'enumerable:true'
								if(exception.number=== -0x7FF5EC54){
									classListPropertyDescriptor.enumerable=false;
									Object.defineProperty(view.Element.prototype,'classList',classListPropertyDescriptor);
								}
							}
						}else if(Object.prototype.__defineGetter__){
							view.Element.prototype.__defineGetter__('classList',classListGetter);
						}
					}(self));
				}else{
					(function(){//Soporte nativo total o parcial de classList,verificar si se necesita normalizar add,remove o toggle APIs.
						'use strict';
						var testElement=document.createElement('_'),createMethod,original;
						testElement.classList.add('c1','c2');
						// Para IE 10/11 y Firefox <26,donde ".add" y ".remove" existten pero soportan sólo un argumento.
						if(!testElement.classList.contains('c2')){
							createMethod=function(method){
								var original=DOMTokenList.prototype[method];
								DOMTokenList.prototype[method]=function(token){
									var i,len=arguments.length;
									for(i=0;i<len;i++){
										token=arguments[i];
										original.call(this,token);
									}
								};
							};
							createMethod('add');
							createMethod('remove');
						}testElement.classList.toggle('c3',false);
						//IE 10 y Firefox <24, ".toggle" no soporta un segundo argumento.
						if(testElement.classList.contains('c3')){
							original=DOMTokenList.prototype.toggle;
							DOMTokenList.prototype.toggle=function(token,force){
								if(1 in arguments&& !this.contains(token)===!force){return force;
								}else{return original.call(this,token);}
							};
						}
						testElement=null;
					}());
				}
			}
		}else if(js=="array.tolocalestring"){
			//Requiere "FIRSTELEMENT y NEXTELEMENT
			if(!Array.prototype.toLocaleString){
				Object.defineProperty(Array.prototype,'toLocaleString',{
					value:function(locales,options){
						if(this==null){throw new TypeError('"this" is null or not defined');}
						var a=Object(this),separator=',',k=1;
						var len=a.length >>> 0;
						if(len===0){return '';}
						var firstElement=a[0];
						var r=firstElement==null ?'':firstElement.toLocaleString(locales,options);
						while(k<len){
							var s=r+separator,nextElement=a[k];
							r=nextElement==null ?'':nextElement.toLocaleString(locales,options);
							r=s+r;k++;
						}return r;
					}
				});
			}
		}else if(js=="number.toLocaleDateString"){
			(function(){
				'use strict';
				function toLocaleStringSupportsLocales(){
					var number=0;
					try{number.toLocaleString("i");
					}catch(e){return e.name==="RangeError";}
					return false;
				}
				if(!toLocaleStringSupportsLocales()){
					var replaceSeparators=function(sNum,separators){
						var sNumParts=sNum.split('.');
						if(separators && separators.thousands){
						sNumParts[0]=sNumParts[0].replace(/(\d)(?=(\d\d\d)+(?!\d))/g,"$1"+separators.thousands);}
						sNum=sNumParts.join(separators.decimal);
						return sNum;};
					var renderFormat=function(template,props){
						for(var prop in props){
						if(props[prop].indexOf('-') !== -1){
							props[prop]=props[prop].replace('-','');
							template='-'+template;
						}template=template.replace("{{"+prop+"}}",props[prop]);
					}return template;};
					var mapMatch=function(map,locale){
						var match=locale;
						var language=locale && locale.toLowerCase().match(/^\w+/);
						if(!map.hasOwnProperty(locale)){
						if(map.hasOwnProperty(language)){match=language;
						}else{match="en";}
					}return map[match];};
					var dotThousCommaDec=function(sNum){
						var separators={decimal:',',thousands:'.'};
						return replaceSeparators(sNum,separators);};
					var commaThousDotDec=function(sNum){
						var separators={decimal:'.',thousands:','};
						return replaceSeparators(sNum,separators);};
					var spaceThousCommaDec=function(sNum){
						var seperators={decimal:',',thousands:'\u00A0'};
						return replaceSeparators(sNum,seperators);};
					var apostrophThousDotDec=function(sNum){
						var seperators={decimal:'.',thousands:'\u0027'};
						return replaceSeparators(sNum,seperators);};
					var transformForLocale={en:commaThousDotDec,'en-GB':commaThousDotDec,'en-US':commaThousDotDec,
						it:dotThousCommaDec,fr:spaceThousCommaDec,de:dotThousCommaDec,"de-DE":dotThousCommaDec,
						"de-AT":dotThousCommaDec,"de-CH":apostrophThousDotDec,"de-LI":apostrophThousDotDec,
						"de-BE":dotThousCommaDec,"nl":dotThousCommaDec,"nl-BE":dotThousCommaDec,
						"nl-NL":dotThousCommaDec,ro:dotThousCommaDec,"ro-RO":dotThousCommaDec,ru:spaceThousCommaDec,
						"ru-RU":spaceThousCommaDec,hu:spaceThousCommaDec,"hu-HU":spaceThousCommaDec,
						"da-DK":dotThousCommaDec,"nb-NO":spaceThousCommaDec};
					var currencyFormatMap={en:"pre",'en-GB':"pre",'en-US':"pre",it:"post",fr:"post",de:"post",
						"de-DE":"post","de-AT":"prespace","de-CH":"prespace","de-LI":"post","de-BE":"post",
						"nl":"post","nl-BE":"post","nl-NL":"post",ro:"post","ro-RO":"post",ru:"post",
						"ru-RU":"post",hu:"post","hu-HU":"post","da-DK":"post","nb-NO":"post"};
					var currencySymbols={"afn":"؋","ars":"$","awg":"ƒ","aud":"$","azn":"₼","bsd":"$","bbd":"$",
						"byr":"p.","bzd":"BZ$","bmd":"$","bob":"Bs.","bam":"KM","bwp":"P","bgn":"лв","brl":"R$",
						"bnd":"$","khr":"៛","cad":"$","kyd":"$","clp":"$","cny":"¥","cop":"$","crc":"₡","hrk":"kn",
						"cup":"₱","czk":"Kč","dkk":"kr","dop":"RD$","xcd":"$","egp":"£","svc":"$","eek":"kr",
						"eur":"€","fkp":"£","fjd":"$","ghc":"¢","gip":"£","gtq":"Q","ggp":"£","gyd":"$","hnl":"L",
						"hkd":"$","huf":"Ft","isk":"kr","inr":"₹","idr":"Rp","irr":"﷼","imp":"£","ils":"₪",
						"jmd":"J$","jpy":"¥","jep":"£","kes":"KSh","kzt":"лв","kpw":"₩","krw":"₩","kgs":"лв",
						"lak":"₭","lvl":"Ls","lbp":"£","lrd":"$","ltl":"Lt","mkd":"ден","myr":"RM","mur":"₨",
						"mxn":"$","mnt":"₮","mzn":"MT","nad":"$","npr":"₨","ang":"ƒ","nzd":"$","nio":"C$",
						"ngn":"₦","nok":"kr","omr":"﷼","pkr":"₨","pab":"B/.","pyg":"Gs","pen":"S/.","php":"₱",
						"pln":"zł","qar":"﷼","ron":"lei","rub":"₽","shp":"£","sar":"﷼","rsd":"Дин.","scr":"₨",
						"sgd":"$","sbd":"$","sos":"S","zar":"R","lkr":"₨","sek":"kr","chf":"CHF","srd":"$",
						"syp":"£","tzs":"TSh","twd":"NT$","thb":"฿","ttd":"TT$","try":"","trl":"₤","tvd":"$",
						"ugx":"USh","uah":"₴","gbp":"£","usd":"$","uyu":"$U","uzs":"лв","vef":"Bs","vnd":"₫",
						"yer":"﷼","zwd":"Z$"};
					var currencyFormats={pre:"{{code}}{{num}}",post:"{{num}}{{code}}",prespace:"{{code}}{{num}}"};
					Number.prototype.toLocaleString=function(locale,options){
						if(locale && locale.length<2)throw new RangeError("Invalid language tag:"+locale);
						var sNum;
						if(options &&(options.minimumFractionDigits || options.minimumFractionDigits===0)){
						sNum=this.toFixed(options.minimumFractionDigits);
					}else{sNum=this.toString();}
						sNum=mapMatch(transformForLocale,locale)(sNum,options);
						if(options && options.currency && options.style==="currency"){
						var format=currencyFormats[mapMatch(currencyFormatMap,locale)];
						var symbol=currencySymbols[options.currency.toLowerCase()];
						if(options.currencyDisplay==="code" || !symbol){
							sNum=renderFormat(format,{num:sNum,code:options.currency.toUpperCase()});
						}else{sNum=renderFormat(format,{num:sNum,code:symbol});}
					}return sNum;
					};
				}
			}());
		}else if(js=="padStart"){
			if(!String.prototype.padStart){
			    String.prototype.padStart=function padStart(targetLength,padString){
			        targetLength=targetLength>>0; //Trunca si el nuvero o es convertido a un non-number para 0;
			        padString=String((typeof padString!=='undefined'?padString:' '));
			        if(this.length>targetLength){
			            return String(this);
			        }else{
			            targetLength=targetLength-this.length;
			            if(targetLength>padString.length)
			                padString+=padString.repeat(targetLength/padString.length); //append to original to ensure we are longer than needed
			            return padString.slice(0,targetLength)+String(this);
			        }
			    };
			}
		}
	}
};