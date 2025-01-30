package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.RelatedColumnsDAO;
import com.aj.model.RelatedColumns;

@Service("RelatedColumnsService")
@Transactional
public class RelatedColumnsServiceImpl implements RelatedColumnsService{
	@Autowired
	private RelatedColumnsDAO RelatedColumnsDAO;
	
	@Override
	public Integer addNewRelatedCol(RelatedColumns relatedcolobj){
		return RelatedColumnsDAO.addNewRelatedCol(relatedcolobj);
	}
	
	@Override
	public List<RelatedColumns> getAll(String query){
		return RelatedColumnsDAO.getAll(query);
	}
	
	@Override
	public RelatedColumns getRelatedColById(Long getrelatedcolid){
		return RelatedColumnsDAO.getRelatedColById(getrelatedcolid);
	}

	@Override
	public void updateRelatedCol(RelatedColumns relatedcolobj){
		RelatedColumnsDAO.updateRelatedCol(relatedcolobj);
	}
	
	@Override
	public void deleteRelatedCol(Long relatedcolid){
		RelatedColumnsDAO.deleteRelatedCol(relatedcolid);
	}
}