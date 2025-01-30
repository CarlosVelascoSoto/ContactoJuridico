package com.aj.dao;

import java.util.List;
import com.aj.model.Circuitos;

public interface CircuitosDAO{
	public Integer addNewCircuit(Circuitos circuitoobj);
	public Circuitos getCircuitById(Long circuitoid);
	public void updateCircuit(Circuitos circuitoobj);
	public void deleteCircuit(Long circuitoid);
	public List<Circuitos> getAll(String query);
}