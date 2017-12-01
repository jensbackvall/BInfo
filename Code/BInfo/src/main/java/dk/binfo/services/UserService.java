package dk.binfo.services;

import dk.binfo.models.User;

public interface UserService {
	User findUserByEmail(String email);
	User updateUserSettings(User user);
	void saveUser(User user);
}
