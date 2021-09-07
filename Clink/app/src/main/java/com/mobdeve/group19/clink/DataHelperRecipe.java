package com.mobdeve.group19.clink;

import java.util.ArrayList;

public class DataHelperRecipe {
    public static ArrayList<Recipe_test> initializeData() {
        ArrayList<Recipe_test> data = new ArrayList<Recipe_test>();

        data.add(new Recipe_test(
                R.drawable.margarita, "Margarita", "5 minutes",
                "1 ½ ounce silver tequila\n1 ounce orange liqueur\n¾ ounce freshly-squeezed lime juice\n2 lime wedge\nCoarse salt",
                "1. Salt the rim.\n2. Run a lime wedge around the top rim of the glass.\n3. Dip the top rim of the glass until it is completely with salt.\n4. Mix the tequila, orange liqueur, lime juice and ice using a cocktail shaker.\n5. Shake for 10 seconds.\n6. Add syrup to taste."));
        data.add(new Recipe_test(
                R.drawable.grasshopper, "Grasshopper", "10 minutes",
                "¾ ounce creme de menthe\n¾ ounce white creme de cacao\n¼ fluid ounce heavy cream\n1 cup ice",
                "1. Combine creme de menthe, creme de cacao, cream and ice in a cocktail shaker.\n2. Cover and shake.\n3. Chill first then strain before serving."));
        return data;
    }
}



//1 ½ ounce silver tequila
//        1 ounce orange liqueur
//        ¾ ounce freshly-squeezed lime juice
//        2 lime wedge
//        Coarse salt
//        Ice
//        Syrup (optional)
