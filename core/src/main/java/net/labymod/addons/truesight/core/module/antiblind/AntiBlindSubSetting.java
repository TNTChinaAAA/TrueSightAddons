package net.labymod.addons.truesight.core.module.antiblind;

import net.labymod.addons.truesight.core.TNTChina;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

public class AntiBlindSubSetting extends Config {

  @ShowSettingInParent
  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(Boolean.TRUE);

  @SettingRequires("enabled")
  @SwitchSetting
  private final ConfigProperty<Boolean> confusionEffect = new ConfigProperty<>(Boolean.TRUE);

  @SettingRequires("enabled")
  @SwitchSetting
  private final ConfigProperty<Boolean> pumpkinEffect = new ConfigProperty<>(Boolean.TRUE);

  @SettingRequires("enabled")
  @SwitchSetting
  private final ConfigProperty<Boolean> fireEffect = new ConfigProperty<>(Boolean.FALSE);

  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Boolean> getConfusionEffect() {
    return this.confusionEffect;
  }

  public ConfigProperty<Boolean> getPumpkinEffect() {
    return this.pumpkinEffect;
  }

  public ConfigProperty<Boolean> getFireEffect() {
    return this.fireEffect;
  }
}
