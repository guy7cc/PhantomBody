package io.github.guy7cc.phantombody.save.cap;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class PBPlayerPropertyProvider extends AbstractCapabilityProvider<PBPlayerProperty> {
    public static final ResourceLocation PB_PLAYER_PROPERTY_LOCATION = new ResourceLocation(PhantomBodyMod.ID, "pb_player_property");
    public static final Capability<PBPlayerProperty> PB_PLAYER_PROPERTY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public PBPlayerPropertyProvider() {
        super(PBPlayerProperty::new);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PB_PLAYER_PROPERTY_CAPABILITY){
            return holder.cast();
        }
        return LazyOptional.empty();
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(PBPlayerProperty.class);
    }

    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof ServerPlayer){
            event.addCapability(PB_PLAYER_PROPERTY_LOCATION, new PBPlayerPropertyProvider());
        }
    }
}
