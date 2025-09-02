package net.labymod.addons.truesight.v1_8_9.key;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.labymod.addons.truesight.core.TNTChina;
import org.apache.commons.lang3.ArrayUtils;
import java.util.ArrayList;
import java.util.List;

public class KeyLoader {

    public static List<ModuleKeyBinding> moduleKeyBindings = new ArrayList<>();

    public KeyLoader() {
        moduleKeyBindings.add(new ModuleKeyBinding(TNTChina.ESP, new KeyBinding("ESP", TNTChina.ESP.getKey(), "TrueSight"), TrueSightAddon.addon.configuration()
            .getEsp().enabled()));
        moduleKeyBindings.add(new ModuleKeyBinding(TNTChina.TRUESIGHT, new KeyBinding("TrueSight", TNTChina.TRUESIGHT.getKey(), "TrueSight"), TrueSightAddon.addon.configuration()
            .getTrueSight().enabled()));
        moduleKeyBindings.add(new ModuleKeyBinding(TNTChina.AUTOTOOL, new KeyBinding("TrueSight", TNTChina.AUTOTOOL.getKey(), "TrueSight"), TrueSightAddon.addon.configuration()
            .getAutoTool()));

        for (ModuleKeyBinding keyBinding : moduleKeyBindings) {
            keyBinding.getModule().setState(keyBinding.getState().get().booleanValue());
            this.registerKeyBinding(keyBinding.getKeyBinding());
        }
    }

    public void registerKeyBinding(KeyBinding keyBinding) {
        Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.add(Minecraft.getMinecraft().gameSettings.keyBindings, keyBinding);
    }

    public static void onKeyInput() {
        if (TrueSightAddon.addon != null) {
            if (TrueSightAddon.addon.configuration().enabled().get().booleanValue()) {
              for (ModuleKeyBinding keyBinding : moduleKeyBindings) {
                  if (keyBinding.getKeyBinding().isPressed()) {
                      keyBinding.getState().set(!keyBinding.getState().get().booleanValue());
                  }
              }
            }
        }
    }
}