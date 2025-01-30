package com.aj.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.aj.model.Company;
import com.aj.model.Users;

@Repository("userDAO")
public class UserDAOImpl extends AbstractDAO implements UserDAO{
	private static final Logger logger=Logger.getLogger(UserDAOImpl.class);

	@Override
	public List<Users> getAllUserList(){
		List<Users> users=new ArrayList<>();
		try{
			Query queryResult=getSession().createQuery("from Users");
			java.util.List<Users> allUsers=queryResult.list();
			for (int i=0; i < allUsers.size(); i++){
				Users userReg=(Users) allUsers.get(i);
				users.add(userReg);
			}
		}catch (HibernateException e){
			e.printStackTrace();
		}return users;
	}

	@Override
	public long addNewUser(Users user){
		long retConfirm=0;
		try{
			retConfirm=(long) getSession().save(user);
		}catch (HibernateException e){
			e.printStackTrace();
		}return retConfirm;
	}

	@Override
	public Users getUserById(long userId){
		Users getuser=null;
		try{
			getuser=(Users) getSession().get(Users.class, userId);
		}catch (Exception e){
			logger.error("Exception in getUserDetail()::" + e.getMessage());
		}return getuser;
	}

	@Override
	public void updateUserDetails(Users user){
		try{
			getSession().update(user);
		}catch (Exception e){
			logger.error("Exception in updateUserDetails() :: " + e.getMessage());
		}
	}

	@Override
	public void deleteUser(long userId){
		try{
			Object o=getSession().load(Users.class, userId);
			getSession().delete(o);
		}catch (Exception e){
			logger.error("Exception in deleteUser(): " + e.getMessage());
		}
	}

	@Override
	public Users getProfile(String email){
		Users user=null;
		try{
			user=(Users) getSession().createQuery("from Users u where u.email=:email").setString("email", email)
					.uniqueResult();
		}catch (Exception e){
			e.printStackTrace();
			logger.error("Exception in getProfile()::" + e.getMessage());
		}return user;
	}

	@Override
	public List<Users> getAll(String query){
		//Este proceso realiza por default un "SELECT * ", 'query' debe contener el resto de la consulta, pero también se puede colocar un "select columnas..." y el resto de query si es necesario  
		try{Query queryResult=getSession().createQuery(query);
			java.util.List<Users> allUsers=queryResult.list();
			return allUsers;
		}catch(HibernateException e){e.printStackTrace();}
		return null;
	}

	@Override
	public List<Company> getAllCompanyList(){
		List<Company> companies=new ArrayList<>();
		try{
			Query queryResult=getSession().createQuery("FROM Company ORDER BY name asc");
			java.util.List<Company> allCompanies=queryResult.list();
			for (int i=0; i < allCompanies.size(); i++){
				Company row=(Company) allCompanies.get(i);
				companies.add(row);
			}
		}catch (HibernateException e){
			e.printStackTrace();
		}return companies;
	}

	@Override
	public Integer addNewCompany(Company company){
		Integer retConfirm=0;
		try{
			retConfirm=(Integer) getSession().save(company);
		}catch (HibernateException e){
			e.printStackTrace();
		}return retConfirm;
	}

}