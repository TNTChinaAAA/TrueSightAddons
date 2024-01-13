package net.labymod.addons.truesight.core.listener;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

public class TrueSightGameTickListener {

    private final TrueSightAddon addon;

    public TrueSightGameTickListener(TrueSightAddon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }

        //this.addon.logger().info(this.addon.configuration().enabled().get() ? "enabled" : "disabled");
    }
}
