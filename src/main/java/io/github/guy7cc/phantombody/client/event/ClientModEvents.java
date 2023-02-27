package io.github.guy7cc.phantombody.client.event;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import io.github.guy7cc.phantombody.item.PBItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PhantomBodyMod.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event){
        PBItems.registerProperties();
    }

    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        //event.registerBlockEntityRenderer(PBBlockEntities.PHANTOM_BLOCK_ENTITY.get(), ctx -> new PhantomBlockEntityRenderer(ctx.getBlockRenderDispatcher()));
    }
}
