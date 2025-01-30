package com.aj.service;

import java.util.List;
import com.aj.model.RelatedColumns;

public interface RelatedColumnsService{
	public Integer addNewRelatedCol(RelatedColumns relatedcolobj);
	public RelatedColumns getRelatedColById(Long relatedcolid);
	public void updateRelatedCol(RelatedColumns relatedcolobj);
	public void deleteRelatedCol(Long relatedcolid);
	public List<RelatedColumns> getAll(String query);
}