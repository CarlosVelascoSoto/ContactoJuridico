package com.aj.service;

import java.util.List;
import com.aj.model.Materias;

public interface MateriasService{
	public Integer addNewMateria(Materias materiaobj);
	public Materias getMateriaById(Long materiaid);
	public void updateMateria(Materias materiaobj);
	public void deleteMateria(Long materiaid);
	public List<Materias> getAll(String query);
}