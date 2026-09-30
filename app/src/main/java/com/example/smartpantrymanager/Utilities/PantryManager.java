package com.example.smartpantrymanager.Utilities;

import android.content.Context;

import com.example.smartpantrymanager.Data.AppDatabase;
import com.example.smartpantrymanager.Data.Ingredient;
import com.example.smartpantrymanager.Data.Recipe;
import com.example.smartpantrymanager.Data.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class PantryManager {
    private AppDatabase db;

    public PantryManager(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public List<Recipe> getStrictlyMatchingRecipes() {
        List<Recipe> allRecipes = db.appDao().getAllRecipes();
        List<Ingredient> pantryIngredients = db.appDao().getAllIngredients();
        List<Recipe> matchingRecipes = new ArrayList<>();

        List<String> pantryNames = new ArrayList<>();
        for (Ingredient ingredient : pantryIngredients) {
            pantryNames.add(ingredient.name.toLowerCase().trim());
        }

        for (Recipe recipe : allRecipes) {
            List<RecipeIngredient> requiredIngredients =
                    db.appDao().getIngredientsForRecipe(recipe.id);
            boolean canMake = true;

            for (RecipeIngredient required : requiredIngredients) {
                boolean isInPantry = pantryNames.contains(
                        required.ingredientName.toLowerCase().trim());
                if (!isInPantry) {
                    canMake = false;
                    break;
                }
            }

            if (canMake) {
                matchingRecipes.add(recipe);
            }
        }
        return matchingRecipes;
    }
}