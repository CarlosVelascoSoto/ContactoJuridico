package com.aj.service;

import java.util.List;
import com.aj.model.TipoJuiciosAccion;

public interface TipoJuiciosAccionService{
	public Integer addNewAccion(TipoJuiciosAccion accionobj);
	public TipoJuiciosAccion getAccionById(Long accionid);
	public void updateAccion(TipoJuiciosAccion accionobj);
	public void deleteAccion(Long accionid);
	public List<TipoJuiciosAccion> getAll(String query);
}