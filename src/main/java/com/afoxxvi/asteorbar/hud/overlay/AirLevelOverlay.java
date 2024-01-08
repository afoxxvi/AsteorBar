package com.afoxxvi.asteorbar.hud.overlay;

import com.afoxxvi.asteorbar.hud.listener.RenderListener;
import com.afoxxvi.asteorbar.hud.utils.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.ForgeMod;

public class AirLevelOverlay implements IGuiOverlay {
    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
            gui.setupOverlayRenderState(true, false);
            //gui.renderAir(screenWidth, screenHeight, poseStack);
            RenderSystem.setShaderTexture(0, RenderListener.TEXTURE);
            int left = screenWidth / 2 + 10;
            int top = screenHeight - gui.rightHeight + 4;
            var player = gui.getMinecraft().player;
            if (player == null) return;
            int air = player.getAirSupply();
            if (player.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) || air < 300) {
                GuiHelper.drawTexturedRect(poseStack, left, top, 9, RenderListener.Y_AIR_BOUND, 81, 9);
                int airWidth = 79 * air / 300;
                GuiHelper.drawTexturedRect(poseStack, left + 81 - 1 - airWidth, top, 10, RenderListener.Y_AIR_FILL, airWidth, 9);
                gui.rightHeight += 6;
            }
        }
    }
}
