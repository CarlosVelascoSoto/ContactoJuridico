package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.TipoOrganosDAO;
import com.aj.model.TipoOrganos;

@Service("tipoorganoService")
@Transactional
public class TipoOrganosServiceImpl implements TipoOrganosService{
	@Autowired
	private TipoOrganosDAO tipoorganosDAO;
	
	@Override
	public Integer addNewTipoOrgano(TipoOrganos tipoorganoobj){
		return tipoorganosDAO.addNewTipoOrgano(tipoorganoobj);
	}
	
	@Override
	public List<TipoOrganos> getAll(String query){
		return tipoorganosDAO.getAll(query);
	}
	
	@Override
	public TipoOrganos getTipoOrganoById(Long gettipoorganoid){
		return tipoorganosDAO.getTipoOrganoById(gettipoorganoid);
	}

	@Override
	public void updateTipoOrgano(TipoOrganos tipoorganoobj){
		tipoorganosDAO.updateTipoOrgano(tipoorganoobj);
	}
	
	@Override
	public void deleteTipoOrgano(Long tipoorganoid){
		tipoorganosDAO.deleteTipoOrgano(tipoorganoid);
	}
}