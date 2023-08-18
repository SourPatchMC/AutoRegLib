package vazkii.arl.util;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ARLClientInitializer implements ClientModInitializer {
	public static void registerBlockColors() {
		RegistryHelper.submitBlockColors(ColorProviderRegistry.BLOCK::register);
	}

	public static void registerItemColors() {
		RegistryHelper.submitItemColors(ColorProviderRegistry.ITEM::register);
	}

	@Override
	public void onInitializeClient(ModContainer mod) {
		registerBlockColors();
		registerItemColors();
	}
}
