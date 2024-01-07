package com.afoxxvi.asteorbar.hud.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AsteorBar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RenderListener {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AsteorBar.MOD_ID, "textures/gui/overlay.png");
    public static final int Y_HEALTH_EMPTY_BAR = 0;
    public static final int Y_HEALTH_BLINK_BOUND = 9;

    public static final int Y_HEALTH_NORMAL_FILL = 18;
    public static final int Y_HEALTH_POISON_FILL = 27;
    public static final int Y_HEALTH_WITHER_FILL = 36;
    public static final int Y_HEALTH_ABSORPTION_FILL = 45;
    public static final int Y_HEALTH_FROZEN_FILL = 135;
    public static final int Y_REGENERATION_FILL = 144;

    public static final int Y_FOOD_EMPTY_BAR = 54;
    public static final int Y_FOOD_BLINK_BOUND = 63;
    public static final int Y_FOOD_NORMAL_FILL = 72;
    public static final int Y_MOUNT_HEALTH_BAR = 153;
    public static final int Y_MOUNT_HEALTH_FILL = 162;
    public static final int Y_SATURATION_BOUND = 81;
    public static final int Y_FOOD_HUNGER_FILL = 90;
    public static final int Y_EXPERIENCE_EMPTY_BAR = 99;
    public static final int Y_EXPERIENCE_BAR = 108;
    public static final int Y_AIR_BOUND = 117;
    public static final int Y_AIR_FILL = 126;
    public static long tickCount = 0L;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Minecraft.getInstance().isPaused()) {
            tickCount++;
        }
    }

    @SubscribeEvent
    public static void onEntityRender(RenderLivingEvent event) {

    }

    static Set<String> set = new HashSet<>();

    @SubscribeEvent
    public static void disableVanillaOverlays(RenderGuiOverlayEvent.Pre event) {
        NamedGuiOverlay overlay = event.getOverlay();
        var name = overlay.id().toString();
        if (!set.contains(name)) {
            set.add(name);
            AsteorBar.LOGGER.info(name);
        }
        if (overlay == VanillaGuiOverlay.PLAYER_HEALTH.type()) {
            event.setCanceled(true);
            return;
        }
        if (overlay == VanillaGuiOverlay.FOOD_LEVEL.type()) {
            event.setCanceled(true);
            return;
        }
        if (overlay == VanillaGuiOverlay.AIR_LEVEL.type()) {
            event.setCanceled(true);
            return;
        }
        if (overlay == VanillaGuiOverlay.EXPERIENCE_BAR.type()) {
            event.setCanceled(true);
            return;
        }
        if (overlay == VanillaGuiOverlay.MOUNT_HEALTH.type()) {
            event.setCanceled(true);
            return;
        }
        return;
    }
}
