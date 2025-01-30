package com.aj.service;

import java.util.List;
import com.aj.model.Juzgados;

public interface JuzgadosService{
	public Integer addNewJuzgado(Juzgados juzgadoobj);
	public Juzgados getJuzgadoById(Long juzgadoid);
	public void updateJuzgado(Juzgados juzgadoobj);
	public void deleteJuzgado(Long juzgadoid);
	public List<Juzgados> getAll(String query);
}