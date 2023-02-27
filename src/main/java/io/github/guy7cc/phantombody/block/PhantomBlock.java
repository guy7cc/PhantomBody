package io.github.guy7cc.phantombody.block;
/*
import io.github.guy7cc.phantombody.block.entity.PhantomBlockEntity;
import io.github.guy7cc.phantombody.save.cap.PBPlayerProperty;
import io.github.guy7cc.phantombody.save.cap.PBPlayerPropertyManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class PhantomBlock extends BaseEntityBlock {
    public PhantomBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState){
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }


    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        PBPlayerProperty property = DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> PBPlayerPropertyManager.getClient());
        if(property.getPhantom() && pReader.getBlockEntity(pPos) instanceof PhantomBlockEntity be && be.getRenderedBlock() != null){
            return be.getRenderedBlock().getShape(pState, pReader, pPos, pContext);
        } else if(property.getPhantom()) return Shapes.block();
        else return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        PBPlayerProperty property = DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> PBPlayerPropertyManager.getClient());
        if(property.getPhantom() && pReader.getBlockEntity(pPos) instanceof PhantomBlockEntity be && be.getRenderedBlock() != null){
            return be.getRenderedBlock().getCollisionShape(pState, pReader, pPos, pContext);
        } else if(property.getPhantom()) return Shapes.block();
        else return Shapes.empty();
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.getBlockEntity(pPos) instanceof PhantomBlockEntity be){
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if(!itemStack.isEmpty() && itemStack.getItem() instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof PhantomBlock)){
                if(!pLevel.isClientSide){
                    be.setRenderedBlock(blockItem.getBlock());
                    pLevel.sendBlockUpdated(pPos, pState, pState, 3);
                    itemStack.shrink(1);
                }
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        }
        return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PhantomBlockEntity(pPos, pState);
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }
}

 */
