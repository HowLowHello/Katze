package com.howlowhello.katze.items.combat;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.world.siege.BloodyCrestManager;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class PoisonFangShield extends KatzeShield{

    public PoisonFangShield() {
        super(new Item.Properties()
                .group(Katze.TAB)
                .maxDamage(754)
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
                    ((LivingEntity) entity1).addPotionEffect(new EffectInstance(Effects.POISON, 300));
                }
                else if (entity1 instanceof MonsterEntity){
                    flag = true;
                    ((LivingEntity) entity1).addPotionEffect(new EffectInstance(Effects.POISON, 300));
                }
            }
        }
        if (flag){
            // spawn fangs
            if (caster instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) caster;
                BloodyCrestManager.spawnFangs(player, caster.getPosX() + 1.0D, caster.getPosZ() + 1.0D, caster.getPosY(), caster.getPosY() + 1.0D, caster.rotationYaw + (float) Math.random() * 180.0F, 0);
                BloodyCrestManager.spawnFangs(player, caster.getPosX() - 1.0D, caster.getPosZ() + 1.0D, caster.getPosY(), caster.getPosY() + 1.0D, caster.rotationYaw + (float) Math.random() * 180.0F, 0);
                BloodyCrestManager.spawnFangs(player, caster.getPosX() + 1.0D, caster.getPosZ() - 1.0D, caster.getPosY(), caster.getPosY() + 1.0D, caster.rotationYaw + (float) Math.random() * 180.0F, 0);
                BloodyCrestManager.spawnFangs(player, caster.getPosX() - 1.0D, caster.getPosZ() - 1.0D, caster.getPosY(), caster.getPosY() + 1.0D, caster.rotationYaw + (float) Math.random() * 180.0F, 0);
                BloodyCrestManager.spawnFangs(player, caster.getPosX(), caster.getPosZ() + 1.0D, caster.getPosY(), caster.getPosY() + 1.0D, caster.rotationYaw + (float) Math.random() * 180.0F, 0);
                BloodyCrestManager.spawnFangs(player, caster.getPosX(), caster.getPosZ() - 1.0D, caster.getPosY(), caster.getPosY() + 1.0D, caster.rotationYaw + (float) Math.random() * 180.0F, 0);
                BloodyCrestManager.spawnFangs(player, caster.getPosX() + 1.0D, caster.getPosZ(), caster.getPosY(), caster.getPosY() + 1.0D, caster.rotationYaw + (float) Math.random() * 180.0F, 0);
                BloodyCrestManager.spawnFangs(player, caster.getPosX() - 1.0D, caster.getPosZ(), caster.getPosY(), caster.getPosY() + 1.0D, caster.rotationYaw + (float) Math.random() * 180.0F, 0);
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int getSpellExpense() {
        return 30;
    }
}
