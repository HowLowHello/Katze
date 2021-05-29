package com.howlowhello.katze.items;

import com.howlowhello.katze.Katze;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TradeBoxOffers extends ArrayList<TradeBoxOffer> {
    private Date modificationDate;

    public String getStringModificationDate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.modificationDate);
    }

    // debug
    public TradeBoxOffers(int size){
        for (int i=0; i<size; i++){
            this.add(new TradeBoxOffer());
        }
    }


    public TradeBoxOffers() {
    }

    // read from nbt
    public TradeBoxOffers(CompoundNBT nbt) {
        ListNBT listnbt = nbt.getList(Katze.MOD_ID+"offersList", 10);

        for(int i = 0; i < listnbt.size(); ++i) {
            this.add(new TradeBoxOffer(listnbt.getCompound(i)));
        }
    }

    @Nullable
    public TradeBoxOffer getOfferByIndex(ItemStack providedStack1, ItemStack providedStack2, int recipeIndex) {
        if (recipeIndex > 0 && recipeIndex < this.size()) {
            TradeBoxOffer tradeBoxoffer1 = this.get(recipeIndex);
            return tradeBoxoffer1.matches(providedStack1, providedStack2) ? tradeBoxoffer1 : null;
        } else {
            for(int i = 0; i < this.size(); ++i) {
                TradeBoxOffer tradeBoxoffer = this.get(i);
                if (tradeBoxoffer.matches(providedStack1, providedStack2)) {
                    return tradeBoxoffer;
                }
            }

            return null;
        }
    }

    public void write(PacketBuffer buffer) {
        buffer.writeByte((byte)(this.size() & 255));

        for(int i = 0; i < this.size(); ++i) {
            TradeBoxOffer tradeBoxoffer = this.get(i);
            buffer.writeItemStack(tradeBoxoffer.getBuyingStackFirst());
            buffer.writeItemStack(tradeBoxoffer.getSellingStack());
            ItemStack itemstack = tradeBoxoffer.getBuyingStackSecond();
            buffer.writeBoolean(!itemstack.isEmpty());
            if (!itemstack.isEmpty()) {
                buffer.writeItemStack(itemstack);
            }

            buffer.writeBoolean(tradeBoxoffer.hasNoUsesLeft());
            buffer.writeInt(tradeBoxoffer.getUses());
            buffer.writeInt(tradeBoxoffer.getMaxUses());
            buffer.writeInt(tradeBoxoffer.getGivenExp());
            buffer.writeInt(tradeBoxoffer.getSpecialPrice());
            buffer.writeFloat(tradeBoxoffer.getPriceMultiplier());
            buffer.writeInt(tradeBoxoffer.getDemand());
        }

    }

    // read from packet
    public static TradeBoxOffers read(PacketBuffer buffer) {
        TradeBoxOffers tradeBoxoffers = new TradeBoxOffers();
        int i = buffer.readByte() & 255;

        for(int j = 0; j < i; ++j) {
            ItemStack itemstack = buffer.readItemStack();
            ItemStack itemstack1 = buffer.readItemStack();
            ItemStack itemstack2 = ItemStack.EMPTY;
            if (buffer.readBoolean()) {
                itemstack2 = buffer.readItemStack();
            }

            boolean flag = buffer.readBoolean();
            int k = buffer.readInt();
            int l = buffer.readInt();
            int i1 = buffer.readInt();
            int j1 = buffer.readInt();
            float f = buffer.readFloat();
            int k1 = buffer.readInt();
            TradeBoxOffer tradeBoxoffer = new TradeBoxOffer(itemstack, itemstack2, itemstack1, k, l, i1, f, k1);
            if (flag) {
                tradeBoxoffer.makeUnavailable();
            }

            tradeBoxoffer.setSpecialPrice(j1);
            tradeBoxoffers.add(tradeBoxoffer);
        }

        return tradeBoxoffers;
    }

    public CompoundNBT write() {
        CompoundNBT compoundnbt = new CompoundNBT();
        ListNBT listnbt = new ListNBT();

        for(int i = 0; i < this.size(); ++i) {
            TradeBoxOffer tradeBoxoffer = this.get(i);
            listnbt.add(tradeBoxoffer.write());
        }

        compoundnbt.put(Katze.MOD_ID+"offersList", listnbt);
        compoundnbt.put(Katze.MOD_ID+"offersModificationDate", StringNBT.valueOf(this.getStringModificationDate()));
        return compoundnbt;
    }
}
