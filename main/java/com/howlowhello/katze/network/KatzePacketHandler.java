package com.howlowhello.katze.network;

import com.howlowhello.katze.Katze;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class KatzePacketHandler {
    public static final String PROTOCOL_VERSION = "1.16";
    private static int ID = 0;
    public static int nextID(){
        return ID++;
    }

    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Katze.MOD_ID, "first_networking"),
            () -> {
                return PROTOCOL_VERSION;
            },
            (version) -> {
                return version.equals(PROTOCOL_VERSION);
            },
            (version) -> {
                return version.equals(PROTOCOL_VERSION);
            });

    public static void registerMessage(){
        INSTANCE.registerMessage(
                nextID(),
                ArmorActiveModePacket.class,
                (pack, buffer) -> {
                    pack.toBytes(buffer);
                },
                (buffer) -> {
                    return new ArmorActiveModePacket(buffer);
                },
                (pack, ctx) -> {
                    pack.handler(ctx);
                }
        );
        INSTANCE.registerMessage(
                nextID(),
                ArmorParticleEffectPacket.class,
                (pack, buffer) -> {
                    pack.toBytes(buffer);
                },
                (buffer) -> {
                    return new ArmorParticleEffectPacket(buffer);
                },
                (pack, ctx) -> {
                    pack.handler(ctx);
                }
        );
        INSTANCE.registerMessage(
                nextID(),
                SelectTradeBoxRecipePacket.class,
                (pack, buffer) -> {
                    pack.toBytes(buffer);
                },
                (buffer) -> {
                    return new SelectTradeBoxRecipePacket(buffer);
                },
                (pack, ctx) -> {
                    pack.handler(ctx);
                }
        );
    }
}
