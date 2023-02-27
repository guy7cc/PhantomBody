package io.github.guy7cc.phantombody.client;

import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.guy7cc.phantombody.PhantomBodyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import org.joml.Vector4f;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Function;

public class PostEffectManager {
    private static Minecraft minecraft;

    private static PostChain monochrome;
    private static PostChain bloom;

    private static boolean initialized = false;

    private static int tick = 20;
    private static State state = State.NORMAL;
    private static float monochromeLerp = 1f;
    private static float monochromeStrength = 1.2f;
    private static Vector4f bloomColor = new Vector4f(0f, 0f, 0.2f, 0.1f);
    private static float bloomDeltaTexel = 5f;
    private static float whiteMask = 0f;

    private static final Vector4f ZERO = new Vector4f(0f, 0f, 0f, 0f);

    public static void initializeIfNecessary(ResourceManager resourceManager){
        if(initialized) return;
        initialized = true;
        minecraft = Minecraft.getInstance();
        try{
            monochrome = new PostChain(minecraft.getTextureManager(), resourceManager, minecraft.getMainRenderTarget(), new ResourceLocation(PhantomBodyMod.ID, "shaders/post/monochrome.json"));
            monochrome.resize(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
            bloom = new PostChain(minecraft.getTextureManager(), resourceManager, minecraft.getMainRenderTarget(), new ResourceLocation(PhantomBodyMod.ID, "shaders/post/bloom.json"));
            bloom.resize(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
        } catch (IOException e) {
            PhantomBodyMod.LOGGER.error(e.getMessage());
            PhantomBodyMod.LOGGER.error(e.getStackTrace());
        }
    }

    public static void tick(){
        tick++;
    }

    public static void applyToLevel(float partialTicks){
        updateParameters(partialTicks);
        if(monochrome != null && monochromeLerp > 0f){
            monochrome.resize(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
            monochrome.process(partialTicks);
        }
        if(bloom != null && !bloomColor.equals(ZERO)){
            bloom.resize(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
            bloom.process(partialTicks);
        }
    }

    public static void applyToScreen(float partialTicks){
        if(whiteMask > 0f){
            int alpha = Mth.clamp((int)(255 * whiteMask), 0, 255);
            GuiComponent.fill(new PoseStack(), 0, 0, minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight(), (alpha << 24) + 0xFFFFFF);
        }
    }

    private static void updateParameters(float partialTicks){
        float t = tick + partialTicks;
        if(t < 5f){
            whiteMask = (10 - t) / 10f;
            t /= 5f;
            float e = (float)Math.pow(1 - t, 4);
            Vector4f blue = new Vector4f(0f, 0f, 0.2f, 0.1f);
            switch(state){
                case NORMAL:
                    monochromeLerp = e;
                    monochromeStrength = 1 + 0.2f * e;
                    bloomColor = blue.mul(e);
                    bloomDeltaTexel = 5 * e;
                    break;
                case PHANTOM:
                    monochromeLerp = 1 - e;
                    monochromeStrength = 1 + 0.2f * (1 - e);
                    bloomColor = blue.mul(1 - e);
                    bloomDeltaTexel = 5 * (1 - e);
                    break;
            }
        } else if(t < 10f){
            whiteMask = (10 - t) / 10f;
        } else {
            whiteMask = 0f;
            Vector4f blue = new Vector4f(0f, 0f, 0.2f, 0.1f);
            switch(state){
                case NORMAL:
                    monochromeLerp = 0f;
                    monochromeStrength = 1f;
                    bloomColor = new Vector4f(0f, 0f, 0f, 0f);
                    bloomDeltaTexel = 0f;
                    break;
                case PHANTOM:
                    monochromeLerp = 1;
                    monochromeStrength = 1.2f;
                    bloomColor = blue;
                    bloomDeltaTexel = 5f;
                    break;
            }
        }
    }

    public static void setUniforms(String effectName, Function<String, AbstractUniform> safeGetUniform){
        if("phantombody:monochrome".equals(effectName)){
            safeGetUniform.apply("Lerp").set(PostEffectManager.monochromeLerp);
            safeGetUniform.apply("Strength").set(PostEffectManager.monochromeStrength);
        } else if("phantombody:bloom_step".equals(effectName)){
            safeGetUniform.apply("Threshold").set(0.25f);
            safeGetUniform.apply("BloomColor").set(PostEffectManager.bloomColor);
        } else if("phantombody:bloom_blur".equals(effectName)){
            safeGetUniform.apply("DeltaTexel").set(PostEffectManager.bloomDeltaTexel);
        }
    }

    public static void changeState(boolean phantom){
        state = phantom ? State.PHANTOM : State.NORMAL;
        tick = 0;
    }

    public static void changeStateIfLocal(boolean phantom, UUID playerUUID){
        Minecraft minecraft = Minecraft.getInstance();
        if(playerUUID.equals(minecraft.player.getUUID())){
            changeState(phantom);
        }
    }

    private enum State{
        NORMAL,
        PHANTOM
    }
}
