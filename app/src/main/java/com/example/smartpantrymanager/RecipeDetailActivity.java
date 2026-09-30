package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.Data.AppDatabase;
import com.example.smartpantrymanager.Data.Recipe;
import com.example.smartpantrymanager.Data.RecipeIngredient;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        db = AppDatabase.getInstance(this);
        int recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
        if (recipeId == -1) { finish(); return; }

        Recipe recipe = null;
        for (Recipe r : db.appDao().getAllRecipes()) {
            if (r.id == recipeId) { recipe = r; break; }
        }
        if (recipe == null) { finish(); return; }

        TextView textViewName = findViewById(R.id.textViewRecipeName);
        TextView textViewIngredients = findViewById(R.id.textViewRecipeIngredients);
        TextView textViewSteps = findViewById(R.id.textViewRecipeSteps);

        textViewName.setText(recipe.name);

        List<RecipeIngredient> ingredients = db.appDao().getIngredientsForRecipe(recipeId);
        StringBuilder sb = new StringBuilder();
        for (RecipeIngredient ri : ingredients) {
            sb.append("- ").append(ri.requiredQuantity).append(" ")
                    .append(ri.requiredUnit).append(" of ")
                    .append(ri.ingredientName).append("\n");
        }
        textViewIngredients.setText(sb.toString());
        textViewSteps.setText(recipe.steps);
    }
}