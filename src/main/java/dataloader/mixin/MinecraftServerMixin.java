package dataloader.mixin;

import com.google.common.collect.ImmutableSet;
import dataloader.DataLoader;
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackCreator;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_10958;
import net.minecraft.resource.*;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;

@Mixin(value = class_10958.class, priority = 1001)
public class MinecraftServerMixin {
	@Inject(method = "method_68919(Lnet/minecraft/resource/ResourcePackManager;Ljava/util/Collection;Lnet/minecraft/resource/featuretoggle/FeatureSet;Z)Lnet/minecraft/resource/DataConfiguration;", at = @At("HEAD"))
	private static void loadDataPacks(
			ResourcePackManager resourcePackManager, Collection<String> enabledProfiles, FeatureSet enabledFeatures, boolean allowEnabling, CallbackInfoReturnable<DataConfiguration> cir
	) {
		ArrayList<ResourcePackProvider> providers = new ArrayList<>(resourcePackManager.providers);
		providers.add(
				new FileResourcePackProvider(
						DataLoader.DATAPACKS_PATH,
						ResourceType.SERVER_DATA,
						DataLoader.RESOURCE_PACK_SOURCE,
						LevelStorage.createSymlinkFinder(FabricLoader.getInstance().getGameDir().resolve(LevelStorage.ALLOWED_SYMLINKS_FILE_NAME))
				)
		);
		resourcePackManager.providers = ImmutableSet.copyOf(providers);
		resourcePackManager.scanPacks();
	}

	@Inject(method = "method_68920", at = @At("HEAD"))
	private static void createDataPackSettings(ResourcePackManager dataPackManager, boolean allowEnabling, CallbackInfoReturnable<DataPackSettings> cir) {
		ArrayList<String> enabledPacks = new ArrayList<>();
		if (!DataLoader.CONFIG.onlyLoadSpecified) {
			enabledPacks.add("vanilla");
			enabledPacks.addAll(
					dataPackManager.getProfiles().stream().filter(it -> it.getSource() == ModResourcePackCreator.RESOURCE_PACK_SOURCE).map(ResourcePackProfile::getId).filter(x -> !DataLoader.CONFIG.loadOrder.contains(x)).collect(ImmutableSet.toImmutableSet())
			);
			enabledPacks.addAll(
					dataPackManager.getProfiles().stream().filter(it -> it.getSource() == DataLoader.RESOURCE_PACK_SOURCE).map(ResourcePackProfile::getId).filter(x -> !DataLoader.CONFIG.loadOrder.contains(x)).collect(ImmutableSet.toImmutableSet())
			);
		}
		else if (!DataLoader.CONFIG.loadOrder.contains("fabric"))
			enabledPacks.add("fabric");
		enabledPacks.addAll(DataLoader.CONFIG.loadOrder);

		DataLoader.LOGGER.info("Applying datapacks: {}", enabledPacks);
		dataPackManager.setEnabledProfiles(enabledPacks);
	}
}
