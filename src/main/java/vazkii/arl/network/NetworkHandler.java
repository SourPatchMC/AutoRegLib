/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 *
 * File Created @ [11/01/2016, 21:58:25 (GMT)]
 */
package vazkii.arl.network;

import java.util.function.Function;

import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import vazkii.arl.util.NetworkDirection;

public class NetworkHandler {
	
	public final SimpleChannel channel;
	
	private int i = 0;
	
	public NetworkHandler(String modid) {
		this(modid, "main");
	}
	
	public NetworkHandler(String modid, String channelName) {
		this.channel = new SimpleChannel(new ResourceLocation(modid, channelName));
	}
	
	public <T extends IMessage> void register(Class<T> clazz, NetworkDirection dir) {
		Function<FriendlyByteBuf, T> decoder = (buf) -> {
			try {
				T msg = clazz.getDeclaredConstructor().newInstance();
				MessageSerializer.readObject(msg, buf);
				return msg;
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			} 
		};

		switch (dir) {
			case PLAY_TO_CLIENT:
				channel.registerS2CPacket(clazz, i, decoder);
				break;
			case PLAY_TO_SERVER:
				channel.registerC2SPacket(clazz, i, decoder);
				break;
		}

		i++;
	}

	public void sendToPlayer(IMessage msg, ServerPlayer player) {
		channel.sendToClient(msg, player);
	}
	
	public void sendToServer(IMessage msg) {
		channel.sendToServer(msg);
	}
	
}
