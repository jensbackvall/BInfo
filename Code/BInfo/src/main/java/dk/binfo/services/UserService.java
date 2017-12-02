package dk.binfo.services;

import dk.binfo.models.User;

import java.util.List;

public interface UserService {
	User findUserByEmail(String email);
	User updateUserSettings(User user);
	List<User> findAll();
	void saveUser(User user);
	void adminSaveUser(User user);
}
