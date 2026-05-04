package com.xaerojourneymap.keybind;

import com.xaerojourneymap.Tags;
import com.xaerojourneymap.XaeroJourneyMapMod;
import com.xaerojourneymap.config.ModConfig;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindingManager {

    private static final String KEY_CATEGORY = Tags.MOD_NAME;

    private final ModConfig config;

    private KeyBinding keyOpenMap;
    private KeyBinding keyOpenSettings;

    public KeyBindingManager(ModConfig config) {
        this.config = config;
    }

    public void registerKeyBindings() {
        keyOpenMap = new KeyBinding(
            "key.xaerojourneymap.open_map",
            Keyboard.KEY_J,
            KEY_CATEGORY
        );
        ClientRegistry.registerKeyBinding(keyOpenMap);

        keyOpenSettings = new KeyBinding(
            "key.xaerojourneymap.open_settings",
            Keyboard.KEY_NONE,
            KEY_CATEGORY
        );
        ClientRegistry.registerKeyBinding(keyOpenSettings);

        XaeroJourneyMapMod.LOGGER.info("Registered key bindings: J (open map), NONE (open settings)");
    }

    public KeyBinding getKeyOpenMap() {
        return keyOpenMap;
    }

    public KeyBinding getKeyOpenSettings() {
        return keyOpenSettings;
    }

    public boolean isMapKeyPressed() {
        return keyOpenMap.isPressed();
    }

    public boolean isSettingsKeyPressed() {
        return keyOpenSettings.isPressed();
    }

    public boolean isMapKeyDown() {
        return keyOpenMap.isKeyDown();
    }
}
