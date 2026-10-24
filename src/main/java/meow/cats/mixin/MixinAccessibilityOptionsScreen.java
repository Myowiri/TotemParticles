package meow.cats.mixin;

import meow.cats.gui.Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;

@Mixin(AccessibilityOptionsScreen.class)
public abstract class MixinAccessibilityOptionsScreen extends OptionsSubScreen {

    public MixinAccessibilityOptionsScreen(Screen lastScreen, Options options, Component title) {
        super(lastScreen, options, title);
    }

    @Inject(method = "addOptions", at = @At("TAIL"))
    private void addConfigButton(CallbackInfo ci) {
        AccessibilityOptionsScreen screen =
                (AccessibilityOptionsScreen) (Object) this;

        list.addSmall(Collections.singletonList(Button.builder(
                Component.literal("Totem Particles"),
                button -> Minecraft.getInstance().setScreenAndShow(
                        Gui.Instance
                )
        ).build()));
    }
}
