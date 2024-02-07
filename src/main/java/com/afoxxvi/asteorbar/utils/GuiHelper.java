package com.afoxxvi.asteorbar.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class GuiHelper {
    public static ResourceLocation resourceLocation;

    public static void drawTexturedRect(GuiGraphics guiGraphics, int left, int top, int textureX, int textureY, int width, int height) {
        guiGraphics.blit(resourceLocation, left, top, textureX, textureY, width, height);
    }

    public static void drawTexturedRect(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int textureX, int textureY, int width, int height) {
        if (right - left == width) {
            guiGraphics.blit(resourceLocation, left, top, textureX, textureY, width, height);
        } else if (right - left < width) {
            guiGraphics.blit(resourceLocation, left, top, textureX, textureY, right - left - 1, height);
            guiGraphics.blit(resourceLocation, right - 1, top, textureX + width - 1, textureY, 1, height);
        } else if (right - left > width) {
            guiGraphics.blit(resourceLocation, left, top, textureX, textureY, width, height);
        }
    }

    public static void drawTextureRectCenterClip(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int textureX, int textureY, int width, int height) {
        if (right - left == width) {
            guiGraphics.blit(resourceLocation, left, top, textureX, textureY, width, height);
        } else if (right - left < width) {
            guiGraphics.blit(resourceLocation, left, top, textureX, textureY, 1, height);
            guiGraphics.blit(resourceLocation, left + 1, top, textureX + (width - (right - left - 2)) / 2, textureY, right - left - 2, height);
            guiGraphics.blit(resourceLocation, right - 1, top, textureX + width - 1, textureY, 1, height);
        } else if (right - left > width) {
            guiGraphics.blit(resourceLocation, left, top, textureX, textureY, width, height);
        }
    }

    public static void drawBound(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int color) {
        guiGraphics.fill(left, top, right, top + 1, color);
        guiGraphics.fill(left, bottom - 1, right, bottom, color);
        guiGraphics.fill(left, top, left + 1, bottom, color);
        guiGraphics.fill(right - 1, top, right, bottom, color);
    }

    public static void drawBoundNoVertex(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int color) {
        guiGraphics.fill(left + 1, top, right - 1, top + 1, color);
        guiGraphics.fill(left + 1, bottom - 1, right - 1, bottom, color);
        guiGraphics.fill(left, top + 1, left + 1, bottom - 1, color);
        guiGraphics.fill(right - 1, top + 1, right, bottom - 1, color);
    }

    public static void drawSolidColor(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int color) {
        guiGraphics.fill(left, top, right, bottom, color);
    }

    public static void drawCenteredString(GuiGraphics guiGraphics, String string, int left, int top, int color) {
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, string, left, top, color);
    }

    public static void drawString(GuiGraphics guiGraphics, String string, int left, int top, int color) {
        guiGraphics.drawString(Minecraft.getInstance().font, string, left, top, color);
    }

    public static void drawString(GuiGraphics guiGraphics, String string, int left, int top, int color, boolean shadow) {
        guiGraphics.drawString(Minecraft.getInstance().font, string, left, top, color, shadow);
    }

    //left < right, top < bottom
    public static void renderSolid(VertexConsumer vertexConsumer, PoseStack poseStack, int left, int top, int right, int bottom, int color, float z) {
        vertexConsumer.vertex(poseStack.last().pose(), left, top, z).color(color).uv(0, 0).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), left, bottom, z).color(color).uv(0, 0.125f).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), right, bottom, z).color(color).uv(1, 0.125f).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), right, top, z).color(color).uv(1, 0).endVertex();
    }

    public static void renderSolidGradient(VertexConsumer vertexConsumer, PoseStack poseStack, int left, int top, int right, int bottom, int color, float z) {
        vertexConsumer.vertex(poseStack.last().pose(), left, top, z).color(color).uv(0, 0.625f).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), left, bottom, z).color(color).uv(0, 1).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), right, bottom, z).color(color).uv(1, 1).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), right, top, z).color(color).uv(1, 0.625f).endVertex();
    }

    public static void renderSolidGradientUpDown(VertexConsumer vertexConsumer, PoseStack poseStack, int left, int top, int right, int bottom, int color, float z) {
        vertexConsumer.vertex(poseStack.last().pose(), left, top, z).color(color).uv(0, 0).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), left, bottom, z).color(color).uv(0, 0.375f).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), right, bottom, z).color(color).uv(1, 0.375f).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), right, top, z).color(color).uv(1, 0).endVertex();
    }

    public static void renderString(PoseStack poseStack, MultiBufferSource buffer, String string, float left, float top, int color, boolean shadow) {
        Minecraft.getInstance().font.drawInBatch(string, left, top, color, shadow, poseStack.last().pose(), buffer,
                Font.DisplayMode.NORMAL, 0, 0xF000F0);
    }

    public static void renderString(PoseStack poseStack, MultiBufferSource buffer, String string, int left, int top, int color) {
        renderString(poseStack, buffer, string, (float) left, (float) top, color, false);
    }

    public static void renderCenteredString(PoseStack poseStack, MultiBufferSource buffer, String string, int left, int top, int color) {
        renderString(poseStack, buffer, string, left - Minecraft.getInstance().font.width(string) / 2.0f, top, color, false);
    }


    public static void renderStringShadow(PoseStack poseStack, MultiBufferSource buffer, String string, int left, int top, int color) {
        renderString(poseStack, buffer, string, left, top, color, true);
    }

    public static void renderCenteredStringShadow(PoseStack poseStack, MultiBufferSource buffer, String string, int left, int top, int color) {
        renderString(poseStack, buffer, string, left - Minecraft.getInstance().font.width(string) / 2.0f, top, color, true);
    }
}
