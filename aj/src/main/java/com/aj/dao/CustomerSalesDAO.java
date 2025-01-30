package com.aj.dao;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Multimap;
import com.aj.model.Contact;
import com.aj.model.LeadProspect;
import com.aj.model.OpportunityAux;

public interface CustomerSalesDAO {
	public int addOpportunity(LeadProspect leadProspect);
	public ArrayList<Contact> getAllContact();
	public int addContact(Contact contact);
	public void deleteOpportunity(int id);
	public ArrayList<Contact> getAllContactByCompany(int companyId);
	public LeadProspect getOpportunityDetailById(int oppId);
	public List<LeadProspect> getSortByDate(String fromDate, String toDate, int role, int company);
	public void updateOpportunity(LeadProspect leadProspect);
}