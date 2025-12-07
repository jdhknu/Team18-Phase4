package com.team18.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.team18.recipe.Recipe;
import com.team18.util.DBUtil;

public class RecipeDAO {

    // 1. 레시피 전체 목록 조회 (기존 기능)
    public List<Recipe> findAll() {
        List<Recipe> list = new ArrayList<>();

        String sql = "SELECT recipe_id, title, description, cooking_time, "
                   + "       difficulty, sugar, sodium, fat, protein, kcal, carbs "
                   + "FROM RECIPE "
                   + "ORDER BY recipe_id";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Recipe r = mapRowToRecipe(rs);
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();   
        }

        return list;
    }

    // 2. recipe_id로 한 개 레시피 상세 조회 (기존 기능)
    public Recipe findById(int recipeId) {
        Recipe recipe = null;

        String sql = "SELECT recipe_id, title, description, cooking_time, "
                   + "       difficulty, sugar, sodium, fat, protein, kcal, carbs "
                   + "FROM RECIPE "
                   + "WHERE recipe_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, recipeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    recipe = mapRowToRecipe(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipe;
    }

    //추가. 내 냉장고 재료로 가능한 요리 추천 (Top 5)
    public List<Recipe> findCookableRecipes(String userId) {
        List<Recipe> list = new ArrayList<>();

        String sql = "SELECT R.recipe_id, R.title, R.description, R.cooking_time, "
                   + "       R.difficulty, R.sugar, R.sodium, R.fat, R.protein, R.kcal, R.carbs "
                   + "FROM RECIPE R "
                   + "WHERE NOT EXISTS ( "
                   + "  SELECT 1 FROM RECIPE_INGREDIENT RI "
                   + "  WHERE RI.recipe_id = R.recipe_id AND RI.ingredient_num NOT IN ( "
                   + "    SELECT ingredient_num FROM PANTRY_ITEM WHERE user_id = ? "
                   + "  ) "
                   + ") "
                   + "ORDER BY R.cooking_time, R.title "
                   + "FETCH FIRST 5 ROWS ONLY";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToRecipe(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 추가. 고단백 / 저칼로리 / 저지방 조건 검색 (Top 10)
    // goalType => 1: 고단백, 2: 저칼로리, 3: 저지방
    public List<Recipe> findRecipesByGoal(int goalType) {
        List<Recipe> list = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
                "SELECT recipe_id, title, description, cooking_time, " +
                "       difficulty, sugar, sodium, fat, protein, kcal, carbs " +
                "FROM RECIPE ");

        // 동적 쿼리 구성
        switch (goalType) {
            case 1: // 고단백 (단백질 > 25g)
                sql.append("WHERE protein > 25 ORDER BY protein DESC ");
                break;
            case 2: // 저칼로리 (칼로리 < 400kcal)
                sql.append("WHERE kcal < 400 ORDER BY kcal ASC ");
                break;
            case 3: // 저지방 (지방 < 10g)
                sql.append("WHERE fat < 10 ORDER BY fat ASC ");
                break;
            default:
                return list; // 잘못된 타입 -> 빈 리스트 반환
        }
        
        sql.append("FETCH FIRST 10 ROWS ONLY");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString());
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToRecipe(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Recipe mapRowToRecipe(ResultSet rs) throws SQLException {
        Recipe r = new Recipe();

        r.setRecipeId(rs.getInt("recipe_id"));
        r.setTitle(rs.getString("title"));
        r.setDescription(rs.getString("description"));

        Object cookingTimeObj = rs.getObject("cooking_time");
        if (cookingTimeObj != null) {
            r.setCookingTime(((Number) cookingTimeObj).intValue());
        }

        r.setDifficulty(rs.getString("difficulty"));

        Object sugarObj = rs.getObject("sugar");
        if (sugarObj != null) r.setSugar(((Number) sugarObj).doubleValue());

        Object sodiumObj = rs.getObject("sodium");
        if (sodiumObj != null) r.setSodium(((Number) sodiumObj).doubleValue());

        Object fatObj = rs.getObject("fat");
        if (fatObj != null) r.setFat(((Number) fatObj).doubleValue());

        Object proteinObj = rs.getObject("protein");
        if (proteinObj != null) r.setProtein(((Number) proteinObj).doubleValue());

        Object kcalObj = rs.getObject("kcal");
        if (kcalObj != null) r.setKcal(((Number) kcalObj).doubleValue());

        Object carbsObj = rs.getObject("carbs");
        if (carbsObj != null) r.setCarbs(((Number) carbsObj).doubleValue());

        return r;
    }
    
    // 추가. 전체 레시피 개수 조회 (레시피 전체보기 할 때 페이지 계산용)
    public int countAll() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM RECIPE";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // 추가. 페이지 적용된 전체 목록 조회
    public List<Recipe> findAll(int page, int pageSize) {
        List<Recipe> list = new ArrayList<>();
        
        String sql = "SELECT recipe_id, title, description, cooking_time, "
                   + "       difficulty, sugar, sodium, fat, protein, kcal, carbs "
                   + "FROM RECIPE "
                   + "ORDER BY recipe_id ASC "
                   + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize; // 건너뛸 개수 계산
            
            pstmt.setInt(1, offset);
            pstmt.setInt(2, pageSize);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToRecipe(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();   
        }
        return list;
    }
}