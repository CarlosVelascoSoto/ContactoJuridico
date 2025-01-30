package com.aj.dao;

import java.util.List;
import com.aj.model.Consultas;

public interface ConsultasDAO {
	public Integer addNewConsulta(Consultas consultabj); 
	public Consultas getConsultaById(int consultaid);
	public void updateconsulta(Consultas consultaobj);
	public void deleteConsulta(int consultaid);
	public List<Consultas> getAll(String query);}