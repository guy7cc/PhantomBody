package io.github.guy7cc.phantombody.save.cap;

import io.github.guy7cc.phantombody.network.ClientboundSetPBLevelChunkPropertyPacket;
import io.github.guy7cc.phantombody.network.ClientboundSetPhantomBlockStatePacket;
import io.github.guy7cc.phantombody.network.PBPacketManager;
import io.github.guy7cc.phantombody.network.ServerboundSetPhantomBlockStatePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.Map;

public class PBLevelChunkPropertyManager {

    // ChunkPos to PBLevelChunkProperty for client
    private static Map<ChunkPos, PBLevelChunkProperty> map = new HashMap<>();

    public static PBLevelChunkProperty get(LevelChunk chunk){
        LazyOptional<PBLevelChunkProperty> optional = chunk.getCapability(PBLevelChunkPropertyProvider.PB_LEVEL_CHUNK_PROPERTY_CAPABILITY);
        return optional.isPresent() ? optional.orElse(PBLevelChunkProperty.DUMMY) : null;
    }

    public static PBLevelChunkProperty getClient(BlockPos pos){
        ChunkPos chunkPos = new ChunkPos(pos);
        return map.getOrDefault(chunkPos, PBLevelChunkProperty.DUMMY);
    }

    public static void updateClient(ChunkPos chunkPos, PBLevelChunkProperty property){
        map.put(chunkPos, property);
    }

    public static void updateClient(BlockPos pos, PBLevelChunkProperty.PhantomBlockState state){
        ChunkPos chunkPos = new ChunkPos(pos);
        if(!map.containsKey(chunkPos)) return;
        PBLevelChunkProperty property = map.get(chunkPos);
        property.setPhantomBlockState(pos, state);
    }

    public static void forgetClient(ChunkPos chunkPos){
        map.remove(chunkPos);
    }

    public static void syncToServer(BlockPos pos, PBLevelChunkProperty.PhantomBlockState state){
        PBPacketManager.sendToServer(new ServerboundSetPhantomBlockStatePacket(pos, state));
    }

    public static void syncToClient(ServerPlayer player, LevelChunk chunk){
        PBLevelChunkProperty property = get(chunk);
        if(property != null){
            PBPacketManager.sendToPlayer(player, new ClientboundSetPBLevelChunkPropertyPacket(chunk));
        }
    }

    public static void syncToClient(ServerPlayer player, BlockPos pos, PBLevelChunkProperty.PhantomBlockState state){
        PBPacketManager.sendToPlayer(player, new ClientboundSetPhantomBlockStatePacket(pos, state));
    }

    public static void syncToOtherClient(ServerPlayer player, BlockPos pos, PBLevelChunkProperty.PhantomBlockState state){
        PlayerList playerList = player.getServer().getPlayerList();
        for(ServerPlayer p : playerList.getPlayers()){
            if(player != p){
                syncToClient(p, pos, state);
            }
        }
    }
}
