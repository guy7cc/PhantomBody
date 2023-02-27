package io.github.guy7cc.phantombody.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;

public class ClientExecutionUtil {
    public static Runnable playPhantomOrbSound(boolean phantom){
        return () -> {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(SoundEvents.ENCHANTMENT_TABLE_USE, 1f, phantom ? 0.5f : 0.8409f));
        };
    }
}
