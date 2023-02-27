package io.github.guy7cc.phantombody.network;

import io.github.guy7cc.phantombody.save.cap.PBLevelChunkProperty;
import io.github.guy7cc.phantombody.save.cap.PBLevelChunkPropertyManager;
import io.github.guy7cc.phantombody.save.cap.PBLevelChunkPropertyProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundSetPhantomBlockStatePacket {
    private BlockPos pos;
    private PBLevelChunkProperty.PhantomBlockState state;

    public ServerboundSetPhantomBlockStatePacket(BlockPos pos, PBLevelChunkProperty.PhantomBlockState state){
        this.pos = pos;
        this.state = state;
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(pos);
        buf.writeInt(state.bits);
    }

    public ServerboundSetPhantomBlockStatePacket(FriendlyByteBuf buf){
        pos = buf.readBlockPos();
        state = PBLevelChunkProperty.PhantomBlockState.fromBits(buf.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            ServerLevel level = player.getLevel();
            LevelChunk chunk = level.getChunkAt(pos);
            PBLevelChunkProperty property = PBLevelChunkPropertyManager.get(chunk);
            if(property != null){
                property.setPhantomBlockState(pos, state);
                PBLevelChunkPropertyManager.syncToOtherClient(player, pos, state);
                ctx.get().setPacketHandled(true);
            }
        });
    }
}
