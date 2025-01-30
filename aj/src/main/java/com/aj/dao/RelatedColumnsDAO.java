package com.aj.dao;

import java.util.List;

import com.aj.model.RelatedColumns;

public interface RelatedColumnsDAO{
	public Integer addNewRelatedCol(RelatedColumns relatedcolobj);
	public RelatedColumns getRelatedColById(Long relatedcolid);
	public void updateRelatedCol(RelatedColumns relatedcolobj);
	public void deleteRelatedCol(Long relatedcolid);
	public List<RelatedColumns> getAll(String query);
}