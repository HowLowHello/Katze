package com.howlowhello.katze.blocks;

import com.howlowhello.katze.blocks.tileentities.TradeBoxTileEntity;
import com.howlowhello.katze.init.ModTileEntityType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class TradeBoxBlock extends EnderChestBlock {
    protected TradeBoxTileEntity tradeBox;

    public TradeBoxBlock() {
        super(Block.Properties.create(Material.CORAL)
                .hardnessAndResistance(3.0f, 8.0f)
                .sound(SoundType.SNOW)
                .harvestTool(ToolType.AXE)
                .setRequiresTool());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        this.tradeBox = ModTileEntityType.TRADE_BOX_TILE_ENTITY.get().create();
        if (this.tradeBox!=null){
            this.tradeBox.block = this;
        }
        return this.tradeBox;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote){

            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TradeBoxTileEntity){
                this.tradeBox = (TradeBoxTileEntity) tileEntity;
                this.tradeBox.block = this;
                NetworkHooks.openGui((ServerPlayerEntity) player, (TradeBoxTileEntity) tileEntity, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
        //return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }




    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    }


}
