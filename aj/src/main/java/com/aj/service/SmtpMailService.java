package com.aj.service;

import java.util.List;
import com.aj.model.SmtpMailCRM;

public interface SmtpMailService{
	public Integer addNewSmtp(SmtpMailCRM smtp);
	public SmtpMailCRM getSmtpById(Long smtpid);
	public void updateSmtpDetails(SmtpMailCRM smtpid);
	public void deleteSmtp(Long smtpid);
	public List<SmtpMailCRM> getAll(String query);
	SmtpMailCRM getSmptEmail(Integer smtpid);
}