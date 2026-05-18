package com.xaerojourneymap.style.overlay;

import com.xaerojourneymap.integration.XaeroWorldMapHelper;
import com.xaerojourneymap.style.StyleManager;
import com.xaerojourneymap.style.UIStyleConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MapStyleOverlay {

    private static final ResourceLocation WIDGETS_TEXTURE = new ResourceLocation("textures/gui/widgets.png");

    private final StyleManager styleManager;
    private final List<OverlayButton> overlayButtons = new ArrayList<>();

    private boolean mapTypeDay = true;
    private boolean mapTypeNight = false;
    private boolean mapTypeTopo = false;
    private boolean mapTypeCave = false;
    private boolean showGrid = true;
    private boolean showMobs = false;
    private boolean showAnimals = false;
    private boolean showPets = false;
    private boolean showVillagers = false;
    private boolean showPlayers = false;
    private boolean showKeys = false;
    private boolean following = true;

    private String blockInfoText = "";

    public MapStyleOverlay(StyleManager styleManager) {
        this.styleManager = styleManager;
    }

    @SubscribeEvent
    public void onGuiDraw(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (!styleManager.isJourneyMapStyle()) return;

        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen screen = mc.currentScreen;
        if (screen == null) return;
        if (!XaeroWorldMapHelper.isGuiMap(screen)) return;

        hideXaeroButtons(screen);

        overlayButtons.clear();
        int mouseX = event.getMouseX();
        int mouseY = event.getMouseY();

        drawMapTypeToolbar(mc, screen, mouseX, mouseY);
        drawOptionsToolbar(mc, screen, mouseX, mouseY);
        drawLeftToolbar(mc, screen, mouseX, mouseY);
        drawCloseButton(mc, screen, mouseX, mouseY);
        drawMenuToolbar(mc, screen, mouseX, mouseY);
        drawPlayerInfoLabel(mc, screen);
        drawBlockInfoLabel(mc, screen);
        drawKeybindingInfo(mc, screen, mouseX, mouseY);
    }

    @SubscribeEvent
    public void onGuiMouseClick(GuiScreenEvent.MouseInputEvent.Pre event) {
        if (!styleManager.isJourneyMapStyle()) return;

        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen screen = mc.currentScreen;
        if (screen == null || !XaeroWorldMapHelper.isGuiMap(screen)) return;

        int mouseX = Mouse.getEventX() * screen.width / mc.displayWidth;
        int mouseY = screen.height - Mouse.getEventY() * screen.height / mc.displayHeight - 1;

        for (OverlayButton btn : overlayButtons) {
            if (btn.isHovered(mouseX, mouseY)) {
                handleButtonClick(btn.id, mc, screen);
                event.setCanceled(true);
                return;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void hideXaeroButtons(GuiScreen screen) {
        try {
            Field buttonListField = GuiScreen.class.getDeclaredField("buttonList");
            buttonListField.setAccessible(true);
            List<GuiButton> buttons = (List<GuiButton>) buttonListField.get(screen);
            for (GuiButton btn : buttons) {
                btn.visible = false;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                Field buttonListField = GuiScreen.class.getDeclaredField("field_146292_n");
                buttonListField.setAccessible(true);
                List<GuiButton> buttons = (List<GuiButton>) buttonListField.get(screen);
                for (GuiButton btn : buttons) {
                    btn.visible = false;
                }
            } catch (Exception ignored) {
            }
        }
    }

    private void drawMapTypeToolbar(Minecraft mc, GuiScreen screen, int mouseX, int mouseY) {
        int iconSize = UIStyleConstants.ICON_SIZE;
        int hgap = UIStyleConstants.TOOLBAR_HGAP;
        int margin = UIStyleConstants.TOOLBAR_MARGIN;

        int totalWidth = iconSize * 4 + hgap * 3;
        int startX = screen.width / 2 - totalWidth / 2 - iconSize - hgap - margin;
        int y = margin;

        addMapTypeToggle(mc, startX, y, iconSize, I18n.format("xaerojourneymap.ui.maptype.day"),
            mapTypeDay, "xaerojourneymap.ui.maptype.day", 10, mouseX, mouseY);
        startX += iconSize + hgap;
        addMapTypeToggle(mc, startX, y, iconSize, I18n.format("xaerojourneymap.ui.maptype.night"),
            mapTypeNight, "xaerojourneymap.ui.maptype.night", 11, mouseX, mouseY);
        startX += iconSize + hgap;
        addMapTypeToggle(mc, startX, y, iconSize, I18n.format("xaerojourneymap.ui.maptype.topo"),
            mapTypeTopo, "xaerojourneymap.ui.maptype.topo", 12, mouseX, mouseY);
        startX += iconSize + hgap;
        addMapTypeToggle(mc, startX, y, iconSize, I18n.format("xaerojourneymap.ui.maptype.cave"),
            mapTypeCave, "xaerojourneymap.ui.maptype.cave", 13, mouseX, mouseY);
    }

    private void drawOptionsToolbar(Minecraft mc, GuiScreen screen, int mouseX, int mouseY) {
        int iconSize = UIStyleConstants.ICON_SIZE;
        int hgap = UIStyleConstants.TOOLBAR_HGAP;
        int margin = UIStyleConstants.TOOLBAR_MARGIN;

        int count = 6;
        int totalWidth = iconSize * count + hgap * (count - 1);
        int startX = screen.width / 2 - totalWidth / 2 + iconSize + hgap + margin;
        int y = margin;

        addOptionToggle(mc, startX, y, iconSize, I18n.format("xaerojourneymap.ui.option.mobs"),
            showMobs, "xaerojourneymap.ui.option.mobs", 21, mouseX, mouseY);
        startX += iconSize + hgap;
        addOptionToggle(mc, startX, y, iconSize, I18n.format("xaerojourneymap.ui.option.animals"),
            showAnimals, "xaerojourneymap.ui.option.animals", 23, mouseX, mouseY);
        startX += iconSize + hgap;
        addOptionToggle(mc, startX, y, iconSize, I18n.format("xaerojourneymap.ui.option.players"),
            showPlayers, "xaerojourneymap.ui.option.players", 22, mouseX, mouseY);
        startX += iconSize + hgap;
        addOptionToggle(mc, startX, y, iconSize, I18n.format("xaerojourneymap.ui.option.grid"),
            showGrid, "xaerojourneymap.ui.option.grid", 20, mouseX, mouseY);
        startX += iconSize + hgap;
        addOptionToggle(mc, startX, y, iconSize, I18n.format("xaerojourneymap.ui.option.keys"),
            showKeys, "xaerojourneymap.ui.option.keys", 33, mouseX, mouseY);
    }

    private void drawLeftToolbar(Minecraft mc, GuiScreen screen, int mouseX, int mouseY) {
        int iconSize = UIStyleConstants.ICON_SIZE;
        int padding = UIStyleConstants.TOOLBAR_PADDING;
        int margin = UIStyleConstants.TOOLBAR_MARGIN;

        int x = margin;
        int centerY = screen.height / 2;
        int totalHeight = iconSize * 4 + padding * 3;
        int startY = centerY - totalHeight / 2;

        addActionButton(mc, x, startY, iconSize, I18n.format("xaerojourneymap.ui.search"),
            "xaerojourneymap.ui.search", 34, mouseX, mouseY);
        startY += iconSize + padding;
        addActionButton(mc, x, startY, iconSize, I18n.format("xaerojourneymap.ui.follow"),
            "xaerojourneymap.ui.follow", 32, mouseX, mouseY, following);
        startY += iconSize + padding;
        addActionButton(mc, x, startY, iconSize, I18n.format("xaerojourneymap.ui.zoom_in"),
            "xaerojourneymap.ui.zoom_in", 30, mouseX, mouseY);
        startY += iconSize + padding;
        addActionButton(mc, x, startY, iconSize, I18n.format("xaerojourneymap.ui.zoom_out"),
            "xaerojourneymap.ui.zoom_out", 31, mouseX, mouseY);
    }

    private void drawCloseButton(Minecraft mc, GuiScreen screen, int mouseX, int mouseY) {
        int iconSize = UIStyleConstants.ICON_SIZE;
        int margin = UIStyleConstants.TOOLBAR_MARGIN;

        int x = screen.width - iconSize - margin;
        int y = margin;

        OverlayButton closeBtn = new OverlayButton(90, x, y, iconSize, iconSize,
            I18n.format("xaerojourneymap.ui.close"), false, "xaerojourneymap.ui.close");
        closeBtn.bgColor = UIStyleConstants.CLOSE_BTN_BG;
        closeBtn.fgColor = UIStyleConstants.CLOSE_BTN_FG;
        closeBtn.hoverBgColor = UIStyleConstants.CLOSE_BTN_HOVER_BG;
        closeBtn.hoverFgColor = UIStyleConstants.CLOSE_BTN_HOVER_FG;
        closeBtn.style = ButtonStyle.ACTION;
        drawNativeStyleButton(mc, closeBtn, mouseX, mouseY);
        overlayButtons.add(closeBtn);
    }

    private void drawMenuToolbar(Minecraft mc, GuiScreen screen, int mouseX, int mouseY) {
        int iconSize = UIStyleConstants.ICON_SIZE;
        int padding = UIStyleConstants.TOOLBAR_PADDING;
        int margin = UIStyleConstants.TOOLBAR_MARGIN;

        String[] labels = {
            I18n.format("xaerojourneymap.ui.waypoints"),
            I18n.format("xaerojourneymap.ui.options"),
            I18n.format("xaerojourneymap.ui.about"),
            I18n.format("xaerojourneymap.ui.theme")
        };
        int[] ids = {40, 41, 42, 43};

        int btnW = 60;
        int btnH = iconSize;
        int totalWidth = labels.length * btnW + (labels.length - 1) * padding;
        int startX = screen.width / 2 - totalWidth / 2;
        int y = screen.height - btnH - margin;

        for (int i = 0; i < labels.length; i++) {
            OverlayButton btn = new OverlayButton(ids[i], startX, y, btnW, btnH,
                labels[i], false, labels[i]);
            btn.bgColor = UIStyleConstants.BTN_OFF_BG;
            btn.fgColor = UIStyleConstants.BTN_OFF_FG;
            btn.hoverBgColor = UIStyleConstants.BTN_HOVER_BG;
            btn.hoverFgColor = UIStyleConstants.BTN_HOVER_FG;
            btn.style = ButtonStyle.ACTION;
            drawNativeStyleButton(mc, btn, mouseX, mouseY);
            overlayButtons.add(btn);
            startX += btnW + padding;
        }
    }

    private void drawPlayerInfoLabel(Minecraft mc, GuiScreen screen) {
        if (mc.player == null) return;

        String playerName = mc.player.getName();
        String coords = String.format("X:%d Y:%d Z:%d",
            (int) mc.player.posX, (int) mc.player.posY, (int) mc.player.posZ);
        String biome = mc.player.getEntityWorld().getBiome(mc.player.getPosition()).getBiomeName();
        String text = playerName + " \u25a0 " + coords + " " + biome;

        int textWidth = mc.fontRenderer.getStringWidth(text);
        int x = screen.width / 2;
        int y = UIStyleConstants.ICON_SIZE + UIStyleConstants.TOOLBAR_MARGIN * 2 + 4;

        drawInfoLabel(mc, text, x, y, textWidth, true);
    }

    private void drawBlockInfoLabel(Minecraft mc, GuiScreen screen) {
        if (blockInfoText.isEmpty()) return;

        int textWidth = mc.fontRenderer.getStringWidth(blockInfoText);
        int x = screen.width / 2;
        int y = screen.height - UIStyleConstants.ICON_SIZE - UIStyleConstants.TOOLBAR_MARGIN * 2 - 12;

        drawInfoLabel(mc, blockInfoText, x, y, textWidth, false);
    }

    private void drawKeybindingInfo(Minecraft mc, GuiScreen screen, int mouseX, int mouseY) {
        if (!showKeys) return;

        int pad = 10;
        int lineH = mc.fontRenderer.FONT_HEIGHT + 3;

        String[][] keyInfo = {
            {"J", I18n.format("xaerojourneymap.ui.key.toggle_map")},
            {"B", I18n.format("xaerojourneymap.ui.key.waypoint")},
            {"+", I18n.format("xaerojourneymap.ui.key.zoom")},
            {"Esc", I18n.format("xaerojourneymap.ui.key.close")},
        };

        int keyNameW = 0;
        int keyDescW = 0;
        for (String[] info : keyInfo) {
            keyNameW = Math.max(keyNameW, mc.fontRenderer.getStringWidth(info[0]));
            keyDescW = Math.max(keyDescW, mc.fontRenderer.getStringWidth(info[1]));
        }

        int panelW = keyNameW + keyDescW + pad * 4;
        int panelH = keyInfo.length * lineH + pad;
        int panelX = screen.width - UIStyleConstants.TOOLBAR_MARGIN - panelW;
        int panelY = screen.height - UIStyleConstants.ICON_SIZE - UIStyleConstants.TOOLBAR_MARGIN * 2 - panelH - 16;

        boolean hovered = mouseX >= panelX && mouseX < panelX + panelW
            && mouseY >= panelY && mouseY < panelY + panelH;
        float bgAlpha = hovered ? 0.2f : 0.7f;
        float fgAlpha = hovered ? 0.2f : 1.0f;

        int bgColor = ((int)(bgAlpha * 255) << 24) | 0x222222;

        GuiScreen.drawRect(panelX, panelY, panelX + panelW, panelY + panelH, bgColor);

        int textX = panelX + pad;
        int lineY = panelY + pad / 2;
        for (String[] info : keyInfo) {
            int fgColor = ((int)(fgAlpha * 255) << 24) | 0xFFFFFF;
            int descColor = ((int)(fgAlpha * 255) << 24) | 0xDDDDDD;
            mc.fontRenderer.drawStringWithShadow(info[0], textX, lineY, fgColor);
            mc.fontRenderer.drawStringWithShadow(info[1], textX + keyNameW + pad, lineY, descColor);
            lineY += lineH;
        }
    }

    private void addMapTypeToggle(Minecraft mc, int x, int y, int size, String label,
                                  boolean active, String tooltip, int id, int mouseX, int mouseY) {
        OverlayButton btn = new OverlayButton(id, x, y, size, size, label, active, tooltip);
        btn.style = ButtonStyle.MAP_TYPE;
        btn.bgColor = active ? UIStyleConstants.TOGGLE_ON_BG : UIStyleConstants.TOGGLE_OFF_BG;
        btn.fgColor = active ? UIStyleConstants.TOGGLE_ON_FG : UIStyleConstants.TOGGLE_OFF_FG;
        btn.hoverBgColor = active ? UIStyleConstants.TOGGLE_HOVER_ON_BG : UIStyleConstants.TOGGLE_HOVER_OFF_BG;
        btn.hoverFgColor = active ? UIStyleConstants.TOGGLE_HOVER_ON_FG : UIStyleConstants.TOGGLE_HOVER_OFF_FG;
        drawNativeStyleButton(mc, btn, mouseX, mouseY);
        overlayButtons.add(btn);
    }

    private void addOptionToggle(Minecraft mc, int x, int y, int size, String label,
                                 boolean active, String tooltip, int id, int mouseX, int mouseY) {
        OverlayButton btn = new OverlayButton(id, x, y, size, size, label, active, tooltip);
        btn.style = ButtonStyle.OPTION;
        btn.bgColor = active ? UIStyleConstants.TOGGLE_ON_BG : UIStyleConstants.TOGGLE_OFF_BG;
        btn.fgColor = active ? UIStyleConstants.TOGGLE_ON_FG : UIStyleConstants.TOGGLE_OFF_FG;
        btn.hoverBgColor = active ? UIStyleConstants.TOGGLE_HOVER_ON_BG : UIStyleConstants.TOGGLE_HOVER_OFF_BG;
        btn.hoverFgColor = active ? UIStyleConstants.TOGGLE_HOVER_ON_FG : UIStyleConstants.TOGGLE_HOVER_OFF_FG;
        drawNativeStyleButton(mc, btn, mouseX, mouseY);
        overlayButtons.add(btn);
    }

    private void addActionButton(Minecraft mc, int x, int y, int size, String label,
                                 String tooltip, int id, int mouseX, int mouseY) {
        addActionButton(mc, x, y, size, label, tooltip, id, mouseX, mouseY, false);
    }

    private void addActionButton(Minecraft mc, int x, int y, int size, String label,
                                 String tooltip, int id, int mouseX, int mouseY, boolean toggled) {
        OverlayButton btn = new OverlayButton(id, x, y, size, size, label, toggled, tooltip);
        btn.style = ButtonStyle.ACTION;
        btn.bgColor = toggled ? UIStyleConstants.BTN_ON_BG : UIStyleConstants.BTN_OFF_BG;
        btn.fgColor = toggled ? UIStyleConstants.BTN_ON_FG : UIStyleConstants.BTN_OFF_FG;
        btn.hoverBgColor = UIStyleConstants.BTN_HOVER_BG;
        btn.hoverFgColor = UIStyleConstants.BTN_HOVER_FG;
        drawNativeStyleButton(mc, btn, mouseX, mouseY);
        overlayButtons.add(btn);
    }

    private void drawNativeStyleButton(Minecraft mc, OverlayButton btn, int mouseX, int mouseY) {
        boolean hovered = btn.isHovered(mouseX, mouseY);
        int bg = hovered ? btn.hoverBgColor : btn.bgColor;
        int fg = hovered ? btn.hoverFgColor : btn.fgColor;

        float r = ((bg >> 16) & 0xFF) / 255.0f;
        float g = ((bg >> 8) & 0xFF) / 255.0f;
        float b = (bg & 0xFF) / 255.0f;

        int hoverState = hovered ? 2 : (btn.active ? 1 : 0);
        int texY = 46 + hoverState * 20;

        mc.getTextureManager().bindTexture(WIDGETS_TEXTURE);
        GlStateManager.color(r, g, b, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO);

        int halfW = btn.w / 2;
        int halfW2 = btn.w - halfW;
        mc.ingameGUI.drawTexturedModalRect(btn.x, btn.y, 0, texY, halfW, btn.h);
        mc.ingameGUI.drawTexturedModalRect(btn.x + halfW, btn.y, 200 - halfW2, texY, halfW2, btn.h);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();

        int textWidth = mc.fontRenderer.getStringWidth(btn.label);
        int textX = btn.x + (btn.w - textWidth) / 2;
        int textY = btn.y + (btn.h - 8) / 2;
        mc.fontRenderer.drawStringWithShadow(btn.label, textX, textY, fg);

        if (hovered && btn.tooltip != null) {
            drawTooltip(mc, I18n.format(btn.tooltip), mouseX, mouseY);
        }
    }

    private void drawTooltip(Minecraft mc, String text, int x, int y) {
        int textWidth = mc.fontRenderer.getStringWidth(text);
        int padding = 4;
        int bgLeft = x + 12;
        int bgTop = y - 12;
        int bgRight = bgLeft + textWidth + padding * 2;
        int bgBottom = bgTop + mc.fontRenderer.FONT_HEIGHT + padding;

        if (bgRight > Minecraft.getMinecraft().currentScreen.width) {
            bgLeft = x - textWidth - padding * 2 - 12;
            bgRight = bgLeft + textWidth + padding * 2;
        }

        net.minecraft.client.gui.Gui.drawRect(bgLeft - 1, bgTop - 1, bgRight + 1, bgBottom + 1, 0xA0555555);
        net.minecraft.client.gui.Gui.drawRect(bgLeft, bgTop, bgRight, bgBottom, 0xE0222222);
        mc.fontRenderer.drawStringWithShadow(text, bgLeft + padding, bgTop + padding, 0xFFAAAAFF);
    }

    private void drawInfoLabel(Minecraft mc, String text, int x, int y, int textWidth, boolean isPlayerInfo) {
        int padding = 4;
        int bgLeft = x - textWidth / 2 - padding;
        int bgRight = x + textWidth / 2 + padding;
        int bgTop = y - 2;
        int bgBottom = y + mc.fontRenderer.FONT_HEIGHT + 2;

        net.minecraft.client.gui.Gui.drawRect(bgLeft, bgTop, bgRight, bgBottom, UIStyleConstants.LABEL_BG_COLOR);
        mc.fontRenderer.drawStringWithShadow(text, x - textWidth / 2, y, UIStyleConstants.LABEL_FG_COLOR);
    }

    private void handleButtonClick(int id, Minecraft mc, GuiScreen screen) {
        switch (id) {
            case 10:
                mapTypeDay = true; mapTypeNight = false; mapTypeTopo = false; mapTypeCave = false;
                break;
            case 11:
                mapTypeDay = false; mapTypeNight = true; mapTypeTopo = false; mapTypeCave = false;
                break;
            case 12:
                mapTypeDay = false; mapTypeNight = false; mapTypeTopo = true; mapTypeCave = false;
                break;
            case 13:
                mapTypeDay = false; mapTypeNight = false; mapTypeTopo = false; mapTypeCave = true;
                break;
            case 20: showGrid = !showGrid; break;
            case 21: showMobs = !showMobs; break;
            case 22: showPlayers = !showPlayers; break;
            case 23: showAnimals = !showAnimals; break;
            case 30: break;
            case 31: break;
            case 32: following = !following; break;
            case 33: showKeys = !showKeys; break;
            case 34: break;
            case 40:
                try {
                    Class<?> waypointClass = Class.forName("xaero.map.gui.GuiWaypoints");
                    java.lang.reflect.Constructor<?> ctor = waypointClass.getConstructor(
                        net.minecraft.client.gui.GuiScreen.class,
                        net.minecraft.client.gui.GuiScreen.class
                    );
                    Object gui = ctor.newInstance(screen, null);
                    mc.displayGuiScreen((GuiScreen) gui);
                } catch (Exception e) {
                    try {
                        Class<?> waypointClass = Class.forName("xaero.minimap.gui.GuiWaypoints");
                        java.lang.reflect.Constructor<?> ctor = waypointClass.getConstructor(
                            net.minecraft.client.gui.GuiScreen.class
                        );
                        Object gui = ctor.newInstance(screen);
                        mc.displayGuiScreen((GuiScreen) gui);
                    } catch (Exception e2) {
                        break;
                    }
                }
                break;
            case 41:
                XaeroWorldMapHelper.openWorldMapSettings(mc);
                break;
            case 42: break;
            case 43: break;
            case 90:
                mc.displayGuiScreen(null);
                mc.setIngameFocus();
                break;
        }
    }

    private enum ButtonStyle {
        MAP_TYPE,
        OPTION,
        ACTION
    }

    private static class OverlayButton {
        final int id;
        final int x, y, w, h;
        final String label;
        final boolean active;
        final String tooltip;
        int bgColor;
        int fgColor;
        int hoverBgColor;
        int hoverFgColor;
        ButtonStyle style = ButtonStyle.ACTION;

        OverlayButton(int id, int x, int y, int w, int h, String label, boolean active, String tooltip) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.label = label;
            this.active = active;
            this.tooltip = tooltip;
        }

        boolean isHovered(int mouseX, int mouseY) {
            return mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
        }
    }
}
