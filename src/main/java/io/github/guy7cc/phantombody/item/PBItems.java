package io.github.guy7cc.phantombody.item;

import io.github.guy7cc.phantombody.PhantomBodyMod;
import io.github.guy7cc.phantombody.block.PBBlocks;
import io.github.guy7cc.phantombody.save.cap.PBPlayerPropertyManager;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class PBItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PhantomBodyMod.ID);

    public static final RegistryObject<Item> PHANTOM_ORB = ITEMS.register("phantom_orb", () -> new PhantomOrbItem(new Item.Properties()));
    public static final RegistryObject<Item> PHANTOM_WAND = ITEMS.register("phantom_wand", () -> new PhantomWandItem(new Item.Properties()));
    //public static final RegistryObject<BlockItem> PHANTOM_BLOCK = ITEMS.register("phantom_block", () -> new BlockItem(PBBlocks.PHANTOM_BLOCK.get(), new Item.Properties()));

    public static void registerProperties(){
        ItemProperties.register(PHANTOM_ORB.get(), new ResourceLocation(PhantomBodyMod.ID, "phantom"), (pStack, pLevel, pEntity, pSeed) -> {
            if(pEntity instanceof Player){
                return PBPlayerPropertyManager.getClient(pEntity.getUUID()).getPhantom() ? 1f : 0f;
            }
            return 0f;
        });
    }
}
