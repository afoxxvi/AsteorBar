package com.afoxxvi.asteorbar.core.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.key.KeyBinding;
import com.afoxxvi.asteorbar.overlay.parts.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.afoxxvi.asteorbar.overlay.Overlays.*;

@Mod.EventBusSubscriber(modid = AsteorBar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventListener {
    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        AsteorBar.LOGGER.info("Registering Overlays");
        event.registerBelow(VanillaGuiOverlay.PLAYER_HEALTH.id(), "player_health", PLAYER_HEALTH);
        event.registerBelow(VanillaGuiOverlay.FOOD_LEVEL.id(), "food_level", FOOD_LEVEL);
        event.registerBelow(VanillaGuiOverlay.MOUNT_HEALTH.id(), "mount_health", MOUNT_HEALTH);
        event.registerBelow(VanillaGuiOverlay.AIR_LEVEL.id(), "air_level", AIR_LEVEL);
        event.registerBelow(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "experience_bar", EXPERIENCE_BAR);
        event.registerBelow(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "armor_level", ARMOR_LEVEL);
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "string", STRING);
    }

    @SubscribeEvent
    public static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.TOGGLE_OVERLAY);
        event.register(KeyBinding.TOGGLE_MOB_BAR);
    }
}
