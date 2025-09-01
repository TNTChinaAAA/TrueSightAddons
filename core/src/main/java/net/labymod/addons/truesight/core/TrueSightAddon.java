package net.labymod.addons.truesight.core;

import net.labymod.addons.truesight.core.commands.ExamplePingCommand;
import net.labymod.addons.truesight.core.listener.TrueSightGameTickListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class TrueSightAddon extends LabyAddon<TrueSightConfiguration> {

    public static TrueSightAddon addon;

    @Override
    protected void enable() {
        this.registerSettingCategory();
        this.registerListener(new TrueSightGameTickListener(this));
        this.registerCommand(new ExamplePingCommand());
        TrueSightAddon.addon = this;
        TNTChina.RENDER_MODULES_MAP.put(TNTChina.AUTOTOOL, this.configuration().getAutoTool());
        TNTChina.RENDER_MODULES_MAP.put(TNTChina.ESP, this.configuration().getEsp().getEnabled());
        TNTChina.RENDER_MODULES_MAP.put(TNTChina.TRUESIGHT, this.configuration().getTrueSight());
        this.logger().info("Enabled TrueSight Addon");
    }

    @Override
    protected Class<TrueSightConfiguration> configurationClass() {
        return TrueSightConfiguration.class;
    }
}
