package com.aj.dao;

import java.util.List;
import com.aj.model.Vias;

public interface ViasDAO{
	public Integer addNewVia(Vias viaobj);
	public Vias getViaById(Long viaid);
	public void updateVia(Vias viaobj);
	public void deleteVia(Long viaid);
	public List<Vias> getAll(String query);
}