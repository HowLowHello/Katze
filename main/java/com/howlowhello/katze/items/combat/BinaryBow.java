package com.howlowhello.katze.items.combat;

import com.howlowhello.katze.Katze;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BinaryBow extends CustomBow{
    protected static final TranslationTextComponent INFORMATION_1 = new TranslationTextComponent("event.katze_adventure.binary_bow.information_1");
    protected static final TranslationTextComponent INFORMATION_2 = new TranslationTextComponent("event.katze_adventure.binary_bow.information_2");
    protected static final TranslationTextComponent INFORMATION_3 = new TranslationTextComponent("event.katze_adventure.binary_bow.information_3");
    protected static final TranslationTextComponent INFORMATION_4 = new TranslationTextComponent("event.katze_adventure.binary_bow.information_4");
    protected static final TranslationTextComponent INFORMATION_5 = new TranslationTextComponent("event.katze_adventure.binary_bow.information_5");

    public BinaryBow() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .maxDamage(1539)
        );
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(INFORMATION_1);
        tooltip.add(INFORMATION_2);
        tooltip.add(INFORMATION_3);
        tooltip.add(INFORMATION_4);
        tooltip.add(INFORMATION_5);
        tooltip.add(new StringTextComponent(" "));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack item = new ItemStack(this);
        item.addEnchantment(Enchantments.POWER, 4);
        item.addEnchantment(Enchantments.UNBREAKING, 3);
        item.addEnchantment(Enchantments.FLAME, 2);
        item.addEnchantment(Enchantments.INFINITY, 1);
        return item;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack item = new ItemStack(this);
            item.addEnchantment(Enchantments.POWER, 4);
            item.addEnchantment(Enchantments.UNBREAKING, 3);
            item.addEnchantment(Enchantments.FLAME, 2);
            item.addEnchantment(Enchantments.INFINITY, 1);
            items.add(item);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {

        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = playerentity.findAmmo(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                // draw speed: 1.3X
                float f = CustomBow.getArrowVelocity(i,1.3f);

                if (!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                    if (!worldIn.isRemote) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractarrowentity1 = arrowitem.createArrow(worldIn, itemstack, playerentity);
                        abstractarrowentity1 = customArrow(abstractarrowentity1);
                        AbstractArrowEntity abstractarrowentity2 = arrowitem.createArrow(worldIn, itemstack, playerentity);
                        abstractarrowentity2 = customArrow(abstractarrowentity2);
                        abstractarrowentity1.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw + 5.0F, 0.0F, f * 3.0F, 1.0F);
                        abstractarrowentity2.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw - 5.0F, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrowentity1.setIsCritical(true);
                            abstractarrowentity2.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            abstractarrowentity1.setDamage(abstractarrowentity1.getDamage() + (double)j * 0.5D + 0.5D);
                            abstractarrowentity2.setDamage(abstractarrowentity2.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (k > 0) {
                            abstractarrowentity1.setKnockbackStrength(k);
                            abstractarrowentity2.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            abstractarrowentity1.setFire(200);
                            abstractarrowentity2.setFire(200);
                        }

                        stack.damageItem(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                        });
                        if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity1.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                            abstractarrowentity2.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        worldIn.addEntity(abstractarrowentity1);
                        worldIn.addEntity(abstractarrowentity2);
                    }

                    worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.deleteStack(itemstack);
                        }
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }

    }
}
