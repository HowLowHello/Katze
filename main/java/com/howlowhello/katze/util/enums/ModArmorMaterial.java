package com.howlowhello.katze.util.enums;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.init.ModItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ModArmorMaterial implements IArmorMaterial {

    RUBY(Katze.MOD_ID + ":ruby", 25, new int[] { 2, 5, 6, 2 }, 18,
    SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0, () -> { return Ingredient.fromItems(ModItems.RUBY.get()); },0),

    SEPITH(Katze.MOD_ID + ":sepith", 32, new int[] { 2, 4, 5, 2 }, 20,
    SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F, () -> { return Ingredient.fromItems(ModItems.SEPITH.get()); },0),

    RED_SEPITH(Katze.MOD_ID + ":red_sepith", 32, new int[] { 2, 4, 5, 2 }, 20,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F, () -> { return Ingredient.fromItems(ModItems.SEPITH.get()); },0),

    BLUE_SEPITH(Katze.MOD_ID + ":blue_sepith", 32, new int[] { 2, 4, 5, 2 }, 20,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F, () -> { return Ingredient.fromItems(ModItems.SEPITH.get()); },0),

    YELLOW_SEPITH(Katze.MOD_ID + ":yellow_sepith", 32, new int[] { 2, 4, 5, 2 }, 20,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F, () -> { return Ingredient.fromItems(ModItems.SEPITH.get()); },0),

    GREEN_SEPITH(Katze.MOD_ID + ":green_sepith", 32, new int[] { 2, 4, 5, 2 }, 20,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F, () -> { return Ingredient.fromItems(ModItems.SEPITH.get()); },0),

    DARK_SEPITH(Katze.MOD_ID + ":dark_sepith", 32, new int[] { 2, 4, 5, 2 }, 20,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F, () -> { return Ingredient.fromItems(ModItems.SEPITH.get()); },0),

    ZEMURIAN(Katze.MOD_ID + ":zemurian", 42, new int[] { 3, 6, 8, 3 }, 22,
    SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F, () -> { return Ingredient.fromItems(ModItems.ZEMURIAN_INGOT.get()); },0.05F),

    RED_ZEMURIAN(Katze.MOD_ID + ":red_zemurian", 64, new int[] { 3, 6, 8, 3 }, 22,
    SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F, () -> { return Ingredient.fromItems(ModItems.ZEMURIAN_INGOT.get()); },0.1F),

    BLUE_ZEMURIAN(Katze.MOD_ID + ":blue_zemurian", 64, new int[] { 3, 6, 8, 3 }, 22,
    SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 5.0F, () -> { return Ingredient.fromItems(ModItems.ZEMURIAN_INGOT.get()); },0.05F),

    YELLOW_ZEMURIAN(Katze.MOD_ID + ":yellow_zemurian", 64, new int[] { 3, 6, 8, 3 }, 22,
    SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F, () -> { return Ingredient.fromItems(ModItems.ZEMURIAN_INGOT.get()); },0.1F),

    GREEN_ZEMURIAN(Katze.MOD_ID + ":green_zemurian", 64, new int[] { 3, 6, 8, 3 }, 22,
    SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 5.0F, () -> { return Ingredient.fromItems(ModItems.ZEMURIAN_INGOT.get()); },0.05F),

    DARK_ZEMURIAN(Katze.MOD_ID + ":dark_zemurian", 64, new int[] { 3, 6, 8, 3 }, 22,
    SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 5.0F, () -> { return Ingredient.fromItems(ModItems.ZEMURIAN_INGOT.get()); },0.1F),

    RED_NETHERITE(Katze.MOD_ID + ":red_netherite", 64, new int[] { 3, 6, 8, 3 }, 15,
    SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0F, () -> { return Ingredient.fromItems(Items.NETHERITE_INGOT); },0.15F),

    BLUE_NETHERITE(Katze.MOD_ID + ":blue_netherite", 64, new int[] { 3, 6, 8, 3 }, 15,
    SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 4.0F, () -> { return Ingredient.fromItems(Items.NETHERITE_INGOT); },0.1F),

    YELLOW_NETHERITE(Katze.MOD_ID + ":yellow_netherite", 64, new int[] { 3, 6, 8, 3 }, 15,
    SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0F, () -> { return Ingredient.fromItems(Items.NETHERITE_INGOT); },0.15F),

    GREEN_NETHERITE(Katze.MOD_ID + ":green_netherite", 64, new int[] { 3, 6, 8, 3 }, 15,
    SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 4.0F, () -> { return Ingredient.fromItems(Items.NETHERITE_INGOT); },0.1F),

    DARK_NETHERITE(Katze.MOD_ID + ":dark_netherite", 64, new int[] { 3, 6, 8, 3 }, 15,
    SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 4.0F, () -> { return Ingredient.fromItems(Items.NETHERITE_INGOT); },0.15F);


    private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };//倒序后对应四个部位的基础耐久，总耐久=基础耐久*修正值
    private final String name;
    private final int maxDamageFactor; //耐久修正数 Durability, Iron=15, Diamond=33, Gold=7, Leather=5
    private final int[] damageReductionAmountArray; //护甲值 Armor Bar Protection, 1 = 1/2 armor bar
    private final int enchantability;
    private final SoundEvent soundEvent;//佩戴时的音效
    private final float toughness; //盔甲韧性 Increases Protection, 0.0F=Iron/Gold/Leather, 2.0F=Diamond, 3.0F=Netherite
    private final Supplier<Ingredient> repairMaterial;
    private final float knockbackResistance; //击退抗性 1.0F=No Knockback, 0.0F=Disabled

    ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
                     SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial, float knockbackResistance) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = repairMaterial;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
