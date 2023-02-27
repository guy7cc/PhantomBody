package io.github.guy7cc.phantombody.client.event;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import io.github.guy7cc.phantombody.client.PostEffectManager;
import io.github.guy7cc.phantombody.save.cap.PBPlayerPropertyManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PhantomBodyMod.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        PostEffectManager.tick();
    }

    @SubscribeEvent
    public static void onRenderBlockScreenEffect(RenderBlockScreenEffectEvent event){
        if(PBPlayerPropertyManager.getClient().getPhantom()) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onUnloadChunk(ChunkEvent.Unload event){

    }
}
