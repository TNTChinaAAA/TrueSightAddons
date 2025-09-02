package net.labymod.addons.truesight.core.module.esp;

import net.labymod.addons.truesight.core.TNTChina;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget.ColorPickerSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import java.awt.*;

public class ESPSubSetting extends Config {

  @ShowSettingInParent
  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true).addChangeListener((type, oldValue, newValue) -> {
    TNTChina.ESP.toggle();
  });

  @SettingRequires("enabled")
  @DropdownSetting
  private final ConfigProperty<EnumESPMode> espMode = new ConfigProperty<>(EnumESPMode.OUTLINE);

  @SettingRequires("enabled")
  @SwitchSetting
  private final ConfigProperty<Boolean> onlyPlayer =  new ConfigProperty<>(Boolean.TRUE);

  @SettingRequires("enabled")
  @SwitchSetting
  private final ConfigProperty<Boolean> attackCheck =  new ConfigProperty<>(Boolean.TRUE);

  @SettingRequires("enabled")
  @SwitchSetting
  private final ConfigProperty<Boolean> targetNPC =  new ConfigProperty<>(Boolean.FALSE);

  @SettingRequires("enabled")
  @ColorPickerSetting
  private final ConfigProperty<Integer> entityColor = new ConfigProperty<>(new Color(18, 144, 195).getRGB()); //new Color(10, 85, 165).getRGB();

  @SettingRequires("enabled")
  @SliderSetting(steps = 0.1f, min = 0.5f, max = 3.0f)
  private final ConfigProperty<Float> lineWidth = new ConfigProperty<>(1.0F);

  public ConfigProperty<Boolean> enabled() {
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

  public ConfigProperty<Boolean> getTargetNPC() {
    return this.targetNPC;
  }

  public ConfigProperty<Integer> getEntityColor() {
    return this.entityColor;
  }

  public ConfigProperty<Float> getLineWidth() {
    return this.lineWidth;
  }
}
