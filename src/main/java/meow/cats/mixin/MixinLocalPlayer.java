package meow.cats.mixin;

import meow.cats.Config;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer {
    @Inject(
            method = "displayItemActivation",
            at = @At("HEAD"),
            cancellable = true
    ) private void nopopanimation(ItemStack itemStack, CallbackInfo ci){
        if (Config.INSTANCE.NoPopAnimation)
            ci.cancel();
    }

}