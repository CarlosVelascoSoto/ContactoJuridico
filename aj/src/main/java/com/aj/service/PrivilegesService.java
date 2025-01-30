package com.aj.service;

import java.util.List;

import com.aj.model.Privileges;

public interface PrivilegesService{
	public Integer addNewPrivilege(Privileges privileges);
	public Privileges getPrivilegeById(Long privilegesid);
	public void updatePrivilege(Privileges privileges);
	public void deletePrivilege(Long privilegeid);
	public List<Privileges> getAll(String query);
}