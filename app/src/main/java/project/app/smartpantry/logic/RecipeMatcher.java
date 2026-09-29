package project.app.smartpantry.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



import project.app.smartpantry.model.PantryItem;
import project.app.smartpantry.model.Recipe;
import project.app.smartpantry.model.RecipeIngredient;







// recipe matching rule and name and unit clean up
public class RecipeMatcher {

    // words that end in s but are already singular
    private static final List<String> NO_CHANGE_WORDS = Arrays.asList(
            "hummus", "couscous", "asparagus", "molasses", "citrus");




    private static final Map<String, String> SPECIAL_PLURALS = new HashMap<>();

    static {
        SPECIAL_PLURALS.put("loaves", "loaf");
        SPECIAL_PLURALS.put("halves", "half");
        SPECIAL_PLURALS.put("leaves", "leaf");
        // the "ses" rule would turn this into "chees"
        SPECIAL_PLURALS.put("cheeses", "cheese");
    }

    // other ways of writing a unit, mapped to the short form used in the unit dropdown
    private static final Map<String, String> UNIT_ALIASES = new HashMap<>();





    static {
        UNIT_ALIASES.put("gram", "g");
        UNIT_ALIASES.put("grams", "g");
        UNIT_ALIASES.put("gr", "g");
        UNIT_ALIASES.put("kilogram", "kg");
        UNIT_ALIASES.put("kilograms", "kg");
        UNIT_ALIASES.put("kilo", "kg");
        UNIT_ALIASES.put("kilos", "kg");
        UNIT_ALIASES.put("millilitre", "ml");
        UNIT_ALIASES.put("millilitres", "ml");
        UNIT_ALIASES.put("milliliter", "ml");
        UNIT_ALIASES.put("milliliters", "ml");
        UNIT_ALIASES.put("litre", "l");
        UNIT_ALIASES.put("litres", "l");
        UNIT_ALIASES.put("liter", "l");
        UNIT_ALIASES.put("liters", "l");
        UNIT_ALIASES.put("teaspoon", "tsp");
        UNIT_ALIASES.put("teaspoons", "tsp");
        UNIT_ALIASES.put("tablespoon", "tbsp");
        UNIT_ALIASES.put("tablespoons", "tbsp");
        UNIT_ALIASES.put("cups", "cup");
        UNIT_ALIASES.put("pc", "pcs");
        UNIT_ALIASES.put("piece", "pcs");
        UNIT_ALIASES.put("pieces", "pcs");
        UNIT_ALIASES.put("whole", "pcs");
    }



    // turns an ingredient name into one standard form
    public static String normaliseName(String name) {
        String cleaned = name.trim().toLowerCase(Locale.ROOT);
        cleaned = cleaned.replaceAll("\\s+", " ");
        return toSingular(cleaned);
    }




    // plural rules applied to the end of the name
    private static String toSingular(String name) {
        // the exception lists checks
        for (String word : NO_CHANGE_WORDS) {
            if (endsWithWord(name, word)) {
                return name;
            }
        }
        for (Map.Entry<String, String> special : SPECIAL_PLURALS.entrySet()) {
            if (endsWithWord(name, special.getKey())) {
                String start = name.substring(0, name.length() - special.getKey().length());
                return start + special.getValue();
            }
        }

        if (name.endsWith("ies")) {
            return name.substring(0, name.length() - 3) + "y";
        }
        if (name.endsWith("oes")) {
            return name.substring(0, name.length() - 2);
        }
        if (name.endsWith("ses") || name.endsWith("ches") || name.endsWith("shes") || name.endsWith("xes")) {
            return name.substring(0, name.length() - 2);
        }
        if (name.endsWith("s") && !name.endsWith("ss")) {
            return name.substring(0, name.length() - 1);
        }
        return name;
    }




    private static boolean endsWithWord(String name, String word) {
        return name.equals(word) || name.endsWith(" " + word);
    }



    public static String normaliseUnit(String unit) {
        String cleaned = unit.trim().toLowerCase(Locale.ROOT);
        if (UNIT_ALIASES.containsKey(cleaned)) {
            return UNIT_ALIASES.get(cleaned);
        }
        return cleaned;
    }


    public static final String WEIGHT = "weight";
    public static final String VOLUME = "volume";
    public static final String COUNT = "count";


    public static class BaseAmount {
        public final String family;
        public final double quantity;

        public BaseAmount(String family, double quantity) {
            this.family = family;
            this.quantity = quantity;
        }
    }

    //converts something like 1.5 kg into weight, 1500 or 2 cup into volume, 500
    public static BaseAmount toBaseUnit(double quantity, String unit) {
        switch (normaliseUnit(unit)) {
            case "g":
                return new BaseAmount(WEIGHT, quantity);
            case "kg":
                return new BaseAmount(WEIGHT, quantity * 1000);
            case "ml":
                return new BaseAmount(VOLUME, quantity);
            case "l":
                return new BaseAmount(VOLUME, quantity * 1000);
            case "tsp":
                return new BaseAmount(VOLUME, quantity * 5);
            case "tbsp":
                return new BaseAmount(VOLUME, quantity * 15);
            case "cup":
                return new BaseAmount(VOLUME, quantity * 250);
            case "pcs":
                return new BaseAmount(COUNT, quantity);
            default:
                return null;
        }
    }

    // the key used in the totals, normalised name
    public static String totalsKey(String name, String family) {
        return normaliseName(name) + "|" + family;
    }

    // adds up the whole pantry by ingredient
    public static Map<String, Double> buildPantryTotals(List<PantryItem> pantryItems) {
        Map<String, Double> totals = new HashMap<>();

        for (PantryItem item : pantryItems) {
            BaseAmount amount = toBaseUnit(item.getQuantity(), item.getUnit());
            if (amount == null) {
                continue;
            }

            String key = totalsKey(item.getName(), amount.family);
            Double current = totals.get(key);
            if (current == null) {
                current = 0.0;
            }
            totals.put(key, current + amount.quantity);
        }

        return totals;
    }





    private static final double TOLERANCE = 0.0001;

    // true only if every ingredient is in the pantry
    public static boolean canMake(Recipe recipe, Map<String, Double> pantryTotals) {
        List<RecipeIngredient> ingredients = recipe.getIngredients();
        // make it so that a recipe with no ingredients means its ingredients weren't loaded, not that it's free
        if (ingredients == null || ingredients.isEmpty()) {
            return false;
        }






        for (RecipeIngredient ingredient : ingredients) {
            BaseAmount needed = toBaseUnit(ingredient.getQuantity(), ingredient.getUnit());
            if (needed == null) {
                return false;
            }



            Double have = pantryTotals.get(totalsKey(ingredient.getIngredientName(), needed.family));
            if (have == null || have + TOLERANCE < needed.quantity) {
                return false;
            }
        }

        return true;
    }



    // matching rule, returns only the recipes the pantry can make right now
    public static List<Recipe> getStrictMatches(List<PantryItem> pantryItems, List<Recipe> recipes) {
        Map<String, Double> totals = buildPantryTotals(pantryItems);

        List<Recipe> matches = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (canMake(recipe, totals)) {
                matches.add(recipe);
            }
        }
        return matches;
    }
}
