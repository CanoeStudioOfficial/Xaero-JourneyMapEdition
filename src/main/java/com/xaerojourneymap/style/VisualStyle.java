package com.xaerojourneymap.style;

public enum VisualStyle {

    JOURNEYMAP("journeymap"),
    XAERO("xaero");

    private final String key;

    VisualStyle(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static VisualStyle fromKey(String key) {
        for (VisualStyle style : values()) {
            if (style.key.equalsIgnoreCase(key)) {
                return style;
            }
        }
        return XAERO;
    }

    public VisualStyle toggle() {
        return this == JOURNEYMAP ? XAERO : JOURNEYMAP;
    }
}
