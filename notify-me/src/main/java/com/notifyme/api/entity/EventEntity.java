package com.notifyme.api.entity;

import org.bson.Document;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EventEntity {

	@Id
	private String eventId;

	private String userName;

	private String eventName;

	private String eventType;

	private String eventDate;

	private int notificationDays;

	private boolean status;

	private String createdDate;

	public Document createDocumentFromEntity(EventEntity eventEntity) {
		Document document = new Document("userName", eventEntity.getUserName())
				.append("eventName", eventEntity.getEventName()).append("eventType", eventEntity.getEventType())
				.append("eventDate", eventEntity.getEventDate())
				.append("notificationDays", eventEntity.getNotificationDays()).append("status", eventEntity.isStatus())
				.append("createdDate", eventEntity.getCreatedDate());
		return document;
	}

	public EventEntity createEntityFromDocument(Document document) {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setEventId(String.valueOf(document.get("_id")));
		eventEntity.setUserName(String.valueOf(document.get("userName")));
		eventEntity.setEventName(String.valueOf(document.get("eventName")));
		eventEntity.setEventType(String.valueOf(document.get("eventType")));
		eventEntity.setEventDate(String.valueOf(document.get("eventDate")));
		eventEntity.setNotificationDays(Integer.parseInt(String.valueOf(document.get("notificationDays"))));
		eventEntity.setStatus(Boolean.valueOf(String.valueOf(document.get("status"))));
		eventEntity.setCreatedDate(String.valueOf(document.get("createdDate")));
		return eventEntity;
	}
}
