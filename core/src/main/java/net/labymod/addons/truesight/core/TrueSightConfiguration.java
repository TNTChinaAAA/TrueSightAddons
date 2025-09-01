package net.labymod.addons.truesight.core;

import net.labymod.addons.truesight.core.module.esp.ESPSubSetting;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

@ConfigName("settings")
public class TrueSightConfiguration extends AddonConfig {

    //@SettingSection("truesightAddon")
    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SettingSection("features")
    @SwitchSetting
    private final ConfigProperty<Boolean> autoTool = new ConfigProperty<>(true).addChangeListener((type, oldValue, newValue) -> {
            TNTChina.AUTOTOOL.toggle();
    });

    @SwitchSetting
    private final ESPSubSetting esp = new ESPSubSetting();

    @SwitchSetting
    private final ConfigProperty<Boolean> trueSight = new ConfigProperty<>(true).addChangeListener((type, oldValue, newValue) -> {
            TNTChina.TRUESIGHT.toggle();
    });

    @SettingSection("mouseDelayFix")
    @SwitchSetting
    private final ConfigProperty<Boolean> mouseDelayFix = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    public ESPSubSetting getEsp() {
      return this.esp;
    }

    public ConfigProperty<Boolean> getTrueSight() {
      return this.trueSight;
    }

    public ConfigProperty<Boolean> getAutoTool() {
      return this.autoTool;
    }

    public ConfigProperty<Boolean> getMouseDelayFix() {
      return this.mouseDelayFix;
    }
}
