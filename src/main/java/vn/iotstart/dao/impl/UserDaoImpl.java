package vn.iotstart.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iotstart.controllers.DBConnection;
import vn.iotstart.dao.IUserModel;
import vn.iotstart.models.UserModel;

public class UserDaoImpl extends DBConnection implements IUserModel {

    public Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;

    @Override
    public List<UserModel> findAll() {

        String sql = "SELECT * FROM users";

        List<UserModel> list = new ArrayList<UserModel>();

        try {
            conn = super.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new UserModel(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getString("image"),
                        rs.getString("phone"),
                        rs.getInt("roleid"),
                        rs.getDate("createDate")));
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserModel findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            conn = super.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new UserModel(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getString("image"),
                        rs.getString("phone"),
                        rs.getInt("roleid"),
                        rs.getDate("createDate"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserModel findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try {
            conn = super.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setImage(rs.getString("image"));
				user.setEmail(rs.getString("email"));
				user.setRoleid(rs.getInt("roleid"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }

    @Override
    public void insert(UserModel user) {
        String sql = "INSERT INTO users(username, email, password, fullname, image, phone, roleid, createDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = super.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFullname());
            ps.setString(5, user.getImage());
            ps.setString(6, user.getPhone());
            ps.setInt(7, user.getRoleid());
            ps.setDate(8, user.getcreateDate());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserModel login(String username, String password) {
        UserModel user = this.findByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            System.out.println("LOGIN SUCCESS");
            return user;
        } else {
            System.out.println("LOGIN ERROR");
        }
        return null;
    }

    public boolean register(String username, String email, String password, String fullname, String image, String phone, int roleid, Date createDate) {
        if (this.checkExistUsername(username) || this.checkExistEmail(email)) {
            System.out.println("REGISTER ERROR");
            return false;
        }

        this.insert(new UserModel(0, username, email, password, fullname, image, phone, roleid, createDate));
        System.out.println("REGISTER SUCCESS");
        return true;
    }

    @Override
    public boolean checkExistUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try {
            conn = super.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkExistEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try {
            conn = super.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        UserDaoImpl newUser = new UserDaoImpl();
        System.out.println(newUser.findByUsername("newuser2"));
    }

	@Override
    public boolean update(UserModel user) {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, roleid = ?, phone = ?, fullname = ? WHERE id = ?";
        try {
            conn = super.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getRoleid());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getFullname());
            ps.setInt(7, user.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
