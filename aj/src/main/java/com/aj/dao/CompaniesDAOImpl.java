package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Companies;

@Repository("companiesDAO")
public class CompaniesDAOImpl extends AbstractDAO implements CompaniesDAO{
	private static final Logger logger=Logger.getLogger(CompaniesDAOImpl.class);

	@Override
	public List<Companies> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Companies> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}

	@Override
	public Integer addNewCompany(Companies companyobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(companyobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Companies getCompanyById(Long companyid){
		Companies getCompanies=null;
		try{getCompanies=(Companies) getSession().get(Companies.class,companyid);
		}catch (Exception e){logger.error("Exception in getCompanies(): "+e.getMessage());}
		return getCompanies;
	}

	@Override
	public void updateCompany(Companies companyobj){
		try{getSession().update(companyobj);
		}catch (Exception e){logger.error("Exception in updateCompanies(): "+e.getMessage());}
	}

	@Override
	public void deleteCompany(Long companyid){
		try{Object o=getSession().load(Companies.class,companyid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteCompany(): "+e.getMessage());}
	}
}