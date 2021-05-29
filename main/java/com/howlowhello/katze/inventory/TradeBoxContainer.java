package com.howlowhello.katze.inventory;

import com.howlowhello.katze.blocks.tileentities.TradeBoxTileEntity;
import com.howlowhello.katze.init.ModBlocks;
import com.howlowhello.katze.init.ModContainerType;
import com.howlowhello.katze.items.TradeBoxOffers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

public class TradeBoxContainer extends Container {
    private final TradeBoxTileEntity tradeBox;
    private final TradeBoxInventory tradeBoxInventory;
    private IWorldPosCallable canInteractWithCallable;
    @OnlyIn(Dist.CLIENT)
    private boolean field_223433_g;
    //
    private final EnderChestInventory enderChestInventory;

    public EnderChestInventory getEnderChestInventory(){
        return this.enderChestInventory;
    }
    //

    @OnlyIn(Dist.CLIENT)
    public void func_223431_b(boolean p_223431_1_) {
        this.field_223433_g = p_223431_1_;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean func_223432_h() {
        return this.field_223433_g;
    }



    public TradeBoxContainer(int windowId, final PlayerInventory playerInventoryIn, final PacketBuffer data){
        this(windowId, playerInventoryIn, TradeBoxContainer.getTileEntity(playerInventoryIn, data));
    }

    public static TradeBoxTileEntity getTileEntity(final PlayerInventory playerInventoryIn, final PacketBuffer data){
        Objects.requireNonNull(playerInventoryIn, "Player Inventory cannot be null.");
        Objects.requireNonNull(data, "Packet Buffer cannot be null.");
        final TileEntity tileEntity = playerInventoryIn.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof TradeBoxTileEntity){
            return (TradeBoxTileEntity) tileEntity;
        }
        throw new IllegalStateException("Tile Entity is not correct.");
    }

    public TradeBoxContainer(int id, PlayerInventory playerInventoryIn, TradeBoxTileEntity tradeBox) {
        // set the container type
        super(ModContainerType.TRADE_BOX.get(), id);
        this.tradeBox = tradeBox;
        this.canInteractWithCallable = IWorldPosCallable.of(tradeBox.getWorld(),tradeBox.getPos());
        this.tradeBoxInventory = new TradeBoxInventory(tradeBox);
        // 下方三个Slot, 原 Y 坐标 应 下移(加) 36
        this.addSlot(new Slot(this.tradeBoxInventory, 0, 136, 1));
        this.addSlot(new Slot(this.tradeBoxInventory, 1, 162, 1));
        this.addSlot(new TradeBoxResultSlot(playerInventoryIn.player, tradeBox, this.tradeBoxInventory, 2, 220, 1));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 108 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventoryIn, k, 108 + k * 18, 142));
        }
        //
        //
        EnderChestInventory enderChest = playerInventoryIn.player.getInventoryEnderChest();
        this.enderChestInventory = enderChest;
        //
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(enderChest, j + i * 9, 108 + j * 18, 19 + i * 18));
            }
        }

    }


    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.tradeBoxInventory.resetRecipeAndSlots();
        super.onCraftMatrixChanged(inventoryIn);
    }

    public void setCurrentRecipeIndex(int currentRecipeIndex) {
        this.tradeBoxInventory.setCurrentRecipeIndex(currentRecipeIndex);
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlocks.TRADE_BOX_BLOCK.get());
    }

    @OnlyIn(Dist.CLIENT)
    public int getPendingExp() {
        return this.tradeBoxInventory.getClientSideExp();
    }


    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
     * null for the initial slot that was double-clicked.
     */
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return false;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);

            } else if (index != 0 && index != 1) {
                if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }


    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        if (!this.tradeBox.getWorld().isRemote) {
            if (!playerIn.isAlive() || playerIn instanceof ServerPlayerEntity && ((ServerPlayerEntity)playerIn).hasDisconnected()) {
                ItemStack itemstack = this.tradeBoxInventory.removeStackFromSlot(0);
                if (!itemstack.isEmpty()) {
                    playerIn.dropItem(itemstack, false);
                }

                itemstack = this.tradeBoxInventory.removeStackFromSlot(1);
                if (!itemstack.isEmpty()) {
                    playerIn.dropItem(itemstack, false);
                }
            } else {
                playerIn.inventory.placeItemBackInInventory(playerIn.world, this.tradeBoxInventory.removeStackFromSlot(0));
                playerIn.inventory.placeItemBackInInventory(playerIn.world, this.tradeBoxInventory.removeStackFromSlot(1));
            }

        }
    }


    /** when player changes selected recipe,
     *  merge or gather payment item automatically */
    public void mergeOrGatherPaymentItem(int selectedRecipeIndex) {
        if (this.getOffers().size() > selectedRecipeIndex) {
            ItemStack itemstack = this.tradeBoxInventory.getStackInSlot(0);
            // case that the first buying slot is not empty
            if (!itemstack.isEmpty()) {
                if (!this.mergeItemStack(itemstack, 3, 39, true)) {
                    return;
                }

                this.tradeBoxInventory.setInventorySlotContents(0, itemstack);
            }

            // case that the second buying slot is not empty
            ItemStack itemstack1 = this.tradeBoxInventory.getStackInSlot(1);
            if (!itemstack1.isEmpty()) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return;
                }

                this.tradeBoxInventory.setInventorySlotContents(1, itemstack1);
            }

            // case that the two buying slots are both empty
            if (this.tradeBoxInventory.getStackInSlot(0).isEmpty() && this.tradeBoxInventory.getStackInSlot(1).isEmpty()) {
                ItemStack itemstack2 = this.getOffers().get(selectedRecipeIndex).getDiscountedBuyingStackFirst();
                this.gatherPaymentItem(0, itemstack2);
                ItemStack itemstack3 = this.getOffers().get(selectedRecipeIndex).getBuyingStackSecond();
                this.gatherPaymentItem(1, itemstack3);
            }

        }
    }

    /** to automatically gather payment item as many as possible from player's inventory,
     * only implemented when the buying slots are previously empty */
    private void gatherPaymentItem(int toIndex, ItemStack neededItem) {
        if (!neededItem.isEmpty()) {
            for(int i = 3; i < 39; ++i) {
                ItemStack temp = this.inventorySlots.get(i).getStack();
                if (!temp.isEmpty() && this.areItemStacksEqual(neededItem, temp)) {
                    ItemStack matchedStack = temp;
                    ItemStack itemstack1 = this.tradeBoxInventory.getStackInSlot(toIndex);
                    int j = itemstack1.isEmpty() ? 0 : itemstack1.getCount();
                    int k = Math.min(neededItem.getMaxStackSize() - j, matchedStack.getCount());
                    ItemStack matchedStackCopy = matchedStack.copy();
                    int l = j + k;
                    matchedStack.shrink(k);
                    matchedStackCopy.setCount(l);
                    this.tradeBoxInventory.setInventorySlotContents(toIndex, matchedStackCopy);
                    if (l >= neededItem.getMaxStackSize()) {
                        break;
                    }
                }
            }
        }

    }


    private boolean areItemStacksEqual(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    /**
     * net.minecraft.client.network.play.ClientPlayNetHandler uses this to set offers for the client side
     * TradeBoxContainer
     */
    @OnlyIn(Dist.CLIENT)
    public void setClientSideOffers(TradeBoxOffers offers) {
        this.tradeBox.setClientSideOffers(offers);
    }

    public TradeBoxOffers getOffers() {
        return this.tradeBox.getOffers();
    }

}
