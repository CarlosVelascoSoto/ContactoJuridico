package com.aj.dao;

import java.util.List;
import com.aj.model.Amparos;

public interface AmparosDAO{
	public Integer addNewAmparo(Amparos amparoobj); 
	public Amparos getAmparoById(int amparoid);
	public void updateAmparo(Amparos amparoobj);
	public void deleteAmparo(int amparoid);
	public List<Amparos> getAll(String query);
}