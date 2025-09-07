package net.labymod.addons.truesight.core;

import net.labymod.addons.truesight.core.commands.*;
import net.labymod.addons.truesight.core.listener.*;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class TrueSightAddon extends LabyAddon<TrueSightConfiguration> {

    public static TrueSightAddon addon;
    public static ESPSubSettingSettingUpdateEventListener settingUpdateEventListener;

    @Override
    protected void enable() {
        TrueSightAddon.addon = this;
        TrueSightAddon.settingUpdateEventListener = new ESPSubSettingSettingUpdateEventListener(this);
        this.registerSettingCategory();
        this.registerListener(TrueSightAddon.settingUpdateEventListener);
        this.registerListener(new TrueSightGameTickListener(this));
        this.registerCommand(new ExamplePingCommand());

        TNTChina.RENDER_MODULES_MAP.put(TNTChina.AUTOTOOL, this.configuration().getAutoTool());
        TNTChina.RENDER_MODULES_MAP.put(TNTChina.ESP, this.configuration().getEsp().enabled());
        TNTChina.RENDER_MODULES_MAP.put(TNTChina.TRUESIGHT, this.configuration().getTrueSight().enabled());
        this.logger().info("Enabled TrueSight Addon");
    }

    @Override
    protected Class<TrueSightConfiguration> configurationClass() {
        return TrueSightConfiguration.class;
    }
}
