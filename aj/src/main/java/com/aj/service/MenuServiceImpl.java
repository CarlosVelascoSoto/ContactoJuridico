package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.dao.MenuDAO;
import com.aj.model.Menu;

@Service("menuService")
@Transactional
public class MenuServiceImpl implements MenuService{
	@Autowired
	private MenuDAO menuDAO;
	
	@Override
	public Integer addNewMenu(Menu menuobj){
		return menuDAO.addNewMenu(menuobj);
	}
	
	@Override
	public List<Menu> getAll(String query){
		return menuDAO.getAll(query);
	}
	
	@Override
	public Menu getMenuById(int menuid){
		return menuDAO.getMenuById(menuid);
	}

	@Override
	public void updateMenu(Menu menuobj){
		menuDAO.updateMenu(menuobj);
	}
	
	@Override
	public void deleteMenu(int menuid){
		menuDAO.deleteMenu(menuid);
	}
}