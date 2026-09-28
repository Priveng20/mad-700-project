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
}
