package com.team18.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.team18.goal.NutritionGoal;
import com.team18.util.DBUtil;

public class GoalDAO {

    // 최신 목표 조회
    public NutritionGoal findRecentGoal(String userId) {
        NutritionGoal goal = null;
        String sql = "SELECT * FROM NUTRITION_GOAL " +
                     "WHERE user_id = ? " +
                     "ORDER BY Created_at DESC " +
                     "FETCH FIRST 1 ROW ONLY";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    goal = new NutritionGoal();
                    goal.setUserId(rs.getString("user_id"));
                    goal.setTargetCalories(rs.getDouble("target_calories"));
                    goal.setTargetProtein(rs.getDouble("target_protein"));
                    goal.setTargetCarb(rs.getDouble("target_carb"));
                    goal.setTargetFat(rs.getDouble("target_fat"));
                    goal.setCreatedAt(rs.getDate("Created_at"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goal;
    }

    // 새 목표 설정
    public boolean insertGoal(NutritionGoal goal) {
        String sql = "INSERT INTO NUTRITION_GOAL " +
                     "(user_id, target_calories, target_protein, target_carb, target_fat, Created_at) " +
                     "VALUES (?, ?, ?, ?, ?, SYSDATE)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, goal.getUserId());
            pstmt.setDouble(2, goal.getTargetCalories());
            pstmt.setDouble(3, goal.getTargetProtein());
            pstmt.setDouble(4, goal.getTargetCarb());
            pstmt.setDouble(5, goal.getTargetFat());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}