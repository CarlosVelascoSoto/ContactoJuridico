package com.aj.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Notifications;

@Repository("NotificationsDAO")
public class NotificationsDAOImpl extends AbstractDAO implements NotificationsDAO{
	private static final Logger logger=Logger.getLogger(NotificationsDAOImpl.class);

	@Override
	public List<Notifications> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo
		try{Query queryResult=getSession().createQuery(query);
			List<Notifications> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewNotification(Notifications notificationobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(notificationobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Notifications getNotificationById(Long notificationid){
		Notifications getNotifications=null;
		try{getNotifications=getSession().get(Notifications.class,notificationid);
		}catch (Exception e){logger.error("Exception in getNotifications(): "+e.getMessage());}
		return getNotifications;
	}

	@Override
	public void updateNotification(Notifications notificationobj){
		try{getSession().update(notificationobj);
		}catch (Exception e){logger.error("Exception in updateNotifications(): "+e.getMessage());}
	}

	@Override
	public void deleteNotification(Long notificationid){
		try{Object o=getSession().load(Notifications.class,notificationid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteNotification(): "+e.getMessage());}
	}
}