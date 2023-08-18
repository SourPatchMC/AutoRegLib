package vazkii.arl.network;

import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import vazkii.arl.quilt.NetworkContext;

@SuppressWarnings("deprecation")
public interface IMessage extends C2SPacket, S2CPacket {

	void receive(NetworkContext context);


	@Override
	default void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener,
			PacketSender responseSender, SimpleChannel channel) {
		receive(new NetworkContext(server, player));
	}

	@Override
	default void handle(Minecraft client, ClientPacketListener listener, PacketSender responseSender,
			SimpleChannel channel) {
		receive(new NetworkContext(client, null));
	}

	@Override
	default void encode(FriendlyByteBuf buf) {
		MessageSerializer.writeObject(this, buf);
	}

}