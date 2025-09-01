package net.labymod.addons.truesight.core.module.esp;

import net.labymod.addons.truesight.core.TNTChina;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class ESPSubSetting extends Config {

  @ShowSettingInParent
  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true).addChangeListener((type, oldValue, newValue) -> {
    TNTChina.ESP.toggle();
  });

  @DropdownSetting
  private final ConfigProperty<EnumESPMode> espMode = new ConfigProperty<>(EnumESPMode.OUTLINE);

  @SwitchSetting
  private final ConfigProperty<Boolean> onlyPlayer =  new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> attackCheck =  new ConfigProperty<>(true);

  public ConfigProperty<Boolean> getEnabled() {
    return this.enabled;
  }

  public EnumESPMode getEspMode() {
    return this.espMode.get();
  }

  public ConfigProperty<Boolean> getOnlyPlayer() {
    return this.onlyPlayer;
  }

  public ConfigProperty<Boolean> getAttackCheck() {
    return this.attackCheck;
  }
}
