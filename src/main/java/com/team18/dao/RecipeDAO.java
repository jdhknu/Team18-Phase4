package com.team18.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.team18.recipe.Recipe;
import com.team18.util.DBUtil;

//RECIPE 테이블에 대한 조회 전용 DAO
public class RecipeDAO {

	// 레시피 전체 목록 조회 (리스트 페이지용)
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

    // recipe_id로 한 개 레시피 상세 조회 (상세 페이지용)
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

    // 공통 매핑 메소드: ResultSet -> Recipe 객체
    private Recipe mapRowToRecipe(ResultSet rs) throws SQLException {
        Recipe r = new Recipe();

        r.setRecipeId(rs.getInt("recipe_id"));
        r.setTitle(rs.getString("title"));
        r.setDescription(rs.getString("description"));

        // NUMBER 컬럼들은 null 가능성이 있으니까 getObject로 한 번 감싸주는 방식 사용
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
	
}
