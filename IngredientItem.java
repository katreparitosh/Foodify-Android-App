package com.foodapp.food.recipe.model;



public class IngredientItem {

    private String nameIngredient;

    public IngredientItem(String nameIngredient){
        this.nameIngredient= nameIngredient;
    }

    public String getNameIngredient(){
        return  this.nameIngredient;
    }
}
