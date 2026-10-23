package meow.cats.mixin;

import meow.cats.Config;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TotemParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(TotemParticle.class)
public abstract class MixinTotemParticles extends SimpleAnimatedParticle {


    protected MixinTotemParticles(ClientLevel level, double x, double y, double z, SpriteSet sprites, float gravity) {
        super(level, x, y, z, sprites, gravity);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstruct(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, SpriteSet spriteSet, CallbackInfo ci) {
        if (Config.INSTANCE.modenable) {
            Color c = Config.INSTANCE.color1;
            Particle particle = (Particle) (Object) this;
            particle.scale(Config.INSTANCE.scale);
            particle.setPower(Config.INSTANCE.power);
            Color color = Config.INSTANCE.color1;
            float r = color.getRed() / 255.0F;
            float gCol = color.getGreen() / 255.0F;
            float b = color.getBlue() / 255.0F;

            Color color2 = Config.INSTANCE.color2;
            float r2 = color2.getRed() / 255.0F;
            float gCol2 = color2.getGreen() / 255.0F;
            float b2 = color2.getBlue() / 255.0F;

            if (this.random.nextInt(4) == 0) {
                this.setColor(r, gCol, b);
            } else {
                this.setColor(r2, gCol2, b2);
            }
        }
    }

}
