package com.aj.service;

import java.util.List;
import com.aj.model.Menu;

public interface MenuService{
	public Integer addNewMenu(Menu menuobj); 
	public Menu getMenuById(int menuid);
	public void updateMenu(Menu menuobj);
	public void deleteMenu(int menuid);
	public List<Menu> getAll(String query);
}