package vazkii.arl.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;

public final class ClientTicker {

	public static int ticksInGame = 0;
	public static float partialTicks = 0;
	public static float delta = 0;
	public static float total = 0;
	
	@ClientOnly
	private static void calcDelta() {
		float oldTotal = total;
		total = ticksInGame + partialTicks;
		delta = total - oldTotal;
	}

	public static void init() {
		ClientTickEvents.START.register(client -> {
			partialTicks = client.getFrameTime();
		});

		ClientTickEvents.END.register(client -> {
			Screen gui = Minecraft.getInstance().screen;
			if(gui == null || !gui.isPauseScreen()) {
				ticksInGame++;
				partialTicks = 0;
			}

			calcDelta();
		});
	}
}
