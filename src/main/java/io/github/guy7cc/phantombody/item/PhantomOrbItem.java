package io.github.guy7cc.phantombody.item;

import io.github.guy7cc.phantombody.client.ClientExecutionUtil;
import io.github.guy7cc.phantombody.client.PostEffectManager;
import io.github.guy7cc.phantombody.save.cap.PBPlayerProperty;
import io.github.guy7cc.phantombody.save.cap.PBPlayerPropertyManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class PhantomOrbItem extends Item {
    public PhantomOrbItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if(pLevel.isClientSide){
            PBPlayerProperty property = PBPlayerPropertyManager.getClient(pPlayer.getUUID());
            property.invertPhantom();
            PostEffectManager.changeState(property.getPhantom());
            PBPlayerPropertyManager.syncToServer(pPlayer.getUUID());
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientExecutionUtil.playPhantomOrbSound(property.getPhantom()));
        }
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide);
    }
}
