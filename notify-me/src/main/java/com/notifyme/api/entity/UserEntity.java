package com.notifyme.api.entity;

import org.bson.Document;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserEntity {

	@Id
	private String userId;
	private String userName;
	private String password;
	private String role;
	private String createdDate;
	private String updatedDate;

	public Document createDocumentFromEntity(UserEntity userEntity) {
		Document document = new Document("userName", userEntity.getUserName())
				.append("password", userEntity.getPassword()).append("role", userEntity.getRole())
				.append("createdDate", userEntity.getCreatedDate()).append("updatedDate", userEntity.getUpdatedDate());
		return document;
	}

	public static UserEntity form(String username, String password) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(username);
		userEntity.setPassword(password);
		return userEntity;
	}

	public UserEntity createEntityFromDocument(Document document) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(String.valueOf(document.get("_id")));
		userEntity.setUserName(String.valueOf(document.get("userName")));
		userEntity.setPassword(String.valueOf(document.get("password")));
		userEntity.setRole(String.valueOf(document.get("role")));
		userEntity.setCreatedDate(String.valueOf(document.get("createdDate")));
		userEntity.setUpdatedDate(String.valueOf(document.get("updatedDate")));
		return userEntity;
	}
}
