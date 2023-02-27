package io.github.guy7cc.phantombody.network;

import io.github.guy7cc.phantombody.save.cap.PBPlayerProperty;
import io.github.guy7cc.phantombody.save.cap.PBPlayerPropertyManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ClientboundSetPBPlayerPropertyPacket {
    private UUID playerUUID;
    private PBPlayerProperty property;

    public ClientboundSetPBPlayerPropertyPacket(ServerPlayer player, PBPlayerProperty property){
        playerUUID = player.getUUID();
        this.property = property;
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(playerUUID);
        buf.writeNbt(property.serializeNBT());
    }

    public ClientboundSetPBPlayerPropertyPacket(FriendlyByteBuf buf){
        playerUUID = buf.readUUID();
        property = new PBPlayerProperty();
        property.deserializeNBT(buf.readNbt());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                PBPlayerPropertyManager.putClient(playerUUID, property);
                ctx.get().setPacketHandled(true);
            });
        });
    }
}
