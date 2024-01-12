package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class MountHealthOverlay extends BaseOverlay {
    private void draw(GuiGraphics guiGraphics, int left, int top, int right, int bottom, float health, float healthMax, boolean flip) {
        int healthWidth = (int) ((right - left - 2) * health / healthMax);
        GuiHelper.drawTexturedRect(guiGraphics, left, top, right, bottom, 9, Y_MOUNT_HEALTH_BAR, 182, 5);
        drawTextureFillFlip(guiGraphics, left + 1, top, right - 1, healthWidth, 5, 10, Y_MOUNT_HEALTH_FILL, flip);
    }

    @Override
    public void renderOverlay(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
            gui.setupOverlayRenderState(true, false);
            Player player = (Player) gui.getMinecraft().getCameraEntity();
            if (player == null) return;
            Entity tmp = player.getVehicle();
            if (tmp instanceof LivingEntity mount) {
                RenderSystem.setShaderTexture(0, TEXTURE);
                int health = (int) Math.ceil(mount.getHealth());
                float healthMax = mount.getMaxHealth();
                switch (Overlays.style) {
                    case Overlays.STYLE_NONE -> {

                    }
                    case Overlays.STYLE_ABOVE_HOT_BAR_LONG -> {
                        int left = screenWidth / 2 - 91;
                        int top = screenHeight - gui.rightHeight + 4;
                        gui.rightHeight += 12;
                        draw(guiGraphics, left, top, left + BOUND_FULL_WIDTH_LONG, top + 5, health, healthMax, false);
                    }
                    case Overlays.STYLE_ABOVE_HOT_BAR_SHORT -> {
                        int left = screenWidth / 2 + 10;
                        int top = screenHeight - gui.rightHeight + 4;
                        gui.rightHeight += 6;
                        draw(guiGraphics, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, health, healthMax, true);
                    }
                    case Overlays.STYLE_TOP_LEFT -> {
                        int top = Overlays.top;
                        int left = Overlays.left;
                        draw(guiGraphics, left, top, left + Overlays.length, top + 5, health, healthMax, false);
                        Overlays.top += 6;
                    }
                    case Overlays.STYLE_TOP_RIGHT -> {
                        int top = Overlays.top;
                        int left = screenWidth - Overlays.length - Overlays.left;
                        draw(guiGraphics, left, top, left + Overlays.length, top + 5, health, healthMax, true);
                        Overlays.top += 6;
                    }
                    case Overlays.STYLE_BOTTOM_LEFT -> {
                        int top = screenHeight - Overlays.top;
                        int left = Overlays.left;
                        draw(guiGraphics, left, top, left + Overlays.length, top + 5, health, healthMax, false);
                        Overlays.top += 6;
                    }
                    case Overlays.STYLE_BOTTOM_RIGHT -> {
                        int top = screenHeight - Overlays.top;
                        int left = screenWidth - Overlays.length - Overlays.left;
                        draw(guiGraphics, left, top, left + Overlays.length, top + 5, health, healthMax, true);
                        Overlays.top += 6;
                    }
                }
            }
        }
    }
}
