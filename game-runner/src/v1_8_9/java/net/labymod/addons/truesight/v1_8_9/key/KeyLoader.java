package net.labymod.addons.truesight.v1_8_9.key;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.labymod.addons.truesight.v1_8_9.TNTChina;
import org.apache.commons.lang3.ArrayUtils;

public class KeyLoader {
    public static KeyBinding esp;
    public static KeyBinding trueSight;

    public KeyLoader() {
        esp = new KeyBinding("ESP", TNTChina.getModule("ESP").getKey(), "TrueSight");
        trueSight = new KeyBinding("TrueSight", TNTChina.getModule("TrueSight").getKey(), "TrueSight");
        this.registerKeyBinding(esp);
        this.registerKeyBinding(trueSight);
    }

    public void registerKeyBinding(KeyBinding keyBinding) {
        Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.add(Minecraft.getMinecraft().gameSettings.keyBindings, keyBinding);
    }

    public static void onKeyInput() {
        if (TrueSightAddon.addon != null) {
            if (TrueSightAddon.addon.configuration().enabled().get().booleanValue()) {
                if (esp.isPressed()) {
                    TNTChina.getModule("ESP").toggle();
                }

                if (trueSight.isPressed()) {
                    TNTChina.getModule("TrueSight").toggle();
                }
            }
        }
    }
}