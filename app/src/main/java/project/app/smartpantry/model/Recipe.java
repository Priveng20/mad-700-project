package project.app.smartpantry.model;

import java.util.ArrayList;
import java.util.List;




//  steps are stored as one string, each step separated by newline
public class Recipe {

    private int id;
    private String name;
    private String steps;
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    // used before the recipe exists in the database, so there is no id yet
    public Recipe(String name, String steps) {
        this.name = name;
        this.steps = steps;
    }



    // used when the recipe already exists in the database and its id is known
    public Recipe(int id, String name, String steps) {
        this.id = id;
        this.name = name;
        this.steps = steps;
    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
