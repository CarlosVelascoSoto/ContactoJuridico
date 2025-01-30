package com.aj.dao;

import java.util.List;
import com.aj.model.Clients;

public interface ClientsDAO{
	public Integer addNewClient(Clients clientobj); 
	public Clients getClientById(int clientid);
	public void updateClient(Clients clientobj);
	public void deleteClient(int clientid);
	public List<Clients> getAll(String query);
}