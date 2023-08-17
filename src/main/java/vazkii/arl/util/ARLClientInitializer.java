package vazkii.arl.util;

import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import vazkii.arl.AutoRegLib;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AutoRegLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ARLClientInitializer implements ClientModInitializer {
	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block evt) {
		RegistryHelper.submitBlockColors(evt.getBlockColors()::register);

	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item evt) {
		RegistryHelper.submitItemColors(evt.getItemColors()::register);
	}
}
