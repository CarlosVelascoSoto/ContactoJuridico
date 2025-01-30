package com.aj.dao;

import java.util.List;
import com.aj.model.Companyclients;

public interface CompanyclientsDAO{
	public Integer addNewCClient(Companyclients cclientobj); 
	public Companyclients getCClientById(int cclientid);
	public void updateCClient(Companyclients cclientobj);
	public void deleteCClient(int cclientid);
	public List<Companyclients> getAll(String query);
}