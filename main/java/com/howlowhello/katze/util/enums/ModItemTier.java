package com.howlowhello.katze.util.enums;

import com.howlowhello.katze.init.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {

    /**最后一个参数为修理材料 */
    RUBY(3, 800, 7.0F, 3.0F, 12, () -> {
        return Ingredient.fromItems(ModItems.RUBY.get());
    }),

    SEPITH(2, 427, 6.0F, 3.0F, 18, () -> {
        return Ingredient.fromItems(ModItems.SEPITH.get());
    }),

    ZEMURIAN(4, 2310, 9.0F, 5.0F, 20, () -> {
        return Ingredient.fromItems(ModItems.ZEMURIAN_INGOT.get());
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }
}
