package com.howlowhello.katze.network;

import com.howlowhello.katze.events.ArmorEventHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ArmorActiveModePacket {

    // 规定一种基本数据类型，目前没有用上
    private UUID player_ID;

    public ArmorActiveModePacket(UUID player) {
        this.player_ID = player;
    }

    // 反序列化
    public ArmorActiveModePacket(PacketBuffer buffer) {
        player_ID = buffer.readUniqueId();
    }

    // 调用序列化方法制造字节流（支持大部分基本数据类型）
    public void toBytes(PacketBuffer buf) {
        buf.writeUniqueId(this.player_ID);
    }

    // 需要执行代码，在线程安全的情况下执行
    public void handler(Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            ServerPlayerEntity player = ctx.get().getSender();

            if (player != null){
                if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag() != null){

                    if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().contains("KatzeUpgrade")){

                        if (ArmorEventHandler.playerHasValidKatzeArmorSet(player)){
                            CompoundNBT tag = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag();

                            if (tag.getInt("NextMode") != 0 && tag.getInt("LastMode") != 0){
                                int lastMode = tag.getInt("LastMode");
                                int activeMode = tag.getInt("ActiveMode");
                                int nextMode = tag.getInt("NextMode");
                                tag.putInt("LastMode", activeMode);
                                tag.putInt("ActiveMode", nextMode);
                                tag.putInt("NextMode", lastMode);
                                player.sendMessage(ArmorEventHandler.getPlayerMessage(nextMode), player.getUniqueID());
                                return;
                            }
                            else if (tag.getInt("NextMode") != 0){
                                int activeMode = tag.getInt("ActiveMode");
                                int nextMode = tag.getInt("NextMode");
                                tag.putInt("ActiveMode", nextMode);
                                tag.putInt("NextMode", activeMode);
                                player.sendMessage(ArmorEventHandler.getPlayerMessage(nextMode), player.getUniqueID());
                                return;
                            }
                            else {
                                player.sendMessage(ArmorEventHandler.getPlayerMessage(0), player.getUniqueID());
                                return;
                            }
                        }
                    }
                }
                player.sendMessage(ArmorEventHandler.getPlayerMessage(0), player.getUniqueID());
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
