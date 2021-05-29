package com.howlowhello.katze.network;

import com.howlowhello.katze.items.TradeBoxOffers;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;

public class TradeBoxOffersPacket {
    private int containerId;
    private TradeBoxOffers offers;
    private int level;
    private int xp;

    public TradeBoxOffersPacket() {
    }

    public TradeBoxOffersPacket(int id, TradeBoxOffers offersIn, int levelIn, int xpIn) {
        this.containerId = id;
        this.offers = offersIn;
        this.level = levelIn;
        this.xp = xpIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.containerId = buf.readVarInt();
        this.offers = TradeBoxOffers.read(buf);
        this.level = buf.readVarInt();
        this.xp = buf.readVarInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarInt(this.containerId);
        this.offers.write(buf);
        buf.writeVarInt(this.level);
        buf.writeVarInt(this.xp);
    }


    @OnlyIn(Dist.CLIENT)
    public int getContainerId() {
        return this.containerId;
    }

    @OnlyIn(Dist.CLIENT)
    public TradeBoxOffers getOffers() {
        return this.offers;
    }

    @OnlyIn(Dist.CLIENT)
    public int getLevel() {
        return this.level;
    }

    @OnlyIn(Dist.CLIENT)
    public int getExp() {
        return this.xp;
    }

}
