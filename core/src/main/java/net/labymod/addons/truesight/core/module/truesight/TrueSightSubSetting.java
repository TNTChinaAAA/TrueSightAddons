package net.labymod.addons.truesight.core.module.truesight;

import net.labymod.addons.truesight.core.TNTChina;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

public class TrueSightSubSetting extends Config {

  @ShowSettingInParent
  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true).addChangeListener((type, oldValue, newValue) -> {
    TNTChina.TRUESIGHT.toggle();
  });

  @SettingRequires("enabled")
  @SwitchSetting
  private final ConfigProperty<Boolean> onlyPlayer =  new ConfigProperty<>(true);

  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Boolean> getOnlyPlayer() {
    return this.onlyPlayer;
  }
}
