package project.app.smartpantry.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;







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
}
