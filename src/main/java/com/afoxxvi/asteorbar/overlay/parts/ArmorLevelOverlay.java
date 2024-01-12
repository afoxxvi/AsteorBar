package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ArmorLevelOverlay extends BaseOverlay {
    private void draw(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int armor, double armorToughness, boolean flip) {
        int armorWidth = (int) ((right - left - 2) * Math.min(1.0, armor / 20f));
        GuiHelper.drawTexturedRect(guiGraphics, left, top, right, bottom, 9, Y_ARMOR_EMPTY_BAR, BOUND_FULL_WIDTH_SHORT, 9);
        drawTextureFillFlip(guiGraphics, left + 1, top, right - 1, armorWidth, 9, 10, Y_ARMOR_BAR, flip);
        if (armorToughness > 0) {
            int armorToughnessWidth = (int) ((right - left) * Math.min(1.0, armorToughness / 20.0));
            drawTextureBoundFlip(guiGraphics, left, top, right, bottom, armorToughnessWidth, 9, 9, Y_ARMOR_BOUND, BOUND_FULL_WIDTH_SHORT, flip);
        }
    }

    @Override
    public void renderOverlay(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements() && AsteorBar.Config.OVERWRITE_VANILLA_ARMOR_BAR.get()) {
            gui.setupOverlayRenderState(true, false);
            RenderSystem.setShaderTexture(0, TEXTURE);
            var player = gui.getMinecraft().player;
            if (player == null) return;
            int armor = player.getArmorValue();
            if (armor <= 0) return;
            double armorToughness = 0;
            var attr = player.getAttribute(Attributes.ARMOR_TOUGHNESS);
            if (attr != null) {
                armorToughness = attr.getValue();
            }
            switch (Overlays.style) {
                case Overlays.STYLE_NONE -> {

                }
                case Overlays.STYLE_ABOVE_HOT_BAR_LONG, Overlays.STYLE_ABOVE_HOT_BAR_SHORT -> {
                    int left = screenWidth / 2 - 91;
                    int top = screenHeight - gui.leftHeight + 4;
                    gui.leftHeight += 6;
                    draw(guiGraphics, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, armor, armorToughness, false);
                }
                case Overlays.STYLE_TOP_LEFT -> {
                    int top = Overlays.top;
                    int left = Overlays.left;
                    draw(guiGraphics, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, armor, armorToughness, false);
                    Overlays.top += 6;
                }
                case Overlays.STYLE_TOP_RIGHT -> {
                    int top = Overlays.top;
                    int left = screenWidth - BOUND_FULL_WIDTH_SHORT - Overlays.left;
                    draw(guiGraphics, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, armor, armorToughness, true);
                    Overlays.top += 6;
                }
                case Overlays.STYLE_BOTTOM_LEFT -> {
                    int top = screenHeight - Overlays.top;
                    int left = Overlays.left;
                    draw(guiGraphics, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, armor, armorToughness, false);
                    Overlays.top += 6;
                }
                case Overlays.STYLE_BOTTOM_RIGHT -> {
                    int top = screenHeight - Overlays.top;
                    int left = screenWidth - BOUND_FULL_WIDTH_SHORT - Overlays.left;
                    draw(guiGraphics, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, armor, armorToughness, true);
                    Overlays.top += 6;
                }
            }
        }
    }
}
