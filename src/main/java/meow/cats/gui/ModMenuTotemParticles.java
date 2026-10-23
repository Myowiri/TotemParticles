package meow.cats.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuTotemParticles implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return TotemParticlesMod -> {
            return ConfigGui.INSTANCE;
        };
    }
}
