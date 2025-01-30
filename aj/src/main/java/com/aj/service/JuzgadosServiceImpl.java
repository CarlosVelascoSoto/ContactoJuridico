package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.JuzgadosDAO;
import com.aj.model.Juzgados;

@Service("juzgadoService")
@Transactional
public class JuzgadosServiceImpl implements JuzgadosService{
	@Autowired
	private JuzgadosDAO juzgadosDAO;
	
	@Override
	public Integer addNewJuzgado(Juzgados juzgadoobj){
		return juzgadosDAO.addNewJuzgado(juzgadoobj);
	}
	
	@Override
	public List<Juzgados> getAll(String query){
		return juzgadosDAO.getAll(query);
	}
	
	@Override
	public Juzgados getJuzgadoById(Long getjuzgadoid){
		return juzgadosDAO.getJuzgadoById(getjuzgadoid);
	}

	@Override
	public void updateJuzgado(Juzgados juzgadoobj){
		juzgadosDAO.updateJuzgado(juzgadoobj);
	}
	
	@Override
	public void deleteJuzgado(Long juzgadoid){
		juzgadosDAO.deleteJuzgado(juzgadoid);
	}
}