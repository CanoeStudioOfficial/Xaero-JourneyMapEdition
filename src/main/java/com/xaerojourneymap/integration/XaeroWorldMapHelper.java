package com.xaerojourneymap.integration;

import com.xaerojourneymap.XaeroJourneyMapMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class XaeroWorldMapHelper {

    private static Class<?> controlsRegisterClass;
    private static Class<?> guiMapClass;
    private static Class<?> mapProcessorClass;
    private static Class<?> worldMapSessionClass;
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) return;
        try {
            controlsRegisterClass = Class.forName("xaero.map.controls.ControlsRegister");
            guiMapClass = Class.forName("xaero.map.gui.GuiMap");
            mapProcessorClass = Class.forName("xaero.map.MapProcessor");
            worldMapSessionClass = Class.forName("xaero.map.WorldMapSession");
            initialized = true;
            XaeroJourneyMapMod.LOGGER.info("XaeroWorldMapHelper initialized successfully");
        } catch (ClassNotFoundException e) {
            XaeroJourneyMapMod.LOGGER.error("Failed to initialize XaeroWorldMapHelper - Xaero World Map classes not found", e);
        }
    }

    public static void openWorldMap(Minecraft mc) {
        if (!initialized) initialize();
        if (!initialized) return;

        try {
            Object session = getCurrentSession();
            if (session == null) {
                XaeroJourneyMapMod.LOGGER.warn("Could not get WorldMapSession, using fallback");
                openWorldMapFallback(mc);
                return;
            }

            Object processor = getMapProcessor(session);
            Entity renderViewEntity = mc.getRenderViewEntity();
            if (renderViewEntity == null) renderViewEntity = mc.player;

            Constructor<?> guiMapCtor = guiMapClass.getConstructor(
                GuiScreen.class, GuiScreen.class, mapProcessorClass, Entity.class
            );
            Object guiMap = guiMapCtor.newInstance(null, null, processor, renderViewEntity);
            mc.displayGuiScreen((GuiScreen) guiMap);

        } catch (Exception e) {
            XaeroJourneyMapMod.LOGGER.error("Failed to open World Map via reflection, trying fallback", e);
            openWorldMapFallback(mc);
        }
    }

    private static void openWorldMapFallback(Minecraft mc) {
        try {
            Class<?> worldMapClass = Class.forName("xaero.map.WorldMap");
            Field controlsRegisterField = worldMapClass.getField("controlsRegister");
            Object controlsRegister = controlsRegisterField.get(null);

            if (controlsRegister != null) {
                Field keybindingsField = controlsRegisterClass.getField("keybindings");
                java.util.List<?> keybindings = (java.util.List<?>) keybindingsField.get(controlsRegister);

                if (keybindings != null && !keybindings.isEmpty()) {
                    KeyBinding openMapKey = (KeyBinding) keybindings.get(0);
                    KeyBinding.setKeyBindState(openMapKey.getKeyCode(), true);
                    KeyBinding.onTick(openMapKey.getKeyCode());
                    KeyBinding.setKeyBindState(openMapKey.getKeyCode(), false);
                    XaeroJourneyMapMod.LOGGER.info("Triggered Xaero World Map open via key binding simulation");
                    return;
                }
            }
            XaeroJourneyMapMod.LOGGER.warn("Could not find Xaero World Map controls register");
        } catch (Exception e) {
            XaeroJourneyMapMod.LOGGER.error("Fallback method also failed", e);
        }
    }

    public static void openWorldMapSettings(Minecraft mc) {
        if (!initialized) initialize();
        if (!initialized) return;

        try {
            Class<?> guiSettingsClass = Class.forName("xaero.map.gui.GuiWorldMapSettings");
            Class<?> contextClass = Class.forName("xaero.map.gui.GuiWorldMapSettings$BuiltInEditConfigScreenContexts");
            Field clientContextField = contextClass.getField("CLIENT");
            Object clientContext = clientContextField.get(null);

            Constructor<?> ctor = guiSettingsClass.getConstructor(contextClass.getInterfaces()[0]);
            Object guiSettings = ctor.newInstance(clientContext);
            mc.displayGuiScreen((GuiScreen) guiSettings);

        } catch (Exception e) {
            XaeroJourneyMapMod.LOGGER.error("Failed to open World Map settings", e);
        }
    }

    private static Object getCurrentSession() {
        try {
            Method getCurrentSessionMethod = worldMapSessionClass.getMethod("getCurrentSession");
            return getCurrentSessionMethod.invoke(null);
        } catch (Exception e) {
            XaeroJourneyMapMod.LOGGER.error("Failed to get WorldMapSession", e);
            return null;
        }
    }

    private static Object getMapProcessor(Object session) {
        try {
            Method getMapProcessorMethod = worldMapSessionClass.getMethod("getMapProcessor");
            return getMapProcessorMethod.invoke(session);
        } catch (Exception e) {
            XaeroJourneyMapMod.LOGGER.error("Failed to get MapProcessor from session", e);
            return null;
        }
    }

    public static boolean isGuiMap(GuiScreen screen) {
        if (screen == null) return false;
        return guiMapClass != null && guiMapClass.isInstance(screen);
    }
}
