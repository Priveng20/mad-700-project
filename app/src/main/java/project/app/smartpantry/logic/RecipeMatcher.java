package project.app.smartpantry.logic;

import java.util.Locale;





// recipe matching rule and name and unit clean up
public class RecipeMatcher {

    // turns an ingredient name into one standard form so that
    public static String normaliseName(String name) {
        String cleaned = name.trim().toLowerCase(Locale.ROOT);
        cleaned = cleaned.replaceAll("\\s+", " ");
        return toSingular(cleaned);
    }




    // plural rules applied to the end of the name
    private static String toSingular(String name) {
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
}
