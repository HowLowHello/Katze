package com.howlowhello.katze.util;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.events.ArmorEventHandler;
import com.howlowhello.katze.network.KatzePacketHandler;
import com.howlowhello.katze.network.ArmorActiveModePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Katze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyboardHelper {
    private static final long WINDOW = Minecraft.getInstance().getMainWindow().getHandle();

    public static final KeyBinding MODE_SWITCHING = new KeyBinding("key.katze_adventure.mode_switching", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_J, "key.katze_adventure.main_category");

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.KeyInputEvent event) {
        if (MODE_SWITCHING.isPressed()) {
            assert Minecraft.getInstance().player != null;
            PlayerEntity player = Minecraft.getInstance().player;

            if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag() != null){

                if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag().contains("KatzeUpgrade")){

                    if (ArmorEventHandler.playerHasValidKatzeArmorSet(player)){
                        // send a packet to server to synchronize
                        KatzePacketHandler.INSTANCE.sendToServer(new ArmorActiveModePacket(player.getUniqueID()));
                        // do client stuff
                        CompoundNBT tag = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getTag();

                        if (tag.getInt("NextMode") != 0 && tag.getInt("LastMode") != 0){
                            int lastMode = tag.getInt("LastMode");
                            int activeMode = tag.getInt("ActiveMode");
                            int nextMode = tag.getInt("NextMode");
                            tag.putInt("LastMode", activeMode);
                            tag.putInt("ActiveMode", nextMode);
                            tag.putInt("NextMode", lastMode);
                            return;
                        }
                        else if (tag.getInt("NextMode") != 0){
                            int activeMode = tag.getInt("ActiveMode");
                            int nextMode = tag.getInt("NextMode");
                            tag.putInt("ActiveMode", nextMode);
                            tag.putInt("NextMode", activeMode);
                            return;
                        }
                        else {
                            return;
                        }
                    }
                }
            }
            player.sendMessage(ArmorEventHandler.getPlayerMessage(0), player.getUniqueID());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift() {
        return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT) || InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingControl() {
        return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_CONTROL) || InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_CONTROL);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingSpace() {
        return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_SPACE);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingAlt() {
        return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_ALT);
    }
}
