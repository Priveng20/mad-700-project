package project.app.smartpantry.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import project.app.smartpantry.data.SeedData;
import project.app.smartpantry.model.PantryItem;
import project.app.smartpantry.model.Recipe;
import project.app.smartpantry.model.RecipeIngredient;







public class RecipeMatcherTest {


    @Test
    public void tomatoesBecomesTomato() {
        assertEquals("tomato", RecipeMatcher.normaliseName("tomatoes"));
    }

    @Test
    public void potatoesBecomesPotato() {
        assertEquals("potato", RecipeMatcher.normaliseName("potatoes"));
    }

    @Test
    public void berriesBecomesBerry() {
        assertEquals("berry", RecipeMatcher.normaliseName("berries"));
    }



    @Test
    public void onionsBecomesOnion() {
        assertEquals("onion", RecipeMatcher.normaliseName("onions"));
    }

    @Test
    public void eggAndEggsNormaliseToTheSameName() {
        assertEquals(RecipeMatcher.normaliseName("egg"), RecipeMatcher.normaliseName("eggs"));
    }

    @Test
    public void singularNameIsLeftAlone() {
        assertEquals("onion", RecipeMatcher.normaliseName("onion"));
    }


    @Test
    public void esEndingsAfterSChShXDropEs() {
        assertEquals("glass", RecipeMatcher.normaliseName("glasses"));
        assertEquals("peach", RecipeMatcher.normaliseName("peaches"));
        assertEquals("dish", RecipeMatcher.normaliseName("dishes"));
        assertEquals("box", RecipeMatcher.normaliseName("boxes"));
    }

    @Test
    public void wordEndingInDoubleSIsNotChanged() {
        assertEquals("glass", RecipeMatcher.normaliseName("glass"));
    }

    @Test
    public void capitalsAndOuterSpacesAreIgnored() {
        assertEquals("tomato", RecipeMatcher.normaliseName("  TOMATOES  "));
    }

    @Test
    public void repeatedInnerSpacesCollapseToOne() {
        assertEquals("red onion", RecipeMatcher.normaliseName("Red    Onions"));
    }

    @Test
    public void multiWordNameOnlyChangesTheLastWord() {
        assertEquals("baked bean", RecipeMatcher.normaliseName("baked beans"));
        assertEquals("olive oil", RecipeMatcher.normaliseName("Olive Oil"));
    }

    // normalise name

    @Test
    public void wordsThatEndInSButAreSingularAreNotDamaged() {
        assertEquals("hummus", RecipeMatcher.normaliseName("hummus"));
        assertEquals("couscous", RecipeMatcher.normaliseName("couscous"));
        assertEquals("asparagus", RecipeMatcher.normaliseName("Asparagus"));
        assertEquals("molasses", RecipeMatcher.normaliseName("molasses"));
    }

    @Test
    public void exceptionWordWorksAsLastWordOfALongerName() {
        assertEquals("garlic hummus", RecipeMatcher.normaliseName("Garlic Hummus"));
    }

    @Test
    public void listedVesWordsBecomeF() {
        assertEquals("loaf", RecipeMatcher.normaliseName("loaves"));
        assertEquals("half", RecipeMatcher.normaliseName("halves"));
        assertEquals("bay leaf", RecipeMatcher.normaliseName("bay leaves"));
    }

    @Test
    public void otherVesWordsJustLoseTheS() {
        assertEquals("olive", RecipeMatcher.normaliseName("olives"));
        assertEquals("clove", RecipeMatcher.normaliseName("cloves"));
    }

    @Test
    public void cheesesBecomesCheese() {
        assertEquals("cheese", RecipeMatcher.normaliseName("cheeses"));
        assertEquals(RecipeMatcher.normaliseName("cheese"), RecipeMatcher.normaliseName("Cheeses"));
    }

    // normalise unit

    @Test
    public void dropdownUnitsStayTheSame() {
        String[] units = {"g", "kg", "ml", "l", "tsp", "tbsp", "cup", "pcs"};
        for (String unit : units) {
            assertEquals(unit, RecipeMatcher.normaliseUnit(unit));
        }
    }

    @Test
    public void weightAliases() {
        assertEquals("g", RecipeMatcher.normaliseUnit("grams"));
        assertEquals("g", RecipeMatcher.normaliseUnit("gram"));
        assertEquals("g", RecipeMatcher.normaliseUnit("gr"));
        assertEquals("kg", RecipeMatcher.normaliseUnit("kilograms"));
        assertEquals("kg", RecipeMatcher.normaliseUnit("kilo"));
    }

    @Test
    public void volumeAliasesInBothSpellings() {
        assertEquals("ml", RecipeMatcher.normaliseUnit("millilitres"));
        assertEquals("ml", RecipeMatcher.normaliseUnit("milliliters"));
        assertEquals("l", RecipeMatcher.normaliseUnit("litres"));
        assertEquals("l", RecipeMatcher.normaliseUnit("liter"));
        assertEquals("tsp", RecipeMatcher.normaliseUnit("teaspoons"));
        assertEquals("tbsp", RecipeMatcher.normaliseUnit("tablespoon"));
        assertEquals("cup", RecipeMatcher.normaliseUnit("cups"));
    }

    @Test
    public void countAliases() {
        assertEquals("pcs", RecipeMatcher.normaliseUnit("pieces"));
        assertEquals("pcs", RecipeMatcher.normaliseUnit("piece"));
        assertEquals("pcs", RecipeMatcher.normaliseUnit("pc"));
        assertEquals("pcs", RecipeMatcher.normaliseUnit("whole"));
    }

    @Test
    public void unitCapitalsAndSpacesAreIgnored() {
        assertEquals("g", RecipeMatcher.normaliseUnit("  Grams "));
        assertEquals("kg", RecipeMatcher.normaliseUnit("KG"));
    }

    @Test
    public void unknownUnitComesBackCleanedButNotGuessed() {
        assertEquals("handful", RecipeMatcher.normaliseUnit(" Handful "));
    }


    private static final double DELTA = 0.0001;

    @Test
    public void kilogramsBecomeGrams() {
        RecipeMatcher.BaseAmount amount = RecipeMatcher.toBaseUnit(1.5, "kg");
        assertEquals(RecipeMatcher.WEIGHT, amount.family);
        assertEquals(1500, amount.quantity, DELTA);
    }

    @Test
    public void gramsStayGrams() {
        RecipeMatcher.BaseAmount amount = RecipeMatcher.toBaseUnit(250, "g");
        assertEquals(RecipeMatcher.WEIGHT, amount.family);
        assertEquals(250, amount.quantity, DELTA);
    }

    @Test
    public void litresBecomeMillilitres() {
        RecipeMatcher.BaseAmount amount = RecipeMatcher.toBaseUnit(2, "l");
        assertEquals(RecipeMatcher.VOLUME, amount.family);
        assertEquals(2000, amount.quantity, DELTA);
    }

    @Test
    public void cupsBecomeMillilitres() {
        assertEquals(500, RecipeMatcher.toBaseUnit(2, "cup").quantity, DELTA);
    }

    @Test
    public void tablespoonsAndTeaspoonsBecomeMillilitres() {
        assertEquals(30, RecipeMatcher.toBaseUnit(2, "tbsp").quantity, DELTA);
        assertEquals(15, RecipeMatcher.toBaseUnit(3, "tsp").quantity, DELTA);
        assertEquals(RecipeMatcher.VOLUME, RecipeMatcher.toBaseUnit(1, "tsp").family);
    }

    @Test
    public void piecesAreACountFamily() {
        RecipeMatcher.BaseAmount amount = RecipeMatcher.toBaseUnit(3, "pcs");
        assertEquals(RecipeMatcher.COUNT, amount.family);
        assertEquals(3, amount.quantity, DELTA);
    }

    @Test
    public void unitAliasesAreConvertedToo() {
        assertEquals(1000, RecipeMatcher.toBaseUnit(1, "Kilograms").quantity, DELTA);
        assertEquals(RecipeMatcher.COUNT, RecipeMatcher.toBaseUnit(2, "whole").family);
    }

    @Test
    public void unknownUnitIsNotGuessed() {
        assertNull(RecipeMatcher.toBaseUnit(1, "handful"));
    }


    private static PantryItem have(String name, double quantity, String unit) {
        return new PantryItem(name, quantity, unit, null);
    }

    private static RecipeIngredient needs(String name, double quantity, String unit) {
        return new RecipeIngredient(0, name, quantity, unit);
    }

    private static Recipe recipe(String name, RecipeIngredient... ingredients) {
        Recipe recipe = new Recipe(name, "step");
        recipe.setIngredients(new ArrayList<>(Arrays.asList(ingredients)));
        return recipe;
    }

    private static List<String> names(List<Recipe> recipes) {
        List<String> names = new ArrayList<>();
        for (Recipe recipe : recipes) {
            names.add(recipe.getName());
        }
        return names;
    }

    // pantry totals

    @Test
    public void duplicatePantryRowsAreAddedUp() {
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(Arrays.asList(
                have("flour", 100, "g"), have("flour", 150, "g")));
        assertEquals(250, totals.get("flour|weight"), DELTA);
    }

    @Test
    public void duplicateRowsInDifferentUnitsOfTheSameFamilyAddUp() {
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(Arrays.asList(
                have("Flour", 100, "g"), have("flour", 0.15, "kg")));
        assertEquals(250, totals.get("flour|weight"), DELTA);
    }

    @Test
    public void sameNameInDifferentFamiliesStaysSeparate() {
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(Arrays.asList(
                have("onion", 500, "g"), have("onions", 2, "pcs")));
        assertEquals(500, totals.get("onion|weight"), DELTA);
        assertEquals(2, totals.get("onion|count"), DELTA);
    }

    @Test
    public void pantryRowWithUnknownUnitIsLeftOutOfTotals() {
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(Arrays.asList(
                have("rice", 1, "handful")));
        assertTrue(totals.isEmpty());
    }

    // can make one recipe

    @Test
    public void canMakeIsFalseWhenOneOfFiveIngredientsIsMissing() {
        Recipe pancakes = recipe("pancakes",
                needs("flour", 200, "g"), needs("milk", 300, "ml"), needs("eggs", 1, "pcs"),
                needs("sugar", 25, "g"), needs("butter", 20, "g"));
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(Arrays.asList(
                have("flour", 1, "kg"), have("milk", 1, "l"), have("eggs", 6, "pcs"),
                have("sugar", 500, "g")));
        assertFalse(RecipeMatcher.canMake(pancakes, totals));
    }




    @Test
    public void canMakeIsTrueWhenEverythingIsThere() {
        Recipe toast = recipe("toast", needs("bread", 2, "pcs"), needs("butter", 10, "g"));
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(Arrays.asList(
                have("bread", 4, "pcs"), have("butter", 250, "g")));
        assertTrue(RecipeMatcher.canMake(toast, totals));
    }

    @Test
    public void canMakeIsFalseWhenThereIsNotEnough() {
        Recipe bread = recipe("bread", needs("flour", 500, "g"));
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(Arrays.asList(
                have("flour", 200, "g")));
        assertFalse(RecipeMatcher.canMake(bread, totals));
    }

    @Test
    public void exactlyTheNeededAmountIsEnough() {
        Recipe bread = recipe("bread", needs("flour", 500, "g"));
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(Arrays.asList(
                have("flour", 0.5, "kg")));
        assertTrue(RecipeMatcher.canMake(bread, totals));
    }

    @Test
    public void recipeWithNoIngredientsLoadedIsNeverMakeable() {
        assertFalse(RecipeMatcher.canMake(recipe("empty"), RecipeMatcher.buildPantryTotals(
                Arrays.asList(have("flour", 1, "kg")))));
    }



    @Test
    public void table_recipeNeedsFiveIngredientsPantryHasFour_notSuggested() {
        Recipe pancakes = recipe("pancakes",
                needs("flour", 200, "g"), needs("milk", 300, "ml"), needs("eggs", 1, "pcs"),
                needs("sugar", 25, "g"), needs("butter", 20, "g"));
        List<PantryItem> pantry = Arrays.asList(
                have("flour", 1, "kg"), have("milk", 1, "l"), have("eggs", 6, "pcs"), have("sugar", 500, "g"));
        assertTrue(RecipeMatcher.getStrictMatches(pantry, Arrays.asList(pancakes)).isEmpty());
    }

    @Test
    public void table_pantryTomatoesRecipeTomato_matches() {
        Recipe salad = recipe("salad", needs("tomato", 2, "pcs"));
        List<PantryItem> pantry = Arrays.asList(have("tomatoes", 3, "pcs"));
        assertEquals(1, RecipeMatcher.getStrictMatches(pantry, Arrays.asList(salad)).size());
    }

    @Test
    public void table_pantry1kgFlourRecipe500g_matches() {
        Recipe bread = recipe("bread", needs("flour", 500, "g"));
        List<PantryItem> pantry = Arrays.asList(have("flour", 1, "kg"));
        assertEquals(1, RecipeMatcher.getStrictMatches(pantry, Arrays.asList(bread)).size());
    }

    @Test
    public void table_pantry200gFlourRecipe500g_notSuggested() {
        Recipe bread = recipe("bread", needs("flour", 500, "g"));
        List<PantryItem> pantry = Arrays.asList(have("flour", 200, "g"));
        assertTrue(RecipeMatcher.getStrictMatches(pantry, Arrays.asList(bread)).isEmpty());
    }

    @Test
    public void table_pantry2CupsMilkRecipe250ml_matches() {
        Recipe shake = recipe("shake", needs("milk", 250, "ml"));
        List<PantryItem> pantry = Arrays.asList(have("milk", 2, "cup"));
        assertEquals(1, RecipeMatcher.getStrictMatches(pantry, Arrays.asList(shake)).size());
    }

    @Test
    public void table_pantry500gOnionRecipe2PiecesOnion_notSuggestedDifferentFamilies() {
        Recipe soup = recipe("soup", needs("onion", 2, "pcs"));
        List<PantryItem> pantry = Arrays.asList(have("onion", 500, "g"));
        assertTrue(RecipeMatcher.getStrictMatches(pantry, Arrays.asList(soup)).isEmpty());
    }

    @Test
    public void table_emptyPantry_noSuggestions() {
        Recipe toast = recipe("toast", needs("bread", 1, "pcs"));
        List<PantryItem> pantry = new ArrayList<>();
        assertTrue(RecipeMatcher.getStrictMatches(pantry, Arrays.asList(toast)).isEmpty());
    }

    @Test
    public void table_sameIngredientAddedTwice100gPlus150gRecipe200g_matches() {
        Recipe bread = recipe("bread", needs("flour", 200, "g"));
        List<PantryItem> pantry = Arrays.asList(have("flour", 100, "g"), have("flour", 150, "g"));
        assertEquals(1, RecipeMatcher.getStrictMatches(pantry, Arrays.asList(bread)).size());
    }

    // more strict matching cases

    @Test
    public void milkDoesNotCountAsCoconutMilk() {
        Recipe curry = recipe("curry", needs("coconut milk", 400, "ml"));
        List<PantryItem> pantry = Arrays.asList(have("milk", 2, "l"));
        assertTrue(RecipeMatcher.getStrictMatches(pantry, Arrays.asList(curry)).isEmpty());
    }




    @Test
    public void onlyTheMakeableRecipesAreReturnedInTheirOriginalOrder() {
        Recipe toast = recipe("toast", needs("bread", 2, "pcs"));
        Recipe omelette = recipe("omelette", needs("eggs", 3, "pcs"), needs("cheese", 30, "g"));
        Recipe eggs = recipe("boiled eggs", needs("eggs", 2, "pcs"));
        List<PantryItem> pantry = Arrays.asList(have("Bread", 4, "pcs"), have("Eggs", 6, "pcs"));
        assertEquals(Arrays.asList("toast", "boiled eggs"),
                names(RecipeMatcher.getStrictMatches(pantry, Arrays.asList(toast, omelette, eggs))));
    }



    // turns SeedData into Recipe objects with their ingredients
    private static List<Recipe> seededRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        for (SeedData.SeedRecipe seed : SeedData.getRecipes()) {
            Recipe recipe = new Recipe(seed.getName(), seed.getSteps());
            recipe.setIngredients(seed.getIngredients());
            recipes.add(recipe);
        }
        return recipes;
    }

    @Test
    public void seededRecipes_pantryForJamToastMatchesOnlyJamToast() {
        List<PantryItem> pantry = Arrays.asList(
                have("Bread", 2, "pcs"), have("Butter", 10, "g"), have("Jam", 20, "g"));
        assertEquals(Arrays.asList("Toast with butter and jam"),
                names(RecipeMatcher.getStrictMatches(pantry, seededRecipes())));
    }

    @Test
    public void seededRecipes_jamToastMissingTheJamMatchesNothing() {
        List<PantryItem> pantry = Arrays.asList(have("Bread", 2, "pcs"), have("Butter", 10, "g"));
        assertTrue(RecipeMatcher.getStrictMatches(pantry, seededRecipes()).isEmpty());
    }

    @Test
    public void seededRecipes_everydayPantryMatchesExactlyTheThreeItCanMake() {
        List<PantryItem> pantry = Arrays.asList(
                have("Bread", 4, "pcs"), have("Butter", 250, "g"), have("Cheese", 200, "g"),
                have("Eggs", 6, "pcs"), have("Milk", 1, "l"), have("Onions", 2, "pcs"));
        assertEquals(Arrays.asList("Scrambled eggs", "Cheese toastie", "Cheese and onion omelette"),
                names(RecipeMatcher.getStrictMatches(pantry, seededRecipes())));
    }
}
