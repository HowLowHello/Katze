package com.howlowhello.katze.items.foods;

import com.howlowhello.katze.Katze;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RestorativeBerries extends Item {

    public RestorativeBerries(){
        super(new Item.Properties()
                .group(Katze.TAB)
                .food(new Food.Builder()
                        .hunger(2)
                        .saturation(4.8f)
                        .effect(new EffectInstance(Effects.REGENERATION, 6000, 1), 1)
                        .effect(new EffectInstance(Effects.SPEED, 9600, 1), 1)
                        .setAlwaysEdible()
                        .build())
        );
    }
}
