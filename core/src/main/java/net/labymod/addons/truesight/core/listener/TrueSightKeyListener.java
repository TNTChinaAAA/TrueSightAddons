package net.labymod.addons.truesight.core.listener;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;

public class TrueSightKeyListener {
    private final TrueSightAddon addon;

    public TrueSightKeyListener(TrueSightAddon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onKeyInput(KeyEvent event) {

    }
}
