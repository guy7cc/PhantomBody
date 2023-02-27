package io.github.guy7cc.phantombody.network;

import io.github.guy7cc.phantombody.save.cap.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundSetPBLevelChunkPropertyPacket {
    private ChunkPos chunkPos;
    private PBLevelChunkProperty property;
    private boolean forget;

    public ClientboundSetPBLevelChunkPropertyPacket(LevelChunk chunk){
        this(chunk, false);
    }

    public ClientboundSetPBLevelChunkPropertyPacket(LevelChunk chunk, boolean forget){
        chunkPos = chunk.getPos();
        property = chunk.getCapability(PBLevelChunkPropertyProvider.PB_LEVEL_CHUNK_PROPERTY_CAPABILITY).orElse(PBLevelChunkProperty.DUMMY);
        this.forget = forget;
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBoolean(forget);
        buf.writeChunkPos(chunkPos);
        if(!forget) buf.writeNbt(property.serializeNBT());
    }

    public ClientboundSetPBLevelChunkPropertyPacket(FriendlyByteBuf buf){
        forget = buf.readBoolean();
        chunkPos = buf.readChunkPos();
        if(!forget){
            property = new PBLevelChunkProperty(null);
            property.deserializeNBT(buf.readNbt());
        }
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                if(!forget) PBLevelChunkPropertyManager.updateClient(chunkPos, property);
                else PBLevelChunkPropertyManager.forgetClient(chunkPos);
                ctx.get().setPacketHandled(true);
            });
        });
    }
}
