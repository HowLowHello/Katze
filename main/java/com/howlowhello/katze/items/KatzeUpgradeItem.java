package com.howlowhello.katze.items;

import com.howlowhello.katze.Katze;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class KatzeUpgradeItem extends Item {
    public int index = 0;

    public KatzeUpgradeItem(int index){
        super(new Item.Properties().group(Katze.TAB).isImmuneToFire().rarity(Rarity.RARE));
        this.index = index;
    }
}
