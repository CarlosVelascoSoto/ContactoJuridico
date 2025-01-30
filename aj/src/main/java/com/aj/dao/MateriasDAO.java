package com.aj.dao;

import java.util.List;
import com.aj.model.Materias;

public interface MateriasDAO{
	public Integer addNewMateria(Materias materiaobj);
	public Materias getMateriaById(Long materiaid);
	public void updateMateria(Materias materiaobj);
	public void deleteMateria(Long materiaid);
	public List<Materias> getAll(String query);
}