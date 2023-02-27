package io.github.guy7cc.phantombody.block;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import io.github.guy7cc.phantombody.item.PBItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PBBlocks {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PhantomBodyMod.ID);

    //public static RegistryObject<PhantomBlock> PHANTOM_BLOCK = BLOCKS.register("phantom_block", () -> new PhantomBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
}
