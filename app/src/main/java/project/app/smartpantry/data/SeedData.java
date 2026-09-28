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

        recipes.add(new SeedRecipe("Banana pancakes",
                "mash the bananas in a bowl until smooth.",
                "beat in the eggs to make a runny batter.",
                "melt the butter in a pan on medium heat.",
                "cook small spoonfuls for 1 to 2 minutes each side until golden.")
                .add("bananas", 2, "pcs")
                .add("eggs", 2, "pcs")
                .add("butter", 10, "g"));

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
                "heat up the olive oil in a pan and cook the garlic gently until golden.",
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

        recipes.add(new SeedRecipe("Spanish tortilla",
                "peel the potatoes and slice them thinly, then slice the onion.",
                "cook the potatoes and onion slowly in the olive oil for 15 minutes until soft.",
                "beat the eggs in a bowl and stir in the potatoes and onion.",
                "pour it all back into the pan and cook on low heat for 8 minutes.",
                "flip it onto a plate, slide it back in and cook the other side for 3 minutes.")
                .add("potatoes", 3, "pcs")
                .add("eggs", 5, "pcs")
                .add("onion", 1, "pcs")
                .add("olive oil", 4, "tbsp"));

        recipes.add(new SeedRecipe("Spaghetti carbonara",
                "boil the pasta in salted water until cooked.",
                "chop the bacon and fry it until crispy.",
                "beat the eggs with the grated cheese.",
                "take the pan off the heat, add the drained pasta and stir in the egg mix quickly so it turns creamy, not scrambled.")
                .add("pasta", 200, "g")
                .add("bacon", 100, "g")
                .add("eggs", 2, "pcs")
                .add("cheese", 50, "g"));

        recipes.add(new SeedRecipe("Egg fried rice",
                "cook the rice and let it cool down.",
                "chop the onion then fry it in the olive oil for 3 minutes.",
                "add the rice and fry for 3 minutes.",
                "push the rice aside, scramble the eggs in the gap, then mix together.",
                "stir in the soy sauce and serve.")
                .add("rice", 200, "g")
                .add("eggs", 2, "pcs")
                .add("onion", 1, "pcs")
                .add("olive oil", 1, "tbsp")
                .add("soy sauce", 2, "tbsp"));

        recipes.add(new SeedRecipe("Macaroni and cheese",
                "boil the pasta until cooked, then drain.",
                "melt the butter in a pan and stir in the flour for 1 minute.",
                "slowly whisk in the milk until the sauce thickens.",
                "stir in the cheese until melted.",
                "mix the sauce through the pasta and serve.")
                .add("pasta", 200, "g")
                .add("butter", 25, "g")
                .add("flour", 25, "g")
                .add("milk", 300, "ml")
                .add("cheese", 100, "g"));

        recipes.add(new SeedRecipe("Potato soup",
                "peel and chop the potatoes and onion.",
                "soften the onion in the butter for 5 minutes.",
                "add the potatoes and stock and simmer for 20 minutes.",
                "blend until smooth, stir in the milk and serve.")
                .add("potatoes", 3, "pcs")
                .add("onion", 1, "pcs")
                .add("butter", 20, "g")
                .add("vegetable stock", 750, "ml")
                .add("milk", 100, "ml"));

        recipes.add(new SeedRecipe("Chicken quesadillas",
                "cut the chicken into strips, slice the onion and grate the cheese.",
                "fry the chicken with the onions for 8 minutes until the chicken is cooked through.",
                "lay the filling and cheese on one tortilla and put the other on top.",
                "cook in a dry pan for 2 minutes each side until crisp, then cut into wedges.")
                .add("tortillas", 2, "pcs")
                .add("chicken", 150, "g")
                .add("cheese", 60, "g")
                .add("onion", 1, "pcs"));

        recipes.add(new SeedRecipe("Bruschetta",
                "chop the tomatoes finely and mix them with half the olive oil.",
                "toast the bread until crisp.",
                "rub each slice with the cut side of a garlic clove.",
                "spoon the tomatoes on top and drizzle with the rest of the olive oil.")
                .add("bread", 4, "pcs")
                .add("tomatoes", 3, "pcs")
                .add("garlic", 1, "pcs")
                .add("olive oil", 2, "tbsp"));

        recipes.add(new SeedRecipe("Cheesy potato gratin",
                "heat the oven to 190 degrees and butter a baking dish.",
                "slice the potatoes thinly and crush the garlic.",
                "layer the potatoes in the dish, scattering garlic and cheese between the layers.",
                "pour over the milk and dot the rest of the butter on top.",
                "bake for 50 minutes until golden and tender.")
                .add("potatoes", 4, "pcs")
                .add("milk", 300, "ml")
                .add("cheese", 80, "g")
                .add("butter", 20, "g")
                .add("garlic", 1, "pcs"));

        recipes.add(new SeedRecipe("Chicken fried rice",
                "cook the rice and let it cool.",
                "cut the chicken into small pieces and chop the onion and garlic.",
                "fry the chicken in the olive oil for 6 minutes until cooked through.",
                "add the onion and garlic and fry for 2 minutes.",
                "stir in the rice and soy sauce and fry for 3 more minutes.")
                .add("chicken", 300, "g")
                .add("rice", 200, "g")
                .add("onion", 1, "pcs")
                .add("garlic", 2, "pcs")
                .add("olive oil", 1, "tbsp")
                .add("soy sauce", 2, "tbsp"));

        recipes.add(new SeedRecipe("Vegetable curry",
                "cook the rice.",
                "chop the onion, garlic, potatoes and tomatoes.",
                "fry the onion and garlic with the curry paste for 2 minutes.",
                "add the potatoes, tomatoes and coconut milk and simmer for 25 minutes.",
                "serve the curry with the rice.")
                .add("potatoes", 2, "pcs")
                .add("onion", 1, "pcs")
                .add("garlic", 2, "pcs")
                .add("tomatoes", 3, "pcs")
                .add("curry paste", 2, "tbsp")
                .add("coconut milk", 400, "ml")
                .add("rice", 200, "g"));

        return recipes;
    }
}
