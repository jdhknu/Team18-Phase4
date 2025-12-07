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

    // 추가. 주간 단백질 섭취량 (최근 7일)
    public double getWeeklyProtein(String userId) {
        double totalProtein = 0.0;
        String sql = "SELECT SUM(total_protein) AS protein_week " +
                     "FROM DIET_RECORD " +
                     "WHERE user_id = ? AND record_date >= TRUNC(SYSDATE)-6";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalProtein = rs.getDouble("protein_week");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalProtein;
    }

    // 추가. 최근 30일 칼로리 합
    public double getMonthlyTotalKcal(String userId) {
        double totalKcal = 0.0;
        String sql = "SELECT SUM(total_kcal) AS month_kcal " +
                     "FROM DIET_RECORD " +
                     "WHERE user_id = ? AND record_date >= TRUNC(SYSDATE)-30";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalKcal = rs.getDouble("month_kcal");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalKcal;
    }

    // 추가. 평균 영양소 비율 (전체 기간 평균)
    // 반환값을 편하게 사용하기 위해 DietRecord 객체에 담아서 리턴
    public DietRecord getNutrientAverages(String userId) {
        DietRecord avgRecord = new DietRecord();
        
        String sql = "SELECT ROUND(AVG(total_protein),1) AS avg_protein, " +
                     "       ROUND(AVG(total_carbs),1) AS avg_carb, " +
                     "       ROUND(AVG(total_fat),1) AS avg_fat " +
                     "FROM DIET_RECORD " +
                     "WHERE user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 평균값을 DietRecord 객체의 필드에 임시로 저장하여 반환
                    avgRecord.setTotalProtein(rs.getDouble("avg_protein"));
                    avgRecord.setTotalCarbs(rs.getDouble("avg_carb"));
                    avgRecord.setTotalFat(rs.getDouble("avg_fat"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avgRecord;
    }

    // [추가] 주간 일평균 섭취 칼로리 (최근 7일)
    public double getWeeklyAvgKcal(String userId) {
        double avgKcal = 0.0;
        String sql = "WITH DAILY AS ( " +
                     "  SELECT user_id, record_date, SUM(total_kcal) AS daily_kcal " +
                     "  FROM DIET_RECORD " +
                     "  WHERE user_id = ? AND record_date >= TRUNC(SYSDATE)-6 " +
                     "  GROUP BY user_id, record_date " +
                     ") " +
                     "SELECT ROUND(AVG(daily_kcal), 1) AS avg_kcal_week FROM DAILY";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    avgKcal = rs.getDouble("avg_kcal_week");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avgKcal;
    }
    
 	// [추가] 검색 조건(날짜)에 맞는 식단 기록 개수 조회
    public int countDietRecords(String userId, String searchDate) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM DIET_RECORD WHERE user_id = ?");
        
        if (searchDate != null && !searchDate.isEmpty()) {
            sql.append(" AND record_date = TO_DATE(?, 'YYYY-MM-DD')");
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            pstmt.setString(1, userId);
            if (searchDate != null && !searchDate.isEmpty()) {
                pstmt.setString(2, searchDate);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // [추가] 날짜별검색 + 페이지화
    public List<DietRecord> findDietRecords(String userId, String searchDate, int page, int pageSize) {
        List<DietRecord> list = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT diet_item, user_id, record_date, total_kcal, total_carbs, ")
           .append("total_protein, total_fat, total_sugar, total_sodium ")
           .append("FROM DIET_RECORD WHERE user_id = ? ");

        if (searchDate != null && !searchDate.isEmpty()) {
            sql.append("AND record_date = TO_DATE(?, 'YYYY-MM-DD') ");
        }

        // 날짜 최신순, 같은 날짜면 기록순
        sql.append("ORDER BY record_date DESC, diet_item DESC ")
           .append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            pstmt.setString(paramIndex++, userId);
            
            if (searchDate != null && !searchDate.isEmpty()) {
                pstmt.setString(paramIndex++, searchDate);
            }
            
            int offset = (page - 1) * pageSize;
            pstmt.setInt(paramIndex++, offset);
            pstmt.setInt(paramIndex++, pageSize);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DietRecord dr = new DietRecord();
                    dr.setDietItem(rs.getLong("diet_item"));
                    dr.setUserId(rs.getString("user_id"));
                    dr.setRecordDate(rs.getDate("record_date"));
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
}