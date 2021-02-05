package com.howlowhello.katze.world.gen;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.init.ModEntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Katze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntitySpawns {

    @SubscribeEvent
    public static void spawnEntities(FMLLoadCompleteEvent event){
        for (Biome biome : ForgeRegistries.BIOMES){
            //下界
            if(biome.getCategory() == Biome.Category.NETHER){ }
            //末地
            else if(biome.getCategory() == Biome.Category.THEEND){ }
            //主世界
            else {
                /**if (biome.getCategory() != Biome.Category.OCEAN){
                    biome.getSpawns(EntityClassification.CREATURE)
                            .add(new Biome.SpawnListEntry(ModEntityType.HOG.get(), 10, 3, 5));
                }*/
            }
        }
    }
}
