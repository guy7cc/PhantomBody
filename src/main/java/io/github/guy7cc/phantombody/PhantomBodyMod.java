package io.github.guy7cc.phantombody;

import com.mojang.logging.LogUtils;
import io.github.guy7cc.phantombody.block.PBBlocks;
import io.github.guy7cc.phantombody.block.entity.PBBlockEntities;
import io.github.guy7cc.phantombody.item.PBItems;
import io.github.guy7cc.phantombody.network.PBPacketManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(PhantomBodyMod.ID)
public class PhantomBodyMod {
    public static final String ID = "phantombody";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public PhantomBodyMod(){
        PBPacketManager.registerMessages("main");

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        PBItems.ITEMS.register(bus);
        PBBlocks.BLOCKS.register(bus);
        PBBlockEntities.BLOCK_ENTITIES.register(bus);
        //PBItems.registerProperties();
    }
}
