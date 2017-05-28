package pro.shikkoku;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pro.shikkoku.configure.NameStore;
import pro.shikkoku.listener.PlayerListener;
import pro.shikkoku.listener.ProtectListener;

public final class Shikkoku extends JavaPlugin {

    private final PluginManager pluginManager = getServer().getPluginManager();

    @Getter
    private NameStore nameStore;

    @Override
    public void onEnable() {
        super.onEnable();
        nameStore = new NameStore(this);
        nameStore.load();
        pluginManager.registerEvents(new PlayerListener(this), this);
        pluginManager.registerEvents(new ProtectListener(this), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        nameStore.save();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("namechange")) {
            String changes = args[0];
            nameStore.setName(player, changes, true);
            player.sendMessage("Changed DisplayName: " + changes);
        }
        return true;
    }
}
