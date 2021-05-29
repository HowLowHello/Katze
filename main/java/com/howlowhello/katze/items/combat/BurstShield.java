package com.howlowhello.katze.items.combat;

import com.howlowhello.katze.Katze;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.Explosion;

public class BurstShield extends KatzeShield{

    public BurstShield() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .maxDamage(899)
        );
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    @Override
    public boolean castSpell(LivingEntity caster) {
        boolean flag = false;

        for(Entity entity1 : caster.getEntityWorld().getEntitiesInAABBexcluding(caster, caster.getBoundingBox().grow(2), null)) {
            if (entity1 instanceof LivingEntity){
                if (((LivingEntity) entity1).getAttackingEntity() instanceof PlayerEntity){
                    flag = true;
                }
                else if (entity1 instanceof MonsterEntity){
                    flag = true;
                }
            }
        }
        if (flag){
            // make the player invincible for 1 sec
            caster.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 20, 4));
            // create an explosion
            caster.getEntityWorld().createExplosion(caster, caster.getPosX(), caster.getPosY(), caster.getPosZ(), 5.0f, Explosion.Mode.NONE);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int getSpellExpense() {
        return 40;
    }


}
