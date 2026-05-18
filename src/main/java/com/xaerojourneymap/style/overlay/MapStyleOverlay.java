package com.xaerojourneymap.style.overlay;

import com.xaerojourneymap.style.StyleManager;
import com.xaerojourneymap.style.UIStyleConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MapStyleOverlay {

    private final StyleManager styleManager;

    public MapStyleOverlay(StyleManager styleManager) {
        this.styleManager = styleManager;
    }

    @SubscribeEvent
    public void onGuiDrawForeground(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (!styleManager.isJourneyMapStyle()) return;

        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen screen = mc.currentScreen;
        if (screen == null) return;

        String className = screen.getClass().getName();
        if (className.equals("xaero.map.gui.GuiMap")) {
            int mouseX = event.getMouseX();
            int mouseY = event.getMouseY();
            drawJourneyMapUIOverlay(mc, screen, mouseX, mouseY);
        }
    }

    private void drawJourneyMapUIOverlay(Minecraft mc, GuiScreen screen, int mouseX, int mouseY) {
        drawJourneyMapTopToolbar(mc, screen, mouseX, mouseY);
        drawJourneyMapRightToolbar(mc, screen, mouseX, mouseY);
        drawJourneyMapBottomToolbar(mc, screen, mouseX, mouseY);
        drawJourneyMapPlayerInfo(mc, screen);
        drawJourneyMapBlockInfo(mc, screen);
    }

    private void drawJourneyMapTopToolbar(Minecraft mc, GuiScreen screen, int mouseX, int mouseY) {
        int toolbarH = 28;
        int toolbarY = 4;
        int centerX = screen.width / 2;

        drawThemedBar(screen, 0, 0, screen.width, toolbarH, UIStyleConstants.TOOLBAR_BG_COLOR, UIStyleConstants.TOOLBAR_BG_ALPHA);

        String[] mapTypes = {"Day", "Night", "Topo", "Cave"};
        int btnW = 50;
        int btnH = 20;
        int startX = centerX - (mapTypes.length * (btnW + 2)) / 2;

        for (int i = 0; i < mapTypes.length; i++) {
            int btnX = startX + i * (btnW + 2);
            int btnY = toolbarY + 2;
            boolean active = i == 0;
            drawThemedButton(mc, btnX, btnY, btnW, btnH,
                I18n.format("xaerojourneymap.ui.maptype." + mapTypes[i].toLowerCase()),
                active, isHovered(mouseX, mouseY, btnX, btnY, btnW, btnH));
        }

        int optionsStartX = centerX + (mapTypes.length * (btnW + 2)) / 2 + 10;
        String[] options = {"Grid", "Mobs", "Players"};
        for (int i = 0; i < options.length; i++) {
            int btnX = optionsStartX + i * (btnW + 2);
            int btnY = toolbarY + 2;
            drawThemedButton(mc, btnX, btnY, btnW, btnH,
                I18n.format("xaerojourneymap.ui.option." + options[i].toLowerCase()),
                i == 0, isHovered(mouseX, mouseY, btnX, btnY, btnW, btnH));
        }

        int closeX = screen.width - 28;
        int closeY = toolbarY + 2;
        drawThemedButton(mc, closeX, closeY, 24, btnH, "X", false,
            isHovered(mouseX, mouseY, closeX, closeY, 24, btnH));
    }

    private void drawJourneyMapRightToolbar(Minecraft mc, GuiScreen screen, int mouseX, int mouseY) {
        int toolbarW = 28;
        int toolbarX = screen.width - toolbarW;
        int centerY = screen.height / 2;

        drawThemedBar(screen, toolbarX, 0, toolbarW, screen.height, UIStyleConstants.TOOLBAR_BG_COLOR, UIStyleConstants.TOOLBAR_BG_ALPHA);

        String[] labels = {"+", "-", I18n.format("xaerojourneymap.ui.follow")};
        int btnSize = 24;
        int startY = centerY - (labels.length * (btnSize + 2)) / 2;

        for (int i = 0; i < labels.length; i++) {
            int btnX = toolbarX + 2;
            int btnY = startY + i * (btnSize + 2);
            drawThemedButton(mc, btnX, btnY, btnSize, btnSize, labels[i],
                i == 2, isHovered(mouseX, mouseY, btnX, btnY, btnSize, btnSize));
        }
    }

    private void drawJourneyMapBottomToolbar(Minecraft mc, GuiScreen screen, int mouseX, int mouseY) {
        int toolbarH = 28;
        int toolbarY = screen.height - toolbarH;
        int centerX = screen.width / 2;

        drawThemedBar(screen, 0, toolbarY, screen.width, toolbarH, UIStyleConstants.TOOLBAR_BG_COLOR, UIStyleConstants.TOOLBAR_BG_ALPHA);

        String[] labels = {
            I18n.format("xaerojourneymap.ui.waypoints"),
            I18n.format("xaerojourneymap.ui.options"),
        };
        int btnW = 70;
        int btnH = 20;
        int startX = centerX - (labels.length * (btnW + 4)) / 2;

        for (int i = 0; i < labels.length; i++) {
            int btnX = startX + i * (btnW + 4);
            int btnY = toolbarY + 4;
            drawThemedButton(mc, btnX, btnY, btnW, btnH, labels[i],
                false, isHovered(mouseX, mouseY, btnX, btnY, btnW, btnH));
        }
    }

    private void drawJourneyMapPlayerInfo(Minecraft mc, GuiScreen screen) {
        if (mc.player == null) return;

        String coords = String.format("X: %d  Y: %d  Z: %d",
            (int) mc.player.posX, (int) mc.player.posY, (int) mc.player.posZ);
        String biome = mc.player.getEntityWorld().getBiome(mc.player.getPosition()).getBiomeName();
        String text = coords + "  \u25a0  " + biome;

        int textWidth = mc.fontRenderer.getStringWidth(text);
        int x = screen.width / 2;
        int y = 34;

        drawInfoLabel(mc, text, x, y, textWidth);
    }

    private void drawJourneyMapBlockInfo(Minecraft mc, GuiScreen screen) {
        int y = screen.height - 36;
        String hint = I18n.format("xaerojourneymap.ui.right_click_hint");
        int textWidth = mc.fontRenderer.getStringWidth(hint);
        drawInfoLabel(mc, hint, screen.width / 2, y, textWidth);
    }

    private void drawInfoLabel(Minecraft mc, String text, int x, int y, int textWidth) {
        int padding = 4;
        int bgLeft = x - textWidth / 2 - padding;
        int bgRight = x + textWidth / 2 + padding;
        int bgTop = y - 2;
        int bgBottom = y + mc.fontRenderer.FONT_HEIGHT + 2;

        drawRect(bgLeft, bgTop, bgRight, bgBottom, UIStyleConstants.LABEL_BG_COLOR);
        mc.fontRenderer.drawStringWithShadow(text, x - textWidth / 2, y, UIStyleConstants.LABEL_FG_COLOR);
    }

    private void drawThemedBar(GuiScreen screen, int x, int y, int width, int height, int color, float alpha) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(r, g, b, alpha);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(x, y + height, 0.0D).endVertex();
        buffer.pos(x + width, y + height, 0.0D).endVertex();
        buffer.pos(x + width, y, 0.0D).endVertex();
        buffer.pos(x, y, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    private void drawThemedButton(Minecraft mc, int x, int y, int w, int h,
                                   String label, boolean active, boolean hovered) {
        int bgColor = active ? UIStyleConstants.BTN_ACTIVE_BG : UIStyleConstants.BTN_INACTIVE_BG;
        int fgColor = active ? UIStyleConstants.BTN_ACTIVE_FG : UIStyleConstants.BTN_INACTIVE_FG;

        if (hovered) {
            bgColor = active ? UIStyleConstants.BTN_HOVER_ACTIVE_BG : UIStyleConstants.BTN_HOVER_INACTIVE_BG;
            fgColor = active ? UIStyleConstants.BTN_HOVER_ACTIVE_FG : UIStyleConstants.BTN_HOVER_INACTIVE_FG;
        }

        drawRect(x, y, x + w, y + h, bgColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        drawRect(x, y, x + w, y + 1, UIStyleConstants.BTN_BORDER_COLOR);
        drawRect(x, y + h - 1, x + w, y + h, UIStyleConstants.BTN_BORDER_COLOR);
        drawRect(x, y, x + 1, y + h, UIStyleConstants.BTN_BORDER_COLOR);
        drawRect(x + w - 1, y, x + w, y + h, UIStyleConstants.BTN_BORDER_COLOR);

        int textWidth = mc.fontRenderer.getStringWidth(label);
        mc.fontRenderer.drawStringWithShadow(label, x + (w - textWidth) / 2, y + (h - 8) / 2, fgColor);
    }

    private boolean isHovered(int mouseX, int mouseY, int x, int y, int w, int h) {
        return mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
    }

    private void drawRect(int left, int top, int right, int bottom, int color) {
        net.minecraft.client.gui.Gui.drawRect(left, top, right, bottom, color);
    }
}
