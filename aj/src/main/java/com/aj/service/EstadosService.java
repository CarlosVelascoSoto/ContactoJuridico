package com.aj.service;

import java.util.List;
import com.aj.model.Estados;

public interface EstadosService{
	public Integer addNewEstado(Estados estadoobj);
	public Estados getEstadoById(Long estadoid);
	public void updateEstado(Estados estadoobj);
	public void deleteEstado(Long estadoid);
	public List<Estados> getAll(String query);
}