package com.aj.service;

import java.util.List;
import com.aj.model.CommunicationTypes;

public interface CommunicationTypesService{
	public Integer addNewCommType(CommunicationTypes commtypeobj);
	public CommunicationTypes getCommTypeById(Long commtypeid);
	public void updateCommType(CommunicationTypes commtypeobj);
	public void deleteCommType(Long commtypeid);
	public List<CommunicationTypes> getAll(String query);
}