package com.howlowhello.katze.items.foods;

import com.howlowhello.katze.Katze;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DynamiteBerries extends Item {

    public DynamiteBerries(){
        super(new Item.Properties()
                .group(Katze.TAB)
                .food(new Food.Builder()
                        .hunger(2)
                        .saturation(1.2f)
                        .setAlwaysEdible()
                        .build())
        );
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isRemote){
            worldIn.createExplosion(entityLiving, entityLiving.getPosX(),
                    entityLiving.getPosY(), entityLiving.getPosZ(),
                    (float)5 * 1.0F, Explosion.Mode.NONE);
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
