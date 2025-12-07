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
    // 추가. ID로 회원 정보 전체 조회 (마이페이지용)
    public User findUserById(String userId) {
        String sql = "SELECT * FROM \"USER\" WHERE user_id = ?";
        User u = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    u = new User();
                    u.setUserId(rs.getString("user_id"));
                    u.setPassword(rs.getString("password"));
                    u.setName(rs.getString("name"));
                    u.setGender(rs.getString("gender"));
                    u.setAge(rs.getInt("age"));
                    u.setHeight(rs.getDouble("height"));
                    u.setWeight(rs.getDouble("weight"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    // 추가. 회원 정보 수정
    public int updateUser(User user) {
        String sql = "UPDATE \"USER\" SET name=?, age=?, height=?, weight=? WHERE user_id=?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.setDouble(3, user.getHeight());
            pstmt.setDouble(4, user.getWeight());
            pstmt.setString(5, user.getUserId());
            
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 추가. 회원 탈퇴 (트랜잭션 적용)
    public boolean deleteUser(String userId) {
        Connection conn = null;
        PreparedStatement pstmt1 = null; // 냉장고 삭제
        PreparedStatement pstmt2 = null; // 식단 삭제
        PreparedStatement pstmt3 = null; // 목표 삭제
        PreparedStatement pstmt4 = null; // 유저 삭제

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 1. 냉장고 아이템 삭제
            pstmt1 = conn.prepareStatement("DELETE FROM PANTRY_ITEM WHERE user_id = ?");
            pstmt1.setString(1, userId);
            pstmt1.executeUpdate();

            // 2. 식단 기록 삭제
            pstmt2 = conn.prepareStatement("DELETE FROM DIET_RECORD WHERE user_id = ?");
            pstmt2.setString(1, userId);
            pstmt2.executeUpdate();

            // 3. 영양 목표 삭제
            pstmt3 = conn.prepareStatement("DELETE FROM NUTRITION_GOAL WHERE user_id = ?");
            pstmt3.setString(1, userId);
            pstmt3.executeUpdate();

            // 4. 회원 정보 삭제
            pstmt4 = conn.prepareStatement("DELETE FROM \"USER\" WHERE user_id = ?");
            pstmt4.setString(1, userId);
            int result = pstmt4.executeUpdate();

            if (result > 0) {
                conn.commit(); // 성공 시 커밋
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            DBUtil.close(null, pstmt1);
            DBUtil.close(null, pstmt2);
            DBUtil.close(null, pstmt3);
            DBUtil.close(conn, pstmt4);
        }
    }
}
