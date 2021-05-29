package com.howlowhello.katze.items.combat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EnderSword extends SwordItem {

    public EnderSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getTag() != null){
            if (stack.getTag().contains("counts") && stack.getTag().getInt("counts") >= 8){
                stack.getTag().putInt("counts", stack.getTag().getInt("counts") - 8);
                // only allow the player to teleport every 1 second
                playerIn.getCooldownTracker().setCooldown(this, 20);
                // get where the player is looking and move them there
                Vector3d lookPos = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.NONE).getHitVec();
                playerIn.setPosition(lookPos.x, lookPos.y, lookPos.z);
                // allow the teleport to cancel fall damage
                playerIn.fallDistance = 0.0F;

                playerIn.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

                return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
            }
        }


        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    // the same as Item.rayTrace(World, PlayerEntity, FluidMode) but with a longer range
    protected static BlockRayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
        double range = 20; // MAX distance

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

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack item = new ItemStack(this);
            item.getOrCreateTag().putInt("counts", 0);
            items.add(item);
        }
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack item = new ItemStack(this);
        item.getOrCreateTag().putInt("counts", 0);
        return item;
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        stack.getOrCreateTag().putInt("counts", 0);
        super.onCreated(stack, worldIn, playerIn);
    }
}
