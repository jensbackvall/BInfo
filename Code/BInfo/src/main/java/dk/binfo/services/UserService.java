package dk.binfo.services;

import dk.binfo.models.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
