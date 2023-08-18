/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 *
 * File Created @ [16/01/2016, 18:51:44 (GMT)]
 */
package vazkii.arl.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import vazkii.arl.quilt.NetworkContext;

public abstract class BlockEntityMessage<T extends BlockEntity> implements IMessage {

	public BlockPos pos;
	public ResourceLocation typeExpected;
	
	public BlockEntityMessage() { }

	public BlockEntityMessage(BlockPos pos, BlockEntityType<T> type) {
		this.pos = pos;
		typeExpected = Registry.BLOCK_ENTITY_TYPE.getKey(type);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public final void receive(NetworkContext context) {
		ServerLevel world = context.sender().getLevel();
		if(world.hasChunkAt(pos)) {
			BlockEntity tile = world.getBlockEntity(pos);
			if(tile != null && Registry.BLOCK_ENTITY_TYPE.getKey(tile.getType()).equals(typeExpected))
				context.enqueueWork(() -> receive((T) tile, context));
		}
	}

	public abstract void receive(T tile, NetworkContext context);

}
