package com.howlowhello.katze.items.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;

public class LightningSword extends SwordItem {
    public static double range = 20; // MAX distance for casting the lightning

    public LightningSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getTag() != null){
            if (stack.getTag().contains("counts") && stack.getTag().getInt("counts") >= 16){

                EntityRayTraceResult result = LightningSword.rayTrace(worldIn, playerIn, playerIn.getBoundingBox().grow(range));
                if (result != null){
                    if (!(result.getEntity() instanceof ItemEntity)){
                        Vector3i blockpos = new Vector3i(result.getHitVec().x, result.getHitVec().y, result.getHitVec().z);
                        LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(worldIn);
                        lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(blockpos));
                        lightningboltentity.setCaster(playerIn instanceof ServerPlayerEntity ? (ServerPlayerEntity) playerIn : null);
                        worldIn.addEntity(lightningboltentity);
                        result.getEntity().playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 5.0F, 1.0F);

                        stack.getTag().putInt("counts", stack.getTag().getInt("counts") - 16);
                        // only allow the player to cast lightning every 4 seconds
                        playerIn.getCooldownTracker().setCooldown(this, 80);

                        return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
                    }
                }

            }
        }


        return super.onItemRightClick(worldIn, playerIn, handIn);
    }



    @Nullable
    public static EntityRayTraceResult rayTrace(World worldIn, PlayerEntity player, AxisAlignedBB boundingBox) {


        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vector3d startVec = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vector3d endVec = startVec.add((double)f6 * range, (double)f5 * range, (double)f7 * range);

        Entity entity = null;

        for(Entity entity1 : worldIn.getEntitiesInAABBexcluding(player, boundingBox, null)) {
            AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow((double)0.3F);
            Optional<Vector3d> optional = axisalignedbb.rayTrace(startVec, endVec);
            if (optional.isPresent()) {
                double d1 = startVec.squareDistanceTo(endVec);
                if (d1 < range * range) {
                    entity = entity1;
                }
            }
        }

        return entity == null ? null : new EntityRayTraceResult(entity);
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
