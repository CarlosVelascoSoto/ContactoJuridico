package com.aj.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.ECalendar;

@Repository("ecalendarDAO")
public class ECalendarDAOImpl extends AbstractDAO implements ECalendarDAO{
	@Override
	public List<ECalendar> getAll(String query) {
		//Este proceso hace un "Select" por default, "query" debe contener el resto de la consulta o un 'select' completo
		try{Query queryResult=getSession().createQuery(query);
			List<ECalendar> ecalendar=queryResult.list();
			return ecalendar;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}

	@Override
	public Integer addNewCalendarEvent(ECalendar ecalendarObject) {
		Serializable retConfirm=null;
		try{retConfirm=getSession().save(ecalendarObject);
		}catch (HibernateException e){e.printStackTrace();}
		return (int) retConfirm;
	}

	@Override
	public void updateCalendar(ECalendar ecalendarObject){
		try{getSession().update(ecalendarObject);
		}catch (Exception e){System.out.println("Exception in updateECalendar(): "+e.getMessage());}
	}

	@Override
	public void deleteCalendar(int ecalendarid){
		try{Object o=getSession().load(ECalendar.class,ecalendarid);
			getSession().delete(o);
		}catch (Exception e){System.out.println("Exception in deleteECalendar(): "+e.getMessage());}
	}
}