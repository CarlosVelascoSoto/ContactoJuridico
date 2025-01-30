package com.aj.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.aj.model.LeadProspect;
import com.aj.model.Privileges;

@Repository("privilegesDAO")
public class PrivilegesDAOImpl extends AbstractDAO implements PrivilegesDAO{
	private static final Logger logger=Logger.getLogger(PrivilegesDAOImpl.class);

	@Override
	public List<Privileges> getAll(String query){
		//Este proceso realiza por default un "SELECT * ", 'query' debe contener el resto de la consulta,
		//pero también se puede colocar un "select columnas..." y el resto de query si es necesario  
		List<Privileges> privileges=new ArrayList<>();
		try{Query queryResult=getSession().createQuery(query);
			java.util.List<Privileges> allPrivileges=queryResult.list();
			for (int i=0; i < allPrivileges.size(); i++){
				Privileges privilegesReg=(Privileges) allPrivileges.get(i);
				privileges.add(privilegesReg);
			}
		}catch(HibernateException e){e.printStackTrace();}
		return privileges;
	}

	@Override
	public Integer addNewPrivilege(Privileges privileges){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(privileges);
		}catch(HibernateException e){e.printStackTrace();}
		return (int) retConfirm;
	}

	@Override
	public Privileges getPrivilegeById(Long privilegesid){
		Privileges getPrivilege=null;
		try{getPrivilege=(Privileges) getSession().get(Privileges.class, privilegesid);
		}catch(Exception e){logger.error("Exception in getPrivilege()::"+e.getMessage());}
		return getPrivilege;
	}

	@Override
	public void updatePrivilege(Privileges privileges){
		try{getSession().update(privileges);
		}catch (Exception e){logger.error("Exception in updatePrivileges() :: "+e.getMessage());}
	}

	@Override
	public void deletePrivilege(Long privilegeid){
		try{Object o=getSession().load(Privileges.class,privilegeid);
			getSession().delete(o);
		}catch(Exception e){logger.error("Exception in deletePrivileges(): "+e.getMessage());}
	}
}