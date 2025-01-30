package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.SharedDocketsDAO;
import com.aj.model.SharedDockets;

@Service("shareddocketService")
@Transactional
public class SharedDocketsServiceImpl implements SharedDocketsService{
	@Autowired
	private SharedDocketsDAO shareddocketsDAO;

	@Override
	public int addNewSharedDocket(SharedDockets sharedprivilegename) {
		return shareddocketsDAO.addNewSharedDocket(sharedprivilegename);
	}

	@Override
	public List<SharedDockets> getAll(String query){
		return shareddocketsDAO.getAll(query);
	}

	@Override
	public SharedDockets getSharedDocketById(Long sharedprivilege){
		return shareddocketsDAO.getSharedDocketById(sharedprivilege);
	}

	@Override
	public void updateSharedDocket(SharedDockets sharedprivilegeid){
		shareddocketsDAO.updateSharedDocket(sharedprivilegeid);
	}

	@Override
	public void deleteSharedDocket(Long sharedprivilegeid){
		shareddocketsDAO.deleteSharedDocket(sharedprivilegeid);
	}

	@Override
	public int updateDeleteByQuery(String query){
		return shareddocketsDAO.updateDeleteByQuery(query);
	}
}