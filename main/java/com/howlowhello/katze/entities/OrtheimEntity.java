package com.howlowhello.katze.entities;

import com.howlowhello.katze.init.ModItems;
import com.howlowhello.katze.init.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class OrtheimEntity extends ZombieEntity {


    public OrtheimEntity(EntityType<? extends ZombieEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 25.0D)
                .createMutableAttribute(Attributes.ARMOR, 2.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE,4.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 48.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.4D)
                .createMutableAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    @Override
    protected void applyEntityAI() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.7D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, DirewolfEntity.class, IsraDynameEntity.class)).setCallsForHelp(OrtheimEntity.class));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, DirewolfEntity.class, IsraDynameEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
    }



    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        if (flag) {
            entityIn.setFire(4);
        }
        return flag;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PLAYER_ATTACK_CRIT;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }



    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);

        if (this.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty()){
            this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(ModItems.RUBY_HELMET.get()));
        }
        if (this.getItemStackFromSlot(EquipmentSlotType.CHEST).isEmpty()){
            this.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(ModItems.RUBY_CHESTPLATE.get()));
        }
        if (this.getItemStackFromSlot(EquipmentSlotType.LEGS).isEmpty()){
            this.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(ModItems.RUBY_LEGGINGS.get()));
        }
        if (this.getItemStackFromSlot(EquipmentSlotType.FEET).isEmpty()){
            this.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(ModItems.RUBY_BOOTS.get()));
        }
        if (this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()){
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModItems.RUBY_SWORD.get()));
        }
        if (this.getItemStackFromSlot(EquipmentSlotType.OFFHAND).isEmpty()){
            this.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(ModItems.RUBY_SWORD.get()));
        }
    }


    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        float f = difficultyIn.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);


        this.setEquipmentBasedOnDifficulty(difficultyIn);
        this.setEnchantmentBasedOnDifficulty(difficultyIn);


        if (this.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty()) {
            LocalDate localdate = LocalDate.now();
            int i = localdate.get(ChronoField.DAY_OF_MONTH);
            int j = localdate.get(ChronoField.MONTH_OF_YEAR);
            if (j == 10 && i == 31 && this.rand.nextFloat() < 0.25F) {
                this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
                this.inventoryArmorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.0F;
            }
        }

        return spawnDataIn;
    }



}
