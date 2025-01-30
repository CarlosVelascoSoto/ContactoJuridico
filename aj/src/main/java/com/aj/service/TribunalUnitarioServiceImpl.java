package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.TribunalUnitarioDAO;
import com.aj.model.TribunalUnitario;

@Service("tribunalunitarioService")
@Transactional
public class TribunalUnitarioServiceImpl implements TribunalUnitarioService{
	@Autowired
	private TribunalUnitarioDAO tribunalcolegiadoDAO;
	
	@Override
	public Integer addNewTribunalUnit(TribunalUnitario tribunalid){
		return tribunalcolegiadoDAO.addNewTribunalUnit(tribunalid);
	}
	
	@Override
	public List<TribunalUnitario> getAll(String query){
		return tribunalcolegiadoDAO.getAll(query);
	}
	
	@Override
	public TribunalUnitario getTribunalUnitById(Long tribunalid){
		return tribunalcolegiadoDAO.getTribunalUnitById(tribunalid);
	}

	@Override
	public void updateTribunalUnit(TribunalUnitario tribunalid){
		tribunalcolegiadoDAO.updateTribunalUnit(tribunalid);
	}
	
	@Override
	public void deleteTribunalUnit(Long tribunalid){
		tribunalcolegiadoDAO.deleteTribunalUnit(tribunalid);
	}
}