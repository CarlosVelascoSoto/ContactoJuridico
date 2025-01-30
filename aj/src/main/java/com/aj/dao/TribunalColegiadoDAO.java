package com.aj.dao;

import java.util.List;
import com.aj.model.TribunalColegiado;

public interface TribunalColegiadoDAO{
	public Integer addNewTribunalC(TribunalColegiado tribunalid); 
	public TribunalColegiado getTribunalCById(Long tribunalid);
	public void updateTribunalC(TribunalColegiado tribunalid);
	public void deleteTribunalC(Long tribunalid);
	public List<TribunalColegiado> getAll(String query);
}