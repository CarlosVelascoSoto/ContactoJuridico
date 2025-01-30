package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.NotificationsDAO;
import com.aj.model.Notifications;

@Service("NotificationsService")
@Transactional
public class NotificationsServiceImpl implements NotificationsService{
	@Autowired
	private NotificationsDAO NotificationsDAO;
	
	@Override
	public Integer addNewNotification(Notifications notificationobj){
		return NotificationsDAO.addNewNotification(notificationobj);
	}
	
	@Override
	public List<Notifications> getAll(String query){
		return NotificationsDAO.getAll(query);
	}
	
	@Override
	public Notifications getNotificationById(Long getnotificationid){
		return NotificationsDAO.getNotificationById(getnotificationid);
	}

	@Override
	public void updateNotification(Notifications notificationobj){
		NotificationsDAO.updateNotification(notificationobj);
	}
	
	@Override
	public void deleteNotification(Long notificationid){
		NotificationsDAO.deleteNotification(notificationid);
	}
}