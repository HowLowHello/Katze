package com.howlowhello.katze.blocks.tileentities;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.blocks.TradeBoxBlock;
import com.howlowhello.katze.inventory.ITradeBox;
import com.howlowhello.katze.inventory.TradeBoxContainer;
import com.howlowhello.katze.items.TradeBoxOffer;
import com.howlowhello.katze.items.TradeBoxOffers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class TradeBoxTileEntity extends EnderChestTileEntity implements ITradeBox, INamedContainerProvider {
    @Nullable
    protected TradeBoxOffers offers;
    public TradeBoxBlock block;

    public TradeBoxTileEntity() {
        super();
        //super(ModTileEntityType.TRADE_BOX_TILE_ENTITY.get());
        // debug
        this.offers = new TradeBoxOffers(7);
    }

    public void remove() {
        this.updateContainingBlockInfo();
        super.remove();
    }


    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container."+ Katze.MOD_ID +".trade_box");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        TradeBoxContainer container = new TradeBoxContainer(id, playerInventory, this);
        //container.getEnderChestInventory().read(player.getPersistentData().getList("EnderItems", 10));
        return container;
    }


    public void setOffers(TradeBoxOffers offers){
        this.offers = offers;
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player) {

    }

    @Nullable
    @Override
    public PlayerEntity getCustomer() {
        return null;
    }

    @Nullable
    @Override
    public TradeBoxOffers getOffers() {
        return this.offers;
    }

    @Override
    public void setClientSideOffers(@Nullable TradeBoxOffers offers) {
    }

    @Override
    public void onTrade(TradeBoxOffer offer) {

    }

    @Override
    public void verifySellingItem(ItemStack stack) {

    }

}
