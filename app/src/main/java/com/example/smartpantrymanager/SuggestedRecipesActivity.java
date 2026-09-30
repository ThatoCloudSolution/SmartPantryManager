package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.Adapters.RecipeAdapter;
import com.example.smartpantrymanager.Data.Recipe;
import com.example.smartpantrymanager.Utilities.PantryManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textViewNoRecipes;
    private PantryManager pantryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        pantryManager = new PantryManager(this);
        recyclerView = findViewById(R.id.recyclerViewSuggestedRecipes);
        textViewNoRecipes = findViewById(R.id.textViewNoRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadSuggestedRecipes();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_pantry) {
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_suggest) {
                return true;
            } else if (item.getItemId() == R.id.navigation_settings) {
                startActivity(new Intent(SuggestedRecipesActivity.this,
                        SettingsActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {
        List<Recipe> matchingRecipes = pantryManager.getStrictlyMatchingRecipes();

        if (matchingRecipes.isEmpty()) {
            textViewNoRecipes.setVisibility(TextView.VISIBLE);
            recyclerView.setVisibility(RecyclerView.GONE);
        } else {
            textViewNoRecipes.setVisibility(TextView.GONE);
            recyclerView.setVisibility(RecyclerView.VISIBLE);
            recyclerView.setAdapter(new RecipeAdapter(matchingRecipes, this));
        }
    }
}