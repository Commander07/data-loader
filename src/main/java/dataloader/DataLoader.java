package dataloader;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourcePackSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataLoader implements ModInitializer {
	public static final ResourcePackSource RESOURCE_PACK_SOURCE = ResourcePackSource.nameAndSource("pack.source.dataloader");
	public static final Path DATAPACKS_PATH = FabricLoader.getInstance().getConfigDir().resolve("datapacks");
	public static final Logger LOGGER = LogManager.getLogger("dataloader");

	@Override
	public void onInitialize() {
		try {
			Path path = DATAPACKS_PATH;
			if (!Files.exists(path)) {
				Files.createDirectory(path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
