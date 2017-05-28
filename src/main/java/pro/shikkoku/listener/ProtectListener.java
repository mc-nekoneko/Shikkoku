package pro.shikkoku.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import pro.shikkoku.Shikkoku;

/**
 * Created by mcnek on 2017/05/28.
 */
@RequiredArgsConstructor
public class ProtectListener implements Listener {

    private final Shikkoku plugin;

    @EventHandler(ignoreCancelled = true)
    public void onArmorStandProtect(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (entity.getType() != EntityType.ARMOR_STAND) {
            return;
        }
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void playerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onArmorStandProtect(EntityDamageByEntityEvent event) {
        if ((event.getDamager() instanceof Player)) {
            Player player = (Player) event.getDamager();
            if ((event.getEntity().getType() != EntityType.ARMOR_STAND)) {
                return;
            }
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
        if (((event.getDamager() instanceof Projectile)) && (event.getEntity().getType().equals(EntityType.ARMOR_STAND))) {
            Projectile projectile = (Projectile) event.getDamager();
            @SuppressWarnings("deprecation")
            Player player = (Player) projectile.getShooter();
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFrameProtect(EntityDamageByEntityEvent event) {
        if ((event.getDamager() instanceof Player)) {
            Player player = (Player) event.getDamager();
            if ((event.getEntity().getType() != EntityType.ITEM_FRAME)) {
                return;
            }
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
        if (((event.getDamager() instanceof Projectile)) && (event.getEntity().getType() == EntityType.ITEM_FRAME)) {
            Projectile projectile = (Projectile) event.getDamager();
            @SuppressWarnings("deprecation")
            Player player = (Player) projectile.getShooter();
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFrameProtect(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (entity.getType() != EntityType.ITEM_FRAME) {
            return;
        }
        ItemFrame frame = (ItemFrame) entity;
        if ((frame.getItem() == null) || (frame.getItem().getType() == Material.AIR)) {
            return;
        }
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFrameProtect(HangingBreakByEntityEvent event) {
        if ((event.getRemover() instanceof Player)) {
            Player player = (Player) event.getRemover();
            if ((event.getEntity().getType() == EntityType.ITEM_FRAME) || (event.getEntity().getType() == EntityType.PAINTING)) {
                if (player.getGameMode() != GameMode.CREATIVE) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
