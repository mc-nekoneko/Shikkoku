package pro.shikkoku;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pro.shikkoku.configure.NameStore;
import pro.shikkoku.listener.PlayerListener;
import pro.shikkoku.listener.ProtectListener;
import pro.shikkoku.listener.ProtocolListener;

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
        //ProtocolLibrary.getProtocolManager().addPacketListener(new ProtocolListener(this));
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
        if (!player.hasPermission("shikkoku.namechange")) {
            return true;
        }
        if (command.getName().equalsIgnoreCase("namechange")) {
            String changes = args[0];
            nameStore.setName(player, changes, true);
            player.sendMessage("Changed DisplayName: " + changes);
        }
        if (command.getName().equalsIgnoreCase("resetname")) {
            nameStore.setName(player, player.getName(), true);
            player.sendMessage("ResetName");
        }
        return true;
    }
}
