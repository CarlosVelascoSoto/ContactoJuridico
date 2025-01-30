package com.aj.dao;

import java.util.List;
import com.aj.model.Recursos;

public interface RecursosDAO{
	public Integer addNewRecurso(Recursos recursoobj); 
	public Recursos getRecursoById(int recursoid);
	public void updateRecurso(Recursos recursoobj);
	public void deleteRecurso(int recursoid);
	public List<Recursos> getAll(String query);
}