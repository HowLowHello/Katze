package com.howlowhello.katze.network;

import com.howlowhello.katze.events.ArmorEventHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ArmorParticleEffectPacket {

    private int activeMode;
    private double posX;
    private double posY;
    private double posZ;

    public ArmorParticleEffectPacket(int activeMode, double posX, double posY, double posZ){
        this.activeMode = activeMode;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public ArmorParticleEffectPacket(PacketBuffer buffer){
        this.activeMode = buffer.readInt();
        this.posX = buffer.readDouble();
        this.posY = buffer.readDouble();
        this.posZ = buffer.readDouble();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeInt(this.activeMode);
        buf.writeDouble(this.posX);
        buf.writeDouble(this.posY);
        buf.writeDouble(this.posZ);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {
            ArmorEventHandler.spawnParticle(this.activeMode, this.posX, this.posY, this.posZ);
        });

        ctx.get().setPacketHandled(true);
    }
}
