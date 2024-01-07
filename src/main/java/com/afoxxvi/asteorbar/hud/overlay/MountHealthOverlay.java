package com.afoxxvi.asteorbar.hud.overlay;

import com.afoxxvi.asteorbar.hud.listener.RenderListener;
import com.afoxxvi.asteorbar.hud.utils.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MountHealthOverlay implements IGuiOverlay {
    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
            gui.setupOverlayRenderState(true, false);
            Player player = (Player) gui.getMinecraft().getCameraEntity();
            if (player == null) return;
            Entity tmp = player.getVehicle();
            if (tmp instanceof LivingEntity) {
                RenderSystem.setShaderTexture(0, RenderListener.TEXTURE);
                LivingEntity mount = (LivingEntity) tmp;
                int left = screenWidth / 2 - 91;
                int top = screenHeight - gui.rightHeight + 4;
                gui.rightHeight += 12;
                int health = (int) Math.ceil(mount.getHealth());
                float healthMax = mount.getMaxHealth();
                int healthWidth = (int) (180 * health / healthMax);
                GuiHelper.drawTexturedRect(poseStack, left, top, 9, RenderListener.Y_MOUNT_HEALTH_BAR, 182, 9);
                GuiHelper.drawTexturedRect(poseStack, left + 1, top, 10, RenderListener.Y_MOUNT_HEALTH_FILL, healthWidth, 9);
            }
        }
    }
}
