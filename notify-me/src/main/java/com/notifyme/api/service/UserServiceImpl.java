package com.notifyme.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.notifyme.api.dao.UserDao;
import com.notifyme.api.entity.EventEntity;
import com.notifyme.api.entity.UserEntity;
import com.notifyme.api.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserDao userDao;

	@Override
	public boolean addNewUser(UserEntity userEntity) {
		Document document = userEntity.createDocumentFromEntity(userEntity);
		return userDao.saveUser(document);
	}

	@Override
	public boolean updateUser(UserEntity userEntity) {
		Document document = userEntity.createDocumentFromEntity(userEntity);
		return userDao.updateUser(document, new ObjectId(userEntity.getUserId()));
	}

	@Override
	public boolean deleteUser(String userName) {
		return userDao.deleteUser(userName);
	}

	@Override
	public List<UserEntity> getAllUsers() {
		List<Document> documentList = userDao.getAllUsers();
		return formUserEntityList(documentList);
	}

	@Override
	public UserEntity getUserByUserName(String username) {
		Document document = userDao.getUserByUserName(username);
		if (Objects.isNull(document)) {
			return null;
		}
		return new UserEntity().createEntityFromDocument(document);
	}

	@Override
	public boolean isUserNameExist(String username) {
		Document document = userDao.getUserByUserName(username);
		if (Objects.isNull(document)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isUserExist(String username, String password) {
		Document document = userDao.getUserByUserNameAndPassword(username, password);
		if (Objects.isNull(document)) {
			return false;
		}
		return true;
	}

	private List<UserEntity> formUserEntityList(List<Document> documentList) {
		List<UserEntity> userEntityList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(documentList)) {
			userEntityList = documentList.stream().filter(Objects::nonNull).map(document -> {
				UserEntity userEntity = new UserEntity();
				return userEntity.createEntityFromDocument(document);
			}).collect(Collectors.toList());
		}
		return userEntityList;
	}
}
