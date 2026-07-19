package com.skillplugins.advancedmlgrush.listener.listeners;

import com.google.inject.Singleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PlayerAchievementAwardedListener implements Listener {

    @EventHandler
    public void onAchievementAwarded(final @NotNull PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }
}
