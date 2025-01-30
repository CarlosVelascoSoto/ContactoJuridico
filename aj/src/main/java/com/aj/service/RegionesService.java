package com.aj.service;

import java.util.List;
import com.aj.model.Regiones;

public interface RegionesService{
	public Integer addNewRegion(Regiones regionobj);
	public Regiones getRegionById(Long regionid);
	public void updateRegion(Regiones regionobj);
	public void deleteRegion(Long regionid);
	public List<Regiones> getAll(String query);
}