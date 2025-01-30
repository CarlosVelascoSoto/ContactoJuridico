package com.aj.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.aj.model.Juicios;
import com.aj.model.Uploadfiles;

@Repository("juiciosDAO")
public class JuiciosDaoImpl extends AbstractDAO implements JuiciosDAO{
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	@Override
	public int addNewJuicio(Juicios juicioobj){
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(juicioobj);
		}catch (HibernateException e){e.printStackTrace();}
		return (int) retConfirm;
	}

	public Juicios getJuicioById(int juicioid){
		Juicios getjuicio=null;
		try{getjuicio=(Juicios) getSession().get(Juicios.class, juicioid);
		}catch (Exception e){logger.error("Exception in getJuicioDetail()::"+e.getMessage());}
		return getjuicio;
	}

	@Override
	public void updateJuicioDetails(Juicios juicioobj){
		try{getSession().update(juicioobj);
		}catch (Exception e){logger.error("Exception in updateJuicioDetails() :: "+e.getMessage());}
	}

	public void deleteJuicio(int juicioid){
		try{Object o=getSession().load(Juicios.class, juicioid);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteJuicio(): "+e.getMessage());}
	}

	@Override
	public List<Juicios> getAll(String query){
		//Este proceso por omisión tiene un "Select", "query" puede contener el resto de la consulta o un "select" completo
		//List<Juicios> juicios=new ArrayList<>();
		try{Query queryResult=getSession().createQuery(query);
			List<Juicios> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}

	@Override
	public int addUploaderFile(Uploadfiles file) {
		// TODO Auto-generated method stub
		int retConfirm=0;
		try{retConfirm=(int) getSession().save(file);
		}catch (HibernateException e){e.printStackTrace();}
		return (int) retConfirm;
	}

	@Override
	public int updateFixUrl(Uploadfiles obj){
		try{getSession().update(obj);
			return 1;
		}catch (Exception e){logger.error("Exception in updateFixUrl() :: "+e.getMessage());}
		return 0;
	}

	@Override
	public int deleteFixUrl(int id) {
		try{Object o=getSession().load(Uploadfiles.class, id);
			getSession().delete(o);
		}catch (Exception e){logger.error("Exception in deleteIdFixUrl(): "+e.getMessage());}
		return id;
	}

	
	/** Obtiene el nombre de los campos de una tabla.
	@param 		table				Cadena de texto con el nombre de la tabla.
	@param 		leaveOutColumns		(opcional) Columnas a omitir:<ul>
							<li>1) Pueden ser uno o varios "nombres de columnas" (separados por comas):<br>
								ejemplo, para omitir las columna 'id' y 'nombre': leaveOutColumns="id,nombre"</li>
							<li>2) Puede ser el "índice u ordinal_position" de una o más columnas (separadas por comas),<br>
							 	ejemplo, para omitir la primera y tercera columna de la tabla: leaveOutColumns="1,3"</li>
						 	<li>Si no es requerido se deberá dejar en cadena vacía ("").</li></ul>
	@returns	Listado 	List&lt;String&gt; con el nombre de los campos (omitiendo los indicados por <b>'leaveOutColumns'</b>). 
	@throws		Listado 	List&lt;String&gt; vacío.	*/
	public @Override List<String> getTableColumnNames(String table, String leaveOutColumns){

		//FIXME:	Tener en cuenta que esta tabla se guardan las contraseñas, se omitirá consultas por default.
		if(table.equals("users"))return null;
		
		List<String> colnames = new ArrayList<String>();
		String whereClause = "";
		List<String> loc = new ArrayList<String>(Arrays.asList(leaveOutColumns.split(",")));
		if(!com.aj.utility.Functions.isEmpty(leaveOutColumns)){
			if(isNumeric(loc.get(0)))
				whereClause=" AND ordinal_position NOT IN(" + leaveOutColumns + ")";
			else
				whereClause=" AND column_name NOT IN('" + leaveOutColumns.replaceAll(",","','") + "')";
		}
		try{
			Query results=getSession().createSQLQuery("SELECT column_name, is_nullable, data_type FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"
			+ table.toLowerCase() + "'" + whereClause);
			results.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List KeysAndValues = results.list();
			for (Object cdata:KeysAndValues){
				String[] noKey = cdata.toString().split("\\{column_name=");
				String colname = noKey[1].replaceAll(".$","");
				colnames.add(colname);
			}
		}catch (HibernateException e){e.printStackTrace();}
		return colnames;
	}

	@Override
	public List<?> getInfo(String query){
		//Este proceso por omisión tiene un "Select", "query" puede contener el resto de la consulta o un "select" completo
		//List<Juicios> juicios=new ArrayList<>();
		try{Query queryResult=getSession().createQuery(query);
			List<?> allRows=queryResult.list();
			return allRows;
		}catch (HibernateException e){e.printStackTrace();}
		return null;
	}

	/**	Verifica que el contenido de una variable sea numérica.
	 * @param strNum	Cadena de texto con contenido numérico.
	 * @return			Retorna 'true' sí es número o 'false' sí no lo es.	*/
	public boolean isNumeric(String strNum) {
	    if (strNum == null)
	        return false;
	    return (strNum).matches("-?\\d+(\\.\\d+)?");
	}
}