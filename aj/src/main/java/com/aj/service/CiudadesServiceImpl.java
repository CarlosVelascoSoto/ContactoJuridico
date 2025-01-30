package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.CiudadesDAO;
import com.aj.model.Ciudades;

@Service("ciudadService")
@Transactional
public class CiudadesServiceImpl implements CiudadesService{
	@Autowired
	private CiudadesDAO ciudadesDAO;
	
	@Override
	public Integer addNewCiudad(Ciudades ciudadobj){
		return ciudadesDAO.addNewCiudad(ciudadobj);
	}
	
	@Override
	public List<Ciudades> getAll(String query){
		return ciudadesDAO.getAll(query);
	}
	
	@Override
	public Ciudades getCiudadById(Long getciudadid){
		return ciudadesDAO.getCiudadById(getciudadid);
	}

	@Override
	public void updateCiudad(Ciudades ciudadobj){
		ciudadesDAO.updateCiudad(ciudadobj);
	}
	
	@Override
	public void deleteCiudad(Long ciudadid){
		ciudadesDAO.deleteCiudad(ciudadid);
	}
}