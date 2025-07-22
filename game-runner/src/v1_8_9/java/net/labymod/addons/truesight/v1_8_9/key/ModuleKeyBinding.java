package net.labymod.addons.truesight.v1_8_9.key;

import net.labymod.addons.truesight.core.Module;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.minecraft.client.settings.KeyBinding;

public class ModuleKeyBinding {

  public final Module module;
  public final KeyBinding keyBinding;
  public final ConfigProperty<Boolean> state;

  public ModuleKeyBinding(Module module, KeyBinding keyBinding, ConfigProperty<Boolean> state) {
    this.module = module;
    this.keyBinding = keyBinding;
    this.state = state;
  }

  public Module getModule() {
    return this.module;
  }

  public KeyBinding getKeyBinding() {
    return this.keyBinding;
  }

  public ConfigProperty<Boolean> getState() {
    return this.state;
  }
}
