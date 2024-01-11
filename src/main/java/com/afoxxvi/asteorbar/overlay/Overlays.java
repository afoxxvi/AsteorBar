package com.afoxxvi.asteorbar.overlay;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.parts.*;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class Overlays {
    public static final int STYLE_NONE = 0;
    public static final int STYLE_ABOVE_HOT_BAR_LONG = 1;
    public static final int STYLE_ABOVE_HOT_BAR_SHORT = 2;
    public static final int STYLE_TOP_LEFT = 3;
    public static final int STYLE_TOP_RIGHT = 4;
    public static final int STYLE_BOTTOM_LEFT = 5;
    public static final int STYLE_BOTTOM_RIGHT = 6;
    public static final int NUM_STYLES = 7;

    public static final PlayerHealthOverlay PLAYER_HEALTH = new PlayerHealthOverlay();
    public static final FoodLevelOverlay FOOD_LEVEL = new FoodLevelOverlay();
    public static final AirLevelOverlay AIR_LEVEL = new AirLevelOverlay();
    public static final ExperienceBarOverlay EXPERIENCE_BAR = new ExperienceBarOverlay();
    public static final MountHealthOverlay MOUNT_HEALTH = new MountHealthOverlay();
    public static final ArmorLevelOverlay ARMOR_LEVEL = new ArmorLevelOverlay();
    public static final StringOverlay STRING = new StringOverlay();
    public static int style = AsteorBar.Config.OVERLAY_LAYOUT_STYLE.get() % NUM_STYLES;
    public static int top = 0;
    public static int left = 0;
    public static int length = 10;
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_RIGHT = 2;
    private static List<Render> stringRenders = new ArrayList<>();

    public static void reset() {
        top = AsteorBar.Config.CORNER_VERTICAL_PADDING.get();
        left = AsteorBar.Config.CORNER_HORIZONTAL_PADDING.get();
        length = AsteorBar.Config.CORNER_BAR_LENGTH.get();
        stringRenders.clear();
    }

    public static void renderString(PoseStack poseStack) {
        if (stringRenders == null) return;
        var font = Minecraft.getInstance().font;
        for (var render : stringRenders) {
            var width = font.width(render.text);
            if (render.shadow) {
                switch (render.align) {
                    case ALIGN_LEFT -> GuiHelper.drawString(poseStack, render.text, render.x, render.y, render.color);
                    case ALIGN_CENTER -> GuiHelper.drawCenteredString(poseStack, render.text, render.x, render.y, render.color);
                    case ALIGN_RIGHT -> GuiHelper.drawString(poseStack, render.text, render.x - width, render.y, render.color);
                }
            } else {
                switch (render.align) {
                    case ALIGN_LEFT -> font.draw(poseStack, render.text, render.x, render.y, render.color);
                    case ALIGN_CENTER -> font.draw(poseStack, render.text, render.x - width / 2F, render.y, render.color);
                    case ALIGN_RIGHT -> font.draw(poseStack, render.text, render.x - width, render.y, render.color);
                }
            }
        }
        stringRenders.clear();
    }

    public static void addStringRender(int x, int y, int color, String text, int align, boolean shadow) {
        if (stringRenders == null) {
            stringRenders = new ArrayList<>();
        }
        var render = new Render();
        render.x = x;
        render.y = y;
        render.color = color;
        render.text = text;
        render.align = align;
        render.shadow = shadow;
        stringRenders.add(render);
    }

    private static class Render {
        int x;
        int y;
        int color;
        String text;
        int align;
        boolean shadow;
    }
}
