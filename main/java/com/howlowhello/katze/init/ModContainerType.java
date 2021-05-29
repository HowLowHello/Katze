package com.howlowhello.katze.init;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.inventory.TradeBoxContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerType {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Katze.MOD_ID);

    public static final RegistryObject<ContainerType<TradeBoxContainer>> TRADE_BOX = CONTAINER_TYPES.register("trade_box", ()-> IForgeContainerType.create(TradeBoxContainer::new));
}
