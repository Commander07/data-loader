package dataloader.mixin;

import com.google.common.collect.ImmutableSet;
import dataloader.DataLoader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.*;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(value = MinecraftServer.class, priority = 1001)
public class MinecraftServerMixin {
	@Inject(method = "loadDataPacks", at = @At("HEAD"))
	private static void loadDataPacks(
			ResourcePackManager resourcePackManager, DataPackSettings dataPackSettings, boolean safeMode, FeatureSet enabledFeatures, CallbackInfoReturnable<DataConfiguration> cir
	) {
		resourcePackManager.providers.add(
				new FileResourcePackProvider(
						DataLoader.DATAPACKS_PATH,
						ResourceType.SERVER_DATA,
						DataLoader.RESOURCE_PACK_SOURCE,
						LevelStorage.createSymlinkFinder(FabricLoader.getInstance().getGameDir().resolve(LevelStorage.ALLOWED_SYMLINKS_FILE_NAME))
				)
		);
	}

	@Inject(method = "createDataPackSettings", at = @At("HEAD"))
	private static void createDataPackSettings(ResourcePackManager dataPackManager, CallbackInfoReturnable<DataPackSettings> cir) {
		ArrayList<String> enabledPacks = new ArrayList<>();
		if (!DataLoader.CONFIG.onlyLoadSpecified)
			enabledPacks.addAll(
					dataPackManager.getEnabledNames().stream().filter(x -> !DataLoader.CONFIG.loadOrder.contains(x)).collect(ImmutableSet.toImmutableSet())
			);
		else if (!DataLoader.CONFIG.loadOrder.contains("fabric"))
			enabledPacks.add("fabric");
		enabledPacks.addAll(DataLoader.CONFIG.loadOrder);

		DataLoader.LOGGER.info("Applying datapacks: {}", enabledPacks);
		dataPackManager.setEnabledProfiles(enabledPacks);
	}
}
