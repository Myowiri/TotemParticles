package meow.cats.gui;

import meow.cats.CatGui;
import meow.cats.Config;
import meow.cats.items.BooleanButton;
import meow.cats.items.ColorPicker;
import meow.cats.items.Slider;

public class Gui extends CatGui {

    public static Gui Instance;

    public Gui(){
        setInstance();
    }

    static{
        Instance = new Gui();
    }

    private void setInstance() {
        Instance = this;
    }

    @Override
    public void addButtons(){
        buttons.add(new BooleanButton(Config.INSTANCE.modenable, "Mod", value -> {Config.INSTANCE.modenable = value;Config.save();}));
        buttons.add(new BooleanButton(Config.INSTANCE.NoPopAnimation, "No Totem Animation", value -> {Config.INSTANCE.NoPopAnimation = value;Config.save();}));
        buttons.add(new Slider("Power", Config.INSTANCE.power, 0, 10f, value -> {Config.INSTANCE.power = value;Config.save();}));
        buttons.add(new Slider("scale", Config.INSTANCE.scale, 0, 10f, value -> {Config.INSTANCE.scale = value;Config.save();}));
        buttons.add(new ColorPicker(Config.INSTANCE.color1, "Color 1", v -> {Config.INSTANCE.color1 = v;Config.save();}).doNotSpace().setWidth(78));
        buttons.add(new ColorPicker(Config.INSTANCE.color2, "Color 2", v -> {Config.INSTANCE.color2 = v;Config.save();}).setDx(82).setWidth(78));
    }
}
