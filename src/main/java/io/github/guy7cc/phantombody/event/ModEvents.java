package io.github.guy7cc.phantombody.event;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import io.github.guy7cc.phantombody.item.PBItems;
import io.github.guy7cc.phantombody.save.cap.PBLevelChunkPropertyProvider;
import io.github.guy7cc.phantombody.save.cap.PBPlayerPropertyProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PhantomBodyMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        PBPlayerPropertyProvider.onRegisterCapabilities(event);
        PBLevelChunkPropertyProvider.onRegisterCapabilities(event);
    }
}
