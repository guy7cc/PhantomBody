package io.github.guy7cc.phantombody.network;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class PBPacketManager {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void registerMessages(String name) {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(PhantomBodyMod.ID, name))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.registerMessage(id(), ServerboundSetPBPlayerPropertyPacket.class, ServerboundSetPBPlayerPropertyPacket::toBytes, ServerboundSetPBPlayerPropertyPacket::new, ServerboundSetPBPlayerPropertyPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        net.registerMessage(id(), ServerboundSetPhantomBlockStatePacket.class, ServerboundSetPhantomBlockStatePacket::toBytes, ServerboundSetPhantomBlockStatePacket::new, ServerboundSetPhantomBlockStatePacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        net.registerMessage(id(), ClientboundSetPBPlayerPropertyPacket.class, ClientboundSetPBPlayerPropertyPacket::toBytes, ClientboundSetPBPlayerPropertyPacket::new, ClientboundSetPBPlayerPropertyPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        net.registerMessage(id(), ClientboundSetPhantomBlockStatePacket.class, ClientboundSetPhantomBlockStatePacket::toBytes, ClientboundSetPhantomBlockStatePacket::new, ClientboundSetPhantomBlockStatePacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        net.registerMessage(id(), ClientboundSetPBLevelChunkPropertyPacket.class, ClientboundSetPBLevelChunkPropertyPacket::toBytes, ClientboundSetPBLevelChunkPropertyPacket::new, ClientboundSetPBLevelChunkPropertyPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message){
        INSTANCE.send(target, message);
    }

    public static <MSG> void sendToPlayer(ServerPlayer player, MSG message){
        send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }
}
