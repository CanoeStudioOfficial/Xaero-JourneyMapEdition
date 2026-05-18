package com.xaerojourneymap;

import com.xaerojourneymap.config.ModConfig;
import com.xaerojourneymap.gui.GuiKeyConfig;
import com.xaerojourneymap.keybind.KeyActionHandler;
import com.xaerojourneymap.keybind.KeyBindingManager;
import com.xaerojourneymap.keybind.KeyBindingOverride;
import com.xaerojourneymap.style.StyleManager;
import com.xaerojourneymap.style.overlay.MapStyleOverlay;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION,
    clientSideOnly = true,
    acceptedMinecraftVersions = "[1.12,1.12.2]",
    dependencies = "after:xaerominimap;after:xaeroworldmap;after:xaerolib;after:journeymap")
public class XaeroJourneyMapMod {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    @Mod.Instance(Tags.MOD_ID)
    public static XaeroJourneyMapMod INSTANCE;

    private ModConfig config;
    private KeyBindingManager keyBindingManager;
    private KeyActionHandler keyActionHandler;
    private KeyBindingOverride keyBindingOverride;
    private StyleManager styleManager;
    private MapStyleOverlay mapStyleOverlay;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new ModConfig(event.getModConfigurationDirectory());
        config.load();
        LOGGER.info("Xaero JourneyMap Edition preInit complete");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        keyBindingManager = new KeyBindingManager(config);
        keyBindingManager.registerKeyBindings();

        keyActionHandler = new KeyActionHandler(config, keyBindingManager);
        MinecraftForge.EVENT_BUS.register(keyActionHandler);

        styleManager = new StyleManager(config);
        keyActionHandler.setStyleManager(styleManager);

        mapStyleOverlay = new MapStyleOverlay(styleManager);
        MinecraftForge.EVENT_BUS.register(mapStyleOverlay);

        LOGGER.info("Xaero JourneyMap Edition init complete");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        keyBindingOverride = new KeyBindingOverride(config);
        keyBindingOverride.overrideXaeroKeyBindings();

        styleManager.initialize();

        LOGGER.info("Xaero JourneyMap Edition postInit complete - Xaero key bindings overridden, visual style: {}",
            styleManager.getCurrentStyle().name());
    }

    public ModConfig getConfig() {
        return config;
    }

    public KeyBindingManager getKeyBindingManager() {
        return keyBindingManager;
    }

    public KeyBindingOverride getKeyBindingOverride() {
        return keyBindingOverride;
    }

    public StyleManager getStyleManager() {
        return styleManager;
    }

    public void openConfigScreen() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.addScheduledTask(() -> mc.displayGuiScreen(
            new GuiKeyConfig(mc.currentScreen, config, keyBindingOverride)));
    }
}
