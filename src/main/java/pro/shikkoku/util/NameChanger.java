package pro.shikkoku.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * This class allows you to change a player's nametag
 * <p>
 * It requires ProtocolLib and only works on 1.8-1.8.3
 * If you need a 1.7 version, use TagAPI
 * </p>
 *
 * @author Techcable
 */
public class NameChanger {

    private final Map<Player, String> fakeNames = new WeakHashMap<>();
    private final Plugin plugin;

    public NameChanger(Plugin plugin) {
        this.plugin = plugin;
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.PLAYER_INFO) {

            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacket().getPlayerInfoAction().read(0) != PlayerInfoAction.ADD_PLAYER) {
                    return;
                }

                List<PlayerInfoData> newPlayerInfoDataList = new ArrayList<>();
                List<PlayerInfoData> playerInfoDataList = event.getPacket().getPlayerInfoDataLists().read(0);
                for (PlayerInfoData playerInfoData : playerInfoDataList) {
                    if (playerInfoData == null || playerInfoData.getProfile() == null || Bukkit.getPlayer(playerInfoData.getProfile().getUUID()) == null) { //Unknown Player
                        newPlayerInfoDataList.add(playerInfoData);
                        continue;
                    }

                    WrappedGameProfile profile = playerInfoData.getProfile();
                    profile = profile.withName(getNameToSend(profile.getUUID()));
                    PlayerInfoData newPlayerInfoData = new PlayerInfoData(profile, playerInfoData.getPing(), playerInfoData.getGameMode(), playerInfoData.getDisplayName());
                    newPlayerInfoDataList.add(newPlayerInfoData);
                }
                event.getPacket().getPlayerInfoDataLists().write(0, newPlayerInfoDataList);
            }
        });
    }

    private String getNameToSend(UUID id) {
        Player player = Bukkit.getPlayer(id);
        if (!fakeNames.containsKey(player)) {
            return player.getName();
        }

        return fakeNames.get(player);
    }

    public void changeNameAdd(final Player player, String fakeName) {
        fakeNames.put(player, fakeName);
        refresh(player);
    }

    public void changeNameRemove(Player player) {
        if (!fakeNames.containsKey(player)) fakeNames.remove(player);
        refresh(player);
    }

    private void refresh(final Player player) {
        for (final Player forWhom : player.getWorld().getPlayers()) {
            if (player.equals(forWhom) || !player.getWorld().equals(forWhom.getWorld()) || !forWhom.canSee(player)) {
                forWhom.hidePlayer(player);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        forWhom.showPlayer(player);
                    }
                }.runTaskLater(plugin, 2);
            }
        }
    }
}
