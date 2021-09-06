package com.mobdeve.group19.clink.model;

public class Ingredients {

    Integer quantity;
    String ingredient;

    public Ingredients(Integer quantity, String ingredient) {
        this.quantity = quantity;
        this.ingredient = ingredient;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

}
