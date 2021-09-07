package com.mobdeve.group19.clink.model;

public class Ingredients {

    int quantity;
    String ingredientName;

    public Ingredients(Integer quantity, String ingredientName) {
        this.quantity = quantity;
        this.ingredientName = ingredientName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

}
