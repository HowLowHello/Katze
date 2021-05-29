package com.howlowhello.katze.events;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.effects.ChangeableProphesy;
import com.howlowhello.katze.entities.FateSpinnerEntity;
import com.howlowhello.katze.entities.HeavyRubyEntity;
import com.howlowhello.katze.entities.IsraDynameEntity;
import com.howlowhello.katze.init.ModEffects;
import com.howlowhello.katze.init.ModItems;
import com.howlowhello.katze.items.BloodyCrest;
import com.howlowhello.katze.items.TradeBoxOffers;
import com.howlowhello.katze.items.combat.KatzeShield;
import com.howlowhello.katze.util.TradeBoxHelper;
import com.howlowhello.katze.world.siege.BloodyCrestManager;
import com.howlowhello.katze.world.siege.Siege;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;


@Mod.EventBusSubscriber(modid = Katze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {


    //通过取消GUIOpen事件来让玩家无法打开工作台
    /**
    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public static void onCraftingTableOpen(GuiOpenEvent event) {
        if (event.isCancelable()) {
            if (event.getGui() instanceof CraftingScreen) {
                event.setCanceled(true);
    }
                Katze.LOGGER.info("Player tried to open a crafting table!");
        }
    }
    */

    /**订阅实体跳跃事件
     * 检测玩家右手握有木棍时，
     * 跳跃将把脚下的方块变成红宝石块
    @SubscribeEvent // LivingEntity#func_233580_cy_c() ----> LivingEntity#getPosition()
    public static void onJumpWithStick(LivingEvent.LivingJumpEvent event) {
        LivingEntity player = event.getEntityLiving();
        if (player.getHeldItemMainhand().getItem() == Items.STICK) {
            Katze.LOGGER.info("Player tried to jump with a stick!");
            World world = player.getEntityWorld();
            world.setBlockState(player.getPosition().add(0, -1, 0), ModBlocks.RUBY_BLOCK.get().getDefaultState());
        }
    }
    */

    /**订阅攻击实体事件
     * 检测当攻击者右手持有毒苹果时
     * 给被攻击的羊添加中毒和标记buff
    @SubscribeEvent
    public static void onDamageSheep(AttackEntityEvent event) {
        if (event.getEntityLiving().getHeldItemMainhand().getItem() == ModItems.POISON_APPLE.get()) {
            if (event.getTarget().isAlive()) {
                LivingEntity target = (LivingEntity) event.getTarget();
                if (target instanceof SheepEntity) {

                    PlayerEntity player = event.getPlayer();
                    target.addPotionEffect(new EffectInstance(Effects.POISON, 200));
                    target.setGlowing(true);

                    // Server Only
                    if (!event.getPlayer().getEntityWorld().isRemote) {
                        String msg = TextFormatting.RED + "That sheep isn't feeling so good...";
                        player.sendMessage(new StringTextComponent(msg), player.getUniqueID());
                    }
                }
            }
        }
    }*/

    /**在投掷药水时
     * 有92%的几率不会消耗
     * 此功能未来应用在某个防具套装上*/
    @SubscribeEvent (priority = EventPriority.LOWEST)
    public static void onSplashPotion(PlayerInteractEvent.RightClickItem event){
        ItemStack thisItem = event.getItemStack();
        if (thisItem.getItem() instanceof SplashPotionItem){
            final double d = Math.random();
            if (d<=0.91) {
                event.getPlayer().addItemStackToInventory(thisItem.copy());
            }
            else { }
        }
    }
    /**在投掷雪球与末影珍珠时不会消耗
     * 此功能日后加到功能性物品上 */
    @SubscribeEvent (priority = EventPriority.LOW)
    public static void onThrowableItems(PlayerInteractEvent.RightClickItem event){
        ItemStack thisItem = event.getItemStack();
        Item theItem = thisItem.getItem();
        if (theItem instanceof SnowballItem) {
            ItemStack thisItemCopy = thisItem.copy();
            thisItemCopy.setCount(1);
            event.getPlayer().addItemStackToInventory(thisItemCopy);
        }
        else if (theItem instanceof EnderPearlItem){
            ItemStack thisItemCopy = thisItem.copy();
            thisItemCopy.setCount(1);
            event.getPlayer().addItemStackToInventory(thisItemCopy);
        }
        else { }
    }
    /** 在玩家每次获得进度时随机给与奖励
     * 几率：90%耀晶石，10%U物质
     * 由于游戏内解锁配方也视作进度事件
     * 需要通过检测该进度是否有Display来过滤掉配方进度*/
    @SubscribeEvent
    public static void onAdvancements(AdvancementEvent event){
        if(event.getAdvancement().getDisplay() != null){
            final double d = Math.random();
            if (d<=0.89){
                event.getPlayer().addItemStackToInventory(new ItemStack(()->ModItems.SEPITH.get(), 1));
            }
            else {
                event.getPlayer().addItemStackToInventory(new ItemStack(()->ModItems.U_MATERIAL.get(), 1));
            }
        }
    }

    /**在玩家每次喝牛奶时给与210秒的冷却效果
     * 防止用牛奶快速重置装备被动技能的冷却时间 */
    @SubscribeEvent
    public static void onDrinkMilk(LivingEntityUseItemEvent.Finish event){
        LivingEntity entityLiving = event.getEntityLiving();
        if(entityLiving.isAlive()) {
            if(event.getItem().getItem() instanceof MilkBucketItem){
                entityLiving.addPotionEffect(new EffectInstance(ModEffects.COOL_DOWN.get(), 4200));
            }
        }
    }


    /**
    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public static void onSiegeDataRegistry(WorldEvent.Load event){

        if (!event.getWorld().isRemote() && event.getWorld() instanceof ServerWorld){
            ServerWorld world = (ServerWorld) event.getWorld();
            Supplier<SiegeManager> supplier = new SiegeManager(world);
            SiegeManager siege = world.getSavedData().getOrCreate(supplier, SiegeManager.DATA_NAME);
        }
    }


    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public static void onSiegeDataTick(TickEvent.WorldTickEvent event){

        if (!event.world.isRemote() && event.world instanceof ServerWorld){
            ServerWorld world = (ServerWorld) event.world;

            event.world.getServer().getWorld().getSavedData()


            SiegeManager siege = ((ServerWorld) event.world).getSavedData().getOrCreate(supplier, SiegeManager.DATA_NAME);
            siege.tick(((ServerWorld) event.world), siege);
        }

    }*/

    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onChangeableProphesyApply(PotionEvent.PotionAddedEvent event){
        if (event.getPotionEffect().getPotion() instanceof ChangeableProphesy){
            if (event.getEntityLiving() instanceof ServerPlayerEntity){
                ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
                // Send the player a msg
                player.sendMessage(new StringTextComponent(TextFormatting.GREEN + "You got a feeling of unease, but what could possibly go wrong?"), player.getUniqueID());
                // Add a single tag for verification
                player.addTag("CanOriginateSiege");
            }
        }
    }

    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onChangeableProphesyExpire(PotionEvent.PotionExpiryEvent event){
        if (event.getPotionEffect() != null){

            if (event.getPotionEffect().getPotion() instanceof ChangeableProphesy){

                if (event.getEntityLiving() instanceof PlayerEntity){

                    if (!(event.getEntityLiving().getEntityWorld() instanceof ServerWorld)){
                        ServerWorld world = (ServerWorld)event.getEntityLiving().getEntityWorld();
                        PlayerEntity player = (PlayerEntity) event.getEntityLiving();

                        if (player.getTags().contains("CanOriginateSiege") && player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof BloodyCrest){

                            // Add Bloody Catastrophe and Blindness to the player
                            player.addPotionEffect(new EffectInstance(ModEffects.BLOODY_CATASTROPHE.get(), 2400));
                            player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 80));

                            // Add the player to UUID set for originator
                            Siege.originator.add(player.getUniqueID());

                            // Send the player a msg
                            player.sendMessage(new StringTextComponent(TextFormatting.RED + "FROM HERE ON, RESIST THE DEMISE..."), player.getUniqueID());

                            // Count the players nearby(32) as helpers, up to 3
                            int numberInvolved = 0;
                            List<? extends PlayerEntity> playerList = world.getPlayers();

                            for (PlayerEntity friend : playerList){
                                if (numberInvolved <= 2){

                                    if (player.getDistanceSq(friend) <= 1024.0D && friend!=player){
                                        // also send the helper a msg
                                        friend.sendMessage(new StringTextComponent(TextFormatting.RED + "FROM HERE ON, RESIST THE DEMISE..."), friend.getUniqueID());

                                        numberInvolved ++;
                                    }
                                }
                                else { break; }
                            }

                            /**
                            // Add up to 3 players near the originator to the involvers
                            for (PlayerEntity friend : playerList){
                                if (numberInvolved <= 2){

                                    if (player.getDistanceSq(friend) <= 900.0D){
                                        // also send the helper a msg
                                        friend.sendMessage(new StringTextComponent(TextFormatting.RED + "FROM HERE ON, RESIST THE DEMISE..."), friend.getUniqueID());
                                        // add the helper to the involvers
                                        Siege.involvers.add(friend.getUniqueID());
                                        // write a tag to mark who is the originator
                                        friend.addTag("TheOriginatorIs"+player.getUniqueID().toString());

                                        numberInvolved ++;
                                    }
                                }
                                else { break; }
                            }
                             */

                            // Spawn 12 + (8 * Additional Players) monsters as the first wave around the player
                            BlockPos playerPos = player.getPosition();
                            UUID uuid = player.getUniqueID();
                            for (int i = 1; i <= (12 + 8 * numberInvolved); i++){
                                BlockPos posRandom = Siege.findRandomSpawnPos(world, playerPos, 6);
                                Siege.doFirstWaveMonstersSpawn(world, posRandom, i, uuid);
                            }

                        }

                        // Remove the tag
                        player.removeTag("CanOriginateSiege");
                    }
                }
            }
        }
    }

    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onDefeatSiegeMonster(LivingDeathEvent event){

        if (event.getEntityLiving().getEntityWorld() instanceof ServerWorld && event.getEntityLiving().getTags().contains("SiegeMilestone")){

            // Case when the siege monster is slain by a player
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();

                // Case when the player is the originator
                if (Siege.originator.contains(player.getUniqueID())
                        && player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof BloodyCrest
                        && player.isPotionActive(ModEffects.BLOODY_CATASTROPHE.get()))
                {
                    ItemStack crest = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
                    ServerWorld world = (ServerWorld) event.getEntityLiving().getEntityWorld();

                    /**
                     // calculate how many helpers are there with the originator
                     int numberInvolved = 0;
                     for (UUID uuid : Siege.involvers){
                     if (player.getEntityWorld().getPlayerByUuid(uuid) != null){
                     if (player.getEntityWorld().getPlayerByUuid(uuid).getTags().contains("TheOriginatorIs"+player.getUniqueID().toString())){
                     numberInvolved ++;
                     }
                     }
                     }
                     */
                    // Count the players nearby(32) as helpers, up to 3
                    int numberInvolved = 0;
                    List<? extends PlayerEntity> playerList = player.getEntityWorld().getPlayers();

                    for (PlayerEntity friend : playerList){
                        if (numberInvolved <= 2){
                            if (player.getDistanceSq(friend) <= 1024.0D && friend!=player){
                                numberInvolved ++;
                            }
                        }
                        else { break; }
                    }

                    // spawn the next monster accordingly which is a milestone
                    player.getEntityWorld().addEntity(Siege.getEntityShouldSpawn(world,
                            Siege.getEntityTypeAccordingly(player.getItemStackFromSlot(EquipmentSlotType.OFFHAND)),
                            Siege.findRandomSpawnPos(player.getEntityWorld(), player.getPosition(), 4),
                            true, player.getUniqueID()));
                    // spawn additional monsters besides the milestone when the player has help
                    for (int i = 0; i < numberInvolved ; i++){
                        player.getEntityWorld().addEntity(Siege.getEntityShouldSpawn(world,
                                Siege.getEntityTypeAccordingly(player.getItemStackFromSlot(EquipmentSlotType.OFFHAND)),
                                Siege.findRandomSpawnPos(player.getEntityWorld(), player.getPosition(), 4),
                                false, player.getUniqueID()));
                    }

                    // reduce the damage of the crest by 1
                    crest.setDamage(crest.getDamage() + 1);
                    if (crest.getDamage() == 111){
                        crest.shrink(1);
                        player.sendBreakAnimation(EquipmentSlotType.OFFHAND);
                    }

                    // lengthen the player's Bloody Catastrophe by 8 seconds
                    player.addPotionEffect(new EffectInstance(ModEffects.BLOODY_CATASTROPHE.get(),
                            (160 + player.getActivePotionEffect(ModEffects.BLOODY_CATASTROPHE.get()).getDuration())));

                    // give the player some bonus
                    BloodyCrestManager.castSpell(player, event.getEntityLiving());
                    if (event.getEntityLiving() instanceof HeavyRubyEntity | event.getEntityLiving() instanceof FateSpinnerEntity | event.getEntityLiving() instanceof IsraDynameEntity){
                        player.heal(4.0F);
                    }
                }

                // Case when the player is not the originator
                else {
                    // try and find who is the originator
                    PlayerEntity originator = null;
                    for (UUID uuid : Siege.originator){
                        if (player.getEntityWorld().getPlayerByUuid(uuid) != null){
                            if (event.getEntityLiving().getTags().contains("TheOriginatorIs"+uuid.toString())){
                                originator = player.getEntityWorld().getPlayerByUuid(uuid);
                                break;
                            }
                        }
                    }

                    // do when the originator can be found
                    if (originator != null && originator.getEntityWorld() instanceof ServerWorld){
                        if (originator.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof BloodyCrest
                                && originator.isPotionActive(ModEffects.BLOODY_CATASTROPHE.get()))
                        {
                            ItemStack crest = originator.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
                            ServerWorld world = (ServerWorld) originator.getEntityWorld();

                            // Count the players near(32) the originator as helpers, up to 3
                            int numberInvolved = 0;
                            List<? extends PlayerEntity> playerList = originator.getEntityWorld().getPlayers();

                            for (PlayerEntity friend : playerList){
                                if (numberInvolved <= 2){
                                    if (originator.getDistanceSq(friend) <= 1024.0D && friend!=originator){
                                        numberInvolved ++;
                                    }
                                }
                                else { break; }
                            }

                            // spawn the next monster accordingly which is a milestone
                            originator.getEntityWorld().addEntity(Siege.getEntityShouldSpawn(world,
                                    Siege.getEntityTypeAccordingly(originator.getItemStackFromSlot(EquipmentSlotType.OFFHAND)),
                                    Siege.findRandomSpawnPos(originator.getEntityWorld(), originator.getPosition(), 4),
                                    true, originator.getUniqueID()));
                            // spawn additional monsters besides the milestone when the originator has help
                            for (int i = 0; i < numberInvolved ; i++){
                                originator.getEntityWorld().addEntity(Siege.getEntityShouldSpawn(world,
                                        Siege.getEntityTypeAccordingly(originator.getItemStackFromSlot(EquipmentSlotType.OFFHAND)),
                                        Siege.findRandomSpawnPos(originator.getEntityWorld(), originator.getPosition(), 4),
                                        false, originator.getUniqueID()));
                            }

                            // reduce the damage of the originator's crest by 1
                            crest.setDamage(crest.getDamage() + 1);
                            if (crest.getDamage() == 111){
                                crest.shrink(1);
                                originator.sendBreakAnimation(EquipmentSlotType.OFFHAND);
                            }

                        }
                    }
                    else {
                        // stop the siege
                    }

                }
            }


            // Case when the siege monster dies otherwise
            else {
                // try and find who is the originator
                PlayerEntity originator = null;
                for (UUID uuid : Siege.originator){
                    if (event.getEntityLiving().getEntityWorld().getPlayerByUuid(uuid) != null){
                        if (event.getEntityLiving().getTags().contains("TheOriginatorIs"+uuid.toString())){
                            originator = event.getEntityLiving().getEntityWorld().getPlayerByUuid(uuid);
                            break;
                        }
                    }
                }
                // do when the originator can be found
                if (originator != null && originator.getEntityWorld() instanceof ServerWorld){
                    if (originator.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof BloodyCrest
                            && originator.isPotionActive(ModEffects.BLOODY_CATASTROPHE.get()))
                    {
                        ItemStack crest = originator.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
                        ServerWorld world = (ServerWorld) originator.getEntityWorld();

                        // Count the players near(32) the originator as helpers, up to 3
                        int numberInvolved = 0;
                        List<? extends PlayerEntity> playerList = originator.getEntityWorld().getPlayers();

                        for (PlayerEntity friend : playerList){
                            if (numberInvolved <= 2){
                                if (originator.getDistanceSq(friend) <= 1024.0D && friend!=originator){
                                    numberInvolved ++;
                                }
                            }
                            else { break; }
                        }

                        // spawn the next monster accordingly which is a milestone
                        originator.getEntityWorld().addEntity(Siege.getEntityShouldSpawn(world,
                                Siege.getEntityTypeAccordingly(originator.getItemStackFromSlot(EquipmentSlotType.OFFHAND)),
                                Siege.findRandomSpawnPos(originator.getEntityWorld(), originator.getPosition(), 4),
                                true, originator.getUniqueID()));
                        // spawn additional monsters besides the milestone when the originator has help
                        for (int i = 0; i < numberInvolved ; i++){
                            originator.getEntityWorld().addEntity(Siege.getEntityShouldSpawn(world,
                                    Siege.getEntityTypeAccordingly(originator.getItemStackFromSlot(EquipmentSlotType.OFFHAND)),
                                    Siege.findRandomSpawnPos(originator.getEntityWorld(), originator.getPosition(), 4),
                                    false, originator.getUniqueID()));
                        }

                        // reduce the damage of the originator's crest by 1
                        crest.setDamage(crest.getDamage() + 1);
                        if (crest.getDamage() == 111){
                            crest.shrink(1);
                            originator.sendBreakAnimation(EquipmentSlotType.OFFHAND);
                        }

                    }
                }
                else {
                    // stop the siege
                }
            }
        }
    }

    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onSiegeOriginatorBeingAttacked(LivingHurtEvent event){
        if (event.getEntityLiving() instanceof PlayerEntity && !event.getEntityLiving().getEntityWorld().isRemote){
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            if (Siege.originator.contains(player.getUniqueID())
                    && player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof BloodyCrest
                    && player.isPotionActive(ModEffects.BLOODY_CATASTROPHE.get()))
            {
                // reduce the damage to 4 whenever the player receives any damage higher
                if (event.getAmount() > 4.0F){
                    event.setAmount(4.0F);
                }

                if (player.getHealth() - event.getAmount() < 1.0F){
                    // if fatal, scan through the player's inventory to see if a totem_of_undying can be found
                    for (int i = 0; i <= player.inventory.getSizeInventory(); i++){
                        if (player.inventory.getStackInSlot(i) != ItemStack.EMPTY){
                            if (player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING){
                                // save the player and use the totem
                                player.heal(20.0F);
                                player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 200, 2));
                                player.getEntityWorld().playSound(player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ITEM_TOTEM_USE, player.getSoundCategory(), 1.0F, 1.0F, false);
                                player.inventory.getStackInSlot(i).shrink(1);
                                event.setCanceled(true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onPlayerArrowHit(LivingHurtEvent event){

        if (!event.getEntityLiving().getEntityWorld().isRemote){

            if (event.getSource().isProjectile() && event.getSource().getTrueSource() instanceof ServerPlayerEntity){
                ServerPlayerEntity player = (ServerPlayerEntity) event.getSource().getTrueSource();

                if (player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof BowItem){
                    ItemStack bow = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
                    if (bow.getTag() != null){
                        if (bow.getTag().contains("rounds") && bow.getTag().getInt("rounds") < 99){
                            bow.getTag().putInt("rounds", bow.getTag().getInt("rounds")+1);
                        }
                    }
                }
                else if (player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof BowItem){
                    ItemStack bow = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
                    if (bow.getTag() != null){
                        if (bow.getTag().contains("rounds")&& bow.getTag().getInt("rounds") < 99){
                            bow.getTag().putInt("rounds", bow.getTag().getInt("rounds")+1);
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onPlayerAttackEntity(CriticalHitEvent event){

        if (event.getPlayer() instanceof ServerPlayerEntity){
            PlayerEntity player = event.getPlayer();

            // HASTE bonus for dual-blade
            if (player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof SwordItem
            && player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof SwordItem){
                player.addPotionEffect(new EffectInstance(Effects.HASTE, 160));
            }

            // increase the "counts" for swords with speciality
            if (player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof SwordItem
                    && player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getTag() != null
                    && player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getTag().contains("counts")){
                ItemStack sword = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
                if (sword.getTag().getInt("counts") < 37){
                    sword.getTag().putInt("counts", sword.getTag().getInt("counts")+1);
                }
            }
            else if (player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof SwordItem
                    && player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getTag() != null
                    && player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getTag().contains("counts")){
                ItemStack sword = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
                if (sword.getTag().getInt("counts") < 37){
                    sword.getTag().putInt("counts", sword.getTag().getInt("counts")+1);
                }
            }

            if (player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() == ModItems.MAINHAND_POISON_SWORD.get()){
                if (event.getTarget().isAlive()){
                    LivingEntity livingEntity = (LivingEntity) event.getTarget();
                    if (livingEntity.isPotionActive(Effects.POISON)){
                        event.setDamageModifier(1.5f);
                        event.setResult(Event.Result.ALLOW);
                    }
                }
            }
            else if (player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() == ModItems.MAINHAND_SLOWNESS_SWORD.get()){
                if (event.getTarget().isAlive()){
                    LivingEntity livingEntity = (LivingEntity) event.getTarget();
                    if (livingEntity.isPotionActive(Effects.SLOWNESS)){
                        event.setDamageModifier(1.5f);
                        event.setResult(Event.Result.ALLOW);
                    }
                }
            }
            else if (player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() == ModItems.MAINHAND_WEAKNESS_SWORD.get()){
                if (event.getTarget().isAlive()){
                    LivingEntity livingEntity = (LivingEntity) event.getTarget();
                    if (livingEntity.isPotionActive(Effects.WEAKNESS)){
                        event.setDamageModifier(1.5f);
                        event.setResult(Event.Result.ALLOW);
                    }
                }
            }
        }
    }

    /**
    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onShieldSpeciality(LivingHurtEvent event){

        if (event.getEntityLiving() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();

            if (player.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof KatzeShield){
                ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
                KatzeShield shield = (KatzeShield) stack.getItem();
                CompoundNBT tag = stack.getOrCreateTag();

                if (tag.getInt("katze_shield_charge") > shield.getSpellExpense()){
                    // Cast spell
                    if (shield.castSpell(player)){
                        // Prevent the damage
                        event.setAmount(0);
                        // update the charger tag
                        tag.putInt("katze_shield_charge", tag.getInt("katze_shield_charge") - shield.getSpellExpense());
                    }
                }
            }
        }
    }*/

    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event){
        if (event.getPlayer() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            if (!player.getPersistentData().contains(Katze.MOD_ID+"offersModificationDate")){
                TradeBoxHelper.writeOffers(player, new TradeBoxOffers(7));
            }
        }
    }

}
