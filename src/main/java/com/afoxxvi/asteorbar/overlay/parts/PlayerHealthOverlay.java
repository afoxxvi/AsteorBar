package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.afoxxvi.asteorbar.utils.Utils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.client.gui.overlay.ForgeGui;

import static com.afoxxvi.asteorbar.core.listener.ForgeEventListener.tickCount;


public class PlayerHealthOverlay extends BaseOverlay {
    private long healthBlinkTime = 0;
    private long lastHealthTime;
    private float lastHealth;

    private void draw(PoseStack poseStack, int left, int top, int right, int bottom, boolean highlight, int healthType, float health, float absorb, float maxHealth, float flashAlpha, int regenerationOffset, String hp, boolean flip) {
        //draw bound
        GuiHelper.drawTexturedRect(poseStack, left, top, right, bottom, 9, Y_HEALTH_EMPTY_BAR, 182, 5);
        if (highlight) {
            GuiHelper.drawTexturedRect(poseStack, left, top, right, bottom, 9, Y_HEALTH_BLINK_BOUND, 182, 5);
        } else if (flashAlpha > 0) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, flashAlpha);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GuiHelper.drawTexturedRect(poseStack, left, top, right, bottom, 9, Y_HEALTH_LOW_BOUND, 182, 5);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
        final var interLength = right - left - 2;
        //draw health
        int healthLength = (int) (interLength * health / (maxHealth + absorb));
        int emptyLength = (int) (interLength * (maxHealth - health) / (maxHealth + absorb));
        int absorbLength = interLength - healthLength - emptyLength;
        if (absorb <= 0.0F) {
            healthLength += absorbLength;
            absorbLength = 0;
        }
        healthLength += interLength - emptyLength - absorbLength - healthLength;
        drawTextureFillFlip(poseStack, left + 1, top, right - 1, healthLength, 5, 10, healthType, flip);
        //draw absorption
        if (absorbLength > 0) {
            if (flip) {
                drawTextureFillFlip(poseStack, left + 1, top, right - 1 - healthLength, absorbLength, 5, 10, Y_HEALTH_ABSORPTION_FILL, true);
            } else {
                drawTextureFillFlip(poseStack, left + 1 + healthLength, top, right - 1, absorbLength, 5, 10, Y_HEALTH_ABSORPTION_FILL, false);
            }
        }
        //draw regeneration
        if (regenerationOffset >= 0) {
            int textureLeft;
            int textureRight;
            if (flip) {
                textureLeft = regenerationOffset - 180;
            } else {
                textureLeft = -regenerationOffset;
            }
            textureRight = textureLeft + right - left - 2;
            if (textureRight > 0) {
                GuiHelper.drawTexturedRect(poseStack, left + 1, top, 10 + 180 + textureLeft, Y_REGENERATION_FILL, -textureLeft, 5);
                GuiHelper.drawTexturedRect(poseStack, left + 1 - textureLeft, top, 10, Y_REGENERATION_FILL, textureRight, 5);
            } else {
                GuiHelper.drawTexturedRect(poseStack, left + 1, top, 10 + 180 + textureLeft, Y_REGENERATION_FILL, right - left - 2, 5);
            }
        }
        Overlays.addStringRender((left + right) / 2, top - 2, 0xFFFFFF, hp, Overlays.ALIGN_CENTER, true);
        //GuiHelper.drawCenteredString(poseStack, hp, (left + right) / 2, top - 3, 0xFFFFFF);
    }

    @Override
    public void renderOverlay(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
            gui.setupOverlayRenderState(true, false);
            RenderSystem.enableBlend();
            RenderSystem.setShaderTexture(0, TEXTURE);
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
            int healthType = Y_HEALTH_NORMAL_FILL;
            if (player.hasEffect(MobEffects.POISON)) {
                healthType = Y_HEALTH_POISON_FILL;
            } else if (player.hasEffect(MobEffects.WITHER)) {
                healthType = Y_HEALTH_WITHER_FILL;
            } else if (player.isFullyFrozen()) {
                healthType = Y_HEALTH_FROZEN_FILL;
            }
            var flashAlpha = -1F;
            if (health < maxHealth * AsteorBar.Config.LOW_HEALTH_RATE.get() && !highlight) {
                int margin = Math.abs((int) tickCount % 20 - 10);
                flashAlpha = 0.08F * margin;
            }
            var regenerationOffset = -1;
            if (player.hasEffect(MobEffects.REGENERATION)) {
                regenerationOffset = (int) (tickCount % 30 * 6);
            }
            String hp = absorb > 0.0F ? (Utils.formatNumber(health) + "(+" + Utils.formatNumber(absorb) + ")/" + Utils.formatNumber(maxHealth)) : (Utils.formatNumber(health) + "/" + Utils.formatNumber(maxHealth));
            switch (Overlays.style) {
                case Overlays.STYLE_NONE -> {

                }
                case Overlays.STYLE_ABOVE_HOT_BAR_LONG -> {
                    int top = screenHeight - gui.leftHeight - 2;
                    int left = screenWidth / 2 - 91;
                    gui.leftHeight += 12;
                    draw(poseStack, left, top, left + BOUND_FULL_WIDTH_LONG, top + 5, highlight, healthType, health, absorb, maxHealth, flashAlpha, regenerationOffset, hp, false);
                }
                case Overlays.STYLE_ABOVE_HOT_BAR_SHORT -> {
                    int top = screenHeight - gui.leftHeight + 4;
                    int left = screenWidth / 2 - 91;
                    gui.leftHeight += 6;
                    draw(poseStack, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, highlight, healthType, health, absorb, maxHealth, flashAlpha, regenerationOffset, hp, false);
                }
                case Overlays.STYLE_TOP_LEFT -> {
                    int top = Overlays.top;
                    int left = Overlays.left;
                    draw(poseStack, left, top, left + Overlays.length, top + 5, highlight, healthType, health, absorb, maxHealth, flashAlpha, regenerationOffset, hp, false);
                    Overlays.top += 6;
                }
                case Overlays.STYLE_TOP_RIGHT -> {
                    int top = Overlays.top;
                    int left = screenWidth - Overlays.length - Overlays.left;
                    draw(poseStack, left, top, left + Overlays.length, top + 5, highlight, healthType, health, absorb, maxHealth, flashAlpha, regenerationOffset, hp, true);
                    Overlays.top += 6;
                }
                case Overlays.STYLE_BOTTOM_LEFT -> {
                    int top = screenHeight - Overlays.top;
                    int left = Overlays.left;
                    draw(poseStack, left, top, left + Overlays.length, top + 5, highlight, healthType, health, absorb, maxHealth, flashAlpha, regenerationOffset, hp, false);
                    Overlays.top += 6;
                }
                case Overlays.STYLE_BOTTOM_RIGHT -> {
                    int top = screenHeight - Overlays.top;
                    int left = screenWidth - Overlays.length - Overlays.left;
                    draw(poseStack, left, top, left + Overlays.length, top + 5, highlight, healthType, health, absorb, maxHealth, flashAlpha, regenerationOffset, hp, true);
                    Overlays.top += 6;
                }
            }
            RenderSystem.disableBlend();
        }
    }
}
