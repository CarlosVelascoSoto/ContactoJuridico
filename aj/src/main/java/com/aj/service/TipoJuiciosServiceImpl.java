package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.TipoJuiciosDAO;
import com.aj.model.TipoJuicios;

@Service("tipojuicioService")
@Transactional
public class TipoJuiciosServiceImpl implements TipoJuiciosService{
	@Autowired
	private TipoJuiciosDAO tipojuiciosDAO;
	
	@Override
	public Integer addNewTipoJuicio(TipoJuicios tipojuicioobj){
		return tipojuiciosDAO.addNewTipoJuicio(tipojuicioobj);
	}
	
	@Override
	public List<TipoJuicios> getAll(String query){
		return tipojuiciosDAO.getAll(query);
	}
	
	@Override
	public TipoJuicios getTipoJuicioById(Long gettipojuicioid){
		return tipojuiciosDAO.getTipoJuicioById(gettipojuicioid);
	}

	@Override
	public void updateTipoJuicio(TipoJuicios tipojuicioobj){
		tipojuiciosDAO.updateTipoJuicio(tipojuicioobj);
	}
	
	@Override
	public void deleteTipoJuicio(Long tipojuicioid){
		tipojuiciosDAO.deleteTipoJuicio(tipojuicioid);
	}
}