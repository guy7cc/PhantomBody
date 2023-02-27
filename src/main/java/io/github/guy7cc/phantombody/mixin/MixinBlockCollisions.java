package io.github.guy7cc.phantombody.mixin;

import com.google.common.collect.AbstractIterator;
import io.github.guy7cc.phantombody.save.cap.PBLevelChunkProperty;
import io.github.guy7cc.phantombody.save.cap.PBLevelChunkPropertyManager;
import io.github.guy7cc.phantombody.save.cap.PBPlayerProperty;
import io.github.guy7cc.phantombody.save.cap.PBPlayerPropertyManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockCollisions.class)
public abstract class MixinBlockCollisions extends AbstractIterator<VoxelShape> {
    @Shadow @Final
    private AABB box;
    @Shadow @Final
    private CollisionContext context;
    @Shadow @Final
    private Cursor3D cursor;
    @Shadow @Final
    private BlockPos.MutableBlockPos pos;
    @Shadow @Final
    private VoxelShape entityShape;
    @Shadow @Final
    private CollisionGetter collisionGetter;
    @Shadow @Final
    private boolean onlySuffocatingBlocks;

    /**
     * @author guy7cc
     * @reason In order to manage collision of phantom blocks
     */
    @Overwrite
    protected VoxelShape computeNext(){
        while(true) {
            if (this.cursor.advance()) {
                int i = this.cursor.nextX();
                int j = this.cursor.nextY();
                int k = this.cursor.nextZ();
                int l = this.cursor.getNextType();
                if (l == 3) {
                    continue;
                }

                BlockGetter blockgetter = getChunk(i, k);
                if (blockgetter == null) {
                    continue;
                }

                this.pos.set(i, j, k);
                BlockState blockstate = blockgetter.getBlockState(this.pos);

                // Overwritten here
                if(context instanceof EntityCollisionContext ctx && ctx.getEntity() instanceof Player player){
                    boolean phantom;
                    PBLevelChunkProperty chunkProperty;
                    if(player instanceof ServerPlayer serverPlayer){
                        PBPlayerProperty property = PBPlayerPropertyManager.get((ServerPlayer) player);
                        phantom = property != null && property.getPhantom();
                        ServerLevel level = serverPlayer.getLevel();
                        chunkProperty = PBLevelChunkPropertyManager.get(level.getChunkAt(pos));
                    } else {
                        PBPlayerProperty property = PBPlayerPropertyManager.getClient(player.getUUID());
                        phantom = property.getPhantom();
                        chunkProperty = PBLevelChunkPropertyManager.getClient(pos);
                    }
                    PBLevelChunkProperty.PhantomBlockState state = chunkProperty.getPhantomBlockState(pos);
                    if(phantom && !state.phantom() || !phantom && !state.real()) continue;
                }

                if (this.onlySuffocatingBlocks && !blockstate.isSuffocating(blockgetter, this.pos) || l == 1 && !blockstate.hasLargeCollisionShape() || l == 2 && !blockstate.is(Blocks.MOVING_PISTON)) {
                    continue;
                }

                VoxelShape voxelshape = blockstate.getCollisionShape(this.collisionGetter, this.pos, this.context);
                if (voxelshape == Shapes.block()) {
                    if (!this.box.intersects((double)i, (double)j, (double)k, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D)) {
                        continue;
                    }

                    return voxelshape.move((double)i, (double)j, (double)k);
                }

                VoxelShape voxelshape1 = voxelshape.move((double)i, (double)j, (double)k);
                if (!Shapes.joinIsNotEmpty(voxelshape1, this.entityShape, BooleanOp.AND)) {
                    continue;
                }

                return voxelshape1;
            }

            return this.endOfData();
        }
    }

    @Shadow
    protected abstract BlockGetter getChunk(int pX, int pZ);
}
