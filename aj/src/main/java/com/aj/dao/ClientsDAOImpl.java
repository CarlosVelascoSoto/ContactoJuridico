package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Clients;

@Repository("clientsDAO")
public class ClientsDAOImpl extends AbstractDAO implements ClientsDAO{
	private static final Logger logger=Logger.getLogger(ClientsDAOImpl.class);

	@Override
	public Integer addNewClient(Clients clientobj){
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(clientobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Clients getClientById(int clientid){
		Clients getClients=null;
		try{getClients=(Clients) getSession().get(Clients.class,clientid);
		}catch (Exception e){logger.error("Exception in getClients(): "+e.getMessage());}
		return getClients;
	}

	@Override
	public void updateClient(Clients obj){
		try{getSession().update(obj);
		}catch (Exception e){logger.error("Exception in updateClients(): "+e.getMessage());}
	}

	@Override
	public void deleteClient(int clientid){
		try{Object o=getSession().load(Clients.class,clientid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteClients(): "+e.getMessage());}
	}

	@Override
	public List<Clients> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Clients> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}
}