package io.github.guy7cc.phantombody.save.cap;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class PBLevelChunkPropertyProvider extends AbstractCapabilityProvider<PBLevelChunkProperty> {
    public static final ResourceLocation PB_LEVEL_CHUNK_PROPERTY_LOCATION = new ResourceLocation(PhantomBodyMod.ID, "pb_level_chunk_property");
    public static final Capability<PBLevelChunkProperty> PB_LEVEL_CHUNK_PROPERTY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public PBLevelChunkPropertyProvider(Supplier<PBLevelChunkProperty> defaultSupplier) {
        super(defaultSupplier);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PB_LEVEL_CHUNK_PROPERTY_CAPABILITY){
            return holder.cast();
        }
        return LazyOptional.empty();
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(PBLevelChunkProperty.class);
    }

    public static void onAttachCapabilities(AttachCapabilitiesEvent<LevelChunk> event){
        event.addCapability(PB_LEVEL_CHUNK_PROPERTY_LOCATION, new PBLevelChunkPropertyProvider(() -> new PBLevelChunkProperty(event.getObject())));
    }
}
