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

public class UserDaoImpl implements UserDao {

	@Override
	public boolean saveUser(Document document) {
		try {
			MongoCollection<Document> collection = getEventCollection();
			InsertOneResult insertOneResult = collection.insertOne(document);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateUser(Document document, ObjectId userId) {
		try {
			MongoCollection<Document> collection = getEventCollection();
			Bson filter = new Document("_id", userId);
			Bson updateOperationDocument = new Document("$set", document);
			collection.updateOne(filter, updateOperationDocument);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteUser(String userName) {
		try {
			MongoCollection<Document> collection = getEventCollection();
			BasicDBObject deleteQuery = new BasicDBObject();
			deleteQuery.put("userName", userName);
			collection.deleteOne(deleteQuery);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Document> getAllUsers() {
		try {
			MongoCollection<Document> collection = getEventCollection();
			return (List<Document>) collection.find().into(new ArrayList<Document>());
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Document getUserByUserName(String userName) {
		try {
			MongoCollection<Document> collection = getEventCollection();
			Bson filter = new Document("userName", userName);
			return (Document) collection.find(filter).first();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Document getUserByUserNameAndPassword(String userName, String password) {
		try {
			MongoCollection<Document> collection = getEventCollection();
			Bson filter = new Document("userName", userName).append("password", password);
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
		MongoCollection<Document> collection = database.getCollection("userDto");
		return collection;
	}

}
