package com.aj.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.aj.model.Amparos;
import com.aj.model.Apelaciones;
import com.aj.model.Clients;
import com.aj.model.Juicios;
import com.aj.model.Movimientos;
import com.aj.model.Recursos;
import com.aj.model.UploadedFile;
import com.aj.model.Uploadfiles;
import com.aj.model.Users;
import com.aj.service.AccessDbJAService;
import com.aj.service.AmparosService;
import com.aj.service.ApelacionesService;
import com.aj.service.ClientsService;
import com.aj.service.JuiciosService;
import com.aj.service.MovimientosService;
import com.aj.service.RecursosService;
import com.aj.utility.Functions;
import com.aj.utility.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class UploadFilesController {
	Properties propiedades;
	
	@Autowired
	public AccessDbJAService dao;

	@Autowired
	public ClientsService clientsService;

	@Autowired
	public AmparosService amparosService;

	@Autowired
	public JuiciosService juiciosService; 

	@Autowired
	public ApelacionesService apelacionesService;

	@Autowired
	public RecursosService recursosService;

	@Autowired
	public MovimientosService movimientosService;
	
	public static final int ROLE_SYSADMIN= 1, ROLE_CJADMIN = 2, ROLE_FIRMADMIN=3;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/previewuploadfile", method = RequestMethod.POST)
	public @ResponseBody String previewuploaded(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Integer id = Functions.toInt(request.getParameter("id"));
		Integer type = Functions.toInt(request.getParameter("type"));
		JsonArray jsonArray=new JsonArray();
		JsonObject json = null;
		HashMap<String, Object> parameters = new HashMap<>();
		
		if(type<0){
			HttpSession sess = request.getSession(false);
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			String query ="";
			List<Users> forPicture = null;
			if(type== -9){
				query="From Clientes where clientid="+id;
				/*if(userDto.getRole()!=ROLE_SYSADMIN && userDto.getRole()!=ROLE_CJADMIN)
					query+=" AND companyid="+userDto.getCompanyid();*/
			}else if(type== -10){
				query="From Users where id="+id;
				if(userDto.getRole()!=ROLE_SYSADMIN && userDto.getRole()!=ROLE_CJADMIN)
					query+=" AND companyid="+userDto.getCompanyid();
			}
			forPicture = dao.sqlHQL(query, parameters);
			String photo = forPicture.get(0).getPhoto_name();
			if(!Functions.isEmpty(photo))
				if(photo.indexOf("doctos")>=0){
					File tmpfile = null;
					tmpfile = new File (photo);
					json = new JsonObject();
					json.addProperty("name", tmpfile.getName());
					json.addProperty("size", tmpfile.length());
					json.addProperty("location", tmpfile.getPath().substring(tmpfile.getPath().indexOf("doctos")));
					jsonArray.add(json);
			}
		}else{
			List<Uploadfiles> listFiles = null;
			parameters.put("idregister", id);
			parameters.put("catalogtype", type);
			listFiles = dao.sqlHQL("From Uploadfiles where idregister = :idregister and catalogtype=:catalogtype", parameters);
			
			File tmpfile = null;
			for(Uploadfiles uploafile : listFiles){
				tmpfile = new File (uploafile.getPath());
				json = new JsonObject();
				json.addProperty("name", tmpfile.getName());
				json.addProperty("size", tmpfile.length());
				json.addProperty("location", tmpfile.getPath().substring(tmpfile.getPath().indexOf("doctos")));
				jsonArray.add(json);
			}
		}
		String result = new Gson().toJson(jsonArray);
		return result;
	}
	
	@RequestMapping(value = "/deletefile", method = RequestMethod.POST)
	public @ResponseBody String deletefile(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Integer id = Functions.toInt(request.getParameter("id"));
		Integer type = Functions.toInt(request.getParameter("type"));
		String file = Functions.toStr(request.getParameter("file"));
				
		List<Uploadfiles> listFiles = null;
		String query = "FROM Uploadfiles WHERE idregister = :idregister and catalogtype=:catalogtype and filename = :filename"; 
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put("idregister", id);
		parameters.put("catalogtype", type);
		parameters.put("filename", file);
		listFiles = dao.sqlHQL(query, parameters);
				
		// Si listFiles es nulo, asumirá que un archivo de clientes y cargará una imagen
		if(type== -9){			//Clientes
			List<Clients> data = clientsService.getAll("FROM Clients WHERE clientid=" + id);
			File tmpfile = new File (data.get(0).getPhoto());
			tmpfile.delete();
			return null;
		}else if(type== -10){	//Usuarios
			List<Users> data = (List<Users>) dao.getFromJA("SELECT photo_name FROM Users WHERE id=" + id, Users.class);
			File tmpfile = new File (data.get(0).getPhoto_name());
			tmpfile.delete();
			return null;
		}else{
			JsonArray jsonArray=new JsonArray();
			JsonObject json = null;
			File tmpfile = null;
			Map<String, Object> valores=null;
			query = "delete from uploadfiles where uploadfileid=:uploadfileid";
			for(Uploadfiles uploafile : listFiles){
				tmpfile = new File (uploafile.getPath());
				tmpfile.delete();
				
				valores = new HashMap<>();
				valores.put("uploadfileid", uploafile.getUploadfileid());
				int rows = dao.directJAUpdate(query, valores);
				
				json = new JsonObject();
				json.addProperty("name", tmpfile.getName());
				json.addProperty("delete", (rows>0?true:false));
				jsonArray.add(json);
			}
			
			String result = new Gson().toJson(jsonArray);
			return result;
		}
	}
	
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public @ResponseBody List<UploadedFile> upload(MultipartHttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String pathid = Functions.getCookieJsesion(request);		
		Map<String, MultipartFile> fileMap = request.getFileMap();
		// Maintain a list to send back the files info. to the client side
		List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();
		if (pathid != null) {
			String newName = "";
			// Iterate through the map
			for (MultipartFile multipartFile : fileMap.values()) {
				// Save the file to local disk
				newName = saveFileToLocalDisk(multipartFile, pathid);
				UploadedFile fileInfo = getUploadedFileInfo(multipartFile, newName, pathid);
				// adding the file info to the list
				uploadedFiles.add(fileInfo);
			}
		}
		return uploadedFiles;
	}

	private String saveFileToLocalDisk(MultipartFile multipartFile, String pathid)
			throws IOException, FileNotFoundException {
		Date hoy = new Date();
		//String newName = hoy.getTime() + "__" + multipartFile.getOriginalFilename();
		String newName = multipartFile.getOriginalFilename();
		String outputFileName = getOutputFilename(newName, pathid);
		//System.out.println("Ruta destino tmp: " + outputFileName);
		FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(outputFileName));
		//StreamUtils.copy(multipartFile.getInputStream(), new FileOutputStream( outputFileName));
		return newName;
	}

	private String getOutputFilename(String nombreDinamico, String pathid) {
		String pathRoot = Functions.getRootPath();
		return Functions.addPath(pathRoot, pathid, true) + nombreDinamico;
	}

	private UploadedFile getUploadedFileInfo(MultipartFile multipartFile, String nombreUpload, String pathid)
			throws IOException {
		String pathRoot = Functions.getRootPath();
		UploadedFile fileInfo = new UploadedFile();
		fileInfo.setName(nombreUpload);
		fileInfo.setNameOriginal(multipartFile.getOriginalFilename());
		fileInfo.setSize(multipartFile.getSize());
		fileInfo.setType(multipartFile.getContentType());
		fileInfo.setLocation(Functions.addPath(pathRoot, pathid, true));

		return fileInfo;
	}

	@ResponseBody
	@RequestMapping(value = "/fixurlpath")
	public ModelAndView fixurlpath(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		if (sess != null)
			if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))) {
				res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				res.setHeader("Pragma", "no-cache");// HTTP 1.0.
				res.setDateHeader("Expires", 0);
				res.setContentType("text/html; charset=utf-8");
				res.setCharacterEncoding("utf-8");

				Map<String, String> forModel = new HashMap<String, String>();
				String s=FileSystems.getDefault().getSeparator();

				String setPath = Functions.getRootPath();
				File directory = new File(setPath);
				if(directory.exists())
					setPath = directory.getAbsolutePath()+s+"doctos"+s;
				forModel.put("dbPath",setPath);
				return new ModelAndView("fixurlpath",forModel);
			}
		return new ModelAndView("login");
	}


	@ResponseBody
	@RequestMapping(value = "/searchUrltoFix")
	public Map<String, Object> searchUrltoFix(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if (sess != null) {
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			if (userDto.getRole()!=ROLE_SYSADMIN)
				return data;//Acceso autorizado sólo para SysAdmin
			String baseUrl = (req.getParameter("baseUrl") == null) ? "" : req.getParameter("baseUrl").trim();

			List<Uploadfiles> allRows = dao.sqlHQL("FROM Uploadfiles WHERE path <>'' AND path NOT LIKE '"
				+ baseUrl + "%' ORDER BY catalogtype ASC, path ASC", null);
			data.put("urls", allRows);
			return data;
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/searchUrlInfo")
	public Map<String, Object> searchUrlInfo(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		Map<String, Object> data = new HashMap<String, Object>();
		if (sess != null) {
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			if (userDto.getRole()!=ROLE_SYSADMIN)
				return data;//Acceso autorizado sólo para SysAdmin
			int idregister = (req.getParameter("id") == null) ? 0 : Functions.toInt(req.getParameter("id"));
			String[] catTableType = {"","Juicios","Apelaciones","Amparos","Amparos","Recursos","Clients","Movimientos"},
					catKeyType = {"","juicioid","apelacionid","amparoid","amparoid","recursoid","clientid","movimientoid"};
					
			List<Uploadfiles> allRows = dao.sqlHQL("FROM Uploadfiles WHERE idregister="+idregister, null);
			HashMap<String, Object> info = new HashMap<>();
			
			int catType = allRows.get(0).getCatalogtype();
			String query= "FROM " + catTableType[catType] + " WHERE " + catKeyType[catType] + "="+idregister,
				exp = "", otros = "";
			if(catType==1){
				List<Juicios> tmp = juiciosService.getAll(query);
				if(tmp.size()<1)exp="El expediente de origen ya no existe.";
				else exp=tmp.get(0).getJuicio();
			}else if(catType==2){
				List<Apelaciones> tmp = apelacionesService.getAll(query);
				if(tmp.size()<1)exp="El expediente de origen ya no existe.";
				else exp=tmp.get(0).getToca();
			}else if(catType==3 || catType==4){
				List<Amparos> tmp = amparosService.getAll(query);
				if(tmp.size()<1){
					exp="El expediente de origen ya no existe.";
				}else{
					exp = tmp.get(0).getAmparo();
					otros = tmp.get(0).getAmparotipo()==1?" (Amparo directo)":" (Amparo indirecto)";
				}
			}else if(catType==5){
				List<Recursos> tmp = recursosService.getAll(query);
				if(tmp.size()<1)exp="El expediente de origen ya no existe.";
				else exp=tmp.get(0).getRecurso();
			}else if(catType==6){
				List<Clients> tmp = clientsService.getAll(query);
				if(tmp.size()<1)exp="El expediente de origen ya no existe.";
				else exp=tmp.get(0).getClient();
			}else if(catType==7){
				List<Movimientos> tmp = movimientosService.getAll(query);
				if(tmp.size()<1){
					exp="El expediente de origen ya no existe.";
					otros="'"+query+"'";
				}else{
					otros = " -> Movimiento: " + tmp.get(0).getMovimiento();
					if(!Functions.isEmpty(tmp.get(0).getJuicioid())){
						List<Juicios> tmpMov = juiciosService.getAll("FROM Juicios WHERE juicioid="+tmp.get(0).getJuicioid());
						if(tmpMov.size()<1){
							exp="El juicio de origen ya no existe. \n";
							otros="'"+query+"'";
						}else{
							exp="Derivado del juicio '" + tmpMov.get(0).getJuicio() + "'";
						}
					}else if(!Functions.isEmpty(tmp.get(0).getApelacionid())){
						List<Apelaciones> tmpMov = apelacionesService.getAll("FROM Apelaciones WHERE apelacionid="+tmp.get(0).getApelacionid());
						if(tmpMov.size()<1){
							exp="La apelación de origen ya no existe.";
							otros="'"+query+"'";
						}else{
							exp="Derivado de la apelación '" + tmpMov.get(0).getToca() + "'";
						}
					}else if(!Functions.isEmpty(tmp.get(0).getAmparoid())){
						List<Amparos> tmpMov = amparosService.getAll("FROM Amparos WHERE amparoid="+tmp.get(0).getAmparoid());
						if(tmpMov.size()<1){
							exp="El amparo de origen ya no existe.";
							otros="'"+query+"'";
						}else{
							exp="Derivado del amparo " + (tmpMov.get(0).getAmparotipo()==1?"directo ":"indirecto ")
								+ "' " + tmpMov.get(0).getAmparo() + "'";
						}
					}else if(catType==5){
						List<Recursos> tmpMov = recursosService.getAll("FROM Recursos WHERE recursoid="+tmp.get(0).getRecursoid());
						if(tmpMov.size()<1){
							exp="El recurso de origen ya no existe.";
							otros="'"+query+"'";
						}else{
							exp="Derivado del recurso '" + tmpMov.get(0).getRecurso() + "'";
						}
					}
				}
			}
			info.put("origen", catTableType[catType]);
			info.put("expediente", exp);
			info.put("otros", otros);
			return info;
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/changesToFixUrl")
	public HashMap<String, Object> changesToFixUrl(HttpServletRequest req, HttpServletResponse res) {
		HttpSession sess = req.getSession(false);
		HashMap<String, Object> results = new HashMap<String, Object>();
		if (sess != null) {
			UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
			String idsToFix = (req.getParameter("ids") == null) ? "" : req.getParameter("ids").trim(),
				baseUrl = (req.getParameter("baseUrl") == null) ? "" : req.getParameter("baseUrl").trim(),
				forcebase = (req.getParameter("forcebase") == null) ? "" : req.getParameter("forcebase").trim(),
				actions = "", s = FileSystems.getDefault().getSeparator();
			if (userDto.getRole()!=ROLE_SYSADMIN||Functions.isEmpty(idsToFix))
				return null;
			baseUrl+=s;
			String[] allIds = idsToFix.split(",");
			Uploadfiles entidad = null;
			for (int i=0; i<allIds.length;i++) {
				int id = Functions.toInt(allIds[i]);
				List<Uploadfiles> ulf = dao.sqlHQL("FROM Uploadfiles WHERE uploadfileid="+id, null);
				if(ulf.isEmpty()||ulf==null){
					actions+=id+"=0|";
					continue;
				}
				String urlOnDB=ulf.get(0).getPath();
				urlOnDB=urlOnDB.replaceAll(s+s,s);
				String[] fileName = {ulf.get(0).getFilename()},originalPath = urlOnDB.split(s+"doctos"+s);
				String oldPath = realPath(urlOnDB, ulf.get(0).getFilename()),targetPath="",destinationPath = "";

				String newFullName = fileName[0].replaceAll("[^\\p{ASCII}]", "");
				String newfilename = fileName[0].replaceAll("(.*)?(\\..*)+$","$1");
				if(newfilename.length()<1)
					newFullName="Sin nombre"+newFullName;

				if(originalPath.length==1)
					targetPath=originalPath[0].replaceAll(ulf.get(0).getFilename(),"");
				else
					targetPath=originalPath[1].replaceAll(ulf.get(0).getFilename(),"");//Conserva id original
				if(Functions.isEmpty(oldPath)){
					int succ = juiciosService.deleteFixUrl(id);
					if(succ!=0)
						actions+=id+"=1|";
					else
						actions+=id+"=2|";
					File directory = new File(oldPath);
					directory.delete();
					continue;
				}
				if(forcebase.equals("true")){
					String[] forcedUrl = baseUrl.split(s+"doctos"+s);
					destinationPath=forcedUrl[0] + s + "doctos" + s + targetPath;
				}else{
					destinationPath = Functions.addPath(Functions.getRootPath(),
						"doctos" + s + targetPath, true);//Crea la ruta
				}
				String[] nfn = newFullName.split("\\?");
				File paths[] = Functions.moveFiles(oldPath, destinationPath, nfn);
				File directory = new File(oldPath);
				directory.delete(); //Elimina la ruta anterior sólo si esta vacía.

				entidad = new Uploadfiles();
				entidad.setUploadfileid(id);
				entidad.setPath((paths[0].getAbsolutePath()).replaceAll(fileName[0],newFullName));
				entidad.setCatalogtype(ulf.get(0).getCatalogtype());
				entidad.setFilename(newFullName);//ulf.get(0).getFilename());
				entidad.setIdregister(ulf.get(0).getIdregister());
				int succ = juiciosService.updateFixUrl(entidad);
				if(succ==1)
					actions+=id+"=3|";
				else
					actions+=id+"=4|";
			}
			results.put("actions", actions.replaceAll(".$",""));
			return results;
		}
		return null;
	}

	/** Rastrea la ruta real en disco duro.
 	 @param fullpathwithfile	Ruta completa con el nombre de archivo.
	 @param filename			Nombre de archivo.
	 @return					Cadena con ruta completa.
	 @throws					Cadena vacía.	 */
	static String realPath(String fullpathwithfile, String filename){
		String s=FileSystems.getDefault().getSeparator(),oldPath = "";
		String[] ulrParts = fullpathwithfile.split(s);
		int solving=0;
		while (true){
			int x2=1;
			do{
				String fileTest = "";
				for(int u=solving;u<ulrParts.length;u++)
					if(x2==1)fileTest+=s+ulrParts[u];
					else fileTest+=ulrParts[u]+s;
				if(x2==2)fileTest=fileTest.replaceAll(".$","");
				File file0 = new File(fileTest);
		        if(file0.exists()){
		        	oldPath=fileTest.replaceAll(filename,"");// Path encontrado
		        	solving=ulrParts.length;
		        	break;
		        }
		        if(x2==1){
		        	x2++;
		        }else if(x2==2){
		        	solving++;
		        	x2++;
		        }
			}while(x2<3);
	        if(solving>=ulrParts.length)break;
		};
		return oldPath;
	}
}