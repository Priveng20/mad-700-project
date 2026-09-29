package project.app.smartpantry.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import project.app.smartpantry.R;
import project.app.smartpantry.data.DatabaseHelper;
import project.app.smartpantry.model.Recipe;
import project.app.smartpantry.model.RecipeIngredient;

// shows one recipe
public class RecipeDetailActivity extends AppCompatActivity {

    // key for the recipe id passed in the intent
    public static final String EXTRA_RECIPE_ID = "recipe_id";
    private static final int NO_RECIPE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        setTitle(R.string.recipe_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, NO_RECIPE);
        Recipe recipe = null;
        if (recipeId != NO_RECIPE) {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            recipe = dbHelper.getRecipe(recipeId);
            dbHelper.close();
        }





        if (recipe == null) {
            Toast.makeText(this, R.string.error_recipe_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        showRecipe(recipe);
    }

    private void showRecipe(Recipe recipe) {
        TextView title = findViewById(R.id.recipe_title);
        TextView ingredientsText = findViewById(R.id.ingredients_text);
        TextView stepsText = findViewById(R.id.steps_text);

        title.setText(recipe.getName());

        // one line per ingredient
        StringBuilder ingredients = new StringBuilder();
        for (RecipeIngredient ingredient : recipe.getIngredients()) {
            if (ingredients.length() > 0) {
                ingredients.append("\n");
            }
            ingredients.append(getString(R.string.ingredient_line, ingredient.getQuantityText(),
                    ingredient.getUnit(), ingredient.getIngredientName()));
        }
        ingredientsText.setText(ingredients.toString());

        // separated by a newline
        String[] steps = recipe.getSteps().split("\n");
        StringBuilder method = new StringBuilder();
        for (int i = 0; i < steps.length; i++) {
            if (method.length() > 0) {
                method.append("\n\n");
            }
            method.append(getString(R.string.step_line, i + 1, steps[i]));
        }
        stepsText.setText(method.toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
