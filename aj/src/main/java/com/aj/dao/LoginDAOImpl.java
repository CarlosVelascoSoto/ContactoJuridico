package com.aj.dao;

import org.springframework.stereotype.Repository;

import com.aj.controller.LoginController;
import com.aj.model.Users;
import com.aj.utility.ScriptCommon;
import com.aj.utility.UserDTO;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import java.util.List;

@Repository("loginDAO")
public class LoginDAOImpl extends AbstractDAO implements LoginDAO{
	private static final Logger logger=Logger.getLogger(LoginController.class);

	@Override
	public UserDTO checkLogin(String userName, String userPassword){
		UserDTO userDto=null;
		boolean userFound=false;
		String password=ScriptCommon.encode(userPassword.trim());
		String SQL_QUERY="FROM Users u WHERE u.username= :param1 AND u.password=:param2 AND status=1";
		Query query=getSession().createQuery(SQL_QUERY);
		query.setParameter("param1", userName);
		query.setParameter("param2", password);
		// query.setParameter(0,userName); query.setParameter(1,userPassword);
		List<Users> list=query.list();
		if((list!=null)&&(list.size()>0)){
			Users user=list.get(0);
			userDto=new UserDTO();
			userDto.setId(user.getId());
			userDto.setEmail(user.getEmail());
			userDto.setRole(user.getRole());
			userDto.setCountAdminUsers(countAdminUsers());
			userDto.setCountSalesManager(countSalesManager());
			userDto.setCountSalesPerson(countSalesPerson());
			userDto.setCountUsers(countUsers());
			userDto.setCountCompany(countCompany());
			userDto.setLanguage(user.getLanguage());
			userDto.setFirst_name(user.getFirst_name());
			userDto.setLast_name(user.getLast_name());
			userDto.setCurrency(user.getCurrency());
			userDto.setCompanyid(user.getCompanyid());
			userDto.setAddress(user.getAddress());
			userDto.setPhone(user.getPhone());
			userDto.setPhoto_name(user.getPhoto_name());
			userDto.setCellphone(user.getCellphone());
			userDto.setUsertype(user.getUsertype());
			userDto.setLinkedclientid(user.getLinkedclientid());
			userFound=true;			
		}else{logger.error("validateUser() "+userName+" User Not Exists");}
		return userDto;
	}

	public int countAdminUsers(){
		return((Long) getSession().createQuery("SELECT count(id) AS COUNT FROM Users WHERE role=1").uniqueResult()).intValue();
	}

	public int countSalesPerson(){
		return((Long) getSession().createQuery("SELECT count(id) AS COUNT FROM Users WHERE role=2").uniqueResult()).intValue();
	}

	public int countSalesManager(){
		return((Long) getSession().createQuery("SELECT count(id) AS COUNT FROM Users WHERE role=3").uniqueResult()).intValue();
	}

	public int countUsers(){
		return((Long) getSession().createQuery("SELECT count(id) AS COUNT FROM Users ").uniqueResult()).intValue();
	}

	public int countCompany(){
		return((Long) getSession().createQuery("SELECT count(id) AS COUNT FROM Users WHERE role=4").uniqueResult()).intValue();
	}
}