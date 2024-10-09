package vn.iotstart.service.impl;

import vn.iotstart.dao.IUserModel;
import vn.iotstart.dao.impl.UserDaoImpl;
import vn.iotstart.models.UserModel;
import vn.iotstart.service.IUserService;

public class UserServiceImpl implements IUserService{
	
	IUserModel userDao = new UserDaoImpl();
	
	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.findByUsername(username);
		if (user != null && password.equals(user.getPassword())) {
	        System.out.println("LOGIN SUCCESS");
	        System.out.println(user.getUsername());
	        System.out.println(user.getPassword());
			return user;
		}
        System.out.println("LOGIN ERROR");
		return null;
	}

	@Override
	public UserModel findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public boolean register(String email, String password, String username, String fullname, String phone) {
		if (this.checkExistUsername(username)) {
			return false;
			}
			long millis=System.currentTimeMillis();
			java.sql.Date date=new java.sql.Date(millis);
			userDao.insert(new UserModel(0, username, email, password, fullname, null, phone, 3, date));
	        System.out.println(new UserModel(0, username, email, password, fullname, null, phone, 3, date));
	        System.out.println("REGISTER SUCCESS");
			return true;
	}	

	@Override
	public void insert(UserModel user) {
		userDao.insert(user);
	}

	@Override
	public boolean checkExistEmail(String email) {
		return userDao.checkExistEmail(email);
	}

	@Override
	public boolean checkExistUsername(String username) {
		return userDao.checkExistUsername(username);
	}

	@Override
	public boolean checkExistPhone(String phone) {
		return userDao.checkExistUsername(phone);
	}
	
	public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        String username = "testuser";
        String email = "testuser@example.com";
        String password = "password123";
        String fullname = "Test User";
        String phone = "0123456789";
        boolean registrationResult = userService.register(email, password, username, fullname, phone);
        System.out.println("Registration result: " + (registrationResult ? "Success" : "Failed"));
        
        UserModel user = userService.login(username, password);
        if (user != null) {
            System.out.println("Login successful: " + user);
        } else {
            System.out.println("Login failed");
        }
        
        UserModel failedLogin = userService.login(username, "wrongpassword");
        if (failedLogin != null) {
            System.out.println("Login successful: " + failedLogin);
        } else {
            System.out.println("Login failed with incorrect password");
        }
    }

	@Override
	public boolean update(UserModel user) {
        return userDao.update(user);	
	}

	@Override
	public void updatePassword(String username, String password) {
        UserServiceImpl userService = new UserServiceImpl();	
	}
	
}
