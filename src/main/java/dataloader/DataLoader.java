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
		return (name) -> {
			return Text.translatable("pack.nameAndSource", new Object[]{name, text}).formatted(Formatting.GRAY);
		};
	}

	public static final ResourcePackSource RESOURCE_PACK_SOURCE = ResourcePackSource.create(getSourceTextSupplier("pack.source.dataloader"), true);
	public static final Path DATAPACKS_PATH = FabricLoader.getInstance().getGameDir().resolve("datapacks");
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
