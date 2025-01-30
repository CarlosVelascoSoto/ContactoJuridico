package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.LawyerAddressBook;

@Repository("LawyerAddressBookDAO")
public class LawyerAddressBookDAOImpl extends AbstractDAO implements LawyerAddressBookDAO{
	private static final Logger logger=Logger.getLogger(LawyerAddressBookDAOImpl.class);

	@Override
	public List<LawyerAddressBook> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<LawyerAddressBook> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewLawyerAddress(LawyerAddressBook lawyeraddressobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(lawyeraddressobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public LawyerAddressBook getLawyerAddressById(Long lawyeraddressid){
		LawyerAddressBook getData=null;
		try{getData=getSession().get(LawyerAddressBook.class,lawyeraddressid);
		}catch (Exception e){logger.error("Exception in getLawyerAddress(): "+e.getMessage());}
		return getData;
	}

	@Override
	public void updateLawyerAddress(LawyerAddressBook lawyeraddressobj){
		try{getSession().update(lawyeraddressobj);
		}catch (Exception e){logger.error("Exception in updateLawyerAddress(): "+e.getMessage());}
	}

	@Override
	public void deleteLawyerAddress(Long lawyeraddressid){
		try{Object o=getSession().load(LawyerAddressBook.class,lawyeraddressid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteLawyerAddress(): "+e.getMessage());}
	}
}