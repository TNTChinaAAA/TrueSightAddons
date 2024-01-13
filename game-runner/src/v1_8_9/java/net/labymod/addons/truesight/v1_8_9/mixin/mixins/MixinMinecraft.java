package net.labymod.addons.truesight.v1_8_9.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.labymod.addons.truesight.v1_8_9.TNTChina;
import net.labymod.addons.truesight.v1_8_9.key.KeyLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public abstract class MixinMinecraft {

    @Inject(method = "<init>", at = {@At("RETURN")})
    public void init(CallbackInfo callbackInfo) {
        TNTChina.INSTANCE = new TNTChina();
    }

    @Inject(method = "dispatchKeypresses", at = {@At("HEAD")})
    private void invokeKey(CallbackInfo callbackInfo) {
        KeyLoader.onKeyInput();
    }

    @Inject(method = "startGame", at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;", shift = At.Shift.AFTER)})
    private void startGame(CallbackInfo callbackInfo) {
        new KeyLoader();
    }
}
