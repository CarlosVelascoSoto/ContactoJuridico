package com.aj.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.aj.model.Circuitos;
import com.aj.model.Ciudades;
import com.aj.model.CommunicationLabels;
import com.aj.model.CommunicationTypes;
import com.aj.model.CustomColumns;
import com.aj.model.Estados;
import com.aj.model.Jueces;
import com.aj.model.JurisdictionalOrgans;
import com.aj.model.Juzgados;
import com.aj.model.Materias;
import com.aj.model.Menu;
import com.aj.model.Paises;
import com.aj.model.Regiones;
import com.aj.model.RelatedColumns;
import com.aj.model.Salas;
import com.aj.model.Socialnetworks;
import com.aj.model.TipoActuacion;
import com.aj.model.TipoJuicios;
import com.aj.model.TipoJuiciosAccion;
import com.aj.model.TipoOrganos;
import com.aj.model.TribunalColegiado;
import com.aj.model.TribunalUnitario;
import com.aj.model.Vias;
import com.aj.service.AccessDbJAService;
import com.aj.service.CircuitosService;
import com.aj.service.CiudadesService;
import com.aj.service.CommLabelsService;
import com.aj.service.CommunicationTypesService;
import com.aj.service.CompaniesService;
import com.aj.service.CompanyclientsService;
import com.aj.service.CustomColumnsService;
import com.aj.service.CustomColumnsValuesService;
import com.aj.service.EstadosService;
import com.aj.service.FiscalsdataService;
import com.aj.service.JuecesService;
import com.aj.service.JuiciosService;
import com.aj.service.JurisdictionalOrgansService;
import com.aj.service.JuzgadosService;
import com.aj.service.MateriasService;
import com.aj.service.PaisesService;
import com.aj.service.PrivilegesService;
import com.aj.service.RegionesService;
import com.aj.service.RelatedColumnsService;
import com.aj.service.SalasService;
import com.aj.service.SocialnetworkService;
import com.aj.service.TipoActuacionService;
import com.aj.service.TipoJuiciosAccionService;
import com.aj.service.TipoJuiciosService;
import com.aj.service.TipoOrganosService;
import com.aj.service.TribunalColegiadoService;
import com.aj.service.TribunalUnitarioService;
import com.aj.service.UserService;
import com.aj.service.ViasService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;

@Controller
public class CatalogsController {
	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	public UserService userService;

	@Autowired
	public PrivilegesService privilegesService;

	@Autowired
	public CiudadesService ciudadesService;

	@Autowired
	public CommLabelsService commlabelsService;

	@Autowired
	public CompaniesService companiesService;

	@Autowired
	public CompanyclientsService companyclientService;

	@Autowired
	public PaisesService paisesService;

	@Autowired
	public SocialnetworkService socialnetworkService;

	@Autowired
	public JuzgadosService juzgadosService;

	@Autowired
	public MateriasService materiasService;

	@Autowired
	public SalasService salasService;

	@Autowired
	public TribunalColegiadoService tribunalCService;

	@Autowired
	public TribunalUnitarioService tribunalUnitService;

	@Autowired
	public JuiciosService juiciosService;

	@Autowired
	public EstadosService estadosService;

	@Autowired
	public CircuitosService circuitosService;

	@Autowired
	public JurisdictionalOrgansService organosService;

	@Autowired
	public RegionesService regionesService;

	@Autowired
	public TipoActuacionService tipoactuacionService;

	@Autowired
	public TipoOrganosService tipoorganosService;

	@Autowired
	public TipoJuiciosService tipojuiciosService;

	@Autowired
	public TipoJuiciosAccionService tipojuiciosaccionService;

	@Autowired
	public ViasService viasService;

	@Autowired
	public CustomColumnsService customcolumnsService;

	@Autowired
	public CustomColumnsValuesService customcolumnsvaluesService;

	@Autowired
	public JuecesService juecesService;

	@Autowired
	public FiscalsdataService fiscalsdataService;

	@Autowired
	public RelatedColumnsService relatedcolumnsService;

	@Autowired
	public CommunicationTypesService commtypeService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	public AccessDbJAService dao;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static final int ROLE_SYSADMIN= 1, ROLE_CJADMIN = 2, ROLE_FIRMADMIN=3;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/countries")
	public ModelAndView countries(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess == null)return new ModelAndView("login");
		if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
			res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP_1.1.
			res.setHeader("Pragma", "no-cache");// HTTP1.0.
			res.setDateHeader("Expires", 0);
			res.setContentType("text/html; charset=utf-8");
			res.setCharacterEncoding("utf-8");

			// Obtiene los privilegios del módulo
			String urlMethod="paises";
			Map<String, Object> forModel = new HashMap<String, Object>();
			@SuppressWarnings({ "rawtypes" })
			HashMap<Object, Object> parameters = new HashMap();
			parameters.put("urlMethod", urlMethod);
			//Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE SUBSTRING(link, 0, LOCATE('.', link))=:urlMethod", parameters);
			Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
			forModel.put("menu", menu);

			List<Paises> countries = paisesService.getAll("FROM Paises ORDER BY pais");
			forModel.put("countries",countries);
			return new ModelAndView("countries", forModel);
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/getCountries")
	public @ResponseBody Object[] getCountries(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			List<Paises> countriesList = paisesService.getAll("FROM Paises ORDER BY paisid ASC");
			return new Object[] { countriesList };
		}
		return null;
	}

	@RequestMapping(value = "/addNewCountry")
	@ResponseBody
	public void addNewCountry(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String countryname = (req.getParameter("pais") == null) ? "" : req.getParameter("pais").trim();
				if (Functions.isEmpty(countryname)) {
					resp  = "msg_empty_data";
				}else{
					List<Paises> existsCountry = paisesService.getAll("FROM Paises WHERE UPPER(pais)='"+countryname.toUpperCase()+"'");
					if(existsCountry.size()<1){
						Paises country = new Paises();
						country.setpais(countryname);
						long succ = paisesService.addNewPais(country);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewCountry(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getCountryById")
	public Object[] getCountryById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<Paises> info = paisesService.getAll("FROM Paises WHERE paisid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/updateCountry")
	public void updateCountry(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("paisid") == null) ? 0 : Functions.toLong(req.getParameter("paisid"));
				String countryname = (req.getParameter("pais") == null) ? "" : req.getParameter("pais").trim();
				if (Functions.isEmpty(countryname)) {
					resp="msg_empty_data";
				}else{
					List<Paises> existsCountry = paisesService.getAll("FROM Paises WHERE UPPER(pais)='"+countryname.toUpperCase()+"'");
					if(existsCountry.size()<2){
						if(existsCountry.size()==1 && existsCountry.get(0).getPaisid() != id){
							resp="err_duplicated_data";
						}else{
							Paises country = new Paises();
							country.setPaisid(id);
							country.setpais(countryname);
							paisesService.updatePais(country);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateCountry(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteCountry")
	public void deleteCountry(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				paisesService.deletePais(id);
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getCountryStateByCityId")
	public Object[] getCountryStateByCityId(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Map<String, Object> data = new HashMap<String, Object>();
			Long cityid = req.getParameter("cityid") == null ? 0 : Functions.toLong(req.getParameter("cityid"));
			List<Ciudades> city = ciudadesService.getAll("FROM Ciudades WHERE ciudadid=" + cityid);
			List<Estados> state = estadosService.getAll("FROM Estados WHERE estadoid=" + city.get(0).getEstadoid());
			List<Paises> country = paisesService.getAll("FROM Paises WHERE paisid=" + state.get(0).getPaisid());
			data.put("city",city);
			data.put("state",state);
			data.put("country",country);
			return new Object[] { data };
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/states")
	public ModelAndView states(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess == null)return new ModelAndView("login");
		if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
			res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP_1.1.
			res.setHeader("Pragma", "no-cache");// HTTP1.0.
			res.setDateHeader("Expires", 0);
			res.setContentType("text/html; charset=utf-8");
			res.setCharacterEncoding("utf-8");

			// Obtiene los privilegios del módulo
			String urlMethod="states";
			Map<String, Object> forModel = new HashMap<String, Object>();
			@SuppressWarnings("rawtypes")
			HashMap<Object, Object> parameters = new HashMap();
			parameters.put("urlMethod", urlMethod);
			//Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE SUBSTRING(link, 0, LOCATE('.', link))=:urlMethod", parameters);
			Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
			forModel.put("menu", menu);

			List<Estados> states = estadosService.getAll("FROM Estados ORDER BY estado");
			forModel.put("states",states);
			return new ModelAndView("states", forModel);
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/addNewState")
	@ResponseBody
	public void addNewState(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String statename = (req.getParameter("estado") == null) ? "" : req.getParameter("estado").trim();
				Long countryid = (req.getParameter("paisid") == null) ? 0 : Functions.toLong(req.getParameter("paisid")),
					companyid = (req.getParameter("companyid") == null) ? 0 : Functions.toLong(req.getParameter("companyid"));
				if (Functions.isEmpty(statename)) {
					resp  = "msg_empty_data";
				}else if(countryid==0){
					resp  = "err_select_country";
				}else{
					List<Estados> existsState = estadosService.getAll("FROM Estados WHERE UPPER(estado)='"+statename.toUpperCase()+"'");
					if(existsState.size()<1){
						UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
						Estados states = new Estados();
						states.setEstado(statename);
						states.setPaisid(countryid);
						states.setCompanyid((long) (companyid==0?userDto.getCompanyid():companyid));
						long succ = estadosService.addNewEstado(states);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewState(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/getStates")
	public @ResponseBody Object[] getStates(HttpServletRequest req, HttpServletResponse res) {
		List<Estados> list = estadosService.getAll("FROM Estados ORDER BY estado ASC");
		return new Object[] { list };
	}

	@ResponseBody
	@RequestMapping(value = "/getStateById")
	public Object[] getStateById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			Map<String, Object> data = new HashMap<String, Object>();

			List<Estados> state = estadosService.getAll("FROM Estados WHERE estadoid=" + id);
			data.put("state", state);
			return new Object[] { data };
		}
		return null;
	}

	@RequestMapping(value = "/getStatesByCountry")
	public @ResponseBody Object[] getStatesByCountry(HttpServletRequest req, HttpServletResponse res) {
		int countryid = req.getParameter("countryid") == null ? 0 : Functions.toInt(req.getParameter("countryid"));
		String whereClause = countryid>0?" WHERE paisid=" + countryid:"";
		List<Estados> list = estadosService.getAll("FROM Estados" + whereClause + " ORDER BY estado ASC");
		return new Object[] { list };
	}

	@ResponseBody
	@RequestMapping(value = "/updateState")
	public void updateState(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id")),
					countryid = (req.getParameter("country") == null) ? 0 : Functions.toLong(req.getParameter("country").trim());
				String statename = (req.getParameter("state") == null) ? "" : req.getParameter("state").trim();
				if (Functions.isEmpty(statename)) {
					resp="msg_empty_data";
				}else{
					List<Estados> existsState = estadosService.getAll("FROM Estados WHERE UPPER(estado)='"+statename.toUpperCase()+"'");
					if(existsState.size()<2){
						if(existsState.size()==1 && existsState.get(0).getEstadoid() != id){
							resp="err_duplicated_data";
						}else{
							UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
							Estados state = new Estados();
							state.setEstadoid(id);
							state.setEstado(statename);
							state.setPaisid(countryid);
							state.setCompanyid((long) userDto.getCompanyid());
							estadosService.updateEstado(state);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateState(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteState")
	public void deleteState(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				estadosService.deleteEstado(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/socialnetwork")
	public ModelAndView socialnetwork(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "socialnetwork");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);
	
				List<Socialnetworks> snw = socialnetworkService.getAll("FROM Socialnetworks ORDER BY socialnetworkid ASC");
				forModel.put("snw", snw);
	
				return new ModelAndView("socialnetwork", forModel);
			}
		return new ModelAndView("login");
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/addNewSocialnetwork")
	public void addNewSocialnetwork(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String logosn = "", snw = (req.getParameter("snw") == null) ? "" : req.getParameter("snw").trim(),
					mainurl = (req.getParameter("mainurl") == null) ? "" : req.getParameter("mainurl").trim();
				if (Functions.isEmpty(snw)) {
					try {
						res.getWriter().write("msg_empty_data");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				List<Socialnetworks> existsSNW = socialnetworkService
						.getAll("FROM Socialnetworks WHERE socialnetwork ilike'%" + snw + "%'");
				if (existsSNW.size() > 0) {
					resp = "err_duplicated_socialnetwork";
				} else {
					// Guardamos archivos relacionados con el registro
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
							"doctos" + FileSystems.getDefault().getSeparator() + "images/socialnetworks", true);
					destinationPath = Functions.addPath(destinationPath, "", true);
					File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);
					
					Socialnetworks socnetwork= new Socialnetworks();
					socnetwork.setSocialnetwork(snw);
					socnetwork.setMainurl(mainurl);
					socnetwork.setImageurl("");
					long succ = socialnetworkService.addNewSocNetWork(socnetwork);
					if (succ > 0){
						resp = "msg_data_saved";
						if (paths != null) {
							for (File file : paths) {
								String[] filename = (file.getName()).split("\\.");
								String extFile = filename[filename.length - 1];
								File f = new File(file.getAbsolutePath());// Renombra el archivo por el id
								f.renameTo(new File(destinationPath + "/" + succ + "." + extFile));
								String path = f.getAbsolutePath();
								String onlyfile = f.getName();
								logosn = path.replaceAll(onlyfile + "$", "") + succ + "." + extFile;
							}
						}
						try {
							Socialnetworks snupdateforlogo= new Socialnetworks();
							snupdateforlogo.setSocialnetworkid((int) succ);
							snupdateforlogo.setSocialnetwork(snw);
							snupdateforlogo.setMainurl(mainurl);
							snupdateforlogo.setImageurl(logosn);
							socialnetworkService.updateSocNetWork(snupdateforlogo);
							resp = "msg_data_saved";
								FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
						} catch (IOException ex) {
							resp = "err_record_no_saved";
							System.out.println("Exception in addNewSocialnetwork(): " + ex.getMessage());
						}
					}
				}
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewSocialnetwork(): " + ex.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getSocNetworkById")
	public Object[] getSocNetworkById(HttpServletRequest req, HttpServletResponse res) {
		int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id").trim());
		Map<String, Object> data = new HashMap<String, Object>();
		if (id > 0) {
			List<Socialnetworks> info = socialnetworkService.getAll("FROM Socialnetworks WHERE socialnetworkid=" + id);
			data.put("info", info);
		}
		return new Object[] { data };
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/matters")
	public ModelAndView matters(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
/*				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int role = userDto.getRole();*/

				// Obtiene los privilegios del módulo
				@SuppressWarnings("rawtypes")
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "matters");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<Materias> data = materiasService.getAll("FROM Materias ORDER BY companyid, materiaid ASC");
				forModel.put("data", data);

				return new ModelAndView("matters", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewMatter")
	public void saveNewMatter(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				String newmatter = (req.getParameter("description") == null) ? ""
					: req.getParameter("description").trim();
				if (Functions.isEmpty(newmatter)) {
					resp = "msg_empty_data";
				}else{
					List<Materias> existsMatter = materiasService.getAll("FROM Materias WHERE UPPER(materia)='"+newmatter.toUpperCase()+"'");
					if(existsMatter.size()<1){
						Materias matter = new Materias();
						matter.setMateria(newmatter);
						matter.setCompanyid(userDto.getCompanyid());
						long succ = materiasService.addNewMateria(matter);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewMatter(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getMatterById")
	public Object[] getMatterById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<Materias> info = materiasService.getAll("FROM Materias WHERE materiaid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/getMaterias")
	public @ResponseBody Object[] getMaterias(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			List<Materias> materiasList = materiasService.getAll("FROM Materias ORDER BY materia ASC");
			return new Object[] { materiasList };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/updateMatter")
	public void updateMatter(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id"));
				String descr = (req.getParameter("descr") == null) ? "" : req.getParameter("descr").trim();
				if (Functions.isEmpty(descr)) {
					resp="msg_empty_data";
				}else{
					List<Materias> existsMatter = materiasService.getAll("FROM Materias WHERE UPPER(materia)='"+descr.toUpperCase()+"'");
					if(existsMatter.size()<2){
						if(existsMatter.size()==1 && existsMatter.get(0).getMateriaid() != id){
							resp="err_duplicated_data";
						}else{
							UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
							Materias entity= new Materias();
							entity.setMateriaid(id);
							entity.setMateria(descr);
							entity.setCompanyid(userDto.getCompanyid());
							materiasService.updateMateria(entity);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateMatter(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteMatter")
	public void deleteMatter(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				materiasService.deleteMateria(id);
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getMatterList")
	public Object[] getMatterList(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			List<Materias> info = materiasService.getAll("FROM Materias ORDER BY materia ASC");
			return new Object[] { info };
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/updateSocNetwork")
	public void updateSocNetwork(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = (req.getParameter("id") == null) ? 0 : Functions.toInt(req.getParameter("id"));
				String logosn = "", snw = (req.getParameter("snw") == null) ? "" : req.getParameter("snw").trim(),
					mainurl = (req.getParameter("mainurl") == null) ? "" : req.getParameter("mainurl").trim();
				if (Functions.isEmpty(snw)) {
					try {
						res.getWriter().write("msg_empty_data");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				// Guardamos archivos relacionados con el registro
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
				String destinationPath = Functions.addPath(rootPath,"doctos" + FileSystems.getDefault().getSeparator() + "images/socialnetworks", true);
				destinationPath = Functions.addPath(destinationPath, "", true);
				File paths[] = Functions.moveFiles(tmpPath, destinationPath, nameFiles);

				Socialnetworks socnetwork= new Socialnetworks();
				socnetwork.setSocialnetworkid(id);
				socnetwork.setSocialnetwork(snw);
				socnetwork.setMainurl(mainurl);
				socnetwork.setImageurl("");
				socialnetworkService.updateSocNetWork(socnetwork);
				resp = "msg_data_saved";
				if ( id > 0){
					if (paths != null) {
						for (File file : paths) {
							String[] filename = (file.getName()).split("\\.");
							String extFile = filename[filename.length - 1];
							File f = new File(file.getAbsolutePath());// Renombra el archivo por el id
							f.renameTo(new File(destinationPath + "/" + id + "." + extFile));
							String path = f.getAbsolutePath();
							String onlyfile = f.getName();
							logosn = path.replaceAll(onlyfile + "$", "") + id + "." + extFile;
						}
					}
					try {
						Socialnetworks snupdateforlogo= new Socialnetworks();
						snupdateforlogo.setSocialnetworkid(id);
						snupdateforlogo.setSocialnetwork(snw);
						snupdateforlogo.setMainurl(mainurl);
						snupdateforlogo.setImageurl(logosn);
						socialnetworkService.updateSocNetWork(snupdateforlogo);
						resp = "msg_data_saved";
							FileUtils.deleteDirectory(new File(tmpPath));// Elimina carpeta temporal
					} catch (IOException ex) {
						resp = "err_record_no_saved";
						System.out.println("Exception in updateSocNetwork(): " + ex.getMessage());//Personalizar mensaje
					}
				}
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteSocNetwork")
	public void deleteSocNetwork(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id"));
				socialnetworkService.deleteSocNetWork(id);
			}
		}
	}

	@RequestMapping(value = "/getSocNetworks")
	public @ResponseBody Object[] getSocNetworks(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);		if (sess != null) {
			List<Socialnetworks> socNetworkList = socialnetworkService.getAll("FROM Socialnetworks ORDER BY socialnetwork ASC");
			return new Object[] { socNetworkList };
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/cities")
	public ModelAndView cities(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
/*				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int role = userDto.getRole();*/

				// Obtiene los privilegios del módulo
				@SuppressWarnings("rawtypes")
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "cities");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<Ciudades> data = ciudadesService.getAll("FROM Ciudades ORDER BY companyid, ciudadid ASC");
				forModel.put("data", data);

				List<Estados> states = null;
				String stids = "";
				if(data.size()>0){
					for (int i = 0; i < data.size(); i++)
						stids += data.get(i).getEstadoid() + ",";
					states = estadosService.getAll("FROM Estados WHERE estadoid IN(" + stids.replaceAll(".$", "") + ")");
				}
				forModel.put("states", states);
				return new ModelAndView("cities", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewCity")
	public void saveNewCity(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				String newcity = (req.getParameter("ciudad") == null) ? "" : req.getParameter("ciudad").trim();
				int estadoid = (req.getParameter("estadoid") == null) ? 0 : Functions.toInt(req.getParameter("estadoid"));
				if (Functions.isEmpty(newcity)) {
					resp = "msg_empty_data";
				}else{
					List<Ciudades> existsCity = ciudadesService.getAll("FROM Ciudades WHERE UPPER(ciudad)='"
						+ newcity.toUpperCase() + "' AND estadoid=" + estadoid);
					if(existsCity.size()<1){
						Ciudades city = new Ciudades();
						city.setCiudad(newcity);
						city.setCompanyid(userDto.getCompanyid());
						city.setEstadoid(estadoid);
						long succ = ciudadesService.addNewCiudad(city);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewCity(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getCityById")
	public Object[] getCityById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<Ciudades> info = ciudadesService.getAll("FROM Ciudades WHERE ciudadid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/getCitiesByState")
	public Object[] getCitiesByState(HttpServletRequest req, HttpServletResponse res) {
		Long id = req.getParameter("estadoid") == null ? 0 : Functions.toLong(req.getParameter("estadoid"));
		String whereClause = (id>0?" WHERE estadoid=" + id:"");
		List<Ciudades> info = ciudadesService.getAll("FROM Ciudades" + whereClause + " ORDER BY ciudad ASC");
		return new Object[] { info };
	}

	@RequestMapping(value = "/getCiudades")
	public @ResponseBody Object[] getCiudades(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess == null)return null;
		List<Ciudades> cdList = ciudadesService.getAll("FROM Ciudades ORDER BY ciudad ASC, ciudadid ASC");
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("cdList", cdList);
		return new Object[] { info };
	}

	/** Obtiene los datos del tribunal de acuerdo a la tabla indicada
	@param id			Número entero con el id de la ciudad.
	@param courttype	Cadena de texto con el tribunal a obtener.
						"federal" consulta en la tabla de "juzgados".
						"unitario" consulta en la tabla de "tribunales unitarios".
						"colegiado" consulta en la tabla de "tribunales colegiados".		*/
	@RequestMapping(value = "/getCitytByIdnType")
	public @ResponseBody Object[] getCitytByIdnType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		List<Ciudades> cdinfo = null;
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			String courtsToGet = req.getParameter("courttype") == null ? "" : (req.getParameter("courttype").trim());
			int cdid = 0;
			if(courtsToGet.indexOf("colegiado")>=0){
				List<TribunalColegiado> court = tribunalCService.getAll("FROM TribunalColegiado WHERE tribunalcolegiadoid=" + id);
				if (court.get(0).getCiudadid() != null)
					cdid = court.get(0).getCiudadid();
			}else if(courtsToGet.indexOf("unitario")>=0){
				List<TribunalUnitario> court = tribunalUnitService.getAll("FROM TribunalUnitario WHERE tribunalunitarioid=" + id);
				if (court.get(0).getCiudadid() != null)
					cdid = court.get(0).getCiudadid();
			}else if(courtsToGet.indexOf("federal")>=0){
				List<Juzgados> court = juzgadosService.getAll("FROM Juzgados WHERE tipojuzgado=2 AND juzgadoid=" + id);
				if (court.get(0).getCiudadid() != null)
					cdid = court.get(0).getCiudadid();
			}
			cdinfo = ciudadesService.getAll("FROM Ciudades WHERE ciudadid=" + cdid);
		}
		return new Object[] {cdinfo};
	}

	@ResponseBody
	@RequestMapping(value = "/updateCity")
	public void updateCity(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("ciudadid") == null) ? 0 : Functions.toLong(req.getParameter("ciudadid"));
				String descr = (req.getParameter("ciudad") == null) ? "" : req.getParameter("ciudad").trim();
				int estadoid = (req.getParameter("estadoid") == null) ? 0 : Functions.toInt(req.getParameter("estadoid"));
				if (Functions.isEmpty(descr)) {
					resp="msg_empty_data";
				}else{
					List<Ciudades> existsCity = ciudadesService.getAll("FROM Ciudades WHERE UPPER(ciudad)='"
						+ descr.toUpperCase() + "' AND estadoid=" + estadoid);
					if(existsCity.size()<2){
						if(existsCity.size()==1 && existsCity.get(0).getCiudadid() != id){
							resp="err_duplicated_data";
						}else{
							UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
							Ciudades entity= new Ciudades();
							entity.setCiudadid(id);
							entity.setCiudad(descr);
							entity.setCompanyid(userDto.getCompanyid());
							entity.setEstadoid(estadoid);
							ciudadesService.updateCiudad(entity);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateCity(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteCity")
	public void deleteCity(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				ciudadesService.deleteCiudad(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/rooms")
	public ModelAndView rooms(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");

				// Obtiene los privilegios del módulo
				@SuppressWarnings({ "rawtypes" })
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "rooms");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);
	
				List<Salas> data = salasService.getAll("FROM Salas ORDER BY companyid, salaid ASC");
				forModel.put("data", data);

				return new ModelAndView("rooms", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewRoom")
	public void saveNewRoom(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
					String newroom = (req.getParameter("description") == null) ? ""
						: req.getParameter("description").trim();
				if (Functions.isEmpty(newroom)) {
					resp = "msg_empty_data";
				}else{
					List<Salas> existsRoom = salasService.getAll("FROM Salas WHERE UPPER(sala)='"+newroom.toUpperCase()+"'");
					if(existsRoom.size()<1){
						Salas room = new Salas();
						room.setSala(newroom);
						room.setCompanyid(userDto.getCompanyid());
						long succ = salasService.addNewSala(room);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewRoom(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getRoomById")
	public Object[] getRoomById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<Salas> info = salasService.getAll("FROM Salas WHERE salaid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/getRooms")
	public @ResponseBody Object[] getRooms(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			List<Salas> roomList = salasService.getAll("FROM Salas ORDER BY sala ASC");
			return new Object[] { roomList };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/updateRoom")
	public void updateRoom(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id"));
				String descr = (req.getParameter("descr") == null) ? "" : req.getParameter("descr").trim();
				if (Functions.isEmpty(descr)) {
					resp="msg_empty_data";
				}else{
					List<Salas> existsRoom = salasService.getAll("FROM Salas WHERE UPPER(sala)='"+descr.toUpperCase()+"'");
					if(existsRoom.size()<2){
						if(existsRoom.size()==1 && existsRoom.get(0).getSalaid() != id){
							resp="err_duplicated_data";
						}else{
							UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
							Salas entity= new Salas();
							entity.setSalaid(id);
							entity.setSala(descr);
							entity.setCompanyid(userDto.getCompanyid());
							salasService.updateSala(entity);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateRoom(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteRoom")
	public void deleteRoom(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				salasService.deleteSala(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/courts")//Juzgados locales
	public ModelAndView courts(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");

				// Obtiene los privilegios del módulo
				@SuppressWarnings({ "rawtypes" })
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "courts");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

//FIXME: La clausula "WHERE" deberá ser "1" y se queda como "is null" porque inicialmente no se tenía definición. 
				List<Juzgados> data = juzgadosService.getAll("FROM Juzgados WHERE tipojuzgado=1 OR tipojuzgado IS NULL ORDER BY juzgado ASC");
				forModel.put("data", data);

				String ids = "";
				List<Ciudades> cd = null;
				for (int i = 0; i < data.size(); i++)
					ids += data.get(i).getCiudadid() + ",";
				ids = ids.replaceAll(".$", "");
				if(!Functions.isEmpty(ids))
					cd = ciudadesService.getAll("FROM Ciudades WHERE ciudadid IN(" + ids + ")");
				forModel.put("cd", cd);
	
				ids = "";
				List<Estados> states = null;
				for (int i = 0; i < cd.size(); i++)
					ids += cd.get(i).getEstadoid() + ",";
				ids = ids.replaceAll(".$", "");
				if(!Functions.isEmpty(ids))
					states = estadosService.getAll("FROM Estados WHERE estadoid IN(" + ids + ")");
				forModel.put("states", states);

				return new ModelAndView("courts", forModel);
			}
		return new ModelAndView("login");
	}
//FIXME   no esta grabando como "matter" pasarlo como "materiaid" (por causas de import)
	@ResponseBody
	@RequestMapping(value = "/saveNewCourt")
	public void saveNewCourt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String newcourt = (req.getParameter("description") == null) ? "" : req.getParameter("description").trim(),
					distrito = (req.getParameter("distrito") == null) ? "" : req.getParameter("distrito").trim(),
					judges = (req.getParameter("judges") == null) ? "" : req.getParameter("judges").trim();
				int cdid = (req.getParameter("cdid") == null) ? 0 : Functions.toInt(req.getParameter("cdid")),
					courttype = (req.getParameter("tipojuzgado") == null) ? 0 : Functions.toInt(req.getParameter("tipojuzgado")),
					matterid = (req.getParameter("materiaid") == null) ? 0 : Functions.toInt(req.getParameter("materiaid")),
					partido = (req.getParameter("partido") == null) ? 0 : Functions.toInt(req.getParameter("partido"));
				if (Functions.isEmpty(newcourt) || cdid==0 || courttype==0)
					resp = (cdid==0)?"err_enter_city":"msg_empty_data";
				List<Juzgados> existsCourt = juzgadosService.getAll("FROM Juzgados WHERE UPPER(juzgado)='"
					+ newcourt.toUpperCase() + "' AND ciudadid=" + cdid + " AND tipojuzgado=" + courttype);
				if(existsCourt.size()>0){
					resp = "err_duplicated_data";
				}else{
					UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
					Juzgados entity = new Juzgados();
					entity.setJuzgado(newcourt);
					entity.setCompanyid(userDto.getCompanyid());
					entity.setCiudadid(cdid);
					entity.setTipojuzgado(courttype);
					entity.setMateriaid(matterid);
					entity.setPartido(partido);
					entity.setDistrito(distrito);
					if(matterid>0)
						entity.setMateriaid(matterid);
					long succ = juzgadosService.addNewJuzgado(entity);
					resp=(succ>0)?"msg_data_saved":"err_duplicated_data";
					
					if(!Functions.isEmpty(judges))
						setJudges(judges, succ);
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewCourt(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/getCourts")
	public @ResponseBody Object[] getCourts(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			List<Juzgados> courtList = juzgadosService.getAll("FROM Juzgados ORDER BY juzgado ASC");
			return new Object[] { courtList };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/getCourtById")
	public Object[] getCourtById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess == null)return null;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HashMap<Object, Object> data = new HashMap();
		Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
		List<Juzgados> info = juzgadosService.getAll("FROM Juzgados WHERE juzgadoid=" + id);
//TODO: Analizar la obtención de Jueces
		List<Jueces> judges = juecesService.getAll("FROM Jueces WHERE juzgadoid=" + id + " ORDER BY juezid");
		data.put("info",info);
		data.put("judges",judges);
		return new Object[] { data };
	}

	@RequestMapping(value = "/getCourtsByCity")
	public @ResponseBody Object[] getCourtsByCity(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			int cityid = (req.getParameter("cityid") == null) ? 0 : Functions.toInt(req.getParameter("cityid").trim());
			List<Juzgados> courtList = juzgadosService.getAll("FROM Juzgados WHERE ciudadid=" + cityid + " ORDER BY juzgado ASC");
			return new Object[] { courtList };
		}
		return null;
	}

	/** Obtiene los tribunales unitarios y/o colegiados en base a la ciudad
	@param cityid		Número entero con el id de la ciudad.
	@param courttype	Cadena de texto con el tribunal a obtener, pueden una o más separada por comas.
						"federales" obtiene los juzgados federales.
						"unitarios" obtiene los tribunales unitarios.
						"colegiados" obtiene los tribunales colegiados.		*/
	@RequestMapping(value = "/getAllCourtsByCity")
	public @ResponseBody Object[] getAllCourtsByCity(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> tribunales = new HashMap<String, Object>();
		if (sess != null) {
			Long cityid = req.getParameter("cityid") == null ? 0 : Functions.toLong(req.getParameter("cityid"));
			String courtsToGet = req.getParameter("courttype") == null ? "" : (req.getParameter("courttype").trim()),
				query = "";
			if(cityid!=0)
				query = " WHERE ciudadid=" + cityid;
			if(courtsToGet.indexOf("colegiados")>=0){
				List<TribunalColegiado> colegiados = tribunalCService
					.getAll("FROM TribunalColegiado" + query + " ORDER BY tribunalcolegiado ASC");
				tribunales.put("colegiados", colegiados);
			}
			if(courtsToGet.indexOf("unitarios")>=0){
				List<TribunalUnitario> unitarios = tribunalUnitService
					.getAll("FROM TribunalUnitario" + query + " ORDER BY tribunalunitario ASC");
				tribunales.put("unitarios", unitarios);
			}
			if(courtsToGet.indexOf("federales")>=0){
				List<Juzgados> federales = juzgadosService
					.getAll("FROM Juzgados WHERE tipojuzgado=2"+ query.replaceAll("^ WHERE "," AND ") + "ORDER BY juzgado ASC");
				//List<Juzgados> federales = juzgadosService.getAll("FROM Juzgados WHERE tipojuzgado=2 AND ciudadid=" + query.replaceAll("^WHERE ","AND ") + "ORDER BY juzgado ASC");
				tribunales.put("federales", federales);
			}
		}
		return new Object[] { tribunales };
	}

	@RequestMapping(value = "/getCourtsByState")
	public @ResponseBody Object[] getCourtsByState(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			int stateid = (req.getParameter("stateid") == null) ? 0 : Functions.toInt(req.getParameter("stateid").trim());
			String cities = "0,", whereClause=(stateid==0?"":" WHERE estadoid=" + stateid);
			List<Ciudades> cds= ciudadesService.getAll("FROM Ciudades" + whereClause);
			for (int i = 0; i < cds.size(); i++)
				cities += cds.get(i).getCiudadid() + ",";
			List<Juzgados> courtList = juzgadosService.getAll("FROM Juzgados WHERE ciudadid IN("
				+ cities.replaceAll(".$", "") + ") ORDER BY juzgado ASC");
			return new Object[] { courtList };
		}
		return null;
	}

	@RequestMapping(value = "/getCourtsByCityMatter")
	public @ResponseBody Object[] getCourtsByCityMatter(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			String cityid = (req.getParameter("cityid") == null) ? "" : req.getParameter("cityid").trim(),
				matterid = (req.getParameter("matterid") == null) ? "" : req.getParameter("matterid").trim(),
				whereClause="";
			
			/*if(!Functions.isEmpty(cityid))
				whereClause+=" AND ciudadid="+cityid;
			if(isNumeric(matterid)){
				if(Functions.toInt(matterid)>0)
					whereClause+=" AND (materiaid IN(" + matterid + ",0) OR materiaid IS NULL)";
				else if(matterid.equals("-1") || matterid.equals(""))
					whereClause+=" AND materiaid=0 OR materiaid IS NULL";
			}*/
			if(cityid.matches("[1-9][0-9]*"))
				whereClause+=" AND ciudadid="+cityid;
			whereClause+=" AND (materiaid"+(matterid.matches("[1-9][0-9]*")
				?" IN(" + matterid + ",0)":"!=0") + " OR materiaid IS NULL)";
			List<Juzgados> courtList = juzgadosService.getAll("FROM Juzgados "
				+ whereClause.replaceAll("^ AND "," WHERE ") + " ORDER BY juzgado ASC");
			return new Object[] { courtList };
		}
		return null;
	}

	@RequestMapping(value = "/getCourtsFilters")
	public @ResponseBody Object[] getCourtsFilters(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			int matter = (req.getParameter("matter") == null) ? 0 : Functions.toInt(req.getParameter("matter").trim()),
				state = (req.getParameter("state") == null) ? 0 : Functions.toInt(req.getParameter("state").trim()),
				city = (req.getParameter("city") == null) ? 0 : Functions.toInt(req.getParameter("city").trim());
			String whereClause="",
				distpart = (req.getParameter("distpart") == null) ? "" : req.getParameter("distpart").trim(),
				tipojuzgado=(req.getParameter("tipojuzgado") == null) ? "" : req.getParameter("tipojuzgado").trim();
			if(matter>0)
				whereClause=" AND (materiaid=" + matter + " OR materiaid IS NULL OR materiaid=0)";
			
			if(state>0 && city==0){
				String ids  = "0,";
				List<Ciudades> clist = ciudadesService.getAll("FROM Ciudades WHERE estadoid="+state);
				for (int i = 0; i < clist.size(); i++)
					ids += clist.get(i).getCiudadid() + ",";
				if(!Functions.isEmpty(ids))
				whereClause+=" AND ciudadid IN(" + ids.replaceAll(".$", "") + ')';
			}else if(city>0){
				whereClause+=" AND ciudadid=" + city;
			}
			if(!Functions.isEmpty(distpart)){
				String[] dp = distpart.split("\\|");
				if(dp.length==2)
					if(dp[0].equals("p"))
						whereClause+=(Functions.isNumeric(dp[1])?" AND partido=" + dp[1]:"");
					else
						whereClause+=" AND UPPER(distrito)='" + dp[1].toUpperCase() + "'";
			}
			if(!Functions.isEmpty(tipojuzgado))
				whereClause+=" AND (tipojuzgado=" + tipojuzgado + " OR tipojuzgado IS NULL)";

			whereClause=whereClause.replaceAll("^ AND "," WHERE ");
			List<Juzgados> courtList = juzgadosService.getAll("FROM Juzgados" + whereClause + " ORDER BY juzgado ASC");
			return new Object[] { courtList };
		}
		return null;
	}

	/** Obtiene el nombre del juzgado de distrito (juzgados o tribunales) a partir de un amparo.
	@param id			Cadena de texto con el id de "demandaamparoturnadaa" del amparo.
	@param courttype	Cadena de texto con el tribunal a obtener.
		"federal" consulta en la tabla de "juzgados".
		"unitario" consulta en la tabla de "tribunales unitarios".
		"colegiado" consulta en la tabla de "tribunales colegiados".		*/
	@RequestMapping(value = "/getCourtNameByIdnType")
	public @ResponseBody String getCourtNameByIdnType(String id, String courttype) {
		String courtname = "";
		if(id=="0")return courtname;
		if(courttype.indexOf("colegiado")>=0){
			List<TribunalColegiado> court = tribunalCService.getAll("FROM TribunalColegiado WHERE tribunalcolegiadoid=" + id);
			if (court.get(0).getTribunalcolegiado() != null)
				courtname = court.get(0).getTribunalcolegiado();
		}else if(courttype.indexOf("unitario")>=0){
			List<TribunalUnitario> court = tribunalUnitService.getAll("FROM TribunalUnitario WHERE tribunalunitarioid=" + id);
			if (court.get(0).getTribunalUnitario() != null)
				courtname = court.get(0).getTribunalUnitario();
		}else if(courttype.indexOf("federal")>=0){
			List<Juzgados> court = juzgadosService.getAll("FROM Juzgados WHERE tipojuzgado=2 AND juzgadoid=" + id);
			if (court.get(0).getJuzgado() != null)
				courtname = court.get(0).getJuzgado();
		}
		return courtname;
	}

	@ResponseBody
	@RequestMapping(value = "/updateCourt")
	public void updateCourt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_on_save";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id"));
				String descr = (req.getParameter("descr") == null) ? "" : req.getParameter("descr").trim(),
					distpart = (req.getParameter("distpart") == null) ? "" : req.getParameter("distpart").trim(),
					distpartnum = (req.getParameter("distpartnum") == null) ? "" : req.getParameter("distpartnum").trim(),
					distrito = "",
					judges = (req.getParameter("judges") == null) ? "" : req.getParameter("judges").trim();
				int cdid = (req.getParameter("cdid") == null) ? 0 : Functions.toInt(req.getParameter("cdid")),
					courttype = (req.getParameter("tipojuzgado") == null) ? 0 : Functions.toInt(req.getParameter("tipojuzgado")),
					matterid = (req.getParameter("matter") == null) ? 0 : Functions.toInt(req.getParameter("matter")),
					partido = 0;
				if (Functions.isEmpty(descr) || cdid==0){
					resp = (cdid==0)?"err_enter_city":"msg_empty_data";
				}else{
					List<Juzgados> existsCourt = juzgadosService.getAll("FROM Juzgados WHERE UPPER(juzgado)='"
						+ descr.toUpperCase() + "' AND ciudadid="+cdid + " AND materiaid=" + matterid + " AND tipojuzgado=" + courttype);
					if(existsCourt.size()<2){
						if(existsCourt.size()==1 && existsCourt.get(0).getJuzgadoid() != id
							&& existsCourt.get(0).getCiudadid() != cdid){
							resp="err_duplicated_data";
						}else{
							if(distpart.equals("d"))
								distrito=distpartnum;
							else if(distpart.equals("p"))
								partido=Functions.toInt(distpartnum);
							Juzgados entity= new Juzgados();
							entity.setJuzgadoid(id);
							entity.setJuzgado(descr);
							entity.setCiudadid(cdid);
							entity.setCompanyid(userDto.getCompanyid());
							entity.setTipojuzgado(courttype);
							entity.setPartido(partido);
							entity.setDistrito(distrito);
							if(matterid>0)
								entity.setMateriaid(matterid);
							juzgadosService.updateJuzgado(entity);
							resp = "msg_data_saved";
							if(!Functions.isEmpty(judges))
								setJudges(judges, id);
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteCourt")
	public void deleteCourt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				juzgadosService.deleteJuzgado(id);
			}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/federalcourts") //Juzgados federales
	public ModelAndView federalcourts(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "courts");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				// tipojuzgado=2 (Juzgados federales)
				List<Juzgados> data = juzgadosService.getAll("FROM Juzgados WHERE tipojuzgado=2 ORDER BY juzgado ASC");
				forModel.put("data", data);

				String ids = "";
				List<Ciudades> cd = null;
				for (int i = 0; i < data.size(); i++)
					ids += data.get(i).getCiudadid() + ",";
				ids = ids.replaceAll(".$", "");
				if(!Functions.isEmpty(ids))
					cd = ciudadesService.getAll("FROM Ciudades WHERE ciudadid IN(" + ids + ")");
				forModel.put("cd", cd);
	
				ids = "";
				List<Estados> states = null;
				for (int i = 0; i < cd.size(); i++)
					ids += cd.get(i).getEstadoid() + ",";
				ids = ids.replaceAll(".$", "");
				if(!Functions.isEmpty(ids))
					states = estadosService.getAll("FROM Estados WHERE estadoid IN(" + ids + ")");
				forModel.put("states", states);

				return new ModelAndView("federalcourts", forModel);
			}
		return new ModelAndView("login");
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/collegiatecourts")//Tribunal colegiado
	public ModelAndView collegiatecourts(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "collegiatecourts");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<TribunalColegiado> data = tribunalCService.getAll("FROM TribunalColegiado ORDER BY tribunalcolegiado ASC");
				forModel.put("data", data);

				List<Ciudades> cities = ciudadesService.getAll("FROM Ciudades ORDER BY ciudadid ASC");
				forModel.put("cities", cities);

				List<Estados> states = estadosService.getAll("FROM Estados ORDER BY estadoid ASC");
				forModel.put("states", states);

				return new ModelAndView("collegiatecourts", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewCollegiateCourt")
	public void saveNewCollegiateCourt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String clgcourt = (req.getParameter("description") == null) ? "" : req.getParameter("description").trim();
				int cityid = (req.getParameter("ciudadid") == null) ? 0 : Functions.toInt(req.getParameter("ciudadid"));

				if (Functions.isEmpty(clgcourt)) {
					resp = "msg_empty_data";
				}else if (cityid==0) {
					resp="err_select_city";
				}else{
					List<TribunalColegiado> existsCourt = tribunalCService.getAll("FROM TribunalColegiado WHERE UPPER(tribunalcolegiado)='"
						+ clgcourt.toUpperCase()+"' AND ciudadid="+ cityid);
					if(existsCourt.size()<1){
						UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
						TribunalColegiado court = new TribunalColegiado();
						court.setTribunalcolegiado(clgcourt);
						court.setCompanyid(userDto.getCompanyid());
						court.setCiudadid(cityid);
						long succ = tribunalCService.addNewTribunalC(court);
						if (succ > 0)
							resp = "msg_data_saved";
					}else{
						resp = "err_duplicated_data";
					}
				}
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewCollegiateCourt(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getClgCourtById")
	public Object[] getClgCourtById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<TribunalColegiado> info = tribunalCService.getAll("FROM TribunalColegiado WHERE tribunalcolegiadoid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/updateClgCourt")
	public void updateClgCourt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id"));
				String clgcourt = (req.getParameter("descr") == null) ? "" : req.getParameter("descr").trim();
				int cityid = (req.getParameter("ciudadid") == null) ? 0 : Functions.toInt(req.getParameter("ciudadid"));

				if (Functions.isEmpty(clgcourt)) {
					resp="msg_empty_data";
				}else if (cityid==0) {
					resp="err_select_city";
				}else{
					List<TribunalColegiado> existsCourt = tribunalCService.getAll("FROM TribunalColegiado WHERE UPPER(tribunalcolegiado)='"
						+ clgcourt.toUpperCase() + "' AND ciudadid="+ cityid);
					if(existsCourt.size()<2){
						if(existsCourt.size()==1 && existsCourt.get(0).getTribunalcolegiadoid() != id){
							resp="err_duplicated_data";
						}else{
							UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
							TribunalColegiado entity= new TribunalColegiado();
							entity.setTribunalcolegiadoid(id);
							entity.setTribunalcolegiado(clgcourt);
							entity.setCompanyid(userDto.getCompanyid());
							entity.setCiudadid(cityid);
							tribunalCService.updateTribunalC(entity);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateClgCourt(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteClgCourt")
	public void deleteClgCourt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				tribunalCService.deleteTribunalC(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/unitarycourts")//Tribunal unitarios
	public ModelAndView unitarycourts(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "unitarycourts");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<TribunalUnitario> data = tribunalUnitService.getAll("FROM TribunalUnitario ORDER BY tribunalunitario ASC");
				forModel.put("data", data);

				List<Ciudades> cities = ciudadesService.getAll("FROM Ciudades ORDER BY ciudadid ASC");
				forModel.put("cities", cities);

				List<Estados> states = estadosService.getAll("FROM Estados ORDER BY estadoid ASC");
				forModel.put("states", states);

				return new ModelAndView("unitarycourts", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewUnitaryCourt")
	public void saveNewUnitaryCourt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String newcourt = (req.getParameter("description") == null) ? "" : req.getParameter("description").trim();
				int cityid = (req.getParameter("ciudadid") == null) ? 0 : Functions.toInt(req.getParameter("ciudadid"));

				if (Functions.isEmpty(newcourt)) {
					resp = "msg_empty_data";
				}else if (cityid==0) {
					resp="err_select_city";
				}else{
					UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
					List<TribunalUnitario> existsCourt = tribunalUnitService.getAll("FROM TribunalUnitario WHERE UPPER(tribunalunitario)='"
						+ newcourt.toUpperCase() + "' AND ciudadid="+ cityid);
					if(existsCourt.size()<1){
						TribunalUnitario court = new TribunalUnitario();
						court.setTribunalUnitario(newcourt);
						court.setCompanyid(userDto.getCompanyid());
						court.setCiudadid(cityid);
						long succ = tribunalUnitService.addNewTribunalUnit(court);
						if (succ > 0)
							resp = "msg_data_saved";
					}else{
						resp = "err_duplicated_data";
					}
				}
			}
		}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewUnitaryCourt(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getUnitaryCourtById")
	public Object[] getUnitaryCourtById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<TribunalUnitario> info = tribunalUnitService.getAll("FROM TribunalUnitario WHERE tribunalunitarioid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/updateUnitaryCourt")
	public void updateUnitaryCourt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id"));
				String descr = (req.getParameter("descr") == null) ? "" : req.getParameter("descr").trim();
				int cityid = (req.getParameter("ciudadid") == null) ? 0 : Functions.toInt(req.getParameter("ciudadid"));

				if (Functions.isEmpty(descr)) {
					resp="msg_empty_data";
				}else if (cityid==0) {
					resp="err_select_city";
				}else{
					List<TribunalUnitario> existsUnitaryCourt = tribunalUnitService.getAll("FROM TribunalUnitario WHERE UPPER(tribunalunitario)='"
						+ descr.toUpperCase() + "' AND ciudadid="+ cityid);
					if(existsUnitaryCourt.size()<2){
						if(existsUnitaryCourt.size()==1 && existsUnitaryCourt.get(0).getTribunalUnitarioid() != id){
							resp="err_duplicated_data";
						}else{
							UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
							TribunalUnitario entity= new TribunalUnitario();
							entity.setTribunalUnitarioid(id);
							entity.setTribunalUnitario(descr);
							entity.setCompanyid(userDto.getCompanyid());
							entity.setCiudadid(cityid);
							tribunalUnitService.updateTribunalUnit(entity);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateUnitaryCourt(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteUnitaryCourt")
	public void deleteUnitaryCourt(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				tribunalUnitService.deleteTribunalUnit(id);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/circuits")
	public ModelAndView circuits(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "circuits");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<Circuitos> data = circuitosService.getAll("FROM Circuitos ORDER BY circuito ASC");
				forModel.put("data", data);

				return new ModelAndView("circuits", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewCircuit")
	public void saveNewCircuit(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String circuitname = (req.getParameter("circuito") == null) ? "" : req.getParameter("circuito").trim();
				Long estado = (req.getParameter("estadoid") == null) ? 0 : Functions.toLong(req.getParameter("estadoid"));
				if (Functions.isEmpty(circuitname) || estado==0) {
					resp = "msg_empty_data";
				}else{
					List<Circuitos> existsCircuit = circuitosService.getAll("FROM Circuitos WHERE UPPER(circuito)='"+circuitname.toUpperCase()+"'");
					if(existsCircuit.size()<1){
						Circuitos circuit = new Circuitos();
						circuit.setCircuito(circuitname);
						circuit.setEstadoid(estado);
						long succ = circuitosService.addNewCircuit(circuit);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewCircuit(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getCircuitById")
	public Object[] getCircuitById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			Map<String, Object> info = new HashMap<String, Object>();
			List<Circuitos> circuits = circuitosService.getAll("FROM Circuitos WHERE circuitoid=" + id);
			info.put("circuits", circuits);
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/getCircuitsByState")
	public @ResponseBody Object[] getCircuitsByState(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			int stateid = (req.getParameter("stateid") == null) ? 0 : Functions.toInt(req.getParameter("stateid"));
			String querytmp = (stateid==0?"":" WHERE estadoid="+stateid);
			List<Circuitos> circuitList = circuitosService.getAll("FROM Circuitos " + querytmp + "ORDER BY circuitoid ASC");
			return new Object[] { circuitList };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/updateCircuit")
	public void updateCircuit(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String circuit = (req.getParameter("circuito") == null) ? "" : req.getParameter("circuito").trim();
				Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id")),
					state = (req.getParameter("estadoid") == null) ? 0 : Functions.toLong(req.getParameter("estadoid"));
				if (Functions.isEmpty(circuit)) {
					resp="msg_empty_data";
				}else if(state == 0){
					resp="err_select_state";
				}else{
					List<Circuitos> existsCircuit = circuitosService.getAll("FROM Circuitos WHERE UPPER(circuito)='"
						+ circuit.toUpperCase()+"' AND estadoid=" + state);
					if(existsCircuit.size()<2){
						if(existsCircuit.size()==1 && existsCircuit.get(0).getCircuitoid() != id){
							resp="err_duplicated_data";
						}else{
							Circuitos entity= new Circuitos();
							entity.setCircuitoid(id);
							entity.setCircuito(circuit);
							entity.setEstadoid(state);
							circuitosService.updateCircuit(entity);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateCircuit(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteCircuit")
	public void deleteCircuit(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				circuitosService.deleteCircuit(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/organtypes")
	public ModelAndView organtypes(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "organtypes");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<TipoOrganos> data = tipoorganosService.getAll("FROM TipoOrganos ORDER BY tipoorgano ASC");
				forModel.put("data", data);

				return new ModelAndView("organtypes", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewOrganType")
	public void saveNewOrganType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String descr = (req.getParameter("tipoorgano") == null) ? "" : req.getParameter("tipoorgano").trim();
				if (Functions.isEmpty(descr)) {
					resp = "msg_empty_data";
				}else{
					List<TipoOrganos> existsOrganType = tipoorganosService.getAll("FROM TipoOrganos WHERE UPPER(tipoorgano)='"
						+ descr.toUpperCase() + "'");
					if(existsOrganType.size()<1){
						TipoOrganos organtype = new TipoOrganos();
						organtype.setTipoorgano(descr);
						long succ = tipoorganosService.addNewTipoOrgano(organtype);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewOrganType(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getOrganTypeById")
	public Object[] getOrganTypeById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<TipoOrganos> info = tipoorganosService.getAll("FROM TipoOrganos WHERE tipoorganoid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/updateOrganType")
	public void updateOrganType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("tipoorganoid") == null) ? 0 : Functions.toLong(req.getParameter("tipoorganoid"));
				String descr = (req.getParameter("tipoorgano") == null) ? "" : req.getParameter("tipoorgano").trim();
				if (Functions.isEmpty(descr)) {
					resp="msg_empty_data";
				}else{
					List<TipoOrganos> existsOrganType = tipoorganosService.getAll("FROM TipoOrganos WHERE UPPER(tipoorgano)='"
						+ descr.toUpperCase() + "'");
					if(existsOrganType.size()==1 && existsOrganType.get(0).getTipoorganoid() != id){
						resp="err_duplicated_data";
					}else{
						com.aj.model.TipoOrganos entity= new TipoOrganos();
						entity.setTipoorganoid(id);
						entity.setTipoorgano(descr);
						tipoorganosService.updateTipoOrgano(entity);
						resp = "msg_data_saved";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateOrganType(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteOrganType")
	public void deleteOrganType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				tipoorganosService.deleteTipoOrgano(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/jrdorgans")
	public ModelAndView jrdorgans(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "jrdorgans");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<JurisdictionalOrgans> data = organosService.getAll("FROM JurisdictionalOrgans ORDER BY organoid ASC");
				forModel.put("data", data);

				return new ModelAndView("jrdorgans", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewJrdOrgan")
	public void saveNewJrdOrgan(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String descr = (req.getParameter("organo") == null) ? "" : req.getParameter("organo").trim();
				Long city = (req.getParameter("ciudadid") == null) ? 0 : Functions.toLong(req.getParameter("ciudadid"));
				if (Functions.isEmpty(descr)) {
					resp="msg_empty_data";
				}else if(city==0){
					resp="err_select_city";
				}else{
					List<JurisdictionalOrgans> existsJrdOrgan = organosService.getAll("FROM JurisdictionalOrgans WHERE UPPER(organo)='"+descr.toUpperCase()+"'");
					if(existsJrdOrgan.size()<1){
						JurisdictionalOrgans entity = new JurisdictionalOrgans();
						entity.setOrgano(descr);
						entity.setCiudadid(city);
						long succ = organosService.addNewJrdOrgan(entity);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewJrdOrgan(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getJrdOrganById")
	public Object[] getJrdOrganById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<JurisdictionalOrgans> info = organosService.getAll("FROM JurisdictionalOrgans WHERE organoid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/updateJrdOrgan")
	public void updateJrdOrgan(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("organoid") == null) ? 0 : Functions.toLong(req.getParameter("organoid")),
					city = (req.getParameter("ciudadid") == null) ? 0 : Functions.toLong(req.getParameter("ciudadid"));
				String descr = (req.getParameter("organo") == null) ? "" : req.getParameter("organo").trim();
				if (Functions.isEmpty(descr)) {
					resp="msg_empty_data";
				}else if(city==0){
					resp="err_select_city";
				}else{
					List<JurisdictionalOrgans> existsJrdOrgan = organosService.getAll("FROM JurisdictionalOrgans WHERE UPPER(organo)='"
						+ descr.toUpperCase()+"' AND ciudadid=" + city);
					if(existsJrdOrgan.size()<2){
						if(existsJrdOrgan.size()==1 && existsJrdOrgan.get(0).getOrganoid() != id){
							resp="err_duplicated_data";
						}else{
							JurisdictionalOrgans entity= new JurisdictionalOrgans();
							entity.setOrganoid(id);
							entity.setOrgano(descr);
							entity.setCiudadid(city);
							organosService.updateJrdOrgan(entity);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateJrdOrgan(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteJrdOrgan")
	public void deleteJrdOrgan(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				organosService.deleteJrdOrgan(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/regions")
	public ModelAndView regions(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "regions");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);
	
				List<Regiones> data = regionesService.getAll("FROM Regiones ORDER BY region ASC");
				forModel.put("data", data);

				return new ModelAndView("regions", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewRegion")
	public void saveNewRegion(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String newregion = (req.getParameter("region") == null) ? "" : req.getParameter("region").trim();
				if (Functions.isEmpty(newregion)) {
					resp = "msg_empty_data";
				}else{
					List<Regiones> existsRegion = regionesService.getAll("FROM Regiones WHERE UPPER(region)='"+newregion.toUpperCase()+"'");
					if(existsRegion.size()<1){
						Regiones reg = new Regiones();
						reg.setRegion(newregion);
						long succ = regionesService.addNewRegion(reg);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewRegion(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getRegionById")
	public Object[] getRegionById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<Regiones> info = regionesService.getAll("FROM Regiones WHERE regionid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/updateRegion")
	public void updateRegion(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("regionid") == null) ? 0 : Functions.toLong(req.getParameter("regionid"));
				String descr = (req.getParameter("region") == null) ? "" : req.getParameter("region").trim();
				if (Functions.isEmpty(descr)) {
					resp="msg_empty_data";
				}else{
					List<Regiones> existsRegion = regionesService.getAll("FROM Regiones WHERE UPPER(region)='"+descr.toUpperCase()+"'");
					if(existsRegion.size()<2){
						if(existsRegion.size()==1 && existsRegion.get(0).getRegionid() != id){
							resp="err_duplicated_data";
						}else{
							Regiones entity= new Regiones();
							entity.setRegionid(id);
							entity.setRegion(descr);
							regionesService.updateRegion(entity);
							resp = "msg_data_saved";
						}
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateRegion(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteRegion")
	public void deleteRegion(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				regionesService.deleteRegion(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/trialtypes")
	public ModelAndView trialtypes(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");

				// Obtiene los privilegios del módulo
				@SuppressWarnings("rawtypes")
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "trialtypes");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<TipoJuicios> data = tipojuiciosService.getAll("FROM TipoJuicios ORDER BY tipojuicio ASC");
				forModel.put("trialtype", data);

				List<TipoJuiciosAccion> tjacc = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion");
				forModel.put("tjacc", tjacc);

				List<Materias> matter = materiasService.getAll("FROM Materias");
				forModel.put("matter", matter);

				return new ModelAndView("trialtypes", forModel);
			}
		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/saveNewTrialType")
	public void saveNewTrialType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved_support";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					String descr = (req.getParameter("tipojuicio") == null) ? "" : req.getParameter("tipojuicio").trim(),
						descren = (req.getParameter("tipojuicioen") == null) ? "" : req.getParameter("tipojuicioen").trim(),
						allColumns = (req.getParameter("allColumns") == null) ? "" : req.getParameter("allColumns").trim(),
						rex = "";	/*(req.getParameter("rex") == null) ? "" : req.getParameter("rex").trim(),*/
					int matterid = (req.getParameter("matterid") == null) ? 0 : Functions.toInt(req.getParameter("matterid")),
						reqactor = (req.getParameter("reqactor") == null) ? 0 : Functions.toInt(req.getParameter("reqactor")),
						reqdef = (req.getParameter("reqdef") == null) ? 0 : Functions.toInt(req.getParameter("reqdef")),
						reqthird = (req.getParameter("reqthird") == null) ? 0 : Functions.toInt(req.getParameter("reqthird")),
						acciontrialid = (req.getParameter("actiontrialid") == null) ? 0 : Functions.toInt(req.getParameter("actiontrialid"));
					if (Functions.isEmpty(descr)) {
						resp = "err_enter_description";
					}else if (matterid==0) {
						resp = "err_select_matter";
					}else if (acciontrialid==0){
						resp = "err_select_accion";
					}else{
						List<TipoJuicios> existsTrialType = tipojuiciosService.getAll("FROM TipoJuicios WHERE UPPER(tipojuicio)='"
							+ descr.toUpperCase() + "' AND accionid=" + acciontrialid);
						if(existsTrialType.size()>1){
							resp="err_duplicated_data";
						}else{
							TipoJuicios trialtype = new TipoJuicios();
							trialtype.setTipojuicio(descr);
							trialtype.setTipojuicioen(descren);
							trialtype.setAccionid(acciontrialid);
							trialtype.setRequiereactor(reqactor);
							trialtype.setRequieredemandado(reqdef);
							trialtype.setRequieretercero(reqthird);

							Integer succ = tipojuiciosService.addNewTipoJuicio(trialtype);
							resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
							String[] allRows = allColumns.split("~");
							for(int r=0; r<allRows.length; r++){
								String[] coldata = allRows[r].split("\\|");
								if(!Functions.isEmpty(coldata[0])){
									String tit=coldata[0],desc=coldata[1],tit_en=coldata[3],desc_en=coldata[4];
									int mandatory = ((coldata[2]).equals("true")?1:0),lng=Functions.toInt(coldata[5]),
										needded = ((coldata[6]).equals("true")?1:0);
									@SuppressWarnings("unused")
									long added = setCustomColumn(0, tit, desc, mandatory, tit_en, desc_en, "text", "tipojuicios", "tipojuicioid", succ, lng, rex, needded);
									//if(added==0)System.out.println("Nombre de columna ya existe.");
								}
							}
							resp="msg_data_saved";
						}
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewTrialType(): " + ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getTrialTypeById")
	public Object[] getTrialTypeById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			Map<String, Object> data = new HashMap<String, Object>();
			List<TipoJuicios> trialtype = tipojuiciosService.getAll("FROM TipoJuicios WHERE tipojuicioid=" + id);
			List<TipoJuiciosAccion> tjacc = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion WHERE accionid="
				+ trialtype.get(0).getAccionid());
			List<CustomColumns> custcol = getCCByTabIdref("tipojuicios", "tipojuicioid", (trialtype.get(0).getTipojuicioid()).intValue());
			data.put("tjacc",tjacc);
			data.put("info", trialtype);
			data.put("custcol", custcol);
			return new Object[] { data };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/getTrialTypesList")
	public Object[] getTrialTypesList(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			int accionid = req.getParameter("accionid") == null ? 0 : Functions.toInt(req.getParameter("accionid"));
			String query="";
			if(accionid!=0)
				query = " WHERE accionid=" + accionid;
			List<TipoJuicios> list = tipojuiciosService
				.getAll("FROM TipoJuicios" + query + " ORDER BY tipojuicio ASC");
			return new Object[] { list };
		}
		return null;
	}

//FIXME validar esta Función si continua utilizandose o no:
	@ResponseBody
	@RequestMapping(value = "/getCCByTrialType")
	public List<CustomColumns> getCCByTrialType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long ttid = req.getParameter("ttid") == null ? 0 : Functions.toLong(req.getParameter("ttid"));
			List<CustomColumns> ccval = getCCByTabIdref("tipojuicios", "tipojuicioid", Functions.toInt(ttid+""));
			return ccval;
		}
		return null;
	}

	@RequestMapping(value = "/getTJGByMatter")
	public @ResponseBody Object[] getTJGByMatter(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			int matterid = (req.getParameter("matterid") == null) ? 0 : Functions.toInt(req.getParameter("matterid"));
			List<TipoJuiciosAccion> tjgList = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion WHERE materiaid="
				+ matterid + " ORDER BY descripcion ASC");
			return new Object[] { tjgList };
		}
		return null;
	}

	/** Obtiene el nombre de las Columnas personalizadas mediante el nombre de tabla y id referenciado
	@param ligadoatabla	{String} Nombre de la tabla donde esta ligado el campo.
	@param nombrepk		{String} Nombre del campo al que esta ligado.
	@param idref		{int}	 Id de referencia ligado al campo.
	@return		Retorna los nombres de columnas registradas para su uso.	*/
	public List<CustomColumns> getCCByTabIdref(String ligadoatabla, String nombrepk, int idref) {
		List<CustomColumns> colnames = customcolumnsService.getAll("FROM CustomColumns WHERE ligadoatabla='" 
			+ ligadoatabla + "' AND nombrepk='" + nombrepk + "' AND idpkreferenced=" + idref + " ORDER BY customcolumnid ASC");
		return colnames;
	}

	@ResponseBody
	@RequestMapping(value = "/updateTrialType")
	public void updateTrialType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved_support";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					String descr = (req.getParameter("tipojuicio") == null) ? "" : req.getParameter("tipojuicio").trim(),
						descren = (req.getParameter("tipojuicioen") == null) ? "" : req.getParameter("tipojuicioen").trim(),
						allColumns = (req.getParameter("allColumns") == null) ? "" : req.getParameter("allColumns").trim(),
						rex = "";	/*(req.getParameter("rex") == null) ? "" : req.getParameter("rex").trim(),*/
					int id = (req.getParameter("tipojuicioid") == null) ? 0 : Functions.toInt(req.getParameter("tipojuicioid")),
						matterid = (req.getParameter("matterid") == null) ? 0 : Functions.toInt(req.getParameter("matterid")),
						reqactor = (req.getParameter("reqactor") == null) ? 0 : Functions.toInt(req.getParameter("reqactor")),
						reqdef = (req.getParameter("reqdef") == null) ? 0 : Functions.toInt(req.getParameter("reqdef")),
						reqthird = (req.getParameter("reqthird") == null) ? 0 : Functions.toInt(req.getParameter("reqthird")),
						acciontrialid = (req.getParameter("actiontrialid") == null) ? 0 : Functions.toInt(req.getParameter("actiontrialid"));
					if (Functions.isEmpty(descr)) {
						resp = "err_enter_description";
					}else if (matterid==0) {
						resp = "err_select_matter";
					}else if (acciontrialid==0){
						resp = "err_select_accion";
					}else{
						List<TipoJuicios> existsTrialType = tipojuiciosService.getAll("FROM TipoJuicios WHERE UPPER(tipojuicio)='"
							+ descr.toUpperCase() + "' AND accionid=" + acciontrialid);	// Verifica que el registro no se duplique
						if(existsTrialType.size()<2){
							if(existsTrialType.size()==1 && existsTrialType.get(0).getTipojuicioid() != id){
								resp="err_duplicated_data";
							}else{
								TipoJuicios trialtype = new TipoJuicios();
								trialtype.setTipojuicioid(Long.valueOf(id));
								trialtype.setTipojuicio(descr);
								trialtype.setTipojuicioen(descren);
								trialtype.setAccionid(acciontrialid);
								trialtype.setRequiereactor(reqactor);
								trialtype.setRequieredemandado(reqdef);
								trialtype.setRequieretercero(reqthird);
								tipojuiciosService.updateTipoJuicio(trialtype);
								resp="msg_data_saved";
	
								String[] allRows = allColumns.split("~");
								String updatedItems="0,";
								for(int r=0; r<allRows.length; r++){
									String[] coldata = allRows[r].split("\\|");
									if(!Functions.isEmpty(coldata[0])){
										long idref=(Functions.isEmpty(coldata[0])?0:Functions.toLong(coldata[0]));
										String tit=coldata[1],desc=coldata[2],tit_en=coldata[4],desc_en=coldata[5];
										int mandatory = ((coldata[3]).equals("true")?1:0),lng=Functions.toInt(coldata[6]),
											needded = ((coldata[7]).equals("true")?1:0);
										long succ = setCustomColumn(idref, tit, desc, mandatory, tit_en, desc_en, "text", "tipojuicios", "tipojuicioid", id, lng, rex, needded);
										updatedItems+= succ+",";
										//if(succ==0)System.out.println("Nombre de columna ya existe.");
									}
								}
								//Elimina las columnas personalizadas que fueron descartadas por el usuario.
								List<CustomColumns> rowsToDelete = customcolumnsService.getAll("FROM CustomColumns WHERE ligadoatabla='tipojuicios' AND nombrepk="
									+ "'tipojuicioid' AND idpkreferenced=" + id + " AND customcolumnid NOT IN(" + updatedItems.replaceAll(".$","") + ")");
								for(int d=0; d<rowsToDelete.size(); d++)
									deleteCustomColumn(rowsToDelete.get(d).getCustomcolumnid());
							}
						}
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateTrialType(): " + ex.getMessage());
		}
	}

	private void deleteCustomColumn(Long id) {
		customcolumnsService.deleteCustomColumn(id);
	}

	@RequestMapping(value = "/deleteTrialType")
	public void deleteTrialType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				tipojuiciosService.deleteTipoJuicio(id);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ttactions")
	public @ResponseBody ModelAndView ttactions(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "tipojuiciosaccion");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<TipoJuiciosAccion> data = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion ORDER BY descripcion ASC");
				forModel.put("data", data);

				List<Vias> wayslist = null;
				List<Materias> matters = null;
				if(data.size()>0){
					String matids = "", wayids = "";
					for (int i = 0; i < data.size(); i++){
						matids += data.get(i).getMateriaid() + ",";
						wayids += data.get(i).getViaid() + ",";
					}
					matters = materiasService.getAll("FROM Materias WHERE materiaid IN(" + matids.replaceAll(".$", "") + ") ORDER BY materiaid ASC");
					wayslist = viasService.getAll("FROM Vias WHERE viaid IN(" + wayids.replaceAll(".$", "") + ") ORDER BY viaid ASC");
				}
				forModel.put("wayslist", wayslist);
				forModel.put("matters", matters);
				//return new ModelAndView("tipojuiciosaccion", forModel);
				return new ModelAndView("ttactions", forModel);
			}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/saveNewAccion")
	public @ResponseBody void saveNewAccion(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved_support";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					String descr = (req.getParameter("description") == null) ? "" : req.getParameter("description").trim(),
						descren = (req.getParameter("descriptionen") == null) ? "" : req.getParameter("descriptionen").trim();
					int matterid = (req.getParameter("matterid") == null) ? 0 : Functions.toInt(req.getParameter("matterid")),
						wayid = (req.getParameter("wayid") == null) ? 0 : Functions.toInt(req.getParameter("wayid"));
					if (Functions.isEmpty(descr)){
						resp = "msg_empty_data";
					}else if (matterid==0){
						resp = "err_select_matter";
					}else if (wayid==0){
						resp = "err_select_way";
					}else{
						List<TipoJuiciosAccion> existsAccion = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion WHERE UPPER(descripcion)='"
							+ descr.toUpperCase() + "' AND materiaid=" + matterid);
						if(existsAccion.size()<1){
							TipoJuiciosAccion entity = new TipoJuiciosAccion();
							entity.setDescripcion(descr);
							entity.setDescripcionen(descren);
							entity.setMateriaid(matterid);
							entity.setViaid(wayid);
							long succ = tipojuiciosaccionService.addNewAccion(entity);
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
			System.out.println("Exception in addNewAccion(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/getAccionById")
	public @ResponseBody Object[] getAccionById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<TipoJuiciosAccion> info = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion WHERE accionid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/getAccionByMatterId")
	public @ResponseBody Object[] getAccionByMatterId(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("matterid") == null ? 0 : Functions.toLong(req.getParameter("matterid"));
			String query = "";
			if(id!=0)
				 query = " WHERE materiaid=" + id;
			List<TipoJuiciosAccion> info = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion" + query);
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/getActType")
	public @ResponseBody Object[] getActType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			List<TipoActuacion> actList = tipoactuacionService
					.getAll("FROM TipoActuacion ORDER BY tipoactuacionid ASC");
			return new Object[] { actList };
		}
		return null;
	}

	@RequestMapping(value = "/updateAccion")
	public @ResponseBody void updateAccion(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved_support";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					String descr = (req.getParameter("descr") == null) ? "" : req.getParameter("descr").trim(),
						descren = (req.getParameter("descren") == null) ? "" : req.getParameter("descren").trim();
					long accion = (req.getParameter("accion") == null) ? 0 : Functions.toLong(req.getParameter("accion"));
					int matterid = (req.getParameter("matterid") == null) ? 0 : Functions.toInt(req.getParameter("matterid")),
						wayid = (req.getParameter("wayid") == null) ? 0 : Functions.toInt(req.getParameter("wayid"));
					if (Functions.isEmpty(descr)){
						resp = "msg_empty_data";
					}else if (matterid==0){
						resp = "err_select_matter";
					}else if (wayid==0){
						resp = "err_select_way";
					}else{
						List<TipoJuiciosAccion> existsAccion = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion WHERE UPPER(descripcion)='"
							+ descr.toUpperCase() + "' AND materiaid=" + matterid);
						if(existsAccion.size()>0){
							resp="err_duplicated_data";
						}else{
							TipoJuiciosAccion entity= new TipoJuiciosAccion();
							entity.setAccionid(accion);
							entity.setDescripcion(descr);
							entity.setDescripcionen(descren);
							entity.setMateriaid(matterid);
							entity.setViaid(wayid);
							tipojuiciosaccionService.updateAccion(entity);
							resp = "msg_data_saved";
						}
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateAccion(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteAccion")
	public void deleteAccion(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
					tipojuiciosaccionService.deleteAccion(id);
				}
			}
		}
	}

// ********** Columnas relacionadas -RelatedColums- (ini) **********
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/relatedcolumns")
	public @ResponseBody ModelAndView relatedcolumns(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) req.getSession().getAttribute("UserDTO");
				if(userDto.getRole()!=ROLE_SYSADMIN)return new ModelAndView("login");
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");

				// Obtiene los privilegios del módulo
				Map<String, Object> forModel = new HashMap<String, Object>();
				List<RelatedColumns> data = relatedcolumnsService.getAll("FROM RelatedColumns ORDER BY columnname1 ASC");
				forModel.put("data", data);
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu ORDER BY menuid ASC", null);
				forModel.put("menu", menu);
				return new ModelAndView("relatedcolumns", forModel);
			}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/saveNewRelCols")
	public @ResponseBody void saveNewRelCols(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN)){
					String colname = (req.getParameter("colname") == null) ? "" : req.getParameter("colname").trim(),
						alias = (req.getParameter("alias") == null) ? "" : req.getParameter("alias").trim(),
						msgjs = (req.getParameter("msgjs") == null) ? "" : req.getParameter("msgjs").trim(),
						msgjsp = (req.getParameter("msgjsp") == null) ? "" : req.getParameter("msgjsp").trim(),
						fromTable = (req.getParameter("fromTable") == null) ? "" : req.getParameter("fromTable").trim(),
						fromCol = (req.getParameter("fromCol") == null) ? "" : req.getParameter("fromCol").trim(),
						descr = (req.getParameter("descr") == null) ? "" : req.getParameter("descr").trim(),
						fromTable2 = (req.getParameter("fromTable2") == null) ? "" : req.getParameter("fromTable2").trim(),
						fromCol2 = (req.getParameter("fromCol2") == null) ? "" : req.getParameter("fromCol2").trim(),
						descr2 = (req.getParameter("descr2") == null) ? "" : req.getParameter("descr2").trim();
					if (Functions.isEmpty(colname)) {
						resp = "err_enter_msg_name";
					}else if (Functions.isEmpty(msgjs) && Functions.isEmpty(msgjsp)) {
						resp = "err_enter_onedescription";
					}else{
						List<RelatedColumns> existsColName = relatedcolumnsService
							.getAll("FROM Relatedcolumns WHERE UPPER(columnname1)='" + colname.toUpperCase()
							+ "' OR UPPER(columnname2)='" + alias.toUpperCase());
						if(existsColName.size()<1){
							RelatedColumns relcol = new RelatedColumns();
							relcol.setColumnname1(colname);
							relcol.setColumnname2(alias);
							relcol.setMessagejs(msgjs);
							relcol.setMessagejsp(msgjsp);
							relcol.setRelfromtable(fromTable);
							relcol.setRelfromcolumn(fromCol);
							relcol.setRelfromdescription(descr);
							relcol.setRelsubtable(fromTable2);
							relcol.setRelsubcolumn(fromCol2);
							relcol.setRelsubdescription(descr2);
							long succ = relatedcolumnsService.addNewRelatedCol(relcol);
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
			System.out.println("Exception in addRelCols(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/getRelColsById")
	public @ResponseBody Object[] getRelColsById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<RelatedColumns> info = relatedcolumnsService.getAll("FROM Relatedcolumns WHERE relcolumnid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/getRelColsByName")
	public @ResponseBody Object[] getRelColsByName(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			String colname = (req.getParameter("colname") == null) ? "" : req.getParameter("colname").trim(),
				alias = (req.getParameter("alias") == null) ? "" : req.getParameter("alias").trim();
			List<RelatedColumns> info = relatedcolumnsService
				.getAll("FROM Relatedcolumns WHERE UPPER(columnname1)='" + colname.toUpperCase()
				+ "' OR UPPER(columnname2)='" + alias.toUpperCase());
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/updateRelCols")
	public @ResponseBody void updateRelCols(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "false";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN)){
					Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
					String colname = (req.getParameter("colname") == null) ? "" : req.getParameter("colname").trim(),
						alias = (req.getParameter("alias") == null) ? "" : req.getParameter("alias").trim(),
						msgjs = (req.getParameter("msgjs") == null) ? "" : req.getParameter("msgjs").trim(),
						msgjsp = (req.getParameter("msgjsp") == null) ? "" : req.getParameter("msgjsp").trim(),
						fromTable = (req.getParameter("fromTable") == null) ? "" : req.getParameter("fromTable").trim(),
						fromCol = (req.getParameter("fromCol") == null) ? "" : req.getParameter("fromCol").trim(),
						descr = (req.getParameter("descr") == null) ? "" : req.getParameter("descr").trim(),
						fromTable2 = (req.getParameter("fromTable2") == null) ? "" : req.getParameter("fromTable2").trim(),
						fromCol2 = (req.getParameter("fromCol2") == null) ? "" : req.getParameter("fromCol2").trim(),
						descr2 = (req.getParameter("descr2") == null) ? "" : req.getParameter("descr2").trim();
					if (Functions.isEmpty(colname)) {
						resp = "err_enter_msg_name";
					}else if (Functions.isEmpty(msgjs) && Functions.isEmpty(msgjsp)) {
						resp = "err_enter_onedescription";
					}else{
						List<RelatedColumns> existsColName = relatedcolumnsService
							.getAll("FROM Relatedcolumns WHERE UPPER(columnname1)='" + colname.toUpperCase()
							+ "' OR UPPER(columnname2)='" + alias.toUpperCase());
						if(existsColName.size()<2){
							if(existsColName.size()==1 && existsColName.get(0).getRelcolumnid() != id){
								resp="err_duplicated_data";
							}else{
								RelatedColumns relcol = new RelatedColumns();
								relcol.setColumnname1(colname);
								relcol.setColumnname2(alias);
								relcol.setMessagejs(msgjs);
								relcol.setMessagejsp(msgjsp);
								relcol.setRelfromtable(fromTable);
								relcol.setRelfromcolumn(fromCol);
								relcol.setRelfromdescription(descr);
								relcol.setRelsubtable(fromTable2);
								relcol.setRelsubcolumn(fromCol2);
								relcol.setRelsubdescription(descr2);
								long succ = relatedcolumnsService.addNewRelatedCol(relcol);
								resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
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
			System.out.println("Exception in updateRelCols(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteRelCols")
	public void deleteRelCols(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN)){
					Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
					relatedcolumnsService.deleteRelatedCol(id);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/customcolumns")
	public @ResponseBody ModelAndView customcolumns(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");
/*				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				int role = userDto.getRole();*/

				// Obtiene los privilegios del módulo
				@SuppressWarnings("rawtypes")
				HashMap<Object, Object> parameters = new HashMap();
				parameters.put("urlMethod", "customcolumns");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<CustomColumns> data = customcolumnsService.getAll("FROM CustomColumns ORDER BY customcolumnid ASC");
				forModel.put("data", data);

				return new ModelAndView("customcolumns", forModel);
			}
		return new ModelAndView("login");
	}

	/** Crea y almacena una nueva columna personalizada.
		@param customcolumnid	{long}	 Customcolumnid en caso que sea un registro a actualizar o cero si es nuevo registro.
		@param titulo			{String} Título a mostrar en el campo.
		@param descripcion		{String} Descripción opcional para ser motrado como ayuda (tooltip y placeholder).
		@param obligatorio		{int}	 0=opcional, 1=Obligatorio.
		@param tituloen			{String} Título en inglés a mostrar el campo.
		@param descripcionen	{String} Descripción opcional en inglés para ser motrado como ayuda (tooltip y placeholder).
		@param tipodecolumna	{String} Tipo de columna basado en el tag &lt;type&gt;.
		@param ligadoatabla		{String} Nombre de la tabla donde será ligado el campo.
		@param nombrepk			{String} Nombre del campo al que será ligado.
		@param idpkreferenced	{int}	 Id de referencia donde será ligado el campo.
		@param longitud			{int}	 Estilo para indicar el largo de la columna basado en la clase bootstrap "col-sm-xx",
										 donde "xx" corresponde a un número entre 1 y 12.
		@param regex			{String} Patrón de expresión regular a aplicar.
		@param masdeuno			{int}	 Permite la facultad que la columna se pueda agregar tantas veces sea necesario: 0=no, 1=sí.
		@return		 */
	public long setCustomColumn(long customcolumnid,  String titulo, String descripcion, int obligatorio, String tituloen, String descripcionen,
		String tipodecolumna, String ligadoatabla, String nombrepk, int idpkreferenced, int longitud, String regex, int masdeuno) {
		if(Functions.isEmpty(titulo))return 0;
		CustomColumns entity = new CustomColumns();
		entity.setTitulo(titulo);
		entity.setDescripcion(descripcion);
		entity.setObligatorio(obligatorio);
		entity.setTituloen(tituloen);
		entity.setDescripcionen(descripcionen);
		entity.setTipodecolumna(tipodecolumna);
		entity.setLigadoatabla(ligadoatabla);
		entity.setNombrepk(nombrepk);
		entity.setIdpkreferenced(idpkreferenced);
		entity.setLongitud(longitud);
		entity.setRegex(regex);
		entity.setMasdeuno(masdeuno);
		if(customcolumnid>0){	// Actualizar elemento existente
			entity.setCustomcolumnid(customcolumnid);
			customcolumnsService.updateCustomColumn(entity);
			return customcolumnid;
		}else{
			return customcolumnsService.addNewCustomColumn(entity);
		}
	}

	@RequestMapping(value = "/deleteCustomColumn")
	public void deleteCustomColumn(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				customcolumnsService.deleteCustomColumn(id);
			}
		}
	}
//FIXME Validar este scriptlet:
	@RequestMapping(value = "/getCustColByTrialType")
	public @ResponseBody Object[] getCustColByTrialType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			int id = req.getParameter("id") == null ? 0 : Functions.toInt(req.getParameter("id"));
			Map<String, Object> data = new HashMap<String, Object>();
			
			List<TipoJuicios> trialtype = tipojuiciosService.getAll("FROM TipoJuicios WHERE tipojuicioid=" + id);
			List<CustomColumns> custCol = customcolumnsService.getAll("FROM CustomColumns " 
				+ "WHERE ligadoatabla='tipojuicios' AND nombrepk='tipojuicioid' AND idpkreferenced="
				+ id + " ORDER BY customcolumnid ASC");
			@SuppressWarnings("unused")
			List<CustomColumns> custcol = getCCByTabIdref("tipojuicios", "tipojuicioid", id);
			data.put("info", trialtype);
			data.put("custcol", custCol);
			return new Object[] { data };
		}
		return null;
	}
	// ********** Columnas relacionadas -RelatedColums- (ini) **********

	/** Crea o actualiza los nombres de los jueces de un juzgado.
	@param judges	Cadena de texto con el nombre del juez y opcionalmente un pipe seguido de 'juezid'
	@param courtid	Id del juzgado
	@return	Número de registros afectados	*/
	public void setJudges(String allJudgesStr, Long courtid) {
		String[] judges = allJudgesStr.split("~");
		String notDel="0,";
		for(int j=0;j<judges.length;j++){
			String[] coldata = judges[j].split("\\|");
			List<Jueces> existsJudge = juecesService.getAll("FROM Jueces WHERE UPPER(nombre)='"
				+ (coldata[0].toUpperCase()) + "' AND juzgadoid=" + courtid);
			if(existsJudge.size()>0){
				notDel+=existsJudge.get(0).getJuezid()+",";
				continue;
			}
			Jueces entity = new Jueces();
			entity.setNombre(coldata[0]);
			entity.setJuzgadoid(courtid);
			if(coldata.length>1){	//Tiene id
				entity.setJuezid(Functions.toLong(coldata[1]));
				juecesService.updateJuez(entity);
				notDel+=coldata[1]+",";
			}else{
				long succ = juecesService.addNewJuez(entity);
				notDel+=succ+",";
			}
		}
		//Elimina los registros descartados por el usuario.
		List<Jueces> rowsToDelete = juecesService.getAll("FROM Jueces WHERE juzgadoid=" + courtid 
			+" AND juezid NOT IN(" + notDel.replaceAll(".$","") + ")");
		for(int d=0; d<rowsToDelete.size(); d++)
			juecesService.deleteJuez(rowsToDelete.get(d).getJuezid());
	}

	@ResponseBody
	@RequestMapping(value = "/getJudgeById")
	public Object[] getJudgeById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<Jueces> info = juecesService.getAll("FROM Jueces WHERE juezid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/getJudgesByCourtid")
	public Object[] getJudgesByCourtid(HttpServletRequest req, HttpServletResponse res) {
		Long id = req.getParameter("courtid") == null ? 0 : Functions.toLong(req.getParameter("courtid"));
		List<Jueces> info = juecesService.getAll("FROM Jueces WHERE juzgadoid=" + id);
		return new Object[] { info };
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ways")
	public @ResponseBody ModelAndView ways(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "ways");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<Vias> data = viasService.getAll("FROM Vias ORDER BY viaid ASC");
				forModel.put("data", data);
				return new ModelAndView("ways", forModel);
			}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/saveNewWay")
	public @ResponseBody void saveNewWay(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved_support";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					String descr = (req.getParameter("description") == null) ? "" : req.getParameter("description").trim(),
						descren = (req.getParameter("descriptionen") == null) ? "" : req.getParameter("descriptionen").trim();
					if (Functions.isEmpty(descr)) {
						resp = "msg_empty_data";
					}else{
						List<Vias> existsWay = viasService.getAll("FROM Vias WHERE UPPER(via)='"
							+ descr.toUpperCase() + "'");
						if(existsWay.size()<1){
							Vias way = new Vias();
							way.setVia(descr);
							way.setViaen(descren);
							long succ = viasService.addNewVia(way);
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
			System.out.println("Exception in addNewWay(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/getWayById")
	public @ResponseBody Object[] getWayById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<Vias> info = viasService.getAll("FROM Vias WHERE viaid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/getWayByActionId")
	public @ResponseBody Object[] getWayByActionId(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long actionid = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<TipoJuiciosAccion> trialType = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion WHERE accionid="
				+ actionid);
			List<Vias> info = null;
			if(trialType.size()>0)
				if(trialType.get(0).getViaid()>0)
					info = viasService.getAll("FROM Vias WHERE viaid=" + trialType.get(0).getViaid());
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/getWayByTTId")
	public @ResponseBody Object[] getWayByTTid(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long actionid = req.getParameter("actionid") == null ? 0 : Functions.toLong(req.getParameter("actionid"));
			List<TipoJuiciosAccion> trialType = tipojuiciosaccionService.getAll("FROM TipoJuiciosAccion WHERE accionid="
				+ actionid);
			List<Vias> info = null;
			if(trialType.size()>0)
				if(trialType.get(0).getViaid()>0)
					info = viasService.getAll("FROM Vias WHERE viaid=" + trialType.get(0).getViaid());
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/getWayList")
	public @ResponseBody Object[] getWayList() {
		List<Vias> info = viasService.getAll("FROM Vias ORDER BY via ASC");
		return new Object[] { info };
	}

	@RequestMapping(value = "/updateWay")
	public @ResponseBody void updateWay(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved_support";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					Long id = (req.getParameter("viaid") == null) ? 0 : Functions.toLong(req.getParameter("viaid"));
					String descr = (req.getParameter("description") == null) ? "" : req.getParameter("description").trim(),
						descren = (req.getParameter("descriptionen") == null) ? "" : req.getParameter("descriptionen").trim();
					if (Functions.isEmpty(descr)) {
						resp="msg_empty_data";
					}else{
						List<Vias> existsWay = viasService.getAll("FROM Vias WHERE UPPER(via)='"
							+ descr.toUpperCase() + "'");
						if(existsWay.size()<2){
							if(existsWay.size()==1 && existsWay.get(0).getViaid() != id){
								resp="err_duplicated_data";
							}else{
								Vias entity= new Vias();
								entity.setViaid(id);
								entity.setVia(descr);
								entity.setViaen(descren);
								viasService.updateVia(entity);
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
			System.out.println("Exception in updateWay(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteWay")
	public void deleteWay(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				viasService.deleteVia(id);
			}
		}
	}

//***** Tipos de comunicación (ini) ***********
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/commtypes")
	public @ResponseBody ModelAndView commtypes(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "CommTypes");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<CommunicationTypes> comm = commtypeService.getAll("FROM CommunicationTypes ORDER BY commtypeid");
				forModel.put("comm", comm);
				return new ModelAndView("commtypes", forModel);
			}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/saveNewCommTypes")
	public @ResponseBody void saveNewCommTypes(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved_support";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					String descr = (req.getParameter("descr") == null) ? "" : req.getParameter("descr").trim(),
						href = (req.getParameter("href") == null) ? "" : req.getParameter("href").trim(),
						onclicked = (req.getParameter("onclicked") == null) ? "" : req.getParameter("onclicked").trim();

					if (Functions.isEmpty(descr)) {
						resp = "msg_empty_data";
					}else{
						List<CommunicationTypes> existsComm = commtypeService
							.getAll("FROM CommunicationTypes WHERE UPPER(description)='" + descr.toUpperCase() + "'");
						if(existsComm.size()<1){
							CommunicationTypes commtype = new CommunicationTypes();
							commtype.setDescription(descr);
							commtype.setHrefaction(href);
							commtype.setOnclickaction(onclicked);
							long succ = commtypeService.addNewCommType(commtype);
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
			System.out.println("Exception in saveNewCommTypes(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/getCommTypeById")
	public @ResponseBody Object[] getCommTypeById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<CommunicationTypes> info = commtypeService.getAll("FROM CommunicationTypes WHERE commtypeid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCommTypesList")
	public @ResponseBody Object[] getCommTypesList(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			List<CommunicationTypes> info = (List<CommunicationTypes>) dao
				.getFromJA("SELECT commtypeid, description FROM CommunicationTypes ORDER BY commtypeid ASC", CommunicationTypes.class);
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/updateCommType")
	public @ResponseBody void updateCommType(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved_support";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					int id = (req.getParameter("id") == null) ? 0 : Functions.toInt(req.getParameter("id"));
					String descr = (req.getParameter("descr") == null) ? "" : req.getParameter("descr").trim(),
						href = (req.getParameter("hrefaction") == null) ? "" : req.getParameter("hrefaction").trim(),
						onclicked = (req.getParameter("onclicked") == null) ? "" : req.getParameter("onclicked").trim();

					if (Functions.isEmpty(descr)) {
						resp = "msg_empty_data";
					}else{
						List<CommunicationTypes> existsComm = commtypeService
								.getAll("FROM CommunicationTypes WHERE UPPER(description)='" + descr.toUpperCase() + "'");
						if(existsComm.size()<2){
							if(existsComm.size()==1 && existsComm.get(0).getCommtypeid() != id){
								resp="err_duplicated_data";
							}else{
								CommunicationTypes commtype = new CommunicationTypes();
								commtype.setCommtypeid(id);
								commtype.setDescription(descr);
								commtype.setHrefaction(href);
								commtype.setOnclickaction(onclicked);
								commtypeService.updateCommType(commtype);
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
			System.out.println("Exception in updateCommTypes(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteCommTypes")
	public void deleteCommTypes(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if ((userDto.getRole()==ROLE_SYSADMIN||userDto.getRole()==ROLE_CJADMIN)){
					Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id"));
					commtypeService.deleteCommType(id);
				}
			}
		}
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/commlabels")
	public @ResponseBody ModelAndView commlabels(HttpServletRequest req, HttpServletResponse res) {
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
				parameters.put("urlMethod", "commlabels");
				Menu menu = (Menu)dao.sqlHQLEntity("FROM Menu WHERE link like concat(:urlMethod,'%') ", parameters);
				Map<String, Object> forModel = new HashMap<String, Object>();
				forModel.put("menu", menu);

				List<CommunicationLabels> data = commlabelsService.getAll("FROM CommunicationLabels ORDER BY commlabelname ASC");
				forModel.put("data", data);

				return new ModelAndView("commlabels", forModel);
			}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/saveNewCommLabel")
	public @ResponseBody void saveNewCommLabel(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				String newcommlabel = (req.getParameter("description") == null) ? "" : req.getParameter("description").trim();
				if (Functions.isEmpty(newcommlabel)) {
					resp = "msg_empty_data";
				}else{
					List<CommunicationLabels> existsCommLabel = commlabelsService.getAll("FROM CommunicationLabels WHERE UPPER(commlabelname)='"
						+ newcommlabel.toUpperCase() + "'");
					if(existsCommLabel.size()<1){
						CommunicationLabels commlabel = new CommunicationLabels();
						commlabel.setCommlabelname(newcommlabel);
						long succ = commlabelsService.addNewCommLabel(commlabel);
						resp=(succ>0)?"msg_data_saved":"err_record_no_saved";
					}else{
						resp="err_duplicated_data";
					}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in addNewCommLabel(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/getCommLabelById")
	public @ResponseBody Object[] getCommLabelById(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
			List<CommunicationLabels> info = commlabelsService.getAll("FROM CommunicationLabels WHERE commlabelid=" + id);
			return new Object[] { info };
		}
		return null;
	}

	@RequestMapping(value = "/getCommLabelList")
	public @ResponseBody Object[] getCommLabelList(HttpServletRequest req, HttpServletResponse res) {
		List<CommunicationLabels> info = commlabelsService.getAll("FROM CommunicationLabels ORDER BY commlabelid ASC");
		return new Object[] { info };
	}

	@RequestMapping(value = "/updateCommLabel")
	public @ResponseBody void updateCommLabel(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		String resp = "err_record_no_saved";
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = (req.getParameter("id") == null) ? 0 : Functions.toLong(req.getParameter("id"));
				String descr = (req.getParameter("description") == null) ? "" : req.getParameter("description").trim();
				if (Functions.isEmpty(descr)) {
					resp="msg_empty_data";
				}else{
					List<CommunicationLabels> existsCommLabel = commlabelsService.getAll("FROM CommunicationLabels WHERE UPPER(commlabelname)='"
						+ descr.toUpperCase() + "'");
					if(existsCommLabel.size()<2)
						if(existsCommLabel.size()==1 && existsCommLabel.get(0).getCommlabelid() != id){
							resp="err_duplicated_data";
						}else{
							CommunicationLabels commlabel= new CommunicationLabels();
							commlabel.setCommlabelid(id);
							commlabel.setCommlabelname(descr);
							commlabelsService.updateCommLabel(commlabel);
							resp = "msg_data_saved";
						}
				}
			}
		try {
			res.getWriter().write(resp);
		} catch (IOException ex) {
			System.out.println("Exception in updateCommLabel(): " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/deleteCommLabel")
	public void deleteCommLabel(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null) {
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				Long id = req.getParameter("id") == null ? 0 : Functions.toLong(req.getParameter("id"));
				commlabelsService.deleteCommLabel(id);
			}
		}
	}
	// ***** Tipos de comunicación (fin) ***********
}