package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Materias;

@Repository("materiasDAO")
public class MateriasDAOImpl extends AbstractDAO implements MateriasDAO{
	private static final Logger logger=Logger.getLogger(MateriasDAOImpl.class);

	@Override
	public List<Materias> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo  
		try{Query queryResult=getSession().createQuery(query);
			List<Materias> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
	return null;
	}

	@Override
	public Integer addNewMateria(Materias materiaobj){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(materiaobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int)retConfirm;
	}

	@Override
	public Materias getMateriaById(Long materiaid){
		Materias getMaterias=null;
		try{getMaterias=getSession().get(Materias.class,materiaid);
		}catch (Exception e){logger.error("Exception in getMaterias(): "+e.getMessage());}
		return getMaterias;
	}

	@Override
	public void updateMateria(Materias materiaobj){
		try{getSession().update(materiaobj);
		}catch (Exception e){logger.error("Exception in updateMaterias(): "+e.getMessage());}
	}

	@Override
	public void deleteMateria(Long materiaid){
		try{Object o=getSession().load(Materias.class,materiaid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteMateria(): "+e.getMessage());}
	}
}