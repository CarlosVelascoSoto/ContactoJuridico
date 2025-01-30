package com.aj.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.aj.model.Roles;

@Repository("rolesDAO")
public class RolesDAOImpl extends AbstractDAO implements RolesDAO{
	private static final Logger logger=Logger.getLogger(RolesDAOImpl.class);

	@Override
	public List<Roles> getAll(String query){
		//Este proceso por omisión tiene un "Select", "query" puede contener el resto de la consulta o un "select" completo
		List<Roles> roles=new ArrayList<>();
		try{Query queryResult=getSession().createQuery(query);
			java.util.List<Roles> allRoles=queryResult.list();
			for (int i=0; i<allRoles.size(); i++){
				Roles roleReg=(Roles) allRoles.get(i);
				roles.add(roleReg);
			}
		}catch (HibernateException e){e.printStackTrace();}
		return roles;
	}

	@Override
	public Integer addNewRole(Roles rolename){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(rolename);
		}catch (HibernateException e){e.printStackTrace();}
		return (int) retConfirm;
	}

	@Override
	public Roles getRoleById(Long roleId){
		Roles getrole=null;
		try{getrole=(Roles) getSession().get(Roles.class, roleId);
		}catch (Exception e){logger.error("Exception in getRoleDetail()::"+e.getMessage());}
		return getrole;
	}

	@Override
	public void updateRoleDetails(Roles roleId){
		try{getSession().update(roleId);
		}catch (Exception e){logger.error("Exception in updateRoleDetails() :: "+e.getMessage());}
	}

	@Override
	public void deleteRole(Long roleId){
		try{Object o=getSession().load(Roles.class, roleId);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteRole(): "+e.getMessage());}
	}
}