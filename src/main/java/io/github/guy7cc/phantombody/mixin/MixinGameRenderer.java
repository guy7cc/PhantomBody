package io.github.guy7cc.phantombody.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.guy7cc.phantombody.client.PostEffectManager;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Shadow @Final
    private ResourceManager resourceManager;

    @Inject(method = "renderLevel", at = @At("TAIL"))
    private void onRenderLevel(float pPartialTicks, long pFinishTimeNano, PoseStack pMatrixStack, CallbackInfo ci){
        PostEffectManager.initializeIfNecessary(resourceManager);
        PostEffectManager.applyToLevel(pPartialTicks);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci){
        PostEffectManager.applyToScreen(pPartialTicks);
    }
}
