package com.afoxxvi.asteorbar.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;

@SuppressWarnings("unused")
public class GuiHelper {
    public static void drawTexturedRect(PoseStack poseStack, int left, int top, int textureX, int textureY, int width, int height) {
        GuiComponent.blit(poseStack, left, top, textureX, textureY, width, height);
    }

    public static void drawTexturedRect(PoseStack poseStack, int left, int top, int right, int bottom, int textureX, int textureY, int width, int height) {
        if (right - left == width) {
            GuiComponent.blit(poseStack, left, top, textureX, textureY, width, height);
        } else if (right - left < width) {
            GuiComponent.blit(poseStack, left, top, textureX, textureY, right - left - 1, height);
            GuiComponent.blit(poseStack, right - 1, top, textureX + width - 1, textureY, 1, height);
        } else if (right - left > width) {
            GuiComponent.blit(poseStack, left, top, textureX, textureY, width, height);
        }
    }

    public static void drawTextureRectCenterClip(PoseStack poseStack, int left, int top, int right, int bottom, int textureX, int textureY, int width, int height) {
        if (right - left == width) {
            GuiComponent.blit(poseStack, left, top, textureX, textureY, width, height);
        } else if (right - left < width) {
            GuiComponent.blit(poseStack, left, top, textureX, textureY, 1, height);
            GuiComponent.blit(poseStack, left + 1, top, textureX + (width - (right - left - 2)) / 2, textureY, right - left - 2, height);
            GuiComponent.blit(poseStack, right - 1, top, textureX + width - 1, textureY, 1, height);
        } else if (right - left > width) {
            GuiComponent.blit(poseStack, left, top, textureX, textureY, width, height);
        }
    }

    public static void drawBound(PoseStack poseStack, int left, int top, int right, int bottom, int color) {
        GuiComponent.fill(poseStack, left, top, right, top + 1, color);
        GuiComponent.fill(poseStack, left, bottom - 1, right, bottom, color);
        GuiComponent.fill(poseStack, left, top, left + 1, bottom, color);
        GuiComponent.fill(poseStack, right - 1, top, right, bottom, color);
    }

    public static void drawBoundNoVertex(PoseStack poseStack, int left, int top, int right, int bottom, int color) {
        GuiComponent.fill(poseStack, left + 1, top, right - 1, top + 1, color);
        GuiComponent.fill(poseStack, left + 1, bottom - 1, right - 1, bottom, color);
        GuiComponent.fill(poseStack, left, top + 1, left + 1, bottom - 1, color);
        GuiComponent.fill(poseStack, right - 1, top + 1, right, bottom - 1, color);
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

    public static void renderString(PoseStack poseStack, String string, int left, int top, int color, MultiBufferSource bufferSource) {
        Minecraft.getInstance().font.drawInBatch(string, left, top, color, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
    }

    public static void renderCenteredString(PoseStack poseStack, String string, int left, int top, int color, MultiBufferSource bufferSource) {
        renderString(poseStack, string, (int) (left - Minecraft.getInstance().font.width(string) / 2.0f), top, color, bufferSource);
    }


    public static void renderStringShadow(PoseStack poseStack, String string, int left, int top, int color) {
        Minecraft.getInstance().font.drawShadow(poseStack, string, left, top, color);
    }

    public static void renderCenteredStringShadow(PoseStack poseStack, String string, int left, int top, int color) {
        Minecraft.getInstance().font.drawShadow(poseStack, string, left - Minecraft.getInstance().font.width(string) / 2.0f, top, color);
    }
}
