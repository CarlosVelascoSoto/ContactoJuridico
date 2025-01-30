package com.aj.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.aj.model.Client;
import com.aj.model.Contact;
import com.aj.model.LeadProspect;
import com.aj.model.Users;
import com.aj.utility.Functions;

@Repository("customerSalesDAO")
public class CustomerSalesDAOImpl extends AbstractDAO implements CustomerSalesDAO{
	private static final Logger logger=Logger.getLogger(CustomerSalesDAOImpl.class);
	
	Properties propiedades;

	public String getCompanyName(int id){
		String name="";
		try{Query queryResult=getSession().createQuery("FROM Users WHERE id="+id);
			List<Users> listUser=queryResult.list();
			if(!listUser.isEmpty() && listUser != null){
				Users user=(Users) queryResult.list().get(0);
				if(user != null){
					name=user.getCompany_name();
					if(name.trim().equals("")){
						name=user.getFirst_name()+" "+user.getLast_name();}
				}			
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("Exception in getAllCompany()::" + ex.getMessage());
		}return name;
	}

	@Override
	public int addContact(Contact contact){
		int result=0;
		try{result=(int) getSession().save(contact);
		}catch(Exception ex){logger.error("Exception in addContact():: "+ex.getMessage());}
		return result;
	}

	@Override
	public ArrayList<Contact> getAllContact(){
		List<Contact> contacts=new ArrayList<>();
		try{Query queryResult=getSession().createQuery("FROM Contact");
			java.util.List<Contact> allContact=queryResult.list();
			for(int i=0; i < allContact.size(); i++){
				Contact contact=allContact.get(i);
				contacts.add(contact);
			}
		}catch(Exception ex){
			logger.error("Exception in getAllContact()::" + ex.getMessage());
		}return(ArrayList<Contact>) contacts;
	}

	@Override
	public int addOpportunity(LeadProspect leadProspect){
		int result=0;
		try{result=(int) getSession().save(leadProspect);
		}catch(Exception ex){logger.error("Exception in addOpportunity():: "+ex.getMessage());}
		return result;
	}

	@Override
	public void deleteOpportunity(int id){
		try{Object o=getSession().load(LeadProspect.class,id);
			LeadProspect p=(LeadProspect)o;
			getSession().delete(p);
		}catch(Exception e){
			logger.error("Exception in deleteOpportunity(): "+e.getMessage());
		}
	}

	@Override
	public ArrayList<Contact> getAllContactByCompany(int companyId){
		List<Contact> contacts=new ArrayList<>();
		try{Query queryResult=getSession().createQuery("FROM Contact c WHERE c.companyId=:companyId");
			queryResult.setParameter("companyId", companyId);
			java.util.List<Contact> allContact=queryResult.list();
			for(int i=0; i < allContact.size(); i++){
				Contact contact=allContact.get(i);
				contacts.add(contact);
			}
		}catch(Exception ex){
			logger.error("Exception in getAllContactByCompany()::" + ex.getMessage());
		}return(ArrayList<Contact>) contacts;
	}

	@Override
	public LeadProspect getOpportunityDetailById(int oppId){
		LeadProspect leadProspect=null;
		try{leadProspect=(LeadProspect) getSession().get(LeadProspect.class, oppId);
		}catch(Exception e){logger.error("Exception in getOpportunityDetailById()::"+e.getMessage());}
		return leadProspect;
	}

	@Override
	public void updateOpportunity(LeadProspect leadProspect){
		try{getSession().update(leadProspect);
		}catch(Exception e){logger.error("Exception in updateOpportunity() :: "+e.getMessage());}
	}

	@Override
	public List<LeadProspect> getSortByDate(String fromDate, String toDate, int role, int company){
		Query queryResult=null;
		List<LeadProspect> leadProspects=new ArrayList<>();
		try{
			if(fromDate.equals("")){
				if(role ==4){
					queryResult=getSession().createQuery("FROM LeadProspect u WHERE u.company="+company+"AND u.date BETWEEN '1971-01-01'AND '"+toDate+"'");
				}else if(role ==2 || role == 3){
					queryResult=getSession().createQuery("FROM LeadProspect u WHERE u.salesperson_id="+company+"AND  u.date BETWEEN '1971-01-01'AND '"+toDate+"'");
				}else{
					queryResult=getSession().createQuery("FROM LeadProspect u WHERE u.date BETWEEN '1971-01-01'AND '"+toDate+"'");
				}
			}else if(toDate.equals("")){
				String current_date=Functions.formateaFecha(new Date(),"yyyy-MM-dd");
				if(role ==4){
					queryResult=getSession().createQuery("FROM LeadProspect u WHERE u.company="+company+"AND u.date BETWEEN '"+fromDate+"'AND '"+current_date+"'");
				}else if(role ==2 || role == 3){
					queryResult=getSession().createQuery("FROM LeadProspect u WHERE u.salesperson_id="+company+"AND u.date BETWEEN '"+fromDate+"'AND '"+current_date+"'");
				}else{
					queryResult=getSession().createQuery("FROM LeadProspect u WHERE u.date BETWEEN '"+fromDate+"'AND '"+current_date+"'");
				}
			}else{
				if(role ==4){
					queryResult=getSession().createQuery("FROM LeadProspect u WHERE u.company=" + company + "AND u.date BETWEEN '" + fromDate +"'AND '"+toDate+"'");
				}else if(role ==2 || role == 3){
					queryResult=getSession().createQuery("FROM LeadProspect u WHERE u.salesperson_id="+company+"AND u.date BETWEEN '"+fromDate+"'AND '"+toDate+"'");
				}else{
					queryResult=getSession().createQuery("FROM LeadProspect u WHERE u.date BETWEEN '" + fromDate+ "'AND '" + toDate + "'");
				}
			}
			java.util.List<LeadProspect> allOpportunity=queryResult.list();
			for(int i=0; i < allOpportunity.size(); i++){
				LeadProspect leadProspect=allOpportunity.get(i);
				leadProspects.add(leadProspect);
			}
		}catch(Exception e){e.printStackTrace();}
		return leadProspects;
	}

}