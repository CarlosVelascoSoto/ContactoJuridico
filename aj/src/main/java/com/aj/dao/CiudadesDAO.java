package com.aj.dao;

import java.util.List;
import com.aj.model.Ciudades;

public interface CiudadesDAO{
	public Integer addNewCiudad(Ciudades ciudadobj);
	public Ciudades getCiudadById(Long ciudadid);
	public void updateCiudad(Ciudades ciudadobj);
	public void deleteCiudad(Long ciudadid);
	public List<Ciudades> getAll(String query);
}