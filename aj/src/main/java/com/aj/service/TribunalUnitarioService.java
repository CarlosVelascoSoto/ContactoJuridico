package com.aj.service;

import java.util.List;
import com.aj.model.TribunalUnitario;

public interface TribunalUnitarioService{
	public Integer addNewTribunalUnit(TribunalUnitario tribunalid); 
	public TribunalUnitario getTribunalUnitById(Long tribunalid);
	public void updateTribunalUnit(TribunalUnitario tribunalid);
	public void deleteTribunalUnit(Long materiaid);
	public List<TribunalUnitario> getAll(String query);
}