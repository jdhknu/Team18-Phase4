package com.team18.recipe;

public class Recipe {
	
	private int recipeId;       // recipe_id
    private String title;       // title
    private String description; // description
    private Integer cookingTime; // cooking_time (분 단위)
    private String difficulty;  // difficulty

    private Double sugar;       // sugar
    private Double sodium;      // sodium
    private Double fat;         // fat
    private Double protein;     // protein
    private Double kcal;        // kcal
    private Double carbs;       // carbs
    
    public Recipe() {}
    
    public Recipe(int recipeId, String title, String description) {
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
    }
    
    
    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Double getSugar() {
        return sugar;
    }

    public void setSugar(Double sugar) {
        this.sugar = sugar;
    }

    public Double getSodium() {
        return sodium;
    }

    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getKcal() {
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cookingTime=" + cookingTime +
                ", difficulty='" + difficulty + '\'' +
                ", sugar=" + sugar +
                ", sodium=" + sodium +
                ", fat=" + fat +
                ", protein=" + protein +
                ", kcal=" + kcal +
                ", carbs=" + carbs +
                '}';
    }

}
