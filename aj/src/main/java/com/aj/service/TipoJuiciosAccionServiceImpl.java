package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.TipoJuiciosAccionDAO;
import com.aj.model.TipoJuiciosAccion;

@Service("tipojuiciosaccionService")
@Transactional
public class TipoJuiciosAccionServiceImpl implements TipoJuiciosAccionService{
	@Autowired
	private TipoJuiciosAccionDAO tipojuiciosaccionDAO;
	
	@Override
	public Integer addNewAccion(TipoJuiciosAccion accionobj){
		return tipojuiciosaccionDAO.addNewAccion(accionobj);
	}
	
	@Override
	public List<TipoJuiciosAccion> getAll(String query){
		return tipojuiciosaccionDAO.getAll(query);
	}
	
	@Override
	public TipoJuiciosAccion getAccionById(Long getaccionid){
		return tipojuiciosaccionDAO.getAccionById(getaccionid);
	}

	@Override
	public void updateAccion(TipoJuiciosAccion accionobj){
		tipojuiciosaccionDAO.updateAccion(accionobj);
	}
	
	@Override
	public void deleteAccion(Long accionid){
		tipojuiciosaccionDAO.deleteAccion(accionid);
	}
}