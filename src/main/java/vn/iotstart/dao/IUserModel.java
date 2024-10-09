package vn.iotstart.dao;

import java.sql.Date;
import java.util.List;

import vn.iotstart.models.UserModel;

public interface IUserModel {
	List<UserModel> findAll();
	UserModel findById(int id);
	void insert(UserModel user);
	boolean checkExistUsername(String username);
	UserModel login(String username, String password);
	boolean checkExistEmail(String email);
	UserModel findByUsername(String username);
	boolean register(String username, String email, String password, String fullname, String image, String phone,
			int roleid, Date createDate);
	boolean update(UserModel user);

	
}
