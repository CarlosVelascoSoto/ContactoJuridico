package com.aj.dao;

import java.util.List;
import com.aj.model.Regiones;

public interface RegionesDAO{
	public Integer addNewRegion(Regiones regionobj);
	public Regiones getRegionById(Long regionid);
	public void updateRegion(Regiones regionobj);
	public void deleteRegion(Long regionid);
	public List<Regiones> getAll(String query);
}