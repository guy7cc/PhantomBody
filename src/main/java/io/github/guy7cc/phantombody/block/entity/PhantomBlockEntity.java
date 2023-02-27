package io.github.guy7cc.phantombody.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
/*
public class PhantomBlockEntity extends BlockEntity {
    private Block renderedBlock;

    public PhantomBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(PBBlockEntities.PHANTOM_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public Block getRenderedBlock(){
        return renderedBlock;
    }

    public void setRenderedBlock(Block block){
        renderedBlock = block;
        setChanged();
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(!"null".equals(pTag.getString("Block"))){
            ResourceLocation location = new ResourceLocation(pTag.getString("Block"));
            renderedBlock = ForgeRegistries.BLOCKS.getValue(location);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("Block", renderedBlock == null ? "null" : ForgeRegistries.BLOCKS.getKey(renderedBlock).toString());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}

 */
