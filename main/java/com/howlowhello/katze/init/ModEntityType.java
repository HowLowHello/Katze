package com.howlowhello.katze.init;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.entities.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityType {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Katze.MOD_ID);

    // Entity Types
    public static final RegistryObject<EntityType<HogEntity>> HOG = ENTITY_TYPES.register("hog",
            () -> EntityType.Builder.create(HogEntity::new, EntityClassification.CREATURE)//将分类设为动物
                    .size(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Katze.MOD_ID, "hog").toString()));

    public static final RegistryObject<EntityType<IsraDynameEntity>> ISRA_DYNAME = ENTITY_TYPES.register("isra_dyname",
            () -> EntityType.Builder.create(IsraDynameEntity::new, EntityClassification.MONSTER)
                    .size(1.5f, 2.5f)
                    .build(new ResourceLocation(Katze.MOD_ID, "isra_dyname").toString()));

    public static final RegistryObject<EntityType<PlayerIsraDynameEntity>> PLAYER_ISRA_DYNAME = ENTITY_TYPES.register("player_isra_dyname",
            () -> EntityType.Builder.create(PlayerIsraDynameEntity::new, EntityClassification.CREATURE)
                    .size(1.5f, 2.5f)
                    .build(new ResourceLocation(Katze.MOD_ID, "player_isra_dyname").toString()));

    public static final RegistryObject<EntityType<DirewolfEntity>> DIREWOLF = ENTITY_TYPES.register("direwolf",
            () -> EntityType.Builder.create(DirewolfEntity::new, EntityClassification.MONSTER)
                    .size(0.8f, 2.2f)
                    .build(new ResourceLocation(Katze.MOD_ID, "direwolf").toString()));

    public static final RegistryObject<EntityType<HeavyRubyEntity>> HEAVY_RUBY = ENTITY_TYPES.register("heavy_ruby",
            () -> EntityType.Builder.create(HeavyRubyEntity::new, EntityClassification.MONSTER)
                    .size(1.5f, 2.5f)
                    .build(new ResourceLocation(Katze.MOD_ID, "heavy_ruby").toString()));

    public static final RegistryObject<EntityType<OrtheimEntity>> ORTHEIM = ENTITY_TYPES.register("ortheim",
            () -> EntityType.Builder.create(OrtheimEntity::new, EntityClassification.MONSTER)
                    .size(0.8f, 1.8f)
                    .build(new ResourceLocation(Katze.MOD_ID, "ortheim").toString()));

    public static final RegistryObject<EntityType<FateSpinnerEntity>> FATE_SPINNER = ENTITY_TYPES.register("fate_spinner",
            () -> EntityType.Builder.create(FateSpinnerEntity::new, EntityClassification.MONSTER)
                    .size(0.8f, 2.5f)
                    .build(new ResourceLocation(Katze.MOD_ID, "fate_spinner").toString()));
}
