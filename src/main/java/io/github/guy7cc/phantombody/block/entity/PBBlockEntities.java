package io.github.guy7cc.phantombody.block.entity;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import io.github.guy7cc.phantombody.block.PBBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PBBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PhantomBodyMod.ID);

    //public static final RegistryObject<BlockEntityType<PhantomBlockEntity>> PHANTOM_BLOCK_ENTITY = BLOCK_ENTITIES.register("phantom_block_entity",
    //        () -> BlockEntityType.Builder.of(PhantomBlockEntity::new, PBBlocks.PHANTOM_BLOCK.get()).build(null));
}
