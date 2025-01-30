package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.EstadosDAO;
import com.aj.model.Estados;

@Service("estadoService")
@Transactional
public class EstadosServiceImpl implements EstadosService{
	@Autowired
	private EstadosDAO estadosDAO;
	
	@Override
	public Integer addNewEstado(Estados estadoobj){
		return estadosDAO.addNewEstado(estadoobj);
	}
	
	@Override
	public List<Estados> getAll(String query){
		return estadosDAO.getAll(query);
	}
	
	@Override
	public Estados getEstadoById(Long getestadoid){
		return estadosDAO.getEstadoById(getestadoid);
	}

	@Override
	public void updateEstado(Estados estadoobj){
		estadosDAO.updateEstado(estadoobj);
	}
	
	@Override
	public void deleteEstado(Long estadoid){
		estadosDAO.deleteEstado(estadoid);
	}
}