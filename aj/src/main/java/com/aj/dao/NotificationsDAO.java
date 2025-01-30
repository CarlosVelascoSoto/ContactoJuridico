package com.aj.dao;

import java.util.List;
import com.aj.model.Notifications;

public interface NotificationsDAO{
	public Integer addNewNotification(Notifications notificationobj);
	public Notifications getNotificationById(Long notificationid);
	public void updateNotification(Notifications notificationobj);
	public void deleteNotification(Long notificationid);
	public List<Notifications> getAll(String query);
}