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
        this.logger().info("Enabled the Addon");
    }

    @Override
    protected Class<TrueSightConfiguration> configurationClass() {
        return TrueSightConfiguration.class;
    }
}
