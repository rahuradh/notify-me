package com.notifyme.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notifyme.api.entity.EventEntity;
import com.notifyme.api.service.EventService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/event")
public class EventController {

	@Autowired
	private EventService eventService;

	@PostMapping("/addNewEvent")
	public boolean saveEvent(@RequestBody EventEntity eventEntity) {
		return eventService.addNewEvent(eventEntity);
	}

	@PostMapping("/updateEvent")
	public boolean updateEvent(@RequestBody EventEntity eventEntity) {
		return eventService.updateEvent(eventEntity);
	}

	@PostMapping("/deleteEvent")
	public boolean deleteEvent(@RequestBody EventEntity eventEntity) {
		return eventService.deleteEvent(eventEntity.getEventId());
	}

	@GetMapping("/getAllEvents")
	public List<EventEntity> getAllEvents() {
		return eventService.getAllEvents();
	}

	@GetMapping("/getEventsByUserName")
	public List<EventEntity> getEventsByUserName(@RequestParam String userName) {
		System.out.println("Reached Controller" + userName);
		return eventService.getEventsByUserName(userName);
	}

	@GetMapping("/getEventById")
	public EventEntity getEventById(@RequestParam String eventId) {
		return eventService.getEventById(eventId);
	}

	@GetMapping("/sendWhatsAppMessage")
	public boolean sendMessage() {
		return eventService.sendNotificationMessage();
	}

}
