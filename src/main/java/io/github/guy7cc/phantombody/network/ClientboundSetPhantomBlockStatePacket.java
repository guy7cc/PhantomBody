package io.github.guy7cc.phantombody.network;

import io.github.guy7cc.phantombody.save.cap.PBLevelChunkProperty;
import io.github.guy7cc.phantombody.save.cap.PBLevelChunkPropertyManager;
import io.github.guy7cc.phantombody.save.cap.PBLevelChunkPropertyProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundSetPhantomBlockStatePacket {
    private BlockPos pos;
    private PBLevelChunkProperty.PhantomBlockState state;

    public ClientboundSetPhantomBlockStatePacket(BlockPos pos, PBLevelChunkProperty.PhantomBlockState state){
        this.pos = pos;
        this.state = state;
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(pos);
        buf.writeInt(state.bits);
    }

    public ClientboundSetPhantomBlockStatePacket(FriendlyByteBuf buf){
        pos = buf.readBlockPos();
        state = PBLevelChunkProperty.PhantomBlockState.fromBits(buf.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                PBLevelChunkPropertyManager.updateClient(pos, state);
                ctx.get().setPacketHandled(true);
            });
        });
    }
}
