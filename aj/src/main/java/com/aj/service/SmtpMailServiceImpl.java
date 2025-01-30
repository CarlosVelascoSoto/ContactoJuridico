package com.aj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.dao.SmtpMailDAO;
import com.aj.model.SmtpMailCRM;

@Service("smtpService")
@Transactional
public class SmtpMailServiceImpl implements SmtpMailService{
	@Autowired
	private SmtpMailDAO smtpDAO;

	@Override
	public Integer addNewSmtp(SmtpMailCRM smtp) {
		return smtpDAO.addNewSmtp(smtp);
	}

	@Override
	public List<SmtpMailCRM> getAll(String query){
		return smtpDAO.getAll(query);
	}

	@Override
	public SmtpMailCRM getSmtpById(Long getsmtp){
		return smtpDAO.getSmtpById(getsmtp);
	}

	@Override
	public void updateSmtpDetails(SmtpMailCRM smtpid){
		smtpDAO.updateSmtpDetails(smtpid);
	}

	@Override
	public void deleteSmtp(Long smtpid) {
		smtpDAO.deleteSmtp(smtpid);
	}
	
	@Override
	public SmtpMailCRM getSmptEmail(Integer smtpid) {
		return smtpDAO.getSmptEmail(smtpid);
	}
}