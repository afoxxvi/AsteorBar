package com.afoxxvi.asteorbar.hud.overlay;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.hud.listener.RenderListener;
import com.afoxxvi.asteorbar.hud.utils.GuiHelper;
import com.afoxxvi.asteorbar.hud.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ExperienceBarOverlay implements IGuiOverlay {
    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (gui.getMinecraft().player != null && gui.getMinecraft().player.jumpableVehicle() == null && !gui.getMinecraft().options.hideGui) {
            gui.setupOverlayRenderState(true, false);
            //gui.renderExperience(screenWidth / 2 - 91, poseStack);
            var mc = gui.getMinecraft();
            if (mc.gameMode == null || !mc.gameMode.hasExperience()) {
                return;
            }
            var player = mc.player;
            if (AsteorBar.Config.OVERWRITE_VANILLA_EXPERIENCE_BAR.get()) {
                RenderSystem.setShaderTexture(0, RenderListener.TEXTURE);
                int top = screenHeight - 32 + 3;
                int left = screenWidth / 2 - 91;
                int expWidth = (int) (183.0F * player.experienceProgress);
                GuiHelper.drawTexturedRect(poseStack, left, top, 9, RenderListener.Y_EXPERIENCE_EMPTY_BAR, 182, 9);
                if (expWidth > 0) {
                    GuiHelper.drawTexturedRect(poseStack, left, top, 9, RenderListener.Y_EXPERIENCE_BAR, expWidth, 9);
                }

                String levelStr = String.valueOf(player.experienceLevel);
                int x = (screenWidth - mc.font.width(levelStr)) / 2;
                int y = screenHeight - 31;
                mc.font.draw(poseStack, levelStr, (float) (x + 1), (float) y, 0);
                mc.font.draw(poseStack, levelStr, (float) (x - 1), (float) y, 0);
                mc.font.draw(poseStack, levelStr, (float) x, (float) (y + 1), 0);
                mc.font.draw(poseStack, levelStr, (float) x, (float) (y - 1), 0);
                mc.font.draw(poseStack, levelStr, x, y, 0x80FF20);
            }

            if (AsteorBar.Config.DISPLAY_EXPERIENCE_PROGRESS.get()) {
                var need = String.valueOf(Utils.getTotalExperience(player.experienceLevel + 1) - Utils.getTotalExperience(player.experienceLevel));
                var has = String.valueOf(player.totalExperience - Utils.getTotalExperience(player.experienceLevel));
                int x = screenWidth / 2;
                int y = screenHeight - 31;
                GuiHelper.drawString(poseStack, has, x - 91 - mc.font.width(has), y, 0xFFFFFF);
                GuiHelper.drawString(poseStack, need, x + 91, y, 0xFFFFFF);
            }
        }
    }
}
