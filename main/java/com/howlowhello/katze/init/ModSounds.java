package com.howlowhello.katze.init;

import com.howlowhello.katze.Katze;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Katze.MOD_ID);

    public static final RegistryObject<SoundEvent> HEAVY_BLADE = SOUNDS.register("heavy_blade",
            ()-> new SoundEvent(new ResourceLocation(Katze.MOD_ID, "heavy_blade")));
}
