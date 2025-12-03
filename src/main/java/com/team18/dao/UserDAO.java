package com.team18.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.team18.user.User;
import com.team18.util.DBUtil;

public class UserDAO {
	
	
	

	// 로그인: 아이디/비번 맞으면 User 객체, 아니면 null
    public User login(String userId, String password) {
        String sql = "SELECT user_id, password, name, gender, age, height, weight "
                   + "FROM \"USER\" WHERE user_id = ? AND password = ?";
       
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getString("user_id"));
                    u.setPassword(rs.getString("password"));
                    u.setName(rs.getString("name"));
                    u.setGender(rs.getString("gender"));

                    int age = rs.getInt("age");
                    if (rs.wasNull()) u.setAge(null);
                    else u.setAge(age);

                    double h = rs.getDouble("height");
                    if (rs.wasNull()) u.setHeight(null);
                    else u.setHeight(h);

                    double w = rs.getDouble("weight");
                    if (rs.wasNull()) u.setWeight(null);
                    else u.setWeight(w);

                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
    // 회원가입
    public boolean register(User user) {
        String sql = "INSERT INTO \"USER\" "
                   + "(user_id, password, name, gender, age, height, weight) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getGender());

            if (user.getAge() != null) pstmt.setInt(5, user.getAge());
            else pstmt.setNull(5, java.sql.Types.INTEGER);

            if (user.getHeight() != null) pstmt.setDouble(6, user.getHeight());
            else pstmt.setNull(6, java.sql.Types.DOUBLE);

            if (user.getWeight() != null) pstmt.setDouble(7, user.getWeight());
            else pstmt.setNull(7, java.sql.Types.DOUBLE);

            int rows = pstmt.executeUpdate();
            return rows == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // 아이디 존재 여부
    public boolean exists(String userId) {
        String sql = "SELECT 1 FROM \"USER\" WHERE user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
