package vazkii.arl.quilt;

import java.util.concurrent.Executor;

import javax.annotation.Nullable;

import io.github.fabricators_of_create.porting_lib.util.NetworkDirection;
import net.minecraft.server.level.ServerPlayer;

public record NetworkContext(Executor executor, @Nullable ServerPlayer sender) {
	public void enqueueWork(Runnable runnable) {
		executor.execute(runnable);
	}

	public NetworkDirection getDirection() {
		return sender() == null ? NetworkDirection.PLAY_TO_SERVER : NetworkDirection.PLAY_TO_CLIENT;
	}
}
