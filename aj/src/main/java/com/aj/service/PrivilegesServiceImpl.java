package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.dao.PrivilegesDAO;
import com.aj.model.Privileges;


@Service("privilegeService")
@Transactional
public class PrivilegesServiceImpl implements PrivilegesService{
	@Autowired
	private PrivilegesDAO privilegesDAO;

	@Override
	public Integer addNewPrivilege(Privileges privileges){
		return privilegesDAO.addNewPrivilege(privileges);
	}

	@Override
	public List<Privileges> getAll(String query){
		return privilegesDAO.getAll(query);
	}

	@Override
	public Privileges getPrivilegeById(Long getprivilegesid){
		return privilegesDAO.getPrivilegeById(getprivilegesid);
	}

	@Override
	public void updatePrivilege(Privileges setprivileges){
		privilegesDAO.updatePrivilege(setprivileges);
	}

	@Override
	public void deletePrivilege(Long privilegeid){
		privilegesDAO.deletePrivilege(privilegeid);
	}
}