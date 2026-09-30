package com.example.smartpantrymanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.AddEditIngredientActivity;
import com.example.smartpantrymanager.Data.AppDatabase;
import com.example.smartpantrymanager.Data.Ingredient;
import com.example.smartpantrymanager.R;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private Context context;
    private AppDatabase db;

    public PantryAdapter(List<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
        this.db = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.textViewName.setText(ingredient.name);
        holder.textViewQuantity.setText(ingredient.quantity + " " + ingredient.unit);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditIngredientActivity.class);
            intent.putExtra("INGREDIENT_ID", ingredient.id);
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            db.appDao().deleteIngredient(ingredient);
            ingredients.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewQuantity;

        ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewIngredientName);
            textViewQuantity = itemView.findViewById(R.id.textViewIngredientQuantity);
        }
    }
}