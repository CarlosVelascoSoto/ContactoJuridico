package com.aj.dao;

import java.util.List;
import com.aj.model.TribunalUnitario;

public interface TribunalUnitarioDAO{
	public Integer addNewTribunalUnit(TribunalUnitario tribunalid); 
	public TribunalUnitario getTribunalUnitById(Long tribunalid);
	public void updateTribunalUnit(TribunalUnitario tribunalid);
	public void deleteTribunalUnit(Long materiaid);
	public List<TribunalUnitario> getAll(String query);
}