package com.team18.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.team18.pantry.PantryItem;
import com.team18.util.DBUtil;

public class PantryDAO {

	// 해당 user의 냉장고 목록 조회
    public List<PantryItem> findByUserId(String userId) {
        List<PantryItem> list = new ArrayList<>();

        String sql =
            "SELECT p.item_no, p.user_id, p.ingredient_num, i.name AS ingredient_name, " +
            "       p.quantity, p.expiry_date " +
            "FROM PANTRY_ITEM p " +
            "JOIN INGREDIENT i ON p.ingredient_num = i.ingredient_num " +
            "WHERE p.user_id = ? " +
            "ORDER BY p.expiry_date, i.name";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PantryItem item = new PantryItem();
                    item.setItemNo(rs.getInt("item_no"));
                    item.setUserId(rs.getString("user_id"));
                    item.setIngredientNum(rs.getInt("ingredient_num"));
                    item.setIngredientName(rs.getString("ingredient_name"));
                    item.setQuantity(rs.getDouble("quantity"));
                    item.setExpiryDate(rs.getDate("expiry_date"));

                    list.add(item);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 냉장고 재료 추가 (INSERT)
    public void insert(PantryItem item) {
        String sql =
            "INSERT INTO PANTRY_ITEM (user_id, ingredient_num, quantity, expiry_date) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getUserId());
            pstmt.setInt(2, item.getIngredientNum());
            pstmt.setDouble(3, item.getQuantity());

            Date exp = item.getExpiryDate();
            if (exp != null) {
                pstmt.setDate(4, exp);
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 냉장고 재료 삭제 (DELETE)
    public void delete(int itemNo, String userId) {
        String sql =
            "DELETE FROM PANTRY_ITEM " +
            "WHERE item_no = ? AND user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemNo);
            pstmt.setString(2, userId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 냉장고 재료 소비 (동시성 제어 + 트랜잭션)
    // 반환값: true  => 정상 차감/삭제 완료
    //         false => 수량 부족, 존재하지 않음 등으로 롤백됨
    public boolean consumeItem(String userId, int itemNo, double amount) {
    	Connection conn = null;
    	PreparedStatement selectStmt = null;
    	PreparedStatement updateStmt = null;
    	PreparedStatement deleteStmt = null;
    	ResultSet rs = null;

    	try {
    		conn = DBUtil.getConnection();
    		// 트랜잭션 시작
    		conn.setAutoCommit(false);

    		// 1) 현재 수량 조회 + 행 잠금 (FOR UPDATE)
    		String selectSql =
    				"SELECT quantity " +
    				"FROM PANTRY_ITEM " +
    				"WHERE item_no = ? AND user_id = ? " +
    				"FOR UPDATE";

    		selectStmt = conn.prepareStatement(selectSql);
    		selectStmt.setInt(1, itemNo);
    		selectStmt.setString(2, userId);
    		rs = selectStmt.executeQuery();

    		if (!rs.next()) {
    			// 해당 아이템이 없으면 롤백
    			conn.rollback();
    			return false;
    		}

    		double currentQty = rs.getDouble("quantity");
    		double newQty = currentQty - amount;

    		// 2) 수량이 부족하면 롤백 (음수 방지)
    		if (newQty < 0) {
    			conn.rollback();
    			return false;
    		}

    		// 3) 새 수량이 0이면 DELETE, 그 외에는 UPDATE
    		if (newQty == 0) {
    			String deleteSql =
    					"DELETE FROM PANTRY_ITEM " +
    					"WHERE item_no = ? AND user_id = ?";

    			deleteStmt = conn.prepareStatement(deleteSql);
    			deleteStmt.setInt(1, itemNo);
    			deleteStmt.setString(2, userId);
    			deleteStmt.executeUpdate();
    		} else {
    			String updateSql =
    					"UPDATE PANTRY_ITEM " +
    					"SET quantity = ? " +
    					"WHERE item_no = ? AND user_id = ?";

    			updateStmt = conn.prepareStatement(updateSql);
    			updateStmt.setDouble(1, newQty);
    			updateStmt.setInt(2, itemNo);
    			updateStmt.setString(3, userId);
    			updateStmt.executeUpdate();
    		}

    		// 4) 모든 작업이 성공하면 커밋
    		conn.commit();
    		return true;

    	} catch (SQLException e) {
    		// 예외 발생 시 롤백
    		if (conn != null) {
    			try {
    				conn.rollback();
    			} catch (SQLException ex) {
    				ex.printStackTrace();
    			}
    		}
    		e.printStackTrace();
    		return false;

    	} finally {
    		// 자원 정리
    		try { if (rs != null) rs.close(); } catch (Exception ignored) {}
    		try { if (selectStmt != null) selectStmt.close(); } catch (Exception ignored) {}
    		try { if (updateStmt != null) updateStmt.close(); } catch (Exception ignored) {}
    		try { if (deleteStmt != null) deleteStmt.close(); } catch (Exception ignored) {}
    		try {
    			if (conn != null) {
    				conn.setAutoCommit(true); // 원래 상태로 복구
    				conn.close();
    			}
    		} catch (Exception ignored) {}
    	}
    }

    // [추가] 내 재료 요약 (종류 수, 총 수량)
    // 반환값: double 배열 [0]=종류수, [1]=총수량
    public double[] getPantrySummary(String userId) {
        double[] summary = {0, 0};
        String sql = "SELECT COUNT(ingredient_num) AS item_kinds, "
                   + "       NVL(SUM(quantity), 0) AS total_qty "
                   + "FROM PANTRY_ITEM "
                   + "WHERE user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    summary[0] = rs.getInt("item_kinds");
                    summary[1] = rs.getDouble("total_qty");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }

    // [추가] 레시피에 안 쓰이는 재료 조회 (이름만 반환)
    public List<String> findUnusedIngredients(String userId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT I.name "
                   + "FROM INGREDIENT I "
                   + "WHERE I.ingredient_num IN ( "
                   + "  SELECT ingredient_num FROM PANTRY_ITEM WHERE user_id = ? "
                   + "  MINUS "
                   + "  SELECT ingredient_num FROM RECIPE_INGREDIENT "
                   + ") ORDER BY I.name";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
