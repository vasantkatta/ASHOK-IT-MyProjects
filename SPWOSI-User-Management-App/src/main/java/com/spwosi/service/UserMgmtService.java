package com.spwosi.service;

import java.util.List;

import com.spwosi.bindings.ActivateAccount;
import com.spwosi.bindings.Login;
import com.spwosi.bindings.User;

public interface UserMgmtService {

	public boolean saveUser(User user);
	
	public boolean activateUserAcc(ActivateAccount activateAcc);
	
	public List<User> getAllUsers();
	
	public User getUserById(Integer userId);
	
	public boolean deleteUserById(Integer userId);
	
	public boolean changeAccountStatus(Integer userId, String AccStatus);
	
	public String login(Login login);
	
	public String forgotPwd(String email);
	
	
	
	
}
