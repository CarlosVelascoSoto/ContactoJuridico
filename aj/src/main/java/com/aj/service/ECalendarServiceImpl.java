package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.ECalendarDAO;
import com.aj.model.ECalendar;

@Service("eCalendarService")
@Transactional
public class ECalendarServiceImpl implements ECalendarService {
	@Autowired
	private ECalendarDAO ecalendarDAO;
		
	@Override
	public Integer addNewCalendarEvent(ECalendar ecalendarObject) {
		return ecalendarDAO.addNewCalendarEvent(ecalendarObject);
	}
	
	@Override
	public List<ECalendar> getAll(String query){
		return ecalendarDAO.getAll(query);
	}

	@Override
	public void updateCalendar(ECalendar ecalendarObject) {
		ecalendarDAO.updateCalendar(ecalendarObject);
	}

	@Override
	public void deleteCalendar(int ecalendarid){
		ecalendarDAO.deleteCalendar(ecalendarid);
	}

}