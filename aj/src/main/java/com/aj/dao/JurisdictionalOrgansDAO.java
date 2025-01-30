package com.aj.dao;

import java.util.List;
import com.aj.model.JurisdictionalOrgans;

public interface JurisdictionalOrgansDAO{
	public Integer addNewJrdOrgan(JurisdictionalOrgans jrdorganobj);
	public JurisdictionalOrgans getJrdOrganById(Long jrdorganid);
	public void updateJrdOrgan(JurisdictionalOrgans jrdorganobj);
	public void deleteJrdOrgan(Long jrdorganid);
	public List<JurisdictionalOrgans> getAll(String query);
}