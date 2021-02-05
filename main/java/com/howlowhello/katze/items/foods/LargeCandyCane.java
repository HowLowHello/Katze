package com.howlowhello.katze.items.foods;

import com.howlowhello.katze.Katze;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class LargeCandyCane extends Item{

    public LargeCandyCane(){
        super(new Item.Properties()
                .group(Katze.TAB)
                .food(new Food.Builder()
                        .hunger(4)
                        .saturation(9.6f)
                        .build())
        );
    }
}
