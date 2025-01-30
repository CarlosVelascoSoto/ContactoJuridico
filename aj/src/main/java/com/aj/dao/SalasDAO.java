package com.aj.dao;

import java.util.List;
import com.aj.model.Salas;

public interface SalasDAO{
	public Integer addNewSala(Salas salaobj);
	public Salas getSalaById(Long salaid);
	public void updateSala(Salas salaobj);
	public void deleteSala(Long salaid);
	public List<Salas> getAll(String query);
}