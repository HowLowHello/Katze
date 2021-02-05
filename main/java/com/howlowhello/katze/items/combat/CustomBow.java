package com.howlowhello.katze.items.combat;

import net.minecraft.item.BowItem;

public class CustomBow extends BowItem {

    public CustomBow(Properties builder) {
        super(builder);
    }

    // Static Method to Replace the Vanilla one in BowItem
    public static float getArrowVelocity(int charge, float multiplier) {
        // multiply the number to increase / decrease draw speed
        float f = multiplier * charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }
}
