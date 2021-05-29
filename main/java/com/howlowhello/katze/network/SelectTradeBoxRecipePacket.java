package com.howlowhello.katze.network;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.inventory.TradeBoxContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectTradeBoxRecipePacket {

    private int recipeIndex;

    public SelectTradeBoxRecipePacket(int recipeIndex){
        this.recipeIndex = recipeIndex;
    }

    public SelectTradeBoxRecipePacket(PacketBuffer buffer){
        this.recipeIndex = buffer.readInt();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeInt(this.recipeIndex);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            ServerPlayerEntity player = ctx.get().getSender();

            if (player != null){

                if (player.openContainer instanceof TradeBoxContainer){

                    TradeBoxContainer tradeBoxContainer = (TradeBoxContainer) player.openContainer;

                    tradeBoxContainer.setCurrentRecipeIndex(recipeIndex);
                    tradeBoxContainer.mergeOrGatherPaymentItem(recipeIndex);
                    //
                    Katze.LOGGER.info("handled");
                    //
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
