package io.github.guy7cc.phantombody.mixin;

import io.github.guy7cc.phantombody.save.cap.PBLevelChunkProperty;
import io.github.guy7cc.phantombody.save.cap.PBLevelChunkPropertyManager;
import io.github.guy7cc.phantombody.save.cap.PBPlayerProperty;
import io.github.guy7cc.phantombody.save.cap.PBPlayerPropertyManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
    private static void onShouldRenderFace(BlockState pState, BlockGetter pLevel, BlockPos pOffset, Direction pFace, BlockPos pPos, CallbackInfoReturnable<Boolean> cir){
        PBPlayerProperty property = PBPlayerPropertyManager.getClient();
        boolean phantom = property.getPhantom();
        PBLevelChunkProperty chunkProperty = PBLevelChunkPropertyManager.getClient(pPos);
        PBLevelChunkProperty.PhantomBlockState state = chunkProperty.getPhantomBlockState(pPos);
        if(phantom && state.real() || !phantom && state.phantom()) cir.setReturnValue(false);
    }
}
