package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.JuecesDAO;
import com.aj.model.Jueces;

@Service("juecesService")
@Transactional
public class JuecesServiceImpl implements JuecesService{
	@Autowired
	private JuecesDAO juecesDAO;
	
	@Override
	public Integer addNewJuez(Jueces juezobj){
		return juecesDAO.addNewJuez(juezobj);
	}
	
	@Override
	public List<Jueces> getAll(String query){
		return juecesDAO.getAll(query);
	}
	
	@Override
	public Jueces getJuezById(Long getjuezid){
		return juecesDAO.getJuezById(getjuezid);
	}

	@Override
	public void updateJuez(Jueces juezobj){
		juecesDAO.updateJuez(juezobj);
	}
	
	@Override
	public void deleteJuez(Long juezid){
		juecesDAO.deleteJuez(juezid);
	}
}