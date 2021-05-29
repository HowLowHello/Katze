package com.howlowhello.katze.inventory;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.items.TradeBoxOffer;
import com.howlowhello.katze.items.TradeBoxOffers;
import com.howlowhello.katze.network.KatzePacketHandler;
import com.howlowhello.katze.network.SelectTradeBoxRecipePacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TradeBoxScreen extends ContainerScreen<TradeBoxContainer> {
    private static final ResourceLocation TRADE_BOX_GUI_TEXTURE = new ResourceLocation(Katze.MOD_ID,"textures/gui/container/villager2.png");
    private static final ITextComponent TEXT_1 = new TranslationTextComponent("tradebox.trades");
    private static final ITextComponent TEXT_2 = new StringTextComponent(" - ");
    private static final ITextComponent TEXT_3 = new TranslationTextComponent("tradebox.deprecated");
    /** The integer value corresponding to the currently selected tradebox recipe. */
    private int selectedTradeBoxRecipe;
    private final TradeBoxScreen.TradeButton[] BUTTON_LIST = new TradeBoxScreen.TradeButton[7];
    private int field_214139_n;
    private boolean field_214140_o;

    public TradeBoxScreen(TradeBoxContainer containerIn, PlayerInventory playerInventory, ITextComponent textComponent) {
        super(containerIn, playerInventory, textComponent);
        this.xSize = 276;
        this.playerInventoryTitleX = 107;
    }

    private void handleRecipe() {
        // update selected recipe index and reset TradeBoxInventory's buying slots and cargo slot
        this.container.setCurrentRecipeIndex(this.selectedTradeBoxRecipe);
        // merge or gather payment item automatically according to selected recipe
        this.container.mergeOrGatherPaymentItem(this.selectedTradeBoxRecipe);
        KatzePacketHandler.INSTANCE.sendToServer(new SelectTradeBoxRecipePacket(this.selectedTradeBoxRecipe));
        //this.minecraft.getConnection().sendPacket(new CSelectTradePacket(this.selectedTradeBoxRecipe));
        //
        Katze.LOGGER.info("send: "+this.selectedTradeBoxRecipe);
        //
    }

    protected void init() {
        super.init();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int k = j + 16 + 2;

        for(int l = 0; l < 7; ++l) {
            this.BUTTON_LIST[l] = this.addButton(new TradeBoxScreen.TradeButton(i + 5, k, l, (buttonPressable) -> {
                if (buttonPressable instanceof TradeBoxScreen.TradeButton) {
                    this.selectedTradeBoxRecipe = ((TradeBoxScreen.TradeButton)buttonPressable).getButtonIndex() + this.field_214139_n;
                    //
                    Katze.LOGGER.info("recipe: "+this.selectedTradeBoxRecipe);
                    //
                    this.handleRecipe();
                }

            }));
            k += 20;
        }

    }

    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        int i = 1;
        // debug, should be this.container.getTradeBoxLevel();
        if (i > 0 && i <= 5) {
            ITextComponent itextcomponent = this.title.deepCopy().append(TEXT_2).append(new TranslationTextComponent("tradebox.level." + i));
            int j = this.font.getStringPropertyWidth(itextcomponent);
            int k = 49 + this.xSize / 2 - j / 2;
            this.font.func_243248_b(matrixStack, itextcomponent, (float)k, 6.0F, 4210752);
        } else {
            this.font.func_243248_b(matrixStack, this.title, (float)(49 + this.xSize / 2 - this.font.getStringPropertyWidth(this.title) / 2), 6.0F, 4210752);
        }

        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 4210752);
        int l = this.font.getStringPropertyWidth(TEXT_1);
        this.font.func_243248_b(matrixStack, TEXT_1, (float)(5 - l / 2 + 48), 6.0F, 4210752);
    }


    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TRADE_BOX_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(matrixStack, i, j, this.getBlitOffset(), 0.0F, 0.0F, this.xSize, this.ySize, 256, 512);
        TradeBoxOffers tradeboxoffers = this.container.getOffers();
        if (!tradeboxoffers.isEmpty()) {
            int k = this.selectedTradeBoxRecipe;
            if (k < 0 || k >= tradeboxoffers.size()) {
                return;
            }

            TradeBoxOffer tradeboxoffer = tradeboxoffers.get(k);
            if (tradeboxoffer.hasNoUsesLeft()) {
                this.minecraft.getTextureManager().bindTexture(TRADE_BOX_GUI_TEXTURE);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                blit(matrixStack, this.guiLeft + 83 + 99, this.guiTop + 35, this.getBlitOffset(), 311.0F, 0.0F, 28, 21, 256, 512);
            }
        }

    }



    private void blitOnSize(MatrixStack p_238840_1_, int p_238840_2_, int p_238840_3_, TradeBoxOffers offers) {
        int i = offers.size() + 1 - 7;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int l = 113;
            int i1 = Math.min(113, this.field_214139_n * k);
            if (this.field_214139_n == i - 1) {
                i1 = 113;
            }

            blit(p_238840_1_, p_238840_2_ + 94, p_238840_3_ + 18 + i1, this.getBlitOffset(), 0.0F, 199.0F, 6, 27, 256, 512);
        } else {
            blit(p_238840_1_, p_238840_2_ + 94, p_238840_3_ + 18, this.getBlitOffset(), 6.0F, 199.0F, 6, 27, 256, 512);
        }

    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        TradeBoxOffers tradeboxoffers = this.container.getOffers();
        if (!tradeboxoffers.isEmpty()) {
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            int k = j + 16 + 1;
            int l = i + 5 + 5;
            RenderSystem.pushMatrix();
            RenderSystem.enableRescaleNormal();
            this.minecraft.getTextureManager().bindTexture(TRADE_BOX_GUI_TEXTURE);
            this.blitOnSize(matrixStack, i, j, tradeboxoffers);
            int i1 = 0;

            for(TradeBoxOffer tradeboxoffer : tradeboxoffers) {
                if (this.intIsOver7(tradeboxoffers.size()) && (i1 < this.field_214139_n || i1 >= 7 + this.field_214139_n)) {
                    ++i1;
                } else {
                    ItemStack itemstack = tradeboxoffer.getBuyingStackFirst();
                    ItemStack itemstack1 = tradeboxoffer.getDiscountedBuyingStackFirst();
                    ItemStack itemstack2 = tradeboxoffer.getBuyingStackSecond();
                    ItemStack itemstack3 = tradeboxoffer.getSellingStack();
                    this.itemRenderer.zLevel = 100.0F;
                    int j1 = k + 2;
                    this.renderDiscountEffect(matrixStack, itemstack1, itemstack, l, j1);
                    if (!itemstack2.isEmpty()) {
                        this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(itemstack2, i + 5 + 35, j1);
                        this.itemRenderer.renderItemOverlays(this.font, itemstack2, i + 5 + 35, j1);
                    }

                    this.func_238842_a_(matrixStack, tradeboxoffer, i, j1);
                    this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(itemstack3, i + 5 + 68, j1);
                    this.itemRenderer.renderItemOverlays(this.font, itemstack3, i + 5 + 68, j1);
                    this.itemRenderer.zLevel = 0.0F;
                    k += 20;
                    ++i1;
                }
            }

            //
            EnderChestInventory enderChestInventory = this.container.getEnderChestInventory();
            for(int i2 = 0; i2 < enderChestInventory.getSizeInventory(); ++i2) {
                ItemStack itemstack = enderChestInventory.getStackInSlot(i2);

            }
            //

            int k1 = this.selectedTradeBoxRecipe;
            TradeBoxOffer tradeboxoffer1 = tradeboxoffers.get(k1);


            if (tradeboxoffer1.hasNoUsesLeft() && this.isPointInRegion(186, 35, 22, 21, (double)mouseX, (double)mouseY)) {
                this.renderTooltip(matrixStack, TEXT_3, mouseX, mouseY);
            }

            for(TradeBoxScreen.TradeButton tradeboxscreen$tradebutton : this.BUTTON_LIST) {
                if (tradeboxscreen$tradebutton.isHovered()) {
                    tradeboxscreen$tradebutton.renderToolTip(matrixStack, mouseX, mouseY);
                }

                tradeboxscreen$tradebutton.visible = tradeboxscreen$tradebutton.buttonIndex < this.container.getOffers().size();
            }

            RenderSystem.popMatrix();
            RenderSystem.enableDepthTest();
        }

        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    private void func_238842_a_(MatrixStack p_238842_1_, TradeBoxOffer p_238842_2_, int p_238842_3_, int p_238842_4_) {
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bindTexture(TRADE_BOX_GUI_TEXTURE);
        if (p_238842_2_.hasNoUsesLeft()) {
            blit(p_238842_1_, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, this.getBlitOffset(), 25.0F, 171.0F, 10, 9, 256, 512);
        } else {
            blit(p_238842_1_, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, this.getBlitOffset(), 15.0F, 171.0F, 10, 9, 256, 512);
        }

    }

    private void renderDiscountEffect(MatrixStack p_238841_1_, ItemStack stackDiscounted, ItemStack stackWithoutDiscount, int x, int y) {
        this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(stackDiscounted, x, y);
        if (stackWithoutDiscount.getCount() == stackDiscounted.getCount()) {
            this.itemRenderer.renderItemOverlays(this.font, stackDiscounted, x, y);
        } else {
            this.itemRenderer.renderItemOverlayIntoGUI(this.font, stackWithoutDiscount, x, y, stackWithoutDiscount.getCount() == 1 ? "1" : null);
            this.itemRenderer.renderItemOverlayIntoGUI(this.font, stackDiscounted, x + 14, y, stackDiscounted.getCount() == 1 ? "1" : null);
            this.minecraft.getTextureManager().bindTexture(TRADE_BOX_GUI_TEXTURE);
            this.setBlitOffset(this.getBlitOffset() + 300);
            blit(p_238841_1_, x + 7, y + 12, this.getBlitOffset(), 0.0F, 176.0F, 9, 2, 256, 512);
            this.setBlitOffset(this.getBlitOffset() - 300);
        }

    }

    private boolean intIsOver7(int i) {
        return i > 7;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int i = this.container.getOffers().size();
        if (this.intIsOver7(i)) {
            int j = i - 7;
            this.field_214139_n = (int)((double)this.field_214139_n - delta);
            this.field_214139_n = MathHelper.clamp(this.field_214139_n, 0, j);
        }

        return true;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.container.getOffers().size();
        if (this.field_214140_o) {
            int j = this.guiTop + 18;
            int k = j + 139;
            int l = i - 7;
            float f = ((float)mouseY - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
            f = f * (float)l + 0.5F;
            this.field_214139_n = MathHelper.clamp((int)f, 0, l);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.field_214140_o = false;
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (this.intIsOver7(this.container.getOffers().size()) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 18) && mouseY <= (double)(j + 18 + 139 + 1)) {
            this.field_214140_o = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @OnlyIn(Dist.CLIENT)
    class TradeButton extends Button {
        final int buttonIndex;

        public TradeButton(int x, int y, int index, Button.IPressable buttonPressable) {
            super(x, y, 89, 20, StringTextComponent.EMPTY, buttonPressable);
            this.buttonIndex = index;
            this.visible = false;
        }

        public int getButtonIndex() {
            return this.buttonIndex;
        }

        public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
            if (this.isHovered && TradeBoxScreen.this.container.getOffers().size() > this.buttonIndex + TradeBoxScreen.this.field_214139_n) {
                if (mouseX < this.x + 20) {
                    ItemStack itemstack = TradeBoxScreen.this.container.getOffers().get(this.buttonIndex + TradeBoxScreen.this.field_214139_n).getDiscountedBuyingStackFirst();
                    TradeBoxScreen.this.renderTooltip(matrixStack, itemstack, mouseX, mouseY);
                } else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
                    ItemStack itemstack2 = TradeBoxScreen.this.container.getOffers().get(this.buttonIndex + TradeBoxScreen.this.field_214139_n).getBuyingStackSecond();
                    if (!itemstack2.isEmpty()) {
                        TradeBoxScreen.this.renderTooltip(matrixStack, itemstack2, mouseX, mouseY);
                    }
                } else if (mouseX > this.x + 65) {
                    ItemStack itemstack1 = TradeBoxScreen.this.container.getOffers().get(this.buttonIndex + TradeBoxScreen.this.field_214139_n).getSellingStack();
                    TradeBoxScreen.this.renderTooltip(matrixStack, itemstack1, mouseX, mouseY);
                }
            }

        }
    }
}
