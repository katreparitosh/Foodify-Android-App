package com.foodapp.food.recipe.model;

import java.util.ArrayList;

public class RecipeIngr {

    private int id;
    private String name;
    private ArrayList<IngredientItem> ingredients;

    public RecipeIngr(int id, String name, ArrayList<IngredientItem> ingredients){
        this.id =id;
        this.name=name;
        this.ingredients=ingredients;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<IngredientItem> getIngredients(){
        return this.ingredients;
    }

    public String toString(){
        return ""+this.id+""+this.name+""+this.ingredients.toString();
    }
}


