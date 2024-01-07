package com.afoxxvi.asteorbar;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
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
        //MinecraftForge.EVENT_BUS.register(new RenderListener());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Enabling AsteorBar");
    }
}
