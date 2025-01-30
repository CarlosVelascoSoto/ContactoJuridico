package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.RecursosDAO;
import com.aj.model.Recursos;

@Service("recursoService")
@Transactional
public class RecursosServiceImpl implements RecursosService{
	@Autowired
	private RecursosDAO recursosDAO;

	@Override
	public Integer addNewRecurso(Recursos recursoobj){
		return recursosDAO.addNewRecurso(recursoobj);
	}

	@Override
	public List<Recursos> getAll(String query){
		return recursosDAO.getAll(query);
	}

	@Override
	public Recursos getRecursoById(int recursoid){
		return recursosDAO.getRecursoById(recursoid);
	}

	@Override
	public void updateRecurso(Recursos recursoobj){
		recursosDAO.updateRecurso(recursoobj);
	}

	@Override
	public void deleteRecurso(int recursoid){
		recursosDAO.deleteRecurso(recursoid);
	}
}