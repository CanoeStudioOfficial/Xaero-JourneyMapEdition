package com.xaerojourneymap.gui;

import com.xaerojourneymap.config.ModConfig;
import com.xaerojourneymap.keybind.KeyBindingOverride;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiKeyConfig extends GuiScreen {

    private final GuiScreen parentScreen;
    private final ModConfig config;
    private final KeyBindingOverride keyBindingOverride;

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 24;

    private static final int ID_J_KEY_OPENS_MAP = 0;
    private static final int ID_J_KEY_CLOSES_MAP = 1;
    private static final int ID_HIDE_ORIGINAL_MAP_KEY = 2;
    private static final int ID_HIDE_ORIGINAL_SETTINGS_KEY = 3;
    private static final int ID_PRESERVE_WAYPOINT_KEY = 4;
    private static final int ID_PRESERVE_MINIMAP_SETTINGS_KEY = 5;
    private static final int ID_PRESERVE_WAYPOINT_MENU_KEY = 6;
    private static final int ID_PRESERVE_ZOOM_KEYS = 7;
    private static final int ID_PRESERVE_ENLARGE_MAP_KEY = 8;
    private static final int ID_PRESERVE_MINIMAP_TOGGLE_KEY = 9;
    private static final int ID_PRESERVE_TEMP_WAYPOINT_KEY = 10;
    private static final int ID_PRESERVE_RADAR_TOGGLE_KEY = 11;
    private static final int ID_PRESERVE_SLIME_CHUNKS_KEY = 12;
    private static final int ID_PRESERVE_LIGHT_OVERLAY_KEY = 13;
    private static final int ID_PRESERVE_GRID_TOGGLE_KEY = 14;
    private static final int ID_PRESERVE_WORLD_WAYPOINTS_KEY = 15;
    private static final int ID_PRESERVE_MAP_WAYPOINTS_KEY = 16;
    private static final int ID_PRESERVE_SWITCH_WAYPOINT_SET_KEY = 17;
    private static final int ID_PRESERVE_MANUAL_CAVE_MODE_KEY = 18;
    private static final int ID_PRESERVE_TOGGLE_DIMENSION_KEY = 19;
    private static final int ID_DONE = 100;

    public GuiKeyConfig(GuiScreen parentScreen, ModConfig config, KeyBindingOverride keyBindingOverride) {
        this.parentScreen = parentScreen;
        this.config = config;
        this.keyBindingOverride = keyBindingOverride;
    }

    @Override
    public void initGui() {
        int centerX = this.width / 2;
        int startY = 40;

        this.buttonList.add(createToggleButton(ID_J_KEY_OPENS_MAP, centerX, startY,
            "xaerojourneymap.config.j_key_opens_map", config.isJKeyOpensMap()));
        this.buttonList.add(createToggleButton(ID_J_KEY_CLOSES_MAP, centerX, startY + BUTTON_SPACING,
            "xaerojourneymap.config.j_key_closes_map", config.isJKeyClosesMap()));
        this.buttonList.add(createToggleButton(ID_HIDE_ORIGINAL_MAP_KEY, centerX, startY + BUTTON_SPACING * 2,
            "xaerojourneymap.config.hide_original_map_key", config.isHideOriginalMapKey()));
        this.buttonList.add(createToggleButton(ID_HIDE_ORIGINAL_SETTINGS_KEY, centerX, startY + BUTTON_SPACING * 3,
            "xaerojourneymap.config.hide_original_settings_key", config.isHideOriginalSettingsKey()));

        int preservedStartY = startY + BUTTON_SPACING * 4 + 20;
        this.buttonList.add(createToggleButton(ID_PRESERVE_WAYPOINT_KEY, centerX, preservedStartY,
            "xaerojourneymap.config.preserve_waypoint_key", config.isPreserveWaypointKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_WAYPOINT_MENU_KEY, centerX, preservedStartY + BUTTON_SPACING,
            "xaerojourneymap.config.preserve_waypoint_menu_key", config.isPreserveWaypointMenuKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_MINIMAP_SETTINGS_KEY, centerX, preservedStartY + BUTTON_SPACING * 2,
            "xaerojourneymap.config.preserve_minimap_settings_key", config.isPreserveMinimapSettingsKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_ZOOM_KEYS, centerX, preservedStartY + BUTTON_SPACING * 3,
            "xaerojourneymap.config.preserve_zoom_keys", config.isPreserveZoomKeys()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_ENLARGE_MAP_KEY, centerX, preservedStartY + BUTTON_SPACING * 4,
            "xaerojourneymap.config.preserve_enlarge_map_key", config.isPreserveEnlargeMapKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_MINIMAP_TOGGLE_KEY, centerX, preservedStartY + BUTTON_SPACING * 5,
            "xaerojourneymap.config.preserve_minimap_toggle_key", config.isPreserveMinimapToggleKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_TEMP_WAYPOINT_KEY, centerX, preservedStartY + BUTTON_SPACING * 6,
            "xaerojourneymap.config.preserve_temp_waypoint_key", config.isPreserveTempWaypointKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_RADAR_TOGGLE_KEY, centerX, preservedStartY + BUTTON_SPACING * 7,
            "xaerojourneymap.config.preserve_radar_toggle_key", config.isPreserveRadarToggleKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_SLIME_CHUNKS_KEY, centerX, preservedStartY + BUTTON_SPACING * 8,
            "xaerojourneymap.config.preserve_slime_chunks_key", config.isPreserveSlimeChunksKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_LIGHT_OVERLAY_KEY, centerX, preservedStartY + BUTTON_SPACING * 9,
            "xaerojourneymap.config.preserve_light_overlay_key", config.isPreserveLightOverlayKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_GRID_TOGGLE_KEY, centerX, preservedStartY + BUTTON_SPACING * 10,
            "xaerojourneymap.config.preserve_grid_toggle_key", config.isPreserveGridToggleKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_WORLD_WAYPOINTS_KEY, centerX, preservedStartY + BUTTON_SPACING * 11,
            "xaerojourneymap.config.preserve_world_waypoints_key", config.isPreserveWorldWaypointsKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_MAP_WAYPOINTS_KEY, centerX, preservedStartY + BUTTON_SPACING * 12,
            "xaerojourneymap.config.preserve_map_waypoints_key", config.isPreserveMapWaypointsKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_SWITCH_WAYPOINT_SET_KEY, centerX, preservedStartY + BUTTON_SPACING * 13,
            "xaerojourneymap.config.preserve_switch_waypoint_set_key", config.isPreserveSwitchWaypointSetKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_MANUAL_CAVE_MODE_KEY, centerX, preservedStartY + BUTTON_SPACING * 14,
            "xaerojourneymap.config.preserve_manual_cave_mode_key", config.isPreserveManualCaveModeKey()));
        this.buttonList.add(createToggleButton(ID_PRESERVE_TOGGLE_DIMENSION_KEY, centerX, preservedStartY + BUTTON_SPACING * 15,
            "xaerojourneymap.config.preserve_toggle_dimension_key", config.isPreserveToggleDimensionKey()));

        this.buttonList.add(new GuiButton(ID_DONE, centerX - BUTTON_WIDTH / 2,
            this.height - 30, BUTTON_WIDTH, BUTTON_HEIGHT, I18n.format("gui.done")));
    }

    private GuiButton createToggleButton(int id, int centerX, int y, String langKey, boolean value) {
        String label = I18n.format(langKey);
        String displayText = label + ": " + (value ? "\u00a7aON" : "\u00a7cOFF");
        return new GuiButton(id, centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT, displayText);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (!button.enabled) return;

        switch (button.id) {
            case ID_J_KEY_OPENS_MAP:
                config.setJKeyOpensMap(!config.isJKeyOpensMap());
                break;
            case ID_J_KEY_CLOSES_MAP:
                config.setJKeyClosesMap(!config.isJKeyClosesMap());
                break;
            case ID_HIDE_ORIGINAL_MAP_KEY:
                config.setHideOriginalMapKey(!config.isHideOriginalMapKey());
                break;
            case ID_HIDE_ORIGINAL_SETTINGS_KEY:
                config.setHideOriginalSettingsKey(!config.isHideOriginalSettingsKey());
                break;
            case ID_PRESERVE_WAYPOINT_KEY:
                config.setPreserveWaypointKey(!config.isPreserveWaypointKey());
                break;
            case ID_PRESERVE_MINIMAP_SETTINGS_KEY:
                config.setPreserveMinimapSettingsKey(!config.isPreserveMinimapSettingsKey());
                break;
            case ID_PRESERVE_WAYPOINT_MENU_KEY:
                config.setPreserveWaypointMenuKey(!config.isPreserveWaypointMenuKey());
                break;
            case ID_PRESERVE_ZOOM_KEYS:
                config.setPreserveZoomKeys(!config.isPreserveZoomKeys());
                break;
            case ID_PRESERVE_ENLARGE_MAP_KEY:
                config.setPreserveEnlargeMapKey(!config.isPreserveEnlargeMapKey());
                break;
            case ID_PRESERVE_MINIMAP_TOGGLE_KEY:
                config.setPreserveMinimapToggleKey(!config.isPreserveMinimapToggleKey());
                break;
            case ID_PRESERVE_TEMP_WAYPOINT_KEY:
                config.setPreserveTempWaypointKey(!config.isPreserveTempWaypointKey());
                break;
            case ID_PRESERVE_RADAR_TOGGLE_KEY:
                config.setPreserveRadarToggleKey(!config.isPreserveRadarToggleKey());
                break;
            case ID_PRESERVE_SLIME_CHUNKS_KEY:
                config.setPreserveSlimeChunksKey(!config.isPreserveSlimeChunksKey());
                break;
            case ID_PRESERVE_LIGHT_OVERLAY_KEY:
                config.setPreserveLightOverlayKey(!config.isPreserveLightOverlayKey());
                break;
            case ID_PRESERVE_GRID_TOGGLE_KEY:
                config.setPreserveGridToggleKey(!config.isPreserveGridToggleKey());
                break;
            case ID_PRESERVE_WORLD_WAYPOINTS_KEY:
                config.setPreserveWorldWaypointsKey(!config.isPreserveWorldWaypointsKey());
                break;
            case ID_PRESERVE_MAP_WAYPOINTS_KEY:
                config.setPreserveMapWaypointsKey(!config.isPreserveMapWaypointsKey());
                break;
            case ID_PRESERVE_SWITCH_WAYPOINT_SET_KEY:
                config.setPreserveSwitchWaypointSetKey(!config.isPreserveSwitchWaypointSetKey());
                break;
            case ID_PRESERVE_MANUAL_CAVE_MODE_KEY:
                config.setPreserveManualCaveModeKey(!config.isPreserveManualCaveModeKey());
                break;
            case ID_PRESERVE_TOGGLE_DIMENSION_KEY:
                config.setPreserveToggleDimensionKey(!config.isPreserveToggleDimensionKey());
                break;
            case ID_DONE:
                config.save();
                if (keyBindingOverride != null) {
                    keyBindingOverride.reapplyOverrides();
                }
                this.mc.displayGuiScreen(parentScreen);
                return;
        }

        this.buttonList.clear();
        this.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer,
            I18n.format("xaerojourneymap.config.title"),
            this.width / 2, 12, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer,
            I18n.format("xaerojourneymap.config.general_settings"),
            this.width / 2, 28, 0xAAAAAA);

        int preservedStartY = 40 + BUTTON_SPACING * 4 + 8;
        this.drawCenteredString(this.fontRenderer,
            I18n.format("xaerojourneymap.config.preserved_keys"),
            this.width / 2, preservedStartY, 0xAAAAAA);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            config.save();
            if (keyBindingOverride != null) {
                keyBindingOverride.reapplyOverrides();
            }
            this.mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
