package com.howlowhello.katze.items.foods;

import com.howlowhello.katze.Katze;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class SearingBerries extends Item {

    public SearingBerries() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .food(new Food.Builder()
                        .hunger(2)
                        .saturation(1.2f)
                        .effect(new EffectInstance(Effects.STRENGTH, 5400, 2), 1)
                        .effect(new EffectInstance(Effects.HASTE, 5400, 0), 1)
                        .effect(new EffectInstance(Effects.NIGHT_VISION, 5400, 0), 1)
                        .effect(new EffectInstance(Effects.HEALTH_BOOST, 5400, 0), 1)
                        .effect(new EffectInstance(Effects.NAUSEA, 1200, 0), 1)
                        .effect(new EffectInstance(Effects.POISON, 3000, 1), 1)
                        .effect(new EffectInstance(Effects.HUNGER, 4200, 0), 1)
                        .setAlwaysEdible()
                        .build())
        );
    }
}
