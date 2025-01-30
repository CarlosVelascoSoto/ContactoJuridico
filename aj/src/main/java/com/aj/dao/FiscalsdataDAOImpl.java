package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Fiscalsdata;

@Repository("fiscalsdataDAO")
public class FiscalsdataDAOImpl extends AbstractDAO implements FiscalsdataDAO{
	private static final Logger logger=Logger.getLogger(FiscalsdataDAOImpl.class);

	@Override
	public List<Fiscalsdata> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Fiscalsdata> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewFiscaldata(Fiscalsdata fiscaldataobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(fiscaldataobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Fiscalsdata getFiscaldataById(Long fiscaldataid){
		Fiscalsdata getFiscalsdata=null;
		try{getFiscalsdata=getSession().get(Fiscalsdata.class,fiscaldataid);
		}catch (Exception e){logger.error("Exception in getFiscalsdata(): "+e.getMessage());}
		return getFiscalsdata;
	}

	@Override
	public void updateFiscaldata(Fiscalsdata fiscaldataobj){
		try{getSession().update(fiscaldataobj);
		}catch (Exception e){logger.error("Exception in updateFiscalsdata(): "+e.getMessage());}
	}

	@Override
	public void deleteFiscaldata(Long fiscaldataid){
		try{Object o=getSession().load(Fiscalsdata.class,fiscaldataid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteFiscaldata(): "+e.getMessage());}
	}
}