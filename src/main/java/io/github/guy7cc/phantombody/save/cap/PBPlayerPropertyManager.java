package io.github.guy7cc.phantombody.save.cap;

import io.github.guy7cc.phantombody.client.PostEffectManager;
import io.github.guy7cc.phantombody.network.ClientboundSetPBPlayerPropertyPacket;
import io.github.guy7cc.phantombody.network.PBPacketManager;
import io.github.guy7cc.phantombody.network.ServerboundSetPBPlayerPropertyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PBPlayerPropertyManager {
    private static final PBPlayerProperty DUMMY = new PBPlayerProperty();

    // Player UUID to property for client-side
    private static final Map<UUID, PBPlayerProperty> map = new HashMap<>();

    public static PBPlayerProperty get(ServerPlayer player){
        LazyOptional<PBPlayerProperty> optional = player.getCapability(PBPlayerPropertyProvider.PB_PLAYER_PROPERTY_CAPABILITY);
        return optional.isPresent() ? optional.orElse(DUMMY) : null;
    }

    public static void syncToClient(ServerPlayer player){
        PBPlayerProperty property = get(player);
        if(property != null)
            PBPacketManager.send(PacketDistributor.PLAYER.with(() -> player), new ClientboundSetPBPlayerPropertyPacket(player, property));
    }

    public static void syncToOthers(ServerPlayer player){
        PBPlayerProperty property = get(player);
        if(property != null){
            for(ServerPlayer p : player.getServer().getPlayerList().getPlayers()){
                if(p != player){
                    PBPacketManager.send(PacketDistributor.PLAYER.with(() -> p), new ClientboundSetPBPlayerPropertyPacket(player, property));
                }
            }
        }
    }

    public static PBPlayerProperty getClient(){
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.player != null ? getClient(minecraft.player.getUUID()) : DUMMY;
    }

    public static PBPlayerProperty getClient(UUID playerUUID){
        return map.containsKey(playerUUID) ? map.get(playerUUID) : DUMMY;
    }

    public static void putClient(UUID playerUUID, PBPlayerProperty property){
        map.put(playerUUID, property);
        PostEffectManager.changeStateIfLocal(property.getPhantom(), playerUUID);
    }

    public static void syncToServer(UUID playerUUID){
        PBPacketManager.sendToServer(new ServerboundSetPBPlayerPropertyPacket(getClient(playerUUID)));
    }
}
