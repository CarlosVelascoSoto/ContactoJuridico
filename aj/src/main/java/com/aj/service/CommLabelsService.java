package com.aj.service;

import java.util.List;
import com.aj.model.CommunicationLabels;

public interface CommLabelsService{
	public Integer addNewCommLabel(CommunicationLabels commlabelobj);
	public CommunicationLabels getCommLabelById(Long commlabelid);
	public void updateCommLabel(CommunicationLabels commlabelobj);
	public void deleteCommLabel(Long commlabelid);
	public List<CommunicationLabels> getAll(String query);
}