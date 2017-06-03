package pro.shikkoku.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;

/**
 * Created by mcnek on 2017/06/03.
 */
@UtilityClass
public class Util {

    public static World getMainWorld() {
        return Bukkit.getWorlds().get(0);
    }

    public static void playerDataRemove() {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> player.kickPlayer("プレイヤーデータを削除しました"));
        File playerFolder = new File(getMainWorld().getWorldFolder(), "playerdata");
        if (!playerFolder.canWrite()) {
            return;
        }
        File[] playerData = playerFolder.listFiles();
        if (nullOrEmpty(playerData)) {
            return;
        }
        for (File file : playerData) {
            delete(file);
        }
    }

    private static void delete(@NonNull File file) {
        if (file.canWrite()) {
            file.delete();
        }
    }

    public static boolean nullOrEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
}
