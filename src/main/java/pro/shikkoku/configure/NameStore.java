package pro.shikkoku.configure;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pro.shikkoku.util.JsonConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by mcnek on 2017/05/28.
 */
public class NameStore extends JsonConfiguration {

    private Data data = new Data();

    public NameStore(JavaPlugin plugin) {
        super(plugin, new File(plugin.getDataFolder(), "namestore.json"));
        save(data);
    }

    public void load() {
        data = load(Data.class);
    }

    public void save() {
        save(data);
    }

    public void setName(Player player, String name, boolean put) {
        if (put) {
            data.getNames().put(player.getUniqueId(), name);
        }
        player.setDisplayName(name);
        player.setCustomName(name);
        player.setPlayerListName(name);
    }

    public String getName(Player player) {
        return data.get(player.getUniqueId()).orElse(player.getName());
    }

    public Optional<String> getName(UUID uuid) {
        return data.get(uuid);
    }

    private class Data {

        @Getter
        private final Map<UUID, String> names;

        private Data() {
            names = new HashMap<>();
        }

        private Optional<String> get(UUID uuid) {
            if (!names.containsKey(uuid)) {
                return Optional.empty();
            }
            return Optional.ofNullable(names.get(uuid));
        }
    }
}
