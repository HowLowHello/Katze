package com.howlowhello.katze.world.siege;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EvokerFangsEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;

public class BloodyCrestManager {




    public static void castSpell(PlayerEntity player, LivingEntity target) {
        double d0 = Math.min(target.getPosY(), player.getPosY());
        double d1 = Math.max(target.getPosY(), player.getPosY()) + 1.0D;
        float f = (float)MathHelper.atan2(target.getPosZ() - player.getPosZ(), target.getPosX() - player.getPosX());
        if (player.getDistanceSq(target) < 9.0D) {
            for(int i = 0; i < 5; ++i) {
                float f1 = f + (float)i * (float)Math.PI * 0.4F;
                BloodyCrestManager.spawnFangs(player,player.getPosX() + (double)MathHelper.cos(f1) * 1.5D, player.getPosZ() + (double)MathHelper.sin(f1) * 1.5D, d0, d1, f1, 0);
            }

            for(int k = 0; k < 8; ++k) {
                float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + 1.2566371F;
                BloodyCrestManager.spawnFangs(player,player.getPosX() + (double)MathHelper.cos(f2) * 2.5D, player.getPosZ() + (double)MathHelper.sin(f2) * 2.5D, d0, d1, f2, 3);
            }
        } else {
            for(int l = 0; l < 16; ++l) {
                double d2 = 1.25D * (double)(l + 1);
                int j = 1 * l;
                BloodyCrestManager.spawnFangs(player,player.getPosX() + (double)MathHelper.cos(f) * d2, player.getPosZ() + (double)MathHelper.sin(f) * d2, d0, d1, f, j);
            }
        }

    }


    public static boolean spawnFangs(PlayerEntity player, double x, double z, double value, double y, float rotationYaw, int warmupDelayTicks) {
        BlockPos blockpos = new BlockPos(x, y, z);
        boolean flag = false;
        double d0 = 0.0D;

        while(true) {
            BlockPos blockpos1 = blockpos.down();
            BlockState blockstate = player.world.getBlockState(blockpos1);
            if (blockstate.isSolidSide(player.world, blockpos1, Direction.UP)) {
                if (!player.world.isAirBlock(blockpos)) {
                    BlockState blockstate1 = player.world.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(player.world, blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.getEnd(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.down();
            if (blockpos.getY() < MathHelper.floor(value) - 1) {
                break;
            }
        }

        if (flag) {
            player.world.addEntity(new EvokerFangsEntity(player.world, x, (double)blockpos.getY() + d0, z, rotationYaw, warmupDelayTicks, player));
        }
        return flag;
    }
}
