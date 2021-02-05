package com.howlowhello.katze.items.foods;

import com.howlowhello.katze.Katze;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ForteBerries extends Item {

    public ForteBerries() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .food(new Food.Builder()
                        .hunger(2)
                        .saturation(1.2f)
                        .effect(new EffectInstance(Effects.STRENGTH, 24000, 1), 1)
                        .setAlwaysEdible()
                        .build())
        );
    }
}
