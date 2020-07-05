package com.notifyme.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.notifyme.api.entity.UserEntity;

@Service
public interface UserService {

	boolean addNewUser(UserEntity userEntity);

	boolean updateUser(UserEntity userEntity);

	boolean deleteUser(String userName);

	List<UserEntity> getAllUsers();

	UserEntity getUserByUserName(String userName);
	
	boolean isUserNameExist(String userName);

	boolean isUserExist(String userName, String password);
}
