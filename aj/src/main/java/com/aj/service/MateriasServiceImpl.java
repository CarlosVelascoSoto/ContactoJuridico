package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.MateriasDAO;
import com.aj.model.Materias;

@Service("materiaService")
@Transactional
public class MateriasServiceImpl implements MateriasService{
	@Autowired
	private MateriasDAO materiasDAO;
	
	@Override
	public Integer addNewMateria(Materias materiaobj){
		return materiasDAO.addNewMateria(materiaobj);
	}
	
	@Override
	public List<Materias> getAll(String query){
		return materiasDAO.getAll(query);
	}
	
	@Override
	public Materias getMateriaById(Long getmateriaid){
		return materiasDAO.getMateriaById(getmateriaid);
	}

	@Override
	public void updateMateria(Materias materiaobj){
		materiasDAO.updateMateria(materiaobj);
	}
	
	@Override
	public void deleteMateria(Long materiaid){
		materiasDAO.deleteMateria(materiaid);
	}
}