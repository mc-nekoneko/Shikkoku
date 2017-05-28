package pro.shikkoku.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pro.shikkoku.Shikkoku;

/**
 * Created by mcnek on 2017/05/28.
 */
@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private final Shikkoku plugin;

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String changed = plugin.getNameStore().getName(player);
        plugin.getNameStore().setName(player, changed, false);
        event.setJoinMessage(ChatColor.YELLOW + changed + " joined the game");
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String changed = plugin.getNameStore().getName(player);
        event.setQuitMessage(ChatColor.YELLOW + changed + " left the game");
    }

    @EventHandler
    public void asyncPlayerChat(AsyncPlayerChatEvent event) {
        event.setFormat(String.format("<%1$s> %2$s", event.getPlayer().getDisplayName(), event.getMessage()));
    }
}
