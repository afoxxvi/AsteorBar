package com.afoxxvi.asteorbar.hud.overlay;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.hud.listener.RenderListener;
import com.afoxxvi.asteorbar.hud.utils.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ArmorLevelOverlay implements IGuiOverlay {
    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements() && AsteorBar.Config.OVERWRITE_VANILLA_ARMOR_BAR.get()) {
            gui.setupOverlayRenderState(true, false);
            RenderSystem.setShaderTexture(0, RenderListener.TEXTURE);
            int left = screenWidth / 2 - 91;
            int top = screenHeight - gui.leftHeight + 4;
            var player = gui.getMinecraft().player;
            if (player == null) return;
            int armor = player.getArmorValue();
            if (armor <= 0) return;
            GuiHelper.drawTexturedRect(poseStack, left, top, 9, RenderListener.Y_ARMOR_EMPTY_BAR, 81, 9);
            int armorWidth = (int) (79 * Math.min(1.0, armor / 20f));
            GuiHelper.drawTexturedRect(poseStack, left, top, 10, RenderListener.Y_ARMOR_BAR, armorWidth, 9);
            double armorToughness = -1;
            var attr = player.getAttribute(Attributes.ARMOR_TOUGHNESS);
            if (attr != null) {
                armorToughness = attr.getValue();
            }
            if (armorToughness > 0) {
                int armorToughnessWidth = (int) (81 * Math.min(1.0, armorToughness / 20f));
                GuiHelper.drawTexturedRect(poseStack, left, top, 9, RenderListener.Y_ARMOR_BOUND, armorToughnessWidth, 9);
            }
            gui.leftHeight += 6;
        }
    }
}
