package project.app.smartpantry.ui;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import project.app.smartpantry.R;
import project.app.smartpantry.adapter.RecipeAdapter;
import project.app.smartpantry.data.DatabaseHelper;
import project.app.smartpantry.logic.RecipeMatcher;
import project.app.smartpantry.model.PantryItem;
import project.app.smartpantry.model.Recipe;





// lists only the recipes the pantry can make right now
public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecipeAdapter adapter;
    private TextView noMatchesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        setTitle(R.string.suggested_recipes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noMatchesText = findViewById(R.id.no_matches_text);

        RecyclerView recipeList = findViewById(R.id.recipe_list);
        recipeList.setLayoutManager(new LinearLayoutManager(this));
        recipeList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // tapping a recipe opens its detail screen
        adapter = new RecipeAdapter(new ArrayList<>(), recipe -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.getId());
            startActivity(intent);
        });
        recipeList.setAdapter(adapter);

        dbHelper = new DatabaseHelper(this);
    }





    // matching runs every time the screen comes to the front
    @Override
    protected void onResume() {
        super.onResume();
        loadMatches();
    }

    private void loadMatches() {
        List<PantryItem> pantry = dbHelper.getAllPantryItems();
        List<Recipe> recipes = dbHelper.getAllRecipesWithIngredients();
        List<Recipe> matches = RecipeMatcher.getStrictMatches(pantry, recipes);

        adapter.setRecipes(matches);

        // a message instead of a blank screen when nothing can be made
        if (matches.isEmpty()) {
            noMatchesText.setVisibility(View.VISIBLE);
        } else {
            noMatchesText.setVisibility(View.GONE);
        }
    }




    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
