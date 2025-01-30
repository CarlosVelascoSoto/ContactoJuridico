package com.aj.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aj.dao.ConsultasDAO;
import com.aj.model.Consultas;

@Service("ConsultasService")
@Transactional
public class ConsultasServiceImpl implements ConsultasService{
	@Autowired
	private ConsultasDAO consultasDAO;

	@Override
	public Integer addNewConsulta(Consultas consultasobj) {
		return consultasDAO.addNewConsulta(consultasobj);
	}

	@Override
	public Consultas getConsultaById(int consultaid) {
		return consultasDAO.getConsultaById(consultaid);
	}

	@Override
	public void updateConsulta(Consultas consultaobj) {
		consultasDAO.updateconsulta(consultaobj);
	}

	@Override
	public void deleteConsulta(int consultaid) {
		 consultasDAO.deleteConsulta(consultaid);	
	}

	@Override
	public List<Consultas> getAll(String query) {
		return consultasDAO.getAll(query);
	}


}