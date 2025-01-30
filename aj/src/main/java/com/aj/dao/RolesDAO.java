package com.aj.dao;

import java.util.List;
import com.aj.model.Roles;

public interface RolesDAO{
	public Integer addNewRole(Roles rolename);
	public Roles getRoleById(Long roleid);
	public void updateRoleDetails(Roles roleid);
	public void deleteRole(Long roleid);
	public List<Roles> getAll(String query);
}