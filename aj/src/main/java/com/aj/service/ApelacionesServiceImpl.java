package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.ApelacionesDAO;
import com.aj.model.Apelaciones;

@Service("apelacionService")
@Transactional
public class ApelacionesServiceImpl implements ApelacionesService{
	@Autowired
	private ApelacionesDAO apelacionesDAO;

	@Override
	public Integer addNewApelacion(Apelaciones apelacionobj){
		return apelacionesDAO.addNewApelacion(apelacionobj);
	}

	@Override
	public List<Apelaciones> getAll(String query){
		return apelacionesDAO.getAll(query);
	}

	@Override
	public Apelaciones getApelacionById(int apelacionid){
		return apelacionesDAO.getApelacionById(apelacionid);
	}

	@Override
	public void updateApelacion(Apelaciones apelacionobj){
		apelacionesDAO.updateApelacion(apelacionobj);
	}

	@Override
	public void deleteApelacion(int apelacionid){
		apelacionesDAO.deleteApelacion(apelacionid);
	}
}