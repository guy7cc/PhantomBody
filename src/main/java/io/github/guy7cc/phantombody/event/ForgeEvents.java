package io.github.guy7cc.phantombody.event;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import io.github.guy7cc.phantombody.item.PBItems;
import io.github.guy7cc.phantombody.save.cap.*;
import net.minecraft.client.telemetry.events.WorldLoadEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.ChunkWatchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PhantomBodyMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event){
        if(event.getEntity() instanceof ServerPlayer player){
            PBPlayerPropertyManager.syncToClient(player);
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesToEntity(AttachCapabilitiesEvent<Entity> event){
        PBPlayerPropertyProvider.onAttachCapabilities(event);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesToLevelChunk(AttachCapabilitiesEvent<LevelChunk> event){
        PBLevelChunkPropertyProvider.onAttachCapabilities(event);
    }

    @SubscribeEvent
    public static void onWatchChunk(ChunkWatchEvent.Watch event){
        ServerPlayer player = event.getPlayer();
        LevelChunk chunk = event.getChunk();
        PBLevelChunkPropertyManager.syncToClient(player, chunk);
    }

    @SubscribeEvent
    public static void onUnWatchChunk(ChunkWatchEvent.UnWatch event){

    }
}
