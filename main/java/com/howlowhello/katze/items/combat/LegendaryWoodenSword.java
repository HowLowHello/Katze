package com.howlowhello.katze.items.combat;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class LegendaryWoodenSword extends SwordItem {
    protected static final TranslationTextComponent INFORMATION_1 = new TranslationTextComponent("event.katze_adventure.legendary_wooden_sword.information_1");
    protected static final TranslationTextComponent INFORMATION_2 = new TranslationTextComponent("event.katze_adventure.legendary_wooden_sword.information_2");
    protected static final TranslationTextComponent INFORMATION_3 = new TranslationTextComponent("event.katze_adventure.legendary_wooden_sword.information_3");
    protected static final TranslationTextComponent INFORMATION_4 = new TranslationTextComponent("event.katze_adventure.legendary_wooden_sword.information_4");

    public LegendaryWoodenSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(INFORMATION_1);
        tooltip.add(INFORMATION_2);
        tooltip.add(INFORMATION_3);
        tooltip.add(INFORMATION_4);
        tooltip.add(new StringTextComponent(" "));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack item = new ItemStack(this);
        item.addEnchantment(Enchantments.SHARPNESS, 10);
        item.addEnchantment(Enchantments.SMITE, 1);
        item.addEnchantment(Enchantments.UNBREAKING, 100);
        item.addEnchantment(Enchantments.KNOCKBACK, 1);
        item.addEnchantment(Enchantments.LOOTING, 3);
        return item;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack item = new ItemStack(this);
            item.addEnchantment(Enchantments.SHARPNESS, 10);
            item.addEnchantment(Enchantments.SMITE, 1);
            item.addEnchantment(Enchantments.UNBREAKING, 100);
            item.addEnchantment(Enchantments.KNOCKBACK, 1);
            item.addEnchantment(Enchantments.LOOTING, 3);
            items.add(item);
        }
    }
}
