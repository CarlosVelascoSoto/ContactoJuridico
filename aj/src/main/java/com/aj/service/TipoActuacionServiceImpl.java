package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.TipoActuacionDAO;
import com.aj.model.TipoActuacion;

@Service("tipoactService")
@Transactional
public class TipoActuacionServiceImpl implements TipoActuacionService{
	@Autowired
	private TipoActuacionDAO tipoactsDAO;

	@Override
	public Integer addNewTipoActuacion(TipoActuacion tipoactobj){
		return tipoactsDAO.addNewTipoActuacion(tipoactobj);
	}

	@Override
	public List<TipoActuacion> getAll(String query){
		return tipoactsDAO.getAll(query);
	}

	@Override
	public TipoActuacion getTipoActuacionById(int tipoactid){
		return tipoactsDAO.getTipoActuacionById(tipoactid);
	}

	@Override
	public void updateTipoActuacion(TipoActuacion tipoactobj){
		tipoactsDAO.updateTipoActuacion(tipoactobj);
	}

	@Override
	public void deleteTipoActuacion(int tipoactid){
		tipoactsDAO.deleteTipoActuacion(tipoactid);
	}
}