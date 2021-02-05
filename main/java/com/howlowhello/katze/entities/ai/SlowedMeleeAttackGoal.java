package com.howlowhello.katze.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class SlowedMeleeAttackGoal extends MeleeAttackGoal {
    private int attackCoolDown = 0;

    public SlowedMeleeAttackGoal(CreatureEntity creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
    }


    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        if (this.attackCoolDown == 0){
            super.checkAndPerformAttack(enemy, distToEnemySqr);
            this.attackCoolDown = 30;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.attackCoolDown > 0){
            this.attackCoolDown --;
        }
        else {this.attackCoolDown = 0;}
    }
}
