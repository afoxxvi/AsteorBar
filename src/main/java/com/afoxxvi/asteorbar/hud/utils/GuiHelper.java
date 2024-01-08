package com.afoxxvi.asteorbar.hud.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

@SuppressWarnings("unused")
public class GuiHelper {
    public static void drawTexturedRect(PoseStack poseStack, int left, int top, int textureX, int textureY, int width, int height) {
        GuiComponent.blit(poseStack, left, top, textureX, textureY, width, height);
    }

    public static void drawSolidColor(PoseStack poseStack, int left, int top, int right, int bottom, int color) {
        GuiComponent.fill(poseStack, left, top, right, bottom, color);
    }

    public static void drawCenteredString(PoseStack poseStack, String string, int left, int top, int color) {
        GuiComponent.drawCenteredString(poseStack, Minecraft.getInstance().font, string, left, top, color);
    }

    public static void drawString(PoseStack poseStack, String string, int left, int top, int color) {
        GuiComponent.drawString(poseStack, Minecraft.getInstance().font, string, left, top, color);
    }
}
