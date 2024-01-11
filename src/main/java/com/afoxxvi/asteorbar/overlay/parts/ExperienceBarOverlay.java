package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ExperienceBarOverlay extends BaseOverlay {
    private void draw(PoseStack poseStack, int left, int top, int right, int bottom, float exp, String levelStr, Font font, boolean flip) {
        drawTextureBoundClipCenter(poseStack, left, top, right, bottom, 9, 9, Y_EXPERIENCE_EMPTY_BAR, BOUND_FULL_WIDTH_LONG);
        int expWidth = (int) ((right - left) * exp);
        if (expWidth > 0) {
            drawTextureBoundClipCenterFlip(poseStack, left, top, right, bottom, expWidth, 9, 9, Y_EXPERIENCE_BAR, BOUND_FULL_WIDTH_LONG, flip);
        }
        int x = (right + left - font.width(levelStr)) / 2;
        int y = top - 2;
        Overlays.addStringRender(x + 1, y, 0, levelStr, Overlays.ALIGN_LEFT, false);
        Overlays.addStringRender(x - 1, y, 0, levelStr, Overlays.ALIGN_LEFT, false);
        Overlays.addStringRender(x, y + 1, 0, levelStr, Overlays.ALIGN_LEFT, false);
        Overlays.addStringRender(x, y - 1, 0, levelStr, Overlays.ALIGN_LEFT, false);
        Overlays.addStringRender(x, y, 0x80FF20, levelStr, Overlays.ALIGN_LEFT, false);
    }

    @Override
    public void renderOverlay(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (gui.getMinecraft().player != null && !gui.getMinecraft().player.isRidingJumpable() && !gui.getMinecraft().options.hideGui) {
            gui.setupOverlayRenderState(true, false);
            var mc = gui.getMinecraft();
            if (mc.gameMode == null || !mc.gameMode.hasExperience()) {
                return;
            }
            var player = mc.player;
            if (AsteorBar.Config.OVERWRITE_VANILLA_EXPERIENCE_BAR.get()) {
                RenderSystem.setShaderTexture(0, TEXTURE);
                String levelStr = String.valueOf(player.experienceLevel);
                switch (Overlays.style) {
                    case Overlays.STYLE_NONE -> {

                    }
                    case Overlays.STYLE_ABOVE_HOT_BAR_LONG, Overlays.STYLE_ABOVE_HOT_BAR_SHORT -> {
                        int top = screenHeight - 29;
                        int left = screenWidth / 2 - 91;
                        draw(poseStack, left, top, left + BOUND_FULL_WIDTH_LONG, top + 5, player.experienceProgress, levelStr, mc.font, false);
                    }
                    case Overlays.STYLE_TOP_LEFT -> {
                        int top = Overlays.top;
                        int left = Overlays.left;
                        draw(poseStack, left, top, left + Overlays.length, top + 5, player.experienceProgress, levelStr, mc.font, false);
                        Overlays.top += 6;
                    }
                    case Overlays.STYLE_TOP_RIGHT -> {
                        int top = Overlays.top;
                        int left = screenWidth - Overlays.length - Overlays.left;
                        draw(poseStack, left, top, left + Overlays.length, top + 5, player.experienceProgress, levelStr, mc.font, true);
                        Overlays.top += 6;
                    }
                    case Overlays.STYLE_BOTTOM_LEFT -> {
                        int top = screenHeight - Overlays.top;
                        int left = Overlays.left;
                        draw(poseStack, left, top, left + Overlays.length, top + 5, player.experienceProgress, levelStr, mc.font, false);
                        Overlays.top += 6;
                    }
                    case Overlays.STYLE_BOTTOM_RIGHT -> {
                        int top = screenHeight - Overlays.top;
                        int left = screenWidth - Overlays.length - Overlays.left;
                        draw(poseStack, left, top, left + Overlays.length, top + 5, player.experienceProgress, levelStr, mc.font, true);
                        Overlays.top += 6;
                    }
                }
            }

            if (AsteorBar.Config.DISPLAY_EXPERIENCE_PROGRESS.get()) {
                var need = String.valueOf(Utils.getTotalExperience(player.experienceLevel + 1) - Utils.getTotalExperience(player.experienceLevel));
                var has = String.valueOf(player.totalExperience - Utils.getTotalExperience(player.experienceLevel));
                int x = -1, y = -1;
                int len = 0;
                boolean z = false;
                switch (Overlays.style) {
                    case Overlays.STYLE_NONE -> {

                    }
                    case Overlays.STYLE_ABOVE_HOT_BAR_LONG, Overlays.STYLE_ABOVE_HOT_BAR_SHORT -> {
                        x = screenWidth / 2;
                        y = screenHeight - 31;
                        len = 91;
                    }
                    case Overlays.STYLE_TOP_LEFT -> {
                        y = Overlays.top - 8;
                        len = Overlays.length / 2;
                        x = Overlays.left + len;
                        z = true;
                    }
                    case Overlays.STYLE_TOP_RIGHT -> {
                        y = Overlays.top - 8;
                        len = Overlays.length / 2;
                        x = screenWidth - Overlays.left - len;
                        z = true;
                    }
                    case Overlays.STYLE_BOTTOM_LEFT -> {
                        y = screenHeight - Overlays.top + 4;
                        len = Overlays.length / 2;
                        x = Overlays.left + len;
                        z = true;
                    }
                    case Overlays.STYLE_BOTTOM_RIGHT -> {
                        y = screenHeight - Overlays.top + 4;
                        len = Overlays.length / 2;
                        x = screenWidth - Overlays.left - len;
                        z = true;
                    }
                }
                if (x >= 0 && y >= 0) {
                    if (z) {
                        Overlays.addStringRender(x - len, y, 0xFFFFFF, has, Overlays.ALIGN_LEFT, true);
                        Overlays.addStringRender(x + len, y, 0xFFFFFF, need, Overlays.ALIGN_RIGHT, true);
                    } else {
                        Overlays.addStringRender(x - len, y, 0xFFFFFF, has, Overlays.ALIGN_RIGHT, true);
                        Overlays.addStringRender(x + len, y, 0xFFFFFF, need, Overlays.ALIGN_LEFT, true);
                    }
                }
            }
        }
    }
}
