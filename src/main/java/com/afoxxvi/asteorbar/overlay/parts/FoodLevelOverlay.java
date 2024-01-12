package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.client.gui.overlay.ForgeGui;

import static com.afoxxvi.asteorbar.core.listener.ForgeEventListener.tickCount;

public class FoodLevelOverlay extends BaseOverlay {
    private int foodBlinkTime = 0;

    private void draw(GuiGraphics guiGraphics, int left, int top, int right, int bottom, boolean highlight, int foodType, int foodLevel, float saturation, float exhaustion, boolean flip) {
        GuiHelper.drawTexturedRect(guiGraphics, left, top, right, bottom, 9, Y_FOOD_EMPTY_BAR, BOUND_FULL_WIDTH_LONG, 9);
        if (highlight) {
            GuiHelper.drawTexturedRect(guiGraphics, left, top, right, bottom, 9, Y_FOOD_BLINK_BOUND, BOUND_FULL_WIDTH_LONG, 9);
        }
        final int interWidth = right - left - 2;
        int foodWidth = (int) (interWidth * foodLevel / 20.0F);
        drawTextureFillFlip(guiGraphics, left + 1, top, right - 1, foodWidth, 9, 10, foodType, flip);
        if (AsteorBar.Config.DISPLAY_SATURATION.get()) {
            int saturationWidth = (int) ((right - left) * (saturation / 20.0F));
            drawTextureBoundFlip(guiGraphics, left, top, right, bottom, saturationWidth, 9, 9, Y_SATURATION_BOUND, BOUND_FULL_WIDTH_LONG, flip);
        }
        if (AsteorBar.Config.DISPLAY_EXHAUSTION.get()) {
            int exhaustionWidth = (int) (interWidth * (Math.min(4.0F, exhaustion) / 4.0F));
            drawTextureFillFlip(guiGraphics, left + 1, top, right - 1, exhaustionWidth, 9, 10, Y_FOOD_EXHAUSTION_FILL, FILL_FULL_WIDTH_LONG, flip);
        }
    }

    @Override
    public void renderOverlay(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        var player = gui.getMinecraft().player;
        if (player == null) return;
        boolean isMounted = player.getVehicle() instanceof LivingEntity;
        if (!isMounted && !gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
            gui.setupOverlayRenderState(true, false);
            RenderSystem.setShaderTexture(0, TEXTURE);
            FoodData stats = gui.getMinecraft().player.getFoodData();
            int level = stats.getFoodLevel();
            //see NetworkHandler, without sync this field is always 0
            float saturation = stats.getSaturationLevel();
            float exhaustion = stats.getExhaustionLevel();
            int foodType = Y_FOOD_NORMAL_FILL;
            if (player.hasEffect(MobEffects.HUNGER)) {
                foodType = Y_FOOD_HUNGER_FILL;
            }
            if (AsteorBar.Config.ENABLE_FOOD_BLINK.get()) {
                if (player.getFoodData().getSaturationLevel() <= 0.0F && tickCount % (Math.max(4, level) * 3L + 1) == 0) {
                    foodBlinkTime = 2;
                }
                if (foodBlinkTime > 0) {
                    foodBlinkTime--;
                }
            }
            switch (Overlays.style) {
                case Overlays.STYLE_NONE -> {

                }
                case Overlays.STYLE_ABOVE_HOT_BAR_LONG -> {
                    int left = screenWidth / 2 - 91;
                    int top = screenHeight - gui.rightHeight + 4;
                    gui.rightHeight += 12;
                    draw(guiGraphics, left, top, left + BOUND_FULL_WIDTH_LONG, top + 5, foodBlinkTime > 0, foodType, level, saturation, exhaustion, false);
                }
                case Overlays.STYLE_ABOVE_HOT_BAR_SHORT -> {
                    int left = screenWidth / 2 + 10;
                    int top = screenHeight - gui.rightHeight + 4;
                    gui.rightHeight += 6;
                    draw(guiGraphics, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, foodBlinkTime > 0, foodType, level, saturation, exhaustion, true);
                }
                case Overlays.STYLE_TOP_LEFT -> {
                    int top = Overlays.top;
                    int left = Overlays.left;
                    draw(guiGraphics, left, top, left + Overlays.length, top + 5, foodBlinkTime > 0, foodType, level, saturation, exhaustion, false);
                    Overlays.top += 6;
                }
                case Overlays.STYLE_TOP_RIGHT -> {
                    int top = Overlays.top;
                    int left = screenWidth - Overlays.length - Overlays.left;
                    draw(guiGraphics, left, top, left + Overlays.length, top + 5, foodBlinkTime > 0, foodType, level, saturation, exhaustion, true);
                    Overlays.top += 6;
                }
                case Overlays.STYLE_BOTTOM_LEFT -> {
                    int top = screenHeight - Overlays.top;
                    int left = Overlays.left;
                    draw(guiGraphics, left, top, left + Overlays.length, top + 5, foodBlinkTime > 0, foodType, level, saturation, exhaustion, false);
                    Overlays.top += 6;
                }
                case Overlays.STYLE_BOTTOM_RIGHT -> {
                    int top = screenHeight - Overlays.top;
                    int left = screenWidth - Overlays.length - Overlays.left;
                    draw(guiGraphics, left, top, left + Overlays.length, top + 5, foodBlinkTime > 0, foodType, level, saturation, exhaustion, true);
                    Overlays.top += 6;
                }
            }
        }
    }
}
