package com.howlowhello.katze.items.foods;

import com.howlowhello.katze.Katze;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class MetallizationBerries extends Item {

    public MetallizationBerries(){
        super(new Item.Properties()
                .group(Katze.TAB)
                .food(new Food.Builder()
                        .hunger(2)
                        .saturation(1.2f)
                        .effect(new EffectInstance(Effects.RESISTANCE, 5400, 2), 1)
                        .effect(new EffectInstance(Effects.ABSORPTION, 5400, 2), 1)
                        .effect(new EffectInstance(Effects.FIRE_RESISTANCE, 5400, 0), 1)
                        .effect(new EffectInstance(Effects.BLINDNESS, 3000, 0), 1)
                        .effect(new EffectInstance(Effects.SLOWNESS, 4200, 0), 1)
                        .setAlwaysEdible()
                        .build())
        );
    }
}
