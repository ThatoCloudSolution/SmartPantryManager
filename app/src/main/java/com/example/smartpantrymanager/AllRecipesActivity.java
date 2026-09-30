package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.Adapters.RecipeAdapter;
import com.example.smartpantrymanager.Data.AppDatabase;
import com.example.smartpantrymanager.Data.Recipe;

import java.util.List;

public class AllRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recipes);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAllRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = AppDatabase.getInstance(this);
        List<Recipe> allRecipes = db.appDao().getAllRecipes();

        recyclerView.setAdapter(new RecipeAdapter(allRecipes, this));
    }
}