package dataloader;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.UnaryOperator;

public class DataLoader implements ModInitializer {
	public static UnaryOperator<Text> getSourceTextSupplier(String translationKey) {
		Text text = Text.translatable(translationKey);
		return (name) -> Text.translatable("pack.nameAndSource", name, text).formatted(Formatting.GRAY);
	}

	public static final ResourcePackSource RESOURCE_PACK_SOURCE = ResourcePackSource.create(getSourceTextSupplier("pack.source.dataloader"), true);
	public static final Path DATAPACKS_PATH = FabricLoader.getInstance().getGameDir().resolve("datapacks");
	public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("data-loader.json");
	public static final Logger LOGGER = LogManager.getLogger("data-loader");
	public static Config CONFIG;

	@Override
	public void onInitialize() {
		try {
			CONFIG = Config.load();
			Path path = DATAPACKS_PATH;
			if (!Files.exists(path)) {
				Files.createDirectory(path);
			}
		} catch (IOException e) {
			LOGGER.error("Failed to initialize data loader: ", e);
		}

	}
}
