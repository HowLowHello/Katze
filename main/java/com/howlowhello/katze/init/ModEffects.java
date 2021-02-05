package com.howlowhello.katze.init;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.effects.BloodyCatastrophe;
import com.howlowhello.katze.effects.ChangeableProphesy;
import com.howlowhello.katze.effects.CoolDown;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {
    public static final DeferredRegister<Effect> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Katze.MOD_ID);

    public static final RegistryObject<CoolDown> COOL_DOWN = POTIONS.register("cool_down", CoolDown::new);
    public static final RegistryObject<BloodyCatastrophe> BLOODY_CATASTROPHE = POTIONS.register("bloody_catastrophe", BloodyCatastrophe::new);
    public static final RegistryObject<ChangeableProphesy> CHANGEABLE_PROPHESY = POTIONS.register("changeable_prophesy", ChangeableProphesy::new);
}
