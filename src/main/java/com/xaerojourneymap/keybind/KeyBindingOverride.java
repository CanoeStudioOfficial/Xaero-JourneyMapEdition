package com.xaerojourneymap.keybind;

import com.xaerojourneymap.XaeroJourneyMapMod;
import com.xaerojourneymap.config.ModConfig;
import com.xaerojourneymap.integration.ModPresenceDetector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import java.util.ArrayList;
import java.util.List;

public class KeyBindingOverride {

    private final ModConfig config;
    private final List<SavedKeyBinding> savedBindings = new ArrayList<>();

    public KeyBindingOverride(ModConfig config) {
        this.config = config;
    }

    public void overrideXaeroKeyBindings() {
        if (!ModPresenceDetector.isXaeroWorldMapLoaded() && !ModPresenceDetector.isXaeroMinimapLoaded()) {
            XaeroJourneyMapMod.LOGGER.info("No Xaero mods detected, skipping key binding override");
            return;
        }

        KeyBinding[] keyBindings = Minecraft.getMinecraft().gameSettings.keyBindings;
        for (KeyBinding kb : keyBindings) {
            String desc = kb.getKeyDescription();
            int keyCode = kb.getKeyCode();

            if (shouldHideKeyBinding(desc, keyCode)) {
                savedBindings.add(new SavedKeyBinding(desc, keyCode, kb.getKeyModifier()));
                kb.setKeyCode(0);
                KeyBinding.resetKeyBindingArrayAndHash();
                XaeroJourneyMapMod.LOGGER.info("Overridden key binding: {} (was keyCode={})", desc, keyCode);
            }
        }

        KeyBinding.resetKeyBindingArrayAndHash();
        XaeroJourneyMapMod.LOGGER.info("Key binding override complete. {} bindings overridden.", savedBindings.size());
    }

    private boolean shouldHideKeyBinding(String description, int currentKeyCode) {
        if (currentKeyCode == 0) {
            return false;
        }

        if (isWorldMapKey(description)) {
            return shouldHideWorldMapKey(description);
        }

        if (isMinimapKey(description)) {
            return shouldHideMinimapKey(description);
        }

        return false;
    }

    private boolean isWorldMapKey(String description) {
        return description.equals("gui.xaero_open_map")
            || description.equals("gui.xaero_open_settings")
            || description.equals("gui.xaero_world_map_server_settings")
            || description.equals("gui.xaero_map_zoom_in")
            || description.equals("gui.xaero_map_zoom_out")
            || description.equals("gui.xaero_quick_confirm")
            || description.equals("gui.xaero_toggle_dimension")
            || description.equals("gui.xaero_toggle_tracked_players_on_map");
    }

    private boolean shouldHideWorldMapKey(String description) {
        if (description.equals("gui.xaero_open_map")) {
            return config.isHideOriginalMapKey();
        }
        if (description.equals("gui.xaero_open_settings")) {
            return config.isHideOriginalSettingsKey();
        }
        return true;
    }

    private boolean isMinimapKey(String description) {
        return description.equals("gui.xaero_zoom_in")
            || description.equals("gui.xaero_zoom_out")
            || description.equals("gui.xaero_new_waypoint")
            || description.equals("gui.xaero_waypoints_key")
            || description.equals("gui.xaero_enlarge_map")
            || description.equals("gui.xaero_toggle_map")
            || description.equals("gui.xaero_toggle_waypoints")
            || description.equals("gui.xaero_toggle_map_waypoints")
            || description.equals("gui.xaero_toggle_slime")
            || description.equals("gui.xaero_toggle_grid")
            || description.equals("gui.xaero_instant_waypoint")
            || description.equals("gui.xaero_switch_waypoint_set")
            || description.equals("gui.xaero_display_all_sets")
            || description.equals("gui.xaero_toggle_light_overlay")
            || description.equals("gui.xaero_toggle_entity_radar")
            || description.equals("gui.xaero_reverse_entity_radar")
            || description.equals("gui.xaero_toggle_manual_cave_mode")
            || description.equals("gui.xaero_alternative_list_players")
            || description.equals("gui.xaero_toggle_tracked_players_on_map")
            || description.equals("gui.xaero_toggle_tracked_players_in_world")
            || description.equals("gui.xaero_minimap_settings")
            || description.equals("gui.xaero_minimap_server_profiles");
    }

    private boolean shouldHideMinimapKey(String description) {
        if (description.equals("gui.xaero_new_waypoint")) {
            return !config.isPreserveWaypointKey();
        }
        if (description.equals("gui.xaero_waypoints_key")) {
            return !config.isPreserveWaypointMenuKey();
        }
        if (description.equals("gui.xaero_minimap_settings")) {
            return !config.isPreserveMinimapSettingsKey();
        }
        if (description.equals("gui.xaero_zoom_in") || description.equals("gui.xaero_zoom_out")) {
            return !config.isPreserveZoomKeys();
        }
        if (description.equals("gui.xaero_enlarge_map")) {
            return !config.isPreserveEnlargeMapKey();
        }
        if (description.equals("gui.xaero_toggle_map")) {
            return !config.isPreserveMinimapToggleKey();
        }
        if (description.equals("gui.xaero_toggle_dimension")) {
            return !config.isPreserveToggleDimensionKey();
        }
        if (description.equals("gui.xaero_toggle_entity_radar")) {
            return !config.isPreserveRadarToggleKey();
        }
        if (description.equals("gui.xaero_toggle_slime")) {
            return !config.isPreserveSlimeChunksKey();
        }
        if (description.equals("gui.xaero_instant_waypoint")) {
            return !config.isPreserveTempWaypointKey();
        }
        if (description.equals("gui.xaero_switch_waypoint_set")) {
            return !config.isPreserveSwitchWaypointSetKey();
        }
        if (description.equals("gui.xaero_toggle_light_overlay")) {
            return !config.isPreserveLightOverlayKey();
        }
        if (description.equals("gui.xaero_toggle_manual_cave_mode")) {
            return !config.isPreserveManualCaveModeKey();
        }
        if (description.equals("gui.xaero_toggle_grid")) {
            return !config.isPreserveGridToggleKey();
        }
        if (description.equals("gui.xaero_toggle_waypoints")) {
            return !config.isPreserveWorldWaypointsKey();
        }
        if (description.equals("gui.xaero_toggle_map_waypoints")) {
            return !config.isPreserveMapWaypointsKey();
        }
        return true;
    }

    public void restoreKeyBindings() {
        KeyBinding[] keyBindings = Minecraft.getMinecraft().gameSettings.keyBindings;
        for (SavedKeyBinding saved : savedBindings) {
            for (KeyBinding kb : keyBindings) {
                if (kb.getKeyDescription().equals(saved.description)) {
                    kb.setKeyCode(saved.keyCode);
                    break;
                }
            }
        }
        KeyBinding.resetKeyBindingArrayAndHash();
        savedBindings.clear();
        XaeroJourneyMapMod.LOGGER.info("Restored all overridden key bindings");
    }

    public void reapplyOverrides() {
        restoreKeyBindings();
        overrideXaeroKeyBindings();
    }

    private static class SavedKeyBinding {
        final String description;
        final int keyCode;
        final Object keyModifier;

        SavedKeyBinding(String description, int keyCode, Object keyModifier) {
            this.description = description;
            this.keyCode = keyCode;
            this.keyModifier = keyModifier;
        }
    }
}
