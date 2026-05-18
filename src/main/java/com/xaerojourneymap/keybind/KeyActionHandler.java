package com.xaerojourneymap.keybind;

import com.xaerojourneymap.XaeroJourneyMapMod;
import com.xaerojourneymap.config.ModConfig;
import com.xaerojourneymap.integration.ModPresenceDetector;
import com.xaerojourneymap.integration.XaeroWorldMapHelper;
import com.xaerojourneymap.style.StyleManager;
import com.xaerojourneymap.style.VisualStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeyActionHandler {

    private final ModConfig config;
    private final KeyBindingManager keyBindingManager;
    private StyleManager styleManager;

    public KeyActionHandler(ModConfig config, KeyBindingManager keyBindingManager) {
        this.config = config;
        this.keyBindingManager = keyBindingManager;
    }

    public void setStyleManager(StyleManager styleManager) {
        this.styleManager = styleManager;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen != null) {
            return;
        }

        if (config.isJKeyOpensMap() && keyBindingManager.isMapKeyPressed()) {
            openWorldMap(mc);
        }

        if (keyBindingManager.isSettingsKeyPressed()) {
            openSettings(mc);
        }

        if (keyBindingManager.isStyleToggleKeyPressed() && styleManager != null) {
            styleManager.toggleStyle();
            VisualStyle current = styleManager.getCurrentStyle();
            if (mc.player != null) {
                mc.player.sendMessage(new TextComponentTranslation(
                    "xaerojourneymap.style.switched", current.name()));
            }
        }
    }

    @SubscribeEvent
    public void onGuiKeyboardInput(GuiScreenEvent.KeyboardInputEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen currentScreen = mc.currentScreen;
        if (currentScreen == null) {
            return;
        }

        int key = Keyboard.getEventKey();
        if (!Keyboard.getEventKeyState()) {
            return;
        }

        if (config.isJKeyClosesMap() && XaeroWorldMapHelper.isGuiMap(currentScreen)) {
            if (keyBindingManager.getKeyOpenMap().isActiveAndMatches(key)) {
                closeWorldMap(mc);
                return;
            }
        }

        if (XaeroWorldMapHelper.isGuiMap(currentScreen)) {
            if (keyBindingManager.getKeyOpenSettings() != null
                && keyBindingManager.getKeyOpenSettings().isActiveAndMatches(key)) {
                openSettings(mc);
            }
        }
    }

    private void openWorldMap(Minecraft mc) {
        if (!ModPresenceDetector.isXaeroWorldMapLoaded()) {
            XaeroJourneyMapMod.LOGGER.warn("Xaero's World Map is not loaded, cannot open map");
            return;
        }

        try {
            XaeroWorldMapHelper.openWorldMap(mc);
            KeyBinding.unPressAllKeys();
            XaeroJourneyMapMod.LOGGER.debug("Opened Xaero World Map via J key");
        } catch (Exception e) {
            XaeroJourneyMapMod.LOGGER.error("Failed to open Xaero World Map", e);
        }
    }

    private void closeWorldMap(Minecraft mc) {
        try {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
            XaeroJourneyMapMod.LOGGER.debug("Closed Xaero World Map via J key");
        } catch (Exception e) {
            XaeroJourneyMapMod.LOGGER.error("Failed to close Xaero World Map", e);
        }
    }

    private void openSettings(Minecraft mc) {
        if (!ModPresenceDetector.isXaeroWorldMapLoaded()) {
            return;
        }

        try {
            XaeroWorldMapHelper.openWorldMapSettings(mc);
            XaeroJourneyMapMod.LOGGER.debug("Opened Xaero World Map settings");
        } catch (Exception e) {
            XaeroJourneyMapMod.LOGGER.error("Failed to open Xaero World Map settings", e);
        }
    }
}
