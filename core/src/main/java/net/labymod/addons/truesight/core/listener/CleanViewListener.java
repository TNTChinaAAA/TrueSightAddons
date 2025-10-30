package net.labymod.addons.truesight.core.listener;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

public class CleanViewListener {

  @Subscribe
  public void onTick(GameTickEvent event) {
      if (TrueSightAddon.addon == null) return;

      if (event.phase() == Phase.PRE) {
          if (TrueSightAddon.cleanView != null) {
              TrueSightAddon.cleanView.onCleanView();
          }
      }
  }
}
