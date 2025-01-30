package com.aj.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.aj.model.Recursos;

@Repository("recursosDAO")
public class RecursosDAOImpl extends AbstractDAO implements RecursosDAO{
	private static final Logger logger=Logger.getLogger(RecursosDAOImpl.class);
	public int maxRetries = 3, failsCounter = 0;
	public long waitTime = 1500; // Milisegundos

	@Override
	public Integer addNewRecurso(Recursos recursoobj){
		int retConfirm=0;
		while(failsCounter<maxRetries){
			try{
				retConfirm=(int) getSession().save(recursoobj);
				break;
			}catch (HibernateException e){
				e.printStackTrace();
				failsCounter++;
	            if(failsCounter<maxRetries){
	                try{
	                    Thread.sleep(waitTime);
	                }catch(InterruptedException ie){
	                    Thread.currentThread().interrupt();
	                    logger.error("Funcíón 'addNewRecurso()': Thread interrumpido durante el reintento "
                    		+ failsCounter + ".\n" + e.getMessage(), ie);
	                }
	            }else{
	                logger.error("Funcíón 'addNewRecurso()': Fallo despues de " + maxRetries + " intentos. "+e.getMessage());
	            }
			}
		}
		return (int)retConfirm;
	}

	@Override
	public Recursos getRecursoById(int recursoid){
		Recursos getRecursos=null;
		while(failsCounter<maxRetries){
			try{
				getRecursos=(Recursos) getSession().get(Recursos.class,recursoid);
				break;
			}catch (Exception e){
				failsCounter++;
	            if(failsCounter<maxRetries){
	                try{
	                    Thread.sleep(waitTime);
	                }catch(InterruptedException ie){
	                    Thread.currentThread().interrupt();
	                    logger.error("Funcíón 'getRecursoById()': Thread interrumpido durante el reintento "
	                    	+ failsCounter + ".\n" + e.getMessage(), ie);
	                }
	            }else{
	                logger.error("Función 'getRecursoById()': Fallo despues de " + maxRetries + " intentos. ", e);
	            }
			}
		}
		return getRecursos;
	}

	@Override
	public void updateRecurso(Recursos recursoobj){
		while(failsCounter<maxRetries){
			try{
				getSession().update(recursoobj);
				break;
			}catch (Exception e){
				failsCounter++;
	            if(failsCounter<maxRetries){
	                try{
	                    Thread.sleep(waitTime);
	                }catch(InterruptedException ie){
	                    Thread.currentThread().interrupt();
	                    logger.error("Funcíón 'updateRecurso()': Thread interrumpido durante el reintento "
	                    	+ failsCounter + ".\n" + e.getMessage(), ie);
	                }
	            }else{
	                logger.error("Funcíón 'updateRecurso()': Fallo despues de " + maxRetries + " intentos. ", e);
	            }
			}
		}
	}

	@Override
	public void deleteRecurso(int recursoid){
		while(failsCounter<maxRetries){
			try{
				Object o=getSession().load(Recursos.class,recursoid);
				getSession().delete(o);
				break;
			}catch (Exception e){
				failsCounter++;
                if(failsCounter<maxRetries){
	                try{
	                    Thread.sleep(waitTime);
	                }catch(InterruptedException ie){
	                    Thread.currentThread().interrupt();
	                    logger.error("Funcíón 'deleteRecurso()': Thread interrumpido durante el reintento "
	                    	+ failsCounter + ".\n" + e.getMessage(), ie);
	                }
	            }else{
	                logger.error("Funcíón 'deleteRecurso()': Fallo despues de " + maxRetries + " intentos. ", e);
	            }
			}
		}
	}

	@Override
	public List<Recursos> getAll(String query){
		//Este proceso por default siempre hace un "Select", "query" debe contener el resto de la consulta o un "Select" completo
		while(failsCounter<maxRetries){
			try{
				Query queryResult=getSession().createQuery(query);
				List<Recursos> allRows=queryResult.list();
				return allRows;
			}catch (HibernateException e){
				e.printStackTrace();
				failsCounter++;
                if(failsCounter<maxRetries){
					try{
		                Thread.sleep(waitTime);
		            }catch(InterruptedException ie){
		                Thread.currentThread().interrupt();
		                logger.error("Funcíón 'getAll()': Thread interrumpido durante el reintento "
	                		+ failsCounter + ".\n" + e.getMessage(), ie);
		            }
		        }else{
		            logger.error("Función 'getAll()': Fallo despues de " + maxRetries + " intentos. ", e);
		        }
			}
		}
		return null;
	}
}