package net.labymod.addons.truesight.core.gpuTape;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

public class GPUBoosterSubSetting extends Config {

  @ShowSettingInParent
  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(Boolean.TRUE).addChangeListener((type, oldValue, newValue) -> {
    if (oldValue.booleanValue() && !newValue.booleanValue()) {
      GpuTape.shouldFixOnDisable = true;
    }
  });

  @SettingRequires("enabled")
  @SwitchSetting
  private final ConfigProperty<Boolean> shouldIdealHash = new ConfigProperty<>(Boolean.FALSE);

  public ConfigProperty<Boolean> getShouldIdealHash() {
    return this.shouldIdealHash;
  }

  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }
}
