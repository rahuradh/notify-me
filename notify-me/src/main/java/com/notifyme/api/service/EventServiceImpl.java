package com.notifyme.api.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.notifyme.api.dao.EventDao;
import com.notifyme.api.entity.EventEntity;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventDao eventDao;

    @Override
    public boolean addNewEvent(EventEntity eventEntity) {
        Document document = eventEntity.createDocumentFromEntity(eventEntity);
        return eventDao.saveEvent(document);
    }

    @Override
    public boolean updateEvent(EventEntity eventEntity) {
        Document document = eventEntity.createDocumentFromEntity(eventEntity);
        return eventDao.updateEvent(document, new ObjectId(eventEntity.getEventId()));
    }

    @Override
    public boolean deleteEvent(String eventId) {
        return eventDao.deleteEvent(new ObjectId(eventId));
    }

    @Override
    public List<EventEntity> getAllEvents() {
        List<Document> documentList = eventDao.getAllEvents();
        return formEventEntityList(documentList);
    }

    @Override
    public List<EventEntity> getEventsByUserName(String userName) {
        List<Document> documentList = eventDao.getEventsByUserName(userName);
        return formEventEntityList(documentList);
    }

    private List<EventEntity> formEventEntityList(List<Document> documentList) {
        List<EventEntity> eventEntityList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(documentList)) {
            eventEntityList = documentList.stream().filter(Objects::nonNull).map(document -> {
                EventEntity eventEntity = new EventEntity();
                return eventEntity.createEntityFromDocument(document);
            }).collect(Collectors.toList());
        }
        return eventEntityList;
    }

    @Override
    public EventEntity getEventById(String eventId) {
        Document document = eventDao.getEventById(new ObjectId(eventId));
        return Objects.nonNull(document) ? new EventEntity().createEntityFromDocument(document) : null;
    }

    @Override
    public boolean sendNotificationMessage() {
        final String ACCOUNT_SID = "ACf9d944d4d8d8ea3a1ac7c1423d1e9c5e";
        final String AUTH_TOKEN = "b6167fce54d8a69c01e07272b3e751b9";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        List<EventEntity> eventEntityList = getAllEvents();
        if (CollectionUtils.isEmpty(eventEntityList)) {
            return false;
        }
        try {
            eventEntityList.stream().filter(eventEntity -> Objects.nonNull(eventEntity) && eventEntity.isStatus())
                    .forEach(eventEntity -> {
                        StringBuilder whatsupMessage = new StringBuilder();
                        String[] eventDateArray = eventEntity.getEventDate().split("-");
                        System.out.println("Date : " + eventDateArray);
                        int year = Integer.valueOf(eventDateArray[0]);
                        int month = Integer.valueOf(eventDateArray[1]);
                        int day = Integer.valueOf(eventDateArray[2]);
                        LocalDate eventDate = LocalDate.of(year, month, day);
                        LocalDate todayDate = LocalDate.now();
                        LocalDate notificationDate = LocalDate.now().plusDays(eventEntity.getNotificationDays());
                        Period currentDifference = Period.between(eventDate, todayDate);
                        Period notificationDifference = Period.between(eventDate, notificationDate);
                        boolean isExactDate = isDayAndMonthEqual(currentDifference.getMonths(),
                                currentDifference.getDays());
                        boolean isNotificationDate = isDayAndMonthEqual(notificationDifference.getMonths(),
                                notificationDifference.getDays());
                        if (isExactDate) {
                            whatsupMessage = getWhatsupMessage(eventEntity, currentDifference, true);
                            Message.creator(new PhoneNumber("whatsapp:+91" + eventEntity.getUserName()),
                                    new PhoneNumber("whatsapp:+14155238886"), whatsupMessage.toString()).create();
                        } else if (isNotificationDate) {
                            whatsupMessage = getWhatsupMessage(eventEntity, notificationDifference, false);
                            Message.creator(new PhoneNumber("whatsapp:+91" + eventEntity.getUserName()),
                                    new PhoneNumber("whatsapp:+14155238886"), whatsupMessage.toString()).create();
                        }
                    });
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private StringBuilder getWhatsupMessage(EventEntity eventEntity, Period difference, boolean isToday) {
        StringBuilder whatsupMessage;
        if ("Birthday".equals(eventEntity.getEventType())) {
            whatsupMessage = new StringBuilder("Event : ").append(eventEntity.getEventName()).append("\n")
                    .append("DOB : ").append(eventEntity.getEventDate()).append("\n").append("Age : ")
                    .append(difference.getYears()).append("\n").append("Days Left : ")
                    .append(isToday ? 0 : eventEntity.getNotificationDays()).append("\n");
        } else if ("Anniversary".equals(eventEntity.getEventType())) {
            whatsupMessage = new StringBuilder("Event : ").append(eventEntity.getEventName()).append("\n")
                    .append("Event Date : ").append(eventEntity.getEventDate()).append("\n").append("Years : ")
                    .append(difference.getYears()).append("\n").append("Days Left : ")
                    .append(isToday ? 0 : eventEntity.getNotificationDays()).append("\n");
        } else {
            whatsupMessage = new StringBuilder("Event : ").append(eventEntity.getEventName()).append("\n")
                    .append("Event Date : ").append(eventEntity.getEventDate()).append("\n").append("Days Left : ")
                    .append(isToday ? 0 : eventEntity.getNotificationDays()).append("\n");
        }
        return whatsupMessage;
    }

    private boolean isDayAndMonthEqual(int differMonth, int differDay) {
        return (differMonth == 0 && differDay == 0);
    }
}
