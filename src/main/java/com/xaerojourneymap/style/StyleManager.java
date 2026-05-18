package com.xaerojourneymap.style;

import com.xaerojourneymap.XaeroJourneyMapMod;
import com.xaerojourneymap.config.ModConfig;

public class StyleManager {

    private VisualStyle currentStyle;
    private final ModConfig config;

    public StyleManager(ModConfig config) {
        this.config = config;
        this.currentStyle = VisualStyle.fromKey(config.getVisualStyle());
    }

    public void initialize() {
        applyStyle(currentStyle);
    }

    public void applyStyle(VisualStyle style) {
        this.currentStyle = style;
        config.setVisualStyle(style.getKey());
        config.save();
        XaeroJourneyMapMod.LOGGER.info("Applied UI style: {}", style.name());
    }

    public void toggleStyle() {
        VisualStyle next = currentStyle.toggle();
        applyStyle(next);
    }

    public VisualStyle getCurrentStyle() {
        return currentStyle;
    }

    public boolean isJourneyMapStyle() {
        return currentStyle == VisualStyle.JOURNEYMAP;
    }

    public boolean isXaeroStyle() {
        return currentStyle == VisualStyle.XAERO;
    }
}
