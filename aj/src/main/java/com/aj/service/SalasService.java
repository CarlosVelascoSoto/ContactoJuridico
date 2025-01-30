package com.aj.service;

import java.util.List;
import com.aj.model.Salas;

public interface SalasService{
	public Integer addNewSala(Salas salaobj);
	public Salas getSalaById(Long salaid);
	public void updateSala(Salas salaobj);
	public void deleteSala(Long salaid);
	public List<Salas> getAll(String query);
}