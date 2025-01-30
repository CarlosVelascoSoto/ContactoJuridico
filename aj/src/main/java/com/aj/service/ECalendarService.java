package com.aj.service;

import java.util.List;
import com.aj.model.ECalendar;

public interface ECalendarService {
	public Integer addNewCalendarEvent(ECalendar ecalendarObject);
	public List<ECalendar> getAll(String query);
	public void updateCalendar(ECalendar ecalendarObject);
	public void deleteCalendar(int ecalendarid);
}
