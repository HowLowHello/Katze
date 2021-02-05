package com.howlowhello.katze.items;

import com.howlowhello.katze.Katze;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BloodyCrest extends Item {

    public BloodyCrest() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .maxDamage(111)
                .setNoRepair()
        );
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);
    }
}
