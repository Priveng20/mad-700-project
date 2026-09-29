package project.app.smartpantry.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import project.app.smartpantry.R;
import project.app.smartpantry.model.Recipe;

// connects a list of recipes to the recyclerview, one item_recipe row per recipe
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    // swaps in a fresh list and retake in every row
    public void setRecipes(List<Recipe> newRecipes) {
        this.recipes = newRecipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.name.setText(recipe.getName());
        holder.ingredientCount.setText(holder.itemView.getContext().getString(
                R.string.recipe_ingredient_count, recipe.getIngredients().size()));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView ingredientCount;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recipe_name);
            ingredientCount = itemView.findViewById(R.id.recipe_ingredient_count);
        }
    }
}
