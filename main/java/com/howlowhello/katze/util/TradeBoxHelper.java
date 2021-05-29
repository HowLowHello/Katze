package com.howlowhello.katze.util;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.items.TradeBoxOffer;
import com.howlowhello.katze.items.TradeBoxOffers;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.datasync.EntityDataManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TradeBoxHelper {

    // judge if a player's trade box offers should be updated
    public static boolean shouldUpdate(ServerPlayerEntity player) throws ParseException {
        EntityDataManager dataManager = player.getDataManager();
        String dateString = player.getPersistentData().getString(Katze.MOD_ID+"offersModificationDate");
        Date modificationDate = new SimpleDateFormat().parse(dateString);
        Date newestDate = new Date();
        // 36 sec
        return newestDate.getTime() - modificationDate.getTime() > 36000;
    }

    // create tags when a player logs in or simply update it
    public static void writeOffers(ServerPlayerEntity player, TradeBoxOffers offers){
        CompoundNBT compoundnbt = player.getPersistentData();
        ListNBT listnbt = new ListNBT();

        for (TradeBoxOffer tradeBoxoffer : offers) {
            listnbt.add(tradeBoxoffer.write());
        }
        compoundnbt.put(Katze.MOD_ID+"offersList", listnbt);
        compoundnbt.put(Katze.MOD_ID+"offersModificationDate", StringNBT.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
    }

}
