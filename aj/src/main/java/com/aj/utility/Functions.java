package com.aj.utility;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.FileSystems;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.aj.model.Menuprivileges;

public class Functions {
	public static final String DEFAULT_CHARSET = "WINDOWS-1252";
	public static final Integer PERFILADMINID = 1;
	public static final Integer PERFILUSERID = 2;
	public static final Integer PERFILCLIENTID = 3;
	public static ApplicationContext appContext;

	public static String toStr(Object obj) {
		if(obj==null)return "";
		String s = obj.toString().trim();
		return s.equals("") || s.trim().equals("null")|| s.trim().equals("undefined")?"":s.trim();
	}

	public static int toInt(Object obj){
		if(obj==null)return 0;
		try{
			return Integer.parseInt(Functions.toStr(obj));
		}catch (Exception ex){
			return 0;
		}
	}

	public static long toLong(Object obj){
		if(obj==null)return 0L;
		try{
			return Long.parseLong(Functions.toStr(obj));
		}catch (Exception ex){
			return 0L;
		}
	}

	public static double toDouble(Object obj){
		if(obj==null)return 0;
		try{
			return Double.parseDouble(Functions.toStr(obj));
		}catch (Exception ex){
			return 0;
		}
	}

	public static BigDecimal toBigDec(Object obj) {
		if(obj==null)return BigDecimal.ZERO;
		try{
			double tmp = toDouble(Functions.toStr(obj));
			BigDecimal dec = new BigDecimal("" + tmp);
			return dec;
		}catch (Exception ex){
			return BigDecimal.ZERO;
		}
	}

	public static String[] getStringToArray(String cadena, char delimitador) {
		cadena = cadena.trim().substring(0, cadena.trim().length() - 1);
		int conPipe = 0, inicio = 0, indexPipe = cadena.indexOf(delimitador), ban = 0, contador = 0;
		String[] arr = new String[0];
		do {
			if (indexPipe==-1) {
				ban = 1;
				if (cadena.length() != inicio) {
					arr = (String[]) resizeArray(arr, arr.length + 1);
					arr[conPipe] = cadena.substring(inicio, cadena.length());
				} else {
					arr = (String[]) resizeArray(arr, arr.length + 1);
					arr[conPipe] = "";
				}
			} else {
				if (inicio != 0 || indexPipe != 0) {
					arr = (String[]) resizeArray(arr, arr.length + 1);
					arr[conPipe] = cadena.substring(inicio, indexPipe);
					conPipe++;
				} else {
					arr = (String[]) resizeArray(arr, arr.length + 1);
					arr[conPipe] = "";
					conPipe++;
				}
			}
			inicio = indexPipe + 1;
			indexPipe = cadena.indexOf(delimitador, inicio);
			contador++;
		} while ((inicio==indexPipe) || (ban != 1));
		return arr;
	}

	public static Object resizeArray(Object oldArray, int newSize) {
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
		int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0) {
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		}
		return newArray;
	}

	public static XMLGregorianCalendar toGregorianCalendar(Date date) {
		try {
			GregorianCalendar gc = new GregorianCalendar();
			XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			return xmlCalendar;
		} catch (DatatypeConfigurationException ex) {
			Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static String formateaFecha(Date fecha, String formatoDeSalida) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatoDeSalida);
		return formatter.format(fecha);
	}

	public static Date parseFecha(String cadena, String formatoDeEntrada) {
		Date fecha = null;
		Locale es_MX = new Locale("es", "MX");
		SimpleDateFormat formatter = new SimpleDateFormat(formatoDeEntrada, es_MX);

		if(formatoDeEntrada.equals("yyyy-MM-dd")){	// FallBack horario de verano 2023
			cadena=cadena+" 12:00:00.000";
			SimpleDateFormat cdt2023cds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", es_MX);
			try {
				fecha = cdt2023cds.parse(cadena);
			}catch(ParseException e){
				e.printStackTrace();
			}
		}else{	// Proceso normal
			try{
				fecha = formatter.parse(cadena);
			}catch(ParseException e){
				e.printStackTrace();
			}
		}
		return fecha;
	}

	public static String formatFecha(String fecha, String formatoDeEntrada, String formatoDeSalida) {
		// String fechaPartida[] = fecha.split("\\-");
		SimpleDateFormat parser = new SimpleDateFormat(formatoDeEntrada);
		SimpleDateFormat formatter = new SimpleDateFormat(formatoDeSalida);
		try {
			return formatter.format(parser.parse(fecha));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fecha;
	}

	public static String revisaNumeroExponente(String numero) {
		String nuevo = numero;
		if (numero.contains("E")) {
			String arr[] = numero.split("E");
			int posicion = Integer.valueOf(arr[1]), aux = 0;
			if (numero.contains(".")) {
				int index = numero.indexOf(".");
				nuevo = arr[0].replace(".", "");
				if ((arr[0].length() - 1) >= posicion) {
					// aux=index+posicion;
					posicion += index;
					nuevo = arr[0].replace(".", "").substring(0, posicion) + "."
							+ arr[0].replace(".", "").substring(posicion);
					System.out.println(nuevo);
				} else {
					for (int i = 1; i < posicion - 1; i++) {
						nuevo += "0";
					}
				}
			}
		}
		return nuevo;
	}

	public static XMLGregorianCalendar getXMLCalendar(String strDate, String datePattern) {
		// "yyyy.MM.dd'T'HH:mm:ss"
		Calendar sDate = Calendar.getInstance();
		DatatypeFactory dtf;
		XMLGregorianCalendar calendar = null;
		try {
			dtf = DatatypeFactory.newInstance();
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(datePattern);
			if (strDate != null) {
				sDate.setTime(DATE_FORMAT.parse(strDate));
				calendar = dtf.newXMLGregorianCalendar();
				calendar.setYear(sDate.get(Calendar.YEAR));
				calendar.setDay(sDate.get(Calendar.DAY_OF_MONTH));
				calendar.setMonth(sDate.get(Calendar.MONTH) + 1);
				calendar.setTime(sDate.get(Calendar.HOUR), sDate.get(Calendar.MINUTE), sDate.get(Calendar.SECOND));
			}
		} catch (DatatypeConfigurationException ex) {
		} catch (ParseException ex) {
		}
		return calendar;
	}

	public static boolean isEmpty(Object s) {
		return ((s==null) || Functions.toStr(s).equals("") || Functions.toStr(s).trim().equals("null")
			|| Functions.toStr(s).trim().equals("undefined"));
	}

	public static String truncateDecimal(Object valor, int decimales) {
		BigDecimal val = new BigDecimal(Functions.toStr(valor).trim());
		boolean negativo = (Functions.toStr(valor).trim().charAt(0)=='-');
		String valFinal = "";
		if (negativo) {
			valFinal = "-";
		}
		valFinal += Functions.toStr(val.abs().setScale(decimales, RoundingMode.DOWN));
		return valFinal;
	}

	public static BigInteger toBigInteger(String val) {
		if (val==null || val.trim().length()==0) {
			return BigInteger.ZERO;
		}
		try {
			return new BigInteger(val.trim());
		} catch (Exception ex) {
			return BigInteger.ZERO;
		}
	}

	public static boolean isWindows() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("win") >= 0);
	}

	// @RequestMapping(value={"/jGetLanguageURL"},method=RequestMethod.POST)
	// public @ResponseBody
	// static String jGetLanguageURL(HttpServletRequest
	// request,HttpServletResponse response){
	public static String jGetLanguageURL() {
		HttpServletRequest request = null;
		String l = "en";
		String Uri = request.getRequestURL() + "?" + request.getQueryString();
		String[] q = request.getQueryString().split("&");
		for (int v = 0; v < q.length; v++) {
			String[] p = q[v].split("=");
			Pattern pat = Pattern.compile("[.*+?^${}()|\\]", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			p[0] = pat.matcher(p[0]).replaceAll("");
			if (("language"==p[0])) {
				l = p[1];
			}
		}
		return l;
	}

	public String getFormatPatternDate(String lang) {
		if (lang=="") {
			jGetLanguageURL();
		}
		String formats[] = { "es", "dd/MM/yyyy", "af-ZA", "yyyy/MM/dd", "am-ET", "d/M/yyyy", "ar-AE", "dd/MM/yyyy",
				"ar-BH", "dd/MM/yyyy", "ar-DZ", "dd-MM-yyyy", "ar-EG", "dd/MM/yyyy", "ar-IQ", "dd/MM/yyyy", "ar-JO",
				"dd/MM/yyyy", "ar-KW", "dd/MM/yyyy", "ar-LB", "dd/MM/yyyy", "ar-LY", "dd/MM/yyyy", "ar-MA",
				"dd-MM-yyyy", "ar-OM", "dd/MM/yyyy", "ar-QA", "dd/MM/yyyy", "ar-SA", "dd/MM/yy", "ar-SY", "dd/MM/yyyy",
				"ar-TN", "dd-MM-yyyy", "ar-YE", "dd/MM/yyyy", "as-IN", "dd-MM-yyyy", "ba-RU", "dd.MM.yy", "be-BY",
				"dd.MM.yyyy", "bg-BG", "dd.M.yyyy", "bn-BD", "dd-MM-yy", "bn-IN", "dd-MM-yy", "bo-CN", "yyyy/M/d",
				"br-FR", "dd/MM/yyyy", "ca-ES", "dd/MM/yyyy", "co-FR", "dd/MM/yyyy", "cs-CZ", "d.M.yyyy", "cy-GB",
				"dd/MM/yyyy", "da-DK", "dd-MM-yyyy", "de-AT", "dd.MM.yyyy", "de-CH", "dd.MM.yyyy", "de-DE",
				"dd.MM.yyyy", "de-LI", "dd.MM.yyyy", "de-LU", "dd.MM.yyyy", "dv-MV", "dd/MM/yy", "el-GR", "d/M/yyyy",
				"en-AU", "d/MM/yyyy", "en-BZ", "dd/MM/yyyy", "en-CA", "dd/MM/yyyy", "en-GB", "dd/MM/yyyy", "en-IE",
				"dd/MM/yyyy", "en-IN", "dd-MM-yyyy", "en-JM", "dd/MM/yyyy", "en-MY", "d/M/yyyy", "en-NZ", "d/MM/yyyy",
				"en-PH", "M/d/yyyy", "en-SG", "d/M/yyyy", "en-TT", "dd/MM/yyyy", "en-US", "M/d/yyyy", "en-ZA",
				"yyyy/MM/dd", "en-ZW", "M/d/yyyy", "es-AR", "dd/MM/yyyy", "es-BO", "dd/MM/yyyy", "es-CL", "dd-MM-yyyy",
				"es-CO", "dd/MM/yyyy", "es-CR", "dd/MM/yyyy", "es-DO", "dd/MM/yyyy", "es-EC", "dd/MM/yyyy", "es-ES",
				"dd/MM/yyyy", "es-GT", "dd/MM/yyyy", "es-HN", "dd/MM/yyyy", "es-MX", "dd/MM/yyyy", "es-NI",
				"dd/MM/yyyy", "es-PE", "dd/MM/yyyy", "es-PR", "dd/MM/yyyy", "es-PY", "dd/MM/yyyy", "es-SV",
				"dd/MM/yyyy", "es-US", "M/d/yyyy", "es-UY", "dd/MM/yyyy", "es-VE", "dd/MM/yyyy", "et-EE", "d.MM.yyyy",
				"eu-ES", "yyyy/MM/dd", "fi-FI", "d.M.yyyy", "fil-PH", "M/d/yyyy", "fo-FO", "dd-MM-yyyy", "fr-BE",
				"d/MM/yyyy", "fr-CA", "yyyy-MM-dd", "fr-CH", "dd.MM.yyyy", "fr-FR", "dd/MM/yyyy", "fr-LU", "dd/MM/yyyy",
				"fr-MC", "dd/MM/yyyy", "fy-NL", "d-M-yyyy", "ga-IE", "dd/MM/yyyy", "gd-GB", "dd/MM/yyyy", "gl-ES",
				"dd/MM/yy", "gu-IN", "dd-MM-yy", "he-IL", "dd/MM/yyyy", "hi-IN", "dd-MM-yyyy", "hr-BA", "d.M.yyyy.",
				"hr-HR", "d.M.yyyy", "hu-HU", "yyyy. MM. dd.", "hy-AM", "dd.MM.yyyy", "id-ID", "dd/MM/yyyy", "ig-NG",
				"d/M/yyyy", "ii-CN", "yyyy/M/d", "is-IS", "d.M.yyyy", "it-CH", "dd.MM.yyyy", "it-IT", "dd/MM/yyyy",
				"ja-JP", "yyyy/MM/dd", "ka-GE", "dd.MM.yyyy", "kk-KZ", "dd.MM.yyyy", "kl-GL", "dd-MM-yyyy", "km-KH",
				"yyyy-MM-dd", "kn-IN", "dd-MM-yy", "ko-KR", "yyyy-MM-dd", "ky-KG", "dd.MM.yy", "lb-LU", "dd/MM/yyyy",
				"lo-LA", "dd/MM/yyyy", "lt-LT", "yyyy.MM.dd", "lv-LV", "yyyy.MM.dd.", "mi-NZ", "dd/MM/yyyy", "mk-MK",
				"dd.MM.yyyy", "ml-IN", "dd-MM-yy", "mn-MN", "yy.MM.dd", "mr-IN", "dd-MM-yyyy", "ms-BN", "dd/MM/yyyy",
				"ms-MY", "dd/MM/yyyy", "mt-MT", "dd/MM/yyyy", "nb-NO", "dd.MM.yyyy", "ne-NP", "M/d/yyyy", "nl-BE",
				"d/MM/yyyy", "nl-NL", "d-M-yyyy", "nn-NO", "dd.MM.yyyy", "oc-FR", "dd/MM/yyyy", "or-IN", "dd-MM-yy",
				"pa-IN", "dd-MM-yy", "pl-PL", "yyyy-MM-dd", "ps-AF", "dd/MM/yy", "pt-BR", "d/M/yyyy", "pt-PT",
				"dd-MM-yyyy", "rm-CH", "dd/MM/yyyy", "ro-RO", "dd.MM.yyyy", "ru-RU", "dd.MM.yyyy", "rw-RW", "M/d/yyyy",
				"sa-IN", "dd-MM-yyyy", "se-FI", "d.M.yyyy", "se-NO", "dd.MM.yyyy", "se-SE", "yyyy-MM-dd", "si-LK",
				"yyyy-MM-dd", "sk-SK", "d. M. yyyy", "sl-SI", "d.M.yyyy", "sq-AL", "yyyy-MM-dd", "sv-FI", "d.M.yyyy",
				"sv-SE", "yyyy-MM-dd", "sw-KE", "M/d/yyyy", "ta-IN", "dd-MM-yyyy", "te-IN", "dd-MM-yy", "th-TH",
				"d/M/yyyy", "tk-TM", "dd.MM.yy", "tn-ZA", "yyyy/MM/dd", "tr-TR", "dd.MM.yyyy", "tt-RU", "dd.MM.yyyy",
				"ug-CN", "yyyy-M-d", "uk-UA", "dd.MM.yyyy", "ur-PK", "dd/MM/yyyy", "vi-VN", "dd/MM/yyyy", "wo-SN",
				"dd/MM/yyyy", "xh-ZA", "yyyy/MM/dd", "yo-NG", "d/M/yyyy", "zh-CN", "yyyy/M/d", "zh-HK", "d/M/yyyy",
				"zh-MO", "d/M/yyyy", "zh-SG", "d/M/yyyy", "zh-TW", "yyyy/M/d", "zu-ZA", "yyyy/MM/dd", "arn-CL",
				"dd-MM-yyyy", "dsb-DE", "d. M. yyyy", "gsw-FR", "dd/MM/yyyy", "hsb-DE", "d. M. yyyy", "kok-IN",
				"dd-MM-yyyy", "moh-CA", "M/d/yyyy", "nso-ZA", "yyyy/MM/dd", "prs-AF", "dd/MM/yy", "qut-GT",
				"dd/MM/yyyy", "quz-BO", "dd/MM/yyyy", "quz-EC", "dd/MM/yyyy", "quz-PE", "dd/MM/yyyy", "sah-RU",
				"MM.dd.yyyy", "sma-NO", "dd.MM.yyyy", "sma-SE", "yyyy-MM-dd", "smj-NO", "dd.MM.yyyy", "smj-SE",
				"yyyy-MM-dd", "smn-FI", "d.M.yyyy", "sms-FI", "d.M.yyyy", "syr-SY", "dd/MM/yyyy", "az-Cyrl-AZ",
				"dd.MM.yyyy", "az-Latn-AZ", "dd.MM.yyyy", "bs-Cyrl-BA", "d.M.yyyy", "bs-Latn-BA", "d.M.yyyy",
				"ha-Latn-NG", "d/M/yyyy", "iu-Cans-CA", "d/M/yyyy", "iu-Latn-CA", "d/MM/yyyy", "mn-Mong-CN", "yyyy/M/d",
				"sr-Cyrl-BA", "d.M.yyyy", "sr-Cyrl-CS", "d.M.yyyy", "sr-Cyrl-ME", "d.M.yyyy", "sr-Cyrl-RS", "d.M.yyyy",
				"sr-Latn-BA", "d.M.yyyy", "sr-Latn-CS", "d.M.yyyy", "sr-Latn-ME", "d.M.yyyy", "sr-Latn-RS", "d.M.yyyy",
				"tg-Cyrl-TJ", "dd.MM.yy", "tzm-Latn-DZ", "dd-MM-yyyy", "uz-Cyrl-UZ", "dd.MM.yyyy", "uz-Latn-UZ",
				"dd/MM yyyy" };
		String pattern = "";
		for (int i = 0; i < formats.length; i++) {
			if (formats[i].equals(lang)) {
				pattern = formats[i + 1];
				break;
			}
		}
		if (pattern=="") {
			pattern = "MM/dd/yyyy";
		}
		return pattern;
	}

	public static String twoDigits(int strNum) {
		String n = "0";
		if (strNum < 10) {
			n += String.valueOf(strNum);
		}
		return n;
	}

	public static Calendar jStrToISODate(String strDate, String pattern) {
		// Convierte un texto con contenido de fecha (strDate) con cierto
		// formato (pattern) a variable Calendar Date de Java.
		// "strDate" deberá ser un texto pero con formato de fecha y acorde a un
		// formato (pattern):
		// dd/MM/yyy ó MM/dd/yyyy ó d/m/yy, d/M/yyyy HH:mm etc, o milisegundos.
		// "pattern" deberá ser un patrón de formato de fecha y en acorde a
		// "strDate", sino se indica o no coincide, será compado
		// con el formatato americano o en último caso, se comparará con formato
		// ISO:
		// 1) Formato ISO: "yyyy-m-d", "yyyy-mm-dd" o "yyyy-mm-ddTHH:mm:ss"
		// 2) "año, mes, dia, hr, min, seg"
		// Si no se indicó una fecha u hora, se tomará del sistema.
		Pattern pat = Pattern.compile("[.,_:;tT/\\s]", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		strDate = pat.matcher(strDate).replaceAll("-");
		pat = Pattern.compile("[()]", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		strDate = pat.matcher(strDate).replaceAll("");
		if (pattern=="") {
			pattern = "MM-dd-yyyy";
		}

		try {
			String dParts[] = strDate.split("-");
			Calendar dVal1 = Calendar.getInstance();
			Boolean fail = false;
			int d = dVal1.get(Calendar.DATE), M = dVal1.get(Calendar.MONTH) - 1, y = dVal1.get(Calendar.YEAR),
					h = dVal1.get(Calendar.HOUR), m = dVal1.get(Calendar.MINUTE), s = dVal1.get(Calendar.SECOND);
			pat = Pattern.compile("[.,_:/\\s]", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			strDate = pat.matcher(strDate).replaceAll("-");
			String bPattern[] = pattern.split("-");
			for (int i = 0; i < dParts.length; i++) {
				if (dParts[i]=="")
					dParts[i] = "0";
			}
			for (int i = 0; i < bPattern.length; i++) {
				if ((bPattern[i] != "") && (bPattern[i].indexOf("d") > -1))
					if (dParts[i] != "")
						d = toInt(dParts[i]);
				if ((bPattern[i] != "") && (bPattern[i].indexOf("M") > -1))
					if (dParts[i] != "")
						M = toInt(dParts[i]);
				if ((bPattern[i] != "") && (bPattern[i].indexOf("y") > -1))
					if (dParts[i] != "")
						y = toInt(dParts[i]);
				if ((bPattern[i] != "") && ((bPattern[i].indexOf("H")) > -1) || ((bPattern[i].indexOf("h")) > -1))
					if (dParts[i] != "")
						h = toInt(dParts[i]);
				if ((bPattern[i] != "") && (bPattern[i].indexOf("m") > -1))
					if (dParts[i] != "")
						m = toInt(dParts[i]);
				if ((bPattern[i] != "") && (bPattern[i].indexOf("s") > -1))
					if (dParts[i] != "")
						s = toInt(dParts[i]);
			}
			Calendar dValidPat = Calendar.getInstance();
			dValidPat.set(toInt(twoDigits(y)), toInt(twoDigits(M)),
					toInt(twoDigits(d)), toInt(twoDigits(h)), toInt(twoDigits(m)),
					toInt(twoDigits(s)));
			dValidPat.set(Calendar.DATE, d);
			for (int i = 0; i < bPattern.length; i++) {
				if (twoDigits(d) != twoDigits(dValidPat.get(Calendar.DATE))) {
					fail = true;
					break;
				}
				if (twoDigits(M) != twoDigits((dValidPat.get(Calendar.MONTH)) + 1)) {
					fail = true;
					break;
				}
				if (twoDigits(y) != twoDigits(dValidPat.get(Calendar.YEAR))) {
					fail = true;
					break;
				}
				if (twoDigits(h) != twoDigits(dValidPat.get(Calendar.HOUR))) {
					fail = true;
					break;
				}
				if (twoDigits(m) != twoDigits(dValidPat.get(Calendar.MINUTE))) {
					fail = true;
					break;
				}
				if (twoDigits(s) != twoDigits(dValidPat.get(Calendar.SECOND))) {
					fail = true;
					break;
				}
			}
			if (dParts[0].length() > 10) {
				Calendar tmpDate = Calendar.getInstance();
				tmpDate.setTimeInMillis(toLong(dParts[0]));
				return tmpDate;
			}
			if (fail) {
				Calendar isoDate = Calendar.getInstance();
				if (dParts[2] != "")
					isoDate.set(Calendar.DATE, toInt(dParts[2]));
				if (dParts[1] != "")
					isoDate.set(Calendar.MONTH, toInt(dParts[1]) - 1);
				if (dParts[0] != "")
					isoDate.set(Calendar.YEAR, toInt(dParts[0]));
				if (dParts[3] != "")
					isoDate.set(Calendar.HOUR_OF_DAY, toInt(dParts[3]));
				if (dParts[4] != "")
					isoDate.set(Calendar.MINUTE, toInt(dParts[4]));
				if (dParts[5] != "")
					isoDate.set(Calendar.SECOND, toInt(dParts[5]));
				return isoDate;
			}
			return dValidPat;
		} catch (Exception ex) {
			return null;
		}
	}

	public static String jSetFormatPatternDate(String sDate, String patternOut, String lang) {
		// Requiere de "toLocaleDateString" para obtener los nombres de días y
		// meses en otros idiomas
		// Retorna una cadena con el formato (patternOut) requerido de fecha y/u
		// hora.
		// "date" deberá ser alguno de estos formatos:
		// 1) Texto con formato ISO: "yyyy-m-d", "yyyy-mm-dd" o
		// "yyyy-mm-ddTHH:mm:ss"
		// 2) Texto separado por comas: "año, mes, dia[, hr, min, seg[,
		// milseg]]"
		// 3) Fecha tipo Date
		// 4) Fecha en milisegundos
		// Si no se indicó una fecha/hora, se tomará del sistema.
		// "patternOut" es el tipo de patrón a regresar (Letras estandares para
		// fecha y hora). Si no es indicado, se tomará el inglés
		// Para muestrar "am" o "pm" los formatos son:
		// "a","am","a.m.","A","AM","A.M."
		// "lang" Son dos caracteres indicando el idioma que se tomará como base
		// para extraer los textos (o ideogramas) de los días y meses:
		// 1) Por ejemplo, para el español se ingresará "es"
		// 2) Si no se indica "lang", verificará si existe la variable
		// "language=" en la URL
		// 3) Si no se indica ningún idioma y de ser necesario, tomará el inglés
		// por default.
		if (patternOut=="")
			patternOut = "MM/dd/yyyy";
		Calendar date = Calendar.getInstance();
		if (sDate != "") {
			sDate = String.valueOf(date.get(Calendar.YEAR)) + "," + String.valueOf(date.get(Calendar.MONTH) - 1) + ","
					+ String.valueOf(date.get(Calendar.DATE)) + " " + String.valueOf(date.get(Calendar.HOUR)) + ","
					+ String.valueOf(date.get(Calendar.MINUTE)) + "," + String.valueOf(date.get(Calendar.SECOND));
		}
		date = jStrToISODate(sDate, patternOut);
		if (lang=="")
			lang = jGetLanguageURL();
		int a1 = 0, a2 = 0; // ,z=date.get(Calendar.SHORT_FORMAT),zz=date.get(Calendar.LONG_FORMAT);
		int z = 0, zz = 0;
		@SuppressWarnings("unused")
		Calendar styear = Calendar.getInstance();
		int week = 0; // date.get(Calendar.WEEK_OF_YEAR);
		// String monthNames[],dayOfWeekNames[],ap[]={"",""};
		for (int i = 0; i < patternOut.length(); i++)
			if ((patternOut.substring(i, i + 1)=="a") || (patternOut.substring(i, i + 1)=="A")
			|| (patternOut.substring(i, i + 1)==".")) {
				if(a1==0)
					a1 = i;
				if(patternOut.substring(i + 1, i + 2)==""){
					a2 = i + 1;
					break;
				}
			}
		String ampm = patternOut.substring(a1, a2);
		String ap[] = {"", ""};
		ap[0] = (ampm=="a") ? "a"
			:(ampm=="aa") ? "am"
				: (ampm=="a.a.") ? "a.m."
					:(ampm=="A") ? "A" : (ampm=="AA") ? "AM" : (ampm=="A.A.") ? "A.M." : "";
		ap[1] = (ampm=="a") ? "p"
			:(ampm=="aa") ? "pm"
				:(ampm=="a.a.") ? "p.m."
					:(ampm=="A") ? "P" : (ampm=="AA") ? "PM" : (ampm=="A.A.") ? "P.M." : "";

		String monthNames[] = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};
		String dayOfWeekNames[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

		/*
		 * for(c=0;c<12;c++){ var convertText=new Date(2000, c, 1),monthname;
		 * try{ monthname=convertText.toLocaleDateString(lang, {month:'long'});
		 * }catch(em){
		 * monthNames=["January","February","March","April","May","June",
		 * "July","August","September","October","November","December"];break;}
		 * monthNames[c]=monthname; } for(c=0;c<7;c++){ var convertText=new
		 * Date(2000, 01, c),dayname; try{
		 * dayname=convertText.toLocaleDateString(lang, {weekday:'long'});
		 * }catch(ed){ dayOfWeekNames=["Sunday","Monday","Tuesday",
		 * "Wednesday","Thursday","Friday","Saturday"];break;}
		 * dayOfWeekNames[c]=dayname; }
		 */
		/*
		 * int day =date.get(Calendar.DATE), month=date.get(Calendar.MONTH),
		 * year=date.get(Calendar.YEAR), hour=date.get(Calendar.HOUR),
		 * minute=date.get(Calendar.MINUTE), second=date.get(Calendar.SECOND),
		 * M=month + 1, h=hour % 12, miliseconds=date.get(Calendar.MILLISECOND);
		 */
		int day = date.get(Calendar.DATE);
		int month = date.get(Calendar.MONTH);
		int year = date.get(Calendar.YEAR);
		int hour = date.get(Calendar.HOUR);
		int minute = date.get(Calendar.MINUTE);
		int second = date.get(Calendar.SECOND);
		int M = month + 1;
		int h = hour % 12;
		int miliseconds = date.get(Calendar.MILLISECOND);
		String hh = twoDigits(h), HH = twoDigits(hour), mm = twoDigits(minute), ss = twoDigits(second),
				aaa = hour < 12 ? ap[0] : ap[1], dd = twoDigits(day),
				dddd = dayOfWeekNames[date.get(Calendar.DAY_OF_WEEK)], ddd = dddd.substring(0, 3), MM = twoDigits(M),
				MMMM = monthNames[month], MMM = MMMM.substring(0, 3), ww = twoDigits(week), yyyy = year + "",
				yy = yyyy.substring(2, 2);
		/*
		 * patternOut=patternOut.replaceAll("hh", hh);
		 * patternOut=patternOut.replaceAll("h", String.valueOf(h));
		 * patternOut=patternOut.replaceAll("HH", HH);
		 * patternOut=patternOut.replaceAll("H", String.valueOf(hour));
		 * patternOut=patternOut.replaceAll("mm", mm);
		 * patternOut=patternOut.replaceAll("m", String.valueOf(minute));
		 * patternOut=patternOut.replaceAll("ss", ss);
		 * patternOut=patternOut.replaceAll("s", String.valueOf(second));
		 * patternOut=patternOut.replaceAll("S", String.valueOf(miliseconds));
		 * patternOut=patternOut.replaceAll("ww", ww);
		 * patternOut=patternOut.replaceAll("w", String.valueOf(week));
		 * patternOut=patternOut.replaceAll("yyyy",yyyy);
		 * patternOut=patternOut.replaceAll("yy", yy);
		 * patternOut=patternOut.replaceAll("dddd",dddd);
		 * patternOut=patternOut.replaceAll("ddd", ddd);
		 * patternOut=patternOut.replaceAll("dd", dd);
		 * patternOut=patternOut.replaceAll("d", String.valueOf(day));
		 * patternOut=patternOut.replaceAll("MMMM",MMMM);
		 * patternOut=patternOut.replaceAll("MMM", MMM);
		 * patternOut=patternOut.replaceAll("MM", MM);
		 * patternOut=patternOut.replaceAll("M", String.valueOf(M));
		 * patternOut=patternOut.replaceAll("zz", String.valueOf(zz));
		 * patternOut=patternOut.replaceAll("z", String.valueOf(z));
		 * patternOut=patternOut.replaceAll(ampm,aaa);
		 */
		patternOut.replaceAll("hh", hh).replaceAll("h", String.valueOf(h)).replaceAll("HH", HH)
				.replaceAll("H", String.valueOf(hour)).replaceAll("mm", mm).replaceAll("m", String.valueOf(minute))
				.replaceAll("ss", ss).replaceAll("s", String.valueOf(second))
				.replaceAll("S", String.valueOf(miliseconds)).replaceAll("ww", ww).replaceAll("w", String.valueOf(week))
				.replaceAll("yyyy", yyyy).replaceAll("yy", yy).replaceAll("dddd", dddd).replaceAll("ddd", ddd)
				.replaceAll("dd", dd).replaceAll("d", String.valueOf(day)).replaceAll("MMMM", MMMM)
				.replaceAll("MMM", MMM).replaceAll("MM", MM).replaceAll("M", String.valueOf(M))
				.replaceAll("zz", String.valueOf(zz)).replaceAll("z", String.valueOf(z)).replaceAll(ampm, aaa);
		return patternOut;
	}

	public static Map<String, String> parseErrors(BindingResult result) {
		return result.getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
	}

	public static String formatCurrency(double val) {
		String result = null;
		try {
			DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en", "US"));
			df.applyPattern("###,###,##0.00");
			result = df.format(val);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;
	}

	public static String addPath(String rootPath, String addfolder, boolean createFolder) {
		rootPath += FileSystems.getDefault().getSeparator() + addfolder + FileSystems.getDefault().getSeparator();
		File folderx = new File(rootPath);
		if (!folderx.exists() && createFolder)
			folderx.mkdirs();

		return rootPath;
	}

	static Properties propiedades;

	public static synchronized String getRootPath() {
		if (propiedades==null) {
			propiedades = new Properties();
			try {
				propiedades.load(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//System.out.println("carga ruta default:"+ Functions.toStr(propiedades.get("savePath")));
		return Functions.toStr(propiedades.get("savePath"));
	}

	public static File[] moveFiles(String pathOrigen, String pathDestino, String[] nameFiles) {
		File[] files = null;
		File origen = null;
		if (nameFiles != null)
			for (String nameFile : nameFiles) {
				origen = new File(addPath(pathOrigen, nameFile, false));
				origen.renameTo(new File(pathDestino, nameFile));

				if (files==null)
					files = new File[1];
				else
					files = (File[]) Functions.resizeArray(files, files.length + 1);
				files[files.length - 1] = new File(addPath(pathDestino, nameFile, false));

			}

		return files;
	}

	public static String getCookieJsesion(HttpServletRequest req) {
		String jsession = "";
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals("JSESSIONID"))
				jsession = cookie.getValue();
		}
		return jsession;
	}

	public static String findExtentionFile(String fileName){
		String ext = fileName.replaceAll(".*\\.(.*)$","$1").toLowerCase();
		if(isEmpty(ext))return null;
		return "resources/assets/images/"+(Arrays.asList("doc","docx","odt","rtf").indexOf(ext)>=0?"word.png"
			:Arrays.asList("odp","otp","pps","ppsx","ppt","pptx").indexOf(ext)>=0?"powerpoint.png"
			:Arrays.asList("csv","ods","xls","xlsx").indexOf(ext)>=0?"excel.png":ext.equals("pdf")?"pdf.png":"");
	}

	public static final int READ = 4;
	public static final int ADD = 1;
	public static final int DEL = 3;
	public static final int EDIT = 2;

	public static boolean containPermission(int permiso, List<Menuprivileges> l, int menu, int role) {
		boolean exist = false;
		for (Menuprivileges menuprivileges : l) {
			if (menuprivileges.getMenuid()==menu && menuprivileges.getRoleid()==role
					&& menuprivileges.getPrivilegesid()==permiso) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	public static String bytesFormatter(long bytes){
		if (-1000 < bytes && bytes < 1000)
			return bytes + " bytes";
		CharacterIterator ci = new StringCharacterIterator("kMGTPE");
		while (bytes <= -999_950 || bytes >= 999_950) {
			bytes /= 1000;
			ci.next();
		}
		return String.format("%.1f %cB", bytes / 1000.0, ci.current());
	}

	/**	Verifica que el contenido de una variable sea numérica.
	@param strNum	Cadena de texto con el contenido del 'posible número' a analizar.
	@return			Boolean 'true' sí es númerico o 'false' sí no lo es.	*/
	public static boolean isNumeric(String strNum){
		if(strNum==null)return false;
	    return (strNum).matches("-?\\d+(\\.\\d+)?");
	}

	/** Convierte un texto con contenido numérico a Integer.
	@param strNum	String a analizar con contenido numérico.
	@return			Número convertido de String a Integer.
	@throws			Cero (0) en caso de errores.	*/
	public static int strToInt(Object strNum){
		String n = toStr(strNum);
		return (Functions.toStr(n)).matches("-?\\d+(\\.\\d+)?")?toInt(n):0;
	}

	public static String normalizeAccents(String input){
		String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(normalizedString).replaceAll("");
	}

	/** Elimina los valores duplicados a partir de un String.
	@param	str		String	Valores separados por coma.
	@return	Cadena de texto separado por comas.	*/
	public static String noDuplicatesStr(String str){
		Set<String> uniqueSet = new LinkedHashSet<>(Arrays.asList(str.split(",")));
        return uniqueSet.stream()
            .map(String::trim)
            .collect(Collectors.joining(","));
    }

	/**	Elimina los valores duplicados a partir de un String[] array
	@param	strArray	String[]	Valores dentro de una variable de arreglo.
	@return	Cadena de texto separado por comas.	*/
	public static String noDuplicatesStrArr(String[] strArray){
		HashSet<String> uniqueWords = new HashSet<>();
		List<String> repeatedWords = new ArrayList<>();
		for(String str : strArray)
			if (!uniqueWords.add(str))
				repeatedWords.add(str);
		String ret = uniqueWords.toString();
		return (ret.replaceAll("[\\[\\]\\s]",""));
	}

	/**	Convierte una variable HashSet-String a cadena de textos y ordenado alfabéticamente.
	@param	origin	(HashSet String)	Cadena de caracteres con los valores separados por un caracter.
	@param	separator1	(String)	Caracter que separa un dato de otro.
	@param	separator2	(String)	Caracter que separa un registro de otro.
	@return	Cadena de texto separado por comas.	*/
	public static String hashSetToStr(HashSet<String> origin, String separator1, String separator2){
		List<String> tmpList = new ArrayList<String>(origin);
		Collections.sort(tmpList);
		String newStr=Functions.toStr(tmpList);
		newStr=newStr
			.replaceAll("\\" + separator1 + "null","\\" + separator1)
			.replaceAll("^\\[", "").replaceAll("\\]$","")
            .replaceAll("\\" + separator2 + ", ","\\" + separator2)
            .replaceAll("\\" + separator2 + "$","");
		return newStr;
	}
/*
	public static String normalizeAccents(String input) {
        return StringUtilities.normalizeAccents(input);
    }

    public static String normalizeAccents(String input, PageContext pageContext) {
        return normalizeAccents(input);
    }*/
}