package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.TribunalColegiadoDAO;
import com.aj.model.TribunalColegiado;

@Service("tribunalcolegiadoService")
@Transactional
public class TribunalColegiadoServiceImpl implements TribunalColegiadoService{
	@Autowired
	private TribunalColegiadoDAO tribunalcolegiadoDAO;
	
	@Override
	public Integer addNewTribunalC(TribunalColegiado tribunalid){
		return tribunalcolegiadoDAO.addNewTribunalC(tribunalid);
	}
	
	@Override
	public List<TribunalColegiado> getAll(String query){
		return tribunalcolegiadoDAO.getAll(query);
	}
	
	@Override
	public TribunalColegiado getTribunalCById(Long tribunalid){
		return tribunalcolegiadoDAO.getTribunalCById(tribunalid);
	}

	@Override
	public void updateTribunalC(TribunalColegiado tribunalid){
		tribunalcolegiadoDAO.updateTribunalC(tribunalid);
	}
	
	@Override
	public void deleteTribunalC(Long tribunalid){
		tribunalcolegiadoDAO.deleteTribunalC(tribunalid);
	}
}