package com.afoxxvi.asteorbar;

import com.afoxxvi.asteorbar.hud.listener.NetworkHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AsteorBar.MOD_ID)
public class AsteorBar {
    public static final String MOD_ID = "asteorbar";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AsteorBar() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(NetworkHandler.class);
        NetworkHandler.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CONFIG);
        //MinecraftForge.EVENT_BUS.register(new RenderListener());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Enabling AsteorBar");
    }


    public static class Config {
        public static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        public static final ForgeConfigSpec.BooleanValue ENABLE_HEALTH_BLINK = BUILDER
                .comment("Whether to enable health bar blink. This feature is designed to simulate the vanilla health icon blink.")
                .translation("config.asteorbar.enableHealthBlink")
                .define("enableHealthBlink", true);

        public static final ForgeConfigSpec.DoubleValue LOW_HEALTH_RATE = BUILDER
                .comment("The health bar will start to flash when health rate is lower than this value. From 0.0-1.0. 0.0 means never flash.")
                .translation("config.asteorbar.lowHealthRate")
                .defineInRange("lowHealthRate", 0.2, 0.0, 1.0);

        public static final ForgeConfigSpec.BooleanValue OVERWRITE_VANILLA_ARMOR_BAR = BUILDER
                .comment("Whether to overwrite vanilla armor bar. If you don't like the mod's armor bar, you can disable this option.")
                .translation("config.asteorbar.overwriteVanillaArmorBar")
                .define("overwriteVanillaArmorBar", true);

        public static final ForgeConfigSpec.BooleanValue OVERWRITE_VANILLA_EXPERIENCE_BAR = BUILDER
                .comment("Whether to overwrite vanilla experience bar. If you don't like the mod's experience bar, you can disable this option, progress label won't be affected.")
                .translation("config.asteorbar.overwriteVanillaExperienceBar")
                .define("overwriteVanillaExperienceBar", true);

        public static final ForgeConfigSpec.BooleanValue DISPLAY_EXPERIENCE_PROGRESS = BUILDER
                .comment("Whether to display experience progress on the side of the experience bar.")
                .translation("config.asteorbar.displayExperienceProgress")
                .define("displayExperienceProgress", true);

        public static final ForgeConfigSpec.BooleanValue ENABLE_FOOD_BLINK = BUILDER
                .comment("Whether to enable food level bar blink. This feature is designed to simulate the vanilla food icon shake.")
                .translation("config.asteorbar.enableFoodBlink")
                .define("enableFoodBlink", true);

        public static final ForgeConfigSpec.BooleanValue DISPLAY_SATURATION = BUILDER
                .comment("Whether to display saturation bar.")
                .translation("config.asteorbar.displaySaturation")
                .define("displaySaturation", true);

        public static final ForgeConfigSpec.BooleanValue DISPLAY_EXHAUSTION = BUILDER
                .comment("Whether to display exhaustion bar.")
                .translation("config.asteorbar.displayExhaustion")
                .define("displayExhaustion", true);

        public static ForgeConfigSpec CONFIG = BUILDER.build();
    }
}
