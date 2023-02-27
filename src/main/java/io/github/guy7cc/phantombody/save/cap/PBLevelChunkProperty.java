package io.github.guy7cc.phantombody.save.cap;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BitStorage;
import net.minecraft.util.SimpleBitStorage;
import net.minecraft.util.ZeroBitStorage;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.INBTSerializable;

public class PBLevelChunkProperty implements INBTSerializable<CompoundTag> {
    public static final PBLevelChunkProperty DUMMY = new PBLevelChunkProperty(new ZeroBitStorage(8), 0, 1);

    private BitStorage phantomBlockStates;
    private int height;
    private int minHeight;

    private PBLevelChunkProperty(BitStorage phantomBlockStates, int minHeight, int height){
        this.phantomBlockStates = phantomBlockStates;
        this.minHeight = minHeight;
        this.height = height;
    }

    public PBLevelChunkProperty(LevelChunk chunk){
        if(chunk != null){
            height = chunk.getLevel().getHeight();
            minHeight = chunk.getMinBuildHeight();
            phantomBlockStates = new SimpleBitStorage(2, 256 * height);
        }
    }

    public PhantomBlockState getPhantomBlockState(BlockPos pos){
        int y = pos.getY() - minHeight;
        if(y < 0 || height <= y) return PhantomBlockState.NORMAL;
        int x = pos.getX() & 15;
        int z = pos.getZ() & 15;
        return PhantomBlockState.fromBits(phantomBlockStates.get(y * 256 + z * 16 + x));
    }

    public void setPhantomBlockState(BlockPos pos, PhantomBlockState state){
        int y = pos.getY() - minHeight;
        if(y < 0 || height <= y) return;
        int x = pos.getX() & 15;
        int z = pos.getZ() & 15;
        phantomBlockStates.set(y * 256 + z * 16 + x, state.bits);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLongArray("PhantomBlockStates", phantomBlockStates.getRaw());
        tag.putInt("MinHeight", minHeight);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        long[] array = nbt.getLongArray("PhantomBlockStates");
        phantomBlockStates = new SimpleBitStorage(2, array.length * 32, array);
        height = array.length / 8;
        minHeight = nbt.getInt("MinHeight");
    }

    public enum PhantomBlockState {
        NORMAL(0),
        PHANTOM(1),
        BOTH(2);

        public final int bits;

        PhantomBlockState(int bits){
            this.bits = bits;
        }

        public static PhantomBlockState fromBits(int bits){
            switch(bits){
                case 0:
                    return NORMAL;
                case 1:
                    return PHANTOM;
                case 2:
                    return BOTH;
                default:
                    PhantomBodyMod.LOGGER.error(bits + " is an invalid value for PhantomBlockState!");
                    return NORMAL;
            }
        }

        public boolean real(){
            return ((bits + 1) & 1) > 0;
        }

        public boolean phantom(){
            return ((bits + 1) & 2) > 0;
        }
    }
}
