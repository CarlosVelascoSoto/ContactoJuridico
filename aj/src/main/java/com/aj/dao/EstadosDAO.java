package com.aj.dao;

import java.util.List;
import com.aj.model.Estados;

public interface EstadosDAO{
	public Integer addNewEstado(Estados estadoobj);
	public Estados getEstadoById(Long estadoid);
	public void updateEstado(Estados estadoobj);
	public void deleteEstado(Long estadoid);
	public List<Estados> getAll(String query);
}