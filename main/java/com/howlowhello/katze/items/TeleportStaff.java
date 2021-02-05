package com.howlowhello.katze.items;

import com.howlowhello.katze.Katze;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TeleportStaff extends Item {
    protected static final TranslationTextComponent INFORMATION_1 = new TranslationTextComponent("event.katze_adventure.teleport_staff.information_1");

    public TeleportStaff() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .maxDamage(88)
        );
    }

    // adds a tool tip when you hover over the item in your inventory and press shift
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(INFORMATION_1);
        tooltip.add(new StringTextComponent(" "));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        // only allow the player to use it every 3 seconds
        playerIn.getCooldownTracker().setCooldown(this, 60);

        // get where the player is looking and move them there
        Vector3d lookPos = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.NONE).getHitVec();
        playerIn.setPosition(lookPos.x, lookPos.y, lookPos.z);

        // allow the teleport to cancel fall damage
        playerIn.fallDistance = 0.0F;

        // reduce durability
        ItemStack stack = playerIn.getHeldItem(handIn);
        stack.setDamage(stack.getDamage() + 1);

        playerIn.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

        // break if durability gets to 0
        if (stack.getDamage() == 88) {
            stack.shrink(1);
            playerIn.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    // the same as Item.rayTrace(World, PlayerEntity, FluidMode) but with a longer range
    protected static BlockRayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
        double range = 20; // player.getAttribute(PlayerEntity.REACH_DISTANCE).getValue();;

        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vector3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vector3d vec3d1 = vec3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }
}
