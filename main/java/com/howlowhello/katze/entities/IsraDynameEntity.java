package com.howlowhello.katze.entities;

import com.howlowhello.katze.entities.ai.AdvancedMeleeAttackGoal;
import com.howlowhello.katze.entities.ai.ApproachTargetGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class IsraDynameEntity extends MonsterEntity {
    private static final DataParameter<Boolean> ESCALATED = EntityDataManager.createKey(IsraDynameEntity.class, DataSerializers.BOOLEAN);
    private int attackTimer;
    private static double speedValue = 0.3D;
    // Initialized as 0 so the entity can start ranged attack immediately
    // launching a single ranged attack will reset this to 25
    private int rangedAttackCoolDown = 0;

    public IsraDynameEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }


    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 150.0D)
                .createMutableAttribute(Attributes.ARMOR, 15.0D)
                .createMutableAttribute(Attributes.ARMOR_TOUGHNESS, 10.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE,21.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 64.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, speedValue);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AdvancedMeleeAttackGoal(this, 1.0D, false, 12.0F));
        this.goalSelector.addGoal(1, new IsraDynameEntity.AttackGoal(this));
        //this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 0.9D, 12.0F));
        this.goalSelector.addGoal(2, new ApproachTargetGoal(this, 0.9D, 64.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
    }

    /**
     * Decrements the entity's air supply when underwater
     */
    protected int decreaseAirSupply(int air) {
        return air;
    }

    protected void collideWithEntity(Entity entityIn) {
        super.collideWithEntity(entityIn);
    }

    public void setEscalated(boolean b){
        this.dataManager.set(ESCALATED, b);
    }

    public boolean isEscalated(){
        return this.dataManager.get(ESCALATED);
    }

    protected int getRangedAttackCoolDown(){ return this.rangedAttackCoolDown; }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void livingTick() {
        // Golem's Melee
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        if (horizontalMag(this.getMotion()) > (double)2.5000003E-7F && this.rand.nextInt(5) == 0) {
            int i = MathHelper.floor(this.getPosX());
            int j = MathHelper.floor(this.getPosY() - (double)0.2F);
            int k = MathHelper.floor(this.getPosZ());
            BlockPos pos = new BlockPos(i, j, k);
            BlockState blockstate = this.world.getBlockState(pos);
            if (!blockstate.isAir(this.world, pos)) {
                this.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, blockstate).setPos(pos), this.getPosX() + ((double)this.rand.nextFloat() - 0.5D) * (double)this.getWidth(), this.getPosY() + 0.1D, this.getPosZ() + ((double)this.rand.nextFloat() - 0.5D) * (double)this.getWidth(), 4.0D * ((double)this.rand.nextFloat() - 0.5D), 0.5D, ((double)this.rand.nextFloat() - 0.5D) * 4.0D);
            }
        }
        // Ranged Attack
        if (this.isAlive()) {
            if (this.world.isRemote) {

                if (this.hasTargetedEntity()) {
                    if (this.clientSideAttackTime < this.getAttackDuration()) {
                        ++this.clientSideAttackTime;
                    }

                    LivingEntity livingentity = this.getTargetedEntity();
                    if (livingentity != null) {
                        this.getLookController().setLookPositionWithEntity(livingentity, 90.0F, 90.0F);
                        this.getLookController().tick();
                        double d5 = (double)this.getAttackAnimationScale(0.0F);
                        double d0 = livingentity.getPosX() - this.getPosX();
                        double d1 = livingentity.getPosYHeight(0.5D) - this.getPosYEye();
                        double d2 = livingentity.getPosZ() - this.getPosZ();
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 = d0 / d3;
                        d1 = d1 / d3;
                        d2 = d2 / d3;
                        double d4 = this.rand.nextDouble();

                        while(d4 < d3 && this.isEscalated()) {
                            d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
                            double d6 = 0.2 * this.rand.nextGaussian();
                            this.world.addParticle(ParticleTypes.FLAME, this.getPosX() + d0 * d4 + d6, this.getPosYEye() + d1 * d4 + d6, this.getPosZ() + d2 * d4 + d6, 0.0D, 0.0D, 0.0D);
                        }

                        while(d4 < d3 && !this.isEscalated()) {
                            d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
                            this.world.addParticle(ParticleTypes.WITCH, this.getPosX() + d0 * d4, this.getPosYEye() + d1 * d4, this.getPosZ() + d2 * d4, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }

            if (this.isInWaterOrBubbleColumn()) {
                this.setAir(300);
            }

            if (this.hasTargetedEntity()) {
                this.rotationYaw = this.rotationYawHead;
            }

            // Ranged Attack Cool Down
            if (this.getRangedAttackCoolDown() > 0){
                this.rangedAttackCoolDown -- ;
            }
            else {this.rangedAttackCoolDown = 0; }
        }


        super.livingTick();
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte)4);
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.rand.nextInt((int)f) : f;
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f1);
        if (flag) {
            entityIn.setMotion(entityIn.getMotion().add(0.0D, (double)0.4F, 0.0D));
            this.applyEnchantments(this, entityIn);
        }

        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean flag = super.attackEntityFrom(source, amount);
        return flag;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTimer = 10;
            this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRON_GOLEM_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_STEP, 1.0F, 1.0F);
    }
    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
    }

    public boolean isNotColliding(IWorldReader worldIn) {
        BlockPos blockpos = this.getPosition();
        BlockPos blockpos1 = blockpos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos1);
        if (!blockstate.canSpawnMobs(worldIn, blockpos1, this)) {
            return false;
        } else {
            for(int i = 1; i < 3; ++i) {
                BlockPos blockpos2 = blockpos.up(i);
                BlockState blockstate1 = worldIn.getBlockState(blockpos2);
                if (!WorldEntitySpawner.func_234968_a_(worldIn, blockpos2, blockstate1, blockstate1.getFluidState(), EntityType.IRON_GOLEM)) {
                    return false;
                }
            }

            return WorldEntitySpawner.func_234968_a_(worldIn, blockpos, worldIn.getBlockState(blockpos), Fluids.EMPTY.getDefaultState(), EntityType.IRON_GOLEM) && worldIn.checkNoEntityCollision(this);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0D, (double)(0.875F * this.getEyeHeight()), (double)(this.getWidth() * 0.4F));
    }


    private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.createKey(IsraDynameEntity.class, DataSerializers.VARINT);
    private int clientSideAttackTime;
    private LivingEntity targetedEntity;

    protected void registerData() {
        super.registerData();
        this.dataManager.register(TARGET_ENTITY, 0);
        this.dataManager.register(ESCALATED, false);
    }

    private void setTargetedEntity(int entityId) {
        this.dataManager.set(TARGET_ENTITY, entityId);
    }

    public boolean hasTargetedEntity() {
        return this.dataManager.get(TARGET_ENTITY) != 0;
    }

    public int getAttackDuration() {
        return 80;
    }

    public float getAttackAnimationScale(float p_175477_1_) {
        return ((float)this.clientSideAttackTime + p_175477_1_) / (float)this.getAttackDuration();
    }

    @Nullable
    public LivingEntity getTargetedEntity() {
        if (!this.hasTargetedEntity()) {
            return null;
        } else if (this.world.isRemote) {
            if (this.targetedEntity != null) {
                return this.targetedEntity;
            } else {
                Entity entity = this.world.getEntityByID(this.dataManager.get(TARGET_ENTITY));
                if (entity instanceof LivingEntity) {
                    this.targetedEntity = (LivingEntity)entity;
                    return this.targetedEntity;
                } else {
                    return null;
                }
            }
        } else {
            return this.getAttackTarget();
        }
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (TARGET_ENTITY.equals(key)) {
            this.clientSideAttackTime = 0;
            this.targetedEntity = null;
        }

    }




    // Ranged Attack Goal

    static class AttackGoal extends Goal {
        private final IsraDynameEntity israDyname;
        private int tickCounter;

        public AttackGoal(IsraDynameEntity israDyname) {
            this.israDyname = israDyname;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            // Includes checking if the rangedAttackCoolDown is ready
            LivingEntity livingentity = this.israDyname.getAttackTarget();
            return livingentity != null && livingentity.isAlive() && (this.israDyname.getDistanceSq(this.israDyname.getAttackTarget()) > 120.0D) && this.israDyname.getRangedAttackCoolDown() == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            // Includes checking if the rangedAttackCoolDown is ready
            return super.shouldContinueExecuting() && (this.israDyname.getDistanceSq(this.israDyname.getAttackTarget()) > 9.0D) && this.israDyname.getRangedAttackCoolDown() == 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.tickCounter = -10;
            this.israDyname.getNavigator().clearPath();
            this.israDyname.getLookController().setLookPositionWithEntity(this.israDyname.getAttackTarget(), 90.0F, 90.0F);
            this.israDyname.isAirBorne = true;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.israDyname.setTargetedEntity(0);
            this.israDyname.setAttackTarget((LivingEntity)null);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.israDyname.getAttackTarget();
            this.israDyname.getNavigator().clearPath();
            this.israDyname.getLookController().setLookPositionWithEntity(livingentity, 90.0F, 90.0F);
            //if (!this.israDyname.canEntityBeSeen(livingentity)) {
                //this.israDyname.setAttackTarget((LivingEntity)null);
            //} else {
                ++this.tickCounter;
                if (this.tickCounter == 0) {
                    this.israDyname.setTargetedEntity(this.israDyname.getAttackTarget().getEntityId());
                    //if (!this.israDyname.isSilent()) {
                    // this.israDyname.world.setEntityState(this.israDyname, (byte)21);
                    //}
                } else if (this.tickCounter >= this.israDyname.getAttackDuration()) {
                    float f = 1.0F;
                    if (this.israDyname.world.getDifficulty() == Difficulty.HARD) {
                        f += 2.0F;
                    }
                    // if escalated, create an additional explosion, then reset it
                    if (this.israDyname.isEscalated()){
                        livingentity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.israDyname, this.israDyname), f);
                        livingentity.attackEntityFrom(DamageSource.causeMobDamage(this.israDyname), (float)this.israDyname.getAttributeValue(Attributes.ATTACK_DAMAGE));
                        this.israDyname.getEntityWorld().createExplosion(this.israDyname, this.israDyname.getTargetedEntity().getPosX(),
                                this.israDyname.getTargetedEntity().getPosY(), this.israDyname.getTargetedEntity().getPosZ(),
                                (float)3 * 1.0F, Explosion.Mode.NONE);
                        this.israDyname.setAttackTarget((LivingEntity)null);
                        this.israDyname.setEscalated(false);
                    }
                    else {
                        livingentity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.israDyname, this.israDyname), f);
                        livingentity.attackEntityFrom(DamageSource.causeMobDamage(this.israDyname), (float)this.israDyname.getAttributeValue(Attributes.ATTACK_DAMAGE));
                        this.israDyname.setAttackTarget((LivingEntity)null);
                    }
                    // 12% chance escalated after a single attack
                    if (Math.random()>0.87) {
                        this.israDyname.setEscalated(true);
                    }

                    // reset rangedAttackCoolDown to 25 ticks
                    this.israDyname.rangedAttackCoolDown = 25;


                super.tick();
            }
        }
    }
}
