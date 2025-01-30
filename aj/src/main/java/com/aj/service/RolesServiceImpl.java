package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.RolesDAO;
import com.aj.model.Roles;

@Service("rolesService")
@Transactional
public class RolesServiceImpl implements RolesService{
	@Autowired
	private RolesDAO rolesDAO;
	
	@Override
	public Integer addNewRole(Roles rolename) {
		return rolesDAO.addNewRole(rolename);
	}
	
	@Override
	public List<Roles> getAll(String query){
		return rolesDAO.getAll(query);
	}
	
	@Override
	public Roles getRoleById(Long getrole){
		return rolesDAO.getRoleById(getrole);
	}

	@Override
	public void updateRoleDetails(Roles setrole){
		rolesDAO.updateRoleDetails(setrole);
	}
	
	@Override
	public void deleteRole(Long roleid) {
		rolesDAO.deleteRole(roleid);
	}
}