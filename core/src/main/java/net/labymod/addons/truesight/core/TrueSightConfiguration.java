package net.labymod.addons.truesight.core;

import net.labymod.addons.truesight.core.gpuTape.GPUBoosterSubSetting;
import net.labymod.addons.truesight.core.gpuTape.GpuTape;
import net.labymod.addons.truesight.core.module.esp.ESPSubSetting;
import net.labymod.addons.truesight.core.module.truesight.TrueSightSubSetting;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

@ConfigName("settings")
public class TrueSightConfiguration extends AddonConfig {

    //@SettingSection("truesightAddon")
    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(Boolean.TRUE);

    @SettingSection("gui")
    @SwitchSetting
    private final ConfigProperty<Boolean> status = new ConfigProperty<>(Boolean.TRUE);

    @SettingSection("features")
    @SwitchSetting
    private final ConfigProperty<Boolean> autoTool = new ConfigProperty<>(Boolean.FALSE).addChangeListener((type, oldValue, newValue) -> {
            TNTChina.AUTOTOOL.toggle();
    });

    @SwitchSetting
    private final ESPSubSetting esp = new ESPSubSetting();

    @SwitchSetting
    private final TrueSightSubSetting truesight = new TrueSightSubSetting();

    @SettingSection("mouseDelayFix")
    @SwitchSetting
    private final ConfigProperty<Boolean> mouseDelayFix = new ConfigProperty<>(Boolean.TRUE);

    @SettingSection("jumpDelayFix")
    @SwitchSetting
    private final ConfigProperty<Boolean> jumpDelayFix = new ConfigProperty<>(Boolean.TRUE);

    /*@SettingSection("optifineOptimization")
    @SwitchSetting
    private final ConfigProperty<Boolean> optifineSkyFix = new ConfigProperty<>(Boolean.FALSE);
    */

    @SettingSection("optimization")
    @SwitchSetting
    private final GPUBoosterSubSetting gpuBooster = new GPUBoosterSubSetting();

    /*@SwitchSetting
    private final ConfigProperty<Boolean> memoryFix = new ConfigProperty<>(Boolean.TRUE);
    */

    @SwitchSetting
    private final ConfigProperty<Boolean> itemOptimizations = new ConfigProperty<>(Boolean.TRUE);

    @SwitchSetting
    private final ConfigProperty<Boolean> cleanView = new ConfigProperty<>(Boolean.TRUE);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    public ESPSubSetting getEsp() {
        return this.esp;
    }

    public TrueSightSubSetting getTrueSight() {
      return this.truesight;
    }

    public ConfigProperty<Boolean> getAutoTool() {
      return this.autoTool;
    }

    public ConfigProperty<Boolean> getMouseDelayFix() {
      return this.mouseDelayFix;
    }

    public ConfigProperty<Boolean> getJumpDelayFix() {
    return this.jumpDelayFix;
  }

    public ConfigProperty<Boolean> getStatus() {
      return this.status;
    }

    public GPUBoosterSubSetting getGpuBooster() {
      return this.gpuBooster;
    }

    /*public ConfigProperty<Boolean> getMemoryFix() {
      return this.memoryFix;
    }*/

    public ConfigProperty<Boolean> getItemOptimizations() {
      return this.itemOptimizations;
    }

    public ConfigProperty<Boolean> getCleanView() {
      return this.cleanView;
    }

    /*
    public ConfigProperty<Boolean> getOptifineSkyFix() {
      return this.optifineSkyFix;
    }*/
}
