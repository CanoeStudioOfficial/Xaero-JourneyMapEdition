package com.xaerojourneymap.config;

import com.xaerojourneymap.XaeroJourneyMapMod;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ModConfig {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_KEY_OVERRIDE = "key_override";
    private static final String CATEGORY_PRESERVED_KEYS = "preserved_keys";

    private Configuration config;
    private File configDir;

    private boolean jKeyOpensMap;
    private boolean jKeyClosesMap;
    private boolean hideOriginalMapKey;
    private boolean hideOriginalSettingsKey;
    private boolean preserveWaypointKey;
    private boolean preserveMinimapToggleKey;
    private boolean preserveMinimapSettingsKey;
    private boolean preserveZoomKeys;
    private boolean preserveWaypointMenuKey;
    private boolean preserveEnlargeMapKey;
    private boolean preserveToggleDimensionKey;
    private boolean preserveRadarToggleKey;
    private boolean preserveSlimeChunksKey;
    private boolean preserveTempWaypointKey;
    private boolean preserveSwitchWaypointSetKey;
    private boolean preserveLightOverlayKey;
    private boolean preserveManualCaveModeKey;
    private boolean preserveGridToggleKey;
    private boolean preserveWorldWaypointsKey;
    private boolean preserveMapWaypointsKey;

    public ModConfig(File configDir) {
        this.configDir = configDir;
        this.config = new Configuration(new File(configDir, "xaerojourneymap.cfg"));
    }

    public void load() {
        config.load();

        jKeyOpensMap = config.getBoolean("jKeyOpensMap", CATEGORY_GENERAL, true,
            "Whether the J key opens the Xaero World Map (like JourneyMap's J key)");
        jKeyClosesMap = config.getBoolean("jKeyClosesMap", CATEGORY_GENERAL, true,
            "Whether the J key closes the World Map when it is open");
        hideOriginalMapKey = config.getBoolean("hideOriginalMapKey", CATEGORY_KEY_OVERRIDE, true,
            "Whether to hide Xaero's original M key binding for opening the World Map");
        hideOriginalSettingsKey = config.getBoolean("hideOriginalSettingsKey", CATEGORY_KEY_OVERRIDE, false,
            "Whether to hide Xaero's original ] key binding for opening World Map settings");

        preserveWaypointKey = config.getBoolean("preserveWaypointKey", CATEGORY_PRESERVED_KEYS, true,
            "Whether to preserve the B key for creating waypoints (Xaero's Minimap)");
        preserveMinimapToggleKey = config.getBoolean("preserveMinimapToggleKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the minimap toggle key binding");
        preserveMinimapSettingsKey = config.getBoolean("preserveMinimapSettingsKey", CATEGORY_PRESERVED_KEYS, true,
            "Whether to preserve the Y key for minimap settings");
        preserveZoomKeys = config.getBoolean("preserveZoomKeys", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve zoom in/out key bindings");
        preserveWaypointMenuKey = config.getBoolean("preserveWaypointMenuKey", CATEGORY_PRESERVED_KEYS, true,
            "Whether to preserve the U key for waypoint menu");
        preserveEnlargeMapKey = config.getBoolean("preserveEnlargeMapKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the Z key for enlarging the minimap");
        preserveToggleDimensionKey = config.getBoolean("preserveToggleDimensionKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the dimension toggle key binding");
        preserveRadarToggleKey = config.getBoolean("preserveRadarToggleKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the entity radar toggle key binding");
        preserveSlimeChunksKey = config.getBoolean("preserveSlimeChunksKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the slime chunks toggle key binding");
        preserveTempWaypointKey = config.getBoolean("preserveTempWaypointKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the N key for temporary waypoints");
        preserveSwitchWaypointSetKey = config.getBoolean("preserveSwitchWaypointSetKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the waypoint set switching key binding");
        preserveLightOverlayKey = config.getBoolean("preserveLightOverlayKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the light overlay toggle key binding");
        preserveManualCaveModeKey = config.getBoolean("preserveManualCaveModeKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the manual cave mode toggle key binding");
        preserveGridToggleKey = config.getBoolean("preserveGridToggleKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the grid toggle key binding");
        preserveWorldWaypointsKey = config.getBoolean("preserveWorldWaypointsKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the world waypoints toggle key binding");
        preserveMapWaypointsKey = config.getBoolean("preserveMapWaypointsKey", CATEGORY_PRESERVED_KEYS, false,
            "Whether to preserve the map waypoints toggle key binding");

        config.save();
        XaeroJourneyMapMod.LOGGER.info("Configuration loaded");
    }

    public void save() {
        config.getCategory(CATEGORY_GENERAL).get("jKeyOpensMap").set(jKeyOpensMap);
        config.getCategory(CATEGORY_GENERAL).get("jKeyClosesMap").set(jKeyClosesMap);
        config.getCategory(CATEGORY_KEY_OVERRIDE).get("hideOriginalMapKey").set(hideOriginalMapKey);
        config.getCategory(CATEGORY_KEY_OVERRIDE).get("hideOriginalSettingsKey").set(hideOriginalSettingsKey);

        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveWaypointKey").set(preserveWaypointKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveMinimapToggleKey").set(preserveMinimapToggleKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveMinimapSettingsKey").set(preserveMinimapSettingsKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveZoomKeys").set(preserveZoomKeys);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveWaypointMenuKey").set(preserveWaypointMenuKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveEnlargeMapKey").set(preserveEnlargeMapKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveToggleDimensionKey").set(preserveToggleDimensionKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveRadarToggleKey").set(preserveRadarToggleKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveSlimeChunksKey").set(preserveSlimeChunksKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveTempWaypointKey").set(preserveTempWaypointKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveSwitchWaypointSetKey").set(preserveSwitchWaypointSetKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveLightOverlayKey").set(preserveLightOverlayKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveManualCaveModeKey").set(preserveManualCaveModeKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveGridToggleKey").set(preserveGridToggleKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveWorldWaypointsKey").set(preserveWorldWaypointsKey);
        config.getCategory(CATEGORY_PRESERVED_KEYS).get("preserveMapWaypointsKey").set(preserveMapWaypointsKey);

        config.save();
        XaeroJourneyMapMod.LOGGER.info("Configuration saved");
    }

    public boolean isJKeyOpensMap() { return jKeyOpensMap; }
    public void setJKeyOpensMap(boolean v) { this.jKeyOpensMap = v; }
    public boolean isJKeyClosesMap() { return jKeyClosesMap; }
    public void setJKeyClosesMap(boolean v) { this.jKeyClosesMap = v; }
    public boolean isHideOriginalMapKey() { return hideOriginalMapKey; }
    public void setHideOriginalMapKey(boolean v) { this.hideOriginalMapKey = v; }
    public boolean isHideOriginalSettingsKey() { return hideOriginalSettingsKey; }
    public void setHideOriginalSettingsKey(boolean v) { this.hideOriginalSettingsKey = v; }
    public boolean isPreserveWaypointKey() { return preserveWaypointKey; }
    public void setPreserveWaypointKey(boolean v) { this.preserveWaypointKey = v; }
    public boolean isPreserveMinimapToggleKey() { return preserveMinimapToggleKey; }
    public void setPreserveMinimapToggleKey(boolean v) { this.preserveMinimapToggleKey = v; }
    public boolean isPreserveMinimapSettingsKey() { return preserveMinimapSettingsKey; }
    public void setPreserveMinimapSettingsKey(boolean v) { this.preserveMinimapSettingsKey = v; }
    public boolean isPreserveZoomKeys() { return preserveZoomKeys; }
    public void setPreserveZoomKeys(boolean v) { this.preserveZoomKeys = v; }
    public boolean isPreserveWaypointMenuKey() { return preserveWaypointMenuKey; }
    public void setPreserveWaypointMenuKey(boolean v) { this.preserveWaypointMenuKey = v; }
    public boolean isPreserveEnlargeMapKey() { return preserveEnlargeMapKey; }
    public void setPreserveEnlargeMapKey(boolean v) { this.preserveEnlargeMapKey = v; }
    public boolean isPreserveToggleDimensionKey() { return preserveToggleDimensionKey; }
    public void setPreserveToggleDimensionKey(boolean v) { this.preserveToggleDimensionKey = v; }
    public boolean isPreserveRadarToggleKey() { return preserveRadarToggleKey; }
    public void setPreserveRadarToggleKey(boolean v) { this.preserveRadarToggleKey = v; }
    public boolean isPreserveSlimeChunksKey() { return preserveSlimeChunksKey; }
    public void setPreserveSlimeChunksKey(boolean v) { this.preserveSlimeChunksKey = v; }
    public boolean isPreserveTempWaypointKey() { return preserveTempWaypointKey; }
    public void setPreserveTempWaypointKey(boolean v) { this.preserveTempWaypointKey = v; }
    public boolean isPreserveSwitchWaypointSetKey() { return preserveSwitchWaypointSetKey; }
    public void setPreserveSwitchWaypointSetKey(boolean v) { this.preserveSwitchWaypointSetKey = v; }
    public boolean isPreserveLightOverlayKey() { return preserveLightOverlayKey; }
    public void setPreserveLightOverlayKey(boolean v) { this.preserveLightOverlayKey = v; }
    public boolean isPreserveManualCaveModeKey() { return preserveManualCaveModeKey; }
    public void setPreserveManualCaveModeKey(boolean v) { this.preserveManualCaveModeKey = v; }
    public boolean isPreserveGridToggleKey() { return preserveGridToggleKey; }
    public void setPreserveGridToggleKey(boolean v) { this.preserveGridToggleKey = v; }
    public boolean isPreserveWorldWaypointsKey() { return preserveWorldWaypointsKey; }
    public void setPreserveWorldWaypointsKey(boolean v) { this.preserveWorldWaypointsKey = v; }
    public boolean isPreserveMapWaypointsKey() { return preserveMapWaypointsKey; }
    public void setPreserveMapWaypointsKey(boolean v) { this.preserveMapWaypointsKey = v; }
}
