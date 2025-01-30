package com.aj.dao;

import java.util.List;



import com.aj.model.Paises;

public interface PaisesDAO {
	public Integer addNewPais(Paises paisObj);

	
	public Paises getPaisById(Long paisId);

	public void updatePais(Paises paisObj);

	public void deletePais(Long paisId);

	public List<Paises> getAll(String query);
}