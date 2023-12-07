package dataloader;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Config {
    private static final Gson gson = new Gson();
    public Boolean onlyLoadSpecified = false;
    public ArrayList<String> loadOrder = new ArrayList<>();

    public static Config load() throws IOException {
        Config config;
        if (Files.isRegularFile(DataLoader.CONFIG_PATH)) {
            config = gson.fromJson(new String(Files.readAllBytes(DataLoader.CONFIG_PATH)), Config.class);
        } else {
            config = new Config();
            Files.createDirectories(DataLoader.CONFIG_PATH.getParent());
            Files.createFile(DataLoader.CONFIG_PATH);
            Files.write(DataLoader.CONFIG_PATH, gson.toJson(config).getBytes());
        }
        return config;
    }
}
