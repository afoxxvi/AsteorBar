package com.afoxxvi.asteorbar.core.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.entity.EntityRenderer;
import com.afoxxvi.asteorbar.key.KeyBinding;
import com.afoxxvi.asteorbar.overlay.Overlays;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AsteorBar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEventListener {
    public static long tickCount = 0L;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Minecraft.getInstance().isPaused()) {
            tickCount++;
        }
    }

    @SubscribeEvent
    public static void disableVanillaOverlays(RenderGuiOverlayEvent.Pre event) {
        if (!AsteorBar.Config.ENABLE_OVERLAY.get()) return;
        NamedGuiOverlay overlay = event.getOverlay();
        if (overlay == VanillaGuiOverlay.VIGNETTE.type()) {
            Overlays.reset();
        }
        if (overlay == VanillaGuiOverlay.PLAYER_HEALTH.type() || overlay == VanillaGuiOverlay.FOOD_LEVEL.type() || overlay == VanillaGuiOverlay.AIR_LEVEL.type() || (AsteorBar.Config.OVERWRITE_VANILLA_EXPERIENCE_BAR.get() && overlay == VanillaGuiOverlay.EXPERIENCE_BAR.type()) || overlay == VanillaGuiOverlay.MOUNT_HEALTH.type() || (AsteorBar.Config.OVERWRITE_VANILLA_ARMOR_BAR.get() && overlay == VanillaGuiOverlay.ARMOR_LEVEL.type())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void handleKeyInput(InputEvent.Key event) {
        while (KeyBinding.TOGGLE_OVERLAY.consumeClick()) {
            Overlays.style = (Overlays.style + 1) % Overlays.NUM_STYLES;
            AsteorBar.Config.ENABLE_OVERLAY.set(Overlays.style != Overlays.STYLE_NONE);
            AsteorBar.Config.ENABLE_OVERLAY.save();
            AsteorBar.Config.OVERLAY_LAYOUT_STYLE.set(Overlays.style);
            AsteorBar.Config.OVERLAY_LAYOUT_STYLE.save();
        }
        while (KeyBinding.TOGGLE_MOB_BAR.consumeClick()) {
            AsteorBar.Config.ENABLE_HEALTH_BAR.set(!AsteorBar.Config.ENABLE_HEALTH_BAR.get());
            AsteorBar.Config.ENABLE_HEALTH_BAR.save();
        }
    }
}
