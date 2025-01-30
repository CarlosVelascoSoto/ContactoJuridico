package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.CommLabelsDAO;
import com.aj.model.CommunicationLabels;

@Service("commlabelService")
@Transactional
public class CommLabelsServiceImpl implements CommLabelsService{
	@Autowired
	private CommLabelsDAO commlabelsDAO;
	
	@Override
	public Integer addNewCommLabel(CommunicationLabels commlabelobj){
		return commlabelsDAO.addNewCommLabel(commlabelobj);
	}
	
	@Override
	public List<CommunicationLabels> getAll(String query){
		return commlabelsDAO.getAll(query);
	}
	
	@Override
	public CommunicationLabels getCommLabelById(Long getcommlabelid){
		return commlabelsDAO.getCommLabelById(getcommlabelid);
	}

	@Override
	public void updateCommLabel(CommunicationLabels commlabelobj){
		commlabelsDAO.updateCommLabel(commlabelobj);
	}
	
	@Override
	public void deleteCommLabel(Long commlabelid){
		commlabelsDAO.deleteCommLabel(commlabelid);
	}
}