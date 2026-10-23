package meow.cats.gui;

import meow.cats.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ConfigGuiTotemParticles extends Screen {


    private final List<Button> buttons = new ArrayList<>();
    private final List<Slider> sliders = new ArrayList<>();
    private final List<ColorPicker> colorPickers = new ArrayList<>();


    public static ConfigGuiTotemParticles INSTANCE;
    public int Width = 160;
    public int Height = 20;


    protected ConfigGuiTotemParticles() {
        super(Component.literal("SmoothCamera"));
        setInstance();
        addButtons();
    }

    static {
        INSTANCE = new ConfigGuiTotemParticles();
    }

    private void setInstance() {
        INSTANCE = this;
    }


    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        for (Button button : buttons) {
            button.mouseClicked((int)click.x(), (int)click.y());
        }
        for (Slider slider : sliders) {
            slider.mouseClicked((int)click.x(), (int)click.y());
        }

        for (ColorPicker colorPicker : colorPickers) {
            colorPicker.mouseClicked((int)click.x(), (int)click.y());
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent click, double deltaX, double deltaY) {

        for (Slider slider : sliders) {
            slider.mouseDragged((int)click.x(), (int)click.y());
        }

        for (ColorPicker colorPicker : colorPickers) {
            colorPicker.mouseDragged((int)click.x(), (int)click.y());
        }

        return super.mouseDragged(click, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent click) {
        for (Slider slider : sliders) {
            slider.mouseReleased();
        }

        for (ColorPicker colorPicker : colorPickers){
            colorPicker.mouseReleased();
        }

        Config.save();

        return super.mouseReleased(click);
    }

    public void addButtons(){

        buttons.add(new Button(Config.INSTANCE.modenable, "Mod", value -> Config.INSTANCE.modenable = value));

        buttons.add(new Button(Config.INSTANCE.NoPopAnimation, "No Totem Animation", value -> Config.INSTANCE.NoPopAnimation = value));

        sliders.add(new Slider("Power", Config.INSTANCE.power, 0, 10f, value -> {Config.INSTANCE.power = value;Config.save();}));

        sliders.add(new Slider("scale", Config.INSTANCE.scale, 0, 10f, value -> {Config.INSTANCE.scale = value;Config.save();}));

        colorPickers.add(new ColorPicker(Config.INSTANCE.color1, "Color 1", v -> Config.INSTANCE.color1 = v));

        colorPickers.add(new ColorPicker(Config.INSTANCE.color2, "Color 2", v -> Config.INSTANCE.color2 = v));

    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        int buttonWidth = ConfigGuiTotemParticles.INSTANCE.Width;
        int buttonHeight = ConfigGuiTotemParticles.INSTANCE.Height;
        int spacing = 5;

        int x = (screenWidth - buttonWidth) / 2;
        int totalHeight = buttonHeight * 2 + spacing;
        int y = (screenHeight - totalHeight) / 3 - 50;

        for (Button button : buttons) {
            button.render(context, x, y);
            y += ConfigGuiTotemParticles.INSTANCE.Height + 5;
        }

        for (Slider slider : sliders) {
            slider.render(context, x, y);
            y += ConfigGuiTotemParticles.INSTANCE.Height + 5;
        }

        for (ColorPicker colorPicker : colorPickers) {
            colorPicker.render(context, x, y, mouseX, mouseY);
            x += 82;
        }

    }

}