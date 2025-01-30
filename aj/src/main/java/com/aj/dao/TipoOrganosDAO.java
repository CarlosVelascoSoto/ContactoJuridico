package com.aj.dao;

import java.util.List;
import com.aj.model.TipoOrganos;

public interface TipoOrganosDAO{
	public Integer addNewTipoOrgano(TipoOrganos tipoorganoobj);
	public TipoOrganos getTipoOrganoById(Long tipoorganoid);
	public void updateTipoOrgano(TipoOrganos tipoorganoobj);
	public void deleteTipoOrgano(Long tipoorganoid);
	public List<TipoOrganos> getAll(String query);
}