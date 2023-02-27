package io.github.guy7cc.phantombody.save.cap;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PBPlayerProperty implements INBTSerializable<CompoundTag> {
    private boolean phantom = false;

    public boolean getPhantom() {
        return phantom;
    }

    public void setPhantom(boolean phantom) {
        this.phantom = phantom;
    }

    public void invertPhantom() {
        phantom = !phantom;
    }

    public void copy(PBPlayerProperty property){
        phantom = property.phantom;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("Phantom", phantom);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        phantom = nbt.getBoolean("Phantom");
    }
}
