package com.howlowhello.katze.items.combat;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.init.ModItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraftforge.common.extensions.IForgeItem;

import javax.annotation.Nullable;

public class BurstShield extends ShieldItem implements IForgeItem {

    public BurstShield() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .maxDamage(899)
        );
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return true;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == ModItems.SEPITH.get() || super.getIsRepairable(toRepair, repair);
    }
}
