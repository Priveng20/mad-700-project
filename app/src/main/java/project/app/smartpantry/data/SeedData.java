package project.app.smartpantry.data;

import java.util.ArrayList;
import java.util.List;
import project.app.smartpantry.model.RecipeIngredient;

// recipes that get put into the database the first time the app runs
public class SeedData {

    // one recipe waiting to be inserted: its name, its steps and its ingredients
    public static class SeedRecipe {
        private final String name;
        private final String steps;
        private final List<RecipeIngredient> ingredients = new ArrayList<>();

        public SeedRecipe(String name, String... steps) {
            this.name = name;
            this.steps = String.join("\n", steps);
        }


        public SeedRecipe add(String ingredientName, double quantity, String unit) {
            ingredients.add(new RecipeIngredient(0, ingredientName, quantity, unit));
            return this;
        }

        public String getName() {
            return name;
        }

        public String getSteps() {
            return steps;
        }

        public List<RecipeIngredient> getIngredients() {
            return ingredients;
        }
    }

    public static List<SeedRecipe> getRecipes() {
        List<SeedRecipe> recipes = new ArrayList<>();

        // tiny recipes with few ingredients so a match is easy to show with a small pantry
        recipes.add(new SeedRecipe("Toast with butter and jam",
                "toast the bread until golden.",
                "spread the butter on the warm toast.",
                "spread the jam on top and serve.")
                .add("bread", 2, "pcs")
                .add("butter", 10, "g")
                .add("jam", 20, "g"));

        recipes.add(new SeedRecipe("Boiled eggs on toast",
                "boil the eggs for 8 minutes.",
                "cool them in cold water, then peel.",
                "toast the bread.",
                "slice the eggs onto the toast.")
                .add("eggs", 2, "pcs")
                .add("bread", 1, "pcs"));

        recipes.add(new SeedRecipe("Scrambled eggs",
                "beat the eggs and milk together.",
                "melt the butter in a pan on low heat.",
                "pour in the eggs and stir slowly until just set.")
                .add("eggs", 3, "pcs")
                .add("milk", 50, "ml")
                .add("butter", 10, "g"));

        recipes.add(new SeedRecipe("Pancakes",
                "mix the flour and sugar in a bowl.",
                "beat in the egg and milk until smooth.",
                "melt a little butter in a pan on medium heat.",
                "pour in a small ladle of batter and cook until bubbles form.",
                "flip and cook the other side for a minute.")
                .add("flour", 200, "g")
                .add("milk", 300, "ml")
                .add("eggs", 1, "pcs")
                .add("sugar", 25, "g")
                .add("butter", 20, "g"));

        recipes.add(new SeedRecipe("Tomato pasta",
                "boil the pasta in salted water until cooked.",
                "chop the onion, garlic and tomatoes.",
                "fry the onion and garlic in the olive oil until soft.",
                "add the tomatoes and simmer for 10 minutes.",
                "drain the pasta and stir it into the sauce.")
                .add("pasta", 200, "g")
                .add("tomatoes", 3, "pcs")
                .add("onion", 1, "pcs")
                .add("garlic", 2, "pcs")
                .add("olive oil", 2, "tbsp"));

        recipes.add(new SeedRecipe("Cheese toastie",
                "butter one side of each slice of bread.",
                "put the cheese between the slices, butter on the outside.",
                "fry in a pan on medium heat for 3 minutes each side until golden.")
                .add("bread", 2, "pcs")
                .add("cheese", 50, "g")
                .add("butter", 10, "g"));

        recipes.add(new SeedRecipe("French toast",
                "whisk the eggs, milk and sugar in a shallow dish.",
                "soak each slice of bread in the mixture for a few seconds.",
                "melt the butter in a pan on medium heat.",
                "fry the bread for 2 minutes each side until golden.")
                .add("bread", 2, "pcs")
                .add("eggs", 2, "pcs")
                .add("milk", 100, "ml")
                .add("sugar", 10, "g")
                .add("butter", 15, "g"));

        recipes.add(new SeedRecipe("Cheese and onion omelette",
                "finely chop the onion and grate the cheese.",
                "soften the onion in the butter for 5 minutes.",
                "beat the eggs and pour them over the onion.",
                "scatter the cheese on top, fold the omelette in half and serve.")
                .add("eggs", 3, "pcs")
                .add("cheese", 30, "g")
                .add("onion", 1, "pcs")
                .add("butter", 10, "g"));

        recipes.add(new SeedRecipe("Garlic pasta",
                "boil the pasta in salted water until cooked.",
                "slice the garlic thinly.",
                "qarm the olive oil in a pan and cook the garlic gently until golden.",
                "toss the drained pasta in the garlic oil and top with the cheese.")
                .add("pasta", 200, "g")
                .add("garlic", 3, "pcs")
                .add("olive oil", 3, "tbsp")
                .add("cheese", 20, "g"));

        recipes.add(new SeedRecipe("Tomato soup",
                "chop the onion, garlic and tomatoes.",
                "soften the onion and garlic in the olive oil for 5 minutes.",
                "add the tomatoes and the stock and simmer for 20 minutes.",
                "blend until smooth and serve hot.")
                .add("tomatoes", 6, "pcs")
                .add("onion", 1, "pcs")
                .add("garlic", 2, "pcs")
                .add("olive oil", 1, "tbsp")
                .add("vegetable stock", 500, "ml"));

        return recipes;
    }
}
