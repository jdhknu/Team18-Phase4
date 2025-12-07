package com.team18.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.team18.util.DBUtil;

public class IngredientDAO {

    // 이름으로 번호 찾기 (없으면 null)
    public Integer findIngredientNumByName(String name) {
        String sql = "SELECT ingredient_num FROM INGREDIENT WHERE name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ingredient_num");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 이름으로 찾고, 없으면 새로 INSERT 후 그 번호 리턴
    public int findOrCreateIngredient(String name) {
        Integer num = findIngredientNumByName(name);
        if (num != null) {
            return num;
        }

        String insertSql = "INSERT INTO INGREDIENT (name) VALUES (?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSql, new String[] { "ingredient_num" })) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);   // 새로 생성된 ingredient_num
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 실패하면 -1 같은 값 리턴 (호출 쪽에서 체크)
        return -1;
    }
}
