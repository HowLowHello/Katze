package com.howlowhello.katze.items.combat;

import com.howlowhello.katze.Katze;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class AnimisticBow extends BowItem {
    protected static final TranslationTextComponent INFORMATION_1 = new TranslationTextComponent("event.katze_adventure.animistic_bow.information_1");
    protected static final TranslationTextComponent INFORMATION_2 = new TranslationTextComponent("event.katze_adventure.animistic_bow.information_2");
    protected static final TranslationTextComponent INFORMATION_3 = new TranslationTextComponent("event.katze_adventure.animistic_bow.information_3");
    protected static final TranslationTextComponent INFORMATION_4 = new TranslationTextComponent("event.katze_adventure.animistic_bow.information_4");
    protected static final TranslationTextComponent INFORMATION_5 = new TranslationTextComponent("event.katze_adventure.animistic_bow.information_5");

    public AnimisticBow() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .maxDamage(4677)
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
        item.addEnchantment(Enchantments.UNBREAKING, 4);
        item.addEnchantment(Enchantments.PUNCH, 1);
        item.addEnchantment(Enchantments.INFINITY, 1);
        return item;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack item = new ItemStack(this);
            item.addEnchantment(Enchantments.POWER, 4);
            item.addEnchantment(Enchantments.UNBREAKING, 4);
            item.addEnchantment(Enchantments.PUNCH, 1);
            item.addEnchantment(Enchantments.INFINITY, 1);
            items.add(item);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);

        if (new Random().nextInt(15)==0){
            entityLiving.heal(2);

            if (worldIn.isRemote){
                for(int i = 0; i < 15; ++i) {
                    double d2 = random.nextGaussian() * 0.02D;
                    double d3 = random.nextGaussian() * 0.02D;
                    double d4 = random.nextGaussian() * 0.02D;
                    double d5 = 0.5D - 3.0D;
                    double d6 = (double)entityLiving.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                    double d7 = (double)entityLiving.getPosition().getY() + 1.0D + random.nextDouble() * 1.0D;
                    double d8 = (double)entityLiving.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                    worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                }
            }
        }
    }
}
