package com.aj.dao;

import java.util.List;
import com.aj.model.Fiscalsdata;

public interface FiscalsdataDAO{
	public Integer addNewFiscaldata(Fiscalsdata fiscaldataobj);
	public Fiscalsdata getFiscaldataById(Long fiscaldataid); 
	public void updateFiscaldata(Fiscalsdata fiscaldataobj);
	public void deleteFiscaldata(Long fiscaldataid);
	public List<Fiscalsdata> getAll(String query);
}