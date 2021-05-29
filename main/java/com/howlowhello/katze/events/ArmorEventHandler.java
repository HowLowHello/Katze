package com.howlowhello.katze.events;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.init.ModEffects;
import com.howlowhello.katze.items.KatzeUpgradeItem;
import com.howlowhello.katze.network.ArmorParticleEffectPacket;
import com.howlowhello.katze.network.KatzePacketHandler;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Arrays;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Katze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorEventHandler {

    /**关于护甲升级与模式切换
     *
     * 凡是经过Katze中物品升级过的防具都会增加KatzeUpgrade的int[3]数组NBTtag
     * 容量设定为3是因为护甲最多可以同时包含3种升级
     * 所有的护甲相关事件会首先判断物品是否拥有此Tag
     * Tag包含数组的元素代表护甲进行了怎样的升级
     * 增加升级时修改数组中元素的值，修改后需要调用本类中的方法进行排序
     *
     * int[3]其中元素的值对应升级的品种：
     * 99 -> 无升级
     *
     * 1 -> 红色纹章
     * 2 -> 蓝色纹章
     * 3 -> 黄色纹章
     * 4 -> 绿色纹章
     * 5 -> 黑色纹章
     *
     * 21 -> 激情之红
     * 22 -> 寂静之蓝
     * 23 -> 深邃之黄
     * 24 -> 常青之绿
     * 25 -> 深渊之影
     *
     *
     * 除KatzeUpgrade以外，升级过的护甲还拥有以下NBTtag：
     * ActiveMode，其中的int值储存当前的增益模式
     * NextMode，其中的int值储存下一个增益模式
     * LastMode，其中的int值储存上一个增益模式
     * 单个升级的护甲只有ActiveMode会存储有效值，有两种升级的护甲增加NextMode存储有效值，只有三种升级的护甲会完全用上这三个Tag
     *
     * 这些Tag只会存储于头盔中
     * 头盔中的Tag将起到代表玩家整套套装状态的作用
     *
     * 玩家在给多升级护甲切换模式时，这些Tag中储存的值互相交换
     * 增益事件通过获取ActiveMode中的值来判断需要执行的代码
     *
     * int值对应的增益模式：
     * 1 -> 红色纹章
     * 2 -> 蓝色纹章
     * 3 -> 黄色纹章
     * 4 -> 绿色纹章
     * 5 -> 黑色纹章
     *
     * 21 -> 激情之红
     * 22 -> 寂静之蓝
     * 23 -> 深邃之黄
     * 24 -> 常青之绿
     * 25 -> 深渊之影
     *
     * */
    private static final TranslationTextComponent MESSAGE_0 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_0");
    private static final TranslationTextComponent MESSAGE_1 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_1");
    private static final TranslationTextComponent MESSAGE_2 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_2");
    private static final TranslationTextComponent MESSAGE_3 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_3");
    private static final TranslationTextComponent MESSAGE_4 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_4");
    private static final TranslationTextComponent MESSAGE_5 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_5");
    private static final TranslationTextComponent MESSAGE_21 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_21");
    private static final TranslationTextComponent MESSAGE_22 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_22");
    private static final TranslationTextComponent MESSAGE_23 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_23");
    private static final TranslationTextComponent MESSAGE_24 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_24");
    private static final TranslationTextComponent MESSAGE_25 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.message_25");
    private static final TranslationTextComponent TOOLTIP_1 = new TranslationTextComponent("event.katze_adventure.armor_katze_upgrade.tooltip_1");

    // ***当判断玩家头盔已有KatzeUpgrade标签后，再调用此方法验证该玩家全套装升级是否和头盔一致
    public static boolean playerHasValidKatzeArmorSet(PlayerEntity player){

        ItemStack head = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
        ItemStack chest = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        ItemStack legs = player.getItemStackFromSlot(EquipmentSlotType.LEGS);
        ItemStack feet = player.getItemStackFromSlot(EquipmentSlotType.FEET);

        if (chest.getTag() != null && legs.getTag() != null && feet.getTag() != null){

            if (chest.getTag().contains("KatzeUpgrade") && legs.getTag().contains("KatzeUpgrade") && feet.getTag().contains("KatzeUpgrade")){

                int[] upgrade = head.getTag().getIntArray("KatzeUpgrade");
                if (Arrays.equals(upgrade, chest.getTag().getIntArray("KatzeUpgrade")) && Arrays.equals(upgrade, legs.getTag().getIntArray("KatzeUpgrade")) && Arrays.equals(upgrade, feet.getTag().getIntArray("KatzeUpgrade"))){
                    return true;
                }
            }
        }
        return false;
    }


    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onTickingPlayer(TickEvent.PlayerTickEvent event){
        if (!event.player.getEntityWorld().isRemote){

            if (event.player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag() != null){

                if (event.player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().contains("KatzeUpgrade")){

                    if (ArmorEventHandler.playerHasValidKatzeArmorSet(event.player)){

                        // 根据头盔的ActiveMode执行增益操作
                        int activeMode = event.player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().getInt("ActiveMode");
                        switch (activeMode){
                            case 2: {
                                event.player.addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 210, 0));
                                break;
                            }
                            case 21: {
                                event.player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 210, 0));
                                break;
                            }
                            case 22: {
                                event.player.addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 210, 0));
                                event.player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 210, 0));
                                event.player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 210, 0));
                                break;
                            }
                            case 23: {
                                event.player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 210, 0));
                                break;
                            }
                            case 25: {
                                event.player.addPotionEffect(new EffectInstance(Effects.SPEED, 210, 0));
                                break;
                            }
                        }
                    }
                    // Particle Effects
                    Random random = new Random();

                    if (random.nextInt(200) < 2){

                        if (ArmorEventHandler.playerHasValidKatzeArmorSet(event.player)){

                            int activeMode = event.player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().getInt("ActiveMode");
                            double posX = event.player.getPosX();
                            double posY = event.player.getPosY();
                            double posZ = event.player.getPosZ();

                            KatzePacketHandler.INSTANCE.send(
                                    PacketDistributor.NEAR.with(
                                            () -> {
                                                return new PacketDistributor.TargetPoint(event.player.getPosX(), event.player.getPosY(), event.player.getPosZ(), 48.0D ,event.player.getEntityWorld().getDimensionKey());
                                            }
                                    ),
                                    new ArmorParticleEffectPacket(activeMode, posX, posY, posZ)
                            );
                        }
                    }
                }
            }
        }
    }

    /**当玩家同时装备4件带有激情之红升级的防具时，给与套装效果
     * 相关的塞姆利亚套或者下界合金套均能触发 */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPassionateRouge(AttackEntityEvent event){
        if (!event.getPlayer().getEntityWorld().isRemote){

            if (!event.getPlayer().isPotionActive(ModEffects.COOL_DOWN.get())){

                if (event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD).getTag() != null){

                    if (event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().contains("KatzeUpgrade")){

                        int activeMode = event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().getInt("ActiveMode");

                        if (activeMode == 1){
                            if (ArmorEventHandler.playerHasValidKatzeArmorSet(event.getPlayer())){
                                event.getPlayer().addPotionEffect(new EffectInstance(Effects.STRENGTH, 3000, 0));
                                event.getPlayer().addPotionEffect(new EffectInstance(ModEffects.COOL_DOWN.get(), 7200));
                            }
                        }
                        else if (activeMode == 21){
                            if (ArmorEventHandler.playerHasValidKatzeArmorSet(event.getPlayer())){
                                event.getPlayer().addPotionEffect(new EffectInstance(Effects.STRENGTH, 3000, 2));
                                event.getPlayer().addPotionEffect(new EffectInstance(ModEffects.COOL_DOWN.get(), 7200));
                            }
                        }
                    }
                }
            }
        }
    }


    /**当玩家同时装备4件带有深邃之黄升级的防具时，给与套装效果
     * 相关的塞姆利亚套或者下界合金套均能触发 */
    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onDeepOcherAndEverGreen(LivingHurtEvent event){
        // Check if the logic side is server && the entity is a player
        if (event.getEntityLiving() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            // Check if the player is alive
            if (player.isAlive()){

                if (!player.isPotionActive(ModEffects.COOL_DOWN.get())){

                    if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag() != null){

                        if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().contains("KatzeUpgrade")){

                            int activeMode = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().getInt("ActiveMode");

                            if (activeMode == 23){

                                if (ArmorEventHandler.playerHasValidKatzeArmorSet(player)){
                                    player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 3000, 4));
                                    player.addPotionEffect(new EffectInstance(ModEffects.COOL_DOWN.get(), 7200));
                                }
                            }
                            else if (activeMode == 24){

                                if (ArmorEventHandler.playerHasValidKatzeArmorSet(player)){
                                    player.addPotionEffect(new EffectInstance(Effects.SPEED, 3600, 0));
                                    player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 3000, 0));
                                    player.addPotionEffect(new EffectInstance(ModEffects.COOL_DOWN.get(), 7200));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**常青之绿效果一
     * 使用骨粉时回复饱食度和血量 */
    @SubscribeEvent (priority = EventPriority.NORMAL)
    public static void onEverGreenBonemeal(BonemealEvent event){

        // Check if BoneMeal can be applied to this Block
        if (event.getBlock().getBlock() instanceof IGrowable){
            IGrowable igrowable = (IGrowable)event.getBlock().getBlock();
            if (igrowable.canGrow(event.getWorld(), event.getPos(), event.getBlock(), event.getWorld().isRemote)) {

                if (event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD).getTag() != null) {

                    if (event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().contains("KatzeUpgrade")) {

                        int activeMode = event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().getInt("ActiveMode");

                        if (activeMode == 4 | activeMode == 24) {
                            if (ArmorEventHandler.playerHasValidKatzeArmorSet(event.getPlayer())) {
                                event.getPlayer().addPotionEffect(new EffectInstance(Effects.SATURATION, 2, 0));
                                event.getPlayer().heal(1);
                            }
                        }
                    }
                }
            }
        }
    }

    /**常青之绿效果二
     *
     * 以及深渊之影套装效果
     *
     * 跳跃时给予玩家状态效果加成，有冷却 */
    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onAbyssShadow(EnderTeleportEvent event){

        if (event.getEntityLiving() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();

            if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag() != null){

                if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().contains("KatzeUpgrade")){

                    int activeMode = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().getInt("ActiveMode");

                    if (activeMode == 5){
                        if (ArmorEventHandler.playerHasValidKatzeArmorSet(player)){
                            if (!player.isPotionActive(ModEffects.COOL_DOWN.get())) {
                                player.addPotionEffect(new EffectInstance(Effects.SPEED, 7200, 1));
                                player.addPotionEffect(new EffectInstance(ModEffects.COOL_DOWN.get(), 7200));
                            }
                            event.setAttackDamage(0);
                        }
                    }
                    else if (activeMode == 25){
                        if (ArmorEventHandler.playerHasValidKatzeArmorSet(player)){
                            if (!player.isPotionActive(ModEffects.COOL_DOWN.get())) {
                                player.addPotionEffect(new EffectInstance(Effects.SPEED, 7200, 2));
                                player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 7200, 0));
                                player.addPotionEffect(new EffectInstance(ModEffects.COOL_DOWN.get(), 7200));
                            }
                            event.setAttackDamage(0);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onAnvilRecipe(AnvilUpdateEvent event){
        if (event.getLeft().getItem() instanceof ArmorItem){
            ArmorItem armorItem = (ArmorItem) event.getLeft().getItem();
            if (event.getRight().getItem() instanceof KatzeUpgradeItem){
                KatzeUpgradeItem upgradeItem = (KatzeUpgradeItem) event.getRight().getItem();
                // 当前正在安装的升级序号
                int index = upgradeItem.index;
                // 升级前的护甲tag
                CompoundNBT tag = event.getLeft().getOrCreateTag();
                // 铁砧右侧输出护甲及输出护甲的tag
                ItemStack output = event.getLeft().copy();
                CompoundNBT tagOutput = output.getOrCreateTag();
                int[] arrOutput = tagOutput.getIntArray("KatzeUpgrade");

                // 当护甲已经安装过任意种类的升级时
                if (tag.contains("KatzeUpgrade")){
                    int[] array = tag.getIntArray("KatzeUpgrade");
                    int num = 3;
                    // 从头到尾遍历：当元素值为99时将变量num赋值为元素位置
                    // 默认数组元素为99时代表没有安装过升级
                    for (int i=0; i<array.length; i++){
                        if (array[i] == 99){
                            num = i;
                            break;
                        }
                    }
                    // 遍历结束，若num的值为3则说明护甲3个位置都有升级，为1则有1种升级，为2则有2种升级
                    if (num == 3){
                        // 不能再安装升级，直接返回
                        event.setCanceled(true);
                        return;
                    }
                    // 操作输出的物品的tag
                    else if (num == 1){
                        // 将数组的第二个元素赋值为当前正在安装的升级，并排序
                        arrOutput[1] = index;
                        Arrays.sort(arrOutput);
                        // 如果是头盔，则修改相应tag
                        if (armorItem.getEquipmentSlot() == EquipmentSlotType.HEAD){
                            tagOutput.putInt("ActiveMode", arrOutput[0]);
                            tagOutput.putInt("NextMode", arrOutput[1]);
                            tagOutput.putInt("LastMode", 0);
                        }
                    }
                    else if (num == 2){
                        // 将数组的第三个元素赋值为当前正在安装的升级，并排序
                        arrOutput[2] = index;
                        Arrays.sort(arrOutput);
                        // 如果是头盔，则修改相应tag
                        if (armorItem.getEquipmentSlot() == EquipmentSlotType.HEAD){
                            tagOutput.putInt("ActiveMode", arrOutput[0]);
                            tagOutput.putInt("NextMode", arrOutput[1]);
                            tagOutput.putInt("LastMode", arrOutput[2]);
                        }
                    }

                }
                // 当护甲没有安装过升级时
                else {
                    // 为输出物品添加需要的tag
                    tagOutput.putIntArray("KatzeUpgrade", new int[] {index,99,99});
                    if (armorItem.getEquipmentSlot() == EquipmentSlotType.HEAD){
                        tagOutput.putInt("ActiveMode", index);
                        tagOutput.putInt("NextMode", 0);
                        tagOutput.putInt("LastMode", 0);
                    }
                }
                // 输出升级后的护甲
                event.setOutput(output);
                event.setCost(1);
                event.setMaterialCost(1);
            }
        }
    }

    public static TranslationTextComponent getTooltip(int index){
        switch (index){
            case 1:return new TranslationTextComponent("item.katze_adventure.red_emblem");
            case 21:return new TranslationTextComponent("item.katze_adventure.passionate_rouge");
            case 2:return new TranslationTextComponent("item.katze_adventure.blue_emblem");
            case 22:return new TranslationTextComponent("item.katze_adventure.still_blue");
            case 3:return new TranslationTextComponent("item.katze_adventure.yellow_emblem");
            case 23:return new TranslationTextComponent("item.katze_adventure.deep_ocher");
            case 4:return new TranslationTextComponent("item.katze_adventure.green_emblem");
            case 24:return new TranslationTextComponent("item.katze_adventure.ever_green");
            case 5:return new TranslationTextComponent("item.katze_adventure.dark_emblem");
            case 25:return new TranslationTextComponent("item.katze_adventure.abyss_shadow");
        }
        return new TranslationTextComponent("");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onItemTooltip(ItemTooltipEvent event){
        CompoundNBT tag = event.getItemStack().getOrCreateTag();
        if (tag.contains("KatzeUpgrade")){
            if (event.getItemStack().getItem() instanceof ArmorItem){
                ArmorItem item = (ArmorItem) event.getItemStack().getItem();
                if (item.getEquipmentSlot() == EquipmentSlotType.HEAD){
                    event.getToolTip().add(ArmorEventHandler.getPlayerMessage(tag.getInt("ActiveMode")));
                }
            }
            int[] arr = tag.getIntArray("KatzeUpgrade");
            event.getToolTip().add(TOOLTIP_1);
            for (int i=0; i<arr.length; i++){
                if (arr[i] != 0){
                    event.getToolTip().add(ArmorEventHandler.getTooltip(arr[i]));
                }
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    public static void spawnParticle(int activeMode, double posX, double posY, double posZ){

        if (Minecraft.getInstance().world != null){

            ClientWorld world = Minecraft.getInstance().world;

            Random random = new Random();
            switch (activeMode){
                case 1:{
                    for(int i = 0; i < 3; ++i) {
                        double d2 = random.nextGaussian() * 0.02D;
                        double d3 = random.nextGaussian() * 0.02D;
                        double d4 = random.nextGaussian() * 0.02D;
                        double d5 = 0.5D - 3.0D;
                        double d6 = posX + d5 + random.nextDouble() * 3.0D * 2.0D;
                        double d7 = posY + 0.25D + random.nextDouble() * 1.0D;
                        double d8 = posZ + d5 + random.nextDouble() * 3.0D * 2.0D;

                        world.addParticle(ParticleTypes.FLAME, d6, d7, d8, d2, d3, d4);
                    }
                    break;
                }
                case 21:{
                    for(int i = 0; i < 5; ++i) {
                        double d2 = random.nextGaussian() * 0.02D;
                        double d3 = random.nextGaussian() * 0.02D;
                        double d4 = random.nextGaussian() * 0.02D;
                        double d5 = 0.5D - 3.0D;
                        double d6 = posX + d5 + random.nextDouble() * 3.0D * 2.0D;
                        double d7 = posY + 0.25D + random.nextDouble() * 1.0D;
                        double d8 = posZ + d5 + random.nextDouble() * 3.0D * 2.0D;

                        world.addParticle(ParticleTypes.FLAME, d6, d7, d8, d2, d3, d4);
                    }
                    break;
                }
                case 2:{
                    for(int i = 0; i < 2; ++i) {
                        double d2 = random.nextGaussian() * 0.02D;
                        double d3 = random.nextGaussian() * 0.02D;
                        double d4 = random.nextGaussian() * 0.02D;
                        double d5 = 0.5D - 3.0D;
                        double d6 = posX + d5 + random.nextDouble() * 3.0D * 2.0D;
                        double d7 = posY + 0.5D + random.nextDouble() * 1.0D;
                        double d8 = posZ + d5 + random.nextDouble() * 3.0D * 2.0D;

                        world.addParticle(ParticleTypes.NAUTILUS, d6, d7, d8, d2, d3, d4);
                    }
                    break;
                }
                case 22:{
                    for(int i = 0; i < 4; ++i) {
                        double d2 = random.nextGaussian() * 0.02D;
                        double d3 = random.nextGaussian() * 0.02D;
                        double d4 = random.nextGaussian() * 0.02D;
                        double d5 = 0.5D - 3.0D;
                        double d6 = posX + d5 + random.nextDouble() * 3.0D * 2.0D;
                        double d7 = posY + 0.5D + random.nextDouble() * 1.0D;
                        double d8 = posZ + d5 + random.nextDouble() * 3.0D * 2.0D;

                        world.addParticle(ParticleTypes.NAUTILUS, d6, d7, d8, d2, d3, d4);
                    }
                    break;
                }
                case 3:{
                    for(int i = 0; i < 2; ++i) {
                        double d2 = random.nextGaussian() * 0.02D;
                        double d3 = random.nextGaussian() * 0.02D;
                        double d4 = random.nextGaussian() * 0.02D;
                        double d5 = 0.5D - 3.0D;
                        double d6 = posX + d5 + random.nextDouble() * 3.0D * 2.0D;
                        double d7 = posY + 0.25D + random.nextDouble() * 1.0D;
                        double d8 = posZ + d5 + random.nextDouble() * 3.0D * 2.0D;

                        world.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()), d6, d7, d8, d2, d3, d4);
                    }
                    break;
                }
                case 23:{
                    for(int i = 0; i < 4; ++i) {
                        double d2 = random.nextGaussian() * 0.02D;
                        double d3 = random.nextGaussian() * 0.02D;
                        double d4 = random.nextGaussian() * 0.02D;
                        double d5 = 0.5D - 3.0D;
                        double d6 = posX + d5 + random.nextDouble() * 3.0D * 2.0D;
                        double d7 = posY + 0.25D + random.nextDouble() * 1.0D;
                        double d8 = posZ + d5 + random.nextDouble() * 3.0D * 2.0D;

                        world.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()), d6, d7, d8, d2, d3, d4);
                    }
                    break;
                }
                case 4:{
                    for(int i = 0; i < 3; ++i) {
                        double d2 = random.nextGaussian() * 0.02D;
                        double d3 = random.nextGaussian() * 0.02D;
                        double d4 = random.nextGaussian() * 0.02D;
                        double d5 = 0.5D - 3.0D;
                        double d6 = posX + d5 + random.nextDouble() * 3.0D * 2.0D;
                        double d7 = posY + 0.25D + random.nextDouble() * 1.0D;
                        double d8 = posZ + d5 + random.nextDouble() * 3.0D * 2.0D;

                        world.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                    }
                    break;
                }
                case 24:{
                    for(int i = 0; i < 5; ++i) {
                        double d2 = random.nextGaussian() * 0.02D;
                        double d3 = random.nextGaussian() * 0.02D;
                        double d4 = random.nextGaussian() * 0.02D;
                        double d5 = 0.5D - 3.0D;
                        double d6 = posX + d5 + random.nextDouble() * 3.0D * 2.0D;
                        double d7 = posY + 0.25D + random.nextDouble() * 1.0D;
                        double d8 = posZ + d5 + random.nextDouble() * 3.0D * 2.0D;

                        world.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                    }
                    break;
                }
                case 5:{
                    for(int i = 0; i < 2; ++i) {
                        double d2 = random.nextGaussian() * 0.02D;
                        double d3 = random.nextGaussian() * 0.02D;
                        double d4 = random.nextGaussian() * 0.02D;
                        double d5 = 0.5D - 3.0D;
                        double d6 = posX + d5 + random.nextDouble() * 3.0D * 2.0D;
                        double d7 = posY + 0.5D + random.nextDouble() * 1.0D;
                        double d8 = posZ + d5 + random.nextDouble() * 3.0D * 2.0D;

                        world.addParticle(ParticleTypes.PORTAL, d6, d7, d8, d2, d3, d4);
                    }
                    break;
                }
                case 25:{
                    for(int i = 0; i < 4; ++i) {
                        double d2 = random.nextGaussian() * 0.02D;
                        double d3 = random.nextGaussian() * 0.02D;
                        double d4 = random.nextGaussian() * 0.02D;
                        double d5 = 0.5D - 3.0D;
                        double d6 = posX + d5 + random.nextDouble() * 3.0D * 2.0D;
                        double d7 = posY + 0.5D + random.nextDouble() * 1.0D;
                        double d8 = posZ + d5 + random.nextDouble() * 3.0D * 2.0D;

                        world.addParticle(ParticleTypes.PORTAL, d6, d7, d8, d2, d3, d4);
                    }
                    break;
                }
            }
        }

    }

    /**
     * 客户端粒子效果代码，未采用
     * 目前依靠服务端来增加粒子
     *
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onSpawnParticle(TickEvent.PlayerTickEvent event){
        // Client Side
        if (event.player.getEntityWorld().isRemote){

            if (event.player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag() != null){

                if (event.player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().contains("KatzeUpgrade")){

                    Random random = new Random();

                    if (random.nextInt(200) < 2){

                        if (ArmorEventHandler.playerHasValidKatzeArmorSet(event.player)){

                            int activeMode = event.player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().getInt("ActiveMode");
                            switch (activeMode){
                                case 1:{
                                    for(int i = 0; i < 3; ++i) {
                                        double d2 = random.nextGaussian() * 0.02D;
                                        double d3 = random.nextGaussian() * 0.02D;
                                        double d4 = random.nextGaussian() * 0.02D;
                                        double d5 = 0.5D - 3.0D;
                                        double d6 = (double)event.player.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                                        double d7 = (double)event.player.getPosition().getY() + 0.25D + random.nextDouble() * 1.0D;
                                        double d8 = (double)event.player.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                                        event.player.getEntityWorld().addParticle(ParticleTypes.FLAME, d6, d7, d8, d2, d3, d4);
                                    }
                                    break;
                                }
                                case 21:{
                                    for(int i = 0; i < 5; ++i) {
                                        double d2 = random.nextGaussian() * 0.02D;
                                        double d3 = random.nextGaussian() * 0.02D;
                                        double d4 = random.nextGaussian() * 0.02D;
                                        double d5 = 0.5D - 3.0D;
                                        double d6 = (double)event.player.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                                        double d7 = (double)event.player.getPosition().getY() + 0.25D + random.nextDouble() * 1.0D;
                                        double d8 = (double)event.player.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                                        event.player.getEntityWorld().addParticle(ParticleTypes.FLAME, d6, d7, d8, d2, d3, d4);
                                    }
                                    break;
                                }
                                case 2:{
                                    for(int i = 0; i < 2; ++i) {
                                        double d2 = random.nextGaussian() * 0.02D;
                                        double d3 = random.nextGaussian() * 0.02D;
                                        double d4 = random.nextGaussian() * 0.02D;
                                        double d5 = 0.5D - 3.0D;
                                        double d6 = (double)event.player.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                                        double d7 = (double)event.player.getPosition().getY() + 0.5D + random.nextDouble() * 1.0D;
                                        double d8 = (double)event.player.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                                        event.player.getEntityWorld().addParticle(ParticleTypes.NAUTILUS, d6, d7, d8, d2, d3, d4);
                                    }
                                    break;
                                }
                                case 22:{
                                    for(int i = 0; i < 4; ++i) {
                                        double d2 = random.nextGaussian() * 0.02D;
                                        double d3 = random.nextGaussian() * 0.02D;
                                        double d4 = random.nextGaussian() * 0.02D;
                                        double d5 = 0.5D - 3.0D;
                                        double d6 = (double)event.player.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                                        double d7 = (double)event.player.getPosition().getY() + 0.5D + random.nextDouble() * 1.0D;
                                        double d8 = (double)event.player.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                                        event.player.getEntityWorld().addParticle(ParticleTypes.NAUTILUS, d6, d7, d8, d2, d3, d4);
                                    }
                                    break;
                                }
                                case 3:{
                                    for(int i = 0; i < 2; ++i) {
                                        double d2 = random.nextGaussian() * 0.02D;
                                        double d3 = random.nextGaussian() * 0.02D;
                                        double d4 = random.nextGaussian() * 0.02D;
                                        double d5 = 0.5D - 3.0D;
                                        double d6 = (double)event.player.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                                        double d7 = (double)event.player.getPosition().getY() + 0.25D + random.nextDouble() * 1.0D;
                                        double d8 = (double)event.player.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                                        event.player.getEntityWorld().addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()), d6, d7, d8, d2, d3, d4);
                                    }
                                    break;
                                }
                                case 23:{
                                    for(int i = 0; i < 4; ++i) {
                                        double d2 = random.nextGaussian() * 0.02D;
                                        double d3 = random.nextGaussian() * 0.02D;
                                        double d4 = random.nextGaussian() * 0.02D;
                                        double d5 = 0.5D - 3.0D;
                                        double d6 = (double)event.player.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                                        double d7 = (double)event.player.getPosition().getY() + 0.25D + random.nextDouble() * 1.0D;
                                        double d8 = (double)event.player.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                                        event.player.getEntityWorld().addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()), d6, d7, d8, d2, d3, d4);
                                    }
                                    break;
                                }
                                case 4:{
                                    for(int i = 0; i < 3; ++i) {
                                        double d2 = random.nextGaussian() * 0.02D;
                                        double d3 = random.nextGaussian() * 0.02D;
                                        double d4 = random.nextGaussian() * 0.02D;
                                        double d5 = 0.5D - 3.0D;
                                        double d6 = (double)event.player.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                                        double d7 = (double)event.player.getPosition().getY() + 0.25D + random.nextDouble() * 1.0D;
                                        double d8 = (double)event.player.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                                        event.player.getEntityWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                                    }
                                    break;
                                }
                                case 24:{
                                    for(int i = 0; i < 5; ++i) {
                                        double d2 = random.nextGaussian() * 0.02D;
                                        double d3 = random.nextGaussian() * 0.02D;
                                        double d4 = random.nextGaussian() * 0.02D;
                                        double d5 = 0.5D - 3.0D;
                                        double d6 = (double)event.player.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                                        double d7 = (double)event.player.getPosition().getY() + 0.25D + random.nextDouble() * 1.0D;
                                        double d8 = (double)event.player.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                                        event.player.getEntityWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                                    }
                                    break;
                                }
                                case 5:{
                                    for(int i = 0; i < 2; ++i) {
                                        double d2 = random.nextGaussian() * 0.02D;
                                        double d3 = random.nextGaussian() * 0.02D;
                                        double d4 = random.nextGaussian() * 0.02D;
                                        double d5 = 0.5D - 3.0D;
                                        double d6 = (double)event.player.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                                        double d7 = (double)event.player.getPosition().getY() + 0.5D + random.nextDouble() * 1.0D;
                                        double d8 = (double)event.player.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                                        event.player.getEntityWorld().addParticle(ParticleTypes.PORTAL, d6, d7, d8, d2, d3, d4);
                                    }
                                    break;
                                }
                                case 25:{
                                    for(int i = 0; i < 4; ++i) {
                                        double d2 = random.nextGaussian() * 0.02D;
                                        double d3 = random.nextGaussian() * 0.02D;
                                        double d4 = random.nextGaussian() * 0.02D;
                                        double d5 = 0.5D - 3.0D;
                                        double d6 = (double)event.player.getPosition().getX() + d5 + random.nextDouble() * 3.0D * 2.0D;
                                        double d7 = (double)event.player.getPosition().getY() + 0.5D + random.nextDouble() * 1.0D;
                                        double d8 = (double)event.player.getPosition().getZ() + d5 + random.nextDouble() * 3.0D * 2.0D;

                                        event.player.getEntityWorld().addParticle(ParticleTypes.PORTAL, d6, d7, d8, d2, d3, d4);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
     */

    @OnlyIn(Dist.CLIENT)
    public static TranslationTextComponent getPlayerMessage(int i){
        switch (i){
            case 0:{
                return MESSAGE_0;
            }
            case 1:{
                return MESSAGE_1;
            }
            case 2:{
                return MESSAGE_2;
            }
            case 3:{
                return MESSAGE_3;
            }
            case 4:{
                return MESSAGE_4;
            }
            case 5:{
                return MESSAGE_5;
            }
            case 21:{
                return MESSAGE_21;
            }
            case 22:{
                return MESSAGE_22;
            }
            case 23:{
                return MESSAGE_23;
            }
            case 24:{
                return MESSAGE_24;
            }
            case 25:{
                return MESSAGE_25;
            }
        }
        return MESSAGE_0;
    }


}
