package com.aj.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Menu;

@Repository("menuDAO")
public class MenuDAOImpl extends AbstractDAO implements MenuDAO{
	private static final Logger logger=Logger.getLogger(MenuDAOImpl.class);

	@Override
	public Integer addNewMenu(Menu menuobj){
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(menuobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Menu getMenuById(int menuid){
		Menu getMenu=null;
		try{getMenu=(Menu) getSession().get(Menu.class,menuid);
		}catch (Exception e){logger.error("Exception in getMenu(): "+e.getMessage());}
		return getMenu;
	}

	@Override
	public void updateMenu(Menu obj){
		try{getSession().update(obj);
		}catch (Exception e){logger.error("Exception in updateMenu(): "+e.getMessage());}
	}

	@Override
	public void deleteMenu(int menuid){
		try{Object o=getSession().load(Menu.class,menuid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteMenu(): "+e.getMessage());}
	}

	@Override
	public List<Menu> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Menu> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}
}