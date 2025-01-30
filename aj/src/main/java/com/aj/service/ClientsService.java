package com.aj.service;

import java.util.List;
import com.aj.model.Clients;

public interface ClientsService{
	public Integer addNewClient(Clients clientobj); 
	public Clients getClientById(int clientid);
	public void updateClient(Clients clientobj);
	public void deleteClient(int clientid);
	public List<Clients> getAll(String query);
}