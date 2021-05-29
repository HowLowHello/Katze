package com.howlowhello.katze.inventory;

import com.howlowhello.katze.blocks.tileentities.TradeBoxTileEntity;
import com.howlowhello.katze.items.TradeBoxOffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;

public class TradeBoxResultSlot extends Slot {
    private final TradeBoxInventory tradeBoxInventory;
    private final PlayerEntity player;
    private int removeCount;
    private final TradeBoxTileEntity tradeBox;

    public TradeBoxResultSlot(PlayerEntity player, TradeBoxTileEntity tradeBox, TradeBoxInventory tradeBoxInventory, int slotIndex, int xPosition, int yPosition) {
        super(tradeBoxInventory, slotIndex, xPosition, yPosition);
        this.player = player;
        this.tradeBox = tradeBox;
        this.tradeBoxInventory = tradeBoxInventory;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new stack.
     */
    public ItemStack decrStackSize(int amount) {
        if (this.getHasStack()) {
            this.removeCount += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void onCrafting(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.onCrafting(stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void onCrafting(ItemStack stack) {
        stack.onCrafting(this.player.world, this.player, this.removeCount);
        this.removeCount = 0;
    }

    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
        this.onCrafting(stack);
        TradeBoxOffer tradeBoxoffer = this.tradeBoxInventory.getResultItem();
        if (tradeBoxoffer != null) {
            ItemStack itemstack = this.tradeBoxInventory.getStackInSlot(0);
            ItemStack itemstack1 = this.tradeBoxInventory.getStackInSlot(1);
            if (tradeBoxoffer.doTransaction(itemstack, itemstack1) || tradeBoxoffer.doTransaction(itemstack1, itemstack)) {
                this.tradeBox.onTrade(tradeBoxoffer);
                thePlayer.addStat(Stats.TRADED_WITH_VILLAGER);
                this.tradeBoxInventory.setInventorySlotContents(0, itemstack);
                this.tradeBoxInventory.setInventorySlotContents(1, itemstack1);
            }
        }

        return stack;
    }
}
