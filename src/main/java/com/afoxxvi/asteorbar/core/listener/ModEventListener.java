package com.afoxxvi.asteorbar.core.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.common.Mod;

import static com.afoxxvi.asteorbar.overlay.Overlays.*;

@Mod.EventBusSubscriber(modid = AsteorBar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventListener {
    public static boolean registerOverlay = false;

    public static void registerOverlays() {
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, "player_health", PLAYER_HEALTH);
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.FOOD_LEVEL_ELEMENT, "food_level", FOOD_LEVEL);
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.MOUNT_HEALTH_ELEMENT, "mount_health", MOUNT_HEALTH);
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.AIR_LEVEL_ELEMENT, "air_level", AIR_LEVEL);
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, "experience_bar", EXPERIENCE_BAR);
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, "armor_level", ARMOR_LEVEL);
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, "string", STRING);
        registerOverlay = true;
    }
}
