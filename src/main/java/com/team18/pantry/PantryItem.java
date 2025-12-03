package com.team18.pantry;

import java.sql.Date;

public class PantryItem {

	private int itemNo;          // item_no (PK)
    private String userId;       // user_id
    private int ingredientNum;   // ingredient_num (FK)
    private String ingredientName; // INGREDIENT.name (JOIN 해서 가져올 때 사용)
    private double quantity;     // quantity
    private Date expiryDate;     // expiry_date

    public PantryItem() {}
    
    // 편의 생성자
    public PantryItem(String userId, int ingredientNum, double quantity, Date expiryDate) {
        this.userId = userId;
        this.ingredientNum = ingredientNum;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }
    
    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIngredientNum() {
        return ingredientNum;
    }

    public void setIngredientNum(int ingredientNum) {
        this.ingredientNum = ingredientNum;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
	
}
