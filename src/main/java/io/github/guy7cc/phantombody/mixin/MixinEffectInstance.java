package io.github.guy7cc.phantombody.mixin;

import com.mojang.blaze3d.shaders.AbstractUniform;
import io.github.guy7cc.phantombody.client.PostEffectManager;
import net.minecraft.client.renderer.EffectInstance;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectInstance.class)
public abstract class MixinEffectInstance {
    @Inject(method = "apply", at = @At("HEAD"))
    public void onApply(CallbackInfo ci){
        PostEffectManager.setUniforms(getName(), this::safeGetUniform);
    }

    @Shadow
    public abstract AbstractUniform safeGetUniform(String pName);

    @Shadow
    public abstract String getName();
}
