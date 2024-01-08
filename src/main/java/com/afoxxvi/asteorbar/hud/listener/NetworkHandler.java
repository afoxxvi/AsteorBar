package com.afoxxvi.asteorbar.hud.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class NetworkHandler {
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(AsteorBar.MOD_ID, "network"), () -> "1.0", s -> true, s -> true);

    //avoid sending packets too frequently
    private static final Map<UUID, Float> EXHAUSTION = new HashMap<>();
    private static final Map<UUID, Float> SATURATION = new HashMap<>();

    public static void init() {
        CHANNEL.registerMessage(0, ExhaustionPacket.class, ExhaustionPacket::encode, ExhaustionPacket::decode, ExhaustionPacket::handle);
        //Without special handle for exhaustion, saturation updates correctly.
        //But with it, saturation updates incorrectly. Strange. Now have to sync both.
        CHANNEL.registerMessage(1, SaturationPacket.class, SaturationPacket::encode, SaturationPacket::decode, SaturationPacket::handle);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer player) {
            var foodStats = player.getFoodData();
            float exhaustionLevel = foodStats.getExhaustionLevel();
            Float oldExhaustion = EXHAUSTION.get(player.getUUID());
            if (oldExhaustion == null || Math.abs(oldExhaustion - exhaustionLevel) >= 0.01F) {
                EXHAUSTION.put(player.getUUID(), exhaustionLevel);
                CHANNEL.sendTo(new ExhaustionPacket(exhaustionLevel), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
            float saturationLevel = foodStats.getSaturationLevel();
            Float oldSaturation = SATURATION.get(player.getUUID());
            if (oldSaturation == null || Math.abs(oldSaturation - saturationLevel) >= 0.01F) {
                SATURATION.put(player.getUUID(), saturationLevel);
                CHANNEL.sendTo(new SaturationPacket(saturationLevel), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    private static Player getPlayer(NetworkEvent.Context context) {
        return context.getDirection() == NetworkDirection.PLAY_TO_SERVER ? context.getSender() : Minecraft.getInstance().player;
    }

    public static class SaturationPacket {
        public float saturation;

        public SaturationPacket(float saturation) {
            this.saturation = saturation;
        }

        public static void encode(SaturationPacket packet, FriendlyByteBuf buffer) {
            buffer.writeFloat(packet.saturation);
        }

        public static SaturationPacket decode(FriendlyByteBuf buffer) {
            return new SaturationPacket(buffer.readFloat());
        }

        public static void handle(SaturationPacket packet, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                var player = getPlayer(context.get());
                if (player != null) {
                    var foodStats = player.getFoodData();
                    foodStats.setSaturation(packet.saturation);
                }
            });
            context.get().setPacketHandled(true);
        }
    }

    public static class ExhaustionPacket {
        public float exhaustion;

        public ExhaustionPacket(float exhaustion) {
            this.exhaustion = exhaustion;
        }

        public static void encode(ExhaustionPacket packet, FriendlyByteBuf buffer) {
            buffer.writeFloat(packet.exhaustion);
        }

        public static ExhaustionPacket decode(FriendlyByteBuf buffer) {
            return new ExhaustionPacket(buffer.readFloat());
        }

        public static void handle(ExhaustionPacket packet, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                var player = getPlayer(context.get());
                if (player != null) {
                    var foodStats = player.getFoodData();
                    foodStats.setExhaustion(packet.exhaustion);
                }
            });
            context.get().setPacketHandled(true);
        }
    }
}
