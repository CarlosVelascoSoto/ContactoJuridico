package com.aj.dao;

import java.util.List;
import com.aj.model.Jueces;

public interface JuecesDAO{
	public Integer addNewJuez(Jueces juezobj);
	public Jueces getJuezById(Long juezid);
	public void updateJuez(Jueces juezobj);
	public void deleteJuez(Long juezid);
	public List<Jueces> getAll(String query);
}