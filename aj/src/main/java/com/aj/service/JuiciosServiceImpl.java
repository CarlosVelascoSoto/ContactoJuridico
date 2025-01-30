package com.aj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.dao.JuiciosDAO;
import com.aj.model.Juicios;
import com.aj.model.Uploadfiles;

@Service("juiciosService")
@Transactional
public class JuiciosServiceImpl implements JuiciosService{

	@Autowired
	private JuiciosDAO juicioDAO;

	public int addNewJuicio(Juicios juicioobj){
		return juicioDAO.addNewJuicio(juicioobj);
	}

	@Override
	public Juicios getJuicioById(int juicioid){
		return juicioDAO.getJuicioById(juicioid);
	}

	@Override
	public void updateJuicioDetails(Juicios juicioobj){
		juicioDAO.updateJuicioDetails(juicioobj);
	}
	
	@Override
	public void deleteJuicio(int juicioid) {
		juicioDAO.deleteJuicio(juicioid);
	}

	@Override
	public List<Juicios> getAll(String query){
		return juicioDAO.getAll(query);
	}

	@Override
	public int addUploaderFile(Uploadfiles entity) {
		// TODO Auto-generated method stub
		return juicioDAO.addUploaderFile(entity);
	}

	@Override
	public int updateFixUrl(Uploadfiles obj) {
		return juicioDAO.updateFixUrl(obj);
	}

	@Override
	public int deleteFixUrl(int id) {
		return juicioDAO.deleteFixUrl(id);
	}

	@Override
	public List<String> getTableColumnNames(String table, String leaveOutColumns) {
		return juicioDAO.getTableColumnNames(table, leaveOutColumns);
	}
	
	@Override
	public List<?> getInfo(String query) {
		return juicioDAO.getInfo(query);
	}
}