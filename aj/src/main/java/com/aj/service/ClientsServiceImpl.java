package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.ClientsDAO;
import com.aj.model.Clients;

@Service("clientsService")
@Transactional
public class ClientsServiceImpl implements ClientsService{
	@Autowired
	private ClientsDAO clientsDAO;

	@Override
	public Integer addNewClient(Clients clientsobj){
		return clientsDAO.addNewClient(clientsobj);
	}

	@Override
	public Clients getClientById(int clientid){
		return clientsDAO.getClientById(clientid);
	}

	@Override
	public void updateClient(Clients clientsobj){
		clientsDAO.updateClient(clientsobj);
	}

	@Override
	public void deleteClient(int clientid){
		clientsDAO.deleteClient(clientid);
	}

	@Override
	public List<Clients> getAll(String query){
		return clientsDAO.getAll(query);
	}
}