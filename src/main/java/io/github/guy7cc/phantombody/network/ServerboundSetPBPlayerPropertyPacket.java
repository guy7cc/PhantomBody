package io.github.guy7cc.phantombody.network;

import io.github.guy7cc.phantombody.save.cap.PBPlayerProperty;
import io.github.guy7cc.phantombody.save.cap.PBPlayerPropertyManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundSetPBPlayerPropertyPacket {
    private PBPlayerProperty property;

    public ServerboundSetPBPlayerPropertyPacket(PBPlayerProperty property){
        this.property = property;
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(property.serializeNBT());
    }

    public ServerboundSetPBPlayerPropertyPacket(FriendlyByteBuf buf){
        property = new PBPlayerProperty();
        property.deserializeNBT(buf.readNbt());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            PBPlayerProperty property = PBPlayerPropertyManager.get(player);
            if(property != null){
                property.copy(this.property);
                PBPlayerPropertyManager.syncToOthers(player);
                ctx.get().setPacketHandled(true);
            }
        });
    }
}
