package com.aj.dao;

import java.util.List;

import com.aj.model.Juicios;
import com.aj.model.Uploadfiles;

public interface JuiciosDAO{
	public int addNewJuicio(Juicios juicioobj);
	public Juicios getJuicioById(int juicioid);
	public void updateJuicioDetails(Juicios juicioobj);
	public void deleteJuicio(int juicioid);
	public List<Juicios> getAll(String query);
	public int addUploaderFile(Uploadfiles file);
	public int updateFixUrl(Uploadfiles obj);
	public int deleteFixUrl(int id);
	public List<String> getTableColumnNames(String table, String leaveOutColumns);
	public List<?> getInfo(String query);
}