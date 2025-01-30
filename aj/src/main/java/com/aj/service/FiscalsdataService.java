package com.aj.service;

import java.util.List;
import com.aj.model.Fiscalsdata;

public interface FiscalsdataService{
	public Integer addNewFiscaldata(Fiscalsdata fiscaldataobj);
	public Fiscalsdata getFiscaldataById(Long fiscaldataid);
	public void updateFiscaldata(Fiscalsdata fiscaldataobj);
	public void deleteFiscaldata(Long fiscaldataid);
	public List<Fiscalsdata> getAll(String query);
}