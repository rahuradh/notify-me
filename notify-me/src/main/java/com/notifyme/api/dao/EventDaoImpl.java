package com.notifyme.api.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

public class EventDaoImpl implements EventDao {

	@Override
	public boolean saveEvent(Document document) {
		try {
			MongoCollection<Document> collection = getEventCollection();
			InsertOneResult insertOneResult = collection.insertOne(document);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateEvent(Document document, ObjectId eventId) {
		try {
			MongoCollection<Document> collection = getEventCollection();
			Bson filter = new Document("_id", eventId);
			Bson updateOperationDocument = new Document("$set", document);
			collection.updateOne(filter, updateOperationDocument);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteEvent(ObjectId eventId) {
		try {
			MongoCollection<Document> collection = getEventCollection();
			BasicDBObject deleteQuery = new BasicDBObject();
			deleteQuery.put("_id", eventId);
			collection.deleteOne(deleteQuery);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Document> getAllEvents() {
		try {
			MongoCollection<Document> collection = getEventCollection();
			return (List<Document>) collection.find().into(new ArrayList<Document>());
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<Document> getEventsByUserName(String userName) {
		try {
			MongoCollection<Document> collection = getEventCollection();
			Bson filter = new Document("userName", userName);
			return (List<Document>) collection.find(filter).into(new ArrayList<Document>());
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Document getEventById(ObjectId eventId) {
		try {
			MongoCollection<Document> collection = getEventCollection();
			Bson filter = new Document("_id", eventId);
			return (Document) collection.find(filter).first();
		} catch (Exception e) {
			return null;
		}
	}

	private static MongoCollection<Document> getEventCollection() {
		ConnectionString connString = new ConnectionString(
				"mongodb+srv://user-root:user-root@cluster0-lvb7l.mongodb.net/test?retryWrites=true&w=majority");
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connString).retryWrites(true)
				.build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase("NotifyMe");
		MongoCollection<Document> collection = database.getCollection("eventDto");
		return collection;
	}
}