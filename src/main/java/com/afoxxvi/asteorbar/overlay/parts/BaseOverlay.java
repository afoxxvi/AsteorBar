package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public abstract class BaseOverlay implements IGuiOverlay {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AsteorBar.MOD_ID, "textures/gui/overlay.png");
    public static final int FILL_FULL_WIDTH_LONG = 180;
    public static final int BOUND_FULL_WIDTH_LONG = 182;
    public static final int FILL_FULL_WIDTH_SHORT = 79;
    public static final int BOUND_FULL_WIDTH_SHORT = 81;
    public static final int Y_HEALTH_EMPTY_BAR = 0;
    public static final int Y_HEALTH_BLINK_BOUND = 9;
    public static final int Y_HEALTH_NORMAL_FILL = 18;
    public static final int Y_HEALTH_POISON_FILL = 27;
    public static final int Y_HEALTH_WITHER_FILL = 36;
    public static final int Y_HEALTH_ABSORPTION_FILL = 45;
    public static final int Y_HEALTH_FROZEN_FILL = 135;
    public static final int Y_HEALTH_LOW_BOUND = 180;
    public static final int Y_REGENERATION_FILL = 144;
    public static final int Y_FOOD_EMPTY_BAR = 54;
    public static final int Y_FOOD_BLINK_BOUND = 63;
    public static final int Y_FOOD_NORMAL_FILL = 72;
    public static final int Y_SATURATION_BOUND = 81;
    public static final int Y_FOOD_HUNGER_FILL = 90;
    public static final int Y_FOOD_EXHAUSTION_FILL = 171;
    public static final int Y_MOUNT_HEALTH_BAR = 153;
    public static final int Y_MOUNT_HEALTH_FILL = 162;
    public static final int Y_EXPERIENCE_EMPTY_BAR = 99;
    public static final int Y_EXPERIENCE_BAR = 108;
    public static final int Y_AIR_BOUND = 117;
    public static final int Y_AIR_FILL = 126;
    public static final int Y_ARMOR_EMPTY_BAR = 189;
    public static final int Y_ARMOR_BAR = 198;
    public static final int Y_ARMOR_BOUND = 207;

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (AsteorBar.Config.ENABLE_OVERLAY.get()) {
            GuiHelper.resourceLocation = TEXTURE;
            renderOverlay(gui, guiGraphics, partialTick, screenWidth, screenHeight);
        }
    }

    protected void drawTextureFillFlip(GuiGraphics guiGraphics, int left, int top, int right, int width, int height, int textureX, int textureY, int textureFullWidth, boolean flip) {
        if (flip) {
            GuiHelper.drawTexturedRect(guiGraphics, right - width, top, textureX + textureFullWidth - width, textureY, width, height);
        } else {
            GuiHelper.drawTexturedRect(guiGraphics, left, top, textureX, textureY, width, height);
        }
    }

    protected void drawTextureFillFlip(GuiGraphics guiGraphics, int left, int top, int right, int width, int height, int textureX, int textureY, boolean flip) {
        if (flip) {
            GuiHelper.drawTexturedRect(guiGraphics, right - width, top, textureX, textureY, width, height);
        } else {
            GuiHelper.drawTexturedRect(guiGraphics, left, top, textureX, textureY, width, height);
        }
    }

    protected void drawTextureBoundClipCenter(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int height, int textureX, int textureY, int textureFullWidth) {
        if (textureFullWidth == right - left) {
            GuiHelper.drawTexturedRect(guiGraphics, left, top, right, bottom, textureX, textureY, textureFullWidth, height);
        } else {
            GuiHelper.drawTextureRectCenterClip(guiGraphics, left, top, right, bottom, textureX, textureY, textureFullWidth, height);
        }
    }

    protected void drawTextureBoundClipCenterFlip(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int width, int height, int textureX, int textureY, int textureFullWidth, boolean flip) {
        if (width == right - left) {
            GuiHelper.drawTextureRectCenterClip(guiGraphics, left, top, right, bottom, textureX, textureY, textureFullWidth, height);
        } else {
            var offset = (textureFullWidth - (right - left)) / 2;
            if (flip) {
                if (width > 0) {
                    GuiHelper.drawTexturedRect(guiGraphics, right - 1, top, textureX + textureFullWidth - 1, textureY, 1, height);
                }
                if (width > 1) {
                    GuiHelper.drawTexturedRect(guiGraphics, right - 1 - (width - 1), top, textureX + 1 + offset + right - left - 2 - (width - 1), textureY, width - 1, height);
                }
            } else {
                if (width > 0) {
                    GuiHelper.drawTexturedRect(guiGraphics, left, top, textureX, textureY, 1, height);
                }
                if (width > 1) {
                    GuiHelper.drawTexturedRect(guiGraphics, left + 1, top, textureX + 1 + offset, textureY, width - 1, height);
                }
            }
        }
    }

    protected void drawTextureBoundFlip(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int width, int height, int textureX, int textureY, int textureFullWidth, boolean flip) {
        if (width == right - left) {
            GuiHelper.drawTexturedRect(guiGraphics, left, top, right, bottom, textureX, textureY, textureFullWidth, height);
        } else {
            if (flip) {
                GuiHelper.drawTexturedRect(guiGraphics, right - width, top, textureX + textureFullWidth - width, textureY, width, height);
            } else {
                GuiHelper.drawTexturedRect(guiGraphics, left, top, textureX, textureY, width, height);
            }
        }
    }

    public abstract void renderOverlay(ForgeGui forgeGui, GuiGraphics guiGraphics, float v, int i, int i1);
}
