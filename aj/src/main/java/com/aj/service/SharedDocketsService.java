package com.aj.service;

import java.util.List;
import com.aj.model.SharedDockets;

public interface SharedDocketsService{
	public int addNewSharedDocket(SharedDockets subprivilegeobj);
	public SharedDockets getSharedDocketById(Long subprivilegeid);
	public void updateSharedDocket(SharedDockets subprivilegeid);
	public void deleteSharedDocket(Long subprivilegeid);
	public List<SharedDockets> getAll(String query);
	public int updateDeleteByQuery(String query);
}