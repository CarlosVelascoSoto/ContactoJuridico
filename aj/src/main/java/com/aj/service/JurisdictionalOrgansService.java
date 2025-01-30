package com.aj.service;

import java.util.List;
import com.aj.model.JurisdictionalOrgans;

public interface JurisdictionalOrgansService{
	public Integer addNewJrdOrgan(JurisdictionalOrgans jrdorganobj);
	public JurisdictionalOrgans getJrdOrganById(Long jrdorganid);
	public void updateJrdOrgan(JurisdictionalOrgans jrdorganobj);
	public void deleteJrdOrgan(Long jrdorganid);
	public List<JurisdictionalOrgans> getAll(String query);
}