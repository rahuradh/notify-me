package com.notifyme.api.dao;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

public interface UserDao {

	boolean saveUser(Document document);

	boolean updateUser(Document document, ObjectId userId);

	boolean deleteUser(String userName);

	List<Document> getAllUsers();

	Document getUserByUserName(String userName);

	Document getUserByUserNameAndPassword(String userName, String password);

}
