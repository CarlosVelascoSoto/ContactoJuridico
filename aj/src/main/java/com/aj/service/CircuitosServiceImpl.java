package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.CircuitosDAO;
import com.aj.model.Circuitos;

@Service("circuitoService")
@Transactional
public class CircuitosServiceImpl implements CircuitosService{
	@Autowired
	private CircuitosDAO circuitosDAO;
	
	@Override
	public Integer addNewCircuit(Circuitos circuitoobj){
		return circuitosDAO.addNewCircuit(circuitoobj);
	}
	
	@Override
	public List<Circuitos> getAll(String query){
		return circuitosDAO.getAll(query);
	}
	
	@Override
	public Circuitos getCircuitById(Long getcircuitoid){
		return circuitosDAO.getCircuitById(getcircuitoid);
	}

	@Override
	public void updateCircuit(Circuitos circuitoobj){
		circuitosDAO.updateCircuit(circuitoobj);
	}
	
	@Override
	public void deleteCircuit(Long circuitoid){
		circuitosDAO.deleteCircuit(circuitoid);
	}
}