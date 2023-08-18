package vazkii.arl.quilt;

import java.util.concurrent.Executor;

import org.jetbrains.annotations.Nullable;

import net.minecraft.server.level.ServerPlayer;
import vazkii.arl.util.NetworkDirection;

public record NetworkContext(Executor executor, @Nullable ServerPlayer sender) {
	public void enqueueWork(Runnable runnable) {
		executor.execute(runnable);
	}

	public NetworkDirection getDirection() {
		return sender() == null ? NetworkDirection.PLAY_TO_SERVER : NetworkDirection.PLAY_TO_CLIENT;
	}
}
