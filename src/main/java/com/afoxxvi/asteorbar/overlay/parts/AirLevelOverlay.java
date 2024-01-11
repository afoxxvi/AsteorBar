package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.ForgeMod;

public class AirLevelOverlay extends BaseOverlay {
    private void draw(PoseStack poseStack, int left, int top, int right, int bottom, int air, boolean flip) {
        int airWidth = (right - left - 2) * air / 300;
        GuiHelper.drawTexturedRect(poseStack, left, top, right, bottom, 9, Y_AIR_BOUND, BOUND_FULL_WIDTH_SHORT, 9);
        drawTextureFillFlip(poseStack, left + 1, top, right - 1, airWidth, 9, 10, Y_AIR_FILL, flip);
    }

    @Override
    public void renderOverlay(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
            gui.setupOverlayRenderState(true, false);
            RenderSystem.setShaderTexture(0, TEXTURE);
            var player = mc.player;
            if (player == null) return;
            int air = player.getAirSupply();
            if (!(player.isEyeInFluid(FluidTags.WATER) || air < 300)) {
                return;
            }
            switch (Overlays.style) {
                case Overlays.STYLE_NONE -> {

                }
                default -> {
                    int left = screenWidth / 2 + 10;
                    int top = screenHeight - gui.right_height + 4;
                    gui.right_height += 6;
                    draw(poseStack, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, air, true);
                }
            }
        }
    }
}
