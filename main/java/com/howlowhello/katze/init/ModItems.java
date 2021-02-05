package com.howlowhello.katze.init;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.items.BloodyCrest;
import com.howlowhello.katze.items.combat.*;
import com.howlowhello.katze.items.TeleportStaff;
import com.howlowhello.katze.items.foods.*;
import com.howlowhello.katze.items.armors.*;
import com.howlowhello.katze.util.enums.ModArmorMaterial;
import com.howlowhello.katze.util.enums.ModItemTier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    //创建一个物品注册类的对象
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Katze.MOD_ID);

    /**实例化物品对象，并使用DeferredRegister类进行注册
     *利用Lambda表达式实例化对象，并作为Register方法的实参
     *同时设置物品ID以及创造模式物品栏
     */
    // Items
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<PoisonApple> POISON_APPLE = ITEMS.register("poison_apple", PoisonApple::new);
    public static final RegistryObject<BloodyCrest> BLOODY_CREST = ITEMS.register("bloody_crest", BloodyCrest::new);
    public static final RegistryObject<TeleportStaff> TELEPORT_STAFF = ITEMS.register("teleport_staff", TeleportStaff::new);
    public static final RegistryObject<KaleidoBow> KALEIDO_BOW = ITEMS.register("kaleido_bow", KaleidoBow::new);
    public static final RegistryObject<AnimisticBow> ANIMISTIC_BOW = ITEMS.register("animistic_bow", AnimisticBow::new);
    public static final RegistryObject<LightningBow> LIGHTNING_BOW = ITEMS.register("lightning_bow", LightningBow::new);
    public static final RegistryObject<ThorBow> THOR_BOW = ITEMS.register("thor_bow", ThorBow::new);
    public static final RegistryObject<SolarStormBow> SOLAR_STORM_BOW = ITEMS.register("solar_storm_bow", SolarStormBow::new);
    public static final RegistryObject<BinaryBow> BINARY_BOW = ITEMS.register("binary_bow", BinaryBow::new);

    public static final RegistryObject<InfernalSword> INFERNAL_SWORD = ITEMS.register("infernal_sword",
            () -> new InfernalSword(ItemTier.GOLD, 2, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<LegendaryWoodenSword> LEGENDARY_WOODEN_SWORD = ITEMS.register("legendary_wooden_sword",
            () -> new LegendaryWoodenSword(ItemTier.WOOD, 2, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<RefinedKatana> REFINED_KATANA = ITEMS.register("refined_katana",
            () -> new RefinedKatana(ItemTier.IRON, 2, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<EnderSword> ENDER_SWORD = ITEMS.register("ender_sword",
            () -> new EnderSword(ModItemTier.ZEMURIAN, -1, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<LightningSword> LIGHTNING_SWORD = ITEMS.register("lightning_sword",
            () -> new LightningSword(ModItemTier.ZEMURIAN, -1, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<OffhandWeaknessSword> OFFHAND_WEAKNESS_SWORD = ITEMS.register("offhand_weakness_sword",
            () -> new OffhandWeaknessSword(ModItemTier.ZEMURIAN, -1, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<OffhandPoisonSword> OFFHAND_POISON_SWORD = ITEMS.register("offhand_poison_sword",
            () -> new OffhandPoisonSword(ModItemTier.ZEMURIAN, -1, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<OffhandSlownessSword> OFFHAND_SLOWNESS_SWORD = ITEMS.register("offhand_slowness_sword",
            () -> new OffhandSlownessSword(ModItemTier.ZEMURIAN, -1, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<SwordItem> MAINHAND_WEAKNESS_SWORD = ITEMS.register("mainhand_weakness_sword",
            () -> new SwordItem(ModItemTier.ZEMURIAN, 2, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<SwordItem> MAINHAND_POISON_SWORD = ITEMS.register("mainhand_poison_sword",
            () -> new SwordItem(ModItemTier.ZEMURIAN, 2, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<SwordItem> MAINHAND_SLOWNESS_SWORD = ITEMS.register("mainhand_slowness_sword",
            () -> new SwordItem(ModItemTier.ZEMURIAN, 2, -2.4F, new Item.Properties().group(Katze.TAB)));

    // Extra Items
    public static final RegistryObject<RestorativeBerries> RESTORATIVE_BERRIES = ITEMS.register("restorative_berries", RestorativeBerries::new);
    public static final RegistryObject<SearingBerries> SEARING_BERRIES = ITEMS.register("searing_berries", SearingBerries::new);
    public static final RegistryObject<MetallizationBerries> METALLIZATION_BERRIES = ITEMS.register("metallization_berries", MetallizationBerries::new);
    public static final RegistryObject<StealthyBerries> STEALTHY_BERRIES = ITEMS.register("stealthy_berries", StealthyBerries::new);
    public static final RegistryObject<TaurineBerries> TAURINE_BERRIES = ITEMS.register("taurine_berries", TaurineBerries::new);
    public static final RegistryObject<KachitoritaiBerries> KACHITORITAI_BERRIES = ITEMS.register("kachitoritai_berries", KachitoritaiBerries::new);
    public static final RegistryObject<ForteBerries> FORTE_BERRIES = ITEMS.register("forte_berries", ForteBerries::new);
    public static final RegistryObject<CelesteBerries> CELESTE_BERRIES = ITEMS.register("celeste_berries", CelesteBerries::new);
    public static final RegistryObject<EclipseBerries> ECLIPSE_BERRIES = ITEMS.register("eclipse_berries", EclipseBerries::new);
    public static final RegistryObject<VitaBerries> VITA_BERRIES = ITEMS.register("vita_berries", VitaBerries::new);
    public static final RegistryObject<DynamiteBerries> DYNAMITE_BERRIES = ITEMS.register("dynamite_berries", DynamiteBerries::new);
    public static final RegistryObject<CandyCane> CANDY_CANE = ITEMS.register("candy_cane", CandyCane::new);
    public static final RegistryObject<LargeCandyCane> LARGE_CANDY_CANE = ITEMS.register("large_candy_cane", LargeCandyCane::new);
    // Adventure Items
    public static final RegistryObject<Item> SEPITH = ITEMS.register("sepith", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> U_MATERIAL = ITEMS.register("u_material", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> ZEMURIAN_ORE = ITEMS.register("zemurian_ore", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> ZEMURIAN_DUST = ITEMS.register("zemurian_dust", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> ZEMURIAN_INGOT = ITEMS.register("zemurian_ingot", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> RED_EMBLEM = ITEMS.register("red_emblem", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> PASSIONATE_ROUGE = ITEMS.register("passionate_rouge", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> YELLOW_EMBLEM = ITEMS.register("yellow_emblem", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> DEEP_OCHER = ITEMS.register("deep_ocher", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> BLUE_EMBLEM = ITEMS.register("blue_emblem", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> STILL_BLUE = ITEMS.register("still_blue", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> GREEN_EMBLEM = ITEMS.register("green_emblem", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> EVER_GREEN = ITEMS.register("ever_green", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> DARK_EMBLEM = ITEMS.register("dark_emblem", () -> new Item(new Item.Properties().group(Katze.TAB)));
    public static final RegistryObject<Item> ABYSS_SHADOW = ITEMS.register("abyss_shadow", () -> new Item(new Item.Properties().group(Katze.TAB)));

    // Block Items
    /**需要新实参BlockItem类对象，作为注册的对应方块    */
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block",
            () -> new BlockItem(ModBlocks.RUBY_BLOCK.get(), new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<Item> RUBY_ORE_ITEM = ITEMS.register("ruby_ore",
            () -> new BlockItem(ModBlocks.RUBY_ORE.get(), new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<Item> OVEN_ITEM = ITEMS.register("oven",
            () -> new BlockItem(ModBlocks.OVEN.get(), new Item.Properties().group(Katze.TAB)));
    // Extra Block Items
    public static final RegistryObject<Item> SEPITH_BLOCK_ITEM = ITEMS.register("sepith_block",
            () -> new BlockItem(ModBlocks.SEPITH_BLOCK.get(), new Item.Properties().group(Katze.TAB)));

    // Tools
    /**需要各种工具类的对象作为实参
     * 其构造方法调用ModArmorMaterial中的材料作为实参，作为工具基础等级 */
    public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register("ruby_sword",
            () -> new SwordItem(ModItemTier.RUBY, 2, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe",
            () -> new PickaxeItem(ModItemTier.RUBY, 0, -2.8F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<ShovelItem> RUBY_SHOVEL = ITEMS.register("ruby_shovel",
            () -> new ShovelItem(ModItemTier.RUBY, 0.5F, -3.0F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<AxeItem> RUBY_AXE = ITEMS.register("ruby_axe",
            () -> new AxeItem(ModItemTier.RUBY, 5, -3.1F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<HoeItem> RUBY_HOE = ITEMS.register("ruby_hoe",
            () -> new HoeItem(ModItemTier.RUBY,-3, -1.0F, new Item.Properties().group(Katze.TAB)));
    // Zemurian Tools
    public static final RegistryObject<SwordItem> ZEMURIAN_SWORD = ITEMS.register("zemurian_sword",
            () -> new SwordItem(ModItemTier.ZEMURIAN, 2, -2.4F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<PickaxeItem> ZEMURIAN_PICKAXE = ITEMS.register("zemurian_pickaxe",
            () -> new PickaxeItem(ModItemTier.ZEMURIAN, 0, -2.8F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<ShovelItem> ZEMURIAN_SHOVEL = ITEMS.register("zemurian_shovel",
            () -> new ShovelItem(ModItemTier.ZEMURIAN, 0.5F, -3.0F, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<AxeItem> ZEMURIAN_AXE = ITEMS.register("zemurian_axe",
            () -> new AxeItem(ModItemTier.ZEMURIAN, 5, -3.1F, new Item.Properties().group(Katze.TAB)));

    // Armor
    /**需要ArmorItem类对象作为实参
     * 其构造方法调用ModArmorMaterial中的材料作为实参，作为工具基础等级 */
    public static final RegistryObject<ArmorItem> RUBY_HELMET = ITEMS.register("ruby_helmet",
            () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<ArmorItem> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate",
            () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<ArmorItem> RUBY_LEGGINGS = ITEMS.register("ruby_leggings",
            () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<ArmorItem> RUBY_BOOTS = ITEMS.register("ruby_boots",
            () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));

    // Red Sepith Armor
    public static final RegistryObject<RedSepith> RED_SEPITH_HELMET = ITEMS.register("red_sepith_helmet",
            () -> new RedSepith(ModArmorMaterial.RED_SEPITH, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<RedSepith> RED_SEPITH_CHESTPLATE = ITEMS.register("red_sepith_chestplate",
            () -> new RedSepith(ModArmorMaterial.RED_SEPITH, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<RedSepith> RED_SEPITH_LEGGINGS = ITEMS.register("red_sepith_leggings",
            () -> new RedSepith(ModArmorMaterial.RED_SEPITH, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<RedSepith> RED_SEPITH_BOOTS = ITEMS.register("red_sepith_boots",
            () -> new RedSepith(ModArmorMaterial.RED_SEPITH, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Blue Sepith Armor
    public static final RegistryObject<BlueSepith> BLUE_SEPITH_HELMET = ITEMS.register("blue_sepith_helmet",
            () -> new BlueSepith(ModArmorMaterial.BLUE_SEPITH, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<BlueSepith> BLUE_SEPITH_CHESTPLATE = ITEMS.register("blue_sepith_chestplate",
            () -> new BlueSepith(ModArmorMaterial.BLUE_SEPITH, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<BlueSepith> BLUE_SEPITH_LEGGINGS = ITEMS.register("blue_sepith_leggings",
            () -> new BlueSepith(ModArmorMaterial.BLUE_SEPITH, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<BlueSepith> BLUE_SEPITH_BOOTS = ITEMS.register("blue_sepith_boots",
            () -> new BlueSepith(ModArmorMaterial.BLUE_SEPITH, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Yellow Sepith Armor
    public static final RegistryObject<YellowSepith> YELLOW_SEPITH_HELMET = ITEMS.register("yellow_sepith_helmet",
            () -> new YellowSepith(ModArmorMaterial.YELLOW_SEPITH, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<YellowSepith> YELLOW_SEPITH_CHESTPLATE = ITEMS.register("yellow_sepith_chestplate",
            () -> new YellowSepith(ModArmorMaterial.YELLOW_SEPITH, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<YellowSepith> YELLOW_SEPITH_LEGGINGS = ITEMS.register("yellow_sepith_leggings",
            () -> new YellowSepith(ModArmorMaterial.YELLOW_SEPITH, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<YellowSepith> YELLOW_SEPITH_BOOTS = ITEMS.register("yellow_sepith_boots",
            () -> new YellowSepith(ModArmorMaterial.YELLOW_SEPITH, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Green Sepith Armor
    public static final RegistryObject<GreenSepith> GREEN_SEPITH_HELMET = ITEMS.register("green_sepith_helmet",
            () -> new GreenSepith(ModArmorMaterial.GREEN_SEPITH, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<GreenSepith> GREEN_SEPITH_CHESTPLATE = ITEMS.register("green_sepith_chestplate",
            () -> new GreenSepith(ModArmorMaterial.GREEN_SEPITH, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<GreenSepith> GREEN_SEPITH_LEGGINGS = ITEMS.register("green_sepith_leggings",
            () -> new GreenSepith(ModArmorMaterial.GREEN_SEPITH, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<GreenSepith> GREEN_SEPITH_BOOTS = ITEMS.register("green_sepith_boots",
            () -> new GreenSepith(ModArmorMaterial.GREEN_SEPITH, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Dark Sepith Armor
    public static final RegistryObject<DarkSepith> DARK_SEPITH_HELMET = ITEMS.register("dark_sepith_helmet",
            () -> new DarkSepith(ModArmorMaterial.DARK_SEPITH, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<DarkSepith> DARK_SEPITH_CHESTPLATE = ITEMS.register("dark_sepith_chestplate",
            () -> new DarkSepith(ModArmorMaterial.DARK_SEPITH, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<DarkSepith> DARK_SEPITH_LEGGINGS = ITEMS.register("dark_sepith_leggings",
            () -> new DarkSepith(ModArmorMaterial.DARK_SEPITH, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<DarkSepith> DARK_SEPITH_BOOTS = ITEMS.register("dark_sepith_boots",
            () -> new DarkSepith(ModArmorMaterial.DARK_SEPITH, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Zemurian Armor
    public static final RegistryObject<ArmorItem> ZEMURIAN_HELMET = ITEMS.register("zemurian_helmet",
            () -> new ArmorItem(ModArmorMaterial.ZEMURIAN, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<ArmorItem> ZEMURIAN_CHESTPLATE = ITEMS.register("zemurian_chestplate",
            () -> new ArmorItem(ModArmorMaterial.ZEMURIAN, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<ArmorItem> ZEMURIAN_LEGGINGS = ITEMS.register("zemurian_leggings",
            () -> new ArmorItem(ModArmorMaterial.ZEMURIAN, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<ArmorItem> ZEMURIAN_BOOTS = ITEMS.register("zemurian_boots",
            () -> new ArmorItem(ModArmorMaterial.ZEMURIAN, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Red Zemurian Armor
    public static final RegistryObject<RedZemurian> RED_ZEMURIAN_HELMET = ITEMS.register("red_zemurian_helmet",
            () -> new RedZemurian(ModArmorMaterial.RED_ZEMURIAN, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<RedZemurian> RED_ZEMURIAN_CHESTPLATE = ITEMS.register("red_zemurian_chestplate",
            () -> new RedZemurian(ModArmorMaterial.RED_ZEMURIAN, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<RedZemurian> RED_ZEMURIAN_LEGGINGS = ITEMS.register("red_zemurian_leggings",
            () -> new RedZemurian(ModArmorMaterial.RED_ZEMURIAN, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<RedZemurian> RED_ZEMURIAN_BOOTS = ITEMS.register("red_zemurian_boots",
            () -> new RedZemurian(ModArmorMaterial.RED_ZEMURIAN, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Red Netherite Armor
    public static final RegistryObject<RedNetherite> RED_NETHERITE_HELMET = ITEMS.register("red_netherite_helmet",
            () -> new RedNetherite(ModArmorMaterial.RED_NETHERITE, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<RedNetherite> RED_NETHERITE_CHESTPLATE = ITEMS.register("red_netherite_chestplate",
            () -> new RedNetherite(ModArmorMaterial.RED_NETHERITE, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<RedNetherite> RED_NETHERITE_LEGGINGS = ITEMS.register("red_netherite_leggings",
            () -> new RedNetherite(ModArmorMaterial.RED_NETHERITE, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<RedNetherite> RED_NETHERITE_BOOTS = ITEMS.register("red_netherite_boots",
            () -> new RedNetherite(ModArmorMaterial.RED_NETHERITE, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Blue Zemurian Armor
    public static final RegistryObject<BlueZemurian> BLUE_ZEMURIAN_HELMET = ITEMS.register("blue_zemurian_helmet",
            () -> new BlueZemurian(ModArmorMaterial.BLUE_ZEMURIAN, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<BlueZemurian> BLUE_ZEMURIAN_CHESTPLATE = ITEMS.register("blue_zemurian_chestplate",
            () -> new BlueZemurian(ModArmorMaterial.BLUE_ZEMURIAN, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<BlueZemurian> BLUE_ZEMURIAN_LEGGINGS = ITEMS.register("blue_zemurian_leggings",
            () -> new BlueZemurian(ModArmorMaterial.BLUE_ZEMURIAN, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<BlueZemurian> BLUE_ZEMURIAN_BOOTS = ITEMS.register("blue_zemurian_boots",
            () -> new BlueZemurian(ModArmorMaterial.BLUE_ZEMURIAN, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Blue Netherite Armor
    public static final RegistryObject<BlueNetherite> BLUE_NETHERITE_HELMET = ITEMS.register("blue_netherite_helmet",
            () -> new BlueNetherite(ModArmorMaterial.BLUE_NETHERITE, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<BlueNetherite> BLUE_NETHERITE_CHESTPLATE = ITEMS.register("blue_netherite_chestplate",
            () -> new BlueNetherite(ModArmorMaterial.BLUE_NETHERITE, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<BlueNetherite> BLUE_NETHERITE_LEGGINGS = ITEMS.register("blue_netherite_leggings",
            () -> new BlueNetherite(ModArmorMaterial.BLUE_NETHERITE, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<BlueNetherite> BLUE_NETHERITE_BOOTS = ITEMS.register("blue_netherite_boots",
            () -> new BlueNetherite(ModArmorMaterial.BLUE_NETHERITE, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Yellow Zemurian Armor
    public static final RegistryObject<YellowZemurian> YELLOW_ZEMURIAN_HELMET = ITEMS.register("yellow_zemurian_helmet",
            () -> new YellowZemurian(ModArmorMaterial.YELLOW_ZEMURIAN, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<YellowZemurian> YELLOW_ZEMURIAN_CHESTPLATE = ITEMS.register("yellow_zemurian_chestplate",
            () -> new YellowZemurian(ModArmorMaterial.YELLOW_ZEMURIAN, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<YellowZemurian> YELLOW_ZEMURIAN_LEGGINGS = ITEMS.register("yellow_zemurian_leggings",
            () -> new YellowZemurian(ModArmorMaterial.YELLOW_ZEMURIAN, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<YellowZemurian> YELLOW_ZEMURIAN_BOOTS = ITEMS.register("yellow_zemurian_boots",
            () -> new YellowZemurian(ModArmorMaterial.YELLOW_ZEMURIAN, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Yellow Netherite Armor
    public static final RegistryObject<YellowNetherite> YELLOW_NETHERITE_HELMET = ITEMS.register("yellow_netherite_helmet",
            () -> new YellowNetherite(ModArmorMaterial.YELLOW_NETHERITE, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<YellowNetherite> YELLOW_NETHERITE_CHESTPLATE = ITEMS.register("yellow_netherite_chestplate",
            () -> new YellowNetherite(ModArmorMaterial.YELLOW_NETHERITE, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<YellowNetherite> YELLOW_NETHERITE_LEGGINGS = ITEMS.register("yellow_netherite_leggings",
            () -> new YellowNetherite(ModArmorMaterial.YELLOW_NETHERITE, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<YellowNetherite> YELLOW_NETHERITE_BOOTS = ITEMS.register("yellow_netherite_boots",
            () -> new YellowNetherite(ModArmorMaterial.YELLOW_NETHERITE, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Green Zemurian Armor
    public static final RegistryObject<GreenZemurian> GREEN_ZEMURIAN_HELMET = ITEMS.register("green_zemurian_helmet",
            () -> new GreenZemurian(ModArmorMaterial.GREEN_ZEMURIAN, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<GreenZemurian> GREEN_ZEMURIAN_CHESTPLATE = ITEMS.register("green_zemurian_chestplate",
            () -> new GreenZemurian(ModArmorMaterial.GREEN_ZEMURIAN, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<GreenZemurian> GREEN_ZEMURIAN_LEGGINGS = ITEMS.register("green_zemurian_leggings",
            () -> new GreenZemurian(ModArmorMaterial.GREEN_ZEMURIAN, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<GreenZemurian> GREEN_ZEMURIAN_BOOTS = ITEMS.register("green_zemurian_boots",
            () -> new GreenZemurian(ModArmorMaterial.GREEN_ZEMURIAN, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Green Netherite Armor
    public static final RegistryObject<GreenNetherite> GREEN_NETHERITE_HELMET = ITEMS.register("green_netherite_helmet",
            () -> new GreenNetherite(ModArmorMaterial.GREEN_NETHERITE, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<GreenNetherite> GREEN_NETHERITE_CHESTPLATE = ITEMS.register("green_netherite_chestplate",
            () -> new GreenNetherite(ModArmorMaterial.GREEN_NETHERITE, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<GreenNetherite> GREEN_NETHERITE_LEGGINGS = ITEMS.register("green_netherite_leggings",
            () -> new GreenNetherite(ModArmorMaterial.GREEN_NETHERITE, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<GreenNetherite> GREEN_NETHERITE_BOOTS = ITEMS.register("green_netherite_boots",
            () -> new GreenNetherite(ModArmorMaterial.GREEN_NETHERITE, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Dark Zemurian Armor
    public static final RegistryObject<DarkZemurian> DARK_ZEMURIAN_HELMET = ITEMS.register("dark_zemurian_helmet",
            () -> new DarkZemurian(ModArmorMaterial.DARK_ZEMURIAN, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<DarkZemurian> DARK_ZEMURIAN_CHESTPLATE = ITEMS.register("dark_zemurian_chestplate",
            () -> new DarkZemurian(ModArmorMaterial.DARK_ZEMURIAN, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<DarkZemurian> DARK_ZEMURIAN_LEGGINGS = ITEMS.register("dark_zemurian_leggings",
            () -> new DarkZemurian(ModArmorMaterial.DARK_ZEMURIAN, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<DarkZemurian> DARK_ZEMURIAN_BOOTS = ITEMS.register("dark_zemurian_boots",
            () -> new DarkZemurian(ModArmorMaterial.DARK_ZEMURIAN, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
    // Dark Netherite Armor
    public static final RegistryObject<DarkNetherite> DARK_NETHERITE_HELMET = ITEMS.register("dark_netherite_helmet",
            () -> new DarkNetherite(ModArmorMaterial.DARK_NETHERITE, EquipmentSlotType.HEAD, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<DarkNetherite> DARK_NETHERITE_CHESTPLATE = ITEMS.register("dark_netherite_chestplate",
            () -> new DarkNetherite(ModArmorMaterial.DARK_NETHERITE, EquipmentSlotType.CHEST, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<DarkNetherite> DARK_NETHERITE_LEGGINGS = ITEMS.register("dark_netherite_leggings",
            () -> new DarkNetherite(ModArmorMaterial.DARK_NETHERITE, EquipmentSlotType.LEGS, new Item.Properties().group(Katze.TAB)));

    public static final RegistryObject<DarkNetherite> DARK_NETHERITE_BOOTS = ITEMS.register("dark_netherite_boots",
            () -> new DarkNetherite(ModArmorMaterial.DARK_NETHERITE, EquipmentSlotType.FEET, new Item.Properties().group(Katze.TAB)));
}
