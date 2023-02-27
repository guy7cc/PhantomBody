package io.github.guy7cc.phantombody.item;

import io.github.guy7cc.phantombody.save.cap.PBLevelChunkProperty;
import io.github.guy7cc.phantombody.save.cap.PBLevelChunkPropertyManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class PhantomWandItem extends Item {
    public PhantomWandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide){
            BlockPos pos = pContext.getClickedPos();
            PBLevelChunkProperty property = PBLevelChunkPropertyManager.getClient(pos);
            int bits = (property.getPhantomBlockState(pos).bits + 1) % 3;
            PBLevelChunkProperty.PhantomBlockState state = PBLevelChunkProperty.PhantomBlockState.fromBits(bits);
            PBLevelChunkPropertyManager.updateClient(pos, state);
            PBLevelChunkPropertyManager.syncToServer(pos, state);
        }
        return InteractionResult.sidedSuccess(pContext.getLevel().isClientSide);
    }
}
