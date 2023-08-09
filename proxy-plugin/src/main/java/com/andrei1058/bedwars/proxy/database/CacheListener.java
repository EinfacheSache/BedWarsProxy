package com.andrei1058.bedwars.proxy.database;

import com.andrei1058.bedwars.proxy.BedWarsProxy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheListener implements Listener {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @EventHandler
    // Create cache for player if does not exist yet.
    public void onLogin(PlayerLoginEvent e) {
        if (e == null) return;
        executorService.submit(() -> {
            final Player p = e.getPlayer();
            Bukkit.getScheduler().runTaskAsynchronously(BedWarsProxy.getPlugin(), () -> {
                //create cache row for player
                BedWarsProxy.getStatsCache().createStatsCache(p);
                //update local cache for player
                BedWarsProxy.getRemoteDatabase().updateLocalCache(p.getUniqueId());
            });
        });
    }
}
