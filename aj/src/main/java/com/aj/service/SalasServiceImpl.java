package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.SalasDAO;
import com.aj.model.Salas;

@Service("salasService")
@Transactional
public class SalasServiceImpl implements SalasService{
	@Autowired
	private SalasDAO salasDAO;
	
	@Override
	public Integer addNewSala(Salas salaobj){
		return salasDAO.addNewSala(salaobj);
	}
	
	@Override
	public List<Salas> getAll(String query){
		return salasDAO.getAll(query);
	}
	
	@Override
	public Salas getSalaById(Long getsalaid){
		return salasDAO.getSalaById(getsalaid);
	}

	@Override
	public void updateSala(Salas salaobj){
		salasDAO.updateSala(salaobj);
	}
	
	@Override
	public void deleteSala(Long salaid){
		salasDAO.deleteSala(salaid);
	}
}