package com.aj.service;

import java.util.List;
import com.aj.model.Apelaciones;

public interface ApelacionesService{
	public Integer addNewApelacion(Apelaciones apelacionobj); 
	public Apelaciones getApelacionById(int apelacionid);
	public void updateApelacion(Apelaciones apelacionobj);
	public void deleteApelacion(int apelacionid);
	public List<Apelaciones> getAll(String query);
}