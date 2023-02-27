package io.github.guy7cc.phantombody.item;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PhantomBodyMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PBCreativeModeTabs {
    public static CreativeModeTab PHANTOM_BODY_TAB;

    @SubscribeEvent
    public static void onRegisterCreativeModeTab(CreativeModeTabEvent.Register event){
        PHANTOM_BODY_TAB = event.registerCreativeModeTab(new ResourceLocation(PhantomBodyMod.ID, PhantomBodyMod.ID + "_tab"), builder -> {
            builder.title(Component.translatable("itemGroup." + PhantomBodyMod.ID)).icon(() -> new ItemStack(PBItems.PHANTOM_ORB.get())).title(Component.translatable("itemGroup." + PhantomBodyMod.ID));
        });
    }

    @SubscribeEvent
    public static void onAdd(CreativeModeTabEvent.BuildContents event){
        if(event.getTab() == PHANTOM_BODY_TAB){
            event.accept(PBItems.PHANTOM_ORB);
            event.accept(PBItems.PHANTOM_WAND);
            //event.accept(PBItems.PHANTOM_BLOCK);
        }
    }
}
