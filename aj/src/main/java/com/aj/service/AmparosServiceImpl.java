package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.AmparosDAO;
import com.aj.model.Amparos;

@Service("amparoService")
@Transactional
public class AmparosServiceImpl implements AmparosService{
	@Autowired
	private AmparosDAO amparosDAO;

	@Override
	public Integer addNewAmparo(Amparos amparoobj){
		return amparosDAO.addNewAmparo(amparoobj);
	}

	@Override
	public List<Amparos> getAll(String query){
		return amparosDAO.getAll(query);
	}

	@Override
	public Amparos getAmparoById(int amparoid){
		return amparosDAO.getAmparoById(amparoid);
	}

	@Override
	public void updateAmparo(Amparos amparoobj){
		amparosDAO.updateAmparo(amparoobj);
	}

	@Override
	public void deleteAmparo(int amparoid){
		amparosDAO.deleteAmparo(amparoid);
	}
}