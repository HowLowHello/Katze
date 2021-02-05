package com.howlowhello.katze.items.combat;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.entities.projectiles.ExplosiveArrowEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class KaleidoBow extends BowItem {

    public KaleidoBow() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .maxDamage(888)
        );
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack item = new ItemStack(this);
        item.addEnchantment(Enchantments.POWER, 4);
        item.addEnchantment(Enchantments.UNBREAKING, 3);
        item.addEnchantment(Enchantments.INFINITY, 1);
        return item;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack item = new ItemStack(this);
            item.addEnchantment(Enchantments.POWER, 4);
            item.addEnchantment(Enchantments.UNBREAKING, 3);
            item.addEnchantment(Enchantments.INFINITY, 1);
            items.add(item);
        }
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = playerentity.findAmmo(stack);

            int timeDrawn = this.getUseDuration(stack) - timeLeft;
            timeDrawn = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, timeDrawn, !itemstack.isEmpty() || flag);
            if (timeDrawn < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(timeDrawn);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                    if (!worldIn.isRemote) {

                        AbstractArrowEntity abstractarrowentity = null;
                        int type = new Random().nextInt(7);
                        if (type==6){
                            abstractarrowentity = new ExplosiveArrowEntity(playerentity.getEntityWorld(), playerentity);
                        }
                        else {
                            ArrowEntity arrow = new ArrowEntity(playerentity.getEntityWorld(), playerentity);
                            switch (type){
                                case 0:{
                                    arrow.addEffect(new EffectInstance(Effects.SLOWNESS, 600));
                                    abstractarrowentity = arrow;
                                    break;
                                }
                                case 1:{
                                    arrow.addEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1));
                                    abstractarrowentity = arrow;
                                    break;
                                }
                                case 2:{
                                    arrow.addEffect(new EffectInstance(Effects.INSTANT_HEALTH,1 ,1));
                                    abstractarrowentity = arrow;
                                    break;
                                }
                                case 3:{
                                    arrow.addEffect(new EffectInstance(Effects.WEAKNESS, 600));
                                    abstractarrowentity = arrow;
                                    break;
                                }
                                case 4:{
                                    arrow.addEffect(new EffectInstance(Effects.POISON, 600));
                                    abstractarrowentity = arrow;
                                    break;
                                }
                                case 5:{
                                    arrow.setFire(200);
                                    abstractarrowentity = arrow;
                                    break;
                                }
                            }

                        }

                        if (abstractarrowentity != null){

                            abstractarrowentity = customArrow(abstractarrowentity);
                            abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
                            if (f == 1.0F) {
                                abstractarrowentity.setIsCritical(true);
                            }

                            int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                            if (j > 0) {
                                abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)j * 0.5D + 0.5D);
                            }

                            int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                            if (k > 0) {
                                abstractarrowentity.setKnockbackStrength(k);
                            }

                            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                                abstractarrowentity.setFire(100);
                            }

                            stack.damageItem(1, playerentity, (p_220009_1_) -> {
                                p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                            });

                            // for this is a kaleido bow, set all arrow shot unable to be picked up
                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;

                            worldIn.addEntity(abstractarrowentity);
                        }
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


    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }


    /**
     * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
     */
    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return (ammoStack) -> {
            return ammoStack.getItem().isIn(ItemTags.ARROWS);
        };
    }

}
