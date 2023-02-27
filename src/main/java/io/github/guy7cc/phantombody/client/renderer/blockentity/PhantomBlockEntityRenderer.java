package io.github.guy7cc.phantombody.client.renderer.blockentity;
/*
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.guy7cc.phantombody.block.entity.PhantomBlockEntity;
import io.github.guy7cc.phantombody.save.cap.PBPlayerPropertyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

public class PhantomBlockEntityRenderer implements BlockEntityRenderer<PhantomBlockEntity> {
    private BlockRenderDispatcher dispatcher;

    public PhantomBlockEntityRenderer(BlockRenderDispatcher dispatcher){
        this.dispatcher = dispatcher;
    }

    @Override
    public void render(PhantomBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(PBPlayerPropertyManager.getClient().getPhantom() && pBlockEntity.getRenderedBlock() != null)
            dispatcher.renderSingleBlock(pBlockEntity.getRenderedBlock().defaultBlockState(), pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
    }
}


 */