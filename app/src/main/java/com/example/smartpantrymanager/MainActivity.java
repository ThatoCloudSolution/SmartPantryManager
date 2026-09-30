package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.Adapters.PantryAdapter;
import com.example.smartpantrymanager.Data.AppDatabase;
import com.example.smartpantrymanager.Data.Ingredient;
import com.example.smartpantrymanager.Data.Recipe;
import com.example.smartpantrymanager.Data.RecipeIngredient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PantryAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);

        if (db.appDao().getAllRecipes().isEmpty()) {
            prePopulateDatabase();
        }

        recyclerView = findViewById(R.id.recyclerViewPantry);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadIngredients();

        FloatingActionButton fab = findViewById(R.id.fabAddIngredient);
        fab.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this,
                        AddEditIngredientActivity.class)));

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_pantry) {
                return true;
            } else if (item.getItemId() == R.id.navigation_suggest) {
                startActivity(new Intent(MainActivity.this,
                        SuggestedRecipesActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation_settings) {
                startActivity(new Intent(MainActivity.this,
                        SettingsActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients();
    }

    private void loadIngredients() {
        List<Ingredient> ingredients = db.appDao().getAllIngredients();
        adapter = new PantryAdapter(ingredients, this);
        recyclerView.setAdapter(adapter);
    }

    private void prePopulateDatabase() {

        // ===== Recipe 1: Spaghetti Carbonara =====
        Recipe r1 = new Recipe("Spaghetti Carbonara",
                "1. Boil pasta.\n2. Fry pancetta.\n3. Mix eggs and cheese.\n4. Combine.");
        long id1 = db.appDao().insertRecipe(r1);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id1, "Spaghetti", "200", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id1, "Eggs", "2", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id1, "Pancetta", "100", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id1, "Parmesan Cheese", "50", "g"));

        // ===== Recipe 2: Simple Salad =====
        Recipe r2 = new Recipe("Simple Salad",
                "1. Chop lettuce.\n2. Chop tomato.\n3. Add dressing.\n4. Toss.");
        long id2 = db.appDao().insertRecipe(r2);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id2, "Lettuce", "1", "head"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id2, "Tomato", "2", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id2, "Olive Oil", "2", "tbsp"));

        // ===== Recipe 3: Omelette =====
        Recipe r3 = new Recipe("Omelette",
                "1. Beat eggs.\n2. Heat pan.\n3. Cook eggs.\n4. Fold and serve.");
        long id3 = db.appDao().insertRecipe(r3);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id3, "Eggs", "3", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id3, "Cheese", "50", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id3, "Milk", "2", "tbsp"));

        // ===== Recipe 4: Pasta with Tomato Sauce =====
        Recipe r4 = new Recipe("Pasta with Tomato Sauce",
                "1. Boil pasta.\n2. Heat sauce.\n3. Combine.\n4. Serve.");
        long id4 = db.appDao().insertRecipe(r4);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id4, "Pasta", "200", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id4, "Tomato Sauce", "200", "ml"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id4, "Parmesan Cheese", "30", "g"));

        // ===== Recipe 5: Pancakes =====
        Recipe r5 = new Recipe("Pancakes",
                "1. Mix flour, eggs, milk.\n2. Heat pan.\n3. Pour batter.\n4. Flip.");
        long id5 = db.appDao().insertRecipe(r5);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id5, "Flour", "200", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id5, "Eggs", "2", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id5, "Milk", "300", "ml"));

        // ===== Recipe 6: Samp and Beans =====
        Recipe r6 = new Recipe("Samp and Beans",
                "1. Soak samp and beans overnight.\n2. Boil until soft.\n3. Fry onion, garlic, and curry powder.\n4. Add tomatoes, simmer.\n5. Mix with samp and beans.");
        long id6 = db.appDao().insertRecipe(r6);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id6, "Samp", "500", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id6, "Sugar Beans", "350", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id6, "Onion", "1", "unit"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id6, "Garlic", "3", "cloves"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id6, "Curry Powder", "10", "ml"));

        // ===== Recipe 7: Kota (Sphatlho) =====
        Recipe r7 = new Recipe("Kota (Sphatlho)",
                "1. Cut bread loaf in half.\n2. Fry chips.\n3. Fry egg, polony, and Russian.\n4. Layer everything inside bread.\n5. Add atchar and cheese.");
        long id7 = db.appDao().insertRecipe(r7);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id7, "Bread Loaf", "1", "unit"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id7, "Potatoes", "4", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id7, "Egg", "1", "unit"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id7, "Polony", "4", "slices"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id7, "Russian Sausage", "1", "unit"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id7, "Atchar", "2", "tbsp"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id7, "Cheese", "2", "slices"));

        // ===== Recipe 8: Pap and Stew =====
        Recipe r8 = new Recipe("Pap and Stew",
                "1. Boil water, add maize meal, stir until thick.\n2. Fry onion, garlic, and ginger.\n3. Brown beef, add stock.\n4. Simmer until tender.\n5. Serve stew over pap.");
        long id8 = db.appDao().insertRecipe(r8);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id8, "Maize Meal", "500", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id8, "Beef", "500", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id8, "Onion", "1", "unit"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id8, "Garlic", "3", "cloves"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id8, "Beef Stock", "500", "ml"));

        // ===== Recipe 9: Chakalaka =====
        Recipe r9 = new Recipe("Chakalaka",
                "1. Fry onion, garlic, and chillies.\n2. Add curry powder and grated carrots.\n3. Add peppers and tomatoes.\n4. Stir in beans and simmer.");
        long id9 = db.appDao().insertRecipe(r9);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id9, "Carrots", "5", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id9, "Onion", "1", "unit"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id9, "Bell Pepper", "2", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id9, "Canned Beans", "400", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id9, "Curry Powder", "2", "tbsp"));

        // ===== Recipe 10: Amagwinya (Vetkoek) =====
        Recipe r10 = new Recipe("Amagwinya (Vetkoek)",
                "1. Mix flour, yeast, salt, and sugar.\n2. Add warm water, knead dough.\n3. Let rise for 1 hour.\n4. Shape into balls.\n5. Deep fry until golden.");
        long id10 = db.appDao().insertRecipe(r10);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id10, "Flour", "500", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id10, "Yeast", "10", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id10, "Sugar", "2", "tbsp"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id10, "Salt", "1", "tsp"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id10, "Oil", "500", "ml"));

        // ===== Recipe 11: Milk Tart =====
        Recipe r11 = new Recipe("Milk Tart",
                "1. Mix flour and butter for crust, press into tin.\n2. Boil milk with cinnamon.\n3. Whisk eggs, sugar, corn flour.\n4. Combine and cook until thick.\n5. Pour into crust, chill, dust with cinnamon.");
        long id11 = db.appDao().insertRecipe(r11);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id11, "Flour", "250", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id11, "Butter", "125", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id11, "Milk", "1", "litre"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id11, "Sugar", "150", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id11, "Eggs", "3", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id11, "Corn Flour", "60", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id11, "Cinnamon", "2", "tsp"));

        // ===== Recipe 12: Lasagna =====
        Recipe r12 = new Recipe("Lasagna",
                "1. Cook lasagna sheets.\n2. Brown mince with onion and garlic.\n3. Add tomato sauce and simmer.\n4. Layer sheets, mince, and cheese sauce.\n5. Top with cheese, bake at 180C for 30 min.");
        long id12 = db.appDao().insertRecipe(r12);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id12, "Lasagna Sheets", "250", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id12, "Beef Mince", "500", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id12, "Onion", "1", "unit"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id12, "Tomato Sauce", "500", "ml"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id12, "Cheese", "250", "g"));

        // ===== Recipe 13: Roast Chicken =====
        Recipe r13 = new Recipe("Roast Chicken",
                "1. Rub chicken with spices and butter.\n2. Place in roasting pan with veggies.\n3. Roast at 200C for 1 hour.\n4. Baste halfway.\n5. Rest before serving.");
        long id13 = db.appDao().insertRecipe(r13);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id13, "Whole Chicken", "1", "unit"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id13, "Butter", "50", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id13, "Potatoes", "4", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id13, "Carrots", "3", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id13, "Paprika", "2", "tsp"));

        // ===== Recipe 14: Mango Lemonade =====
        Recipe r14 = new Recipe("Mango Lemonade",
                "1. Blend ripe mango with water.\n2. Strain pulp.\n3. Add lemon juice and sugar.\n4. Stir until dissolved.\n5. Chill and serve over ice.");
        long id14 = db.appDao().insertRecipe(r14);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id14, "Mango", "2", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id14, "Lemon", "3", "units"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id14, "Sugar", "100", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id14, "Water", "1", "litre"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id14, "Ice", "1", "cup"));

        // ===== Recipe 15: Chickpea Curry =====
        Recipe r15 = new Recipe("Chickpea Curry",
                "1. Fry onion, garlic, and ginger.\n2. Add curry powder and tomatoes.\n3. Stir in chickpeas.\n4. Add coconut milk, simmer 20 min.\n5. Serve with rice.");
        long id15 = db.appDao().insertRecipe(r15);
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id15, "Chickpeas", "400", "g"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id15, "Onion", "1", "unit"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id15, "Garlic", "3", "cloves"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id15, "Curry Powder", "2", "tbsp"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id15, "Coconut Milk", "400", "ml"));
        db.appDao().insertRecipeIngredient(new RecipeIngredient((int) id15, "Tomatoes", "2", "units"));

        Toast.makeText(this, "Recipes seeded!", Toast.LENGTH_SHORT).show();
    }
}