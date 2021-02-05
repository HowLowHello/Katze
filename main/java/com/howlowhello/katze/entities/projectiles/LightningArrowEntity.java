package com.howlowhello.katze.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LightningArrowEntity extends AbstractArrowEntity {
    LivingEntity shooter;


    public LightningArrowEntity(EntityType<? extends ArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public LightningArrowEntity(World worldIn, double x, double y, double z) {
        super(EntityType.ARROW, x, y, z, worldIn);
    }

    public LightningArrowEntity(World worldIn, LivingEntity shooter) {
        super(EntityType.ARROW, shooter, worldIn);
        this.shooter = shooter;
    }


    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
        if (this.world instanceof ServerWorld) {
            BlockPos blockpos = living.getPosition();
            //if (this.world.canSeeSky(blockpos)) {
                LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.world);
                lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(blockpos));
                lightningboltentity.setCaster(this.shooter instanceof ServerPlayerEntity ? (ServerPlayerEntity) this.shooter : null);
                this.world.addEntity(lightningboltentity);
                this.playSound(SoundEvents.ITEM_TRIDENT_THUNDER, 5.0F, 1.0F);
            //}
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.ARROW);
    }

}
