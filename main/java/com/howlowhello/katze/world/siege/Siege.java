package com.howlowhello.katze.world.siege;

import com.google.common.collect.Sets;
import com.howlowhello.katze.entities.*;
import com.howlowhello.katze.init.ModEntityType;
import com.howlowhello.katze.items.BloodyCrest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.UUID;

public class Siege {


    public static Set<UUID> originator = Sets.newHashSet();

    public static void addOriginator(PlayerEntity player) {
        originator.add(player.getUniqueID());
    }

    public static void removeOriginator(PlayerEntity player) {
        originator.remove(player.getUniqueID());
    }

    public static void clearOriginator(){
        originator.clear();
    }



    /**
    public static Set<UUID> involvers = Sets.newHashSet();

    public static void addInvolvers(PlayerEntity player) {
        involvers.add(player.getUniqueID());
    }

    public static void removeInvolvers(PlayerEntity player) {
        involvers.remove(player.getUniqueID());
    }

    public static void clearInvolvers() {
        involvers.clear();
    }
     */



    public static boolean isSiegeMonster(Entity entity){
        return entity instanceof OrtheimEntity | entity instanceof DirewolfEntity | entity instanceof HeavyRubyEntity
                | entity instanceof FateSpinnerEntity | entity instanceof IsraDynameEntity;
    }


    public static BlockPos findRandomSpawnPos(World world, BlockPos pos, int distanceMultiplier) {
        int i = 1;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(int i1 = 0; i1 < 3; ++i1) {
            float f = world.rand.nextFloat() * ((float)Math.PI * 2F);
            int j = pos.getX() + MathHelper.floor(MathHelper.cos(f) * (4.0F * distanceMultiplier) * (float)i) + world.rand.nextInt(5);
            int l = pos.getZ() + MathHelper.floor(MathHelper.sin(f) * (4.0F * distanceMultiplier) * (float)i) + world.rand.nextInt(5);
            int k = world.getHeight(Heightmap.Type.WORLD_SURFACE, j, l);
            blockpos$mutable.setPos(j, k, l);

            if ( world.isAreaLoaded(blockpos$mutable.getX() - 10, blockpos$mutable.getY() - 10, blockpos$mutable.getZ() - 10, blockpos$mutable.getX() + 10, blockpos$mutable.getY() + 10, blockpos$mutable.getZ() + 10)
                    && world.getChunkProvider().isChunkLoaded(new ChunkPos(blockpos$mutable))
                    || world.getBlockState(blockpos$mutable.down()).isIn(Blocks.SNOW)
                    && world.getBlockState(blockpos$mutable).isAir()) {

                return blockpos$mutable;
            }
        }

        return pos;
    }

    public static void doFirstWaveMonstersSpawn(ServerWorld world, BlockPos pos, int forLoopInt, UUID uuid){
        switch (forLoopInt){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6: {
                OrtheimEntity ortheim = new OrtheimEntity(ModEntityType.ORTHEIM.get(), world);
                ortheim.onInitialSpawn(world, world.getDifficultyForLocation(pos), SpawnReason.REINFORCEMENT, null, null);
                ortheim.setPosition(pos.getX(), pos.getY(), pos.getZ());
                ortheim.addTag("SiegeMilestone");
                ortheim.addTag("TheOriginatorIs"+uuid.toString());
                ortheim.enablePersistence();
                world.addEntity(ortheim);
                break;
            }
            case 7:
            case 8:
            case 9:
            case 10: {
                DirewolfEntity direwolf = new DirewolfEntity(ModEntityType.DIREWOLF.get(), world);
                direwolf.setPosition(pos.getX(), pos.getY(), pos.getZ());
                direwolf.addTag("SiegeMilestone");
                direwolf.addTag("TheOriginatorIs"+uuid.toString());
                direwolf.enablePersistence();
                world.addEntity(direwolf);
                break;
            }
            case 11:
            case 12: {
                HeavyRubyEntity heavyRuby = new HeavyRubyEntity(ModEntityType.HEAVY_RUBY.get(), world);
                heavyRuby.setPosition(pos.getX(), pos.getY(), pos.getZ());
                heavyRuby.addTag("SiegeMilestone");
                heavyRuby.addTag("TheOriginatorIs"+uuid.toString());
                heavyRuby.enablePersistence();
                world.addEntity(heavyRuby);
                break;
            }
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 29:
            case 30:
            case 31:
            case 32: {
                OrtheimEntity ortheim = new OrtheimEntity(ModEntityType.ORTHEIM.get(), world);
                ortheim.onInitialSpawn(world, world.getDifficultyForLocation(pos), SpawnReason.REINFORCEMENT, null, null);
                ortheim.setPosition(pos.getX(), pos.getY(), pos.getZ());
                ortheim.enablePersistence();
                world.addEntity(ortheim);
                break;
            }
            case 18:
            case 19:
            case 26:
            case 27:
            case 33:
            case 34:
            case 35: {
                DirewolfEntity direwolf = new DirewolfEntity(ModEntityType.DIREWOLF.get(), world);
                direwolf.setPosition(pos.getX(), pos.getY(), pos.getZ());
                direwolf.enablePersistence();
                world.addEntity(direwolf);
                break;
            }
            case 20:
            case 28:
            case 36: {
                HeavyRubyEntity heavyRuby = new HeavyRubyEntity(ModEntityType.HEAVY_RUBY.get(), world);
                heavyRuby.setPosition(pos.getX(), pos.getY(), pos.getZ());
                heavyRuby.enablePersistence();
                world.addEntity(heavyRuby);
                break;
            }

        }
    }

    public static MonsterEntity getEntityShouldSpawn(ServerWorld world, EntityType<?> entityType, BlockPos pos, boolean isMilestone, UUID uuid){

        if (entityType == ModEntityType.DIREWOLF.get()){
            DirewolfEntity direwolf = new DirewolfEntity(ModEntityType.DIREWOLF.get(), world);
            direwolf.setPosition(pos.getX(), pos.getY(), pos.getZ());
            if (isMilestone){
                direwolf.addTag("SiegeMilestone");
                direwolf.addTag("TheOriginatorIs"+uuid.toString());
            }
            direwolf.enablePersistence();
            return direwolf;
        }
        else if (entityType == ModEntityType.HEAVY_RUBY.get()){
            HeavyRubyEntity heavyRuby = new HeavyRubyEntity(ModEntityType.HEAVY_RUBY.get(), world);
            heavyRuby.setPosition(pos.getX(), pos.getY(), pos.getZ());
            if (isMilestone){
                heavyRuby.addTag("SiegeMilestone");
                heavyRuby.addTag("TheOriginatorIs"+uuid.toString());
            }
            heavyRuby.enablePersistence();
            return heavyRuby;
        }
        else if (entityType == ModEntityType.FATE_SPINNER.get()){
            FateSpinnerEntity fateSpinner = new FateSpinnerEntity(ModEntityType.FATE_SPINNER.get(), world);
            fateSpinner.setPosition(pos.getX(), pos.getY(), pos.getZ());
            if (isMilestone){
                fateSpinner.addTag("SiegeMilestone");
                fateSpinner.addTag("TheOriginatorIs"+uuid.toString());
            }
            fateSpinner.enablePersistence();
            return fateSpinner;
        }
        else if (entityType == ModEntityType.ISRA_DYNAME.get()){
            IsraDynameEntity israDyname = new IsraDynameEntity(ModEntityType.ISRA_DYNAME.get(), world);
            israDyname.setPosition(pos.getX(), pos.getY(), pos.getZ());
            if (isMilestone){
                israDyname.addTag("SiegeMilestone");
                israDyname.addTag("TheOriginatorIs"+uuid.toString());
            }
            israDyname.enablePersistence();
            return israDyname;
        }
        else {
            OrtheimEntity ortheim = new OrtheimEntity(ModEntityType.ORTHEIM.get(), world);
            ortheim.onInitialSpawn(world, world.getDifficultyForLocation(pos), SpawnReason.REINFORCEMENT, null, null);
            ortheim.setPosition(pos.getX(), pos.getY(), pos.getZ());
            if (isMilestone){
                ortheim.addTag("SiegeMilestone");
                ortheim.addTag("TheOriginatorIs"+uuid.toString());
            }
            ortheim.enablePersistence();
            return ortheim;
        }
    }


    @Nullable
    public static EntityType<?> getEntityTypeAccordingly(ItemStack crestIn) {

        if (!(crestIn.getItem() instanceof BloodyCrest)) { return null; }

        else {
            int damage = crestIn.getDamage();

            // Isra Dyname (6 total)
            if ((111-damage)==1 | (111-damage)==2 | (111-damage)==22 | (111-damage)==42 | (111-damage)==62 | (111-damage) == 92){
                return ModEntityType.ISRA_DYNAME.get();
            }
            // Fate Spinner (10 total)
            else if ((111-damage)==3 | (111-damage)==6 | (111-damage)<=104 && (111-damage)%13==0){
                return ModEntityType.FATE_SPINNER.get();
            }
            // Heavy Ruby (13 total)
            else if ((111-damage)==108 | (111-damage)%7==0 && (111-damage)!=105){
                return ModEntityType.HEAVY_RUBY.get();
            }
            // Direwolf (29 total)
            else if ((111-damage)==4 | (111-damage)==8 | (111-damage)%5==0 | (111-damage)%11==0){
                return ModEntityType.DIREWOLF.get();
            }
            // Ortheim (53 total)
            else {
                return ModEntityType.ORTHEIM.get();
            }
        }
    }


}
