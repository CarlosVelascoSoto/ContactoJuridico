package com.aj.dao;

import java.util.List;
import com.aj.model.Movimientos;

public interface MovimientosDAO{
	public Integer addNewMovto(Movimientos movObj); 
	public Movimientos getMovtoById(Long movId);
	public void updateMovto(Movimientos movObj);
	public void deleteMovto(Long movId);
	public List<Movimientos> getAll(String query);
}