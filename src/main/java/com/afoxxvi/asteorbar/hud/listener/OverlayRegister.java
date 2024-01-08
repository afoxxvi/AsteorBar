package com.afoxxvi.asteorbar.hud.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.hud.overlay.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AsteorBar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class OverlayRegister {
    private static final PlayerHealthOverlay PLAYER_HEALTH = new PlayerHealthOverlay();
    private static final FoodLevelOverlay FOOD_LEVEL = new FoodLevelOverlay();
    private static final AirLevelOverlay AIR_LEVEL = new AirLevelOverlay();
    private static final ExperienceBarOverlay EXPERIENCE_BAR = new ExperienceBarOverlay();
    private static final MountHealthOverlay MOUNT_HEALTH = new MountHealthOverlay();
    private static final ArmorLevelOverlay ARMOR_LEVEL = new ArmorLevelOverlay();

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        AsteorBar.LOGGER.info("Registering Overlays");
        event.registerBelow(VanillaGuiOverlay.PLAYER_HEALTH.id(), "player_health", PLAYER_HEALTH);
        event.registerBelow(VanillaGuiOverlay.FOOD_LEVEL.id(), "food_level", FOOD_LEVEL);
        event.registerBelow(VanillaGuiOverlay.AIR_LEVEL.id(), "air_level", AIR_LEVEL);
        event.registerBelow(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "experience_bar", EXPERIENCE_BAR);
        event.registerBelow(VanillaGuiOverlay.MOUNT_HEALTH.id(), "mount_health", MOUNT_HEALTH);
        event.registerBelow(VanillaGuiOverlay.ARMOR_LEVEL.id(), "armor_level", ARMOR_LEVEL);
    }
}
