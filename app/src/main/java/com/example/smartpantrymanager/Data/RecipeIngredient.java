package com.example.smartpantrymanager.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_ingredients")
public class RecipeIngredient {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int recipeId;
    public String ingredientName;
    public String requiredQuantity;
    public String requiredUnit;

    public RecipeIngredient(int recipeId, String ingredientName,
                            String requiredQuantity, String requiredUnit) {
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.requiredQuantity = requiredQuantity;
        this.requiredUnit = requiredUnit;
    }
}