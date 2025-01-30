package com.aj.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.aj.model.SmtpMailCRM;
import com.aj.model.Smtpmail;

@Repository("smtpDAO")
public class SmtpMailDAOImpl extends AbstractDAO implements SmtpMailDAO{
	private static final String SQL_SMPTMAIL_BY_ID = "SELECT * FROM smtpmail WHERE smtpid=?";
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<SmtpMailCRM> getAll(String query){
		//Este proceso por omisión tiene un "Select", "query" puede contener el resto de la consulta o un "select" completo
		List<SmtpMailCRM> smtp=new ArrayList<>();
		try{Query queryResult=getSession().createQuery(query);
			java.util.List<SmtpMailCRM> allsmtpmail=queryResult.list();
			for (int i=0; i<allsmtpmail.size(); i++){
				SmtpMailCRM smtpReg=(SmtpMailCRM) allsmtpmail.get(i);
				smtp.add(smtpReg);
			}
		}catch (HibernateException e){e.printStackTrace();}
		return smtp;
	}

	@Override
	public Integer addNewSmtp(SmtpMailCRM smtp){
		long retConfirm=0;
		try{retConfirm=(long) getSession().save(smtp);
		}catch (HibernateException e){e.printStackTrace();}
		return (int) retConfirm;
	}

	@Override
	public SmtpMailCRM getSmtpById(Long smtpid){
		SmtpMailCRM getsmtp=null;
		try{getsmtp=(SmtpMailCRM) getSession().get(SmtpMailCRM.class, smtpid);
		}catch (Exception e){System.err.println("Exception in getSmtpDetail()::"+e.getMessage());}
		return getsmtp;
	}

	@Override
	public void updateSmtpDetails(SmtpMailCRM smtpid){
		try{getSession().update(smtpid);
		}catch (Exception e){System.err.println("Exception in updateRoleDetails() :: "+e.getMessage());}
	}

	@Override
	public void deleteSmtp(Long smtpid){
		try{Object o=getSession().load(SmtpMailCRM.class, smtpid);
			getSession().delete(o);
		}catch (Exception e){System.err.println("Exception in deleteRole(): "+e.getMessage());}
	}

	public SmtpMailCRM getSmptEmail(Integer smtpid){
		Object[] params={smtpid};
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		return (SmtpMailCRM) jdbcTemplate.queryForObject(SQL_SMPTMAIL_BY_ID, params,
			new BeanPropertyRowMapper<SmtpMailCRM>(SmtpMailCRM.class));
	}
}