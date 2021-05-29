package com.howlowhello.katze.inventory;

import com.howlowhello.katze.items.TradeBoxOffer;
import com.howlowhello.katze.items.TradeBoxOffers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public interface ITradeBox {
    void setCustomer(@Nullable PlayerEntity player);

    @Nullable
    PlayerEntity getCustomer();

    TradeBoxOffers getOffers();

    @OnlyIn(Dist.CLIENT)
    void setClientSideOffers(@Nullable TradeBoxOffers offers);

    void onTrade(TradeBoxOffer offer);

    /**
     * Notifies the merchant of a possible merchantrecipe being fulfilled or not. Usually, this is just a sound byte
     * being played depending if the suggested itemstack is not null.
     */
    void verifySellingItem(ItemStack stack);

    World getWorld();

    default boolean canRestockTrades() {
        return false;
    }

    /**
    default void openTradeBoxContainer(PlayerEntity player, ITextComponent displayName) {
        player.openContainer(new SimpleNamedContainerProvider((id, playerInventory, player2) -> {
            return new TradeBoxContainer(id, playerInventory, this);
        }, displayName));
    }
     */
}
