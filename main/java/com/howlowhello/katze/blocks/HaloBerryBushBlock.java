package com.howlowhello.katze.blocks;

import com.howlowhello.katze.init.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class HaloBerryBushBlock extends BushBlock implements IGrowable {

    /** int AGE:
     *  0 refers to the state without any fruit
     *  1,2 Candy(70%)/Dual_Berries(30%) fruit (growing,grown)
     *  3,4 Sepith(80%)/U_Material(20%) fruit (growing,grown)
     *  5,6 Ingots(80%)/Diamond(20%) fruit (growing,grown)
     *  7,8 Kachitoritai_Berries (growing,grown)
     *  9,10 Restorative_Berries (growing,grown)
     *  11,12 Taurine_Berries (growing,grown)
     *  13,14 Stealthy_Berries (growing,grown)
     *  15,16 Forte_Berries (growing,grown)
     *  17,18 Celeste_Berries (growing,grown)
     *  19,20 Eclipse_Berries (growing,grown)
     *  21,22 Vita_Berries (growing,grown)
     *  23,24 Dynamite_Berries (growing,grown)
     *
     * */
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_25;

    // random ticking would use up buffer first, slowing the speed at which the crop actually grows
    public static final IntegerProperty PRE_GROWING_BUFFER = BlockStateProperties.HATCH_0_2;
    public static final IntegerProperty GROWN_BUFFER = BlockStateProperties.LEVEL_0_3;
    // will count each time player resets the crop, up to 2 (which means resetting would be disabled)
    public static final IntegerProperty RESET_TIMES = BlockStateProperties.CHARGES;

    private static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public HaloBerryBushBlock() {
        super(AbstractBlock.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement());
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)));
        this.setDefaultState(this.stateContainer.getBaseState().with(PRE_GROWING_BUFFER, Integer.valueOf(0)));
        this.setDefaultState(this.stateContainer.getBaseState().with(GROWN_BUFFER, Integer.valueOf(0)));
    }

    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(Items.SWEET_BERRIES);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    /**
     * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
     * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    public boolean ticksRandomly(BlockState state) {
        return state.get(AGE) == 0 | state.get(AGE) % 2 != 0;
    }

    /**
     * Performs a random tick on a block.
     */
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i == 0 && worldIn.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(5) == 0)){
            if (state.get(PRE_GROWING_BUFFER) == 2){
                Random rand = new Random();
                int num = rand.nextInt(24);
                if (num % 2 == 0){
                    num = num + 1;
                }
                worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(num)), 2);
            }
            else {
                worldIn.setBlockState(pos, state.with(PRE_GROWING_BUFFER, Integer.valueOf(state.get(PRE_GROWING_BUFFER) + 1)), 2);
            }
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);

        }
        else if (i % 2 == 1  && worldIn.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(5) == 0)) {
            if (state.get(GROWN_BUFFER) == 3){
                worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i + 1)), 2);
            }
            else {
                worldIn.setBlockState(pos, state.with(GROWN_BUFFER, Integer.valueOf(state.get(GROWN_BUFFER) + 1)), 2);
            }
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != EntityType.BEE) {
            entityIn.setMotionMultiplier(state, new Vector3d((double)0.8F, 0.75D, (double)0.8F));
        }
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int i = state.get(AGE);

        boolean canBeActivated = (i%2==0 && i!=0  |  i%2==1 && state.get(RESET_TIMES) < 2);
        if (!canBeActivated | player.getHeldItem(handIn).getItem() == Items.BONE_MEAL) {
            return ActionResultType.PASS;
        }

        // Case resetting the fruit type
        else if (i%2==1 && state.get(RESET_TIMES) < 2) {
            Random rand = new Random();
            int num = rand.nextInt(24);
            if (num % 2 == 0){
                num = num + 1;
            }
            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
            worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(num)), 2);
            worldIn.setBlockState(pos, state.with(RESET_TIMES, Integer.valueOf(state.get(RESET_TIMES) + 1)), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);

            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }

        // Case harvesting the fruit
        else if (i%2==0 && i!=0){
            // candy / dual berries
            if (i == 2){
                Random rand = new Random();
                if (rand.nextInt(10) < 7){
                    spawnAsEntity(worldIn, pos, new ItemStack(ModItems.CANDY_CANE.get(), (11 + rand.nextInt(12))));
                }
                else {
                    spawnAsEntity(worldIn, pos, new ItemStack(ModItems.SEARING_BERRIES.get(),1));
                    spawnAsEntity(worldIn, pos, new ItemStack(ModItems.METALLIZATION_BERRIES.get(),1));
                }
            }
            // sepith / u_material
            else if (i == 4){
                Random rand = new Random();
                if (rand.nextInt(10) < 8){
                    spawnAsEntity(worldIn, pos, new ItemStack(ModItems.SEPITH.get(), (1 + rand.nextInt(2))));
                }
                else {
                    spawnAsEntity(worldIn, pos, new ItemStack(ModItems.U_MATERIAL.get(),1));
                }
            }
            // ingots / diamond
            else if (i == 6){
                Random rand = new Random();
                if (rand.nextInt(10) < 8){
                    spawnAsEntity(worldIn, pos, new ItemStack(Items.IRON_INGOT, (2 + rand.nextInt(3))));
                    spawnAsEntity(worldIn, pos, new ItemStack(Items.GOLD_INGOT, (1 + rand.nextInt(3))));
                }
                else {
                    spawnAsEntity(worldIn, pos, new ItemStack(Items.DIAMOND,1));
                }
            }
            // dynamite_berries
            else if (i == 24){
                Random rand = new Random();
                spawnAsEntity(worldIn, pos, new ItemStack(ModItems.DYNAMITE_BERRIES.get(), (4 + rand.nextInt(3))));
            }
            // other types of berries
            else {
                spawnAsEntity(worldIn, pos, new ItemStack(this.getBerriesAccordingly(i),1));
            }

            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
            worldIn.setBlockState(pos, Blocks.SWEET_BERRY_BUSH.getDefaultState());
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }

        else {
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
    }


    protected Item getBerriesAccordingly(int ageIn){
        switch (ageIn){
            case 8:{
                return ModItems.KACHITORITAI_BERRIES.get();
            }
            case 10:{
                return ModItems.RESTORATIVE_BERRIES.get();
            }
            case 12:{
                return ModItems.TAURINE_BERRIES.get();
            }
            case 14:{
                return ModItems.STEALTHY_BERRIES.get();
            }
            case 16:{
                return ModItems.FORTE_BERRIES.get();
            }
            case 18:{
                return ModItems.CELESTE_BERRIES.get();
            }
            case 20:{
                return ModItems.ECLIPSE_BERRIES.get();
            }
            case 22:{
                return ModItems.VITA_BERRIES.get();
            }
        }

        return Items.SWEET_BERRIES;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(PRE_GROWING_BUFFER);
        builder.add(GROWN_BUFFER);
        builder.add(RESET_TIMES);
    }

    /**
     * Whether this IGrowable can grow
     */
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return false;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
    }


}
