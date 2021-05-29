package com.howlowhello.katze.init;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.blocks.*;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    //创建一个物品注册类对象
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Katze.MOD_ID);

    // Blocks
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", RubyBlock::new);
    public static final RegistryObject<Block> RUBY_ORE = BLOCKS.register("ruby_ore", RubyOre::new);
    public static final RegistryObject<Block> OVEN = BLOCKS.register("oven", Oven::new);
    // Extra Blocks
    public static final RegistryObject<Block> SEPITH_BLOCK = BLOCKS.register("sepith_block", SepithBlock::new);
    public static final RegistryObject<Block> HALO_BERRY_BUSH = BLOCKS.register("halo_berry_bush", HaloBerryBushBlock::new);
    public static final RegistryObject<Block> TRADE_BOX_BLOCK = BLOCKS.register("trade_box_block", TradeBoxBlock::new);
}
