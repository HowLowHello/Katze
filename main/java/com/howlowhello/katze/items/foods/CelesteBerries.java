package com.howlowhello.katze.items.foods;

import com.howlowhello.katze.Katze;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class CelesteBerries extends Item {

    public CelesteBerries() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .food(new Food.Builder()
                        .hunger(2)
                        .saturation(1.2f)
                        .effect(new EffectInstance(Effects.WATER_BREATHING, 24000, 0), 1)
                        .effect(new EffectInstance(Effects.DOLPHINS_GRACE, 1200, 0), 1)
                        .setAlwaysEdible()
                        .build())
        );
    }
}
