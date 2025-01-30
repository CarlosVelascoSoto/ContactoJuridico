package com.aj.controller;

/*Para leer archivos*/
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aj.model.Estados;
import com.aj.model.Menu;
import com.aj.model.Menuprivileges;
import com.aj.model.Privileges;
import com.aj.model.Users;
import com.aj.service.AccessDbJAService;
import com.aj.service.EstadosService;
import com.aj.service.LoginService;
import com.aj.service.PrivilegesService;
import com.aj.service.UserService;
import com.aj.utility.Functions;
import com.aj.utility.IpAndMAC;
import com.aj.utility.UserDTO;

@Controller
@ComponentScan("com.aj")
public class LoginController {
	@Autowired
	public LoginService loginService;

	@Autowired
	PrivilegesService privilegesService;

	@Autowired
	UserService userService;

	@Autowired
	public EstadosService estadosService;

	@SuppressWarnings("rawtypes")
	@Autowired
	AccessDbJAService dao;

	static Properties propiedades;
	
	@RequestMapping(value = "/")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response, Locale locale, Model model)
			throws IOException {
		try {
			HttpSession sess = request.getSession(false);
			//String path = request.getServletPath();
			if (sess != null) {
				UserDTO userDto = (UserDTO) sess.getAttribute("UserDTO");
				if (sess.getAttribute("isLogin") != null && (((String) sess.getAttribute("isLogin")).equals("yes"))
						&& userDto != null) {
					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP1.1.
					response.setHeader("Pragma", "no-cache");// HTTP 1.0.
					response.setDateHeader("Expires", 0);
					response.setContentType("text/html; charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					return new ModelAndView("redirect:home");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Flags Test
		if (propiedades == null) {
			propiedades = new Properties();
			try {
				propiedades.load(
						Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> flags = new HashMap<String, Object>();
		flags.put("flag1", propiedades.get("jdbc.url").toString());
		flags.put("flag2", propiedades.get("savePath").toString());
		return new ModelAndView("login", flags);//Fin de Flags Test

		//return new ModelAndView("login");
	}

	//@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public @ResponseBody UserDTO processForm(HttpServletRequest request, HttpServletResponse response) {
		String UserLogIn = request.getParameter("userLogin").trim();
		String Password = request.getParameter("password").trim();
		String check = request.getParameter("remMe").trim();
		UserDTO user = loginService.checkLogin(UserLogIn, Password);
		if (user != null) {
			HttpSession sess = request.getSession(false);
			List<Users> userlang = userService.getAll("from Users where username='" + UserLogIn + "'");
			String lang = userlang.get(0).getLanguage(), pic = userlang.get(0).getPhoto_name();
			if (Functions.isEmpty(lang))
				lang = "es";
			sess.setAttribute("language", lang);
			user.setLanguage(lang);
			pic = (Functions.isEmpty(pic))?"resources/assets/images/users/user-profile.png"
				:(pic).replaceAll("(?:.*)(doctos/.*)+","$1");
			user.setPhoto_name(pic);
			user.setAddress(userlang.get(0).getAddress());
			user.setZipcode(userlang.get(0).getZipcode());
			user.setPhone(userlang.get(0).getPhone());
			user.setCellphone(userlang.get(0).getCellphone());
			sess.setAttribute("picprofile", pic);
			sess.setAttribute("firstname", userlang.get(0).getFirst_name());
			sess.setAttribute("lastname", userlang.get(0).getLast_name());

			Cookie cookUser = new Cookie("userName", null);
			cookUser.setPath(StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
			cookUser.setMaxAge(0);
			response.addCookie(cookUser);
			Cookie cookPass = new Cookie("passWord", null);
			cookPass.setPath(StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
			cookPass.setMaxAge(0);
			response.addCookie(cookPass);
			request.getSession().setAttribute("isLogin", "yes");
			request.getSession().setAttribute("UserDTO", user);
			// request.getSession().setAttribute("cityMap",
			// UtilData.getCityMap());
			request.getSession().setMaxInactiveInterval(20 * 60);
			String cookiePath = "/";
			if (check != null && check.trim().equalsIgnoreCase("true")) {
				cookUser = new Cookie("userName", UserLogIn);
				cookUser.setPath(StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
				cookUser.setMaxAge(24 * 60 * 60);
				response.addCookie(cookUser);
				cookPass = new Cookie("passWord", Password);
				cookPass.setPath(StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
				cookPass.setMaxAge(24 * 60 * 60);
				response.addCookie(cookPass);
			} // response.getWriter().write("exists");
			String ip = IpAndMAC.getIpAddr(request);
			String mac = IpAndMAC.getMac(ip);
			Map<String, String> mp = IpAndMAC.getRequestHeadersInMap(request);
			Iterator it = mp.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				it.remove();// avoids a ConcurrentModificationException
			} // configura privilegios de usuario sobre el módulo
			String userRoleId = Integer.toString(user.getRole());
			loadMenus(request, response, userRoleId);
		} else {
			try {
				response.getWriter().write("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public void loadMenus(HttpServletRequest request, HttpServletResponse response, String roleid) {
		// Configura los privilegios de usuario sobre el módulo actual
		HashMap<String, Object> parameters = new HashMap<>();
		List<Menu> dicmenus = dao.sqlHQL("FROM Menu p ",parameters);
		request.getSession().setAttribute("dicmenus", dicmenus);
		List<Privileges> dicprivileges = dao.sqlHQL("FROM Privileges p ",parameters);
		request.getSession().setAttribute("dicpermisos", dicprivileges);

		parameters.put("roleid", Functions.toInt(roleid));
		ArrayList<String> permisosUser = new ArrayList<>();
		//HashMap<String, Integer> permisosUser =new HashMap<>();
		for(Menu m:dicmenus){
			parameters.put("menuid", m.getMenuid());
			List<Menuprivileges> lpermisos = dao.sqlHQL("FROM Menuprivileges p WHERE p.roleid=:roleid and p.menuid=:menuid", parameters);
			for (Menuprivileges mp : lpermisos) {
				//permisosUser.put(m.getMenuid()+"_"+ mp.getPrivilegesid(),1);
				permisosUser.add("."+m.getMenuid()+"_"+ mp.getPrivilegesid());
			}
		}
		
		request.getSession().setAttribute("arrayPermisos", permisosUser);

//TODO menus en prototipo (no mostrar hasta ser liberados
		String notThis = " AND link NOT IN(' emailsettings', 'emailsettings') ";
		
		// menu superior
		parameters.clear();
		List<Menu> lmenusup = dao.sqlHQL("FROM Menu p WHERE p.menuparentid=0 and tipomenu=1 " + notThis + " order by orden",
				parameters);
		request.getSession().setAttribute("pMenuSup", lmenusup);
		// menu usuario
		List<Menu> lmenuuser = dao.sqlHQL("FROM Menu p WHERE p.menuparentid=0 and tipomenu=3 " + notThis + " order by orden",
				parameters);
		request.getSession().setAttribute("pMenuUser", lmenuuser);
		
		// menu lateral
		String queryMenuRecursive = "WITH RECURSIVE tmp AS ( "
				+ "SELECT menuparentid, menuparentid as menupadre, menuid, menu, icon, link, tipomenu, orden, array[orden] as path "
				+ "FROM   menu WHERE  tipomenu = 2 and menuid = (Select min(menuid) from menu) " + "UNION  ALL "
				+ "SELECT p.menuid, menuparentid as menupadre, p.menuid, p.menu, p.icon, p.link, p.tipomenu, p.orden, path || p.orden "
				+ "FROM   tmp " + "JOIN   menu p USING (menuparentid) "
				+ "where p.tipomenu = 2 ) SELECT menuid, menupadre as menuparentid, menu,icon, link, tipomenu, orden FROM tmp order by path";
		List<Menu> lmenus = dao.getNative(queryMenuRecursive, Menu.class);
		lmenus.remove(0);
		
		request.getSession().setAttribute("pMenuLat", lmenus);
	}

	@RequestMapping(value = "/validateccount")
	public @ResponseBody ModelAndView validateccount(HttpServletRequest req, HttpServletResponse res) {
		res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");// HTTP_1.1.
		res.setHeader("Pragma", "no-cache");// HTTP1.0.
		res.setDateHeader("Expires", 0);
		res.setContentType("text/html; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		Map<String, Object> forModel = new HashMap<String, Object>();
		List<Estados> stateList = estadosService.getAll("FROM Ciudades ORDER BY ciudad ASC");

		forModel.put("statelist", stateList);
		return new ModelAndView("createaccount", forModel);
	}
}