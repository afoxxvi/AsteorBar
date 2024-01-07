package com.afoxxvi.asteorbar.hud.overlay;

import com.afoxxvi.asteorbar.hud.listener.RenderListener;
import com.afoxxvi.asteorbar.hud.utils.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class FoodLevelOverlay implements IGuiOverlay {
    private int foodBlinkTime = 0;

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        boolean isMounted = gui.getMinecraft().player.getVehicle() instanceof LivingEntity;
        if (!isMounted && !gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
            gui.setupOverlayRenderState(true, false);
            //gui.renderFood(screenWidth, screenHeight, poseStack);
            //gui.getMinecraft().textureManager.bindForSetup(RenderListener.TEXTURE);
            RenderSystem.setShaderTexture(0, RenderListener.TEXTURE);
            FoodData stats = gui.getMinecraft().player.getFoodData();
            int level = stats.getFoodLevel();
            float saturation = stats.getSaturationLevel();
            int left = screenWidth / 2 - 91;
            int top = screenHeight - gui.rightHeight + 4;
            gui.rightHeight += 12;
            int foodWidth = 180 * level / 20;
            int saturationWidth = (int) (182 * saturation / 20.0F);
            GuiHelper.drawTexturedRect(poseStack, left, top, 9, RenderListener.Y_FOOD_EMPTY_BAR, 182, 9);
            int foodType = RenderListener.Y_FOOD_NORMAL_FILL;
            var player = gui.getMinecraft().player;
            if (player.hasEffect(MobEffects.HUNGER)) {
                foodType = RenderListener.Y_FOOD_HUNGER_FILL;
            }

            if (player.getFoodData().getSaturationLevel() <= 0.0F && RenderListener.tickCount % (level * 3L + 1) == 0) {
                foodBlinkTime = 2;
            }
            if (foodBlinkTime > 0) {
                foodBlinkTime--;
                GuiHelper.drawTexturedRect(poseStack, left, top, 9, RenderListener.Y_FOOD_BLINK_BOUND, 182, 9);
            }
            GuiHelper.drawTexturedRect(poseStack, left + 1, top, 10, foodType, foodWidth, 9);
            GuiHelper.drawTexturedRect(poseStack, left, top, 9, RenderListener.Y_SATURATION_BOUND, saturationWidth, 9);
        }
    }
}
