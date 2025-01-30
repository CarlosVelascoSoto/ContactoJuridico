package com.aj.service;

import java.util.List;
import com.aj.model.Consultas;

public interface ConsultasService {
	public Integer addNewConsulta(Consultas consultasobj); 
	public Consultas getConsultaById(int consultaid);
	public void updateConsulta(Consultas consultaobj);
	public void deleteConsulta(int consultaobj);
	public List<Consultas> getAll(String query);
}