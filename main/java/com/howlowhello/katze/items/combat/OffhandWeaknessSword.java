package com.howlowhello.katze.items.combat;

import com.howlowhello.katze.world.siege.BloodyCrestManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;


public class OffhandWeaknessSword extends SwordItem {
    public static double range = 20; // MAX distance for Fangs

    public OffhandWeaknessSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getTag() != null){
            if (stack.getTag().contains("counts") && stack.getTag().getInt("counts") >= 8){

                EntityRayTraceResult result = LightningSword.rayTrace(worldIn, playerIn, playerIn.getBoundingBox().grow(range));
                if (result != null){

                    if (BloodyCrestManager.spawnFangs(playerIn, result.getHitVec().x, result.getHitVec().z, result.getHitVec().y, result.getHitVec().y + 1.0D, playerIn.rotationYaw, 0)){

                        BloodyCrestManager.spawnFangs(playerIn, result.getHitVec().x+1.0D, result.getHitVec().z+1.0D, result.getHitVec().y, result.getHitVec().y + 1.0D, playerIn.rotationYaw+(float)Math.random()*180.0F, 0);
                        BloodyCrestManager.spawnFangs(playerIn, result.getHitVec().x+1.0D, result.getHitVec().z-1.0D, result.getHitVec().y, result.getHitVec().y + 1.0D, playerIn.rotationYaw+(float)Math.random()*180.0F, 0);
                        BloodyCrestManager.spawnFangs(playerIn, result.getHitVec().x-1.0D, result.getHitVec().z+1.0D, result.getHitVec().y, result.getHitVec().y + 1.0D, playerIn.rotationYaw+(float)Math.random()*180.0F, 0);
                        BloodyCrestManager.spawnFangs(playerIn, result.getHitVec().x-1.0D, result.getHitVec().z-1.0D, result.getHitVec().y, result.getHitVec().y + 1.0D, playerIn.rotationYaw+(float)Math.random()*180.0F, 0);

                        if (result.getEntity() instanceof LivingEntity){
                            LivingEntity livingEntity = (LivingEntity) result.getEntity();
                            livingEntity.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 600));
                        }
                        stack.getTag().putInt("counts", stack.getTag().getInt("counts") - 8);
                        // only allow the player to cast spell every 2 seconds
                        playerIn.getCooldownTracker().setCooldown(this, 40);

                        return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
                    }
                }

            }
        }


        return super.onItemRightClick(worldIn, playerIn, handIn);
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
