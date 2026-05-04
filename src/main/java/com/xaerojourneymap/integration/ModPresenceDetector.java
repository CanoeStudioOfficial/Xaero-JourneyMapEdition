package com.xaerojourneymap.integration;

import net.minecraftforge.fml.common.Loader;

public class ModPresenceDetector {

    private static Boolean xaeroMinimapLoaded;
    private static Boolean xaeroWorldMapLoaded;
    private static Boolean xaeroLibLoaded;
    private static Boolean journeyMapLoaded;

    public static boolean isXaeroMinimapLoaded() {
        if (xaeroMinimapLoaded == null) {
            xaeroMinimapLoaded = Loader.isModLoaded("xaerominimap");
        }
        return xaeroMinimapLoaded;
    }

    public static boolean isXaeroWorldMapLoaded() {
        if (xaeroWorldMapLoaded == null) {
            xaeroWorldMapLoaded = Loader.isModLoaded("xaeroworldmap");
        }
        return xaeroWorldMapLoaded;
    }

    public static boolean isXaeroLibLoaded() {
        if (xaeroLibLoaded == null) {
            xaeroLibLoaded = Loader.isModLoaded("xaerolib");
        }
        return xaeroLibLoaded;
    }

    public static boolean isJourneyMapLoaded() {
        if (journeyMapLoaded == null) {
            journeyMapLoaded = Loader.isModLoaded("journeymap");
        }
        return journeyMapLoaded;
    }

    public static void resetCache() {
        xaeroMinimapLoaded = null;
        xaeroWorldMapLoaded = null;
        xaeroLibLoaded = null;
        journeyMapLoaded = null;
    }
}
