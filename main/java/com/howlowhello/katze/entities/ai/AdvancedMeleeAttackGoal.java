package com.howlowhello.katze.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class AdvancedMeleeAttackGoal extends MeleeAttackGoal {
    private float targetMaxDistance;


    public AdvancedMeleeAttackGoal(CreatureEntity creature, double speedIn, boolean useLongMemory, float targetMaxDistance) {
        super(creature, speedIn, useLongMemory);
        this.targetMaxDistance = targetMaxDistance;
    }

    @Override
    public boolean shouldExecute() {
        if (this.attacker.getAttackTarget() != null){
            return super.shouldExecute() && this.attacker.getAttackTarget().getDistanceSq(this.attacker) < (double)(this.targetMaxDistance * this.targetMaxDistance);
        }
        else {return super.shouldExecute();}
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.attacker.getAttackTarget() != null){
            return super.shouldContinueExecuting() && this.attacker.getAttackTarget().getDistanceSq(this.attacker) < (double)(this.targetMaxDistance * this.targetMaxDistance);
        }
        else {return super.shouldContinueExecuting();}
    }
}
