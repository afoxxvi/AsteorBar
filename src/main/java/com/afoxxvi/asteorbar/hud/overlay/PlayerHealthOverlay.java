package com.afoxxvi.asteorbar.hud.overlay;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.hud.listener.RenderListener;
import com.afoxxvi.asteorbar.hud.utils.GuiHelper;
import com.afoxxvi.asteorbar.hud.utils.Utils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static com.afoxxvi.asteorbar.hud.listener.RenderListener.*;

public class PlayerHealthOverlay implements IGuiOverlay {
    private long healthBlinkTime = 0;
    private long lastHealthTime;
    private float lastHealth;

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
            gui.setupOverlayRenderState(true, false);
            RenderSystem.enableBlend();
            //gui.renderHealth(screenWidth, screenHeight, poseStack);
            RenderSystem.setShaderTexture(0, RenderListener.TEXTURE);
            var mc = gui.getMinecraft();
            var player = mc.player;
            if (player == null) return;
            float health = player.getHealth();
            boolean highlight = false;
            if (AsteorBar.Config.ENABLE_HEALTH_BLINK.get()) {
                highlight = healthBlinkTime > tickCount && (healthBlinkTime - tickCount) / 3L % 2L == 1L;
                if (health < lastHealth && player.invulnerableTime > 0) {
                    lastHealthTime = Util.getMillis();
                    healthBlinkTime = tickCount + 20L;
                } else if (health > lastHealth && player.invulnerableTime > 0) {
                    lastHealthTime = Util.getMillis();
                    healthBlinkTime = tickCount + 10L;
                }
                if (Util.getMillis() - lastHealthTime > 1000L) {
                    lastHealth = health;
                    lastHealthTime = Util.getMillis();
                }
                lastHealth = health;
            }
            float maxHealth = player.getMaxHealth();
            float absorb = player.getAbsorptionAmount();
            int top = screenHeight - gui.leftHeight - 2;
            int left = screenWidth / 2 - 91;
            gui.leftHeight += 12;
            GuiHelper.drawTexturedRect(poseStack, left, top, 9, Y_HEALTH_EMPTY_BAR, 182, 9);
            if (highlight) {
                GuiHelper.drawTexturedRect(poseStack, left, top, 9, Y_HEALTH_BLINK_BOUND, 182, 9);
            }
            int healthType = Y_HEALTH_NORMAL_FILL;
            if (player.hasEffect(MobEffects.POISON)) {
                healthType = Y_HEALTH_POISON_FILL;
            } else if (player.hasEffect(MobEffects.WITHER)) {
                healthType = Y_HEALTH_WITHER_FILL;
            } else if (player.isFullyFrozen()) {
                healthType = Y_HEALTH_FROZEN_FILL;
            }
            int healthLength = (int) (180F * health / (maxHealth + absorb));
            int emptyLength = (int) (180F * (maxHealth - health) / (maxHealth + absorb));
            int absorbLength = 180 - healthLength - emptyLength;
            if (absorb <= 0.0F) {
                healthLength += absorbLength;
                absorbLength = 0;
            }
            while (healthLength + emptyLength + absorbLength < 180) {
                ++healthLength;
            }
            GuiHelper.drawTexturedRect(poseStack, left + 1, top, 10, healthType, healthLength, 9);
            if (absorb > 0.0F) {
                GuiHelper.drawTexturedRect(poseStack, left + 1 + healthLength, top, 10, Y_HEALTH_ABSORPTION_FILL, absorbLength, 9);
            }
            if (health < maxHealth * AsteorBar.Config.LOW_HEALTH_RATE.get() && !highlight) {
                int margin = Math.abs((int) tickCount % 20 - 10);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.08F * margin);
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                GuiHelper.drawTexturedRect(poseStack, left, top, 9, Y_HEALTH_LOW_BOUND, 182, 9);
                //GuiHelper.drawSolidColor(poseStack, left + 1 + healthLength + absorbLength, top + 1, left + 182 - 1, top + 5 - 1, margin * 0x1000000 + 0xFF0000);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
            if (player.hasEffect(MobEffects.REGENERATION)) {
                int offset = (int) (tickCount % 30 * 6);
                GuiHelper.drawTexturedRect(poseStack, left + 1 + offset, top, 10, Y_REGENERATION_FILL, 180 - offset, 9);
                GuiHelper.drawTexturedRect(poseStack, left + 1, top, 10 + 180 - offset, Y_REGENERATION_FILL, offset, 9);
            }
            String hp = absorb > 0.0F ?
                    (Utils.formatNumber(health) + "(+" + Utils.formatNumber(absorb) + ")/" + Utils.formatNumber(maxHealth)) :
                    (Utils.formatNumber(health) + "/" + Utils.formatNumber(maxHealth));
            GuiHelper.drawCenteredString(poseStack, hp, screenWidth / 2, top - 3, 0xFFFFFF);
            RenderSystem.disableBlend();
        }
    }
}
