package com.howlowhello.katze.items.combat;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RefinedKatana extends SwordItem {
    protected static final TranslationTextComponent INFORMATION_1 = new TranslationTextComponent("event.katze_adventure.refined_katana.information_1");
    protected static final TranslationTextComponent INFORMATION_2 = new TranslationTextComponent("event.katze_adventure.refined_katana.information_2");
    protected static final TranslationTextComponent INFORMATION_3 = new TranslationTextComponent("event.katze_adventure.refined_katana.information_3");
    protected static final TranslationTextComponent INFORMATION_4 = new TranslationTextComponent("event.katze_adventure.refined_katana.information_4");

    public RefinedKatana(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
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
        item.addEnchantment(Enchantments.SHARPNESS, 8);
        item.addEnchantment(Enchantments.UNBREAKING, 8);
        item.addEnchantment(Enchantments.KNOCKBACK, 2);
        item.addEnchantment(Enchantments.FIRE_ASPECT, 2);
        item.addEnchantment(Enchantments.LOOTING, 3);
        return item;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack item = new ItemStack(this);
            item.addEnchantment(Enchantments.SHARPNESS, 8);
            item.addEnchantment(Enchantments.UNBREAKING, 8);
            item.addEnchantment(Enchantments.KNOCKBACK, 2);
            item.addEnchantment(Enchantments.FIRE_ASPECT, 2);
            item.addEnchantment(Enchantments.LOOTING, 3);
            items.add(item);
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {

        EnderChestInventory enderChest = player.getInventoryEnderChest();

        if (enderChest != null)
        {
            if (!world.isRemote)
            {
                player.openContainer(new SimpleNamedContainerProvider((windowID, playerInventory, inventoryIn) -> {
                    return ChestContainer.createGeneric9X3(windowID, playerInventory, enderChest);
                }, new TranslationTextComponent("item.gobber2.gobber2_ring_enderchest.title")));
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, player.getHeldItem(hand));
    }
}
