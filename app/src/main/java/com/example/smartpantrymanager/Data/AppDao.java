package com.example.smartpantrymanager.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {

    // --- Ingredient CRUD ---
    @Insert
    void insertIngredient(Ingredient ingredient);

    @Update
    void updateIngredient(Ingredient ingredient);

    @Delete
    void deleteIngredient(Ingredient ingredient);

    @Query("SELECT * FROM ingredients ORDER BY name ASC")
    List<Ingredient> getAllIngredients();

    // --- Recipe CRUD ---
    @Insert
    long insertRecipe(Recipe recipe);

    @Query("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();

    // --- RecipeIngredient CRUD ---
    @Insert
    void insertRecipeIngredient(RecipeIngredient recipeIngredient);

    @Query("SELECT * FROM recipe_ingredients WHERE recipeId = :recipeId")
    List<RecipeIngredient> getIngredientsForRecipe(int recipeId);

    // --- Search ---
    @Query("SELECT * FROM ingredients WHERE LOWER(name) LIKE '%' || LOWER(:name) || '%'")
    List<Ingredient> findIngredientByName(String name);
}