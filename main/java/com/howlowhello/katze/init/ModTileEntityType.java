package com.howlowhello.katze.init;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.blocks.tileentities.TradeBoxTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityType {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Katze.MOD_ID);

    public static final RegistryObject<TileEntityType<TradeBoxTileEntity>> TRADE_BOX_TILE_ENTITY = TILE_ENTITY_TYPES.register("trade_box_tile_entity", ()-> TileEntityType.Builder.create(TradeBoxTileEntity::new, ModBlocks.TRADE_BOX_BLOCK.get()).build(null));
}
