package com.howlowhello.katze.items.foods;

import com.howlowhello.katze.Katze;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class EclipseBerries extends Item{

    public EclipseBerries(){
        super(new Item.Properties()
                .group(Katze.TAB)
                .food(new Food.Builder()
                        .hunger(2)
                        .saturation(1.2f)
                        .effect(new EffectInstance(Effects.FIRE_RESISTANCE, 24000, 0), 1)
                        .setAlwaysEdible()
                        .build())
        );
    }
}
