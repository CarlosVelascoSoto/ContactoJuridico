package com.aj.dao;

import java.util.List;
import com.aj.model.Apelaciones;

public interface ApelacionesDAO{
	public Integer addNewApelacion(Apelaciones apelacionobj); 
	public Apelaciones getApelacionById(int apelacionid);
	public void updateApelacion(Apelaciones apelacionobj);
	public void deleteApelacion(int apelacionid);
	public List<Apelaciones> getAll(String query);
}