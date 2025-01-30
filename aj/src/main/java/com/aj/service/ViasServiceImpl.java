package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.ViasDAO;
import com.aj.model.Vias;

@Service("viaService")
@Transactional
public class ViasServiceImpl implements ViasService{
	@Autowired
	private ViasDAO viasDAO;
	
	@Override
	public Integer addNewVia(Vias viaobj){
		return viasDAO.addNewVia(viaobj);
	}
	
	@Override
	public List<Vias> getAll(String query){
		return viasDAO.getAll(query);
	}
	
	@Override
	public Vias getViaById(Long getviaid){
		return viasDAO.getViaById(getviaid);
	}

	@Override
	public void updateVia(Vias viaobj){
		viasDAO.updateVia(viaobj);
	}
	
	@Override
	public void deleteVia(Long viaid){
		viasDAO.deleteVia(viaid);
	}
}