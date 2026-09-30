package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.Data.AppDatabase;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnClearPantry = findViewById(R.id.buttonClearPantry);
        Button btnReseedRecipes = findViewById(R.id.buttonReseedRecipes);
        Button btnViewAllRecipes = findViewById(R.id.buttonViewAllRecipes);
        Button btnAbout = findViewById(R.id.buttonAbout);

        btnClearPantry.setOnClickListener(v -> {
            AppDatabase db = AppDatabase.getInstance(this);
            for (com.example.smartpantrymanager.Data.Ingredient i : db.appDao().getAllIngredients()) {
                db.appDao().deleteIngredient(i);
            }
            Toast.makeText(this, "Pantry cleared", Toast.LENGTH_SHORT).show();
        });

        btnReseedRecipes.setOnClickListener(v -> {
            Toast.makeText(this, "Recipes are seeded on app install. Reinstall app to reseed.",
                    Toast.LENGTH_LONG).show();
        });

        btnViewAllRecipes.setOnClickListener(v ->
                startActivity(new Intent(SettingsActivity.this, AllRecipesActivity.class)));

        btnAbout.setOnClickListener(v ->
                Toast.makeText(this, "SmartPantryManager v1.0\nKeeps track of your pantry and suggests recipes.",
                        Toast.LENGTH_LONG).show());
    }
}