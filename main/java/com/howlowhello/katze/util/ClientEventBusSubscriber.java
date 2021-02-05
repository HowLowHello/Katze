package com.howlowhello.katze.util;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.client.entity.render.*;
import com.howlowhello.katze.init.ModEntityType;
import com.howlowhello.katze.init.ModItems;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Katze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.HOG.get(), HogRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.ISRA_DYNAME.get(), IsraDynameRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.PLAYER_ISRA_DYNAME.get(), PlayerIsraDynameRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.DIREWOLF.get(), DirewolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.HEAVY_RUBY.get(), HeavyRubyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.ORTHEIM.get(), OrtheimRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.FATE_SPINNER.get(), FateSpinnerRenderer::new);

        ItemModelsProperties.registerProperty(ModItems.ENDER_SWORD.get(), new ResourceLocation("charge"), (itemStack, worldIn, entityIn) -> {
            if (itemStack.getTag() != null){
                if (itemStack.getTag().getInt("counts") >= 32){
                    return 1.0F;
                }
                else if (itemStack.getTag().getInt("counts") >= 24){
                    return 0.75F;
                }
                else if (itemStack.getTag().getInt("counts") >= 16){
                    return 0.5F;
                }
                else if (itemStack.getTag().getInt("counts") >= 8){
                    return 0.25F;
                }
            }
            return 0.0F;
        });



        ItemModelsProperties.registerProperty(ModItems.LIGHTNING_SWORD.get(), new ResourceLocation("charge"), (itemStack, worldIn, entityIn) -> {
            if (itemStack.getTag() != null){
                if (itemStack.getTag().getInt("counts") >= 32){
                    return 1.0F;
                }
                else if (itemStack.getTag().getInt("counts") >= 16){
                    return 0.5F;
                }
            }
            return 0.0F;
        });



        ItemModelsProperties.registerProperty(ModItems.OFFHAND_WEAKNESS_SWORD.get(), new ResourceLocation("charge"), (itemStack, worldIn, entityIn) -> {
            if (itemStack.getTag() != null){
                if (itemStack.getTag().getInt("counts") >= 32){
                    return 1.0F;
                }
                else if (itemStack.getTag().getInt("counts") >= 24){
                    return 0.75F;
                }
                else if (itemStack.getTag().getInt("counts") >= 16){
                    return 0.5F;
                }
                else if (itemStack.getTag().getInt("counts") >= 8){
                    return 0.25F;
                }
            }
            return 0.0F;
        });



        ItemModelsProperties.registerProperty(ModItems.OFFHAND_POISON_SWORD.get(), new ResourceLocation("charge"), (itemStack, worldIn, entityIn) -> {
            if (itemStack.getTag() != null){
                if (itemStack.getTag().getInt("counts") >= 32){
                    return 1.0F;
                }
                else if (itemStack.getTag().getInt("counts") >= 24){
                    return 0.75F;
                }
                else if (itemStack.getTag().getInt("counts") >= 16){
                    return 0.5F;
                }
                else if (itemStack.getTag().getInt("counts") >= 8){
                    return 0.25F;
                }
            }
            return 0.0F;
        });



        ItemModelsProperties.registerProperty(ModItems.OFFHAND_SLOWNESS_SWORD.get(), new ResourceLocation("charge"), (itemStack, worldIn, entityIn) -> {
            if (itemStack.getTag() != null){
                if (itemStack.getTag().getInt("counts") >= 32){
                    return 1.0F;
                }
                else if (itemStack.getTag().getInt("counts") >= 24){
                    return 0.75F;
                }
                else if (itemStack.getTag().getInt("counts") >= 16){
                    return 0.5F;
                }
                else if (itemStack.getTag().getInt("counts") >= 8){
                    return 0.25F;
                }
            }
            return 0.0F;
        });




        ItemModelsProperties.registerProperty(ModItems.LIGHTNING_BOW.get(), new ResourceLocation("powerful"), (itemStack, worldIn, entityIn) -> {
            if (itemStack.getTag() != null){
                if (itemStack.getTag().getInt("rounds") >= 10){
                    return 1.0F;
                }
            }
            return 0.0F;
        });
        ItemModelsProperties.registerProperty(ModItems.LIGHTNING_BOW.get(), new ResourceLocation("pull"), (itemStack, worldIn, entityIn) -> {
            if (entityIn == null) {
                return 0.0F;
            } else {
                return entityIn.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.registerProperty(ModItems.LIGHTNING_BOW.get(), new ResourceLocation("pulling"), (itemStack, worldIn, entityIn) -> {
            return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });



        ItemModelsProperties.registerProperty(ModItems.THOR_BOW.get(), new ResourceLocation("powerful"), (itemStack, worldIn, entityIn) -> {
            if (itemStack.getTag() != null){
                if (itemStack.getTag().getInt("rounds") >= 10){
                    return 1.0F;
                }
            }
            return 0.0F;
        });
        ItemModelsProperties.registerProperty(ModItems.THOR_BOW.get(), new ResourceLocation("pull"), (itemStack, worldIn, entityIn) -> {
            if (entityIn == null) {
                return 0.0F;
            } else {
                return entityIn.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.registerProperty(ModItems.THOR_BOW.get(), new ResourceLocation("pulling"), (itemStack, worldIn, entityIn) -> {
            return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });



        ItemModelsProperties.registerProperty(ModItems.KALEIDO_BOW.get(), new ResourceLocation("pull"), (itemStack, worldIn, entityIn) -> {
            if (entityIn == null) {
                return 0.0F;
            } else {
                return entityIn.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.registerProperty(ModItems.KALEIDO_BOW.get(), new ResourceLocation("pulling"), (itemStack, worldIn, entityIn) -> {
            return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });



        ItemModelsProperties.registerProperty(ModItems.ANIMISTIC_BOW.get(), new ResourceLocation("pull"), (itemStack, worldIn, entityIn) -> {
            if (entityIn == null) {
                return 0.0F;
            } else {
                return entityIn.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.registerProperty(ModItems.ANIMISTIC_BOW.get(), new ResourceLocation("pulling"), (itemStack, worldIn, entityIn) -> {
            return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });



        ItemModelsProperties.registerProperty(ModItems.SOLAR_STORM_BOW.get(), new ResourceLocation("pull"), (itemStack, worldIn, entityIn) -> {
            if (entityIn == null) {
                return 0.0F;
            } else {
                return entityIn.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.registerProperty(ModItems.SOLAR_STORM_BOW.get(), new ResourceLocation("pulling"), (itemStack, worldIn, entityIn) -> {
            return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });



        ItemModelsProperties.registerProperty(ModItems.BINARY_BOW.get(), new ResourceLocation("pull"), (itemStack, worldIn, entityIn) -> {
            if (entityIn == null) {
                return 0.0F;
            } else {
                return entityIn.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.registerProperty(ModItems.BINARY_BOW.get(), new ResourceLocation("pulling"), (itemStack, worldIn, entityIn) -> {
            return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });

    }
}
