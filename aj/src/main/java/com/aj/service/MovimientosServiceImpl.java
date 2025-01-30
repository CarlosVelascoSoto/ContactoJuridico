package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.MovimientosDAO;
import com.aj.model.Movimientos;

@Service("movimientosService")
@Transactional
public class MovimientosServiceImpl implements MovimientosService{
	@Autowired
	private MovimientosDAO movimientosDAO;

	@Override
	public Integer addNewMovto(Movimientos movObj){
		return movimientosDAO.addNewMovto(movObj);
	}

	@Override
	public Movimientos getMovtoById(Long movId){
		return movimientosDAO.getMovtoById(movId);
	}

	@Override
	public void updateMovto(Movimientos movObj){
		movimientosDAO.updateMovto(movObj);
	}

	@Override
	public void deleteMovto(Long movId){
		movimientosDAO.deleteMovto(movId);
	}

	@Override
	public List<Movimientos> getAll(String query){
		return movimientosDAO.getAll(query);
	}
}