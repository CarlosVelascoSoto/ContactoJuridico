package com.aj.controller;
/** Este Controller contiene lo relacionado con Generalidades de Firmas, abogados y redes sociales para Firmas **/


import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aj.model.Ciudades;
import com.aj.model.CommunicationLabels;
import com.aj.model.CommunicationTypes;
import com.aj.model.Companies;
import com.aj.model.Companyclients;
import com.aj.model.Estados;
import com.aj.model.Fiscalsdata;
import com.aj.model.Juicios;
import com.aj.model.LawyerAddressBook;
import com.aj.model.LawyerDirectory;
import com.aj.model.Menu;
import com.aj.model.Menuprivileges;
import com.aj.model.SharedDockets;
import com.aj.model.Socialnetworks;
import com.aj.model.Users;
import com.aj.service.AccessDbJAService;
import com.aj.service.CiudadesService;
import com.aj.service.CommLabelsService;
import com.aj.service.CommunicationTypesService;
import com.aj.service.CompaniesService;
import com.aj.service.CompanyclientsService;
import com.aj.service.EstadosService;
import com.aj.service.FiscalsdataService;
import com.aj.service.JuiciosService;
import com.aj.service.LawyerAddressBookService;
import com.aj.service.LawyerDirectoryService;
import com.aj.service.SharedDocketsService;
import com.aj.service.SocialnetworkService;
import com.aj.service.UserService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class FirmsController {
	@Autowired
	public CiudadesService ciudadesService;

	@Autowired
	public CompaniesService companiesService;

	@Autowired
	public CompanyclientsService companyclientService;

	@Autowired
	public CommLabelsService commlabelsService;

	@Autowired
	private CommonController commonsCtrll;

	@Autowired
	public CommunicationTypesService commtypeService;

	@Autowired
	public EstadosService estadosService;

	@Autowired
	public FiscalsdataService fiscalsdataService;

	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public LawyerAddressBookService lawyeraddressService;

	@Autowired
	public LawyerDirectoryService lawyerdirectoryService;

	@Autowired
	private NotificationsController notificationsCtrll;

	@Autowired
	public SharedDocketsService sharedDocketsService;

	@Autowired
	public SocialnetworkService socialnetworkService;

	@Autowired
	public UserService userService;

	@SuppressWarnings("rawtypes")
	@Autowired
	public AccessDbJAService dao;

	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static final int ROLE_SYSADMIN= 1;
	public static final int ROLE_CJADMIN = 2;
	public static final int ROLE_FIRMADMIN=3;

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/companies")
	public ModelAndView companies(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");

				// Obtiene los privilegios del módulo
				@SuppressWarnings("rawtypes")
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "companies");
				Menu menu = (Menu) dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<Companies> compList = companiesService.getAll("FROM Companies ORDER BY companyid ASC");
				List<Ciudades> citiesList = null;
				List<Estados> statesList = null;
				String cdids = "", stids = "";
				for (int i = 0; i < compList.size(); i++){
					if(Functions.isNumeric(compList.get(i).getCity()))
						cdids+=compList.get(i).getCity()+",";
					if(Functions.isNumeric(compList.get(i).getState()))
						stids+=compList.get(i).getState()+",";
				}

				if(!Functions.isEmpty(cdids)){
					citiesList = ciudadesService.getAll("FROM Ciudades WHERE ciudadid IN("
						+ cdids.replaceAll(".$", "") + ") ORDER BY ciudadid ASC");
					statesList = estadosService.getAll("FROM Estados WHERE estadoid IN("
						+ stids.replaceAll(".$", "") + ") ORDER BY estadoid");
				}
				forModel.put("compList", compList);
				forModel.put("statesList",statesList);
				forModel.put("citiesList", citiesList);				
				return new ModelAndView("companies", forModel);
			}
		return new ModelAndView("login");
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@ResponseBody
	@RequestMapping(value = "/addNewCompanies")
	public void addNewCompanies(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String companyname = (req.getParameter("company") == null) ? "" : req.getParameter("company").trim(),
						address1 = (req.getParameter("address1") == null) ? "" : req.getParameter("address1").trim(),
						address2 = (req.getParameter("address2") == null) ? "" : req.getParameter("address2").trim(),
						address3 = (req.getParameter("address3") == null) ? "" : req.getParameter("address3").trim(),
						city = (req.getParameter("city") == null) ? "" : req.getParameter("city").trim(),
						country = (req.getParameter("country") == null) ? "" : req.getParameter("country").trim(),
						email = (req.getParameter("email") == null) ? "" : req.getParameter("email").trim(),
						phone1 = (req.getParameter("phone1") == null) ? "" : req.getParameter("phone1").trim(),
						phone2 = (req.getParameter("phone2") == null) ? "" : req.getParameter("phone2").trim(),
						phone3 = (req.getParameter("phone3") == null) ? "" : req.getParameter("phone3").trim(),
						state = (req.getParameter("state") == null) ? "" : req.getParameter("state").trim(),
						cp = (req.getParameter("cp") == null) ? "" : req.getParameter("cp").trim(),
						rfc = (req.getParameter("rfc") == null) ? "" : req.getParameter("rfc").trim(),
						shortname = (req.getParameter("shortname") == null) ? "" : req.getParameter("shortname").trim(),
						photo = "",
						netwAcct1 = (req.getParameter("netwAcct1") == null) ? "" : req.getParameter("netwAcct1").trim(),
						netwAcct2 = (req.getParameter("netwAcct2") == null) ? "" : req.getParameter("netwAcct2").trim(),
						netwAcct3 = (req.getParameter("netwAcct3") == null) ? "" : req.getParameter("netwAcct3").trim(),
						netwAcct4 = (req.getParameter("netwAcct4") == null) ? "" : req.getParameter("netwAcct4").trim(),
						webpage = (req.getParameter("webpage") == null) ? "" : req.getParameter("webpage").trim();
				int netw1 = (req.getParameter("netw1") == null) ? 0 : Functions.toInt(req.getParameter("netw1").trim()),
					netw2 = (req.getParameter("netw2") == null) ? 0 : Functions.toInt(req.getParameter("netw2").trim()),
					netw3 = (req.getParameter("netw3") == null) ? 0 : Functions.toInt(req.getParameter("netw3").trim()),
					netw4 = (req.getParameter("netw4") == null) ? 0 : Functions.toInt(req.getParameter("netw4").trim()),
					commlabelid1 = (req.getParameter("commlabelid1") == null) ? 0 : Functions.toInt(req.getParameter("commlabelid1")),
					commlabelid2 = (req.getParameter("commlabelid2") == null) ? 0 : Functions.toInt(req.getParameter("commlabelid2")),
					commlabelid3 = (req.getParameter("commlabelid3") == null) ? 0 : Functions.toInt(req.getParameter("commlabelid3")),
					fiscalpersontype = (req.getParameter("fiscalpersontype") == null) ? 0 : Functions.toInt(req.getParameter("fiscalpersontype"));
				resp = "";
				if (Functions.isEmpty(companyname)){
					resp="err_enter_companyname";
				}else if (Functions.isEmpty(address1)){
					resp="err_enter_address";
				}else if (Functions.isEmpty(email)){
					resp="err_enter_email";
				}else if (Functions.isEmpty(cp)){
					resp="err_enter_zipcode";
				}else if (!Functions.isEmpty(rfc)){
					rfc=rfc.toUpperCase().replaceAll("[^a-zA-Z0-9]","");
					if(!rfc.matches("^[a-zA-Z]{3,4}[0-9]{6}[a-zA-Z0-9]{3}$"))
						resp="err_enter_rfc";
				}
				if (fiscalpersontype>0){
					String businessname = (req.getParameter("businessname") == null) ? "" : req.getParameter("businessname").trim(),
						fiscaladdress1 = (req.getParameter("fiscaladdress1") == null) ? "" : req.getParameter("fiscaladdress1").trim(),
						fiscalcp = (req.getParameter("fiscalcp") == null) ? "" : req.getParameter("fiscalcp").trim();
					int fiscalcity = (req.getParameter("fiscalcity") == null) ? 0 : Functions.toInt(req.getParameter("fiscalcity"));
					if (Functions.isEmpty(businessname))
						resp="err_enter_businessname";
					else if (Functions.isEmpty(fiscaladdress1))
						resp="err_enter_address";
					else if (fiscalcity==0)
						resp="err_enter_zipcode";
					else if (Functions.isEmpty(fiscalcp))
						resp="err_enter_zipcode";
				}
				if(!Functions.isEmpty(resp)){
					try {
						res.getWriter().write(resp);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				Companies company = new Companies();
				company.setCompany(companyname);
				company.setAddress1(address1);
				company.setAddress2(address2);
				company.setAddress3(address3);
				company.setCity(city);
				company.setCountry(country);
				company.setEmail(email);
				company.setPhone(phone1);
				company.setPhone2(phone2);
				company.setPhone3(phone3);
				company.setCommunicationlabel1(commlabelid1);
				company.setCommunicationlabel2(commlabelid2);
				company.setCommunicationlabel3(commlabelid3);
				company.setState(state);
				company.setZipcode(cp);
				company.setRfc(rfc);
				company.setShortname(shortname);
				company.setSocialnetworkid1((Functions.isEmpty(netwAcct1)?null:netw1));
				company.setSocialnetworkacct1(netwAcct1);
				company.setSocialnetworkid2((Functions.isEmpty(netwAcct2)?null:netw2));
				company.setSocialnetworkacct2(netwAcct2);
				company.setSocialnetworkid3((Functions.isEmpty(netwAcct3)?null:netw3));
				company.setSocialnetworkacct3(netwAcct3);
				company.setSocialnetworkid4((Functions.isEmpty(netwAcct4)?null:netw4));
				company.setSocialnetworkacct4(netwAcct4);
				company.setWebpage(webpage);
				long newcompid = companiesService.addNewCompany(company);

				setFiscalCompanies(req, newcompid);//Datos fiscales
				if (newcompid > 0){
					// Guarda archivos relacionados con el registro
					String[] nameFiles = null;
					Enumeration enumeration = req.getParameterNames();
					while (enumeration.hasMoreElements()) {
						String parameterName = (String) enumeration.nextElement();
						if (parameterName.contains("fileuploadx_") && !Functions.isEmpty(req.getParameter(parameterName))) {
							if (nameFiles == null)
								nameFiles = new String[1];
							else
								nameFiles = (String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
							nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
						}
					}
					// Mueve archivos a destino
					String rootPath = Functions.getRootPath();
					String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
					String destinationPath = Functions.addPath(rootPath,
							"doctos" + FileSystems.getDefault().getSeparator() + "images/companieslogos", true);
					destinationPath = Functions.addPath(destinationPath, "", true);
					File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);

					if (paths != null) {
						for (File file : paths) {
							String[] filename = (file.getName()).split("\\.");
							String extFile = filename[filename.length - 1];
							File f = new File(file.getAbsolutePath());//Renombra archivo
							f.renameTo(new File(destinationPath + "/" + newcompid + "." + extFile));
							String path = f.getAbsolutePath();
							String onlyfile = f.getName();
							photo = path.replaceAll(onlyfile + "$", "") + newcompid + "." + extFile;
						}
					}
					resp = "msg_data_saved";
					try {
						FileUtils.deleteDirectory(new File(tmpPath));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else
					resp = "err_record_no_saved";
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getCompanyById")
	public Object[] getCompanyById(HttpServletRequest req, HttpServletResponse res) {
		int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
		Map<String, Object> data = new HashMap<String, Object>();
		if (id > 0) {
			List<Companies> info = companiesService.getAll("FROM Companies WHERE companyid=" + id);
			List<Fiscalsdata> fiscal = fiscalsdataService.getAll("FROM Fiscalsdata WHERE origintype=1 AND originid=" + id);
			data.put("info", info);
			data.put("fiscal", fiscal);
		}
		return new Object[] { data };
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/updateCompanies")
	public void updateCompanies(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("fdid") == null ? 0 : Functions.toLong(req.getParameter("fdid").trim());
				@SuppressWarnings("unused")
				String companyname = (req.getParameter("company") == null) ? "" : req.getParameter("company").trim(),
						address1 = (req.getParameter("address1") == null) ? "" : req.getParameter("address1").trim(),
						address2 = (req.getParameter("address2") == null) ? "" : req.getParameter("address2").trim(),
						address3 = (req.getParameter("address3") == null) ? "" : req.getParameter("address3").trim(),
						city = (req.getParameter("city") == null) ? "" : req.getParameter("city").trim(),
						country = (req.getParameter("country") == null) ? "" : req.getParameter("country").trim(),
						email = (req.getParameter("email") == null) ? "" : req.getParameter("email").trim(),
						phone1 = (req.getParameter("phone1") == null) ? "" : req.getParameter("phone1").trim(),
						phone2 = (req.getParameter("phone2") == null) ? "" : req.getParameter("phone2").trim(),
						phone3 = (req.getParameter("phone3") == null) ? "" : req.getParameter("phone3").trim(),
						state = (req.getParameter("state") == null) ? "" : req.getParameter("state").trim(),
						cp = (req.getParameter("cp") == null) ? "" : req.getParameter("cp").trim(),
						rfc = (req.getParameter("rfc") == null) ? "" : req.getParameter("rfc").trim(),
						shortname = (req.getParameter("shortname") == null) ? "" : req.getParameter("shortname").trim(),
						photo = "",
						netwAcct1 = (req.getParameter("netwAcct1") == null) ? "" : req.getParameter("netwAcct1").trim(),
						netwAcct2 = (req.getParameter("netwAcct2") == null) ? "" : req.getParameter("netwAcct2").trim(),
						netwAcct3 = (req.getParameter("netwAcct3") == null) ? "" : req.getParameter("netwAcct3").trim(),
						netwAcct4 = (req.getParameter("netwAcct4") == null) ? "" : req.getParameter("netwAcct4").trim(),
						webpage = (req.getParameter("webpage") == null) ? "" : req.getParameter("webpage").trim();
				int netw1 = (req.getParameter("netw1") == null) ? 0 : Functions.toInt(req.getParameter("netw1").trim()),
					netw2 = (req.getParameter("netw2") == null) ? 0 : Functions.toInt(req.getParameter("netw2").trim()),
					netw3 = (req.getParameter("netw3") == null) ? 0 : Functions.toInt(req.getParameter("netw3").trim()),
					netw4 = (req.getParameter("netw4") == null) ? 0 : Functions.toInt(req.getParameter("netw4").trim()),
					commlabelid1 = (req.getParameter("commlabelid1") == null) ? 0 : Functions.toInt(req.getParameter("commlabelid1")),
					commlabelid2 = (req.getParameter("commlabelid2") == null) ? 0 : Functions.toInt(req.getParameter("commlabelid2")),
					commlabelid3 = (req.getParameter("commlabelid3") == null) ? 0 : Functions.toInt(req.getParameter("commlabelid3")),
					fiscalpersontype = (req.getParameter("fiscalpersontype") == null) ? 0 : Functions.toInt(req.getParameter("fiscalpersontype"));
				resp = "";
				if (Functions.isEmpty(companyname)){
					resp="err_enter_companyname";
				}else if (Functions.isEmpty(address1)){
					resp="err_enter_address";
				}else if (Functions.isEmpty(email)){
					resp="err_enter_email";
				}else if (Functions.isEmpty(cp)){
					resp="err_enter_zipcode";
				}else if (!Functions.isEmpty(rfc)){
					rfc=rfc.toUpperCase().replaceAll("[^a-zA-Z0-9]","");
					if(!rfc.matches("^[a-zA-Z]{3,4}[0-9]{6}[a-zA-Z0-9]{3}$"))
						resp="err_enter_rfc";
				}
				if (fiscalpersontype>0){
					String businessname = (req.getParameter("businessname") == null) ? "" : req.getParameter("businessname").trim(),
						fiscaladdress1 = (req.getParameter("fiscaladdress1") == null) ? "" : req.getParameter("fiscaladdress1").trim(),
						fiscalcp = (req.getParameter("fiscalcp") == null) ? "" : req.getParameter("fiscalcp").trim();
					int fiscalcity = (req.getParameter("fiscalcity") == null) ? 0 : Functions.toInt(req.getParameter("fiscalcity"));
					if (Functions.isEmpty(businessname))
						resp="err_enter_businessname";
					else if (Functions.isEmpty(fiscaladdress1))
						resp="err_enter_address";
					else if (fiscalcity==0)
						resp="err_enter_zipcode";
					else if (Functions.isEmpty(fiscalcp))
						resp="err_enter_zipcode";
				}
				if(!Functions.isEmpty(resp)){
					try {
						res.getWriter().write(resp);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				Companies company = new Companies();
				company.setCompanyid(id);
				company.setCompany(companyname);
				company.setAddress1(address1);
				company.setAddress2(address2);
				company.setAddress3(address3);
				company.setCity(city);
				company.setCountry(country);
				company.setEmail(email);
				company.setPhone(phone1);
				company.setPhone2(phone2);
				company.setPhone3(phone3);
				company.setCommunicationlabel1(commlabelid1);
				company.setCommunicationlabel2(commlabelid2);
				company.setCommunicationlabel3(commlabelid3);
				company.setState(state);
				company.setZipcode(cp);
				company.setRfc(rfc);
				company.setShortname(shortname);
				company.setSocialnetworkid1((Functions.isEmpty(netwAcct1)?null:netw1));
				company.setSocialnetworkacct1(netwAcct1);
				company.setSocialnetworkid2((Functions.isEmpty(netwAcct2)?null:netw2));
				company.setSocialnetworkacct2(netwAcct2);
				company.setSocialnetworkid3((Functions.isEmpty(netwAcct3)?null:netw3));
				company.setSocialnetworkacct3(netwAcct3);
				company.setSocialnetworkid4((Functions.isEmpty(netwAcct4)?null:netw4));
				company.setSocialnetworkacct4(netwAcct4);
				company.setWebpage(webpage);
				companiesService.updateCompany(company);
				resp = "msg_data_saved";

				setFiscalCompanies(req, id);//Datos fiscales

				// Guarda archivos relacionados con el registro
				String[] nameFiles = null;
				Enumeration enumeration = req.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String parameterName = (String) enumeration.nextElement();
					if (parameterName.contains("fileuploadx_") && !Functions.isEmpty(req.getParameter(parameterName))) {
						if (nameFiles == null)
							nameFiles = new String[1];
						else
							nameFiles = (String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
						nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
					}
				}

				// Mueve archivos a destino e inserta rows en db
				String rootPath = Functions.getRootPath();
				String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
				String destinationPath = Functions.addPath(rootPath,
						"doctos" + FileSystems.getDefault().getSeparator() + "images/companieslogos", true);
				destinationPath = Functions.addPath(destinationPath, "", true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);

				if (paths != null) {
					for (File file : paths) {
						String[] filename = (file.getName()).split("\\.");
						String extFile = filename[filename.length - 1];
						File f = new File(file.getAbsolutePath());// Renombra el archivo
						f.renameTo(new File(destinationPath + "/" + id + "." + extFile));
						String path = f.getAbsolutePath();
						String onlyfile = f.getName();
						photo = path.replaceAll(onlyfile + "$", "") + id + "." + extFile;
					}
				}
				try {
					FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteCompanies")
	public void deleteCampanies(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id").trim());
				companiesService.deleteCompany(id);
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getCompClientByCCid")
	public Object[] getCompClientByCCid(HttpServletRequest req, HttpServletResponse res) {
		int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
		Map<String, Object> data = new HashMap<String, Object>();
		if (id > 0) {
			List<Companyclients> info = companyclientService.getAll("FROM Companyclients WHERE companyclientid=" + id);
			data.put("detail", info);
		}
		return new Object[] { data };
	}

	@RequestMapping(value = "/setFiscalCompanies")
	public @ResponseBody String setFiscalCompanies(HttpServletRequest req, long companyid){
		String businessname = (req.getParameter("businessname") == null) ? "" : req.getParameter("businessname").trim(),
			commercialname = (req.getParameter("commercialname")==null) ? "" : req.getParameter("commercialname").trim(),
			rfc = (req.getParameter("rfc") == null) ? "" : req.getParameter("rfc").trim(),
			curp = (req.getParameter("curp") == null) ? "" : req.getParameter("curp").trim(),
			fiscaladdress1 = (req.getParameter("fiscaladdress1") == null) ? "" : req.getParameter("fiscaladdress1").trim(),
			fiscaladdress2 = (req.getParameter("fiscaladdress2") == null) ? "" : req.getParameter("fiscaladdress2").trim(),
			fiscaladdress3 = (req.getParameter("fiscaladdress3") == null) ? "" : req.getParameter("fiscaladdress3").trim(),
			fiscalcp = (req.getParameter("fiscalcp") == null) ? "" : req.getParameter("fiscalcp").trim(),
			enddate = (req.getParameter("enddate") == null) ? "" : req.getParameter("enddate").trim();
		int fiscalpersontype = (req.getParameter("fiscalpersontype") == null) ? 0 : Functions.toInt(req.getParameter("fiscalpersontype")),
			fiscalcity = (req.getParameter("fiscalcity") == null) ? 0 : Functions.toInt(req.getParameter("fiscalcity")),
			samedata=(req.getParameter("samedata") == null) ? 0 : Functions.toInt(req.getParameter("samedata"));

		Fiscalsdata entity = new Fiscalsdata();
		entity.setOrigintype(1);//1=Firmas
		entity.setOriginid(companyid);
		entity.setPersonafiscalid(fiscalpersontype);
		entity.setBusinessname(businessname);
		entity.setCommercialname(commercialname);
		entity.setRfc(rfc);
		entity.setCurp(curp);
		entity.setAddress1(fiscaladdress1);
		entity.setAddress2(fiscaladdress2);
		entity.setAddress3(fiscaladdress3);
		entity.setZipcode(fiscalcp);
		entity.setCiudadid(fiscalcity);
		entity.setSamedata(samedata);
		if(!Functions.isEmpty(enddate))
			entity.setEnddate(Functions.parseFecha(enddate, "yyyy-MM-dd"));

		List<Fiscalsdata> fd = fiscalsdataService.getAll("FROM Fiscalsdata WHERE origintype=1 AND originid=" + companyid);
		long fiscalcompid=0;
		if(fd.size()>0)
			fiscalcompid=fd.get(0).getFiscaldataid();
		if(fiscalcompid==0){
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH);
			entity.setStartdate(Functions.parseFecha(date, "yyyy-MM-dd"));
			companyid = fiscalsdataService.addNewFiscaldata(entity);
		}else{
			entity.setFiscaldataid(fiscalcompid);
			fiscalsdataService.updateFiscaldata(entity);
		}
		return (companyid > 0)?"msg_data_saved":"err_record_no_saved";
	}

	@RequestMapping(value = "/getCompanies")
	public @ResponseBody Object[] getCompanies(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			int role = userDto.getRole();

			String whereClause = "";
			if ((role!=ROLE_SYSADMIN && role!=ROLE_CJADMIN))
				whereClause = " WHERE companyid=" + userDto.getCompanyid();
			List<Companies> compList = companiesService.getAll("FROM Companies" + whereClause + " ORDER BY company ASC");
			return new Object[] { compList };
		}
		return null;
	}

	/** Obtiene el listado de abogados de la firma en acorde al usuario activo.
	@param sid	Indica si dentro del listado será incluido el usuario activo (1) o será omitido (0).	*/
	@RequestMapping(value = "/getLawyerList")
	public @ResponseBody Object[] getLawyerList(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Map<String, Object> data = new HashMap<String, Object>();
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			int role = userDto.getRole(),
				selfUserId = (req.getParameter("sid") == null) ? 0 : Functions.toInt(req.getParameter("sid"));
			String whereClause = "WHERE (usertype!=1 OR usertype IS NULL)", clauseCompany = "";
			if(selfUserId==0)
				whereClause += " AND id!=" + userDto.getId();
	  		if (role!=ROLE_SYSADMIN)
	  			if (role==ROLE_CJADMIN){
	  				whereClause += " AND role<>"+ROLE_SYSADMIN;
	  			}else{
	  				whereClause += " AND role not IN("+ROLE_SYSADMIN + "," + ROLE_CJADMIN;
	  				if(role!=ROLE_FIRMADMIN)
	  					whereClause += "," + ROLE_FIRMADMIN;
  					whereClause += ") AND companyid=" + userDto.getCompanyid();
  					clauseCompany = " WHERE companyid=" + userDto.getCompanyid();
	  			}

	  		List<Users> usersFirm = userService
	  			.getAll("FROM Users " + whereClause + " ORDER BY first_name ASC, last_name ASC");
			Map<Integer,Object> lawyerList=new HashMap<Integer,Object>();
			for(int i=0; i<usersFirm.size();i++){
				Map<String,String> m=new HashMap<String,String>();
				m.put("id",usersFirm.get(i).getId()+"");
				m.put("first_name",usersFirm.get(i).getFirst_name());
				m.put("last_name",usersFirm.get(i).getLast_name());
				m.put("companyid",usersFirm.get(i).getCompanyid()+"");
				lawyerList.put(i,m);
			}
			data.put("lawyerList", lawyerList);

			List<Companies> firmList = companiesService.getAll("FROM Companies" + clauseCompany);
			Map<Integer,Object> companyList=new HashMap<Integer,Object>();
			for(int i=0; i<firmList.size();i++){
				Map<String,String> m=new HashMap<String,String>();
				m.put("companyid",firmList.get(i).getCompanyid()+"");
				m.put("company",firmList.get(i).getCompany());
				companyList.put(i,m);
			}
			data.put("companyList",companyList);
			return new Object[] { data };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/getUserListByFirm")
	public Object[] getUserListByFirm(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		validate:{
		if (sess != null) {
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			int firmid = (req.getParameter("firmid") == null) ? 0
				: Functions.toInt(req.getParameter("firmid").trim());
	  		if (userDto.getRole()!=ROLE_SYSADMIN)	// || role==ROLE_CJADMIN
	  			break validate;

	  		List<Users> usersFirm = userService.getAll("FROM Users WHERE companyid="
  				+ firmid + " ORDER BY first_name ASC, last_name ASC");
			Map<Integer,Object> lawyerList=new HashMap<Integer,Object>();
			for(int i=0; i<usersFirm.size();i++){
				Map<String,String> m=new HashMap<String,String>();
				m.put("id",usersFirm.get(i).getId()+"");
				m.put("first_name",usersFirm.get(i).getFirst_name());
				m.put("last_name",usersFirm.get(i).getLast_name());
				m.put("companyid",usersFirm.get(i).getCompanyid()+"");
				lawyerList.put(i,m);
			}
			data.put("lawyerList", lawyerList);
		}}
		return new Object[] { data };
	}

	/** Asignación de abogados/sub-privilegios
	@param req				HttpServletRequest
	@param trialid			Id del juicio
	@param lawyerassigned	Todos los privilegios a aplicar separados por coma: "sharedUserid,externalEmail,privileges~"
	@param userid			Id del Usuario de sesión activa.
	@param originalUser		Id del Usuario original o quien dio de alta el juicio.<br>
	@param companyid		Id de la firma del usuario activo.<br>
	@param companyclientid	Cadena de caracteres con el companyclientid del cliente.<br><dl>
		<dt>El formato del parámetro "lawyerassigned" corresponde a:</dt>
		<dd>	sharedUserid =	Id del usuario interno a compartir. Si el registro tiene definido <b>"externalEmail"</b>, dejarlo en blanco.</dd>
		<dd>	externalEmail =	Email del usuario externo a compartir. Si el registro tiene definido <b>"sharedUserid"</b>, dejarlo en blanco.</dd>
		<dd>	privileges	=	Privilegios para movimientos con formato "menuid_privilegeid=truefalse"</dd>
		<dd>	~			=	Separa un usuario de otro</dd></dl>	*/
	@SuppressWarnings({ "unchecked", "unused" })
	public void setLawyerSubPrivileges(HttpServletRequest req, HttpServletResponse res,
	int trialid, String lawyerassigned, int userid, int role, int originalUser, int companyid,
	String companyclientid, String useremail){
		int shDockid=0, uAssigned = 0;

		// Separación de abogados
		String[] allUsersPriv = lawyerassigned.split("~");
		String shUsrIds = "", shUsrEmails="";
		if(!Functions.isEmpty(lawyerassigned))
			for(int p=0;p<allUsersPriv.length;p++){
				String[] rowRec = allUsersPriv[p].split(",");
				String uidtmp = rowRec[0]+"";
				if(Functions.isNumeric(uidtmp) && !uidtmp.equals("0") && !Functions.isEmpty(uidtmp))
					shUsrIds+=uidtmp+",";
				else
					shUsrEmails+="'"+rowRec[1]+"',";
			}

		// Elimina los abogados que ya no esten asignados al juicio
		shUsrIds=shUsrIds.replaceAll(",$","");
		shUsrEmails=shUsrEmails.replaceAll(",$","");
		String clauseIds=(Functions.isEmpty(shUsrIds)?"":"userid IN("+shUsrIds+")"), shClause="",
			clauseEmails=(Functions.isEmpty(shUsrEmails)?"":"emailexternaluser IN("+shUsrEmails+")");
		if(!Functions.isEmpty(clauseEmails) && !Functions.isEmpty(clauseIds))
			shClause="(" + clauseEmails + " OR " + clauseIds + ")";
		else
			shClause=Functions.isEmpty(clauseIds)?clauseEmails:clauseIds;
		int rows = sharedDocketsService.updateDeleteByQuery("DELETE FROM SharedDockets WHERE juicioid="
			+ trialid + (Functions.isEmpty(shClause)?"":" AND NOT " + shClause));

		// Integra los abogados asignados al juicio
		if(!Functions.isEmpty(lawyerassigned))
			for(int a=0;a<allUsersPriv.length;a++){
				List<SharedDockets> existsDocket = null;
				Gson gson = new Gson();
				int useridLocExt=0;
				String[] spUsrRow=allUsersPriv[a].split(",");
				String shuserid=spUsrRow[0], shemail=spUsrRow.length<=1?"":spUsrRow[1],
					whereClause="", oldjson="", newdata="";
				if(useremail.equals(shemail))
					continue;	// El mismo usuario no se puede aplicar permisos a sí mismo
	
				// Obtiene los datos del docket/juicio compartido (cabecera del docket)
				if(Functions.isEmpty(shuserid) || shuserid.equals("0")){	// Busca el userid de la firma externa
					List<Users> extFirmUsr=userService.getAll("FROM Users WHERE email='" + shemail + "'");
					whereClause=" AND emailexternaluser='" + shemail + "'";
					if(extFirmUsr.size()>0){
						if(userid==extFirmUsr.get(0).getId())
							continue;	// El mismo usuario no se puede aplicar permisos a sí mismo
						useridLocExt=Functions.toInt(extFirmUsr.get(0).getId()+"");
					}
				}else{
					whereClause=" AND userid=" + shuserid;
					useridLocExt=Functions.toInt(shuserid);
				}
	
				// Aplica datos principales (cabecera del docket)
				existsDocket=sharedDocketsService
					.getAll("FROM SharedDockets WHERE juicioid=" + trialid + whereClause);
				if(existsDocket.size()>0){
					shDockid = existsDocket.get(0).getShareddocketid();
					oldjson = gson.toJson(existsDocket);
					newdata = oldjson;
				}else{	//Inserta nuevos datos al docket
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date shDate = Functions.parseFecha(dateFormat.format(new Date()), "yyyy-MM-dd");
	
					SharedDockets dockets = new SharedDockets();
					dockets.setJuicioid(trialid);
					dockets.setUsuarioidautoriza(userid);
					dockets.setEmailexternaluser(shemail);
					dockets.setUserid(useridLocExt);
					dockets.setShareddate(shDate);
					shDockid = sharedDocketsService.addNewSharedDocket(dockets);
					newdata = gson.toJson(dockets);
				}
	
				// Asigna privilegios de movimientos
				List<Menuprivileges> beforeSet = dao.sqlHQL("SELECT menuid, privilegesid FROM Menuprivileges "
					+"WHERE shareddocketid=" + shDockid +" GROUP BY menuid, privilegesid ORDER BY menuid, privilegesid",null);
				String query = "DELETE FROM Menuprivileges WHERE shareddocketid="+shDockid,
					shToSet = spUsrRow.length<=2?"":spUsrRow[2].replaceAll("\\d*_\\d+=false\\|?","");
				String shNoDel=shToSet.replaceAll("(\\d+)_(\\d+)=true(?:\\|?\\s?)","(menuid=$1 AND privilegesid=$2) OR ");
				if(!Functions.isEmpty(shNoDel))
					query+=" AND NOT (" + shNoDel.replaceAll(" OR $","") + ")";
				rows = dao.updateSqlNative(query, null);
				String[] shData = shToSet.replaceAll("(\\d+)_(\\d+)=true(?:\\|?\\s?)","$1=$2,")
					.replaceAll(",?\\s?$","").split(",");
				if(!Functions.isEmpty(shToSet))
					for(int r=0;r<shData.length;r++){
						String[] shMnPr = shData[r].split("=");
						String shModule = shMnPr[0], shPrivilege = shMnPr[1];
						List<Menuprivileges> alreadySet = dao.sqlHQL("FROM Menuprivileges WHERE privilegesid="
							+ shPrivilege + " AND menuid=" + shModule + " AND shareddocketid=" + shDockid, null);
						if(alreadySet.size()>0)
							continue;
						Menuprivileges shAllow = new Menuprivileges();
						shAllow.setPrivilegesid(Functions.toInt(shPrivilege));
						shAllow.setMenuid(Functions.toInt(shModule));
						shAllow.setRoleid(0);	// Documentos compartidos no tienen rol definido
						shAllow.setShareddocketid(shDockid);
						dao.save(shAllow);
					}
				// Si hay diferencias enviará la notificación
				if(useridLocExt==0)	// Sí el usuario aún no existe en el sistema, no se le puede enviar notificación
					continue;
				String bSet = "", aSet = "";
				Object[] tmpMP = beforeSet.toArray();
				if(beforeSet.size()>0)
					for (Object cdata:tmpMP){
						Object[] obj = (Object[]) cdata;
						bSet+=obj[0]+"-"+obj[1]+",";
					}
				List<Menuprivileges> afterSet = dao.sqlHQL("SELECT menuid, privilegesid FROM Menuprivileges "
					+"WHERE shareddocketid=" + shDockid +" GROUP BY menuid, privilegesid ORDER BY menuid, privilegesid",null);
				tmpMP = afterSet.toArray();
				if(afterSet.size()>0)
					for (Object cdata:tmpMP){
						Object[] obj = (Object[]) cdata;
						aSet+=obj[0]+"-"+obj[1]+",";
					}
				if(aSet.equals(bSet))
					continue;	// Sin cambios, no hay notificaciones
	
				// Envía notificación
				Menu modRefId = (Menu) dao
					.sqlHQLEntity("FROM Menu WHERE menu NOT LIKE 'Compartir%' AND (link LIKE 'juicios' OR link LIKE 'juicios.jet')", null);
				List<Juicios> tmpTrialDoc = juiciosService.getAll("FROM Juicios WHERE juicioid=" + trialid);
	
//TODO:i18n para la notificación. Puede ser sustituir los ids por textos durante la carga de datos o Agregar un espacio más para las notificaciones para incluir "txtBefore" u "txtAfter" para ser procesado por i18n.js
				long nsucc = notificationsCtrll.addNotificationDB(req, res, modRefId.getMenuid(), 1,	//1=Juicios
					trialid, Functions.toInt(companyclientid), userid, 1,	//1=Edición
					useridLocExt+"", companyid, new JsonObject(), 
"Cambios en los privilegios en el juicio compartido " + tmpTrialDoc.get(0).getJuicio(), oldjson, newdata);
				if(nsucc<1)
					System.err.println("Sin cambios o no se pudo almacenar la notificación del juicioid " + trialid);
			}
List<Menuprivileges> usersShared = dao.sqlHQL("FROM Menuprivileges WHERE shareddocketid=" + shDockid, null);
		// Analiza si el juicio/docket ya no tiene usuarios compartidos, eliminará el docket
//System.out.println("Size: " + usersShared.size());
		if(usersShared.size()==0 && shDockid>0)
			sharedDocketsService.deleteSharedDocket((long) shDockid);
	}

	//***** Directorio de abogados (ini) ***********
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lawyerdirectory")
	public @ResponseBody ModelAndView lawyerdirectory(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");

				// Obtiene los privilegios del módulo
				@SuppressWarnings("rawtypes")
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "lawyerdirectory");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				String query = "";
				if ((userDto.getRole()!=ROLE_SYSADMIN&&userDto.getRole()!=ROLE_CJADMIN))
					query = "WHERE companyid=" + userDto.getCompanyid();
				List<LawyerDirectory> data = lawyerdirectoryService.getAll("FROM LawyerDirectory "
					+ query + " ORDER BY company_name ASC, first_name ASC, last_name ASC, lawyerid ASC");
				forModel.put("data", data);
				forModel.put("coid", userDto.getRole());
				return new ModelAndView("lawyerdirectory", forModel);
			}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/saveNewLawyerDir")
	public @ResponseBody void saveNewLawyerDir(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		validateData:{
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int companyid = (req.getParameter("coid") == null) ? 0 : Functions.toInt(req.getParameter("coid").trim()),
					paisid = (req.getParameter("paisid") == null) ? 0 : Functions.toInt(req.getParameter("paisid").trim()),
					edoid = (req.getParameter("edoid") == null) ? 0 : Functions.toInt(req.getParameter("edoid").trim()),
					cdid = (req.getParameter("city") == null) ? 0 : Functions.toInt(req.getParameter("city").trim());
				String jobpos = (req.getParameter("jobpos") == null) ? "" : req.getParameter("jobpos").trim(),
					matspec = (req.getParameter("matspec") == null) ? "" : req.getParameter("matspec").trim(),
					fname = (req.getParameter("fname") == null) ? "" : req.getParameter("fname").trim(),
					lname = (req.getParameter("lname") == null) ? "" : req.getParameter("lname").trim(),
					firmname = (req.getParameter("firmname") == null) ? "" : req.getParameter("firmname").trim(),
					address1 = (req.getParameter("address1") == null) ? "" : req.getParameter("address1").trim(),
					address2 = (req.getParameter("address2") == null) ? "" : req.getParameter("address2").trim(),
					address3 = (req.getParameter("address3") == null) ? "" : req.getParameter("address3").trim(),
					zipcode = (req.getParameter("zipcode") == null) ? "" : req.getParameter("zipcode").trim(),
					notes = (req.getParameter("notes") == null) ? "" : req.getParameter("notes").trim(),
					contactinfo = (req.getParameter("contactinfo") == null) ? "" : req.getParameter("contactinfo").trim();
				if(Functions.isEmpty(fname)){
					resp = "err_enter_msg_name";
				}else if(Functions.isEmpty(address1)){
					resp = "err_enter_address";
				}else{
					if(userDto.getRole()!=ROLE_SYSADMIN || userDto.getRole()!=ROLE_CJADMIN)
						companyid=userDto.getCompanyid();
					List<LawyerDirectory> existsLawyer = lawyerdirectoryService
						.getAll("FROM LawyerDirectory WHERE companyid=" + companyid
						+ " AND UPPER(company_name)='" + firmname.toUpperCase() + "'"
						+ " AND UPPER(first_name)='" + fname.toUpperCase()
						+ "' AND UPPER(last_name)='" + lname.toUpperCase() + "'");
					if(existsLawyer.size()>=1){
						resp="err_duplicated_data";
						break validateData;
					}
					Date now = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(now);
					String cdate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
						+ calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR)  + ":"
						+ calendar.get(Calendar.MINUTE);
					LawyerDirectory entity = new LawyerDirectory();
					entity.setCompanyid(companyid);
					entity.setCompany_name(firmname);
					entity.setFirst_name(fname);
					entity.setLast_name(lname);
					entity.setJobposition(jobpos);
					entity.setSpeciality(matspec);
					entity.setAddress1(address1);
					entity.setAddress2(address2);
					entity.setAddress3(address3);
					entity.setZipcode(zipcode);
					if(paisid>0)
						entity.setPaisid(paisid);
					if(edoid>0)
						entity.setEstadoid(edoid);
					if(cdid>0)
						entity.setCiudadid(cdid);
					entity.setStatus(1);	//Default: 1=Activo
					entity.setNotes(notes);
					entity.setCreated(Functions.parseFecha(cdate, "yyyy-MM-dd HH:mm"));
					
					long newlawyerid = lawyerdirectoryService.addNewLawyerDir(entity);
					if(newlawyerid==0){
						resp="err_record_no_saved";
						break validateData;
					}
					resp="msg_data_saved";

					// Guarda imagen
					String[] nameFiles = null;
					@SuppressWarnings("rawtypes")
					Enumeration enumeration = req.getParameterNames();
					while (enumeration.hasMoreElements()) {
						String parameterName = (String) enumeration.nextElement();
						if (parameterName.contains("fileuploadx_") && !Functions.isEmpty(req.getParameter(parameterName))) {
							if (nameFiles == null)
								nameFiles = new String[1];
							else
								nameFiles = (String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
							nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
						}
						break; //Sólo toma el primer archivo
					}

					// Mueve imagen
					String rootPath = Functions.getRootPath();
					String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
					String destinationPath = Functions.addPath(rootPath,
						"doctos" + FileSystems.getDefault().getSeparator() + "images/lawyerdir", true);
					destinationPath = Functions.addPath(destinationPath, "", true);
					File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
					if (paths != null) {
						for (File file : paths) {
							String[] filename = (file.getName()).split("\\.");
							String extFile = filename[filename.length - 1];
							File f = new File(file.getAbsolutePath());// Renombra el archivo
							f.renameTo(new File(destinationPath + "/" + newlawyerid + "." + extFile));
//								String path = f.getAbsolutePath();
//								String onlyfile = f.getName();
							break; //Sólo toma el primer archivo
						}
					}
					try {
						FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					// Agrega los medios de contacto.
					addUpdateLawyerAddressBook((int) newlawyerid, contactinfo, companyid);
				}   
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in saveNewCommTypes(): " + ex.getMessage());
		}
	}

	/** Agrega o actualiza los medios de contacto para el abogado contraparte
	@param lawyerid		Id del abogado contraparte.
	@param contactinfo	Formato JSON en cadena de texto con los medios contacto.
	@param companyid	Id de la firma.	*/
	@SuppressWarnings("unchecked")
	public void addUpdateLawyerAddressBook(int lawyerid, String contactinfo, int companyid){
		Map<String,Object> commMap = null;
	    try{
	    	commMap = new ObjectMapper().readValue(contactinfo, HashMap.class);
	    }catch(JsonParseException e){e.printStackTrace();
	    }catch(JsonMappingException e){e.printStackTrace();
	    }catch(IOException e){e.printStackTrace();}
	    if(Functions.isEmpty(contactinfo))return;
		for (Entry<String, Object> entry : commMap.entrySet()){
		    ObjectMapper mapper = new ObjectMapper();
		    JsonNode node = null;
			try{
				node = mapper.readTree(contactinfo).path(entry.getKey());
			}catch(IOException e){e.printStackTrace();}
			Integer commid = node.path("commtype").asInt(),
				commtypeid = node.path("commtype").asInt();
			String cinfo = node.path("contactinfo").asText();

			List<LawyerAddressBook> existsAdrs = null;
			if(Functions.isEmpty(commid))
				existsAdrs = lawyeraddressService.getAll("FROM LawyerAddressBook WHERE abogadocontraparte="
					+ lawyerid + " AND UPPER(contactinfo)='" + cinfo.toUpperCase()
					+ "' AND commtypeid=" + commtypeid);
			else
				existsAdrs = lawyeraddressService.getAll("FROM LawyerAddressBook WHERE id=" + commid 
					+ "' AND commtypeid=" + commtypeid);
			LawyerAddressBook lab = new LawyerAddressBook();
			lab.setCompanyid((long) companyid);
	    	lab.setAbogadocontraparte((int) lawyerid);
	    	lab.setCommtypeid((long) commtypeid);
	    	lab.setCommlabelid((long) node.path("commlabel").asInt());
	    	lab.setContactinfo(cinfo);
	    	lab.setAdditionalinfo(node.path("addtionalinfo").asText());
	    	if(existsAdrs.size()>0){
	    		lab.setId(existsAdrs.get(0).getId());
	    		lawyeraddressService.updateLawyerAddress(lab);
	    	}else{
	    		lawyeraddressService.addNewLawyerAddress(lab);
	    	}
	    }
	}
	
	@RequestMapping(value = "/getLawyerDirById")
	public @ResponseBody Map<String, Object> getLawyerDirById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if (sess != null) {
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			int companyid = (req.getParameter("coid") == null) ? 0 : Functions.toInt(req.getParameter("coid").trim());
			if(userDto.getRole()!=ROLE_SYSADMIN || userDto.getRole()!=ROLE_CJADMIN)
				companyid=userDto.getCompanyid();
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<LawyerDirectory> lawyerInfo = lawyerdirectoryService
				.getAll("FROM LawyerDirectory WHERE lawyerid=" + id + " AND companyid=" + companyid);
			List<LawyerAddressBook> addressBook = lawyeraddressService
				.getAll("FROM LawyerAddressBook WHERE abogadocontraparte=" + id + " AND companyid=" + companyid);
			List<CommunicationTypes> commtype = commtypeService
				.getAll("FROM CommunicationTypes ORDER BY commtypeid ASC");
			List<CommunicationLabels> commlabel = commlabelsService
				.getAll("FROM CommunicationLabels ORDER BY commlabelid ASC");
			List<Socialnetworks> snwk = socialnetworkService
					.getAll("FROM Socialnetworkclient ORDER BY snid ASC");
			data.put("lawyerInfo", lawyerInfo);
			data.put("addressBook", addressBook);
			data.put("commtype", commtype);
			data.put("commlabel", commlabel);
			data.put("snwk", snwk);
		}
		return data;
	}

	@RequestMapping(value = "/updateLawyerDir")
	public @ResponseBody void updateLawyerDir(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		validateData:{
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int lawyerid = (req.getParameter("lawyerid") == null) ? 0 : Functions.toInt(req.getParameter("lawyerid").trim()),
					status = (req.getParameter("status") == null) ? 0 : Functions.toInt(req.getParameter("status").trim()),
					companyid = (req.getParameter("coid") == null) ? 0 : Functions.toInt(req.getParameter("coid").trim()),
					paisid = (req.getParameter("paisid") == null) ? 0 : Functions.toInt(req.getParameter("paisid").trim()),
					edoid = (req.getParameter("edoid") == null) ? 0 : Functions.toInt(req.getParameter("edoid").trim()),
					cdid = (req.getParameter("city") == null) ? 0 : Functions.toInt(req.getParameter("city").trim());
				String jobpos = (req.getParameter("jobpos") == null) ? "" : req.getParameter("jobpos").trim(),
					matspec = (req.getParameter("matspec") == null) ? "" : req.getParameter("matspec").trim(),
					fname = (req.getParameter("fname") == null) ? "" : req.getParameter("fname").trim(),
					lname = (req.getParameter("lname") == null) ? "" : req.getParameter("lname").trim(),
					firmname = (req.getParameter("firmname") == null) ? "" : req.getParameter("firmname").trim(),
					address1 = (req.getParameter("address1") == null) ? "" : req.getParameter("address1").trim(),
					address2 = (req.getParameter("address2") == null) ? "" : req.getParameter("address2").trim(),
					address3 = (req.getParameter("address3") == null) ? "" : req.getParameter("address3").trim(),
					zipcode = (req.getParameter("zipcode") == null) ? "" : req.getParameter("zipcode").trim(),
					notes = (req.getParameter("notes") == null) ? "" : req.getParameter("notes").trim(),
					contactinfo = (req.getParameter("contactinfo") == null) ? "" : req.getParameter("contactinfo").trim();
				if(Functions.isEmpty(fname)){
					resp = "err_enter_msg_name";
				}else if(Functions.isEmpty(address1)){
					resp = "err_enter_address";
				}else{
					if(userDto.getRole()!=ROLE_SYSADMIN || userDto.getRole()!=ROLE_CJADMIN)
						companyid=userDto.getCompanyid();
					List<LawyerDirectory> existsLawyer = lawyerdirectoryService
						.getAll("FROM LawyerDirectory WHERE companyid=" + companyid
						+ " AND UPPER(company_name)='" + firmname.toUpperCase() + "'"
						+ " AND UPPER(first_name)='" + fname.toUpperCase()
						+ "' AND UPPER(last_name)='" + lname.toUpperCase() + "'");
					if(existsLawyer.size()>=1){
						resp="err_duplicated_data";
						break validateData;
					}
					Date now = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(now);
					String cdate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
						+ calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR)  + ":"
						+ calendar.get(Calendar.MINUTE);
					LawyerDirectory entity = new LawyerDirectory();
					entity.setLawyerid((long) lawyerid);
					entity.setCompanyid(companyid);
					entity.setCompany_name(firmname);
					entity.setFirst_name(fname);
					entity.setLast_name(lname);
					entity.setJobposition(jobpos);
					entity.setSpeciality(matspec);
					entity.setAddress1(address1);
					entity.setAddress2(address2);
					entity.setAddress3(address3);
					entity.setZipcode(zipcode);
					if(paisid>0)
						entity.setPaisid(paisid);
					if(edoid>0)
						entity.setEstadoid(edoid);
					if(cdid>0)
						entity.setCiudadid(cdid);
					entity.setStatus(status);
					entity.setNotes(notes);
					entity.setCreated(Functions.parseFecha(cdate, "yyyy-MM-dd HH:mm"));
					lawyerdirectoryService.updateLawyerDir(entity);
					resp="msg_data_saved";

					// Guarda imagen
					String[] nameFiles = null;
					@SuppressWarnings("rawtypes")
					Enumeration enumeration = req.getParameterNames();
					while (enumeration.hasMoreElements()) {
						String parameterName = (String) enumeration.nextElement();
						if (parameterName.contains("fileuploadx_") && !Functions.isEmpty(req.getParameter(parameterName))) {
							if (nameFiles == null)
								nameFiles = new String[1];
							else
								nameFiles = (String[]) Functions.resizeArray(nameFiles, nameFiles.length + 1);
							nameFiles[nameFiles.length - 1] = Functions.toStr(req.getParameter(parameterName));
						}
						break; //Sólo toma el primer archivo
					}

					// Mueve imagen
					String rootPath = Functions.getRootPath();
					String tmpPath = Functions.addPath(rootPath, Functions.getCookieJsesion(req), true);
					String destinationPath = Functions.addPath(rootPath,
						"doctos" + FileSystems.getDefault().getSeparator() + "images/lawyerdir", true);
					destinationPath = Functions.addPath(destinationPath, "", true);
					File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
					if (paths != null) {
						for (File file : paths) {
							String[] filename = (file.getName()).split("\\.");
							String extFile = filename[filename.length - 1];
							File f = new File(file.getAbsolutePath());// Renombra el archivo
							f.renameTo(new File(destinationPath + "/" + lawyerid + "." + extFile));
//								String path = f.getAbsolutePath();
//								String onlyfile = f.getName();
							break; //Sólo toma el primer archivo
						}
					}
					try {
						FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					// Agrega los medios de contacto.
					addUpdateLawyerAddressBook((int) lawyerid, contactinfo, companyid);
				}   
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateLawyerDir(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteLawyerDir")
	public void deleteLawyerDir(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id"));
					lawyerdirectoryService.deleteLawyerDir(id);
				}
			}
		}
	}
//***** Directorio de abogados (fin) ***********

/*
		@ResponseBody
		@RequestMapping(value = "/addsncompanyclient")
		public void addsncompanyclient(HttpServletRequest req, HttpServletResponse res) {
			HttpSession sess = req.getSession(false);
			String resp = "err_record_no_saved";
			if (sess != null) {
				if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
					String sntext = (req.getParameter("sntext") == null) ? "" : req.getParameter("sntext").trim();
					int snid = (req.getParameter("snid") == null) ? 0 : Functions.toInt(req.getParameter("snid"));
					if (snid==0 || Functions.isEmpty(sntext)) {
						try {
							res.getWriter().write("msg_empty_data");
						} catch (IOException e) {
							e.printStackTrace();
						}
						return;
					}
					UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
					int userid = (int) userDto.getId();
					Users user = new Users();
					user = userService.getUserById(userid);
					int companyid = user.getCompanyid();

					List<Companyclients> existCC = companyclientService
							.getAll("FROM Companyclients WHERE companyid=" + companyid + " AND clientid=" + clientid);
					long ccid = 0;

					if (existCC.size() == 0) {
						Companyclients cclient = new Companyclients();
						cclient.setCompanyid(companyid);
						cclient.setClientid(clientid);
						ccid = companyclientService.addNewCClient(cclient);
					} else {
						ccid = existCC.get(0).getCompanyclientid();
					}
					Socialnetworkclient snwcc= new Socialnetworkclient();
					snwcc.setSocialnetworkid(snid);
					snwcc.setCompanyclientid(sncclient);
					int succ = socialnetworkclientService.addNewSNCWork(snwcc);
					if (succ > 0)
						resp = "msg_data_saved";
				}
			}
			try {
				res.getWriter().write(resp);
			} catch (IOException ex) {
				System.out.println("Exception in addNewSocialnetwork(): " + ex.getMessage());
			}
		}
	/*
		@ResponseBody
		@RequestMapping(value = "/deletesn")
		public void deletesn(HttpServletRequest req, HttpServletResponse res) {
			HttpSession sess = req.getSession(false);
			String resp = "err_record_no_saved";
			if (sess != null) {
				if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
					String sntext = (req.getParameter("sntext") == null) ? "" : req.getParameter("sntext").trim();
					int snid = (req.getParameter("snid") == null) ? 0 : Functions.toInt(req.getParameter("snid"));
					if (snid==0 || Functions.isEmpty(sntext)) {
						try {
							res.getWriter().write("msg_empty_data");
						} catch (IOException e) {
							e.printStackTrace();
						}
						return;
					}
					UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
					int userid = (int) userDto.getId();
					Users user = new Users();
					user = userService.getUserById(userid);
					int companyid = user.getCompanyid();

					List<Companyclients> existCC = companyclientService
							.getAll("FROM Companyclients WHERE companyid=" + companyid + " AND clientid=" + clientid);
					long ccid = 0;
					
					if (existCC.size() == 0) {
						Companyclients cclient = new Companyclients();
						cclient.setCompanyid(companyid);
						cclient.setClientid(clientid);
						ccid = companyclientService.addNewCClient(cclient);
					} else {
						ccid = existCC.get(0).getCompanyclientid();
					}
					Socialnetworkclient snwcc= new Socialnetworkclient();
					snwcc.setSocialnetworkid(snid);
					snwcc.setCompanyclientid(sncclient);
					int succ = socialnetworkclientService.addNewSNCWork(snwcc);
					if (succ > 0)
						resp = "msg_data_saved";
				}
			}
			try {
				res.getWriter().write(resp);
			} catch (IOException ex) {
				System.out.println("Exception in addNewSocialnetwork(): " + ex.getMessage());
			}
		}
	*/

	//***** Formas de contacto (ini) ***********
	/*	@RequestMapping(value = "/lawyeraddressbook")
		public @ResponseBody ModelAndView lawyeraddressbook(HttpServletRequest req, HttpServletResponse res) {
			HttpSession sess = req.getSession(false);
			if (sess != null)
				if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
					res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
					res.setHeader("Pragma", "no-cache");// HTTP 1.0.
					res.setDateHeader("Expires", 0);
					res.setContentType("text/html; charset=utf-8");
					res.setCharacterEncoding("utf-8");

					// Obtiene los privilegios del módulo
					HashMap<Object, Object> parameters = new HashMap();
					parameters.put("urlMethod", "LawyerAddressBook");
					Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
					Map<String, Object> forModel = new HashMap<String, Object>();
					forModel.put("menu", menu);

					List<LawyerAddressBook> cways = lawyeraddressService.getAll("FROM LawyerAddressBook ORDER BY contactwayid");
					forModel.put("cways", cways);
					return new ModelAndView("lawyeraddressbook", forModel);
				}
			return new ModelAndView("login");
		}

		@RequestMapping(value = "/saveNewContactWay")
		public @ResponseBody void saveNewContactWay(HttpServletRequest req, HttpServletResponse res) {
			HttpSession sess = req.getSession(false);
			String resp = "err_record_no_saved";
			if (sess != null)
				if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
					UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
					if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
						String label = (req.getParameter("label") == null) ? "" : req.getParameter("label").trim(),
							contactinfo = (req.getParameter("contactinfo") == null) ? "" : req.getParameter("contactinfo").trim(),
							additionalinfo = (req.getParameter("additionalinfo") == null) ? "" : req.getParameter("additionalinfo").trim();
						int collaborateid = (req.getParameter("collaborateid") == null) ? 0 : Functions.toInt(req.getParameter("collaborateid").trim()),
							commtypeid = (req.getParameter("commtypeid") == null) ? 0 : Functions.toInt(req.getParameter("commtypeid").trim());

						if (Functions.isEmpty(collaborateid)) {
							resp = "err_record_no_saved";
						}else if (Functions.isEmpty(collaborateid)) {
							resp = "msg_empty_data";
						}else{
							List<LawyerAddressBook> existsWay = lawyeraddressService.getAll("FROM LawyerAddressBook WHERE collaborateid="
								+ collaborateid + " AND UPPER(contactinfo)='" + contactinfo.toUpperCase() + "'");
							if(existsWay.size()<1){
								LawyerAddressBook way = new LawyerAddressBook();
								way.setAbogadocontraparte(collaborateid);
								way.setCommtypeid(commtypeid);
								way.setCommlabelid(label);
								way.setContactinfo(contactinfo);
								way.setAdditionalinfo(additionalinfo);
								long succ = lawyeraddressService.addNewLawyerAddress(way);
								resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
							}else{
								resp="err_duplicated_data";
							}
						}
					}
				}
			try {
				res.getWriter().write(resp);
			} catch (IOException ex) {
				System.out.println("Exception in saveNewContactWay(): " + ex.getMessage());
			}
		}

		@RequestMapping(value = "/getContactWayById")
		public @ResponseBody Object[] getContactWayById(HttpServletRequest req, HttpServletResponse res) {
			HttpSession sess = req.getSession(false);
			if (sess != null) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				List<LawyerAddressBook> info = lawyeraddressService.getAll("FROM LawyerAddressBook WHERE contactwayid=" + id);
				return new Object[] { info };
			}
			return null;
		}

		@RequestMapping(value = "/updateContactWay")
		public @ResponseBody void updateContactWay(HttpServletRequest req, HttpServletResponse res) {
			HttpSession sess = req.getSession(false);
			String resp = "false";
			if (sess != null)
				if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
					UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
					if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
						Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id"));
						int collaborateid = (req.getParameter("collaborateid") == null) ? 0 : Functions.toInt(req.getParameter("collaborateid").trim()),
							commtypeid = (req.getParameter("commtypeid") == null) ? 0 : Functions.toInt(req.getParameter("commtypeid").trim());
						String label = (req.getParameter("label") == null) ? "" : req.getParameter("label").trim(),
							contactinfo = (req.getParameter("contactinfo") == null) ? "" : req.getParameter("contactinfo").trim(),
							additionalinfo = (req.getParameter("additionalinfo") == null) ? "" : req.getParameter("additionalinfo").trim();

						if (Functions.isEmpty(collaborateid)) {
							resp = "err_record_no_saved";
						}else if (Functions.isEmpty(collaborateid)) {
							resp = "msg_empty_data";
						}else{
							List<LawyerAddressBook> existsWay = lawyeraddressService.getAll("FROM LawyerAddressBook WHERE collaborateid="
								+ collaborateid + " AND UPPER(contactinfo)='" + contactinfo.toUpperCase() + "'");
							if(existsWay.size()<2){
								if(existsWay.size()==1 && existsWay.get(0).getId() != id){
									resp="err_duplicated_data";
								}else{
									LawyerAddressBook way = new LawyerAddressBook();
									way.setId((long) id);
									way.setAbogadocontraparte(collaborateid);
									way.setCommtypeid(commtypeid);
									way.setCommlabelid(label);
									way.setContactinfo(contactinfo);
									way.setAdditionalinfo(additionalinfo);
									lawyeraddressService.updateLawyerAddress(way);
									resp = "msg_data_saved";
								}
							}else{
								resp="err_duplicated_data";
							}
						}
					}
				}
			try {
				res.getWriter().write(resp);
			} catch (IOException ex) {
				System.out.println("Exception in updateContactWay(): " + ex.getMessage());
			}
		}

		@RequestMapping(value = "/deleteContactWay")
		public void deleteContactWay(HttpServletRequest req, HttpServletResponse res) {
			HttpSession sess = req.getSession(false);
			if (sess != null) {
				if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
					UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
					if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
						Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id"));
						lawyeraddressService.deleteLawyerAddress(id);
					}
				}
			}
		}*/
	// ***** Formas de contacto (fin) ***********
}