package com.aj.dao;

import java.util.List;
import com.aj.model.TipoActuacion;

public interface TipoActuacionDAO{
	public Integer addNewTipoActuacion(TipoActuacion tipoactobj); 
	public TipoActuacion getTipoActuacionById(int tipoactid);
	public void updateTipoActuacion(TipoActuacion tipoactobj);
	public void deleteTipoActuacion(int tipoactid);
	public List<TipoActuacion> getAll(String query);
}