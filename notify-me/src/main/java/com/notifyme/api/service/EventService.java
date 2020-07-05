package com.notifyme.api.service;

import java.util.List;

import com.notifyme.api.entity.EventEntity;

public interface EventService {

	boolean addNewEvent(EventEntity eventEntity);

	boolean updateEvent(EventEntity eventEntity);

	boolean deleteEvent(String eventId);

	List<EventEntity> getAllEvents();

	List<EventEntity> getEventsByUserName(String userName);

	EventEntity getEventById(String eventId);

	boolean sendNotificationMessage();
}
