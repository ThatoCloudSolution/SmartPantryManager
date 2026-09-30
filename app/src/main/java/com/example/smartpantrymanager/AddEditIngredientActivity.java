package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.Data.AppDatabase;
import com.example.smartpantrymanager.Data.Ingredient;

public class AddEditIngredientActivity extends AppCompatActivity {
    private EditText editTextName, editTextQuantity, editTextUnit, editTextExpiry;
    private Button buttonSave;
    private AppDatabase db;
    private Ingredient editingIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        db = AppDatabase.getInstance(this);
        editTextName = findViewById(R.id.editTextName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextUnit = findViewById(R.id.editTextUnit);
        editTextExpiry = findViewById(R.id.editTextExpiry);
        buttonSave = findViewById(R.id.buttonSaveIngredient);

        int ingredientId = getIntent().getIntExtra("INGREDIENT_ID", -1);
        if (ingredientId != -1) {
            for (Ingredient i : db.appDao().getAllIngredients()) {
                if (i.id == ingredientId) {
                    editingIngredient = i;
                    break;
                }
            }
            if (editingIngredient != null) {
                editTextName.setText(editingIngredient.name);
                editTextQuantity.setText(editingIngredient.quantity);
                editTextUnit.setText(editingIngredient.unit);
                editTextExpiry.setText(editingIngredient.expiryDate);
            }
        }

        buttonSave.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String name = editTextName.getText().toString().trim();
        String quantity = editTextQuantity.getText().toString().trim();
        String unit = editTextUnit.getText().toString().trim();
        String expiry = editTextExpiry.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("Name is required");
            return;
        }
        if (quantity.isEmpty()) {
            editTextQuantity.setError("Quantity is required");
            return;
        }

        if (editingIngredient != null) {
            editingIngredient.name = name;
            editingIngredient.quantity = quantity;
            editingIngredient.unit = unit;
            editingIngredient.expiryDate = expiry;
            db.appDao().updateIngredient(editingIngredient);
            Toast.makeText(this, "Ingredient Updated", Toast.LENGTH_SHORT).show();
        } else {
            db.appDao().insertIngredient(new Ingredient(name, quantity, unit, expiry));
            Toast.makeText(this, "Ingredient Added", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}