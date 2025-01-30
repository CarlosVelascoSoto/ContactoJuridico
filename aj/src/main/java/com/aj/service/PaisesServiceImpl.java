package com.aj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.dao.PaisesDAO;
import com.aj.model.Paises;

@Service("paisesService")
@Transactional
public class PaisesServiceImpl implements PaisesService{
	@Autowired
	private PaisesDAO paisesDAO;

	@Override
	public Integer addNewPais(Paises paisObj){
		return paisesDAO.addNewPais(paisObj);
	}

	@Override
	public Paises getPaisById(Long paisId){
		return paisesDAO.getPaisById(paisId);
	}

	@Override
	public void updatePais(Paises paisObj){
		paisesDAO.updatePais(paisObj);
	}

	@Override
	public void deletePais(Long paisId){
		paisesDAO.deletePais(paisId);
	}

	@Override
	public List<Paises> getAll(String query){
		return paisesDAO.getAll(query);
	}
}