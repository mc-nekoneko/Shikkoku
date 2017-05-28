package pro.shikkoku.util;

import com.google.common.io.Files;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by mcnek on 2017/05/15.
 */
@Data
public class JsonConfiguration {

    private final JavaPlugin plugin;
    private final File file;

    @Getter(AccessLevel.PROTECTED)
    private Gson gson = new Gson();

    public JsonConfiguration(JavaPlugin plugin) {
        this(plugin, new File(plugin.getDataFolder(), "config.json"));
    }

    public JsonConfiguration(JavaPlugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        checkOrCreate();
    }

    protected void setGson(@NonNull Gson gson) {
        this.gson = gson;
    }

    protected boolean checkOrCreate() {
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                return false;
            }
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return false;
                }
                save(new Object());
            }

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    protected <T> T load(Class<T> obj) {
        checkOrCreate();
        try {
            return GsonUtil.load(gson, file, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected <T> void save(T obj) {
        checkOrCreate();
        File temp = null;
        try {
            temp = File.createTempFile("config.json", "tmp", plugin.getDataFolder());
            Files.copy(file, temp);

            GsonUtil.save(gson, file, obj, obj.getClass());
            temp.deleteOnExit();
        } catch (IOException ex) {
            ex.printStackTrace();
            if (temp != null) {
                restore(temp, obj.getClass());
            }
        }
    }

    private void restore(File temp, Class<?> clazz) {
        try {
            GsonUtil.load(gson, temp, clazz);
            Files.copy(file, temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
