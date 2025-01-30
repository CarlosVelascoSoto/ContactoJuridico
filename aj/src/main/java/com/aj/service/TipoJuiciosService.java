package com.aj.service;

import java.util.List;
import com.aj.model.TipoJuicios;

public interface TipoJuiciosService{
	public Integer addNewTipoJuicio(TipoJuicios tipojuicioobj);
	public TipoJuicios getTipoJuicioById(Long tipojuicioid);
	public void updateTipoJuicio(TipoJuicios tipojuicioobj);
	public void deleteTipoJuicio(Long tipojuicioid);
	public List<TipoJuicios> getAll(String query);
}