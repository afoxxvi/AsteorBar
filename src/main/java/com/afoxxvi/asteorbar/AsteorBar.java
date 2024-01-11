package com.afoxxvi.asteorbar;

import com.afoxxvi.asteorbar.core.NetworkHandler;
import com.afoxxvi.asteorbar.overlay.Overlays;
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
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Enabling AsteorBar");
    }


    public static class Config {
        public static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        //overlay config
        public static final ForgeConfigSpec.BooleanValue ENABLE_OVERLAY;
        public static final ForgeConfigSpec.IntValue OVERLAY_LAYOUT_STYLE;
        public static final ForgeConfigSpec.BooleanValue ENABLE_HEALTH_BLINK;
        public static final ForgeConfigSpec.DoubleValue LOW_HEALTH_RATE;
        public static final ForgeConfigSpec.BooleanValue OVERWRITE_VANILLA_ARMOR_BAR;
        public static final ForgeConfigSpec.BooleanValue OVERWRITE_VANILLA_EXPERIENCE_BAR;
        public static final ForgeConfigSpec.BooleanValue DISPLAY_EXPERIENCE_PROGRESS;
        public static final ForgeConfigSpec.BooleanValue ENABLE_FOOD_BLINK;
        public static final ForgeConfigSpec.BooleanValue DISPLAY_SATURATION;
        public static final ForgeConfigSpec.BooleanValue DISPLAY_EXHAUSTION;
        public static final ForgeConfigSpec.IntValue CORNER_BAR_LENGTH;
        public static final ForgeConfigSpec.IntValue CORNER_HORIZONTAL_PADDING;
        public static final ForgeConfigSpec.IntValue CORNER_VERTICAL_PADDING;
        //mob config
        public static final ForgeConfigSpec.BooleanValue ENABLE_HEALTH_BAR;
        public static final ForgeConfigSpec.DoubleValue MAX_DISTANCE;
        public static final ForgeConfigSpec.BooleanValue SHOW_ON_PLAYERS;
        public static final ForgeConfigSpec.BooleanValue SHOW_ON_BOSSES;
        public static final ForgeConfigSpec.BooleanValue SHOW_ON_FULL_HEALTH_WITHOUT_ABSORPTION;
        public static final ForgeConfigSpec.BooleanValue SHOW_ON_FULL_HEALTH_WITH_ABSORPTION;
        public static final ForgeConfigSpec.IntValue HEALTH_BAR_HALF_WIDTH;
        public static final ForgeConfigSpec.IntValue HEALTH_BAR_HALF_HEIGHT;
        public static final ForgeConfigSpec.DoubleValue HEALTH_BAR_OFFSET_Y;
        public static final ForgeConfigSpec.DoubleValue HEALTH_BAR_SCALE;
        public static final ForgeConfigSpec.DoubleValue HEALTH_BAR_TEXT_SCALE;
        public static final ForgeConfigSpec.DoubleValue HEALTH_BAR_TEXT_OFFSET_Y;
        public static final ForgeConfigSpec.IntValue HEALTH_BAR_BOUND_WIDTH;
        public static final ForgeConfigSpec.BooleanValue HEALTH_BAR_BOUND_VERTEX;
        public static final ForgeConfigSpec.IntValue HEALTH_BAR_HEALTH_COLOR;
        public static final ForgeConfigSpec.IntValue HEALTH_BAR_ABSORPTION_COLOR;
        public static final ForgeConfigSpec.IntValue HEALTH_BAR_BOUND_COLOR;
        public static final ForgeConfigSpec.IntValue HEALTH_BAR_EMPTY_COLOR;
        public static final ForgeConfigSpec.BooleanValue HEALTH_BAR_HEALTH_COLOR_DYNAMIC;
        public static final ForgeConfigSpec.IntValue HEALTH_BAR_HEALTH_COLOR_FULL;
        public static final ForgeConfigSpec.IntValue HEALTH_BAR_HEALTH_COLOR_EMPTY;

        static {
            BUILDER.push("overlay");
            ENABLE_OVERLAY = BUILDER
                    .comment("Whether to enable the overlay. If disabled, all other overlay options will be ignored.")
                    .translation("config.asteorbar.overlay.enableOverlay")
                    .define("enableOverlay", true);
            OVERLAY_LAYOUT_STYLE = BUILDER
                    .comment("The layout style of the overlay. 0: none, 1: above hot bar long, 2: above hot bar short, 3: top left, 4: top right, 5: bottom left, 6: bottom right")
                    .translation("config.asteorbar.overlay.overlayLayoutStyle")
                    .defineInRange("overlayLayoutStyle", 1, 0, Overlays.NUM_STYLES - 1);
            ENABLE_HEALTH_BLINK = BUILDER
                    .comment("Whether to enable health bar blink. This feature is designed to simulate the vanilla health icon blink.")
                    .translation("config.asteorbar.overlay.enableHealthBlink")
                    .define("enableHealthBlink", true);
            LOW_HEALTH_RATE = BUILDER
                    .comment("The health bar will start to flash when health rate is lower than this value. From 0.0-1.0. 0.0 means never flash.")
                    .translation("config.asteorbar.overlay.lowHealthRate")
                    .defineInRange("lowHealthRate", 0.2, 0.0, 1.0);
            OVERWRITE_VANILLA_ARMOR_BAR = BUILDER
                    .comment("Whether to overwrite vanilla armor bar. If you don't like the mod's armor bar, you can disable this option.")
                    .translation("config.asteorbar.overlay.overwriteVanillaArmorBar")
                    .define("overwriteVanillaArmorBar", true);
            OVERWRITE_VANILLA_EXPERIENCE_BAR = BUILDER
                    .comment("Whether to overwrite vanilla experience bar. If you don't like the mod's experience bar, you can disable this option, progress label won't be affected.")
                    .translation("config.asteorbar.overlay.overwriteVanillaExperienceBar")
                    .define("overwriteVanillaExperienceBar", true);
            DISPLAY_EXPERIENCE_PROGRESS = BUILDER
                    .comment("Whether to display experience progress on the side of the experience bar.")
                    .translation("config.asteorbar.overlay.displayExperienceProgress")
                    .define("displayExperienceProgress", true);
            ENABLE_FOOD_BLINK = BUILDER
                    .comment("Whether to enable food level bar blink. This feature is designed to simulate the vanilla food icon shake.")
                    .translation("config.asteorbar.overlay.enableFoodBlink")
                    .define("enableFoodBlink", true);
            DISPLAY_SATURATION = BUILDER
                    .comment("Whether to display saturation bar.")
                    .translation("config.asteorbar.overlay.displaySaturation")
                    .define("displaySaturation", true);
            DISPLAY_EXHAUSTION = BUILDER
                    .comment("Whether to display exhaustion bar.")
                    .translation("config.asteorbar.overlay.displayExhaustion")
                    .define("displayExhaustion", true);
            CORNER_BAR_LENGTH = BUILDER
                    .comment("The length of the bars if using corner layout. Affected bars: health, food, experience.")
                    .translation("config.asteorbar.overlay.cornerBarLength")
                    .defineInRange("cornerBarLength", 120, 40, 182);
            CORNER_HORIZONTAL_PADDING = BUILDER
                    .comment("The horizontal padding of the bars if using corner layout.")
                    .translation("config.asteorbar.overlay.cornerHorizontalPadding")
                    .defineInRange("cornerHorizontalPadding", 16, 0, 100);
            CORNER_VERTICAL_PADDING = BUILDER
                    .comment("The vertical padding of the bars if using corner layout.")
                    .translation("config.asteorbar.overlay.cornerVerticalPadding")
                    .defineInRange("cornerVerticalPadding", 16, 0, 100);
            BUILDER.pop();
            BUILDER.push("entity");
            ENABLE_HEALTH_BAR = BUILDER
                    .comment("Whether to enable health bar for entity. If disabled, all other health bar options will be ignored.")
                    .translation("config.asteorbar.entity.healthBarEnabled")
                    .define("healthBarEnabled", true);
            MAX_DISTANCE = BUILDER
                    .comment("The maximum distance to display mob health bar.")
                    .translation("config.asteorbar.entity.maxDistance")
                    .defineInRange("maxDistance", 32.0, 0.0, 100.0);
            SHOW_ON_PLAYERS = BUILDER
                    .comment("Whether to display health bar on players.")
                    .translation("config.asteorbar.entity.showOnPlayers")
                    .define("showOnPlayers", true);
            SHOW_ON_BOSSES = BUILDER
                    .comment("Whether to display health bar on bosses.")
                    .translation("config.asteorbar.entity.showOnBosses")
                    .define("showOnBosses", true);
            SHOW_ON_FULL_HEALTH_WITHOUT_ABSORPTION = BUILDER
                    .comment("Whether to display health bar on mobs with full health if the mob's absorption value is 0.")
                    .translation("config.asteorbar.entity.showOnFullHealthWithoutAbsorption")
                    .define("showOnFullHealthWithoutAbsorption", true);
            SHOW_ON_FULL_HEALTH_WITH_ABSORPTION = BUILDER
                    .comment("Whether to display health bar on mobs with full health if the mob's absorption value is not 0.")
                    .translation("config.asteorbar.entity.showOnFullHealthWithAbsorption")
                    .define("showOnFullHealthWithAbsorption", true);
            HEALTH_BAR_HALF_WIDTH = BUILDER
                    .comment("The half width of the health bar.")
                    .translation("config.asteorbar.entity.healthBarHalfWidth")
                    .defineInRange("healthBarHalfWidth", 50, 1, 1000);
            HEALTH_BAR_HALF_HEIGHT = BUILDER
                    .comment("The half height of the health bar.")
                    .translation("config.asteorbar.entity.healthBarHalfHeight")
                    .defineInRange("healthBarHalfHeight", 3, 1, 200);
            HEALTH_BAR_OFFSET_Y = BUILDER
                    .comment("The offset of the health bar on the Y axis.")
                    .translation("config.asteorbar.entity.healthBarOffsetY")
                    .defineInRange("healthBarOffsetY", 0.2, -10, 10);
            HEALTH_BAR_SCALE = BUILDER
                    .comment("The scale of the health bar.")
                    .translation("config.asteorbar.entity.healthBarScale")
                    .defineInRange("healthBarScale", 0.015, 0.001, 0.1);
            HEALTH_BAR_TEXT_SCALE = BUILDER
                    .comment("The scale of the health bar text.")
                    .translation("config.asteorbar.entity.healthBarTextScale")
                    .defineInRange("healthBarTextScale", 0.8, 0.1, 1.0);
            HEALTH_BAR_TEXT_OFFSET_Y = BUILDER
                    .comment("The offset of the health bar text on the Y axis.")
                    .translation("config.asteorbar.entity.healthBarTextOffsetY")
                    .defineInRange("healthBarTextOffsetY", -2.75, -10, 10);
            HEALTH_BAR_BOUND_WIDTH = BUILDER
                    .comment("The width of the health bar bound. 0 to 10. Hint: This value is a little hard to adjust. If you want to make the bounds looks thinner, " +
                            "you can increase the health bar width&height and decrease the health bar scale. You may also need to change the text scale and offset. " +
                            "This can be complicated, I highly recommend you to use some in-game config mod like 'configured'.")
                    .translation("config.asteorbar.entity.healthBarBoundWidth")
                    .defineInRange("healthBarBoundWidth", 2, 0, 10);
            HEALTH_BAR_BOUND_VERTEX = BUILDER
                    .comment("Whether to render the vertex of the health bar bound.")
                    .translation("config.asteorbar.entity.healthBarBoundVertex")
                    .define("healthBarBoundVertex", false);
            HEALTH_BAR_HEALTH_COLOR = BUILDER
                    .comment("The color of the health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.")
                    .translation("config.asteorbar.entity.healthBarHealthColor")
                    .defineInRange("healthBarHealthColor", 0xAA008000, Integer.MIN_VALUE, Integer.MAX_VALUE);
            HEALTH_BAR_ABSORPTION_COLOR = BUILDER
                    .comment("The color of the absorption bar. 0x00000000 to 0xFFFFFFFF. ARGB format.")
                    .translation("config.asteorbar.entity.healthBarAbsorptionColor")
                    .defineInRange("healthBarAbsorptionColor", 0xAAFFFF00, Integer.MIN_VALUE, Integer.MAX_VALUE);
            HEALTH_BAR_BOUND_COLOR = BUILDER
                    .comment("The color of the health bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.")
                    .translation("config.asteorbar.entity.healthBarBoundColor")
                    .defineInRange("healthBarBoundColor", 0x55000000, Integer.MIN_VALUE, Integer.MAX_VALUE);
            HEALTH_BAR_EMPTY_COLOR = BUILDER
                    .comment("The color of the empty part of the health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.")
                    .translation("config.asteorbar.entity.healthBarEmptyColor")
                    .defineInRange("healthBarEmptyColor", 0x33000000, Integer.MIN_VALUE, Integer.MAX_VALUE);
            HEALTH_BAR_HEALTH_COLOR_DYNAMIC = BUILDER
                    .comment("Whether to use dynamic color for health bar. The color will be picked between healthBarHealthColorFull and healthBarHealthColorEmpty " +
                            "based on the health rate. If disabled, the health bar will always be healthBarHealthColor")
                    .translation("config.asteorbar.entity.healthBarHealthColorGradient")
                    .define("healthBarHealthColorGradient", true);
            HEALTH_BAR_HEALTH_COLOR_FULL = BUILDER
                    .comment("The color of the health bar when the mob is full health. 0x00000000 to 0xFFFFFFFF. ARGB format.")
                    .translation("config.asteorbar.entity.healthBarHealthColorFull")
                    .defineInRange("healthBarHealthColorFull", 0xAA008000, Integer.MIN_VALUE, Integer.MAX_VALUE);
            HEALTH_BAR_HEALTH_COLOR_EMPTY = BUILDER
                    .comment("The color of the health bar when the mob is low health. 0x00000000 to 0xFFFFFFFF. ARGB format.")
                    .translation("config.asteorbar.entity.healthBarHealthColorEmpty")
                    .defineInRange("healthBarHealthColorEmpty", 0xAA800000, Integer.MIN_VALUE, Integer.MAX_VALUE);
            BUILDER.pop();
        }

        public static ForgeConfigSpec CONFIG = BUILDER.build();
    }
}
