package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.FiscalsdataDAO;
import com.aj.model.Fiscalsdata;

@Service("fiscaldataService")
@Transactional
public class FiscalsdataServiceImpl implements FiscalsdataService{
	@Autowired
	private FiscalsdataDAO fiscalsdataDAO;
	
	@Override
	public Integer addNewFiscaldata(Fiscalsdata fiscaldataobj){
		return fiscalsdataDAO.addNewFiscaldata(fiscaldataobj);
	}
	
	@Override
	public List<Fiscalsdata> getAll(String query){
		return fiscalsdataDAO.getAll(query);
	}
	
	@Override
	public Fiscalsdata getFiscaldataById(Long getfiscaldataid){
		return fiscalsdataDAO.getFiscaldataById(getfiscaldataid);
	}

	@Override
	public void updateFiscaldata(Fiscalsdata fiscaldataobj){
		fiscalsdataDAO.updateFiscaldata(fiscaldataobj);
	}
	
	@Override
	public void deleteFiscaldata(Long fiscaldataid){
		fiscalsdataDAO.deleteFiscaldata(fiscaldataid);
	}
}