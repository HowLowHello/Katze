package com.howlowhello.katze.items.combat;

import com.howlowhello.katze.init.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

import javax.annotation.Nullable;

public abstract class KatzeShield extends ShieldItem implements IForgeItem {

    public KatzeShield(Properties builder) {
        super(builder);
    }

    // subclasses should define their spells
    protected abstract boolean castSpell(LivingEntity caster);

    public boolean castSpell(PlayerEntity player){
        return this.castSpell((LivingEntity) player);
    }

    // subclasses should set how much charging points should the spell consume
    public abstract int getSpellExpense();

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return true;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == ModItems.SEPITH.get() || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote){
            CompoundNBT tag = stack.getOrCreateTag();
            if (!tag.contains("katze_shield_damage")){
                tag.putInt("katze_shield_damage", stack.getDamage());
            }
            else {
                // detect if the shield is taking damage
                if (stack.getDamage() > tag.getInt("katze_shield_damage")){

                    // if is charged already, cast the spell immediately
                    if (tag.contains("katze_shield_charge")){
                        if (tag.getInt("katze_shield_charge") > this.getSpellExpense()){
                            if (entityIn instanceof LivingEntity){

                                LivingEntity entityLiving = (LivingEntity) entityIn;

                                if (this.castSpell(entityLiving)){
                                    // prevent the durability loss
                                    stack.setDamage(tag.getInt("katze_shield_damage"));

                                    tag.putInt("katze_shield_charge", tag.getInt("katze_shield_charge") - this.getSpellExpense());
                                    return;
                                }
                            }
                        }
                    }
                    // update the charger tag
                    int i = 1 + EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
                    // Multiplier that compensates potential charge loss due to unbreaking enchantment
                    if (!tag.contains("katze_shield_charge")){
                        tag.putInt("katze_shield_charge", i * (stack.getDamage() - tag.getInt("katze_shield_damage")));
                    }

                    else {
                        tag.putInt("katze_shield_charge", i * (tag.getInt("katze_shield_charge") + (stack.getDamage() - tag.getInt("katze_shield_damage"))));
                    }
                    // update the damage tag
                    tag.putInt("katze_shield_damage", stack.getDamage());
                }
            }
        }
    }
}
