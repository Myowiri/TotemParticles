package meow.cats.gui;

import meow.cats.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;

import java.util.function.Consumer;

public class Button {
    private boolean state;
    private final String name;
    private final Consumer<Boolean> onChange;
    private int x;
    private int y;


    public Button(boolean state, String name, Consumer<Boolean> onChange) {
        this.state = state;
        this.name = name;
        this.onChange = onChange;
    }

    public void render(GuiGraphicsExtractor context, int x, int y) {
        int width = ConfigGui.INSTANCE.Width;
        int height = ConfigGui.INSTANCE.Height;



        context.fill(
                x, y, x + width, y + height,
                state ? 0x4F85C6FA : 0x4F000000
        );

        context.text(
                Minecraft.getInstance().font,
                name + ": " + (state ? "On" : "Off"),
                x + 10,
                y + (height - 9) / 2,
                -1
        );



        this.x = x;
        this.y = y;
    }

    public boolean mouseClicked(int mouseX, int mouseY) {
        int width = ConfigGui.INSTANCE.Width;
        int height = ConfigGui.INSTANCE.Height;

        boolean inside = mouseX >= x && mouseX < x + width
                && mouseY >= y && mouseY < y + height;

        if (!inside) {
            return false;
        }

        state = !state;
        onChange.accept(state);
        Config.save();
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1f));
        return true;
    }



    public boolean getState() {
        return state;
    }
}