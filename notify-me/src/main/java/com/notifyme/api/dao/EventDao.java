package com.notifyme.api.dao;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

public interface EventDao {

	boolean saveEvent(Document document);

	boolean updateEvent(Document document, ObjectId eventId);

	boolean deleteEvent(ObjectId eventId);

	List<Document> getAllEvents();

	Document getEventById(ObjectId eventId);

	List<Document> getEventsByUserName(String userName);
}
