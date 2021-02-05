package com.howlowhello.katze.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveTowardsTargetGoal;

public class ApproachTargetGoal extends MoveTowardsTargetGoal {
    private int moveCounter;

    public ApproachTargetGoal(CreatureEntity creature, double speedIn, float targetMaxDistance) {
        super(creature, speedIn, targetMaxDistance);
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.moveCounter = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.moveCounter > 0){
            this.moveCounter -- ;
        }
        else {this.moveCounter = 0;}
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && this.moveCounter > 0;
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.moveCounter = 0;
    }
}
