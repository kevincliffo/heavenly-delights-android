package com.kevin.heavenlydelights.Model;

import com.kevin.heavenlydelights.R;

/**
 * Created by Jashan on 18/07/2015.
 */
public class Pages {
    private static final int cakePrice = 700;
    private static final int creamPrice = 350;
    private static final OrderObject [] flavours = {
            new OrderObject(R.drawable.red_velvet_cake, "Red Velvet", cakePrice),
            new OrderObject(R.drawable.chocolate_truffle, "Chocolate Truffle", cakePrice),
            new OrderObject(R.drawable.cheescake_base, "Cheescake", cakePrice),
            new OrderObject(R.drawable.vanilla_cake, "Vanilla Cake", cakePrice),
            new OrderObject(R.drawable.black_forest, "Black Forest Cake", cakePrice),
            new OrderObject(R.drawable.rainbow_themed_cake, "Rainbow Themed Cake", cakePrice),
            new OrderObject(R.drawable.rich_chocolate, "Rich Chocolate Cake", cakePrice),
            new OrderObject(R.drawable.strawberry_cake, "Strawberry Cake", cakePrice),
            new OrderObject(R.drawable.white_forest, "White Forest Cake", cakePrice)
    };
    public static final OrderPage cakePage = new OrderPage("Step 1: Choose a Base Flavour"
            , flavours);
    private static final OrderObject [] creams = {
            new OrderObject(R.drawable.choco_ganache, "Rich Chocolate Ganache",
                    creamPrice),
            new OrderObject(R.drawable.cinnamon_cream, "Cinnamon Cream",
                    creamPrice),
            new OrderObject(R.drawable.choco_cream, "Chocolate Buttercream",
                    creamPrice),
            new OrderObject(R.drawable.oreo_cream, "Oreo Buttercream",
                    creamPrice),
            new OrderObject(R.drawable.lemon_cream, "Rich Lemon Cream",
                    creamPrice),
            new OrderObject(R.drawable.caramel_cream, "Salted Caramel Glaze",
                    creamPrice),
            new OrderObject(R.drawable.honey_cream, "Rich Honey",
                    creamPrice),
            new OrderObject(R.drawable.blueberry_topping, "Blueberry",
                    creamPrice),
            new OrderObject(R.drawable.vanilla_cream, "Vanilla Buttercream",
                    creamPrice)
    };
    public static final OrderPage creamPage = new OrderPage(
            "Step Two: Select a topping", creams
    );
    public static final int [] themePics = {
            R.drawable.simran_birthday_cake,
            R.drawable.flower_cake,
            R.drawable.messi_theme_cake,
            R.drawable.theme_christmas,
            R.drawable.vanilla_cake
    };
    private static final OrderObject [] weightObjects = {
            new OrderObject(R.drawable.button_bg, "0.5 Kg Cake - Ksh. 900", 900),
            new OrderObject(R.drawable.button_bg, "1 Kg Cake - Ksh. 1600", 1600),
            new OrderObject(R.drawable.button_bg, "1.5 Kg Cake - Ksh. 2500", 2500),
            new OrderObject(R.drawable.button_bg, "2 Kg Cake - Ksh. 3000", 3000),
            new OrderObject(R.drawable.button_bg, "3 Kg Cake - Ksh. 4200", 4200)
    };

    public static final OrderPage weights = new OrderPage("Select The Weight of Your Cake",
            weightObjects);
    private static final OrderObject [] sugar = {
            new OrderObject(R.drawable.button_bg, "Yes (add Ksh. 100)", 100),
            new OrderObject(R.drawable.button_bg, "No", 0)
    };
    public static final OrderPage sugarPage =
            new OrderPage("Would you like the cake to be Sugarfree?", sugar);
}
