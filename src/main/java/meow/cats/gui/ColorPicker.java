//package meow.cats.gui;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.GuiGraphicsExtractor;
//import net.minecraft.client.gui.render.TextureSetup;
//import net.minecraft.client.renderer.RenderPipelines;
//import net.minecraft.util.Mth;
//import org.joml.Matrix3x2f;
//
//import java.awt.Color;
//import java.util.function.Consumer;
//
//public class ColorPicker {
//    private static final int PICKER_SIZE = 84;
//    private static Color clipboard;
//    private final String name;
//    private final Consumer<Color> onChange;
//    private Color color;
//    private float[] hsb;
//    private int x;
//    private int y;
//    private int width = 78;
//    private boolean draggingColor;
//    private boolean draggingHue;
//
//    public ColorPicker(Color color, String name, Consumer<Color> onChange) {
//        this.color = color;
//        this.name = name;
//        this.onChange = onChange;
//        updateHsb();
//    }
//
//
//    public int getLayoutHeight() {
//        return 134;
//    }
//
//    public void setColor(Color color) {
//        this.color = color;
//        updateHsb();
//        onChange.accept(color);
//    }
//
//    public void render(GuiGraphicsExtractor context, int x, int y, int mouseX, int mouseY) {
//        this.x = x;
//        this.y = y;
//        context.fill(x, y, x + width, y + getLayoutHeight(), 0x4F000000);
//        context.text(Minecraft.getInstance().font, name, x + 2, y+5, -1);
//        context.fill(x + width - 10, y + 5, x + width - 3, y + 12, color.getRGB());
//
//        int size = Math.min(PICKER_SIZE, width);
//        int pickerX = x + (width - size) / 2;
//        int squareY = y + 20;
//        int hueY = squareY + size + 4;
//        int buttonsY = hueY + 13;
//
//
//        int hueColor = Color.getHSBColor(hsb[0], 1.0f, 1.0f).getRGB();
//        gradient(context, pickerX, squareY, pickerX + size, squareY + size,
//                Color.WHITE.getRGB(), Color.BLACK.getRGB(), Color.BLACK.getRGB(), hueColor);
//
//        horizontalGradient(context, pickerX, squareY, pickerX + size, squareY + size, Color.WHITE, new Color(hueColor, true));
//        verticalGradient(context, pickerX, squareY, pickerX + size, squareY + size, new Color(0, 0, 0, 0), Color.BLACK);
//
//        for (int i = 0; i < 6; i++) {
//            int left = pickerX + i * size / 6;
//            int right = pickerX + (i + 1) * size / 6;
//            Color leftColor = Color.getHSBColor(i / 6.0f, 1.0f, 1.0f);
//            Color rightColor = Color.getHSBColor((i + 1) / 6.0f, 1.0f, 1.0f);
//            horizontalGradient(context, left, hueY, right, hueY + 8, leftColor, rightColor);
//        }
//
//
//        int markerX = pickerX + Math.round(hsb[1] * (size - 1));
//        int markerY = squareY + Math.round((1.0f - hsb[2]) * (size - 1));
//        context.fill(markerX - 2, markerY - 2, markerX + 3, markerY + 3, Color.BLACK.getRGB());
//        context.fill(markerX - 1, markerY - 1, markerX + 2, markerY + 2, Color.WHITE.getRGB());
//
//        int hueMarkerX = pickerX + Math.round(hsb[0] * (size - 1));
//        context.fill(hueMarkerX - 1, hueY - 1, hueMarkerX + 2, hueY + 9, Color.WHITE.getRGB());
//
//        int half = size / 2;
//        context.fill(pickerX, buttonsY, pickerX + half - 1, buttonsY + 14, 0x4F85C6FA);
//        context.fill(pickerX + half + 1, buttonsY, pickerX + size, buttonsY + 14, 0x4F85C6FA);
//        context.centeredText(Minecraft.getInstance().font, "Copy", pickerX + half/2, buttonsY + 3, -1);
//        context.centeredText(Minecraft.getInstance().font, "Paste", pickerX + (int)(half * 1.5), buttonsY + 3, -1);
//    }
//
//    public void mouseClicked(int mouseX, int mouseY) {
//
//        int size = Math.min(PICKER_SIZE, width);
//        int pickerX = x + (width - size) / 2;
//        int squareY = y + 20;
//        int hueY = squareY + size + 4;
//        int buttonsY = hueY + 13;
//        int half = size / 2;
//
//        if (inside(mouseX, mouseY, pickerX, squareY, size, size)) {
//            draggingColor = true;
//            updateColorFromMouse(mouseX, mouseY);
//        } else if (inside(mouseX, mouseY, pickerX, hueY, size, 8)) {
//            draggingHue = true;
//            updateHueFromMouse(mouseX);
//        } else if (inside(mouseX, mouseY, pickerX, buttonsY, half, 14)) {
//            clipboard = color;
//        } else if (inside(mouseX, mouseY, pickerX + half + 1, buttonsY, size - half - 1, 14) && clipboard != null) {
//            setColor(clipboard);
//        }
//    }
//
//
//    public void mouseDragged(int mouseX, int mouseY) {
//        if (draggingColor) updateColorFromMouse(mouseX, mouseY);
//        else if (draggingHue) updateHueFromMouse(mouseX);
//    }
//
//    public void mouseReleased() {
//        draggingColor = false;
//        draggingHue = false;
//    }
//
//    private void updateColorFromMouse(int mouseX, int mouseY) {
//        int size = Math.min(PICKER_SIZE, width);
//        int pickerX = x + (width - size) / 2;
//        int squareY = y + 20;
//        hsb[1] = (float) Mth.clamp(mouseX - pickerX, 0, size - 1) / Math.max(1, size - 1);
//        hsb[2] = 1.0f - (float) Mth.clamp(mouseY - squareY, 0, size - 1) / Math.max(1, size - 1);
//        applyHsb();
//    }
//
//    private void updateHueFromMouse(int mouseX) {
//        int size = Math.min(PICKER_SIZE, width);
//        int pickerX = x + (width - size) / 2;
//        hsb[0] = (float) Mth.clamp(mouseX - pickerX, 0, size - 1) / Math.max(1, size - 1);
//        applyHsb();
//    }
//
//    private void applyHsb() {
//        Color rgb = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
//        color = new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), color.getAlpha());
//        onChange.accept(color);
//    }
//
//    private void updateHsb() {
//        hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
//    }
//
//    private boolean inside(int mouseX, int mouseY, int left, int top, int width, int height) {
//        return mouseX >= left && mouseX < left + width && mouseY >= top && mouseY < top + height;
//    }
//    public static void horizontalGradient(GuiGraphicsExtractor context, float x1, float y1, float x2, float y2, Color left, Color right) {
//        int ix1 = Math.round(x1);
//        int iy1 = Math.round(y1);
//        int ix2 = Math.round(x2);
//        int iy2 = Math.round(y2);
//        gradient(context, ix1, iy1, ix2, iy2, left.getRGB(), left.getRGB(), right.getRGB(), right.getRGB());
//    }
//
//    public static void verticalGradient(GuiGraphicsExtractor context, float x1, float y1, float x2, float y2, Color top, Color bottom) {
//        int ix1 = Math.round(x1);
//        int iy1 = Math.round(y1);
//        int ix2 = Math.round(x2);
//        int iy2 = Math.round(y2);
//        gradient(context, ix1, iy1, ix2, iy2, top.getRGB(), bottom.getRGB(), bottom.getRGB(), top.getRGB());
//    }
//
//    public static void gradient(
//            GuiGraphicsExtractor graphics,
//            int x1,
//            int y1,
//            int x2,
//            int y2,
//            int topLeft,
//            int bottomLeft,
//            int bottomRight,
//            int topRight
//    ) {
//        graphics.guiRenderState.addGuiElement(
//                new RectRenderState(
//                        RenderPipelines.GUI,
//                        TextureSetup.noTexture(),
//                        new Matrix3x2f(graphics.pose()),
//                        x1,
//                        y1,
//                        x2,
//                        y2,
//                        topLeft,
//                        bottomLeft,
//                        bottomRight,
//                        topRight,
//                        graphics.scissorStack.peek()
//                )
//        );
//    }
//}
