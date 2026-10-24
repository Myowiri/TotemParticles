//package meow.cats.gui;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.GuiGraphicsExtractor;
//
//import java.util.Locale;
//import java.util.function.Consumer;
//
//public class Slider {
//    private final String name;
//    private final float min;
//    private final float max;
//    private final Consumer<Float> onChange;
//
//    private float value;
//    private int x;
//    private int y;
//    private boolean dragging;
//
//    public Slider(String name, float value, float min, float max,
//                  Consumer<Float> onChange) {
//        this.name = name;
//        this.min = min;
//        this.max = max;
//        this.value = clamp(value);
//        this.onChange = onChange;
//    }
//
//    public void render(GuiGraphicsExtractor context, int x, int y) {
//        this.x = x;
//        this.y = y;
//
//        int width = ConfigGui.INSTANCE.Width;
//        int height = ConfigGui.INSTANCE.Height;
//
//        context.fill(x, y, x + width, y + height, 0x4F000000);
//
//        String label = String.format(
//                Locale.ROOT, "%s: %.1f", name, value
//        );
//
//        context.text(
//                Minecraft.getInstance().font,
//                label,
//                x + 6,
//                y + 2,
//                -1
//        );
//
//        int barX = x + 6;
//        int barY = y + height - 5;
//        int barWidth = width - 12;
//        float range = max - min;
//        float progress = range == 0.0f
//                ? 0.0f
//                : (value - min) / range;
//
//        progress = Math.max(0.0f, Math.min(1.0f, progress));
//
//        int filledWidth = Math.round(barWidth * progress);
//
//        context.fill(barX, barY, barX + barWidth, barY + 2, 0xFF555555);
//        context.fill(barX, barY, barX + filledWidth, barY + 2, 0xFF85C6FA);
//    }
//
//    public boolean mouseClicked(int mouseX, int mouseY) {
//        int width = ConfigGui.INSTANCE.Width;
//        int height = ConfigGui.INSTANCE.Height;
//
//        if (mouseX < x || mouseX >= x + width
//                || mouseY < y || mouseY >= y + height) {
//            return false;
//        }
//
//        dragging = true;
//        setFromMouse(mouseX);
//        return true;
//    }
//
//    public void mouseDragged(int mouseX, int mouseY) {
//        if (dragging) {
//            setFromMouse(mouseX);
//        }
//    }
//
//    public void mouseReleased() {
//        dragging = false;
//    }
//
//    private void setFromMouse(int mouseX) {
//        int barX = x + 6;
//        int barWidth = ConfigGui.INSTANCE.Width - 12;
//
//        float progress = (float) (mouseX - barX) / barWidth;
//        progress = Math.max(0.0f, Math.min(1.0f, progress));
//
//        value = min + progress * (max - min);
//        value = clamp(value);
//        onChange.accept(value);
//    }
//
//    private float clamp(float value) {
//        return Math.max(min, Math.min(max, value));
//    }
//}