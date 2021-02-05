package com.howlowhello.katze.items.foods;

import com.howlowhello.katze.Katze;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class CandyCane extends Item {

    public CandyCane(){
        super(new Item.Properties()
                .group(Katze.TAB)
                .food(new Food.Builder()
                        .hunger(1)
                        .saturation(0.0f)
                        .fastToEat()
                        .build())
        );
    }
}
