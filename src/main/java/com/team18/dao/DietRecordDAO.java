package com.team18.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.team18.diet.DietRecord;
import com.team18.util.DBUtil;

public class DietRecordDAO {

    // 오늘 식단 기록 가져오기
    public List<DietRecord> getTodayDiet(String userId) {

        String sql =
            "SELECT user_id, record_date, diet_item, " +
            "       total_kcal, total_carbs, total_protein, " +
            "       total_fat, total_sugar, total_sodium " +
            "FROM DIET_RECORD " +
            "WHERE user_id = ? " +
            "  AND record_date = TRUNC(SYSDATE) " +
            "ORDER BY diet_item";

        List<DietRecord> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DietRecord dr = new DietRecord();

                    dr.setUserId(rs.getString("user_id"));
                    dr.setRecordDate(rs.getDate("record_date"));
                    dr.setDietItem(rs.getLong("diet_item"));

                    dr.setTotalKcal(rs.getDouble("total_kcal"));
                    dr.setTotalCarbs(rs.getDouble("total_carbs"));
                    dr.setTotalProtein(rs.getDouble("total_protein"));
                    dr.setTotalFat(rs.getDouble("total_fat"));
                    dr.setTotalSugar(rs.getDouble("total_sugar"));
                    dr.setTotalSodium(rs.getDouble("total_sodium"));

                    list.add(dr);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    //식단 기입하는 기능
    public int insertRecord(DietRecord record) {
        String sql = "INSERT INTO DIET_RECORD "
                   + "(user_id, record_date, total_kcal, total_carbs, total_protein, total_fat, total_sugar, total_sodium) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, record.getUserId());
            pstmt.setDate(2, record.getRecordDate());
            pstmt.setDouble(3, record.getTotalKcal());
            pstmt.setDouble(4, record.getTotalCarbs());
            pstmt.setDouble(5, record.getTotalProtein());
            pstmt.setDouble(6, record.getTotalFat());
            pstmt.setDouble(7, record.getTotalSugar());
            pstmt.setDouble(8, record.getTotalSodium());

            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    //기록 식단 삭제 기능
    public int deleteRecord(int dietItem) {
        String sql = "DELETE FROM DIET_RECORD WHERE diet_item = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dietItem);
            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}