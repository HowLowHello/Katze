package com.howlowhello.katze.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosiveArrowEntity extends AbstractArrowEntity {
    LivingEntity shooter;


    public ExplosiveArrowEntity(EntityType<? extends ArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ExplosiveArrowEntity(World worldIn, double x, double y, double z) {
        super(EntityType.ARROW, x, y, z, worldIn);
    }

    public ExplosiveArrowEntity(World worldIn, LivingEntity shooter) {
        super(EntityType.ARROW, shooter, worldIn);
        this.shooter = shooter;
    }



    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
    }



    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
        living.getEntityWorld().createExplosion(shooter, living.getPosX(),
                living.getPosY(), living.getPosZ(),
                (float)2 * 1.0F, Explosion.Mode.NONE);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.ARROW);
    }


}
