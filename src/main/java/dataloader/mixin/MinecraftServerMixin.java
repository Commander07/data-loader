package dataloader.mixin;

import dataloader.DataLoader;
import net.minecraft.resource.*;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
						DataLoader.RESOURCE_PACK_SOURCE
				)
		);
	}

}
