package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.RegionesDAO;
import com.aj.model.Regiones;

@Service("regionService")
@Transactional
public class RegionesServiceImpl implements RegionesService{
	@Autowired
	private RegionesDAO regionesDAO;
	
	@Override
	public Integer addNewRegion(Regiones regionobj){
		return regionesDAO.addNewRegion(regionobj);
	}
	
	@Override
	public List<Regiones> getAll(String query){
		return regionesDAO.getAll(query);
	}
	
	@Override
	public Regiones getRegionById(Long getregionid){
		return regionesDAO.getRegionById(getregionid);
	}

	@Override
	public void updateRegion(Regiones regionobj){
		regionesDAO.updateRegion(regionobj);
	}
	
	@Override
	public void deleteRegion(Long regionid){
		regionesDAO.deleteRegion(regionid);
	}
}