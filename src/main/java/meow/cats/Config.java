package meow.cats;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Color.class, new com.google.gson.JsonSerializer<Color>() {
                @Override
                public com.google.gson.JsonElement serialize(
                        Color color,
                        java.lang.reflect.Type type,
                        com.google.gson.JsonSerializationContext context) {
                    return new com.google.gson.JsonPrimitive(color.getRGB());
                }
            })
            .registerTypeAdapter(Color.class, new com.google.gson.JsonDeserializer<Color>() {
                @Override
                public Color deserialize(
                        com.google.gson.JsonElement json,
                        java.lang.reflect.Type type,
                        com.google.gson.JsonDeserializationContext context) {
                    return new Color(json.getAsInt(), true);
                }
            })
            .create();

    private static final Path PATH =
            FabricLoader.getInstance().getConfigDir().resolve("TotemParticles.json");

    public boolean modenable = true;
    public boolean NoPopAnimation = true;
    public Color color1 = new Color(-1);
    public Color color2 = new Color(0xEBA4FF);
    public float scale = 1f;
    public float power = 1f;


    public static Config INSTANCE = new Config();

    public static void load() {
        try {
            File file = PATH.toFile();

            if (file.exists()) {
                INSTANCE = GSON.fromJson(new FileReader(file), Config.class);
                if (INSTANCE == null) {
                    INSTANCE = new Config();
                }
            } else {
                INSTANCE = new Config();
                save();
                return;
            }

            INSTANCE.validate();
            save();
        } catch (Exception e) {
            e.printStackTrace();
            INSTANCE = new Config();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(PATH.getParent());
            INSTANCE.validate();

            try (Writer writer = Files.newBufferedWriter(PATH)) {
                GSON.toJson(INSTANCE, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void validate() {
        scale = clamp(scale, .0f, 10f);
        power = clamp(power, .0f, 10f);
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}