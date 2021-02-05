package com.howlowhello.katze;

import com.howlowhello.katze.entities.*;
import com.howlowhello.katze.init.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈
@Mod("katze_adventure")
public class Katze {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "katze_adventure";

    public Katze() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityType.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEffects.POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModSounds.SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        /** 载入注册完的实体*/
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(ModEntityType.HOG.get(), HogEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.ISRA_DYNAME.get(), IsraDynameEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.PLAYER_ISRA_DYNAME.get(), PlayerIsraDynameEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.DIREWOLF.get(), DirewolfEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.HEAVY_RUBY.get(), HeavyRubyEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.ORTHEIM.get(), OrtheimEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.FATE_SPINNER.get(), FateSpinnerEntity.setCustomAttributes().create());
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) { }

    //创造模式物品栏 Custom ItemGroup TAB
    public static final ItemGroup TAB = new ItemGroup("Katze") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.RUBY.get());
        }
        //给物品栏增加图标
    };

}
